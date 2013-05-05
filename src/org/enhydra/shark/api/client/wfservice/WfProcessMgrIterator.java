package org.enhydra.shark.api.client.wfservice;


import org.enhydra.shark.api.client.wfmodel.*;

import org.enhydra.shark.api.client.wfbase.*;
import org.enhydra.shark.api.SharkTransaction;

/**
 The WfProcessMgrIterator interface is extension of OMG interface. It specializes
 the WfBase::BaseIterator interface and adds specific process manager operations.
 <p>If you use methods without SharkTransaction parameter, the transaction will be implicitly created, and if you use it with
 SharkTransaction parameter you must obey to some rules explained in HowTo documentation.
 <p> Look at our implementation API documentation of this interface to see
 which attributes can be used in query expressions.
 */
public interface WfProcessMgrIterator  extends BaseIterator {
   WfProcessMgr get_next_object () throws BaseException;
   WfProcessMgr get_next_object (SharkTransaction t) throws BaseException;

   WfProcessMgr get_previous_object () throws BaseException;
   WfProcessMgr get_previous_object (SharkTransaction t) throws BaseException;

   WfProcessMgr[] get_next_n_sequence (int max_number) throws BaseException;
   WfProcessMgr[] get_next_n_sequence (SharkTransaction t,int max_number) throws BaseException;

   WfProcessMgr[] get_previous_n_sequence (int max_number) throws BaseException;
   WfProcessMgr[] get_previous_n_sequence (SharkTransaction t,int max_number) throws BaseException;
} // interface WfProcessMgrIterator
