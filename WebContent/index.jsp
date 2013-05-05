<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.enhydra.shark.api.client.wfmodel.WfAssignment" %>
<%@ page import="org.enhydra.shark.api.client.wfservice.SharkConnection" %>
<%@ page import="test.JSPClientUtilities" %>
<html>
<head>
   <title>test shark</title>
   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
   <style>
   <!--
      .CyanBG { font-family:sans-serif; font-size:large; color:black; background-color:cyan;}
      .RedBG { font-family:sans-serif; font-size:large; color:black; background-color:red;}
      .BlueBG { font-family:sans-serif; font-size:large; color:black; background-color:#7777FF;}
      .PurpleBG { font-family:sans-serif; font-size:large; color:black; background-color:#AA44AA;}
      .YellowBG { font-family:sans-serif; font-size:large; color:black; background-color:yellow;}
      .GrayBG { font-family:sans-serif; font-size:large; color:black; background-color:gray;}
      .GreenBG { font-family:sans-serif; font-size:large; color:black; background-color:lightgreen;}
      a { color:black; text-decoration:none;}
      body { background-color:lightgrey;}
      tr { text-align:center;}
   -->
   </style>
</head>
<%!
   static final String FORM_METHOD = "post";
   static final String VARIABLESET = "variable_set";
   static final String COMPLETE = "complete";
   static final String ACCEPT = "accept";
   static final String OPEN_N_START = "openNstart";
   static final String LOAD_PACKAGE = "loadPackage";
%>
<body>
<div align="center">
  <h2>Shark JSP worklist handler</h2>
  <h3>demo</h3>
  <hr>

  <table class="PurpleBG" width="65%" border="0" cellpadding="0" cellspacing="0">
    <tr class="GrayBG">
      <td><div align="center"><em><strong>Username</strong></em></div></td>
      <td><div align="center"><em><strong>Real name</strong></em></div></td>
      <td><div align="center"><em><strong>email</strong></em></div></td>
    </tr>
<%
   JSPClientUtilities.init(getServletContext().getRealPath("/"));
   try{
      String[] uns = JSPClientUtilities.getAllUsers();
      for (int i=0; i<uns.length; i++) {

%>
    <tr>
      <td><div align="center"><%=uns[i]%></div></td>
      <td><div align="center"><%=JSPClientUtilities.getUserRealName(uns[i])%></div></td>
      <td><div align="center"><%=JSPClientUtilities.getUserEMailAddress(uns[i])%></div></td>
    </tr>
    <%
      }

   } catch(Exception e) {
      e.printStackTrace();
   }
%>
  </table>
</div><%
   SharkConnection sConn = JSPClientUtilities.connect();

   String activityId = request.getParameter("activity");
   String fn = request.getParameter("fn");
   if (null != fn) {
      if (fn.equals(COMPLETE))
         JSPClientUtilities.activityComplete(sConn, activityId);
      else if (fn.equals(ACCEPT))
         JSPClientUtilities.assignmentAccept(sConn, activityId);
      else if (fn.equals(VARIABLESET)) {
         String vName = request.getParameter("varName");
         String vValue = request.getParameter("varValue");
         JSPClientUtilities.variableSet(sConn, activityId, vName, vValue);
      } else if (fn.equals(OPEN_N_START)) {
         String vValue = request.getParameter("varValue");
         JSPClientUtilities.processStart(vValue);
      } else if (fn.equals(LOAD_PACKAGE)) {
         String vValue = request.getParameter("varValue");
         JSPClientUtilities.packageLoad(vValue);
      }
   }
   // not accepted
   WfAssignment[] wItems =sConn.getResourceObject().get_sequence_work_item(0);
   if (null != wItems) {%>
<div align="center">
   <table width="65%" border="0" cellpadding="0" cellspacing="0">
      <tr class="GrayBG"><td colspan="2" align="center">Task list</td></tr><%
      for (int i = 0; i < wItems.length; ++i) {
         if (JSPClientUtilities.isMine(sConn, wItems[i])) {
            continue;
         }
         %> <tr class="GreenBG">
         <td align="center" valign="top">
            <form name="activity_<%=wItems[i].activity().key()%>"
                  method="<%=FORM_METHOD%>"
                  action="">
               <p>Activity</p>
               <h3><%=wItems[i].activity().name()%></h3>
               <tt><%=wItems[i].activity().key()%></tt><p>
               <input type="hidden" name="activity" value="<%=wItems[i].activity().key()%>">
               <input type="submit" name="fn" value="<%=ACCEPT%>">
            </form>
            <p></p>
         </td>
       </tr><%
      }
   }
   // accepted
   wItems =sConn.getResourceObject().get_sequence_work_item(0);
   if (null != wItems) {
      for (int i = 0; i < wItems.length; ++i) {
         if (!JSPClientUtilities.isMine(sConn, wItems[i])) {
            continue;
         }
         %> <tr class="CyanBG">
         <td align="center" valign="top">
            <form name="activity_<%=wItems[i].activity().key()%>"
                  method="<%=FORM_METHOD%>"
                  action="">
               <p>Activity</p>
               <h3><%=wItems[i].activity().name()%></h3>
               <tt><%=wItems[i].activity().key()%></tt><p>
               <!--input type="hidden" name="fn" value="complete"-->
               <input type="hidden" name="activity" value="<%=wItems[i].activity().key()%>">
               <input type="submit" name="fn" value="<%=COMPLETE%>">
            </form>
         </td>
         <td><%
         for (Iterator it =wItems[i].activity().process_context().keySet().iterator();
                 it.hasNext();) {
            String key = (String)it.next();
            %><p>
               <b><%=key%>:</b>
               <form name="variable_<%=key%>" method="<%=FORM_METHOD%>" action="">
                  <input type="text"
                         name="varValue"
                         value="<%=wItems[i].activity().process_context().get(key)%>"
                         onblur="submit();"
                  >
                  <input type="hidden" name="activity" value="<%=wItems[i].activity().key()%>">
                  <input type="hidden" name="fn" value="<%=VARIABLESET%>">
                  <input type="hidden" name="varName" value="<%=key%>">
               </form>
            </p><%
         }
         %></td>
       </tr><%
      }
      %>
   </table>
</div><%
   }
   JSPClientUtilities.disconnect(sConn);

%><hr>
   <div align="center">
 <table>
   <tr><td valign="top">
      <h4>Load package</h4>
      <form name="loadPackage" method="<%=FORM_METHOD%>" action="">
      <select name="varValue" size="6" onchange="submit();"><%
   String [] a = JSPClientUtilities.xpdlsToLoad();
   for (int i = 0; i < a.length; ++i) {
      %><option value="<%=a[i]%>"><%=a[i]%></option><%
   }
   %>
      </select>
         <input type="hidden" name="fn" value="<%=LOAD_PACKAGE%>">
      </form>
   </td>
   <td valign="top">
      <h4>Start process</h4>
      <form name="processStart" method="<%=FORM_METHOD%>" action="">
      <select name="varValue" size="8" onchange="submit();"><%
   String [] p = JSPClientUtilities.processesToStart();
   for (int i = 0; i < p.length; ++i) {
      %><option value="<%=p[i]%>"><%=p[i]%></option><%
   }
   %>
      </select>
         <input type="hidden" name="fn" value="<%=OPEN_N_START%>">
      </form>
   </td>
   </tr>
   </table>
   </div>
   <div align="center">
      <form name="rForm" method="<%=FORM_METHOD%>" action="">
         <input type="submit" name="fn" value="refresh">
      </form>
   </div>
   <p align="left">&nbsp;</p>
</body>
</html>
