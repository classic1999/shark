package org.enhydra.shark;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.util.ResourceManager;
import org.enhydra.shark.xpdl.PackageValidator;
import org.enhydra.shark.xpdl.XMLCollectionElement;
import org.enhydra.shark.xpdl.XMLComplexElement;
import org.enhydra.shark.xpdl.XMLElement;
import org.enhydra.shark.xpdl.XMLInterface;
import org.enhydra.shark.xpdl.XMLUtil;
import org.enhydra.shark.xpdl.XPDLConstants;
import org.enhydra.shark.xpdl.elements.Activity;
import org.enhydra.shark.xpdl.elements.ApplicationTypes;
import org.enhydra.shark.xpdl.elements.BasicType;
import org.enhydra.shark.xpdl.elements.DataField;
import org.enhydra.shark.xpdl.elements.DataType;
import org.enhydra.shark.xpdl.elements.DeclaredType;
import org.enhydra.shark.xpdl.elements.ExternalReference;
import org.enhydra.shark.xpdl.elements.Package;
import org.enhydra.shark.xpdl.elements.Participant;
import org.enhydra.shark.xpdl.elements.Script;
import org.enhydra.shark.xpdl.elements.TypeDeclaration;
import org.enhydra.shark.xpdl.elements.WorkflowProcess;
import org.enhydra.shark.xpdl.elements.WorkflowProcesses;

/**
 * Special shark validation - to claim that package is not 'shark' valid.
 * It extends the JaWE's package validator to add some additional restrictions.
 * @author Sasa Bojanic
 * @version 1.0
 */
public class SharkPackageValidator extends PackageValidator {
   private XMLInterface xpdlManager;

   public SharkPackageValidator (Package p,XMLInterface xpdlManager,boolean checkExternalPackages) {
      super(xpdlManager,p,true,checkExternalPackages,true,true);
      this.xpdlManager=xpdlManager;
   }

   /** Introduces restrictions on script type. */
   public boolean checkScript (boolean fullCheck) {
      boolean isValid=true;
      Script s=pkg.getScript();
      String sType=s.getType().trim();
      boolean isScriptInvalid=false;
      SharkTransaction t=null;
      try {
         t=SharkUtilities.createTransaction();
         isScriptInvalid=SharkEngineManager.
            getInstance().
            getScriptingManager().
            getEvaluator(t,sType)==null;
         //SharkUtilities.commitTransaction(t);
      } catch (Exception ex) {
         //try { SharkUtilities.rollbackTransaction(t);}catch(Exception e) {}
         isScriptInvalid=true;
      } finally {
         try { SharkUtilities.releaseTransaction(t);} catch (BaseException e) {}
      }
      if (isScriptInvalid) {
         isValid=false;
         Map les=getLogicErrors(pkg);
         if (les!=null) {
            les.put(pkg,ResourceManager.getLanguageDependentString("ErrorUnsupportedScriptLanguage"));
         }
      }

      return isValid;
   }

   public boolean checkDataType (XMLCollectionElement dfOrFp,boolean fullCheck) {
      boolean isValid=super.checkDataType(dfOrFp,fullCheck);

      if (isValid || fullCheck) {
         DataType xpdlType=(DataType)dfOrFp.get("DataType");
         Object type=xpdlType.getDataTypes().getChoosen();
         if (type instanceof DeclaredType) {
            TypeDeclaration td=pkg.getTypeDeclaration(((DeclaredType)type).getId());
            isValid=checkTypeDeclaration(td,false);
         } else if (!((type instanceof BasicType)|| (type instanceof ExternalReference))) {
            isValid=false;
         }
         if (!isValid) {
            Map les;
            XMLElement firstOwner=dfOrFp.getParent().getParent();
            if (dfOrFp instanceof DataField) {
               les=getLogicErrors((XMLComplexElement)firstOwner);
            } else {
               if (firstOwner instanceof ApplicationTypes) {
                  les=getLogicErrors((XMLComplexElement)((ApplicationTypes)firstOwner).getParent().getParent().getParent());
               } else {
                  les=getLogicErrors((XMLComplexElement)firstOwner);
               }
            }
            String msg=(String)les.get(dfOrFp);
            msg=prepareMessageString(msg);
            msg=msg+ResourceManager.getLanguageDependentString("ErrorUnsupportedDataType");
            les.put(dfOrFp,msg);
         }
      }
      return isValid;
   }

