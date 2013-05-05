package org.enhydra.shark.api.internal.working;

import java.util.Properties;
import org.enhydra.shark.api.RootException;

/**
 * CallbackUtilities interface contains properties used for configuration
 * in Shark.
 *
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public interface CallbackUtilities {

   /**
    * Returns value of property <i>propertyName</i> used for Shark configuration.
    *
    * @param propertyName property name.
    * @return Value of property <i>propertyName</i>.
    *
    */
   String getProperty (String propertyName);

   /**
    * Returns value of property <i>propertyName</i> used for Shark configuration.
    * If doesn't exist the default value is returned.
    *
    * @param propertyName property name.
    * @param defaultValue default property value.
    * @return Value of property <i>propertyName</i> or default value.
    *
    */
   String getProperty (String propertyName,String defaultValue);

   /**
    * Returns all properties used for Shark configuration.
    *
    * @return Properties object.
    *
    */
   Properties getProperties ();

   /**
    * Sets properties for Shark configuration.
    *
    * @param props Properties object.
    *
    */
   void setProperties (Properties props);

   /**
    * Log a message object with the <i>ERROR</i> Level.
    *
    * @param msg the message to log.
    *
    */
   void error (String msg);

   /**
    * Log a message object with the <i>ERROR</i> Level.
    *
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace.
    *
    */
   void error (String msg,RootException ex);

   /**
    * Log a message object with the <i>ERROR</i> Level.
    *
    * @param channel the log channel to be used for logging.
    * @param msg the message to log.
    *
    */
   void error (String channel,String msg);

   /**
    * Log a message object with the <i>ERROR</i> Level.
    *
    * @param channel the log channel to be used for logging.
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace.
    *
    */
   void error (String channel,String msg,RootException ex);

   /**
    * Log a message object with the <i>WARN</i> Level.
    *
    * @param msg the message to log.
    *
    */
   void warn (String msg);

   /**
    * Log a message object with the  <i>WARN</i> Level.
    *
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace.
    *
    */
   void warn (String msg,RootException ex);

   /**
    * Log a message object with the  <i>WARN</i> Level.
    *
    * @param channel the log channel to be used for logging.
    * @param msg the message to log.
    *
    */
   void warn (String channel,String msg);

   /**
    * Log a message object with the  <i>WARN</i> Level.
    *
    * @param channel the log channel to be used for logging.
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace.
    *
    */
   void warn (String channel,String msg,RootException ex);

   /**
    * Log a message object with the  <i>INFO</i> Level.
    *
    * @param msg the message to log.
    *
    */
   void info (String msg);

   /**
    * Log a message object with the <i>INFO</i> Level.
    *
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace.
    *
    */
   void info (String msg,RootException ex);

   /**
    * Log a message object with the <i>INFO</i> Level.
    *
    * @param channel the log channel to be used for logging.
    * @param msg the message to log.
    *
    */
   void info (String channel,String msg);

   /**
    * Log a message object with the <i>INFO</i> Level.
    *
    * @param channel the log channel to be used for logging.
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace.
    *
    */
   void info (String channel,String msg,RootException ex);

   /**
    * Log a message object with the <i>DEBUG</i> level.
    *
    * @param msg the message to log.
    *
    */
   void debug (String msg);

   /**
    * Log a message object with the <i>DEBUG</i> level.
    *
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace.
    *
    */
   void debug (String msg,RootException ex);

   /**
    * Log a message object with the <i>DEBUG</i> level.
    *
    * @param channel the log channel to be used for logging.
    * @param msg the message to log.
    *
    */
   void debug (String channel,String msg);

   /**
    * Log a message object with the <i>DEBUG</i> level.
    *
    * @param channel the log channel to be used for logging.
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace.
    *
    */
   void debug (String channel,String msg,RootException ex);


}

