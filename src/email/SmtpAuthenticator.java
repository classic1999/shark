package email;

import javax.mail.PasswordAuthentication;
import javax.mail.Authenticator;

/** The class Authenticator represents an object that knows how to obtain authentication 
 *  for a network connection. 
 *  Applications use this class by creating a subclass, and registering an instance of that 
 *  subclass with the system with setDefault(). When authentication is required, the system
 *  will invoke a method on the subclass (like getPasswordAuthentication)
 *
 *  @author Paloma Trigueros Cabezon
 *  @version 1.0
 */

public class SmtpAuthenticator extends javax.mail.Authenticator {

        String pass = "";
        String login = "";

        public SmtpAuthenticator() {
                super();
        }

        public SmtpAuthenticator(String login, String pass) {
                super();

                this.login = login;
                this.pass = pass;
        }

        public PasswordAuthentication getPasswordAuthentication() {
                if (pass.equals(""))
                        return null;
                else
                        return new PasswordAuthentication(login, pass);
        }

}