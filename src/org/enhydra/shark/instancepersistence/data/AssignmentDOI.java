
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
 * org.enhydra.shark.instancepersistence.data/AssignmentDOI.java
 *-----------------------------------------------------------------------------
 */

package org.enhydra.shark.instancepersistence.data;

import java.io.*;
import java.sql.*;
import java.math.*;
import com.lutris.appserver.server.sql.*;
import com.lutris.dods.builder.generator.query.*;

/**
 * Interface implemented by AssignmentDO
 * Interface could also be implemented by a (hand-written) Business Layer class
 * which uses AssignmentDO
 *
 * @version $Revision: 1.6 $
 * @author  NN
 * @since   DODS Project
 */
public interface AssignmentDOI {

    ////////////////////////// data member Activity

    /**
     * Get Activity of the AssignmentsTable
     *
     * @return Activity of the AssignmentsTable
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.instancepersistence.data.ActivityDO getActivity () 
    throws DataObjectException;
    

    /**
     * Set Activity of the AssignmentsTable
     *
     * @param Activity of the AssignmentsTable
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivity ( org.enhydra.shark.instancepersistence.data.ActivityDO Activity )
    throws DataObjectException;

    ////////////////////////// data member TheResource

    /**
     * Get TheResource of the AssignmentsTable
     *
     * @return TheResource of the AssignmentsTable
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.instancepersistence.data.ResourceDO getTheResource () 
    throws DataObjectException;
    

    /**
     * Set TheResource of the AssignmentsTable
     *
     * @param TheResource of the AssignmentsTable
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setTheResource ( org.enhydra.shark.instancepersistence.data.ResourceDO TheResource )
    throws DataObjectException;

    ////////////////////////// data member ActivityId

    /**
     * Get ActivityId of the AssignmentsTable
     *
     * @return ActivityId of the AssignmentsTable
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getActivityId () 
    throws DataObjectException;
    

    /**
     * Set ActivityId of the AssignmentsTable
     *
     * @param ActivityId of the AssignmentsTable
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivityId ( String ActivityId )
    throws DataObjectException;

    ////////////////////////// data member ActivityProcessId

    /**
     * Get ActivityProcessId of the AssignmentsTable
     *
     * @return ActivityProcessId of the AssignmentsTable
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getActivityProcessId () 
    throws DataObjectException;
    

    /**
     * Set ActivityProcessId of the AssignmentsTable
     *
     * @param ActivityProcessId of the AssignmentsTable
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivityProcessId ( String ActivityProcessId )
    throws DataObjectException;

    ////////////////////////// data member ActivityProcessDefName

    /**
     * Get ActivityProcessDefName of the AssignmentsTable
     *
     * @return ActivityProcessDefName of the AssignmentsTable
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getActivityProcessDefName () 
    throws DataObjectException;
    

    /**
     * Set ActivityProcessDefName of the AssignmentsTable
     *
     * @param ActivityProcessDefName of the AssignmentsTable
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivityProcessDefName ( String ActivityProcessDefName )
    throws DataObjectException;

    ////////////////////////// data member ResourceId

    /**
     * Get ResourceId of the AssignmentsTable
     *
     * @return ResourceId of the AssignmentsTable
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getResourceId () 
    throws DataObjectException;
    

    /**
     * Set ResourceId of the AssignmentsTable
     *
     * @param ResourceId of the AssignmentsTable
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setResourceId ( String ResourceId )
    throws DataObjectException;

    ////////////////////////// data member IsAccepted

    /**
     * Get IsAccepted of the AssignmentsTable
     *
     * @return IsAccepted of the AssignmentsTable
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public boolean getIsAccepted () 
    throws DataObjectException;
    

    /**
     * Set IsAccepted of the AssignmentsTable
     *
     * @param IsAccepted of the AssignmentsTable
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setIsAccepted ( boolean IsAccepted )
    throws DataObjectException;

    ////////////////////////// data member IsValid

    /**
     * Get IsValid of the AssignmentsTable
     *
     * @return IsValid of the AssignmentsTable
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public boolean getIsValid () 
    throws DataObjectException;
    

    /**
     * Set IsValid of the AssignmentsTable
     *
     * @param IsValid of the AssignmentsTable
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setIsValid ( boolean IsValid )
    throws DataObjectException;

    ////////////////////////// data member CNT

    /**
     * Get CNT of the AssignmentsTable
     *
     * @return CNT of the AssignmentsTable
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public java.math.BigDecimal getCNT () 
    throws DataObjectException;
    

    /**
     * Set CNT of the AssignmentsTable
     *
     * @param CNT of the AssignmentsTable
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
