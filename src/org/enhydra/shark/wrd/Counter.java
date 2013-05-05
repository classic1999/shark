package org.enhydra.shark.wrd;

import java.io.Serializable;

public class Counter implements Serializable, Cloneable {

   private int no;

   public void increment () {
      no++;
   }

   public void decrement () {
      no--;
   }

   public int value () {
      return no;
   }

   public void reset () {
      no=0;
   }

   public String toString () {
      return "I'm counter and my value is "+no;
   }

   public Object clone () {
      try {
         return super.clone();
      } catch (Exception ex) {
         return null;
      }
   }

   public boolean equals (Object obj) {
      if (!(obj instanceof Counter)) return false;
      Counter cnt=(Counter)obj;
      if (cnt.no==no) {
         return true;
      } else {
         return false;
      }
   }
}
