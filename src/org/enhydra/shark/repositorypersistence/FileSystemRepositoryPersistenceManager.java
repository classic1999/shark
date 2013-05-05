package org.enhydra.shark.repositorypersistence;

import java.io.*;
import java.util.*;

import org.enhydra.shark.api.RepositoryTransaction;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.TransactionException;
import org.enhydra.shark.api.common.SharkConstants;
import org.enhydra.shark.api.internal.repositorypersistence.RepositoryException;
import org.enhydra.shark.api.internal.repositorypersistence.RepositoryPersistenceManager;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.utilities.MiscUtilities;
import org.enhydra.shark.xpdl.XMLUtil;

/**
 * File system implementation of Repository persistence interface.
 *
 * @author Sasa Bojanic
 *
 */
public class FileSystemRepositoryPersistenceManager implements RepositoryPersistenceManager {

   private CallbackUtilities cus;
   // The locations to various repositories
   private String XPDL_REPOSITORY;

   // Changed from private to public
   private String XPDL_HISTORY_REPOSITORY;

   private final String EXT_REF_FNAME="#ext_references#";
   private final String NEXT_IDS_FNAME="#next_ids#";
   private final String SER_PKG ="#ser_pkg#";
   private final String HASH = "#";
   
   private String EXT_REFERENCES_FILE_NAME;
   private String NEXT_IDS_FILE_NAME;

   private ExternalReferences extRefs;
   private NextVersions nextVersions;

   public void configure(CallbackUtilities cus) throws RootException {
      this.cus=cus;
      String xr=cus.getProperty("FileSystemRepositoryPersistenceManager.XPDL_REPOSITORY");
      String hxr=cus.getProperty("FileSystemRepositoryPersistenceManager.XPDL_HISTORY_REPOSITORY");
      XPDL_REPOSITORY=getRepositoryFullPath(xr);
      XPDL_HISTORY_REPOSITORY=getRepositoryFullPath(hxr);
      EXT_REFERENCES_FILE_NAME=XPDL_REPOSITORY+File.separator+EXT_REF_FNAME;
      NEXT_IDS_FILE_NAME=XPDL_REPOSITORY+File.separator+NEXT_IDS_FNAME;

      File xrf=new File(XPDL_REPOSITORY);
      if (!xrf.exists()) {
         xrf.mkdir();
      }
      File hxrf=new File(XPDL_HISTORY_REPOSITORY);
      if (!hxrf.exists()) {
         hxrf.mkdir();
      }

      File extR=new File(EXT_REFERENCES_FILE_NAME);
      if (extR.exists()) {
         try {
            extRefs=(ExternalReferences)readFile(EXT_REFERENCES_FILE_NAME);
         } catch (Exception ex) {
            throw new RootException(ex);
         }
      } else {
         extRefs=new ExternalReferences();
      }

      File nextV=new File(NEXT_IDS_FILE_NAME);
      if (nextV.exists()) {
         try {
            nextVersions=(NextVersions)readFile(NEXT_IDS_FILE_NAME);
         } catch (Exception ex) {
            throw new RootException(ex);
         }
      } else {
         nextVersions=new NextVersions();
      }
   }

   public void uploadXPDL (RepositoryTransaction t,String xpdlId,byte[] xpdl,byte[] serializedPkg,long xpdlClassVer) throws RepositoryException {
      try {
         String newVersion=nextVersions.updateNextVersion(xpdlId);
         writeFile(nextVersions,NEXT_IDS_FILE_NAME);
         FileOutputStream fos = new FileOutputStream(XPDL_REPOSITORY+File.separator+xpdlId+"-"+newVersion);
         fos.write(xpdl);
         // Write to file
         fos.flush();
         fos.close();
         fos = new FileOutputStream(XPDL_REPOSITORY+File.separator+xpdlId+"-"+SER_PKG+xpdlClassVer+HASH+"-"+newVersion);
         fos.write(serializedPkg);
         // Write to file
         fos.flush();
         fos.close();
      } catch (Exception ex) {
         cus.error("FileSystemRepositoryPersistenceManager -> The upload of the file "+xpdlId+" failed");
         throw new RepositoryException(ex);
      }
   }

