package org.enhydra.shark.api.internal.toolagent;


import org.enhydra.shark.api.*;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * This interface is based on WfMC's definition of ToolAgents. It is pretty
 * close to the WfMC's specification, but expressed in Object oriented way.
 * <p>The following is different then WfMC spec proposes:
 * <ul>
 * <li> Every method is throwing additional {@link ToolAgentGeneralException}
 * <li> Every method has an additional SharkTransaction parameter, which
 * represents the shark's current transaction, and provides a way for tool
 * agents to access the same database as shark does by using shark's current
 * transaction.
 * <li> {@link AppParameter} class is different then WfMC's WMTPAttribute type -
 * it defines some additional fields.
 * <li> It has additional configure() method, that is used to pass
 * {@link CallbackUtilities} to tool agent, which provides ToolAgent to get
 * some configuration information from Shark, and provides a way to log tool
 * agent events.
 * <li> It has additional getInfo() method, that is used to get some information
 * about tool agent.
 * </ul>
 * <p> Also, when calling invokeApplication() method, as the first {@link AppParameter}
 * parameter in array, Shark is always passing a string representing
 * ExtendedAttributes section of the corresponding XPDL application, chopped
 * out from the XPDL definition, i.e.:
 * <pre>
 * &lt;ExtendedAttributes&gt;
 *    &lt;ExtendedAttribute Name="ToolAgentClass" Value="org.enhydra.shark.toolagent.JavaScriptToolAgent"/&gt;
 *    &lt;ExtendedAttribute Name="Script" Value="java.lang.Thread.sleep(wait_ms);"/&gt;
 * &lt;/ExtendedAttributes&gt;
 * </pre>
 * <p> Shark uses tool agents like the following:
 * <ul>
 * <li> It aquires ToolAgent instance from {@link ToolAgentFactory}. When it
 * creates ToolAgent instance, {@link ToolAgentFactory} implementation has to
 * call ToolAgent's configure() method.
 * <li> It calls connect() method, and gets handle to ToolAgent
 * <li> It calls invokeApplication() method (the {@link AppParameter} array
 * passed to this method should be considered to be passed as a value, not as
 * a reference, because Shark doesn't care about it after calling this method).
 * <li> It calls requestAppStatus() method. Tool agent implementation
 * has to fill the {@link AppParameter} array that Shark passes to this method,
 * with the values of appropriate parameters (These parameters are the one
 * passed in invokeApplication() method call, that could be changed
 * during tool agent execution). It also expects the information on application
 * status to be returned, and in our standard implementation, if the status is
 * equal to -1, shark throws exception (Of course, ToolAgent can also signal an
 * exception to shark by throwing any of defined exceptions for a method).
 * <li> finally, shark calls disconnect().
 * </ul>
 *
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public interface ToolAgent {
   /**
    * Used to configure tool agent if needed.
    */
   void configure (CallbackUtilities cus) throws RootException;

   /**
    * This is the first method that shark calls when accessing some tool agent.
    *
    * @param    t                   Current shark transaction.
    * @param    userId              Applications executed by toolagent might
    * require login procedures, therefore authentication should be passed to
    * a Tool Agent to provide single-login mechanisms.
    * @param    password            Applications executed by toolagent might
    * require login procedures, therefore authentication should be passed to
    * a Tool Agent to provide single-login mechanisms.
    * @param    engineName          The name of shark instance that connects to
    * Tool agent.
    * @param    scope               Identification of scope for the application.
    * If scope is not relevant, then this field would be empty and ignored.
    *
    * @return   a SessionHandle.
    *
    * @throws   ConnectFailed       If application executed by tool agent needs
    * authentication, and userId and/or password are not valid
    * @throws   ToolAgentGeneralException If something unexpected happens.
    *
    */
   SessionHandle connect (SharkTransaction t,
                          String userId,
                          String password,
                          String engineName,
                          String scope)
      throws ConnectFailed, ToolAgentGeneralException;

   /**
    * Method disconnect.
    *
    * @param    t                   Current shark transaction.
    * @param    shandle             a  SessionHandle.
    *
    * @throws   InvalidSessionHandle      If the given session handle is not valid.
    * @throws   ToolAgentGeneralException If something unexpected happens.
    *
    */
   void disconnect (SharkTransaction t,SessionHandle shandle)
      throws InvalidSessionHandle, ToolAgentGeneralException;

   /**
    * Executes tool agent application.
    *
    * @param    t                   Current shark transaction.
    * @param    handle              a  long representing unique session Id.
    * @param    applicationName     the name of application which will be
    * executed by this tool agent
    * @param    processInstanceId   Id of process instance for which tool
    * agent application is called.
    * @param    workitemId          Id of assignment that is associated with
    * invoked application.
    * @param    parameters          array of parameters (engine variables)
    * passed to tool agent application. Some of these parameters will be changed
    * during application execution. These parameters should be considered the
    * input ones - the engine will not read there value after this method is
    * finished, but it will give to the {@link #requestAppStatus} method
    * the array of parameters to be filled with the result of tool agent
    * application execution.
    * <p> The value field of the first parameter in the parameter array
    * is always a string representing ExtendedAttributes section of the
    * corresponding XPDL application, chopped out from the XPDL definition, i.e.:
    * <pre>
    * &lt;ExtendedAttributes&gt;
    *    &lt;ExtendedAttribute Name="ToolAgentClass" Value="org.enhydra.shark.toolagent.JavaScriptToolAgent"/&gt;
    *    &lt;ExtendedAttribute Name="Script" Value="java.lang.Thread.sleep(wait_ms);"/&gt;
    * &lt;/ExtendedAttributes&gt;
    * </pre>
    * @param    applicationMode        an Integer representing application mode.
    *
    * @throws   ApplicationNotStarted  If application can't be started.
    * @throws   ApplicationNotDefined  If there is no such application.
    * @throws   ApplicationBusy        If application is bussy.
    * @throws   ToolAgentGeneralException If something unexpected happens.
    *
    */
   void invokeApplication (SharkTransaction t,
                           long handle,
                           String applicationName,
                           String processInstanceId,
                           String workitemId,
                           AppParameter[] parameters,
                           Integer applicationMode)
      throws ApplicationNotStarted, ApplicationNotDefined,
      ApplicationBusy, ToolAgentGeneralException;

   /**
    * Returns the status of tool agent application execution, and fills
    * the parameters with the results of tool agent application execution.
    *
    * @param    t                   Current shark transaction.
    * @param    handle              a  long representing unique session Id.
    * @param    processInstanceId   Id of process instance for which tool
    * agent application is called.
    * @param    workitemId          Id of assignment that is associated with
    * invoked application.
    * @param    parameters          array of parameters (engine variables)
    * passed to application. This is a subset of parameters passed to the
    * invokeApplication() method, and these parameters need to be filled with
    * the result of tool agent's application execution.
    *
    * @return   The status of tool agent application.
    *
    * @throws   ApplicationBusy        If application is bussy.
    * @throws   InvalidToolAgentHandle If handle is invalid.
    * @throws   InvalidWorkitem        If there is no such workitem as the one
    * represented by given Id parameters.
    * @throws   InvalidProcessInstance If there is no process instance with
    * given Id.
    * @throws   ToolAgentGeneralException If something unexpected happens.
    *
    */
   long requestAppStatus (SharkTransaction t,
                          long handle,
                          String processInstanceId,
                          String workitemId,
                          AppParameter[] parameters)
      throws ApplicationBusy, InvalidToolAgentHandle, InvalidWorkitem,
      InvalidProcessInstance, ToolAgentGeneralException;

   /**
    * Terminates tool agent application.
    *
    * @param    t                   Current shark transaction.
    * @param    handle              long representing unique session Id.
    * @param    processInstanceId   Id of process instance for which tool
    * agent application is called.
    * @param    workitemId          Id of assignment that is associated with
    * invoked application.
    *
    * @throws   ApplicationNotStopped  If application can't be stopped.
    * @throws   InvalidWorkitem        If there is no such workitem as the one
    * represented by given Id parameters.
    * @throws   InvalidProcessInstance If there is no process instance with
    * given Id.
    * @throws   ApplicationBusy        If application is bussy.
    * @throws   ToolAgentGeneralException If something unexpected happens.
    *
    */
   void terminateApp (SharkTransaction t,
                      long handle,
                      String processInstanceId,
                      String workitemId)
      throws ApplicationNotStopped, InvalidWorkitem,
      InvalidProcessInstance, ApplicationBusy, ToolAgentGeneralException;


   /**
    * Method getInfo.
    *
    * @param    t                   a  SharkTransaction
    *
    * @return   a String representing brief description on tool agent use.
    *
    * @throws   ToolAgentGeneralException
    *
    */
   String getInfo (SharkTransaction t) throws ToolAgentGeneralException;

}
