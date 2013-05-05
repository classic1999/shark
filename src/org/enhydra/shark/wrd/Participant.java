package org.enhydra.shark.wrd;

import java.io.Serializable;

public class Participant implements Serializable, Cloneable {

   private String id;
   private Counter score=new Counter();

   public Participant () {}

   public Participant (String id) {
      this.id=id;
   }

   public void incrementScore () {
      score.increment();
   }

   public int getScore () {
      return score.value();
   }

   public String getId () {
      return id;
   }

   public String toString () {
      return "I'm "+id+", and my score is "+score.value();
   }

   public Object clone () {
      try {
         return super.clone();
         //return super.clone();
      } catch (Exception ex) {
         return null;
      }
   }

}
