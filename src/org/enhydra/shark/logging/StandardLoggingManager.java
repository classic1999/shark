package org.enhydra.shark.logging;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.enhydra.shark.api.internal.logging.LoggingManager;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * Implementation of LoggingManager interface.
 *
 * @author Sasa Bojanic
 * @author Tanja Jovanovic
 */
public class StandardLoggingManager implements LoggingManager {
   
   private static final String defaultLogChannel="Shark";
   private CallbackUtilities cus;

   /**
    * Configures StandardLoggingManager.
    *
    * @param cus an instance of CallbackUtilities used to get
    *            properties for configuring logging in Shark.
    *
    * @exception RootException thrown if configuring doesn't succeed.
    */
   public void configure (CallbackUtilities cus) throws RootException {
      this.cus=cus;
      PropertyConfigurator.configure(cus.getProperties()); 
   }

   //////////////////////////////////////////////////////////////////
   //  LoggingManager API implementation
   //////////////////////////////////////////////////////////////////
   
   /**
    * Log a message object with the <i>ERROR</i> Level.
    *
    * @param msg the message to log.
    *
    * @exception RootException If something unexpected happens.
    *
    */
   public void error (String msg)  throws RootException {
      error(defaultLogChannel,msg);
   }

   /**
    * Log a message object with the <i>ERROR</i> Level.
    *
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace. 
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   public void error (String msg,RootException ex)  throws RootException {
      error(defaultLogChannel,msg,ex);
   }

   /**
    * Log a message object with the <i>ERROR</i> Level.
    *
    * @param channel the log channel to be used for logging. 
    * @param msg the message to log.
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   public void error (String channel,String msg)  throws RootException {
      Logger logger=Logger.getLogger(channel);
      if (logger!=null)
         logger.error(msg);
      else
         throw new RootException("Logger does not exist!"); 
         
   }

   /**
    * Log a message object with the <i>ERROR</i> Level.
    *
    * @param channel the log channel to be used for logging. 
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace. 
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   public void error (String channel,String msg,RootException ex)  throws RootException{
      Logger logger=Logger.getLogger(channel);
      if (logger!=null)
         logger.error(msg,ex);
      else
         throw new RootException("Logger does not exist!");    
   }

   /**
    * Log a message object with the <i>WARN</i> Level.
    *
    * @param msg the message to log.
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   public void warn (String msg) throws RootException {
      warn(defaultLogChannel,msg);
   }

   /**
    * Log a message object with the <i>WARN</i> Level.
    *
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace. 
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   public void warn (String msg,RootException ex) throws RootException {
      warn(defaultLogChannel,msg,ex);
   }

   /**
    * Log a message object with the <i>WARN</i> Level.
    *
    * @param channel the log channel to be used for logging. 
    * @param msg the message to log.
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   public void warn (String channel,String msg) throws RootException {
      Logger logger=Logger.getLogger(channel);
      if (logger!=null)
         logger.warn(msg);
      else
         throw new RootException("Logger does not exist!");    
   }

   /**
    * Log a message object with the <i>WARN</i> Level.
    *
    * @param channel the log channel to be used for logging. 
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace. 
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   public void warn (String channel,String msg,RootException ex) throws RootException {
      Logger logger=Logger.getLogger(channel);
      if (logger!=null)
         logger.warn(msg,ex);
      else
         throw new RootException("Logger does not exist!");    
   }

   /**
    * Log a message object with the <i>INFO</i> Level.
    *
    * @param msg the message to log.
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   public void info (String msg) throws RootException {
      info(defaultLogChannel,msg);
   }

   /**
    * Log a message object with the <i>INFO</i> Level.
    *
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace. 
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   public void info (String msg,RootException ex) throws RootException {
      info(defaultLogChannel,msg,ex);
   }

   /**
    * Log a message object with the <i>INFO</i> Level.
    *
    * @param channel the log channel to be used for logging. 
    * @param msg the message to log.
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   public void info (String channel,String msg) throws RootException {
      Logger logger=Logger.getLogger(channel);
      if (logger!=null)
         logger.info(msg);
      else
         throw new RootException("Logger does not exist!"); 
   }

   /**
    * Log a message object with the <i>INFO</i> Level.
    *
    * @param channel the log channel to be used for logging. 
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace. 
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   public void info (String channel,String msg,RootException ex) throws RootException {
      Logger logger=Logger.getLogger(channel);
      if (logger!=null)
         logger.info(msg,ex);
      else
         throw new RootException("Logger does not exist!");    
   }

   /**
    * Log a message object with the <i>DEBUG</i> level.
    *
    * @param msg the message to log.
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   public void debug (String msg) throws RootException {
      debug(defaultLogChannel,msg);
   }

   /**
    * Log a message object with the <i>DEBUG</i> level.
    *
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace. 
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   public void debug (String msg,RootException ex) throws RootException {
      debug(defaultLogChannel,msg,ex);
   }

   /**
    * Log a message object with the <i>DEBUG</i> level.
    *
    * @param channel the log channel to be used for logging. 
    * @param msg the message to log.
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   public void debug (String channel,String msg) throws RootException {
      Logger logger=Logger.getLogger(channel);
      if (logger!=null)
         logger.debug(msg);
      else
         throw new RootException("Logger does not exist!");    
   }

   /**
    * Log a message object with the <i>DEBUG</i> level.
    *
    * @param channel the log channel to be used for logging. 
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace. 
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   public void debug (String channel,String msg,RootException ex) throws RootException {
      Logger logger=Logger.getLogger(channel);
      if (logger!=null)
         logger.debug(msg,ex);
      else
         throw new RootException("Logger does not exist!");    
   }

}
