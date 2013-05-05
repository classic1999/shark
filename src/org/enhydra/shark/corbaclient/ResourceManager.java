/* ResourceManager.java
 *
 * Authors:
 * Stefanovic Nenad  chupo@iis.ns.ac.yu
 * Bojanic Sasa      sasaboy@neobee.net
 * Puskas Vladimir   vpuskas@eunet.yu
 * Pilipovic Goran   zboniek@uns.ac.yu
 *
 */

package org.enhydra.shark.corbaclient;

import java.net.*;
import java.util.*;

/**
* Implements the static methods that are used to implement
* multilanguage support, and to create some resources as
* menubar, toolbars and button panels.
*/
public class ResourceManager {
private static final String resourcePath=
         "org.enhydra.shark.corbaclient.resources.SharkClient";

   public static Locale defaultLocale;
   public static ResourceBundle defaultResource;
   public static Locale choosenLocale;
   public static ResourceBundle choosenResource;


   static {
      try {
         // default is english
         defaultLocale = new Locale("en");
         defaultResource = ResourceBundle.
            getBundle(resourcePath,new Locale(""));

         // chose the default system settings at the start
         choosenLocale = Locale.getDefault();
         choosenResource = ResourceBundle.getBundle(resourcePath,choosenLocale);
      }
      catch (MissingResourceException mre) {
         System.err.println(resourcePath+".properties not found");
         System.exit(1);
      }
   }

   /**
    * Gets a resource string from the resource bundle.<p> Resource bundle
    * represents the <i>property file</i>. For example, if property file
    * contains something like this:<BR><CENTER>menubar=file edit help</CENTER>
    * method call getResourceString("menubar") will give the string
    * <i>file edit help</i> as a result. <BR> This method reads information
    * from property file. If can't find desired resource, returns <b>null</b>.
    * @param nm name of the resource to fetch.
    * @return String value of named resource.
    */
   public static String getLanguageDependentString (String nm) {
      String str;
      try {
         str=choosenResource.getString(nm);
      } catch (MissingResourceException mre) {
         try {
            str=defaultResource.getString(nm);
         } catch (MissingResourceException mre1) {
            str = null;
         }
      }
      return str;
   }

   /**
    * Gets the url from a resource string.
    * @param key the string key to the url in the resource bundle.
    * @return the resource location.
    * @see java.lang.Class#getResource
    */
   public static URL getResource (String key) {
      String name = getLanguageDependentString(key);
      if (name != null) {
         URL url = ResourceManager.class.getClassLoader().getResource(name);
         return url;
      }
      return null;
   }


   public static void setDefault () {
      choosenResource=defaultResource;
      choosenLocale=defaultLocale;
   }

   /** Returns the default resource bundle.
    *
    */
   public static ResourceBundle getDefaultResource() {
      return defaultResource;
   }

   /** Returns the current locale.
    *
    */
   public static ResourceBundle getChoosenResource() {
      return choosenResource;
   }

   /** Returns the default locale.
    *
    */
   public static Locale getDefaultLocale() {
      return defaultLocale;
   }

   /** Returns the current locale.
    *
    */
   public static Locale getChoosenLocale() {
      return choosenLocale;
   }

   /**
    * Sets the new resource and locale to be used.
    */
   public static void setChoosen (String language) throws MissingResourceException {
      choosenLocale = new Locale(language);
      String bundle=resourcePath+"_"+language;
      choosenResource = ResourceBundle.getBundle(bundle);
   }

   /**
    * Sets the new resource and locale to be used.
    */
   public static void setChoosen (String language,String country) throws MissingResourceException {
      choosenLocale = new Locale(language,country);
      String bundle=resourcePath+"_"+language+"_"+country;
      choosenResource = ResourceBundle.getBundle(bundle);
   }

   /**
    * Sets the new resource and locale to be used.
    */
   public static void setChoosen (String language,String country,String variant) throws MissingResourceException {
      choosenLocale = new Locale(language,country,variant);
      String bundle=resourcePath+"_"+language+"_"+country+"_"+variant;
      choosenResource = ResourceBundle.getBundle(bundle);
   }

}

