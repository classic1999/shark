
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
 * org.enhydra.shark.instancepersistence.data/ProcessDataWOBDOI.java
 *-----------------------------------------------------------------------------
 */

package org.enhydra.shark.instancepersistence.data;

import java.io.*;
import java.sql.*;
import java.math.*;
import com.lutris.appserver.server.sql.*;
import com.lutris.dods.builder.generator.query.*;

/**
 * Interface implemented by ProcessDataWOBDO
 * Interface could also be implemented by a (hand-written) Business Layer class
 * which uses ProcessDataWOBDO
 *
 * @version $Revision: 1.6 $
 * @author  NN
 * @since   DODS Project
 */
public interface ProcessDataWOBDOI {

    ////////////////////////// data member Process

    /**
     * Get Process of the ProcessDataWOB
     *
     * @return Process of the ProcessDataWOB
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.instancepersistence.data.ProcessDO getProcess () 
    throws DataObjectException;
    

    /**
     * Set Process of the ProcessDataWOB
     *
     * @param Process of the ProcessDataWOB
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcess ( org.enhydra.shark.instancepersistence.data.ProcessDO Process )
    throws DataObjectException;

    ////////////////////////// data member VariableDefinitionId

    /**
     * Get VariableDefinitionId of the ProcessDataWOB
     *
     * @return VariableDefinitionId of the ProcessDataWOB
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getVariableDefinitionId () 
    throws DataObjectException;
    

    /**
     * Set VariableDefinitionId of the ProcessDataWOB
     *
     * @param VariableDefinitionId of the ProcessDataWOB
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setVariableDefinitionId ( String VariableDefinitionId )
    throws DataObjectException;

    ////////////////////////// data member VariableType

    /**
     * Get VariableType of the ProcessDataWOB
     *
     * @return VariableType of the ProcessDataWOB
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public short getVariableType () 
    throws DataObjectException;
    

    /**
     * Set VariableType of the ProcessDataWOB
     *
     * @param VariableType of the ProcessDataWOB
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setVariableType ( short VariableType )
    throws DataObjectException;

    ////////////////////////// data member VariableValueVCHAR

    /**
     * Get VariableValueVCHAR of the ProcessDataWOB
     *
     * @return VariableValueVCHAR of the ProcessDataWOB
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getVariableValueVCHAR () 
    throws DataObjectException;
    

    /**
     * Set VariableValueVCHAR of the ProcessDataWOB
     *
     * @param VariableValueVCHAR of the ProcessDataWOB
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setVariableValueVCHAR ( String VariableValueVCHAR )
    throws DataObjectException;

    ////////////////////////// data member VariableValueDBL

    /**
     * Get VariableValueDBL of the ProcessDataWOB
     *
     * @return VariableValueDBL of the ProcessDataWOB
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public double getVariableValueDBL () 
    throws DataObjectException;
    

    /**
     * Set VariableValueDBL of the ProcessDataWOB
     *
     * @param VariableValueDBL of the ProcessDataWOB
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setVariableValueDBL ( double VariableValueDBL )
    throws DataObjectException;

    ////////////////////////// data member VariableValueLONG

    /**
     * Get VariableValueLONG of the ProcessDataWOB
     *
     * @return VariableValueLONG of the ProcessDataWOB
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public long getVariableValueLONG () 
    throws DataObjectException;
    

    /**
     * Set VariableValueLONG of the ProcessDataWOB
     *
     * @param VariableValueLONG of the ProcessDataWOB
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setVariableValueLONG ( long VariableValueLONG )
    throws DataObjectException;

    ////////////////////////// data member VariableValueDATE

    /**
     * Get VariableValueDATE of the ProcessDataWOB
     *
     * @return VariableValueDATE of the ProcessDataWOB
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public java.sql.Timestamp getVariableValueDATE () 
    throws DataObjectException;
    

    /**
     * Set VariableValueDATE of the ProcessDataWOB
     *
     * @param VariableValueDATE of the ProcessDataWOB
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setVariableValueDATE ( java.sql.Timestamp VariableValueDATE )
    throws DataObjectException;

    ////////////////////////// data member VariableValueBOOL

    /**
     * Get VariableValueBOOL of the ProcessDataWOB
     *
     * @return VariableValueBOOL of the ProcessDataWOB
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public boolean getVariableValueBOOL () 
    throws DataObjectException;
    

    /**
     * Set VariableValueBOOL of the ProcessDataWOB
     *
     * @param VariableValueBOOL of the ProcessDataWOB
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setVariableValueBOOL ( boolean VariableValueBOOL )
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
