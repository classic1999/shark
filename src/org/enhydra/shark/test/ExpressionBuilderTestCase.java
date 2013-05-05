/* ExpressionBuilderTestCase.java */
package org.enhydra.shark.test;

import java.io.PrintStream;
import java.util.*;

import junit.framework.TestCase;

import org.enhydra.shark.Shark;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.api.client.wfservice.*;
import org.enhydra.shark.api.common.*;
import org.enhydra.shark.expressionbuilders.*;

/**
 * ExpressionBuilderTestCase
 * 
 * @author V.Puskas
 * @version 0.2
 */
public class ExpressionBuilderTestCase extends TestCase {
   private String m1 = "Assignment is not valid anymore";

   private String m2 = "activity state is closed";

   private String m3 = "Can't change to state ";

   private static boolean alreadyConfigured = false;

   private ExecutionAdministration ea = null;

   private Properties p;

   public static void main(String[] args) {
      junit.textui.TestRunner.run(ExpressionBuilderTestCase.class);
   }

   int toGo = 2;

   /*
    * @see TestCase#setUp()
    */
   protected void setUp() throws Exception {
      super.setUp();
      if (!alreadyConfigured) {
         Shark.configure("conf/Shark.conf");
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
         do Thread.sleep(1200); while (0 != toGo);
         alreadyConfigured = true;
      }
      p = Shark.getInstance().getProperties();
      ea = Shark.getInstance()
         .getAdminInterface()
         .getExecutionAdministration();
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
      ProcessMgrIteratorExpressionBuilder eb = new ProcessMgrIteratorExpressionBuilderDODS(p);
      eb.not()
         .addPackageIdEquals("test_js")
         .or()
         .addNameEquals("test_js#1#basic")
         .setOrderByName(ExpressionBuilder.ORDER_DESCENDING);
      WfProcessMgrIterator it1 = ea.get_iterator_processmgr();
      WfProcessMgrIterator it2 = ea.get_iterator_processmgr();
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ "+eb.toExpression());
      report(System.err,
               eb,
               it1.get_next_n_sequence(0),
               it2.get_next_n_sequence(0),
               false);
      System.out.println("---");
      eb = new ProcessMgrIteratorExpressionBuilderDODS(p);
      eb.addProcessDefIdEquals("basic")
         .and()
         .not()
         .addPackageIdEquals("test_bs")
         /**/
         .and()
         .addIsEnabled()/**/
         .setOrderByVersion(ExpressionBuilder.ORDER_DESCENDING);
      it1 = ea.get_iterator_processmgr();
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ "+eb.toExpression());
      report(System.err,
               eb,
               it1.get_next_n_sequence(0),
               it2.get_next_n_sequence(0),
               false);
   }

   public void testResourceIteratorExpressionBuilder() throws Exception {
      ResourceIteratorExpressionBuilder eb = new ResourceIteratorExpressionBuilderDODS(p);
      eb.addUsernameEquals("qq")
         .setOrderByUsername(ExpressionBuilder.ORDER_DESCENDING);
      WfResourceIterator it1 = ea.get_iterator_resource();
      WfResourceIterator it2 = ea.get_iterator_resource();
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ "+eb.toExpression());
      report(System.err,
               eb,
               it1.get_next_n_sequence(0),
               it2.get_next_n_sequence(0),
               false);
      System.out.println("---");
      eb = new ResourceIteratorExpressionBuilderDODS(p);
      eb.addAssignemtCountGreaterThan(1)
         .and()
         .not()
         .addUsernameEquals("admin")
         .setOrderByUsername(ExpressionBuilder.ORDER_ASCENDING);
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ "+eb.toExpression());
      report(System.err,
               eb,
               it1.get_next_n_sequence(0),
               it2.get_next_n_sequence(0),
               false);
   }

