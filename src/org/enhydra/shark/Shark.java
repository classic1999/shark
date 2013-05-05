package org.enhydra.shark;

import org.enhydra.shark.api.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfservice.AdminInterface;
import org.enhydra.shark.api.client.wfservice.ExpressionBuilderManager;
import org.enhydra.shark.api.client.wfservice.RepositoryMgr;
import org.enhydra.shark.api.client.wfservice.SharkConnection;
import org.enhydra.shark.api.client.wfservice.SharkInterface;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.instancepersistence.PersistentManagerInterface;
import org.enhydra.shark.api.internal.instancepersistence.ProcessPersistenceInterface;
import org.enhydra.shark.api.internal.instancepersistence.ResourcePersistenceInterface;
import org.enhydra.shark.api.internal.limitagent.LimitAgentManager;
import org.enhydra.shark.api.internal.working.WfActivityInternal;
import org.enhydra.shark.api.internal.working.WfProcessInternal;
import org.enhydra.shark.utilities.MiscUtilities;

/**
 * The main engine class. The client application first has to call one of
 * the static methods configure() (which will initialize shark), and then
 * static getInstance() method of this class to get one and only one instance
 * of this class. After that, clients can call other methods for getting
 * appropriate interfaces.
 *
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public final class Shark implements SharkInterface {

   private static final String initCachesBoundary=",";
   private static final String initAllCachesString="*";

   private static final String defaultLogChannel="Shark";

   private static boolean isConfigured=false;
   // the one and only instance of this class
   private static Shark shark;

   static {
      shark=new Shark();
   }

   //////////////////////////////////////////////////////////////////
   //  Configuration methods
   //////////////////////////////////////////////////////////////////
   public static void configure (Properties props) {
      if (isConfigured) {
         SharkEngineManager.getInstance().getCallbackUtilities().info(
            "Trying to configure shark instance that is already configured !!!");
         return;
      }
      if (props==null) {
         throw new RootError("Shark need to be configured properly - given Poperties have null value!!!");
      }
      configureFromJar();
      adjustSharkProperties(props);
      isConfigured=true;
      shark.init();
   }

   public static void configure (String filePath) {
      if (isConfigured) {
         SharkEngineManager.getInstance().getCallbackUtilities().info(
            "Trying to configure shark instance that is already configured !!!");
         return;
      }
      if (filePath==null) {
         throw new RootError("Shark need to be configured properly - given path to configuration file is null!!!");
      }
      File config=new File(filePath);
      Shark.configure(config);
   }

   public static void configure (File configFile) {
      if (isConfigured) {
         SharkEngineManager.getInstance().getCallbackUtilities().info(
            "Trying to configure shark instance that is already configured !!!");
         return;
      }
      if (configFile==null) {
         throw new RootError("Shark need to be configured properly - given configuration file is null!!!");
      }
      if (!configFile.isAbsolute()) {
         configFile=configFile.getAbsoluteFile();
      }
      if (configFile.exists()) {
         configureFromJar();
         FileInputStream fis=null;
         try {
            fis=new FileInputStream(configFile);
            Properties props=new Properties();
            props.load(fis);
            fis.close();
            adjustSharkProperties(props);
         } catch (Exception ex) {
            throw new RootError ("Something went wrong while reading of configuration from the file!!!",ex);
         }
         try {
            shark.properties.put(SharkConstants.ROOT_DIRECTORY_PATH_PROP,
                                 configFile.getParentFile().getCanonicalPath());
          } catch (Exception ex) {
            shark.properties.put(SharkConstants.ROOT_DIRECTORY_PATH_PROP,
                                 configFile.getParentFile().getAbsolutePath());
         }
      } else {
         throw new RootError("Shark need to be configured properly - configuration file "+configFile+" does not exist!!!");
      }
      isConfigured=true;
      shark.init();
   }

   public static void configure () {
      if (isConfigured) {
         SharkEngineManager.getInstance().getCallbackUtilities().info(
            "Trying to configure shark instance that is already configured !!!");
         return;
      }
      configureFromJar();
      isConfigured=true;
      shark.init();
   }

   public static Shark getInstance () {
      if (!isConfigured) {
         throw new RootError("Can't use Shark - it is not configured !!!");
      }
      return shark;
   }

   private Properties properties;
   private ExpressionBuilderManager expressionBuilderManager;

   private Shark () {
   }

   //////////////////////////////////////////////////////////////////
   //  SharkInterface API implementation
   //////////////////////////////////////////////////////////////////
   public AdminInterface getAdminInterface () {
      return SharkEngineManager.getInstance().getObjectFactory().createAdminInterface();
   }

   public RepositoryMgr getRepositoryManager () {
      return SharkEngineManager.getInstance().getObjectFactory().createRepositoryManager();
   }

   public SharkConnection getSharkConnection () {
      return SharkEngineManager.getInstance().getObjectFactory().createSharkConnection();
   }

   public ExpressionBuilderManager getExpressionBuilderManager () {
      if (expressionBuilderManager==null) {
         String ebmClassName = properties.getProperty
         ("ExpressionBuilderManagerClassName",
          "org.enhydra.shark.ExpressionBuilderMgr");
   
         ClassLoader cl=getClass().getClassLoader();
   
         try {
            expressionBuilderManager=(ExpressionBuilderManager)cl.loadClass(ebmClassName).newInstance();
            SharkEngineManager.getInstance().getCallbackUtilities().info("Shark -> Working with '"+ebmClassName+"' implementation of ExpressionBuilderManager API");
         } catch (Throwable ex) {         
            if (ebmClassName==null || ebmClassName.trim().equals("")) {
               SharkEngineManager.getInstance().getCallbackUtilities().info("Shark -> Working without ExpressionBuilderManager implementation.");
            } else if (expressionBuilderManager==null) {
               SharkEngineManager.getInstance().getCallbackUtilities().info("Shark -> Can't find ExpressionBuilderManager class '"+ebmClassName+"' in classpath!");
            }
         }
      }

      return expressionBuilderManager;      
   }
   
   public SharkTransaction createTransaction() throws TransactionException {
      try {
         return SharkUtilities.createTransaction();
      } catch (Exception ex) {
         throw new TransactionException("Can't create SharkTransaction",ex);
      }
   }

   public ParticipantMappingTransaction createParticipantMappingTransaction() throws TransactionException {
      try {
         return SharkEngineManager.getInstance().getParticipantMapPersistenceManager().getParticipantMappingTransaction();
      } catch (Exception ex) {
         throw new TransactionException("Can't create ParticipantMappingTransaction",ex);
      }
   }

   public ApplicationMappingTransaction createApplicationMappingTransaction() throws TransactionException {
      try {
         return SharkEngineManager.getInstance().getApplicationMapPersistenceManager().getApplicationMappingTransaction();
      } catch (Exception ex) {
         throw new TransactionException("Can't create ApplicationMappingTransaction",ex);
      }
   }

   public ScriptMappingTransaction createScriptMappingTransaction() throws TransactionException {
      try {
         return SharkEngineManager.getInstance().getScriptMapPersistenceManager().getScriptMappingTransaction();
      } catch (Exception ex) {
         throw new TransactionException("Can't create ScriptMappingTransaction",ex);
      }
   }

   public UserTransaction createUserTransaction() throws TransactionException {
      try {
         return SharkEngineManager.getInstance().getUserTransactionFactory().createTransaction();
      } catch (Exception ex) {
         throw new TransactionException("Can't create UserTransaction",ex);
      }
   }

   public RepositoryTransaction createRepositoryTransaction() throws TransactionException {
      try {
         return SharkEngineManager.getInstance().getRepositoryPersistenceManager().createTransaction();
      } catch (Exception ex) {
         throw new TransactionException("Can't create UserTransaction",ex);
      }
   }

   public void unlockProcesses (SharkTransaction t) throws TransactionException {
      SharkUtilities.unlock(t);
   }

   public void emptyCaches (SharkTransaction t) {
      SharkUtilities.emptyCaches(t);
   }

   public Properties getProperties () {
      Properties copy=new Properties();
      Iterator it=properties.entrySet().iterator();
      while (it.hasNext()) {
         Map.Entry me=(Map.Entry)it.next();
         copy.setProperty((String)me.getKey(),(String)me.getValue());
      }
      return copy;
   }

   //////////////////////////////////////////////////////////////////
   //  Utility methods
   //////////////////////////////////////////////////////////////////
   public boolean validateUser (String username,String pwd) {
      try {
         return SharkUtilities.validateUser(username,pwd);
      } catch (Exception ex) {
         return false;
      }
   }

   //  Initialization methods
   //////////////////////////////////////////////////////////////////

   private static void configureFromJar () {
      String rootDirectoryPath=System.getProperty("user.dir");
      try {
         //URL u=Shark.class.getClassLoader().getResource("Shark.conf.forJar");
         //InputStream is=(InputStream)u.getContent();
         URL u=Shark.class.getClassLoader().getResource("Shark.conf.forJar");
         URLConnection urlConnection = u.openConnection();
         InputStream is=urlConnection.getInputStream();

         shark.properties=new Properties();
         shark.properties.load(is);
         shark.properties.put(SharkConstants.ROOT_DIRECTORY_PATH_PROP,rootDirectoryPath);
      } catch (Exception ex) {
         throw new RootError("Shark need to be configured properly - Can't read Shark's default configuration from JAR!!!",ex);
      }
   }

   private static void adjustSharkProperties (Properties external) {
      Iterator it=external.entrySet().iterator();
      while (it.hasNext()) {
         Map.Entry me=(Map.Entry)it.next();
         String key=(String)me.getKey();
         String val=(String)me.getValue();
         shark.properties.setProperty(key,val);
      }
   }

   private void init () {
      long tStart=System.currentTimeMillis();
      System.out.println("SharkEngineManager -> Shark engine is being initialized ...");

      // this is where the singlton class of SharkEngineManager is created
      SharkEngineManager.getInstance().init(properties);

      shark.initCaches();
      shark.reevaluateAssignments();
      shark.initLimitManager();

      /*System.setProperty("user.dir",shark
                            .getProperties()
       .getProperty(SharkConstants.ROOT_DIRECTORY_PATH_PROP));*/
      long tEnd=System.currentTimeMillis();
      System.out.println("Shark -> shark engine initialization is finished, it lasted "+MiscUtilities.getTimeDiff(tStart,tEnd));
      System.out.println("Shark -> "+properties.getProperty("enginename","Shark")+" ready and waiting ...");
   }

   // init caches
   private void initCaches () {
      String initPCS=properties.getProperty("Cache.InitProcessCacheString","");
      String initRCS=properties.getProperty("Cache.InitResourceCacheString","");
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         if (!initPCS.trim().equals("")) {
            SharkEngineManager.getInstance().getCallbackUtilities().info(
               "Initializing Process cache using string "+initPCS);
            if (initPCS.trim().equalsIgnoreCase(initAllCachesString)) {
               List l=SharkEngineManager.
                  getInstance().
                  getInstancePersistenceManager().
                  getAllProcesses(t);
               Iterator it=l.iterator();
               while (it.hasNext()) {
                  ProcessPersistenceInterface po=(ProcessPersistenceInterface)it.next();
                  // this creates and puts process into cache
                  SharkEngineManager.getInstance().getObjectFactory().createProcess(po);
               }
            } else {
               String[] procIds=MiscUtilities.tokenize(initPCS.trim(),initCachesBoundary);
               if (procIds!=null) {
                  for (int i=0; i<procIds.length; i++) {
                     // this gets process from db and puts it into cache
                     Object proc=SharkUtilities.getProcess(t,procIds[i]);
                     if (proc==null) {
                        SharkEngineManager.getInstance().getCallbackUtilities().info(
                           "Process cache initialization - process with Id="+procIds[i]+" does not exist");
                     }
                  }
               }
            }
         }

         if (!initRCS.trim().equals("")) {
            SharkEngineManager.getInstance().getCallbackUtilities().info(
               "Initializing Resource cache using string "+initRCS);
            if (initRCS.equalsIgnoreCase(initAllCachesString)) {
               List l=SharkEngineManager.
                  getInstance().
                  getInstancePersistenceManager().
                  getAllResources(t);
               Iterator it=l.iterator();
               while (it.hasNext()) {
                  // this creates and puts resources into cache
                  ResourcePersistenceInterface po=(ResourcePersistenceInterface)it.next();
                  SharkEngineManager.getInstance().getObjectFactory().createResource(po);
               }
            } else {
               String[] resIds=MiscUtilities.tokenize(initRCS.trim(),initCachesBoundary);
               if (resIds!=null) {
                  for (int i=0; i<resIds.length; i++) {
                     // this gets resource from db and puts it into cache
                     Object res=SharkUtilities.getResource(t,resIds[i]);
                     if (res==null) {
                        SharkEngineManager.getInstance().getCallbackUtilities().info(
                           "Resource cache initialization - resource with username="+resIds[i]+" does not exist");
                     }
                  }
               }
            }
         }
         SharkUtilities.restorePackages();
         //SharkUtilities.commitTransaction(t);
      } catch (Throwable e) {
         SharkEngineManager.getInstance().getCallbackUtilities().error(
            "Problem while initializing caches !!!");
         e.printStackTrace();
         //try {SharkUtilities.rollbackTransaction(t);} catch (Exception ex) {}
      } finally {
         try {
            SharkUtilities.releaseTransaction(t);
         } catch (BaseException e) {}
      }
   }

   // reevaluate assignments
   private void reevaluateAssignments () {
      boolean reeval=new Boolean(properties.getProperty("Assignments.InitialReevaluation","false")).booleanValue();
      if (!reeval) return;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         SharkUtilities.reevaluateAssignments(t);
         SharkUtilities.commitTransaction(t);
      } catch (Throwable e) {
         SharkEngineManager
            .getInstance()
            .getCallbackUtilities()
            .error("Problem while reevaluating assignments !!!");
         try {
            SharkUtilities.rollbackTransaction(t,new RootException(e));
         } catch (Exception ex) {}
      } finally {
         try {
            SharkUtilities.releaseTransaction(t);
         } catch (BaseException e) {}
      }
   }

   private void initLimitManager() {
      SharkTransaction t = null;
      LimitAgentManager lam = SharkEngineManager
         .getInstance()
         .getLimitAgentManager();
      PersistentManagerInterface pmi = SharkEngineManager
         .getInstance()
         .getInstancePersistenceManager();

      if (null != lam) {
         try {
            t = SharkUtilities.createTransaction();
            for (Iterator processes = pmi.getAllProcesses(t).iterator();
                 processes.hasNext();) {
               ProcessPersistenceInterface po =
                  ((ProcessPersistenceInterface)processes.next());

               if (!po.getState().startsWith(SharkConstants.STATEPREFIX_CLOSED)) {
                  WfProcessInternal theProcess = SharkUtilities.getProcess(t,po.getId());
                  theProcess.activateLimitAgent(t);
                  for (Iterator activeActivities = theProcess.getActiveActivities(t).iterator();
                       activeActivities.hasNext();){
                     ((WfActivityInternal)activeActivities.next()).activateLimitAgent(t);
                  }
               }
            }
         } catch (Throwable e) {
            e.printStackTrace();
            SharkEngineManager
               .getInstance()
               .getCallbackUtilities()
               .error("Limit agent manager initialization didn't work");
         } finally {
            try { SharkUtilities.releaseTransaction(t);}
            catch (BaseException e) {}
         }
      }
   }

}

