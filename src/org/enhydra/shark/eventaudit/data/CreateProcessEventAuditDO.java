
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
 * org.enhydra.shark.eventaudit.data/CreateProcessEventAuditDO.java
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
 * Data core class, used to set and retrieve the CreateProcessEventAuditDO information.
 *
 * @version $Revision: 1.16 $
 * @author  NN
 * @since   DODS Project
 */
 public class CreateProcessEventAuditDO extends com.lutris.dods.builder.generator.dataobject.GenericDO implements CreateProcessEventAuditDOI, java.io.Serializable {
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
    static public final RDBTable  table = new RDBTable( "CreateProcessEventAudits" );

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
     * Return CreateProcessEventAudits as the name of the table in the database
     * which contains CreateProcessEventAuditDO objects.
     * This method overrides CoreDO.getTableName()
     * and is used by CoreDO.executeUpdate() during error handling.
     *
     * @return The database table name.
     *
     * @see com.lutris.appserver.server.sql.CoreDO CoreDO
     * author Jay Gunter
     */
    public String getTableName() {
        return "CreateProcessEventAudits";
    }

    /**
     * static final RDBColumn PrimaryKey for use with QueryBuilder.
     * See example above.
     */
    static public final RDBColumn PrimaryKey = new RDBColumn( table, get_primaryKeyName() );

    /* RDBColumns for CreateProcessEventAuditDO attributes are defined below. */


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
     * If notUsingOId is true, CreateProcessEventAuditDO.createExisting(ResultSet)
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
    private CreateProcessEventAuditDataStruct data = null;

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
        this.data = (CreateProcessEventAuditDataStruct)data;
     }

    /**
     * Sets original DO's data.
     * @param data Data object.
     */
     public void originalData_set (Object data) {
        originalData = (CreateProcessEventAuditDataStruct)data;
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
    public CreateProcessEventAuditDataStruct getDataStruct () {
        return get_DataStruct();
    }

    /**
     * Returns dataStruct.
     * @return Data Struct object.
     */
    public CreateProcessEventAuditDataStruct get_DataStruct () {
        return (CreateProcessEventAuditDataStruct) get_Data();
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
             data = ((CreateProcessEventAuditDataStruct)originalData).duplicate();
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
    protected CreateProcessEventAuditDO ( boolean is_view )
    throws ObjectIdException, DatabaseManagerException {
        super( is_view );
        if(isTransactionCheck()) {
            DODS.getLogChannel().write(Logger.WARNING, "DO without transaction context is created :"+(is_view?"":" Database: "+get_OriginDatabase()+" CreateProcessEventAuditDO class, oid: "+get_Handle()+", version: "+get_Version())+" \n");
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
    protected CreateProcessEventAuditDO ( boolean is_view, DBTransaction dbTrans )
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
    protected CreateProcessEventAuditDO ()
    throws ObjectIdException, DatabaseManagerException {
        super( notUsingOId );
        originDatabase = get_logicalDBName();
        get_DataStruct().set_Database(originDatabase);
        if(isTransactionCheck()) {
            DODS.getLogChannel().write(Logger.WARNING, "DO without transaction context is created : Database: "+get_OriginDatabase()+" CreateProcessEventAuditDO class, oid: "+get_Handle()+", version: "+get_Version()+" \n");
            (new Throwable()).printStackTrace(DODS.getLogChannel().getLogWriter(Logger.WARNING));

        }
        if (autoSaveAllowed&&isAutoSaveCreateVirgin()&&null != transaction) {
            try {
                save(transaction,false);
            } catch (Exception ex) {
                        DODS.getLogChannel().write(Logger.DEBUG,"Faild to AutoSave virgin DO: "+get_OriginDatabase()+" CreateProcessEventAuditDO class\n");
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
    protected CreateProcessEventAuditDO (DBTransaction dbTrans)
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
                        DODS.getLogChannel().write(Logger.DEBUG,"Faild to AutoSave virgin DO: "+get_OriginDatabase()+" CreateProcessEventAuditDO class\n");
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
            originalData = new CreateProcessEventAuditDataStruct ();
        }

        ObjectId id = get_OId();
        if ( null == id )
            return;
        if ( ! isPersistent() )   // DO from createVirgin
            return;
        // DO from createExisting.  Complain if no record in database.
        CreateProcessEventAuditQuery query;

               query = new CreateProcessEventAuditQuery (get_transaction());

//        if(get_refs()!=null)
//        {
        query.setRefs(get_refs());
//        }
        query.setQueryOId( id );
        query.requireUniqueInstance();
        CreateProcessEventAuditDO obj;
        try {
           query.setLoadData(true);
           obj = query.getNextDO();
            if ( null == obj )
                throw new DataObjectException("CreateProcessEventAuditDO DO not found for id=" + id );
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
                throw new DataObjectException("Unable to load data for CreateProcessEventAuditDO id=" + get_OId() +
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
    protected CreateProcessEventAuditDO( ObjectId id )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        super( id );
        originDatabase = get_logicalDBName();
        get_DataStruct().set_Database(originDatabase);
        if(isTransactionCheck()) {
            DODS.getLogChannel().write(Logger.WARNING, "DO without transaction context is created : Database: "+get_OriginDatabase()+" CreateProcessEventAuditDO class, oid: "+get_Handle()+", version: "+get_Version()+" \n");
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
    protected CreateProcessEventAuditDO( ObjectId id , DBTransaction dbTrans)
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


    protected static DataStructCache cache; // cache for CreateProcessEventAuditDO


    protected static boolean isFullCacheNeeded = false; // it depends of CacheFullCacheCountLimit parameter and number of data in database.



    /**
     * Read cache configuration from application configuration file:
     * cache size for org.enhydra.shark.eventaudit.data.CreateProcessEventAudits table or default cache size.
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
            tableConfig = (Config)DODS.getDatabaseManager().getConfig().getSection("DB."+database+".CreateProcessEventAudits");
        } catch (Exception ex) {
                    DODS.getLogChannel().write(Logger.DEBUG," CreateProcessEventAuditDO class\n :"+" Using default configuration for 'CreateProcessEventAudits' table");
        }
        try {
            cacheConfig = (Config)DODS.getDatabaseManager().getConfig().getSection("DB."+database+".CreateProcessEventAudits.cache");
        } catch ( Exception e ) {
            DODS.getLogChannel().write(Logger.DEBUG," CreateProcessEventAuditDO class\n :"+" Using default cache configuration for 'CreateProcessEventAudits' table");
        }
        
        cache.readConfiguration(tableConfig,cacheConfig, database);
    }


    /**
     * Get name of the table that is cached.
     *
     * @return Name of the table that is cached.
     */
    public static String getCacheDodsTableName() {
        return "CreateProcessEventAudits";
    }


    /**
     * Returns CreateProcessEventAudits table cache.
     *
     * @return CreateProcessEventAudits table cache.
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
  	            CreateProcessEventAuditQuery query;

	                DBTransaction tmpTransaction = DODS.getDatabaseManager().createTransaction(get_logicalDBName());
	                query = new CreateProcessEventAuditQuery (tmpTransaction);
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
	            CreateProcessEventAuditQuery query;

	                DBTransaction tmpTransaction = DODS.getDatabaseManager().createTransaction(get_logicalDBName());
	                query = new CreateProcessEventAuditQuery (tmpTransaction);
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
	                CreateProcessEventAuditDO obj;
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
  	            CreateProcessEventAuditQuery query;

	                DBTransaction tmpTransaction = DODS.getDatabaseManager().createTransaction(get_logicalDBName());
	                query = new CreateProcessEventAuditQuery (tmpTransaction);
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
	                CreateProcessEventAuditQuery query;
	    
	                    DBTransaction tmpTransaction = DODS.getDatabaseManager().createTransaction(get_logicalDBName());
	                    query = new CreateProcessEventAuditQuery (tmpTransaction);
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
	                    CreateProcessEventAuditDO obj;
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
    private CreateProcessEventAuditDO addToTransactionCache( CreateProcessEventAuditDO newDO ) {
        CreateProcessEventAuditDO ret = null;
        if(get_transaction()!=null && _tr_(get_transaction()).getTransactionCache()!=null) {
            ret = (CreateProcessEventAuditDO)_tr_(get_transaction()).getTransactionCache().addDO(newDO);
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
    public static synchronized CreateProcessEventAuditDataStruct addToCache( CreateProcessEventAuditDataStruct newDS ) {
        CreateProcessEventAuditDataStruct ret = (CreateProcessEventAuditDataStruct)cache.addDataStruct(newDS);
        if (ret == null)
            return newDS;
        return ret;
    }

    /**
     * Add DO's original data object to cache.
     */
    public void addToCache() {
        addToCache((CreateProcessEventAuditDataStruct)this.originalData_get());
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
    public static synchronized CreateProcessEventAuditDataStruct updateCache( CreateProcessEventAuditDataStruct updDS) {
        CreateProcessEventAuditDataStruct ret = (CreateProcessEventAuditDataStruct)cache.updateDataStruct(updDS);
        if (ret == null)
            return updDS;
        return ret;
    }

    /**
     * Update Cache.
     */
    public void updateCache() {
        updateCache((CreateProcessEventAuditDataStruct)this.originalData_get());
    }

    /**
     * Delete DataStruct object from cache
     *
     * @param data DataStruct object for deleting
     *
     * @return Deleted DataStruct object
     */
    public static synchronized CreateProcessEventAuditDataStruct deleteFromCache( CreateProcessEventAuditDataStruct data ) {
        cache.deleteDataStruct(data);
        return data;
    }


    /**
     * Remove DataStruct object from cache.
     *
     * @param dbName Logical name of the database from which
     * CreateProcessEventAuditDataStruct object will be removed.
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
        deleteFromCache((CreateProcessEventAuditDataStruct)this.originalData_get());
    }


    /**
     * Remove DataStruct from cache.
     *
     * @param data DataStruct object which will be removed.
     *
     * @return Removed DataStruct object or null if DataStruct object doesn't
     * exist in the cache.
     */
    public static synchronized CreateProcessEventAuditDataStruct removeFromCache( CreateProcessEventAuditDataStruct data ) {
        return (CreateProcessEventAuditDataStruct)cache.removeDataStruct(data);
    }

    /**
     * Remove DataStruct from cache.
     */
    public void evict() {
        if (!isPersistent())
            removeFromCache((CreateProcessEventAuditDataStruct)this.originalData_get());
    }

    /**
     * Remove DataStruct objects from cache.
     *
     * @param DSs Array of DataStruct objects which will be removed from cache.
     */
    public static void evict(CreateProcessEventAuditDataStruct[] DSs) {
        for (int i=0; i<DSs.length; i++)
            removeFromCache((CreateProcessEventAuditDataStruct) DSs[i]);
    }

    /**
     * Remove DataStruct objects from cache.
     *
     * @param dbName Logical name of the database from which
     * CreateProcessEventAuditDataStruct object will be removed.
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
						DODS.getLogChannel().write(Logger.DEBUG,"CreateProcessEventAuditDO : Illegal value for UseBinaryStreamForLongarchar parameter. Using default. ");
					}
				}
			} catch (DatabaseManagerException e){
				DODS.getLogChannel().write(Logger.DEBUG,"CreateProcessEventAuditDO : Unable to read configuration for UseBinaryStreamForLongarchar. Using default. ");
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
						DODS.getLogChannel().write(Logger.DEBUG,"CreateProcessEventAuditDO : Invalid value for OrderedResultSet parameter. Using default. ");
					}
				}else{
					initColumnsNameString((Boolean)null);
				}
                tweak = ((StandardLogicalDatabase)DODS.getDatabaseManager().findLogicalDatabase(dbName)).getDriverDependencies();
			} catch (DatabaseManagerException e){
				DODS.getLogChannel().write(Logger.DEBUG,"CreateProcessEventAuditDO : Unable to read configuration for OrderedResultSet. Using default. ");
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
            throw new DataObjectException("Unable to load data for CreateProcessEventAuditDO id=" + get_OId() +
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
    public static void refresh(CreateProcessEventAuditDO[] DOs) throws DataObjectException {
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
                   qb.select( CreateProcessEventAuditDO.PrimaryKey);
                   qb.addWhere(querySnt);
         BigDecimal objId;
         String handle;
         String database = get_logicalDBName();
                   RDBRow row;
                   try {
                           while ( null != ( row = qb.getNextRow() ) ) {
                                objId = row.get( CreateProcessEventAuditDO.PrimaryKey ).getBigDecimal();
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
    public static CreateProcessEventAuditDO createVirgin(DBTransaction dbTrans)
    throws DatabaseManagerException, ObjectIdException {
        return new CreateProcessEventAuditDO (dbTrans);
    }


    /**
     * createExisting( BigDecimal, DBTransaction )
     *
     * @param bd The BigDecimal representation of the ObjectId for the object.
     * @param dbTrans The current database transaction.
     * @return Created CreateProcessEventAuditDO object.
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
    public static CreateProcessEventAuditDO createExisting(BigDecimal bd, DBTransaction dbTrans)
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
     * @return instance of CreateProcessEventAuditDO or null
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
    public static CreateProcessEventAuditDO ceInternal(BigDecimal bd, DBTransaction dbTrans)
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
     * @return Created CreateProcessEventAuditDO object.
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
    public static CreateProcessEventAuditDO createExisting(BigDecimal bd, HashMap queryRefs, DBTransaction dbTrans)
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
     * @return instance of CreateProcessEventAuditDO or null
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
    public static CreateProcessEventAuditDO ceInternal(BigDecimal bd, HashMap queryRefs, DBTransaction dbTrans)
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        if (null == bd)
            return null;
        return ceInternal(new ObjectId(bd), queryRefs, dbTrans);
    }



    /**
     * createExisting( String, DBTransaction )
     *
     * The createExisting method is used to create a <CODE>CreateProcessEventAuditDO</CODE>
     * from a string handle.
     *
     * @param handle String representation of the ObjectId for the object.
     * @param dbTrans The current database transaction.
     * @return Created CreateProcessEventAuditDO object.
     */
    public static CreateProcessEventAuditDO createExisting(String handle, DBTransaction dbTrans) {
        CreateProcessEventAuditDO ret = null;
        try {
            BigDecimal bd = new BigDecimal(handle);
            ret = publicCreateExisting(null, bd, null, dbTrans);
        } catch (Exception e) {
            DODS.getLogChannel().write(Logger.DEBUG," CreateProcessEventAuditDO class\n : Create existing failed");
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
     * @return instance of CreateProcessEventAuditDO or null
     */
    public static CreateProcessEventAuditDO ceInternal(String handle, DBTransaction dbTrans) {
        CreateProcessEventAuditDO ret = null;
        try {
            BigDecimal bd = new BigDecimal(handle);
            ret = ceInternal(bd, dbTrans);
        } catch (Exception e) {
            DODS.getLogChannel().write(Logger.DEBUG," CreateProcessEventAuditDO class\n : Create existing failed");
        }
        return ret;
    }



    /**
     * createExisting( ObjectId , DBTransaction)
     *
     * Factory method creates a CreateProcessEventAuditDO object by searching for it
     * in the database using the passed ObjectID value as the primary key.
     *
     * @param id The ObjectId for the object.
     * @param dbTrans The current database transaction.
     * @return Created CreateProcessEventAuditDO object.
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
    protected static CreateProcessEventAuditDO ceInternal(ObjectId id, DBTransaction dbTrans)
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        if (null == id)
            return null;
        return ceInternal(id, null, dbTrans);
    }

    /**
     * ceInternal( ObjectId , HashMap queryRefs, DBTransaction)
     *
     * Factory method creates a CreateProcessEventAuditDO object by searching for it
     * in the database using the passed ObjectID value as the primary key.
     *
     * @param id The ObjectId for the object.
     * @param queryRefs HashMap of available references.
     * @param dbTrans The current database transaction.
     * @return Created CreateProcessEventAuditDO object.
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
    protected static CreateProcessEventAuditDO ceInternal(ObjectId id, HashMap queryRefs, DBTransaction dbTrans )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {

        boolean isNewDO = true;

        if (null == id)
            return null;
        String cacheHandle = get_logicalDBName()+"."+id.toString();
        CreateProcessEventAuditDO ret = null;
        CreateProcessEventAuditDataStruct data = null;
        if (null == queryRefs) {
            queryRefs = new HashMap();
        }
        if (null!= dbTrans && null!= _tr_(dbTrans).getTransactionCache()) {
            ret = (CreateProcessEventAuditDO)_tr_(dbTrans).getTransactionCache().getDOByHandle(cacheHandle);
            isNewDO=false;
        } else if (queryRefs.containsKey(cacheHandle)) {
            ret = (CreateProcessEventAuditDO)queryRefs.get(cacheHandle);
            isNewDO=false;
        }
        if (ret==null) {
            ret = (CreateProcessEventAuditDO)createDO (id, dbTrans);
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
     * @return Created CreateProcessEventAuditDO object.
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
    protected static CreateProcessEventAuditDO ceInternal(ResultSet rs , DBTransaction dbTrans)
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        if ( null == rs )
            return null;
        CreateProcessEventAuditDO ret = null;
        if ( notUsingOId ) {
            ret = new CreateProcessEventAuditDO (dbTrans);
            ret.initFromResultSet( rs );
        } else {
            ret = new CreateProcessEventAuditDO ( rs, dbTrans );
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
     * @return Created CreateProcessEventAuditDO object.
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
    protected static CreateProcessEventAuditDO ceInternal(ResultSet rs , HashMap queryRefs, DBTransaction dbTrans)
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        if ( null == rs )
            return null;
        CreateProcessEventAuditDO ret = null;
        if ( notUsingOId ) {
            ret = new CreateProcessEventAuditDO (dbTrans);
            ret.set_refs(queryRefs);
            ret.initFromResultSet( rs );
        } else {
            if(queryRefs==null)
                queryRefs = new HashMap();
            BigDecimal tmpOid = rs.getBigDecimal(get_OIdColumnName());
            String cacheHandle = get_logicalDBName()+"."+tmpOid;
            if(queryRefs.containsKey(cacheHandle)) {
                ret = (CreateProcessEventAuditDO)queryRefs.get(cacheHandle);
                if (!ret.isLoaded()) {
                    ret.set_refs(queryRefs);
                    ret.initFromResultSet(rs);
                }
                return ret;
            }
            if (useOrderedWithTable != null){
            	ret = new CreateProcessEventAuditDO (new ObjectId(tmpOid),dbTrans);
            	ret.set_refs(queryRefs);
            	ret.initFromResultSet(rs);
            	//if(dbTrans!=null) dbTrans.lockDO(this);
            }else{
				ret = new CreateProcessEventAuditDO ( rs, queryRefs, dbTrans );
            }
        }
        return ret;
    }



    /**
     * ceInternal( RDBRow , DBTransaction)
     *
     * Factory method creates a CreateProcessEventAuditDO object by searching for it
     * in the database using the CreateProcessEventAuditDO.PrimaryKey value
     * in the passed RDBRow.
     *
     * @param row A row returned by QueryBuilder.getNextRow().
     * @param dbTrans The current database transaction.
     * @return Created CreateProcessEventAuditDO object.
     *
     * @exception DataObjectException
     *   If the RDBRow does not contain a CreateProcessEventAuditDO.PrimaryKey.
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static CreateProcessEventAuditDO ceInternal(RDBRow row, DBTransaction dbTrans )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        if ( null == row )
            return null;
        RDBColumnValue pk = null;
        try {
            pk = row.get( CreateProcessEventAuditDO.PrimaryKey );
            return ceInternal( pk, dbTrans );
        } catch ( Exception e ) {
            throw new DataObjectException("Cannot create CreateProcessEventAuditDO, row does not " +
                                          "contain CreateProcessEventAuditDO primary key." );
        }
    }




    /**
     * ceInternal( RDBColumnValue, DBTransaction )
     *
     * Factory method creates a CreateProcessEventAuditDO object by searching for it
     * in the database using the passed CreateProcessEventAuditDO.PrimaryKey.
     *
     * @param pk a PrimaryKey column value from a row that was returned by
     * QueryBuilder.getNextRow().
     * @param dbTrans The current database transaction.
     * @return Created CreateProcessEventAuditDO object.
     *
     * @exception DataObjectException
     *   If the RDBColumnValue does not contain a CreateProcessEventAuditDO.PrimaryKey.
     *   If the object is not found in the database.
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     * @exception SQLException
     *   If the database rejects the SQL generated to retrieve data
     *   for this object, or if the table contains a bad foreign key, etc.
     */
    protected static CreateProcessEventAuditDO ceInternal(RDBColumnValue pk, DBTransaction dbTrans )
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        if ( null == pk )
            return null;
        if ( ! pk.equals( CreateProcessEventAuditDO.PrimaryKey ) )
            throw new DataObjectException("Cannot create CreateProcessEventAuditDO, " +
                                          "RDBColumnValue is not CreateProcessEventAuditDO.PrimaryKey." );
        BigDecimal bd = null;
        try {
            bd = pk.getBigDecimal();
        } catch ( Exception e ) {
            throw new DataObjectException("Cannot create CreateProcessEventAuditDO, bad primary key." );
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
     * @return Created CreateProcessEventAuditDO object.
     *
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     */
    public static CreateProcessEventAuditDO createCopy( CreateProcessEventAuditDataStruct data, DBTransaction dbTrans )
    throws DatabaseManagerException, ObjectIdException {
        CreateProcessEventAuditDO ret = new CreateProcessEventAuditDO (true, dbTrans);
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
     * @return Created CreateProcessEventAuditDO object.
     *
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     */
    public static CreateProcessEventAuditDO createCopy( CreateProcessEventAuditDO orig )
    throws DatabaseManagerException, ObjectIdException {
        if (null == orig)
            return null;
        CreateProcessEventAuditDO ret = new CreateProcessEventAuditDO (true);
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
     * @return Created CreateProcessEventAuditDO object.
     *
     * @exception com.lutris.appserver.server.sql.ObjectIdException
     *   If an object's id can't be allocated for this object.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     */
    public static CreateProcessEventAuditDO createCopy( CreateProcessEventAuditDO orig, DBTransaction dbTrans )
    throws DatabaseManagerException, ObjectIdException {
        if (null == orig)
            return null;
        CreateProcessEventAuditDO ret = new CreateProcessEventAuditDO (true, dbTrans);
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
        return new CreateProcessEventAuditDO(oid, dbTrans);
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
     * @see CreateProcessEventAuditDO#get_Handle() get_Handle
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
     * @return <CODE>CreateProcessEventAuditDO</CODE>
     *   Object if one is found in cache, otherwise null.
     *
     * @see CreateProcessEventAuditDO#get_Handle() get_Handle
     */
    public CreateProcessEventAuditDO findTransactionCachedObjectByHandle( String cacheHandle ){
        if ( null == cacheHandle )
            return null;
        if(get_transaction()!=null && _tr_(get_transaction()).getTransactionCache()!= null)
            return (CreateProcessEventAuditDO)_tr_(get_transaction()).getTransactionCache().getDOByHandle( cacheHandle );
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
     * @return <CODE>CreateProcessEventAuditDataStruct</CODE>
     *   Object if one is found in cache, otherwise null.
     *
     * @see CreateProcessEventAuditDO#get_Handle() get_Handle
     */
    public static CreateProcessEventAuditDataStruct findCachedObjectByHandle( String cacheHandle ){
        if ( null == cacheHandle )
            return null;
        return ( CreateProcessEventAuditDataStruct ) cache.getDataStructByHandle( cacheHandle );
    }



    /**
     * Assigns the DataStruct of an existing DO to this DO.
     * Does not duplicate data. Just assigns the reference.
     *
     * @param orig The original DO.
     *
     */
    protected void makeIdentical( CreateProcessEventAuditDO orig ) {
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
                DODS.getLogChannel().write(Logger.DEBUG," CreateProcessEventAuditDO class\n :"+" can't set new version ");
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
            DODS.getLogChannel().write(Logger.DEBUG, " MakeReadOnly failed: Database: "+get_OriginDatabase()+" CreateProcessEventAuditDO class, oid: "+get_OId()+", version: "+get_Version()+" \n");
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
            DODS.getLogChannel().write(Logger.DEBUG, " MakeReadWrite failed: Database: "+get_OriginDatabase()+" CreateProcessEventAuditDO class, oid: "+get_OId()+", version: "+get_Version()+" \n");
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
            originalData = new CreateProcessEventAuditDataStruct();
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

        CreateProcessEventAuditDO _clone = new CreateProcessEventAuditDO (get_transaction());

        try {
            checkLoad();
            CreateProcessEventAuditDataStruct toClone = (null != get_Data())
                     ?(CreateProcessEventAuditDataStruct)get_Data()
                     :(CreateProcessEventAuditDataStruct)originalData_get();
            if (null != toClone) {
                _clone.set_Data(toClone.duplicate());
                ((CreateProcessEventAuditDataStruct)_clone.get_Data()).set_OId(((CreateProcessEventAuditDataStruct)_clone.originalData_get()).get_OId());
                ((CreateProcessEventAuditDataStruct)_clone.get_Data()).set_Version(0);
                changedFlags_set(true);
            }
        } catch (Exception e) {
            DODS.getLogChannel().write(Logger.DEBUG," cloneUnique failed: Database: "+get_OriginDatabase()+" CreateProcessEventAuditDO class, oid: "+get_Handle()+", version: "+get_Version()+" \n");
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
        ((CreateProcessEventAuditDataStruct)originalData).readOnly = true;
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

            changedFlags_set(false);
        }
    }

    public synchronized void executeUpdate(DBConnection conn)
        throws SQLException, DBRowUpdateException {
        if (dirty) {
            super.executeUpdate(conn);

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
                                        data =((CreateProcessEventAuditDataStruct)originalData).duplicate();
                                        set_Version(tempVersion);
                                        persistent=false;
                                        deleted=false;
                                        isDeletedFromDatabase=false;
                                        if (isAutoSave()) {
                            save(transaction,false);
                        }
                                }else{
                                        data =((CreateProcessEventAuditDataStruct)originalData).duplicate();
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
        originalData_set(new CreateProcessEventAuditDataStruct());
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
    private static CreateProcessEventAuditDO publicCreateExisting(String _dbName, BigDecimal _oid, HashMap _refs, DBTransaction dbt)
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        if (null == _oid)
            return null;
        if (null == _dbName)
            _dbName = get_logicalDBName();
        CreateProcessEventAuditQuery qry = new CreateProcessEventAuditQuery(dbt);
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
        return (obj instanceof CreateProcessEventAuditDO)
           && this.__the_handle.equals(((CreateProcessEventAuditDO)obj).__the_handle);
    }


    ////////////////////////// data member UTCTime

    /**
     * static final RDBColumn UTCTime for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn UTCTime = new RDBColumn( table, "UTCTime", true);

    private boolean changedUTCTime = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_UTCTIME = 0;
    static public final int UTCTime_MaxLength = 254;

    /**
     * Get UTCTime of the CreateProcessEventAudits.
     *
     * @return UTCTime of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getUTCTime ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getUTCTime();
    }


    /**
     * Get original UTCTime of the CreateProcessEventAudits.
     *
     * @return UTCTime of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String originalData_getUTCTime ()
    throws DataObjectException {
        checkLoad();
        return ((CreateProcessEventAuditDataStruct)originalData_get()).getUTCTime();
    }



    /**
     * Set UTCTime of the CreateProcessEventAudits.
     *
     * @param UTCTime of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setUTCTime ( String UTCTime )
    throws DataObjectException {
        _setUTCTime ( UTCTime );
    }
    
    /**
     * _setUTCTime is a protected method that is called by
     * setUTCTime if UTCTime is not part of
     * a multicolumn foreign key.
     *
     * @param UTCTime of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setUTCTime ( String UTCTime )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setUTCTime(markNewValue(get_DataStruct().getUTCTime(),  UTCTime, 0, UTCTime_MaxLength, false));
        changedUTCTime |= colChanged;
        if (changedUTCTime) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member TheType

    /**
     * static final RDBColumn TheType for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn TheType = new RDBColumn( table, "TheType", true);

    private boolean changedTheType = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_THETYPE = 1;
    

    /**
     * Get TheType of the CreateProcessEventAudits.
     *
     * @return TheType of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.eventaudit.data.EventTypeDO getTheType ()
    throws DataObjectException {
        checkLoad();
        
        if (null == get_DataStruct().getTheType()) {
            return null;
        }
        String qKey = get_logicalDBName()+"."+get_DataStruct().getTheType().toString();
        org.enhydra.shark.eventaudit.data.EventTypeDO ret = (org.enhydra.shark.eventaudit.data.EventTypeDO)getRefs(qKey);
        if(transaction!=null && ret!=null && ret.get_transaction()!=null) {
            if(!(ret.get_transaction()).equals(transaction))
                throw new DataObjectException ("Referenced DO doesn't belong this transaction ");
        } else if (null == ret) {
            try {
                ret = org.enhydra.shark.eventaudit.data.EventTypeDO.ceInternal(get_DataStruct().getTheType(), get_refs(), get_transaction());
                addRefs(qKey, ret);
            } catch (Exception e) {
                throw new DataObjectException("FIXME: should make proper log entry - didn't created ref", e); //FIXME: should make proper log entry
            }
        }
        return ret;
    }


    /**
     * Get BigDecimal value of TheType of the CreateProcessEventAudits.
     *
     * @return TheType of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public BigDecimal oid_getTheType ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getTheType();
    }

    /**
     * Get original TheType of the CreateProcessEventAudits.
     *
     * @return TheType of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.eventaudit.data.EventTypeDO originalData_getTheType ()
    throws DataObjectException {
        checkLoad();
        if (null ==((CreateProcessEventAuditDataStruct)originalData_get()).getTheType()) {
            return null;
        }
        String qKey = get_logicalDBName()+"."+((CreateProcessEventAuditDataStruct)originalData_get()).getTheType().toString();
        org.enhydra.shark.eventaudit.data.EventTypeDO ret = (org.enhydra.shark.eventaudit.data.EventTypeDO)getRefs(qKey);
        if(transaction!=null && ret!=null && ret.get_transaction()!=null) {
            if(!(ret.get_transaction()).equals(transaction))
                throw new DataObjectException ("Referenced DO doesn't belong this transaction ");
        } else if (null == ret) {
          try {
            ret = org.enhydra.shark.eventaudit.data.EventTypeDO.ceInternal(((CreateProcessEventAuditDataStruct)originalData_get()).getTheType(), get_refs(), get_transaction());
            addRefs(qKey, ret);
            } catch (Exception e) {
                throw new DataObjectException("FIXME: should make proper log entry - didn't created ref", e); //FIXME: should make proper log entry
            }
        }
        return ret;
    }



    /**
     * Set TheType of the CreateProcessEventAudits.
     *
     * @param TheType of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setTheType ( org.enhydra.shark.eventaudit.data.EventTypeDO TheType )
    throws DataObjectException {
        _setTheType ( TheType );
    }
    
    /**
     * _setTheType is a protected method that is called by
     * setTheType if TheType is not part of
     * a multicolumn foreign key.
     *
     * @param TheType of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setTheType ( org.enhydra.shark.eventaudit.data.EventTypeDO TheType )
    throws DataObjectException {
        if(transaction!=null && TheType!=null && TheType.get_transaction()!=null) {
            if(!TheType.get_transaction().equals(this.transaction))
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
        if (null != TheType) {
            bdNewOId = TheType.get_OId().toBigDecimal();
            String qKey = get_logicalDBName()+"."+ bdNewOId.toString();
            addRefs(qKey, TheType);
        }
        //
        if (data.isEmpty)
            data.isEmpty = false;
        data.setTheType(markNewValue(get_DataStruct().getTheType(),  bdNewOId));
        changedTheType |= colChanged;
        if (changedTheType) {
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
     * Set TheType of the CreateProcessEventAudits.
     * 
     * @param TheType value of TheType of the CreateProcessEventAudits as a BigDecimal value.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void oid_setTheType(BigDecimal TheType)
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setTheType(markNewValue(get_DataStruct().getTheType(), TheType));
        changedTheType |= colChanged;
        if (changedTheType) {
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
     * Set TheType of the CreateProcessEventAudits.
     * 
     * @param TheType value of TheType of the CreateProcessEventAudits as a ObjectId value.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void oid_setTheType(ObjectId TheType)
    throws DataObjectException {
        if(TheType != null)
            oid_setTheType(TheType.toBigDecimal());
        else
            oid_setTheType((BigDecimal)null);    
    }

    /**
     * Set TheType of the CreateProcessEventAudits.
     * 
     * @param TheType value of TheType of the CreateProcessEventAudits as a String value.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void oid_setTheType(String TheType)
    throws DataObjectException {
        if(TheType != null)
            oid_setTheType(new BigDecimal(TheType));
        else
            oid_setTheType((BigDecimal)null);    
    }


    ////////////////////////// data member ProcessId

    /**
     * static final RDBColumn ProcessId for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn ProcessId = new RDBColumn( table, "ProcessId", true);

    private boolean changedProcessId = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_PROCESSID = 2;
    static public final int ProcessId_MaxLength = 100;

    /**
     * Get ProcessId of the CreateProcessEventAudits.
     *
     * @return ProcessId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessId ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getProcessId();
    }


    /**
     * Get original ProcessId of the CreateProcessEventAudits.
     *
     * @return ProcessId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String originalData_getProcessId ()
    throws DataObjectException {
        checkLoad();
        return ((CreateProcessEventAuditDataStruct)originalData_get()).getProcessId();
    }



    /**
     * Set ProcessId of the CreateProcessEventAudits.
     *
     * @param ProcessId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessId ( String ProcessId )
    throws DataObjectException {
        _setProcessId ( ProcessId );
    }
    
    /**
     * _setProcessId is a protected method that is called by
     * setProcessId if ProcessId is not part of
     * a multicolumn foreign key.
     *
     * @param ProcessId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setProcessId ( String ProcessId )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setProcessId(markNewValue(get_DataStruct().getProcessId(),  ProcessId, 0, ProcessId_MaxLength, false));
        changedProcessId |= colChanged;
        if (changedProcessId) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member ProcessName

    /**
     * static final RDBColumn ProcessName for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn ProcessName = new RDBColumn( table, "ProcessName", false);

    private boolean changedProcessName = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_PROCESSNAME = 3;
    static public final int ProcessName_MaxLength = 254;

    /**
     * Get ProcessName of the CreateProcessEventAudits.
     *
     * @return ProcessName of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessName ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getProcessName();
    }


    /**
     * Get original ProcessName of the CreateProcessEventAudits.
     *
     * @return ProcessName of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String originalData_getProcessName ()
    throws DataObjectException {
        checkLoad();
        return ((CreateProcessEventAuditDataStruct)originalData_get()).getProcessName();
    }



    /**
     * Set ProcessName of the CreateProcessEventAudits.
     *
     * @param ProcessName of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessName ( String ProcessName )
    throws DataObjectException {
        _setProcessName ( ProcessName );
    }
    
    /**
     * _setProcessName is a protected method that is called by
     * setProcessName if ProcessName is not part of
     * a multicolumn foreign key.
     *
     * @param ProcessName of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setProcessName ( String ProcessName )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setProcessName(markNewValue(get_DataStruct().getProcessName(),  ProcessName, 0, ProcessName_MaxLength, true));
        changedProcessName |= colChanged;
        if (changedProcessName) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member ProcessDefinitionName

    /**
     * static final RDBColumn ProcessDefinitionName for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn ProcessDefinitionName = new RDBColumn( table, "ProcessDefinitionName", true);

    private boolean changedProcessDefinitionName = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_PROCESSDEFINITIONNAME = 4;
    static public final int ProcessDefinitionName_MaxLength = 200;

    /**
     * Get ProcessDefinitionName of the CreateProcessEventAudits.
     *
     * @return ProcessDefinitionName of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessDefinitionName ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getProcessDefinitionName();
    }


    /**
     * Get original ProcessDefinitionName of the CreateProcessEventAudits.
     *
     * @return ProcessDefinitionName of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String originalData_getProcessDefinitionName ()
    throws DataObjectException {
        checkLoad();
        return ((CreateProcessEventAuditDataStruct)originalData_get()).getProcessDefinitionName();
    }



    /**
     * Set ProcessDefinitionName of the CreateProcessEventAudits.
     *
     * @param ProcessDefinitionName of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessDefinitionName ( String ProcessDefinitionName )
    throws DataObjectException {
        _setProcessDefinitionName ( ProcessDefinitionName );
    }
    
    /**
     * _setProcessDefinitionName is a protected method that is called by
     * setProcessDefinitionName if ProcessDefinitionName is not part of
     * a multicolumn foreign key.
     *
     * @param ProcessDefinitionName of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setProcessDefinitionName ( String ProcessDefinitionName )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setProcessDefinitionName(markNewValue(get_DataStruct().getProcessDefinitionName(),  ProcessDefinitionName, 0, ProcessDefinitionName_MaxLength, false));
        changedProcessDefinitionName |= colChanged;
        if (changedProcessDefinitionName) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member ProcessDefinitionVersion

    /**
     * static final RDBColumn ProcessDefinitionVersion for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn ProcessDefinitionVersion = new RDBColumn( table, "ProcessDefinitionVersion", true);

    private boolean changedProcessDefinitionVersion = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_PROCESSDEFINITIONVERSION = 5;
    static public final int ProcessDefinitionVersion_MaxLength = 20;

    /**
     * Get ProcessDefinitionVersion of the CreateProcessEventAudits.
     *
     * @return ProcessDefinitionVersion of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessDefinitionVersion ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getProcessDefinitionVersion();
    }


    /**
     * Get original ProcessDefinitionVersion of the CreateProcessEventAudits.
     *
     * @return ProcessDefinitionVersion of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String originalData_getProcessDefinitionVersion ()
    throws DataObjectException {
        checkLoad();
        return ((CreateProcessEventAuditDataStruct)originalData_get()).getProcessDefinitionVersion();
    }



    /**
     * Set ProcessDefinitionVersion of the CreateProcessEventAudits.
     *
     * @param ProcessDefinitionVersion of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessDefinitionVersion ( String ProcessDefinitionVersion )
    throws DataObjectException {
        _setProcessDefinitionVersion ( ProcessDefinitionVersion );
    }
    
    /**
     * _setProcessDefinitionVersion is a protected method that is called by
     * setProcessDefinitionVersion if ProcessDefinitionVersion is not part of
     * a multicolumn foreign key.
     *
     * @param ProcessDefinitionVersion of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setProcessDefinitionVersion ( String ProcessDefinitionVersion )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setProcessDefinitionVersion(markNewValue(get_DataStruct().getProcessDefinitionVersion(),  ProcessDefinitionVersion, 0, ProcessDefinitionVersion_MaxLength, false));
        changedProcessDefinitionVersion |= colChanged;
        if (changedProcessDefinitionVersion) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member ProcessDefinitionId

    /**
     * static final RDBColumn ProcessDefinitionId for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn ProcessDefinitionId = new RDBColumn( table, "ProcessDefinitionId", true);

    private boolean changedProcessDefinitionId = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_PROCESSDEFINITIONID = 6;
    static public final int ProcessDefinitionId_MaxLength = 90;

    /**
     * Get ProcessDefinitionId of the CreateProcessEventAudits.
     *
     * @return ProcessDefinitionId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessDefinitionId ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getProcessDefinitionId();
    }


    /**
     * Get original ProcessDefinitionId of the CreateProcessEventAudits.
     *
     * @return ProcessDefinitionId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String originalData_getProcessDefinitionId ()
    throws DataObjectException {
        checkLoad();
        return ((CreateProcessEventAuditDataStruct)originalData_get()).getProcessDefinitionId();
    }



    /**
     * Set ProcessDefinitionId of the CreateProcessEventAudits.
     *
     * @param ProcessDefinitionId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessDefinitionId ( String ProcessDefinitionId )
    throws DataObjectException {
        _setProcessDefinitionId ( ProcessDefinitionId );
    }
    
    /**
     * _setProcessDefinitionId is a protected method that is called by
     * setProcessDefinitionId if ProcessDefinitionId is not part of
     * a multicolumn foreign key.
     *
     * @param ProcessDefinitionId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setProcessDefinitionId ( String ProcessDefinitionId )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setProcessDefinitionId(markNewValue(get_DataStruct().getProcessDefinitionId(),  ProcessDefinitionId, 0, ProcessDefinitionId_MaxLength, false));
        changedProcessDefinitionId |= colChanged;
        if (changedProcessDefinitionId) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member PackageId

    /**
     * static final RDBColumn PackageId for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn PackageId = new RDBColumn( table, "PackageId", true);

    private boolean changedPackageId = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_PACKAGEID = 7;
    static public final int PackageId_MaxLength = 90;

    /**
     * Get PackageId of the CreateProcessEventAudits.
     *
     * @return PackageId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPackageId ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getPackageId();
    }


    /**
     * Get original PackageId of the CreateProcessEventAudits.
     *
     * @return PackageId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String originalData_getPackageId ()
    throws DataObjectException {
        checkLoad();
        return ((CreateProcessEventAuditDataStruct)originalData_get()).getPackageId();
    }



    /**
     * Set PackageId of the CreateProcessEventAudits.
     *
     * @param PackageId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPackageId ( String PackageId )
    throws DataObjectException {
        _setPackageId ( PackageId );
    }
    
    /**
     * _setPackageId is a protected method that is called by
     * setPackageId if PackageId is not part of
     * a multicolumn foreign key.
     *
     * @param PackageId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setPackageId ( String PackageId )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setPackageId(markNewValue(get_DataStruct().getPackageId(),  PackageId, 0, PackageId_MaxLength, false));
        changedPackageId |= colChanged;
        if (changedPackageId) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member PActivityId

    /**
     * static final RDBColumn PActivityId for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn PActivityId = new RDBColumn( table, "PActivityId", false);

    private boolean changedPActivityId = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_PACTIVITYID = 8;
    static public final int PActivityId_MaxLength = 100;

    /**
     * Get PActivityId of the CreateProcessEventAudits.
     *
     * @return PActivityId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPActivityId ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getPActivityId();
    }


    /**
     * Get original PActivityId of the CreateProcessEventAudits.
     *
     * @return PActivityId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String originalData_getPActivityId ()
    throws DataObjectException {
        checkLoad();
        return ((CreateProcessEventAuditDataStruct)originalData_get()).getPActivityId();
    }



    /**
     * Set PActivityId of the CreateProcessEventAudits.
     *
     * @param PActivityId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPActivityId ( String PActivityId )
    throws DataObjectException {
        _setPActivityId ( PActivityId );
    }
    
    /**
     * _setPActivityId is a protected method that is called by
     * setPActivityId if PActivityId is not part of
     * a multicolumn foreign key.
     *
     * @param PActivityId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setPActivityId ( String PActivityId )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setPActivityId(markNewValue(get_DataStruct().getPActivityId(),  PActivityId, 0, PActivityId_MaxLength, true));
        changedPActivityId |= colChanged;
        if (changedPActivityId) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member PProcessId

    /**
     * static final RDBColumn PProcessId for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn PProcessId = new RDBColumn( table, "PProcessId", false);

    private boolean changedPProcessId = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_PPROCESSID = 9;
    static public final int PProcessId_MaxLength = 100;

    /**
     * Get PProcessId of the CreateProcessEventAudits.
     *
     * @return PProcessId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPProcessId ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getPProcessId();
    }


    /**
     * Get original PProcessId of the CreateProcessEventAudits.
     *
     * @return PProcessId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String originalData_getPProcessId ()
    throws DataObjectException {
        checkLoad();
        return ((CreateProcessEventAuditDataStruct)originalData_get()).getPProcessId();
    }



    /**
     * Set PProcessId of the CreateProcessEventAudits.
     *
     * @param PProcessId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPProcessId ( String PProcessId )
    throws DataObjectException {
        _setPProcessId ( PProcessId );
    }
    
    /**
     * _setPProcessId is a protected method that is called by
     * setPProcessId if PProcessId is not part of
     * a multicolumn foreign key.
     *
     * @param PProcessId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setPProcessId ( String PProcessId )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setPProcessId(markNewValue(get_DataStruct().getPProcessId(),  PProcessId, 0, PProcessId_MaxLength, true));
        changedPProcessId |= colChanged;
        if (changedPProcessId) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member PProcessName

    /**
     * static final RDBColumn PProcessName for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn PProcessName = new RDBColumn( table, "PProcessName", false);

    private boolean changedPProcessName = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_PPROCESSNAME = 10;
    static public final int PProcessName_MaxLength = 254;

    /**
     * Get PProcessName of the CreateProcessEventAudits.
     *
     * @return PProcessName of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPProcessName ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getPProcessName();
    }


    /**
     * Get original PProcessName of the CreateProcessEventAudits.
     *
     * @return PProcessName of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String originalData_getPProcessName ()
    throws DataObjectException {
        checkLoad();
        return ((CreateProcessEventAuditDataStruct)originalData_get()).getPProcessName();
    }



    /**
     * Set PProcessName of the CreateProcessEventAudits.
     *
     * @param PProcessName of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPProcessName ( String PProcessName )
    throws DataObjectException {
        _setPProcessName ( PProcessName );
    }
    
    /**
     * _setPProcessName is a protected method that is called by
     * setPProcessName if PProcessName is not part of
     * a multicolumn foreign key.
     *
     * @param PProcessName of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setPProcessName ( String PProcessName )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setPProcessName(markNewValue(get_DataStruct().getPProcessName(),  PProcessName, 0, PProcessName_MaxLength, true));
        changedPProcessName |= colChanged;
        if (changedPProcessName) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member PProcessDefinitionName

    /**
     * static final RDBColumn PProcessDefinitionName for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn PProcessDefinitionName = new RDBColumn( table, "PProcessDefinitionName", false);

    private boolean changedPProcessDefinitionName = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_PPROCESSDEFINITIONNAME = 11;
    static public final int PProcessDefinitionName_MaxLength = 200;

    /**
     * Get PProcessDefinitionName of the CreateProcessEventAudits.
     *
     * @return PProcessDefinitionName of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPProcessDefinitionName ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getPProcessDefinitionName();
    }


    /**
     * Get original PProcessDefinitionName of the CreateProcessEventAudits.
     *
     * @return PProcessDefinitionName of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String originalData_getPProcessDefinitionName ()
    throws DataObjectException {
        checkLoad();
        return ((CreateProcessEventAuditDataStruct)originalData_get()).getPProcessDefinitionName();
    }



    /**
     * Set PProcessDefinitionName of the CreateProcessEventAudits.
     *
     * @param PProcessDefinitionName of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPProcessDefinitionName ( String PProcessDefinitionName )
    throws DataObjectException {
        _setPProcessDefinitionName ( PProcessDefinitionName );
    }
    
    /**
     * _setPProcessDefinitionName is a protected method that is called by
     * setPProcessDefinitionName if PProcessDefinitionName is not part of
     * a multicolumn foreign key.
     *
     * @param PProcessDefinitionName of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setPProcessDefinitionName ( String PProcessDefinitionName )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setPProcessDefinitionName(markNewValue(get_DataStruct().getPProcessDefinitionName(),  PProcessDefinitionName, 0, PProcessDefinitionName_MaxLength, true));
        changedPProcessDefinitionName |= colChanged;
        if (changedPProcessDefinitionName) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member PProcessDefinitionVersion

    /**
     * static final RDBColumn PProcessDefinitionVersion for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn PProcessDefinitionVersion = new RDBColumn( table, "PProcessDefinitionVersion", false);

    private boolean changedPProcessDefinitionVersion = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_PPROCESSDEFINITIONVERSION = 12;
    static public final int PProcessDefinitionVersion_MaxLength = 20;

    /**
     * Get PProcessDefinitionVersion of the CreateProcessEventAudits.
     *
     * @return PProcessDefinitionVersion of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPProcessDefinitionVersion ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getPProcessDefinitionVersion();
    }


    /**
     * Get original PProcessDefinitionVersion of the CreateProcessEventAudits.
     *
     * @return PProcessDefinitionVersion of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String originalData_getPProcessDefinitionVersion ()
    throws DataObjectException {
        checkLoad();
        return ((CreateProcessEventAuditDataStruct)originalData_get()).getPProcessDefinitionVersion();
    }



    /**
     * Set PProcessDefinitionVersion of the CreateProcessEventAudits.
     *
     * @param PProcessDefinitionVersion of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPProcessDefinitionVersion ( String PProcessDefinitionVersion )
    throws DataObjectException {
        _setPProcessDefinitionVersion ( PProcessDefinitionVersion );
    }
    
    /**
     * _setPProcessDefinitionVersion is a protected method that is called by
     * setPProcessDefinitionVersion if PProcessDefinitionVersion is not part of
     * a multicolumn foreign key.
     *
     * @param PProcessDefinitionVersion of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setPProcessDefinitionVersion ( String PProcessDefinitionVersion )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setPProcessDefinitionVersion(markNewValue(get_DataStruct().getPProcessDefinitionVersion(),  PProcessDefinitionVersion, 0, PProcessDefinitionVersion_MaxLength, true));
        changedPProcessDefinitionVersion |= colChanged;
        if (changedPProcessDefinitionVersion) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member PActivityDefinitionId

    /**
     * static final RDBColumn PActivityDefinitionId for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn PActivityDefinitionId = new RDBColumn( table, "PActivityDefinitionId", false);

    private boolean changedPActivityDefinitionId = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_PACTIVITYDEFINITIONID = 13;
    static public final int PActivityDefinitionId_MaxLength = 90;

    /**
     * Get PActivityDefinitionId of the CreateProcessEventAudits.
     *
     * @return PActivityDefinitionId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPActivityDefinitionId ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getPActivityDefinitionId();
    }


    /**
     * Get original PActivityDefinitionId of the CreateProcessEventAudits.
     *
     * @return PActivityDefinitionId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String originalData_getPActivityDefinitionId ()
    throws DataObjectException {
        checkLoad();
        return ((CreateProcessEventAuditDataStruct)originalData_get()).getPActivityDefinitionId();
    }



    /**
     * Set PActivityDefinitionId of the CreateProcessEventAudits.
     *
     * @param PActivityDefinitionId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPActivityDefinitionId ( String PActivityDefinitionId )
    throws DataObjectException {
        _setPActivityDefinitionId ( PActivityDefinitionId );
    }
    
    /**
     * _setPActivityDefinitionId is a protected method that is called by
     * setPActivityDefinitionId if PActivityDefinitionId is not part of
     * a multicolumn foreign key.
     *
     * @param PActivityDefinitionId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setPActivityDefinitionId ( String PActivityDefinitionId )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setPActivityDefinitionId(markNewValue(get_DataStruct().getPActivityDefinitionId(),  PActivityDefinitionId, 0, PActivityDefinitionId_MaxLength, true));
        changedPActivityDefinitionId |= colChanged;
        if (changedPActivityDefinitionId) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member PActivitySetDefinitionId

    /**
     * static final RDBColumn PActivitySetDefinitionId for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn PActivitySetDefinitionId = new RDBColumn( table, "PActivitySetDefinitionId", false);

    private boolean changedPActivitySetDefinitionId = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_PACTIVITYSETDEFINITIONID = 14;
    static public final int PActivitySetDefinitionId_MaxLength = 90;

    /**
     * Get PActivitySetDefinitionId of the CreateProcessEventAudits.
     *
     * @return PActivitySetDefinitionId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPActivitySetDefinitionId ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getPActivitySetDefinitionId();
    }


    /**
     * Get original PActivitySetDefinitionId of the CreateProcessEventAudits.
     *
     * @return PActivitySetDefinitionId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String originalData_getPActivitySetDefinitionId ()
    throws DataObjectException {
        checkLoad();
        return ((CreateProcessEventAuditDataStruct)originalData_get()).getPActivitySetDefinitionId();
    }



    /**
     * Set PActivitySetDefinitionId of the CreateProcessEventAudits.
     *
     * @param PActivitySetDefinitionId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPActivitySetDefinitionId ( String PActivitySetDefinitionId )
    throws DataObjectException {
        _setPActivitySetDefinitionId ( PActivitySetDefinitionId );
    }
    
    /**
     * _setPActivitySetDefinitionId is a protected method that is called by
     * setPActivitySetDefinitionId if PActivitySetDefinitionId is not part of
     * a multicolumn foreign key.
     *
     * @param PActivitySetDefinitionId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setPActivitySetDefinitionId ( String PActivitySetDefinitionId )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setPActivitySetDefinitionId(markNewValue(get_DataStruct().getPActivitySetDefinitionId(),  PActivitySetDefinitionId, 0, PActivitySetDefinitionId_MaxLength, true));
        changedPActivitySetDefinitionId |= colChanged;
        if (changedPActivitySetDefinitionId) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member PProcessDefinitionId

    /**
     * static final RDBColumn PProcessDefinitionId for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn PProcessDefinitionId = new RDBColumn( table, "PProcessDefinitionId", false);

    private boolean changedPProcessDefinitionId = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_PPROCESSDEFINITIONID = 15;
    static public final int PProcessDefinitionId_MaxLength = 90;

    /**
     * Get PProcessDefinitionId of the CreateProcessEventAudits.
     *
     * @return PProcessDefinitionId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPProcessDefinitionId ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getPProcessDefinitionId();
    }


    /**
     * Get original PProcessDefinitionId of the CreateProcessEventAudits.
     *
     * @return PProcessDefinitionId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String originalData_getPProcessDefinitionId ()
    throws DataObjectException {
        checkLoad();
        return ((CreateProcessEventAuditDataStruct)originalData_get()).getPProcessDefinitionId();
    }



    /**
     * Set PProcessDefinitionId of the CreateProcessEventAudits.
     *
     * @param PProcessDefinitionId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPProcessDefinitionId ( String PProcessDefinitionId )
    throws DataObjectException {
        _setPProcessDefinitionId ( PProcessDefinitionId );
    }
    
    /**
     * _setPProcessDefinitionId is a protected method that is called by
     * setPProcessDefinitionId if PProcessDefinitionId is not part of
     * a multicolumn foreign key.
     *
     * @param PProcessDefinitionId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setPProcessDefinitionId ( String PProcessDefinitionId )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setPProcessDefinitionId(markNewValue(get_DataStruct().getPProcessDefinitionId(),  PProcessDefinitionId, 0, PProcessDefinitionId_MaxLength, true));
        changedPProcessDefinitionId |= colChanged;
        if (changedPProcessDefinitionId) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member PPackageId

    /**
     * static final RDBColumn PPackageId for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn PPackageId = new RDBColumn( table, "PPackageId", false);

    private boolean changedPPackageId = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_PPACKAGEID = 16;
    static public final int PPackageId_MaxLength = 90;

    /**
     * Get PPackageId of the CreateProcessEventAudits.
     *
     * @return PPackageId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPPackageId ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getPPackageId();
    }


    /**
     * Get original PPackageId of the CreateProcessEventAudits.
     *
     * @return PPackageId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String originalData_getPPackageId ()
    throws DataObjectException {
        checkLoad();
        return ((CreateProcessEventAuditDataStruct)originalData_get()).getPPackageId();
    }



    /**
     * Set PPackageId of the CreateProcessEventAudits.
     *
     * @param PPackageId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPPackageId ( String PPackageId )
    throws DataObjectException {
        _setPPackageId ( PPackageId );
    }
    
    /**
     * _setPPackageId is a protected method that is called by
     * setPPackageId if PPackageId is not part of
     * a multicolumn foreign key.
     *
     * @param PPackageId of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setPPackageId ( String PPackageId )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setPPackageId(markNewValue(get_DataStruct().getPPackageId(),  PPackageId, 0, PPackageId_MaxLength, true));
        changedPPackageId |= colChanged;
        if (changedPPackageId) {
            if (autoSaveAllowed&&isAutoSave()&&null != transaction) {
                try {
                    save(transaction,false);
                } catch (Exception ex) {
                    throw new DataObjectException("Error during transaction's writting data into database",ex);
                }
            }
        }
    }




    ////////////////////////// data member CNT

    /**
     * static final RDBColumn CNT for use with QueryBuilder.
     * See RDBColumn PrimaryKey at the top of this file for usage example.
     */
    static public final RDBColumn CNT = new RDBColumn( table, "CNT", true);

    private boolean changedCNT = false;

    /**
     * Use for query caching.
     */
    static public final int COLUMN_CNT = 17;
    

    /**
     * Get CNT of the CreateProcessEventAudits.
     *
     * @return CNT of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public java.math.BigDecimal getCNT ()
    throws DataObjectException {
        checkLoad();
        
        return get_DataStruct().getCNT();
    }


    /**
     * Get original CNT of the CreateProcessEventAudits.
     *
     * @return CNT of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public java.math.BigDecimal originalData_getCNT ()
    throws DataObjectException {
        checkLoad();
        return ((CreateProcessEventAuditDataStruct)originalData_get()).getCNT();
    }



    /**
     * Set CNT of the CreateProcessEventAudits.
     *
     * @param CNT of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setCNT ( java.math.BigDecimal CNT )
    throws DataObjectException {
        _setCNT ( CNT );
    }
    
    /**
     * _setCNT is a protected method that is called by
     * setCNT if CNT is not part of
     * a multicolumn foreign key.
     *
     * @param CNT of the CreateProcessEventAudits.
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    protected void _setCNT ( java.math.BigDecimal CNT )
    throws DataObjectException {
        checkLoad();
        
        try {
            checkDup();
        } catch (Exception e) {
            throw new DataObjectException ("Coudn't duplicate DataStruct:", e);
        }
        if (data.isEmpty)
            data.isEmpty = false;
        data.setCNT(markNewValue(get_DataStruct().getCNT(),  CNT));
        changedCNT |= colChanged;
        if (changedCNT) {
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
        case COLUMN_UTCTIME:
                    
                     return QueryBuilder.compare(getUTCTime(),cond.getValue(),cond.getOperator());
        case COLUMN_THETYPE:
                    
                    if ((getTheType()!=null)&& (getTheType() instanceof CoreDO)) {
                     CoreDataStruct xDataStruct = (CoreDataStruct)getTheType().get_DataStruct(); 
                     return QueryBuilder.compare(xDataStruct,cond.getValue(),cond.getOperator());
                    } 
                    else
                    
                     return QueryBuilder.compare(getTheType(),cond.getValue(),cond.getOperator());
        case COLUMN_PROCESSID:
                    
                     return QueryBuilder.compare(getProcessId(),cond.getValue(),cond.getOperator());
        case COLUMN_PROCESSNAME:
                    
                     return QueryBuilder.compare(getProcessName(),cond.getValue(),cond.getOperator());
        case COLUMN_PROCESSDEFINITIONNAME:
                    
                     return QueryBuilder.compare(getProcessDefinitionName(),cond.getValue(),cond.getOperator());
        case COLUMN_PROCESSDEFINITIONVERSION:
                    
                     return QueryBuilder.compare(getProcessDefinitionVersion(),cond.getValue(),cond.getOperator());
        case COLUMN_PROCESSDEFINITIONID:
                    
                     return QueryBuilder.compare(getProcessDefinitionId(),cond.getValue(),cond.getOperator());
        case COLUMN_PACKAGEID:
                    
                     return QueryBuilder.compare(getPackageId(),cond.getValue(),cond.getOperator());
        case COLUMN_PACTIVITYID:
                    
                     return QueryBuilder.compare(getPActivityId(),cond.getValue(),cond.getOperator());
        case COLUMN_PPROCESSID:
                    
                     return QueryBuilder.compare(getPProcessId(),cond.getValue(),cond.getOperator());
        case COLUMN_PPROCESSNAME:
                    
                     return QueryBuilder.compare(getPProcessName(),cond.getValue(),cond.getOperator());
        case COLUMN_PPROCESSDEFINITIONNAME:
                    
                     return QueryBuilder.compare(getPProcessDefinitionName(),cond.getValue(),cond.getOperator());
        case COLUMN_PPROCESSDEFINITIONVERSION:
                    
                     return QueryBuilder.compare(getPProcessDefinitionVersion(),cond.getValue(),cond.getOperator());
        case COLUMN_PACTIVITYDEFINITIONID:
                    
                     return QueryBuilder.compare(getPActivityDefinitionId(),cond.getValue(),cond.getOperator());
        case COLUMN_PACTIVITYSETDEFINITIONID:
                    
                     return QueryBuilder.compare(getPActivitySetDefinitionId(),cond.getValue(),cond.getOperator());
        case COLUMN_PPROCESSDEFINITIONID:
                    
                     return QueryBuilder.compare(getPProcessDefinitionId(),cond.getValue(),cond.getOperator());
        case COLUMN_PPACKAGEID:
                    
                     return QueryBuilder.compare(getPPackageId(),cond.getValue(),cond.getOperator());
        case COLUMN_CNT:
                    
                     return QueryBuilder.compare(getCNT(),cond.getValue(),cond.getOperator());
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
     * CreateProcessEventAuditDO and the corresponding Query class.
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
     * CreateProcessEventAuditDO.createTransaction().
     *
     * The CreateProcessEventAuditDO save() and delete() methods use this method.
     *
     * @return A DBTransaction object to use with the CreateProcessEventAuditDO class.
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
                      "CreateProcessEventAuditDO.logicalDbName='" + get_logicalDBName() + "'.  "+
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
     * access the 'CreateProcessEventAudits' table should be created using 
     * CreateProcessEventAuditDO.createQuery().
     *
     * The Query class corresponding to CreateProcessEventAuditDO uses this method.
     *
     * @param trans DBTransaction
     * @return A DBQuery object to use in accessing the 'CreateProcessEventAudits' table.
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
    protected CreateProcessEventAuditDO(ResultSet rs)
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        super(rs);
        initFromResultSet( rs );
        originDatabase = get_logicalDBName();
        get_DataStruct().set_Database(originDatabase);
        set_OId(new ObjectId(rs.getBigDecimal(get_OIdColumnName())));
    	if ( versioning )
       	    set_Version(rs.getInt(get_versionColumnName()));
        if(isTransactionCheck()) {
            DODS.getLogChannel().write(Logger.WARNING, "DO without transaction context is created : Database: "+get_OriginDatabase()+" CreateProcessEventAuditDO class, oid: "+get_Handle()+", version: "+get_Version()+" \n");
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
    protected CreateProcessEventAuditDO(ResultSet rs, HashMap queryRefs)
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
    protected CreateProcessEventAuditDO(ResultSet rs, HashMap queryRefs, DBTransaction dbTrans)
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
    protected CreateProcessEventAuditDO(ResultSet rs, DBTransaction dbTrans)
    throws SQLException, ObjectIdException, DataObjectException, DatabaseManagerException {
        this(rs, null, dbTrans);
    }
    
    



    /**
     * while in initFromResultSet, auto save can't be allowed
     */
    private boolean autoSaveAllowed = true;

    /**
     * initFromResultSet initializes the data members of CreateProcessEventAudits.
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
            originalData = new CreateProcessEventAuditDataStruct ();
	get_DataStruct().isEmpty = false; 
        // writeMemberStuff uses the ResultSetExtraction.template
        // to build up the value for this tag:
        // the value is a series of calls to the DO set methods.
        
        if ( versioning )set_Version(rs.getInt(get_versionColumnName()));
         setUTCTime( rs.getString( "UTCTime"  ) );
          oid_setTheType( rs.getBigDecimal( "TheType") );
         setProcessId( rs.getString( "ProcessId"  ) );
         setProcessName( rs.getString( "ProcessName"  ) );
         setProcessDefinitionName( rs.getString( "ProcessDefinitionName"  ) );
         setProcessDefinitionVersion( rs.getString( "ProcessDefinitionVersion"  ) );
         setProcessDefinitionId( rs.getString( "ProcessDefinitionId"  ) );
         setPackageId( rs.getString( "PackageId"  ) );
         setPActivityId( rs.getString( "PActivityId"  ) );
         setPProcessId( rs.getString( "PProcessId"  ) );
         setPProcessName( rs.getString( "PProcessName"  ) );
         setPProcessDefinitionName( rs.getString( "PProcessDefinitionName"  ) );
         setPProcessDefinitionVersion( rs.getString( "PProcessDefinitionVersion"  ) );
         setPActivityDefinitionId( rs.getString( "PActivityDefinitionId"  ) );
         setPActivitySetDefinitionId( rs.getString( "PActivitySetDefinitionId"  ) );
         setPProcessDefinitionId( rs.getString( "PProcessDefinitionId"  ) );
         setPPackageId( rs.getString( "PPackageId"  ) );
         setCNT( rs.getBigDecimal( "CNT"  ) );

        get_DataStruct().isEmpty = false;
        setPersistent(true);
        markClean();
        syncStructs(false);
//        refs = null;
        autoSaveAllowed = true;
    }

    	private static void initColumnsNameString(Boolean para){
    	if(para==null){
    		columnsNameString="CreateProcessEventAudits.*";
    	}else{
			columnsNameString = "";
   		String tableName;
			if (para.booleanValue()){
				tableName="@T@_CreateProcessEventAudits_@@.";
			}else{
				tableName="@F@_CreateProcessEventAudits_@@.";
			}
			String oidStr = CreateProcessEventAuditDO.get_OIdColumnName();
			String verStr = CreateProcessEventAuditDO.get_versionColumnName();
			columnsNameString = tableName+oidStr+", " ;
			if ( versioning ) {
				columnsNameString +=tableName+verStr+", ";
			}

			columnsNameString += tableName + "UTCTime, ";

			columnsNameString += tableName + "TheType, ";

			columnsNameString += tableName + "ProcessId, ";

			columnsNameString += tableName + "ProcessName, ";

			columnsNameString += tableName + "ProcessDefinitionName, ";

			columnsNameString += tableName + "ProcessDefinitionVersion, ";

			columnsNameString += tableName + "ProcessDefinitionId, ";

			columnsNameString += tableName + "PackageId, ";

			columnsNameString += tableName + "PActivityId, ";

			columnsNameString += tableName + "PProcessId, ";

			columnsNameString += tableName + "PProcessName, ";

			columnsNameString += tableName + "PProcessDefinitionName, ";

			columnsNameString += tableName + "PProcessDefinitionVersion, ";

			columnsNameString += tableName + "PActivityDefinitionId, ";

			columnsNameString += tableName + "PActivitySetDefinitionId, ";

			columnsNameString += tableName + "PProcessDefinitionId, ";

			columnsNameString += tableName + "PPackageId, ";

			columnsNameString += tableName + "CNT, ";

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
            "insert into CreateProcessEventAudits ( UTCTime, TheType, ProcessId, ProcessName, ProcessDefinitionName, ProcessDefinitionVersion, ProcessDefinitionId, PackageId, PActivityId, PProcessId, PProcessName, PProcessDefinitionName, PProcessDefinitionVersion, PActivityDefinitionId, PActivitySetDefinitionId, PProcessDefinitionId, PPackageId, CNT, " + get_OIdColumnName() + ", " + get_versionColumnName() + " )" +
            "values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");


        param = new int[1]; param[0] = 1;
        // writeMemberStuff uses the JDBCsetCalls.template
        // to build up the value for this tag:
        // the value is a series of calls to setPrepStmtParam_TYPE methods.
        // Those methods are defined in GenericDO.
        try {
            setPrepStmtParam_String ( stmt, param, getUTCTime());
            setPrepStmtParam_BigDecimal ( stmt, param, oid_getTheType());
            setPrepStmtParam_String ( stmt, param, getProcessId());
            setPrepStmtParam_String ( stmt, param, getProcessName());
            setPrepStmtParam_String ( stmt, param, getProcessDefinitionName());
            setPrepStmtParam_String ( stmt, param, getProcessDefinitionVersion());
            setPrepStmtParam_String ( stmt, param, getProcessDefinitionId());
            setPrepStmtParam_String ( stmt, param, getPackageId());
            setPrepStmtParam_String ( stmt, param, getPActivityId());
            setPrepStmtParam_String ( stmt, param, getPProcessId());
            setPrepStmtParam_String ( stmt, param, getPProcessName());
            setPrepStmtParam_String ( stmt, param, getPProcessDefinitionName());
            setPrepStmtParam_String ( stmt, param, getPProcessDefinitionVersion());
            setPrepStmtParam_String ( stmt, param, getPActivityDefinitionId());
            setPrepStmtParam_String ( stmt, param, getPActivitySetDefinitionId());
            setPrepStmtParam_String ( stmt, param, getPProcessDefinitionId());
            setPrepStmtParam_String ( stmt, param, getPPackageId());
            setPrepStmtParam_java_math_BigDecimal ( stmt, param, getCNT());

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
            throw new SQLException("CreateProcessEventAudits ("
					+get_OId()+") is already deleted, "
					+"cannot lock it.");

        param = new int[1]; param[0] = 1;
        try {
            updateStmt.append("Update CreateProcessEventAudits set ");
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
            updateStmt.append("Update CreateProcessEventAudits set ");
            updateStmt.append(get_versionColumnName()).append(" = ? ");

            if (changedUTCTime)
                updateStmt.append(", UTCTime = ? ");
            if (changedTheType)
                updateStmt.append(", TheType = ? ");
            if (changedProcessId)
                updateStmt.append(", ProcessId = ? ");
            if (changedProcessName)
                updateStmt.append(", ProcessName = ? ");
            if (changedProcessDefinitionName)
                updateStmt.append(", ProcessDefinitionName = ? ");
            if (changedProcessDefinitionVersion)
                updateStmt.append(", ProcessDefinitionVersion = ? ");
            if (changedProcessDefinitionId)
                updateStmt.append(", ProcessDefinitionId = ? ");
            if (changedPackageId)
                updateStmt.append(", PackageId = ? ");
            if (changedPActivityId)
                updateStmt.append(", PActivityId = ? ");
            if (changedPProcessId)
                updateStmt.append(", PProcessId = ? ");
            if (changedPProcessName)
                updateStmt.append(", PProcessName = ? ");
            if (changedPProcessDefinitionName)
                updateStmt.append(", PProcessDefinitionName = ? ");
            if (changedPProcessDefinitionVersion)
                updateStmt.append(", PProcessDefinitionVersion = ? ");
            if (changedPActivityDefinitionId)
                updateStmt.append(", PActivityDefinitionId = ? ");
            if (changedPActivitySetDefinitionId)
                updateStmt.append(", PActivitySetDefinitionId = ? ");
            if (changedPProcessDefinitionId)
                updateStmt.append(", PProcessDefinitionId = ? ");
            if (changedPPackageId)
                updateStmt.append(", PPackageId = ? ");
            if (changedCNT)
                updateStmt.append(", CNT = ? ");
            updateStmt.append(" where " + get_OIdColumnName() + " = ? and " + get_versionColumnName() + " = ?");

            stmt = conn.prepareStatement(updateStmt.toString());
            setPrepStmtParam_int(stmt, param, get_Version());

            if (changedUTCTime) {
                setPrepStmtParam_String( stmt, param, getUTCTime());
                changedUTCTime = false;
            }
            if (changedTheType) {
                setPrepStmtParam_BigDecimal( stmt, param, oid_getTheType());
                changedTheType = false;
            }
            if (changedProcessId) {
                setPrepStmtParam_String( stmt, param, getProcessId());
                changedProcessId = false;
            }
            if (changedProcessName) {
                setPrepStmtParam_String( stmt, param, getProcessName());
                changedProcessName = false;
            }
            if (changedProcessDefinitionName) {
                setPrepStmtParam_String( stmt, param, getProcessDefinitionName());
                changedProcessDefinitionName = false;
            }
            if (changedProcessDefinitionVersion) {
                setPrepStmtParam_String( stmt, param, getProcessDefinitionVersion());
                changedProcessDefinitionVersion = false;
            }
            if (changedProcessDefinitionId) {
                setPrepStmtParam_String( stmt, param, getProcessDefinitionId());
                changedProcessDefinitionId = false;
            }
            if (changedPackageId) {
                setPrepStmtParam_String( stmt, param, getPackageId());
                changedPackageId = false;
            }
            if (changedPActivityId) {
                setPrepStmtParam_String( stmt, param, getPActivityId());
                changedPActivityId = false;
            }
            if (changedPProcessId) {
                setPrepStmtParam_String( stmt, param, getPProcessId());
                changedPProcessId = false;
            }
            if (changedPProcessName) {
                setPrepStmtParam_String( stmt, param, getPProcessName());
                changedPProcessName = false;
            }
            if (changedPProcessDefinitionName) {
                setPrepStmtParam_String( stmt, param, getPProcessDefinitionName());
                changedPProcessDefinitionName = false;
            }
            if (changedPProcessDefinitionVersion) {
                setPrepStmtParam_String( stmt, param, getPProcessDefinitionVersion());
                changedPProcessDefinitionVersion = false;
            }
            if (changedPActivityDefinitionId) {
                setPrepStmtParam_String( stmt, param, getPActivityDefinitionId());
                changedPActivityDefinitionId = false;
            }
            if (changedPActivitySetDefinitionId) {
                setPrepStmtParam_String( stmt, param, getPActivitySetDefinitionId());
                changedPActivitySetDefinitionId = false;
            }
            if (changedPProcessDefinitionId) {
                setPrepStmtParam_String( stmt, param, getPProcessDefinitionId());
                changedPProcessDefinitionId = false;
            }
            if (changedPPackageId) {
                setPrepStmtParam_String( stmt, param, getPPackageId());
                changedPPackageId = false;
            }
            if (changedCNT) {
                setPrepStmtParam_java_math_BigDecimal( stmt, param, getCNT());
                changedCNT = false;
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
            "delete from CreateProcessEventAudits \n" +
            "where " + get_OIdColumnName() + " = ? and " + get_versionColumnName() + " = ?";
        else    
            sql =
            "delete from CreateProcessEventAudits \n" +
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
        sb.append(indent + "CreateProcessEventAuditDO:");

        ObjectId oid = get_OId();
        String id = "virgin";
        if ( null != oid )
            id = oid.toString();
        sb.append(" OID=" + id + ",VERSION=" + get_Version());
        if (isLoaded()) {    
                sb.append("\n" + indent + "UTCTime=" + get_DataStruct().getUTCTime());
    
                sb.append("\n" + indent + "TheType=" + get_DataStruct().getTheType());
    
                sb.append("\n" + indent + "ProcessId=" + get_DataStruct().getProcessId());
    
                sb.append("\n" + indent + "ProcessName=" + get_DataStruct().getProcessName());
    
                sb.append("\n" + indent + "ProcessDefinitionName=" + get_DataStruct().getProcessDefinitionName());
    
                sb.append("\n" + indent + "ProcessDefinitionVersion=" + get_DataStruct().getProcessDefinitionVersion());
    
                sb.append("\n" + indent + "ProcessDefinitionId=" + get_DataStruct().getProcessDefinitionId());
    
                sb.append("\n" + indent + "PackageId=" + get_DataStruct().getPackageId());
    
                sb.append("\n" + indent + "PActivityId=" + get_DataStruct().getPActivityId());
    
                sb.append("\n" + indent + "PProcessId=" + get_DataStruct().getPProcessId());
    
                sb.append("\n" + indent + "PProcessName=" + get_DataStruct().getPProcessName());
    
                sb.append("\n" + indent + "PProcessDefinitionName=" + get_DataStruct().getPProcessDefinitionName());
    
                sb.append("\n" + indent + "PProcessDefinitionVersion=" + get_DataStruct().getPProcessDefinitionVersion());
    
                sb.append("\n" + indent + "PActivityDefinitionId=" + get_DataStruct().getPActivityDefinitionId());
    
                sb.append("\n" + indent + "PActivitySetDefinitionId=" + get_DataStruct().getPActivitySetDefinitionId());
    
                sb.append("\n" + indent + "PProcessDefinitionId=" + get_DataStruct().getPProcessDefinitionId());
    
                sb.append("\n" + indent + "PPackageId=" + get_DataStruct().getPPackageId());
    
                sb.append("\n" + indent + "CNT=" + get_DataStruct().getCNT());
;
            sb.append("\n" + indent + "SUPER=" + super.toString( indentCount ));
        }
        return sb.toString(); 
    }

    /**
     * A stub method for implementing pre-commit assertions 
     * for this CreateProcessEventAuditDO.
     * Implement this stub to throw an RefAssertionException for cases
     * where this object is not valid for writing to the database.
     * @exception RefAssertionException
     */
    protected void okToCommit() 
    throws RefAssertionException { 
    }

    /**
     * A stub method for implementing pre-delete assertions 
     * for this CreateProcessEventAuditDO.
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
            throw new AssertionDataObjectException("CreateProcessEventAuditDO's cache is read-only. Therefore, DML opertions are not allowed.");
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
            throw new AssertionDataObjectException("CreateProcessEventAuditDO's cache is read-only. Therefore, DML opertions are not allowed.");
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
            throw new AssertionDataObjectException("CreateProcessEventAuditDO's cache is read-only. Therefore, DML opertions are not allowed.");
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
            throw new AssertionDataObjectException("CreateProcessEventAuditDO's cache is read-only. Therefore, DML opertions are not allowed.");
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
     * for the TheType data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where TheType is not valid for writing to the database.
     *
     * @param member TheType data member. 
     * @exception RefAssertionException
     *
     */
    protected void okToCommitTheType( org.enhydra.shark.eventaudit.data.EventTypeDO member ) 
    throws RefAssertionException { }

    /**
     * A stub method for implementing pre-delete assertions 
     * for the TheType data member.
     * Implement this stub to throw an RefAssertionException for cases
     * where TheType is not valid for deletion from the database.
     *
     * @param member TheType data member
     * @exception RefAssertionException
     *
     */
    protected void okToDeleteTheType( org.enhydra.shark.eventaudit.data.EventTypeDO member ) 
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
                    org.enhydra.shark.eventaudit.data.EventTypeDO TheType_DO = getTheType();
                    if ( null != TheType_DO && !this.equals(TheType_DO)) {
                        if ( TheType_DO.isDirty() ) {
                            boolean tmpDirty = true;
                            if(isDirty()) {
                                markClean();
                                tmpDirty = false;
                            }    
                            okToCommitTheType( TheType_DO );
                            if ( !TheType_DO.getConfigurationAdministration().getTableConfiguration().isReadOnly() ) {
                                TheType_DO.save( dbt );
                                if(!tmpDirty)
                                   markNewValue(); 
                            }    
                        } else {
                            // since the referenced DO is not loaded,
                            // it cannot be dirty, so there is no need to commit it.
                        }
                    } else {
                        if ( ! false )
                            throw new RefAssertionException( "Cannot commit CreateProcessEventAuditDO ( " + toString() +
                                                             " ) because TheType is not allowed to be null." );
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
        changedUTCTime = value;
        changedTheType = value;
        changedProcessId = value;
        changedProcessName = value;
        changedProcessDefinitionName = value;
        changedProcessDefinitionVersion = value;
        changedProcessDefinitionId = value;
        changedPackageId = value;
        changedPActivityId = value;
        changedPProcessId = value;
        changedPProcessName = value;
        changedPProcessDefinitionName = value;
        changedPProcessDefinitionVersion = value;
        changedPActivityDefinitionId = value;
        changedPActivitySetDefinitionId = value;
        changedPProcessDefinitionId = value;
        changedPPackageId = value;
        changedCNT = value;
    }
}
