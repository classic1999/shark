package org.enhydra.shark.api.client.wfmodel;

/**
 * Used to describe sets of states of WfProcessMgr object.
 * A WfProcessMgr can be enabled or disabled.
 * <p>
 * enabled - Indicates that creation of workflow processes is enabled.
 * <p>
 * disabled - Indicates that creation of workflow processes is disabled.
 */
public class process_mgr_stateType
{
   private        int __value;
   private static int __size = 2;
   private static process_mgr_stateType[] __array = new process_mgr_stateType [__size];

   public static final int _enabled = 0;
   public static final process_mgr_stateType enabled = new process_mgr_stateType(_enabled);
   public static final int _disabled = 1;
   public static final process_mgr_stateType disabled = new process_mgr_stateType(_disabled);

   public int value ()
   {
      return __value;
   }

   public static process_mgr_stateType from_int (int value)
   {
      if (value >= 0 && value < __size)
         return __array[value];
      else
         throw new org.omg.CORBA.BAD_PARAM ();
   }

   protected process_mgr_stateType (int value)
   {
      __value = value;
      __array[__value] = this;
   }
} // class process_mgr_stateType
