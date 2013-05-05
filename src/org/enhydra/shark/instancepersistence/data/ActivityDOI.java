
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
 * org.enhydra.shark.instancepersistence.data/ActivityDOI.java
 *-----------------------------------------------------------------------------
 */

package org.enhydra.shark.instancepersistence.data;

import java.io.*;
import java.sql.*;
import java.math.*;
import com.lutris.appserver.server.sql.*;
import com.lutris.dods.builder.generator.query.*;

/**
 * Interface implemented by ActivityDO
 * Interface could also be implemented by a (hand-written) Business Layer class
 * which uses ActivityDO
 *
 * @version $Revision: 1.6 $
 * @author  NN
 * @since   DODS Project
 */
public interface ActivityDOI {

    ////////////////////////// data member Id

    /**
     * Get Id of the Activities
     *
     * @return Id of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getId () 
    throws DataObjectException;
    

    /**
     * Set Id of the Activities
     *
     * @param Id of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setId ( String Id )
    throws DataObjectException;

    ////////////////////////// data member ActivitySetDefinitionId

    /**
     * Get ActivitySetDefinitionId of the Activities
     *
     * @return ActivitySetDefinitionId of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getActivitySetDefinitionId () 
    throws DataObjectException;
    

    /**
     * Set ActivitySetDefinitionId of the Activities
     *
     * @param ActivitySetDefinitionId of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivitySetDefinitionId ( String ActivitySetDefinitionId )
    throws DataObjectException;

    ////////////////////////// data member ActivityDefinitionId

    /**
     * Get ActivityDefinitionId of the Activities
     *
     * @return ActivityDefinitionId of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getActivityDefinitionId () 
    throws DataObjectException;
    

    /**
     * Set ActivityDefinitionId of the Activities
     *
     * @param ActivityDefinitionId of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivityDefinitionId ( String ActivityDefinitionId )
    throws DataObjectException;

    ////////////////////////// data member Process

    /**
     * Get Process of the Activities
     *
     * @return Process of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.instancepersistence.data.ProcessDO getProcess () 
    throws DataObjectException;
    

    /**
     * Set Process of the Activities
     *
     * @param Process of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcess ( org.enhydra.shark.instancepersistence.data.ProcessDO Process )
    throws DataObjectException;

    ////////////////////////// data member TheResource

    /**
     * Get TheResource of the Activities
     *
     * @return TheResource of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.instancepersistence.data.ResourceDO getTheResource () 
    throws DataObjectException;
    

    /**
     * Set TheResource of the Activities
     *
     * @param TheResource of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setTheResource ( org.enhydra.shark.instancepersistence.data.ResourceDO TheResource )
    throws DataObjectException;

    ////////////////////////// data member PDefName

    /**
     * Get PDefName of the Activities
     *
     * @return PDefName of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPDefName () 
    throws DataObjectException;
    

    /**
     * Set PDefName of the Activities
     *
     * @param PDefName of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPDefName ( String PDefName )
    throws DataObjectException;

    ////////////////////////// data member ProcessId

    /**
     * Get ProcessId of the Activities
     *
     * @return ProcessId of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessId () 
    throws DataObjectException;
    

    /**
     * Set ProcessId of the Activities
     *
     * @param ProcessId of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessId ( String ProcessId )
    throws DataObjectException;

    ////////////////////////// data member ResourceId

    /**
     * Get ResourceId of the Activities
     *
     * @return ResourceId of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getResourceId () 
    throws DataObjectException;
    

    /**
     * Set ResourceId of the Activities
     *
     * @param ResourceId of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setResourceId ( String ResourceId )
    throws DataObjectException;

    ////////////////////////// data member State

    /**
     * Get State of the Activities
     *
     * @return State of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.instancepersistence.data.ActivityStateDO getState () 
    throws DataObjectException;
    

    /**
     * Set State of the Activities
     *
     * @param State of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setState ( org.enhydra.shark.instancepersistence.data.ActivityStateDO State )
    throws DataObjectException;

    ////////////////////////// data member BlockActivityId

    /**
     * Get BlockActivityId of the Activities
     *
     * @return BlockActivityId of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getBlockActivityId () 
    throws DataObjectException;
    

    /**
     * Set BlockActivityId of the Activities
     *
     * @param BlockActivityId of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setBlockActivityId ( String BlockActivityId )
    throws DataObjectException;

    ////////////////////////// data member Performer

    /**
     * Get Performer of the Activities
     *
     * @return Performer of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPerformer () 
    throws DataObjectException;
    

    /**
     * Set Performer of the Activities
     *
     * @param Performer of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPerformer ( String Performer )
    throws DataObjectException;

    ////////////////////////// data member IsPerformerAsynchronous

    /**
     * Get IsPerformerAsynchronous of the Activities
     *
     * @return IsPerformerAsynchronous of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public boolean getIsPerformerAsynchronous () 
    throws DataObjectException;
    

    /**
     * Set IsPerformerAsynchronous of the Activities
     *
     * @param IsPerformerAsynchronous of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setIsPerformerAsynchronous ( boolean IsPerformerAsynchronous )
    throws DataObjectException;

    ////////////////////////// data member Priority

    /**
     * Get Priority of the Activities
     *
     * @return Priority of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public short getPriority () 
    throws DataObjectException;
    

    /**
     * Set Priority of the Activities
     *
     * @param Priority of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPriority ( short Priority )
    throws DataObjectException;

    ////////////////////////// data member Name

    /**
     * Get Name of the Activities
     *
     * @return Name of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getName () 
    throws DataObjectException;
    

    /**
     * Set Name of the Activities
     *
     * @param Name of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setName ( String Name )
    throws DataObjectException;

    ////////////////////////// data member Activated

    /**
     * Get Activated of the Activities
     *
     * @return Activated of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public long getActivated () 
    throws DataObjectException;
    

    /**
     * Set Activated of the Activities
     *
     * @param Activated of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivated ( long Activated )
    throws DataObjectException;

    ////////////////////////// data member Accepted

    /**
     * Get Accepted of the Activities
     *
     * @return Accepted of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public long getAccepted () 
    throws DataObjectException;
    

    /**
     * Set Accepted of the Activities
     *
     * @param Accepted of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setAccepted ( long Accepted )
    throws DataObjectException;

    ////////////////////////// data member LastStateTime

    /**
     * Get LastStateTime of the Activities
     *
     * @return LastStateTime of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public long getLastStateTime () 
    throws DataObjectException;
    

    /**
     * Set LastStateTime of the Activities
     *
     * @param LastStateTime of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setLastStateTime ( long LastStateTime )
    throws DataObjectException;

    ////////////////////////// data member LimitTime

    /**
     * Get LimitTime of the Activities
     *
     * @return LimitTime of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public long getLimitTime () 
    throws DataObjectException;
    

    /**
     * Set LimitTime of the Activities
     *
     * @param LimitTime of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setLimitTime ( long LimitTime )
    throws DataObjectException;

    ////////////////////////// data member Description

    /**
     * Get Description of the Activities
     *
     * @return Description of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getDescription () 
    throws DataObjectException;
    

    /**
     * Set Description of the Activities
     *
     * @param Description of the Activities
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setDescription ( String Description )
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
