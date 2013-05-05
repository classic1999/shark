/* BasicExpressionBuilder.java */

package org.enhydra.shark.expressionbuilders;

import java.io.Serializable;
import java.util.*;

import org.enhydra.shark.api.common.ExpressionBuilder;

/**
 * BasicExpressionBuilder implements core expression builder
 * functionality.
 * 
 * @author V.Puskas
 * @version 0.21
 */
public abstract class BasicExpressionBuilder implements Serializable {
   protected List bshExpression;

   protected List sqlExpression;

   protected List propertiesUsed;

   private String orderBy;

   protected boolean sqlComplete;

   protected int operator;

   protected char delimiter = '\'';

   protected boolean usingStandardVariableModel;

   protected String objectid_column_name = " oid ";

   public static final String PARAM_NAME_STRING_DELIMITER = "ExpressionBuilder.StringDelimiter";

   public static final String PARAM_NAME_OBJECTID_COLUMN_NAME = "DatabaseManager.ObjectIdColumnName";

   public static final String PARAM_NAME_VARIABLE_MODEL = "DODSEventAuditManager.useStandardVariableDataModel";

   /**
    * There are several parameters needed for expression building to
    * work properly. Constructor parameter may contain:
    * <tt>ExpressionBuilder.StringDelimiter</tt> and
    * <tt>DatabaseManager.ObjectIdColumnName</tt>.
    * 
    * @param p property object: probably
    *           <code>Shark.getInstance().getProperties()</code>, but
    *           we're nop picky :-) it could even be null in which case
    *           we'll use hardcoded defaults for configurable parameters
    */
   protected BasicExpressionBuilder(Properties p) {
      this.bshExpression = new ArrayList();
      this.sqlExpression = new ArrayList();
      this.propertiesUsed = new ArrayList();
      this.sqlComplete = true;
      if (null != p) {
         this.objectid_column_name = " "
                                     + p.getProperty(PARAM_NAME_OBJECTID_COLUMN_NAME,
                                                     "oid") + " ";
         this.usingStandardVariableModel = Boolean.valueOf(p.getProperty(PARAM_NAME_VARIABLE_MODEL,
                                                                         "true"))
            .booleanValue();
         this.delimiter = p.getProperty(PARAM_NAME_STRING_DELIMITER, "'")
            .charAt(0);
      }
   }

   /**
    * Some database vendors are peculiar about string(CHAR) delimiters,
    * thus need for its indirect usage.
    * 
    * @return currently configured delimiter
    */
   protected char getDelimiter() {
      return delimiter;
   }

   /**
    * Every public method on expression builder class will end-up
    * updating both native script and sql expression.
    * 
    * @param sqlToo if method is certain that
    * @return either blank ' ' or exclamation mark '!' chararter to
    *         insert in front of the next clause
    */
   protected char appendOperator(boolean sqlToo) {
      char ret = ' ';
      if (ExpressionBuilder.AND_OPERATOR == (ExpressionBuilder.AND_OPERATOR & this.operator)) {
         this.bshExpression.add(" && ");
         if (sqlToo) this.sqlExpression.add(" AND ");
      } else if (ExpressionBuilder.OR_OPERATOR == (ExpressionBuilder.OR_OPERATOR & this.operator)) {
         this.bshExpression.add("||");
         if (sqlToo) this.sqlExpression.add(" OR ");
      }
      if (ExpressionBuilder.NOT_OPERATOR == (ExpressionBuilder.NOT_OPERATOR & this.operator)) {
         ret = '!';
      }
      this.operator = 0;
      return ret;
   }

   protected void addEquals(String java_name, String sql_name, String value) {
      char _notPrecedes = appendOperator(true);
      this.bshExpression.add(_notPrecedes
                             + java_name + ".equals(\"" + value + "\")");
      this.sqlExpression.add(sql_name
                             + _notPrecedes + "= " + getDelimiter() + value
                             + getDelimiter() + " ");
      if (!this.propertiesUsed.contains(java_name)) {
         this.propertiesUsed.add(java_name);
      }
   }

   protected void addEquals(String java_name, String sql_name, long value) {
      char _notPrecedes = appendOperator(true);
      this.bshExpression.add(java_name
                             + ".longValue()" + _notPrecedes + "== " + value);
      this.sqlExpression.add(sql_name + _notPrecedes + "= " + value + " ");
      if (!this.propertiesUsed.contains(java_name)) {
         this.propertiesUsed.add(java_name);
      }
   }

   protected void addGreaterThan(String java_name, String sql_name, long value) {
      char _notPrecedes = appendOperator(true);
      String operator = ' ' == _notPrecedes ? " > " : " <= ";
      this.bshExpression.add(java_name + ".longValue()" + operator + value);
      this.sqlExpression.add(sql_name + operator + value + " ");
      if (!this.propertiesUsed.contains(java_name)) {
         this.propertiesUsed.add(java_name);
      }
   }

