package org.enhydra.shark.api.client.wfmodel;

/**
 * Used to describe sets of states of various workflow objects:
 * <p>
 * completed - When an execution object has finished its task in the
 * overall workflow process it enters the completed state; it
 * is assumed that all execution objects associated with that
 * execution object are completed when it enters this state.
 * <p>
 * terminated - Indicates that enactment of the execution object was
 * stopped before normal completion. It is assumed that all
 * execution objects depending on this execution object
 * (i.e., WfActivities contained in a WfProcess or a
 * WfProcess implementing a WfActivity) are either
 * completed or are terminated when it enters this state.
 * <p>
 * aborted - Indicates that the enactment of the execution object has
 * been aborted before normal completion. No assumptions
 * on the state of execution objects depending on this
 * execution object are made when it enters this state.
 */
public class how_closedType
{
  private        int __value;
  private static int __size = 3;
  private static how_closedType[] __array = new how_closedType [__size];

  public static final int _completed = 0;
  public static final how_closedType completed = new how_closedType(_completed);
  public static final int _terminated = 1;
  public static final how_closedType terminated = new how_closedType(_terminated);
  public static final int _aborted = 2;
  public static final how_closedType aborted = new how_closedType(_aborted);

  public int value ()
  {
    return __value;
  }

  public static how_closedType from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected how_closedType (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class how_closedType
