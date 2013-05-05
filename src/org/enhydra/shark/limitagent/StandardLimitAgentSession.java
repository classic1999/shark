package org.enhydra.shark.limitagent;

import java.util.Map;

import org.enhydra.shark.Shark;
import org.enhydra.shark.api.internal.limitagent.LimitAgentSession;

/**
 *
 * @author     <a href="mailto:jaz@ofbiz.org">Andy Zeneski</a>
 * @since      Mar 24, 2004
 */
public class StandardLimitAgentSession implements LimitAgentSession {

   protected String[][] attrs;
   protected String processId;
   protected String activityId;
   protected Map context;
   protected long time;

   protected StandardLimitAgentSession(String procId, String actId, Map ctx, long limitTime) {
      this.processId = procId;
      this.activityId = actId;
      this.context = ctx;
      this.time = limitTime;
   }

   public String[][] getExtendedAttributesNameValuePairs() {
      if (attrs==null) {
         try {
            if (activityId!=null) {
               attrs=Shark.getInstance().getAdminInterface().getAdminMisc().getActivitiesExtendedAttributeNameValuePairs(processId, activityId);
            } else {
               attrs=Shark.getInstance().getAdminInterface().getAdminMisc().getProcessExtendedAttributeNameValuePairs(processId);
            }
         } catch (Exception ex) {
            System.err.println("Failed to retrieve extended attribute name value pairs!");
         }
      }      
      return this.attrs;
   }

   public String getProcessId () {
      return this.processId;
   }

   public String getActivityId () {
      return this.activityId;
   }

   public Map getProcessContext() {
      return this.context;
   }

   public long getLimitTime() {
      return this.time;
   }
}

