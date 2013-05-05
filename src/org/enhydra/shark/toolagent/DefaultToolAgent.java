package org.enhydra.shark.toolagent;


import org.enhydra.shark.api.RootError;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.toolagent.AppParameter;
import org.enhydra.shark.api.internal.toolagent.ApplicationBusy;
import org.enhydra.shark.api.internal.toolagent.ApplicationNotDefined;
import org.enhydra.shark.api.internal.toolagent.ApplicationNotStarted;
import org.enhydra.shark.api.internal.toolagent.SessionHandle;
import org.enhydra.shark.api.internal.toolagent.ToolAgent;
import org.enhydra.shark.api.internal.toolagent.ToolAgentGeneralException;
import org.enhydra.shark.xpdl.elements.ExtendedAttribute;
import org.enhydra.shark.xpdl.elements.ExtendedAttributes;

/**
 * Tool agent that is called when there are no any mapped tool agents.
 * It reads the ext. attributes to find the tool agent to call.
 * When it calls another tool agent, it just passes it parameters and ...
 * @author Sasa Bojanic
 * @version 1.0
 */
public class DefaultToolAgent extends AbstractToolAgent {

   private final static String TOOL_AGENT_CLASS_EXT_ATTR_NAME="ToolAgentClass";

   private String taClass;
   private String callingAppName;
   private Integer callingAppMode;

   public void invokeApplication (SharkTransaction t,
                                  long handle,
                                  String applicationName,
                                  String procInstId,
                                  String assId,
                                  AppParameter[] parameters,
                                  Integer appMode)
      throws ApplicationNotStarted, ApplicationNotDefined,
      ApplicationBusy, ToolAgentGeneralException {

      super.invokeApplication(t,handle,applicationName,procInstId,assId,parameters,appMode);

      try {
         status=APP_STATUS_RUNNING;

         String extAttribs=(String)parameters[0].the_value;
         readParamsFromExtAttributes(extAttribs);

         //System.out.println("Calling "+taClass+" tool agent");

         Class cls=null;

         try {
            cls=Class.forName(taClass);
         } catch (ClassNotFoundException cnfe1) {
            cls = ToolAgentLoader.load(cus,taClass);
         }



         ToolAgent ta=(ToolAgent)cls.newInstance();
         ta.configure(cus);

         // try to connect to the tool agent
         SessionHandle taShandle;
         try {
            taShandle=ta.connect(t,username,password,engineName,scope);
         } catch (org.enhydra.shark.api.internal.toolagent.ConnectFailed cf) {
            cus.error("Default Tool Agent - connection to Tool agent "+taClass+" failed !");
            throw cf;
         }
         ta.invokeApplication(t,taShandle.getHandle(),callingAppName,procInstId,assId,parameters,callingAppMode);
         status=ta.requestAppStatus(t,taShandle.getHandle(),procInstId,assId,parameters);
         //System.out.println("DTA finished execution");
         ta.disconnect(t,taShandle);

      } catch (ClassNotFoundException cnf) {
         cus.error("DefaultToolAgent - application "+appName+" terminated incorrectly, can't find class: "+cnf);
         throw new ApplicationNotDefined("Can't find class "+taClass,cnf);
      } catch (NoClassDefFoundError ncdfe) {
         cus.error("DefaultToolAgent - application "+appName+" terminated incorrectly, can't find class definition: "+ncdfe);
         throw new ApplicationNotDefined("Class "+appName+" can't be executed",ncdfe);
      } catch (Throwable ex) {
         //ex.printStackTrace();
         cus.error("DefaultToolAgent - application "+appName+" terminated incorrectly: "+ex);
         status=APP_STATUS_INVALID;
         if (ex instanceof ToolAgentGeneralException) {
            throw (ToolAgentGeneralException)ex;
         } else if (ex instanceof ApplicationNotStarted) {
            throw (ApplicationNotStarted)ex;
         } else if (ex instanceof ApplicationNotDefined) {
            throw (ApplicationNotDefined)ex;
         } else if (ex instanceof ApplicationBusy) {
            throw (ApplicationBusy)ex;
         } else {
            throw new RootError("Unexpected error",ex);
         }
      }
   }

   public String getInfo (SharkTransaction t) throws ToolAgentGeneralException {
      String i="It is used to execute other tool agents based on XPDL extended attributes"+
         "\nof appropriate Application definition. It is called by kernel if there are"+
         "\nno mapping information for some XPDL Application definition";
      return i;
   }

   protected ExtendedAttributes readParamsFromExtAttributes (String extAttribs) throws Exception {
      String oldAppName=appName;
      Integer oldAppMode=appMode;
      ExtendedAttributes eas=super.readParamsFromExtAttributes(extAttribs);
      callingAppName=appName;
      callingAppMode=appMode;
      appName=oldAppName;
      appMode=oldAppMode;
      ExtendedAttribute ea=eas.getFirstExtendedAttributeForName(TOOL_AGENT_CLASS_EXT_ATTR_NAME);
      if (ea!=null) {
         taClass=ea.getVValue();
      }
      return eas;
   }

}
