package org.enhydra.shark.toolagent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.enhydra.shark.Shark;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfmodel.WfActivity;
import org.enhydra.shark.api.client.wfservice.AdminInterface;
import org.enhydra.shark.api.client.wfservice.ExecutionAdministration;
import org.enhydra.shark.api.internal.toolagent.AppParameter;
import org.enhydra.shark.api.internal.toolagent.ApplicationBusy;
import org.enhydra.shark.api.internal.toolagent.ApplicationNotDefined;
import org.enhydra.shark.api.internal.toolagent.ApplicationNotStarted;
import org.enhydra.shark.api.internal.toolagent.SessionHandle;
import org.enhydra.shark.api.internal.toolagent.ToolAgent;
import org.enhydra.shark.api.internal.toolagent.ToolAgentGeneralException;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.xpdl.XPDLConstants;
import org.enhydra.shark.xpdl.elements.ExtendedAttribute;
import org.enhydra.shark.xpdl.elements.ExtendedAttributes;

import EDU.oswego.cs.dl.util.concurrent.LinkedQueue;
import EDU.oswego.cs.dl.util.concurrent.PooledExecutor;

/**
 * <P>
 * Tool agent class to schedule a ToolAgent call in
 * separate pool of threads
 * </P>
 * @author Abe Achkinaz, BDNACorp.com
 */
public class SchedulerToolAgent extends AbstractToolAgent {

   private final static String TOOL_AGENT_CLASS_EXT_ATTR_NAME="ToolAgentClass";
   private final static String TOOL_AGENT_CLASS_PROXY_EXT_ATTR_NAME="ToolAgentClassProxy";

   /*
    * Helper methods for logging
    */
   private void info(String infoString) {
      if (null != cus) {
         cus.info(infoString);
      }
   }
   private void error(String infoString) {
      if (null != cus) {
         cus.error(infoString);
      }
   }

   public String getInfo (SharkTransaction t) throws ToolAgentGeneralException {
      String i=
         "Wraps a ToolAgent standard call and executes them a separate"+
         "\nin thread-pool."+
         "\n"+
         "\nTo use define an ToolAgentClass extended attribute to the scheduler"+
         "\nanother extended attribute 'ToolAgentClassProxy' to the actual"+
         "\napplication to be called in a separet thread. For example: "+
         "\n<ExtendedAttributes>"+
         "\n  <ExtendedAttribute Name=\"ToolAgentClass\" Value=\"org.enhydra.shark.toolagent.SchedulerToolAgent\"/>"+
         "\n  <ExtendedAttribute Name=\"ToolAgentClassProxy\" Value=\"org.enhydra.shark.toolagent.BshToolAgent\"/>"+
         "\n  <ExtendedAttribute Name=\"Script\" Value=\"System.out.println(\"I was called...\");\"/>"+
         "\n</ExtendedAttributes>"+
         "\n"+
         "\nTo be able to work with SchedulerToolAgent, you must define some "+
         "\nproperties, and here is a section from shark's configuration file \"Shark.conf\" "+
         "\nthat defines these properties:"+
         "\n# Configure number of threads to execute commands and admin user/password: "+
         "\nSchedulerToolAgent.threadPoolSize=3"+
         "\nSchedulerToolAgent.sharkUsername=admin"+
         "\nSchedulerToolAgent.sharkPassword=enhydra"+
         "\n"
         ;
      return i;
   }

   public void invokeApplication(SharkTransaction t, long handle,
                                 String applicationName, String procInstId, String assId,
                                 AppParameter[] parameters, Integer appMode)
      throws ApplicationNotStarted, ApplicationNotDefined,
      ApplicationBusy, ToolAgentGeneralException {

      super.invokeApplication(t, handle, applicationName, procInstId, assId,
                              parameters, appMode);
      try {
         /*
          * Get proxy class name and replace in parameter[0]:
          */
         String extAttribs=(String)parameters[0].the_value;
         ExtendedAttributes eas = readParamsFromExtAttributes(extAttribs);
         ExtendedAttribute eaScheduler=eas.getFirstExtendedAttributeForName(TOOL_AGENT_CLASS_EXT_ATTR_NAME);
         String schedulerClassName = eaScheduler.getVValue();
         ExtendedAttribute eaProxy=eas.getFirstExtendedAttributeForName(TOOL_AGENT_CLASS_PROXY_EXT_ATTR_NAME);
         String proxyClassName = eaProxy.getVValue();
         int idxSchedulerClassName = extAttribs.indexOf(schedulerClassName);
         String newExtAttribs = extAttribs.substring(0,idxSchedulerClassName)
            + proxyClassName
            + extAttribs.substring(idxSchedulerClassName+schedulerClassName.length());
         parameters[0].the_value = newExtAttribs;
         readParamsFromExtAttributes(newExtAttribs);

         Class cls=Class.forName(proxyClassName);
         ToolAgent ta=(ToolAgent)cls.newInstance();

         /*
          * Get shark's username and password to use to report back to engine
          */
         String sharkUsername = cus.getProperty("SchedulerToolAgent.sharkUsername", "admin");
         String sharkPassword = cus.getProperty("SchedulerToolAgent.sharkPassword", "enhydra");

         ta.configure(cus);

         ToolAgentCmdProxy taCmdProxy = new ToolAgentCmdProxy(
            cus, ta, proxyClassName,
            username, password, engineName, scope,
            handle, applicationName, procInstId, assId, parameters,
            appMode);

         SingletonPooledExecutor.getInstance(cus).execute(taCmdProxy);

         /*
          * Finished spawning the command, returned finished to engine
          * otherwise it throws an exception!
          */
         status = AbstractToolAgent.APP_STATUS_FINISHED;
      } catch (Throwable ex) {
         error("SchedulerToolAgent terminated incorrectly: " + ex);
         status = AbstractToolAgent.APP_STATUS_INVALID;
         throw new ToolAgentGeneralException(ex);
      }
   }
}

