package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;

/**
 * Is raised by a request for event audit history of a WfExecutionObject
 * when the History is not available.
 */
public final class HistoryNotAvailable extends RootException
{

   public HistoryNotAvailable ()
   {
      super();
   } // ctor


   public HistoryNotAvailable (String $reason)
   {
      super($reason);
   } // ctor

   public HistoryNotAvailable(Throwable th) {
      super(th);
   }

   public HistoryNotAvailable(String message, Throwable th) {
      super(message, th);
   }

} // class HistoryNotAvailable
