/* SharkWebServiceImpl.java */

package org.enhydra.shark.wfxml;

import javax.xml.namespace.QName;

import org.apache.axis.encoding.DefaultTypeMappingImpl;
import org.apache.axis.encoding.ser.SimpleSerializerFactory;
import org.enhydra.shark.Shark;
import org.enhydra.shark.api.RootError;
import org.enhydra.shark.api.client.wfservice.*;
import org.enhydra.shark.wfxml.util.AltBeanDeserializerFactory;

/**
 * @author V.Puskas, S.Bojanic
 * @version 0.2
 */
public class SharkServiceImpl extends org.enhydra.shark.asap.SharkServiceImpl {

   private static PackageAdministration packageAdministration;

   private static RepositoryMgr repositoryMgr;

   public static void main(String[] argv) {
      try {
         Shark.configure(argv[0]);
         packageAdministration = Shark.getInstance()
            .getAdminInterface()
            .getPackageAdministration();
         repositoryMgr = Shark.getInstance().getRepositoryManager();
         UserGroupAdministration uga = Shark.getInstance()
            .getAdminInterface()
            .getUserGroupAdministration();
         try {
            if (!uga.doesGroupExist(GROUPNAME)) {
               uga.createGroup("test", "test group");
            }
            if (!uga.doesUserExist(USERNAME)) {
               uga.createUser(GROUPNAME, USERNAME, PASSWD, "Jane", "Doe", "");
            }
            /*String pkgId = repositoryMgr.getPackageId(XPDL_FILENAME);
            if (!packageAdministration.isPackageOpened(pkgId)) {
               packageAdministration.openPackage(XPDL_FILENAME);
             }*/

         } catch (Throwable t) {
            t.printStackTrace();
         }

         //asapPersistenceManager=new DODSAsapPersistenceManager();
         //asapPersistenceManager.configure(Shark.getInstance().getProperties());

         //sharkExternalRequester=new RequesterImpl();

         adminMisc = Shark.getInstance().getAdminInterface().getAdminMisc();
         adminMisc.connect(SharkServiceImpl.USERNAME);

         executionAdmin = Shark.getInstance()
            .getAdminInterface()
            .getExecutionAdministration();
         executionAdmin.connect(SharkServiceImpl.USERNAME,
                                SharkServiceImpl.PASSWD,
                                "",
                                "");
         factoryBindingAddress = new SharkWebServiceLocator().getwfxmlFactoryBindingAddress();

         //restoreExternalRequester();
         QName xmlType = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
                                                       "anyType");
         DefaultTypeMappingImpl.getSingleton()
            .register(String.class,
                      xmlType,
                      new SimpleSerializerFactory(String.class,
                                                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
                                                "string")),
                      new AltBeanDeserializerFactory(String.class,
                                                     new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
                                                                                   "string")));

         String[] args = new String[argv.length - 1];
         for (int n = 1; n < argv.length; ++n) {
            args[n - 1] = argv[n];
         }
         Class.forName("org.apache.axis.transport.http.SimpleAxisServer")
            .getDeclaredMethod("main", new Class[] {
               args.getClass()
            })
            .invoke(null, new Object[] {
               args
            });
      } catch (Exception e) {
         throw new RootError(e);
      }
   }

   public static PackageAdministration getPackageAdministration() {
      return packageAdministration;
   }

   public static RepositoryMgr getRepositoryMgr() {
      return repositoryMgr;
   }

   public static AdminMisc getAdminMiscUtilities() {
      return adminMisc;
   }
}
