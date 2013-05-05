package org.enhydra.shark.api.internal.transaction;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.working.WfProcessInternal;
import org.enhydra.shark.api.internal.working.WfResourceInternal;

/**
 * Since Shark tends to be a transaction oriented, this is
 * the interface that the kernel uses to signal operations on the
 * transaction.
 *
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public interface SharkInternalTransaction extends SharkTransaction {

   public void addToTransaction (String procId,WfProcessInternal proc) throws RootException;

   public void addToTransaction (String resUname,WfResourceInternal res) throws RootException;

   public void removeProcess (String procId) throws RootException;

   public void removeResource (String resUname) throws RootException;

   public WfProcessInternal getProcess (String procId) throws RootException;

   public WfResourceInternal getResource (String resUname) throws RootException;

}

