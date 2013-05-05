package org.enhydra.shark.api.client.wfservice;


import org.enhydra.shark.api.client.wfmodel.*;

import org.enhydra.shark.api.client.wfbase.*;
import org.enhydra.shark.api.SharkTransaction;

/**
 The WfResourceIterator interface is extension of OMG interface. It specializes
 the WfBase::BaseIterator interface and adds specific resource operations.
 <p>If you use methods without SharkTransaction parameter, the transaction will be implicitly created, and if you use it with
 SharkTransaction parameter you must obey to some rules explained in HowTo documentation.
 <p> Look at our implementation API documentation of this interface to see
 which attributes can be used in query expressions.
 */
public interface WfResourceIterator  extends BaseIterator {
   WfResource get_next_object () throws BaseException;
   WfResource get_next_object (SharkTransaction t) throws BaseException;

   WfResource get_previous_object () throws BaseException;
   WfResource get_previous_object (SharkTransaction t) throws BaseException;

   WfResource[] get_next_n_sequence (int max_number) throws BaseException;
   WfResource[] get_next_n_sequence (SharkTransaction t,int max_number) throws BaseException;

   WfResource[] get_previous_n_sequence (int max_number) throws BaseException;
   WfResource[] get_previous_n_sequence (SharkTransaction t,int max_number) throws BaseException;
} // interface WfResourceIterator
