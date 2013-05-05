/* Collective.java */
package org.enhydra.shark.corba;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Collective
 * 
 * @author V.Puskas
 * @version 0.1
 */
public interface Collective {
   public void __recruit(org.omg.CORBA.Object obj);

   public void __leave(org.omg.CORBA.Object _obj);

   public void __disband(org.omg.CORBA.ORB _orb);

   public final class CollectiveCORBA implements Collective {

      private transient HashSet myObjects = new HashSet();

      public synchronized void __recruit(org.omg.CORBA.Object obj) {
         myObjects.add(obj);
      }

      public synchronized void __disband(org.omg.CORBA.ORB _orb) {
         for (Iterator it = myObjects.iterator(); it.hasNext();) {
            _orb.disconnect((org.omg.CORBA.Object) it.next());
            it.remove();
         }
         myObjects = null;
      }

      public synchronized void __leave(org.omg.CORBA.Object obj) {
         Iterator i=myObjects.iterator();
         Object toRemove=obj;
         while (i.hasNext()) {
            org.omg.CORBA.Object o=(org.omg.CORBA.Object)i.next();
            if (obj._is_equivalent(o)) {
               toRemove=o;
               break;
            }
         }
         boolean r=myObjects.remove(toRemove);
         if (r==false) System.out.println("Warning: object is not removed from collection!");
      }
   
   }

}