   public void updateXPDL (RepositoryTransaction t,String xpdlId,String xpdlVersion,byte[] xpdl,byte[] serializedPkg,long xpdlClassVer) throws RepositoryException {
      try {
         getXPDLFile(xpdlId,xpdlVersion); // this will throw an exception if file doesn't exist
         deleteXPDL(t, xpdlId, xpdlVersion);
         FileOutputStream fos = new FileOutputStream(XPDL_REPOSITORY+File.separator+xpdlId+"-"+xpdlVersion);
         fos.write(xpdl);
         // Write to file
         fos.flush();
         fos.close();
         fos = new FileOutputStream(XPDL_REPOSITORY+File.separator+xpdlId+"-"+SER_PKG+xpdlClassVer+HASH+"-"+xpdlVersion);
         fos.write(serializedPkg);
         // Write to file
         fos.flush();
         fos.close();
      } catch (Exception ex) {
         cus.error("FileSystemRepositoryPersistenceManager -> The update of the file "+xpdlId+"-"+xpdlVersion+" failed");
         throw new RepositoryException(ex);
      }
   }

   public void deleteXPDL (RepositoryTransaction t,String xpdlId,String xpdlVersion) throws RepositoryException {
      try {
         if (!getXPDLFile(xpdlId,xpdlVersion).delete()) {
            throw new Exception("File "+xpdlId+"-"+xpdlVersion+" is not deleted from repository");
         }
         if (!getSerializedXPDLFile(xpdlId,xpdlVersion).delete()) {
            throw new Exception("The serialized pkg File "+xpdlId+"-"+xpdlVersion+" is not deleted from repository");
         }
         extRefs.removeReferrencedIds(xpdlId,xpdlVersion);
         writeFile(extRefs,EXT_REFERENCES_FILE_NAME);
      } catch (Exception ex) {
         throw new RepositoryException(ex);
      }
   }

   public void moveToHistory (RepositoryTransaction t,String xpdlId,String xpdlVersion) throws RepositoryException {
      try {
         File f1=getXPDLFile(xpdlId,xpdlVersion);
         File f2=getSerializedXPDLFile(xpdlId,xpdlVersion);

         String historyFolder=XPDL_HISTORY_REPOSITORY+File.separator+xpdlId;
         File fhf=new File(historyFolder);
         if (!fhf.exists()) {
            fhf.mkdir();
         }

         String historyFilename1=historyFolder+File.separator+f1.getName()+".xpdl";
         String historyFilename2=historyFolder+File.separator+f2.getName();
         MiscUtilities.copyFile(f1.getCanonicalPath(),historyFilename1);
         MiscUtilities.copyFile(f2.getCanonicalPath(),historyFilename2);

         // delete the removed file from internal repository
         if (!f1.delete() || !f2.delete()) {
            throw new Exception("File "+xpdlId+"-"+xpdlVersion+" is not deleted from repository");
         }
         extRefs.removeReferrencedIds(xpdlId,xpdlVersion);
         writeFile(extRefs,EXT_REFERENCES_FILE_NAME);
      } catch (Exception ex) {
         throw new RepositoryException(ex);
      }

   }

   public void deleteFromHistory (RepositoryTransaction t,String xpdlId,String xpdlVersion) throws RepositoryException {
      List xpdlFiles=getXPDLFiles(XPDL_HISTORY_REPOSITORY+File.separator+xpdlId,false,new XPDLIdFilter(xpdlId,xpdlVersion,true,false));
      if (xpdlFiles.size()>0) {
         if (!((File)xpdlFiles.get(0)).delete()) {
            throw new RepositoryException("File is not deleted from history repository");
         }
      }
      xpdlFiles=getXPDLFiles(XPDL_HISTORY_REPOSITORY+File.separator+xpdlId,false,new XPDLIdFilter(xpdlId,xpdlVersion,false,true));
      if (xpdlFiles.size()>0) {
         if (!((File)xpdlFiles.get(0)).delete()) {
            throw new RepositoryException("File is not deleted from history repository");
         }
      }

      throw new RepositoryException("There is no xpdl with Id="+xpdlId+", and version "+xpdlVersion+" in the repository");
   }

