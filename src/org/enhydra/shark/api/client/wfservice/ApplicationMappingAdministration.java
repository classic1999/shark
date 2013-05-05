
package org.enhydra.shark.api.client.wfservice;

import org.enhydra.shark.api.ApplicationMappingTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;

import java.util.Map;

/**
 * Used to handle mapping among XPDL application definitions and
 * shark's tool agents.
 *
 * @author Zoran Milakovic
 *
 */
public interface ApplicationMappingAdministration {

   /**
    * This method is used to let shark know who uses this API.
    *
    * @param    userId              String representing user Id.
    *
    */
   void connect (String userId);

   /**
    * Returns array of ApplicationMap objects that represent applications
    * from XPDLs that are currently loaded into engine. Only relevant attributes
    * of these objects are packageId, processDefinitionId and applicationId.
    *
    * @return   Array of ApplicationMap objects representing applications
    * from XPDLs that are currently loaded into engine.
    * @throws   BaseException If something unexpected happens.
    */
   ApplicationMap[] getAllApplications () throws BaseException;

   /**
    * Returns array of ApplicationMap objects that represent applications
    * from XPDLs that are currently loaded into engine. Only relevant attributes
    * of these objects are packageId, processDefinitionId and applicationId.
    *
    * @param    t ApplicationMappingTransaction.
    * @return   Array of ApplicationMap objects representing applications
    * from XPDLs that are currently loaded into engine.
    * @throws   BaseException If something unexpected happens.
    */
   ApplicationMap[] getAllApplications (ApplicationMappingTransaction t) throws BaseException;

   /**
    * Returns array of ApplicationMap objects that represent applications
    * from XPDL package determined by given Id. Only relevant attributes
    * of these objects are packageId, processDefinitionId and applicationId.
    *
    * @param    pkgId Package Id
    * @return   Array of ApplicationMap objects representing applications
    * from XPDL package determined by given pkgId parameter.
    * @throws   BaseException If something unexpected happens.
    */
   ApplicationMap[] getAllApplications (String pkgId) throws BaseException;

   /**
    * Returns array of ApplicationMap objects that represent applications
    * from XPDL package determined by given Id. Only relevant attributes
    * of these objects are packageId, processDefinitionId and applicationId.
    *
    * @param    t ApplicationMappingTransaction.
    * @param    pkgId Package Id
    * @return   Array of ApplicationMap objects representing applications
    * from XPDL package determined by given pkgId parameter.
    * @throws   BaseException If something unexpected happens.
    */
   ApplicationMap[] getAllApplications (ApplicationMappingTransaction t,String pkgId) throws BaseException;

   /**
    * Returns array of ApplicationMap objects that represent applications
    * from XPDL package and process determined by given Ids. Only relevant attributes
    * of these objects are packageId, processDefinitionId and applicationId.
    *
    * @param    pkgId Package Id
    * @param    pDefId Process definition Id
    * @return   Array of ApplicationMap objects representing applications
    * from XPDL package determined by given pkgId and pDefId parameters.
    * @throws   BaseException If something unexpected happens.
    */
   ApplicationMap[] getAllApplications (String pkgId,String pDefId) throws BaseException;

   /**
    * Returns array of ApplicationMap objects that represent applications
    * from XPDL package and process determined by given Ids. Only relevant attributes
    * of these objects are packageId, processDefinitionId and applicationId.
    *
    * @param    t ApplicationMappingTransaction.
    * @param    pkgId Package Id
    * @param    pDefId Process definition Id
    * @return   Array of ApplicationMap objects representing applications
    * from XPDL package determined by given pkgId and pDefId parameters.
    * @throws   BaseException If something unexpected happens.
    */
   ApplicationMap[] getAllApplications (ApplicationMappingTransaction t,String pkgId,String pDefId) throws BaseException;

   /**
    * Returns array of class names of tool agents that can be used with shark.
    *
    * @return   String array of class names of tool agents that can be used
    * with shark.
    * @throws   BaseException If something unexpected happens.
    */
   String[] getDefinedToolAgents () throws BaseException;

   /**
    * Returns array of class names of tool agents that can be used with shark.
    *
    * @param    t ApplicationMappingTransaction.
    * @return   String array of class names of tool agents that can be used
    * with shark.
    * @throws   BaseException If something unexpected happens.
    */
   String[] getDefinedToolAgents (ApplicationMappingTransaction t) throws BaseException;

   /**
    * Returns a map which keys are the full class names of tool agents, and
    * values are information about tool agents.
    *
    * @return   a Map
    * @throws   BaseException If something unexpected happens.
    */
   Map getToolAgentsInfo () throws BaseException;

