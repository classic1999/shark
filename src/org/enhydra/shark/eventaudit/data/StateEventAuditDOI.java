
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
 * org.enhydra.shark.eventaudit.data/StateEventAuditDOI.java
 *-----------------------------------------------------------------------------
 */

package org.enhydra.shark.eventaudit.data;

import java.io.*;
import java.sql.*;
import java.math.*;
import com.lutris.appserver.server.sql.*;
import com.lutris.dods.builder.generator.query.*;

/**
 * Interface implemented by StateEventAuditDO
 * Interface could also be implemented by a (hand-written) Business Layer class
 * which uses StateEventAuditDO
 *
 * @version $Revision: 1.6 $
 * @author  NN
 * @since   DODS Project
 */
public interface StateEventAuditDOI {

    ////////////////////////// data member UTCTime

    /**
     * Get UTCTime of the StateEventAudits
     *
     * @return UTCTime of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getUTCTime () 
    throws DataObjectException;
    

    /**
     * Set UTCTime of the StateEventAudits
     *
     * @param UTCTime of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setUTCTime ( String UTCTime )
    throws DataObjectException;

    ////////////////////////// data member TheType

    /**
     * Get TheType of the StateEventAudits
     *
     * @return TheType of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.eventaudit.data.EventTypeDO getTheType () 
    throws DataObjectException;
    

    /**
     * Set TheType of the StateEventAudits
     *
     * @param TheType of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setTheType ( org.enhydra.shark.eventaudit.data.EventTypeDO TheType )
    throws DataObjectException;

    ////////////////////////// data member ActivityId

    /**
     * Get ActivityId of the StateEventAudits
     *
     * @return ActivityId of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getActivityId () 
    throws DataObjectException;
    

    /**
     * Set ActivityId of the StateEventAudits
     *
     * @param ActivityId of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivityId ( String ActivityId )
    throws DataObjectException;

    ////////////////////////// data member ActivityName

    /**
     * Get ActivityName of the StateEventAudits
     *
     * @return ActivityName of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getActivityName () 
    throws DataObjectException;
    

    /**
     * Set ActivityName of the StateEventAudits
     *
     * @param ActivityName of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivityName ( String ActivityName )
    throws DataObjectException;

    ////////////////////////// data member ProcessId

    /**
     * Get ProcessId of the StateEventAudits
     *
     * @return ProcessId of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessId () 
    throws DataObjectException;
    

    /**
     * Set ProcessId of the StateEventAudits
     *
     * @param ProcessId of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessId ( String ProcessId )
    throws DataObjectException;

    ////////////////////////// data member ProcessName

    /**
     * Get ProcessName of the StateEventAudits
     *
     * @return ProcessName of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessName () 
    throws DataObjectException;
    

    /**
     * Set ProcessName of the StateEventAudits
     *
     * @param ProcessName of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessName ( String ProcessName )
    throws DataObjectException;

    ////////////////////////// data member ProcessDefinitionName

    /**
     * Get ProcessDefinitionName of the StateEventAudits
     *
     * @return ProcessDefinitionName of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessDefinitionName () 
    throws DataObjectException;
    

    /**
     * Set ProcessDefinitionName of the StateEventAudits
     *
     * @param ProcessDefinitionName of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessDefinitionName ( String ProcessDefinitionName )
    throws DataObjectException;

    ////////////////////////// data member ProcessDefinitionVersion

    /**
     * Get ProcessDefinitionVersion of the StateEventAudits
     *
     * @return ProcessDefinitionVersion of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessDefinitionVersion () 
    throws DataObjectException;
    

    /**
     * Set ProcessDefinitionVersion of the StateEventAudits
     *
     * @param ProcessDefinitionVersion of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessDefinitionVersion ( String ProcessDefinitionVersion )
    throws DataObjectException;

    ////////////////////////// data member ActivityDefinitionId

    /**
     * Get ActivityDefinitionId of the StateEventAudits
     *
     * @return ActivityDefinitionId of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getActivityDefinitionId () 
    throws DataObjectException;
    

    /**
     * Set ActivityDefinitionId of the StateEventAudits
     *
     * @param ActivityDefinitionId of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivityDefinitionId ( String ActivityDefinitionId )
    throws DataObjectException;

    ////////////////////////// data member ActivitySetDefinitionId

    /**
     * Get ActivitySetDefinitionId of the StateEventAudits
     *
     * @return ActivitySetDefinitionId of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getActivitySetDefinitionId () 
    throws DataObjectException;
    

    /**
     * Set ActivitySetDefinitionId of the StateEventAudits
     *
     * @param ActivitySetDefinitionId of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivitySetDefinitionId ( String ActivitySetDefinitionId )
    throws DataObjectException;

    ////////////////////////// data member ProcessDefinitionId

    /**
     * Get ProcessDefinitionId of the StateEventAudits
     *
     * @return ProcessDefinitionId of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessDefinitionId () 
    throws DataObjectException;
    

    /**
     * Set ProcessDefinitionId of the StateEventAudits
     *
     * @param ProcessDefinitionId of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessDefinitionId ( String ProcessDefinitionId )
    throws DataObjectException;

    ////////////////////////// data member PackageId

    /**
     * Get PackageId of the StateEventAudits
     *
     * @return PackageId of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPackageId () 
    throws DataObjectException;
    

    /**
     * Set PackageId of the StateEventAudits
     *
     * @param PackageId of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPackageId ( String PackageId )
    throws DataObjectException;

    ////////////////////////// data member OldProcessState

    /**
     * Get OldProcessState of the StateEventAudits
     *
     * @return OldProcessState of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.eventaudit.data.ProcessStateEventAuditDO getOldProcessState () 
    throws DataObjectException;
    

    /**
     * Set OldProcessState of the StateEventAudits
     *
     * @param OldProcessState of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setOldProcessState ( org.enhydra.shark.eventaudit.data.ProcessStateEventAuditDO OldProcessState )
    throws DataObjectException;

    ////////////////////////// data member NewProcessState

    /**
     * Get NewProcessState of the StateEventAudits
     *
     * @return NewProcessState of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.eventaudit.data.ProcessStateEventAuditDO getNewProcessState () 
    throws DataObjectException;
    

    /**
     * Set NewProcessState of the StateEventAudits
     *
     * @param NewProcessState of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setNewProcessState ( org.enhydra.shark.eventaudit.data.ProcessStateEventAuditDO NewProcessState )
    throws DataObjectException;

    ////////////////////////// data member OldActivityState

    /**
     * Get OldActivityState of the StateEventAudits
     *
     * @return OldActivityState of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.eventaudit.data.ActivityStateEventAuditDO getOldActivityState () 
    throws DataObjectException;
    

    /**
     * Set OldActivityState of the StateEventAudits
     *
     * @param OldActivityState of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setOldActivityState ( org.enhydra.shark.eventaudit.data.ActivityStateEventAuditDO OldActivityState )
    throws DataObjectException;

    ////////////////////////// data member NewActivityState

    /**
     * Get NewActivityState of the StateEventAudits
     *
     * @return NewActivityState of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.eventaudit.data.ActivityStateEventAuditDO getNewActivityState () 
    throws DataObjectException;
    

    /**
     * Set NewActivityState of the StateEventAudits
     *
     * @param NewActivityState of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setNewActivityState ( org.enhydra.shark.eventaudit.data.ActivityStateEventAuditDO NewActivityState )
    throws DataObjectException;

    ////////////////////////// data member CNT

    /**
     * Get CNT of the StateEventAudits
     *
     * @return CNT of the StateEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public java.math.BigDecimal getCNT () 
    throws DataObjectException;
    

    /**
     * Set CNT of the StateEventAudits
     *
     * @param CNT of the StateEventAudits
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
