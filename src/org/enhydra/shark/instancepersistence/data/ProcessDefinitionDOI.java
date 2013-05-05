
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
 * org.enhydra.shark.instancepersistence.data/ProcessDefinitionDOI.java
 *-----------------------------------------------------------------------------
 */

package org.enhydra.shark.instancepersistence.data;

import java.io.*;
import java.sql.*;
import java.math.*;
import com.lutris.appserver.server.sql.*;
import com.lutris.dods.builder.generator.query.*;

/**
 * Interface implemented by ProcessDefinitionDO
 * Interface could also be implemented by a (hand-written) Business Layer class
 * which uses ProcessDefinitionDO
 *
 * @version $Revision: 1.6 $
 * @author  NN
 * @since   DODS Project
 */
public interface ProcessDefinitionDOI {

    ////////////////////////// data member Name

    /**
     * Get Name of the ProcessDefinitions
     *
     * @return Name of the ProcessDefinitions
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getName () 
    throws DataObjectException;
    

    /**
     * Set Name of the ProcessDefinitions
     *
     * @param Name of the ProcessDefinitions
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setName ( String Name )
    throws DataObjectException;

    ////////////////////////// data member PackageId

    /**
     * Get PackageId of the ProcessDefinitions
     *
     * @return PackageId of the ProcessDefinitions
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getPackageId () 
    throws DataObjectException;
    

    /**
     * Set PackageId of the ProcessDefinitions
     *
     * @param PackageId of the ProcessDefinitions
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setPackageId ( String PackageId )
    throws DataObjectException;

    ////////////////////////// data member ProcessDefinitionId

    /**
     * Get ProcessDefinitionId of the ProcessDefinitions
     *
     * @return ProcessDefinitionId of the ProcessDefinitions
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessDefinitionId () 
    throws DataObjectException;
    

    /**
     * Set ProcessDefinitionId of the ProcessDefinitions
     *
     * @param ProcessDefinitionId of the ProcessDefinitions
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessDefinitionId ( String ProcessDefinitionId )
    throws DataObjectException;

    ////////////////////////// data member ProcessDefinitionCreated

    /**
     * Get ProcessDefinitionCreated of the ProcessDefinitions
     *
     * @return ProcessDefinitionCreated of the ProcessDefinitions
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public long getProcessDefinitionCreated () 
    throws DataObjectException;
    

    /**
     * Set ProcessDefinitionCreated of the ProcessDefinitions
     *
     * @param ProcessDefinitionCreated of the ProcessDefinitions
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessDefinitionCreated ( long ProcessDefinitionCreated )
    throws DataObjectException;

    ////////////////////////// data member ProcessDefinitionVersion

    /**
     * Get ProcessDefinitionVersion of the ProcessDefinitions
     *
     * @return ProcessDefinitionVersion of the ProcessDefinitions
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getProcessDefinitionVersion () 
    throws DataObjectException;
    

    /**
     * Set ProcessDefinitionVersion of the ProcessDefinitions
     *
     * @param ProcessDefinitionVersion of the ProcessDefinitions
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcessDefinitionVersion ( String ProcessDefinitionVersion )
    throws DataObjectException;

    ////////////////////////// data member State

    /**
     * Get State of the ProcessDefinitions
     *
     * @return State of the ProcessDefinitions
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public int getState () 
    throws DataObjectException;
    

    /**
     * Set State of the ProcessDefinitions
     *
     * @param State of the ProcessDefinitions
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setState ( int State )
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