   /**
    * Returns a map which keys are the full class names of tool agents, and
    * values are information about tool agents.
    *
    * @param    t ApplicationMappingTransaction.
    * @return   a Map
    * @throws   BaseException If something unexpected happens.
    */
   Map getToolAgentsInfo (ApplicationMappingTransaction t) throws BaseException;

   /**
    * Returns a string which represents tool agent description.
    *
    * @return   a String
    * @throws   BaseException If something unexpected happens.
    */
   String getToolAgentInfo (String toolAgentFullClassName) throws BaseException;

   /**
    * Returns a string which represents tool agent description.
    *
    * @param    t ApplicationMappingTransaction.
    * @return   a String
    * @throws   BaseException If something unexpected happens.
    */
   String getToolAgentInfo (ApplicationMappingTransaction t,String toolAgentFullClassName) throws BaseException;

   /**
    * Adds new application to tool agent mapping. If the mapping information
    * contained in ApplicationMap object contains packageId, processDefinitionId,
    * applicationDefId and toolAgentName information that is same as of any
    * existing mappings, the update of existing mapping is performed.
    *
    * @param    am  ApplicationMap that contains mapping information.
    * @throws   BaseException If something unexpected happens.
    */
   public void addApplicationMapping (ApplicationMap am) throws BaseException;

   /**
    * Adds new application to tool agent mapping. If the mapping information
    * contained in ApplicationMap object contains packageId, processDefinitionId,
    * applicationDefId and toolAgentName information that is same as of any
    * existing mappings, the update of existing mapping is performed.
    *
    * @param    t ApplicationMappingTransaction.
    * @param    am  ApplicationMap that contains mapping information.
    * @throws   BaseException If something unexpected happens.
    */
   public void addApplicationMapping (ApplicationMappingTransaction t,ApplicationMap am) throws BaseException;

   /**
    * Returns ApplicationMap object that represents mapping for XPDL application
    * represented with the given parameters.
    *
    * @param    pkgId               package Id.
    * @param    pDefId              processDefinition Id.
    * @param    appDefId            applicationDef Id.
    *
    * @return   ApplicationMap representing required mapping.
    * @throws   BaseException If something unexpected happens.
    */
   public ApplicationMap getApplicationMapping (String pkgId,
                                                String pDefId,
                                                String appDefId) throws BaseException;

   /**
    * Returns ApplicationMap object that represents mapping for XPDL application
    * represented with the given parameters.
    *
    * @param    t                   ApplicationMappingTransaction.
    * @param    pkgId               package Id.
    * @param    pDefId              processDefinition Id.
    * @param    appDefId            applicationDef Id.
    * @return   ApplicationMap representing required mapping.
    * @throws   BaseException If something unexpected happens.
    */
   public ApplicationMap getApplicationMapping (ApplicationMappingTransaction t,
                                                String pkgId,
                                                String pDefId,
                                                String appDefId) throws BaseException;

   /**
    * Removes application from tool agent mapping.
    *
    * @param    packageId           package Id.
    * @param    processDefinitionId processDefinition Id.
    * @param    applicationId       application Id.
    * @throws   BaseException If something unexpected happens.
    */
   public void removeApplicationMapping (String packageId,
                                         String processDefinitionId,
                                         String applicationId) throws BaseException;

   /**
    * Removes application from tool agent mapping.
    *
    * @param    t                   ApplicationMappingTransaction.
    * @param    packageId           package Id.
    * @param    processDefinitionId processDefinition Id.
    * @param    applicationId       application Id.
    * @throws   BaseException If something unexpected happens.
    */
   public void removeApplicationMapping (ApplicationMappingTransaction t,
                                         String packageId,
                                         String processDefinitionId,
                                         String applicationId) throws BaseException;

   /**
    * Returns all mappings that map XPDL applications to Tool agents.
    *
    * @return   Array of ApplicationMap objects that represent mappings.
    * @throws   BaseException If something unexpected happens.
    */
   public ApplicationMap[] getApplicationMappings () throws BaseException; // returns ApplicationMap

   /**
    * Returns all mappings that map XPDL applications to Tool agents.
    *
    * @param    t ApplicationMappingTransaction.
    * @return   Array of ApplicationMap objects that represent mappings.
    * @throws   BaseException If something unexpected happens.
    */
   public ApplicationMap[] getApplicationMappings (ApplicationMappingTransaction t) throws BaseException; // returns ApplicationMap

   /**
    * Creates and returns an empty implementation of ApplicationMap interface.
    *
    * @return  Created empty ApplicationMap instance.
    */
   public ApplicationMap createApplicationMap ();

   /**
    * Creates and returns an empty implementation of ApplicationMap interface.
    *
    * @param    t                   a  ApplicationMappingTransaction
    * @return  Created empty ApplicationMap instance.
    */
   public ApplicationMap createApplicationMap (ApplicationMappingTransaction t);
}
