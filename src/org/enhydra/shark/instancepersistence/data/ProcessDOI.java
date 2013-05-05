
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
 * org.enhydra.shark.instancepersistence.data/ProcessDOI.java
 *-----------------------------------------------------------------------------
 */

package org.enhydra.shark.instancepersistence.data;

import java.io.*;
import java.sql.*;
import java.math.*;
import com.lutris.appserver.server.sql.*;
import com.lutris.dods.builder.generator.query.*;

/**
 * Interface implemented by ProcessDO
 * Interface could also be implemented by a (hand-written) Business Layer class
 * which uses ProcessDO
 *
 * @version $Revision: 1.6 $
 * @author  NN
 * @since   DODS Project
 */
public interface ProcessDOI {

    ////////////////////////// data member Id

    /**
     * Get Id of the Processes
     *
     * @return Id of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getId () 
    throws DataObjectException;
    

    /**
     * Set Id of the Processes
     *
     * @param Id of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setId ( String Id )
    throws DataObjectException;

    ////////////////////////// data member ProcessDefinition

    /**
     * Get ProcessDefinition of the Processes
     *
     * @return ProcessDefinition of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.instancepersistence.data.ProcessDefinitionDO getProcessDefinition () 
    throws DataObjectException;
    

    /**
     * Set ProcessDefinition of the Processes
     *
     * @param ProcessDefinition of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessDefinition ( org.enhydra.shark.instancepersistence.data.ProcessDefinitionDO ProcessDefinition )
    throws DataObjectException;

    ////////////////////////// data member PDefName

    /**
     * Get PDefName of the Processes
     *
     * @return PDefName of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPDefName () 
    throws DataObjectException;
    

    /**
     * Set PDefName of the Processes
     *
     * @param PDefName of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPDefName ( String PDefName )
    throws DataObjectException;

    ////////////////////////// data member ActivityRequesterId

    /**
     * Get ActivityRequesterId of the Processes
     *
     * @return ActivityRequesterId of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getActivityRequesterId () 
    throws DataObjectException;
    

    /**
     * Set ActivityRequesterId of the Processes
     *
     * @param ActivityRequesterId of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivityRequesterId ( String ActivityRequesterId )
    throws DataObjectException;

    ////////////////////////// data member ActivityRequesterProcessId

    /**
     * Get ActivityRequesterProcessId of the Processes
     *
     * @return ActivityRequesterProcessId of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getActivityRequesterProcessId () 
    throws DataObjectException;
    

    /**
     * Set ActivityRequesterProcessId of the Processes
     *
     * @param ActivityRequesterProcessId of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivityRequesterProcessId ( String ActivityRequesterProcessId )
    throws DataObjectException;

    ////////////////////////// data member ResourceRequesterId

    /**
     * Get ResourceRequesterId of the Processes
     *
     * @return ResourceRequesterId of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getResourceRequesterId () 
    throws DataObjectException;
    

    /**
     * Set ResourceRequesterId of the Processes
     *
     * @param ResourceRequesterId of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setResourceRequesterId ( String ResourceRequesterId )
    throws DataObjectException;

    ////////////////////////// data member ExternalRequesterClassName

    /**
     * Get ExternalRequesterClassName of the Processes
     *
     * @return ExternalRequesterClassName of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getExternalRequesterClassName () 
    throws DataObjectException;
    

    /**
     * Set ExternalRequesterClassName of the Processes
     *
     * @param ExternalRequesterClassName of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setExternalRequesterClassName ( String ExternalRequesterClassName )
    throws DataObjectException;

    ////////////////////////// data member State

    /**
     * Get State of the Processes
     *
     * @return State of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.instancepersistence.data.ProcessStateDO getState () 
    throws DataObjectException;
    

    /**
     * Set State of the Processes
     *
     * @param State of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setState ( org.enhydra.shark.instancepersistence.data.ProcessStateDO State )
    throws DataObjectException;

    ////////////////////////// data member Priority

    /**
     * Get Priority of the Processes
     *
     * @return Priority of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public short getPriority () 
    throws DataObjectException;
    

    /**
     * Set Priority of the Processes
     *
     * @param Priority of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPriority ( short Priority )
    throws DataObjectException;

    ////////////////////////// data member Name

    /**
     * Get Name of the Processes
     *
     * @return Name of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getName () 
    throws DataObjectException;
    

    /**
     * Set Name of the Processes
     *
     * @param Name of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setName ( String Name )
    throws DataObjectException;

    ////////////////////////// data member Created

    /**
     * Get Created of the Processes
     *
     * @return Created of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public long getCreated () 
    throws DataObjectException;
    

    /**
     * Set Created of the Processes
     *
     * @param Created of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setCreated ( long Created )
    throws DataObjectException;

    ////////////////////////// data member Started

    /**
     * Get Started of the Processes
     *
     * @return Started of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public long getStarted () 
    throws DataObjectException;
    

    /**
     * Set Started of the Processes
     *
     * @param Started of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setStarted ( long Started )
    throws DataObjectException;

    ////////////////////////// data member LastStateTime

    /**
     * Get LastStateTime of the Processes
     *
     * @return LastStateTime of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public long getLastStateTime () 
    throws DataObjectException;
    

    /**
     * Set LastStateTime of the Processes
     *
     * @param LastStateTime of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setLastStateTime ( long LastStateTime )
    throws DataObjectException;

    ////////////////////////// data member LimitTime

    /**
     * Get LimitTime of the Processes
     *
     * @return LimitTime of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public long getLimitTime () 
    throws DataObjectException;
    

    /**
     * Set LimitTime of the Processes
     *
     * @param LimitTime of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setLimitTime ( long LimitTime )
    throws DataObjectException;

    ////////////////////////// data member Description

    /**
     * Get Description of the Processes
     *
     * @return Description of the Processes
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getDescription () 
    throws DataObjectException;
    

    /**
     * Set Description of the Processes
     *
     * @param Description of the Processes
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
