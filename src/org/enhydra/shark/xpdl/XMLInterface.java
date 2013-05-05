package org.enhydra.shark.xpdl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.enhydra.shark.xpdl.elements.Package;

/**
* Class which purpose is to provide methods which are
* used by classes that represents program apstraction of
* XML elements. These methods offers support for reading or
* writting an XML document.
 *
 *  @author Sasa Bojanic
 */
public interface XMLInterface {

   void setValidation (boolean isActive);

   public void clearParserErrorMessages ();

   public boolean isPackageOpened (String pkgId);

   public Package getPackageById (String pkgId);

   public Package getPackageByIdAndVersion (String pkgId,String version);

   public Package getPackageByFilename (String filename);

   public Package getExternalPackageByRelativeFilePath (
   String relativePathToExtPkg,Package rootPkg);

   public String getAbsoluteFilePath (Package pkg);

   public Collection getAllPackages ();

   public Collection getAllPackageIds ();

   public Collection getAllPackageVersions (String pkgId);

   public Collection getAllPackageFilenames ();

   public boolean doesPackageFileExists (String xmlFile);

   public String getParentDirectory (Package pkg);

   public Package openPackage (String pkgReference,boolean handleExternalPackages);

   public Package openPackagesFromStreams (List pkgContents,boolean isFileStream) throws Exception;

   public Package openPackageFromStream (byte[] pkgContent,boolean isFileStream) throws Exception;

   public Package parseDocument (String toParse,boolean isFile);

   /**
    * This method should be called immediatelly after opening a document,
    * otherwise, messages could be invalid.
    * @return The map which keys are opened packages, and values are the sets
    * of errors for corresponding package.
    */
   public Map getParsingErrorMessages ();

   public List closePackages (String pkgId);

   public Package closePackageVersion (String pkgId,String version);

   public void closeAllPackages ();

   public String getIdFromFile (String xmlFile);

   public void synchronizePackages (XMLInterface xmlInterface);
}

