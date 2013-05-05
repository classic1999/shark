
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
 *      This product includes Enhydra software developed by Lutris
 *      Technologies, Inc. and its contributors.
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
 *-----------------------------------------------------------------------------
 * org.enhydra.shark.eventaudit.data/DataEventAuditDOI.java
 *-----------------------------------------------------------------------------
 */

package org.enhydra.shark.eventaudit.data;

import java.io.*;
import java.sql.*;
import java.math.*;
import com.lutris.appserver.server.sql.*;
import com.lutris.dods.builder.generator.query.*;

/**
 * Interface implemented by DataEventAuditDO
 * Interface could also be implemented by a (hand-written) Business Layer class
 * which uses DataEventAuditDO
 *
 * @version $Revision: 1.6 $
 * @author  NN
 * @since   DODS Project
 */
public interface DataEventAuditDOI {

    ////////////////////////// data member UTCTime

    /**
     * Get UTCTime of the DataEventAudits
     *
     * @return UTCTime of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getUTCTime () 
    throws DataObjectException;
    

    /**
     * Set UTCTime of the DataEventAudits
     *
     * @param UTCTime of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setUTCTime ( String UTCTime )
    throws DataObjectException;

    ////////////////////////// data member TheType

    /**
     * Get TheType of the DataEventAudits
     *
     * @return TheType of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.eventaudit.data.EventTypeDO getTheType () 
    throws DataObjectException;
    

    /**
     * Set TheType of the DataEventAudits
     *
     * @param TheType of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setTheType ( org.enhydra.shark.eventaudit.data.EventTypeDO TheType )
    throws DataObjectException;

    ////////////////////////// data member ActivityId

    /**
     * Get ActivityId of the DataEventAudits
     *
     * @return ActivityId of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getActivityId () 
    throws DataObjectException;
    

    /**
     * Set ActivityId of the DataEventAudits
     *
     * @param ActivityId of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivityId ( String ActivityId )
    throws DataObjectException;

    ////////////////////////// data member ActivityName

    /**
     * Get ActivityName of the DataEventAudits
     *
     * @return ActivityName of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getActivityName () 
    throws DataObjectException;
    

    /**
     * Set ActivityName of the DataEventAudits
     *
     * @param ActivityName of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivityName ( String ActivityName )
    throws DataObjectException;

    ////////////////////////// data member ProcessId

    /**
     * Get ProcessId of the DataEventAudits
     *
     * @return ProcessId of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessId () 
    throws DataObjectException;
    

    /**
     * Set ProcessId of the DataEventAudits
     *
     * @param ProcessId of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessId ( String ProcessId )
    throws DataObjectException;

    ////////////////////////// data member ProcessName

    /**
     * Get ProcessName of the DataEventAudits
     *
     * @return ProcessName of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessName () 
    throws DataObjectException;
    

    /**
     * Set ProcessName of the DataEventAudits
     *
     * @param ProcessName of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessName ( String ProcessName )
    throws DataObjectException;

    ////////////////////////// data member ProcessDefinitionName

    /**
     * Get ProcessDefinitionName of the DataEventAudits
     *
     * @return ProcessDefinitionName of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessDefinitionName () 
    throws DataObjectException;
    

    /**
     * Set ProcessDefinitionName of the DataEventAudits
     *
     * @param ProcessDefinitionName of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessDefinitionName ( String ProcessDefinitionName )
    throws DataObjectException;

    ////////////////////////// data member ProcessDefinitionVersion

    /**
     * Get ProcessDefinitionVersion of the DataEventAudits
     *
     * @return ProcessDefinitionVersion of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessDefinitionVersion () 
    throws DataObjectException;
    

    /**
     * Set ProcessDefinitionVersion of the DataEventAudits
     *
     * @param ProcessDefinitionVersion of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessDefinitionVersion ( String ProcessDefinitionVersion )
    throws DataObjectException;

    ////////////////////////// data member ActivityDefinitionId

    /**
     * Get ActivityDefinitionId of the DataEventAudits
     *
     * @return ActivityDefinitionId of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getActivityDefinitionId () 
    throws DataObjectException;
    

    /**
     * Set ActivityDefinitionId of the DataEventAudits
     *
     * @param ActivityDefinitionId of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivityDefinitionId ( String ActivityDefinitionId )
    throws DataObjectException;

    ////////////////////////// data member ActivitySetDefinitionId

    /**
     * Get ActivitySetDefinitionId of the DataEventAudits
     *
     * @return ActivitySetDefinitionId of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getActivitySetDefinitionId () 
    throws DataObjectException;
    

    /**
     * Set ActivitySetDefinitionId of the DataEventAudits
     *
     * @param ActivitySetDefinitionId of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivitySetDefinitionId ( String ActivitySetDefinitionId )
    throws DataObjectException;

    ////////////////////////// data member ProcessDefinitionId

    /**
     * Get ProcessDefinitionId of the DataEventAudits
     *
     * @return ProcessDefinitionId of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessDefinitionId () 
    throws DataObjectException;
    

    /**
     * Set ProcessDefinitionId of the DataEventAudits
     *
     * @param ProcessDefinitionId of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessDefinitionId ( String ProcessDefinitionId )
    throws DataObjectException;

    ////////////////////////// data member PackageId

    /**
     * Get PackageId of the DataEventAudits
     *
     * @return PackageId of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPackageId () 
    throws DataObjectException;
    

    /**
     * Set PackageId of the DataEventAudits
     *
     * @param PackageId of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPackageId ( String PackageId )
    throws DataObjectException;

    ////////////////////////// data member CNT

    /**
     * Get CNT of the DataEventAudits
     *
     * @return CNT of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public java.math.BigDecimal getCNT () 
    throws DataObjectException;
    

    /**
     * Set CNT of the DataEventAudits
     *
     * @param CNT of the DataEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setCNT ( java.math.BigDecimal CNT )
    throws DataObjectException;

    /**
     * Inserts/Updates the DO into its table.
     *
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception com.lutris.appserver.server.sql.DatabaseManagerException if a Transaction can not be created.
     * @exception DataObjectException
     * @exception RefAssertionException thrown by okTo method.
     * @exception DBRowUpdateException
     * @exception QueryException
     */
    public void save() 
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException;
    