/*
 * <P>
 * Wrap ToolAgent interface into runnable class used
 * by SinglePooledExec
 * </P>
 * @author aachkinazi, BDNACorp.com
 */
class ToolAgentCmdProxy implements Runnable {
   CallbackUtilities   m_cus;
   String              m_taName;
   ToolAgent           m_ta;
   String              m_username;
   String              m_password;
   String              m_engineName;
   String              m_scope;
   long                m_handle;
   String              m_applicationName;
   String              m_procInstId;
   String              m_assId;
   AppParameter[]      m_parameters;
   AppParameter[]      m_proxyParameters;
   Integer             m_appMode;

   ToolAgentCmdProxy(CallbackUtilities cus,
                     ToolAgent ta, String taName,
                     String username, String password,
                     String engineName, String scope,
                     long handle,
                     String applicationName, String procInstId, String assId,
                     AppParameter[] parameters, Integer appMode) {
      m_cus = cus;
      m_ta = ta;
      m_taName = taName;
      m_username = username;
      m_password = password;
      m_engineName = engineName;
      m_scope = scope;
      m_handle = handle;
      m_applicationName = applicationName;
      m_procInstId = procInstId;
      m_assId = assId;
      m_parameters = parameters;
      m_appMode = appMode;
   }

   /*
    * Helper logging methods
    */
   private void info(String infoString) {
      if (null != m_cus) {
         m_cus.info(infoString);
      }
   }
   private void error(String infoString) {
      if (null != m_cus) {
         m_cus.error(infoString);
      }
   }

   /**
    * Return IN_OUT and OUT values to
    * the calling activity
    * @return
    */
   private Map getResults() throws Exception {
      /*
       * Build activity result map,
       */
      Map results = new HashMap();
      for (int i=0; i<m_parameters.length; ++i) {
         AppParameter p = m_parameters[i];
         if (p.the_mode.equals(XPDLConstants.FORMAL_PARAMETER_MODE_INOUT)
             || p.the_mode.equals(XPDLConstants.FORMAL_PARAMETER_MODE_OUT)) {
            results.put(p.the_actual_name,
                        convertToProperType(p.the_value, p.the_class));
         }
      }

      return results;
   }

   private Object convertToProperType(Object toConvert, Class desiredType)
      throws Exception {
      if (null == toConvert || desiredType.isInstance(toConvert))
         return toConvert;

      if (desiredType.equals(Integer.class)) {
         return new Integer((new Integer(toConvert.toString())).intValue());
      } else if (desiredType.equals(Long.class)) {
         return new Long((new Double(toConvert.toString())).longValue());
      } else if (desiredType.equals(Boolean.class)) {
         return new Boolean(toConvert.toString());
      } else if (desiredType.equals(Double.class)) {
         return new Double(toConvert.toString());
      } else if (desiredType.equals(java.util.Date.class)) {
         return new java.util.Date(toConvert.toString());
      }
      return toConvert;
   }

