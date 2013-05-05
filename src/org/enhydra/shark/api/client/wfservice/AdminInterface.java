package org.enhydra.shark.api.client.wfservice;


/**
 * Interface used to perform some administrative operations.
 * Through this interface, you can get the administration interface of interest.
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public interface AdminInterface {

   /** Returns new instance of interface for administering packages (XPDLs). */
   PackageAdministration getPackageAdministration();

   /** Returns new instance of interface for administering organizational structure. */
   UserGroupAdministration getUserGroupAdministration();

   /** Returns new instance of interface for execution objects administering. */
   ExecutionAdministration getExecutionAdministration ();

   /**
    Returns new instance of interface for administering XPDL Participant
    mapping information.
    */
   ParticipantMappingAdministration getParticipantMappingAdministration ();

   /**
    Returns new instance of interface for administering XPDL Application
    mapping information.
    */
   ApplicationMappingAdministration getApplicationMappingAdministration ();

   /**
    Returns new instance of interface for administering XPDL Script
    mapping information.
    */
   ScriptMappingAdministration getScriptMappingAdministration ();

   /** Returns new instance of interface for Miscellaneous administering. */
   AdminMisc getAdminMisc ();

   /** Returns new instance of interface for administering shark's cache of runtime objects. */
   CacheAdministration getCacheAdministration ();

   /** Returns new instance of interface for administering deadlines. */
   DeadlineAdministration getDeadlineAdministration();

   /** Returns new instance of interface for administering limits. */
   LimitAdministration getLimitAdministration();
}
