package org.enhydra.shark.api.internal.working;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.TransactionException;
/**
 * Interface that all engine objects that need to be persisted have to implement.
 * @author Sasa Bojanic
 * @version 1.0
 */
public interface PersistenceInterface {
   public void persist(SharkTransaction t) throws TransactionException;

   public void delete(SharkTransaction t) throws TransactionException;

}
