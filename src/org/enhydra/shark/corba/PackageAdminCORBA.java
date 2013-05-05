package org.enhydra.shark.corba;

import org.omg.WfBase.*;
import org.enhydra.shark.corba.WorkflowService.*;


/**
 * Various package related operations.
 * @author Sasa Bojanic
 *
 */
public class PackageAdminCORBA extends _PackageAdministrationImplBase {
   private SharkCORBAServer myServer;

   private String userId;
   private boolean connected=false;

   org.enhydra.shark.api.client.wfservice.PackageAdministration myPkgAdmin;

   PackageAdminCORBA (SharkCORBAServer server,org.enhydra.shark.api.client.wfservice.PackageAdministration pa) {
      this.myServer=server;
      this.myPkgAdmin=pa;
   }

   public void connect(String userId, String password, String engineName, String scope) throws BaseException, ConnectFailed {
      this.userId=userId;

      try {
         if (!myServer.validateUser(userId,password)) {
            throw new ConnectFailed("Connection failed, invalid username or password");
         }
         connected=true;
         myPkgAdmin.connect(userId);
      } catch (ConnectFailed cf) {
         throw cf;
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void disconnect() throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      connected=false;
      this._orb().disconnect(this);
   }

   public String[] getOpenedPackageIds () throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myPkgAdmin.getOpenedPackageIds();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String[] getPackageVersions (String pkgId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myPkgAdmin.getPackageVersions(pkgId);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public boolean isPackageOpened (String pkgId)  throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myPkgAdmin.isPackageOpened(pkgId);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public byte[] getPackageContent (String pkgId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myPkgAdmin.getPackageContent(pkgId);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public byte[] getPackageVersionContent (String pkgId,String pkgVer) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myPkgAdmin.getPackageContent(pkgId,pkgVer);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String getCurrentPackageVersion(String pkgId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myPkgAdmin.getCurrentPackageVersion(pkgId);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String openPkg (String relativePath) throws BaseException, NotConnected, PackageInvalid, ExternalPackageInvalid {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myPkgAdmin.openPackage(relativePath);
      } catch (org.enhydra.shark.api.client.wfservice.PackageInvalid pi) {
         throw new PackageInvalid(pi.getXPDLValidationErrors());
      } catch (org.enhydra.shark.api.client.wfservice.ExternalPackageInvalid epi) {
         throw new ExternalPackageInvalid(epi.getXPDLValidationErrors());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void updatePackage1 (String id, String relativePathToNewPackage) throws BaseException, NotConnected, PackageUpdateNotAllowed, PackageInvalid, ExternalPackageInvalid {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myPkgAdmin.updatePackage(id,relativePathToNewPackage);
      } catch (org.enhydra.shark.api.client.wfservice.PackageInvalid pi) {
         throw new PackageInvalid(pi.getXPDLValidationErrors());
      } catch (org.enhydra.shark.api.client.wfservice.ExternalPackageInvalid epi) {
         throw new ExternalPackageInvalid(epi.getXPDLValidationErrors());
      } catch (org.enhydra.shark.api.client.wfservice.PackageUpdateNotAllowed pna) {
         throw new PackageUpdateNotAllowed();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void closePkg (String pkgId) throws BaseException, NotConnected, PackageInUse, PackageHasActiveProcesses {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myPkgAdmin.closePackage(pkgId);
      } catch (org.enhydra.shark.api.client.wfservice.PackageInUse piu) {
         throw new PackageInUse();
      } catch (org.enhydra.shark.api.client.wfservice.PackageHasActiveProcesses phap) {
         throw new PackageHasActiveProcesses();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void closePkgWithVersion(String pkgId,String pkgVer) throws BaseException, NotConnected, PackageInUse, PackageHasActiveProcesses {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myPkgAdmin.closePackage(pkgId,pkgVer);
      } catch (org.enhydra.shark.api.client.wfservice.PackageInUse piu) {
         throw new PackageInUse();
      } catch (org.enhydra.shark.api.client.wfservice.PackageHasActiveProcesses phap) {
         throw new PackageHasActiveProcesses();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public boolean isPackageReferenced (String pkgId) throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         return myPkgAdmin.isPackageReferenced(pkgId);
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void synchronizeXPDLCache () throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myPkgAdmin.synchronizeXPDLCache();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void clearXPDLCache () throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myPkgAdmin.clearXPDLCache();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void refreshXPDLCache () throws BaseException, NotConnected {
      if (!connected) {
         throw new NotConnected("The connection is not established...");
      }
      try {
         myPkgAdmin.refreshXPDLCache();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

}

