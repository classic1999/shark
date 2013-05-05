/* ExpressionBuilderTestCase.java */
package org.enhydra.shark.test;

import java.io.PrintStream;
import java.util.Properties;

import junit.framework.TestCase;

import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.corba.ExpressionBuilders.*;
import org.enhydra.shark.corba.WorkflowService.*;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.*;
import org.omg.WfBase.BaseException;
import org.omg.WfBase.NameValue;
import org.omg.WorkflowModel.*;

/**
 * ExpressionBuilderTestCase
 * 
 * @author V.Puskas
 * @version 0.1
 */
public class ExpressionBuilderCORBATestCase extends TestCase {
   private String m1 = "Assignment is not valid anymore";

   private String m2 = "activity state is closed";

   private String m3 = "Can't change to state ";

   private static boolean alreadyConfigured = false;

   private ExecutionAdministration ea = null;

   private Properties p;

   public static void main(String[] args) {
      junit.textui.TestRunner.run(ExpressionBuilderCORBATestCase.class);
   }

   int toGo = 2;

   private static SharkInterface aha;

   private static ORB orb;

   /*
    * @see TestCase#setUp()
    */
   protected void setUp() throws Exception {
      super.setUp();
      aha = findWorkflowServer("localhost", "10123", "Shark");
      if (!alreadyConfigured) {
         createUsers();
         uploadDefinitions();
         new Thread() {
            public void run() {
               executeInstances("admin", 3);
            }
         }.start();
         new Thread() {
            public void run() {
               executeInstances("qq", 3);
            }
         }.start();
         do
            Thread.sleep(1200);
         while (0 != toGo);
         alreadyConfigured = true;
      }
      ea = aha.getExecutionAdministration();
      ea.connect("qq", "enhydra", "shark", "");
   }

   /*
    * @see TestCase#tearDown()
    */
   protected void tearDown() throws Exception {
      super.tearDown();
      ea.disconnect();
   }

   public void testProcessMgrIteratorExpressionBuilder() throws Exception {
      ProcessMgrIteratorExpressionBuilder eb = aha.getExpressionBuilderManager()
         .getProcessMgrIteratorExpressionBuilder();
      eb.not()
         .addPackageIdEquals("test_js")
         .or()
         .addNameEquals("test_js#1#basic");
      WfProcessMgrIterator it1 = ea.get_iterator_processmgr();
      WfProcessMgrIterator it2 = ea.get_iterator_processmgr();
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ " + eb.toExpression());
      report(System.out,
             eb,
             it1.get_next_n_sequence(0),
             it2.get_next_n_sequence(0),
             false);
      System.out.println("---");
      eb = aha.getExpressionBuilderManager()
         .getProcessMgrIteratorExpressionBuilder();
      eb.addProcessDefIdEquals("basic")
         .and()
         .not()
         .addPackageIdEquals("test_bs")
         /**/
         .and()
         .addIsEnabled()/**/
         .setOrderByEnabled(false);
      it1 = ea.get_iterator_processmgr();
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ " + eb.toExpression());
      report(System.out,
             eb,
             it1.get_next_n_sequence(0),
             it2.get_next_n_sequence(0),
             false);
   }

   public void testResourceIteratorExpressionBuilder() throws Exception {
      ResourceIteratorExpressionBuilder eb = aha.getExpressionBuilderManager()
         .getResourceIteratorExpressionBuilder();
      eb.addUsernameEquals("qq");
      WfResourceIterator it1 = ea.get_iterator_resource();
      WfResourceIterator it2 = ea.get_iterator_resource();
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ " + eb.toExpression());
      report(System.out,
             eb,
             it1.get_next_n_sequence(0),
             it2.get_next_n_sequence(0),
             false);
      System.out.println("---");
      eb = aha.getExpressionBuilderManager()
         .getResourceIteratorExpressionBuilder();
      eb.addAssignemtCountGreaterThan(1)
         .and()
         .not()
         .addUsernameEquals("admin")
         .setOrderByUsername(true);
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ " + eb.toExpression());
      report(System.out,
             eb,
             it1.get_next_n_sequence(0),
             it2.get_next_n_sequence(0),
             false);
   }