   public boolean checkTypeDeclaration(TypeDeclaration td,boolean fullCheck) {
      boolean isValid=true;
      Object choosenType=td.getDataTypes().getChoosen();
      if (!((choosenType instanceof BasicType) || (choosenType instanceof ExternalReference))) {
         isValid=false;
      }
      if (choosenType instanceof DeclaredType) {
         TypeDeclaration td2=pkg.getTypeDeclaration(((DeclaredType)choosenType).getId());
         isValid=checkTypeDeclaration(td2,fullCheck);
      }
      if (!isValid) {
         Map les=getLogicErrors(pkg);
         String msg=ResourceManager.getLanguageDependentString("ErrorUnsupportedTypeDeclaration");
         les.put(td,msg);

      }
      return isValid;
   }

   public boolean validateAgainstXPDLSchema () {
      xpdlSchemaValidationErrors=xpdlManager.getParsingErrorMessages();
      if (xpdlSchemaValidationErrors.size()>0) {
         return false;
      } else {
         return true;
      }
   }

   public boolean checkExternalPackages(boolean fullCheck) {
      boolean isValid=true;
      Map les=getLogicErrors(pkg);

      Iterator it=pkg.getExternalPackageIds().iterator();
      while (it.hasNext() && (fullCheck || isValid)) {
         Package p=(Package)xmlInterface.getPackageById((String)it.next());
         PackageValidator pv=new SharkPackageValidator(p,xpdlManager,false);
         if (!pv.validateAll(false)) {
            isValid=false;
            if (les!=null) {
               les.put(p,ResourceManager.getLanguageDependentString("ErrorInvalidExternalPackage"));
            }
         }
      }
                  
      return isValid;
   }

   public boolean isExternalPackageError () {
      List bgces=getBasicGraphConformanceErrors(pkg);
      String bgce=getBasicGraphConnectionError(pkg);
      String ble=getBasicLogicError(pkg);
      String myPkgXmlFile=xpdlManager.getAbsoluteFilePath(pkg);
      Map scherrs=getXPDLSchemaValidationErrors();
      if (((bgces!=null && bgces.size()>0) || bgce!=null || ble!=null) &&
             ((scherrs.size()>0 && scherrs.containsKey(myPkgXmlFile)) || scherrs.size()==0)) {
         return false;
      } else {
         return true;
      }
   }

   public boolean checkActivity (Activity act,boolean fullCheck) {
      boolean isValid=super.checkActivity(act,fullCheck);
      int type=act.getActivityType();
      if (type==XPDLConstants.ACTIVITY_TYPE_TOOL && act.getActivityTypes().getImplementation().getImplementationTypes().getTools().size()>0) {
         isValid=checkStartMode(act) && isValid;
         //isValid=checkFinishMode(act) && isValid;
      }
      return isValid;
   }

   protected boolean checkStartMode (Activity toolAct) {
      boolean isValid=true;
      int startMode=toolAct.getActivityStartMode();
      String performer=toolAct.getPerformer().trim();
      boolean isSystemOrEmptyExpressionPerformer=false;
      Participant p=null;
      p=XMLUtil.getWorkflowProcess(toolAct).getParticipant(performer);
      if (p==null) {
         p=pkg.getParticipant(performer);
         if (p==null) {
            List l=XMLUtil.getAllExternalPackageIds(xmlInterface, pkg);
            Iterator ita=l.iterator();
            while (ita.hasNext()) {
               Package pk=xmlInterface.getPackageById((String)ita.next());
               if (pk!=null) {
                  p=pk.getParticipant(performer);
                  if (p!=null) {
                     break;
                  }
               }
            }
            
         }
      }
      if (p!=null) {
         String participantType=p.getParticipantType().getType();
         if (participantType.equals(XPDLConstants.PARTICIPANT_TYPE_SYSTEM)) {
            isSystemOrEmptyExpressionPerformer=true;
         }
      } else {
         if (performer.length()==0) {
            isSystemOrEmptyExpressionPerformer=true;
         }
      }
      if (isSystemOrEmptyExpressionPerformer && startMode==XPDLConstants.ACTIVITY_MODE_MANUAL) {
         isValid=false;
         Map les=getLogicErrors((XMLComplexElement)toolAct.getParent().getParent());
         String msg=(String)les.get(toolAct);
         msg=prepareMessageString(msg);
         msg=msg+ResourceManager.getLanguageDependentString("ErrorMANUALStartModeNotAllowedForToolActivitiesWithSystemParticipantOrEmptyExpressionPerformer");
         les.put(toolAct,msg);
      }
      if (!isSystemOrEmptyExpressionPerformer && startMode==XPDLConstants.ACTIVITY_MODE_AUTOMATIC) {
         isValid=false;
         Map les=getLogicErrors((XMLComplexElement)toolAct.getParent().getParent());
         String msg=(String)les.get(toolAct);
         msg=prepareMessageString(msg);
         msg=msg+ResourceManager.getLanguageDependentString("ErrorAUTOMATICStartModeNotAllowedForToolActivitiesWithPerformerOtherThenSystemParticipantOrEmptyExpression");
         les.put(toolAct,msg);
      }
      return isValid;
   }