   protected void addLessThan(String java_name, String sql_name, long value) {
      char _notPrecedes = appendOperator(true);
      String operator = ' ' == _notPrecedes ? " < " : " >= ";
      this.bshExpression.add(java_name + ".longValue()" + operator + value);
      this.sqlExpression.add(sql_name + operator + value + " ");
      if (!this.propertiesUsed.contains(java_name)) {
         this.propertiesUsed.add(java_name);
      }
   }

   protected void addGreaterThanWithSubQuery(String javaName,
                                             String sqlName,
                                             String sqlInPart,
                                             long value,
                                             String sqlEndPart) {
      char _notPrecedes = appendOperator(true);
      String operator = ' ' == _notPrecedes ? " > " : " <= ";
      this.bshExpression.add(javaName + ".longValue()" + operator + value);
      this.sqlExpression.add(sqlName
                             + (' ' == _notPrecedes ? "" : "NOT ")
                             + sqlInPart + operator + value + sqlEndPart);
      if (!this.propertiesUsed.contains(javaName)) {
         this.propertiesUsed.add(javaName);
      }
   }

   protected void addLessThanWithSubQuery(String javaName,
                                          String sqlName,
                                          String sqlInPart,
                                          long value,
                                          String sqlEndPart) {
      char _notPrecedes = appendOperator(true);
      String operator = ' ' == _notPrecedes ? " < " : " >= ";
      this.bshExpression.add(javaName + ".longValue()" + operator + value);
      this.sqlExpression.add(sqlName
                             + (' ' == _notPrecedes ? "" : "NOT ")
                             + sqlInPart + operator + value + sqlEndPart);
      if (!this.propertiesUsed.contains(javaName)) {
         this.propertiesUsed.add(javaName);
      }
   }

   protected void addEqualsWithSubQuery(String javaName,
                                        String sqlName,
                                        String sqlInPart,
                                        long value,
                                        String sqlEndPart) {
      char _notPrecedes = appendOperator(true);
      this.bshExpression.add(_notPrecedes
                             + javaName + ".longValue() == " + value);
      this.sqlExpression.add(sqlName
                             + (' ' == _notPrecedes ? "" : "NOT ")
                             + sqlInPart + value + sqlEndPart);
      if (!this.propertiesUsed.contains(javaName)) {
         this.propertiesUsed.add(javaName);
      }
   }

   protected void addGreaterThanWithSubQuery(String javaName,
                                             String sqlName,
                                             String sqlInPart,
                                             double value,
                                             String sqlEndPart) {
      char _notPrecedes = appendOperator(true);
      String operator = ' ' == _notPrecedes ? " > " : " <= ";
      this.bshExpression.add(javaName + ".doubleValue()" + operator + value);
      this.sqlExpression.add(sqlName
                             + (' ' == _notPrecedes ? "" : "NOT ")
                             + sqlInPart + operator + value + sqlEndPart);
      if (!this.propertiesUsed.contains(javaName)) {
         this.propertiesUsed.add(javaName);
      }
   }

   protected void addLessThanWithSubQuery(String javaName,
                                          String sqlName,
                                          String sqlInPart,
                                          double value,
                                          String sqlEndPart) {
      char _notPrecedes = appendOperator(true);
      String operator = ' ' == _notPrecedes ? " < " : " >= ";
      this.bshExpression.add(javaName + ".doubleValue()" + operator + value);
      this.sqlExpression.add(sqlName
                             + (' ' == _notPrecedes ? "" : "NOT ")
                             + sqlInPart + operator + value + sqlEndPart);
      if (!this.propertiesUsed.contains(javaName)) {
         this.propertiesUsed.add(javaName);
      }
   }

   protected void addEqualsWithSubQuery(String javaName,
                                        String sqlName,
                                        String sqlInPart,
                                        double value,
                                        String sqlEndPart) {
      char _notPrecedes = appendOperator(true);
      this.bshExpression.add(_notPrecedes
                             + javaName + ".doubleValue() == " + value);
      this.sqlExpression.add(sqlName
                             + (' ' == _notPrecedes ? "" : "NOT ")
                             + sqlInPart + value + sqlEndPart);
      if (!this.propertiesUsed.contains(javaName)) {
         this.propertiesUsed.add(javaName);
      }
   }

   protected void addEqualsWithSubQuery(String javaName,
                                        String sqlName,
                                        String sqlInPart,
                                        String value,
                                        String sqlEndPart) {
      char _notPrecedes = appendOperator(true);
      this.bshExpression.add(_notPrecedes
                             + javaName + ".equals(\"" + value + "\")");
      this.sqlExpression.add(sqlName
                             + (' ' == _notPrecedes ? "" : "NOT ")
                             + sqlInPart + getDelimiter() + value
                             + getDelimiter() + sqlEndPart);
      if (!this.propertiesUsed.contains(javaName)) {
         this.propertiesUsed.add(javaName);
      }
   }