   public void testAssignmentIteratorExpressionBuilder() throws Exception {
      /**/
      AssignmentIteratorExpressionBuilder eb = aha.getExpressionBuilderManager()
         .getAssignmentIteratorExpressionBuilder();
      eb.addUsernameEquals("qq").setOrderByCreatedTime(true);
      WfAssignmentIterator it1 = ea.get_iterator_assignment();
      WfAssignmentIterator it2 = ea.get_iterator_assignment();
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ " + eb.toExpression());
      report(System.out,
             eb,
             it1.get_next_n_sequence(0),
             it2.get_next_n_sequence(0),
             false);
      System.out.println("---");
      /**/
      eb = aha.getExpressionBuilderManager()
         .getAssignmentIteratorExpressionBuilder();
      eb.not()
         .addProcessDefIdEquals("Game")
         .and()
         .not()
         .addPackageIdEquals("test_bs")
         .setOrderByProcessId(false);
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ " + eb.toExpression());
      report(System.out,
             eb,
             it1.get_next_n_sequence(0),
             it2.get_next_n_sequence(0),
             false);
      /**/
   }

   public void testProcessIteratorExpressionBuilder() throws Exception {
      /**/
      ProcessIteratorExpressionBuilder eb = aha.getExpressionBuilderManager()
         .getProcessIteratorExpressionBuilder();
      eb.not()
         .addPackageIdEquals("test_bs")
         .or()
         .addMgrNameEquals("test_bs#1#basic")
         .setOrderByLastStateTime(true);
      WfProcessIterator it1 = ea.get_iterator_process();
      WfProcessIterator it2 = ea.get_iterator_process();
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ " + eb.toExpression());
      report(System.out,
             eb,
             it1.get_next_n_sequence(0),
             it2.get_next_n_sequence(0),
             false);
      System.out.println("---");
      /**/
      eb = aha.getExpressionBuilderManager()
         .getProcessIteratorExpressionBuilder();
      eb.addProcessDefIdEquals("basic")
         .and()
         .not()
         .addPackageIdEquals("test_bs")
         /**/
         .and()
         .addStateStartsWith("open")/**/;
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ " + eb.toExpression());
      report(System.out,
             eb,
             it1.get_next_n_sequence(0),
             it2.get_next_n_sequence(0),
             false);
      /**/
      System.out.println("---");
      /**/
      eb = aha.getExpressionBuilderManager()
         .getProcessIteratorExpressionBuilder();
      eb.addExpression(aha.getExpressionBuilderManager()
         .getProcessIteratorExpressionBuilder()
         .not()
         .addProcessDefIdEquals("basic")
      /*****************************************************************
       * / .and() .addVariableGreaterThan("game_cycles", 2)/
       ****************************************************************/
      ).or().addExpression(aha.getExpressionBuilderManager()
         .getProcessIteratorExpressionBuilder()
         .addProcessDefIdEquals("basic")
         .and()
         .addStateStartsWith("closed"))
         .setOrderByPriority(false);
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ " + eb.toExpression());
      report(System.out,
             eb,
             it1.get_next_n_sequence(0),
             it2.get_next_n_sequence(0),
             false);
      /**/
      System.out.println("---");
      /**/
      eb = aha.getExpressionBuilderManager()
         .getProcessIteratorExpressionBuilder();
      eb.not().addProcessDefIdEquals("basic")
      /**/
      .and().addRequesterIdEquals("qq")/**/;
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ " + eb.toExpression());
      report(System.out,
             eb,
             it1.get_next_n_sequence(0),
             it2.get_next_n_sequence(0),
             false);
   }

