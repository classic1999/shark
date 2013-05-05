package org.enhydra.shark.corbaclient.workflowadmin.repository.actions;

import org.omg.WfBase.*;
import org.enhydra.shark.corba.WorkflowService.*;

import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

import org.enhydra.shark.corbaclient.*;
import org.enhydra.shark.corbaclient.workflowadmin.*;
import org.enhydra.shark.corbaclient.workflowadmin.repository.*;
import org.enhydra.jawe.xml.XMLUtil;

/**
 * Uploads the package the wanted location into engine repository.
 *
 * @author Sasa Bojanic
 * @version 1.0
 */
public class UploadPackage extends ActionBase {

   public UploadPackage (RepositoryManagement rpm) {
      super(rpm);
   }

   public void actionPerformed(ActionEvent e) {
      RepositoryManagement rpm=(RepositoryManagement)actionPanel;
      SharkAdmin workflowAdmin=rpm.getWorkflowAdmin();
      String pkgToUpload=XMLUtil.dialog(workflowAdmin.getFrame(),
            ResourceManager.getLanguageDependentString("MessageChoosePackageFileToUpload")
            ,0,0,"");
      if (pkgToUpload==null) return;
      String errMsg=null;
      String xpdlReport=null;
      boolean invalidPackage=false;
      try {
         File pkgFile=new File(pkgToUpload);
         RandomAccessFile raf=new RandomAccessFile(pkgFile,"r");
         byte[] utf8Bytes=null;
         long noOfBytes=raf.length();
         if (noOfBytes>0) {
            utf8Bytes=new byte[(int)noOfBytes];
            raf.seek(0);
            raf.readFully(utf8Bytes);
         }
         if (utf8Bytes!=null) {
            UploadedPackageRelativePath uprp=new UploadedPackageRelativePath(
                  workflowAdmin.getFrame(),pkgFile.getName());
            uprp.showDialog();
            String rPath=uprp.getRelativeFilePath();
            if (rPath==null) {
               return;
            } else {
               SharkAdmin.getRepositoryManager().uploadPkg(utf8Bytes,rPath);
            }

            rpm.refresh(true);
         }
      } catch (RepositoryInvalid ri) {
         invalidPackage=true;
         xpdlReport=ri.XPDLValidationErrors;
         errMsg=ResourceManager.getLanguageDependentString("ErrorThePackageIsInvalid");
      } catch (Exception ex) {
         errMsg=ResourceManager.getLanguageDependentString("ErrorCannotUploadSelectedPackageFileAtTheMoment");
      }

      if (errMsg!=null) {
         JOptionPane.showMessageDialog(workflowAdmin.getFrame(),errMsg,
               workflowAdmin.getAppTitle(),JOptionPane.ERROR_MESSAGE);
      }

      if (invalidPackage) {
         workflowAdmin.showXPDLErrorsReport(xpdlReport);
      }

   }

}