   public void clearRepository (RepositoryTransaction t) throws RepositoryException {
      try {
         List xpdlFiles=getXPDLFiles(XPDL_REPOSITORY,false,new XPDLIdFilter(null,null,false,false));
         Iterator itXPDL=xpdlFiles.iterator();
         while (itXPDL.hasNext()) {
            if (!((File)itXPDL.next()).delete()) {
               throw new Exception("Some file is not deleted from repository");
            }
         }
         xpdlFiles=getXPDLFiles(XPDL_REPOSITORY,false,new XPDLIdFilter(null,null,false,true));
         itXPDL=xpdlFiles.iterator();
         while (itXPDL.hasNext()) {
            if (!((File)itXPDL.next()).delete()) {
               throw new Exception("Some file is not deleted from repository");
            }
         }
      } catch (Exception ex) {
         throw new RepositoryException(ex);
      }
   }

   public String getCurrentVersion (RepositoryTransaction t,String xpdlId) throws RepositoryException {
      try {
         return getFileVersion(getXPDLFile(xpdlId,null));
      } catch (Exception ex) {
         throw new RepositoryException(ex);
      }
   }

   public String getNextVersion (RepositoryTransaction t,String xpdlId) throws RepositoryException {
      try {
         return nextVersions.getNextVersion(xpdlId);
      } catch (Exception ex) {
         throw new RepositoryException(ex);
      }
   }

   public long getSerializedXPDLObjectVersion (RepositoryTransaction t,String xpdlId,String xpdlVersion) throws RepositoryException {
      try {
         return Long.parseLong(getSerializedFileVersion(getSerializedXPDLFile(xpdlId,xpdlVersion)));
      } catch (Exception ex) {
         throw new RepositoryException(ex);
      }
   }

   public byte[] getXPDL (RepositoryTransaction t,String xpdlId) throws RepositoryException {
      try {
         return fileToByteArray(getXPDLFile(xpdlId,null));
      } catch (Exception ex) {
         throw new RepositoryException(ex);
      }
   }

   public byte[] getSerializedXPDLObject (RepositoryTransaction t,String xpdlId) throws RepositoryException {
      try {
         return fileToByteArray(getSerializedXPDLFile(xpdlId,null));
      } catch (Exception ex) {
         throw new RepositoryException(ex);
      }
   }

   public byte[] getXPDL (RepositoryTransaction t,String xpdlId,String xpdlVersion) throws RepositoryException {
      try {
         return fileToByteArray(getXPDLFile(xpdlId,xpdlVersion));
      } catch (Exception ex) {
         throw new RepositoryException(ex);
      }
   }

   public byte[] getSerializedXPDLObject (RepositoryTransaction t,String xpdlId,String xpdlVersion) throws RepositoryException {
      try {
         return fileToByteArray(getSerializedXPDLFile(xpdlId,xpdlVersion));
      } catch (Exception ex) {
         throw new RepositoryException(ex);
      }
   }

   public List getXPDLVersions (RepositoryTransaction t,String xpdlId) throws RepositoryException {
      List xpdlFiles=getXPDLFiles(XPDL_REPOSITORY,false,new XPDLIdFilter(xpdlId,null,false,false));
      if (xpdlFiles.size()==0) {
         throw new RepositoryException(
            "There is no xpdl with Id="+xpdlId+" in the repository");
      }
      List xpdlVersions=new ArrayList();
      Iterator itXPDL=xpdlFiles.iterator();
      while (itXPDL.hasNext()) {
         File f=(File)itXPDL.next();
         try {
            xpdlVersions.add(getFileVersion(f));
         } catch (Exception ex) {
            throw new RepositoryException(ex);
         }
      }
      return xpdlVersions;
   }

   public boolean doesXPDLExist (RepositoryTransaction t,String xpdlId) throws RepositoryException {
      try {
         getXPDLFile(xpdlId,null);
         return true;
      } catch (Exception ex) {
         return false;
      }
   }

   public boolean doesXPDLExist (RepositoryTransaction t,String xpdlId,String xpdlVersion) throws RepositoryException {
      try {
         getXPDLFile(xpdlId,xpdlVersion);
         return true;
      } catch (Exception ex) {
         return false;
      }
   }

   public List getExistingXPDLIds (RepositoryTransaction t) throws RepositoryException {
      List xpdlFiles=getXPDLFiles(XPDL_REPOSITORY,false,new XPDLIdFilter(null,null,false,false));
      Set ids=new HashSet();
      Iterator itXPDL=xpdlFiles.iterator();
      while (itXPDL.hasNext()) {
         File f=(File)itXPDL.next();
         String n=f.getName();
         int li=n.lastIndexOf("-");
         String fId=n.substring(0,li);
         ids.add(fId);
      }

      return new ArrayList(ids);
   }

