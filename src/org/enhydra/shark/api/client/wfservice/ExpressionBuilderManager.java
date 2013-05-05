/*
 * ExpressionBuilderManager.java
 */
package org.enhydra.shark.api.client.wfservice;

import org.enhydra.shark.api.common.ActivityIteratorExpressionBuilder;
import org.enhydra.shark.api.common.AssignmentIteratorExpressionBuilder;
import org.enhydra.shark.api.common.EventAuditIteratorExpressionBuilder;
import org.enhydra.shark.api.common.ProcessIteratorExpressionBuilder;
import org.enhydra.shark.api.common.ProcessMgrIteratorExpressionBuilder;
import org.enhydra.shark.api.common.ResourceIteratorExpressionBuilder;

/**
 * Interface ExpressionBuilderManager
 * 
 * @author A. Zeneski
 * @version 0.1
 */
public interface ExpressionBuilderManager {

   public ActivityIteratorExpressionBuilder getActivityIteratorExpressionBuilder();

   public AssignmentIteratorExpressionBuilder getAssignmentIteratorExpressionBuilder();

   public EventAuditIteratorExpressionBuilder getEventAuditIteratorExpressionBuilder();

   public ProcessIteratorExpressionBuilder getProcessIteratorExpressionBuilder();

   public ProcessMgrIteratorExpressionBuilder getProcessMgrIteratorExpressionBuilder();

   public ResourceIteratorExpressionBuilder getResourceIteratorExpressionBuilder();
}