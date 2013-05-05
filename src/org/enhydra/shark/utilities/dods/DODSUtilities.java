/* DODSUtilities.java */

package org.enhydra.shark.utilities.dods;

import com.lutris.appserver.server.sql.DBTransaction;
import com.lutris.appserver.server.sql.DatabaseManager;
import com.lutris.appserver.server.sql.StandardDatabaseManager;
import com.lutris.util.Config;
import com.lutris.util.ConfigFile;
import com.lutris.util.ConfigParser;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.enhydra.dods.DODS;
import org.enhydra.shark.api.RootException;
import com.lutris.logging.Logger;
import com.lutris.logging.Log4jLogger;
import org.apache.log4j.PropertyConfigurator;

/**
 * DODSUtilities represents a <i>toolbox</i> for DODS based modules.
 * There are static methods only in charge of some specific task.
 *
 * @author Zoran Milakovic
 * @author Vladimir Puskas
 * @author Sasa Bojanic
 */
public class DODSUtilities {

   public final static String NULL_VALUE_FOR_PROCID = "~@#proc_id#@~";

   private static Map counterCachesSizes=new HashMap();
   private static Map counterCachesMax=new HashMap();
   private static Map counterCachesCurrent=new HashMap();
   private static Map counterCachesNext=new HashMap();

   private static Properties props;

   private static final String COUNTER_CACHE_PREFFIX="DODS.IdGenerator.";
   private static final String COUNTER_CACHE_POSTFIX=".CacheSize";
   private static final String COUNTER_DEFAULT_CACHE="DODS.defaults.IdGenerator.CacheSize";
   private static final long DEFAULT_CACHE_SIZE=100;

   private static int allocTryCount=50;
   //private constructor to prevent instantiate of Counter
   private DODSUtilities() { }

   /**
    * Gets next value for counter named in objectName parameter
    * if counter exists, otherwise new one will be started with value 1.
    *
    * @param    objectName          name of a counter
    *
    * @return   next value available for the counter
    *
    * @exception   RootException thrown if counter value cannot be acquired
    *
    */
   public static synchronized BigDecimal getNext(String objectName) throws RootException {
      if (objectName==null) {
         throw new RootException("Object name parameter can't be null");
      }
      try {
         if (counterCachesNext.get(objectName) == null ||
             counterCachesMax.get(objectName) == null ||
             counterCachesNext.get(objectName).equals(counterCachesMax.get(objectName))) {
            updateCaches(objectName);
         }
         counterCachesCurrent.put(objectName,counterCachesNext.get(objectName)); // next free
         counterCachesNext.put(objectName,((BigDecimal)counterCachesNext.get(objectName)).add(new BigDecimal("1")));
         return (BigDecimal)counterCachesCurrent.get(objectName);
      } catch (Exception e) {
         throw new RootException("Counter Id Allocator failed to allocate object id.",e);
      }
   }

   private static void updateCaches (String objectName) throws RootException {
      DBTransaction trans = null;
      // try it 50 times
      for (int iTry = 0; iTry < allocTryCount; iTry++) {
         try {

            trans = DODS.getDatabaseManager().createTransaction();
            CounterQuery qryCounter = new CounterQuery(trans);
            qryCounter.requireUniqueInstance();
            qryCounter.setQueryName(objectName);
            CounterDO doCounter = qryCounter.getNextDO();

            BigDecimal dbNext;
            if (doCounter == null) {
               // insert new Counter
               doCounter = CounterDO.createVirgin(trans);
               doCounter.setName(objectName);
               dbNext = new BigDecimal("1");
            } else {
               // increment existing Counter
               dbNext = doCounter.getThe_number();
            }

            BigDecimal dbLast = dbNext;

            dbNext = dbNext.add(BigDecimal.valueOf(getCacheSize(objectName)));

            doCounter.setThe_number(dbNext);
            doCounter.save(trans);
            trans.commit();
            trans.release();

            counterCachesNext.put(objectName,dbLast);
            counterCachesMax.put(objectName,dbNext);

            return;
         } catch (Exception e) {
            if (trans != null) {
               trans.release();
            }
         }
      }
      // this line should normaly never be executed
      throw new RootException("Can't allocate Id for object table "+objectName);
   }

   private static long getCacheSize (String objectName) {
      long cacheSize=-1;
      Object cs=counterCachesSizes.get(objectName);
      if (cs==null) {
         String propName=COUNTER_CACHE_PREFFIX+objectName+COUNTER_CACHE_POSTFIX;
         String cSize=DODSUtilities.props.getProperty(propName);
         String defCSize=DODSUtilities.props.getProperty(COUNTER_DEFAULT_CACHE);
         if (cSize!=null) {
            try {
               cacheSize=Long.parseLong(cSize);
               if (cacheSize<=0) {
                  cacheSize=-1;
                  System.err.println("Wrong value for "+objectName+" cache size");
               }
            } catch (Exception ex) {
               cacheSize=-1;
               System.err.println("Wrong value for "+objectName+" cache size");
            }
         }
         if (cacheSize==-1) {
            if (defCSize!=null) {
               try {
                  cacheSize=Long.parseLong(defCSize);
                  if (cacheSize<=0) {
                     cacheSize=DEFAULT_CACHE_SIZE;
                     System.err.println("Wrong value for default cache size, using default size "+DEFAULT_CACHE_SIZE);
                  }
               } catch (Exception ex) {
                  cacheSize=DEFAULT_CACHE_SIZE;
                  System.err.println("Wrong value for default cache size, using default size "+DEFAULT_CACHE_SIZE);
               }
            } else {
               cacheSize=DEFAULT_CACHE_SIZE;
            }
         }
         counterCachesSizes.put(objectName,new Long(cacheSize));
      } else {
         cacheSize=((Long)cs).longValue();
      }
      return cacheSize;
   }


   /**
    * DODS runtime initialization.
    *
    * @param    props properites which will be used for init
    *
    * @exception   RootException
    *
    */
   public static synchronized void init(final Properties props) throws RootException {
      if (DODSUtilities.props==null) {
         DODSUtilities.props=new Properties(props);
      }
      if (null == DODS.getDatabaseManager()) {
         Logger enhydraLogger = new Log4jLogger(true) {
            public void configure(String s) {
               PropertyConfigurator.configure(props);
            }
         };
         DODS.registerDefaultLogChannel(enhydraLogger
                                           .getChannel("DatabaseManager"));
         DODS.setThreading(false);
         try {
            StringWriter sw = new StringWriter();
            for (Iterator it = props.entrySet().iterator(); it.hasNext();) {
               Map.Entry me = (Map.Entry)it.next();
               String key =((String)me.getKey()).trim();
               if (key.startsWith("DatabaseManager.")) {
                  sw.write(key+" = "+me.getValue()+"\n");
               }
            }
            /**
            ConfigParser cp = new ConfigParser(new StringReader(sw.getBuffer().toString()));
            ConfigFile cf = new ConfigFile();
            cp.process(cf);
            */
            
            ByteArrayInputStream baris = new ByteArrayInputStream(sw.getBuffer().toString().getBytes());
            ConfigFile cf = new ConfigFile(baris);
            
            Config dmConfig = new Config(cf.getConfig().getSection("DatabaseManager"));
            DatabaseManager dbMgr = new StandardDatabaseManager(dmConfig);
            DODS.registerDefault(dbMgr);
         } catch (Exception e) {
            throw new RootException("DODS init problem.", e);
         }
      }
   }
}
/* End of DODSUtilities.java */