   /*protected boolean checkFinishMode (Activity toolAct) {
      boolean isValid=true;
      int finishMode=toolAct.getFinishMode();
      Object performer=toolAct.get("Performer").toValue();
      if (finishMode==Activity.MODE_MANUAL) {
         isValid=false;
         Map les=getLogicErrors(toolAct.getCollection().getOwner());
         String msg=(String)les.get(toolAct);
         msg=prepareMessageString(msg);
         msg=msg+ResourceManager.getLanguageDependentString("ErrorMANUALFinishModeNotAllowedForToolActivities");
         les.put(toolAct,msg);
      }
      return isValid;
    }*/

   public boolean checkActivityPerformer (Activity act,boolean fullCheck) {
      boolean isValid=super.checkActivityPerformer(act,fullCheck);
      // check performer
      String performer=act.getPerformer().trim();
      int type=act.getActivityType();
      if (type==XPDLConstants.ACTIVITY_TYPE_NO || (type==XPDLConstants.ACTIVITY_TYPE_TOOL && act.getActivityTypes().getImplementation().getImplementationTypes().getTools().size()==0)) {
         Participant p=null;
         WorkflowProcess wp=XMLUtil.getWorkflowProcess(act);
         p=wp.getParticipant(performer);
         if (p==null) {
            p=pkg.getParticipant(performer);
         }
         if (p!=null) {
            String participantType=p.getParticipantType().getType();
            if (participantType.equals(XPDLConstants.PARTICIPANT_TYPE_SYSTEM)) {
               isValid=false;
               Map les=getLogicErrors((XMLComplexElement)act.getParent().getParent());
               String msg=(String)les.get(act);
               msg=prepareMessageString(msg);
               msg=msg+ResourceManager.getLanguageDependentString("ErrorNoImplementationActivityCannotHaveSystemParticipantPerformer");
               les.put(act,msg);
            }
         }
      }
      return isValid;
   }

   public void printDebug () {
      System.err.println("SVEs="+xpdlSchemaValidationErrors);
      System.err.println("GCEs="+graphsConnectionErrors);
      System.err.println("BGCEs="+basicGraphConnectionErrors);
      System.err.println("GCFEs="+graphsConformanceErrors);
      System.err.println("BGCFEs="+basicGraphsConformanceErrors);
      System.err.println("LEs="+logicErrors);
      System.err.println("BLEs="+basicLogicErrors);
      /*Iterator it=logicErrors.entrySet().iterator();
       while (it.hasNext()) {
       System.err.println("LES="+it.next());
       }
       it=basicLogicErrors.entrySet().iterator();
       while (it.hasNext()) {
       System.err.println("BLES="+it.next());
       }*/
   }

