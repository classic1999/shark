package org.enhydra.shark.util;


import java.util.*;
import org.enhydra.shark.api.RootError;

/**
* Implements the static methods used to get the multilanguage support.
*/
public class ResourceManager {
   private static ResourceBundle resourceBundle;

   static {
      try {
         /*resourceBundle = ResourceBundle.
          getBundle("org.enhydra.shark.resources.SharkServer",new Locale(""));*/
         resourceBundle = ResourceBundle.
            getBundle("org.enhydra.shark.resources.SharkServer");

         // chose the default system settings at the start
      } catch (MissingResourceException mre) {
System.err.println("ResourceManager -> org.enhydra.shark.resources.SharkServer.properties not found");
         throw new RootError("No property file!",mre);
      }
   }

   /**
    * Gets a language dependent string from the resource bundle.<p> Resource bundle
    * represents the <i>property file</i>. For example, if property file
    * contains something like this:<BR><CENTER>menubar=file edit help</CENTER>
    * method call getLanguageDependentString("menubar") will give the string
    * <i>file edit help</i> as a result. <BR> This method reads information
    * from property file. If can't find desired resource, returns <b>null</b>.
    * @param nm name of the resource to fetch.
    * @return String value of named resource.
    */
   public static String getLanguageDependentString (String nm) {
      String str;
      try {
         str=resourceBundle.getString(nm);
      } catch (MissingResourceException mre) {
         str="";
      }
      return str;
   }

}
