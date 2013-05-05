package org.enhydra.shark.xpdl;

import java.io.InputStream;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * Replaces the internet location of the XPDL1.0 schema, with its content read
 * from resources.
 * 
 *  @author Sasa Bojanic
 */
public class XPDLEntityResolver implements EntityResolver {

   public static final String XPDL_SCHEMA = "org/enhydra/shark/xpdl/resources/TC-1025_schema_10_xpdl.xsd";

   public InputSource resolveEntity (String publicId,String systemId) {
//System.out.println("pId="+publicId+", sId="+systemId);
      if (systemId!=null) {
         return getSchemaInputSource();
      } else {
         // use the default behaviour
         return null;
      }
   }

   public static InputSource getSchemaInputSource () {
      InputStream is=null;
      try {
         java.net.URL u=
               XPDLEntityResolver.class.getClassLoader().getResource(XPDL_SCHEMA);
         is=(InputStream)u.getContent();
         return new InputSource(is);
      } catch (Exception ex) {
         return null;
      }
   }
}

