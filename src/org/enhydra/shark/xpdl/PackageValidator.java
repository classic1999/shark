package org.enhydra.shark.xpdl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.enhydra.shark.utilities.SequencedHashMap;
import org.enhydra.shark.xpdl.elements.Activities;
import org.enhydra.shark.xpdl.elements.Activity;
import org.enhydra.shark.xpdl.elements.ActivitySet;
import org.enhydra.shark.xpdl.elements.ActualParameters;
import org.enhydra.shark.xpdl.elements.Application;
import org.enhydra.shark.xpdl.elements.ApplicationTypes;
import org.enhydra.shark.xpdl.elements.BlockActivity;
import org.enhydra.shark.xpdl.elements.DataField;
import org.enhydra.shark.xpdl.elements.DataType;
import org.enhydra.shark.xpdl.elements.Deadline;
import org.enhydra.shark.xpdl.elements.DeclaredType;
import org.enhydra.shark.xpdl.elements.FormalParameter;
import org.enhydra.shark.xpdl.elements.FormalParameters;
import org.enhydra.shark.xpdl.elements.Join;
import org.enhydra.shark.xpdl.elements.Package;
import org.enhydra.shark.xpdl.elements.PackageHeader;
import org.enhydra.shark.xpdl.elements.Participant;
import org.enhydra.shark.xpdl.elements.RedefinableHeader;
import org.enhydra.shark.xpdl.elements.Responsible;
import org.enhydra.shark.xpdl.elements.Responsibles;
import org.enhydra.shark.xpdl.elements.Split;
import org.enhydra.shark.xpdl.elements.SubFlow;
import org.enhydra.shark.xpdl.elements.Tool;
import org.enhydra.shark.xpdl.elements.Tools;
import org.enhydra.shark.xpdl.elements.Transition;
import org.enhydra.shark.xpdl.elements.TransitionRef;
import org.enhydra.shark.xpdl.elements.TransitionRefs;
import org.enhydra.shark.xpdl.elements.Transitions;
import org.enhydra.shark.xpdl.elements.TypeDeclaration;
import org.enhydra.shark.xpdl.elements.WorkflowProcess;
import org.w3c.dom.Document;

/**
 *  Represents coresponding element from XPDL schema.
 *
 *  @author Sasa Bojanic
 */
public class PackageValidator {

   protected static final String CURRENT_XPDL_VERSION="1.0";

   protected XMLInterface xmlInterface;
   protected Package pkg;
   protected boolean getExistingSchemaValidationErrors;
   protected boolean checkExternalPackages;
   protected boolean allowUndefinedStart;
   protected boolean allowUndefinedEnd;

   protected Map xpdlSchemaValidationErrors=new HashMap();
   protected Map graphsConnectionErrors=new HashMap();
   protected Map basicGraphConnectionErrors=new HashMap();
   protected Map graphsConformanceErrors=new HashMap();
   protected Map basicGraphsConformanceErrors=new HashMap();
   protected Map logicErrors=new HashMap();
   protected Map basicLogicErrors=new HashMap();

   public Map getXPDLSchemaValidationErrors () {
      return xpdlSchemaValidationErrors;
   }

   public Map getGraphsConnectionErrors (XMLComplexElement pkgOrWpOrAs) {
      return (Map)graphsConnectionErrors.get(pkgOrWpOrAs);
   }

   public String getBasicGraphConnectionError (XMLComplexElement pkgOrWpOrAs) {
      return (String)basicGraphConnectionErrors.get(pkgOrWpOrAs);
   }

   public Map getGraphConformanceErrors (XMLComplexElement pkgOrWpOrAs) {
      return (Map)graphsConformanceErrors.get(pkgOrWpOrAs);
   }

   public List getBasicGraphConformanceErrors (XMLComplexElement pkgOrWpOrAs) {
      return (List)basicGraphsConformanceErrors.get(pkgOrWpOrAs);
   }

   public Map getLogicErrors (XMLComplexElement pkgOrWpOrAs) {
      return (Map)logicErrors.get(pkgOrWpOrAs);
   }

   public String getBasicLogicError (XMLComplexElement pkgOrWpOrAs) {
      return (String)basicLogicErrors.get(pkgOrWpOrAs);
   }

   public PackageValidator(XMLInterface xmlInterface,Package pkg, boolean getExistingSchemaValidationErrors,
                           boolean checkExternalPackages,boolean allowUndefinedStart,
                           boolean allowUndefinedEnd) {
      this.xmlInterface=xmlInterface;
      this.pkg=pkg;
      this.getExistingSchemaValidationErrors=getExistingSchemaValidationErrors;
      this.checkExternalPackages=checkExternalPackages;
      this.allowUndefinedStart=allowUndefinedStart;
      this.allowUndefinedEnd=allowUndefinedEnd;
   }

   public boolean validateAll (boolean fullCheck) {
      try {
         boolean isValid=validateAgainstXPDLSchema();
         if (fullCheck || isValid) {
            isValid=checkPackage(fullCheck) && isValid;
         }
         if (fullCheck || isValid) {
            isValid=checkGraphConnections(fullCheck) && isValid;
         }
         if (fullCheck || isValid) {
            isValid=checkGraphConformance(fullCheck) && isValid;
         }

         return isValid;
      } catch (Exception ex) {
         ex.printStackTrace();
         return false;
      }
   }

