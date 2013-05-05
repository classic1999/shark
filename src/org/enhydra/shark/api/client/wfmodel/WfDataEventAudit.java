package org.enhydra.shark.api.client.wfmodel;


import org.enhydra.shark.api.client.wfbase.*;
import java.util.Map;

/**
 OMG definition: This interface specializes WfEventAudit for data change events. A data change event is
 signaled when the context of a WfExecutionObject or the result of a WfActivity is
 initialized or changed. The event_type is processContextChanged,
 activityContextChanged, or activityResultChanged.
 */
public interface WfDataEventAudit  extends WfEventAudit {

   /**
    Records the context resp. result data of the execution object before the change; only the
    data items that were changed are reported. This event also records the initialization of
    the context of a WfProcess resp. of the result of a WfActivity; in these cases, old_data
    is NULL.
    <p>The old data are recorded for convenience here; they could be deduced by analyzing
    the history of the execution object. Support for recording of old data is optional.
    */
   Map old_data () throws BaseException;

   /**
    Records the context resp. result data of the execution object after the change; only the
    data items that were changed are reported. This event also records the initialization of
    the context of a WfProcess resp. of the result of a WfActivity; in these cases, new_data
    contains the initial data.
    */
   Map new_data () throws BaseException;
} // interface WfDataEventAudit
