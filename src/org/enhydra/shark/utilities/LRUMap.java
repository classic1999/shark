/* ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2002 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package org.enhydra.shark.utilities;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * An implementation of a Map which has a maximum size and uses a Least Recently Used
 * algorithm to remove items from the Map when the maximum size is reached and new items are added.
 * </p>
 *
 * <p>
 * A synchronized version can be obtained with:
 * <code>Collections.synchronizedMap( theMapToSynchronize )</code>
 * If it will be accessed by multiple threads, you _must_ synchronize access
 * to this Map.  Even concurrent get(Object) operations produce indeterminate
 * behaviour.
 * </p>
 *
 * <p>
 * Unlike the Collections 1.0 version, this version of LRUCache does use a true
 * LRU algorithm.  The keys for all gets and puts are moved to the front of
 * the list.  LRUCache is now a subclass of SequencedHashMap, and the "LRU"
 * key is now equivalent to LRUCache.getFirst().
 * </p>
 *
 * @since 1.0
 * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
 * @author <a href="mailto:morgand@apache.org">Morgan Delagrange</a>
 */
public class LRUMap extends SequencedHashMap implements Externalizable {

   private int maximumSize = 0;

   /**
    * Default constructor, primarily for the purpose of
    * de-externalization.  This constructors sets a default
    * cache with maximum size set to 0 (cache is not used at the moment) and
    * with a default capacity (16) and load factor (0.75).
    */
   public LRUMap() {
      super();
   }

   /**
    * Creates a new LRUMap with a maximum capacity of <i>maxSize</i> and
    * with a default capacity (16) and load factor (0.75). Once <i>maxSize</i>
    * capacity is achieved, subsequent gets and puts will push keys out of
    * the map.
    *
    * @param maxSize      Maximum capacity of the LRUMap.
    */
   public LRUMap(int maxSize) {
      this();
      maximumSize = maxSize;
   }

   /**
    * Create a new LRUCache with a default maximum capacity (0) and
    * with the capacity <i>initialCapacity</i> and load factor <i>loadFactor</i>.
    *
    * @param  initialCapacity The initial capacity.
    * @param  loadFactor The load factor.
    */
   public LRUMap(int initialCapacity, float loadFactor) {
      super(initialCapacity, loadFactor);
   }

   /**
    * Create a new LRUCache with a maximum capacity of <i>maxSize</i> and
    * with the capacity <i>initialCapacity</i> and load factor <i>loadFactor</i>.
    * Once <i>maxSize</i> capacity is achieved, subsequent gets and puts will
    * push keys out of the map.
    *
    * @param  initialCapacity The initial capacity.
    * @param  loadFactor The load factor.
    * @param  maxSize Maximum capacity of the LRUMap.
    */

   public LRUMap(int initialCapacity, float loadFactor, int maxSize) {
      this(initialCapacity, loadFactor);
      maximumSize = maxSize;
   }

   /**
    * Create a new LRUCache with the same mappings as the specified map <i>m</i>.
    * The <tt>LRUCache</tt> instance is created with default load factor (0.75)
    * and an initial capacity sufficient to hold the mappings in the specified
    * map.
    *
    * @param  m The map whose mappings are to be placed in this LRUCache.
    */

   public LRUMap(Map m) {
      maximumSize = m.size()+1;
      //        super(m);
      putAll(m);
   }

   /**
    * Create a new LRUCache with the same mappings as the specified map <i>m</i>.
    * The <tt>LRUCache</tt> instance is created with default load factor (0.75)
    * and an initial capacity sufficient to hold the mappings in the specified
    * map. The attribute <i>maximumSize</i> is set to value <i>maxSize</i> if
    * that value is greater than the size of the map <i>m</i>.
    *
    * @param  m The map whose mappings are to be placed in this LRUCache.
    * @param  maxSize Maximum capacity of the LRUMap.
    */
   public LRUMap(Map m, int maxSize) {
      this(m);
      if ((maxSize<0) || (maxSize>m.size()+1))
         maximumSize = maxSize;
   }

   /**
    * Returns true if this LRUCache contains a mapping for the specified key.
    *
    * @param key The key whose presence in this LRUCache is to be tested.
    *
    * @return true if this LRUCache contains a mapping for the specified key.
    */
   public boolean containsKey(Object key) {
      if(maximumSize == 0)
         return false;
      return super.containsKey(key);
   }

