package org.enhydra.shark.corba.WorkflowService;


/**
* org/enhydra/shark/corba/WorkflowService/ApplicationMapPOA.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 WorkflowService.idl
* 2009年2月3日 星期二 下午05时32分31秒 CST
*/

public abstract class ApplicationMapPOA extends org.omg.PortableServer.Servant
 implements org.enhydra.shark.corba.WorkflowService.ApplicationMapOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("setApplicationDefinitionId", new java.lang.Integer (0));
    _methods.put ("getApplicationDefinitionId", new java.lang.Integer (1));
    _methods.put ("setPackageId", new java.lang.Integer (2));
    _methods.put ("getPackageId", new java.lang.Integer (3));
    _methods.put ("setProcessDefinitionId", new java.lang.Integer (4));
    _methods.put ("getProcessDefinitionId", new java.lang.Integer (5));
    _methods.put ("setToolAgentClassName", new java.lang.Integer (6));
    _methods.put ("getToolAgentClassName", new java.lang.Integer (7));
    _methods.put ("setUsername", new java.lang.Integer (8));
    _methods.put ("getUsername", new java.lang.Integer (9));
    _methods.put ("setPassword", new java.lang.Integer (10));
    _methods.put ("getPassword", new java.lang.Integer (11));
    _methods.put ("setApplicationName", new java.lang.Integer (12));
    _methods.put ("getApplicationName", new java.lang.Integer (13));
    _methods.put ("setApplicationMode", new java.lang.Integer (14));
    _methods.put ("getApplicationMode", new java.lang.Integer (15));
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
       case 0:  // WorkflowService/ApplicationMap/setApplicationDefinitionId
       {
         String id = in.read_wstring ();
         this.setApplicationDefinitionId (id);
         out = $rh.createReply();
         break;
       }

       case 1:  // WorkflowService/ApplicationMap/getApplicationDefinitionId
       {
         String $result = null;
         $result = this.getApplicationDefinitionId ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 2:  // WorkflowService/ApplicationMap/setPackageId
       {
         String id = in.read_wstring ();
         this.setPackageId (id);
         out = $rh.createReply();
         break;
       }

       case 3:  // WorkflowService/ApplicationMap/getPackageId
       {
         String $result = null;
         $result = this.getPackageId ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 4:  // WorkflowService/ApplicationMap/setProcessDefinitionId
       {
         String id = in.read_wstring ();
         this.setProcessDefinitionId (id);
         out = $rh.createReply();
         break;
       }

       case 5:  // WorkflowService/ApplicationMap/getProcessDefinitionId
       {
         String $result = null;
         $result = this.getProcessDefinitionId ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 6:  // WorkflowService/ApplicationMap/setToolAgentClassName
       {
         String name = in.read_wstring ();
         this.setToolAgentClassName (name);
         out = $rh.createReply();
         break;
       }

       case 7:  // WorkflowService/ApplicationMap/getToolAgentClassName
       {
         String $result = null;
         $result = this.getToolAgentClassName ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 8:  // WorkflowService/ApplicationMap/setUsername
       {
         String username = in.read_wstring ();
         this.setUsername (username);
         out = $rh.createReply();
         break;
       }

       case 9:  // WorkflowService/ApplicationMap/getUsername
       {
         String $result = null;
         $result = this.getUsername ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 10:  // WorkflowService/ApplicationMap/setPassword
       {
         String password = in.read_wstring ();
         this.setPassword (password);
         out = $rh.createReply();
         break;
       }

       case 11:  // WorkflowService/ApplicationMap/getPassword
       {
         String $result = null;
         $result = this.getPassword ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 12:  // WorkflowService/ApplicationMap/setApplicationName
       {
         String name = in.read_wstring ();
         this.setApplicationName (name);
         out = $rh.createReply();
         break;
       }

       case 13:  // WorkflowService/ApplicationMap/getApplicationName
       {
         String $result = null;
         $result = this.getApplicationName ();
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 14:  // WorkflowService/ApplicationMap/setApplicationMode
       {
         int mode = in.read_long ();
         this.setApplicationMode (mode);
         out = $rh.createReply();
         break;
       }

       case 15:  // WorkflowService/ApplicationMap/getApplicationMode
       {
         int $result = (int)0;
         $result = this.getApplicationMode ();
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:WorkflowService/ApplicationMap:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public ApplicationMap _this() 
  {
    return ApplicationMapHelper.narrow(
    super._this_object());
  }

  public ApplicationMap _this(org.omg.CORBA.ORB orb) 
  {
    return ApplicationMapHelper.narrow(
    super._this_object(orb));
  }


} // class ApplicationMapPOA
