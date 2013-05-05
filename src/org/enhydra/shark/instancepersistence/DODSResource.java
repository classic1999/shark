package org.enhydra.shark.instancepersistence;


import org.enhydra.shark.api.internal.instancepersistence.*;

public class DODSResource implements ResourcePersistenceInterface {

   private String username;
   private String name;

   public void setUsername (String un) {
      this.username=un;
   }

   public String getUsername () {
      return username;
   }

   public void setName (String newName) {
      name=newName;
   }

   public String getName () {
      return name;
   }

}
