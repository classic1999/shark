
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
 * org.enhydra.shark.instancepersistence.data/ActivityDataDataStruct.java
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
public class ActivityDataDataStruct extends CoreDataStruct implements Cloneable, Serializable {

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
        ActivityDataDataStruct ret =
            new ActivityDataDataStruct();
        ret.set_OId(get_OId());
        ret.set_Version((incrementVersion?1:0)+get_Version());
        ret.set_Database(get_Database());
        return ret;
    }
    
    private BigDecimal Activity =  null ;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_ACTIVITY = 0;
    

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
    
    private String VariableDefinitionId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_VARIABLEDEFINITIONID = 1;
    

    /**
     * Sets VariableDefinitionId column.
     * @param _VariableDefinitionId new column value.
     */
    public void setVariableDefinitionId(String _VariableDefinitionId) {
        if (readOnly)
            throw new Error("This should never happen! setVariableDefinitionId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(VariableDefinitionId, _VariableDefinitionId);
        VariableDefinitionId = _VariableDefinitionId;
//        return bDiff;
    }

    /**
     * Return value of VariableDefinitionId column.
     * @return value of VariableDefinitionId column.
     */
    public String getVariableDefinitionId() {
        return VariableDefinitionId;
    }
    
    private short VariableType = 0;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_VARIABLETYPE = 2;
    

    /**
     * Sets VariableType column.
     * @param _VariableType new column value.
     */
    public void setVariableType(short _VariableType) {
        if (readOnly)
            throw new Error("This should never happen! setVariableType on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_short(VariableType, _VariableType);
        VariableType = _VariableType;
//        return bDiff;
    }

    /**
     * Return value of VariableType column.
     * @return value of VariableType column.
     */
    public short getVariableType() {
        return VariableType;
    }
    
    private byte[] VariableValue = {0};

    /**
     * Used for query caching.
     */
    static public final int COLUMN_VARIABLEVALUE = 3;
    

    /**
     * Sets VariableValue column.
     * @param _VariableValue new column value.
     */
    public void setVariableValue(byte[] _VariableValue) {
        if (readOnly)
            throw new Error("This should never happen! setVariableValue on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_bytes(VariableValue, _VariableValue);
        VariableValue = _VariableValue;
//        return bDiff;
    }

    /**
     * Return value of VariableValue column.
     * @return value of VariableValue column.
     */
    public byte[] getVariableValue() {
        return VariableValue;
    }
    
    private String VariableValueVCHAR = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_VARIABLEVALUEVCHAR = 4;
    

    /**
     * Sets VariableValueVCHAR column.
     * @param _VariableValueVCHAR new column value.
     */
    public void setVariableValueVCHAR(String _VariableValueVCHAR) {
        if (readOnly)
            throw new Error("This should never happen! setVariableValueVCHAR on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(VariableValueVCHAR, _VariableValueVCHAR);
        VariableValueVCHAR = _VariableValueVCHAR;
//        return bDiff;
    }

    /**
     * Return value of VariableValueVCHAR column.
     * @return value of VariableValueVCHAR column.
     */
    public String getVariableValueVCHAR() {
        return VariableValueVCHAR;
    }
    
    private double VariableValueDBL = 0d;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_VARIABLEVALUEDBL = 5;
    

    /**
     * Sets VariableValueDBL column.
     * @param _VariableValueDBL new column value.
     */
    public void setVariableValueDBL(double _VariableValueDBL) {
        if (readOnly)
            throw new Error("This should never happen! setVariableValueDBL on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_double(VariableValueDBL, _VariableValueDBL);
        VariableValueDBL = _VariableValueDBL;
//        return bDiff;
    }

    /**
     * Return value of VariableValueDBL column.
     * @return value of VariableValueDBL column.
     */
    public double getVariableValueDBL() {
        return VariableValueDBL;
    }
    
    private long VariableValueLONG = 0L;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_VARIABLEVALUELONG = 6;
    

    /**
     * Sets VariableValueLONG column.
     * @param _VariableValueLONG new column value.
     */
    public void setVariableValueLONG(long _VariableValueLONG) {
        if (readOnly)
            throw new Error("This should never happen! setVariableValueLONG on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_long(VariableValueLONG, _VariableValueLONG);
        VariableValueLONG = _VariableValueLONG;
//        return bDiff;
    }

    /**
     * Return value of VariableValueLONG column.
     * @return value of VariableValueLONG column.
     */
    public long getVariableValueLONG() {
        return VariableValueLONG;
    }
    
    private java.sql.Timestamp VariableValueDATE = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_VARIABLEVALUEDATE = 7;
    

    /**
     * Sets VariableValueDATE column.
     * @param _VariableValueDATE new column value.
     */
    public void setVariableValueDATE(java.sql.Timestamp _VariableValueDATE) {
        if (readOnly)
            throw new Error("This should never happen! setVariableValueDATE on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_java_sql_Timestamp(VariableValueDATE, _VariableValueDATE);
        VariableValueDATE = _VariableValueDATE;
//        return bDiff;
    }

    /**
     * Return value of VariableValueDATE column.
     * @return value of VariableValueDATE column.
     */
    public java.sql.Timestamp getVariableValueDATE() {
        return VariableValueDATE;
    }
    
    private boolean VariableValueBOOL = false;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_VARIABLEVALUEBOOL = 8;
    

    /**
     * Sets VariableValueBOOL column.
     * @param _VariableValueBOOL new column value.
     */
    public void setVariableValueBOOL(boolean _VariableValueBOOL) {
        if (readOnly)
            throw new Error("This should never happen! setVariableValueBOOL on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_boolean(VariableValueBOOL, _VariableValueBOOL);
        VariableValueBOOL = _VariableValueBOOL;
//        return bDiff;
    }

    /**
     * Return value of VariableValueBOOL column.
     * @return value of VariableValueBOOL column.
     */
    public boolean getVariableValueBOOL() {
        return VariableValueBOOL;
    }
    
    private boolean IsResult = false;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_ISRESULT = 9;
    

    /**
     * Sets IsResult column.
     * @param _IsResult new column value.
     */
    public void setIsResult(boolean _IsResult) {
        if (readOnly)
            throw new Error("This should never happen! setIsResult on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_boolean(IsResult, _IsResult);
        IsResult = _IsResult;
//        return bDiff;
    }

    /**
     * Return value of IsResult column.
     * @return value of IsResult column.
     */
    public boolean getIsResult() {
        return IsResult;
    }

    /**
     * Used for query caching.
     */
    static public final int COLUMN_OID = 10;

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
              case COLUMN_ACTIVITY:
                   return QueryBuilder.compare(getActivity(),cond.getValue(),cond.getOperator());

              case COLUMN_VARIABLEDEFINITIONID:
                   return QueryBuilder.compare(getVariableDefinitionId(),cond.getValue(),cond.getOperator());

              case COLUMN_VARIABLETYPE:
                   return QueryBuilder.compare(getVariableType(),cond.getValue(),cond.getOperator());

              case COLUMN_VARIABLEVALUE:
                   return QueryBuilder.compare(getVariableValue(),cond.getValue(),cond.getOperator());

              case COLUMN_VARIABLEVALUEVCHAR:
                   return QueryBuilder.compare(getVariableValueVCHAR(),cond.getValue(),cond.getOperator());

              case COLUMN_VARIABLEVALUEDBL:
                   return QueryBuilder.compare(getVariableValueDBL(),cond.getValue(),cond.getOperator());

              case COLUMN_VARIABLEVALUELONG:
                   return QueryBuilder.compare(getVariableValueLONG(),cond.getValue(),cond.getOperator());

              case COLUMN_VARIABLEVALUEDATE:
                   return QueryBuilder.compare(getVariableValueDATE(),cond.getValue(),cond.getOperator());

              case COLUMN_VARIABLEVALUEBOOL:
                   return QueryBuilder.compare(getVariableValueBOOL(),cond.getValue(),cond.getOperator());

              case COLUMN_ISRESULT:
                   return QueryBuilder.compare(getIsResult(),cond.getValue(),cond.getOperator());


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
    public ActivityDataDataStruct duplicate() 
    throws DatabaseManagerException, ObjectIdException {
        ActivityDataDataStruct ret = new ActivityDataDataStruct ();
        if (!isEmpty) {
            ret.Activity = GenericDO.copyBigDecimal(Activity);
            ret.VariableDefinitionId = GenericDO.copyString(VariableDefinitionId);
            ret.VariableType = VariableType;
            ret.VariableValue = GenericDO.copyByteArray(VariableValue);
            ret.VariableValueVCHAR = GenericDO.copyString(VariableValueVCHAR);
            ret.VariableValueDBL = VariableValueDBL;
            ret.VariableValueLONG = VariableValueLONG;
            ret.VariableValueDATE = GenericDO.copyTimestamp(VariableValueDATE);
            ret.VariableValueBOOL = VariableValueBOOL;
            ret.IsResult = IsResult;

        }
        ret.set_OId(get_OId());
        ret.set_Version(get_Version());
        ret.databaseName=get_Database();
        ret.isEmpty = isEmpty;
        return ret;
    }
}
