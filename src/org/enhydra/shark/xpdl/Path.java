/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
// Modified by Sasa Bojanic
package org.enhydra.shark.xpdl;

import java.io.File;

/**
 * Paths are always maintained in canonicalized form.  That is, parent
 * references (i.e., <code>../../</code>) and duplicate separators are
 * resolved.  For example,
 * <pre>     new Path("/a/b").append("../foo/bar")</pre>
 * will yield the path
 * <pre>     /a/foo/bar</pre>
 * <p>
 * This class is not intended to be subclassed by clients but
 * may be instantiated.
 * </p>
 */
public class Path {
   /**
    * Path separator character constant "/" used in paths.
    */
   public static final char SEPARATOR = '/';

   /**
    * Device separator character constant ":" used in paths.
    */
   public static final char DEVICE_SEPARATOR = ':';

   /** The path segments */
   private String[] segments;

   /** The device id string. May be null if there is no device. */
   private String device = null;

   /** flags indicating separators (has leading, is UNC, has trailing) */
   private int separators;

   /** masks for separator values */
   private static final int HAS_LEADING = 1;
   private static final int IS_UNC = 2;
   private static final int HAS_TRAILING = 4;
   private static final int ALL_SEPARATORS = HAS_LEADING | IS_UNC | HAS_TRAILING;

   /** Mask for all bits that are involved in the hashcode */
   private static final int HASH_MASK = ~HAS_TRAILING;

   /** Constant value indicating no segments */
   private static final String[] NO_SEGMENTS = new String[0];

   /** Constant root path string (<code>"/"</code>). */
   private static final String ROOT_STRING = "/"; //$NON-NLS-1$

   /** Constant value containing the root path with no device. */
   public static final Path ROOT = new Path(ROOT_STRING);

   /** Constant empty string value. */
   private static final String EMPTY_STRING = ""; //$NON-NLS-1$

   /** Constant value containing the empty path with no device. */
   public static final Path EMPTY = new Path(EMPTY_STRING);

   //Private implementation note: the segments and separators
   //arrays are never modified, so that they can be shared between
   //path instances
   private Path(String device, String[] segments, int _separators) {
      // no segment validations are done for performance reasons
      this.segments = segments;
      this.device = device;
      //hashcode is cached in all but the bottom three bits of the separators field
      this.separators = (computeHashCode() << 3) | (_separators & ALL_SEPARATORS);
   }

   /**
    * Constructs a new path from the given string path.
    * The given string path must be valid.
    * The path is canonicalized and double slashes are removed
    * except at the beginning. (to handle UNC paths) All backslashes ('\')
    * are replaced with forward slashes. ('/')
    *
    * @param fullPath the string path
    */
   public Path(String fullPath) {
      // no segment validations are done for performance reasons
      initialize(null, fullPath);
   }

   /**
    * Constructs a new path from the given device id and string path.
    * The given string path must be valid.
    * The path is canonicalized and double slashes are removed except
    * at the beginning (to handle UNC paths). All backslashes ('\')
    * are replaced with forward slashes. ('/')
    *
    * @param device the device id
    * @param path the string path
    * @see #setDevice
    */
   public Path(String device, String path) {
      // no segment validations are done for performance reasons
      initialize(device, path);
   }

   /**
    * Destructively converts this path to its canonical form.
    * <p>
    * In its canonical form, a path does not have any
    * "." segments, and parent references ("..") are collapsed
    * where possible.
    * </p>
    * @return true if the path was modified, and false otherwise.
    */
   private boolean canonicalize() {
      //look for segments that need canonicalizing
      for (int i = 0, max = segments.length; i < max; i++) {
         String segment = segments[i];
         if (segment.charAt(0) == '.' && (segment.equals("..") || segment.equals("."))) { //$NON-NLS-1$ //$NON-NLS-2$
            //path needs to be canonicalized
            collapseParentReferences();
            //paths of length 0 have no trailing separator
            if (segments.length == 0)
               separators &= (HAS_LEADING | IS_UNC);
            //recompute hash because canonicalize affects hash
            separators = (separators & ALL_SEPARATORS) | (computeHashCode() << 3);
            return true;
         }
      }
      return false;
   }

   /**
    * Destructively removes all occurrences of ".." segments from this path.
    */
   private void collapseParentReferences() {
      int segmentCount = segments.length;
      String[] stack = new String[segmentCount];
      int stackPointer = 0;
      for (int i = 0; i < segmentCount; i++) {
         String segment = segments[i];
         if (segment.equals("..")) { //$NON-NLS-1$
            if (stackPointer==0) {
               // if the stack is empty we are going out of our scope
               // so we need to accumulate segments.  But only if the original
               // path is relative.  If it is absolute then we can't go any higher than
               // root so simply toss the .. references.
               if (!isAbsolute())
                  stack[stackPointer++] = segment;//stack push
            } else {
               // if the top is '..' then we are accumulating segments so don't pop
               if ("..".equals(stack[stackPointer-1])) //$NON-NLS-1$
                  stack[stackPointer++] = ".."; //$NON-NLS-1$
               else
                  stackPointer--;//stack pop
            }
            //collapse current references
         } else
            if (!segment.equals(".") || (i == 0 && !isAbsolute())) //$NON-NLS-1$
               stack[stackPointer++] = segment;//stack push
      }
      //if the number of segments hasn't changed, then no modification needed
      if (stackPointer== segmentCount)
         return;
      //build the new segment array backwards by popping the stack
      String[] newSegments = new String[stackPointer];
      System.arraycopy(stack, 0, newSegments, 0, stackPointer);
      this.segments = newSegments;
   }

