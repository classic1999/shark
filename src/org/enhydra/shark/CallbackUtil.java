package org.enhydra.shark;

import java.util.Properties;
import org.enhydra.shark.SharkEngineManager;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * Implementation of Callback Utilities interface.
 *
 * @author Sasa Bojanic
 * @author Tanja Jovanovic
 */
public class CallbackUtil implements CallbackUtilities {

   private Properties properties;

   protected CallbackUtil () {
   }

   //////////////////////////////////////////////////////////////////
   //  Callback API implementation
   //////////////////////////////////////////////////////////////////

   /**
    * Returns value of property <i>propertyName</i> used for Shark configuration.
    *
    * @param name property name.
    * @return Value of property <i>name</i>.
    *
    */
   public String getProperty (String name) {
      return properties.getProperty(name);
   }

   /**
    * Returns value of property <i>name</i> used for Shark configuration.
    * If doesn't exist the default value is returned.
    *
    * @param name property name.
    * @param defaultValue default property value.
    * @return Value of property <i>name</i> or default value.
    *
    */
   public String getProperty (String name,String defaultValue) {
      return properties.getProperty(name,defaultValue);
   }

   /**
    * Returns all properties used for Shark configuration.
    *
    * @return Properties object.
    *
    */
   public Properties getProperties () {
      return properties;
   }

   /**
    * Sets properties for Shark configuration.
    *
    * @param props Properties object.
    *
    */
   public void setProperties (Properties props) {
      this.properties=props;
   }

   /**
    * Log a message object with the <i>ERROR</i> Level.
    *
    * @param msg the message to log.
    *
    */
   public void error (String msg) {
      if (SharkEngineManager.getInstance().getLoggingManager() != null) {
         try {
           SharkEngineManager.getInstance().getLoggingManager().error(msg);
         }
         catch (Exception e){
           System.err.println("Error in logging - errMsg="+e.getMessage());
         }
      }
   }

   /**
    * Log a message object with the <i>ERROR</i> Level.
    *
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace.
    *
    */
   public void error (String msg,RootException ex) {
     if (SharkEngineManager.getInstance().getLoggingManager() != null) {
        try {
          SharkEngineManager.getInstance().getLoggingManager().error(msg, ex);
        }
        catch (Exception e){
          System.err.println("Error in logging - errMsg="+e.getMessage());
        }
     }
   }

   /**
    * Log a message object with the <i>ERROR</i> Level.
    *
    * @param channel the log channel to be used for logging.
    * @param msg the message to log.
    *
    */
   public void error (String channel,String msg) {
      if (SharkEngineManager.getInstance().getLoggingManager() != null) {
         try {
           SharkEngineManager.getInstance().getLoggingManager().error(channel, msg);
         }
         catch (Exception e){
           System.err.println("Error in logging - errMsg="+e.getMessage());
         }
      }
   }

   /**
    * Log a message object with the <i>ERROR</i> Level.
    *
    * @param channel the log channel to be used for logging.
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace.
    *
    */
   public void error (String channel,String msg,RootException ex) {
      if (SharkEngineManager.getInstance().getLoggingManager() != null) {
         try {
           SharkEngineManager.getInstance().getLoggingManager().error(channel, msg, ex);
         }
         catch (Exception e){
           System.err.println("Error in logging - errMsg="+e.getMessage());
         }
      }
   }

   /**
    * Log a message object with the <i>WARN</i> Level.
    *
    * @param msg the message to log.
    *
    */
   public void warn (String msg) {
      if (SharkEngineManager.getInstance().getLoggingManager() != null) {
         try {
           SharkEngineManager.getInstance().getLoggingManager().warn(msg);
         }
         catch (Exception e){
           System.err.println("Error in logging - errMsg="+e.getMessage());
         }
      }
   }

   /**
    * Log a message object with the  <i>WARN</i> Level.
    *
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace.
    *
    */
   public void warn (String msg,RootException ex) {
      if (SharkEngineManager.getInstance().getLoggingManager() != null) {
         try {
           SharkEngineManager.getInstance().getLoggingManager().warn(msg, ex);
         }
         catch (Exception e){
           System.err.println("Error in logging - errMsg="+e.getMessage());
         }
      }
   }

   /**
    * Log a message object with the  <i>WARN</i> Level.
    *
    * @param channel the log channel to be used for logging.
    * @param msg the message to log.
    *
    */
   public void warn (String channel,String msg) {
      if (SharkEngineManager.getInstance().getLoggingManager() != null) {
         try {
           SharkEngineManager.getInstance().getLoggingManager().warn(channel, msg);
         }
         catch (Exception e){
           System.err.println("Error in logging - errMsg="+e.getMessage());
         }
      }
   }

