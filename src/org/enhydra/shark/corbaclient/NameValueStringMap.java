package org.enhydra.shark.corbaclient;

/**
* The class that holds the strings representing some property,
* and it's value.
*/
public class NameValueStringMap {
   public String name;
   public String value;

   public NameValueStringMap (String name,String value) {
      this.name=name;
      this.value=value;
   }

   public boolean equals (Object obj) {
      if (obj instanceof NameValueStringMap) {
         NameValueStringMap nvsm=(NameValueStringMap)obj;
         if (nvsm==null) return false;
         return (name.equals(nvsm.name) &&
            value.equals(nvsm.value));
      }
      return false;
   }

   public String toString () {
      return "["+name+","+value+"]";
   }
}
