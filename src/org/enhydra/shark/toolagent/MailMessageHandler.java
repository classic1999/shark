package org.enhydra.shark.toolagent;

import java.io.IOException;
import javax.mail.MessagingException;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.internal.toolagent.AppParameter;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/*
 *  @author Sasa Bojanic
 */
public interface MailMessageHandler {

   void configure (CallbackUtilities cus,AppParameter[] aps) throws RootException;

   void sendMail () throws Exception;
   String receiveMail () throws Exception;

}