   /**
    * Log a message object with the  <i>WARN</i> Level.
    *
    * @param channel the log channel to be used for logging.
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace.
    *
    */
   public void warn (String channel,String msg,RootException ex) {
      if (SharkEngineManager.getInstance().getLoggingManager() != null) {
         try {
           SharkEngineManager.getInstance().getLoggingManager().warn(channel, msg, ex);
         }
         catch (Exception e){
           System.err.println("Error in logging - errMsg="+e.getMessage());
         }
      }
   }

    /**
    * Log a message object with the  <i>INFO</i> Level.
    *
    * @param msg the message to log.
    *
    */
   public void info (String msg) {
      if (SharkEngineManager.getInstance().getLoggingManager() != null) {
         try {
           SharkEngineManager.getInstance().getLoggingManager().info(msg);
         }
         catch (Exception e){
           System.err.println("Error in logging - errMsg="+e.getMessage());
         }
      }
   }

  /**
    * Log a message object with the <i>INFO</i> Level.
    *
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace.
    *
    */
  public void info (String msg,RootException ex) {
      if (SharkEngineManager.getInstance().getLoggingManager() != null) {
         try {
           SharkEngineManager.getInstance().getLoggingManager().info(msg, ex);
         }
         catch (Exception e){
           System.err.println("Error in logging - errMsg="+e.getMessage());
         }
      }
   }

   /**
    * Log a message object with the <i>INFO</i> Level.
    *
    * @param channel the log channel to be used for logging.
    * @param msg the message to log.
    *
    */
   public void info (String channel,String msg) {
      if (SharkEngineManager.getInstance().getLoggingManager() != null) {
         try {
           SharkEngineManager.getInstance().getLoggingManager().info(channel, msg);
         }
         catch (Exception e){
           System.err.println("Error in logging - errMsg="+e.getMessage());
         }
      }
   }

   /**
    * Log a message object with the <i>INFO</i> Level.
    *
    * @param channel the log channel to be used for logging.
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace.
    *
    */
   public void info (String channel,String msg,RootException ex) {
      if (SharkEngineManager.getInstance().getLoggingManager() != null) {
         try {
           SharkEngineManager.getInstance().getLoggingManager().info(channel, msg, ex);
         }
         catch (Exception e){
            System.err.println("Error in logging - errMsg="+e.getMessage());
         }
      }
   }

   /**
    * Log a message object with the <i>DEBUG</i> level.
    *
    * @param msg the message to log.
    *
    */
   public void debug (String msg) {
      if (SharkEngineManager.getInstance().getLoggingManager() != null) {
         try {
           SharkEngineManager.getInstance().getLoggingManager().debug(msg);
         }
         catch (Exception e){
           System.err.println("Error in logging - errMsg="+e.getMessage());
         }
      }
   }

   /**
    * Log a message object with the <i>DEBUG</i> level.
    *
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace.
    *
    */
   public void debug (String msg,RootException ex) {
      if (SharkEngineManager.getInstance().getLoggingManager() != null) {
         try {
           SharkEngineManager.getInstance().getLoggingManager().debug(msg, ex);
         }
         catch (Exception e){
           System.err.println("Error in logging - errMsg="+e.getMessage());
         }
      }
   }

   /**
    * Log a message object with the <i>DEBUG</i> level.
    *
    * @param channel the log channel to be used for logging.
    * @param msg the message to log.
    *
    */
   public void debug (String channel,String msg) {
      if (SharkEngineManager.getInstance().getLoggingManager() != null) {
         try {
           SharkEngineManager.getInstance().getLoggingManager().debug(channel, msg);
         }
         catch (Exception e){
           System.err.println("Error in logging - errMsg="+e.getMessage());
         }
      }
   }

   /**
    * Log a message object with the <i>DEBUG</i> level.
    *
    * @param channel the log channel to be used for logging.
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace.
    *
    */
   public void debug (String channel,String msg,RootException ex) {
      if (SharkEngineManager.getInstance().getLoggingManager() != null) {
         try {
           SharkEngineManager.getInstance().getLoggingManager().debug(channel, msg, ex);
         }
         catch (Exception e){
           System.err.println("Error in logging - errMsg="+e.getMessage());
         }
      }
   }

}

