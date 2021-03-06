
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
 *    This product includes Enhydra software developed by Lutris
 *    Technologies, Inc. and its contributors.
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
 * org.enhydra.shark.repositorypersistence.data/XPDLHistoryDataStruct.java
 *-----------------------------------------------------------------------------
 */


package org.enhydra.shark.repositorypersistence.data;

import com.lutris.appserver.server.sql.*;
import java.sql.*;
import java.math.*;
import java.io.Serializable;
import com.lutris.dods.builder.generator.dataobject.*;
import com.lutris.dods.builder.generator.query.*;
import org.enhydra.dods.cache.Condition;


/**
 * Data structure for DO class.
 * A container for data members of a DO class.
 * A DO class contains a reference to a DataStruct class.  This reference
 * can be null (a DO whose data has not yet been retrieved from the database),
 * allowing a DO object to be a lightweight placeholder until its data is needed.
 *
 * @version $Revision: 1.9 $
 * @author  NN
 * @since   DODS Project;
 */
public class XPDLHistoryDataStruct extends CoreDataStruct implements Cloneable, Serializable {

    /**
     * A DO refers to this DataStruct.
     * readOnly is set to true when the DO is stored in its class cache.
     */
    public boolean readOnly = false;

    /**
     * Since originalData is being constructed for every DO, this flag
     * "knows" if DataStruct has any useful content.
     */
    protected boolean isEmpty = true;

    /**
     * String identifying logical database this DataStruct belongs to.
     */
    private String databaseName = null;

    private byte[] copyByteArray( byte[] source ) {
        byte[] dest = new byte[ source.length ];
        System.arraycopy( source, 0, dest, 0, source.length );
        return dest;
    }

    /**
     * Returns DataStruct's version.    
     * @return DataStruct's version.    
     */
    protected int getVersion () {
        return get_Version();
    }

    /**
     * Returns DataStruct's version.    
     * @return DataStruct's version.    
     */
    protected int get_Version () {
        return super.get_Version();
    }

    /**
     * Sets DataStruct's version.
     * @param v new DataStruct's version.
     */
    protected void setVersion (int v) {
        set_Version(v);
    }

    /**
     * Sets DataStruct's version.
     * @param v new DataStruct's version.
     */
    protected void set_Version (int v) {
        super.set_Version(v);
    }

    /**
     * Returns this object's identifier.
     * @return this object's identifier.
     */
    public ObjectId getOId() {
	   return get_OId();
    }

    /**
     * Returns this object's identifier.
     * @return this object's identifier.
     */
    public ObjectId get_OId() {
	return super.get_OId();
    }

    /**
     * @deprecated Use set_OId()
     * @param oId this object's identifier.
     */
    protected void setOId(ObjectId oId) {
    	set_OId(oId);
    }

    /**
     * Sets this object's identifier.
     * @param oId this object's identifier.
     */
    protected void set_OId(ObjectId oId) {
    	super.set_OId(oId);
        __the_handle = oId.toString();
    }
    private String __the_handle;
    

    /**
     * Returns this object's handle (identifier as a string).
     * @return This object's identifier as a string.
     *
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     */
    public String  get_Handle()
    throws DatabaseManagerException {
        String ret = null;
        if (null == __the_handle)
               throw new DatabaseManagerException( "ID not set " );
        //ret = get_OId().toString();
        return __the_handle;
    }


    /**
     * Returns this object's cache handle (String in the form:
     * "<database_name>.<indetifier_as_String>").
     *
     * @return cache handle.
     * @exception DatabaseManagerException
     *   If a connection to the database cannot be established, etc.
     */
    public String  get_CacheHandle()throws DatabaseManagerException {
        String ret = get_Database() + "." + get_Handle();
        return ret;
    }



    /**
     * @param dbName - name of the logical database this DataStruct should belong to.
     */
    public void set_Database(String dbName) {
         if (null != databaseName)
             throw new Error("Whatta hack you are doing! Multiple db setting not allowed.");
         databaseName = dbName;
    }


    /**
     * @return name of the logical database this DataStruct belongs to.
     */
    public String get_Database() {
         return databaseName;
    }

    public CoreDataStruct dumpData(boolean incrementVersion) {
        XPDLHistoryDataStruct ret =
            new XPDLHistoryDataStruct();
        ret.set_OId(get_OId());
        ret.set_Version((incrementVersion?1:0)+get_Version());
        ret.set_Database(get_Database());
        return ret;
    }
    
    private String XPDLId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_XPDLID = 0;
    

