
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
 * org.enhydra.shark.instancepersistence.data/ProcessDataStruct.java
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
public class ProcessDataStruct extends CoreDataStruct implements Cloneable, Serializable {

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
        ProcessDataStruct ret =
            new ProcessDataStruct();
        ret.set_OId(get_OId());
        ret.set_Version((incrementVersion?1:0)+get_Version());
        ret.set_Database(get_Database());
        return ret;
    }
    
    private String Id = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_ID = 0;
    

    /**
     * Sets Id column.
     * @param _Id new column value.
     */
    public void setId(String _Id) {
        if (readOnly)
            throw new Error("This should never happen! setId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(Id, _Id);
        Id = _Id;
//        return bDiff;
    }

    /**
     * Return value of Id column.
     * @return value of Id column.
     */
    public String getId() {
        return Id;
    }
    
    private BigDecimal ProcessDefinition =  null ;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PROCESSDEFINITION = 1;
    

    /**
     * Sets ProcessDefinition column.
     * @param _ProcessDefinition new column value.
     */
    public void setProcessDefinition(BigDecimal _ProcessDefinition) {
        if (readOnly)
            throw new Error("This should never happen! setProcessDefinition on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_BigDecimal(ProcessDefinition, _ProcessDefinition);
        ProcessDefinition = _ProcessDefinition;
//        return bDiff;
    }

    /**
     * Return value of ProcessDefinition column.
     * @return value of ProcessDefinition column.
     */
    public BigDecimal getProcessDefinition() {
        return ProcessDefinition;
    }
    
    private String PDefName = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PDEFNAME = 2;
    

    /**
     * Sets PDefName column.
     * @param _PDefName new column value.
     */
    public void setPDefName(String _PDefName) {
        if (readOnly)
            throw new Error("This should never happen! setPDefName on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(PDefName, _PDefName);
        PDefName = _PDefName;
//        return bDiff;
    }

    /**
     * Return value of PDefName column.
     * @return value of PDefName column.
     */
    public String getPDefName() {
        return PDefName;
    }
    
    private String ActivityRequesterId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_ACTIVITYREQUESTERID = 3;
    

    /**
     * Sets ActivityRequesterId column.
     * @param _ActivityRequesterId new column value.
     */
    public void setActivityRequesterId(String _ActivityRequesterId) {
        if (readOnly)
            throw new Error("This should never happen! setActivityRequesterId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(ActivityRequesterId, _ActivityRequesterId);
        ActivityRequesterId = _ActivityRequesterId;
//        return bDiff;
    }

    /**
     * Return value of ActivityRequesterId column.
     * @return value of ActivityRequesterId column.
     */
    public String getActivityRequesterId() {
        return ActivityRequesterId;
    }
    
    private String ActivityRequesterProcessId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_ACTIVITYREQUESTERPROCESSID = 4;
    

    /**
     * Sets ActivityRequesterProcessId column.
     * @param _ActivityRequesterProcessId new column value.
     */
    public void setActivityRequesterProcessId(String _ActivityRequesterProcessId) {
        if (readOnly)
            throw new Error("This should never happen! setActivityRequesterProcessId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(ActivityRequesterProcessId, _ActivityRequesterProcessId);
        ActivityRequesterProcessId = _ActivityRequesterProcessId;
//        return bDiff;
    }

    /**
     * Return value of ActivityRequesterProcessId column.
     * @return value of ActivityRequesterProcessId column.
     */
    public String getActivityRequesterProcessId() {
        return ActivityRequesterProcessId;
    }
    
    private String ResourceRequesterId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_RESOURCEREQUESTERID = 5;
    

    /**
     * Sets ResourceRequesterId column.
     * @param _ResourceRequesterId new column value.
     */
    public void setResourceRequesterId(String _ResourceRequesterId) {
        if (readOnly)
            throw new Error("This should never happen! setResourceRequesterId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(ResourceRequesterId, _ResourceRequesterId);
        ResourceRequesterId = _ResourceRequesterId;
//        return bDiff;
    }

    /**
     * Return value of ResourceRequesterId column.
     * @return value of ResourceRequesterId column.
     */
    public String getResourceRequesterId() {
        return ResourceRequesterId;
    }
    
    private String ExternalRequesterClassName = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_EXTERNALREQUESTERCLASSNAME = 6;
    

    /**
     * Sets ExternalRequesterClassName column.
     * @param _ExternalRequesterClassName new column value.
     */
    public void setExternalRequesterClassName(String _ExternalRequesterClassName) {
        if (readOnly)
            throw new Error("This should never happen! setExternalRequesterClassName on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(ExternalRequesterClassName, _ExternalRequesterClassName);
        ExternalRequesterClassName = _ExternalRequesterClassName;
//        return bDiff;
    }

    /**
     * Return value of ExternalRequesterClassName column.
     * @return value of ExternalRequesterClassName column.
     */
    public String getExternalRequesterClassName() {
        return ExternalRequesterClassName;
    }
    
    private BigDecimal State =  null ;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_STATE = 7;
    

    /**
     * Sets State column.
     * @param _State new column value.
     */
    public void setState(BigDecimal _State) {
        if (readOnly)
            throw new Error("This should never happen! setState on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_BigDecimal(State, _State);
        State = _State;
//        return bDiff;
    }

    /**
     * Return value of State column.
     * @return value of State column.
     */
    public BigDecimal getState() {
        return State;
    }
    
    private short Priority = 0;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PRIORITY = 8;
    

    /**
     * Sets Priority column.
     * @param _Priority new column value.
     */
    public void setPriority(short _Priority) {
        if (readOnly)
            throw new Error("This should never happen! setPriority on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_short(Priority, _Priority);
        Priority = _Priority;
//        return bDiff;
    }

    /**
     * Return value of Priority column.
     * @return value of Priority column.
     */
    public short getPriority() {
        return Priority;
    }
    
    private String Name = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_NAME = 9;
    

    /**
     * Sets Name column.
     * @param _Name new column value.
     */
    public void setName(String _Name) {
        if (readOnly)
            throw new Error("This should never happen! setName on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(Name, _Name);
        Name = _Name;
//        return bDiff;
    }

    /**
     * Return value of Name column.
     * @return value of Name column.
     */
    public String getName() {
        return Name;
    }
    
    private long Created = 0L;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_CREATED = 10;
    

    /**
     * Sets Created column.
     * @param _Created new column value.
     */
    public void setCreated(long _Created) {
        if (readOnly)
            throw new Error("This should never happen! setCreated on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_long(Created, _Created);
        Created = _Created;
//        return bDiff;
    }

    /**
     * Return value of Created column.
     * @return value of Created column.
     */
    public long getCreated() {
        return Created;
    }
    
    private long Started = 0L;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_STARTED = 11;
    

    /**
     * Sets Started column.
     * @param _Started new column value.
     */
    public void setStarted(long _Started) {
        if (readOnly)
            throw new Error("This should never happen! setStarted on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_long(Started, _Started);
        Started = _Started;
//        return bDiff;
    }

    /**
     * Return value of Started column.
     * @return value of Started column.
     */
    public long getStarted() {
        return Started;
    }
    
    private long LastStateTime = 0L;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_LASTSTATETIME = 12;
    

    /**
     * Sets LastStateTime column.
     * @param _LastStateTime new column value.
     */
    public void setLastStateTime(long _LastStateTime) {
        if (readOnly)
            throw new Error("This should never happen! setLastStateTime on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_long(LastStateTime, _LastStateTime);
        LastStateTime = _LastStateTime;
//        return bDiff;
    }

    /**
     * Return value of LastStateTime column.
     * @return value of LastStateTime column.
     */
    public long getLastStateTime() {
        return LastStateTime;
    }
    
    private long LimitTime = 0L;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_LIMITTIME = 13;
    

    /**
     * Sets LimitTime column.
     * @param _LimitTime new column value.
     */
    public void setLimitTime(long _LimitTime) {
        if (readOnly)
            throw new Error("This should never happen! setLimitTime on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_long(LimitTime, _LimitTime);
        LimitTime = _LimitTime;
//        return bDiff;
    }

    /**
     * Return value of LimitTime column.
     * @return value of LimitTime column.
     */
    public long getLimitTime() {
        return LimitTime;
    }
    
    private String Description = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_DESCRIPTION = 14;
    

    /**
     * Sets Description column.
     * @param _Description new column value.
     */
    public void setDescription(String _Description) {
        if (readOnly)
            throw new Error("This should never happen! setDescription on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(Description, _Description);
        Description = _Description;
//        return bDiff;
    }

    /**
     * Return value of Description column.
     * @return value of Description column.
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Used for query caching.
     */
    static public final int COLUMN_OID = 15;

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
              case COLUMN_ID:
                   return QueryBuilder.compare(getId(),cond.getValue(),cond.getOperator());

              case COLUMN_PROCESSDEFINITION:
                   return QueryBuilder.compare(getProcessDefinition(),cond.getValue(),cond.getOperator());

              case COLUMN_PDEFNAME:
                   return QueryBuilder.compare(getPDefName(),cond.getValue(),cond.getOperator());

              case COLUMN_ACTIVITYREQUESTERID:
                   return QueryBuilder.compare(getActivityRequesterId(),cond.getValue(),cond.getOperator());

              case COLUMN_ACTIVITYREQUESTERPROCESSID:
                   return QueryBuilder.compare(getActivityRequesterProcessId(),cond.getValue(),cond.getOperator());

              case COLUMN_RESOURCEREQUESTERID:
                   return QueryBuilder.compare(getResourceRequesterId(),cond.getValue(),cond.getOperator());

              case COLUMN_EXTERNALREQUESTERCLASSNAME:
                   return QueryBuilder.compare(getExternalRequesterClassName(),cond.getValue(),cond.getOperator());

              case COLUMN_STATE:
                   return QueryBuilder.compare(getState(),cond.getValue(),cond.getOperator());

              case COLUMN_PRIORITY:
                   return QueryBuilder.compare(getPriority(),cond.getValue(),cond.getOperator());

              case COLUMN_NAME:
                   return QueryBuilder.compare(getName(),cond.getValue(),cond.getOperator());

              case COLUMN_CREATED:
                   return QueryBuilder.compare(getCreated(),cond.getValue(),cond.getOperator());

              case COLUMN_STARTED:
                   return QueryBuilder.compare(getStarted(),cond.getValue(),cond.getOperator());

              case COLUMN_LASTSTATETIME:
                   return QueryBuilder.compare(getLastStateTime(),cond.getValue(),cond.getOperator());

              case COLUMN_LIMITTIME:
                   return QueryBuilder.compare(getLimitTime(),cond.getValue(),cond.getOperator());

              case COLUMN_DESCRIPTION:
                   return QueryBuilder.compare(getDescription(),cond.getValue(),cond.getOperator());


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
    public ProcessDataStruct duplicate() 
    throws DatabaseManagerException, ObjectIdException {
        ProcessDataStruct ret = new ProcessDataStruct ();
        if (!isEmpty) {
            ret.Id = GenericDO.copyString(Id);
            ret.ProcessDefinition = GenericDO.copyBigDecimal(ProcessDefinition);
            ret.PDefName = GenericDO.copyString(PDefName);
            ret.ActivityRequesterId = GenericDO.copyString(ActivityRequesterId);
            ret.ActivityRequesterProcessId = GenericDO.copyString(ActivityRequesterProcessId);
            ret.ResourceRequesterId = GenericDO.copyString(ResourceRequesterId);
            ret.ExternalRequesterClassName = GenericDO.copyString(ExternalRequesterClassName);
            ret.State = GenericDO.copyBigDecimal(State);
            ret.Priority = Priority;
            ret.Name = GenericDO.copyString(Name);
            ret.Created = Created;
            ret.Started = Started;
            ret.LastStateTime = LastStateTime;
            ret.LimitTime = LimitTime;
            ret.Description = GenericDO.copyString(Description);

        }
        ret.set_OId(get_OId());
        ret.set_Version(get_Version());
        ret.databaseName=get_Database();
        ret.isEmpty = isEmpty;
        return ret;
    }
}