    /**
     * Inserts/Updates the DO into its table.
     * The transaction is likely provided by the save() method of another DO
     * which references this DO.
     * 
     * @param dbt The transaction object to use for this operation.
     * @exception com.lutris.appserver.server.sql.DatabaseManagerException if a Transaction can not be created.
     * @exception com.lutris.appserver.server.sql.DBRowUpdateException if a version error occurs.
     * @exception RefAssertionException thrown by okTo method.
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException
     * @exception QueryException
     */
    public void save(DBTransaction dbt)
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException;
    
    /**
     * Inserts/Updates the DO into its table.
     *
     * @exception com.lutris.appserver.server.sql.DatabaseManagerException if a Transaction can not be created.
     * @exception RefAssertionException thrown by okTo method.
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException
     * @exception DBRowUpdateException 
     * @exception QueryException 
     * @deprecated Use save() instead.
     */
    public void commit() 
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException;
    
    /**
     * Inserts/Updates the DO into its table.
     * The transaction is likely provided by the commit() method of another DO
     * which references this DO.
     * 
     * @param dbt The transaction object to use for this operation.
     * @exception com.lutris.appserver.server.sql.DatabaseManagerException if a Transaction can not be created.
     * @exception com.lutris.appserver.server.sql.DBRowUpdateException if a version error occurs.
     * @exception RefAssertionException thrown by okTo method.
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException
     * @exception QueryException 
     * @deprecated Use save() instead.
     */
    public void commit(DBTransaction dbt)
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException;
    
    /**
     * Deletes the DO from its table.
     *
     * @exception com.lutris.appserver.server.sql.DatabaseManagerException if a Transaction can not be created.
     * @exception RefAssertionException thrown by okTo method.
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException
     * @exception DBRowUpdateException 
     * @exception QueryException 
     */
    public void delete() 
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException;
    
    /**
     * Deletes the DO from its table.
     * The transaction is likely provided by the delete() method of another DO
     * which references this DO.
     *
     * @param dbt The transaction object to use for this operation.
     * @exception com.lutris.appserver.server.sql.DatabaseManagerException if a Transaction can not be created.
     * @exception com.lutris.appserver.server.sql.DBRowUpdateException if a version error occurs.
     * @exception RefAssertionException thrown by okTo method.
     * @exception java.sql.SQLException if any SQL errors occur.
     * @exception DataObjectException 
     * @exception QueryException
     */
    public void delete(DBTransaction dbt)
    throws SQLException, DatabaseManagerException, DataObjectException, RefAssertionException, DBRowUpdateException, QueryException;

}
