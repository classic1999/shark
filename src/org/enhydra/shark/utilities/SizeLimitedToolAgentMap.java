package org.enhydra.shark.utilities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SizeLimitedToolAgentMap extends HashMap {

   private int maximumSize = Integer.MAX_VALUE-1;

   public SizeLimitedToolAgentMap() {
      super();
      System.out.println("Tool agent map created, maximum number of mappings is "+maximumSize);
   }

   public SizeLimitedToolAgentMap (int maxSize) {
      this();
      if (maxSize<0) {
         maximumSize=Integer.MAX_VALUE-1;
      } else {
         maximumSize = maxSize;
      }
      System.out.println("Tool agent map created, maximum number of mappings is "+maximumSize);
   }

   public SizeLimitedToolAgentMap (int initialCapacity, float loadFactor) {
      super(initialCapacity, loadFactor);
      System.out.println("Tool agent map created, maximum number of mappings is "+maximumSize);
   }

   public SizeLimitedToolAgentMap (int initialCapacity, float loadFactor, int maxSize) {
      this(initialCapacity, loadFactor);
      maximumSize = maxSize;
      System.out.println("Tool agent map created, maximum number of mappings is "+maximumSize);
   }

   public SizeLimitedToolAgentMap (Map m) {
      maximumSize = m.size()+1;
      putAll(m);
      System.out.println("Tool agent map created, maximum number of mappings is "+maximumSize);
   }

   public synchronized Object put( Object key, Object value ) {
      int mapSize = size();

      if (mapSize >= maximumSize) {
         System.err.println("Tool agent map can't accept more mappings - the maximum no. of mappings is "+maximumSize+" !");
         return null;
      } else {
         System.out.println("New mapping added to tool agent map, tool agent map currently has "+(mapSize+1)+" mappings !");
         return super.put(key,value);
      }
   }

   public synchronized void putAll(Map t) {
      Iterator iter = t.entrySet().iterator();
      while(iter.hasNext()) {
         Map.Entry entry = (Map.Entry)iter.next();
         this.put(entry.getKey(), entry.getValue());
      }
   }

   public synchronized Object remove (Object key) {
      return super.remove(key);
   }

   public int getMaximumSize() {
      return maximumSize;
   }

}