   public void testActivityIteratorExpressionBuilder() throws Exception {
      /**/
      ActivityIteratorExpressionBuilder eb = aha.getExpressionBuilderManager()
         .getActivityIteratorExpressionBuilder();
      eb.not()
         .addPackageIdEquals("test_bs")
         .or()
         .addMgrNameEquals("test_bs#1#basic");
      WfActivityIterator it1 = ea.get_iterator_activity();
      WfActivityIterator it2 = ea.get_iterator_activity();
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ " + eb.toExpression());
      report(System.out,
             eb,
             it1.get_next_n_sequence(0),
             it2.get_next_n_sequence(0),
             false);
      System.out.println("---");
      /**/
      eb = aha.getExpressionBuilderManager()
         .getActivityIteratorExpressionBuilder();
      eb.addProcessDefIdEquals("basic")
         .and()
         .not()
         .addPackageIdEquals("test_bs")
         /**/
         .and()
         .addStateStartsWith("open")/**/;
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ " + eb.toExpression());
      report(System.out,
             eb,
             it1.get_next_n_sequence(0),
             it2.get_next_n_sequence(0),
             false);
      /**/
      System.out.println("---");
      /**/
      eb = aha.getExpressionBuilderManager()
         .getActivityIteratorExpressionBuilder();
      eb.addExpression(aha.getExpressionBuilderManager()
         .getActivityIteratorExpressionBuilder()
         .not()
         .addProcessDefIdEquals("basic")
      /*****************************************************************
       * / .and() .addProcessVariableGreaterThan("game_cycles", 2)/
       ****************************************************************/
      ).or().addExpression(aha.getExpressionBuilderManager()
         .getActivityIteratorExpressionBuilder()
         .addProcessDefIdEquals("basic")
         .and()
         .addProcessStateStartsWith("closed"));
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ " + eb.toExpression());
      report(System.out,
             eb,
             it1.get_next_n_sequence(0),
             it2.get_next_n_sequence(0),
             false);
      /**/
      System.out.println("---");
      /**/
      eb = aha.getExpressionBuilderManager()
         .getActivityIteratorExpressionBuilder();
      eb.not().addProcessDefIdEquals("basic")
      /**/
      .and().addProcessRequesterIdEquals("qq")/**/;
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ " + eb.toExpression());
      report(System.out,
             eb,
             it1.get_next_n_sequence(0),
             it2.get_next_n_sequence(0),
             false);
      /**/
      System.out.println("---");
      /**/
      eb = aha.getExpressionBuilderManager()
         .getActivityIteratorExpressionBuilder();
      eb.not().addProcessDefIdEquals("basic")
      /**/
      .and().not().addProcessRequesterIdEquals("qq")/**/;
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ " + eb.toExpression());
      report(System.out,
             eb,
             it1.get_next_n_sequence(0),
             it2.get_next_n_sequence(0),
             false);
   }

   private void report(PrintStream _out_,
                       Object eb,
                       Object[] a,
                       Object[] b,
                       boolean itemsToo) throws Exception {
      if (a.length != b.length) { throw new Exception("Results lengths do not match:"
                                                      + a.length
                                                      + " != "
                                                      + b.length); }
      _out_.println(eb.getClass().getName());
      _out_.println("length of sequence:" + a.length);
      if (0 == a.length) { throw new Exception("empty array"); }
      if (itemsToo) {
         for (int i = 0; i < a.length; i++) {
            _out_.println("\t" + i + ":" + a[i]);
         }
      }
   }

