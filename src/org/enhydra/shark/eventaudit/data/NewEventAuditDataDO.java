
/*-----------------------------------------------------------------------------
 * Enhydra Java Application Server
 * Copyright 1997-2000 Lutris Technologies, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    This product includes Enhydra software developed by Lutris
 *    Technologies, Inc. and its contributors.
 * 4. Neither the name of Lutris Technologies nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY LUTRIS TECHNOLOGIES AND CONTRIBUTORS ``AS IS''
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL LUTRIS TECHNOLOGIES OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * contains WebDocWf extensions
 *
 *-----------------------------------------------------------------------------
 * org.enhydra.shark.eventaudit.data/NewEventAuditDataDO.java
 *-----------------------------------------------------------------------------
 */


package org.enhydra.shark.eventaudit.data;

import java.io.*;
import java.sql.*;
import java.math.*;
import java.util.Hashtable;
import java.util.Collection;
import java.util.Properties;
import java.io.FileInputStream;
import java.util.Vector;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.lang.reflect.Method;

import com.lutris.logging.LogChannel;
import com.lutris.logging.Logger;
import org.enhydra.dods.DODS;
import com.lutris.util.Config;
import com.lutris.util.ConfigException;
import com.lutris.appserver.server.sql.*;
import com.lutris.appserver.server.sql.standard.*;
import com.lutris.dods.builder.generator.dataobject.*;
import com.lutris.dods.builder.generator.query.*;
import org.enhydra.dods.cache.Condition;
import org.enhydra.dods.cache.DataStructCache;
import org.enhydra.dods.cache.QueryCache;
import org.enhydra.dods.cache.QueryCacheImpl;
import org.enhydra.dods.cache.QueryResult;
import org.enhydra.dods.cache.ConfigurationAdministration;
import org.enhydra.dods.statistics.Statistics;
import com.lutris.classloader.MultiClassLoader;
import org.enhydra.xml.XMLConfig;
import org.enhydra.dods.Common;
import org.enhydra.dods.cache.CacheConstants;
import org.enhydra.dods.exceptions.AssertionDataObjectException;
import org.enhydra.dods.exceptions.CacheObjectException;



// WebDocWf extension for DODS row instance security and genric store
import org.webdocwf.dods.access.*;
// end of WebDocWf extension for DODS row instance security and generic store

