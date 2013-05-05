package org.enhydra.shark.test;

import java.util.*;
import org.enhydra.shark.Shark;
import org.enhydra.shark.api.client.wfservice.ExternalPackageInvalid;
import org.enhydra.shark.api.client.wfservice.PackageAdministration;
import org.enhydra.shark.api.client.wfservice.PackageInvalid;

public class UploadXPDL {

   public static void main (String[] args) throws Exception {
      String confF=args[0];
      List xpdlsToLoad=new ArrayList();
      for (int i=1; i<args.length; i++) {
         xpdlsToLoad.add(args[i]);
      }

      Shark.configure(confF);
      Shark ss=Shark.getInstance();
      PackageAdministration pa=ss.getAdminInterface().getPackageAdministration();

      try {
         Iterator it=xpdlsToLoad.iterator();
         while (it.hasNext()) {
            pa.openPackage((String)it.next());
         }
      } catch (Throwable ex) {
         if (ex instanceof PackageInvalid) {
            System.out.println("PIERRS="+((PackageInvalid)ex).getXPDLValidationErrors());
         }
         if (ex instanceof ExternalPackageInvalid) {
            System.out.println("PIERRS="+((ExternalPackageInvalid)ex).getXPDLValidationErrors());
         }
         ex.printStackTrace();
      } finally {
         org.enhydra.dods.DODS.shutdown();
         System.exit(0);
      }

   }

}

