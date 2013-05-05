package org.enhydra.shark.toolagent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.toolagent.AppParameter;
import org.enhydra.shark.api.internal.toolagent.ApplicationBusy;
import org.enhydra.shark.api.internal.toolagent.ApplicationNotDefined;
import org.enhydra.shark.api.internal.toolagent.ApplicationNotStarted;
import org.enhydra.shark.api.internal.toolagent.ToolAgentGeneralException;
import org.enhydra.shark.xpdl.XPDLConstants;
import org.enhydra.shark.xpdl.elements.ExtendedAttribute;
import org.enhydra.shark.xpdl.elements.ExtendedAttributes;

import bsh.Interpreter;

/**
 * Tool agent that executes bean shell scripts. Script can be executed as a file that
 * is stored in tool agent repository, or may be contained within the given
 * application name.
 * @author Sasa Bojanic
 * @version 1.0
 */
public class BshToolAgent extends AbstractToolAgent {

   public static final long APP_MODE_FILE=0;
   public static final long APP_MODE_TEXT=1;

   public static final String SCRIPT_EXT_ATTR_NAME="Script";

   private String script;

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
         if (script==null) {
            if (appMode!=null && appMode.intValue()==APP_MODE_FILE) {
               script=readScriptFromFile(appName);
            } else {
               script=appName;
            }
         }

         Interpreter interpreter=new Interpreter();
         prepareContext (t, interpreter,parameters);
         Reader sr = new StringReader (script);
         //System.out.println("Executing script "+script);
         interpreter.eval(sr);
         prepareResult(parameters,interpreter);

         status=APP_STATUS_FINISHED;
      } catch (IOException ioe) {
         cus.error("BshToolAgent - application "+appName+" terminated incorrectly, can't find script file: "+ioe);
         throw new ApplicationNotDefined("Can't find script file "+appName,ioe);
      } catch (Throwable ex) {
         status=APP_STATUS_INVALID;
         //ex.printStackTrace();
         cus.error("BshToolAgent - application "+appName+" terminated incorrectly: "+ex);
         throw new ToolAgentGeneralException(ex);
      }

   }

   public String getInfo (SharkTransaction t) throws ToolAgentGeneralException {
      String i="Executes scripts written in Java language syntax." +
         "\nIf you set application mode to 0 (zero), tool agent will search for a script "+
         "\nfile given as applicationName parameter (this file has to be in the class path),"+
         "\nand if it founds it, it will try to execute it. Otherwise, it will consider "+
         "\napplicationName parameter to be the script itself, and will try to execute it."+
         "\n"+
         "\nThis tool agent is able to understand the extended attributes with the following names:"+
         "\n     * AppName - value of this attribute should represent the name of script file to "+
         "\n                 execute (this file has to be in class path)"+
         "\n     * Script - the value of this represents the script to be executed. I.e. this"+
         "\n                extended attribute in XPDL can be defined as follows:"+
         "\n                      <ExtendedAttribute Name=\"Script\" Value=\"c=new java.lang.Long(a.longValue()-b.longValue());\"/>"+
         "\n           (a, b and c in above text are Formal parameter Ids from XPDL Application definition)"+
         "\n"+
         "\n NOTE: Tool agent will read extended attributes only if they are called through"+
         "\n       Default tool agent (not by shark directly) and this is the case when information "+
         "\n       on which tool agent to start for XPDL application definition is not contained in mappings";
      return i;
   }

   private void prepareContext (SharkTransaction t, Interpreter interpreter,AppParameter[] context) throws Throwable {
      if (context!=null) {
         // ignore 1. param - it is ext. attribs
         for (int i=1; i<context.length; i++) {
            String key = context[i].the_formal_name;
            java.lang.Object value = context[i].the_value;
            //System.out.println("Value for key "+key+" is "+value);
            //System.out.println("Putting fp "+context[i].the_formal_name+" which class is "+value.getClass().getName());
            if (key.equals("assId")||key.equals("procInstId")||key.equals("sharkTransaction")) {
               cus.warn("Process \""+this.procInstId+"\", activity \""+this.assId+"\" variable conflict");
            }
            interpreter.set(key,value);
         }
         interpreter.set("assId",this.assId);
         interpreter.set("procInstId",this.procInstId);
         interpreter.set("sharkTransaction", t);
      }
   }

   private void prepareResult (AppParameter[] context,Interpreter interpreter) throws Throwable {
      if (context!=null) {
         for (int i = 0; i < context.length; i++) {
            if(context[i].the_mode.equals(XPDLConstants.FORMAL_PARAMETER_MODE_OUT) || context[i].the_mode.equals(XPDLConstants.FORMAL_PARAMETER_MODE_INOUT)) {
               //java.lang.Object val = interpreter.get(context[i].the_formal_name);
               //System.out.println("Getting fp "+context[i].the_formal_name+" - the class is "+val.getClass().getName());
               /*if (context[i].the_value instanceof Boolean) {
                context[i].the_value=(Boolean)val;
                } else if (context[i].the_value instanceof String) {
                context[i].the_value=(String)val;
                } else if (context[i].the_value instanceof Integer) {
                context[i].the_value=new Integer((int)Double.parseDouble(val.toString()));
                } else if (context[i].the_value instanceof Long) {
                context[i].the_value=new Long((long)Double.parseDouble(val.toString()));
                } else if (context[i].the_value instanceof Double) {
                context[i].the_value=(Double)val;
                } else if (context[i].the_value instanceof Date) {
                context[i].the_value=java.text.DateFormat.getDateInstance().parse(val.toString());
                } else {
                context[i].the_value=val;
                }*/
               context[i].the_value=interpreter.get(context[i].the_formal_name);
               if (context[i].the_value instanceof Integer) {
                  context[i].the_value =new Long(((Integer)context[i].the_value).intValue());
               }
            //System.out.println(context[i].the_formal_name+" converted to a class "+context[i].the_value.getClass().getName());

         }
      }
   }
}

   private static String readScriptFromFile (String filename) throws IOException {
      String script=null;
      Reader rdr=null;
      InputStream in = null;
      URL url = null;
      ClassLoader cl = BshToolAgent.class.getClassLoader();
      url = cl.getResource(filename);
      if (url != null) {
         try {
            in = url.openStream();
         } catch (IOException e) {
         }
      }
      if (in != null) {
         rdr=new InputStreamReader(in);
      }

      if (rdr != null) {
         try {
            BufferedReader brdr = new BufferedReader(rdr);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = brdr.readLine()) != null) {
               sb.append(line);
               sb.append('\n');
            }
            script = sb.toString();
         } finally {
            rdr.close();
         }
      }
      return script;
   }

   protected ExtendedAttributes readParamsFromExtAttributes (String extAttribs) throws Exception {
      ExtendedAttributes eas=super.readParamsFromExtAttributes(extAttribs);
      if (appName==null || appName.trim().length()==0) {
         ExtendedAttribute ea=eas.getFirstExtendedAttributeForName(BshToolAgent.SCRIPT_EXT_ATTR_NAME);
         if (ea!=null) {
            script=ea.getVValue();
         }
      }
      return eas;
   }

}