   //********************* validation against XPDL schema *********************
   public boolean validateAgainstXPDLSchema () {
      if (getExistingSchemaValidationErrors) {
         xpdlSchemaValidationErrors=xmlInterface.getParsingErrorMessages();
         if (xpdlSchemaValidationErrors.size()>0) {
            return false;
         } else {
            return true;
         }
      }
      try {
         Document document = null;

         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder dbuilder = dbf.newDocumentBuilder();
         document = dbuilder.newDocument();
         ByteArrayOutputStream baos=new ByteArrayOutputStream();

         // The extended attributes for all package elements must
         // be updated if current package is not externally
         // referenced package.
         //Save.updateExtendedAttributesForWorkflowProcesses();

         // Here we get all document elements
         XMLUtil.toXML(document, pkg);

         // Use a Transformer for output
         TransformerFactory tFactory =
            TransformerFactory.newInstance();
         Transformer transformer = tFactory.newTransformer();
         transformer.setOutputProperty("indent","yes");
         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","4");
         transformer.setOutputProperty("encoding","UTF-8");
         DOMSource source = new DOMSource(document);
         StreamResult result = new StreamResult(baos);
         transformer.transform(source,result);

         xmlInterface.clearParserErrorMessages();
         xmlInterface.parseDocument(baos.toString("UTF8"),false);
         baos.close();
         xpdlSchemaValidationErrors=xmlInterface.getParsingErrorMessages();
         if (xpdlSchemaValidationErrors.size()>0) {
            return false;
         }
      } catch (Exception ex) {
         return false;
      }
      return true;

   }
   //********************* Logic checking **************************************
   public boolean checkPackage (boolean fullCheck) {
      Map les=new HashMap();
      logicErrors.put(pkg,les);
      basicLogicErrors.remove(pkg);

      boolean isPackageValid=true;
      boolean invalidId=false;
      if (!isIdValid(pkg.getId())) {
         isPackageValid=false;
         invalidId=true;
         les.put(pkg,XMLUtil.getLanguageDependentString("ErrorPackageIdIsNotValid"));
      }
      
      if (fullCheck || isPackageValid) {
         checkPackageHeader(fullCheck);
      }
      if (fullCheck || isPackageValid) {
         isPackageValid=checkRedefinableHeader(pkg,fullCheck) && isPackageValid;;
      }
      if (fullCheck || isPackageValid) {
         isPackageValid=checkConformanceClass(fullCheck) && isPackageValid;;
      }
      if (fullCheck || isPackageValid) {
         isPackageValid=checkScript(fullCheck) && isPackageValid;;
      }
      if ((fullCheck || isPackageValid) && checkExternalPackages) {
         isPackageValid=checkExternalPackages(fullCheck) && isPackageValid;;
      }
      if (fullCheck || isPackageValid) {
         isPackageValid=checkCollection("TypeDeclarations",pkg,fullCheck) && isPackageValid;;
      }
      if (fullCheck || isPackageValid) {
         isPackageValid=checkCollection("Participants",pkg,fullCheck) && isPackageValid;;
      }
      if (fullCheck || isPackageValid) {
         isPackageValid=checkCollection("Applications",pkg,fullCheck) && isPackageValid;;
      }
      if (fullCheck || isPackageValid) {
         isPackageValid=checkCollection("DataFields",pkg,fullCheck) && isPackageValid;;
      }
      boolean areProcessesValid=true;
      if (fullCheck || isPackageValid) {
         areProcessesValid=checkCollection("WorkflowProcesses",pkg,fullCheck);
         isPackageValid=areProcessesValid && isPackageValid;;
      }
      if (!isPackageValid) {
         if (invalidId) {
            basicLogicErrors.put(pkg,XMLUtil.getLanguageDependentString("ErrorPackageIdIsNotValid"));
         } else if (!areProcessesValid){
            basicLogicErrors.put(pkg,XMLUtil.getLanguageDependentString("ErrorOneOrMoreProcessesHaveLogicErrors"));
         } else {
            basicLogicErrors.put(pkg,les.values().toArray()[0]);
         }
      }
      return isPackageValid;
   }

   public boolean checkPackageHeader (boolean fullCheck) {
      PackageHeader phdr=pkg.getPackageHeader();
      String xpdlv="XPDLVersion";
      if (!phdr.getXPDLVersion().equals(CURRENT_XPDL_VERSION)) {
         Map les=getLogicErrors(pkg);
         les.put(phdr,XMLUtil.getLanguageDependentString("ErrorInvalidXPDLVersion"));
         return false;
      } else {
         return true;
      }
   }

   public boolean checkRedefinableHeader (XMLComplexElement pkgOrWp,boolean fullCheck) {
      boolean isValid=true;
      RedefinableHeader rh=(RedefinableHeader)pkgOrWp.get("RedefinableHeader"); //Harald Meister: use argument instead of package
      Iterator rspns=((Responsibles)rh.get("Responsibles")).toElements().iterator();
      while (rspns.hasNext()) {
         Responsible r=(Responsible)rspns.next();
         String rv=r.toValue();         
         Participant p;
         if (pkgOrWp instanceof Package) {
            p=XMLUtil.getPackage(r).getParticipant(rv);
         } else {
            p=XMLUtil.getWorkflowProcess(r).getParticipant(rv);
            if (p==null) {
               p=XMLUtil.getPackage(r).getParticipant(rv);
            }
         }
         if (p==null) {
            List l=XMLUtil.getAllExternalPackageIds(xmlInterface, pkg);
            Iterator itp=l.iterator();
            while (itp.hasNext()) {
               Package pkg=xmlInterface.getPackageById((String)itp.next());
               if (pkg!=null) {
                  p=pkg.getParticipant(rv);
                  if (p!=null) {
                     break;
                  }
               }
            }
         }
         if (p==null) {
            isValid=false;
            Map les=getLogicErrors(pkgOrWp);
            les.put(rh,XMLUtil.getLanguageDependentString("ErrorOneOrMoreResponsibleParticipantsDoNotExist"));
            break;
         }
      }
      return isValid;
   }

   public boolean checkConformanceClass (boolean fullCheck) {
      return true;
   }

   public boolean checkScript(boolean fullCheck) {
      //
      return true;
   }

   public boolean checkExternalPackages(boolean fullCheck) {
      boolean isValid=true;
      Map les=getLogicErrors(pkg);
      Iterator it=pkg.getExternalPackageIds().iterator();
      while (it.hasNext() && (fullCheck || isValid)) {
         Package p=(Package)xmlInterface.getPackageById((String)it.next());
         PackageValidator pv=new PackageValidator(xmlInterface,p,
                                                  getExistingSchemaValidationErrors,false,allowUndefinedStart,
                                                  allowUndefinedEnd);
         if (!pv.validateAll(false)) {
            isValid=false;
            if (les!=null) {
               les.put(p,XMLUtil.getLanguageDependentString("ErrorInvalidExternalPackage"));
            }
         }
      }
      return isValid;
   }

   public boolean checkCollection (String colName,XMLComplexElement cOwner,boolean fullCheck) {
      boolean isValid=true;
      Iterator it=((XMLCollection)cOwner.get(colName)).
         toElements().iterator();
      while (it.hasNext() && (fullCheck || isValid)) {
         isValid=checkCollectionElement((XMLCollectionElement)it.next(),fullCheck) && isValid;
      }
      return isValid;
   }

   public boolean checkCollectionElement (XMLCollectionElement ce,boolean fullCheck) {
      boolean isValid=true;
      if (!isIdValid(ce.getId())) {
         isValid=false;
         XMLElement firstOwner=ce.getParent().getParent();
         Map les;
         if (firstOwner instanceof ApplicationTypes) {
            les=getLogicErrors((XMLComplexElement)((ApplicationTypes)firstOwner).getParent().getParent().getParent());
         } else {
            les=getLogicErrors((XMLComplexElement)firstOwner);
         }
         les.put(ce,XMLUtil.getLanguageDependentString("ErrorIdIsNotValid"));
      }
      if (fullCheck || isValid) {
         if (!isUniqueId((XMLCollection)ce.getParent(),ce.getId())) {
            isValid=false;
            XMLElement firstOwner=ce.getParent().getParent();
            Map les;
            if (firstOwner instanceof ApplicationTypes) {
               les=getLogicErrors((XMLComplexElement)((ApplicationTypes)firstOwner).getParent().getParent());
            } else {
               les=getLogicErrors((XMLComplexElement)firstOwner);
            }
            String msg=(String)les.get(ce);
            msg=prepareMessageString(msg);
            msg=msg+XMLUtil.getLanguageDependentString("ErrorIdIsNotUnique");
            les.put(ce,msg);
         }
      }
      if (fullCheck || isValid) {
         if (ce instanceof TypeDeclaration) {
            isValid=checkTypeDeclaration((TypeDeclaration)ce,fullCheck) && isValid;
         } else if (ce instanceof Participant) {
            isValid=checkParticipant((Participant)ce,fullCheck) && isValid;
         } else if (ce instanceof Application) {
            isValid=checkApplication((Application)ce,fullCheck) && isValid;
         } else if (ce instanceof DataField) {
            isValid=checkDataField((DataField)ce,fullCheck) && isValid;
         } else if (ce instanceof FormalParameter) {
            isValid=checkFormalParameter((FormalParameter)ce,fullCheck) && isValid;
         } else if (ce instanceof WorkflowProcess) {
            isValid=checkWorkflowProcess((WorkflowProcess)ce,fullCheck) && isValid;
         } else if (ce instanceof ActivitySet) {
            isValid=checkActivitySet((ActivitySet)ce,fullCheck) && isValid;
         } else if (ce instanceof Activity) {
            isValid=checkActivity((Activity)ce,fullCheck) && isValid;
         } else if (ce instanceof Transition) {
            isValid=checkTransition((Transition)ce,fullCheck) && isValid;
         }
      }
      return isValid;
   }