   /**
    * Removes duplicate slashes from the given path, with the exception
    * of leading double slash which represents a UNC path.
    */
   private String collapseSlashes(String path) {
      int length = path.length();
      // if the path is only 0, 1 or 2 chars long then it could not possibly have illegal
      // duplicate slashes.
      if (length < 3)
         return path;
      // check for an occurence of // in the path.  Start at index 1 to ensure we skip leading UNC //
      // If there are no // then there is nothing to collapse so just return.
      if (path.indexOf("//", 1) == -1) //$NON-NLS-1$
         return path;
      // We found an occurence of // in the path so do the slow collapse.
      char[] result = new char[path.length()];
      int count = 0;
      boolean hasPrevious = false;
      char[] characters = path.toCharArray();
      for (int index = 0; index < characters.length; index++) {
         char c = characters[index];
         if (c == SEPARATOR) {
            if (hasPrevious) {
               // skip double slashes, except for beginning of UNC.
               // note that a UNC path can't have a device.
               if (device == null && index == 1) {
                  result[count] = c;
                  count++;
               }
            } else {
               hasPrevious = true;
               result[count] = c;
               count++;
            }
         } else {
            hasPrevious = false;
            result[count] = c;
            count++;
         }
      }
      return new String(result, 0, count);
   }

   /*
   * Computes the hash code for this object.
   */
   private int computeHashCode() {
      int hash = device == null ? 17 : device.hashCode();
      int segmentCount = segments.length;
      for (int i = 0; i < segmentCount; i++) {
         //this function tends to given a fairly even distribution
         hash = hash * 37 + segments[i].hashCode();
      }
      return hash;
   }

   /*
   * Returns the size of the string that will be created by toString or toOSString.
   */
   private int computeLength() {
      int length = 0;
      if (device != null)
         length += device.length();
      if ((separators & HAS_LEADING) != 0)
         length ++;
      if ((separators & IS_UNC) != 0)
         length++;
      //add the segment lengths
      int max = segments.length;
      if (max > 0) {
         for (int i = 0; i < max; i++) {
            length += segments[i].length();
         }
         //add the separator lengths
         length += max-1;
      }
      if ((separators & HAS_TRAILING) != 0)
         length++;
      return length;
   }

   /*
   * Returns the number of segments in the given path
   */
   private int computeSegmentCount(String path) {
      int len = path.length();
      if (len == 0 || (len == 1 && path.charAt(0) == SEPARATOR)) {
         return 0;
      }
      int count = 1;
      int prev = -1;
      int i;
      while ((i = path.indexOf(SEPARATOR, prev + 1)) != -1) {
         if (i != prev + 1 && i != len) {
            ++count;
         }
         prev = i;
      }
      if (path.charAt(len - 1) == SEPARATOR) {
         --count;
      }
      return count;
   }

   /**
    * Computes the segment array for the given canonicalized path.
    */
   private String[] computeSegments(String path) {
      // performance sensitive --- avoid creating garbage
      int segmentCount = computeSegmentCount(path);
      if (segmentCount == 0)
         return NO_SEGMENTS;
      String[] newSegments = new String[segmentCount];
      int len = path.length();
      // check for initial slash
      int firstPosition = (path.charAt(0) == SEPARATOR) ? 1 : 0;
      // check for UNC
      if (firstPosition == 1 && len > 1 && (path.charAt(1) == SEPARATOR))
         firstPosition = 2;
      int lastPosition = (path.charAt(len - 1) != SEPARATOR) ? len - 1 : len - 2;
      // for non-empty paths, the number of segments is
      // the number of slashes plus 1, ignoring any leading
      // and trailing slashes
      int next = firstPosition;
      for (int i = 0; i < segmentCount; i++) {
         int start = next;
         int end = path.indexOf(SEPARATOR, next);
         if (end == -1) {
            newSegments[i] = path.substring(start, lastPosition + 1);
         } else {
            newSegments[i] = path.substring(start, end);
         }
         next = end + 1;
      }
      return newSegments;
   }


