package org.enhydra.shark.api.client.wfservice;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;

/**
 * Interface used to perform some administrative operations.
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public interface AdminMisc {

   /**
    * This method is used to let shark know who uses this API.
    *
    * @param    userId              String representing user Id.
    *
    */
   void connect (String userId);

   /**
    * Returns the [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL package definition for the current version of package that has
    * the given Id.
    *
    * @param    pkgId        package Id.
    * @return   The [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of the package.
    * @throws   BaseException If something unexpected happens
    */
   String[][] getPackageExtendedAttributeNameValuePairs (String pkgId) throws BaseException;

   /**
    * Returns the [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL package definition for the current version of package that has
    * the given Id.
    *
    * @param    t SharkTransaction.
    * @param    pkgId        package Id.
    * @return   The [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of the package.
    * @throws   BaseException If something unexpected happens
    */
   String[][] getPackageExtendedAttributeNameValuePairs (SharkTransaction t,String pkgId) throws BaseException;

   /**
    * Returns the names of all specified XPDL ExtendedAttribute elements of XPDL
    * package definition for the current version of package that has the given Id.
    *
    * @param    pkgId        package Id.
    * @return   The names of all specified XPDL ExtendedAttribute elements of
    * the package.
    * @throws   BaseException If something unexpected happens
    */
   String[] getPackageExtendedAttributeNames (String pkgId) throws BaseException;

   /**
    * Returns the names of all specified XPDL ExtendedAttribute elements of XPDL
    * package definition for the current version of package that has the given Id.
    *
    * @param    t SharkTransaction.
    * @param    pkgId        package Id.
    * @return   The names of all specified XPDL ExtendedAttribute elements of
    * the package.
    * @throws   BaseException If something unexpected happens
    */
   String[] getPackageExtendedAttributeNames (SharkTransaction t,String pkgId) throws BaseException;

   /**
    * Returns string representation of the value of specified XPDL ExtendedAttribute
    * of XPDL package definition for the current version of the package that has the given Id.
    *
    * @param    pkgId        package Id.
    * @param    extAttrName   the name of XPDL ext. attribute.
    * @return   The value of specified XPDL ExtendedAttribute of the package.
    * @throws   BaseException If something unexpected happens (i.e. if there
    * are is no such ext. attr)
    */
   String getPackageExtendedAttributeValue (String pkgId,String extAttrName) throws BaseException;

   /**
    * Returns string representation of the value of specified XPDL ExtendedAttribute
    * of XPDL package definition for the current version of the package that has the given Id.
    *
    * @param    t SharkTransaction.
    * @param    pkgId        package Id.
    * @param    extAttrName   the name of XPDL ext. attribute.
    * @return   The value of specified XPDL ExtendedAttribute of the package.
    * @throws   BaseException If something unexpected happens (i.e. if there
    * are is no such ext. attr)
    */
   String getPackageExtendedAttributeValue (SharkTransaction t,String pkgId,String extAttrName) throws BaseException;

   /**
    * Returns the [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL process definition for the process manager with a
    * given name.
    *
    * @param    mgrName        manager name.
    * @return   The [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of the process.
    * @throws   BaseException If something unexpected happens
    */
   String[][] getProcessDefinitionExtendedAttributeNameValuePairs (String mgrName) throws BaseException;

   /**
    * Returns the [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL process definition for the process manager with a
    * given name.
    *
    * @param    t SharkTransaction.
    * @param    mgrName        manager name.
    * @return   The [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of the process.
    * @throws   BaseException If something unexpected happens
    */
   String[][] getProcessDefinitionExtendedAttributeNameValuePairs (SharkTransaction t,String mgrName) throws BaseException;

   /**
    * Returns the names of all specified XPDL ExtendedAttribute elements of XPDL
    * process definition for the process manager with a given name.
    *
    * @param    mgrName        manager name.
    * @return   The names of all specified XPDL ExtendedAttribute elements of
    * the process.
    * @throws   BaseException If something unexpected happens
    */
   String[] getProcessDefinitionExtendedAttributeNames (String mgrName) throws BaseException;

   /**
    * Returns the names of all specified XPDL ExtendedAttribute elements of XPDL
    * process definition for the process manager with a given name.
    *
    * @param    t SharkTransaction.
    * @param    mgrName        manager name.
    * @return   The names of all specified XPDL ExtendedAttribute elements of
    * the process.
    * @throws   BaseException If something unexpected happens
    */
   String[] getProcessDefinitionExtendedAttributeNames (SharkTransaction t,String mgrName) throws BaseException;

   /**
    * Returns string representation of the value of specified XPDL ExtendedAttribute
    * of XPDL process definition for the process manager with a given Id.
    *
    * @param    mgrName        manager name.
    * @param    extAttrName   the name of XPDL ext. attribute.
    * @return   The value of specified XPDL ExtendedAttribute of the process.
    * @throws   BaseException If something unexpected happens (i.e. if there
    * are is no such ext. attr)
    */
   String getProcessDefinitionExtendedAttributeValue (String mgrName,String extAttrName) throws BaseException;

   /**
    * Returns string representation of the value of specified XPDL ExtendedAttribute
    * of XPDL process definition for the process manager with a given Id.
    *
    * @param    t SharkTransaction.
    * @param    mgrName        manager name.
    * @param    extAttrName   the name of XPDL ext. attribute.
    * @return   The value of specified XPDL ExtendedAttribute of the process.
    * @throws   BaseException If something unexpected happens (i.e. if there
    * are is no such ext. attr)
    */
   String getProcessDefinitionExtendedAttributeValue (SharkTransaction t,String mgrName,String extAttrName) throws BaseException;

   /**
    * Returns the [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL process definition for the process instance with a
    * given name.
    *
    * @param    procId        process Id.
    * @return   The [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of the process.
    * @throws   BaseException If something unexpected happens
    */
   String[][] getProcessExtendedAttributeNameValuePairs (String procId) throws BaseException;

   /**
    * Returns the [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL process definition for the process instance that has
    * the given Id.
    *
    * @param    t SharkTransaction.
    * @param    procId        process Id.
    * @return   The [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of the process.
    * @throws   BaseException If something unexpected happens
    */
   String[][] getProcessExtendedAttributeNameValuePairs (SharkTransaction t,String procId) throws BaseException;

   /**
    * Returns the names of all specified XPDL ExtendedAttribute elements of XPDL
    * process definition for the process instance that has the given Id.
    *
    * @param    procId        process Id.
    * @return   The names of all specified XPDL ExtendedAttribute elements of
    * the process.
    * @throws   BaseException If something unexpected happens
    */
   String[] getProcessExtendedAttributeNames (String procId) throws BaseException;

   /**
    * Returns the names of all specified XPDL ExtendedAttribute elements of XPDL
    * process definition for the process instance that has the given Id.
    *
    * @param    t SharkTransaction.
    * @param    procId        process Id.
    * @return   The names of all specified XPDL ExtendedAttribute elements of
    * the process.
    * @throws   BaseException If something unexpected happens
    */
   String[] getProcessExtendedAttributeNames (SharkTransaction t,String procId) throws BaseException;

   /**
    * Returns string representation of the value of specified XPDL ExtendedAttribute
    * of XPDL process definition for the process instance that has the given Id.
    *
    * @param    procId        process Id.
    * @param    extAttrName   the name of XPDL ext. attribute.
    * @return   The value of specified XPDL ExtendedAttribute of the process.
    * @throws   BaseException If something unexpected happens (i.e. if there
    * are is no such ext. attr)
    */
   String getProcessExtendedAttributeValue (String procId,String extAttrName) throws BaseException;

   /**
    * Returns string representation of the value of specified XPDL ExtendedAttribute
    * of XPDL process definition for the process instance that has the given Id.
    *
    * @param    t SharkTransaction.
    * @param    procId        process Id.
    * @param    extAttrName   the name of XPDL ext. attribute.
    * @return   The value of specified XPDL ExtendedAttribute of the process.
    * @throws   BaseException If something unexpected happens (i.e. if there
    * are is no such ext. attr)
    */
   String getProcessExtendedAttributeValue (SharkTransaction t,String procId,String extAttrName) throws BaseException;

   /**
    * Returns string representation of XPDL ExtendedAttributes tag content of
    * XPDL activity definition for the activity instance that has the given Id.
    *
    * @param    procId activity's process Id.
    * @param    actId   Id of activity instance.
    * @return   The ExtendedAttributes tag content of XPDL definition of the
    * activity.
    * @throws   BaseException If something unexpected happens.
    */
   String getActivitiesExtendedAttributes (String procId,String actId) throws BaseException;

   /**
    * Returns string representation of XPDL ExtendedAttributes tag content of
    * XPDL activity definition for the activity instance that has the given Id.
    *
    * @param    t SharkTransaction.
    * @param    procId activity's process Id.
    * @param    actId   Id of activity instance.
    * @return   The ExtendedAttributes tag content of XPDL definition of the
    * activity.
    * @throws   BaseException If something unexpected happens.
    */
   String getActivitiesExtendedAttributes (SharkTransaction t,String procId,String actId) throws BaseException;

   /**
    * Returns the [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL activity definition for the activity instance that has
    * the given Id.
    *
    * @param    procId        activity's process Id.
    * @param    actId         Id of activity instance.
    * @return   The [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of the activity.
    * @throws   BaseException If something unexpected happens
    */
   String[][] getActivitiesExtendedAttributeNameValuePairs (String procId,String actId) throws BaseException;

   /**
    * Returns the [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL activity definition for the activity instance that has
    * the given Id.
    *
    * @param    t SharkTransaction.
    * @param    procId        activity's process Id.
    * @param    actId         Id of activity instance.
    * @return   The [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of the activity.
    * @throws   BaseException If something unexpected happens
    */
   String[][] getActivitiesExtendedAttributeNameValuePairs (SharkTransaction t,String procId,String actId) throws BaseException;

   /**
    * Returns the names of all specified XPDL ExtendedAttribute elements of XPDL
    * activity definition for the activity instance that has the given Id.
    *
    * @param    procId        activity's process Id.
    * @param    actId         Id of activity instance.
    * @return   The names of all specified XPDL ExtendedAttribute elements of
    * the activity.
    * @throws   BaseException If something unexpected happens
    */
   String[] getActivitiesExtendedAttributeNames (String procId,String actId) throws BaseException;

   /**
    * Returns the names of all specified XPDL ExtendedAttribute elements of XPDL
    * activity definition for the activity instance that has the given Id.
    *
    * @param    t SharkTransaction.
    * @param    procId        activity's process Id.
    * @param    actId         Id of activity instance.
    * @return   The names of all specified XPDL ExtendedAttribute elements of
    * the activity.
    * @throws   BaseException If something unexpected happens
    */
   String[] getActivitiesExtendedAttributeNames (SharkTransaction t,String procId,String actId) throws BaseException;

   /**
    * Returns string representation of the value of specified XPDL ExtendedAttribute
    * of XPDL activity definition for the activity instance that has the given Id.
    *
    * @param    procId        activity's process Id.
    * @param    actId         Id of activity instance.
    * @param    extAttrName   the name of XPDL ext. attribute.
    * @return   The value of specified XPDL ExtendedAttribute of the activity.
    * @throws   BaseException If something unexpected happens (i.e. if there
    * are is no such ext. attr)
    */
   String getActivitiesExtendedAttributeValue (String procId,String actId,String extAttrName) throws BaseException;

   /**
    * Returns string representation of the value of specified XPDL ExtendedAttribute
    * of XPDL activity definition for the activity instance that has the given Id.
    *
    * @param    t SharkTransaction.
    * @param    procId        activity's process Id.
    * @param    actId         Id of activity instance.
    * @param    extAttrName   the name of XPDL ext. attribute.
    * @return   The value of specified XPDL ExtendedAttribute of the activity.
    * @throws   BaseException If something unexpected happens (i.e. if there
    * are is no such ext. attr)
    */
   String getActivitiesExtendedAttributeValue (SharkTransaction t,String procId,String actId,String extAttrName) throws BaseException;

   /**
    * Returns the [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL variable that can be determined by the given parameters.
    * If there is no such variable, BaseException will be thrown..
    *
    * @param    mgrName        manager name.
    * @param    variableId    Id of XPDL variable definition.
    * @return   The [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL variable.
    * @throws   BaseException If something unexpected happens
    */
   String[][] getDefVariableExtendedAttributeNameValuePairs (String mgrName,String variableId) throws BaseException;

   /**
    * Returns the [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL variable that can be determined by the given parameters.
    * If there is no such variable, BaseException will be thrown.
    *
    * @param    t SharkTransaction.
    * @param    mgrName        manager name.
    * @param    variableId    Id of XPDL variable definition.
    * @return   The [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL variable.
    * @throws   BaseException If something unexpected happens
    */
   String[][] getDefVariableExtendedAttributeNameValuePairs (SharkTransaction t,String mgrName,String variableId) throws BaseException;

   /**
    * Returns the names of all specified XPDL ExtendedAttribute elements of XPDL
    * variable that can be determined by the given parameters.
    * If there is no such variable, BaseException will be thrown.
    *
    * @param    mgrName        manager name.
    * @param    variableId    Id of XPDL variable definition.
    * @return   The names of all specified XPDL ExtendedAttribute elements of
    * XPDL variable.
    * @throws   BaseException If something unexpected happens
    */
   String[] getDefVariableExtendedAttributeNames (String mgrName,String variableId) throws BaseException;

   /**
    * Returns the names of all specified XPDL ExtendedAttribute elements of XPDL
    * variable that can be determined by the given parameters.
    * If there is no such variable, BaseException will be thrown.
    *
    * @param    t SharkTransaction.
    * @param    mgrName        manager name.
    * @param    variableId    Id of XPDL variable definition.
    * @return   The names of all specified XPDL ExtendedAttribute elements of
    * XPDL variable.
    * @throws   BaseException If something unexpected happens
    */
   String[] getDefVariableExtendedAttributeNames (SharkTransaction t,String mgrName,String variableId) throws BaseException;

   /**
    * Returns string representation of the value of specified XPDL ExtendedAttribute
    * of XPDL variable that can be determined by the given parameters.
    * If there is no such variable, BaseException will be thrown.
    *
    * @param    mgrName        manager name.
    * @param    variableId    Id of XPDL variable definition.
    * @param    extAttrName   the name of XPDL ext. attribute.
    * @return   The value of specified XPDL ExtendedAttribute of XPDL variable.
    * @throws   BaseException If something unexpected happens (i.e. if there
    * are is no such ext. attr)
    */
   String getDefVariableExtendedAttributeValue (String mgrName,String variableId,String extAttrName) throws BaseException;

   /**
    * Returns string representation of the value of specified XPDL ExtendedAttribute
    * of XPDL variable that can be determined by the given parameters.
    * If there is no such variable, BaseException will be thrown.
    *
    * @param    t SharkTransaction.
    * @param    mgrName        manager name.
    * @param    variableId    Id of XPDL variable definition.
    * @param    extAttrName   the name of XPDL ext. attribute.
    * @return   The value of specified XPDL ExtendedAttribute of XPDL variable.
    * @throws   BaseException If something unexpected happens (i.e. if there
    * are is no such ext. attr)
    */
   String getDefVariableExtendedAttributeValue (SharkTransaction t,String mgrName,String variableId,String extAttrName) throws BaseException;

   /**
    * Returns the [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL variable that can be determined by the given parameters.
    * If there is no such variable, BaseException will be thrown..
    *
    * @param    procId        activity's process Id.
    * @param    variableId    Id of XPDL variable definition.
    * @return   The [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL variable.
    * @throws   BaseException If something unexpected happens
    */
   String[][] getVariableExtendedAttributeNameValuePairs (String procId,String variableId) throws BaseException;

   /**
    * Returns the [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL variable that can be determined by the given parameters.
    * If there is no such variable, BaseException will be thrown.
    *
    * @param    t SharkTransaction.
    * @param    procId        activity's process Id.
    * @param    variableId    Id of XPDL variable definition.
    * @return   The [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL variable.
    * @throws   BaseException If something unexpected happens
    */
   String[][] getVariableExtendedAttributeNameValuePairs (SharkTransaction t,String procId,String variableId) throws BaseException;

   /**
    * Returns the names of all specified XPDL ExtendedAttribute elements of XPDL
    * variable that can be determined by the given parameters.
    * If there is no such variable, BaseException will be thrown.
    *
    * @param    procId        activity's process Id.
    * @param    variableId    Id of XPDL variable definition.
    * @return   The names of all specified XPDL ExtendedAttribute elements of
    * XPDL variable.
    * @throws   BaseException If something unexpected happens
    */
   String[] getVariableExtendedAttributeNames (String procId,String variableId) throws BaseException;

   /**
    * Returns the names of all specified XPDL ExtendedAttribute elements of XPDL
    * variable that can be determined by the given parameters.
    * If there is no such variable, BaseException will be thrown.
    *
    * @param    t SharkTransaction.
    * @param    procId        activity's process Id.
    * @param    variableId    Id of XPDL variable definition.
    * @return   The names of all specified XPDL ExtendedAttribute elements of
    * XPDL variable.
    * @throws   BaseException If something unexpected happens
    */
   String[] getVariableExtendedAttributeNames (SharkTransaction t,String procId,String variableId) throws BaseException;

   /**
    * Returns string representation of the value of specified XPDL ExtendedAttribute
    * of XPDL variable that can be determined by the given parameters.
    * If there is no such variable, BaseException will be thrown.
    *
    * @param    procId        activity's process Id.
    * @param    variableId    Id of XPDL variable definition.
    * @param    extAttrName   the name of XPDL ext. attribute.
    * @return   The value of specified XPDL ExtendedAttribute of XPDL variable.
    * @throws   BaseException If something unexpected happens (i.e. if there
    * are is no such ext. attr)
    */
   String getVariableExtendedAttributeValue (String procId,String variableId,String extAttrName) throws BaseException;

   /**
    * Returns string representation of the value of specified XPDL ExtendedAttribute
    * of XPDL variable that can be determined by the given parameters.
    * If there is no such variable, BaseException will be thrown.
    *
    * @param    t SharkTransaction.
    * @param    procId        activity's process Id.
    * @param    variableId    Id of XPDL variable definition.
    * @param    extAttrName   the name of XPDL ext. attribute.
    * @return   The value of specified XPDL ExtendedAttribute of XPDL variable.
    * @throws   BaseException If something unexpected happens (i.e. if there
    * are is no such ext. attr)
    */
   String getVariableExtendedAttributeValue (SharkTransaction t,String procId,String variableId,String extAttrName) throws BaseException;

   /**
    * Returns the [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL participant that can be determined by the given parameters.
    * If there is no such participant, BaseException will be thrown..
    *
    * @param    pkgId       Id of participant's package.
    * @param    pDefId      Id of participant's process (null if participant is from process level).
    * @param    participantId Id of participant.
    * @return   The [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL participant.
    * @throws   BaseException If something unexpected happens
    */
   String[][] getParticipantExtendedAttributeNameValuePairs (String pkgId,String pDefId,String participantId) throws BaseException;

   /**
    * Returns the [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL participant that can be determined by the given parameters.
    * If there is no such participant, BaseException will be thrown.
    *
    * @param    t SharkTransaction.
    * @param    pkgId       Id of participant's package.
    * @param    pDefId      Id of participant's process (null if participant is from process level).
    * @param    participantId Id of participant.
    * @return   The [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL participant.
    * @throws   BaseException If something unexpected happens
    */
   String[][] getParticipantExtendedAttributeNameValuePairs (SharkTransaction t,String pkgId,String pDefId,String participantId) throws BaseException;

   /**
    * Returns the names of all specified XPDL ExtendedAttribute elements of XPDL
    * participant that can be determined by the given parameters.
    * If there is no such participant, BaseException will be thrown.
    *
    * @param    pkgId       Id of participant's package.
    * @param    pDefId      Id of participant's process (null if participant is from process level).
    * @param    participantId Id of participant.
    * @return   The names of all specified XPDL ExtendedAttribute elements of
    * XPDL participant.
    * @throws   BaseException If something unexpected happens
    */
   String[] getParticipantExtendedAttributeNames (String pkgId,String pDefId,String participantId) throws BaseException;

   /**
    * Returns the names of all specified XPDL ExtendedAttribute elements of XPDL
    * participant that can be determined by the given parameters.
    * If there is no such participant, BaseException will be thrown.
    *
    * @param    t SharkTransaction.
    * @param    pkgId       Id of participant's package.
    * @param    pDefId      Id of participant's process (null if participant is from process level).
    * @param    participantId Id of participant.
    * @return   The names of all specified XPDL ExtendedAttribute elements of
    * XPDL participant.
    * @throws   BaseException If something unexpected happens
    */
   String[] getParticipantExtendedAttributeNames (SharkTransaction t,String pkgId,String pDefId,String participantId) throws BaseException;

   /**
    * Returns string representation of the value of specified XPDL ExtendedAttribute
    * of XPDL participant that can be determined by the given parameters.
    * If there is no such participant, BaseException will be thrown.
    *
    * @param    pkgId       Id of participant's package.
    * @param    pDefId      Id of participant's process (null if participant is from process level).
    * @param    participantId Id of participant.
    * @param    extAttrName   the name of XPDL ext. attribute.
    * @return   The value of specified XPDL ExtendedAttribute of XPDL participant.
    * @throws   BaseException If something unexpected happens (i.e. if there
    * are is no such ext. attr)
    */
   String getParticipantExtendedAttributeValue (String pkgId,String pDefId,String participantId,String extAttrName) throws BaseException;

   /**
    * Returns string representation of the value of specified XPDL ExtendedAttribute
    * of XPDL participant that can be determined by the given parameters.
    * If there is no such participant, BaseException will be thrown.
    *
    * @param    t SharkTransaction.
    * @param    pkgId       Id of participant's package.
    * @param    pDefId      Id of participant's process (null if participant is from process level).
    * @param    participantId Id of participant.
    * @param    extAttrName   the name of XPDL ext. attribute.
    * @return   The value of specified XPDL ExtendedAttribute of XPDL participant.
    * @throws   BaseException If something unexpected happens (i.e. if there
    * are is no such ext. attr)
    */
   String getParticipantExtendedAttributeValue (SharkTransaction t,String pkgId,String pDefId,String participantId,String extAttrName) throws BaseException;

   /**
    * Returns the [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL application that can be determined by the given parameters.
    * If there is no such application, BaseException will be thrown..
    *
    * @param    pkgId       Id of application's package.
    * @param    pDefId      Id of application's process (null if application is from process level).
    * @param    applicationId Id of application.
    * @return   The [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL application.
    * @throws   BaseException If something unexpected happens
    */
   String[][] getApplicationExtendedAttributeNameValuePairs (String pkgId,String pDefId,String applicationId) throws BaseException;

   /**
    * Returns the [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL application that can be determined by the given parameters.
    * If there is no such application, BaseException will be thrown.
    *
    * @param    t SharkTransaction.
    * @param    pkgId       Id of application's package.
    * @param    pDefId      Id of application's process (null if application is from process level).
    * @param    applicationId Id of application.
    * @return   The [name,value] pairs of all specified XPDL ExtendedAttribute
    * elements of XPDL application.
    * @throws   BaseException If something unexpected happens
    */
   String[][] getApplicationExtendedAttributeNameValuePairs (SharkTransaction t,String pkgId,String pDefId,String applicationId) throws BaseException;

   /**
    * Returns the names of all specified XPDL ExtendedAttribute elements of XPDL
    * application that can be determined by the given parameters.
    * If there is no such application, BaseException will be thrown.
    *
    * @param    pkgId       Id of application's package.
    * @param    pDefId      Id of application's process (null if application is from process level).
    * @param    applicationId Id of application.
    * @return   The names of all specified XPDL ExtendedAttribute elements of
    * XPDL application.
    * @throws   BaseException If something unexpected happens
    */
   String[] getApplicationExtendedAttributeNames (String pkgId,String pDefId,String applicationId) throws BaseException;

   /**
    * Returns the names of all specified XPDL ExtendedAttribute elements of XPDL
    * application that can be determined by the given parameters.
    * If there is no such application, BaseException will be thrown.
    *
    * @param    t SharkTransaction.
    * @param    pkgId       Id of application's package.
    * @param    pDefId      Id of application's process (null if application is from process level).
    * @param    applicationId Id of application.
    * @return   The names of all specified XPDL ExtendedAttribute elements of
    * XPDL application.
    * @throws   BaseException If something unexpected happens
    */
   String[] getApplicationExtendedAttributeNames (SharkTransaction t,String pkgId,String pDefId,String applicationId) throws BaseException;

   /**
    * Returns string representation of the value of specified XPDL ExtendedAttribute
    * of XPDL application that can be determined by the given parameters.
    * If there is no such application, BaseException will be thrown.
    *
    * @param    pkgId       Id of application's package.
    * @param    pDefId      Id of application's process (null if application is from process level).
    * @param    applicationId Id of application.
    * @param    extAttrName   the name of XPDL ext. attribute.
    * @return   The value of specified XPDL ExtendedAttribute of XPDL application.
    * @throws   BaseException If something unexpected happens (i.e. if there
    * are is no such ext. attr)
    */
   String getApplicationExtendedAttributeValue (String pkgId,String pDefId,String applicationId,String extAttrName) throws BaseException;

   /**
    * Returns string representation of the value of specified XPDL ExtendedAttribute
    * of XPDL application that can be determined by the given parameters.
    * If there is no such application, BaseException will be thrown.
    *
    * @param    t SharkTransaction.
    * @param    pkgId       Id of application's package.
    * @param    pDefId      Id of application's process (null if application is from process level).
    * @param    applicationId Id of application.
    * @param    extAttrName   the name of XPDL ext. attribute.
    * @return   The value of specified XPDL ExtendedAttribute of XPDL application.
    * @throws   BaseException If something unexpected happens (i.e. if there
    * are is no such ext. attr)
    */
   String getApplicationExtendedAttributeValue (SharkTransaction t,String pkgId,String pDefId,String applicationId,String extAttrName) throws BaseException;

   /**
    * Returns string representing the Id of block activity instance of
    * activity instance that has the given Id.
    *
    * @param    procId activity's process Id.
    * @param    actId   Id of activity instance that we search block
    * activity instance Id for.
    * @return   block activity instance Id if there is one for the given
    * activity instance, or null if given activity does not belong to the block.
    * @throws   BaseException If something unexpected happens.
    */
   String getBlockActivityId (String procId,String actId) throws BaseException;

   /**
    * Returns string representing the Id of block activity instance of
    * activity instance that has the given Id.
    *
    * @param    t  SharkTransaction.
    * @param    procId activity's process Id.
    * @param    actId   Id of activity instance that we search block
    * activity instance Id for.
    * @return   block activity instance Id if there is one for the given
    * activity instance, or null if given activity does not belong to the block.
    * @throws   BaseException If something unexpected happens.
    */
   String getBlockActivityId (SharkTransaction t,String procId,String actId) throws BaseException;

   /**
    * Returns string representing the XPDL definition Id of activity
    * instance that has the given Id.
    *
    * @param    procId activity's process Id.
    * @param    actId   Id of activity instance.
    * @return   XPDL activity definition Id for the given activity instance.
    * @throws   BaseException If something unexpected happens.
    */
   String getActivityDefinitionId (String procId,String actId) throws BaseException;

   /**
    * Returns string representing the XPDL definition Id of activity
    * instance that has the given Id.
    *
    * @param    t   SharkTransaction.
    * @param    procId activity's process Id.
    * @param    actId   Id of activity instance.
    * @return   XPDL activity definition Id for the given activity instance.
    * @throws   BaseException If something unexpected happens.
    */
   String getActivityDefinitionId (SharkTransaction t,String procId,String actId) throws BaseException;

   /**
    * Returns string representing the XPDL definition Id of process
    * instance that has the given Id.
    *
    * @param    procId   Id of process instance.
    * @return   XPDL process definition Id for the given process instance.
    * @throws   BaseException If something unexpected happens.
    */
   String getProcessDefinitionId (String procId) throws BaseException;

   /**
    * Returns string representing the XPDL definition Id of process
    * instance that has the given Id.
    *
    * @param    t SharkTransaction.
    * @param    procId   Id of process instance.
    * @return   XPDL process definition Id for the given process instance.
    * @throws   BaseException If something unexpected happens.
    */
   String getProcessDefinitionId (SharkTransaction t,String procId) throws BaseException;

   /**
    * Returns string representing the XPDL name of variable that can
    * be determined by the given parameters. If there is no such variable,
    * BaseException will be thrown, and if this variable is the FormalParameter
    * of the process, he variableId parameter will be returned.
    *
    * @param    mgrName   process manager name.
    * @param    variableId Id of XPDL variable definition.
    * @return   XPDL name of variable.
    * @throws   BaseException If something unexpected happens.
    */
   String getDefVariableName (String mgrName,String variableId) throws BaseException;

   /**
    * Returns string representing the XPDL name of variable that can
    * be determined by the given parameters. If there is no such variable,
    * BaseException will be thrown, and if this variable is the FormalParameter
    * of the process, the variableId parameter will be returned.
    *
    * @param    t SharkTransaction.
    * @param    mgrName   process manager name.
    * @param    variableId Id of XPDL variable definition.
    * @return   XPDL name of variable.
    * @throws   BaseException If something unexpected happens.
    */
   String getDefVariableName (SharkTransaction t,String mgrName,String variableId) throws BaseException;

   /**
    * Returns string representing the XPDL name of variable that can
    * be determined by the given parameters. If there is no such variable,
    * BaseException will be thrown, and if this variable is the FormalParameter
    * of the process, he variableId parameter will be returned.
    *
    * @param    procId   Id of process instance.
    * @param    variableId Id of XPDL variable definition.
    * @return   XPDL name of variable.
    * @throws   BaseException If something unexpected happens.
    */
   String getVariableName (String procId,String variableId) throws BaseException;

   /**
    * Returns string representing the XPDL name of variable that can
    * be determined by the given parameters. If there is no such variable,
    * BaseException will be thrown, and if this variable is the FormalParameter
    * of the process, the variableId parameter will be returned.
    *
    * @param    t SharkTransaction.
    * @param    procId   Id of process instance.
    * @param    variableId Id of XPDL variable definition.
    * @return   XPDL name of variable.
    * @throws   BaseException If something unexpected happens.
    */
   String getVariableName (SharkTransaction t,String procId,String variableId) throws BaseException;

   /**
    * Returns string representing the XPDL description of variable that can
    * be determined by the given parameters. If there is no such variable,
    * BaseException will be thrown
    *
    * @param    mgrName   process manager name.
    * @param    variableId Id of XPDL variable definition.
    * @return   XPDL description of variable.
    * @throws   BaseException If something unexpected happens.
    */
   String getDefVariableDescription (String mgrName,String variableId) throws BaseException;

   /**
    * Returns string representing the XPDL description of variable that can
    * be determined by the given parameters. If there is no such variable,
    * BaseException will be thrown.
    *
    * @param    t SharkTransaction.
    * @param    mgrName   process manager name.
    * @param    variableId Id of XPDL variable definition.
    * @return   XPDL description of variable.
    * @throws   BaseException If something unexpected happens.
    */
   String getDefVariableDescription (SharkTransaction t,String mgrName,String variableId) throws BaseException;

   /**
    * Returns string representing the XPDL description of variable that can
    * be determined by the given parameters. If there is no such variable,
    * BaseException will be thrown
    *
    * @param    procId   Id of process instance.
    * @param    variableId Id of XPDL variable definition.
    * @return   XPDL description of variable.
    * @throws   BaseException If something unexpected happens.
    */
   String getVariableDescription (String procId,String variableId) throws BaseException;

   /**
    * Returns string representing the XPDL description of variable that can
    * be determined by the given parameters. If there is no such variable,
    * BaseException will be thrown.
    *
    * @param    t SharkTransaction.
    * @param    procId   Id of process instance.
    * @param    variableId Id of XPDL variable definition.
    * @return   XPDL description of variable.
    * @throws   BaseException If something unexpected happens.
    */
   String getVariableDescription (SharkTransaction t,String procId,String variableId) throws BaseException;

   /**
    * Returns string representing the class name of the instance of variable that can
    * be determined by the given parameters. If there is no such variable,
    * BaseException will be thrown.
    *
    * @param    mgrName  process manager name.
    * @param    variableId Id of XPDL variable definition.
    * @return   Java class name of variable instance.
    * @throws   BaseException If something unexpected happens.
    */
   String getDefVariableJavaClassName (String mgrName,String variableId) throws BaseException;

   /**
    * Returns string representing the class name of the instance of variable that can
    * be determined by the given parameters. If there is no such variable,
    * BaseException will be thrown.
    *
    * @param    t SharkTransaction.
    * @param    mgrName  process manager name.
    * @param    variableId Id of XPDL variable definition.
    * @return   Java class name of variable instance.
    * @throws   BaseException If something unexpected happens.
    */
   String getDefVariableJavaClassName (SharkTransaction t,String mgrName,String variableId) throws BaseException;

   /**
    * Returns string representing the class name of the instance of variable that can
    * be determined by the given parameters. If there is no such variable,
    * BaseException will be thrown.
    *
    * @param    procId   Id of process instance.
    * @param    variableId Id of XPDL variable definition.
    * @return   Java class name of variable instance.
    * @throws   BaseException If something unexpected happens.
    */
   String getVariableJavaClassName (String procId,String variableId) throws BaseException;

   /**
    * Returns string representing the class name of the instance of variable that can
    * be determined by the given parameters. If there is no such variable,
    * BaseException will be thrown.
    *
    * @param    t SharkTransaction.
    * @param    procId   Id of process instance.
    * @param    variableId Id of XPDL variable definition.
    * @return   Java class name of variable instance.
    * @throws   BaseException If something unexpected happens.
    */
   String getVariableJavaClassName (SharkTransaction t,String procId,String variableId) throws BaseException;

   /**
    * Returns string representing the XPDL name of Participant that can
    * be determined by the given parameters. If there is no such Participant,
    * BaseException will be thrown.
    *
    * @param    pkgId       Id of participant's package.
    * @param    pDefId      Id of participant's process (null if participant is from process level).
    * @param    participantId Id of participant.
    * @return   XPDL name of participant.
    * @throws   BaseException If something unexpected happens.
    */
   String getParticipantName (String pkgId,String pDefId,String participantId) throws BaseException;

   /**
    * Returns string representing the XPDL name of Participant that can
    * be determined by the given parameters. If there is no such Participant,
    * BaseException will be thrown.
    *
    * @param    pkgId       Id of participant's package.
    * @param    pDefId      Id of participant's process (null if participant is from process level).
    * @param    participantId Id of participant.
    * @return   XPDL name of participant.
    * @throws   BaseException If something unexpected happens.
    */
   String getParticipantName (SharkTransaction t,String pkgId,String pDefId,String participantId) throws BaseException;

   /**
    * Returns string representing the XPDL name of Application that can
    * be determined by the given parameters. If there is no such Application,
    * BaseException will be thrown.
    *
    * @param    pkgId       Id of application's package.
    * @param    pDefId      Id of application's process (null if application is from process level).
    * @param    applicationId Id of application.
    * @return   XPDL name of application.
    * @throws   BaseException If something unexpected happens.
    */
   String getApplicationName (String pkgId,String pDefId,String applicationId) throws BaseException;

   /**
    * Returns string representing the XPDL name of Application that can
    * be determined by the given parameters. If there is no such Application,
    * BaseException will be thrown.
    *
    * @param    pkgId       Id of application's package.
    * @param    pDefId      Id of application's process (null if application is from process level).
    * @param    applicationId Id of application.
    * @return   XPDL name of application.
    * @throws   BaseException If something unexpected happens.
    */
   String getApplicationName (SharkTransaction t,String pkgId,String pDefId,String applicationId) throws BaseException;

   /**
    * Returns string representing the XPDL package definition Id of package
    * that contains XPDL process definition that corresponds to the WfProcessMgr
    * with the given name.
    *
    * @param    name   The name field of WfProcessMgr object.
    * @return   XPDL package definition Id.
    * @throws   BaseException If something unexpected happens.
    */
   String getProcessMgrPkgId (String name) throws BaseException;

   /**
    * Returns string representing the XPDL package definition Id of package
    * that contains XPDL process definition that corresponds to the WfProcessMgr
    * with the given name.
    *
    * @param    t   SharkTransaction.
    * @param    name   The name field of WfProcessMgr object.
    * @return   XPDL package definition Id.
    * @throws   BaseException If something unexpected happens.
    */
   String getProcessMgrPkgId (SharkTransaction t,String name) throws BaseException;

   /**
    * Returns string representing the XPDL process definition Id of XPDL process
    * definition that corresponds to the WfProcessMgr with the given name.
    *
    * @param    name   The name field of WfProcessMgr object.
    * @return   XPDL process definition Id.
    * @throws   BaseException If something unexpected happens.
    */
   String getProcessMgrProcDefId (String name) throws BaseException;

   /**
    * Returns string representing the XPDL process definition Id of XPDL process
    * definition that corresponds to the WfProcessMgr with the given name.
    *
    * @param    t  SharkTransaction.
    * @param    name   The name field of WfProcessMgr object.
    * @return   XPDL process definition Id.
    * @throws   BaseException If something unexpected happens.
    */
   String getProcessMgrProcDefId (SharkTransaction t,String name) throws BaseException;

   /**
    * Returns string representing the XPDL process definition name of XPDL process
    * definition that corresponds to the WfProcessMgr with the given name.
    *
    * @param    name   The name field of WfProcessMgr object.
    * @return   XPDL process definition name.
    * @throws   BaseException If something unexpected happens.
    */
   String getProcessMgrProcDefName (String name) throws BaseException;

   /**
    * Returns string representing the XPDL process definition name of XPDL process
    * definition that corresponds to the WfProcessMgr with the given name.
    *
    * @param    t  SharkTransaction.
    * @param    name   The name field of WfProcessMgr object.
    * @return   XPDL process definition name.
    * @throws   BaseException If something unexpected happens.
    */
   String getProcessMgrProcDefName (SharkTransaction t,String name) throws BaseException;

   /**
    * Returns the name of the resource that requested (created) the proces.
    * If the process was requested by subflow activity, it returns the
    * name of the resource that created the process that this activity is
    * belonging to, and if this process is also created by another activity,
    * it searches for the one created by resource.
    *
    * @param    procId              The id of the process instance.
    * @return   String representing resource name which is unique in the system.
    * @throws   BaseException If something unexpected happens.
    */
   String getProcessRequesterUsername (String procId) throws BaseException;

   /**
    * Returns the name of the resource that requested (created) the proces.
    * If the process was requested by subflow activity, it returns the
    * name of the resource that created the process that this activity is
    * belonging to, and if this process is also created by another activity,
    * it searches for the one created by resource.
    *
    * @param    t   SharkTransaction.
    * @param    procId              The id of the process instance.
    * @return   String representing resource name which is unique in the system.
    * @throws   BaseException If something unexpected happens.
    */
   String getProcessRequesterUsername (SharkTransaction t,String procId) throws BaseException;

   /**
    * Returns the name of the resource that accepted/completed activity.
    * If there is no such (i.e. activity is still not accepted by any resource,
    * or this is an "automatic" activity executed by shark itself), it
    * returns null.
    *
    * @param    procId activity's process Id.
    * @param    actId   Id of activity instance that we search block
    * activity instance Id for.
    * @return   String representing resource name which is unique in the system.
    * @throws   BaseException If something unexpected happens.
    */
   String getActivityResourceUsername (String procId,String actId) throws BaseException;

   /**
    * Returns the name of the resource that accepted/completed activity.
    * If there is no such (i.e. activity is still not accepted by any resource,
    * or this is an "automatic" activity executed by shark itself), it
    * returns null.
    *
    * @param    t SharkTransaction.
    * @param    procId activity's process Id.
    * @param    actId   Id of activity instance that we search block
    * activity instance Id for.
    * @return   String representing resource name which is unique in the system.
    * @throws   BaseException If something unexpected happens.
    */
   String getActivityResourceUsername (SharkTransaction t,String procId,String actId) throws BaseException;

   /**
    * Returns string representing the activity Id of activity instance that
    * relates to the assignment determined by given parameters.
    *
    * @param    procId assignment's process Id.
    * @param    assId   Id of assignment instance.
    * @return   activity instance Id for the given assignment.
    * @throws   BaseException If something unexpected happens.
    */
   String getAssignmentActivityId (String procId,String assId) throws BaseException;

   /**
    /**
    * Returns string representing the activity Id of activity instance that
    * relates to assignment determined by given parameters.
    *
    * @param    t   SharkTransaction.
    * @param    procId assignment's process Id.
    * @param    assId   Id of assignment instance.
    * @return   activity instance Id for the given assignment.
    * @throws   BaseException If something unexpected happens.
    */
   String getAssignmentActivityId (SharkTransaction t,String procId,String assId) throws BaseException;

   /**
    * Returns string representing username of resource instance that
    * relates to assignment determined by given parameters.
    *
    * @param    procId assignment's process Id.
    * @param    assId   Id of assignment instance.
    * @return   resource username for the given assignment.
    * @throws   BaseException If something unexpected happens.
    */
   String getAssignmentResourceUsername (String procId,String assId) throws BaseException;

   /**
    /**
    * Returns string representing username of resource instance that
    * relates to assignment determined by given parameters.
    *
    * @param    t   SharkTransaction.
    * @param    procId assignment's process Id.
    * @param    assId   Id of assignment instance.
    * @return   resource username for the given assignment.
    * @throws   BaseException If something unexpected happens.
    */
   String getAssignmentResourceUsername (SharkTransaction t,String procId,String assId) throws BaseException;

   /**
    * Returns the time when process is created in number of milliseconds that
    * have passed since January 1, 1970 00:00:00.000 GMT.
    *
    * @param    procId Id of process instance
    * @throws   BaseException If process does not exist, it is locked too long or
    * if something unexpected happens.
    */
   long getProcessCreatedTime (String procId) throws BaseException;

   /**
    * Returns the time when process is created in number of milliseconds that
    * have passed since January 1, 1970 00:00:00.000 GMT.
    *
    * @param    t   SharkTransaction.
    * @param    procId Id of process instance
    * @throws   BaseException If process does not exist, it is locked too long or
    * if something unexpected happens.
    */
   long getProcessCreatedTime (SharkTransaction t,String procId) throws BaseException;

   /**
    * Returns the time when process is started in number of milliseconds that
    * have passed since January 1, 1970 00:00:00.000 GMT. If process is not
    * started returns Long.MAX_VALUE/2.
    *
    * @param    procId Id of process instance
    * @throws   BaseException If process does not exist, it is locked too long or
    * if something unexpected happens.
    */
   long getProcessStartedTime (String procId) throws BaseException;

   /**
    * Returns the time when process is started in number of milliseconds that
    * have passed since January 1, 1970 00:00:00.000 GMT. If process is not
    * started returns Long.MAX_VALUE/2.
    *
    * @param    t   SharkTransaction.
    * @param    procId Id of process instance
    * @throws   BaseException If process does not exist, it is locked too long or
    * if something unexpected happens.
    */
   long getProcessStartedTime (SharkTransaction t,String procId) throws BaseException;

   /**
    * Returns the time when process is finished in number of milliseconds that
    * have passed since January 1, 1970 00:00:00.000 GMT. If process is not
    * finished returns Long.MAX_VALUE/2.
    *
    * @param    procId Id of process instance
    * @throws   BaseException If process does not exist, it is locked too long or
    * if something unexpected happens.
    */
   long getProcessFinishTime (String procId) throws BaseException;

   /**
    * Returns the time when process is finished in number of milliseconds that
    * have passed since January 1, 1970 00:00:00.000 GMT. If process is not
    * finished returns Long.MAX_VALUE/2.
    *
    * @param    t   SharkTransaction.
    * @param    procId Id of process instance
    * @throws   BaseException If process does not exist, it is locked too long or
    * if something unexpected happens.
    */
   long getProcessFinishTime (SharkTransaction t,String procId) throws BaseException;

   /**
    * Returns the time when activity is created in number of milliseconds that
    * have passed since January 1, 1970 00:00:00.000 GMT.
    *
    * @param    procId Id of process instance
    * @throws   BaseException If activity does not exist, its process is locked
    * too long or if something unexpected happens.
    */
   long getActivityCreatedTime (String procId,String actId) throws BaseException;

   /**
    * Returns the time when activity is created in number of milliseconds that
    * have passed since January 1, 1970 00:00:00.000 GMT.
    *
    * @param    t   SharkTransaction.
    * @param    procId Id of process instance
    * @throws   BaseException If activity does not exist, its process is locked
    * too long or if something unexpected happens.
    */
   long getActivityCreatedTime (SharkTransaction t,String procId,String actId) throws BaseException;

   /**
    * Returns the time when activity is last time accepted in number of milliseconds that
    * have passed since January 1, 1970 00:00:00.000 GMT. If activity is not
    * accepted (it is not in open.running state) returns Long.MAX_VALUE/2.
    *
    * @param    procId Id of process instance
    * @throws   BaseException If activity does not exist, its process is locked
    * too long or if something unexpected happens.
    */
   long getActivityStartedTime (String procId,String actId) throws BaseException;

   /**
    * Returns the time when activity is last time accepted in number of milliseconds that
    * have passed since January 1, 1970 00:00:00.000 GMT. If activity is not
    * accepted (it is not in open.running state) returns Long.MAX_VALUE/2.
    *
    * @param    t   SharkTransaction.
    * @param    procId Id of process instance
    * @throws   BaseException If activity does not exist, its process is locked
    * too long or if something unexpected happens.
    */
   long getActivityStartedTime (SharkTransaction t,String procId,String actId) throws BaseException;

   /**
    * Returns the time when activity is finished in number of milliseconds that
    * have passed since January 1, 1970 00:00:00.000 GMT. If activity is not
    * finished returns Long.MAX_VALUE/2.
    *
    * @param    procId Id of process instance
    * @throws   BaseException If activity does not exist, its process is locked
    * too long or if something unexpected happens.
    */
   long getActivityFinishTime (String procId,String actId) throws BaseException;

   /**
    * Returns the time when activity is finished in number of milliseconds that
    * have passed since January 1, 1970 00:00:00.000 GMT. If activity is not
    * finished returns Long.MAX_VALUE/2.
    *
    * @param    t   SharkTransaction.
    * @param    procId Id of process instance
    * @throws   BaseException If activity does not exist, its process is locked
    * too long or if something unexpected happens.
    */
   long getActivityFinishTime (SharkTransaction t,String procId,String actId) throws BaseException;

}