   public void testAssignmentIteratorExpressionBuilder() throws Exception {
      /**/
      AssignmentIteratorExpressionBuilder eb = new AssignmentIteratorExpressionBuilderDODS(p);
      eb.addUsernameEquals("qq");
      WfAssignmentIterator it1 = ea.get_iterator_assignment();
      WfAssignmentIterator it2 = ea.get_iterator_assignment();
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ "+eb.toExpression());
      report(System.err,
               eb,
               it1.get_next_n_sequence(0),
               it2.get_next_n_sequence(0),
               false);
      System.out.println("---");
      /**/
      eb = new AssignmentIteratorExpressionBuilderDODS(p);
      eb.not()
         .addProcessDefIdEquals("Game")
         .and()
         .not()
         .addPackageIdEquals("test_bs");
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ "+eb.toExpression());
      report(System.err,
               eb,
               it1.get_next_n_sequence(0),
               it2.get_next_n_sequence(0),
               false);
      /**/
   }

   public void testProcessIteratorExpressionBuilder() throws Exception {
      /**/
      ProcessIteratorExpressionBuilder eb = new ProcessIteratorExpressionBuilderDODS(p);
      eb.not()
         .addPackageIdEquals("test_bs")
         .or()
         .addMgrNameEquals("test_bs#1#basic");
      WfProcessIterator it1 = ea.get_iterator_process();
      WfProcessIterator it2 = ea.get_iterator_process();
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ "+eb.toExpression());
      report(System.err,
               eb,
               it1.get_next_n_sequence(0),
               it2.get_next_n_sequence(0),
               false);
      System.out.println("---");
      /**/
      eb = new ProcessIteratorExpressionBuilderDODS(p);
      eb.addProcessDefIdEquals("basic")
         .and()
         .not()
         .addPackageIdEquals("test_bs")
         /**/
         .and()
         .addStateStartsWith("open")/**/;
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ "+eb.toExpression());
      report(System.err,
               eb,
               it1.get_next_n_sequence(0),
               it2.get_next_n_sequence(0),
               false);
      /**/
      System.out.println("---");
      /**/
      eb = new ProcessIteratorExpressionBuilderDODS(p);
      eb.addExpression(new ProcessIteratorExpressionBuilderDODS(p)
         .addProcessDefIdEquals("Game")
         /**/
         .and()
         .addVariableGreaterThan("game_cycles", 2)/**/)
         .or()
         .addExpression(new ProcessIteratorExpressionBuilderDODS(p).addProcessDefIdEquals("basic")
            .and()
            .addStateStartsWith("closed"))
            .setOrderByCreatedTime(ExpressionBuilder.ORDER_DESCENDING);
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ "+eb.toExpression());
      report(System.err,
               eb,
               it1.get_next_n_sequence(0),
               it2.get_next_n_sequence(0),
               false);
      /**/
      System.out.println("---");
      /**/
      eb = new ProcessIteratorExpressionBuilderDODS(p);
      eb.not().addProcessDefIdEquals("basic")
      /**/
      .and().addRequesterUsernameEquals("qq")/**/;
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ "+eb.toExpression());
      report(System.err,
               eb,
               it1.get_next_n_sequence(0),
               it2.get_next_n_sequence(0),
               false);
      /**/
   }

