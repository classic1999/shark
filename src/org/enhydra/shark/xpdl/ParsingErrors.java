package org.enhydra.shark.xpdl;

import java.util.HashSet;
import java.util.Set;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Class that serves as an ErrorHandler for DOM parser.
 *  @author Sasa Bojanic
 */
public class ParsingErrors implements ErrorHandler {

   public ParsingErrors () {
      super();
   }

   Set errorMessages = new HashSet();

   public void warning(SAXParseException ex) {
      store(ex, "[Warning]");
   }

   public void error(SAXParseException ex) {
      store(ex, "[Error]");
   }

   public void fatalError(SAXParseException ex) throws SAXException {
      store(ex, "[Fatal Error]");
   }

   public Set getErrorMessages() {
      return errorMessages;
   }

   public void clearErrors() {
      errorMessages.clear();
   }

   void store(SAXParseException ex, String type) {
      // build error text
      String errorString= type+" at line number "+ex.getLineNumber()
         +": "+ex.getMessage()+"\n";
      errorMessages.add(errorString);
   }
}