   public boolean checkTypeDeclaration(TypeDeclaration td,boolean fullCheck) {
      //
      return true;
   }

   public boolean checkParticipant (Participant p,boolean fullCheck) {
      //
      return true;
   }

   public boolean checkApplication (Application app,boolean fullCheck) {
      boolean isValid=true;
      if (app.getApplicationTypes().getChoosen() instanceof FormalParameters) {
         FormalParameters fps=app.getApplicationTypes().getFormalParameters();
         Iterator it=fps.toElements().iterator();
         while (it.hasNext() && (fullCheck || isValid)) {
            isValid=checkCollectionElement((XMLCollectionElement)it.next(),fullCheck) && isValid;
         }
      }
      return isValid;
   }

   public boolean checkDataField (DataField df,boolean fullCheck) {
      //
      return checkDataType(df,fullCheck);
   }

   public boolean checkFormalParameter (FormalParameter fp,boolean fullCheck) {
      //
      return checkDataType(fp,fullCheck);
   }

   public boolean checkDataType (XMLCollectionElement dfOrFp,boolean fullCheck) {
      boolean isValid=true;

      DataType xpdlType=(DataType)dfOrFp.get("DataType");
      XMLElement type=xpdlType.getDataTypes().getChoosen();
      if (type instanceof DeclaredType) {
         TypeDeclaration td=pkg.getTypeDeclaration(((DeclaredType)type).getId());
         if (td==null) {
            isValid=false;
            XMLElement firstOwner=dfOrFp.getParent().getParent();
            Map les;
            if (dfOrFp instanceof DataField) {
               les=getLogicErrors((XMLComplexElement)firstOwner);
            } else {
               if (firstOwner instanceof ApplicationTypes) {
                  les=getLogicErrors((XMLComplexElement)((ApplicationTypes)firstOwner).getParent().getParent());
               } else {
                  les=getLogicErrors((XMLComplexElement)firstOwner);
               }
            }
            String msg=(String)les.get(dfOrFp);
            msg=prepareMessageString(msg);
            msg=msg+XMLUtil.getLanguageDependentString("ErrorNonExistingDeclaredTypeReference");
            les.put(dfOrFp,msg);
         }
      }
      return isValid;
   }

   public boolean checkWorkflowProcess (WorkflowProcess wp,boolean fullCheck) {
      Map les=new HashMap();
      logicErrors.put(wp,les);
      basicLogicErrors.remove(wp);

      boolean notDefined=false;
      boolean isValid=checkProcessHeader(wp,fullCheck);
      if (fullCheck || isValid) {
         isValid=checkRedefinableHeader(wp,fullCheck) && isValid;
      }
      if (fullCheck || isValid) {
         isValid=checkCollection("FormalParameters",wp,fullCheck) && isValid;
      }
      if (fullCheck || isValid) {
         isValid=checkCollection("DataFields",wp,fullCheck) && isValid;
      }
      if (fullCheck || isValid) {
         isValid=checkCollection("Participants",wp,fullCheck) && isValid;
      }
      if (fullCheck || isValid) {
         isValid=checkCollection("Applications",wp,fullCheck) && isValid;
      }
      if (fullCheck || isValid) {
         isValid=checkCollection("ActivitySets",wp,fullCheck) && isValid;
      }
      if (fullCheck || isValid) {
         if (wp.getActivities().toElements().size()==0) {
            isValid=false;
            notDefined=true;
            les.put(wp,XMLUtil.getLanguageDependentString("ErrorProcessIsNotDefined"));
         } else {
            isValid=checkCollection("Activities",wp,fullCheck) && isValid;
         }
      }
      if (fullCheck || isValid) {
         isValid=checkCollection("Transitions",wp,fullCheck) && isValid;
      }
      if (!isValid) {
         basicLogicErrors.put(wp,les.values().toArray()[0]);
         Map pkgles=getLogicErrors(pkg);
         if (pkgles!=null) {
            if (notDefined) {
               pkgles.put(wp,XMLUtil.getLanguageDependentString("ErrorProcessIsNotDefined"));
            } else {
               pkgles.put(wp,XMLUtil.getLanguageDependentString("ErrorProcessContainsOneOrMoreLogicErrors"));
            }
         }
      }
      return isValid;
   }

   public boolean checkProcessHeader(WorkflowProcess wp,boolean fullCheck) {
      //
      return true;
   }

   public boolean checkActivitySet (ActivitySet as,boolean fullCheck) {
      Map les=new HashMap();
      logicErrors.put(as,les);
      basicLogicErrors.remove(as);
      boolean isValid=true;
      boolean notDefined=false;
      if (as.getActivities().toElements().size()==0) {
         isValid=false;
         notDefined=true;
         les.put(as,XMLUtil.getLanguageDependentString("ErrorBlockActivityIsNotDefined"));
      } else {
         isValid=checkCollection("Activities",as,fullCheck);
      }
      if (fullCheck || isValid) {
         isValid=checkCollection("Transitions",as,fullCheck) && isValid;
      }
      if (!isValid) {
         basicLogicErrors.put(as,getLogicErrors(as).values().toArray()[0]);
         Map wples=getLogicErrors(XMLUtil.getWorkflowProcess(as));
         Activity blockActivity=findBlockActivity(as);
         if (!(wples==null || blockActivity==null)) {
            if (notDefined) {
               wples.put(blockActivity,XMLUtil.getLanguageDependentString("ErrorBlockActivityIsNotDefined"));
            } else {
               wples.put(blockActivity,XMLUtil.getLanguageDependentString("ErrorInnerLogicError"));
            }
         } else if (wples!=null) {
            wples.put(as,XMLUtil.getLanguageDependentString("ErrorBlockActivityIsNotDefined"));
         }
      }
      return isValid;
   }

