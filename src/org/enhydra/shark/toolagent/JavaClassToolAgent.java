package org.enhydra.shark.toolagent;

import java.lang.reflect.*;

import org.enhydra.shark.api.*;
import org.enhydra.shark.api.internal.toolagent.*;

/**
 * Tool agent that executes special kind of java class.
 * @author Sasa Bojanic
 * @version 1.0
 */
public class JavaClassToolAgent extends AbstractToolAgent {

   private static final String EXECUTION_METHOD_NAME = "execute";

   public void invokeApplication (SharkTransaction t,
                                  long handle,
                                  String applicationName,
                                  String procInstId,
                                  String assId,
                                  AppParameter[] parameters,
                                  Integer appMode)
      throws ApplicationNotStarted, ApplicationNotDefined,
      ApplicationBusy, ToolAgentGeneralException {

      super.invokeApplication(t,handle,applicationName,procInstId,assId,parameters,appMode);

      try {
         status=APP_STATUS_RUNNING;

         if (appName==null || appName.trim().length()==0) {
            readParamsFromExtAttributes((String)parameters[0].the_value);
         }

         ClassLoader cl=getClass().getClassLoader();
         Class c = cl.loadClass(appName);

         // Get parameter types - ignore 1. param, these are ext.attribs
         Class[] parameterTypes=null;
         if (parameters!=null) {
            parameterTypes=new Class[parameters.length-1];
            for (int i=1; i<parameters.length; i++) {
               parameterTypes[i-1]=AppParameter.class;
            }
         }

         Method m = c.getMethod(EXECUTION_METHOD_NAME,parameterTypes);

         // invoke the method
         AppParameter[] aps=new AppParameter[parameters.length-1];
         System.arraycopy(parameters,1,aps,0,aps.length);
         m.invoke(null,aps);
         status=APP_STATUS_FINISHED;

      } catch (ClassNotFoundException cnf) {
         cus.error("JavaClassToolAgent - application "+appName+" terminated incorrectly, can't find class: "+cnf);
         throw new ApplicationNotDefined("Can't find class "+appName,cnf);
      } catch (NoSuchMethodException nsm) {
         cus.error("JavaClassToolAgent - application "+appName+" terminated incorrectly, can't find method "+EXECUTION_METHOD_NAME+" : "+nsm);
         throw new ApplicationNotDefined("Class "+appName+" doesn't have method "+EXECUTION_METHOD_NAME,nsm);
      } catch (NoClassDefFoundError ncdfe) {
         cus.error("JavaClassToolAgent - application "+appName+" terminated incorrectly, can't find class definition: "+ncdfe);
         throw new ApplicationNotDefined("Class "+appName+" can't be executed",ncdfe);
      } catch (Throwable ex) {
         cus.error("JavaClassToolAgent - application "+appName+" terminated incorrectly: "+ex);
         status=APP_STATUS_INVALID;
         throw new ToolAgentGeneralException(ex);
      }
   }

   public String getInfo (SharkTransaction t) throws ToolAgentGeneralException {
      String i="This tool agent executes Java classes, by calling its static method called \"execute\"."+
         "\nWhen calling this tool agent's invokeApplication() method, the application "+
         "\nname parameter should be the full name of the class that should be executed "+
         "\nby this tool agent (the classes had to be in the class path) "+
         "\n"+
         "\nThis tool agent is able to understand the extended attribute with the following name:"+
         "\n     * AppName - value of this attribute should represent the class name to be executed"+
         "\n"+
         "\n NOTE: Tool agent will read extended attributes only if they are called through"+
         "\n       Default tool agent (not by shark directly) and this is the case when information "+
         "\n       on which tool agent to start for XPDL application definition is not contained in mappings";
      return i;
   }
}
