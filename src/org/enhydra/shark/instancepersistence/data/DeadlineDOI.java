
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
 * org.enhydra.shark.instancepersistence.data/DeadlineDOI.java
 *-----------------------------------------------------------------------------
 */

package org.enhydra.shark.instancepersistence.data;

import java.io.*;
import java.sql.*;
import java.math.*;
import com.lutris.appserver.server.sql.*;
import com.lutris.dods.builder.generator.query.*;

/**
 * Interface implemented by DeadlineDO
 * Interface could also be implemented by a (hand-written) Business Layer class
 * which uses DeadlineDO
 *
 * @version $Revision: 1.6 $
 * @author  NN
 * @since   DODS Project
 */
public interface DeadlineDOI {

    ////////////////////////// data member Process

    /**
     * Get Process of the Deadlines
     *
     * @return Process of the Deadlines
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.instancepersistence.data.ProcessDO getProcess () 
    throws DataObjectException;
    

    /**
     * Set Process of the Deadlines
     *
     * @param Process of the Deadlines
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setProcess ( org.enhydra.shark.instancepersistence.data.ProcessDO Process )
    throws DataObjectException;

    ////////////////////////// data member Activity

    /**
     * Get Activity of the Deadlines
     *
     * @return Activity of the Deadlines
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public org.enhydra.shark.instancepersistence.data.ActivityDO getActivity () 
    throws DataObjectException;
    

    /**
     * Set Activity of the Deadlines
     *
     * @param Activity of the Deadlines
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setActivity ( org.enhydra.shark.instancepersistence.data.ActivityDO Activity )
    throws DataObjectException;

    ////////////////////////// data member CNT

    /**
     * Get CNT of the Deadlines
     *
     * @return CNT of the Deadlines
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public java.math.BigDecimal getCNT () 
    throws DataObjectException;
    

    /**
     * Set CNT of the Deadlines
     *
     * @param CNT of the Deadlines
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setCNT ( java.math.BigDecimal CNT )
    throws DataObjectException;

    ////////////////////////// data member TimeLimit

    /**
     * Get TimeLimit of the Deadlines
     *
     * @return TimeLimit of the Deadlines
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public long getTimeLimit () 
    throws DataObjectException;
    

    /**
     * Set TimeLimit of the Deadlines
     *
     * @param TimeLimit of the Deadlines
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setTimeLimit ( long TimeLimit )
    throws DataObjectException;

    ////////////////////////// data member ExceptionName

    /**
     * Get ExceptionName of the Deadlines
     *
     * @return ExceptionName of the Deadlines
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getExceptionName () 
    throws DataObjectException;
    

    /**
     * Set ExceptionName of the Deadlines
     *
     * @param ExceptionName of the Deadlines
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setExceptionName ( String ExceptionName )
    throws DataObjectException;

    ////////////////////////// data member IsSynchronous

    /**
     * Get IsSynchronous of the Deadlines
     *
     * @return IsSynchronous of the Deadlines
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public boolean getIsSynchronous () 
    throws DataObjectException;
    

    /**
     * Set IsSynchronous of the Deadlines
     *
     * @param IsSynchronous of the Deadlines
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setIsSynchronous ( boolean IsSynchronous )
    throws DataObjectException;

    ////////////////////////// data member IsExecuted

    /**
     * Get IsExecuted of the Deadlines
     *
     * @return IsExecuted of the Deadlines
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public boolean getIsExecuted () 
    throws DataObjectException;
    

    /**
     * Set IsExecuted of the Deadlines
     *
     * @param IsExecuted of the Deadlines
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setIsExecuted ( boolean IsExecuted )
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
