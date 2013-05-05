package org.enhydra.shark.api.client.wfbase;

/**
 * Java implementation of OMG's data type. This is how OMG defines it:
 * <p>
 * The BaseError structure is used to hold information on an application error. The
 * exception_source is a printable description of the source of the exception. The
 * exception_object is a pass-by-value object or an object reference of the object that
 * generated the exception. The exception_code is an identifier associated with the
 * source type. The exception_reason is a textual string containing a description of the
 * exception and should correspond to the code.
 * <p>
 * Currently, it is not used in shark, but it is here because we want to
 * be as much as possible close to OMG specification.
 */
public final class BaseError
{
   public int exception_code = (int)0;
   public String exception_source = null;
   public Object exception_object = null;
   public String exception_reason = null;

   public BaseError ()
   {
   } // ctor

   public BaseError (int _exception_code, String _exception_source, Object _exception_object, String _exception_reason)
   {
      exception_code = _exception_code;
      exception_source = _exception_source;
      exception_object = _exception_object;
      exception_reason = _exception_reason;
   } // ctor

}