   public boolean checkActivity (Activity act,boolean fullCheck) {
      // check performer
      boolean isValid=checkActivityPerformer(act,fullCheck);

      if (!(fullCheck || isValid)) {
         return false;
      }

      // if this is a block activity
      int actType=act.getActivityType();
      if (actType==XPDLConstants.ACTIVITY_TYPE_BLOCK) {
         isValid=checkActivityBlock(act,fullCheck) && isValid;
      } else if (actType==XPDLConstants.ACTIVITY_TYPE_TOOL) {
         isValid=checkActivityTools(act,fullCheck) && isValid;
      } else if (actType==XPDLConstants.ACTIVITY_TYPE_SUBFLOW) {
         isValid=checkActivitySubFlow(act,fullCheck) && isValid;
      }

      if (!(fullCheck || isValid)) {
         return false;
      }

      Transitions trans=(Transitions)((XMLCollectionElement)act.getParent().getParent()).get("Transitions");
      Set outTrans=XMLUtil.getOutgoingTransitions(act);
      Set inTrans=XMLUtil.getIncomingTransitions(act);

      // check deadlines
      isValid=checkActivityDeadlines(act,fullCheck) && isValid;
      if (!(fullCheck || isValid)) {
         return false;
      }

      Map les=getLogicErrors((XMLComplexElement)act.getParent().getParent());
      String msg=(String)les.get(act);
      // Split type and no. of outgoing transitions
      Split split=XMLUtil.getSplit(act);
      if ((split==null || split.getType().length()==0) && outTrans.size()>1) {
         isValid=false;
         msg=prepareMessageString(msg);
         msg=msg+XMLUtil.getLanguageDependentString("ErrorMultipleOutgoingTransitionsWithoutSplitTypeDefined");
         les.put(act,msg);
      }

      if (!(fullCheck || isValid)) {
         return false;
      }

      // TransitionRefs size must be the same as the one of outgoing transitions
      if (split!=null) {
         TransitionRefs tRfs=split.getTransitionRefs();
         if ((tRfs.size()!=outTrans.size()) && outTrans.size()>1 && !split.getType().equals(XPDLConstants.JOIN_SPLIT_TYPE_AND)) {
            isValid=false;
            msg=prepareMessageString(msg);
            msg=msg+XMLUtil.getLanguageDependentString("ErrorNumberOfActivitiesOutgoingTransitionsAndTransitionRefsIsNotSame");
            les.put(act,msg);
         }
         if (!(fullCheck || isValid)) {
            return false;
         }
         // TransitionRefs must refer to valid transitions.
         Iterator tRefs=tRfs.toElements().iterator();
         boolean invalidTref=false;
         while (tRefs.hasNext()) {
            String transitionId=((TransitionRef)tRefs.next()).getId();
            Transition t=trans.getTransition(transitionId);
            if (t==null || !outTrans.contains(t)) {
               isValid=false;
               invalidTref=true;
            }
         }

         if (invalidTref) {
            msg=prepareMessageString(msg);
            msg=msg+XMLUtil.getLanguageDependentString("ErrorTransitionRefIsNotValid");
            les.put(act,msg);
         }
   
         if (!(fullCheck || isValid)) {
            return false;
         }
      }
      // Join type and no. of incoming transitions
      Join join=XMLUtil.getJoin(act);
      if ((join==null || join.getType().length()==0) && inTrans.size()>1) {
         isValid=false;
         msg=prepareMessageString(msg);
         msg=msg+XMLUtil.getLanguageDependentString(
            "ErrorMultipleIncomingTransitionsWithoutJoinTypeDefined");
         les.put(act,msg);
      }

      if (!(fullCheck || isValid)) {
         return false;
      }

      isValid=checkMultipleOtherwiseOrDefaultExceptionTransitions(act,fullCheck) && isValid;

      return isValid;
   }

   public boolean checkActivityPerformer (Activity act,boolean fullCheck) {
      boolean isValid=true;

      // check performer
      String performer=act.getPerformer().trim();
      // if this is not an No or Tool activity, check peformer
      int actType=act.getActivityType();
      boolean hasToCheck=true;
      if (actType!=XPDLConstants.ACTIVITY_TYPE_NO || actType!=XPDLConstants.ACTIVITY_TYPE_TOOL) {
         hasToCheck=false;
      }
      if (hasToCheck && performer.length()>0) {
         isValid=false;
         Map les=getLogicErrors((XMLComplexElement)act.getParent().getParent());
         String msg=(String)les.get(act);
         msg=prepareMessageString(msg);
         msg=msg+XMLUtil.getLanguageDependentString("ErrorActivityCannotHavePerformer");
         les.put(act,msg);
      }
      return isValid;
   }

   public boolean checkActivityTools (Activity act,boolean fullCheck) {
      boolean isValid=true;
      boolean nonExistingToolReference=false;

      Tools tools=act.getActivityTypes().getImplementation().getImplementationTypes().getTools();
      Iterator it=tools.toElements().iterator();
      while (it.hasNext() && (fullCheck || isValid)) {
         Tool tool=(Tool)it.next();
         String toolID=tool.getId();
         if (toolID==null || toolID.equals("")) {
            isValid=false;
            nonExistingToolReference=true;
         }
         if (!(isValid || fullCheck)) break;

         WorkflowProcess wp=XMLUtil.getWorkflowProcess(act);
         Application app=null;
         if (toolID!=null) {
            app=wp.getApplication(toolID);
            if (app==null) {
               app=pkg.getApplication(toolID);
            }
            if (app==null) {
               List l=XMLUtil.getAllExternalPackageIds(xmlInterface, pkg);
               Iterator ita=l.iterator();
               while (ita.hasNext()) {
                  Package p=xmlInterface.getPackageById((String)ita.next());
                  if (p!=null) {
                     app=p.getApplication(toolID);
                     if (app!=null) {
                        break;
                     }
                  }
               }
            }
         }
         try {
            isValid=checkParameterMappings(tool,app,fullCheck) && isValid;
         } catch (Exception ex) {}
      }

      if (!isValid) {
         Map les=getLogicErrors((XMLComplexElement)act.getParent().getParent());
         String msg=(String)les.get(act);
         msg=prepareMessageString(msg);
         if (nonExistingToolReference) {
            msg+=XMLUtil.getLanguageDependentString("ErrorNonExistingToolReference");
         } else {
            msg+=XMLUtil.getLanguageDependentString("ErrorToolsFormalAndActualParametersDoNotMatch");
         }
         les.put(act,msg);
      }

      return isValid;
   }

   public boolean checkActivitySubFlow (Activity act,boolean fullCheck) {
      boolean isValid=true;
      boolean nonExistingProcessReference=false;
      boolean notAllowedProcessReference=false;
      SubFlow s=act.getActivityTypes().getImplementation().getImplementationTypes().getSubFlow();      
      String subflowID=s.getId();
      if (subflowID==null || subflowID.trim().equals("")) {
         isValid=false;
         nonExistingProcessReference=true;
      }
      
      Package pkg=XMLUtil.getPackage(act);
      WorkflowProcess wp=null;
      if (isValid) {
         wp=pkg.getWorkflowProcess(subflowID);         
         if (wp==null) {
            List l=XMLUtil.getAllExternalPackageIds(xmlInterface, pkg);
            Iterator it=l.iterator();
            while (it.hasNext()) {
               Package p=xmlInterface.getPackageById((String)it.next());
               if (p!=null) {
                  wp=p.getWorkflowProcess(subflowID);
                  if (wp!=null) {
                     break;
                  }
               }
            }
         }
      }
      if ((fullCheck || isValid) && wp!=null) {
         try {
            isValid=checkParameterMappings(s,wp,fullCheck) && isValid;
         } catch (Exception ex) {}
      }

      if (!isValid) {
         Map les=getLogicErrors((XMLComplexElement)act.getParent().getParent());
         String msg=(String)les.get(act);
         msg=prepareMessageString(msg);
         if (nonExistingProcessReference) {
            msg=msg+XMLUtil.getLanguageDependentString("ErrorNonExistingProcessReference");
         } else if (nonExistingProcessReference) {
            msg=msg+XMLUtil.getLanguageDependentString("ErrorNotAllowedProcessReference");
         } else {
            msg=msg+XMLUtil.getLanguageDependentString("ErrorSubFlowFormalAndActualParametersDoNotMatch");
         }
         les.put(act,msg);
      }
      return isValid;
   }

