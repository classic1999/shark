package org.enhydra.shark.toolagent;

import org.enhydra.shark.api.internal.toolagent.AppParameter;
import org.enhydra.shark.api.RootException;

import javax.mail.*;
import java.io.*;
import java.util.*;
import javax.mail.internet.*;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/*
 *  @author Sasa Bojanic
 */
public class DefaultMailMessageHandler implements MailMessageHandler {

   AppParameter[] sharkParams;


   protected static String SMTPMailServer;
   protected static String incomingMailServer;
   protected static int SMTPport;
   protected static int IMAPport;
   protected static int POP3port;
   protected static String sourceAddress;
   protected static String login;
   protected static String password;
   protected static String incomingMailProtocol;
   protected static String storeFolderName;

   public void configure (CallbackUtilities cus,AppParameter[] aps) throws RootException {
      this.sharkParams=aps;

      if (DefaultMailMessageHandler.SMTPMailServer==null) {
         DefaultMailMessageHandler.SMTPMailServer=cus.getProperty("DefaultMailMessageHandler.SMTPMailServer");
         DefaultMailMessageHandler.incomingMailServer=cus.getProperty("DefaultMailMessageHandler.IncomingMailServer");
         try {
            DefaultMailMessageHandler.SMTPport=Integer.parseInt(cus.getProperty("DefaultMailMessageHandler.SMTPPortNo"));
            DefaultMailMessageHandler.IMAPport=Integer.parseInt(cus.getProperty("DefaultMailMessageHandler.IMAPPortNo"));
            DefaultMailMessageHandler.POP3port=Integer.parseInt(cus.getProperty("DefaultMailMessageHandler.POP3PortNo"));
         } catch (Exception ex) {}
         DefaultMailMessageHandler.sourceAddress=cus.getProperty("DefaultMailMessageHandler.SourceAddress");
         DefaultMailMessageHandler.login=cus.getProperty("DefaultMailMessageHandler.Login");
         DefaultMailMessageHandler.password=cus.getProperty("DefaultMailMessageHandler.Password");
         DefaultMailMessageHandler.incomingMailProtocol=cus.getProperty("DefaultMailMessageHandler.IncomingMailProtocol");
         DefaultMailMessageHandler.storeFolderName=cus.getProperty("DefaultMailMessageHandler.StoreFolderName");
      }
   }


   public void sendMail () throws Exception {

      // Get system properties
      Properties props = new Properties();

      // Setup mail server
      props.put("mail.smtp.host",DefaultMailMessageHandler.SMTPMailServer);
      props.put("mail.smtp.port", "" + DefaultMailMessageHandler.SMTPport);

      // User name
      props.put("mail.smtp.user", DefaultMailMessageHandler.login);
      props.put("mail.smtp.auth", "true");

      // Get session
      javax.mail.Session session = Session.getInstance(props,new SmtpAuthenticator(DefaultMailMessageHandler.login,DefaultMailMessageHandler.password));

      // Define message
      MimeMessage message = new MimeMessage(session);

      message.setFrom(new InternetAddress(DefaultMailMessageHandler.sourceAddress));

      String[] dests=getEMailDestinationAddresses();
      if (dests!=null) {
         for (int i=0; i<dests.length; i++) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(dests[i]));
         }
      }
      message.setSubject(this.composeMailMessageSubject());
      message.setContent(this.composeMailMessageContent(), "text/plain");
      // Send message
      Transport.send(message);
   }

   public String receiveMail () throws Exception  {

      HashMap hashmap = new HashMap();

      // Get system properties
      Properties props = new Properties();

      // Get session
      javax.mail.Session session = Session.getInstance(props, null);

      // Get the store
      int imPort=DefaultMailMessageHandler.POP3port;
      if (!DefaultMailMessageHandler.incomingMailProtocol.equals("pop3")) {
         imPort=DefaultMailMessageHandler.IMAPport;
      }
      Store store = session.getStore(DefaultMailMessageHandler.incomingMailProtocol);
      store.connect(DefaultMailMessageHandler.incomingMailServer,imPort,DefaultMailMessageHandler.login,DefaultMailMessageHandler.password);

      // Get folder
      Folder folder = null;
      Message messages[] = null;
      Message msg=null;
      folder = store.getFolder(DefaultMailMessageHandler.storeFolderName);
      folder.open(Folder.READ_WRITE);
      messages = folder.getMessages();

      String subject=null;
      if (messages!=null && messages.length>0) {
         for (int i=0; i<messages.length; i++) {
            Flags flags=messages[i].getFlags();
            Flags.Flag[] flagarr=flags.getSystemFlags();
            boolean valid=true;
            System.out.println("Checking flags for mail message "+messages[i].getSubject());
            for (int j=0; j<flagarr.length; j++) {
               if (flagarr[j].equals(Flags.Flag.SEEN) || flagarr[j].equals(Flags.Flag.ANSWERED) ||
                     flagarr[j].equals(Flags.Flag.DELETED)) {
                  valid=false;
                  break;
               }
            }
            if (!valid) continue;
            msg=messages[i];
            subject = msg.getSubject();
            // Once we have the subject we mark message as seen
            msg.setFlag(Flags.Flag.SEEN, true);
            break;
         }
      }


      // Close connection
      folder.close(true);
      store.close();

      // here we use handler to set parameters based on mail content
      if (msg!=null) {
         this.setParamsBasedOnMailMessage(msg);
      } else {
         this.setParamsBasedOnMailMessage(null);
      }

      return subject;
   }

   public String composeMailMessageContent () throws Exception {
      return sharkParams[2].the_value.toString();
   }

   public String composeMailMessageSubject () throws Exception {
      return sharkParams[1].the_value.toString();
   }

   public String[] getEMailDestinationAddresses () throws Exception {
      String[] sa=new String[] {sharkParams[0].the_value.toString()};
      return sa;
   }

   public void setParamsBasedOnMailMessage (Message mmessage) throws Exception {
      if (mmessage!=null) {
         sharkParams[0].the_value=mmessage.getSubject();
      } else {
         sharkParams[0].the_value=null;
      }
   }

}
