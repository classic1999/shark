package org.enhydra.shark.api.client.wfservice;
import org.enhydra.shark.api.RootException;

/**
 * Raised when package that is being unloaded has processes
 * that are written in instance persistence repository, and are
 * instantiated from a package's process definitions.
 */
public final class PackageHasActiveProcesses extends RootException
{

   public PackageHasActiveProcesses ()
   {
      super();
   } // ctor


   public PackageHasActiveProcesses (String $reason)
   {
      super($reason);
   } // ctor

   public PackageHasActiveProcesses(Throwable th) {
      super(th);
   }

   public PackageHasActiveProcesses(String message, Throwable th) {
      super(message, th);
   }

} // class PackageHasActiveProcesses