   public boolean checkActivityBlock (Activity act,boolean fullCheck) {
      boolean isValid=true;
      BlockActivity blk=act.getActivityTypes().getBlockActivity();
      String blockId=blk.getBlockId();
      // check if the activity set exists
      ActivitySet as=XMLUtil.getWorkflowProcess(act).getActivitySet(blockId);
      // check if there is activity set with the referenced id and if
      // block activity is inside other block activity, and references it's owner
      if (as==null || act.getParent().getParent().equals(as)) {
         isValid=false;
         Map les=getLogicErrors((XMLComplexElement)act.getParent().getParent());
         String msg=(String)les.get(act);
         msg=prepareMessageString(msg);
         if (as==null) {
            msg=msg+XMLUtil.getLanguageDependentString("ErrorNonExistingActivitySetReference");
         } else {
            msg=msg+XMLUtil.getLanguageDependentString("ErrorNotAllowedActivitySetReference");
         }
         les.put(act,msg);
      }
      return isValid;
   }

   public boolean checkActivityDeadlines (Activity act,boolean fullCheck) {
      boolean isValid=true;

      Collection deadlines=act.getDeadlines().toElements();
      if (deadlines.size()==0) return isValid;

      Iterator dls=deadlines.iterator();
      int syncCount = 0;
      while (dls.hasNext()) {
         Deadline dl=(Deadline)dls.next();
         // TODO: validate condition.
         //XMLElement dc=dl.get("DeadlineCondition");
         if (dl.getExecution().equals(XPDLConstants.EXECUTION_SYNCHR)) {
            syncCount++;
         }
      }

      Map les=getLogicErrors((XMLComplexElement)act.getParent().getParent());
      String msg=(String)les.get(act);
      if (syncCount>1) {
         isValid=false;
         msg=prepareMessageString(msg);
         msg+=XMLUtil.getLanguageDependentString("ErrorActivityCanHaveOnlyOneSynchronousDeadline");
         les.put(act,msg);
      }
      if (!(fullCheck || isValid)) {
         return false;
      }

      /*Iterator it=excOutTrans.iterator();
      while (it.hasNext()) {
         Transition t=(Transition)it.next();
         Condition c=(Condition)t.get("Condition");
         String ct=((Condition)t.get("Condition")).get("Type").
            toValue().toString();
         if (ct.equals(Condition.CONDITION_TYPE_DEFAULTEXCEPTION)) {
            deadlineExceptions.clear();
            break;
         } else if (ct.equals("EXCEPTION")) {
            deadlineExceptions.remove(c.toString().trim());
         }
       }*/

      if (XMLUtil.getExceptionalOutgoingTransitions(act).size()==0) {
         isValid=false;
         msg=prepareMessageString(msg);
         msg+=XMLUtil.getLanguageDependentString(
            "ErrorDeadlineExceptionIsNotHandledByAnyOutgoingTransitionWithExceptionOrDefaultExceptionConditionType");
         les.put(act,msg);
      }
      return isValid;
   }

   public boolean checkMultipleOtherwiseOrDefaultExceptionTransitions (Activity act,boolean fullCheck) {
      Set outTrans=XMLUtil.getOutgoingTransitions(act);
      // Check outgoing transitions
      // do not allow more then 1 transitions of type otherwise or default_exception
      boolean foundOtherwise=false;
      boolean foundMultipleOtherwise=false;
      boolean foundDefaultException=false;
      boolean foundMultipleDefaultException=false;
      Iterator ts=outTrans.iterator();
      while (ts.hasNext()) {
         Transition t=(Transition)ts.next();
         String ct=t.getCondition().getType();
         if (ct.equals(XPDLConstants.CONDITION_TYPE_OTHERWISE)) {
            if (foundOtherwise) {
               foundMultipleOtherwise=true;
               if (foundMultipleDefaultException || !fullCheck) break;
            } else {
               foundOtherwise=true;
            }
         } else if (ct.equals(XPDLConstants.CONDITION_TYPE_DEFAULTEXCEPTION)) {
            if (foundDefaultException) {
               foundMultipleDefaultException=true;
               if (foundMultipleOtherwise || !fullCheck) break;
            } else {
               foundDefaultException=true;
            }
         }
      }

      if (foundMultipleOtherwise || foundMultipleDefaultException) {
         Map les=getLogicErrors((XMLComplexElement)act.getParent().getParent());
         String msg=(String)les.get(act);
         msg=prepareMessageString(msg);
         if (foundMultipleDefaultException && foundMultipleOtherwise) {
            msg=msg+XMLUtil.getLanguageDependentString(
               "ErrorMoreThenOneOTHERWISEAndDEFAULTEXCEPTIONTypeOutgoingTransition");
         } else if (foundMultipleOtherwise) {
            msg=msg+XMLUtil.getLanguageDependentString(
               "ErrorMoreThenOneOTHERWISETypeOutgoingTransition");
         } else if (foundMultipleDefaultException) {
            msg=msg+XMLUtil.getLanguageDependentString(
               "ErrorMoreThenOneDEFAULTEXCEPTIONTypeOutgoingTransition");
         }
         les.put(act,msg);
         return false;
      } else {
         return true;
      }
   }

   public boolean checkParameterMappings (XMLComplexElement toolOrSbflw,
                                          XMLComplexElement appOrWp,boolean fullCheck) {
      FormalParameters fps;
      if (appOrWp instanceof WorkflowProcess) {
         fps=((WorkflowProcess)appOrWp).getFormalParameters();
      } else {
         ApplicationTypes ats=((Application)appOrWp).getApplicationTypes();
         if (ats.getChoosen() instanceof FormalParameters) {
            fps=ats.getFormalParameters();
            // do not check if application is externally defined
         } else {
            return true;
         }
      }
      ActualParameters aps=(ActualParameters)toolOrSbflw.get("ActualParameters");
      int pm=XMLUtil.checkParameterMatching(fps,aps);
      if (pm!=0) {
         return false;
      } else {
         return true;
      }
   }

   public boolean checkTransition (Transition transition,boolean fullCheck) {
      boolean isValid=true;
      Map les=getLogicErrors((XMLComplexElement)transition.getParent().getParent());
      String msg=(String)les.get(transition);
      if (XMLUtil.getFromActivity(transition)==null) {
         isValid=false;
         msg=prepareMessageString(msg);
         msg+=XMLUtil.getLanguageDependentString("ErrorNonExistingFromActivityReference");
      }
      if (XMLUtil.getToActivity(transition)==null) {
         isValid=false;
         msg=prepareMessageString(msg);
         msg+=XMLUtil.getLanguageDependentString("ErrorNonExistingToActivityReference");
      }
      if (!isValid) {
         les.put(transition,msg);
      }
      return isValid;
   }

   public boolean isIdValid (String id) {
      return XMLUtil.isIdValid(id);
   }

   public static boolean isEmpty (String str) {
      if (str==null || str.trim().length()==0) {
         return true;
      } else {
         return false;
      }
   }

