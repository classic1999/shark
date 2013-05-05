
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
 * org.enhydra.shark.eventaudit.data/CreateProcessEventAuditDataStruct.java
 *-----------------------------------------------------------------------------
 */


package org.enhydra.shark.eventaudit.data;

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
public class CreateProcessEventAuditDataStruct extends CoreDataStruct implements Cloneable, Serializable {

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
        CreateProcessEventAuditDataStruct ret =
            new CreateProcessEventAuditDataStruct();
        ret.set_OId(get_OId());
        ret.set_Version((incrementVersion?1:0)+get_Version());
        ret.set_Database(get_Database());
        return ret;
    }
    
    private String UTCTime = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_UTCTIME = 0;
    

    /**
     * Sets UTCTime column.
     * @param _UTCTime new column value.
     */
    public void setUTCTime(String _UTCTime) {
        if (readOnly)
            throw new Error("This should never happen! setUTCTime on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(UTCTime, _UTCTime);
        UTCTime = _UTCTime;
//        return bDiff;
    }

    /**
     * Return value of UTCTime column.
     * @return value of UTCTime column.
     */
    public String getUTCTime() {
        return UTCTime;
    }
    
    private BigDecimal TheType =  null ;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_THETYPE = 1;
    

    /**
     * Sets TheType column.
     * @param _TheType new column value.
     */
    public void setTheType(BigDecimal _TheType) {
        if (readOnly)
            throw new Error("This should never happen! setTheType on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_BigDecimal(TheType, _TheType);
        TheType = _TheType;
//        return bDiff;
    }

    /**
     * Return value of TheType column.
     * @return value of TheType column.
     */
    public BigDecimal getTheType() {
        return TheType;
    }
    
    private String ProcessId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PROCESSID = 2;
    

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
    
    private String ProcessName = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PROCESSNAME = 3;
    

    /**
     * Sets ProcessName column.
     * @param _ProcessName new column value.
     */
    public void setProcessName(String _ProcessName) {
        if (readOnly)
            throw new Error("This should never happen! setProcessName on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(ProcessName, _ProcessName);
        ProcessName = _ProcessName;
//        return bDiff;
    }

    /**
     * Return value of ProcessName column.
     * @return value of ProcessName column.
     */
    public String getProcessName() {
        return ProcessName;
    }
    
    private String ProcessDefinitionName = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PROCESSDEFINITIONNAME = 4;
    

    /**
     * Sets ProcessDefinitionName column.
     * @param _ProcessDefinitionName new column value.
     */
    public void setProcessDefinitionName(String _ProcessDefinitionName) {
        if (readOnly)
            throw new Error("This should never happen! setProcessDefinitionName on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(ProcessDefinitionName, _ProcessDefinitionName);
        ProcessDefinitionName = _ProcessDefinitionName;
//        return bDiff;
    }

    /**
     * Return value of ProcessDefinitionName column.
     * @return value of ProcessDefinitionName column.
     */
    public String getProcessDefinitionName() {
        return ProcessDefinitionName;
    }
    
    private String ProcessDefinitionVersion = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PROCESSDEFINITIONVERSION = 5;
    

    /**
     * Sets ProcessDefinitionVersion column.
     * @param _ProcessDefinitionVersion new column value.
     */
    public void setProcessDefinitionVersion(String _ProcessDefinitionVersion) {
        if (readOnly)
            throw new Error("This should never happen! setProcessDefinitionVersion on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(ProcessDefinitionVersion, _ProcessDefinitionVersion);
        ProcessDefinitionVersion = _ProcessDefinitionVersion;
//        return bDiff;
    }

    /**
     * Return value of ProcessDefinitionVersion column.
     * @return value of ProcessDefinitionVersion column.
     */
    public String getProcessDefinitionVersion() {
        return ProcessDefinitionVersion;
    }
    
    private String ProcessDefinitionId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PROCESSDEFINITIONID = 6;
    

    /**
     * Sets ProcessDefinitionId column.
     * @param _ProcessDefinitionId new column value.
     */
    public void setProcessDefinitionId(String _ProcessDefinitionId) {
        if (readOnly)
            throw new Error("This should never happen! setProcessDefinitionId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(ProcessDefinitionId, _ProcessDefinitionId);
        ProcessDefinitionId = _ProcessDefinitionId;
//        return bDiff;
    }

    /**
     * Return value of ProcessDefinitionId column.
     * @return value of ProcessDefinitionId column.
     */
    public String getProcessDefinitionId() {
        return ProcessDefinitionId;
    }
    
    private String PackageId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PACKAGEID = 7;
    

    /**
     * Sets PackageId column.
     * @param _PackageId new column value.
     */
    public void setPackageId(String _PackageId) {
        if (readOnly)
            throw new Error("This should never happen! setPackageId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(PackageId, _PackageId);
        PackageId = _PackageId;
//        return bDiff;
    }

    /**
     * Return value of PackageId column.
     * @return value of PackageId column.
     */
    public String getPackageId() {
        return PackageId;
    }
    
    private String PActivityId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PACTIVITYID = 8;
    

    /**
     * Sets PActivityId column.
     * @param _PActivityId new column value.
     */
    public void setPActivityId(String _PActivityId) {
        if (readOnly)
            throw new Error("This should never happen! setPActivityId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(PActivityId, _PActivityId);
        PActivityId = _PActivityId;
//        return bDiff;
    }

    /**
     * Return value of PActivityId column.
     * @return value of PActivityId column.
     */
    public String getPActivityId() {
        return PActivityId;
    }
    
    private String PProcessId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PPROCESSID = 9;
    

    /**
     * Sets PProcessId column.
     * @param _PProcessId new column value.
     */
    public void setPProcessId(String _PProcessId) {
        if (readOnly)
            throw new Error("This should never happen! setPProcessId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(PProcessId, _PProcessId);
        PProcessId = _PProcessId;
//        return bDiff;
    }

    /**
     * Return value of PProcessId column.
     * @return value of PProcessId column.
     */
    public String getPProcessId() {
        return PProcessId;
    }
    
    private String PProcessName = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PPROCESSNAME = 10;
    

    /**
     * Sets PProcessName column.
     * @param _PProcessName new column value.
     */
    public void setPProcessName(String _PProcessName) {
        if (readOnly)
            throw new Error("This should never happen! setPProcessName on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(PProcessName, _PProcessName);
        PProcessName = _PProcessName;
//        return bDiff;
    }

    /**
     * Return value of PProcessName column.
     * @return value of PProcessName column.
     */
    public String getPProcessName() {
        return PProcessName;
    }
    
    private String PProcessDefinitionName = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PPROCESSDEFINITIONNAME = 11;
    

    /**
     * Sets PProcessDefinitionName column.
     * @param _PProcessDefinitionName new column value.
     */
    public void setPProcessDefinitionName(String _PProcessDefinitionName) {
        if (readOnly)
            throw new Error("This should never happen! setPProcessDefinitionName on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(PProcessDefinitionName, _PProcessDefinitionName);
        PProcessDefinitionName = _PProcessDefinitionName;
//        return bDiff;
    }

    /**
     * Return value of PProcessDefinitionName column.
     * @return value of PProcessDefinitionName column.
     */
    public String getPProcessDefinitionName() {
        return PProcessDefinitionName;
    }
    
    private String PProcessDefinitionVersion = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PPROCESSDEFINITIONVERSION = 12;
    

    /**
     * Sets PProcessDefinitionVersion column.
     * @param _PProcessDefinitionVersion new column value.
     */
    public void setPProcessDefinitionVersion(String _PProcessDefinitionVersion) {
        if (readOnly)
            throw new Error("This should never happen! setPProcessDefinitionVersion on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(PProcessDefinitionVersion, _PProcessDefinitionVersion);
        PProcessDefinitionVersion = _PProcessDefinitionVersion;
//        return bDiff;
    }

    /**
     * Return value of PProcessDefinitionVersion column.
     * @return value of PProcessDefinitionVersion column.
     */
    public String getPProcessDefinitionVersion() {
        return PProcessDefinitionVersion;
    }
    
    private String PActivityDefinitionId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PACTIVITYDEFINITIONID = 13;
    

    /**
     * Sets PActivityDefinitionId column.
     * @param _PActivityDefinitionId new column value.
     */
    public void setPActivityDefinitionId(String _PActivityDefinitionId) {
        if (readOnly)
            throw new Error("This should never happen! setPActivityDefinitionId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(PActivityDefinitionId, _PActivityDefinitionId);
        PActivityDefinitionId = _PActivityDefinitionId;
//        return bDiff;
    }

    /**
     * Return value of PActivityDefinitionId column.
     * @return value of PActivityDefinitionId column.
     */
    public String getPActivityDefinitionId() {
        return PActivityDefinitionId;
    }
    
    private String PActivitySetDefinitionId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PACTIVITYSETDEFINITIONID = 14;
    

    /**
     * Sets PActivitySetDefinitionId column.
     * @param _PActivitySetDefinitionId new column value.
     */
    public void setPActivitySetDefinitionId(String _PActivitySetDefinitionId) {
        if (readOnly)
            throw new Error("This should never happen! setPActivitySetDefinitionId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(PActivitySetDefinitionId, _PActivitySetDefinitionId);
        PActivitySetDefinitionId = _PActivitySetDefinitionId;
//        return bDiff;
    }

    /**
     * Return value of PActivitySetDefinitionId column.
     * @return value of PActivitySetDefinitionId column.
     */
    public String getPActivitySetDefinitionId() {
        return PActivitySetDefinitionId;
    }
    
    private String PProcessDefinitionId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PPROCESSDEFINITIONID = 15;
    

    /**
     * Sets PProcessDefinitionId column.
     * @param _PProcessDefinitionId new column value.
     */
    public void setPProcessDefinitionId(String _PProcessDefinitionId) {
        if (readOnly)
            throw new Error("This should never happen! setPProcessDefinitionId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(PProcessDefinitionId, _PProcessDefinitionId);
        PProcessDefinitionId = _PProcessDefinitionId;
//        return bDiff;
    }

    /**
     * Return value of PProcessDefinitionId column.
     * @return value of PProcessDefinitionId column.
     */
    public String getPProcessDefinitionId() {
        return PProcessDefinitionId;
    }
    
    private String PPackageId = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_PPACKAGEID = 16;
    

    /**
     * Sets PPackageId column.
     * @param _PPackageId new column value.
     */
    public void setPPackageId(String _PPackageId) {
        if (readOnly)
            throw new Error("This should never happen! setPPackageId on "
			    + this +" is being called although readOnly is true");
//        boolean bDiff = GenericDO.isNewDataDifferent_String(PPackageId, _PPackageId);
        PPackageId = _PPackageId;
//        return bDiff;
    }

    /**
     * Return value of PPackageId column.
     * @return value of PPackageId column.
     */
    public String getPPackageId() {
        return PPackageId;
    }
    
    private java.math.BigDecimal CNT = null;

    /**
     * Used for query caching.
     */
    static public final int COLUMN_CNT = 17;
    

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
    static public final int COLUMN_OID = 18;

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
              case COLUMN_UTCTIME:
                   return QueryBuilder.compare(getUTCTime(),cond.getValue(),cond.getOperator());

              case COLUMN_THETYPE:
                   return QueryBuilder.compare(getTheType(),cond.getValue(),cond.getOperator());

              case COLUMN_PROCESSID:
                   return QueryBuilder.compare(getProcessId(),cond.getValue(),cond.getOperator());

              case COLUMN_PROCESSNAME:
                   return QueryBuilder.compare(getProcessName(),cond.getValue(),cond.getOperator());

              case COLUMN_PROCESSDEFINITIONNAME:
                   return QueryBuilder.compare(getProcessDefinitionName(),cond.getValue(),cond.getOperator());

              case COLUMN_PROCESSDEFINITIONVERSION:
                   return QueryBuilder.compare(getProcessDefinitionVersion(),cond.getValue(),cond.getOperator());

              case COLUMN_PROCESSDEFINITIONID:
                   return QueryBuilder.compare(getProcessDefinitionId(),cond.getValue(),cond.getOperator());

              case COLUMN_PACKAGEID:
                   return QueryBuilder.compare(getPackageId(),cond.getValue(),cond.getOperator());

              case COLUMN_PACTIVITYID:
                   return QueryBuilder.compare(getPActivityId(),cond.getValue(),cond.getOperator());

              case COLUMN_PPROCESSID:
                   return QueryBuilder.compare(getPProcessId(),cond.getValue(),cond.getOperator());

              case COLUMN_PPROCESSNAME:
                   return QueryBuilder.compare(getPProcessName(),cond.getValue(),cond.getOperator());

              case COLUMN_PPROCESSDEFINITIONNAME:
                   return QueryBuilder.compare(getPProcessDefinitionName(),cond.getValue(),cond.getOperator());

              case COLUMN_PPROCESSDEFINITIONVERSION:
                   return QueryBuilder.compare(getPProcessDefinitionVersion(),cond.getValue(),cond.getOperator());

              case COLUMN_PACTIVITYDEFINITIONID:
                   return QueryBuilder.compare(getPActivityDefinitionId(),cond.getValue(),cond.getOperator());

              case COLUMN_PACTIVITYSETDEFINITIONID:
                   return QueryBuilder.compare(getPActivitySetDefinitionId(),cond.getValue(),cond.getOperator());

              case COLUMN_PPROCESSDEFINITIONID:
                   return QueryBuilder.compare(getPProcessDefinitionId(),cond.getValue(),cond.getOperator());

              case COLUMN_PPACKAGEID:
                   return QueryBuilder.compare(getPPackageId(),cond.getValue(),cond.getOperator());

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
    public CreateProcessEventAuditDataStruct duplicate() 
    throws DatabaseManagerException, ObjectIdException {
        CreateProcessEventAuditDataStruct ret = new CreateProcessEventAuditDataStruct ();
        if (!isEmpty) {
            ret.UTCTime = GenericDO.copyString(UTCTime);
            ret.TheType = GenericDO.copyBigDecimal(TheType);
            ret.ProcessId = GenericDO.copyString(ProcessId);
            ret.ProcessName = GenericDO.copyString(ProcessName);
            ret.ProcessDefinitionName = GenericDO.copyString(ProcessDefinitionName);
            ret.ProcessDefinitionVersion = GenericDO.copyString(ProcessDefinitionVersion);
            ret.ProcessDefinitionId = GenericDO.copyString(ProcessDefinitionId);
            ret.PackageId = GenericDO.copyString(PackageId);
            ret.PActivityId = GenericDO.copyString(PActivityId);
            ret.PProcessId = GenericDO.copyString(PProcessId);
            ret.PProcessName = GenericDO.copyString(PProcessName);
            ret.PProcessDefinitionName = GenericDO.copyString(PProcessDefinitionName);
            ret.PProcessDefinitionVersion = GenericDO.copyString(PProcessDefinitionVersion);
            ret.PActivityDefinitionId = GenericDO.copyString(PActivityDefinitionId);
            ret.PActivitySetDefinitionId = GenericDO.copyString(PActivitySetDefinitionId);
            ret.PProcessDefinitionId = GenericDO.copyString(PProcessDefinitionId);
            ret.PPackageId = GenericDO.copyString(PPackageId);
            ret.CNT = GenericDO.copyBigDecimal(CNT);

        }
        ret.set_OId(get_OId());
        ret.set_Version(get_Version());
        ret.databaseName=get_Database();
        ret.isEmpty = isEmpty;
        return ret;
    }
}
