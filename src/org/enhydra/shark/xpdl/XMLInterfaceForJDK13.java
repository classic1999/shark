package org.enhydra.shark.xpdl;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
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

import org.apache.xerces.parsers.DOMParser;
import org.enhydra.shark.utilities.SequencedHashMap;
import org.enhydra.shark.xpdl.elements.ExternalPackage;
import org.enhydra.shark.xpdl.elements.Package;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 *  Implementation of XMLInterface that works with JDK1.3.
 *
 *  @author Sasa Bojanic
 */
public class XMLInterfaceForJDK13 implements XMLInterface {

   protected Map idToPackages=new SequencedHashMap();
   protected Map xmlFileToPackage=new SequencedHashMap();
   protected Map packageToParentDirectory=new SequencedHashMap();

   protected String mainPackageReference;

   protected Map parsingErrorMessages=new SequencedHashMap();
   protected boolean isValidationON=true;
   public void setValidation (boolean isActive) {
      isValidationON=isActive;
   }

   public void clearParserErrorMessages () {
      parsingErrorMessages.clear();
   }

   public synchronized boolean isPackageOpened (String pkgId) {
      return idToPackages.containsKey(pkgId);
   }

   public synchronized Package getPackageById (String pkgId) {
      ArrayList l=(ArrayList)idToPackages.get(pkgId);
      Package toRet=null;
      if (l!=null) {
         Iterator it=l.iterator();
         String lastVersion="-1";
         while (it.hasNext()) {
            Package p=(Package)it.next();
            String v=p.getInternalVersion();
            if (v.compareTo(lastVersion)>=0) {
               lastVersion=v;
               toRet=p;
            }
         }
      }
      return toRet;
   }

   public synchronized Package getPackageByIdAndVersion (String pkgId,String version) {
      ArrayList l=(ArrayList)idToPackages.get(pkgId);
      Package toRet=null;
      if (l!=null) {
         Iterator it=l.iterator();
         while (it.hasNext()) {
            Package p=(Package)it.next();
            String v=p.getInternalVersion();
            if (v.equals(version)) {
               toRet=p;
               break;
            }
         }
      }
      return toRet;
   }

   public synchronized Package getPackageByFilename (String filename) {
      filename=XMLUtil.getCanonicalPath(filename,"",false);
      return (Package)xmlFileToPackage.get(filename);
   }

   public synchronized Package getExternalPackageByRelativeFilePath (
      String relativePathToExtPkg,Package rootPkg) {

      File f=new File(relativePathToExtPkg);
      if (!f.isAbsolute()) {
         f=new File(getParentDirectory(rootPkg)+File.separator+relativePathToExtPkg);
      }
      if (f.exists()) {
         //System.out.println("Pkg for "+relativePathToExtPkg+"->"+f.getAbsolutePath()+" is found");
         return getPackageByFilename(f.getAbsolutePath());
      } else {
         //System.out.println("Pkg for "+relativePathToExtPkg+"->"+f.getAbsolutePath()+" is not found");
         return null;
      }
   }

   public synchronized String getAbsoluteFilePath (Package pkg) {
      Iterator it=xmlFileToPackage.entrySet().iterator();
      String fullPath=null;
      while (it.hasNext()) {
         Map.Entry me=(Map.Entry)it.next();
         String u=(String)me.getKey();
         Package p=(Package)me.getValue();
         if (p.equals(pkg)) {
            fullPath=u;
            break;
         }
      }
      return fullPath;
   }

   public synchronized Collection getAllPackages () {
      ArrayList l=new ArrayList();
      Iterator it=idToPackages.values().iterator();
      while (it.hasNext()) {
         l.addAll((ArrayList)it.next());
      }
      return l;
   }

   public synchronized Collection getAllPackageIds () {
      return idToPackages.keySet();
   }

   public Collection getAllPackageVersions (String pkgId) {
      ArrayList l=new ArrayList();
      ArrayList all=(ArrayList)idToPackages.get(pkgId);
      if (all!=null) {
         Iterator it=all.iterator();
         while (it.hasNext()) {
            l.add(((Package)it.next()).getInternalVersion());
         }
      }
      return l;
   }

   public synchronized Collection getAllPackageFilenames () {
      return xmlFileToPackage.keySet();
   }

