package org.enhydra.shark;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.client.timebase.UtcT;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.SourceNotAvailable;
import org.enhydra.shark.api.client.wfmodel.WfExecutionObject;
import org.enhydra.shark.api.client.wfservice.WfPackageEventAudit;
import org.enhydra.shark.persistence.PackageEventAuditPersistenceInterface;
import org.enhydra.shark.utilities.MiscUtilities;


/**
 * WfPackageEventAuditImpl - Workflow Event Audit implementation
 */
public class WfPackageEventAuditImpl extends WfEventAuditWrapper
   implements WfPackageEventAudit, PackageEventAuditPersistenceInterface {

   private String packageVersion;
   private String resourceUsername;

   protected WfPackageEventAuditImpl(org.enhydra.shark.xpdl.elements.Package pkg,
                                     String eventType,String resourceUsername) throws BaseException{
      this.timeStamp = new UtcT(
         MiscUtilities.getAbsoluteTimeInUTCFormat(),0,(short)0,(short)0);
      this.eventType=eventType;
      this.packageId=pkg.getId();
      this.packageVersion=pkg.getInternalVersion();
      this.resourceUsername=resourceUsername;
      try {
         persist(null);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String package_version () {
      return packageVersion;
   }

   public String resource_username () {
      return resourceUsername;
   }

   public WfExecutionObject source () throws BaseException, SourceNotAvailable {
      throw new SourceNotAvailable("The source of WfPackageEventAudit is not available!");
   }

   // Interface used for Persistence

   /**
    * Used to create object when restoring it from database.
    */
   public WfPackageEventAuditImpl () {
      //
   }

   public void setPackageVersion (String pkgVersion) {
      packageVersion=pkgVersion;
   }

   public String getPackageVersion () {
      return packageVersion;
   }

   public void setResourceUsername (String resUsername) {
      resourceUsername=resUsername;
   }

   public String getResourceUsername () {
      return resourceUsername;
   }

   public void setUTCTime (String time) {
      try {
         timeStamp = new UtcT(Long.parseLong(time),0,(short)0,(short)0);
      } catch (Exception ex) {}
   }

   public String getUTCTime () {
      return String.valueOf(timeStamp.time);
   }

   public void setType (String type) {
      this.eventType=type;
   }

   public String getType () {
      return eventType;
   }

   public void setActivityId (String aId) {}

   public String getActivityId () {
      return null;
   }

   public void setActivityName (String an) {}

   public String getActivityName () {
      return null;
   }

   public void setProcessId (String pId) {}

   public String getProcessId () {
      return null;
   }

   public void setProcessName (String n) {}

   public String getProcessName () {
      return null;
   }

   public void setProcessDefinitionName (String pn) {}

   public String getProcessDefinitionName () {
      return null;
   }

   public void setProcessDefinitionVersion (String pdv) {}

   public String getProcessDefinitionVersion () {
      return null;
   }

   public void setActivityDefinitionId (String adId) {}

   public String getActivityDefinitionId () {
      return null;
   }

   public void setActivitySetDefinitionId (String asdId) {}

   public String getActivitySetDefinitionId () {
      return null;
   }

   public void setProcessDefinitionId (String pdId) {}

   public String getProcessDefinitionId () {
      return null;
   }

   public void setPackageId (String pkgId) {
      packageId=pkgId;
   }

   public String getPackageId () {
      return packageId;
   }

   public void persist (SharkTransaction t) throws TransactionException {
      String log="UTCTime="+getUTCTime()+",EventType="+getType()+
         ",PackageId="+getPackageId()+",PackageVersion="+getPackageVersion()+
         ",EventPerformedBy="+getResourceUsername();
      SharkEngineManager.getInstance().getCallbackUtilities().info("PackageEventLogger",log);
   }

   public void refresh () {
   }

   public void delete (SharkTransaction t) throws TransactionException {
   }

}