   public void testActivityIteratorExpressionBuilder() throws Exception {
      /**/
      ActivityIteratorExpressionBuilder eb = new ActivityIteratorExpressionBuilderDODS(p);
      eb.not()
         .addPackageIdEquals("test_bs")
         .or()
         .addMgrNameEquals("test_bs#1#basic");
      WfActivityIterator it1 = ea.get_iterator_activity();
      WfActivityIterator it2 = ea.get_iterator_activity();
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ "+eb.toExpression());
      report(System.err,
               eb,
               it1.get_next_n_sequence(0),
               it2.get_next_n_sequence(0),
               false);
      System.out.println("---");
      /**/
      eb = new ActivityIteratorExpressionBuilderDODS(p);
      eb.addProcessDefIdEquals("basic")
         .and()
         .not()
         .addPackageIdEquals("test_bs")
         /**/
         .and()
         .addStateStartsWith("open")/**/;
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ "+eb.toExpression());
      report(System.err,
               eb,
               it1.get_next_n_sequence(0),
               it2.get_next_n_sequence(0),
               false);
      /**/
      System.out.println("---");
      /**/
      eb = new ActivityIteratorExpressionBuilderDODS(p);
      eb.addExpression(new ActivityIteratorExpressionBuilderDODS(p)
         .addProcessDefIdEquals("Game")
         /**/
         .and()
         .addProcessVariableLessThan("game_cycles", 2)/**/)
         .or()
         .addExpression(new ActivityIteratorExpressionBuilderDODS(p).addProcessDefIdEquals("basic")
            .and()
            .addProcessStateStartsWith("closed"));
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE* / "+eb.toExpression());
      report(System.err,
               eb,
               it1.get_next_n_sequence(0),
               it2.get_next_n_sequence(0),
               false);
      /**/
      System.out.println("---");
      /**/
      eb = new ActivityIteratorExpressionBuilderDODS(p);
      eb.not().addProcessDefIdEquals("basic")
      /**/
      .and().addProcessRequesterIdEquals("qq")/**/;
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE* / "+eb.toExpression());
      report(System.err,
               eb,
               it1.get_next_n_sequence(0),
               it2.get_next_n_sequence(0),
               false);
      /**/
      System.out.println("---");
      /**/
      eb = new ActivityIteratorExpressionBuilderDODS(p);
      eb.not().addProcessDefIdEquals("basic")
      /**/
      .and().not().addProcessRequesterIdEquals("qq")/**/;
      it1.set_query_expression(eb.toExpression());
      it2.set_query_expression("/*FORCE*/ "+eb.toExpression());
      report(System.err,
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
      if (a.length != b.length) {
         throw new Exception("Results lengths do not match:"
                             + a.length 
                             +" != "
                             + b.length);
      }
      _out_.println(eb);
      _out_.println("length of sequence:" + a.length);
      if (0 == a.length) { throw new Exception("empty array"); }
      if (itemsToo) {
         for (int i = 0; i < a.length; i++) {
            _out_.println("\t" + i + ":" + a[i]);
         }
      }
   }

   private void executeInstances(String user, int cnt) {
      ExecutionAdministration lea = Shark.getInstance()
         .getAdminInterface()
         .getExecutionAdministration();
      try {
         AdminMisc am = Shark.getInstance()
            .getAdminInterface()
            .getAdminMisc();
         lea.connect(user, "enhydra", "shark", "");
         Map cntxt = new HashMap();
 		 Map cntxt1 = new HashMap();
         cntxt.put("game_cycles", new Long(cnt));
		 cntxt1.put("proceed1", new Boolean(true));
         for (int i = 0; i < cnt; ++i) {
            WfProcess proc = lea.getProcessMgr("test_bs", "Game")
               .create_process(null);
            proc.set_process_context(cntxt);
            proc.start();
			proc = lea.getProcessMgr("test_bs", "domath")
			   .create_process(null);
			proc.set_process_context(cntxt1);
			proc.start();
            lea.getProcessMgr("test_js", "basic")
               .create_process(null)
               .start();
            lea.getProcessMgr("test_js", "manufacturing")
               .create_process(null)
               .start();
            lea.getProcessMgr("test_bs", "manufacturing")
               .create_process(null)
               .start();
         }
         WfResource res = lea.getResource(user);
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
                     } else {
                        for (int k = 0; k < acts.length; k++) {
                           WfActivity _aha_ = acts[k];
                           System.err.println(":::"
                                              + am.getActivityResourceUsername(_prc_.key(),
                                                                               _aha_.key()));
                        }
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
                     if ((ex instanceof RootException)
                         && (m1.equals(ex.getMessage())
                             || m2.equals(ex.getMessage()) || (ex.getMessage() != null && ex.getMessage()
                            .startsWith(m3)))) {
                        System.err.println("failed: yet ignoring "
                                           + ex.getMessage());
                        continue;
                     }
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
      } catch (RootException re) {
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
      PackageAdministration pa = Shark.getInstance()
         .getAdminInterface()
         .getPackageAdministration();
      RepositoryMgr rm = Shark.getInstance().getRepositoryManager();
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
               pa.openPackage(xpdlName);
            }
         } catch (Throwable _) {}
      }
   }

   private void createUsers() {
      UserGroupAdministration uga = Shark.getInstance()
         .getAdminInterface()
         .getUserGroupAdministration();
      try {
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
      }
   }
}