   public synchronized boolean doesPackageFileExists (String xmlFile) {
      if (new File(xmlFile).exists()) {// || getPackageFileContent(xmlFile)!=null) {
         return true;
      } else {
         return false;
      }
   }

   public synchronized String getParentDirectory (Package pkg) {
      return (String)packageToParentDirectory.get(pkg);
   }

   public Package openPackage (String pkgReference,
                               boolean handleExternalPackages) {
      //long t1,t2;
      //t1=System.currentTimeMillis();
      parsingErrorMessages.clear();
      mainPackageReference=pkgReference;

      // this method opens the package. It also opens all of it's external packages
      // if handleExternalPackages is set to true
      Package pkg=openPackageRecursively(pkgReference,handleExternalPackages);

      //printDebug();
      //t2=System.currentTimeMillis();
      //System.out.println("OPT="+(t2-t1));
      return pkg;
   }

   public void printDebug () {
      System.out.println("idToPackage="+idToPackages);
      System.out.println("xmlFileToPackage="+xmlFileToPackage);
      System.out.println("packageToWorkingDirectory="+packageToParentDirectory);
      //Package.printDebug();
   }

   // Recursive implementation
   // pkgReference MUST be absolute path
   protected Package openPackageRecursively (String pkgReference,
                                   boolean handleExternalPackages) {

      Package pkg=null;
      File f=null;
      String oldP=pkgReference;

      String baseDirectory=null;
      pkgReference=XMLUtil.getCanonicalPath(pkgReference,"",false);
      if (pkgReference==null) {
         Set fem=new HashSet();
         fem.add("File does not exist");
         parsingErrorMessages.put(oldP,fem);
         return null;
      } else {
         f=new File(pkgReference);
         try {
            baseDirectory=f.getParentFile().getCanonicalPath();
         } catch (Exception ex) {
            baseDirectory=f.getParentFile().getAbsolutePath();
         }
         }

      if (xmlFileToPackage.containsKey(pkgReference)) {
         return getPackageByFilename(pkgReference);
      }

      pkg=parseDocument(pkgReference,true);

      if (pkg!=null) {
         String pkgId=pkg.getId();
         // check if package is already imported
         if (idToPackages.containsKey(pkgId)) {
            // check if this is the same package, or just the one with the same id
            if (xmlFileToPackage.containsKey(pkgReference)) {
               return getPackageById(pkgId);
            } else {
               throw new RuntimeException("Can't open two packages with the same Id");
            }
         }
         ArrayList l=(ArrayList)idToPackages.get(pkgId);
         if (l==null) {
            l=new ArrayList();
         }
         l.add(pkg);
         idToPackages.put(pkgId,l);
         xmlFileToPackage.put(pkgReference,pkg);
         try {
            packageToParentDirectory.put(pkg,f.getParentFile().getCanonicalPath());
         } catch (Exception ex) {
            packageToParentDirectory.put(pkg,f.getParentFile().getAbsolutePath());
         }

         // open all external packages if handleExternalPackages is set to true,
         // otherwise, it assumes that if there are external packages, the
         // href element is similar to their Ids
         Iterator eps=pkg.getExternalPackages().toElements().iterator();
         while (eps.hasNext()) {
            String pathToExtPackage=((ExternalPackage)eps.next()).getHref();
            String extPkgId=null;
            if (handleExternalPackages) {
               // setting working dir to be the one of the current package
               String ptep=XMLUtil.getCanonicalPath(pathToExtPackage,baseDirectory,false);
               //System.setProperty("user.dir",packageToParentDirectory.get(pkg).toString());
               Package extPkg=openPackageRecursively(ptep,handleExternalPackages);
               extPkgId=extPkg.getId();
            } else {
               extPkgId=XMLUtil.getExternalPackageId(pathToExtPackage);
            }
            pkg.addExternalPackageMapping(pathToExtPackage, extPkgId);
         }
      } else {
         System.err.println("Problems with opening file "+pkgReference);
      }
      return pkg;
   }


