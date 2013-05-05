package org.enhydra.shark;

import java.util.List;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfservice.LimitAdministration;
import org.enhydra.shark.api.internal.limitagent.LimitAgentManager;

/**
 * The implementation of client interface through which client can check limits.
 * @author Sasa Bojanic
 */
public class LimitAdmin implements LimitAdministration {

   private String userId="Unknown";

   protected LimitAdmin () {
   }

   public void connect (String userId) {
      this.userId=userId;
   }

   public void checkLimits () throws BaseException {
      SharkTransaction t = null;
      List pos;
      try {
         t = SharkUtilities.createTransaction();
         checkLimits(t);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void checkLimits (SharkTransaction t) throws BaseException {
      LimitAgentManager lam=SharkEngineManager.getInstance().getLimitAgentManager();
      if (lam==null) throw new BaseException("Shark is working without implementation of Limit API!");
      try {
         lam.checkLimits(t);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public void checkLimits (String[] procIds) throws BaseException {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         checkLimits(t,procIds);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void checkLimits (SharkTransaction t,String[] procIds) throws BaseException {
      LimitAgentManager lam=SharkEngineManager.getInstance().getLimitAgentManager();
      if (lam==null) throw new BaseException("Shark is working without implementation of Limit API!");
      if (procIds==null) throw new BaseException("Invalid null value for parameter procIds");
      try {
         for (int i=0; i<procIds.length; i++) {
            lam.checkLimits(t,procIds[i]);
         }
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public void checkLimits (String procId) throws BaseException {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         checkLimits(t,procId);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void checkLimits (SharkTransaction t,String procId) throws BaseException {
      LimitAgentManager lam=SharkEngineManager.getInstance().getLimitAgentManager();
      if (lam==null) throw new BaseException("Shark is working without implementation of Limit API!");
      if (procId==null) throw new BaseException("Invalid null value for parameter procId");
      try {
         lam.checkLimits(t,procId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public void checkProcessLimit (String procId) throws BaseException {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         checkProcessLimit(t,procId);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void checkProcessLimit (SharkTransaction t,String procId) throws BaseException {
      LimitAgentManager lam=SharkEngineManager.getInstance().getLimitAgentManager();
      if (lam==null) throw new BaseException("Shark is working without implementation of Limit API!");
      if (procId==null) throw new BaseException("Invalid null value for parameter procId");
      try {
         lam.checkProcessLimit(t,procId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public void checkActivityLimit (String procId,String actId) throws BaseException {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         checkActivityLimit(t,procId,actId);
         SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         SharkUtilities.rollbackTransaction(t,e);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void checkActivityLimit (SharkTransaction t,String procId,String actId) throws BaseException {
      LimitAgentManager lam=SharkEngineManager.getInstance().getLimitAgentManager();
      if (lam==null) throw new BaseException("Shark is working without implementation of Limit API!");
      if (procId==null) throw new BaseException("Invalid null value for parameter procId");
      if (actId==null) throw new BaseException("Invalid null value for parameter actId");
      try {
         lam.checkActivityLimit(t,procId,actId);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

}
