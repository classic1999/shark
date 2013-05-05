
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
 * org.enhydra.shark.instancepersistence.data/AndJoinEntryDataStruct.java
 *-----------------------------------------------------------------------------
 */


package org.enhydra.shark.instancepersistence.data;

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
public class AndJoinEntryDataStruct extends CoreDataStruct implements Cloneable, Serializable {

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
        AndJoinEntryDataStruct ret =
            new AndJoinEntryDataStruct();
        ret.set_OId(get_OId());
        ret.set_Version((incrementVersion?1:0)+get_Version());
        ret.set_Database(get_Database());
        return ret;
    }
    
    private BigDecimal Process =  null ;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PROCESS = 0;
    

    /**
     * Sets Process column.
     * @param _Process new column value.
     */
    public void setProcess(BigDecimal _Process) {
        if (readOnly)
            throw new Error("This should never happen! setProcess on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_BigDecimal(Process, _Process);
        Process = _Process;
//        return bDiff;
    }

    /**
     * Return value of Process column.
     * @return value of Process column.
     */
    public BigDecimal getProcess() {
        return Process;
    }
    
    private String ActivitySetDefinitionId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_ACTIVITYSETDEFINITIONID = 1;
    

    /**
     * Sets ActivitySetDefinitionId column.
     * @param _ActivitySetDefinitionId new column value.
     */
    public void setActivitySetDefinitionId(String _ActivitySetDefinitionId) {
        if (readOnly)
            throw new Error("This should never happen! setActivitySetDefinitionId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(ActivitySetDefinitionId, _ActivitySetDefinitionId);
        ActivitySetDefinitionId = _ActivitySetDefinitionId;
//        return bDiff;
    }

    /**
     * Return value of ActivitySetDefinitionId column.
     * @return value of ActivitySetDefinitionId column.
     */
    public String getActivitySetDefinitionId() {
        return ActivitySetDefinitionId;
    }
    
    private String ActivityDefinitionId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_ACTIVITYDEFINITIONID = 2;
    

    /**
     * Sets ActivityDefinitionId column.
     * @param _ActivityDefinitionId new column value.
     */
    public void setActivityDefinitionId(String _ActivityDefinitionId) {
        if (readOnly)
            throw new Error("This should never happen! setActivityDefinitionId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(ActivityDefinitionId, _ActivityDefinitionId);
        ActivityDefinitionId = _ActivityDefinitionId;
//        return bDiff;
    }

    /**
     * Return value of ActivityDefinitionId column.
     * @return value of ActivityDefinitionId column.
     */
    public String getActivityDefinitionId() {
        return ActivityDefinitionId;
    }
    
    private BigDecimal Activity =  null ;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_ACTIVITY = 3;
    

    /**
     * Sets Activity column.
     * @param _Activity new column value.
     */
    public void setActivity(BigDecimal _Activity) {
        if (readOnly)
            throw new Error("This should never happen! setActivity on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_BigDecimal(Activity, _Activity);
        Activity = _Activity;
//        return bDiff;
    }

    /**
     * Return value of Activity column.
     * @return value of Activity column.
     */
    public BigDecimal getActivity() {
        return Activity;
    }
    
    private java.math.BigDecimal CNT = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_CNT = 4;
    

    /**
     * Sets CNT column.
     * @param _CNT new column value.
     */
    public void setCNT(java.math.BigDecimal _CNT) {
        if (readOnly)
            throw new Error("This should never happen! setCNT on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_java_math_BigDecimal(CNT, _CNT);
        CNT = _CNT;
//        return bDiff;
    }

    /**
     * Return value of CNT column.
     * @return value of CNT column.
     */
    public java.math.BigDecimal getCNT() {
        return CNT;
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
              case COLUMN_PROCESS:
                   return QueryBuilder.compare(getProcess(),cond.getValue(),cond.getOperator());

              case COLUMN_ACTIVITYSETDEFINITIONID:
                   return QueryBuilder.compare(getActivitySetDefinitionId(),cond.getValue(),cond.getOperator());

              case COLUMN_ACTIVITYDEFINITIONID:
                   return QueryBuilder.compare(getActivityDefinitionId(),cond.getValue(),cond.getOperator());

              case COLUMN_ACTIVITY:
                   return QueryBuilder.compare(getActivity(),cond.getValue(),cond.getOperator());

              case COLUMN_CNT:
                   return QueryBuilder.compare(getCNT(),cond.getValue(),cond.getOperator());


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
    public AndJoinEntryDataStruct duplicate() 
    throws DatabaseManagerException, ObjectIdException {
        AndJoinEntryDataStruct ret = new AndJoinEntryDataStruct ();
        if (!isEmpty) {
            ret.Process = GenericDO.copyBigDecimal(Process);
            ret.ActivitySetDefinitionId = GenericDO.copyString(ActivitySetDefinitionId);
            ret.ActivityDefinitionId = GenericDO.copyString(ActivityDefinitionId);
            ret.Activity = GenericDO.copyBigDecimal(Activity);
            ret.CNT = GenericDO.copyBigDecimal(CNT);

        }
        ret.set_OId(get_OId());
        ret.set_Version(get_Version());
        ret.databaseName=get_Database();
        ret.isEmpty = isEmpty;
        return ret;
    }
}