   public void addXPDLReference (RepositoryTransaction t,
                                 String referredXPDLId,
                                 String referringXPDLId,
                                 String referringXPDLVersion,
                                 int referredXPDLNumber) throws RepositoryException {
      try {
         extRefs.addExtRef(referredXPDLId,referringXPDLId,referringXPDLVersion,referredXPDLNumber);
         writeFile(extRefs,EXT_REFERENCES_FILE_NAME);
      } catch (Exception ex) {
         extRefs.remExtRef(referredXPDLId,referringXPDLId,referringXPDLVersion);
         throw new RepositoryException(ex);
      }
   }

   public List getReferringXPDLIds (RepositoryTransaction t,String referredXPDLId) throws RepositoryException {
      return extRefs.getExtRefIds(referredXPDLId);
   }

   public List getReferringXPDLVersions (RepositoryTransaction t,String referredXPDLId,String refferingXPDLId) throws RepositoryException {
      return extRefs.getExtRefVersions(referredXPDLId,refferingXPDLId);
   }

   public List getReferredXPDLIds (RepositoryTransaction t,String refferingXPDLId,String refferingXPDLVersion) throws RepositoryException {
      return extRefs.getReferrencedIds(refferingXPDLId,refferingXPDLVersion);
   }

   public RepositoryTransaction createTransaction() throws TransactionException {
      return null;
   }

   private void writeFile (Object obj,String fName) throws Exception {
      OutputStream fos = new FileOutputStream(fName);
      ObjectOutputStream oout = new ObjectOutputStream(fos);
      oout.writeObject(obj);
      oout.flush();
      oout.close();
      fos.close();
   }

   private Object readFile (String fName) throws Exception {
      InputStream fis = new FileInputStream(fName);
      ObjectInputStream oin = new ObjectInputStream(fis);
      Object obj=oin.readObject();
      oin.close();
      return obj;
   }

   /**
    * Returns the full path to the repository based on given path.
    * If repository doesn't exist, the one is created at that location.
    *
    * @param path path (maybe relative) of the repository
    * @return String containing full path to the repository
    */
   private String getRepositoryFullPath (String path) {
      String rdPath=cus.getProperty(SharkConstants.ROOT_DIRECTORY_PATH_PROP);
      //System.setProperty("user.dir",rdPath);
      // if repository don't exist, create it
      File f=new File(path);

      if (!f.isAbsolute()) {
         f=new File(XMLUtil.createPath(rdPath,path));
         //f=f.getAbsoluteFile();
      }

      if (!f.exists()) {
         if (!f.mkdir()) {
            return path;
         }
      }

      try {
         return f.getCanonicalPath();
      } catch (Exception ex) {
         return f.getAbsolutePath();
      }
   }

   /**
    * Converts a file specified to the array of  bytes.
    */
   private byte[] fileToByteArray (File xpdlFile) throws Exception {
      // TODO: put this method in Utilities, and see about the one in SharkUtilties.
      byte[] utf8Bytes=null;
      if (xpdlFile != null) {
         try {
            FileInputStream fis=new FileInputStream(xpdlFile);
            int noOfBytes=fis.available();
            if (noOfBytes>0) {
               utf8Bytes=new byte[noOfBytes];
               // must do it byte by byte, otherwise sometimes it will be only partially read
               int nextB, i=0;
               while ((nextB=fis.read())!=-1) {
                  utf8Bytes[i++]=(byte)nextB;
               }
            }
         }
         catch (Throwable ex) {
            cus.error("FileSystemRepositoryPersistenceManager -> Problems while getting XPDL file "+xpdlFile);
            throw new Exception();
         }
      }
      return utf8Bytes;
   }

   private File getXPDLFile (String xpdlId,String xpdlVersion) throws Exception {
      List xpdlFiles=getXPDLFiles(XPDL_REPOSITORY,false,new XPDLIdFilter(xpdlId,xpdlVersion,false,false));
      if (xpdlFiles.size()>0) {
         return getLastVersionXPDLFile(xpdlFiles);
      }

      throw new Exception("There is no xpdl with Id="+xpdlId+", and version "+xpdlVersion+" in the repository");
   }

