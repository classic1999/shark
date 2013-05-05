package org.enhydra.shark.eventaudit;

import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.enhydra.shark.Shark;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.eventaudit.*;
import org.enhydra.shark.api.internal.instancepersistence.PersistenceException;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.utilities.MiscUtilities;

/**
 * This persistent manager send an email to every person that has to
 * accept a new task DODSPersistentManager is the default persistent
 * manager of Enhydra-Shark.
 * <p>
 * In addition to original functionality (coded against beta 2 by
 * Mathias), there is now new configuration procedure, and separation
 * from other components - persistence manager and tool agents.
 * 
 * @version 1.1
 * @author Mathias Holst
 * @since 9. August 2004
 */
public class SMTPEventAuditManager implements EventAuditManagerInterface {

   private boolean DEBUG;

   private boolean enable_email;

   private CallbackUtilities cus;

   private EventAuditManagerInterface other;

   private static final String _PARAM_PREFIX = "SMTPEventAuditManager";

   private static final String _PARAM_OTHER_MGR = ".OtherClassName";

   private static final String _PARAM_OTHER_MGR_DEFAULT = "org.enhydra.shark.eventaudit.DODSEventAuditManager";

   private static final String _PARAM_DEBUG = ".Debug";

   private static final String _PARAM_ENABLE = ".Enable";

   private static final String _PARAM_SERVER_ADDR = ".Server";

   private static final String _PARAM_SERVER_PORT = ".Port";

   private static final String _PARAM_AUTH = ".Auth";

   protected static final String _PARAM_PASSWD = ".Passwd";

   private static final String _PARAM_SOURCE = ".Source";

   private static final String _PARAM_MESSAGE_TEMPLATE = ".Message";

   private static final String _PARAM_SUBJECT_TEMPLATE = ".Subject";

   private String _subject;

   private String _message;

   public void configure(CallbackUtilities cus) throws RootException {
      try {
         other = (EventAuditManagerInterface) Class.forName(cus.getProperty(_PARAM_PREFIX
                                                                                  + _PARAM_OTHER_MGR,
                                                                            _PARAM_OTHER_MGR_DEFAULT))
            .newInstance();
         other.configure(cus);
      } catch (Throwable t) {}
      DEBUG = Boolean.valueOf(cus.getProperty(_PARAM_PREFIX + _PARAM_DEBUG,
                                              "false")).booleanValue();
      enable_email = Boolean.valueOf(cus.getProperty(_PARAM_PREFIX
                                                     + _PARAM_ENABLE, "false"))
         .booleanValue();
      _subject = cus.getProperty(_PARAM_PREFIX + _PARAM_SUBJECT_TEMPLATE,
                                 "New task: {activity}");
      _message = cus.getProperty(_PARAM_PREFIX + _PARAM_MESSAGE_TEMPLATE,
                                 "Dear {person},\n\nyou have a new task!\n\n"
                                 + "name: {activity}\n"
                                 + "workflow: {process}\n"
                                 + "workflow id: {definition}");
      this.cus = cus;
      if (DEBUG) System.err.println(_PARAM_PREFIX + " configured");
   }

