/* WfEngineInteroperability.java */
package org.enhydra.shark.api.internal.interoperability;

import java.util.Map;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * WfEngineInteroperability
 * 
 * @author V.Puskas
 * @version 0.1
 */
public interface WfEngineInteroperability {
   /**
    * Used to configure component.
    */
   void configure(CallbackUtilities cus) throws RootException;

   /**
    * @param st transaction
    * @param remoteEngine containing uri of the remote service
    * @param processInstanceId id of the local process
    * @param workitemId assignment id of the local activity
    * @param parameters map of parameters to pass to the remote engine
    * @return remote instance uri
    * @throws InteroperabilityException thrown on failure to complete
    *            the task
    */
   String start(SharkTransaction st,
                String remoteEngine,
                String processInstanceId,
                String workitemId,
                boolean isSynchronized,
                Map parameters) throws InteroperabilityException;

   /**
    * @param st
    * @param remoteInstanceUri
    * @param processInstanceId
    * @param workitemId
    * @throws InteroperabilityException
    */
   void suspend(SharkTransaction st,
                String remoteInstanceUri,
                String processInstanceId,
                String workitemId) throws InteroperabilityException;

   /**
    * @param st
    * @param remoteInstanceUri
    * @param processInstanceId
    * @param workitemId
    * @throws InteroperabilityException
    */
   void resume(SharkTransaction st,
               String remoteInstanceUri,
               String processInstanceId,
               String workitemId) throws InteroperabilityException;

   /**
    * @param st
    * @param remoteInstanceUri
    * @param processInstanceId
    * @param workitemId
    * @throws InteroperabilityException
    */
   void terminate(SharkTransaction st,
                  String remoteInstanceUri,
                  String processInstanceId,
                  String workitemId) throws InteroperabilityException;

   /**
    * @param st
    * @param remoteInstanceUri
    * @param processInstanceId
    * @param workitemId
    * @throws InteroperabilityException
    */
   void abort(SharkTransaction st,
              String remoteInstanceUri,
              String processInstanceId,
              String workitemId) throws InteroperabilityException;

   /**
    * @param st
    * @param processInstanceId
    * @param workitemId
    * @param parameters
    * @return @throws BaseException
    * @throws InteroperabilityException
    */
   Map parseOutParams(SharkTransaction st,
                      String processInstanceId,
                      String workitemId,
                      Map parameters,
                      Map cSig) throws InteroperabilityException;
}