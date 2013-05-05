
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
 * org.enhydra.shark.instancepersistence.data/ActivityDataStruct.java
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
public class ActivityDataStruct extends CoreDataStruct implements Cloneable, Serializable {

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
        ActivityDataStruct ret =
            new ActivityDataStruct();
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
    
    private BigDecimal Process =  null ;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PROCESS = 3;
    

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
    
    private BigDecimal TheResource =  null ;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_THERESOURCE = 4;
    

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
    
    private String PDefName = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PDEFNAME = 5;
    

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
    
    private String ProcessId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PROCESSID = 6;
    

    /**
     * Sets ProcessId column.
     * @param _ProcessId new column value.
     */
    public void setProcessId(String _ProcessId) {
        if (readOnly)
            throw new Error("This should never happen! setProcessId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(ProcessId, _ProcessId);
        ProcessId = _ProcessId;
//        return bDiff;
    }

    /**
     * Return value of ProcessId column.
     * @return value of ProcessId column.
     */
    public String getProcessId() {
        return ProcessId;
    }
    
    private String ResourceId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_RESOURCEID = 7;
    

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
    
    private BigDecimal State =  null ;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_STATE = 8;
    

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
    
    private String BlockActivityId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_BLOCKACTIVITYID = 9;
    

    /**
     * Sets BlockActivityId column.
     * @param _BlockActivityId new column value.
     */
    public void setBlockActivityId(String _BlockActivityId) {
        if (readOnly)
            throw new Error("This should never happen! setBlockActivityId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(BlockActivityId, _BlockActivityId);
        BlockActivityId = _BlockActivityId;
//        return bDiff;
    }

    /**
     * Return value of BlockActivityId column.
     * @return value of BlockActivityId column.
     */
    public String getBlockActivityId() {
        return BlockActivityId;
    }
    
    private String Performer = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PERFORMER = 10;
    

    /**
     * Sets Performer column.
     * @param _Performer new column value.
     */
    public void setPerformer(String _Performer) {
        if (readOnly)
            throw new Error("This should never happen! setPerformer on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(Performer, _Performer);
        Performer = _Performer;
//        return bDiff;
    }

    /**
     * Return value of Performer column.
     * @return value of Performer column.
     */
    public String getPerformer() {
        return Performer;
    }
    
    private boolean IsPerformerAsynchronous = false;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_ISPERFORMERASYNCHRONOUS = 11;
    

    /**
     * Sets IsPerformerAsynchronous column.
     * @param _IsPerformerAsynchronous new column value.
     */
    public void setIsPerformerAsynchronous(boolean _IsPerformerAsynchronous) {
        if (readOnly)
            throw new Error("This should never happen! setIsPerformerAsynchronous on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_boolean(IsPerformerAsynchronous, _IsPerformerAsynchronous);
        IsPerformerAsynchronous = _IsPerformerAsynchronous;
//        return bDiff;
    }

    /**
     * Return value of IsPerformerAsynchronous column.
     * @return value of IsPerformerAsynchronous column.
     */
    public boolean getIsPerformerAsynchronous() {
        return IsPerformerAsynchronous;
    }
    
    private short Priority = 0;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PRIORITY = 12;
    

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
    static public final int COLUMN_NAME = 13;
    

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
    
    private long Activated = 0L;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_ACTIVATED = 14;
    

    /**
     * Sets Activated column.
     * @param _Activated new column value.
     */
    public void setActivated(long _Activated) {
        if (readOnly)
            throw new Error("This should never happen! setActivated on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_long(Activated, _Activated);
        Activated = _Activated;
//        return bDiff;
    }

    /**
     * Return value of Activated column.
     * @return value of Activated column.
     */
    public long getActivated() {
        return Activated;
    }
    
    private long Accepted = 0L;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_ACCEPTED = 15;
    

    /**
     * Sets Accepted column.
     * @param _Accepted new column value.
     */
    public void setAccepted(long _Accepted) {
        if (readOnly)
            throw new Error("This should never happen! setAccepted on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_long(Accepted, _Accepted);
        Accepted = _Accepted;
//        return bDiff;
    }

    /**
     * Return value of Accepted column.
     * @return value of Accepted column.
     */
    public long getAccepted() {
        return Accepted;
    }
    
    private long LastStateTime = 0L;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_LASTSTATETIME = 16;
    

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
    static public final int COLUMN_LIMITTIME = 17;
    

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
    static public final int COLUMN_DESCRIPTION = 18;
    

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
    static public final int COLUMN_OID = 19;

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

              case COLUMN_ACTIVITYSETDEFINITIONID:
                   return QueryBuilder.compare(getActivitySetDefinitionId(),cond.getValue(),cond.getOperator());

              case COLUMN_ACTIVITYDEFINITIONID:
                   return QueryBuilder.compare(getActivityDefinitionId(),cond.getValue(),cond.getOperator());

              case COLUMN_PROCESS:
                   return QueryBuilder.compare(getProcess(),cond.getValue(),cond.getOperator());

              case COLUMN_THERESOURCE:
                   return QueryBuilder.compare(getTheResource(),cond.getValue(),cond.getOperator());

              case COLUMN_PDEFNAME:
                   return QueryBuilder.compare(getPDefName(),cond.getValue(),cond.getOperator());

              case COLUMN_PROCESSID:
                   return QueryBuilder.compare(getProcessId(),cond.getValue(),cond.getOperator());

              case COLUMN_RESOURCEID:
                   return QueryBuilder.compare(getResourceId(),cond.getValue(),cond.getOperator());

              case COLUMN_STATE:
                   return QueryBuilder.compare(getState(),cond.getValue(),cond.getOperator());

              case COLUMN_BLOCKACTIVITYID:
                   return QueryBuilder.compare(getBlockActivityId(),cond.getValue(),cond.getOperator());

              case COLUMN_PERFORMER:
                   return QueryBuilder.compare(getPerformer(),cond.getValue(),cond.getOperator());

              case COLUMN_ISPERFORMERASYNCHRONOUS:
                   return QueryBuilder.compare(getIsPerformerAsynchronous(),cond.getValue(),cond.getOperator());

              case COLUMN_PRIORITY:
                   return QueryBuilder.compare(getPriority(),cond.getValue(),cond.getOperator());

              case COLUMN_NAME:
                   return QueryBuilder.compare(getName(),cond.getValue(),cond.getOperator());

              case COLUMN_ACTIVATED:
                   return QueryBuilder.compare(getActivated(),cond.getValue(),cond.getOperator());

              case COLUMN_ACCEPTED:
                   return QueryBuilder.compare(getAccepted(),cond.getValue(),cond.getOperator());

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
    public ActivityDataStruct duplicate() 
    throws DatabaseManagerException, ObjectIdException {
        ActivityDataStruct ret = new ActivityDataStruct ();
        if (!isEmpty) {
            ret.Id = GenericDO.copyString(Id);
            ret.ActivitySetDefinitionId = GenericDO.copyString(ActivitySetDefinitionId);
            ret.ActivityDefinitionId = GenericDO.copyString(ActivityDefinitionId);
            ret.Process = GenericDO.copyBigDecimal(Process);
            ret.TheResource = GenericDO.copyBigDecimal(TheResource);
            ret.PDefName = GenericDO.copyString(PDefName);
            ret.ProcessId = GenericDO.copyString(ProcessId);
            ret.ResourceId = GenericDO.copyString(ResourceId);
            ret.State = GenericDO.copyBigDecimal(State);
            ret.BlockActivityId = GenericDO.copyString(BlockActivityId);
            ret.Performer = GenericDO.copyString(Performer);
            ret.IsPerformerAsynchronous = IsPerformerAsynchronous;
            ret.Priority = Priority;
            ret.Name = GenericDO.copyString(Name);
            ret.Activated = Activated;
            ret.Accepted = Accepted;
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
