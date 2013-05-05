package org.enhydra.shark;

import java.util.*;
import java.util.ListIterator;
import java.util.Map;
import org.enhydra.shark.api.*;
import org.enhydra.shark.api.client.wfbase.BaseException;
import org.enhydra.shark.api.client.wfbase.BaseIterator;
import org.enhydra.shark.api.client.wfbase.GrammarNotSupported;
import org.enhydra.shark.api.client.wfbase.InvalidQuery;
import org.enhydra.shark.api.client.wfbase.NameMismatch;
import org.enhydra.shark.api.common.SharkConstants;

/**
 * Base class for all iterators.
 *
 * @author Sasa Bojanic
 */
public abstract class BaseIteratorWrapper implements BaseIterator {

   protected String userAuth;
   protected List objectList;
   protected ListIterator objectListIterator;

   protected String queryExpression="";
   protected Map namesInExpression;
   protected String queryGrammar=SharkConstants.GRAMMAR_JAVA;
   protected boolean eval;
   protected String sqlWhere;
   protected String usedContext;
   protected boolean ignoreUsedContext;

   protected BaseIteratorWrapper (String userAuth) throws BaseException {
      this.userAuth=userAuth;
   }

   public String query_expression () throws BaseException {
      return queryExpression;
   }

   public String query_expression (SharkTransaction t) throws BaseException {
      return queryExpression;
   }

   public void set_query_expression(String query) throws BaseException,
                                                 InvalidQuery {
      set_query_expression(null, query);
   }

   public void set_query_expression (SharkTransaction t,String query) throws BaseException, InvalidQuery {
      queryExpression = query;
      if (queryExpression==null) queryExpression="";
      clearObjectList();
      eval = queryExpression.trim().length() > 0;
      sqlWhere = null;
      usedContext = "";
      ignoreUsedContext = true;

      if (eval) {
         int sqlBeginIndex = queryExpression.indexOf("/*sql ") + 6;
         int sqlEndIndex = queryExpression.indexOf(" sql*/");
         if (5 < sqlBeginIndex) {
            sqlWhere = queryExpression.substring(sqlBeginIndex, sqlEndIndex);
            if (0 > queryExpression.indexOf("/*FORCE*/")) {
               eval = false;
            }
         }
         int usedBeginIndex = queryExpression.indexOf("/*used ");
         if (usedBeginIndex==-1) return;
         usedBeginIndex += 7;
         int usedEndIndex = queryExpression.indexOf(" used*/");
         if (6 < usedBeginIndex) {
            usedContext = queryExpression.substring(usedBeginIndex,
                                                    usedEndIndex);
            ignoreUsedContext = false;
         }         
      }
   }

   public Map names_in_expression () throws BaseException {
      return namesInExpression;
   }

   public Map names_in_expression (SharkTransaction t) throws BaseException {
      return namesInExpression;
   }

   public void set_names_in_expression (Map query) throws BaseException, NameMismatch {
      namesInExpression=query;
   }

   public void set_names_in_expression (SharkTransaction t,Map query) throws BaseException, NameMismatch {
      namesInExpression=query;
   }


   public String query_grammar () throws BaseException {
      return queryGrammar;
   }

   public String query_grammar (SharkTransaction t) throws BaseException {
      return queryGrammar;
   }

   public void set_query_grammar (String query_grammar) throws BaseException, GrammarNotSupported {
      if (query_grammar!=null && (query_grammar.equals(SharkConstants.GRAMMAR_JAVA) ||
                                     query_grammar.equals(SharkConstants.GRAMMAR_JAVA_SCRIPT) ||
                                     query_grammar.equals(SharkConstants.GRAMMAR_PYTHON_SCRIPT))) {
         this.queryGrammar=query_grammar;
         clearObjectList();
      } else {
         String errMsg="Unsupported grammar "+query_grammar+". Supported grammars are:"+
            SharkConstants.GRAMMAR_JAVA+", "+
            SharkConstants.GRAMMAR_JAVA_SCRIPT+" and "+
            SharkConstants.GRAMMAR_PYTHON_SCRIPT+".";
         throw new GrammarNotSupported(errMsg);
      }
   }

