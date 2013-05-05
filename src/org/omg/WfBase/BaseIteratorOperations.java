package org.omg.WfBase;


/**
* org/omg/WfBase/BaseIteratorOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WfBase.idl
* 2009年2月3日 星期二 下午05时32分27秒 CST
*/

public interface BaseIteratorOperations 
{
  String query_expression () throws org.omg.WfBase.BaseException;
  void set_query_expression (String query) throws org.omg.WfBase.BaseException, org.omg.WfBase.InvalidQuery;
  org.omg.WfBase.NameValue[] names_in_expression () throws org.omg.WfBase.BaseException;
  void set_names_in_expression (org.omg.WfBase.NameValue[] query) throws org.omg.WfBase.BaseException, org.omg.WfBase.NameMismatch;
  String query_grammar () throws org.omg.WfBase.BaseException;
  void set_query_grammar (String query_grammmar) throws org.omg.WfBase.BaseException, org.omg.WfBase.GrammarNotSupported;
  int how_many () throws org.omg.WfBase.BaseException;
  void goto_start () throws org.omg.WfBase.BaseException;
  void goto_end () throws org.omg.WfBase.BaseException;
} // interface BaseIteratorOperations
