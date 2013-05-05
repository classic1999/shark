package org.enhydra.shark.api.internal.toolagent;

/**
 * It holds information on locally unique ID for the session and
 * vendor specific information.
 */
public interface SessionHandle {
   /**
    * Returns locally unique ID for the session.
    *
    * @return   Locally unique ID for the session.
    *
    */
   long getHandle();

   /**
    * Returns vendor specific information for the session.
    *
    * @return   Vendor specific information for the session.
    *
    */
   String getInfo();
}