   /**
    * Returns the value to which the specified key is mapped in this LRUCache,
    * or null if the map contains no mapping for this key. If found, the key
    * will be promoted to the Most Recently Used position.
    * Note that get(Object) operations will modify the underlying Collection.
    * Calling get(Object) inside of an iteration over keys, values, etc. is
    * currently unsupported.</p>
    *
    * @param key    Key to retrieve.
    * @return Returns the value. Returns null if the key has a null value <i>or</i>
    * if the key has no value.
    */
   public Object get(Object key) {
      if(maximumSize == 0)
         return null;
      if(!containsKey(key))
         return null;
      Object value = remove(key);
      super.put(key,value);
      return value;
   }

   /**
    * Associates the specified value with the specified key in this LRUCache.
    * If the map previously contained a mapping for this key, the old value
    * is replaced.
    * <p>(Note: this may result in the "Least Recently Used"
    * object being removed from the Map.  In that case,
    * the removeLRU() method is called.  See javadoc for
    * removeLRU() for more details.)</p>
    *
    * @param key Key with which the specified value is to be associated.
    * @param value Value to be associated with the specified key.
    * @return Former value of the key.
    */


   public Object put( Object key, Object value ) {
      if(maximumSize == 0)
         return null;
      int mapSize = size();
      Object retval = null;

      if ((maximumSize > 0) && (mapSize >= maximumSize )) {

         // don't retire LRU if you are just
         // updating an existing key
         if (!containsKey(key)) {
            // lets retire the least recently used item in the cache
            removeLRU();
         }
      }
      retval = super.put(key,value);
      return retval;
   }

   /**
    *  Adds all the mappings in the specified map to this map, replacing any
    *  mappings that already exist (as per {@link Map#putAll(Map)}).  The order
    *  in which the entries are added is determined by the iterator returned
    *  from {@link Map#entrySet()} for the specified map.
    *
    *  @param t the mappings that should be added to this map.
    *
    *  @exception NullPointerException if <code>t</code> is <code>null</code>.
    **/
   public void putAll(Map t) {
      Iterator iter = t.entrySet().iterator();
      while(iter.hasNext()) {
         Map.Entry entry = (Map.Entry)iter.next();
         this.put(entry.getKey(), entry.getValue());
      }
   }

   /**
    * This method is used internally by the class for finding and removing
    * the LRU Object.
    */
   protected void removeLRU() {
      Object key = getFirstKey();
      // be sure to call super.get(key), or you're likely to
      // get infinite promotion recursion
      Object value = super.get(key);
      remove(key);
      processRemovedLRU(key,value);
   }

   /**
    * Subclasses of LRUCache may hook into this method to
    * provide specialized actions whenever an Object is
    * automatically removed from the cache.  By default,
    * this method does nothing.
    *
    * @param key    Key that was removed.
    * @param value  Value of that key (can be null).
    */
   protected void processRemovedLRU(Object key, Object value) {
   }

   // Externalizable interface
   //-------------------------------------------------------------------------
   public void readExternal( ObjectInput in )  throws IOException, ClassNotFoundException {
      maximumSize = in.readInt();
      int size = in.readInt();

      for( int i = 0; i < size; i++ )  {
         Object key = in.readObject();
         Object value = in.readObject();
         put(key,value);
      }
   }

   public void writeExternal( ObjectOutput out ) throws IOException {
      out.writeInt( maximumSize );
      out.writeInt( size() );
      for( Iterator iterator = keySet().iterator(); iterator.hasNext(); ) {
         Object key = iterator.next();
         out.writeObject( key );
         // be sure to call super.get(key), or you're likely to
         // get infinite promotion recursion
         Object value = super.get( key );
         out.writeObject( value );
      }
   }


   // Properties
   //-------------------------------------------------------------------------
   /**
    * Getter for property maximumSize.
    *
    * @return Value of property maximumSize.
    */
   public int getMaximumSize() {
      return maximumSize;
   }
   /**
    * Setter for property maximumSize.
    *
    * @param maxSize New value of property maximumSize.
    */
   public void setMaximumSize(int maxSize) {
      this.maximumSize = maxSize;
      if(maxSize >= 0) {
         while (size() > maximumSize) {
            removeLRU();
         }
      }
   }


   // add a serial version uid, so that if we change things in the future
   // without changing the format, we can still deserialize properly.
   //    private static final long serialVersionUID = 2197433140769957051L;
}