   private void executeInstances(String user, int cnt) {
      SharkConnection lea = aha.getSharkConnection();
      try {
         AdminMisc am = aha.getAdminMisc();
         lea.connect(user, "enhydra", "shark", "");
         NameValue[] cntxt = new NameValue[] {
            new NameValue("game_cycles", orb.create_any())
         };
         cntxt[0].the_value.insert_longlong(cnt);
         for (int i = 0; i < cnt; ++i) {
            WfProcess proc = lea.createProcess("test_bs", "Game");
            proc.set_process_context(cntxt);
            proc.start();
            lea.createProcess("test_js", "basic")
               .start();
            lea.createProcess("test_js", "manufacturing")
               .start();
            lea.createProcess("test_bs", "manufacturing")
               .start();
         }
         WfResource res = lea.getResourceObject();
         for (WfAssignment[] ass = null; true;) {
            try {
               ass = res.get_sequence_work_item(0);
               if (ass.length < 20) break;
            } catch (BaseException e) {
               e.printStackTrace();
               continue;
            }
            if (ass != null && ass.length > 0) {
               for (int i = 0; i < ass.length; i++) {
                  String asss = ass[i].toString();
                  try {
                     // koop
                     WfProcess _prc_ = ass[i].activity().container();
                     WfActivity[] acts = _prc_.get_activities_in_state(SharkConstants.STATE_OPEN_RUNNING)
                        .get_next_n_sequence(0);
                     if (acts.length < 1) {
                        System.err.println(":::NONE");
//                     } else {
//                        for (int k = 0; k < acts.length; k++) {
//                           WfActivity _aha_ = acts[k];
//                           System.err.println(":::"
//                                              + am.getActivityResourceUsername(_prc_.key(),
//                                                                               _aha_.key()));
//                        }
                     }
                     if (!ass[i].get_accepted_status()) {
                        System.err.println("Thread "
                                           + this + " - Accepting ass "
                                           + asss + ", active threads="
                                           + Thread.activeCount());
                        ass[i].set_accepted_status(true);
                     }
                     // koop
                     System.err.println("Thread "
                                        + this + " - Completing ass " + asss
                                        + ", active threads="
                                        + Thread.activeCount());
                     ass[i].activity().complete();
                     System.err.println("Thread "
                                        + this + " - Ass " + asss
                                        + " completed, active threads="
                                        + Thread.activeCount());
                  } catch (Exception ex) {
                     ex.printStackTrace();
                     System.err.println("Thread "
                                        + this
                                        + " - Problems while executing ass "
                                        + asss + ", active threads="
                                        + Thread.activeCount() + " errMsg="
                                        + ex.getMessage());
                  }
               }
            } else {
               break;
            }
         }
      } catch (Exception re) {
         re.printStackTrace();
         throw new Error();
      } finally {
         synchronized (System.err) {
            --toGo;
         }
         try {
            lea.disconnect();
         } catch (Exception _) {}
      }
   }

   private void uploadDefinitions() {
      PackageAdministration pa = aha.getPackageAdministration();
      RepositoryMgr rm = aha.getRepositoryManager();
      try {
         pa.connect("admin","enhydra","Shark","");
         rm.connect("admin","enhydra","Shark","");
      } catch (Exception e) {
         e.printStackTrace();
      }
      String[] xpdlNames = {
                  "181",
                  "shark_manufacturer",
                  "test-BeanShell",
                  "test-JavaScript"
      };
      for (int i = 0; i < xpdlNames.length; i++) {
         String xpdlName = xpdlNames[i] + ".xpdl";
         try {
            String pkgId = rm.getPackageId(xpdlName);
            if (!pa.isPackageOpened(pkgId)) {
               pa.openPkg(xpdlName);
            }
         } catch (Throwable _) {}
      }
   }

   private void createUsers() {
      UserGroupAdministration uga = aha.getUserGroupAdministration();
      try {
         uga.connect("admin", "enhydra", "Shark", "");
         if (!uga.doesGroupExist("test")) {
            uga.createGroup("test", "test group");
         }
         String[] users = {
                           "qq", "admin", "sima", "zika"
         };
         for (int i = 0; i < users.length; i++) {
            if (!uga.doesUserExist(users[i])) {
               uga.createUser("test", users[i], "enhydra", "Jane", "Doe", "");
            }
         }
      } catch (Throwable t) {
         t.printStackTrace();
      } finally {
         try {
            uga.disconnect();
         } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
   }

   public static SharkInterface findWorkflowServer(String host,
                                                   String port,
                                                   String workflowServerName) throws Exception {
      //      String[] args={"-ORBInitialHost",host,"-ORBInitialPort",port};
      String[] args = {
                  "-ORBInitRef",
                  "NameService=corbaloc::"
                                                      + host + ":" + port
                                                      + "/NameService"
      };

      // Create and initialize the ORB
      orb = ORB.init(args, null);
      // Get the root naming context
      org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
      NamingContext ncRef = NamingContextHelper.narrow(objRef);

      // Resolve the object reference in naming
      NameComponent nc = new NameComponent(workflowServerName, "");
      NameComponent path[] = {
         nc
      };
      SharkInterface si = SharkInterfaceHelper.narrow(ncRef.resolve(path));
      //objRef._release();
      //ncRef._release();
      return si;
   }
}