   private File getSerializedXPDLFile (String xpdlId,String xpdlVersion) throws Exception {
      List xpdlFiles=getXPDLFiles(XPDL_REPOSITORY,false,new XPDLIdFilter(xpdlId,xpdlVersion,false,true));
      if (xpdlFiles.size()>0) {
         return getLastVersionXPDLFile(xpdlFiles);
      }

      throw new Exception("There is no xpdl with Id="+xpdlId+", and version "+xpdlVersion+" in the repository");
   }

   private File getLastVersionXPDLFile (List xpdlFiles) throws Exception {
      File lastFileVersion=null;
      int maxVer=-1;
      Iterator itXPDL=xpdlFiles.iterator();
      while (itXPDL.hasNext()) {
         File f=(File)itXPDL.next();
         String fv=getFileVersion(f);
         int ver=Integer.parseInt(fv);
         if (ver>maxVer) {
            maxVer=ver;
            lastFileVersion=f;
         }
      }

      if (lastFileVersion==null) throw new Exception ("Something is wrong in XPDL repository - can't determine file version");

      return lastFileVersion;
   }

   private String getFileVersion (File f) throws RootException {
      String n=f.getName();
      int li=n.lastIndexOf("-");
      String fv=n.substring(li+1);
      return fv;
   }

   private String getSerializedFileVersion (File f) throws RootException {
      String n=f.getName();
      int li1=n.indexOf(SER_PKG);
      int li2=n.lastIndexOf(HASH);
      String fv=n.substring(li1+SER_PKG.length(),li2);
      return fv;
   }
   
   // TODO: put this method in utilitites, and see about the one in SharkUtilities
   private List getXPDLFiles (String repository,boolean traverse,FileFilter ff) {
      File startingFolder=new File(repository);
      List packageFiles=new ArrayList();
      if (traverse) {
         MiscUtilities.traverse(startingFolder,packageFiles,null);
      } else {
         packageFiles=Arrays.asList(startingFolder.listFiles(ff));
      }
      return packageFiles;
   }

   class XPDLIdFilter implements FileFilter {
      private String xpdlId;
      private String xpdlVersion;
      private boolean hasExtension;
      private boolean retrieveOnlySerialized; 

      public XPDLIdFilter (String xpdlId,String xpdlVersion,boolean hasExtension,boolean retrieveOnlySerialized) {
         this.xpdlId=xpdlId;
         this.xpdlVersion=xpdlVersion;
         this.hasExtension=hasExtension;
         this.retrieveOnlySerialized=retrieveOnlySerialized;
      }

      public boolean accept (File file) {

         if (file.isDirectory()) return false;

         String fileName=file.getName();
         boolean isSerFile=fileName.indexOf(SER_PKG)>=0;
         boolean accept=((retrieveOnlySerialized && isSerFile) || (!retrieveOnlySerialized && fileName.indexOf(HASH)<0));
         if (!accept) return false;
         if (xpdlId==null) {
            return true;
         } else {
            accept=fileName.startsWith(xpdlId);
            if (xpdlVersion==null) {
               return accept;
            } else {
               String endsWith=xpdlVersion;
               if (hasExtension) {
                  endsWith+=".xpdl";
               }
               return accept && fileName.endsWith(endsWith);
            }
         }
      }

   }

}

class ExternalReferences extends HashMap implements Serializable {

   public synchronized void addExtRef (String refTo,String refFromId,String refFromVersion,int refToNumber) {
      if (!containsKey(refTo)) {
         put(refTo,new HashSet());
      }
      Set s=(Set)get(refTo);
      addToSet(s,new RefFrom(refFromId,refFromVersion,refToNumber));
   }

   public synchronized void remExtRef (String refTo,String refFromId,String refFromVersion) {
      Set s=(Set)get(refTo);
      if (s!=null) {
         removeFromSet(s,new RefFrom(refFromId,refFromVersion));
      }
   }

   public List getExtRefIds (String refTo) {
      Set ret=new HashSet();
      Set s=(Set)get(refTo);
      if (s!=null) {
         Iterator it=s.iterator();
         while (it.hasNext()) {
            RefFrom rf=(RefFrom)it.next();
            ret.add(rf.getRefFromId());
         }
      }
      return new ArrayList(ret);
   }