   public void set_query_grammar (SharkTransaction t,String query_grammar) throws BaseException, GrammarNotSupported {
      if (query_grammar!=null && (query_grammar.equals(SharkConstants.GRAMMAR_JAVA) ||
                                     query_grammar.equals(SharkConstants.GRAMMAR_JAVA_SCRIPT) ||
                                     query_grammar.equals(SharkConstants.GRAMMAR_PYTHON_SCRIPT))) {
         this.queryGrammar=query_grammar;
         clearObjectList();
      } else {
         String errMsg="Unsupported grammar "+query_grammar+". Supported grammars are:"+
            SharkConstants.GRAMMAR_JAVA+", "+
            SharkConstants.GRAMMAR_JAVA_SCRIPT+" and "+
            SharkConstants.GRAMMAR_PYTHON_SCRIPT+".";
         throw new GrammarNotSupported(errMsg);
      }
   }

   public int how_many () throws BaseException {
      int ret=-1;
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         ret = how_many(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
      return ret;
   }

   public int how_many (SharkTransaction t) throws BaseException {
      fillObjectList(t);
      return objectList.size();
   }

   public void goto_start () throws BaseException {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         goto_start(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void goto_start (SharkTransaction t) throws BaseException {
      fillObjectList(t);
      while (objectListIterator.hasPrevious()) {
         objectListIterator.previous();
      }
   }

   public void goto_end () throws BaseException {
      SharkTransaction t = null;
      try {
         t = SharkUtilities.createTransaction();
         goto_end(t);
         //SharkUtilities.commitTransaction(t);
      } catch (RootException e) {
         //SharkUtilities.rollbackTransaction(t);
         SharkUtilities.emptyCaches(t);
         if (e instanceof BaseException)
            throw (BaseException)e;
         else
            throw new BaseException(e);
      } finally {
         SharkUtilities.releaseTransaction(t);
      }
   }

   public void goto_end (SharkTransaction t) throws BaseException {
      fillObjectList(t);
      while (objectListIterator.hasNext()) {
         objectListIterator.next();
      }
   }

   protected void setObjectList (List lst) {
      this.objectList=lst;
      this.objectListIterator=objectList.listIterator();
   }

   protected void clearObjectList () {
      this.objectList=null;
      this.objectListIterator=null;
   }

   protected abstract void fillObjectList (SharkTransaction t) throws BaseException;

   protected Object getNextObject (SharkTransaction t) throws BaseException {
      fillObjectList(t);
      Object ret=null;
      if (objectListIterator.hasNext()) {
         ret=objectListIterator.next();
      }
      if (ret==null) throw new BaseException("There is no next object!");
      return ret;
   }

   protected Object getPreviousObject (SharkTransaction t) throws BaseException {
      fillObjectList(t);
      Object ret=null;
      if (objectListIterator.hasPrevious()) {
         ret=objectListIterator.previous();
      }
      if (ret==null) throw new BaseException("No previous object!");
      return ret;
   }

   protected List getNextNSequence (SharkTransaction t,int max_number) throws BaseException {
      fillObjectList(t);
      List ret=new ArrayList();
      if (max_number<=0) {
         max_number=Integer.MAX_VALUE;
      }
      int i=0;
      while (objectListIterator.hasNext() && i<max_number) {
         Object next=objectListIterator.next();
         ret.add(next);
         i++;
      }

      return ret;
   }

   protected List getPreviousNSequence (SharkTransaction t,int max_number) throws BaseException {
      fillObjectList(t);
      List ret=new ArrayList();
      if (max_number<=0) {
         max_number=Integer.MAX_VALUE;
      }
      int i=0;
      while (objectListIterator.hasPrevious() && i<max_number) {
         Object prev=objectListIterator.previous();
         ret.add(prev);
         i++;
      }

      return ret;
   }

}
