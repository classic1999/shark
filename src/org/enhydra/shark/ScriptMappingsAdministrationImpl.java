/*
 * ScriptMappingsImpl.java. Created on Apr 29, 2004.
 */
package org.enhydra.shark;

import org.enhydra.shark.api.client.wfservice.ScriptMappingAdministration;

/**
 *
 *
 * @author Zoran Milakovic
 */
public class ScriptMappingsAdministrationImpl implements ScriptMappingAdministration {

   private String userId="Unknown";

   public void connect (String userId) {
      this.userId=userId;
   }

}