   /**
    * Opens all the packages represented by their streams, and returns first of them.
    * This implementation assumes that external package references have the similar
    * name as Id of external package, i.e. if external package reference
    * is ../tests/maintest/testPackage.xpdl, it is assumed that external package's Id
    * is testPackage.
    */
   public Package openPackagesFromStreams (List pkgContents,boolean isFileStream) throws Exception {
      Package pkg=null;
      for (int i=0; i<pkgContents.size(); i++) {
         byte[] pkgCont=(byte[])pkgContents.get(i);
         Package p=openPackageFromStream(pkgCont,isFileStream);
         if (i==0) {
            pkg=p;
         }
      }
      return pkg;
   }

   /**
    * This implementation assumes that external package references have the similar
    * name as Id of external package, i.e. if external package reference
    * is ../tests/maintest/testPackage.xpdl, it is assumed that external package's Id
    * is testPackage.
    */
   public Package openPackageFromStream (byte[] pkgContent,boolean isFileStream) throws Exception {
      Package pkg=null;
      if (isFileStream) {
         String fileContStr=new String(pkgContent,"UTF8");
         pkg=parseDocument(fileContStr, false);
      } else {
         pkg=(Package)XMLUtil.deserialize(pkgContent);
      }
      if (pkg!=null) {
         String pkgId=pkg.getId();
         ArrayList l=(ArrayList)idToPackages.get(pkgId);
         if (l==null) {
            l=new ArrayList();
         }
         if (!l.contains(pkg)) {
            l.add(pkg);
         }
         idToPackages.put(pkgId,l);
         Iterator eps=pkg.getExternalPackages().toElements().iterator();
         while (eps.hasNext()) {
            String pathToExtPackage=((ExternalPackage)eps.next()).getHref();
            String extPkgId=pkg.getExternalPackageId(pathToExtPackage);
            if (extPkgId==null) {
               extPkgId=XMLUtil.getExternalPackageId(pathToExtPackage);
               pkg.addExternalPackageMapping(pathToExtPackage, extPkgId);
            }
         }
      }
      return pkg;
   }

   public Package parseDocument (String toParse,boolean isFile) {
      //long t1=0, t2=0, t3=0;
      //t1=System.currentTimeMillis();
      Package pkg=null;
      //  Create a Xerces DOM Parser
      DOMParser parser = new DOMParser();

      //  Parse the Document and traverse the DOM
      try {
         parser.setFeature("http://apache.org/xml/features/continue-after-fatal-error",true);
         ParsingErrors pErrors=new ParsingErrors();
         parser.setErrorHandler(pErrors);
         if (isValidationON) {
            parser.setEntityResolver(new XPDLEntityResolver());
            parser.setFeature("http://xml.org/sax/features/validation",true);
            parser.setFeature("http://apache.org/xml/features/validation/schema",true);
            //parser.setFeature("http://apache.org/xml/features/validation/schema-full-checking",true);
         }
         if (isFile) {
            //System.out.println("Parsing from file");
            //parser.parse(toParse);
            File f=new File(toParse);
            if (!f.exists()) {
               f=new File(f.getCanonicalPath());
            }
            parser.parse(new InputSource(new FileInputStream(f))); // Fixed by Harald Meister
         } else {
            //System.out.println("Parsing from stream");
            parser.parse(new InputSource(new StringReader(toParse)));
         }
         Document document = parser.getDocument();
         Set errorMessages = pErrors.getErrorMessages();
         if (errorMessages.size()>0) {
            //System.err.println("Errors during document parsing");
            if (isFile) {
               parsingErrorMessages.put(toParse,errorMessages);
            } else {
               parsingErrorMessages.put("",errorMessages);
            }
         }
         if (document!=null) {
            //pkg=new Package(this);
            pkg=new Package();
            //t2=System.currentTimeMillis();
            XMLUtil.fromXML(document.getDocumentElement(),pkg);
            //System.out.println("package "+pkg+" imported");
         }
      } catch (Exception ex) {
         ex.printStackTrace();
         System.err.println("Fatal error while parsing document");
         Set fem=new HashSet();
         fem.add("Fatal error while parsing document");
         if (isFile) {
            parsingErrorMessages.put(toParse,fem);
         } else {
            parsingErrorMessages.put("",fem);
         }
         return null;
      }
      //t3=System.currentTimeMillis();
      //System.out.println("TOverall  ="+(t3-t1));
      //System.out.println("TParse  ="+(t2-t1));
      //System.out.println("TFXML ="+(t3-t2));

      return pkg;
   }