   public List getExtRefVersions (String refTo,String refFromId) {
      List ret=new ArrayList();
      Set s=(Set)get(refTo);
      if (s!=null) {
         Iterator it=s.iterator();
         while (it.hasNext()) {
            RefFrom rf=(RefFrom)it.next();
            if (rf.getRefFromId().equals(refFromId)) {
               ret.add(rf.getRefFromVersion());
            }
         }
      }
      return ret;
   }

   public List getReferrencedIds (String refFromId,String refFromVersion) {
      List ret=new ArrayList();
      Iterator it=entrySet().iterator();
      while (it.hasNext()) {
         Map.Entry me=(Map.Entry)it.next();
         String refTo=(String)me.getKey();
         Set s=(Set)me.getValue();
         Iterator itS=s.iterator();
         while (itS.hasNext()) {
            RefFrom rf=(RefFrom)itS.next();
            
            Map temp=new HashMap();
            if (rf.getRefFromId().equals(refFromId) && rf.getRefFromVersion().equals(refFromVersion)) {
               temp.put(new Integer(rf.getRefNo()),refTo);
            }
            List tmp=new ArrayList(temp.keySet());
            // sort by number
            Collections.sort(tmp);
            for (int i=0; i<tmp.size(); i++) {
               ret.add(temp.get(tmp.get(i)));
            }

         }
      }
      return ret;
   }

   public synchronized void removeReferrencedIds (String refFromId,String refFromVersion) {
      List ret=getReferrencedIds(refFromId,refFromVersion);
      Iterator it=ret.iterator();
      while (it.hasNext()) {
         String refTo=(String)it.next();
         Set s=(Set)get(refTo);
         removeFromSet(s,new RefFrom(refFromId,refFromVersion));
         if (s.size()==0) {
            remove(refTo);
         }
      }
   }

   private void addToSet (Set s,RefFrom rf) {
      Iterator it=s.iterator();
      boolean contains=false;
      while (it.hasNext()) {
         RefFrom rfs=(RefFrom)it.next();
         if (rfs.equals(rf)) {
            contains=true;
            break;
         }
      }
      if (!contains) {
         s.add(rf);
      }
   }

   // we have to do this because set does not properly remove RefFrom (probably
   // some Java bug or something)
   private void removeFromSet (Set s,RefFrom rf) {
      Iterator it=s.iterator();
      RefFrom toRem=null;
      while (it.hasNext()) {
         RefFrom rfs=(RefFrom)it.next();
         if (rfs.equals(rf)) {
            toRem=rfs;
            break;
         }
      }
      s.remove(toRem);
   }
}

class RefFrom implements Serializable {
   private String refFromId;
   private String refFromVersion;
   private int refNo=-1;

   public RefFrom (String refFromId,String refFromVersion,int refNo) {
      this.refFromId=refFromId;
      this.refFromVersion=refFromVersion;
      this.refNo=refNo;
   }

   public RefFrom (String refFromId,String refFromVersion) {
      this.refFromId=refFromId;
      this.refFromVersion=refFromVersion;
   }

   public String getRefFromId () {
      return refFromId;
   }

   public String getRefFromVersion () {
      return refFromVersion;
   }

   public int getRefNo () {
      return refNo;
   }
   
   public boolean equals (Object obj) {
      boolean eq=false;
      if (obj instanceof RefFrom) {
         RefFrom refFrom=(RefFrom)obj;
         eq=(refFrom.refFromId.equals(this.refFromId) &&
                    refFrom.refFromVersion.equals(this.refFromVersion));
      }
      return eq;
   }

   public String toString () {
      return "[referringId="+refFromId+",referringVersion="+refFromVersion+", refNo="+refNo+"]";
   }

}

class NextVersions extends HashMap implements Serializable {
   private static final String INITIAL_VERSION = "1";


   public synchronized String getNextVersion (String xpdlId) {
      if (containsKey(xpdlId)) {
         return (String)get(xpdlId);
      }
      return INITIAL_VERSION;
   }

   public synchronized String updateNextVersion (String xpdlId) throws Exception {
      String curVersion=INITIAL_VERSION;
      String nextVersion=INITIAL_VERSION;

      if (containsKey(xpdlId)) {
         curVersion=(String)get(xpdlId);
      }

      int nver=Integer.parseInt(curVersion)+1;
      nextVersion=String.valueOf(nver);

      put(xpdlId,nextVersion);

      return curVersion;
   }
}