/**
 * Data core class, used to set and retrieve the NewEventAuditDataDO information.
 *
 * @version $Revision: 1.16 $
 * @author  NN
 * @since   DODS Project
 */
 public class NewEventAuditDataDO extends com.lutris.dods.builder.generator.dataobject.GenericDO implements NewEventAuditDataDOI, java.io.Serializable {
    /**
     * Static final data members name the table and columns for this DO.
     * By using these members with an instance of the QueryBuilder class,
     * an application can perform straight SQL queries while retaining
     * compile-time checking of table and column usage.
     *
     * Example:  List the Cities containing Persons named Bob:
     *
     *    Using straight SQL with QueryBuilder:
     *      Pro: code runs faster because you create fewer objects
     *      Con: code is less clear
     *
     *         Vector fields = new Vector();
     *         fields.addElement( AddressDO.City );
     *         QueryBuilder qb = new QueryBuilder( fields );
     *         qb.addWhere( PersonDO.FirstName, "Bob" );
     *         qb.addWhere( PersonDO.PrimaryKey, AddressDO.Person );
     *         RDBRow row;
     *         while ( null != ( row = qb.getNextRow() ) ) {
     *             String city = row.get( AddressDO.City ).getString();
     *         }
     *
     *    Using Query/DO classes:
     *      Pro: code is (often) clearer
     *      Con: code runs slower because you create more objects
     *
     *         PersonQuery pq = new PersonQuery();
     *         pq.setQueryFirstName( "Bob" );
     *         PersonDO[] bobs = pq.getDOArray();
     *         for ( int i = 0; i < bobs.length; i++ ) {
     *             AddressQuery aq = new AddressQuery();
     *             aq.setQueryPerson( bobs[i] );
     *             AddressDO addr = aq.getNextDO();
     *             String city = addr.getCity();
     *         }
     */
    static public final RDBTable  table = new RDBTable( "NewEventAuditData" );

    /**
     * List of reference objects
     */
    private HashMap refs = null;


    /**
    * null : use old style init query
    * true : use columns names with table prefix
    * false: use columns names withouth table prefix
    */
    private static Boolean useOrderedWithTable = null ;


    /**
    * true : Use binary stream for longvarchar
    * false: Don't use binary stream for longvarchar
    */
    private static boolean useBinaryStreamForLongvarchar ;


    /**
    * String with column names
    */
	static String columnsNameString = null;

    /**
    * Is DO first node in DELETE CASCADE sekvence
    */
    private boolean isRootDeleteNode = true;


    /**
     * Name of the logical database for which DO object was created
     */
    protected String originDatabase = null;


    /**
     * Return the name of the logical database for which DO object was created.
     *
     * @deprecated Use get_OriginDatabase()
     * @return origin logical database name.
     *
     */
    public String getOriginDatabase() {
        return get_OriginDatabase();
    }

    /**
     * Return the name of the logical database for which DO object was created.
     *
     * @return origin logical database name.
     *
     */
    public String get_OriginDatabase() {
        return get_DataStruct().get_Database();
    }

    /**
     * Return NewEventAuditData as the name of the table in the database
     * which contains NewEventAuditDataDO objects.
     * This method overrides CoreDO.getTableName()
     * and is used by CoreDO.executeUpdate() during error handling.
     *
     * @return The database table name.
     *
     * @see com.lutris.appserver.server.sql.CoreDO CoreDO
     * author Jay Gunter
     */
    public String getTableName() {
        return "NewEventAuditData";
    }

    /**
     * static final RDBColumn PrimaryKey for use with QueryBuilder.
     * See example above.
     */
    static public final RDBColumn PrimaryKey = new RDBColumn( table, get_primaryKeyName() );

    /* RDBColumns for NewEventAuditDataDO attributes are defined below. */


    /* Using a DO (and its Query class) to access a VIEW instead of a TABLE:
     *
     * A DO (and its Query class) can be used to access a VIEW
     * instead of a TABLE.  The Data Object is created as usual in DODS,
     * but the "create table" SQL command for that DO is not used.
     * Instead, you substitute a "create view" command to create a
     * virtual table in the database; this is often done to provide
     * convenient access to a collection of tables joined together.
     *
     * A VIEW usually does not return "oid" and "version" columns;
     * often (but now always) a VIEW is defined to return the "oid" column
     * for one of the tables joined together in the definition of the VIEW.
     *
     * If notUsingOId is true, NewEventAuditDataDO.createExisting(ResultSet)
     * will NOT invoke the GenericDO(ResultSet) constructor
     * so to avoid attempting to extract the "oid" and "version" columns
     * from the ResultSet.
     */
    static protected final boolean notUsingOId = false;


    /**
     * A DO class contains a reference to a DataStruct class.
     * This reference can be null (when the data for the DO
     * has not yet been retrieved from the database),
     * allowing a DO object to be a lightweight placeholder
     * until its data is needed.
     */
    private NewEventAuditDataDataStruct data = null;

    /**
     * A DO class contains a reference to a DBTransaction class.
     * This reference can be null (when the DO is created without
     * transaction).
     */
    private DBTransaction transaction = null;

    /**
     * Return transaction which DO belongs.
     *
     * @return DBTransaction or null if not specified.
     *
     */
    public DBTransaction get_transaction() {
        return transaction;
    }

    /**
     * Set Transaction to current DO.
     *
     * @param trans The transaction.
     * @return true if the operation was successfully performed, otherwise false.
     */
    protected boolean setTransaction(DBTransaction trans) {
        boolean isOK=false;
        if (get_transaction() == null) {
            transaction = trans;
            isOK=true;
        } else {
        if(get_transaction().equals(trans))
            isOK=true;
        }
        return isOK;
    }


    /**
     * Return information whether the data for this object has been marked read-only.
     *
     * @return True if the data for this object has been marked read-only.
     *
     */
    public boolean isReadOnly() {
      return getConfigurationAdministration().getTableConfiguration().isReadOnly();
     }

    /**
     * Sets DO's data.
     * @param data Data object.
     * @deprecated Use set_Data()
     */
     public void setData (Object data) {
        set_Data(data);
     }

    /**
     * Sets DO's data.
     * @param data Data object.
     */
     public void set_Data (Object data) {
        this.data = (NewEventAuditDataDataStruct)data;
     }

    /**
     * Sets original DO's data.
     * @param data Data object.
     */
     public void originalData_set (Object data) {
        originalData = (NewEventAuditDataDataStruct)data;
     }

    /**
     * Returns DO's data.
     * @return DO's data.
     * @deprecated Use get_Data()
     */
     public Object getData () {
        return get_Data();
     }

    /**
     * Returns DO's data.
     * @return DO's data.
     */
     public Object get_Data () {
        return (null != data)? data : originalData;
     }

    /**
     * Returns dataStruct.
     * @return Data Struct object.
     * @deprecated Use get_DataStruct()
     */
    public NewEventAuditDataDataStruct getDataStruct () {
        return get_DataStruct();
    }

    /**
     * Returns dataStruct.
     * @return Data Struct object.
     */
    public NewEventAuditDataDataStruct get_DataStruct () {
        return (NewEventAuditDataDataStruct) get_Data();
    }

    /**
     * Returns original DO's data.
     * @return Original DO's data.
     */
     public Object originalData_get () {
        return originalData;
     }

     public void checkDup () throws DatabaseManagerException, com.lutris.appserver.server.sql.ObjectIdException {
         if (isDeletedFromDatabase)
             throw new DatabaseManagerException("Object "+get_Handle()+" is deleted");
         if (null == data) {
             data = ((NewEventAuditDataDataStruct)originalData).duplicate();
         data.readOnly = false;
         }
     }

    /**
     * Protected constructor. Only derived classes should call it.
     *
     * @param is_view Is this view or not.
     *
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     */
    protected NewEventAuditDataDO ( boolean is_view )
    throws ObjectIdException, DatabaseManagerException {
        super( is_view );
        if(isTransactionCheck()) {
            DODS.getLogChannel().write(Logger.WARNING, "DO without transaction context is created :"+(is_view?"":" Database: "+get_OriginDatabase()+" NewEventAuditDataDO class, oid: "+get_Handle()+", version: "+get_Version())+" \n");
            (new Throwable()).printStackTrace(DODS.getLogChannel().getLogWriter(Logger.WARNING));

        }
    }
    /**
     * Protected constructor. Only derived classes should call it.
     *
     * @param is_view Is this view or not.
     * @param dbTrans Database transaction.
     *
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     */
    protected NewEventAuditDataDO ( boolean is_view, DBTransaction dbTrans )
    throws ObjectIdException, DatabaseManagerException {
        super( is_view );
        setTransaction(dbTrans);
        if(dbTrans!=null) {
           originDatabase = dbTrans.getDatabaseName();
        }
        if(originDatabase==null)
           originDatabase = get_logicalDBName();
        get_DataStruct().set_Database(originDatabase);
        addToTransactionCache();

    }


    /**
     * Protected constructor.  Only derived classes should call it.
     *
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     */
    protected NewEventAuditDataDO ()
    throws ObjectIdException, DatabaseManagerException {
        super( notUsingOId );
        originDatabase = get_logicalDBName();
        get_DataStruct().set_Database(originDatabase);
        if(isTransactionCheck()) {
            DODS.getLogChannel().write(Logger.WARNING, "DO without transaction context is created : Database: "+get_OriginDatabase()+" NewEventAuditDataDO class, oid: "+get_Handle()+", version: "+get_Version()+" \n");
            (new Throwable()).printStackTrace(DODS.getLogChannel().getLogWriter(Logger.WARNING));

        }
        if (autoSaveAllowed&&isAutoSaveCreateVirgin()&&null != transaction) {
            try {
                save(transaction,false);
            } catch (Exception ex) {
                        DODS.getLogChannel().write(Logger.DEBUG,"Faild to AutoSave virgin DO: "+get_OriginDatabase()+" NewEventAuditDataDO class\n");
            }
        }
    }

    /**
     * Protected constructor.  Only derived classes should call it.
     * @param dbTrans The current database transaction.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     */
    protected NewEventAuditDataDO (DBTransaction dbTrans)
    throws ObjectIdException, DatabaseManagerException {
        super( notUsingOId );
        setTransaction(dbTrans);
        if(dbTrans!=null) {
           originDatabase = dbTrans.getDatabaseName();
        }
        if(originDatabase==null)
           originDatabase = get_logicalDBName();
        get_DataStruct().set_Database(originDatabase);

        addToTransactionCache();
        if (autoSaveAllowed&&isAutoSaveCreateVirgin()&&null != transaction) {
            try {
                save(transaction,false);
            } catch (Exception ex) {
                        DODS.getLogChannel().write(Logger.DEBUG,"Faild to AutoSave virgin DO: "+get_OriginDatabase()+" NewEventAuditDataDO class\n");
            }
        }
    }



    /**
     * isLoaded()
     * Returns information whether object's data is loaded from database.
     * @return true if the data for this object has been retrieved
     * from the database.
     */
    public boolean isLoaded() {
        return (null != originalData)&&(!get_DataStruct().isEmpty);
    }


    /**
     * Load the fields for the DO from the database.
     *
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    public void loadData()
    throws SQLException, ObjectIdException, DataObjectException {
        if (null == originalData&&!get_DataStruct().isEmpty) {
            originalData = new NewEventAuditDataDataStruct ();
        }

        ObjectId id = get_OId();
        if ( null == id )
            return;
        if ( ! isPersistent() )   // DO from createVirgin
            return;
        // DO from createExisting.  Complain if no record in database.
        NewEventAuditDataQuery query;

               query = new NewEventAuditDataQuery (get_transaction());

//        if(get_refs()!=null)
//        {
        query.setRefs(get_refs());
//        }
        query.setQueryOId( id );
        query.requireUniqueInstance();
        NewEventAuditDataDO obj;
        try {
           query.setLoadData(true);
           obj = query.getNextDO();
            if ( null == obj )
                throw new DataObjectException("NewEventAuditDataDO DO not found for id=" + id );
            makeIdentical(obj);
            set_Version(    obj.get_Version() );
            get_DataStruct().isEmpty = false;
        } catch ( NonUniqueQueryException e ) {
            throw new ObjectIdException( "Duplicate ObjectId" );
        }

    }

    /**
     * Load the actual DO data if necessary.
     * Called by get/set methods.
     *
     * @exception DataObjectException If a data access error occurs.
     */
    protected void checkLoad()
    throws DataObjectException {
        if (null == originalData||get_DataStruct().isEmpty) {
            try {
                loadData();
            } catch ( Exception e ) {
                throw new DataObjectException("Unable to load data for NewEventAuditDataDO id=" + get_OId() +
                                              ", error = ", e);
            }
    }
    }


    /**
     * Protected constructor used by createExisting(ObjectId) above.
     *
     * @param id The ObjectId for the object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   Should never see this exception since GenericDO.ctor(ObjectId)
     *   never accesses the database.
     */
    protected NewEventAuditDataDO( ObjectId id )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        super( id );
        originDatabase = get_logicalDBName();
        get_DataStruct().set_Database(originDatabase);
        if(isTransactionCheck()) {
            DODS.getLogChannel().write(Logger.WARNING, "DO without transaction context is created : Database: "+get_OriginDatabase()+" NewEventAuditDataDO class, oid: "+get_Handle()+", version: "+get_Version()+" \n");
           (new Throwable()).printStackTrace(DODS.getLogChannel().getLogWriter(Logger.WARNING));

        }

    }

    /**
     * Protected constructor used by createExisting(ObjectId, DBTransaction) above.
     *
     * @param id The ObjectId for the object.
     * @param dbTrans The current database transaction
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   Should never see this exception since GenericDO.ctor(ObjectId)
     *   never accesses the database.
     */
    protected NewEventAuditDataDO( ObjectId id , DBTransaction dbTrans)
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        super( id );
        setTransaction(dbTrans);
        if(dbTrans!=null) {
           originDatabase = dbTrans.getDatabaseName();
        }
        if(originDatabase==null)
           originDatabase = get_logicalDBName();
        get_DataStruct().set_Database(originDatabase);
        addToTransactionCache();
    }




    /**
     * Represents table and cache (if there is caching) statistics.
     */
    protected static Statistics statistics;

    /**
     * Get table statistics.
     *
     * @return Table statistics.
     */
    public static Statistics get_statistics() {
        statistics = cache.getStatistics();
        return statistics;
    }

    /**
     * Refresh table statistics.
     */
    public static void refreshStatistics() {
       cache.refreshStatistics();
    }


    /**
     * Get all used logical databases.
     *
     * @deprecated Use get_UsedLogicalDatabases()
     * @return Array that contains names of all used logical databases.
     *
     */
    public static String[] getUsedLogicalDatabases() {
        return get_UsedLogicalDatabases();
    }

    /**
     * Get all used logical databases.
     *
     * @return Array that contains names of all used logical databases.
     *
     */
    public static String[] get_UsedLogicalDatabases() {
        String[] str = { get_logicalDBName() };
        return str;
    }


    protected static DataStructCache cache; // cache for NewEventAuditDataDO


    protected static boolean isFullCacheNeeded = false; // it depends of CacheFullCacheCountLimit parameter and number of data in database.



    /**
     * Read cache configuration from application configuration file:
     * cache size for org.enhydra.shark.eventaudit.data.NewEventAuditData table or default cache size.
     * @param database DO's database.
     * @exception CacheObjectException
     */
    public static void readCacheConfiguration(String database) throws CacheObjectException {
        if (getConfigurationAdministration().isDisabled()) {
            throw new CacheObjectException("Caching is disabled");
        }
        Config tableConfig = null;
        Config cacheConfig = null;
        try {
            tableConfig = (Config)DODS.getDatabaseManager().getConfig().getSection("DB."+database+".NewEventAuditData");
        } catch (Exception ex) {
                    DODS.getLogChannel().write(Logger.DEBUG," NewEventAuditDataDO class\n :"+" Using default configuration for 'NewEventAuditData' table");
        }
        try {
            cacheConfig = (Config)DODS.getDatabaseManager().getConfig().getSection("DB."+database+".NewEventAuditData.cache");
        } catch ( Exception e ) {
            DODS.getLogChannel().write(Logger.DEBUG," NewEventAuditDataDO class\n :"+" Using default cache configuration for 'NewEventAuditData' table");
        }
        
        cache.readConfiguration(tableConfig,cacheConfig, database);
    }


    /**
     * Get name of the table that is cached.
     *
     * @return Name of the table that is cached.
     */
    public static String getCacheDodsTableName() {
        return "NewEventAuditData";
    }


    /**
     * Returns NewEventAuditData table cache.
     *
     * @return NewEventAuditData table cache.
     */
    public static ConfigurationAdministration getConfigurationAdministration() {
        return cache;
    }

    /**
     * Queries all rows in table, and for each row
     * creates a DO instance in the cache.
     * For these DOs, data.readOnly = true,
     * which causes set methods to throw an exception.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    public static void refreshCache()
    throws java.sql.SQLException, DatabaseManagerException, ObjectIdException, DataObjectException {
       getConfigurationAdministration().getCacheAdministration(CacheConstants.DATA_CACHE).refresh();
       String querySnt = cache.getInitialQueryCache();
       int maxSize = cache.getCacheAdministration(CacheConstants.DATA_CACHE).getMaxCacheSize();
			 if (maxSize != 0) {
	        cache.checkFull();
           if(cache.isFull()) {
           if(cache.getTableConfiguration().getFullCacheCountLimit() == 0) {
               isFullCacheNeeded=false;
           } else if(cache.getTableConfiguration().getFullCacheCountLimit() > 0) {
            int numOfRows = 0;
  	            NewEventAuditDataQuery query;

	                DBTransaction tmpTransaction = DODS.getDatabaseManager().createTransaction(get_logicalDBName());
	                query = new NewEventAuditDataQuery (tmpTransaction);
	                try {
	                    numOfRows = query.getCount();
	                    tmpTransaction.release();
	                } catch ( NonUniqueQueryException ex ) {
	                    // Since we do not call query.requireUniqueInstance()
	                    // this should never happen.
	                    throw new ObjectIdException( "Duplicate ObjectId" );
	                }
	

               if (numOfRows < cache.getTableConfiguration().getFullCacheCountLimit())
                  isFullCacheNeeded = true;
               else
                  isFullCacheNeeded = false;

           } else
               isFullCacheNeeded=true;
	        }
	        if (querySnt != null) {
	            NewEventAuditDataQuery query;

	                DBTransaction tmpTransaction = DODS.getDatabaseManager().createTransaction(get_logicalDBName());
	                query = new NewEventAuditDataQuery (tmpTransaction);
	                query.hitDatabase();
// tj 02.04.2004	                maxSize = cache.getCacheAdministration(CacheConstants.DATA_CACHE).getMaxCacheSize();
					int initMaxSize = 0;
					if(cache.getInitialDSCacheSize()> 0){
						initMaxSize=cache.getInitialDSCacheSize();
					}
					if((initMaxSize > 0) && ((initMaxSize < maxSize)||(maxSize < 0))) {
					   isFullCacheNeeded = false;
                  maxSize=initMaxSize;
               }
	                if (maxSize > 0)
	                    try {
	                        query.setMaxRows(maxSize);
	                    }
	                    catch (NonUniqueQueryException nuEx){
	                        System.err.println ("NonUniqueQueryException in refreshCache() method : too many rows were      found.");
	                    }
	                if (!querySnt.equals("*")) {
	                    QueryBuilder builder = query.getQueryBuilder();
	                    builder.addWhere(querySnt);
	                }
	                NewEventAuditDataDO obj;
	                try {
	                	if (cache.getInitialCacheFetchSize()>0){
	                		query.set_FetchSize(cache.getInitialCacheFetchSize());
	                	}
	                	query.set_CursorType(cache.getTableConfiguration().getInitCachesResultSetType(),cache.getTableConfiguration().getInitCachesResultSetConcurrency());
	                    query.setLoadData(true);
	                    query.getNextDO();
	                    tmpTransaction.release();
	                } catch ( NonUniqueQueryException ex ) {
	                    // Since we do not call query.requireUniqueInstance()
	                    // this should never happen.
	                    throw new ObjectIdException( "Duplicate ObjectId" );
	                }
	
	        }
	     }
	     cache.refreshStatistics();
    }

    private static boolean isDisabledCaching = false;

    /**
     * Disable caching.
     * @exception java.sql.SQLException
     * @exception DatabaseManagerException
     * @exception ObjectIdException
     * @exception DataObjectException
     */
    public static void disableCaching()
    throws java.sql.SQLException, DatabaseManagerException, ObjectIdException, DataObjectException {
        isDisabledCaching = true;
        getConfigurationAdministration().getCacheAdministration(CacheConstants.DATA_CACHE).disable();
    }

    /**
     * Enable caching.
     * @exception java.sql.SQLException
     * @exception DatabaseManagerException
     * @exception ObjectIdException
     * @exception DataObjectException
     */
    public static void enableCaching()
    throws java.sql.SQLException, DatabaseManagerException, ObjectIdException, DataObjectException {
        if (isDisabledCaching){
            getConfigurationAdministration().getCacheAdministration(CacheConstants.DATA_CACHE).enable();
            int maxSize = cache.getCacheAdministration(CacheConstants.DATA_CACHE).getMaxCacheSize();
            if (maxSize != 0){
           if(cache.isFull()) {
           if(cache.getTableConfiguration().getFullCacheCountLimit() == 0) {
               isFullCacheNeeded=false;
           } else if(cache.getTableConfiguration().getFullCacheCountLimit() > 0) {
            int numOfRows = 0;
  	            NewEventAuditDataQuery query;

	                DBTransaction tmpTransaction = DODS.getDatabaseManager().createTransaction(get_logicalDBName());
	                query = new NewEventAuditDataQuery (tmpTransaction);
	                try {
	                    numOfRows = query.getCount();
	                    tmpTransaction.release();
	                } catch ( NonUniqueQueryException ex ) {
	                    // Since we do not call query.requireUniqueInstance()
	                    // this should never happen.
	                    throw new ObjectIdException( "Duplicate ObjectId" );
	                }
	

               if (numOfRows < cache.getTableConfiguration().getFullCacheCountLimit())
                  isFullCacheNeeded = true;
               else
                  isFullCacheNeeded = false;

           } else
               isFullCacheNeeded=true;
	        }

            String querySnt = cache.getInitialQueryCache();
            if (querySnt != null) {
	                NewEventAuditDataQuery query;
	    
	                    DBTransaction tmpTransaction = DODS.getDatabaseManager().createTransaction(get_logicalDBName());
	                    query = new NewEventAuditDataQuery (tmpTransaction);
	                    query.hitDatabase();
           					int initMaxSize = 0;
         					if(cache.getInitialDSCacheSize()> 0){
			         			initMaxSize=cache.getInitialDSCacheSize();
					         }
					         if((initMaxSize > 0) && ((initMaxSize < maxSize)||(maxSize < 0))){
					            isFullCacheNeeded = false;
                           maxSize=initMaxSize;
                        }

     	                 if (maxSize > 0)
	                     try {
	                        query.setMaxRows(maxSize);
	                     }
	                     catch (NonUniqueQueryException nuEx){
	                        System.out.println ("NonUniqueQueryException in enableCache() method : too many rows were found.");
	                     }

	                    if (!querySnt.equals("*")) {
	                        QueryBuilder builder = query.getQueryBuilder();
	                        builder.addWhere(querySnt);
	                    }
	                    NewEventAuditDataDO obj;
	                    try {
	                       	if (cache.getInitialCacheFetchSize()>0){
								query.set_FetchSize(cache.getInitialCacheFetchSize());
							}
							query.set_CursorType(cache.getTableConfiguration().getInitCachesResultSetType(),cache.getTableConfiguration().getInitCachesResultSetConcurrency());
	                        query.setLoadData(true);
	                        query.getNextDO();
	                        tmpTransaction.release();
	                    } catch ( NonUniqueQueryException ex ) {
	                        // Since we do not call query.requireUniqueInstance()
	                        // this should never happen.
	                        throw new ObjectIdException( "Duplicate ObjectId" );
	                    }
	    
	            }
	         }
        }
    }
    private static CachedDBTransaction _tr_(DBTransaction dbt) {
        return (CachedDBTransaction)dbt;
    }

    /**
     * Add DO to cache.
     * If DO already exists in cache, just the data member is replaced,
     * so that application references to the DO remain valid.
     * If there  is no caching newDO object is returned.
     *
     * @param newDO Data object that will be added to cache.
     * @return Data object added to cache.
     */
    private NewEventAuditDataDO addToTransactionCache( NewEventAuditDataDO newDO ) {
        NewEventAuditDataDO ret = null;
        if(get_transaction()!=null && _tr_(get_transaction()).getTransactionCache()!=null) {
            ret = (NewEventAuditDataDO)_tr_(get_transaction()).getTransactionCache().addDO(newDO);
        }
        if (ret == null)
            return newDO;
        return ret;
    }

    /**
     * Add DataStruct object to cache.
     * If there is no caching newDO object is returned.
     *
     * @param newDS DataStruct object that will be added to cache.
     * @return DataStruct object added to cache.
     */
    public static synchronized NewEventAuditDataDataStruct addToCache( NewEventAuditDataDataStruct newDS ) {
        NewEventAuditDataDataStruct ret = (NewEventAuditDataDataStruct)cache.addDataStruct(newDS);
        if (ret == null)
            return newDS;
        return ret;
    }

    /**
     * Add DO's original data object to cache.
     */
    public void addToCache() {
        addToCache((NewEventAuditDataDataStruct)this.originalData_get());
    }

    /**
     * Add DO to cache.
     * If DO already exists in cache, just the data member is replaced,
     * so that application references to the DO remain valid.
     * This method overides method addToCache of the class CoreDO.
     */
    private void addToTransactionCache() {
        addToTransactionCache(this);
    }


     /**
     * UpdateCache for given DataStruct object.
     *
     * @param updDS DataStruct object
     * @return Updated or inserted DataStruct object.
     */
    public static synchronized NewEventAuditDataDataStruct updateCache( NewEventAuditDataDataStruct updDS) {
        NewEventAuditDataDataStruct ret = (NewEventAuditDataDataStruct)cache.updateDataStruct(updDS);
        if (ret == null)
            return updDS;
        return ret;
    }

    /**
     * Update Cache.
     */
    public void updateCache() {
        updateCache((NewEventAuditDataDataStruct)this.originalData_get());
    }

    /**
     * Delete DataStruct object from cache
     *
     * @param data DataStruct object for deleting
     *
     * @return Deleted DataStruct object
     */
    public static synchronized NewEventAuditDataDataStruct deleteFromCache( NewEventAuditDataDataStruct data ) {
        cache.deleteDataStruct(data);
        return data;
    }


    /**
     * Remove DataStruct object from cache.
     *
     * @param dbName Logical name of the database from which
     * NewEventAuditDataDataStruct object will be removed.
     * @param handle Handle of DataStruct object which will be re moved.
     */
    public static synchronized void removeFromCache(String dbName, String handle) {
        String cacheHandle = dbName+"."+handle;
        cache.removeDataStruct( cacheHandle );
    }

    /**
     * Delete object from cache
     */
    public void deleteFromCache() {
        deleteFromCache((NewEventAuditDataDataStruct)this.originalData_get());
    }


    /**
     * Remove DataStruct from cache.
     *
     * @param data DataStruct object which will be removed.
     *
     * @return Removed DataStruct object or null if DataStruct object doesn't
     * exist in the cache.
     */
    public static synchronized NewEventAuditDataDataStruct removeFromCache( NewEventAuditDataDataStruct data ) {
        return (NewEventAuditDataDataStruct)cache.removeDataStruct(data);
    }

    /**
     * Remove DataStruct from cache.
     */
    public void evict() {
        if (!isPersistent())
            removeFromCache((NewEventAuditDataDataStruct)this.originalData_get());
    }

    /**
     * Remove DataStruct objects from cache.
     *
     * @param DSs Array of DataStruct objects which will be removed from cache.
     */
    public static void evict(NewEventAuditDataDataStruct[] DSs) {
        for (int i=0; i<DSs.length; i++)
            removeFromCache((NewEventAuditDataDataStruct) DSs[i]);
    }

    /**
     * Remove DataStruct objects from cache.
     *
     * @param dbName Logical name of the database from which
     * NewEventAuditDataDataStruct object will be removed.
     * @param handles array of DataStruct object handles that will be removed
     * from cache.
     */
    public static void evict(String dbName, String[] handles) {
        if (handles!=null) {
            for (int i=0; i<handles.length; i++)
                removeFromCache(dbName, handles[i]);
        }
    }

    /**
     * DriverDependencies instance helps to overcome non standard
     * solutions implemented by some JDBC drivers.
     *
     */
    static org.enhydra.dods.DriverDependencies tweak;

    /**
     * The cache is used automatically by the class.
     * Callers of the create() methods do not know whether
     * the returned DO instance is shared or not.
     * This prevents the existence of multiple instances in memory
     * of the same database object.
     * The cache is instantiated when the first DO instance is created.
     */

    /**
     * Class that contains unchanging (static) data from the database
     * will have a cache of DOs representing the entire contents of the table.
     */
    static {
        try {
			String dbName = get_logicalDBName();

			useBinaryStreamForLongvarchar = DriverSpecificConstants.DEFAULT_USE_BINARY_STREAM_FOR_LONGVARCHAR;
			try {
				String useBinaryStreamStr = ((StandardLogicalDatabase)DODS.getDatabaseManager()
							  	 .findLogicalDatabase(dbName)).getDriverProperty(DriverSpecificConstants.PARAMNAME_USE_BINARY_STREAM_FOR_LONGVARCHAR);
				if(useBinaryStreamStr!=null){
					if(useBinaryStreamStr.equalsIgnoreCase("true")){
						useBinaryStreamForLongvarchar=true;
					}else if(useBinaryStreamStr.equalsIgnoreCase("false")){
						useBinaryStreamForLongvarchar=false;
					}else{
						DODS.getLogChannel().write(Logger.DEBUG,"NewEventAuditDataDO : Illegal value for UseBinaryStreamForLongarchar parameter. Using default. ");
					}
				}
			} catch (DatabaseManagerException e){
				DODS.getLogChannel().write(Logger.DEBUG,"NewEventAuditDataDO : Unable to read configuration for UseBinaryStreamForLongarchar. Using default. ");
			}

			useOrderedWithTable = null;
			try {
				String orderedResultSetStr = ((StandardLogicalDatabase)DODS.getDatabaseManager()
							  				.findLogicalDatabase(dbName)).getDriverProperty(Common.VENDOR_ORDERED_RESULT_SET);
				if (orderedResultSetStr!=null){
					if (orderedResultSetStr.equalsIgnoreCase("oldStyle")){
						initColumnsNameString((Boolean)null);
					}else if (orderedResultSetStr.equalsIgnoreCase("withPrefix")){
						useOrderedWithTable = new Boolean(true);
						initColumnsNameString(useOrderedWithTable);
					}else if (orderedResultSetStr.equalsIgnoreCase("noPrefix")){
						useOrderedWithTable = new Boolean(false);
						initColumnsNameString(useOrderedWithTable);
					}else{
						DODS.getLogChannel().write(Logger.DEBUG,"NewEventAuditDataDO : Invalid value for OrderedResultSet parameter. Using default. ");
					}
				}else{
					initColumnsNameString((Boolean)null);
				}
                tweak = ((StandardLogicalDatabase)DODS.getDatabaseManager().findLogicalDatabase(dbName)).getDriverDependencies();
			} catch (DatabaseManagerException e){
				DODS.getLogChannel().write(Logger.DEBUG,"NewEventAuditDataDO : Unable to read configuration for OrderedResultSet. Using default. ");
			}

            XMLConfig dodsConf = Common.getDodsConf();
            String cacheClassPath = null;
            String cacheClassName = null;

			String queryCacheImplClass =
				((StandardLogicalDatabase)DODS.getDatabaseManager().findLogicalDatabase(dbName)).getDatabaseConfiguration().getQueryCacheImplClass();
            try {
            	if (queryCacheImplClass==null){
					cacheClassPath = dodsConf.getText("CacheJar");
					cacheClassName = dodsConf.getText("CacheClassName");
					if (cacheClassPath != null && cacheClassName != null) {
						MultiClassLoader loader = new MultiClassLoader(null);
						loader.setClassPath(cacheClassPath);
						Class cacheClass = loader.loadClass(cacheClassName);
						cache = (DataStructCache)cacheClass.newInstance();
						cache = cache.newInstance();
					} else  {
						cache = new QueryCacheImpl();
					}
            	}else{
					Class cacheClass = Class.forName(queryCacheImplClass);
					cache = (DataStructCache)cacheClass.newInstance();
					cache = cache.newInstance();
            	}
            } catch ( Exception e ) {}

            if (cache == null) {
                cache = new QueryCacheImpl();
            }
            readCacheConfiguration(get_logicalDBName());
            get_statistics(); // set statistics
            refreshCache();
        } catch ( Exception e ) {
            // cannot throw from static block
        }
    }

    /**
     * This method is invoked whenever object needs to be loaded from database.
     *
     * @exception DataObjectException If a data access error occurs.
     */
    public void refresh() throws DataObjectException {
        try {
            loadData();
        } catch ( Exception e ) {
            throw new DataObjectException("Unable to load data for NewEventAuditDataDO id=" + get_OId() +
                                          ", error = ", e);
        }
    }

    /**
     * This method is invoked whenever objects needs to be loaded from database.
     *
     * @param DOs Array of DOs which will be red from database.
     *
     * @exception DataObjectException If a data access error occurs.
     */
    public static void refresh(NewEventAuditDataDO[] DOs) throws DataObjectException {
        for (int i=0; i<DOs.length; i++)
            DOs[i].refresh();
    }


    /**
     * Refresh cache by removing from the cache results of the query
     * querySnt
     *
     * @param querySnt query used in this
     * @exception QueryException If a data access error occurs.
     */
         public static void refresh(String querySnt)  throws QueryException {
                 try {
                   QueryBuilder qb = new QueryBuilder();
                   qb.select( NewEventAuditDataDO.PrimaryKey);
                   qb.addWhere(querySnt);
         BigDecimal objId;
         String handle;
         String database = get_logicalDBName();
                   RDBRow row;
                   try {
                           while ( null != ( row = qb.getNextRow() ) ) {
                                objId = row.get( NewEventAuditDataDO.PrimaryKey ).getBigDecimal();
                                   handle = objId.toString();
                       removeFromCache(database, handle);
                           }
                   }catch ( Exception e ) {
                           throw new QueryException(" Query Exception occured," );
                   }
         }catch (Exception ex){
                 System.out.println("Error in refresh(String) of DO object.");
            }
         }


    /**
     * createVirgin(DBTransaction)
     * @param dbTrans The current database transaction
     * @return Created data object.
     *
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     */
    public static NewEventAuditDataDO createVirgin(DBTransaction dbTrans)
    throws DatabaseManagerException, ObjectIdException {
        return new NewEventAuditDataDO (dbTrans);
    }


    /**
     * createExisting( BigDecimal, DBTransaction )
     *
     * @param bd The BigDecimal representation of the ObjectId for the object.
     * @param dbTrans The current database transaction.
     * @return Created NewEventAuditDataDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    public static NewEventAuditDataDO createExisting(BigDecimal bd, DBTransaction dbTrans)
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        return publicCreateExisting(null, bd, null, dbTrans);
    }

    /**
     * Method ceInternal is public, only to allow generated classes
     * to instantiate one another. Public modifier doesn't mean user
     * application could use it - it mustn't.
     *
     * @param bd The BigDecimal representation of the ObjectId for the object.
     * @param dbTrans The current database transaction.
     * @return instance of NewEventAuditDataDO or null
     *
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     * @exception ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     */
    public static NewEventAuditDataDO ceInternal(BigDecimal bd, DBTransaction dbTrans)
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        if (null == bd)
            return null;
        return ceInternal(new ObjectId(bd), dbTrans);
    }

    /**
     * createExisting( BigDecimal, HashMap, DBTransaction )
     *
     * @param bd The BigDecimal representation of the ObjectId for the object.
     * @param queryRefs HashMap Created referenced DO's (key datamaseName.Oid)
     * @param dbTrans The current database transaction.
     * @return Created NewEventAuditDataDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    public static NewEventAuditDataDO createExisting(BigDecimal bd, HashMap queryRefs, DBTransaction dbTrans)
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        return publicCreateExisting(null, bd, queryRefs, dbTrans);
    }

    /**
     * Method ceInternal is public, only to allow generated classes
     * to instantiate one another. Public modifier doesn't mean user
     * application could use it - it mustn't.
     *
     * @param bd The BigDecimal representation of the ObjectId for the object.
     * @param queryRefs HashMap Created referenced DO's (key datamaseName.Oid)
     * @param dbTrans The current database transaction.
     * @return instance of NewEventAuditDataDO or null
     *
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     * @exception ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     */
    public static NewEventAuditDataDO ceInternal(BigDecimal bd, HashMap queryRefs, DBTransaction dbTrans)
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        if (null == bd)
            return null;
        return ceInternal(new ObjectId(bd), queryRefs, dbTrans);
    }



    /**
     * createExisting( String, DBTransaction )
     *
     * The createExisting method is used to create a <CODE>NewEventAuditDataDO</CODE>
     * from a string handle.
     *
     * @param handle String representation of the ObjectId for the object.
     * @param dbTrans The current database transaction.
     * @return Created NewEventAuditDataDO object.
     */
    public static NewEventAuditDataDO createExisting(String handle, DBTransaction dbTrans) {
        NewEventAuditDataDO ret = null;
        try {
            BigDecimal bd = new BigDecimal(handle);
            ret = publicCreateExisting(null, bd, null, dbTrans);
        } catch (Exception e) {
            DODS.getLogChannel().write(Logger.DEBUG," NewEventAuditDataDO class\n : Create existing failed");
        }
        return ret;
    }

    /**
     * Method ceInternal is public, only to allow generated classes
     * to instantiate one another. Public modifier doesn't mean user
     * application could use it - it mustn't.
     *
     * @param handle String representation of the ObjectId for the object.
     * @param dbTrans The current database transaction.
     * @return instance of NewEventAuditDataDO or null
     */
    public static NewEventAuditDataDO ceInternal(String handle, DBTransaction dbTrans) {
        NewEventAuditDataDO ret = null;
        try {
            BigDecimal bd = new BigDecimal(handle);
            ret = ceInternal(bd, dbTrans);
        } catch (Exception e) {
            DODS.getLogChannel().write(Logger.DEBUG," NewEventAuditDataDO class\n : Create existing failed");
        }
        return ret;
    }



    /**
     * createExisting( ObjectId , DBTransaction)
     *
     * Factory method creates a NewEventAuditDataDO object by searching for it
     * in the database using the passed ObjectID value as the primary key.
     *
     * @param id The ObjectId for the object.
     * @param dbTrans The current database transaction.
     * @return Created NewEventAuditDataDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static NewEventAuditDataDO ceInternal(ObjectId id, DBTransaction dbTrans)
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        if (null == id)
            return null;
        return ceInternal(id, null, dbTrans);
    }

    /**
     * ceInternal( ObjectId , HashMap queryRefs, DBTransaction)
     *
     * Factory method creates a NewEventAuditDataDO object by searching for it
     * in the database using the passed ObjectID value as the primary key.
     *
     * @param id The ObjectId for the object.
     * @param queryRefs HashMap of available references.
     * @param dbTrans The current database transaction.
     * @return Created NewEventAuditDataDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static NewEventAuditDataDO ceInternal(ObjectId id, HashMap queryRefs, DBTransaction dbTrans )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {

        boolean isNewDO = true;

        if (null == id)
            return null;
        String cacheHandle = get_logicalDBName()+"."+id.toString();
        NewEventAuditDataDO ret = null;
        NewEventAuditDataDataStruct data = null;
        if (null == queryRefs) {
            queryRefs = new HashMap();
        }
        if (null!= dbTrans && null!= _tr_(dbTrans).getTransactionCache()) {
            ret = (NewEventAuditDataDO)_tr_(dbTrans).getTransactionCache().getDOByHandle(cacheHandle);
            isNewDO=false;
        } else if (queryRefs.containsKey(cacheHandle)) {
            ret = (NewEventAuditDataDO)queryRefs.get(cacheHandle);
            isNewDO=false;
        }
        if (ret==null) {
            ret = (NewEventAuditDataDO)createDO (id, dbTrans);
        }
        if (ret.get_DataStruct().isEmpty) {
            data = findCachedObjectByHandle(cacheHandle);
            if (data != null)
               ret.originalData_set(data);
        }
        ret.setPersistent(true);  // mark DO as persistent (preexisting)

        if (queryRefs!=null) {
            ret.set_refs(queryRefs);
            ret.addRefs(cacheHandle, ret);
        }
        if (!cache.getTableConfiguration().isLazyLoading() ) { // If not lazy-loading, fetch DO data now.
           if(ret.get_DataStruct().isEmpty)
            ret.loadData();
        } else {
            statistics.incrementLazyLoadingNum();
        }
        // unset the GenericDO.dirty flag.
        if(isNewDO)
         ret.markClean();

        return ret;
    }



    /**
     * ceInternal( ResultSet, DBTransaction )
     *
     * @param rs The ResultSet returned by the Query class for
     * an existing Data Object stored in the database.
     * @param dbTrans The current database transaction
     * @return Created NewEventAuditDataDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static NewEventAuditDataDO ceInternal(ResultSet rs , DBTransaction dbTrans)
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        if ( null == rs )
            return null;
        NewEventAuditDataDO ret = null;
        if ( notUsingOId ) {
            ret = new NewEventAuditDataDO (dbTrans);
            ret.initFromResultSet( rs );
        } else {
            ret = new NewEventAuditDataDO ( rs, dbTrans );
        }
        return ret;
    }



    /**
     * ceInternal( ResultSet , HashMap, DBTransaction)
     *
     * @param rs The ResultSet returned by the Query class for
     * an existing Data Object stored in the database.
     * @param queryRefs list of created refernce objects.
     * @param dbTrans The current database transaction
     * @return Created NewEventAuditDataDO object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static NewEventAuditDataDO ceInternal(ResultSet rs , HashMap queryRefs, DBTransaction dbTrans)
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        if ( null == rs )
            return null;
        NewEventAuditDataDO ret = null;
        if ( notUsingOId ) {
            ret = new NewEventAuditDataDO (dbTrans);
            ret.set_refs(queryRefs);
            ret.initFromResultSet( rs );
        } else {
            if(queryRefs==null)
                queryRefs = new HashMap();
            BigDecimal tmpOid = rs.getBigDecimal(get_OIdColumnName());
            String cacheHandle = get_logicalDBName()+"."+tmpOid;
            if(queryRefs.containsKey(cacheHandle)) {
                ret = (NewEventAuditDataDO)queryRefs.get(cacheHandle);
                if (!ret.isLoaded()) {
                    ret.set_refs(queryRefs);
                    ret.initFromResultSet(rs);
                }
                return ret;
            }
            if (useOrderedWithTable != null){
            	ret = new NewEventAuditDataDO (new ObjectId(tmpOid),dbTrans);
            	ret.set_refs(queryRefs);
            	ret.initFromResultSet(rs);
            	//if(dbTrans!=null) dbTrans.lockDO(this);
            }else{
				ret = new NewEventAuditDataDO ( rs, queryRefs, dbTrans );
            }
        }
        return ret;
    }



    /**
     * ceInternal( RDBRow , DBTransaction)
     *
     * Factory method creates a NewEventAuditDataDO object by searching for it
     * in the database using the NewEventAuditDataDO.PrimaryKey value
     * in the passed RDBRow.
     *
     * @param row A row returned by QueryBuilder.getNextRow().
     * @param dbTrans The current database transaction.
     * @return Created NewEventAuditDataDO object.
     *
     * @exception DataObjectException
     *   If the RDBRow does not contain a NewEventAuditDataDO.PrimaryKey.
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static NewEventAuditDataDO ceInternal(RDBRow row, DBTransaction dbTrans )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        if ( null == row )
            return null;
        RDBColumnValue pk = null;
        try {
            pk = row.get( NewEventAuditDataDO.PrimaryKey );
            return ceInternal( pk, dbTrans );
        } catch ( Exception e ) {
            throw new DataObjectException("Cannot create NewEventAuditDataDO, row does not " +
                                          "contain NewEventAuditDataDO primary key." );
        }
    }




    /**
     * ceInternal( RDBColumnValue, DBTransaction )
     *
     * Factory method creates a NewEventAuditDataDO object by searching for it
     * in the database using the passed NewEventAuditDataDO.PrimaryKey.
     *
     * @param pk a PrimaryKey column value from a row that was returned by
     * QueryBuilder.getNextRow().
     * @param dbTrans The current database transaction.
     * @return Created NewEventAuditDataDO object.
     *
     * @exception DataObjectException
     *   If the RDBColumnValue does not contain a NewEventAuditDataDO.PrimaryKey.
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static NewEventAuditDataDO ceInternal(RDBColumnValue pk, DBTransaction dbTrans )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        if ( null == pk )
            return null;
        if ( ! pk.equals( NewEventAuditDataDO.PrimaryKey ) )
            throw new DataObjectException("Cannot create NewEventAuditDataDO, " +
                                          "RDBColumnValue is not NewEventAuditDataDO.PrimaryKey." );
        BigDecimal bd = null;
        try {
            bd = pk.getBigDecimal();
        } catch ( Exception e ) {
            throw new DataObjectException("Cannot create NewEventAuditDataDO, bad primary key." );
        }
        if ( null == bd )
            return null;
        return ceInternal( bd , dbTrans);
    }


    /**
     * Creates a DO that has no ObjectId
     * but has a copy of an existing DO's data.
     * Such a DO is used to insert a new database entry
     * that is largely similar to an existing entry.
     *
     * @param data The data struct to copy values from.
     * @param dbTrans The current database transaction
     * @return Created NewEventAuditDataDO object.
     *
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     */
    public static NewEventAuditDataDO createCopy( NewEventAuditDataDataStruct data, DBTransaction dbTrans )
    throws DatabaseManagerException, ObjectIdException {
        NewEventAuditDataDO ret = new NewEventAuditDataDO (true, dbTrans);
        ret.originalData_set(data);
        ret.markClean();
        return ret;
    }



    /**
     * Creates a DO that has no ObjectId
     * but has a copy of an existing DO's data.
     * Such a DO is used to insert a new database entry
     * that is largely similar to an existing entry.
     *
     * @param orig The original DO to copy.
     *
     * @return Created NewEventAuditDataDO object.
     *
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     */
    public static NewEventAuditDataDO createCopy( NewEventAuditDataDO orig )
    throws DatabaseManagerException, ObjectIdException {
        if (null == orig)
            return null;
        NewEventAuditDataDO ret = new NewEventAuditDataDO (true);
        if (null != orig.originalData_get()) {
            ret.originalData_set(orig.originalData_get());
            ret.markClean();
            ret.transaction = orig.transaction;
            ret.setPersistent(orig.isPersistent());
        }
        return ret;
    }

   /**
     *
     * @param orig The original DO to copy.
     * @param dbTrans The current database transaction
     * @return Created NewEventAuditDataDO object.
     *
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     */
    public static NewEventAuditDataDO createCopy( NewEventAuditDataDO orig, DBTransaction dbTrans )
    throws DatabaseManagerException, ObjectIdException {
        if (null == orig)
            return null;
        NewEventAuditDataDO ret = new NewEventAuditDataDO (true, dbTrans);
        if (null != orig.originalData_get()) {
            ret.originalData_set(orig.originalData_get());
            ret.markClean();
            ret.setPersistent(orig.isPersistent());
        }
        return ret;

    }


    /**
     * Causes the DO to refresh itself from the database
     * the next time a set or get method is called.
     */
    public void reload() {
        originalData = data = null;
    }

    /**
     * The methods <CODE>
     *     getHandle
     *     hasMatchingHandle

     *     findCachedObjectByHandle

     * </CODE> are used by Presentation Objects that need to populate
     * HTML select lists with Data Objects as options.
     * The <CODE>getHandle()</CODE> method is used
     * to set the value for each option,
     * and the <CODE>hasMatchingHandle()<CODE>

     * methods are used to lookup the Data Object when the selection has
     * been made.
     *
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     *
     * @return id of this DO as a string
     *   If an object's id can't be allocated for this object.
     * @deprecated Use get_Handle() instead.
     */
    public String  getHandle()
    throws DatabaseManagerException {
        return get_Handle();
    }


    /**
     * The methods <CODE>
     *     get_Handle
     *     hasMatchingHandle

     *     findCachedObjectByHandle

     * </CODE> are used by Presentation Objects that need to populate
     * HTML select lists with Data Objects as options.
     * The <CODE>get_Handle()</CODE> method is used
     * to set the value for each option,
     * and the <CODE>hasMatchingHandle()<CODE>

     * methods are used to lookup the Data Object when the selection has
     * been made.
     *
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     *
     * @return id of this DO as a string
     *   If an object's id can't be allocated for this object.
     */
    public String  get_Handle()
    throws DatabaseManagerException {
        /*
        String ret = null;
        if ( null == get_OId() )
               throw new DatabaseManagerException( "ID not set " );
        ret = get_OId().toString();
        return ret;
        */
        if (null == __the_handle)
            throw new DatabaseManagerException( "ID not set " );
        return __the_handle;
    }

    /**
     * Returns cache handle.
     *
     * @return cache handle.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     */
    public String  get_CacheHandle()
    throws DatabaseManagerException {
        String ret = get_OriginDatabase() + "." + get_Handle();
        return ret;
    }


    /**
     * Created DO with specified OID.
     *
     * @param oid DO's oid.
     * @param dbTrans The current database transaction.
     * @return copy of DO (with the same id).
     *
     * @exception SQLException
     * @exception ObjectIdException
     * @exception DataObjectException
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     */
    public static GenericDO createDO(ObjectId oid, DBTransaction dbTrans) throws java.sql.SQLException, com.lutris.appserver.server.sql.ObjectIdException, com.lutris.dods.builder.generator.query.DataObjectException, com.lutris.appserver.server.sql.DatabaseManagerException{
        return new NewEventAuditDataDO(oid, dbTrans);
    }


   /**
     * Compare string version of the id of this DO and handle.
     *
     * @param handle
     *   <CODE>String</CODE> version of DO id.
     *
     * @return boolean
     *   True if the string version of the id of this DO matches passed handle.
     *
     * @see NewEventAuditDataDO#get_Handle() get_Handle
     */
    public boolean hasMatchingHandle( String handle ) {
        boolean ret = false;
        if (null == __the_handle) {
           return false;
        } else {
           //String thisHnadle = get_OId().toString();
           ret = __the_handle.equals( handle );
        }
        return ret;
    }


   /**
     * Get data object with key cacheHandle from the cache.
     *
     * @param cacheHandle
     *   <CODE>String</CODE> version of concatenation of:
     * name of the data object's database, followed by '.', followed by
     * data object's id.
     *
     * @return <CODE>NewEventAuditDataDO</CODE>
     *   Object if one is found in cache, otherwise null.
     *
     * @see NewEventAuditDataDO#get_Handle() get_Handle
     */
    public NewEventAuditDataDO findTransactionCachedObjectByHandle( String cacheHandle ){
        if ( null == cacheHandle )
            return null;
        if(get_transaction()!=null && _tr_(get_transaction()).getTransactionCache()!= null)
            return (NewEventAuditDataDO)_tr_(get_transaction()).getTransactionCache().getDOByHandle( cacheHandle );
        else
            return null;
    }

     /**
     * Get DataStruct object with key cacheHandle from the cache.
     *
     * @param cacheHandle
     *   <CODE>String</CODE> version of concatenation of:
     * name of the data object's database, followed by '.', followed by
     * data object's id.
     *
     * @return <CODE>NewEventAuditDataDataStruct</CODE>
     *   Object if one is found in cache, otherwise null.
     *
     * @see NewEventAuditDataDO#get_Handle() get_Handle
     */
    public static NewEventAuditDataDataStruct findCachedObjectByHandle( String cacheHandle ){
        if ( null == cacheHandle )
            return null;
        return ( NewEventAuditDataDataStruct ) cache.getDataStructByHandle( cacheHandle );
    }



    /**
     * Assigns the DataStruct of an existing DO to this DO.
     * Does not duplicate data. Just assigns the reference.
     *
     * @param orig The original DO.
     *
     */
    protected void makeIdentical( NewEventAuditDataDO orig ) {
        super.makeIdentical(orig);
        originalData = orig.originalData;
        data = orig.data;
    }

    /**
     * Return Data object's version.
     * @return Data object's version.
     */
    public int getVersion() {
        return get_Version();
    }

    /**
     * get_Version makes the protected method public in CoreDO.
     *
     * @return Data object's version.
     */
    public int get_Version() {
        return (null != data)?data.get_Version():super.get_Version();
    }

    /**
     * getNewVersion overloaded
     *
     * @return Data object's version.
     * @deprecated get_NewVersion()
     */
    public int getNewVersion() {
        if (null != data)
            return data.get_Version();
        else
            return super.get_Version();
    }

    /**
     * get_NewVersion overloaded
     *
     * @return Data object's version.
     */
    public int get_NewVersion() {
        if (null != data)
            return data.get_Version();
        else
            return super.get_Version();
    }

    /**
     * setVersion overloaded.
     * @param _ver DO's version.
     */
    public void setVersion(int _ver) {
         set_Version(_ver);
    }

    /**
     * set_Version overloaded.
     *
     * @param _ver DO's version.
     */
    public void set_Version(int _ver) {
        if(_ver < get_Version()) {
            new Throwable("WOW, ("+get_OId()+") oldVer:"+get_Version()+", new one is "+_ver).printStackTrace();
        } else if (null != data)
            data.set_Version(_ver);
        else
            super.set_Version(_ver);
    }

    /**
     * setNewVersion overloaded.
     *
     * @param _ver New DO's version.
     * @deprecated Use set_NewVersion
     */
    public void setNewVersion(int _ver){
        set_NewVersion(_ver);
    }

    /**
     * set_NewVersion overloaded.
     *
     * @param _ver Ignored.
     */
    public void set_NewVersion(int _ver){
        try {
            doTouch();
        } catch (Exception e) {
                DODS.getLogChannel().write(Logger.DEBUG," NewEventAuditDataDO class\n :"+" can't set new version ");
        }
    }

    // WebDocWf extension for writeable fully cached objects
    /**
     * Mark the object as read-only.
     *
     * WebDocWf extension
     *
     */
    public void makeReadOnly() {
        if (null != data) {
          try{
            checkDup();
          }catch(Exception ex) {
            DODS.getLogChannel().write(Logger.DEBUG, " MakeReadOnly failed: Database: "+get_OriginDatabase()+" NewEventAuditDataDO class, oid: "+get_OId()+", version: "+get_Version()+" \n");
            (new Throwable()).printStackTrace(DODS.getLogChannel().getLogWriter(Logger.WARNING));
          }
        }
         data.readOnly = true;
    }

    /**
     * Mark the object as read-write.
     *
     * WebDocWf extension
     */
    public void makeReadWrite() {
        if (null != data) {
          try{
            checkDup();
          }catch(Exception ex) {
            DODS.getLogChannel().write(Logger.DEBUG, " MakeReadWrite failed: Database: "+get_OriginDatabase()+" NewEventAuditDataDO class, oid: "+get_OId()+", version: "+get_Version()+" \n");
            (new Throwable()).printStackTrace(DODS.getLogChannel().getLogWriter(Logger.WARNING));

          }
        }
         data.readOnly = false;
    }
    // end of WebDocWf extension for writeable fully cached objects

    // WebDocWf extension for writeable fully cached objects

    /**
     * Set reference objects.
     *
     * @param queryRefs Reference objects.
     */
    private void set_refs(HashMap queryRefs) {
        refs = queryRefs;
    }
    /**
     * Return Object with the handle key from reference objects HashMap.
     *
     * @param key DO's handle.
     * @return Object with the handle key from reference objects HashMap.
     */
    private Object getRefs(String key) {
        if(get_transaction()!=null && _tr_(get_transaction()).getTransactionCache()!= null) {
            return _tr_(get_transaction()).getTransactionCache().getDOByHandle(key);
        } else if (null == refs) {
            refs = new HashMap();
            return null;
        } else {
            return refs.get(key);
        }
    }
    /**
     * Add Object newRefs with the handle key to
     * reference objects HashMap.
     *
     * @param key DO's handle.
     * @param newRefs Object to be added.
     */
    private void addRefs(String key, Object newRefs) {
        if (null == refs) {
            refs = new HashMap();
        }
        refs.put(key, newRefs);
    }

    /**
     * Get reference objects.
     * @return HashMap with reference objects.
     */
    private HashMap get_refs() {
        return refs;
    }

    /**
     * Returns this object's identifier.
     * @return this object's identifier.
     */
    public ObjectId getOId() {
        return get_OId();
    }

    /**
     * Returns this object's identifier.
     * @return this object's identifier.
     */
    public ObjectId get_OId() {
        return get_DataStruct().get_OId();
    }

    /**
     * Sets this object's identifier.
     * @deprecated Use set_OId()
     * @param _oId this object's identifier.
     */
    protected void setOId(ObjectId _oId) {
        set_OId(_oId);
    }

    private String __the_handle;

    /**
     * Sets this object's identifier.
     * @param _oId this object's identifier.
     */
    protected void set_OId(ObjectId _oId) {
        if (get_DataStruct() == null)
            originalData = new NewEventAuditDataDataStruct();
             get_DataStruct().set_OId(_oId);
        __the_handle = _oId.toString();
    }

    /**
     * Creates a clone of the object, but ensures that
     * a new and unique object id is created for the object
     * and that the version number is set to zero.
     *
     * @return Cloned object.
     * @exception DatabaseManagerException if an error occurs while
     * allocation a new object id from the default logical database.
     * @exception ObjectIdException if a new object id could not be
     * allocated.
     */
    public synchronized Object cloneUnique()
        throws DatabaseManagerException, ObjectIdException {

        NewEventAuditDataDO _clone = new NewEventAuditDataDO (get_transaction());

        try {
            checkLoad();
            NewEventAuditDataDataStruct toClone = (null != get_Data())
                     ?(NewEventAuditDataDataStruct)get_Data()
                     :(NewEventAuditDataDataStruct)originalData_get();
            if (null != toClone) {
                _clone.set_Data(toClone.duplicate());
                ((NewEventAuditDataDataStruct)_clone.get_Data()).set_OId(((NewEventAuditDataDataStruct)_clone.originalData_get()).get_OId());
                ((NewEventAuditDataDataStruct)_clone.get_Data()).set_Version(0);
                changedFlags_set(true);
            }
        } catch (Exception e) {
            DODS.getLogChannel().write(Logger.DEBUG," cloneUnique failed: Database: "+get_OriginDatabase()+" NewEventAuditDataDO class, oid: "+get_Handle()+", version: "+get_Version()+" \n");
           (new Throwable()).printStackTrace(DODS.getLogChannel().getLogWriter(Logger.WARNING));

        }
        return _clone;
    }

    protected boolean deleted;

    /**
     * Returns the value of delete tag.
     * @return true if DO has been deleted, but not commited yet
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the value of delete tag.
     * @param flag true if DO has been deleted, but not commited yet.
     */
    public void setDeleted(boolean flag) {
        deleted = flag;
    }


    /**
     * If transaction succeeded marks this object as clean.
     * @param success true if the transaction succeeded
     *   and this object was successfully inserted into the database.
     */
    public void finalizeInsert(boolean success) {
        // on rollback reject inserted (createdVirgin) DO
        if (!success && !isPersistent())
            setDeleted(true);
        super.finalizeInsert(success);
        if (success)
            syncStructs(true);
    }

    /**
     * If transaction succeeded marks this object as clean.
     * @param success true if the transaction succeeded
     *   and this object was successfully updated in the database.
     */
    public void finalizeUpdate(boolean success) {
        super.finalizeUpdate(success);
        if (success)
            syncStructs(true);
    }

    /**
     * Currently does nothing.
     *
     * @param success true if the transaction succeeded
     *   and this object was successfully deleted from the
     *   database.
     */
    public void finalizeDelete(boolean success) {
        super.finalizeDelete(success);
        if (success) {
            deleteFromCache();
        }
    }

    /**
     * Method SyncStructs(boolean _updateCache).
     *
     * @param _updateCache boolean value.
     */
    private synchronized void syncStructs(boolean _updateCache) {
        if (null != data)
            originalData = data;
        data = null;
        changedFlags_set(false);
        ((NewEventAuditDataDataStruct)originalData).readOnly = true;
        if (_updateCache)
            updateCache();
    }

    /**
     * Returns information whether the DO is created virgin and hasn't been
     * commited yet.
     * @return true for DO that's created virgin and hasn't been commited yet.
     */
    public boolean isVirgin() {
        return !isPersistent();
    }

    /**
     * Make DO's data from cache visible.
     */
    public void makeVisible () {
        try {
            ((QueryCache)cache).makeVisible(get_CacheHandle());
        } catch (DatabaseManagerException dme) {
            System.err.println("makeVisible for "+super.toString()+"failed");
        }
    }

    /**
     * Make DO's  data from cache Invisible.
     */
    public void makeInvisible () {
        try {
            ((QueryCache)cache).makeInvisible(get_CacheHandle());
        } catch (DatabaseManagerException dme) {
            System.err.println("makeInvisible for "+super.toString()+"failed");
        }
    }

    /**
     * Inserts this object into the database.
     *
     * @param conn the database connection.
     * @exception java.sql.SQLException if a database access error occurs.
     * @exception DBRowUpdateException If a version error occurs.
     */
    public synchronized void executeInsert(DBConnection conn)
    throws SQLException, DBRowUpdateException {
        if (dirty) {
            super.executeInsert(conn);
            if (tweak.isBlobAccessSpecial()) {
                try {
                    tweak.insertBlob(conn,
                                     getVariableValue(),
                                     PrimaryKey,
                                     VariableValue,
                                     get_Handle());
                } catch (Exception e) {
                    throw new SQLException("Row insert failed - "+e.getMessage());
                }
            }
            changedFlags_set(false);
        }
    }

    public synchronized void executeUpdate(DBConnection conn)
        throws SQLException, DBRowUpdateException {
        if (dirty) {
            super.executeUpdate(conn);
            if (tweak.isBlobAccessSpecial()) {
                try {
                    tweak.insertBlob(conn,
                                     getVariableValue(),
                                     PrimaryKey,
                                     VariableValue,
                                     get_Handle());
                } catch (Exception e) {
                    throw new SQLException("Row insert failed - "+e.getMessage());
                }
            }
            changedFlags_set(false);
        }
    }

    /**
     * Returns value for AutoSave.
     * @return true if AutoSave is on, otherwise false.
     */
    protected boolean isAutoSave()
    {
      boolean flag = false;
      try {
        flag = ((StandardLogicalDatabase)(DODS.getDatabaseManager().findLogicalDatabase(get_OriginDatabase()))).getDatabaseConfiguration().getAutoSave();
      } catch (Exception ex) {}
      return flag;
    }

    /**
     * Returns value for AutoSaveCreateVirgin.
     * @return true if AutoSaveCreateVirgin is on, otherwise false.
     */
    protected boolean isAutoSaveCreateVirgin()
    {
      boolean flag = false;
      try {
        flag = ((StandardLogicalDatabase)(DODS.getDatabaseManager().findLogicalDatabase(get_OriginDatabase()))).getDatabaseConfiguration().getAutoSaveCreateVirgin();
      } catch (Exception ex) {}
      return flag;
    }

    /**
     * Returns value for TransactionCheck.
     * @return true if TransactionCheck is on, otherwise false.
     */
    protected boolean isTransactionCheck()
    {
      boolean flag = false;
      try {
        flag = ((StandardLogicalDatabase)(DODS.getDatabaseManager().findLogicalDatabase(get_OriginDatabase()))).getDatabaseConfiguration().getTransactionCheck();
      } catch (Exception ex) {}
      return flag;
    }

    /**
     * Returns value for TransactionCaches.
     * @return true if TransactionCaches are on, otherwise false.
     */
    protected boolean isTransactionCaches()
    {
      boolean flag = false;
      try {
        flag = ((StandardLogicalDatabase)(DODS.getDatabaseManager().findLogicalDatabase(get_OriginDatabase()))).getDatabaseConfiguration().getTransactionCaches();
      } catch (Exception ex) {}
      return flag;
    }

    /**
     * Returns value for DeleteCheckVersion.
     * @return true if DeleteCheckVersion is on, otherwise false.
     */
    protected boolean isDeleteCheckVersion()
    {
      boolean flag = false;
      try {
        flag = ((StandardLogicalDatabase)(DODS.getDatabaseManager().findLogicalDatabase(this.get_OriginDatabase()))).getDatabaseConfiguration().getDeleteCheckVersion();
      } catch (Exception ex) {}
      return flag;
    }


    /**
     * Returns value for AllReadOnly.
     * @return true if AllReadOnly is on, otherwise false.
     */
    protected static boolean isAllReadOnly()
    {
      boolean flag = false;
      try {
        flag = ((StandardLogicalDatabase)(DODS.getDatabaseManager().findLogicalDatabase(get_logicalDBName()))).getDatabaseConfiguration().isAllReadOnly();
      } catch (Exception ex) {}
      return flag;
    }



     /**
     * Undo action.
     *
     * @exception DataObjectException
     */
    public void undo()throws com.lutris.dods.builder.generator.query.DataObjectException
    {
      try{
        if(null != transaction){
                if((data!=null) || (data==null && isDeleted())){
                        int tempVersion=get_Version();
                                if(isDeleted() && !isDeletedFromDatabase){
                                                unDelete(transaction);
                                }else if (isDeleted() && isDeletedFromDatabase){
                                        data =((NewEventAuditDataDataStruct)originalData).duplicate();
                                        set_Version(tempVersion);
                                        persistent=false;
                                        deleted=false;
                                        isDeletedFromDatabase=false;
                                        if (isAutoSave()) {
                            save(transaction,false);
                        }
                                }else{
                                        data =((NewEventAuditDataDataStruct)originalData).duplicate();
                                        set_Version(tempVersion);
                                        if ( isAutoSave()) {
                            save(transaction,false);
                        }
                                }
                }
        }else{
                        throw new DataObjectException("Error during Undo operation");
                }
      }catch(Exception ex){
                throw new DataObjectException("Error during Undo operation");
      }

    }

    /**
     * dumpData action.
     *
     * @param incrementVersion Increment version.
     */
    public void dumpData(boolean incrementVersion) {
        data = null;
        ObjectId _oid = get_OId();
        int _version = (incrementVersion?1:0)+ get_Version();
        originalData_set(new NewEventAuditDataDataStruct());
        set_OId(_oid);
        set_Version(_version);
    }
    /**
     * publicCreateExisting action.
     *
     * @param _dbName Database name.
     * @param _oid DO's oid.
     * @param _refs Reference objects.
     * @param dbt Current transaction.
     * @return Created DO or null if doesn't exist.
     *
     * @exception SQLException
     * @exception ObjectIdException
     * @exception DataObjectException
     * @exception DatabaseManagerException
     */
    private static NewEventAuditDataDO publicCreateExisting(String _dbName, BigDecimal _oid, HashMap _refs, DBTransaction dbt)
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        if (null == _oid)
            return null;
        if (null == _dbName)
            _dbName = get_logicalDBName();
        NewEventAuditDataQuery qry = new NewEventAuditDataQuery(dbt);
        qry.setLogicalDatabase(_dbName);
        if (null != _refs) {
            qry.setRefs(_refs);
        }
        qry.setQueryOId(new ObjectId(_oid));
        qry.requireUniqueInstance();
        try {
            return qry.getNextDO();
        } catch (com.lutris.dods.builder.generator.query.NonUniqueQueryException nuqe) {
            throw new DataObjectException("Query didn't give unique result.");
        }
    }

    public boolean equals(Object obj) {
        return (obj instanceof NewEventAuditDataDO)
           && this.__the_handle.equals(((NewEventAuditDataDO)obj).__the_handle);
    }


    ////////////////////////// data member DataEventAudit

    /**
     * static final RDBColumn DataEventAudit for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn DataEventAudit = new RDBColumn( table, "DataEventAudit", true);

    private boolean changedDataEventAudit = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_DATAEVENTAUDIT = 0;
    

    /**
     * Get DataEventAudit of the NewEventAuditData.
     *
     * @return DataEventAudit of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.eventaudit.data.DataEventAuditDO getDataEventAudit ()
    throws DataObjectException {
        checkLoad();
        
        if (null == get_DataStruct().getDataEventAudit()) {
            return null;
        }
        String qKey = get_logicalDBName()+"."+get_DataStruct().getDataEventAudit().toString();
        org.enhydra.shark.eventaudit.data.DataEventAuditDO ret = (org.enhydra.shark.eventaudit.data.DataEventAuditDO)getRefs(qKey);
        if(transaction!=null && ret!=null && ret.get_transaction()!=null) {
            if(!(ret.get_transaction()).equals(transaction))
                throw new DataObjectException ("Referenced DO doesn't belong this transaction ");
        } else if (null == ret) {
            try {
                ret = org.enhydra.shark.eventaudit.data.DataEventAuditDO.ceInternal(get_DataStruct().getDataEventAudit(), get_refs(), get_transaction());
                addRefs(qKey, ret);
            } catch (Exception e) {
                throw new DataObjectException("FIXME: should make proper log entry - didn't created ref", e); //FIXME: should make proper log entry
            }
        }
        return ret;
    }


    /**
     * Get BigDecimal value of DataEventAudit of the NewEventAuditData.
     *
     * @return DataEventAudit of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public BigDecimal oid_getDataEventAudit ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getDataEventAudit();
    }

    /**
     * Get original DataEventAudit of the NewEventAuditData.
     *
     * @return DataEventAudit of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.eventaudit.data.DataEventAuditDO originalData_getDataEventAudit ()
    throws DataObjectException {
        checkLoad();
        if (null ==((NewEventAuditDataDataStruct)originalData_get()).getDataEventAudit()) {
            return null;
        }
        String qKey = get_logicalDBName()+"."+((NewEventAuditDataDataStruct)originalData_get()).getDataEventAudit().toString();
        org.enhydra.shark.eventaudit.data.DataEventAuditDO ret = (org.enhydra.shark.eventaudit.data.DataEventAuditDO)getRefs(qKey);
        if(transaction!=null && ret!=null && ret.get_transaction()!=null) {
            if(!(ret.get_transaction()).equals(transaction))
                throw new DataObjectException ("Referenced DO doesn't belong this transaction ");
        } else if (null == ret) {
          try {
            ret = org.enhydra.shark.eventaudit.data.DataEventAuditDO.ceInternal(((NewEventAuditDataDataStruct)originalData_get()).getDataEventAudit(), get_refs(), get_transaction());
            addRefs(qKey, ret);
            } catch (Exception e) {
                throw new DataObjectException("FIXME: should make proper log entry - didn't created ref", e); //FIXME: should make proper log entry
            }
        }
        return ret;
    }



    /**
     * Set DataEventAudit of the NewEventAuditData.
     *
     * @param DataEventAudit of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setDataEventAudit ( org.enhydra.shark.eventaudit.data.DataEventAuditDO DataEventAudit )
    throws DataObjectException {
        _setDataEventAudit ( DataEventAudit );
    }
    
    /**
     * _setDataEventAudit is a protected method that is called by
     * setDataEventAudit if DataEventAudit is not part of
     * a multicolumn foreign key.
     *
     * @param DataEventAudit of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setDataEventAudit ( org.enhydra.shark.eventaudit.data.DataEventAuditDO DataEventAudit )
    throws DataObjectException {
        if(transaction!=null && DataEventAudit!=null && DataEventAudit.get_transaction()!=null) {
            if(!DataEventAudit.get_transaction().equals(this.transaction))
                throw new DataObjectException ("Referenced DO doesn't belong this transaction ");
         }
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        //
        BigDecimal bdNewOId = null;
        if (null != DataEventAudit) {
            bdNewOId = DataEventAudit.get_OId().toBigDecimal();
            String qKey = get_logicalDBName()+"."+ bdNewOId.toString();
            addRefs(qKey, DataEventAudit);
        }
        //
        if (data.isEmpty)
            data.isEmpty = false;
        data.setDataEventAudit(markNewValue(get_DataStruct().getDataEventAudit(),  bdNewOId));
        changedDataEventAudit |= colChanged;
        if (changedDataEventAudit) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }



    /**
     * Set DataEventAudit of the NewEventAuditData.
     * 
     * @param DataEventAudit value of DataEventAudit of the NewEventAuditData as a BigDecimal value.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void oid_setDataEventAudit(BigDecimal DataEventAudit)
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setDataEventAudit(markNewValue(get_DataStruct().getDataEventAudit(), DataEventAudit));
        changedDataEventAudit |= colChanged;
        if (changedDataEventAudit) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }
    
    /**
     * Set DataEventAudit of the NewEventAuditData.
     * 
     * @param DataEventAudit value of DataEventAudit of the NewEventAuditData as a ObjectId value.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void oid_setDataEventAudit(ObjectId DataEventAudit)
    throws DataObjectException {
        if(DataEventAudit != null)
            oid_setDataEventAudit(DataEventAudit.toBigDecimal());
        else
            oid_setDataEventAudit((BigDecimal)null);    
    }

    /**
     * Set DataEventAudit of the NewEventAuditData.
     * 
     * @param DataEventAudit value of DataEventAudit of the NewEventAuditData as a String value.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void oid_setDataEventAudit(String DataEventAudit)
    throws DataObjectException {
        if(DataEventAudit != null)
            oid_setDataEventAudit(new BigDecimal(DataEventAudit));
        else
            oid_setDataEventAudit((BigDecimal)null);    
    }


    ////////////////////////// data member VariableDefinitionId

    /**
     * static final RDBColumn VariableDefinitionId for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn VariableDefinitionId = new RDBColumn( table, "VariableDefinitionId", true);

    private boolean changedVariableDefinitionId = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_VARIABLEDEFINITIONID = 1;
    static public final int VariableDefinitionId_MaxLength = 100;

    /**
     * Get VariableDefinitionId of the NewEventAuditData.
     *
     * @return VariableDefinitionId of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getVariableDefinitionId ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getVariableDefinitionId();
    }


    /**
     * Get original VariableDefinitionId of the NewEventAuditData.
     *
     * @return VariableDefinitionId of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String originalData_getVariableDefinitionId ()
    throws DataObjectException {
        checkLoad();
        return ((NewEventAuditDataDataStruct)originalData_get()).getVariableDefinitionId();
    }



    /**
     * Set VariableDefinitionId of the NewEventAuditData.
     *
     * @param VariableDefinitionId of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setVariableDefinitionId ( String VariableDefinitionId )
    throws DataObjectException {
        _setVariableDefinitionId ( VariableDefinitionId );
    }
    
    /**
     * _setVariableDefinitionId is a protected method that is called by
     * setVariableDefinitionId if VariableDefinitionId is not part of
     * a multicolumn foreign key.
     *
     * @param VariableDefinitionId of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setVariableDefinitionId ( String VariableDefinitionId )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setVariableDefinitionId(markNewValue(get_DataStruct().getVariableDefinitionId(),  VariableDefinitionId, 0, VariableDefinitionId_MaxLength, false));
        changedVariableDefinitionId |= colChanged;
        if (changedVariableDefinitionId) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member VariableType

    /**
     * static final RDBColumn VariableType for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn VariableType = new RDBColumn( table, "VariableType", true);

    private boolean changedVariableType = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_VARIABLETYPE = 2;
    

    /**
     * Get VariableType of the NewEventAuditData.
     *
     * @return VariableType of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public short getVariableType ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getVariableType();
    }


    /**
     * Get original VariableType of the NewEventAuditData.
     *
     * @return VariableType of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public short originalData_getVariableType ()
    throws DataObjectException {
        checkLoad();
        return ((NewEventAuditDataDataStruct)originalData_get()).getVariableType();
    }



    /**
     * Set VariableType of the NewEventAuditData.
     *
     * @param VariableType of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setVariableType ( short VariableType )
    throws DataObjectException {
        _setVariableType ( VariableType );
    }
    
    /**
     * _setVariableType is a protected method that is called by
     * setVariableType if VariableType is not part of
     * a multicolumn foreign key.
     *
     * @param VariableType of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setVariableType ( short VariableType )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setVariableType(markNewValue(get_DataStruct().getVariableType(),  VariableType));
        changedVariableType |= colChanged;
        if (changedVariableType) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member VariableValue

    /**
     * static final RDBColumn VariableValue for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn VariableValue = new RDBColumn( table, "VariableValue", false);

    private boolean changedVariableValue = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_VARIABLEVALUE = 3;
    

    /**
     * Get VariableValue of the NewEventAuditData.
     *
     * @return VariableValue of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public byte[] getVariableValue ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getVariableValue();
    }


    /**
     * Get original VariableValue of the NewEventAuditData.
     *
     * @return VariableValue of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public byte[] originalData_getVariableValue ()
    throws DataObjectException {
        checkLoad();
        return ((NewEventAuditDataDataStruct)originalData_get()).getVariableValue();
    }



    /**
     * Set VariableValue of the NewEventAuditData.
     *
     * @param VariableValue of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setVariableValue ( byte[] VariableValue )
    throws DataObjectException {
        _setVariableValue ( VariableValue );
    }
    
    /**
     * _setVariableValue is a protected method that is called by
     * setVariableValue if VariableValue is not part of
     * a multicolumn foreign key.
     *
     * @param VariableValue of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setVariableValue ( byte[] VariableValue )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setVariableValue(markNewValue(get_DataStruct().getVariableValue(),  VariableValue));
        changedVariableValue |= colChanged;
        if (changedVariableValue) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member VariableValueVCHAR

    /**
     * static final RDBColumn VariableValueVCHAR for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn VariableValueVCHAR = new RDBColumn( table, "VariableValueVCHAR", false);

    private boolean changedVariableValueVCHAR = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_VARIABLEVALUEVCHAR = 4;
    static public final int VariableValueVCHAR_MaxLength = 4000;

    /**
     * Get VariableValueVCHAR of the NewEventAuditData.
     *
     * @return VariableValueVCHAR of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getVariableValueVCHAR ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getVariableValueVCHAR();
    }


    /**
     * Get original VariableValueVCHAR of the NewEventAuditData.
     *
     * @return VariableValueVCHAR of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String originalData_getVariableValueVCHAR ()
    throws DataObjectException {
        checkLoad();
        return ((NewEventAuditDataDataStruct)originalData_get()).getVariableValueVCHAR();
    }



    /**
     * Set VariableValueVCHAR of the NewEventAuditData.
     *
     * @param VariableValueVCHAR of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setVariableValueVCHAR ( String VariableValueVCHAR )
    throws DataObjectException {
        _setVariableValueVCHAR ( VariableValueVCHAR );
    }
    
    /**
     * _setVariableValueVCHAR is a protected method that is called by
     * setVariableValueVCHAR if VariableValueVCHAR is not part of
     * a multicolumn foreign key.
     *
     * @param VariableValueVCHAR of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setVariableValueVCHAR ( String VariableValueVCHAR )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setVariableValueVCHAR(markNewValue(get_DataStruct().getVariableValueVCHAR(),  VariableValueVCHAR, 0, VariableValueVCHAR_MaxLength, true));
        changedVariableValueVCHAR |= colChanged;
        if (changedVariableValueVCHAR) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member VariableValueDBL

    /**
     * static final RDBColumn VariableValueDBL for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn VariableValueDBL = new RDBColumn( table, "VariableValueDBL", false);

    private boolean changedVariableValueDBL = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_VARIABLEVALUEDBL = 5;
    

    /**
     * Get VariableValueDBL of the NewEventAuditData.
     *
     * @return VariableValueDBL of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public double getVariableValueDBL ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getVariableValueDBL();
    }


    /**
     * Get original VariableValueDBL of the NewEventAuditData.
     *
     * @return VariableValueDBL of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public double originalData_getVariableValueDBL ()
    throws DataObjectException {
        checkLoad();
        return ((NewEventAuditDataDataStruct)originalData_get()).getVariableValueDBL();
    }



    /**
     * Set VariableValueDBL of the NewEventAuditData.
     *
     * @param VariableValueDBL of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setVariableValueDBL ( double VariableValueDBL )
    throws DataObjectException {
        _setVariableValueDBL ( VariableValueDBL );
    }
    
    /**
     * _setVariableValueDBL is a protected method that is called by
     * setVariableValueDBL if VariableValueDBL is not part of
     * a multicolumn foreign key.
     *
     * @param VariableValueDBL of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setVariableValueDBL ( double VariableValueDBL )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setVariableValueDBL(markNewValue(get_DataStruct().getVariableValueDBL(),  VariableValueDBL));
        changedVariableValueDBL |= colChanged;
        if (changedVariableValueDBL) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member VariableValueLONG

    /**
     * static final RDBColumn VariableValueLONG for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn VariableValueLONG = new RDBColumn( table, "VariableValueLONG", false);

    private boolean changedVariableValueLONG = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_VARIABLEVALUELONG = 6;
    

    /**
     * Get VariableValueLONG of the NewEventAuditData.
     *
     * @return VariableValueLONG of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public long getVariableValueLONG ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getVariableValueLONG();
    }


    /**
     * Get original VariableValueLONG of the NewEventAuditData.
     *
     * @return VariableValueLONG of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public long originalData_getVariableValueLONG ()
    throws DataObjectException {
        checkLoad();
        return ((NewEventAuditDataDataStruct)originalData_get()).getVariableValueLONG();
    }



    /**
     * Set VariableValueLONG of the NewEventAuditData.
     *
     * @param VariableValueLONG of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setVariableValueLONG ( long VariableValueLONG )
    throws DataObjectException {
        _setVariableValueLONG ( VariableValueLONG );
    }
    
    /**
     * _setVariableValueLONG is a protected method that is called by
     * setVariableValueLONG if VariableValueLONG is not part of
     * a multicolumn foreign key.
     *
     * @param VariableValueLONG of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setVariableValueLONG ( long VariableValueLONG )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setVariableValueLONG(markNewValue(get_DataStruct().getVariableValueLONG(),  VariableValueLONG));
        changedVariableValueLONG |= colChanged;
        if (changedVariableValueLONG) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member VariableValueDATE

    /**
     * static final RDBColumn VariableValueDATE for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn VariableValueDATE = new RDBColumn( table, "VariableValueDATE", false);

    private boolean changedVariableValueDATE = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_VARIABLEVALUEDATE = 7;
    

    /**
     * Get VariableValueDATE of the NewEventAuditData.
     *
     * @return VariableValueDATE of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public java.sql.Timestamp getVariableValueDATE ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getVariableValueDATE();
    }


    /**
     * Get original VariableValueDATE of the NewEventAuditData.
     *
     * @return VariableValueDATE of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public java.sql.Timestamp originalData_getVariableValueDATE ()
    throws DataObjectException {
        checkLoad();
        return ((NewEventAuditDataDataStruct)originalData_get()).getVariableValueDATE();
    }



    /**
     * Set VariableValueDATE of the NewEventAuditData.
     *
     * @param VariableValueDATE of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setVariableValueDATE ( java.sql.Timestamp VariableValueDATE )
    throws DataObjectException {
        _setVariableValueDATE ( VariableValueDATE );
    }
    
    /**
     * _setVariableValueDATE is a protected method that is called by
     * setVariableValueDATE if VariableValueDATE is not part of
     * a multicolumn foreign key.
     *
     * @param VariableValueDATE of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setVariableValueDATE ( java.sql.Timestamp VariableValueDATE )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setVariableValueDATE(markNewValue(get_DataStruct().getVariableValueDATE(),  VariableValueDATE));
        changedVariableValueDATE |= colChanged;
        if (changedVariableValueDATE) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member VariableValueBOOL

    /**
     * static final RDBColumn VariableValueBOOL for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn VariableValueBOOL = new RDBColumn( table, "VariableValueBOOL", false);

    private boolean changedVariableValueBOOL = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_VARIABLEVALUEBOOL = 8;
    

    /**
     * Get VariableValueBOOL of the NewEventAuditData.
     *
     * @return VariableValueBOOL of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public boolean getVariableValueBOOL ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getVariableValueBOOL();
    }


    /**
     * Get original VariableValueBOOL of the NewEventAuditData.
     *
     * @return VariableValueBOOL of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public boolean originalData_getVariableValueBOOL ()
    throws DataObjectException {
        checkLoad();
        return ((NewEventAuditDataDataStruct)originalData_get()).getVariableValueBOOL();
    }



    /**
     * Set VariableValueBOOL of the NewEventAuditData.
     *
     * @param VariableValueBOOL of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setVariableValueBOOL ( boolean VariableValueBOOL )
    throws DataObjectException {
        _setVariableValueBOOL ( VariableValueBOOL );
    }
    
    /**
     * _setVariableValueBOOL is a protected method that is called by
     * setVariableValueBOOL if VariableValueBOOL is not part of
     * a multicolumn foreign key.
     *
     * @param VariableValueBOOL of the NewEventAuditData.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setVariableValueBOOL ( boolean VariableValueBOOL )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setVariableValueBOOL(markNewValue(get_DataStruct().getVariableValueBOOL(),  VariableValueBOOL));
        changedVariableValueBOOL |= colChanged;
        if (changedVariableValueBOOL) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }



    /**
     * Compares whether this DO satisfies condition cond.
     *
     * @param cond condition.
     * 
     * @return true if DO satisfies condition cond, otherwise false.
     */
    public boolean compareCond(Condition cond) {
        try {
            switch(cond.getColumnIndex()) {
        case COLUMN_DATAEVENTAUDIT:
                    
                    if ((getDataEventAudit()!=null)&& (getDataEventAudit() instanceof CoreDO)) {
                     CoreDataStruct xDataStruct = (CoreDataStruct)getDataEventAudit().get_DataStruct(); 
                     return QueryBuilder.compare(xDataStruct,cond.getValue(),cond.getOperator());
                    } 
                    else
                    
                     return QueryBuilder.compare(getDataEventAudit(),cond.getValue(),cond.getOperator());
        case COLUMN_VARIABLEDEFINITIONID:
                    
                     return QueryBuilder.compare(getVariableDefinitionId(),cond.getValue(),cond.getOperator());
        case COLUMN_VARIABLETYPE:
                    
                     return QueryBuilder.compare(getVariableType(),cond.getValue(),cond.getOperator());
        case COLUMN_VARIABLEVALUE:
                    
                     return QueryBuilder.compare(getVariableValue(),cond.getValue(),cond.getOperator());
        case COLUMN_VARIABLEVALUEVCHAR:
                    
                     return QueryBuilder.compare(getVariableValueVCHAR(),cond.getValue(),cond.getOperator());
        case COLUMN_VARIABLEVALUEDBL:
                    
                     return QueryBuilder.compare(getVariableValueDBL(),cond.getValue(),cond.getOperator());
        case COLUMN_VARIABLEVALUELONG:
                    
                     return QueryBuilder.compare(getVariableValueLONG(),cond.getValue(),cond.getOperator());
        case COLUMN_VARIABLEVALUEDATE:
                    
                     return QueryBuilder.compare(getVariableValueDATE(),cond.getValue(),cond.getOperator());
        case COLUMN_VARIABLEVALUEBOOL:
                    
                     return QueryBuilder.compare(getVariableValueBOOL(),cond.getValue(),cond.getOperator());
            }
        } catch (Exception e) {
        }
        return false;
    }

    static {
    }

    /**
     * logicalDbName is logical database name
     * set by setLogicalDBName()
     * and retrieved by get_logicalDBName().
     */
    static private String logicalDbName = null;

    /**
     * setLogicalDBName sets the logical database name that will be used
     * to create DBTransaction and DBQuery objects used by
     * NewEventAuditDataDO and the corresponding Query class.
     * 
     * @param logicalDbNameInConfFile The logical database specified in the
     * application's .conf file.
     *
     * @deprecated It is dangeruous to use this method in multiuser environment because, 
     * this setings are applied to all users (sets logical database to all users)
     */
    static public synchronized void setLogicalDBName( String logicalDbNameInConfFile ) {
        if ( null != logicalDbNameInConfFile && 0    != logicalDbNameInConfFile.length() )
            logicalDbName = logicalDbNameInConfFile;
        else 
            logicalDbName = DODS.getDatabaseManager().getDefaultDB();
    }
    
    /**
     * get_logicalDBName retrieves the logical database name 
     * set by setLogicalDBName().
     *
     * @return the logical database name that was set by method setLogicalDBName()
     *
     */
    static public synchronized String get_logicalDBName() {
        if (logicalDbName == null)
            logicalDbName = DODS.getDatabaseManager().getDefaultDB();                    
        return logicalDbName;
    }

    /**
     * createTransaction() creates a new DBTransaction.
     * This method uses the logical database name set by method setLogicalDBName().
     *
     * If setLogicalDBName() was used to set the logical database name
     * to something other than the value of DatabaseManager.DefaultDatabase
     * in the application's .conf file, then any DBTransaction passed to 
     * save(DBTransaction) or delete(DBTransaction) should be created using 
     * NewEventAuditDataDO.createTransaction().
     *
     * The NewEventAuditDataDO save() and delete() methods use this method.
     *
     * @return A DBTransaction object to use with the NewEventAuditDataDO class.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    static public DBTransaction createTransaction() 
    throws DatabaseManagerException, SQLException {
        DBTransaction ret;
        try {
            ret = DODS.getDatabaseManager().createTransaction(get_logicalDBName());
            ret.setDatabaseName(get_logicalDBName());
            return ret;
        } catch ( DatabaseManagerException e ) { 
            String err = "";
            if ( null != get_logicalDBName() )
                err = "ERROR: Could not create a DBTransaction.  " +
                      "NewEventAuditDataDO.logicalDbName='" + get_logicalDBName() + "'.  "+
                      "The application .conf file must list this name in " +
                      "DatabaseManager.Databases[], and there must be " +
                      "DatabaseManager.DB." + get_logicalDBName() + " settings.";
            throw new DatabaseManagerException( err, e );
        }
    }
    


    /**
     * createQuery() creates a new DBQuery.
     * This method uses the logical database name set by method setLogicalDBName().
     *
     * If setLogicalDBName() was used to set the logical database name
     * to something other than the value of DatabaseManager.DefaultDatabase
     * in the application's .conf file, then any DBQuery object used to 
     * access the 'NewEventAuditData' table should be created using 
     * NewEventAuditDataDO.createQuery().
     *
     * The Query class corresponding to NewEventAuditDataDO uses this method.
     *
     * @param trans DBTransaction
     * @return A DBQuery object to use in accessing the 'NewEventAuditData' table.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    static public DBQuery createQuery(DBTransaction trans) 
    throws DatabaseManagerException, SQLException {

	 	return trans.createQuery();
    }


    /**
     * Protected constructor.
     *
     * @param rs Result set from which to obtain product data.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected NewEventAuditDataDO(ResultSet rs)
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        super(rs);
        initFromResultSet( rs );
        originDatabase = get_logicalDBName();
        get_DataStruct().set_Database(originDatabase);
        set_OId(new ObjectId(rs.getBigDecimal(get_OIdColumnName())));
    	if ( versioning )
       	    set_Version(rs.getInt(get_versionColumnName()));
        if(isTransactionCheck()) {
            DODS.getLogChannel().write(Logger.WARNING, "DO without transaction context is created : Database: "+get_OriginDatabase()+" NewEventAuditDataDO class, oid: "+get_Handle()+", version: "+get_Version()+" \n");
            (new Throwable()).printStackTrace(DODS.getLogChannel().getLogWriter(Logger.WARNING));
            
        }    
    }

    /**
     * Protected constructor.
     *
     * @param rs Result set from which to obtain product data.
     * @param queryRefs Reference objects.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected NewEventAuditDataDO(ResultSet rs, HashMap queryRefs)
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        this(rs,queryRefs,null);
    }

    /**
     * Protected constructor.
     *
     * @param rs Result set from which to obtain product data.
     * @param queryRefs Reference objects.
     * @param dbTrans DBTransaction object.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected NewEventAuditDataDO(ResultSet rs, HashMap queryRefs, DBTransaction dbTrans)
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        super(rs);
        set_refs(queryRefs);
	setTransaction(dbTrans);
        initFromResultSet( rs );
        if(dbTrans!=null)
            originDatabase = dbTrans.getDatabaseName();
        if(originDatabase==null)    
           originDatabase = get_logicalDBName();
        get_DataStruct().set_Database(originDatabase);
        addToTransactionCache();
        if(dbTrans!=null)
            dbTrans.lockDO(this);
    }    

    /**
     * Protected constructor.
     *
     * @param rs Result set from which to obtain product data.
     * @param dbTrans The current database transaction
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected NewEventAuditDataDO(ResultSet rs, DBTransaction dbTrans)
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        this(rs, null, dbTrans);
    }
    
    



    /**
     * while in initFromResultSet, auto save can't be allowed
     */
    private boolean autoSaveAllowed = true;

    /**
     * initFromResultSet initializes the data members of NewEventAuditData.
     * This code was separated from the ResultSet constructor
     * so that createExisting(ResultSet) could handle VIEWs.
     *
     * @param rs ResultSet from which data members are initialized.
     * @exception DataObjectException
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    private void initFromResultSet( ResultSet rs )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        autoSaveAllowed = false;
        // Constructing a DO from a ResultSet means we definitely need the 
        // DataStruct ready for the setXxx methods invoked below.
        if (null == get_DataStruct())
            originalData = new NewEventAuditDataDataStruct ();
	get_DataStruct().isEmpty = false; 
        // writeMemberStuff uses the ResultSetExtraction.template
        // to build up the value for this tag:
        // the value is a series of calls to the DO set methods.
        
        if ( versioning )set_Version(rs.getInt(get_versionColumnName()));
          oid_setDataEventAudit( rs.getBigDecimal( "DataEventAudit") );
         setVariableDefinitionId( rs.getString( "VariableDefinitionId"  ) );
         setVariableType( rs.getShort( "VariableType"  ) );
        if (tweak.isBlobAccessSpecial()) {
            setVariableValue(tweak.readBlob(rs,"VariableValue"));
        } else {
            setVariableValue( rs.getBytes( "VariableValue"  ) );
        }
         setVariableValueVCHAR( rs.getString( "VariableValueVCHAR"  ) );
         setVariableValueDBL( rs.getDouble( "VariableValueDBL"  ) );
         setVariableValueLONG( rs.getLong( "VariableValueLONG"  ) );
         setVariableValueDATE( rs.getTimestamp( "VariableValueDATE"  ) );
         setVariableValueBOOL( rs.getBoolean( "VariableValueBOOL"  ) );

        get_DataStruct().isEmpty = false;
        setPersistent(true);
        markClean();
        syncStructs(false);
//        refs = null;
        autoSaveAllowed = true;
    }

    	private static void initColumnsNameString(Boolean para){
    	if(para==null){
    		columnsNameString="NewEventAuditData.*";
    	}else{
			columnsNameString = "";
   		String tableName;
			if (para.booleanValue()){
				tableName="@T@_NewEventAuditData_@@.";
			}else{
				tableName="@F@_NewEventAuditData_@@.";
			}
			String oidStr = NewEventAuditDataDO.get_OIdColumnName();
			String verStr = NewEventAuditDataDO.get_versionColumnName();
			columnsNameString = tableName+oidStr+", " ;
			if ( versioning ) {
				columnsNameString +=tableName+verStr+", ";
			}

			columnsNameString += tableName + "DataEventAudit, ";

			columnsNameString += tableName + "VariableDefinitionId, ";

			columnsNameString += tableName + "VariableType, ";

			columnsNameString += tableName + "VariableValue, ";

			columnsNameString += tableName + "VariableValueVCHAR, ";

			columnsNameString += tableName + "VariableValueDBL, ";

			columnsNameString += tableName + "VariableValueLONG, ";

			columnsNameString += tableName + "VariableValueDATE, ";

			columnsNameString += tableName + "VariableValueBOOL, ";

			if ( versioning ) {
				columnsNameString += tableName + verStr ;
			}else{
				columnsNameString = columnsNameString.substring(0,columnsNameString.length()-2);
			}
		}
	}


    private int[] param = null;
    private boolean isDeletedFromDatabase = false;
    /**
     * Prepares the statement used to insert this object
     * into the database.
     *
     * @param conn The database connection.
     *
     * @return The insert statement.
     *
     * @exception java.sql.SQLException if an error occurs.
     */
    public PreparedStatement getInsertStatement(DBConnection conn)
    throws SQLException {
        ObjectId oid;
    
	if (isDeletedFromDatabase) 
            throw new SQLException("Object "+get_OId()+" is already deleted");

        PreparedStatement stmt = conn.prepareStatement(  
            "insert into NewEventAuditData ( DataEventAudit, VariableDefinitionId, VariableType, VariableValue, VariableValueVCHAR, VariableValueDBL, VariableValueLONG, VariableValueDATE, VariableValueBOOL, " + get_OIdColumnName() + ", " + get_versionColumnName() + " )" +
            "values ( ?, ?, ?, "+((tweak.isBlobAccessSpecial())?"empty_blob()":"?")+", ?, ?, ?, ?, ?, ?, ?)");


        param = new int[1]; param[0] = 1;
        // writeMemberStuff uses the JDBCsetCalls.template
        // to build up the value for this tag:
        // the value is a series of calls to setPrepStmtParam_TYPE methods.
        // Those methods are defined in GenericDO.
        try {
            setPrepStmtParam_BigDecimal ( stmt, param, oid_getDataEventAudit());
            setPrepStmtParam_String ( stmt, param, getVariableDefinitionId());
            setPrepStmtParam_short ( stmt, param, getVariableType());
            if (!tweak.isBlobAccessSpecial()) {
                setPrepStmtParam_bytes ( stmt, param, getVariableValue());
            }
            setPrepStmtParam_String ( stmt, param, getVariableValueVCHAR());
            setPrepStmtParam_double ( stmt, param, getVariableValueDBL());
            setPrepStmtParam_long ( stmt, param, getVariableValueLONG());
            setPrepStmtParam_java_sql_Timestamp ( stmt, param, getVariableValueDATE());
            setPrepStmtParam_boolean ( stmt, param, getVariableValueBOOL());

            /* The ObjecId/Version columns form the primary key.  */
            setPrepStmtParam_BigDecimal( stmt, param, get_OId().toBigDecimal() );
            setPrepStmtParam_int( stmt, param, get_NewVersion() );

        } catch ( Exception e ) {
            throw new SQLException( "Data Object error: " + e.getMessage() );
        }
        statistics.incrementInsertNum();
        return stmt;
    }


    /**
     *
     */
    private boolean _lockDO = false;

    /**
     * Specifies whether to lock this DO (row) in database just before commit.
     * Locking is attempted via "dummy" update:
     * "update set version=OLD_ONE where OID=X and version=OLD_ONE".
     * @param value true for locking, false otherwise
     */
    public void doCheck(boolean value) {
        _lockDO = value;
    }

    /**
     * Locks this DO in database by performing
     * "update set version=OLD_ONE where OID=X and version=OLD_ONE".
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    public void doLock() throws SQLException {
        if (null!=transaction) {
            incrementVersionToo = false;
            boolean _ol = _lockDO;
            _lockDO = true;
            transaction.lockDO(this);
            _lockDO = _ol;
        }
    }

    private boolean incrementVersionToo = true;
    /**
     * Locks this DO in database by performing
     * "update set version=INCREMENTED where OID=X and version=OLD_ONE".
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     * @exception ObjectIdException
     * @exception DataObjectException
     */
    public void doTouch() 
    throws SQLException, DatabaseManagerException, 
    com.lutris.appserver.server.sql.ObjectIdException, DataObjectException {
        if (null!=transaction) {
	    checkLoad();
	    checkDup();
  	    markNewValue();
            incrementVersionToo = true;
            boolean _ol = _lockDO;
            _lockDO = true;
            transaction.lockDO(this);
            _lockDO = _ol;
        }
    }

    /**
     * Prepares and executes the statement used to lock this object
     * in the database.
     *
     * @param conn The database connection
     *
     * @exception java.sql.SQLException if an error occurs.
     */
    public void executeLockingStatement(DBConnection conn) throws SQLException {
        if (!_lockDO)
            return;
        StringBuffer updateStmt = new StringBuffer();
        PreparedStatement stmt = null;
        if (isDeletedFromDatabase)
            throw new SQLException("NewEventAuditData ("
					+get_OId()+") is already deleted, "
					+"cannot lock it.");

        param = new int[1]; param[0] = 1;
        try {
            updateStmt.append("Update NewEventAuditData set ");
            updateStmt.append(get_versionColumnName()).append(" = ? ");
            updateStmt.append(" where " + get_OIdColumnName() + " = ? and " + get_versionColumnName() + " = ?");

            stmt = conn.prepareStatement(updateStmt.toString());
            setPrepStmtParam_int(stmt, param, get_Version()+(incrementVersionToo?1:0));
            setPrepStmtParam_BigDecimal( stmt, param, get_OId().toBigDecimal() );
            setPrepStmtParam_int(stmt, param, get_Version());
            if (null != stmt) {
	        conn.executeUpdate(stmt, "execute update");
                if (incrementVersionToo) {
                    set_Version(get_Version()+1);
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new SQLException( "Data Object error: " + e.getMessage() );
        }
    }


    /**
     * Prepares the statement used to update this object
     * in the database.
     *
     * @param conn The database connection
     *
     * @return The update statement.
     *
     * @exception java.sql.SQLException if an error occurs.
     */
    public PreparedStatement getUpdateStatement(DBConnection conn)
    throws SQLException {
        StringBuffer updateStmt = new StringBuffer();
        PreparedStatement stmt;

	     if (isDeletedFromDatabase)
            throw new SQLException("Object "+get_OId()+" is already deleted");

        data.set_Version(get_Version()+1);
        param = new int[1]; param[0] = 1;
        try {
            updateStmt.append("Update NewEventAuditData set ");
            updateStmt.append(get_versionColumnName()).append(" = ? ");

            if (changedDataEventAudit)
                updateStmt.append(", DataEventAudit = ? ");
            if (changedVariableDefinitionId)
                updateStmt.append(", VariableDefinitionId = ? ");
            if (changedVariableType)
                updateStmt.append(", VariableType = ? ");
            if (!tweak.isBlobAccessSpecial() && changedVariableValue)
                updateStmt.append(", VariableValue = ? ");
            if (changedVariableValueVCHAR)
                updateStmt.append(", VariableValueVCHAR = ? ");
            if (changedVariableValueDBL)
                updateStmt.append(", VariableValueDBL = ? ");
            if (changedVariableValueLONG)
                updateStmt.append(", VariableValueLONG = ? ");
            if (changedVariableValueDATE)
                updateStmt.append(", VariableValueDATE = ? ");
            if (changedVariableValueBOOL)
                updateStmt.append(", VariableValueBOOL = ? ");
            updateStmt.append(" where " + get_OIdColumnName() + " = ? and " + get_versionColumnName() + " = ?");

            stmt = conn.prepareStatement(updateStmt.toString());
            setPrepStmtParam_int(stmt, param, get_Version());

            if (changedDataEventAudit) {
                setPrepStmtParam_BigDecimal( stmt, param, oid_getDataEventAudit());
                changedDataEventAudit = false;
            }
            if (changedVariableDefinitionId) {
                setPrepStmtParam_String( stmt, param, getVariableDefinitionId());
                changedVariableDefinitionId = false;
            }
            if (changedVariableType) {
                setPrepStmtParam_short( stmt, param, getVariableType());
                changedVariableType = false;
            }
            if (!tweak.isBlobAccessSpecial() && changedVariableValue) {
                setPrepStmtParam_bytes( stmt, param, getVariableValue());
                changedVariableValue = false;
            }
            if (changedVariableValueVCHAR) {
                setPrepStmtParam_String( stmt, param, getVariableValueVCHAR());
                changedVariableValueVCHAR = false;
            }
            if (changedVariableValueDBL) {
                setPrepStmtParam_double( stmt, param, getVariableValueDBL());
                changedVariableValueDBL = false;
            }
            if (changedVariableValueLONG) {
                setPrepStmtParam_long( stmt, param, getVariableValueLONG());
                changedVariableValueLONG = false;
            }
            if (changedVariableValueDATE) {
                setPrepStmtParam_java_sql_Timestamp( stmt, param, getVariableValueDATE());
                changedVariableValueDATE = false;
            }
            if (changedVariableValueBOOL) {
                setPrepStmtParam_boolean( stmt, param, getVariableValueBOOL());
                changedVariableValueBOOL = false;
            }
            setPrepStmtParam_BigDecimal( stmt, param, get_OId().toBigDecimal() );
            setPrepStmtParam_int(stmt, param, get_Version() - 1);
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new SQLException( "Data Object error: " + e.getMessage() );
        }
        statistics.incrementUpdateNum();
        return stmt;
    }

    /**
     * Prepares the statement used to delete this object
     * from the database.
     *
     * @param conn The database connection
     *
     * @return The delete statement.
     *
     * @exception java.sql.SQLException if an error occurs.
     */
    public PreparedStatement getDeleteStatement(DBConnection conn)
    throws SQLException {
        String sql="";
        if(isDeleteCheckVersion())
            sql =
            "delete from NewEventAuditData \n" +
            "where " + get_OIdColumnName() + " = ? and " + get_versionColumnName() + " = ?";
        else    
            sql =
            "delete from NewEventAuditData \n" +
            "where " + get_OIdColumnName() + " = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setBigDecimal(1, get_OId().toBigDecimal());
        if(isDeleteCheckVersion()) {
            stmt.setInt(2, get_Version());
        }
        statistics.incrementDeleteNum();
        isDeletedFromDatabase = true;
        return stmt;
    }
    


    /*
     * toString - for debugging
     *
     * @return String for debugging.
     *
     */
    public String toString(){
        return toString( 1 );
    }

    public String toString( int indentCount ){
        String indent = "";
        for ( int i = 0; i < indentCount; i++ ) {
            indent += ". ";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(indent + "NewEventAuditDataDO:");

        ObjectId oid = get_OId();
        String id = "virgin";
        if ( null != oid )
            id = oid.toString();
        sb.append(" OID=" + id + ",VERSION=" + get_Version());
        if (isLoaded()) {    
                sb.append("\n" + indent + "DataEventAudit=" + get_DataStruct().getDataEventAudit());
    
                sb.append("\n" + indent + "VariableDefinitionId=" + get_DataStruct().getVariableDefinitionId());
    
                sb.append("\n" + indent + "VariableType=" + get_DataStruct().getVariableType());
    
                sb.append("\n" + indent + "VariableValue=" + get_DataStruct().getVariableValue());
    
                sb.append("\n" + indent + "VariableValueVCHAR=" + get_DataStruct().getVariableValueVCHAR());
    
                sb.append("\n" + indent + "VariableValueDBL=" + get_DataStruct().getVariableValueDBL());
    
                sb.append("\n" + indent + "VariableValueLONG=" + get_DataStruct().getVariableValueLONG());
    
                sb.append("\n" + indent + "VariableValueDATE=" + get_DataStruct().getVariableValueDATE());
    
                sb.append("\n" + indent + "VariableValueBOOL=" + get_DataStruct().getVariableValueBOOL());
;
            sb.append("\n" + indent + "SUPER=" + super.toString( indentCount ));
        }
        return sb.toString(); 
    }

    /**
     * A stub method for implementing pre-commit assertions 
     * for this NewEventAuditDataDO.
     * Implement this stub to throw an RefAssertionException for cases
     * where this object is not valid for writing to the database.
     * @exception RefAssertionException
     */
    protected void okToCommit() 
    throws RefAssertionException { 
    }

    /**
     * A stub method for implementing pre-delete assertions 
     * for this NewEventAuditDataDO.
     * Implement this stub to throw an RefAssertionException for cases
     * where this object is not valid for deletion from the database.
     * @exception RefAssertionException
     */
    protected void okToDelete() throws RefAssertionException {  }

    /**
     * Inserts/Updates the DO into its table.
     *
     * @exception com.lutris.appserver.server.sql.DatabaseManagerException If a Transaction can not be created.
     * @exception RefAssertionException Thrown by okTo method.
     * @exception java.sql.SQLException If any SQL errors occur.
     * @exception DataObjectException 
     * @exception DBRowUpdateException
     * @exception QueryException
     *
     * @deprecated Use save() instead.
     */
    public void commit() 
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        commit(null);
    }

    /**
     * Inserts/Updates the DO into its table.
     * The transaction is likely provided by the commit() method of another DO
     * which references this DO.
     * 
     * @param dbt The transaction object used for this operation.
     *
     * @exception com.lutris.appserver.server.sql.DatabaseManagerException If a Transaction can not be created.
     * @exception RefAssertionException Thrown by okTo method.
     * @exception java.sql.SQLException If any SQL errors occur.
     * @exception DataObjectException 
     * @exception DBRowUpdateException
     * @exception QueryException
     *
     * @deprecated Use save() instead.
     */
    public void commit(DBTransaction dbt)
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        if (cache.getTableConfiguration().isReadOnly())
            throw new AssertionDataObjectException("NewEventAuditDataDO's cache is read-only. Therefore, DML opertions are not allowed.");
        // WebDocWf extension for generic store
        try { 
            DBTransaction dbtlocal = dbt;
            boolean needToCommit = false;
            if (dbtlocal == null) {
               if( get_transaction()==null) {
                  dbtlocal = DODS.getDatabaseManager().createTransaction(get_OriginDatabase());
                  dbtlocal.setDatabaseName(get_OriginDatabase());
                  needToCommit = true;
               }else 
                  dbtlocal=transaction;
            } else {
                if(get_transaction()!=null) {
                   if(!get_transaction().equals(dbt))
                         throw new DatabaseManagerException("DO doesn't belong this transaction.");
                }
            }
            modifyDO( dbtlocal, false );
            if (needToCommit) {
                dbtlocal.commit();
                dbtlocal.release();
            }
        } catch (DataObjectException e) {
            modifyDO( dbt, false ); 
        } 
        // end of WebDocWf extension for generic store
    }

    /**
     * Inserts/Updates the DO into its table.
     *
     * @exception com.lutris.appserver.server.sql.DatabaseManagerException If a Transaction can not be created.
     * @exception RefAssertionException Thrown by okTo method.
     * @exception java.sql.SQLException If any SQL errors occur.
     * @exception DataObjectException 
     * @exception DBRowUpdateException
     * @exception QueryException
     * 
     * WebDocWf extension
     */
    public void save() 
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        save(get_transaction(),true);
    }

    /**
     * Inserts/Updates the DO into its table.
     *
     * @param references True if references should be saved with this DO.
     *
     * @exception com.lutris.appserver.server.sql.DatabaseManagerException If a Transaction can not be created.
     * @exception RefAssertionException Thrown by okTo method.
     * @exception java.sql.SQLException If any SQL errors occur.
     * @exception DataObjectException 
     * @exception DBRowUpdateException
     * @exception QueryException
     * 
     * WebDocWf extension
     */
    public void save(boolean references) 
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        save(get_transaction(),references);
    }

    /**
     * Inserts/Updates the DO into its table.
     * The transaction is likely provided by the commit() method of another DO
     * which references this DO.
     * 
     * @param dbt The transaction object used for this operation.
     *
     * @exception com.lutris.appserver.server.sql.DatabaseManagerException If a Transaction can not be created.
     * @exception RefAssertionException Thrown by okTo method.
     * @exception java.sql.SQLException If any SQL errors occur.
     * @exception DataObjectException 
     * @exception DBRowUpdateException
     * @exception QueryException
     * 
     * WebDocWf extension
     */
    public void save(DBTransaction dbt)
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        save(dbt, true);
    }

    /**
     * Inserts/Updates the DO into its table.
     * The transaction is likely provided by the commit() method of another DO
     * which references this DO.
     * 
     * @param dbt The transaction object used for this operation.
     * @param references True if references of this DO should be saved.
     *
     * @exception com.lutris.appserver.server.sql.DatabaseManagerException If a Transaction can not be created.
     * @exception RefAssertionException Thrown by okTo method.
     * @exception java.sql.SQLException If any SQL errors occur.
     * @exception DataObjectException 
     * @exception DBRowUpdateException
     * @exception QueryException
     * 
     * WebDocWf extension
     */
    public void save(DBTransaction dbt, boolean references)
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        if (cache.getTableConfiguration().isReadOnly()) {
            throw new AssertionDataObjectException("NewEventAuditDataDO's cache is read-only. Therefore, DML opertions are not allowed.");
        }    
        // before: modifyDO( dbt, false );
        // The following line has been inserted:
        try {  
            // WebDocWf extension for generic store
            // The following line has been inserted:
            DBTransaction dbtlocal = dbt;
            boolean needToCommit = false;

            if (dbtlocal == null) {
              if( get_transaction()==null) {
                dbtlocal = DODS.getDatabaseManager().createTransaction(get_OriginDatabase());
                dbtlocal.setDatabaseName(get_OriginDatabase());
                needToCommit = true;
              }
             else 
                dbtlocal=transaction;
            } else {
                if(get_transaction()!=null) {
                    if(!get_transaction().equals(dbt))
                          throw new DatabaseManagerException("DO doesn't belong this transaction.");
                }
            }

            // The following line has been changed:
            modifyDO( dbtlocal, false, references );
            if (needToCommit) {
                dbtlocal.commit();
                dbtlocal.release();
            }
        } catch (DataObjectException e) {
            modifyDO( dbt, false );
        }  
        // end of WebDocWf extension for generic store
    }

    /**
     * Deletes the DO from its table.
     *
     * @exception com.lutris.appserver.server.sql.DatabaseManagerException If a Transaction can not be created.
     * @exception RefAssertionException Thrown by okTo method.
     * @exception java.sql.SQLException If any SQL errors occur.
     * @exception DataObjectException 
     * @exception DBRowUpdateException
     * @exception QueryException
     */
    public void delete() 
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        delete(get_transaction(), true);
    }


	/**
	 * Deletes the DO from its table.
	 *
     * @param dbt The transaction object used for this operation.
	 *
	 * @exception com.lutris.appserver.server.sql.DatabaseManagerException If a Transaction can not be created.
	 * @exception RefAssertionException Thrown by okTo method.
	 * @exception java.sql.SQLException If any SQL errors occur.
     * @exception DataObjectException 
     * @exception DBRowUpdateException
     * @exception QueryException
	 */
	public void delete(DBTransaction dbt) 
	throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
		delete(dbt,true);
	}


    /**
     * UnDeletes the DO and inserts to the table.
     *
     * @exception com.lutris.appserver.server.sql.DatabaseManagerException If a Transaction can not be created.
     * @exception RefAssertionException Thrown by okTo method.
     * @exception java.sql.SQLException If any SQL errors occur.
     * @exception DataObjectException 
     * @exception DBRowUpdateException
     * @exception QueryException
     */
    public void unDelete() 
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        unDelete(get_transaction());
    }
    
    /**
     * Deletes the DO from its table.
     * The transaction is likely provided by the delete() method of another DO
     * which references this DO. 
     * NOTE : This method is only for DODS internal usage.
     * @param dbt The transaction object used for this operation.
     * @param rootDO Is DO root in DELETE CASCADE sekvence
     *
     * @exception com.lutris.appserver.server.sql.DatabaseManagerException If a Transaction can not be created.
     * @exception RefAssertionException Thrown by okTo method.
     * @exception java.sql.SQLException If any SQL errors occur.
     * @exception DataObjectException 
     * @exception DBRowUpdateException
     * @exception QueryException
     */
    public void delete(DBTransaction dbt, boolean rootDO)
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        isRootDeleteNode = rootDO ;
        if (cache.getTableConfiguration().isReadOnly())
            throw new AssertionDataObjectException("NewEventAuditDataDO's cache is read-only. Therefore, DML opertions are not allowed.");
        // WebDocWf extension for generic store
        // The following lines have been inserted:
        try {       
            DBTransaction dbtlocal = dbt;
            boolean needToCommit = false;
            if (dbtlocal == null) {
              if(get_transaction()==null) {
                dbtlocal = DODS.getDatabaseManager().createTransaction(get_OriginDatabase());
                dbtlocal.setDatabaseName(get_OriginDatabase());
                needToCommit = true;
              }
              else
                dbtlocal=transaction;  
            } else {
                if(get_transaction()!=null) {
                    if(!get_transaction().equals(dbtlocal))
                        throw new DatabaseManagerException("DO doesn't belong this transaction.");
                }
            }
            // The following line has been changed:
            modifyDO( dbtlocal, true );
            // end of WebDocWf extension for generic store
            if (needToCommit) {
                dbtlocal.commit();
                dbtlocal.release();
            }
        } catch (DataObjectException e) {
            modifyDO( dbt, true );
        } 
    }

    /**
     * UnDeletes the DO and inserts to the table.
     *
     * @param dbt The transaction object used for this operation.
     *
     * @exception com.lutris.appserver.server.sql.DatabaseManagerException If a Transaction can not be created.
     * @exception RefAssertionException Thrown by okTo method.
     * @exception java.sql.SQLException If any SQL errors occur.
     * @exception DataObjectException 
     * @exception DBRowUpdateException
     * @exception QueryException
     */
    public void unDelete(DBTransaction dbt)
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
     
     if (cache.getTableConfiguration().isReadOnly())
            throw new AssertionDataObjectException("NewEventAuditDataDO's cache is read-only. Therefore, DML opertions are not allowed.");
        // WebDocWf extension for generic store
        // The following lines have been inserted:
        try {
            DBTransaction dbtlocal = dbt;
            boolean needToCommit = false;
            if (dbtlocal == null) {
              if(get_transaction()==null) {
                dbtlocal = DODS.getDatabaseManager().createTransaction(get_OriginDatabase());
                dbtlocal.setDatabaseName(get_OriginDatabase());
                needToCommit = true;
              }
              else
                dbtlocal=transaction;  
            } else {
           if(get_transaction()!=null) {
                if(!get_transaction().equals(dbtlocal))
                    throw new DatabaseManagerException("DO didn't belong this transaction.");
           }
          }
          setDeleted(false);
	 isDeletedFromDatabase = false;
          persistent=false;
          modifyDO( dbtlocal, false );
          if (needToCommit) {
                dbtlocal.commit();
                dbtlocal.release();
          }
          isDeletedFromDatabase = false;
        } catch (DataObjectException e) { 
            persistent=true;
            setDeleted(true);
        } 
    }

    
    /**
     * A stub method for implementing pre-commit assertions 
     * for the DataEventAudit data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where DataEventAudit is not valid for writing to the database.
     *
     * @param member DataEventAudit data member. 
     * @exception RefAssertionException
     *
     */
    protected void okToCommitDataEventAudit( org.enhydra.shark.eventaudit.data.DataEventAuditDO member ) 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for the DataEventAudit data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where DataEventAudit is not valid for deletion from the database.
     *
     * @param member DataEventAudit data member
     * @exception RefAssertionException
     *
     */
    protected void okToDeleteDataEventAudit( org.enhydra.shark.eventaudit.data.DataEventAuditDO member ) 
    throws RefAssertionException { }

    /**
     * Modifies the DO within its table.
     * Performs recursive commit/delete on referenced DOs;
     * all operations occur within a single transaction
     * to allow rollback in the event of error.
     * Only the creator of the transaction releases it.
     *
     * @param dbt The transaction object used for this operation.
     * @param delete True if doing a delete, otherwise false (for insert/update).
     * @exception com.lutris.appserver.server.sql.DatabaseManagerException If a Transaction can not be created.
     * @exception RefAssertionException Thrown by okTo method.
     * @exception java.sql.SQLException If any SQL errors occur.
     * @exception DataObjectException 
     * @exception DBRowUpdateException
     * @exception QueryException
     * 
     * WebDocWf extension
     */
    protected void modifyDO( DBTransaction dbt, boolean delete)
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        modifyDO( dbt, delete, true);
    }

    /**
     * Modifies the DO within its table.
     * Performs recursive commit/delete on referenced DOs;
     * all operations occur within a single transaction
     * to allow rollback in the event of error.
     * Only the creator of the transaction releases it.
     *
     * @param dbt The transaction object used for this operation.
     * @param delete True if doing a delete, otherwise false (for insert/update).
     * @param references True if references should be saved
     *
     * @exception com.lutris.appserver.server.sql.DatabaseManagerException If a Transaction can not be created.
     * @exception RefAssertionException Thrown by okTo method.
     * @exception java.sql.SQLException If any SQL errors occur.
     * @exception DataObjectException 
     * @exception DBRowUpdateException
     * @exception QueryException
     * 
     * WebDocWf extension
     */
    protected void modifyDO( DBTransaction dbt, boolean delete, boolean references )
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        if ( delete ) 
            okToDelete();
        else
            okToCommit();

        boolean ownTransaction = false;
        try {
            if ( null == dbt ) {
                if( null==get_transaction()) { 
                    DatabaseManager dbm = DODS.getDatabaseManager();
                    dbt = dbm.createTransaction(get_OriginDatabase());      // create a transaction
                    dbt.setDatabaseName(get_OriginDatabase());
                    ownTransaction = true;
                } else {       
                    dbt=transaction;
                }
            } else {

                if(get_transaction()!=null) {
                    if(!get_transaction().equals(dbt))
                        throw new DatabaseManagerException("DO doesn't belong this transaction.");
                }
            }
            // end of WebDocWf fix for circular references
            if ( null == dbt )
                throw new DatabaseManagerException("DatabaseManager.createTransaction returned null." );
            if ( delete ) {
               _tr_(dbt).addDeletedDO(this);

                // The following line keeps the compiler happy 
                // when the CASCADING_DELETES tag is empty.
                if ( false )
                    throw new QueryException("XXX");
            } else {
                // WebDocWf extension for save without references
                // The following line has been inserted
                if (references) {
                    // end of WebDocWf extension for save without references
                    // commit referenced DOs.
                    org.enhydra.shark.eventaudit.data.DataEventAuditDO DataEventAudit_DO = getDataEventAudit();
                    if ( null != DataEventAudit_DO && !this.equals(DataEventAudit_DO)) {
                        if ( DataEventAudit_DO.isDirty() ) {
                            boolean tmpDirty = true;
                            if(isDirty()) {
                                markClean();
                                tmpDirty = false;
                            }    
                            okToCommitDataEventAudit( DataEventAudit_DO );
                            if ( !DataEventAudit_DO.getConfigurationAdministration().getTableConfiguration().isReadOnly() ) {
                                DataEventAudit_DO.save( dbt );
                                if(!tmpDirty)
                                   markNewValue(); 
                            }    
                        } else {
                            // since the referenced DO is not loaded,
                            // it cannot be dirty, so there is no need to commit it.
                        }
                    } else {
                        if ( ! false )
                            throw new RefAssertionException( "Cannot commit NewEventAuditDataDO ( " + toString() +
                                                             " ) because DataEventAudit is not allowed to be null." );
                    }
 
                    // WebDocWf extension for save without references
                    // The following line has been inserted
                }
                // end of WebDocWf extension for save without references
            }
            if ( false ) {
                // This throw is here to keep the compiler happy
                // in the case of a DO that does not refer to other DOs.
                // In that case, the above delete/commit code blocks will be empty
                // and throw nothing.
                throw new DataObjectException( "foo" );   
            }
            if ( delete ) {
              dbt.delete( this );
              setDeleted(true);
              if (isRootDeleteNode) {
                   _tr_(dbt).resetDeletedDOs();
              }
            } else {
                if ( isLoaded() || isAutoSaveCreateVirgin())
                    dbt.insert( this );   // dbt.insert() handles insertions and updates
            }
            if (ownTransaction) {
                dbt.commit(); // commit the transaction
            }
        } catch (SQLException sqle) {
            StringBuffer message = new StringBuffer("Failed to insert/update DO: ");
            message.append(sqle.getMessage());
            // rollback, if necessary
            if (ownTransaction) {
                try {
                    dbt.rollback();
                } catch (SQLException sqle2) {
                    message.insert(0,"\n");
                    message.insert(0,sqle2.getMessage());
                    message.insert(0,"Rollback failed: ");
                }
            }
            throw new SQLException(message.toString());
        } finally {
            // release the transaction, if any
            if (ownTransaction) {
                dbt.release();
            }
        }
    }

    private void changedFlags_set(boolean value) {
        changedDataEventAudit = value;
        changedVariableDefinitionId = value;
        changedVariableType = value;
        changedVariableValue = value;
        changedVariableValueVCHAR = value;
        changedVariableValueDBL = value;
        changedVariableValueLONG = value;
        changedVariableValueDATE = value;
        changedVariableValueBOOL = value;
    }
}