   /**
    * This method should be called immediatelly after opening a document,
    * otherwise, messages could be invalid.
    * @return The map which keys are opened packages, and values are the sets
    * of errors for corresponding package.
    */
   public Map getParsingErrorMessages () {
      return parsingErrorMessages;
   }

   public synchronized List closePackages (String pkgId) {
      ArrayList l=(ArrayList)idToPackages.remove(pkgId);
      if (l!=null) {
         // removing file to package mapping
         Iterator itr=l.iterator();
         while (itr.hasNext()) {
            Package toRemove=(Package)itr.next();
            Iterator it=xmlFileToPackage.entrySet().iterator();
            Object keyToRemove=null;
            while (it.hasNext()) {
               Map.Entry me=(Map.Entry)it.next();
               Object key=me.getKey();
               Object val=me.getValue();
               if (val.equals(toRemove)) {
                  keyToRemove=key;
                  break;
               }
            }
            if (keyToRemove!=null) {
               xmlFileToPackage.remove(keyToRemove);
            }
            packageToParentDirectory.remove(toRemove);
         }

      }
      return l;
   }

   public synchronized Package closePackageVersion (String pkgId,String pkgVer) {
      ArrayList l=(ArrayList)idToPackages.get(pkgId);
      Package toRemove=null;
      if (l!=null) {
         // removing file to package mapping
         Iterator itr=l.iterator();
         while (itr.hasNext()) {
            Package p=(Package)itr.next();
            if (p.getInternalVersion().equals(pkgVer)) {
               toRemove=p;
               break;
            }
         }
         if (toRemove!=null) {
            Iterator it=xmlFileToPackage.entrySet().iterator();
            Object keyToRemove=null;
            while (it.hasNext()) {
               Map.Entry me=(Map.Entry)it.next();
               Object key=me.getKey();
               Object val=me.getValue();
               if (val.equals(toRemove)) {
                  keyToRemove=key;
                  break;
               }
            }
            if (keyToRemove!=null) {
               xmlFileToPackage.remove(keyToRemove);
            }
            packageToParentDirectory.remove(toRemove);
         }

      }
      return toRemove;
   }

   public synchronized void closeAllPackages () {
      /*System.out.println("I'm XI "+this.hashCode() +" - closing pkgs=");
       Iterator it=idToPackage.values().iterator();
       while(it.hasNext()) {
       Package p=(Package)it.next();
       System.out.println("    p="+p.getId()+", hc="+p.hashCode());
       }*/
      idToPackages.clear();
      xmlFileToPackage.clear();
      packageToParentDirectory.clear();
   }

   public synchronized String getIdFromFile (String xmlFile) {
      try {
         //  Create parser
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         factory.setValidating(false);
         DocumentBuilder parser = factory.newDocumentBuilder();
         Document document=null;

         //  Parse the Document
         try {
            //document=parser.parse(xmlFile);
            File f=new File(xmlFile);
            if (!f.exists()) {
               f=new File(f.getCanonicalPath());
            }
            document=parser.parse(new InputSource(new FileInputStream(f))); // Fixed by Harald Meister
         } catch (Exception ex) {
            document=parser.parse(new InputSource(new StringReader(xmlFile)));
         }
         return XMLUtil.getId(document.getDocumentElement());
      } catch (Exception ex) {
         return "";
      }
   }

   public synchronized void synchronizePackages (XMLInterface xmlInterface) {
      closeAllPackages();

      Iterator it=xmlInterface.getAllPackages().iterator();
      while (it.hasNext()) {
         Package pkg=(Package)it.next();
         String pkgId=pkg.getId();

         ArrayList l=(ArrayList)idToPackages.get(pkgId);
         if (l==null) {
            l=new ArrayList();
         }
         l.add(pkg);
         idToPackages.put(pkgId,l);
         String fp=xmlInterface.getAbsoluteFilePath(pkg);
         if (fp!=null) {
            xmlFileToPackage.put(fp,pkg);
         }
         String pd=xmlInterface.getParentDirectory(pkg);
         if (pd!=null) {
            packageToParentDirectory.put(pkg,pd);
         }
      }
   }

}