   public static boolean isUniqueId (XMLCollection xmlCol,String id) {
      int idCnt=0;
      Iterator it=xmlCol.toElements().iterator();
      while (it.hasNext()) {
         try {
            XMLCollectionElement xmlce=(XMLCollectionElement)it.next();
            String cId=xmlce.getId();
            if (cId.equals(id)) {
               idCnt++;
               if (idCnt>1) {
                  return false;
               }
            }
         } catch (ClassCastException cce) {
            return true;
         }
      }
      return true;
   }

   public static void main(String[] args) {
      try {
         XMLInterfaceForJDK13 xmlI=new XMLInterfaceForJDK13();
         Package pkg=xmlI.parseDocument(args[0],true);
         PackageValidator validator = new PackageValidator(null,pkg,false,
                                                           true,false,false);
         if (validator.validateAll(false)) {
            System.out.println(args[0]+" is a valid XPDL package");
         } else {
            System.out.println(args[0]+" is not a valid XPDL package");
         }
      } catch (Exception ex) {
         ex.printStackTrace();
         System.exit(1);
      }
   }

   /** Used for debug only */
   public static void printIM(boolean[][] im,java.util.List acts) {
      if (im != null) {
         for (int i=0; i<im.length; i++) {
            for (int j=0; j<im[i].length; j++) {
               System.out.print(acts.get(i)+"->"+acts.get(j)+"="+im[i][j]+" ");
            }
            System.out.println();
         }
      } else {
         System.out.println("Passed array is null !!!");
      }
   }

   /** Used for debug only */
   public static void printIM2(boolean[][] im,java.util.List acts) {
      System.out.println("Activities are"+acts);
      if (im != null) {
         for (int i=0; i<im.length; i++) {
            for (int j=0; j<im[i].length; j++) {
               System.out.print(((im[i][j]) ? "1":"0")+" ");
            }
            System.out.println();
         }
      } else {
         System.out.println("Passed array is null !!!");
      }
   }