   /* (non-Javadoc)
    * @see java.lang.Runnable#run()
    */
   public void run() {
      Thread curThread = Thread.currentThread();
      String oldThreadName = curThread.getName();
      /*
       * Invoke query application and set state of activity in proxy
       * thread context:
       */
      long status = AbstractToolAgent.APP_STATUS_INVALID;

      SingletonPooledExecutor spe=null;
      try {
         spe = SingletonPooledExecutor.getInstance(m_cus);
      } catch (Exception _) {}
      if (null == spe) {
         error("Unable to get thread-pool!");
         return;
      }

      Shark shark = Shark.getInstance();
      if (null == shark) {
         error("Unable to get Shark engine instance!");
         spe.updateCompleteCount(this);
         return;
      }

      SharkTransaction trans = null;
      try {
         curThread.setName(oldThreadName+"->"+m_taName);
         SessionHandle taShandle;

         trans = shark.createTransaction();

         taShandle=m_ta.connect(trans, m_username,m_password,m_engineName,m_scope);

         /*
          * Use null for appName because ToolAgent implementations check for appName
          * being null to parse extended attributes and look for the name! This has
          * the side-effect of initializing other ToolAgent specific extended attributes
          * such as Script for BshToolAgent.
          */
         m_ta.invokeApplication(trans, taShandle.getHandle(), null,
                                m_procInstId, m_assId, m_parameters, m_appMode);

         status=m_ta.requestAppStatus(trans,taShandle.getHandle(),m_procInstId,
                                      m_assId,m_parameters);
         m_ta.disconnect(trans,taShandle);

         int finishCount = spe.updateCompleteCount(this);

         /*
          * Find activity and set results
          */
         AdminInterface adminInterface = shark.getAdminInterface();
         ExecutionAdministration execAdmin =
            adminInterface.getExecutionAdministration();
         execAdmin.connect(trans, spe.getSharkUsername(), spe.getSharkPassword(),
                           m_engineName, m_scope);
         String activityId = adminInterface.getAdminMisc()
            .getAssignmentActivityId(m_procInstId, m_assId);

         WfActivity wfActivity = execAdmin.getActivity(trans, m_procInstId, activityId);
         wfActivity.set_result(trans, getResults());

         if (0 == finishCount) {
            wfActivity.complete(trans);
         }

         execAdmin.disconnect(trans);
         trans.commit();
      } catch (Throwable ex) {
         error("SchedulerToolAgent -> applicationProxy " + m_taName
                  + " terminated incorrectly: " + ex);
         status = AbstractToolAgent.APP_STATUS_INVALID;
         try {
            shark.emptyCaches(trans);
            trans.rollback();
         } catch (Exception _) { /* left blank */ }
      } finally {
         try { shark.unlockProcesses(trans);} catch (Exception _){}
         try {trans.release(); } catch (Exception _) {}
         trans = null;
      }

      /*
       * Update activity with results and set complete state
       */
      curThread.setName(oldThreadName);
   }

   /**
    * Cancel an activity step that never got scheduled
    */
   public void cancel() {
      /*
       * There is no generic way to handle cancelling a pending
       * application that never got a thread to run. Do nothing for now!
       */
   }

   /**
    * @return
    */
   public Object getAssId() {
      return m_assId;
   }
}

/*
 * Define SingletonPoolExecutor to manager threads used by
 * SchedulerToolAgent and controlling wfActivityComplete.
 * @author aachkinazi, BDNACorp.com
 */
class SingletonPooledExecutor extends PooledExecutor {
   private static final Object classLock = SingletonPooledExecutor.class;

   /** Shutdown hook thread name. */
   private static final String         SHUTDOWN_HOOK_THREAD_NAME = "SingletonPooledExecShutdownHook";

   /**
    * Global shutdown flag to expedite shutdown process. There
    * is no need to synchronize access to this flag because the
    * shutdown sequence does not rely or coordinate on the state
    * of this flag.  It simply indicates that the shutdown
    * sequence has begun so the threads that happen to be in
    * a good state to do so can shutdown early.
    */
   private static boolean              s_shutdown = false;

   /** Thread to run as shutdown hook. */
   private static Thread               s_shutdownHook;

   /**
    * Sigleton pool of threads
    */
   private static SingletonPooledExecutor m_spe = null;

   private static CallbackUtilities m_cus = null;

   /**
    * Keep track of pending scheduled commands per assignment
    * only call WfActivity.complete() when the count reaches 0. This
    * handles the case where an activity has multiple SchedulerToolAgent
    * steps.
    * @see SingletonPooledExecutor#execute(ToolAgentCmdProxy)
    * @see SingletonPooledExecutor#completeCmd(ToolAgentCmdProxy)
    */
   private Map                         m_assIdToCount = new HashMap();

   /**
    * Use this name to connect to Shark engine
    */
   private String                      m_sharkUsername;

   /**
    * Use this password to connect to Shark engine
    */
   private String                      m_sharkPassword;

   /**
    * @param queue
    * @param threadPoolSize
    */
   private SingletonPooledExecutor(LinkedQueue queue, int threadPoolSize) {
      super(queue,threadPoolSize);
   }

   /**
    * @return
    */
   public String getSharkUsername() {
      return m_sharkUsername;
   }

   /**
    * @return
    */
   public String getSharkPassword() {
      return m_sharkPassword;
   }

