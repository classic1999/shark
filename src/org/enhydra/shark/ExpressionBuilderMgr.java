package org.enhydra.shark;

import org.enhydra.shark.api.client.wfservice.ExpressionBuilderManager;
import org.enhydra.shark.api.common.ActivityIteratorExpressionBuilder;
import org.enhydra.shark.api.common.AssignmentIteratorExpressionBuilder;
import org.enhydra.shark.api.common.EventAuditIteratorExpressionBuilder;
import org.enhydra.shark.api.common.ProcessIteratorExpressionBuilder;
import org.enhydra.shark.api.common.ProcessMgrIteratorExpressionBuilder;
import org.enhydra.shark.api.common.ResourceIteratorExpressionBuilder;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.expressionbuilders.ActivityIteratorExpressionBuilderDODS;
import org.enhydra.shark.expressionbuilders.AssignmentIteratorExpressionBuilderDODS;
import org.enhydra.shark.expressionbuilders.ProcessIteratorExpressionBuilderDODS;
import org.enhydra.shark.expressionbuilders.ProcessMgrIteratorExpressionBuilderDODS;
import org.enhydra.shark.expressionbuilders.ResourceIteratorExpressionBuilderDODS;

/**
 * Class ExpressionBuilderMgr
 * 
 * @author Sasa Bojanic
 */
public class ExpressionBuilderMgr implements ExpressionBuilderManager {

   private CallbackUtilities cus;

   protected ExpressionBuilderMgr() {
      this.cus = SharkEngineManager.getInstance().getCallbackUtilities();
   }

   public ActivityIteratorExpressionBuilder getActivityIteratorExpressionBuilder() {
      return new ActivityIteratorExpressionBuilderDODS(cus.getProperties());
   }

   public AssignmentIteratorExpressionBuilder getAssignmentIteratorExpressionBuilder() {
      return new AssignmentIteratorExpressionBuilderDODS(cus.getProperties());
   }

   public EventAuditIteratorExpressionBuilder getEventAuditIteratorExpressionBuilder() {
      throw new RuntimeException("Not implemented yet!");
      //return new EventAuditIteratorExpressionBuilderDODS(cus.getProperties());
   }

   public ProcessIteratorExpressionBuilder getProcessIteratorExpressionBuilder() {
      return new ProcessIteratorExpressionBuilderDODS(cus.getProperties());
   }

   public ProcessMgrIteratorExpressionBuilder getProcessMgrIteratorExpressionBuilder() {
      return new ProcessMgrIteratorExpressionBuilderDODS(cus.getProperties());

   }

   public ResourceIteratorExpressionBuilder getResourceIteratorExpressionBuilder() {
      return new ResourceIteratorExpressionBuilderDODS(cus.getProperties());
   }
}