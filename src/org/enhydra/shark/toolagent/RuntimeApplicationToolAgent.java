package org.enhydra.shark.toolagent;


import java.io.IOException;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.toolagent.AppParameter;
import org.enhydra.shark.api.internal.toolagent.ApplicationBusy;
import org.enhydra.shark.api.internal.toolagent.ApplicationNotDefined;
import org.enhydra.shark.api.internal.toolagent.ApplicationNotStarted;
import org.enhydra.shark.api.internal.toolagent.ToolAgentGeneralException;
import org.enhydra.shark.xpdl.XPDLConstants;

/**
 * Tool agent that can execute some system application.
 * @author Sasa Bojanic
 * @version 1.0
 */
public class RuntimeApplicationToolAgent extends AbstractToolAgent {

   public static final long APP_MODE_SYNCHRONOUS=0;
   public static final long APP_MODE_ASYNCHRONOUS=1;


   private Process p;

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

         StringBuffer buffer = new StringBuffer();

         if (appName==null || appName.trim().length()==0) {
            readParamsFromExtAttributes((String)parameters[0].the_value);
         }

         buffer.append(appName);

         // ignore 1. param, this is ext. attribs.
         buffer.append(" ");
         if (parameters!=null) {
            for (int i=1; i<parameters.length; i++) {
               if(parameters[i].the_mode.equals(XPDLConstants.FORMAL_PARAMETER_MODE_IN) ||
                  parameters[i].the_mode.equals(XPDLConstants.FORMAL_PARAMETER_MODE_INOUT)) {
                  try {
                     buffer.append((String)parameters[i].the_value);
                     buffer.append(" ");
                  } catch (Throwable ex) {}
               }
            }
         }

         Runtime rt = Runtime.getRuntime();
         p = rt.exec(buffer.toString().substring(0,buffer.length()-1));
         if (appMode!=null && appMode.intValue()==APP_MODE_SYNCHRONOUS) {
            p.waitFor();
         }

         status=APP_STATUS_FINISHED;

      } catch (IOException ioe) {
         cus.error("RuntimeApplicationToolAgent - application "+appName+" terminated incorrectly, can't find executable: "+ioe);
         throw new ApplicationNotDefined("Can't find executable "+appName,ioe);
      } catch (Throwable ex) {
         cus.error("RuntimeApplicationToolAgent - application "+appName+" terminated incorrectly "+ex);
         status=APP_STATUS_INVALID;
         throw new ToolAgentGeneralException(ex);
      }

   }

   /*public void terminate () {
    try {
    p.destroy();
    if (appMode.equals(APP_MODE_SYNCHRONOUS)) {
    status=APP_STATUS_TERMINATED;
    }
    } catch (Throwable ex) {}
    }*/

   public String getInfo (SharkTransaction t) throws ToolAgentGeneralException {
      String i="Executes some system applications like notepad or any other executable application."+
         "\nIt is important that this application should be in the system path of machine where shark is running." +
         "\nf you use application mode 0 (zero), the tool agent will wait until the executable application "+
         "\nis completed, and if you choose application status other then 0 the tool agent will finish its work as "+
         "\nsoon as the executable application is started (this usually happens immediately), and shark "+
         "\nwill proceed to the next activity, even if the executable application is still running "+
         "\n(this is asynchronous starting of some external applications)"+
         "\nThis tool agent accepts parameters (AppParameter class instances), but does not modify any."+
         "\nThe parameters sent to this tool agents, for which the corresponding application definition "+
         "\nformal parameters are of \"IN\" type, and whose data type is string, are added as suffixes to "+
         "\nthe application name, and resulting application that is started could be something like "+
         "\n             \"notepad c:\\Shark\\readme\""+
         "\n"+
         "\nThis tool agent is able to understand the extended attributes with the following names:"+
         "\n     * AppName - value of this attribute should represent the executable application name to "+
         "\n                 be executed by tool agent (must be in a system path)"+
         "\n     * AppMode - value of this attribute should represent the mode of execution, if set to "+
         "\n                 0 (zero), tool agent will wait until the executable application is finished."+
         "\n"+
         "\n NOTE: Tool agent will read extended attributes only if they are called through"+
         "\n       Default tool agent (not by shark directly) and this is the case when information "+
         "\n       on which tool agent to start for XPDL application definition is not contained in mappings";
      return i;
   }


}
