package org.enhydra.shark.api.common;

import java.io.Serializable;

/**
 * This class represents the deadline information.
 * 
 * @author Sasa Bojanic
 */
public final class DeadlineInfo implements Serializable {
   /** Process Id */
   public String procId = null;

   /** Activity Id */
   public String actId = null;

   /** Is executed */
   public boolean isExecuted = false;

   /** Time limit */
   public long timeLimit = -1;

   /** Exception name */
   public String exceptionName = null;

   /** Synchronous deadline. */
   public boolean isSynchronous = false;

   /**
    * Creates instance with all object fields initialized to null, and primitive type fields to -1
    * and false.
    */
   public DeadlineInfo() {
   }

   /**
    * Creates an instance with fields set to the given parameter values.
    */
   public DeadlineInfo(
         String _procId, 
         String _actId, 
         boolean _isExecuted, 
         long _timeLimit, 
         String _exceptionName, 
         boolean _isSynchronous) {

      procId = _procId;
      actId = _actId;
      isExecuted = _isExecuted;
      timeLimit = _timeLimit;
      exceptionName = _exceptionName;
      isSynchronous = _isSynchronous;
   }

   public String toString() {
      return "[procId=" + procId + ", actId=" + procId + ", isExecuted=" + isExecuted
            + ", excName=" + exceptionName + ",timeLimit=" + new java.util.Date(timeLimit)
            + ",isSynchronous=" + isSynchronous + "]";
   }

}