package org.enhydra.shark.api.client.wfmodel;


import org.enhydra.shark.api.client.wfbase.*;


/**
 OMG definition: This interface specializes WfEventAudit by adding state change information. A state
 change event is signaled when a WfExecutionObject changes its state. This covers both
 state changes resulting from a change_state operation request and internal state
 changes triggered by the execution logic of a WfProcess (e.g., process completes
 successfully, activity is suspended because the containing process was suspended, etc.).
 <p>The event_type is processStateChanged or activityStateChanged.
 */
public interface WfStateEventAudit  extends WfEventAudit {

   /**
    The state of the execution object before the status change is recorded. The state is
    described using dot-notation. The old state is recorded for convenience here; it
    could be deduced by analyzing the history of the execution object. Recording of the
    old state is optional.
    */
   String old_state () throws BaseException;

   /**
    The state of the execution object after the state change is recorded. The state is
    described using dot-notation.
    */
   String new_state () throws BaseException;

} // interface WfStateEventAudit
