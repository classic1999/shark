package org.enhydra.shark.wrd;

import java.io.Serializable;
import java.util.*;

public class Participants extends HashMap implements Serializable, Cloneable {

   public void addParticipant (String id) {
      put(id,new Participant(id));
   }

   public Participant getParticipant (String id) {
      return (Participant)get(id);
   }

   public Participant theBestScoreParticipant () {
      Participant theBest=null;
      Iterator it=values().iterator();
      while (it.hasNext()) {
         Participant p=(Participant)it.next();
         if (theBest==null) {
            theBest=p;
            continue;
         }
         if (p.getScore()>theBest.getScore()) {
            theBest=p;
         }
      }
      return theBest;
   }

   public Object clone () {
      try {
         Participants ps=(Participants)super.clone();
         ps.clear();
         Iterator it=this.entrySet().iterator();
         while (it.hasNext()) {
            Map.Entry me=(Map.Entry)it.next();
            ps.put(me.getKey(),((Participant)me.getValue()).clone());
         }
         return ps;
      } catch (Exception ex) {
         return null;
      }
   }

}
