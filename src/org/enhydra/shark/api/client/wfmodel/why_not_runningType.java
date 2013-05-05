package org.enhydra.shark.api.client.wfmodel;


/**
 * Used to describe sets of states of various workflow objects:
 * <p>
 * not_started - Provides a state after creation where the object is active
 * and ready to be initialized and started.
 * <p>
 * suspended - Provides a state to temporarily pause the execution of
 * the object. When an execution object is suspended, no
 * execution objects depending on this object may be
 * started.
 */
public class why_not_runningType
{
  private        int __value;
  private static int __size = 2;
  private static why_not_runningType[] __array = new why_not_runningType [__size];

  public static final int _not_started = 0;
  public static final why_not_runningType not_started = new why_not_runningType(_not_started);
  public static final int _suspended = 1;
  public static final why_not_runningType suspended = new why_not_runningType(_suspended);

  public int value ()
  {
    return __value;
  }

  public static why_not_runningType from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected why_not_runningType (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class why_not_runningType
