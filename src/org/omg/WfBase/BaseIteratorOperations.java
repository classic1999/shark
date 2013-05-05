package org.omg.WfBase;


/**
* org/omg/WfBase/BaseIteratorOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� WfBase.idl
* 2009��2��3�� ���ڶ� ����05ʱ32��27�� CST
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
