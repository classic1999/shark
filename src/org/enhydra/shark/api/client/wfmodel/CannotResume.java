package org.enhydra.shark.api.client.wfmodel;
import org.enhydra.shark.api.RootException;


/**
 * A CannotResume exception is raised when the execution object cannot be
 * resumed. For example, resuming a WfActivity might not be allowed when
 * the containing WfProcess is suspended.
 */
 public final class CannotResume extends RootException
{

   public CannotResume ()
   {
      super();
   } // ctor


   public CannotResume (String $reason)
   {
      super($reason);
   } // ctor

   public CannotResume(Throwable th) {
      super(th);
   }

   public CannotResume(String message, Throwable th) {
      super(message, th);
   }

} // class CannotResume
