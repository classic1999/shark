
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
 * org.enhydra.shark.eventaudit.data/AssignmentEventAuditDOI.java
 *-----------------------------------------------------------------------------
 */

package org.enhydra.shark.eventaudit.data;

import java.io.*;
import java.sql.*;
import java.math.*;
import com.lutris.appserver.server.sql.*;
import com.lutris.dods.builder.generator.query.*;

/**
 * Interface implemented by AssignmentEventAuditDO
 * Interface could also be implemented by a (hand-written) Business Layer class
 * which uses AssignmentEventAuditDO
 *
 * @version $Revision: 1.6 $
 * @author  NN
 * @since   DODS Project
 */
public interface AssignmentEventAuditDOI {

    ////////////////////////// data member UTCTime

    /**
     * Get UTCTime of the AssignmentEventAudits
     *
     * @return UTCTime of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getUTCTime () 
    throws DataObjectException;
    

    /**
     * Set UTCTime of the AssignmentEventAudits
     *
     * @param UTCTime of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setUTCTime ( String UTCTime )
    throws DataObjectException;

    ////////////////////////// data member TheType

    /**
     * Get TheType of the AssignmentEventAudits
     *
     * @return TheType of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.eventaudit.data.EventTypeDO getTheType () 
    throws DataObjectException;
    

    /**
     * Set TheType of the AssignmentEventAudits
     *
     * @param TheType of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setTheType ( org.enhydra.shark.eventaudit.data.EventTypeDO TheType )
    throws DataObjectException;

    ////////////////////////// data member ActivityId

    /**
     * Get ActivityId of the AssignmentEventAudits
     *
     * @return ActivityId of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getActivityId () 
    throws DataObjectException;
    

    /**
     * Set ActivityId of the AssignmentEventAudits
     *
     * @param ActivityId of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivityId ( String ActivityId )
    throws DataObjectException;

    ////////////////////////// data member ActivityName

    /**
     * Get ActivityName of the AssignmentEventAudits
     *
     * @return ActivityName of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getActivityName () 
    throws DataObjectException;
    

    /**
     * Set ActivityName of the AssignmentEventAudits
     *
     * @param ActivityName of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivityName ( String ActivityName )
    throws DataObjectException;

    ////////////////////////// data member ProcessId

    /**
     * Get ProcessId of the AssignmentEventAudits
     *
     * @return ProcessId of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessId () 
    throws DataObjectException;
    

    /**
     * Set ProcessId of the AssignmentEventAudits
     *
     * @param ProcessId of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessId ( String ProcessId )
    throws DataObjectException;

    ////////////////////////// data member ProcessName

    /**
     * Get ProcessName of the AssignmentEventAudits
     *
     * @return ProcessName of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessName () 
    throws DataObjectException;
    

    /**
     * Set ProcessName of the AssignmentEventAudits
     *
     * @param ProcessName of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessName ( String ProcessName )
    throws DataObjectException;

    ////////////////////////// data member ProcessDefinitionName

    /**
     * Get ProcessDefinitionName of the AssignmentEventAudits
     *
     * @return ProcessDefinitionName of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessDefinitionName () 
    throws DataObjectException;
    

    /**
     * Set ProcessDefinitionName of the AssignmentEventAudits
     *
     * @param ProcessDefinitionName of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessDefinitionName ( String ProcessDefinitionName )
    throws DataObjectException;

    ////////////////////////// data member ProcessDefinitionVersion

    /**
     * Get ProcessDefinitionVersion of the AssignmentEventAudits
     *
     * @return ProcessDefinitionVersion of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessDefinitionVersion () 
    throws DataObjectException;
    

    /**
     * Set ProcessDefinitionVersion of the AssignmentEventAudits
     *
     * @param ProcessDefinitionVersion of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessDefinitionVersion ( String ProcessDefinitionVersion )
    throws DataObjectException;

    ////////////////////////// data member ActivityDefinitionId

    /**
     * Get ActivityDefinitionId of the AssignmentEventAudits
     *
     * @return ActivityDefinitionId of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getActivityDefinitionId () 
    throws DataObjectException;
    

    /**
     * Set ActivityDefinitionId of the AssignmentEventAudits
     *
     * @param ActivityDefinitionId of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivityDefinitionId ( String ActivityDefinitionId )
    throws DataObjectException;

    ////////////////////////// data member ActivitySetDefinitionId

    /**
     * Get ActivitySetDefinitionId of the AssignmentEventAudits
     *
     * @return ActivitySetDefinitionId of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getActivitySetDefinitionId () 
    throws DataObjectException;
    

    /**
     * Set ActivitySetDefinitionId of the AssignmentEventAudits
     *
     * @param ActivitySetDefinitionId of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivitySetDefinitionId ( String ActivitySetDefinitionId )
    throws DataObjectException;

    ////////////////////////// data member ProcessDefinitionId

    /**
     * Get ProcessDefinitionId of the AssignmentEventAudits
     *
     * @return ProcessDefinitionId of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessDefinitionId () 
    throws DataObjectException;
    

    /**
     * Set ProcessDefinitionId of the AssignmentEventAudits
     *
     * @param ProcessDefinitionId of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessDefinitionId ( String ProcessDefinitionId )
    throws DataObjectException;

    ////////////////////////// data member PackageId

    /**
     * Get PackageId of the AssignmentEventAudits
     *
     * @return PackageId of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPackageId () 
    throws DataObjectException;
    

    /**
     * Set PackageId of the AssignmentEventAudits
     *
     * @param PackageId of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPackageId ( String PackageId )
    throws DataObjectException;

    ////////////////////////// data member OldResourceUsername

    /**
     * Get OldResourceUsername of the AssignmentEventAudits
     *
     * @return OldResourceUsername of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getOldResourceUsername () 
    throws DataObjectException;
    

    /**
     * Set OldResourceUsername of the AssignmentEventAudits
     *
     * @param OldResourceUsername of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setOldResourceUsername ( String OldResourceUsername )
    throws DataObjectException;

    ////////////////////////// data member OldResourceName

    /**
     * Get OldResourceName of the AssignmentEventAudits
     *
     * @return OldResourceName of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getOldResourceName () 
    throws DataObjectException;
    

    /**
     * Set OldResourceName of the AssignmentEventAudits
     *
     * @param OldResourceName of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setOldResourceName ( String OldResourceName )
    throws DataObjectException;

    ////////////////////////// data member NewResourceUsername

    /**
     * Get NewResourceUsername of the AssignmentEventAudits
     *
     * @return NewResourceUsername of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getNewResourceUsername () 
    throws DataObjectException;
    

    /**
     * Set NewResourceUsername of the AssignmentEventAudits
     *
     * @param NewResourceUsername of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setNewResourceUsername ( String NewResourceUsername )
    throws DataObjectException;

    ////////////////////////// data member NewResourceName

    /**
     * Get NewResourceName of the AssignmentEventAudits
     *
     * @return NewResourceName of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getNewResourceName () 
    throws DataObjectException;
    

    /**
     * Set NewResourceName of the AssignmentEventAudits
     *
     * @param NewResourceName of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setNewResourceName ( String NewResourceName )
    throws DataObjectException;

    ////////////////////////// data member IsAccepted

    /**
     * Get IsAccepted of the AssignmentEventAudits
     *
     * @return IsAccepted of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public boolean getIsAccepted () 
    throws DataObjectException;
    

    /**
     * Set IsAccepted of the AssignmentEventAudits
     *
     * @param IsAccepted of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setIsAccepted ( boolean IsAccepted )
    throws DataObjectException;

    ////////////////////////// data member CNT

    /**
     * Get CNT of the AssignmentEventAudits
     *
     * @return CNT of the AssignmentEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public java.math.BigDecimal getCNT () 
    throws DataObjectException;
    

    /**
     * Set CNT of the AssignmentEventAudits
     *
     * @param CNT of the AssignmentEventAudits
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
