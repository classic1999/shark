/*-----------------------------------------------------------------------------
 * Enhydra Java Application Server
 * Copyright Together Teamsolutions.
 * All rights reserved.
 *
 *-----------------------------------------------------------------------------
 * org.enhydra.shark.processlocking.data/LockEntryDelete.java
 *-----------------------------------------------------------------------------
 */

package org.enhydra.shark.processlocking.data;

import java.io.*;
import java.sql.*;
import java.math.*;
import java.util.*;
import com.lutris.appserver.server.sql.*;
import com.lutris.appserver.server.sql.standard.*;
import com.lutris.dods.builder.generator.query.*;
import com.lutris.logging.*;
import org.enhydra.dods.DODS;
import org.enhydra.dods.cache.*;
import org.enhydra.dods.exceptions.AssertionDataObjectException;

/**
 *  *
 * @version $Revision: 1.4 $
 * @author  NN
 * @since   DODS Project
 */
public class LockEntryDelete extends CoreDO {

    private CachedDBTransaction myDBt = null;
    private Vector vecOIdsUpdated = null;
    private LockEntryQuery myQuery;
    private QueryBuilder myQB;
    private ObjectId OId;
    private boolean doSelectOIds;

    /**
     * @param query
     */
    public LockEntryDelete (LockEntryQuery query)
           throws ObjectIdException, DataObjectException, DatabaseManagerException {
        OId = DODS.getDatabaseManager().allocateObjectId();
        myQuery = query;
        myDBt =(CachedDBTransaction)query.transaction;
        myQB = query.getQueryBuilder();
        doSelectOIds = true;
        setPersistent(true);
        if (myDBt != null && isAutoSave()) {
            try {
                save(myDBt);
            } catch (Exception ex) {
                throw new DataObjectException
                    ("Error during transaction's writting data into database",ex);
            }
        }
    }

    /**
     * @param dbt
     */
    public void save(DBTransaction dbt) throws
    SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException {
        if (LockEntryDO.cache.getTableConfiguration().isReadOnly()) {
            throw new AssertionDataObjectException("LockEntryDO's cache is read-only. Therefore, DML opertions are not allowed.");
        }
            DBTransaction dbtlocal = dbt;
            boolean needToCommit = false;

        try {

            if (dbtlocal == null) {
              if( myDBt==null) {
                dbtlocal = DODS.getDatabaseManager().createTransaction(myQuery.getLogicalDatabase());
                dbtlocal.setDatabaseName(myQuery.getLogicalDatabase());
                needToCommit = true;
              }
             else
                dbtlocal=myDBt;
            } else {
                if(myDBt!=null) {
                    if(!myDBt.equals(dbt))
                          throw new DatabaseManagerException("DO doesn't belong this transaction.");
                }
            }

            dbtlocal.delete(this);
            if (needToCommit) {
                dbtlocal.commit();
                dbtlocal.release();
            }
        } catch (SQLException e) {
            StringBuffer message = new StringBuffer("Failed to delete : ");
            message.append(e.getMessage());
            // rollback, if necessary
            if (needToCommit) {
                try {
                    dbtlocal.rollback();
                    dbtlocal.release();
                } catch (SQLException sqle2) {
                    message.insert(0,"\n");
                    message.insert(0,sqle2.getMessage());
                    message.insert(0,"Rollback failed: ");
                }
            }

            throw new SQLException(message.toString());
        }
    }

    /**
     *
     */
    public void save() throws
    SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException  {
        save(myDBt);
    }

    /**
     * overloaded method from CoreDO
     */
    public void executeDelete(DBConnection conn)
            throws SQLException {
        /*
         * optional OId gathering depends on parameter
         * and existence either of caches
         */
        DODS.getLogChannel().write(Logger.DEBUG, "executing LockEntryDelete");
        if (beMorePrecise()&&(hasTransactionCache()||hasGlobalCache())) {
            // optional "select oid" + all clauses from query builder
            // which makes vector of oids to update directly in cache
            vecOIdsUpdated = myQuery.collectOIds();
        }

        myQB.setDeleteQuery();
        myQB.executeUpdate(conn);

        if (hasTransactionCache()) {
            // empty all DO objects from xxx table in transaction cache
            //  - crate new empty DataStruct discarding both old ones
            // (or from list acquired in executeUpdate)
            if (beMorePrecise()) {
                ((TransactionQueryCache)myDBt.getTransactionCache()).removeEntries(vecOIdsUpdated);
            } else {
                ((TransactionQueryCache)myDBt.getTransactionCache()).removeEntries(LockEntryDO.class);
            }
        }
    }

    /**
     * overloaded method from CoreDO
     */
    public void finalizeDelete(boolean success) {
        if (success) {
            try {
                if (beMorePrecise()) {
                    ((QueryCache)LockEntryDO.cache).removeEntries(vecOIdsUpdated);
                } else {
                    ((QueryCache)LockEntryDO.cache).removeEntries();
                }
            } catch(Exception e) {}
        }
    }

    /**
     *
     */
    public ObjectId get_OId() {
        return OId;
    }

    private boolean hasTransactionCache() {
        return (null != myDBt)&&(null != myDBt.getTransactionCache());
    }

    private static boolean hasGlobalCache() {
        return !LockEntryDO.cache.getCacheType().equals("none");
    }

    /**
     *
     */
    protected boolean isAutoSave() {
        boolean flag = false;
        try {
            String dbName =(myDBt!=null)?
                myDBt.getDatabaseName():
                LockEntryDO.get_logicalDBName();
            flag = ((StandardLogicalDatabase)(DODS.getDatabaseManager().findLogicalDatabase(dbName))).getDatabaseConfiguration().getAutoSave();
        } catch (Exception ex) {}
        return flag;
    }

    /**
     * @return true for ...
     */
    public boolean isSelectOIds() {
        return doSelectOIds;
    }

    /**
     * @param arg - without explanation, yet
     */
    public void setSelectOIds(boolean arg) {
        doSelectOIds = arg;
    }

    /**
     *
     */
    protected boolean beMorePrecise() {
        try {
            if (doSelectOIds)
                return LockEntryDO.getConfigurationAdministration().getTableConfiguration().getSelectOids();
        } catch (Exception ex) {}
        return false;
    }
    public void dumpData(boolean incrementVersion) {}


    public PreparedStatement getInsertStatement(DBConnection conn)
            throws SQLException {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    public PreparedStatement getUpdateStatement(DBConnection conn)
            throws SQLException {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    public PreparedStatement getDeleteStatement(DBConnection conn)
            throws SQLException {
        //throw new RuntimeException("NOT IMPLEMENTED");
        myQB.setDeleteQuery();
        return myQB.getStatement(conn);
    }
}
