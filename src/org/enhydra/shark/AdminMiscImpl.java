package org.enhydra.shark;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfservice.AdminMisc;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.working.*;

/**
 * The client interface through which client accesses the engine objects, and
 * performs the various actions on engine.
 * @author Sasa Bojanic
 * @version 1.0
 */
public class AdminMiscImpl implements AdminMisc {

   private CallbackUtilities cus;
   private String userId="Unknown";

   protected AdminMiscImpl () {
      this.cus=SharkEngineManager.getInstance().getCallbackUtilities();
   }

   public void connect (String userId) {
      this.userId=userId;
   }

   public String[][] getPackageExtendedAttributeNameValuePairs (String pkgId) throws BaseException {
      String[][] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getPackageExtendedAttributeNameValuePairs(t,pkgId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String[][] getPackageExtendedAttributeNameValuePairs (SharkTransaction t,String pkgId) throws BaseException {
      try {
         return SharkUtilities.getPackageExtendedAttributeNameValuePairs(t,pkgId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String[] getPackageExtendedAttributeNames (String pkgId) throws BaseException {
      String[] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getPackageExtendedAttributeNames(t,pkgId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String[] getPackageExtendedAttributeNames (SharkTransaction t,String pkgId) throws BaseException {
      try {
         return SharkUtilities.getPackageExtendedAttributeNames(t,pkgId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getPackageExtendedAttributeValue (String pkgId,String extAttrName) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getPackageExtendedAttributeValue(t,pkgId,extAttrName);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getPackageExtendedAttributeValue (SharkTransaction t,String pkgId,String extAttrName) throws BaseException{
      try {
         return SharkUtilities.getProcessExtendedAttributeValue(t,pkgId,extAttrName);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String[][] getProcessDefinitionExtendedAttributeNameValuePairs (String mgrName) throws BaseException {
      String[][] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessDefinitionExtendedAttributeNameValuePairs(t,mgrName);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String[][] getProcessDefinitionExtendedAttributeNameValuePairs (SharkTransaction t,String mgrName) throws BaseException {
      try {
         return SharkUtilities.getProcessDefinitionExtendedAttributeNameValuePairs(t,mgrName);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String[] getProcessDefinitionExtendedAttributeNames (String mgrName) throws BaseException {
      String[] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessDefinitionExtendedAttributeNames(t,mgrName);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String[] getProcessDefinitionExtendedAttributeNames (SharkTransaction t,String mgrName) throws BaseException {
      try {
         return SharkUtilities.getProcessDefinitionExtendedAttributeNames(t,mgrName);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getProcessDefinitionExtendedAttributeValue (String mgrName,String extAttrName) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessDefinitionExtendedAttributeValue(t,mgrName,extAttrName);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getProcessDefinitionExtendedAttributeValue (SharkTransaction t,String mgrName,String extAttrName) throws BaseException {
      try {
         return SharkUtilities.getProcessDefinitionExtendedAttributeValue(t,mgrName,extAttrName);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String[][] getProcessExtendedAttributeNameValuePairs (String procId) throws BaseException {
      String[][] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessExtendedAttributeNameValuePairs(t,procId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String[][] getProcessExtendedAttributeNameValuePairs (SharkTransaction t,String procId) throws BaseException {
      try {
         return SharkUtilities.getProcessExtendedAttributeNameValuePairs(t,procId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String[] getProcessExtendedAttributeNames (String procId) throws BaseException {
      String[] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessExtendedAttributeNames(t,procId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String[] getProcessExtendedAttributeNames (SharkTransaction t,String procId) throws BaseException {
      try {
         return SharkUtilities.getProcessExtendedAttributeNames(t,procId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getProcessExtendedAttributeValue (String procId,String extAttrName) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessExtendedAttributeValue(t,procId,extAttrName);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getProcessExtendedAttributeValue (SharkTransaction t,String procId,String extAttrName) throws BaseException{
      try {
         return SharkUtilities.getProcessExtendedAttributeValue(t,procId,extAttrName);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getActivitiesExtendedAttributes (String procId,String actId) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getActivitiesExtendedAttributes(t,procId,actId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getActivitiesExtendedAttributes (SharkTransaction t,String procId,String actId) throws BaseException{
      try {
         return SharkUtilities.getActivitiesExtendedAttributes(t,procId,actId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String[][] getActivitiesExtendedAttributeNameValuePairs (String procId,String actId) throws BaseException {
      String[][] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getActivitiesExtendedAttributeNameValuePairs(t,procId,actId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String[][] getActivitiesExtendedAttributeNameValuePairs (SharkTransaction t,String procId,String actId) throws BaseException {
      try {
         return SharkUtilities.getActivitiesExtendedAttributeNameValuePairs(t,procId,actId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String[] getActivitiesExtendedAttributeNames (String procId,String actId) throws BaseException {
      String[] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getActivitiesExtendedAttributeNames(t,procId,actId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String[] getActivitiesExtendedAttributeNames (SharkTransaction t,String procId,String actId) throws BaseException {
      try {
         return SharkUtilities.getActivitiesExtendedAttributeNames(t,procId,actId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getActivitiesExtendedAttributeValue (String procId,String actId,String extAttrName) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getActivitiesExtendedAttributeValue(t,procId,actId,extAttrName);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getActivitiesExtendedAttributeValue (SharkTransaction t,String procId,String actId,String extAttrName) throws BaseException{
      try {
         return SharkUtilities.getActivitiesExtendedAttributeValue(t,procId,actId,extAttrName);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }


   public String[][] getDefVariableExtendedAttributeNameValuePairs (String mgrName,String variableId) throws BaseException {
      String[][] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getDefVariableExtendedAttributeNameValuePairs(t,mgrName,variableId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String[][] getDefVariableExtendedAttributeNameValuePairs (SharkTransaction t,String mgrName,String variableId) throws BaseException {
      try {
         return SharkUtilities.getDefVariableExtendedAttributeNameValuePairs(t,mgrName,variableId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String[] getDefVariableExtendedAttributeNames (String mgrName,String variableId) throws BaseException {
      String[] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getDefVariableExtendedAttributeNames(t,mgrName,variableId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String[] getDefVariableExtendedAttributeNames (SharkTransaction t,String mgrName,String variableId) throws BaseException {
      try {
         return SharkUtilities.getDefVariableExtendedAttributeNames(t,mgrName,variableId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getDefVariableExtendedAttributeValue (String mgrName,String variableId,String extAttrName) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getDefVariableExtendedAttributeValue(t,mgrName,variableId,extAttrName);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getDefVariableExtendedAttributeValue (SharkTransaction t,String mgrName,String variableId,String extAttrName) throws BaseException{
      try {
         return SharkUtilities.getDefVariableExtendedAttributeValue(t,mgrName,variableId,extAttrName);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String[][] getVariableExtendedAttributeNameValuePairs (String procId,String variableId) throws BaseException {
      String[][] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getVariableExtendedAttributeNameValuePairs(t,procId,variableId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String[][] getVariableExtendedAttributeNameValuePairs (SharkTransaction t,String procId,String variableId) throws BaseException {
      try {
         return SharkUtilities.getVariableExtendedAttributeNameValuePairs(t,procId,variableId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String[] getVariableExtendedAttributeNames (String procId,String variableId) throws BaseException {
      String[] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getVariableExtendedAttributeNames(t,procId,variableId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String[] getVariableExtendedAttributeNames (SharkTransaction t,String procId,String variableId) throws BaseException {
      try {
         return SharkUtilities.getVariableExtendedAttributeNames(t,procId,variableId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getVariableExtendedAttributeValue (String procId,String variableId,String extAttrName) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getVariableExtendedAttributeValue(t,procId,variableId,extAttrName);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getVariableExtendedAttributeValue (SharkTransaction t,String procId,String variableId,String extAttrName) throws BaseException{
      try {
         return SharkUtilities.getVariableExtendedAttributeValue(t,procId,variableId,extAttrName);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String[][] getParticipantExtendedAttributeNameValuePairs (String pkgId,String pDefId,String participantId) throws BaseException {
      String[][] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getParticipantExtendedAttributeNameValuePairs(t,pkgId,pDefId,participantId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String[][] getParticipantExtendedAttributeNameValuePairs (SharkTransaction t,String pkgId,String pDefId,String participantId) throws BaseException {
      try {
         return SharkUtilities.getParticipantExtendedAttributeNameValuePairs(t,pkgId,pDefId,participantId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String[] getParticipantExtendedAttributeNames (String pkgId,String pDefId,String participantId) throws BaseException {
      String[] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getParticipantExtendedAttributeNames(t,pkgId,pDefId,participantId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String[] getParticipantExtendedAttributeNames (SharkTransaction t,String pkgId,String pDefId,String participantId) throws BaseException {
      try {
         return SharkUtilities.getParticipantExtendedAttributeNames(t,pkgId,pDefId,participantId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getParticipantExtendedAttributeValue (String pkgId,String pDefId,String participantId,String extAttrName) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getParticipantExtendedAttributeValue(t,pkgId,pDefId,participantId,extAttrName);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getParticipantExtendedAttributeValue (SharkTransaction t,String pkgId,String pDefId,String participantId,String extAttrName) throws BaseException{
      try {
         return SharkUtilities.getParticipantExtendedAttributeValue(t,pkgId,pDefId,participantId,extAttrName);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String[][] getApplicationExtendedAttributeNameValuePairs (String pkgId,String pDefId,String applicationId) throws BaseException {
      String[][] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getApplicationExtendedAttributeNameValuePairs(t,pkgId,pDefId,applicationId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String[][] getApplicationExtendedAttributeNameValuePairs (SharkTransaction t,String pkgId,String pDefId,String applicationId) throws BaseException {
      try {
         return SharkUtilities.getApplicationExtendedAttributeNameValuePairs(t,pkgId,pDefId,applicationId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String[] getApplicationExtendedAttributeNames (String pkgId,String pDefId,String applicationId) throws BaseException {
      String[] ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getApplicationExtendedAttributeNames(t,pkgId,pDefId,applicationId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String[] getApplicationExtendedAttributeNames (SharkTransaction t,String pkgId,String pDefId,String applicationId) throws BaseException {
      try {
         return SharkUtilities.getApplicationExtendedAttributeNames(t,pkgId,pDefId,applicationId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getApplicationExtendedAttributeValue (String pkgId,String pDefId,String applicationId,String extAttrName) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getApplicationExtendedAttributeValue(t,pkgId,pDefId,applicationId,extAttrName);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getApplicationExtendedAttributeValue (SharkTransaction t,String pkgId,String pDefId,String applicationId,String extAttrName) throws BaseException{
      try {
         return SharkUtilities.getApplicationExtendedAttributeValue(t,pkgId,pDefId,applicationId,extAttrName);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getBlockActivityId (String procId,String actId) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getBlockActivityId(t,procId,actId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getBlockActivityId (SharkTransaction t,String procId,String actId) throws BaseException{
      try {
         return SharkUtilities.getActivity(t,procId,actId).block_activity_id(t);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getActivityDefinitionId (String procId,String actId) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getActivityDefinitionId(t,procId,actId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getActivityDefinitionId (SharkTransaction t,String procId,String actId) throws BaseException{
      try {
         return SharkUtilities.getActivity(t,procId,actId).activity_definition_id(t);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getProcessDefinitionId (String procId) throws BaseException{
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessDefinitionId(t,procId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getProcessDefinitionId (SharkTransaction t,String procId) throws BaseException{
      try {
         return SharkUtilities.getProcess(t,procId).process_definition_id(t);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getDefVariableName (String mgrName,String variableId) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getDefVariableName(t,mgrName,variableId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getDefVariableName (SharkTransaction t,String mgrName,String variableId) throws BaseException {
      try {
         return SharkUtilities.getDefVariableName(t,mgrName,variableId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getDefVariableDescription (String mgrName,String variableId) throws BaseException{
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getDefVariableDescription(t,mgrName,variableId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getDefVariableDescription (SharkTransaction t,String mgrName,String variableId) throws BaseException{
      try {
         return SharkUtilities.getDefVariableDescription(t,mgrName,variableId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getDefVariableJavaClassName (String mgrName,String variableId) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getDefVariableJavaClassName(t,mgrName,variableId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getDefVariableJavaClassName (SharkTransaction t,String mgrName,String variableId) throws BaseException {
      try {
         return SharkUtilities.getDefVariableJavaClassName(t,mgrName,variableId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getVariableName (String procId,String variableId) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getVariableName(t,procId,variableId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getVariableName (SharkTransaction t,String procId,String variableId) throws BaseException {
      try {
         return SharkUtilities.getVariableName(t,procId,variableId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getVariableDescription (String procId,String variableId) throws BaseException{
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getVariableDescription(t,procId,variableId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getVariableDescription (SharkTransaction t,String procId,String variableId) throws BaseException{
      try {
         return SharkUtilities.getVariableDescription(t,procId,variableId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getVariableJavaClassName (String procId,String variableId) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getVariableJavaClassName(t,procId,variableId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getVariableJavaClassName (SharkTransaction t,String procId,String variableId) throws BaseException {
      try {
         return SharkUtilities.getVariableJavaClassName(t,procId,variableId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getParticipantName (String pkgId,String pDefId,String participantId) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getParticipantName(t,pkgId,pDefId,participantId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getParticipantName (SharkTransaction t,String pkgId,String pDefId,String participantId) throws BaseException {
      try {
         return SharkUtilities.getParticipantName(t,pkgId,pDefId,participantId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getApplicationName (String pkgId,String pDefId,String participantId) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getApplicationName(t,pkgId,pDefId,participantId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getApplicationName (SharkTransaction t,String pkgId,String pDefId,String participantId) throws BaseException {
      try {
         return SharkUtilities.getApplicationName(t,pkgId,pDefId,participantId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getProcessMgrPkgId (String name) throws BaseException{
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessMgrPkgId(t,name);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getProcessMgrPkgId (SharkTransaction t,String name) throws BaseException{
      try {
         return SharkUtilities.getProcessMgr(t,name).package_id(t);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getProcessMgrProcDefId (String name) throws BaseException{
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessMgrProcDefId(t,name);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getProcessMgrProcDefId (SharkTransaction t,String name) throws BaseException{
      try {
         return SharkUtilities.getProcessMgr(t,name).process_definition_id(t);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getProcessMgrProcDefName (String name) throws BaseException{
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessMgrProcDefName(t,name);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getProcessMgrProcDefName (SharkTransaction t,String name) throws BaseException{
      try {
         return SharkUtilities.getProcessMgr(t,name).process_definition_name(t);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getProcessRequesterUsername (String procId) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessRequesterUsername(t,procId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getProcessRequesterUsername (SharkTransaction t,String procId) throws BaseException {
      try {
         return SharkUtilities.getProcess(t,procId).requester(t).getResourceRequesterUsername(t);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getActivityResourceUsername (String procId,String actId) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getActivityResourceUsername(t,procId,actId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getActivityResourceUsername (SharkTransaction t,String procId,String actId) throws BaseException {
      try {
         return SharkUtilities.getActivity(t,procId,actId).getResourceUsername(t);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getAssignmentActivityId (String procId,String assId) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getAssignmentActivityId(t,procId,assId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getAssignmentActivityId (SharkTransaction t,String procId,String assId) throws BaseException {
      try {
         return SharkUtilities.getAssignmentActivityId(assId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String getAssignmentResourceUsername (String procId,String assId) throws BaseException {
      String ret = null;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getAssignmentResourceUsername(t,procId,assId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public String getAssignmentResourceUsername (SharkTransaction t,String procId,String assId) throws BaseException {
      try {
         return SharkUtilities.getAssignmentUsername(assId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public long getProcessCreatedTime (String procId) throws BaseException {
      long ret = -1;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessCreatedTime(t,procId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public long getProcessCreatedTime (SharkTransaction t,String procId) throws BaseException {
      try {
         WfProcessInternal proc=SharkUtilities.getProcess(t,procId);
         return proc.getCreationTime(t);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public long getProcessStartedTime (String procId) throws BaseException {
      long ret = -1;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessStartedTime(t,procId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public long getProcessStartedTime (SharkTransaction t,String procId) throws BaseException {
      try {
         WfProcessInternal proc=SharkUtilities.getProcess(t,procId);
         return proc.getStartTime(t);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public long getProcessFinishTime (String procId) throws BaseException {
      long ret = -1;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getProcessFinishTime(t,procId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public long getProcessFinishTime (SharkTransaction t,String procId) throws BaseException {
      try {
         WfProcessInternal proc=SharkUtilities.getProcess(t,procId);
         if (proc.state(t).startsWith(SharkConstants.STATEPREFIX_CLOSED)) {
            return proc.last_state_time(t).time;
         } else {
            return Long.MAX_VALUE/2;
         }
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public long getActivityCreatedTime (String procId,String actId) throws BaseException {
      long ret = -1;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getActivityCreatedTime(t,procId,actId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public long getActivityCreatedTime (SharkTransaction t,String procId,String actId) throws BaseException {
      try {
         WfActivityInternal act=SharkUtilities.getActivity(t,procId,actId);
         return act.getCreationTime(t);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public long getActivityStartedTime (String procId,String actId) throws BaseException {
      long ret = -1;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getActivityStartedTime(t,procId,actId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public long getActivityStartedTime (SharkTransaction t,String procId,String actId) throws BaseException {
      try {
         WfActivityInternal act=SharkUtilities.getActivity(t,procId,actId);
         return act.getStartTime(t);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public long getActivityFinishTime (String procId,String actId) throws BaseException {
      long ret = -1;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = getActivityFinishTime(t,procId,actId);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public long getActivityFinishTime (SharkTransaction t,String procId,String actId) throws BaseException {
      try {
         WfActivityInternal act=SharkUtilities.getActivity(t,procId,actId);
         if (act.state(t).startsWith(SharkConstants.STATEPREFIX_CLOSED)) {
            return act.last_state_time(t).time;
         } else {
            return Long.MAX_VALUE/2;
         }
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

}
