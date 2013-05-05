package test;

import java.util.*;
import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.api.client.wfservice.*;

import java.io.FileInputStream;
import org.enhydra.shark.Shark;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.RootException;

public class JSPClientUtilities {
   private static boolean _debug_ = false;
   private static boolean sharkConfigured = false;
   private static String userName = "qq";
   private static String pwd = "lele";
   private static String engineName = "SharkExampleJSP";

   private static String EXT_ATT_NAME = "pageContent";

   public static void init(String realPath){
      if (_debug_)
         System.err.println("#_init_#");
      if (!sharkConfigured){
         Properties p = new Properties();
         try {
            if (!realPath.endsWith("\\")) {
               realPath=realPath+"\\";
            }
            realPath = replaceAll(realPath, "\\", "/");
            p.load(new FileInputStream(realPath +"conf/Shark.conf"));
            for (Iterator it = p.keySet().iterator(); it.hasNext();) {
               String key = (String)it.next();
               String value = p.getProperty(key);
               if (0 <= value.indexOf("@@")) {
                  if (_debug_)
                     System.err.print("key is "+key+", old value is"+value);
                  value = replaceAll(value, "@@/", realPath);
                  p.setProperty(key, value);
                  if (_debug_)
                     System.err.println(", new value is"+value);
               }
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
         p.setProperty("enginename", engineName);
         Shark.configure(p);
         sharkConfigured = true;
      }
      user();
   }

   private static void user() {
      String groupName = "idle";
      UserGroupAdministration uga= Shark
      .getInstance()
      .getAdminInterface()
      .getUserGroupAdministration();
      try {
         if (!uga.doesGroupExist(groupName)) {
            uga.createGroup(groupName,"test group");
         }
         if (!uga.doesUserExist(userName)) {
            uga.createUser(groupName, userName, pwd,"Jane", "Doe","test@together.at");
         }
      } catch (Throwable t) {}
   }

   public static String packageLoad(String xpdlName) throws BaseException {
      if (_debug_)
         System.err.println("#_packageLoad_#");
      PackageAdministration pa =Shark
      .getInstance()
      .getAdminInterface()
      .getPackageAdministration();
      RepositoryMgr rm = Shark
      .getInstance()
      .getRepositoryManager();
      String pkgId=rm.getPackageId(xpdlName);
      if (!pa.isPackageOpened(pkgId)) {
         try {
            pa.openPackage(xpdlName);
         } catch (Throwable e) {
            e.printStackTrace();
            throw new BaseException(e);
         }
      }
      return rm.getPackageId(xpdlName);
   }

   public static void processStart(String mgrName) throws BaseException {
      if (_debug_)
         System.err.println("#_processStartName_#");
      SharkConnection sConn = null;
      sConn = Shark
      .getInstance()
      .getSharkConnection();
      try {
         String pkgId = Shark
         .getInstance()
         .getAdminInterface()
         .getAdminMisc()
         .getProcessMgrPkgId(mgrName);
         String pDefId = Shark
         .getInstance()
         .getAdminInterface()
         .getAdminMisc()
         .getProcessMgrProcDefId(mgrName);

         if (!isProcessRunning(pkgId, pDefId)) {
            sConn.connect(userName, pwd, engineName, null);
            sConn
            .createProcess(pkgId, pDefId)
            .start();
            sConn.disconnect();
         }
      } catch (Exception e) {
         e.printStackTrace();
         throw new BaseException(e);
      }
   }

   public static void processStart(String mgrName, SharkConnection sConn) throws BaseException {
      if (_debug_)
         System.err.println("#_processStartName_#");
      try {
         String pkgId = Shark
         .getInstance()
         .getAdminInterface()
         .getAdminMisc()
         .getProcessMgrPkgId(mgrName);
         String pDefId = Shark
         .getInstance()
         .getAdminInterface()
         .getAdminMisc()
         .getProcessMgrProcDefId(mgrName);

         if (!isProcessRunning(pkgId, pDefId)) {
            sConn
            .createProcess(pkgId, pDefId)
            .start();
         }
      } catch (Exception e) {
         e.printStackTrace();
         throw new BaseException(e);
      }
   }

   public static void processStart(String pkgId, String pDefId) throws BaseException {
      if (_debug_)
         System.err.println("#_processStartIds_#");
      SharkConnection sConn = null;
      sConn = Shark
      .getInstance()
      .getSharkConnection();
      try {
         if (!isProcessRunning(pkgId, pDefId)) {
            sConn.connect(userName, pwd, engineName, null);
            sConn
            .createProcess(pkgId, pDefId)
            .start();
            sConn.disconnect();
         }
      } catch (Exception e) {
         e.printStackTrace();
         throw new BaseException(e);
      }
   }

   public static String extAttribute(String procId, String actId) throws BaseException {
      return Shark
      .getInstance()
      .getAdminInterface()
      .getAdminMisc()
      .getActivitiesExtendedAttributeValue(procId, actId, EXT_ATT_NAME);
   }

   public static boolean isProcessRunning(String pkgId, String pDefId) throws BaseException {
      //      if (_debug_)
      System.err.println("#_isProcessRunning_# ("+pkgId+", "+pDefId+")");
      ExecutionAdministration ea = Shark
      .getInstance()
      .getAdminInterface()
      .getExecutionAdministration();
      try {
         ea.connect(userName, pwd, engineName, null);
         WfProcessMgr pMgr = ea.getProcessMgr(pkgId, pDefId);
         WfProcessIterator pit = pMgr.get_iterator_process();
         //pit.set_query_expression("state.equals(\"open.running\");");
         pit.set_query_expression("state.equals(\""
                                     + SharkConstants.STATE_OPEN_RUNNING
                                     +"\")");
         if (_debug_) {
            System.err.println("#_"+pit.how_many()+"_#");
            System.err.println("#_"+pit.get_next_n_sequence(0).length+"_#");
         }
         return 0 < pit.get_next_n_sequence(0).length;
      } catch (Exception e) {
         e.printStackTrace();
         throw new BaseException(e);
      } finally {
         try {
            ea.disconnect();
         } catch (Exception e) {}
      }
   }

   public static void activityComplete(SharkConnection sConn, String activityId) throws BaseException {
      try {
         if(null != activityId) {
            try {
               WfAssignment a = getAssignment(sConn, activityId);
               if (!isMine(sConn, a))
                  assignmentAccept(sConn, a);
               a.activity().complete();
            } catch (Exception e) {
               throw new BaseException(e);
            }
         }
      } catch (BaseException e) {
         e.printStackTrace();
         //throw e;
      }
   }

   public static boolean isMine(SharkConnection sConn, String activityId) throws NotConnected, BaseException {
      WfAssignment a = getAssignment(sConn, activityId);
      return isMine(sConn, a);
   }

   public static boolean isMine(SharkConnection sConn, WfAssignment a) throws NotConnected, BaseException {
      return a.get_accepted_status();
   }

   public static void assignmentAccept(SharkConnection sConn, String activityId) throws CannotAcceptSuspended, NotConnected, BaseException {
      assignmentAccept(sConn, getAssignment(sConn, activityId));
   }

   private static void assignmentAccept(SharkConnection sConn, WfAssignment a) throws CannotAcceptSuspended, NotConnected, BaseException {
      a.set_accepted_status(true);
   }

   public static WfAssignment getAssignment(SharkConnection sConn, String activityId) throws NotConnected, BaseException {
      try {
         WfAssignment[] ar = sConn.getResourceObject().get_sequence_work_item(0);
         for (int i = 0; i < ar.length; ++i) {
            if (activityId.equals(ar[i].activity().key())) {
               return ar[i];
            }
         }
         throw new BaseException("Activity:"
                                    + activityId
                                    +" not found in "
                                    + sConn.getResourceObject().resource_key()
                                    +"'s worklist");
      } catch (Exception e) {
         if (_debug_)
            System.err.println("zvekseptsn");
         e.printStackTrace();
         throw new BaseException(e);
      }
   }

   public static SharkConnection connect() throws ConnectFailed, BaseException {
      if (_debug_)
         System.err.println("#_connect_#");
      SharkConnection sConn = Shark
      .getInstance()
      .getSharkConnection();
      sConn.connect(userName, pwd, engineName, null);
      return sConn;
   }

   public static SharkConnection connect(String user, String passwd) throws ConnectFailed, BaseException {
      if (_debug_)
         System.err.println("#_connect_#");
      try {
         String groupName = "idle";
         UserGroupAdministration uga= Shark
         .getInstance()
         .getAdminInterface()
         .getUserGroupAdministration();
         if (!uga.doesGroupExist(groupName)) {
            uga.createGroup(groupName,"test group");
         }
         if (!uga.doesUserExist(user)) {
            uga.createUser(groupName, user, passwd, user, passwd,"test@together.at");
         }
      } catch (Throwable t) {}
      SharkConnection sConn = Shark
      .getInstance()
      .getSharkConnection();
      sConn.connect(user, passwd, engineName, null);
      return sConn;
   }

   public static void disconnect(SharkConnection sConn) throws NotConnected, BaseException {
      if (_debug_)
         System.err.println("#_disconnect_#");
      sConn.disconnect();
   }

   public static String[] getAllUsers() throws BaseException {
      UserGroupAdministration uga= Shark
      .getInstance()
      .getAdminInterface()
      .getUserGroupAdministration();

      return uga.getAllUsers();
   }

   public static String getUserRealName(String uname) throws BaseException {
      UserGroupAdministration uga= Shark
      .getInstance()
      .getAdminInterface()
      .getUserGroupAdministration();

      return uga.getUserRealName(uname);
   }

   public static String getUserEMailAddress(String uname) throws BaseException {
      UserGroupAdministration uga= Shark
      .getInstance()
      .getAdminInterface()
      .getUserGroupAdministration();

      return uga.getUserEMailAddress(uname);
   }

   public static void variableSet(SharkConnection sConn,
                                  String activityId,
                                  String vName,
                                  String vValue) throws NotConnected, BaseException, UpdateNotAllowed, InvalidData {
      WfAssignment a = getAssignment(sConn, activityId);
      if (!isMine(sConn, a))
         throw new BaseException("I don't own activity "+ activityId);
      Map _m = new HashMap();
      Object c = a.activity().process_context().get(vName);
      if (c instanceof Long) {
         c = new Long(vValue);
      } else if (c instanceof Boolean) {
         c = Boolean.valueOf(vValue);
      } else if (c instanceof Double) {
         c = Double.valueOf(vValue);
      } else {
         c = vValue;
      }
      _m.put(vName, c);
      a.activity().set_result(_m);
   }
   public static void s() {
      WfAssignment a = null;
      Map _m = null;
      try {
         SharkTransaction tr = Shark.getInstance().createTransaction();
         try {
            a.activity(tr).set_result(tr, _m);
            a.activity(tr).complete(tr);
            tr.commit();
         } catch (RootException e) {
            Shark.getInstance().emptyCaches(tr);
            Shark.getInstance().unlockProcesses(tr);
            tr.rollback();
         }
      } catch (TransactionException e) {
      }
   }
   public static String[] xpdlsToLoad() {
      try {
         return Shark
         .getInstance()
         .getRepositoryManager()
         .getPackagePaths();
      } catch (BaseException e) {
         e.printStackTrace();
         return new String[]{};
      }
   }

   public static String[] processesToStart() {
      ExecutionAdministration ea = null;
      try {
         ea = Shark
         .getInstance()
         .getAdminInterface()
         .getExecutionAdministration();
         ea.connect(userName, pwd, engineName, null);
         WfProcessMgr[] a = ea
         .get_iterator_processmgr()
         .get_next_n_sequence(0);
         String [] ret = new String[a.length];
         for (int i = 0; i < a.length; ++i) {
            String n = a[i].name();
            if (_debug_)
               System.err.println("processName "+n);
            ret[i]= n;
         }
         return ret;
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            ea.disconnect();
         } catch (BaseException e) {} catch (NotConnected e) {}
      }
      return new String[]{};
   }

   /**
    * Replace all occurence of forReplace with replaceWith in input string.
    * @param input represents input string
    * @param forReplace represents substring for replace
    * @param replaceWith represents replaced string value
    * @return new string with replaced values
    */
   private static String replaceAll(String input,
                                    String forReplace,
                                    String replaceWith) {
      if( input == null )
         return null;
      StringBuffer result = new StringBuffer();
      boolean hasMore = true;
      while (hasMore) {
         int start = input.indexOf(forReplace);
         int end = start + forReplace.length();
         if (start != -1) {
            result.append(input.substring(0, start) + replaceWith);
            input = input.substring(end);
         }
         else {
            hasMore = false;
            result.append(input);
         }
      }
      if (result.toString().equals(""))
         return input; //nothing is changed
      else
         return result.toString();
   }
}

