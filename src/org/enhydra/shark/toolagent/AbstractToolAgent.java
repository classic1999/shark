package org.enhydra.shark.toolagent;

import java.util.ArrayList;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.toolagent.AppParameter;
import org.enhydra.shark.api.internal.toolagent.ApplicationBusy;
import org.enhydra.shark.api.internal.toolagent.ApplicationNotDefined;
import org.enhydra.shark.api.internal.toolagent.ApplicationNotStarted;
import org.enhydra.shark.api.internal.toolagent.ApplicationNotStopped;
import org.enhydra.shark.api.internal.toolagent.ConnectFailed;
import org.enhydra.shark.api.internal.toolagent.InvalidProcessInstance;
import org.enhydra.shark.api.internal.toolagent.InvalidSessionHandle;
import org.enhydra.shark.api.internal.toolagent.InvalidToolAgentHandle;
import org.enhydra.shark.api.internal.toolagent.InvalidWorkitem;
import org.enhydra.shark.api.internal.toolagent.SessionHandle;
import org.enhydra.shark.api.internal.toolagent.ToolAgent;
import org.enhydra.shark.api.internal.toolagent.ToolAgentGeneralException;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.utilities.SizeLimitedToolAgentMap;
import org.enhydra.shark.xpdl.XMLUtil;
import org.enhydra.shark.xpdl.XPDLConstants;
import org.enhydra.shark.xpdl.elements.ExtendedAttribute;
import org.enhydra.shark.xpdl.elements.ExtendedAttributes;

/**
 * Base class for all tool agents we implement.
 * If one wants to extend this class and create tool agent, he should override
 * invokeApplication method.
 *
 * @author Sasa Bojanic
 */
public abstract class AbstractToolAgent implements ToolAgent {

   public static final long APP_STATUS_INVALID=-1;
   public static final long APP_STATUS_RUNNING=0;
   public static final long APP_STATUS_ACTIVE=1;
   public static final long APP_STATUS_WAITING=2;
   public static final long APP_STATUS_TERMINATED=3;
   public static final long APP_STATUS_FINISHED=4;

   public static final String APP_NAME_EXT_ATTR_NAME="AppName";
   public static final String APP_MODE_EXT_ATTR_NAME="AppMode";

   protected String username;
   protected String password;
   protected String engineName;
   protected String scope;
   protected SessionHandle shandle;

   protected long handle;
   protected String appName;
   protected String procInstId;
   protected String assId;
   protected AppParameter[] parameters;
   protected Integer appMode;
   protected long status=AbstractToolAgent.APP_STATUS_ACTIVE;

   protected CallbackUtilities cus;

   protected static SizeLimitedToolAgentMap extAttributes=null;

   public void configure (CallbackUtilities cus) throws RootException {
      this.cus=cus;

      if (AbstractToolAgent.extAttributes==null) {
         int eacs=-1;
         try {
            eacs=Integer.parseInt(cus.getProperty("AbstractToolAgent.extAttribsCacheSize","-1"));
         } catch (Exception ex) {}
         AbstractToolAgent.extAttributes=new SizeLimitedToolAgentMap(eacs);
      }
   }


   public SessionHandle connect (SharkTransaction t,
                                 String userId,
                                 String password,
                                 String engineName,
                                 String scope)
      throws ConnectFailed, ToolAgentGeneralException {

      this.username=userId;
      this.password=password;
      this.engineName=engineName;
      this.scope=scope;
      shandle=new SessionHandleImpl(0,"");
      return shandle;
   }

   public void disconnect (SharkTransaction t,SessionHandle shandle)
      throws InvalidSessionHandle, ToolAgentGeneralException {

      if (!this.shandle.equals(shandle)) throw new InvalidSessionHandle();
   }

   public void invokeApplication (SharkTransaction t,
                                  long handle,
                                  String applicationName,
                                  String procInstId,
                                  String assId,
                                  AppParameter[] parameters,
                                  Integer appMode)
      throws ApplicationNotStarted, ApplicationNotDefined,
      ApplicationBusy, ToolAgentGeneralException {

      this.handle=handle;
      this.appName=applicationName;
      this.procInstId=procInstId;
      this.assId=assId;
      this.parameters=parameters;
      this.appMode=appMode;
      this.status=AbstractToolAgent.APP_STATUS_ACTIVE;
            
   }

