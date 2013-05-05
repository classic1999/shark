/* SharkWebServiceImpl.java */

package org.enhydra.shark.asap;

import org.enhydra.shark.Shark;
import org.enhydra.shark.api.RootError;
import org.enhydra.shark.api.client.wfservice.*;

/**
 * @author V.Puskas, S.Bojanic
 * @version 0.2
 */
public class SharkServiceImpl {

   public static final String GROUPNAME = "test";

   public static final String USERNAME = "admin";

   public static final String PASSWD = "enhydra";

   protected static final String XPDL_FILENAME = "test-BeanShell.xpdl";

   public static final String DEFAULT_PROC_MGR = "test_bs#1#math_operations";

   public static final String QSPN_PROCESS_DEFINITION = "procDef=";

   public static final String QSPN_PACKAGE_ID = "packageId=";

   public static final String QSPN_PROCESS_MANAGER = "procMgr=";

   public static final String QSPN_PROCESS_INSTANCE = "procInst=";

   //public static AsapPersistenceManager asapPersistenceManager;

   //public static RequesterImpl sharkExternalRequester;

   protected static ExecutionAdministration executionAdmin;

   protected static AdminMisc adminMisc;

   public static String factoryBindingAddress;

   public static void main(String[] argv) {
      try {
         Shark.configure(argv[0]);
         PackageAdministration pa = Shark.getInstance()
            .getAdminInterface()
            .getPackageAdministration();
         RepositoryMgr rm = Shark.getInstance().getRepositoryManager();
         UserGroupAdministration uga = Shark.getInstance()
            .getAdminInterface()
            .getUserGroupAdministration();
         try {
            if (!uga.doesGroupExist(GROUPNAME)) {
               uga.createGroup("test", "test group");
            }
            if (!uga.doesUserExist(USERNAME)) {
               uga.createUser(GROUPNAME, USERNAME, PASSWD, "Jane", "Doe", "");
            }
            String pkgId = rm.getPackageId(XPDL_FILENAME);
            if (!pa.isPackageOpened(pkgId)) {
               pa.openPackage(XPDL_FILENAME);
            }

         } catch (Throwable t) {
            t.printStackTrace();
         }

         //asapPersistenceManager=new DODSAsapPersistenceManager();
         //asapPersistenceManager.configure(Shark.getInstance().getProperties());

         //sharkExternalRequester=new RequesterImpl();

         adminMisc = Shark.getInstance().getAdminInterface().getAdminMisc();
         adminMisc.connect(SharkServiceImpl.USERNAME);

         executionAdmin = Shark.getInstance()
            .getAdminInterface()
            .getExecutionAdministration();
         executionAdmin.connect(SharkServiceImpl.USERNAME,
                                SharkServiceImpl.PASSWD,
                                "",
                                "");

         factoryBindingAddress = new SharkWebServiceLocator().getasapFactoryBindingAddress();
         //restoreExternalRequester();
         String[] args = new String[argv.length - 1];
         for (int n = 1; n < argv.length; ++n) {
            args[n - 1] = argv[n];
         }
         Class.forName("org.apache.axis.transport.http.SimpleAxisServer")
            .getDeclaredMethod("main", new Class[] {
               args.getClass()
            })
            .invoke(null, new Object[] {
               args
            });
      } catch (Exception e) {
         throw new RootError(e);
      }
   }

   /*
    * public static AsapPersistenceManager getAsapPersistenceManager () {
    * return asapPersistenceManager; }
    */

   /*
    * public static RequesterImpl getExternalRequester () { return
    * sharkExternalRequester; }
    */

   public static AdminMisc getAdminMiscUtilities() {
      return adminMisc;
   }

   public static ExecutionAdministration getExecAdmin() {
      return executionAdmin;
   }
   //
   //   public static String getMyAddress() {
   //      String[] a = MessageContext.getCurrentContext()
   //         .getRequestMessage()
   //         .getMimeHeaders()
   //         .getHeader("X-Forwarded-Server");
   //      if (null != a && a.length > 0 && null != a[a.length - 1]) return
   // a[a.length - 1];
   //      return myAddress;
   //   }

   /*
    * private static void restoreExternalRequester () throws
    * RootException { ExecutionAdministration
    * ea=Shark.getInstance().getAdminInterface().getExecutionAdministration();
    * ea.connect(USERNAME,PASSWD,"",""); List
    * l=asapPersistenceManager.getAllObservedProcesses(null); Iterator
    * it=l.iterator(); while (it.hasNext()) { String
    * procId=(String)it.next(); WfProcess proc=ea.getProcess(procId);
    * proc.set_requester(sharkExternalRequester); } }
    */

}