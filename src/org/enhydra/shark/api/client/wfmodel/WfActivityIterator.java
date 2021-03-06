package org.enhydra.shark.api.client.wfmodel;


import org.enhydra.shark.api.client.wfbase.*;

import org.enhydra.shark.api.SharkTransaction;

/**
 OMG definition: The WfActivityIterator interface specializes the WfBase::BaseIterator interface
 and adds activity specific operations.
 The following attributes can be used in query expressions using: key, name,
 priority, description, state.
 <p>We extended OMG's interface by duplicating methods, and adding additional
 parameter that represents transaction. If you use methods without SharkTransaction
 parameter, the transaction will be implicitly created, and if you use it with
 SharkTransaction parameter you must obey to some rules explained in HowTo documentation.
 <p> Also, look at our implementation API documentation of this interface to see
 which additional attributes can be used in query expressions.
 */
public interface WfActivityIterator  extends BaseIterator {

   WfActivity get_next_object () throws BaseException;
   WfActivity get_next_object (SharkTransaction t) throws BaseException;

   WfActivity get_previous_object () throws BaseException;
   WfActivity get_previous_object (SharkTransaction t) throws BaseException;

   WfActivity[] get_next_n_sequence (int max_number) throws BaseException;
   WfActivity[] get_next_n_sequence (SharkTransaction t,int max_number) throws BaseException;

   WfActivity[] get_previous_n_sequence (int max_number) throws BaseException;
   WfActivity[] get_previous_n_sequence (SharkTransaction t,int max_number) throws BaseException;
}