   public long requestAppStatus (SharkTransaction t,
                                 long handle,
                                 String procInstId,
                                 String assId,
                                 AppParameter[] parameters)
      throws ApplicationBusy, InvalidToolAgentHandle,
      InvalidWorkitem, InvalidProcessInstance, ToolAgentGeneralException {

      if (this.handle==handle) {
         //System.out.println("Status for class "+getClass().getName()+" = "+status);
         if (!(status==APP_STATUS_FINISHED || status==APP_STATUS_TERMINATED ||
                  status==APP_STATUS_INVALID)) {
            //System.out.println("Throwing AB exc");
            throw new ApplicationBusy();
         }
         if (status!=APP_STATUS_INVALID) {
            copyParams(getReturnParameters(),parameters);
            //ai.getReturnParameters(),parameters);
         }
         return status;
      } else {
         throw new InvalidToolAgentHandle("TA "+getClass().getName()+" - Can't find app for [handle="+handle+",pId="+procInstId+",assId="+assId+"]");
      }
   }

   public void terminateApp (SharkTransaction t,
                             long handle,
                             String procInstId,
                             String assId)
      throws ApplicationNotStopped, InvalidWorkitem,
      InvalidProcessInstance, ApplicationBusy, ToolAgentGeneralException {
      cus.info("Terminating tool agent applications not implemented !!!");
      throw new ApplicationNotStopped();
   }

   public String getInfo (SharkTransaction t) throws ToolAgentGeneralException {
      return "";
   }

   protected ExtendedAttributes readParamsFromExtAttributes (String extAttribs) throws Exception {
      ExtendedAttributes eas=(ExtendedAttributes)AbstractToolAgent.extAttributes.get(extAttribs);
      if (eas==null) {
         if (extAttribs!=null && !extAttribs.trim().equals("")) {
            try {
               eas=XMLUtil.destringyfyExtendedAttributes(extAttribs);
               eas.setReadOnly(true);
               eas.initCaches();
            } catch (Throwable ex) {
               System.out.println("FAiled to destr");
               cus.warn("AbstractToolAgent -> "+ex.getMessage());
            }
         }

         //if (AbstractToolAgent.extAttributes.getMaximumSize()>0) {
            AbstractToolAgent.extAttributes.put(extAttribs,eas);
         //}
      }
      ExtendedAttribute ea=eas.getFirstExtendedAttributeForName(AbstractToolAgent.APP_NAME_EXT_ATTR_NAME);
      if (ea!=null) {
         appName=ea.getVValue();
      }
      ea=eas.getFirstExtendedAttributeForName(AbstractToolAgent.APP_MODE_EXT_ATTR_NAME);
      if (ea!=null) {
         try {
            appMode=new Integer(Integer.parseInt(ea.getVValue()));
         } catch (Exception ex){}
      }
      return eas;
   }

   protected AppParameter[] getReturnParameters () {
      // preparing result
      ArrayList returnValues = new ArrayList();
      if (parameters!=null) {
         for(int i = 0; i < parameters.length; i++){
            if(parameters[i].the_mode.equals(XPDLConstants.FORMAL_PARAMETER_MODE_OUT) ||
               parameters[i].the_mode.equals(XPDLConstants.FORMAL_PARAMETER_MODE_INOUT)) {
               returnValues.add(parameters[i]);
            }
         }
      }
      AppParameter[] retParameters=(AppParameter[])returnValues.toArray(new AppParameter[returnValues.size()]);

      return retParameters;
   }

   public static void copyParams (AppParameter[] taApps,AppParameter[] apps) {
      if (taApps!=null) {
         for (int i=0; i<taApps.length; i++) {
            AppParameter taApp=taApps[i];
            for (int j=0; j<apps.length; j++) {
               if (apps[j].the_actual_name.equals(taApp.the_actual_name)) {
                  apps[j].the_value=taApp.the_value;
               }
            }
         }
      }
   }

}
