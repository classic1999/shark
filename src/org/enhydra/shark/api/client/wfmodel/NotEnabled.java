package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;

/**
 * Is raised by an attempt to create a WfProcess using a WfProcessMgr
 * that is disabled.
 */
public final class NotEnabled extends RootException
{

   public NotEnabled ()
   {
      super();
   } // ctor


   public NotEnabled (String $reason)
   {
      super($reason);
   } // ctor

   public NotEnabled(Throwable th) {
      super(th);
   }

   public NotEnabled(String message, Throwable th) {
      super(message, th);
   }

} // class NotEnabled