   protected void addEqualsWithSubQueryTwice(String javaName,
                                             String sqlName,
                                             String sqlInPart,
                                             String value,
                                             String sqlMiddlePart,
                                             String sqlEndPart) {
      char _notPrecedes = appendOperator(true);
      this.bshExpression.add(_notPrecedes
                             + javaName + ".equals(\"" + value + "\")");
      this.sqlExpression.add(sqlName
                             + (' ' == _notPrecedes ? "" : "NOT ")
                             + sqlInPart + getDelimiter() + value
                             + getDelimiter() + sqlMiddlePart
                             + getDelimiter() + value + getDelimiter()
                             + sqlEndPart);
      if (!this.propertiesUsed.contains(javaName)) {
         this.propertiesUsed.add(javaName);
      }
   }

   protected void addStartsWithSubQuery(String javaName,
                                        String sqlName,
                                        String sqlInPart,
                                        String value,
                                        String sqlEndPart) {
      char _notPrecedes = appendOperator(true);
      this.bshExpression.add(_notPrecedes
                             + javaName + ".startsWith(\"" + value + "\")");
      this.sqlExpression.add(sqlName
                             + (' ' == _notPrecedes ? "" : "NOT ")
                             + sqlInPart + getDelimiter() + value + "%"
                             + getDelimiter() + sqlEndPart);
      if (!this.propertiesUsed.contains(javaName)) {
         this.propertiesUsed.add(javaName);
      }
   }

   protected void addContainsWithSubQuery(String javaName,
                                          String sqlName,
                                          String sqlInPart,
                                          String value,
                                          String sqlEndPart) {
      char _notPrecedes = appendOperator(true);
      this.bshExpression.add(_notPrecedes
                             + javaName + ".indexOf(\"" + value + "\") != -1");
      this.sqlExpression.add(sqlName
                             + (' ' == _notPrecedes ? "" : "NOT ")
                             + sqlInPart + getDelimiter() + "%" + value + "%"
                             + getDelimiter() + sqlEndPart);
      if (!this.propertiesUsed.contains(javaName)) {
         this.propertiesUsed.add(javaName);
      }
   }

   protected void addContains(String javaName, String sqlName, String value) {
      char _notPrecedes = appendOperator(true);
      this.bshExpression.add(_notPrecedes
                             + javaName + ".indexOf(\"" + value + "\") != -1");
      this.sqlExpression.add(sqlName
                             + (' ' == _notPrecedes ? "" : "NOT ") + " LIKE "
                             + getDelimiter() + "%" + value + "%"
                             + getDelimiter());
      if (!this.propertiesUsed.contains(javaName)) {
         this.propertiesUsed.add(javaName);
      }
   }

   protected String parseSQLArguments() {
      StringBuffer sql = new StringBuffer();
      for (Iterator i = this.sqlExpression.iterator(); i.hasNext();) {
         Object o = i.next();
         if (o instanceof ExpressionBuilder) {
            ExpressionBuilder eb = (ExpressionBuilder) o;
            sql.append("( " + eb.toSQL() + " )");
         } else if (o instanceof String) {
            sql.append(o);
         }
      }
      if (null != orderBy) sql.append(orderBy);
      return sql.toString();
   }

   protected String parseBshParameters() {
      StringBuffer bsh = new StringBuffer();
      for (Iterator i = this.bshExpression.iterator(); i.hasNext();) {
         Object o = i.next();
         if (o instanceof ExpressionBuilder) {
            ExpressionBuilder eb = (ExpressionBuilder) o;
            bsh.append("( " + eb.toScript() + " )");
         } else if (o instanceof String) {
            bsh.append(o);
         }
      }
      return bsh.toString();
   }

   public boolean isComplete() {
      return sqlComplete;
   }

   protected String whatsUsed() {
      StringBuffer ret = new StringBuffer();
      for (Iterator iter = this.propertiesUsed.iterator(); iter.hasNext();) {
         ret.append(iter.next()).append(" ");
      }
      return ret.toString();
   }

   public String toSQL() {
      return parseSQLArguments();
   }

   public String toScript() {
      return parseBshParameters();
   }

   public String toExpression() {
      StringBuffer ret = new StringBuffer();
      if (!this.isComplete()) {
         ret.append("/*FORCE*/\n");
      }
      if (0 < this.propertiesUsed.size()) {
         ret.append("/*used ").append(whatsUsed()).append(" used*/\n");
      }
      ret.append(toScript());
      if (0 < this.sqlExpression.size()) {
         ret.append("\n/*sql ").append(toSQL()).append(" sql*/");
      }
      return ret.toString();
   }

   public void setOrderBy(String column, boolean isAscending) {
      orderBy = " ORDER BY " + column + (isAscending ? " ASC" : " DESC");
   }
}