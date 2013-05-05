
package org.enhydra.shark.api.internal.appmappersistence;

import java.util.List;

import org.enhydra.shark.api.ApplicationMappingTransaction;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * Interface represents mappings between applications and tool agents.
 * @author Zoran Milakovic
 */
public interface ApplicationMappingManager
{
   void configure (CallbackUtilities cus) throws RootException;

   public boolean saveApplicationMapping( ApplicationMappingTransaction trans, ApplicationMap am ) throws RootException;

   public boolean deleteApplicationMapping( ApplicationMappingTransaction trans, ApplicationMap am ) throws RootException;

   public List getAllApplicationMappings( ApplicationMappingTransaction trans ) throws RootException;

   public ApplicationMap createApplicationMap ();

   public boolean deleteApplicationMapping(
      ApplicationMappingTransaction trans,
      String packageId,
      String processDefinitionId,
      String applicationId ) throws RootException;

   public ApplicationMap getApplicationMap (
      ApplicationMappingTransaction trans,
      String pkgId,
      String pDefId,
      String appDefId) throws RootException;

   public ApplicationMappingTransaction getApplicationMappingTransaction() throws TransactionException;
}

