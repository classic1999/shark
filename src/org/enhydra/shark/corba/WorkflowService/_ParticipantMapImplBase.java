package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/_ParticipantMapImplBase.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分33秒 CST
*/

public abstract class _ParticipantMapImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements org.enhydra.shark.corba.WorkflowService.ParticipantMap, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _ParticipantMapImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("setParticipantId", new java.lang.Integer (0));
    _methods.put ("getParticipantId", new java.lang.Integer (1));
    _methods.put ("setPackageId", new java.lang.Integer (2));
    _methods.put ("getPackageId", new java.lang.Integer (3));
    _methods.put ("setProcessDefinitionId", new java.lang.Integer (4));
    _methods.put ("getProcessDefinitionId", new java.lang.Integer (5));
    _methods.put ("setUsername", new java.lang.Integer (6));
    _methods.put ("getUsername", new java.lang.Integer (7));
    _methods.put ("getIsGroupUser", new java.lang.Integer (8));
    _methods.put ("setIsGroupUser", new java.lang.Integer (9));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // WorkflowService/ParticipantMap/setParticipantId
       {
         String id = in.read_wstring ();
         this.setParticipantId (id);
         out = $rh.createReply();
         break;
       }

       case 1:  // WorkflowService/ParticipantMap/getParticipantId
       {
         String $result = null;
         $result = this.getParticipantId ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 2:  // WorkflowService/ParticipantMap/setPackageId
       {
         String id = in.read_wstring ();
         this.setPackageId (id);
         out = $rh.createReply();
         break;
       }

       case 3:  // WorkflowService/ParticipantMap/getPackageId
       {
         String $result = null;
         $result = this.getPackageId ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 4:  // WorkflowService/ParticipantMap/setProcessDefinitionId
       {
         String id = in.read_wstring ();
         this.setProcessDefinitionId (id);
         out = $rh.createReply();
         break;
       }

       case 5:  // WorkflowService/ParticipantMap/getProcessDefinitionId
       {
         String $result = null;
         $result = this.getProcessDefinitionId ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 6:  // WorkflowService/ParticipantMap/setUsername
       {
         String username = in.read_wstring ();
         this.setUsername (username);
         out = $rh.createReply();
         break;
       }

       case 7:  // WorkflowService/ParticipantMap/getUsername
       {
         String $result = null;
         $result = this.getUsername ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 8:  // WorkflowService/ParticipantMap/getIsGroupUser
       {
         boolean $result = false;
         $result = this.getIsGroupUser ();
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 9:  // WorkflowService/ParticipantMap/setIsGroupUser
       {
         boolean isGroupUser = in.read_boolean ();
         this.setIsGroupUser (isGroupUser);
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:WorkflowService/ParticipantMap:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _ParticipantMapImplBase