   //************************** GRAPH CONFORMANCE CHECKING ****************************
   public boolean checkGraphConformance (boolean fullCheck) {
      boolean areGraphsConformant=true;

      Map graphConformanceErrors=new HashMap();
      List basicGraphConformanceErrors=new ArrayList();

      Iterator procs=pkg.getWorkflowProcesses().toElements().iterator();
      while (procs.hasNext()) {
         WorkflowProcess wp=(WorkflowProcess)procs.next();
         if (!checkGraphConformance(wp,fullCheck)) {
            areGraphsConformant=false;
            if (!fullCheck) {
               break;
            }
            String msg="";
            Iterator bces=getBasicGraphConformanceErrors(wp).iterator();
            while (bces.hasNext()) {
               msg=msg+bces.next().toString()+"<br>";
            }
            graphConformanceErrors.put(wp,msg);
         }
      }
      if (!areGraphsConformant) {
         basicGraphConformanceErrors.add(XMLUtil.
                                            getLanguageDependentString("ErrorOneOrMoreProcessesDoNotSatisfyGraphConformance"));
      }
      basicGraphsConformanceErrors.put(pkg,basicGraphConformanceErrors);
      graphsConformanceErrors.put(pkg,graphConformanceErrors);
      return areGraphsConformant;
   }
   /**
    * Checks if graph conforms to the given conformance class.
    * @return true if graph is conformant, false otherwise
    */
   public boolean checkGraphConformance(XMLCollectionElement wpOrAs,boolean fullCheck) {
      Map graphConformanceErrors=new HashMap();
      List basicGraphConformanceErrors=new ArrayList();

      Collection allActs=((XMLCollection)wpOrAs.get("Activities")).toElements();

      Package pkg=XMLUtil.getPackage(wpOrAs);
      String conformanceClass=pkg.getConformanceClass().getGraphConformance();
      // ct=0->NON_BLOCKED, ct=1->LOOP_BLOCKED, ct=2->FULL_BLOCKED, ct=-1->default NON_BLOCKED
      int ct=XMLUtil.getConformanceClassNo(conformanceClass);

      Activities acts=(Activities)wpOrAs.get("Activities");
      SequencedHashMap activities=acts.toElementMap();

      if (activities.size()==0) {
         graphsConformanceErrors.put(wpOrAs,graphConformanceErrors);
         basicGraphsConformanceErrors.put(wpOrAs,basicGraphConformanceErrors);
         return true;
      }

      boolean isGraphConformant=true;

      Set splitActs=XMLUtil.getSplitOrJoinActivities(activities.values(),0);
      Set joinActs=XMLUtil.getSplitOrJoinActivities(activities.values(),1);

      Set noSplitActs=new HashSet(activities.values());
      noSplitActs.removeAll(splitActs);

      GraphChecker gc=null;
      if (ct>0 && (isGraphConformant || fullCheck)) {
         boolean[][] incidenceMatrix=createIncidenceMatrix(activities);
         if (incidenceMatrix==null) {
            basicGraphConformanceErrors.add("Unexpected error");
            graphsConformanceErrors.put(wpOrAs,graphConformanceErrors);
            basicGraphsConformanceErrors.put(wpOrAs,basicGraphConformanceErrors);
            return false;
         }

         gc=new GraphChecker(incidenceMatrix);

         // call method to check loop cycling
         boolean loopError=false;
         if (fullCheck) {
            int[] loopNodes=gc.getCyclicNodes();
            if (loopNodes!=null) {
               isGraphConformant=false;
               loopError=true;
               for (int i=0; i<loopNodes.length; i++) {
                  Activity act=(Activity)activities.get(loopNodes[i]);
                  graphConformanceErrors.put(act,XMLUtil.
                                                getLanguageDependentString("ErrorLoopContainedActivity"));
               }
            }
         } else {
            loopError=gc.isGraphCyclic();
            if (loopError) {
               isGraphConformant=false;
            }
         }
         if (loopError) {
            basicGraphConformanceErrors.add(XMLUtil.getLanguageDependentString("ErrorTheGraphIsCyclic"));
         }
      }
      // Here we check FULL_BLOCK conformance
      if (ct==2 && (isGraphConformant || fullCheck)) {
         // check if there is more then one starting activity
         if (XMLUtil.getStartingActivities(wpOrAs).size()!=1) {
            isGraphConformant=false;
            basicGraphConformanceErrors.add(XMLUtil.getLanguageDependentString(
                                               "ErrorThereMustBeExactlyOneStartingActivityInFullBlockedMode"));
         }
         // check if there is more then one ending activity
         if ((isGraphConformant || fullCheck) && XMLUtil.getEndingActivities(wpOrAs).size()!=1) {
            isGraphConformant=false;
            basicGraphConformanceErrors.add(XMLUtil.getLanguageDependentString(
                                               "ErrorThereMustBeExactlyOneEndingActivityInFullBlockedMode"));
         }

         // check if the number of splits and joins matches
         boolean smerr=false;
         if ((isGraphConformant || fullCheck) && splitActs.size()!=joinActs.size()) {
            if (splitActs.size()>joinActs.size()) {
               basicGraphConformanceErrors.add(XMLUtil.getLanguageDependentString(
                                                  "ErrorTheNumberOfSplitsAndJoinsIsNotTheSame-MoreSplits"));
            } else {
               basicGraphConformanceErrors.add(XMLUtil.getLanguageDependentString(
                                                  "ErrorTheNumberOfSplitsAndJoinsIsNotTheSame-MoreJoins"));
            }
            isGraphConformant=false;
            smerr=true;
         }

         // check for split/join type mismatch
         if ((isGraphConformant || fullCheck) && !smerr) {
            if (getNoOfANDSplitsOrJoins(splitActs,0)!=getNoOfANDSplitsOrJoins(joinActs,1)) {
               basicGraphConformanceErrors.add(XMLUtil.getLanguageDependentString(
                                                  "ErrorOneOrMoreSplitsDoNotHaveCorrespondingJoinBecauseOfTypeMismatch"));
               isGraphConformant=false;
            }
         }
         // first check for correct outgoing transitions
         if (isGraphConformant || fullCheck) {
            Iterator it=splitActs.iterator();
            boolean andSplitError=false;
            boolean xorSplitError=false;
            while (it.hasNext()) {
               Activity act=(Activity)it.next();
               if (XMLUtil.isANDTypeSplitOrJoin(act, 0)) {
                  if (!checkANDSplit(act)) {
                     isGraphConformant=false;
                     andSplitError=true;
                     String msg=(String)graphConformanceErrors.get(act);
                     msg=prepareMessageString(msg);
                     msg=msg+XMLUtil.getLanguageDependentString(
                        "ErrorOneOrMoreConditionalOutgoingTransitions");
                     graphConformanceErrors.put(act,msg);
                     if (!fullCheck) {
                        break;
                     }
                  }
               } else {
                  if (!checkXORSplit(act)) {
                     isGraphConformant=false;
                     xorSplitError=true;
                     String msg=(String)graphConformanceErrors.get(act);
                     msg=prepareMessageString(msg);
                     msg=msg+XMLUtil.getLanguageDependentString(
                        "ErrorMissingOTHERWISETypeOutgoingTransition");
                     graphConformanceErrors.put(act,msg);
                     if (!fullCheck) {
                        break;
                     }
                  }
               }
            }

            // check activities that has only one outgoing transition, if
            // there is condition on it -> report XOR split with conditional
            // transition error
            it=noSplitActs.iterator();
            while (it.hasNext()) {
               Activity act=(Activity)it.next();
               if (!checkXORSplit(act)) {
                  isGraphConformant=false;
                  xorSplitError=true;
                  String msg=(String)graphConformanceErrors.get(act);
                  msg=prepareMessageString(msg);
                  msg=msg+XMLUtil.getLanguageDependentString(
                     "ErrorMissingOTHERWISETypeOutgoingTransition");
                  graphConformanceErrors.put(act,msg);
                  if (!fullCheck) {
                     break;
                  }
               }
            }

            if (andSplitError) {
               basicGraphConformanceErrors.add(XMLUtil.getLanguageDependentString(
                                                  "ErrorOneOrMoreANDSplitsHaveConditionalOutgoingTransitions"));
            }
            if (xorSplitError) {
               basicGraphConformanceErrors.add(XMLUtil.getLanguageDependentString(
                                                  "ErrorOneOrMoreXORSplitsWithConditionalTransitionsDoNotHaveOTHERWISETransition"));
            }
         }

         // now perform search on every split activity for corresponding join activity
         if (isGraphConformant || fullCheck) {
            boolean noCorrespondingJoinError=false;
            Iterator it=splitActs.iterator();
            while (it.hasNext()) {
               Activity act=(Activity)it.next();
               int splitIndex=activities.indexOf(act);
               if (splitIndex==-1) {
                  basicGraphConformanceErrors.add("Unexpected error");
                  isGraphConformant=false;
                  if (!fullCheck) {
                     break;
                  } else {
                     continue;
                  }
               }
               int ji=gc.getJoinIndex(splitIndex);
               // The correspondin join can't be found
               if (ji<0) {
                  isGraphConformant=false;
                  noCorrespondingJoinError=true;
                  String msg=(String)graphConformanceErrors.get(act);
                  msg=prepareMessageString(msg);
                  msg=msg+XMLUtil.getLanguageDependentString("ErrorThereIsNoCorrespondingJoinActivity");
                  graphConformanceErrors.put(act,msg);
                  if (!fullCheck) {
                     break;
                  }
                  // if the join is found and their types are different
                  // the graph is not conformant
               } else {
                  if (XMLUtil.isANDTypeSplitOrJoin(act, 0)!= 
                        XMLUtil.isANDTypeSplitOrJoin((Activity)activities.get(ji), 1)) {
                     isGraphConformant=false;
                     noCorrespondingJoinError=true;
                     String msg=(String)graphConformanceErrors.get(act);
                     msg=prepareMessageString(msg);
                     if (XMLUtil.isANDTypeSplitOrJoin((Activity)act, ji)) {
                        msg=msg+XMLUtil.getLanguageDependentString(
                           "ErrorTheCorrespondingJoinActivityDoesNotHaveTheSameType-ANDXOR");
                     } else {
                        msg=msg+XMLUtil.getLanguageDependentString(
                           "ErrorTheCorrespondingJoinActivityDoesNotHaveTheSameType-XORAND");
                     }
                     graphConformanceErrors.put(act,msg);
                     if (!fullCheck) {
                        break;
                     }
                  }
               }
            }
            if (noCorrespondingJoinError) {
               basicGraphConformanceErrors.add(XMLUtil.getLanguageDependentString(
                                                  "ErrorOneOrMoreSplitsDoNotHaveCorrespondingJoin"));
            }
         }
      }

      // if so far the graph is conformant, or the full check is required,
      // check the graphs block activities
      if (isGraphConformant || fullCheck) {
         Set blockActivities=XMLUtil.getBlockActivities(wpOrAs,false);
         boolean innerConformanceError=false;
         Iterator it=blockActivities.iterator();
         while (it.hasNext()) {
            Activity act=(Activity)it.next();
            BlockActivity ba=act.getActivityTypes().getBlockActivity();
            String asId=ba.getBlockId();
            ActivitySet as=XMLUtil.getWorkflowProcess(act).getActivitySet(asId);
            if (as!=null && !checkGraphConformance(as,false)) {
               isGraphConformant=false;
               innerConformanceError=true;
               String msg=(String)graphConformanceErrors.get(ba);
               msg=prepareMessageString(msg);
               msg=msg+XMLUtil.getLanguageDependentString("ErrorInnerGraphConformanceError");
               graphConformanceErrors.put(act,msg);
               graphConformanceErrors.put(act,msg);
               if (!fullCheck) {
                  break;
               }
            }
         }
         if (innerConformanceError) {
            basicGraphConformanceErrors.add(XMLUtil.getLanguageDependentString(
                                               "ErrorOneOrMoreBlockActivitiesAreNotValid"));
         }
      }

      graphsConformanceErrors.put(wpOrAs,graphConformanceErrors);
      basicGraphsConformanceErrors.put(wpOrAs,basicGraphConformanceErrors);
      return isGraphConformant;
   }

   protected boolean[][] createIncidenceMatrix (SequencedHashMap sortedMap) {
      int size=sortedMap.size();
      boolean[][] incidenceMatrix=new boolean[size][size];
      for (int indAct=0; indAct<size; indAct++) {
         Activity a=(Activity)sortedMap.get(indAct);
         Set oas=new HashSet();
         Iterator trs=XMLUtil.getOutgoingTransitions(a).iterator();
         while (trs.hasNext()) {
            Transition t=(Transition)trs.next();
            String aOut=t.getTo();
            int indOut=sortedMap.indexOf(aOut);
            if (indOut==-1) return null;
            incidenceMatrix[indAct][indOut]=true;
         }
      }
      return incidenceMatrix;
   }