   /*
    * Helper logging methods
    */
   private void info(String infoString) {
      if (null != m_cus) {
         m_cus.info(infoString);
      } else {
         System.out.println(infoString);
      }
   }
   private void error(String infoString) {
      if (null != m_cus) {
         m_cus.error(infoString);
      } else {
         System.out.println(infoString);
      }
   }

   private static void shutdown() {
      s_shutdown = true;
      if (null != m_spe) {
         m_spe.shutdownNow();
         try {
            final long      timeout = 2*1000L;
            if (!m_spe.awaitTerminationAfterShutdown(timeout)) {
               final int   remainingThreads = m_spe.getPoolSize();
               if (0!= remainingThreads) {
                  m_spe.info("Threads remaining during shutdown: "
                                +remainingThreads);
               }
            }

            List pendingTasks = m_spe.drain();
            for (Iterator itr = pendingTasks.iterator(); itr.hasNext(); ) {
               ToolAgentCmdProxy obj = (ToolAgentCmdProxy)itr.next();
               m_spe.info("Pending task: "+obj.toString());
               obj.cancel();
            }
         } catch (Exception ex) {
            m_spe.error("Exception during thread pool shutdown: "+ex.toString());
         }
         m_spe = null;
      }
   }

   public static SingletonPooledExecutor getInstance(CallbackUtilities cus) throws Exception {
      synchronized(classLock) {
         if (null == m_spe) {
            try {
               /*
                * Configure for the first time,
                */
               m_cus = cus;
               int threadPoolSize = 3;
               try {
                  String threadPoolSizeStr =
                     cus.getProperty("SchedulerToolAgent.threadPoolSize",Integer.toString(3));
                  threadPoolSize = Integer.parseInt(threadPoolSizeStr);
               } catch (Exception ex) {
                  // Left blank!
               }
               String sharkUsername = cus.getProperty("SchedulerToolAgent.sharkUsername", "admin");
               String sharkPassword = cus.getProperty("SchedulerToolAgent.sharkPassword", "enhydra");

               /*
                * Provide a shutdown hook to clean up thread pools
                */
               s_shutdownHook =
                  new Thread() {
                  public void run() {
                     SingletonPooledExecutor.shutdown();
                  }

               };
               s_shutdownHook.setName(
                  SingletonPooledExecutor.SHUTDOWN_HOOK_THREAD_NAME);
               Runtime.getRuntime().addShutdownHook(s_shutdownHook);

               m_spe = new SingletonPooledExecutor(new LinkedQueue(),threadPoolSize);
               m_spe.waitWhenBlocked();
               m_spe.createThreads(threadPoolSize);
               m_spe.m_sharkUsername = sharkUsername;
               m_spe.m_sharkPassword = sharkPassword;

            } catch (Exception ex) {
               String errorMsg = "Exception during thread pool init "+ex.toString();
               if (null != m_cus) {
                  m_cus.error(errorMsg);
               } else {
                  System.out.println(errorMsg);
               }
               if (null != s_shutdownHook) {
                  Runtime.getRuntime().removeShutdownHook(s_shutdownHook);
               }
               s_shutdownHook = null;
               if (null != m_spe) {
                  SingletonPooledExecutor.shutdown();
               }
               throw ex;
            }
         }
         return m_spe;
      }
   }


   /* (non-Javadoc)
    * @see EDU.oswego.cs.dl.util.concurrent.Executor#execute(java.lang.Runnable)
    */
   public void execute(ToolAgentCmdProxy cmdProxy) throws InterruptedException {
      synchronized (m_assIdToCount) {
         /*
          * Keep track of outstanding SchedulerToolAgent calls
          * per assId/Activity.
          */
         Object assId = cmdProxy.getAssId();
         Integer curCnt = null == assId ? null
            : (Integer)m_assIdToCount.get(assId);
         int assIdCnt = null == curCnt ? 1 : curCnt.intValue()+1;
         m_assIdToCount.put(assId, new Integer(assIdCnt));
      }
      super.execute(cmdProxy);
   }

   /**
    * @param proxy
    * @return
    */
   public int updateCompleteCount(ToolAgentCmdProxy cmdProxy) {
      int returnCnt;
      synchronized (m_assIdToCount) {
         Object assId = cmdProxy.getAssId();
         Integer curCnt = null == assId ? null
            : (Integer)m_assIdToCount.get(assId);
         if (null == curCnt) {
            error("Unable to find cmd count for assId "+assId.toString());
            returnCnt=-1;
         }

         /*
          * Only complete the last scheduled activity
          */
         returnCnt = Math.max(curCnt.intValue()-1,0);
         m_assIdToCount.put(assId, new Integer(returnCnt));
      }

      return returnCnt;
   }

}
