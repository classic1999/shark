
package org.enhydra.shark.api.client.wfservice;

/**
 *
 * Represents script mappings.
 *
 * @author Zoran Milakovic
 */

public interface ScriptMappingAdministration {

   /**
    * This method is used to let shark know who uses this API.
    *
    * @param    userId              String representing user Id.
    *
    */
   void connect (String userId);

}
