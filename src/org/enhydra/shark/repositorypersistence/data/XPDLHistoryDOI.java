
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
 * org.enhydra.shark.repositorypersistence.data/XPDLHistoryDOI.java
 *-----------------------------------------------------------------------------
 */

package org.enhydra.shark.repositorypersistence.data;

import java.io.*;
import java.sql.*;
import java.math.*;
import com.lutris.appserver.server.sql.*;
import com.lutris.dods.builder.generator.query.*;

/**
 * Interface implemented by XPDLHistoryDO
 * Interface could also be implemented by a (hand-written) Business Layer class
 * which uses XPDLHistoryDO
 *
 * @version $Revision: 1.6 $
 * @author  NN
 * @since   DODS Project
 */
public interface XPDLHistoryDOI {

    ////////////////////////// data member XPDLId

    /**
     * Get XPDLId of the XPDLHistory
     *
     * @return XPDLId of the XPDLHistory
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getXPDLId () 
    throws DataObjectException;
    

    /**
     * Set XPDLId of the XPDLHistory
     *
     * @param XPDLId of the XPDLHistory
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setXPDLId ( String XPDLId )
    throws DataObjectException;

    ////////////////////////// data member XPDLVersion

    /**
     * Get XPDLVersion of the XPDLHistory
     *
     * @return XPDLVersion of the XPDLHistory
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public String getXPDLVersion () 
    throws DataObjectException;
    

    /**
     * Set XPDLVersion of the XPDLHistory
     *
     * @param XPDLVersion of the XPDLHistory
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setXPDLVersion ( String XPDLVersion )
    throws DataObjectException;

    ////////////////////////// data member XPDLClassVersion

    /**
     * Get XPDLClassVersion of the XPDLHistory
     *
     * @return XPDLClassVersion of the XPDLHistory
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public long getXPDLClassVersion () 
    throws DataObjectException;
    

    /**
     * Set XPDLClassVersion of the XPDLHistory
     *
     * @param XPDLClassVersion of the XPDLHistory
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setXPDLClassVersion ( long XPDLClassVersion )
    throws DataObjectException;

    ////////////////////////// data member XPDLUploadTime

    /**
     * Get XPDLUploadTime of the XPDLHistory
     *
     * @return XPDLUploadTime of the XPDLHistory
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public java.sql.Timestamp getXPDLUploadTime () 
    throws DataObjectException;
    

    /**
     * Set XPDLUploadTime of the XPDLHistory
     *
     * @param XPDLUploadTime of the XPDLHistory
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setXPDLUploadTime ( java.sql.Timestamp XPDLUploadTime )
    throws DataObjectException;

    ////////////////////////// data member XPDLHistoryUploadTime

    /**
     * Get XPDLHistoryUploadTime of the XPDLHistory
     *
     * @return XPDLHistoryUploadTime of the XPDLHistory
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public java.sql.Timestamp getXPDLHistoryUploadTime () 
    throws DataObjectException;
    

    /**
     * Set XPDLHistoryUploadTime of the XPDLHistory
     *
     * @param XPDLHistoryUploadTime of the XPDLHistory
     *
     * @exception DataObjectException
     *   If the object is not found in the database.
     */
    public void setXPDLHistoryUploadTime ( java.sql.Timestamp XPDLHistoryUploadTime )
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
