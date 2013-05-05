
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
 * org.enhydra.shark.eventaudit.data/CreateProcessEventAuditDOI.java
 *-----------------------------------------------------------------------------
 */

package org.enhydra.shark.eventaudit.data;

import java.io.*;
import java.sql.*;
import java.math.*;
import com.lutris.appserver.server.sql.*;
import com.lutris.dods.builder.generator.query.*;

/**
 * Interface implemented by CreateProcessEventAuditDO
 * Interface could also be implemented by a (hand-written) Business Layer class
 * which uses CreateProcessEventAuditDO
 *
 * @version $Revision: 1.6 $
 * @author  NN
 * @since   DODS Project
 */
public interface CreateProcessEventAuditDOI {

    ////////////////////////// data member UTCTime

    /**
     * Get UTCTime of the CreateProcessEventAudits
     *
     * @return UTCTime of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getUTCTime () 
    throws DataObjectException;
    

    /**
     * Set UTCTime of the CreateProcessEventAudits
     *
     * @param UTCTime of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setUTCTime ( String UTCTime )
    throws DataObjectException;

    ////////////////////////// data member TheType

    /**
     * Get TheType of the CreateProcessEventAudits
     *
     * @return TheType of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.eventaudit.data.EventTypeDO getTheType () 
    throws DataObjectException;
    

    /**
     * Set TheType of the CreateProcessEventAudits
     *
     * @param TheType of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setTheType ( org.enhydra.shark.eventaudit.data.EventTypeDO TheType )
    throws DataObjectException;

    ////////////////////////// data member ProcessId

    /**
     * Get ProcessId of the CreateProcessEventAudits
     *
     * @return ProcessId of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessId () 
    throws DataObjectException;
    

    /**
     * Set ProcessId of the CreateProcessEventAudits
     *
     * @param ProcessId of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessId ( String ProcessId )
    throws DataObjectException;

    ////////////////////////// data member ProcessName

    /**
     * Get ProcessName of the CreateProcessEventAudits
     *
     * @return ProcessName of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessName () 
    throws DataObjectException;
    

    /**
     * Set ProcessName of the CreateProcessEventAudits
     *
     * @param ProcessName of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessName ( String ProcessName )
    throws DataObjectException;

    ////////////////////////// data member ProcessDefinitionName

    /**
     * Get ProcessDefinitionName of the CreateProcessEventAudits
     *
     * @return ProcessDefinitionName of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessDefinitionName () 
    throws DataObjectException;
    

    /**
     * Set ProcessDefinitionName of the CreateProcessEventAudits
     *
     * @param ProcessDefinitionName of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessDefinitionName ( String ProcessDefinitionName )
    throws DataObjectException;

    ////////////////////////// data member ProcessDefinitionVersion

    /**
     * Get ProcessDefinitionVersion of the CreateProcessEventAudits
     *
     * @return ProcessDefinitionVersion of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessDefinitionVersion () 
    throws DataObjectException;
    

    /**
     * Set ProcessDefinitionVersion of the CreateProcessEventAudits
     *
     * @param ProcessDefinitionVersion of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessDefinitionVersion ( String ProcessDefinitionVersion )
    throws DataObjectException;

    ////////////////////////// data member ProcessDefinitionId

    /**
     * Get ProcessDefinitionId of the CreateProcessEventAudits
     *
     * @return ProcessDefinitionId of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessDefinitionId () 
    throws DataObjectException;
    

    /**
     * Set ProcessDefinitionId of the CreateProcessEventAudits
     *
     * @param ProcessDefinitionId of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessDefinitionId ( String ProcessDefinitionId )
    throws DataObjectException;

    ////////////////////////// data member PackageId

    /**
     * Get PackageId of the CreateProcessEventAudits
     *
     * @return PackageId of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPackageId () 
    throws DataObjectException;
    

    /**
     * Set PackageId of the CreateProcessEventAudits
     *
     * @param PackageId of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPackageId ( String PackageId )
    throws DataObjectException;

    ////////////////////////// data member PActivityId

    /**
     * Get PActivityId of the CreateProcessEventAudits
     *
     * @return PActivityId of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPActivityId () 
    throws DataObjectException;
    

    /**
     * Set PActivityId of the CreateProcessEventAudits
     *
     * @param PActivityId of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPActivityId ( String PActivityId )
    throws DataObjectException;

    ////////////////////////// data member PProcessId

    /**
     * Get PProcessId of the CreateProcessEventAudits
     *
     * @return PProcessId of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPProcessId () 
    throws DataObjectException;
    

    /**
     * Set PProcessId of the CreateProcessEventAudits
     *
     * @param PProcessId of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPProcessId ( String PProcessId )
    throws DataObjectException;

    ////////////////////////// data member PProcessName

    /**
     * Get PProcessName of the CreateProcessEventAudits
     *
     * @return PProcessName of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPProcessName () 
    throws DataObjectException;
    

    /**
     * Set PProcessName of the CreateProcessEventAudits
     *
     * @param PProcessName of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPProcessName ( String PProcessName )
    throws DataObjectException;

    ////////////////////////// data member PProcessDefinitionName

    /**
     * Get PProcessDefinitionName of the CreateProcessEventAudits
     *
     * @return PProcessDefinitionName of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPProcessDefinitionName () 
    throws DataObjectException;
    

    /**
     * Set PProcessDefinitionName of the CreateProcessEventAudits
     *
     * @param PProcessDefinitionName of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPProcessDefinitionName ( String PProcessDefinitionName )
    throws DataObjectException;

    ////////////////////////// data member PProcessDefinitionVersion

    /**
     * Get PProcessDefinitionVersion of the CreateProcessEventAudits
     *
     * @return PProcessDefinitionVersion of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPProcessDefinitionVersion () 
    throws DataObjectException;
    

    /**
     * Set PProcessDefinitionVersion of the CreateProcessEventAudits
     *
     * @param PProcessDefinitionVersion of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPProcessDefinitionVersion ( String PProcessDefinitionVersion )
    throws DataObjectException;

    ////////////////////////// data member PActivityDefinitionId

    /**
     * Get PActivityDefinitionId of the CreateProcessEventAudits
     *
     * @return PActivityDefinitionId of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPActivityDefinitionId () 
    throws DataObjectException;
    

    /**
     * Set PActivityDefinitionId of the CreateProcessEventAudits
     *
     * @param PActivityDefinitionId of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPActivityDefinitionId ( String PActivityDefinitionId )
    throws DataObjectException;

    ////////////////////////// data member PActivitySetDefinitionId

    /**
     * Get PActivitySetDefinitionId of the CreateProcessEventAudits
     *
     * @return PActivitySetDefinitionId of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPActivitySetDefinitionId () 
    throws DataObjectException;
    

    /**
     * Set PActivitySetDefinitionId of the CreateProcessEventAudits
     *
     * @param PActivitySetDefinitionId of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPActivitySetDefinitionId ( String PActivitySetDefinitionId )
    throws DataObjectException;

    ////////////////////////// data member PProcessDefinitionId

    /**
     * Get PProcessDefinitionId of the CreateProcessEventAudits
     *
     * @return PProcessDefinitionId of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPProcessDefinitionId () 
    throws DataObjectException;
    

    /**
     * Set PProcessDefinitionId of the CreateProcessEventAudits
     *
     * @param PProcessDefinitionId of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPProcessDefinitionId ( String PProcessDefinitionId )
    throws DataObjectException;

    ////////////////////////// data member PPackageId

    /**
     * Get PPackageId of the CreateProcessEventAudits
     *
     * @return PPackageId of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPPackageId () 
    throws DataObjectException;
    

    /**
     * Set PPackageId of the CreateProcessEventAudits
     *
     * @param PPackageId of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPPackageId ( String PPackageId )
    throws DataObjectException;

    ////////////////////////// data member CNT

    /**
     * Get CNT of the CreateProcessEventAudits
     *
     * @return CNT of the CreateProcessEventAudits
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public java.math.BigDecimal getCNT () 
    throws DataObjectException;
    

    /**
     * Set CNT of the CreateProcessEventAudits
     *
     * @param CNT of the CreateProcessEventAudits
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
