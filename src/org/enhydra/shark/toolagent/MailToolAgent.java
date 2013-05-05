package org.enhydra.shark.toolagent;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.toolagent.AppParameter;
import org.enhydra.shark.api.internal.toolagent.ApplicationBusy;
import org.enhydra.shark.api.internal.toolagent.ApplicationNotDefined;
import org.enhydra.shark.api.internal.toolagent.ApplicationNotStarted;
import org.enhydra.shark.api.internal.toolagent.ToolAgentGeneralException;


/**
 * Tool agent that sends or receives e-mail.
 * @author Paloma Trigueros
 * @author Sasa Bojanic
 */
public class MailToolAgent extends AbstractToolAgent {

   public static final long APP_MODE_SEND=0;
   public static final long APP_MODE_RECEIVE=1;

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

         if (appName==null || appName.trim().length()==0) {
            readParamsFromExtAttributes((String)parameters[0].the_value);
         }

         if (appName==null || appName.trim().length()==0) {
            appName="org.enhydra.shark.toolagent.DefaultMailMessageHandler";
         }

         ClassLoader cl=getClass().getClassLoader();
         Class c = cl.loadClass(appName);
         MailMessageHandler mch=(MailMessageHandler)c.newInstance();

         // Get parameters - ignore 1. param, these are ext.attribs
         AppParameter[] aps=new AppParameter[parameters.length-1];
         System.arraycopy(parameters,1,aps,0,aps.length);

         mch.configure(this.cus,aps);

         long am=0;
         if (appMode!=null && appMode.intValue()==APP_MODE_RECEIVE) {
            am=1;
         }

         if (am==APP_MODE_SEND) {
            // Send the message
            System.out.println("Sending mail...");
            mch.sendMail();
            System.out.println("Mail sent.");
         }
            // Receive the message
         else if (am==APP_MODE_RECEIVE){
            System.out.println("Receiving mail...");
            String mailSubject = mch.receiveMail();
            System.out.println("Mail received:"+mailSubject);
         }

         status=APP_STATUS_FINISHED;

      } catch (ClassNotFoundException cnf) {
         cus.error("MailToolAgent - application "+appName+" terminated incorrectly, can't find class: "+cnf);
         status=APP_STATUS_INVALID;
         throw new ApplicationNotDefined("Can't find class "+appName,cnf);
      } catch (NoClassDefFoundError ncdfe) {
         cus.error("MailToolAgent - application "+appName+" terminated incorrectly, can't find class definition: "+ncdfe);
         throw new ApplicationNotDefined("Class "+appName+" can't be executed",ncdfe);
      } catch(Throwable ex) {
         cus.error("MailToolAgent - application "+appName+" terminated incorrectly: " + ex);
         status=APP_STATUS_INVALID;
         throw new ToolAgentGeneralException(ex);
      }
   }

   public String getInfo (SharkTransaction t) throws ToolAgentGeneralException {
      String i="Sends and receives mail messages." +
         "\nThere is a MailMessageHandler interface defined that is used to actually "+
         "\nhandle mails. We gave default implementation of this interface, but one can "+
         "\ncreate its own implementation. This interface is specifically defined for this "+
         "\ntool agent, and it is not part of Shark's APIs."+
         "\nWhen performing mappings, you should set application name to be the full class "+
         "\nname of the implementation class of MailMessageHandler interface."+
         "\n"+
         "\nTo be able to work with our DefaultMailMessageHandler, you must define some "+
         "\nproperties, and here is a section from shark's configuration file \"Shark.conf\" "+
         "\nthat defines these properties:"+
         "\n# the parameters for retrieving mails, possible values for protocol are \"pop3\" and \"imap\""+
         "\nDefaultMailMessageHandler.IncomingMailServer=someserver.co.yu"+
         "\nDefaultMailMessageHandler.IncomingMailProtocol=pop3"+
         "\nDefaultMailMessageHandler.StoreFolderName=INBOX"+
         "\nDefaultMailMessageHandler.IMAPPortNo=143"+
         "\nDefaultMailMessageHandler.POP3PortNo=110"+
         "\n"+
         "\n# the parameters for sending mails"+
         "\nDefaultMailMessageHandler.SMTPMailServer=someserver.co.yu"+
         "\nDefaultMailMessageHandler.SMTPPortNo=25"+
         "\nDefaultMailMessageHandler.SourceAddress=shark@objectweb.org"+
         "\n"+
         "\n# credentials"+
         "\nDefaultMailMessageHandler.Login=shark"+
         "\nDefaultMailMessageHandler.Password=sharkspwd"+
         "\n"+
         "\nThis tool agent is able to understand the extended attributes with the following names:"+
         "\n     * AppName - value of this attribute should represent the full class name of "+
         "\n                 MailMessageHandler interface implementation that will handle mails"+
         "\n     * AppMode - value of this attribute should represent the mode of execution, "+
         "\n                 if set to 0 (zero), tool agent will send mails, and if set to 1 it "+
         "\n                 will receive mails"+
         "\n"+
         "\n NOTE: Tool agent will read extended attributes only if they are called through"+
         "\n       Default tool agent (not by shark directly) and this is the case when information "+
         "\n       on which tool agent to start for XPDL application definition is not contained in mappings";
      return i;
   }


}
