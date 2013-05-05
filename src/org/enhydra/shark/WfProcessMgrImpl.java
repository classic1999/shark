package org.enhydra.shark;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfmodel.InvalidRequester;
import org.enhydra.shark.api.client.wfmodel.NotEnabled;
import org.enhydra.shark.api.client.wfmodel.RequesterRequired;
import org.enhydra.shark.api.client.wfmodel.TransitionNotAllowed;
import org.enhydra.shark.api.client.wfmodel.process_mgr_stateType;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.instancepersistence.PersistenceException;
import org.enhydra.shark.api.internal.instancepersistence.ProcessMgrPersistenceInterface;
import org.enhydra.shark.api.internal.working.WfProcessInternal;
import org.enhydra.shark.api.internal.working.WfProcessMgrInternal;
import org.enhydra.shark.api.internal.working.WfRequesterInternal;
import org.enhydra.shark.xpdl.XMLCollectionElement;
import org.enhydra.shark.xpdl.XPDLConstants;
import org.enhydra.shark.xpdl.elements.FormalParameter;
import org.enhydra.shark.xpdl.elements.FormalParameters;
import org.enhydra.shark.xpdl.elements.WorkflowProcess;

/**
 * WfProcessMgrImpl - Workflow Process Manager implementation
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public class WfProcessMgrImpl implements WfProcessMgrInternal {

   private WorkflowProcess wp;

   private String packageId;
   private String processDefinitionId;

   private String name;

   private process_mgr_stateType state=process_mgr_stateType.enabled;

   Map contextSignature;
   Map resultSignature;
   Map inputSignature;

   private String version;
   private long created;

   private boolean justCreated=false;

   /**
    * Creates new WfProcessMgrImpl
    * @param pkgId The Id of package where process definition exists.
    * @param pDefId The Id of process definition.
    */
   protected WfProcessMgrImpl(SharkTransaction t,String pkgId,String version,String pDefId) throws BaseException {
      this.justCreated = true;
      this.name=SharkUtilities.createProcessMgrKey(pkgId,version,pDefId);
      if (this.name==null) {
         SharkEngineManager.getInstance().getCallbackUtilities().error("ERROR - MGR NAME NULL WHILE CRE OF PROCMGR");
         throw new BaseException ("Problems while determining process mgr name");
      }

      this.packageId=pkgId;
      this.processDefinitionId=pDefId;
      this.version=version;
      this.created=System.currentTimeMillis();
      try {
         persist(t);
      } catch (TransactionException tme) {
         throw new BaseException(tme);
      }
   }

   /**
    * Used to create object when restoring it from database.
    */
   protected WfProcessMgrImpl (ProcessMgrPersistenceInterface po) {
      restore(po);
   }

   public process_mgr_stateType process_mgr_state (SharkTransaction t) throws BaseException {
      return state;
   }

   public void set_process_mgr_state (SharkTransaction t,process_mgr_stateType new_state) throws BaseException, TransitionNotAllowed {
      if (!state.equals(new_state)) {
         state=new_state;
         try {
            persist(t);
         } catch (Exception ex) {
            throw new BaseException(ex);
         }
         String msg="ProcessDefinition "+toString()+" - the instantiation from process definition is ";
         if (state.equals(process_mgr_stateType.enabled)) {
            msg+="enabled";
         } else {
            msg+="disabled";
         }
         SharkEngineManager.getInstance().getCallbackUtilities().info(msg);
      }
   }

   public final String name (SharkTransaction t) throws BaseException {
      return name;
   }

   public String description (SharkTransaction t) throws BaseException {
      try {
         String desc=getProcessDefinition().getProcessHeader().getDescription();

         return desc;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public String category (SharkTransaction t) throws BaseException {
      try {
         String cat=getProcessDefinition().getAccessLevel();
         return cat;
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public final String version (SharkTransaction t) throws BaseException {
      return version;
   }

   public Map context_signature (SharkTransaction t) throws BaseException {
      try {
         if (contextSignature==null) {
            buildSignatures(t);
         }
         return new HashMap(contextSignature);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public Map result_signature (SharkTransaction t) throws BaseException {
      try {
         if (resultSignature==null) {
            buildSignatures(t);
         }
         return new HashMap(resultSignature);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   public Map input_signature (SharkTransaction t) throws BaseException {
      try {
         if (inputSignature==null) {
            buildSignatures(t);
         }
         return new HashMap(inputSignature);
      } catch (Exception ex) {
         throw new BaseException(ex);
      }
   }

   /**
    * Create a WfProcess object
    */
   public WfProcessInternal create_process (SharkTransaction t,WfRequesterInternal requester) throws BaseException,
      NotEnabled, InvalidRequester, RequesterRequired {
      if (state.value()==process_mgr_stateType._disabled) {
         throw new NotEnabled("Can't create process based on WfProcessMgr '"+this+"' - it is disabled!");
      }
      // This can be changed - we can allow that some processes do not have to have requester
      if (requester == null) {
         throw new RequesterRequired("Can't create process based on WfProcessMgr '"+this+"' - the requester is required!");
      }
      String procId=getNextProcessKey();
      WfProcessInternal process = SharkEngineManager.getInstance().
         getObjectFactory().createProcess(t,this,requester,procId);
      return process;
   }

   public final String process_definition_id(SharkTransaction t) throws BaseException {
      return processDefinitionId;
   }

   public final String package_id(SharkTransaction t) throws BaseException {
      return packageId;
   }

   public String process_definition_name(SharkTransaction t)  throws BaseException {
      try {
         return getProcessDefinition().getName();
      } catch (Exception ex){
         throw new BaseException(ex);
      }
   }

   // Constructs the context/result signatures from the formalParameters
   private void buildSignatures(SharkTransaction t) throws BaseException {
      contextSignature = new HashMap();
      resultSignature = new HashMap();
      inputSignature = new HashMap();

      FormalParameters fps=getProcessDefinition().getFormalParameters();
      Iterator it=fps.toElements().iterator();
      while (it.hasNext()) {
         FormalParameter fp=(FormalParameter)it.next();
         String id = fp.getId();
         String mode=fp.getMode();
         String javaType=SharkUtilities.getJavaClass(fp).getName();

         if (mode.equals(XPDLConstants.FORMAL_PARAMETER_MODE_IN) || mode.equals(XPDLConstants.FORMAL_PARAMETER_MODE_INOUT)) {
            inputSignature.put(id,javaType);
         }
         if (mode.equals(XPDLConstants.FORMAL_PARAMETER_MODE_OUT) || mode.equals(XPDLConstants.FORMAL_PARAMETER_MODE_INOUT)) {
            resultSignature.put(id,javaType);
         }
      }

      Collection dfsAndFPs=getProcessDefinition().getAllVariables().values();

      it=dfsAndFPs.iterator();
      while (it.hasNext()) {
         XMLCollectionElement dfOrFp=(XMLCollectionElement)it.next();
         String id = dfOrFp.getId();

         String javaType=SharkUtilities.getJavaClass(dfOrFp).getName();
         contextSignature.put(id,javaType);
      }

   }

   protected String getNextProcessKey() {
      String pk = SharkUtilities.getNextId(SharkConstants.PROCESS__ID_NAME);
      // calculate 20 spaces for the Long, and do it twice because of
      // the activities Id, also calculate 3 spaces for underscore
      if (pk==null || packageId==null || processDefinitionId==null) {
         SharkEngineManager.getInstance().getCallbackUtilities().error("PK="+pk+", packageId="+packageId+", pDefId="+processDefinitionId);
      }
      pk=pk+"_"+packageId+"_"+processDefinitionId;
      if (pk.length()>100) pk=pk.substring(0,100);
      return pk;
   }

   private WorkflowProcess getProcessDefinition () throws BaseException{
      if (wp==null) {
         wp=SharkUtilities.getWorkflowProcess(
            packageId,
            version,
            processDefinitionId);
      }
      return wp;
   }

   public String toString () {
      return "[name="+name+",version="+version+",pkgId="+packageId+",pDefId="+processDefinitionId+"]";
   }

   /**
    * It is assumed that there can't be two or more processes mgrs
    * having the same package id and process definition id.
    */
   public boolean equals (Object obj) {
      if (!(obj instanceof WfProcessMgrImpl)) return false;
      return (((WfProcessMgrImpl)obj).name.equals(name)); //&& mgr.version().equals(version()));
   }

   public void persist (SharkTransaction t) throws TransactionException {
      try {
         SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager()
            .persist(createAndFillPersistentObject(), this.justCreated, t);
         this.justCreated = false;
      } catch (PersistenceException pe) {
         throw new TransactionException(pe);
      }
   }

   public void delete (SharkTransaction t) throws TransactionException {
      try {
         SharkEngineManager
            .getInstance()
            .getInstancePersistenceManager()
            .deleteProcessMgr(name,t);
      } catch (Exception ex) {
         throw new TransactionException("Exception while deleting ProcessMgr",ex);
      }
   }

   private ProcessMgrPersistenceInterface createAndFillPersistentObject () {
      ProcessMgrPersistenceInterface po=
         SharkEngineManager.getInstance().getInstancePersistenceManager().createProcessMgr();
      fillPersistentObject(po);
      return po;
   }

   private void fillPersistentObject (ProcessMgrPersistenceInterface po) {
      po.setName(this.name);
      po.setPackageId(this.packageId);
      po.setProcessDefinitionId(this.processDefinitionId);
      po.setState(this.state.value());
      po.setVersion(this.version);
      po.setCreated(this.created);
   }

   private void restore (ProcessMgrPersistenceInterface po) {
      this.name=po.getName();
      if (this.name==null) {
         SharkEngineManager.getInstance().getCallbackUtilities().error("ERROR - MGR NAME NULL WHILE REST OF PROCMGR");
      }

      this.packageId=po.getPackageId();
      this.processDefinitionId=po.getProcessDefinitionId();
      this.state=process_mgr_stateType.from_int(po.getState());
      this.version=po.getVersion();
      this.created=po.getCreated();
   }

}
