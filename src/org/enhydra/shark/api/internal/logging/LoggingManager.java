package org.enhydra.shark.api.internal.logging;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * LoggingManager handles logging in Shark.
 *
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 * @author Tanja Jovanovic
 */
public interface LoggingManager {
  
   /**
    * Method configure is called at Shark start up, to configure
    * implementation of LoggingManager.
    *
    * @param cus an instance of CallbackUtilities used to get
    *            properties for configuring logging in Shark.
    *
    * @exception RootException Thrown if configuring doesn't succeed.
    */
   void configure (CallbackUtilities cus) throws RootException;
   
   /**
    * Log a message object with the <i>ERROR</i> Level.
    *
    * @param msg the message to log.
    *
    * @exception RootException If something unexpected happens.
    *
    */
   void error (String msg) throws RootException;
   
   /**
    * Log a message object with the <i>ERROR</i> Level.
    *
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace. 
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   void error (String msg, RootException ex) throws RootException;
   
   /**
    * Log a message object with the <i>ERROR</i> Level.
    *
    * @param channel the log channel to be used for logging. 
    * @param msg the message to log.
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   void error (String channel, String msg) throws RootException;
   
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
   void error (String channel, String msg, RootException ex) throws RootException;

   /**
    * Log a message object with the <i>WARN</i> Level.
    *
    * @param msg the message to log.
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   void warn (String msg) throws RootException;

   /**
    * Log a message object with the <i>WARN</i> Level.
    *
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace. 
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   void warn (String msg, RootException ex) throws RootException;

   /**
    * Log a message object with the <i>WARN</i> Level.
    *
    * @param channel the log channel to be used for logging. 
    * @param msg the message to log.
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   void warn (String channel,String msg) throws RootException;

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
   void warn (String channel, String msg, RootException ex) throws RootException;

   /**
    * Log a message object with the <i>INFO</i> Level.
    *
    * @param msg the message to log.
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   void info (String msg) throws RootException;

  /**
    * Log a message object with the <i>INFO</i> Level.
    *
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace. 
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   void info (String msg, RootException ex) throws RootException;

   /**
    * Log a message object with the <i>INFO</i> Level.
    *
    * @param channel the log channel to be used for logging. 
    * @param msg the message to log.
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   void info (String channel, String msg) throws RootException;

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
   void info (String channel, String msg, RootException ex) throws RootException;

   /**
    * Log a message object with the <i>DEBUG</i> level.
    *
    * @param msg the message to log.
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   void debug (String msg) throws RootException;

   /**
    * Log a message object with the <i>DEBUG</i> level.
    *
    * @param msg the message to log.
    * @param ex the exception to log, including its stack trace. 
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   void debug (String msg, RootException ex) throws RootException;

   /**
    * Log a message object with the <i>DEBUG</i> level.
    *
    * @param channel the log channel to be used for logging. 
    * @param msg the message to log.
    *
    * @exception   RootException If something unexpected happens.
    *
    */
   void debug (String channel, String msg) throws RootException;

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
   void debug (String channel, String msg, RootException ex) throws RootException;


}

