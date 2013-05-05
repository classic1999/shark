
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
 * org.enhydra.shark.instancepersistence.data/AssignmentDataStruct.java
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
public class AssignmentDataStruct extends CoreDataStruct implements Cloneable, Serializable {

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
        AssignmentDataStruct ret =
            new AssignmentDataStruct();
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
    
    private BigDecimal TheResource =  null ;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_THERESOURCE = 1;
    

    /**
     * Sets TheResource column.
     * @param _TheResource new column value.
     */
    public void setTheResource(BigDecimal _TheResource) {
        if (readOnly)
            throw new Error("This should never happen! setTheResource on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_BigDecimal(TheResource, _TheResource);
        TheResource = _TheResource;
//        return bDiff;
    }

    /**
     * Return value of TheResource column.
     * @return value of TheResource column.
     */
    public BigDecimal getTheResource() {
        return TheResource;
    }
    
    private String ActivityId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_ACTIVITYID = 2;
    

    /**
     * Sets ActivityId column.
     * @param _ActivityId new column value.
     */
    public void setActivityId(String _ActivityId) {
        if (readOnly)
            throw new Error("This should never happen! setActivityId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(ActivityId, _ActivityId);
        ActivityId = _ActivityId;
//        return bDiff;
    }

    /**
     * Return value of ActivityId column.
     * @return value of ActivityId column.
     */
    public String getActivityId() {
        return ActivityId;
    }
    
    private String ActivityProcessId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_ACTIVITYPROCESSID = 3;
    

    /**
     * Sets ActivityProcessId column.
     * @param _ActivityProcessId new column value.
     */
    public void setActivityProcessId(String _ActivityProcessId) {
        if (readOnly)
            throw new Error("This should never happen! setActivityProcessId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(ActivityProcessId, _ActivityProcessId);
        ActivityProcessId = _ActivityProcessId;
//        return bDiff;
    }

    /**
     * Return value of ActivityProcessId column.
     * @return value of ActivityProcessId column.
     */
    public String getActivityProcessId() {
        return ActivityProcessId;
    }
    
    private String ActivityProcessDefName = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_ACTIVITYPROCESSDEFNAME = 4;
    

    /**
     * Sets ActivityProcessDefName column.
     * @param _ActivityProcessDefName new column value.
     */
    public void setActivityProcessDefName(String _ActivityProcessDefName) {
        if (readOnly)
            throw new Error("This should never happen! setActivityProcessDefName on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(ActivityProcessDefName, _ActivityProcessDefName);
        ActivityProcessDefName = _ActivityProcessDefName;
//        return bDiff;
    }

    /**
     * Return value of ActivityProcessDefName column.
     * @return value of ActivityProcessDefName column.
     */
    public String getActivityProcessDefName() {
        return ActivityProcessDefName;
    }
    
    private String ResourceId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_RESOURCEID = 5;
    

    /**
     * Sets ResourceId column.
     * @param _ResourceId new column value.
     */
    public void setResourceId(String _ResourceId) {
        if (readOnly)
            throw new Error("This should never happen! setResourceId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(ResourceId, _ResourceId);
        ResourceId = _ResourceId;
//        return bDiff;
    }

    /**
     * Return value of ResourceId column.
     * @return value of ResourceId column.
     */
    public String getResourceId() {
        return ResourceId;
    }
    
    private boolean IsAccepted = false;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_ISACCEPTED = 6;
    

    /**
     * Sets IsAccepted column.
     * @param _IsAccepted new column value.
     */
    public void setIsAccepted(boolean _IsAccepted) {
        if (readOnly)
            throw new Error("This should never happen! setIsAccepted on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_boolean(IsAccepted, _IsAccepted);
        IsAccepted = _IsAccepted;
//        return bDiff;
    }

    /**
     * Return value of IsAccepted column.
     * @return value of IsAccepted column.
     */
    public boolean getIsAccepted() {
        return IsAccepted;
    }
    
    private boolean IsValid = false;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_ISVALID = 7;
    

    /**
     * Sets IsValid column.
     * @param _IsValid new column value.
     */
    public void setIsValid(boolean _IsValid) {
        if (readOnly)
            throw new Error("This should never happen! setIsValid on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_boolean(IsValid, _IsValid);
        IsValid = _IsValid;
//        return bDiff;
    }

    /**
     * Return value of IsValid column.
     * @return value of IsValid column.
     */
    public boolean getIsValid() {
        return IsValid;
    }
    
    private java.math.BigDecimal CNT = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_CNT = 8;
    

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
    static public final int COLUMN_OID = 9;

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

              case COLUMN_THERESOURCE:
                   return QueryBuilder.compare(getTheResource(),cond.getValue(),cond.getOperator());

              case COLUMN_ACTIVITYID:
                   return QueryBuilder.compare(getActivityId(),cond.getValue(),cond.getOperator());

              case COLUMN_ACTIVITYPROCESSID:
                   return QueryBuilder.compare(getActivityProcessId(),cond.getValue(),cond.getOperator());

              case COLUMN_ACTIVITYPROCESSDEFNAME:
                   return QueryBuilder.compare(getActivityProcessDefName(),cond.getValue(),cond.getOperator());

              case COLUMN_RESOURCEID:
                   return QueryBuilder.compare(getResourceId(),cond.getValue(),cond.getOperator());

              case COLUMN_ISACCEPTED:
                   return QueryBuilder.compare(getIsAccepted(),cond.getValue(),cond.getOperator());

              case COLUMN_ISVALID:
                   return QueryBuilder.compare(getIsValid(),cond.getValue(),cond.getOperator());

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
    public AssignmentDataStruct duplicate() 
    throws DatabaseManagerException, ObjectIdException {
        AssignmentDataStruct ret = new AssignmentDataStruct ();
        if (!isEmpty) {
            ret.Activity = GenericDO.copyBigDecimal(Activity);
            ret.TheResource = GenericDO.copyBigDecimal(TheResource);
            ret.ActivityId = GenericDO.copyString(ActivityId);
            ret.ActivityProcessId = GenericDO.copyString(ActivityProcessId);
            ret.ActivityProcessDefName = GenericDO.copyString(ActivityProcessDefName);
            ret.ResourceId = GenericDO.copyString(ResourceId);
            ret.IsAccepted = IsAccepted;
            ret.IsValid = IsValid;
            ret.CNT = GenericDO.copyBigDecimal(CNT);

        }
        ret.set_OId(get_OId());
        ret.set_Version(get_Version());
        ret.databaseName=get_Database();
        ret.isEmpty = isEmpty;
        return ret;
    }
}