   /**
    * Description of the Method
    * 
    * @param text_msg Description of the Parameter
    * @param subject Description of the Parameter
    * @param destination_address Description of the Parameter
    * @throws RootException
    */
   private void sendEmail(String text_msg,
                          String subject,
                          String destination_address) throws RootException {
      // Get system properties
      try {
         // FIXME: can we create this props object only once
         final Properties props = new Properties();
         // Setup mail server
         props.put("mail.smtp.host", cus.getProperty(_PARAM_PREFIX
                                                     + _PARAM_SERVER_ADDR));
         props.put("mail.smtp.port", cus.getProperty(_PARAM_PREFIX
                                                     + _PARAM_SERVER_PORT));
         // User name
         final String auth = cus.getProperty(_PARAM_PREFIX + _PARAM_AUTH);
         javax.mail.Session session;
         if (auth.equals("")) {
              session = Session.getInstance(props);
         } else {
            props.put("mail.smtp.user", auth);
            props.put("mail.smtp.auth", "true");
            // Get session
            session = Session.getInstance(props,
                                          new Authenticator() {
                                             public PasswordAuthentication getPasswordAuthentication() {
                                                return new PasswordAuthentication(auth,
         cus.getProperty(_PARAM_PREFIX
                         + _PARAM_PASSWD));
                                                                }
                                                             });

         }
         // Define message
         final MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress(cus.getProperty(_PARAM_PREFIX
                                                             + _PARAM_SOURCE)));
         message.addRecipient(Message.RecipientType.TO,
                              new InternetAddress(destination_address));
         message.setSubject(subject);
         message.setContent(text_msg, "text/plain");
         // Send message
         Transport.send(message);
      } catch (Exception e) {
         if (DEBUG) {
            e.printStackTrace();
         }
         throw new EventAuditException(e);
      }
   }

   /**
    * Description of the Method
    * 
    * @param assea Description of the Parameter
    * @param ti Description of the Parameter
    * @exception PersistenceException Description of the Exception
    */
   public void persist(AssignmentEventAuditPersistenceInterface assea,
                       SharkTransaction ti) throws EventAuditException {
      other.persist(assea, ti);
      // senhd email to person that has to accpet new task
      if (enable_email) {
         try {
            // get user's login name
            final String login = assea.getNewResourceUsername();
            // get user's real name
            final String person = Shark.getInstance()
               .getAdminInterface()
               .getUserGroupAdministration()
               .getUserRealName(login);
            // get user's email-address
            final String email = Shark.getInstance()
               .getAdminInterface()
               .getUserGroupAdministration()
               .getUserEMailAddress(login);
            // define subject of email
            final String subject = parse(_subject,
                                         person,
                                         assea.getActivityName(),
                                         assea.getProcessName(),
                                         assea.getProcessDefinitionId());
            final String msg = parse(_message,
                                     person,
                                     assea.getActivityName(),
                                     assea.getProcessName(),
                                     assea.getProcessDefinitionId());
            // finally send email
            sendEmail(msg, subject, email);
         } catch (Exception e) {
            System.err.println(e);
            if (DEBUG) {
               e.printStackTrace();
            }
         }
      }
   }

   private String parse(String template,
                        String person,
                        String activity,
                        String process,
                        String definition) {
      String ret = template;
      if (-1 != template.indexOf("{person}")) {
         ret = MiscUtilities.replaceAll(ret, "{person}", person);
      }
      if (-1 != template.indexOf("{activity}")) {
         ret = MiscUtilities.replaceAll(ret, "{activity}", activity);
      }
      if (-1 != template.indexOf("{process}")) {
         ret = MiscUtilities.replaceAll(ret, "{process}", process);
      }
      if (-1 != template.indexOf("{definition}")) {
         ret = MiscUtilities.replaceAll(ret, "{definition}", definition);
      }
      return ret;
   }

   public void persist(CreateProcessEventAuditPersistenceInterface cpea,
                       SharkTransaction ti) throws EventAuditException {
      other.persist(cpea, ti);
   }

   public void persist(DataEventAuditPersistenceInterface dea,
                       SharkTransaction ti) throws EventAuditException {
      other.persist(dea, ti);
   }

   public void persist(StateEventAuditPersistenceInterface sea,
                       SharkTransaction ti) throws EventAuditException {
      other.persist(sea, ti);
   }

   public boolean restore(AssignmentEventAuditPersistenceInterface assea,
                          SharkTransaction ti) throws EventAuditException {
      return other.restore(assea, ti);
   }

   public boolean restore(CreateProcessEventAuditPersistenceInterface cpea,
                          SharkTransaction ti) throws EventAuditException {
      return other.restore(cpea, ti);
   }

   public boolean restore(DataEventAuditPersistenceInterface dea,
                          SharkTransaction ti) throws EventAuditException {
      return other.restore(dea, ti);
   }

   public boolean restore(StateEventAuditPersistenceInterface sea,
                          SharkTransaction ti) throws EventAuditException {
      return other.restore(sea, ti);
   }

   public List restoreProcessHistory(String procId, SharkTransaction ti) throws EventAuditException {
      return other.restoreProcessHistory(procId, ti);
   }

   public List restoreActivityHistory(String procId,
                                      String actId,
                                      SharkTransaction ti) throws EventAuditException {
      return other.restoreActivityHistory(procId, actId, ti);
   }

   public void delete(AssignmentEventAuditPersistenceInterface assea,
                      SharkTransaction ti) throws EventAuditException {
      other.delete(assea, ti);
   }

   public void delete(CreateProcessEventAuditPersistenceInterface cpea,
                      SharkTransaction ti) throws EventAuditException {
      other.delete(cpea, ti);
   }

   public void delete(DataEventAuditPersistenceInterface dea,
                      SharkTransaction ti) throws EventAuditException {
      other.delete(dea, ti);
   }

   public void delete(StateEventAuditPersistenceInterface sea,
                      SharkTransaction ti) throws EventAuditException {
      other.delete(sea, ti);
   }

   public AssignmentEventAuditPersistenceInterface createAssignmentEventAudit() {
      return other.createAssignmentEventAudit();
   }

   public CreateProcessEventAuditPersistenceInterface createCreateProcessEventAudit() {
      return other.createCreateProcessEventAudit();
   }

   public DataEventAuditPersistenceInterface createDataEventAudit() {
      return other.createDataEventAudit();
   }

   public StateEventAuditPersistenceInterface createStateEventAudit() {
      return other.createStateEventAudit();
   }

   public String getNextId(String idName) throws EventAuditException {
      return other.getNextId(idName);
   }
}