   public String createXPDLValidationErrorsString () {
      String errMsg="<html>";
      try {
         errMsg=processErrors(xpdlSchemaValidationErrors,getGraphsConnectionErrors(pkg),
                              getBasicGraphConformanceErrors(pkg),getGraphConformanceErrors(pkg),
                              getLogicErrors(pkg),errMsg,pkg);
         WorkflowProcesses wps=pkg.getWorkflowProcesses();
         Iterator it=wps.toElements().iterator();
         while (it.hasNext()) {
            WorkflowProcess wp=(WorkflowProcess)it.next();
            errMsg=processErrors(null,getGraphsConnectionErrors(wp),
                                 getBasicGraphConformanceErrors(wp),getGraphConformanceErrors(wp),
                                 getLogicErrors(wp),errMsg,wp);
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      }
      errMsg+="</html>";
      return errMsg;
   }


   /**
    * Processes given errors, and evaluates a string to be displayed.
    */
   protected String processErrors(Map parsingErrors,Map connectionErrors,
                                  java.util.List basicGraphConformanceErrors,Map graphConformanceErrors,
                                  Map logicErrors,String errMsg,XMLComplexElement pkgOrWpOrAs) {
      boolean packageLevelProcessing=(pkgOrWpOrAs instanceof Package);
      // Connections
      String subtitle="";
      if (packageLevelProcessing) {
         subtitle=XMLUtil.getLanguageDependentString("XPDLSchemaKey");
         errMsg+="<font size=6 face=\"sans-serif\" color=\"green\"><b><i><u>"+subtitle+"</u></i></b></font><br><br>";

         if (parsingErrors!=null && parsingErrors.size()>0) {
            Iterator it=parsingErrors.entrySet().iterator();
            while (it.hasNext()) {
               Map.Entry me=(Map.Entry)it.next();
               String pkgFile=(String)me.getKey();
               Set errorsForPkg=(Set)me.getValue();
               if (pkgFile.length()>0) {
                  errMsg+="<font size=5 face=\"sans-serif\" color=\"blue\"><b><i><u>"+
                     pkgFile+"</u></i></b></font><br><br>";
               }
               errMsg+="<font size=4 face=\"sans-serif\" color=\"#FF0000\"><i>";

               Iterator it2=errorsForPkg.iterator();
               while (it2.hasNext()) {
                  String msg=it2.next().toString();
                  errMsg+=msg+"<br>";
               }

               errMsg+="</i></font><br><br>";
            }
         }
         if (parsingErrors==null || parsingErrors.size()==0) {
            String msg=XMLUtil.
               getLanguageDependentString("MessageThereAreNoXPDLSchemaValidationErrors");
            errMsg+="<br><font size=4 face=\"sans-serif\" color=\"#0033CC\">"+msg+"</font><br><br>";
         }

      } else {
         errMsg+="<p>";
      }

      if (packageLevelProcessing) {
         subtitle=XMLUtil.getLanguageDependentString("PackageKey");
      } else if (pkgOrWpOrAs instanceof WorkflowProcess) {
         subtitle=XMLUtil.getLanguageDependentString("WorkflowProcessKey");
      }
      errMsg+="<font size=6 face=\"sans-serif\" color=\"black\"><b><i><u>"+subtitle+":"+pkgOrWpOrAs.get("Id").toValue()+"</u></i></b></font><br><br>";
      errMsg+="<p>";

      // Connections
      subtitle=XMLUtil.getLanguageDependentString("ConnectionsKey");
      errMsg+="<font size=5 face=\"sans-serif\" color=\"green\"><b><i><u>"+subtitle+"</u></i></b></font><br><br>";

      if (connectionErrors!=null) {
         Iterator it=connectionErrors.entrySet().iterator();
         while (it.hasNext()) {
            Map.Entry me=(Map.Entry)it.next();
            Object obj=me.getKey();
            String msg=me.getValue().toString();
            errMsg+="<font size=4 face=\"sans-serif\" color=\"#0033CC\">";
            errMsg=appendErrorMessage(obj,errMsg,packageLevelProcessing);
            //errMsg+="</font>";
            errMsg+="<blockquote>";
            errMsg+="<font size=\"4\" face=\"sans-serif\">";
            errMsg+="<em>"+msg+"</em><br>";
            errMsg+="</font></p></blockquote>";
         }
      }
      if (connectionErrors==null || connectionErrors.size()==0) {
         String msg=XMLUtil.
            getLanguageDependentString("MessageAllElementsAreProperlyConnected");
         errMsg+="<br><font size=4 face=\"sans-serif\" color=\"#0033CC\">"+msg+"</font><br><br>";
      }

      // Graph conformance
      subtitle=XMLUtil.getLanguageDependentString("GraphConformanceKey");
      errMsg+="<font size=5 face=\"sans-serif\" color=\"green\"><b><i><u>"+subtitle+"</u></i></b></font><br>";

      if (basicGraphConformanceErrors!=null) {
         Iterator it=basicGraphConformanceErrors.iterator();
         while (it.hasNext()) {
            String msg=(String)it.next();
            errMsg+="<font size=4 face=\"sans-serif\" color=\"#FF0000\">"+msg+"</font>";
            errMsg+="<br>";
         }
      }

      if (graphConformanceErrors!=null && graphConformanceErrors.size()>0) {
         if (basicGraphConformanceErrors!=null && basicGraphConformanceErrors.size()>0) {
            errMsg+="<br><br>";
         } else {
            errMsg+="<br>";
         }
      }

      if (graphConformanceErrors!=null) {
         Iterator it=graphConformanceErrors.entrySet().iterator();
         while (it.hasNext()) {
            Map.Entry me=(Map.Entry)it.next();
            Object obj=me.getKey();
            String msg=me.getValue().toString();
            errMsg+="<font size=4 face=\"sans-serif\" color=\"#0033CC\">";
            errMsg=appendErrorMessage(obj,errMsg,packageLevelProcessing);
            //errMsg+="</font>";
            errMsg+="<blockquote>";
            errMsg+="<font size=\"4\" face=\"sans-serif\">";
            errMsg+="<em>"+msg+"</em><br>";
            errMsg+="</font></p></blockquote>";
         }
      }

      if ((basicGraphConformanceErrors==null || basicGraphConformanceErrors.size()==0) &&
             (graphConformanceErrors==null || graphConformanceErrors.size()==0)) {
         String msg;
         if (packageLevelProcessing) {
            msg=XMLUtil.
               getLanguageDependentString("MessagePackageGraphsConformsToTheGivenGraphConformanceClass");
         } else {
            msg=XMLUtil.
               getLanguageDependentString("MessageProcessGraphConformsToTheGivenGraphConformanceClass");
         }
         errMsg+="<br><font size=4 face=\"sans-serif\" color=\"#0033CC\">"+msg+"</font><br><br>";
      }

      if (basicGraphConformanceErrors!=null && basicGraphConformanceErrors.size()>0 &&
             (graphConformanceErrors==null || graphConformanceErrors.size()==0)) {
         errMsg+="<br>";
      }

      // Logic
      subtitle=XMLUtil.getLanguageDependentString("LogicKey");
      errMsg+="<font size=5 face=\"sans-serif\" color=\"green\"><b><i><u>"+subtitle+"</u></i></b></font><br><br>";

      if (logicErrors!=null) {
         Iterator it=logicErrors.entrySet().iterator();
         while (it.hasNext()) {
            Map.Entry me=(Map.Entry)it.next();
            Object obj=me.getKey();
            String msg=me.getValue().toString();
            errMsg+="<font size=4 face=\"sans-serif\" color=\"#0033CC\">";
            errMsg=appendErrorMessage(obj,errMsg,packageLevelProcessing);
            //errMsg+="</font>";
            errMsg+="<blockquote>";
            errMsg+="<font size=\"4\" face=\"sans-serif\">";
            errMsg+="<em>"+msg+"</em><br>";
            errMsg+="</font></p></blockquote>";
         }
      }
      if (logicErrors==null || logicErrors.size()==0) {
         String msg=XMLUtil.
            getLanguageDependentString("MessageThereAreNoLogicErrors");
         errMsg+="<br><font size=4 face=\"sans-serif\" color=\"#0033CC\">"+msg+"</font><br><br>";
      }

      return errMsg;
   }

   private String appendErrorMessage (Object obj,String errMsg,boolean packageLevelProcessing) {
      if ((obj instanceof XMLCollectionElement) || (obj instanceof Package)) {
         XMLComplexElement xmlCe=(XMLComplexElement)obj;
         if (obj instanceof WorkflowProcess) {
            if(!packageLevelProcessing) return errMsg;
            errMsg+="<u><strong>"+XMLUtil.
               getLanguageDependentString("ProcessKey")+":</strong></u> </font>";
         } else if (obj instanceof Activity) {
            Activity act=(Activity)obj;
            if (act.getActivityType()==XPDLConstants.ACTIVITY_TYPE_SUBFLOW) {
               errMsg+="<u><strong>"+XMLUtil.
                  getLanguageDependentString("SubFlowKey")+":</strong></u> </font>";
            } else if (act.getActivityType()==XPDLConstants.ACTIVITY_TYPE_BLOCK) {
               errMsg+="<u><strong>"+XMLUtil.
                  getLanguageDependentString("BlockActivityKey")+":</strong></u> </font>";
            } else if (act.getActivityType()==XPDLConstants.ACTIVITY_TYPE_TOOL) {
               errMsg+="<u><strong>"+XMLUtil.
                  getLanguageDependentString("GenericKey")+":</strong></u> </font>";
            } else {
               errMsg+="<u><strong>"+XMLUtil.
                  getLanguageDependentString("RouteKey")+":</strong></u> </font>";
            }
         } else {
            errMsg+="<u><strong>"+XMLUtil.getLanguageDependentString(((XMLElement)obj).toName()+"Key")+":</strong></u> </font>";
         }
         try {
            errMsg+="<font size=\"4\" face=\"sans-serif\">"
               +XMLUtil.getLanguageDependentString("IdKey")+"= <font color=\"#FF0000\">"
               +xmlCe.get("Id").toValue()+"</font>, ";
            errMsg+=XMLUtil.getLanguageDependentString("NameKey")+
               "= <font color=\"#FF0000\">"+xmlCe.get("Name").toValue()+"</font></font>";
         } catch (Exception ex) {}
      } else if (obj instanceof XMLElement) {
         errMsg+="<u><strong>"+XMLUtil.getLanguageDependentString(((XMLElement)obj).toName()+"Key")+":</strong></u> </font>";
      } else {
      }
      return errMsg;
   }


}
