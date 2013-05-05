package org.enhydra.shark;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.timebase.UtcT;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.AlreadySuspended;
import org.enhydra.shark.api.client.wfmodel.CannotResume;
import org.enhydra.shark.api.client.wfmodel.CannotStop;
import org.enhydra.shark.api.client.wfmodel.CannotSuspend;
import org.enhydra.shark.api.client.wfmodel.InvalidData;
import org.enhydra.shark.api.client.wfmodel.NotRunning;
import org.enhydra.shark.api.client.wfmodel.NotSuspended;
import org.enhydra.shark.api.client.wfmodel.UpdateNotAllowed;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.limitagent.LimitAgentManager;
import org.enhydra.shark.api.internal.working.WfExecutionObjectInternal;
import org.enhydra.shark.api.internal.working.WfStateEventAuditInternal;
import org.enhydra.shark.utilities.MiscUtilities;
import org.enhydra.shark.xpdl.XMLCollectionElement;
import org.enhydra.shark.xpdl.XMLUtil;
import org.enhydra.shark.xpdl.elements.Activity;
import org.enhydra.shark.xpdl.elements.ProcessHeader;
import org.enhydra.shark.xpdl.elements.WorkflowProcess;

/**
 * WfExecutionObjectImpl - Workflow Execution Object implementation
 * The WfProcessImpl and WfActivityImpl classes are extended from this class.
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public abstract class WfExecutionObjectImpl implements WfExecutionObjectInternal {

   protected String state=SharkConstants.STATE_OPEN_NOT_RUNNING_NOT_STARTED;
   protected String name;
   protected String key;
   protected String description;
   protected short priority;
   protected long lastStateTime;
   protected long limitTime=Long.MAX_VALUE/2;

   protected WfStateEventAuditInternal lastStateEventAudit;

   public String state (SharkTransaction t) throws BaseException {
      return state;
   }

   public String name (SharkTransaction t) throws BaseException {
      return name;
   }

   public void set_name (SharkTransaction t,String new_value) throws BaseException {
      try {
         name=new_value;
         if (name.length()>254) {
            name=name.substring(0,254);
         }
         persist(t);
      } catch (Exception tme) {
         throw new BaseException(tme);
      }
   }

   public String key (SharkTransaction t) throws BaseException {
      return key;
   }

   public String description (SharkTransaction t) throws BaseException {
      return description;
   }

   public void set_description (SharkTransaction t,String new_value) throws BaseException {
      try {
         description=new_value;
         if (description.length()>254) {
            description=description.substring(0,254);
         }
         persist(t);
      } catch (Exception tme) {
         throw new BaseException(tme);
      }
   }

   public Map process_context (SharkTransaction t) throws BaseException {
      Map m=getContext(t);
      Map ret=new HashMap();
      Iterator it=m.entrySet().iterator();
      while (it.hasNext()) {
         Map.Entry me=(Map.Entry)it.next();
         Object id=me.getKey();
         Object val=me.getValue();
         try {
            ret.put(id,MiscUtilities.cloneWRD(val));
         } catch (Throwable thr) {
            throw new BaseException(thr);
         }
      }
      return ret;
   }

   public abstract void set_process_context (SharkTransaction t,Map new_value) throws BaseException, InvalidData, UpdateNotAllowed;

   public short priority (SharkTransaction t) throws BaseException {
      return priority;
   }

   public void set_priority (SharkTransaction t,short new_value) throws BaseException {
      if (new_value<1 || new_value>5) throw new BaseException("Priority is out of range!");
      if (state(t).startsWith(SharkConstants.STATEPREFIX_CLOSED)) throw new BaseException("Priority cannot be updated.!");
      priority=new_value;
      try {
         persist(t);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public void activateLimitAgent(SharkTransaction trans) throws BaseException {
      LimitAgentManager mgr = SharkEngineManager.getInstance().getLimitAgentManager();

      // check the current state
      if (mgr==null || this.state(trans).startsWith(SharkConstants.STATEPREFIX_CLOSED)) {
         // we only set this if we are running
         return;
      }

      String durationStr;
      String limitStr;
      long startTime=-1;

      // get the duration unit string
      XMLCollectionElement xpdlObj=getXPDLObject(trans);
      WorkflowProcess wp=XMLUtil.getWorkflowProcess(xpdlObj);
      ProcessHeader ph = wp.getProcessHeader();
      durationStr = ph.getDurationUnit();

      if (xpdlObj instanceof Activity) {
         limitStr = ((Activity)xpdlObj).getLimit();
         startTime=getCreationTime(trans);
      } else {
         limitStr = ph.getLimit();
         startTime=getStartTime(trans);
      }

      // duration unit
      int durationUnit = 0;

      // convert to calendar value
      if (durationStr == null || durationStr.trim().length() == 0) {
         SharkEngineManager.getInstance().getCallbackUtilities().debug("DurationUnit is not set, not setting limit agent");
         return;
      } else {
         // get the Calendar value
         if ("Y".equals(durationStr)) {
            durationUnit = Calendar.YEAR;
         } else if ("M".equals(durationStr)) {
            durationUnit = Calendar.MONTH;
         } else if ("D".equals(durationStr)) {
            durationUnit = Calendar.DAY_OF_MONTH;
         } else if ("h".equals(durationStr)) {
            durationUnit = Calendar.HOUR;
         } else if ("m".equals(durationStr)) {
            durationUnit = Calendar.MINUTE;
         } else if ("s".equals(durationStr)) {
            durationUnit = Calendar.SECOND;
         }
      }

      // limit value - convert to integer
      int limit = 0;
      if (limitStr == null || limitStr.trim().length() == 0) {
         SharkEngineManager.getInstance().getCallbackUtilities().debug("Limit value is not set, not setting limit agent");
         return;
      } else {
         try {
            limit = Integer.parseInt(limitStr);
         } catch (NumberFormatException e) {
            SharkEngineManager.getInstance().getCallbackUtilities().error("Defined Limit is not a valid integer, not setting limit : " + limitStr);
            return;
         }
      }

      // determine the limit runtime
      long runtime = 0;
      if (durationUnit > 0 && limit > 0) {
         Calendar cal = Calendar.getInstance();
         // MUST support JDK 1.3.x, so we cannot use
         // cal.setTimeInMillis & cal.getTimeInMillis
         cal.setTime(new Date(startTime));
         cal.add(durationUnit, limit);
         runtime = cal.getTime().getTime();
      }

      // get the process context for this execution object
      Map context = this.process_context(trans);

      // register with the limit agent manager
      if (runtime > 0) {
         notifyStart(trans,context,runtime);
         limitTime=runtime;
      }
   }

   public abstract long getCreationTime (SharkTransaction trans) throws BaseException;

   public abstract long getStartTime (SharkTransaction trans) throws BaseException;

   public UtcT last_state_time(SharkTransaction t) throws BaseException {
      return new UtcT(lastStateTime, 0, (short)0, (short)0);
   }

   public abstract void resume(SharkTransaction t) throws BaseException, CannotResume, NotSuspended;

   public abstract void suspend(SharkTransaction t) throws BaseException, CannotSuspend, NotRunning, AlreadySuspended;

   public abstract void terminate(SharkTransaction t) throws BaseException, CannotStop, NotRunning;

   public abstract void abort(SharkTransaction t) throws BaseException, CannotStop, NotRunning;

   protected abstract XMLCollectionElement getXPDLObject(SharkTransaction t) throws BaseException;

   protected abstract void notifyStart (SharkTransaction t,Map context,long runtime) throws BaseException;
}


