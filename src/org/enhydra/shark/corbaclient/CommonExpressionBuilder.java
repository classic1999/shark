package org.enhydra.shark.corbaclient;

import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.corba.ExpressionBuilders.ActivityIteratorExpressionBuilder;
import org.enhydra.shark.corba.ExpressionBuilders.ProcessIteratorExpressionBuilder;
import org.enhydra.shark.corba.ExpressionBuilders.ProcessMgrIteratorExpressionBuilder;
import org.enhydra.shark.corba.WorkflowService.ExecutionAdministration;
import org.enhydra.shark.corba.WorkflowService.ExpressionBuilderManager;
import org.enhydra.shark.corba.WorkflowService.WfProcessMgrIterator;
import org.omg.WorkflowModel.WfActivityIterator;
import org.omg.WorkflowModel.WfProcessIterator;

/**
 * @author Sasa Bojanic
 */
public class CommonExpressionBuilder {
   protected ExecutionAdministration ea = null;
   protected ExpressionBuilderManager ebm = null;

   public CommonExpressionBuilder (ExecutionAdministration ea,ExpressionBuilderManager ebm) {
      this.ea=ea;
      this.ebm=ebm;
   }

   public WfProcessMgrIterator getManagersForPackage (String pkgId) throws Exception {
      ProcessMgrIteratorExpressionBuilder eb = ebm.getProcessMgrIteratorExpressionBuilder();
      eb.addPackageIdEquals(pkgId);
      eb.setOrderByCreatedTime(true);
      WfProcessMgrIterator it = ea.get_iterator_processmgr();
      it.set_query_expression(eb.toExpression());
      return it;
   }

   public WfProcessMgrIterator getEnabledOrDisabledManagersForPackage (String pkgId,boolean enabled) throws Exception {
      ProcessMgrIteratorExpressionBuilder eb = ebm.getProcessMgrIteratorExpressionBuilder();
      eb.addPackageIdEquals(pkgId).and();
      eb.setOrderByCreatedTime(true);
      if (!enabled) {
         eb.not();
      }
      eb.addIsEnabled();
      WfProcessMgrIterator it = ea.get_iterator_processmgr();
      it.set_query_expression(eb.toExpression());
      return it;
   }

   public WfProcessIterator getOpenProcessesForManager (String mgrName) throws Exception {
      ProcessIteratorExpressionBuilder eb = ebm.getProcessIteratorExpressionBuilder();
      eb.addMgrNameEquals(mgrName)
         .and()
         .addStateStartsWith(SharkConstants.STATEPREFIX_OPEN);
      eb.setOrderByCreatedTime(false);
      WfProcessIterator it = ea.get_iterator_process();
      it.set_query_expression(eb.toExpression());
      return it;
   }

   public WfProcessIterator getClosedProcessesForManager (String mgrName) throws Exception {
      ProcessIteratorExpressionBuilder eb = ebm.getProcessIteratorExpressionBuilder();
      eb.addMgrNameEquals(mgrName)
         .and()
         .addStateStartsWith(SharkConstants.STATEPREFIX_CLOSED);
      eb.setOrderByCreatedTime(false);
      WfProcessIterator it = ea.get_iterator_process();
      it.set_query_expression(eb.toExpression());
      return it;
   }

   public WfProcessIterator getAllProcessesForManager (String mgrName) throws Exception {
      ProcessIteratorExpressionBuilder eb = ebm.getProcessIteratorExpressionBuilder();
      eb.addMgrNameEquals(mgrName);
      eb.setOrderByCreatedTime(false);
      WfProcessIterator it = ea.get_iterator_process();
      it.set_query_expression(eb.toExpression());
      return it;
   }
   public WfActivityIterator getOpenActivities (String procId) throws Exception {
      ActivityIteratorExpressionBuilder eb = ebm.getActivityIteratorExpressionBuilder();
      eb.addProcessIdEquals(procId)
         .and()
         .addStateStartsWith(SharkConstants.STATEPREFIX_OPEN);
      eb.setOrderByActivatedTime(false);
      WfActivityIterator it = ea.get_iterator_activity();
      it.set_query_expression(eb.toExpression());
      return it;
   }

   public WfActivityIterator getActivitiesForDefinitionAndState (String procId,String definitionId,String stateOrStatePrefix,boolean stateEquality) throws Exception {
      ActivityIteratorExpressionBuilder eb = ebm.getActivityIteratorExpressionBuilder();
      eb.addProcessIdEquals(procId).and().addDefinitionId(definitionId).and();
      if (stateOrStatePrefix!=null && !stateOrStatePrefix.equals("")) {
         eb.and();
         if (stateEquality) {
            eb.addStateEquals(stateOrStatePrefix);
         } else {
            eb.addStateStartsWith(stateOrStatePrefix);
         }
      }
      eb.setOrderByActivatedTime(false);
      WfActivityIterator it = ea.get_iterator_activity();
      it.set_query_expression(eb.toExpression());
      return it;
   }

}
