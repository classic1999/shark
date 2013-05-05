package org.enhydra.shark.api.client.wfmodel;

/**
 * Used to describe sets of states of various workflow objects.
 * An execution object is either in state ?open? (i.e., it is active)
 * or in state ?closed? ( i.e., it has finished execution).
 * <p>
 * open - To reflect that the object is active and not finished.
 * closed - Reflects that the object is finished and inactive.
 */
public class workflow_stateType
{
   private        int __value;
   private static int __size = 2;
   private static workflow_stateType[] __array = new workflow_stateType [__size];

   public static final int _open = 0;
   public static final workflow_stateType open = new workflow_stateType(_open);
   public static final int _closed = 1;
   public static final workflow_stateType closed = new workflow_stateType(_closed);

   public int value ()
   {
      return __value;
   }

   public static workflow_stateType from_int (int value)
   {
      if (value >= 0 && value < __size)
         return __array[value];
      else
         throw new org.omg.CORBA.BAD_PARAM ();
   }

   protected workflow_stateType (int value)
   {
      __value = value;
      __array[__value] = this;
   }
} // class workflow_stateType
