package org.enhydra.shark;


import org.enhydra.shark.api.client.wfservice.*;

/**
 * The client interface through which client accesses specific
 * admin interfaces to perform the various actions on engine.
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public class Administration implements AdminInterface {

   protected Administration () {}

   public AdminMisc getAdminMisc () {
      return SharkEngineManager.getInstance().getObjectFactory().createAdminMisc();
   }

   public ExecutionAdministration getExecutionAdministration () {
      return SharkEngineManager.getInstance().getObjectFactory().createExecutionAdministration();
   }

   public ParticipantMappingAdministration getParticipantMappingAdministration () {
       return SharkEngineManager.getInstance().getObjectFactory().createParticipantMappingAdministration();
   }

   public ApplicationMappingAdministration getApplicationMappingAdministration() {
        return SharkEngineManager.getInstance().getObjectFactory().createApplicationMappingAdministration();
   }

   public ScriptMappingAdministration getScriptMappingAdministration () {
        return SharkEngineManager.getInstance().getObjectFactory().createScriptMappingAdministration();
   }

   public PackageAdministration getPackageAdministration() {
      return SharkEngineManager.getInstance().getObjectFactory().createPackageAdministration();
   }

   public UserGroupAdministration getUserGroupAdministration() {
      return SharkEngineManager.getInstance().getObjectFactory().createUserGroupAdministration();
   }

   public CacheAdministration getCacheAdministration() {
      return SharkEngineManager.getInstance().getObjectFactory().createCacheAdministration();
   }

   public DeadlineAdministration getDeadlineAdministration() {
      return SharkEngineManager.getInstance().getObjectFactory().createDeadlineAdministration();
   }

   public LimitAdministration getLimitAdministration() {
      return SharkEngineManager.getInstance().getObjectFactory().createLimitAdministration();
   }


}
