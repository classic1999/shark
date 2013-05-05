package email;

import org.enhydra.shark.api.internal.toolagent.AppParameter;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/** MailProc sends an e-mail using specified parameters:
 *
 *  param1 - text_msg         The text of the message
 *  param2 - subject       Subject of the e-mail
 *  param3 - source_address      Address of the sender (XXX@XXXX.XXX)
 *  param4 - destination_address Address of the receiver (XXX@XXXX.XXX)
 *  param5 - user       If the server requires athentication
 *             Can be an empty string ""
 *  param6 - password         Password the user needs to authenticate
 *             Can be an empty string ""
 *  param7 - server        IP address of de server
 *  param8 - port       Number of the port (usually 25)
 *
 *  @throws MessagingException      If exists an error while sending the message
 *
 *  @author Paloma Trigueros Cabezon
 *  @version 1.0
 */


public class MailProc {
   public static void execute (AppParameter param1, AppParameter param2, AppParameter param3, AppParameter param4, AppParameter param5, AppParameter param6, AppParameter param7, AppParameter param8)
   {
        try
        {
            System.out.println("Beginning MailProc...");

            //Extracting parameters
            System.out.println("Extracting parameters...");

            /*  This is an example of how to send a number (the result of
      a calculation, for example) as the text of the message:
               long text      = param1.extract_longlong();
               Long t         = new Long(text);
               String text_msg   = t.toString(text);
            */

            String text_msg      = param1.the_value.toString();
            String subject    = param2.the_value.toString();
            String source_address   = param3.the_value.toString();
            String destination_address    = param4.the_value.toString();
            String user       = param5.the_value.toString();
            String password      = param6.the_value.toString();
            String server     = param7.the_value.toString();
            long port         = ((Long)param8.the_value).longValue();

            // Get system properties
            Properties props = new Properties();

            // Setup mail server
            props.put("mail.smtp.host", server);
            props.put("mail.smtp.port", "" + port);

            // User name
            props.put("mail.smtp.user", user);
            props.put("mail.smtp.auth", "true");

            // Get session
            javax.mail.Session session = Session.getInstance(props,new SmtpAuthenticator(user,password));

            // Define message
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(source_address));

            message.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(destination_address));

            message.setSubject(subject);

            message.setContent(text_msg, "text/plain");

            // Send message
            Transport.send(message);

        }
        catch (Exception ex)
        {
            System.out.println("MailProc - Problems during execution of MailProc" + ex);
        }
        System.out.println("Finishing MailProc...");
   }
}