   /*
   * Initialize the current path with the given string.
   */
   private void initialize(String device, String fullPath) {
      if (fullPath==null) throw new RuntimeException();
      this.device = device;

      //indexOf is much faster than replace
      String path = fullPath.indexOf('\\') == -1 ? fullPath : fullPath.replace('\\', SEPARATOR);

      int i = path.indexOf(DEVICE_SEPARATOR);
      if (i != -1) {
         // if the specified device is null then set it to
         // be whatever is defined in the path string
         if (device == null)
            this.device = path.substring(0, i + 1);
         path = path.substring(i + 1, path.length());
      }
      path = collapseSlashes(path);
      int len = path.length();

      //compute the separators array
      if (len < 2) {
         if (len == 1 && path.charAt(0) == SEPARATOR) {
            this.separators = HAS_LEADING;
         } else {
            this.separators = 0;
         }
      } else {
         boolean hasLeading = path.charAt(0) == SEPARATOR;
         boolean isUNC = hasLeading && path.charAt(1) == SEPARATOR;
         //UNC path of length two has no trailing separator
         boolean hasTrailing = !(isUNC && len == 2) && path.charAt(len-1) == SEPARATOR;
         separators = hasLeading ? HAS_LEADING : 0;
         if (isUNC) separators |= IS_UNC;
         if (hasTrailing) separators |= HAS_TRAILING;
      }
      //compute segments and ensure canonical form
      segments = computeSegments(path);
      if (!canonicalize()) {
         //compute hash now because canonicalize didn't need to do it
         separators = (separators & ALL_SEPARATORS) | (computeHashCode() << 3);
      }
   }

   public boolean isAbsolute() {
      //it's absolute if it has a leading separator
      return (separators & HAS_LEADING) != 0;
   }

   public int matchingFirstSegments(Path anotherPath) {
      if (anotherPath==null) throw new RuntimeException();
      int anotherPathLen = anotherPath.segmentCount();
      int max = Math.min(segments.length, anotherPathLen);
      int count = 0;
      for (int i = 0; i < max; i++) {
         if (!segments[i].equals(anotherPath.segment(i))) {
            return count;
         }
         count++;
      }
      return count;
   }

   public String segment(int index) {
      if (index >= segments.length)
         return null;
      return segments[index];
   }

   public int segmentCount() {
      return segments.length;
   }

   public String[] segments() {
      String[] segmentCopy = new String[segments.length];
      System.arraycopy(segments, 0, segmentCopy, 0, segments.length);
      return segmentCopy;
   }

   public Path setDevice(String value) {
      if (value != null) {
         //Assert.isTrue(value.indexOf(Path.DEVICE_SEPARATOR) == (value.length() - 1), "Last character should be the device separator"); //$NON-NLS-1$
         if (value.indexOf(Path.DEVICE_SEPARATOR) != (value.length() - 1)) throw new RuntimeException("Last character should be the device separator");
      }
      //return the reciever if the device is the same
      if (value == device || (value != null && value.equals(device)))
         return this;

      return new Path(value, segments, separators);
   }

   public String toOSString() {
      //Note that this method is identical to toString except
      //it uses the OS file separator instead of the path separator
      int resultSize = computeLength();
      if (resultSize <= 0)
         return EMPTY_STRING;
      char FILE_SEPARATOR = File.separatorChar;
      char[] result = new char[resultSize];
      int offset = 0;
      if (device != null) {
         int size = device.length();
         device.getChars(0, size, result, offset);
         offset += size;
      }
      if ((separators & HAS_LEADING) != 0)
         result[offset++] = FILE_SEPARATOR;
      if ((separators & IS_UNC) != 0)
         result[offset++] = FILE_SEPARATOR;
      int len = segments.length-1;
      if (len>=0) {
         //append all but the last segment, with separators
         for (int i = 0; i < len; i++) {
            int size = segments[i].length();
            segments[i].getChars(0, size, result, offset);
            offset += size;
            result[offset++] = FILE_SEPARATOR;
         }
         //append the last segment
         int size = segments[len].length();
         segments[len].getChars(0, size, result, offset);
         offset += size;
      }
      if ((separators & HAS_TRAILING) != 0)
         result[offset++] = FILE_SEPARATOR;
      return new String(result);
   }

   public static String getRelativePath(Path fullPath,Path fBasePath) {
      if (fBasePath == null || !hasSameDevice(fullPath, fBasePath)) {
         return fullPath.toOSString();
      }
      int matchingSegments= fBasePath.matchingFirstSegments(fullPath);
      StringBuffer res= new StringBuffer();
      int backSegments= fBasePath.segmentCount() - matchingSegments;
      while (backSegments > 0) {
         res.append(".."); //$NON-NLS-1$
         res.append(File.separatorChar);
         backSegments--;
      }
      int segCount= fullPath.segmentCount();
      for (int i= matchingSegments; i < segCount; i++) {
         if (i > matchingSegments) {
            res.append(File.separatorChar);
         }
         res.append(fullPath.segment(i));
      }
      return res.toString();
   }

   private static boolean hasSameDevice(Path p1, Path p2) {
      String dev= p1.device;
      if (dev == null) {
         return p2.device == null;
      }
      return dev.equals(p2.device);
   }

}