   /**
    * Returns the number of activities in the given set that have
    * split or join, depending on second parameter.
    * @param acts The set of activities that are searched for split or join
    * @param sOrJ 0 -> searching for split, otherwise, searching for join
    */
   protected int getNoOfANDSplitsOrJoins (Set acts,int sOrJ) {
      int no=0;
      Iterator it=acts.iterator();
      while (it.hasNext()) {
         Activity act=(Activity)it.next();
         if (sOrJ==0 && XMLUtil.isANDTypeSplitOrJoin(act, 0)) {
            no++;
         } else if (sOrJ==1 && XMLUtil.isANDTypeSplitOrJoin(act, 0)) {
            no++;
         }
      }
      return no;
   }

   protected boolean checkANDSplit (Activity act) {
      return !hasAnyPostcondition(act);
   }

   protected boolean checkXORSplit (Activity act) {
      // if activity has any postcondition, it must have an otherwise transition
      if (hasAnyPostcondition(act)) {
         Set ots=XMLUtil.getOutgoingTransitions(act);
         Iterator trs=ots.iterator();
         while (trs.hasNext()) {
            Transition t=(Transition)trs.next();
            if (t.getCondition().getType().equals(XPDLConstants.CONDITION_TYPE_OTHERWISE)) {
               return true;
            }
         }
         return false;
      } else {
         return true;
      }
   }

   protected boolean hasAnyPostcondition (Activity act) {
      Set outL=XMLUtil.getOutgoingTransitions(act);
      Iterator it=outL.iterator();
      while (it.hasNext()) {
         if (!((Transition)it.next()).getCondition().toValue().equals("")) {
            return true;
         }
      }
      return false;
   }

   //************************** GRAPH CONNECTIONS CHECKING ****************************

   public boolean checkGraphConnections (boolean fullCheck) {
      basicGraphConnectionErrors.remove(pkg);
      graphsConnectionErrors.remove(pkg);

      boolean areWellConnected=true;
      String basicGraphConnectionError;
      Map connectionErrorMessages=new HashMap();

      Iterator procs=pkg.getWorkflowProcesses().toElements().iterator();
      while (procs.hasNext()) {
         WorkflowProcess wp=(WorkflowProcess)procs.next();
         if (!checkGraphConnections(wp,false)) {
            areWellConnected=false;
            if (!fullCheck) {
               break;
            }
            String msg=getBasicGraphConnectionError(wp);
            if (msg==null) {
               msg="";
            }
            connectionErrorMessages.put(wp,msg);
         }
      }
      if (!areWellConnected) {
         basicGraphConnectionError=
            XMLUtil.getLanguageDependentString("InformationOneOrMoreProcessesHaveImproperlyConnectedElements");
         basicGraphConnectionErrors.put(pkg,basicGraphConnectionError);
      }
      graphsConnectionErrors.put(pkg,connectionErrorMessages);
      return areWellConnected;
   }

   public boolean checkGraphConnections (XMLCollectionElement wpOrAs,boolean fullCheck) {
      if (wpOrAs==null) return false;
      basicGraphConnectionErrors.remove(wpOrAs);
      graphsConnectionErrors.remove(wpOrAs);

      boolean isWellConnected=true;
      boolean basicError=false;
      Map connectionErrorMessages=new HashMap();

      Transitions ts=(Transitions)wpOrAs.get("Transitions");
      Collection acts=((Activities)wpOrAs.get("Activities")).toElements();
      if (acts==null || acts.size()==0) {
         graphsConnectionErrors.put(wpOrAs,connectionErrorMessages);
         return true;
      }

      Set startActs=null;
      Set endActs=null;
      if (fullCheck || isWellConnected) {
         startActs=XMLUtil.getStartingActivities(wpOrAs);
         if (startActs.size()==0 && (!allowUndefinedStart || (wpOrAs instanceof ActivitySet))) {
            isWellConnected=false;
            basicError=true;
            String msg=(String)connectionErrorMessages.get(wpOrAs);
            msg=prepareMessageString(msg);
            msg+=XMLUtil.getLanguageDependentString("ErrorStartingActivityDoesNotExist");
            connectionErrorMessages.put(wpOrAs,msg);
         }
      }
      if (fullCheck || isWellConnected) {
         endActs=XMLUtil.getEndingActivities(wpOrAs);
         if (endActs.size()==0 && (!allowUndefinedEnd || (wpOrAs instanceof ActivitySet))) {
            isWellConnected=false;
            basicError=true;
            String msg=(String)connectionErrorMessages.get(wpOrAs);
            msg=prepareMessageString(msg);
            msg+=XMLUtil.getLanguageDependentString("ErrorEndingActivityDoesNotExist");
            connectionErrorMessages.put(wpOrAs,msg);
         }
      }
      if (fullCheck || isWellConnected) {
         Iterator it=acts.iterator();
         while (it.hasNext()) {
            Activity act=(Activity)it.next();
            String cem=checkActivityConnection(act,ts,startActs,endActs,fullCheck);
            if (cem!=null) {
               connectionErrorMessages.put(act,cem);
               isWellConnected=false;
               if (!fullCheck) {
                  break;
               }
            }

         }
      }

      if (!isWellConnected) {
         if (basicError) {
            basicGraphConnectionErrors.put(wpOrAs,connectionErrorMessages.get(wpOrAs));
         } else {
            basicGraphConnectionErrors.put(wpOrAs,
                                           XMLUtil.getLanguageDependentString("InformationOneOrMoreElementsAreNotProperlyConnected"));
         }
      }
      graphsConnectionErrors.put(wpOrAs,connectionErrorMessages);
      return isWellConnected;
   }

   /**
    * Checks if given activity is well connected.
    * @return String describing the error, or empty string if there is no connection
    * error for giving activity.
    */
   public String checkActivityConnection (Activity act,Transitions ts,
                                          Set startActs,Set endActs,boolean fullCheck) {

      String connectionErrorMsg="";
      // if this is a block activity, check inner transitions      
      if (act.getActivityTypes().getChoosen() instanceof BlockActivity) {
         BlockActivity ba=act.getActivityTypes().getBlockActivity();
         String asId=ba.getBlockId();
         ActivitySet as=XMLUtil.getWorkflowProcess(act).getActivitySet(asId);
         if (as!=null) {
            if (!checkGraphConnections(as,false)) {
               connectionErrorMsg+=
                  XMLUtil.getLanguageDependentString("ErrorInnerTransitionError")+"; ";
            }
         }
      }

      if (connectionErrorMsg.length()==0) connectionErrorMsg=null;

      return connectionErrorMsg;
   }

   protected Activity findBlockActivity (ActivitySet as) {
      String asId=as.getId();
      WorkflowProcess wp=XMLUtil.getWorkflowProcess(as);
      Set bas=XMLUtil.getBlockActivities(wp,true);
      Iterator it=bas.iterator();
      while (it.hasNext()) {
         Activity a=(Activity)it.next();
         String baId=a.getActivityTypes().getBlockActivity().getBlockId();
         if (baId.equals(asId)) {
            return a;
         }
      }
      return null;
   }

   protected String prepareMessageString (String msg) {
      if (msg!=null) {
         msg=msg+"; ";
      } else {
         msg="";
      }
      return msg;
   }

}