    /**
     * Sets XPDLId column.
     * @param _XPDLId new column value.
     */
    public void setXPDLId(String _XPDLId) {
        if (readOnly)
            throw new Error("This should never happen! setXPDLId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(XPDLId, _XPDLId);
        XPDLId = _XPDLId;
//        return bDiff;
    }

    /**
     * Return value of XPDLId column.
     * @return value of XPDLId column.
     */
    public String getXPDLId() {
        return XPDLId;
    }
    
    private String XPDLVersion = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_XPDLVERSION = 1;
    

    /**
     * Sets XPDLVersion column.
     * @param _XPDLVersion new column value.
     */
    public void setXPDLVersion(String _XPDLVersion) {
        if (readOnly)
            throw new Error("This should never happen! setXPDLVersion on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(XPDLVersion, _XPDLVersion);
        XPDLVersion = _XPDLVersion;
//        return bDiff;
    }

    /**
     * Return value of XPDLVersion column.
     * @return value of XPDLVersion column.
     */
    public String getXPDLVersion() {
        return XPDLVersion;
    }
    
    private long XPDLClassVersion = 0L;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_XPDLCLASSVERSION = 2;
    

    /**
     * Sets XPDLClassVersion column.
     * @param _XPDLClassVersion new column value.
     */
    public void setXPDLClassVersion(long _XPDLClassVersion) {
        if (readOnly)
            throw new Error("This should never happen! setXPDLClassVersion on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_long(XPDLClassVersion, _XPDLClassVersion);
        XPDLClassVersion = _XPDLClassVersion;
//        return bDiff;
    }

    /**
     * Return value of XPDLClassVersion column.
     * @return value of XPDLClassVersion column.
     */
    public long getXPDLClassVersion() {
        return XPDLClassVersion;
    }
    
    private java.sql.Timestamp XPDLUploadTime = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_XPDLUPLOADTIME = 3;
    

    /**
     * Sets XPDLUploadTime column.
     * @param _XPDLUploadTime new column value.
     */
    public void setXPDLUploadTime(java.sql.Timestamp _XPDLUploadTime) {
        if (readOnly)
            throw new Error("This should never happen! setXPDLUploadTime on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_java_sql_Timestamp(XPDLUploadTime, _XPDLUploadTime);
        XPDLUploadTime = _XPDLUploadTime;
//        return bDiff;
    }

    /**
     * Return value of XPDLUploadTime column.
     * @return value of XPDLUploadTime column.
     */
    public java.sql.Timestamp getXPDLUploadTime() {
        return XPDLUploadTime;
    }
    
    private java.sql.Timestamp XPDLHistoryUploadTime = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_XPDLHISTORYUPLOADTIME = 4;
    

    /**
     * Sets XPDLHistoryUploadTime column.
     * @param _XPDLHistoryUploadTime new column value.
     */
    public void setXPDLHistoryUploadTime(java.sql.Timestamp _XPDLHistoryUploadTime) {
        if (readOnly)
            throw new Error("This should never happen! setXPDLHistoryUploadTime on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_java_sql_Timestamp(XPDLHistoryUploadTime, _XPDLHistoryUploadTime);
        XPDLHistoryUploadTime = _XPDLHistoryUploadTime;
//        return bDiff;
    }

    /**
     * Return value of XPDLHistoryUploadTime column.
     * @return value of XPDLHistoryUploadTime column.
     */
    public java.sql.Timestamp getXPDLHistoryUploadTime() {
        return XPDLHistoryUploadTime;
    }

    /**
     * Used for query caching.
     */
    static public final int COLUMN_OID = 5;

    /**
     * Compares whether this DataStruct object satisfies condition cond.
     *
     * @param cond Condition of the query.
     *
     * @return true if this DataStruct object satisfies condition of this query,
     * otherwise false.
     */
    public boolean compareCond(Condition cond) {
        try {
            switch (cond.getColumnIndex()) {
              case COLUMN_XPDLID:
                   return QueryBuilder.compare(getXPDLId(),cond.getValue(),cond.getOperator());

              case COLUMN_XPDLVERSION:
                   return QueryBuilder.compare(getXPDLVersion(),cond.getValue(),cond.getOperator());

              case COLUMN_XPDLCLASSVERSION:
                   return QueryBuilder.compare(getXPDLClassVersion(),cond.getValue(),cond.getOperator());

              case COLUMN_XPDLUPLOADTIME:
                   return QueryBuilder.compare(getXPDLUploadTime(),cond.getValue(),cond.getOperator());

              case COLUMN_XPDLHISTORYUPLOADTIME:
                   return QueryBuilder.compare(getXPDLHistoryUploadTime(),cond.getValue(),cond.getOperator());


                case COLUMN_OID:
                    return QueryBuilder.compare(get_CacheHandle(),cond.getValue(),cond.getOperator());
            }
        } catch (Exception e) {
          System.out.println("**************************  compareCond catck blok");
        }
        return false;
    }

    /**
     * Create a copy of the guts of a DO.
     *
     * @return Copied DataStruct object.
     *
     * @exception DatabaseManagerException 
     *       if createExisting() fails for a contained DO
     * @exception ObjectIdException 
     *       if GenericDO has trouble obtaining a valid id.
     */
    public XPDLHistoryDataStruct duplicate() 
    throws DatabaseManagerException, ObjectIdException {
        XPDLHistoryDataStruct ret = new XPDLHistoryDataStruct ();
        if (!isEmpty) {
            ret.XPDLId = GenericDO.copyString(XPDLId);
            ret.XPDLVersion = GenericDO.copyString(XPDLVersion);
            ret.XPDLClassVersion = XPDLClassVersion;
            ret.XPDLUploadTime = GenericDO.copyTimestamp(XPDLUploadTime);
            ret.XPDLHistoryUploadTime = GenericDO.copyTimestamp(XPDLHistoryUploadTime);

        }
        ret.set_OId(get_OId());
        ret.set_Version(get_Version());
        ret.databaseName=get_Database();
        ret.isEmpty = isEmpty;
        return ret;
    }
}
