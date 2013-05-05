package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;

/**
 * Is raised by a request for the source of a WfEventAudit when the source
 * is no longer available.
 */
public final class SourceNotAvailable extends RootException
{

   public SourceNotAvailable ()
   {
      super();
   } // ctor


   public SourceNotAvailable (String $reason)
   {
      super($reason);
   } // ctor

   public SourceNotAvailable(Throwable th) {
      super(th);
   }

   public SourceNotAvailable(String message, Throwable th) {
      super(message, th);
   }

} // class SourceNotAvailable
