package org.enhydra.shark.api.client.wfmodel;

/**
 * Used to describe sets of states of various workflow objects:
 * <p>
 * not_running - Object is active and quiescent, but ready to execute.
 * <p>
 * running - The object is active and executing in the workflow.
 */
public class while_openType
{
   private        int __value;
   private static int __size = 2;
   private static while_openType[] __array = new while_openType [__size];

   public static final int _not_running = 0;
   public static final while_openType not_running = new while_openType(_not_running);
   public static final int _running = 1;
   public static final while_openType running = new while_openType(_running);

   public int value ()
   {
      return __value;
   }

   public static while_openType from_int (int value)
   {
      if (value >= 0 && value < __size)
         return __array[value];
      else
         throw new org.omg.CORBA.BAD_PARAM ();
   }

   protected while_openType (int value)
   {
      __value = value;
      __array[__value] = this;
   }
} // class while_openType
