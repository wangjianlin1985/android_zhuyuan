<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Patient" %>
<%@ page import="com.chengxusheji.domain.Doctor" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Patient信息
    List<Patient> patientList = (List<Patient>)request.getAttribute("patientList");
    //获取所有的Doctor信息
    List<Doctor> doctorList = (List<Doctor>)request.getAttribute("doctorList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加住院</TITLE> 
<STYLE type=text/css>
BODY {
    	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var bedNum = document.getElementById("zhuYuan.bedNum").value;
    if(bedNum=="") {
        alert('请输入床位号!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>

<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top >
    <s:form action="ZhuYuan/ZhuYuan_AddZhuYuan.action" method="post" id="zhuYuanAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>病人:</td>
    <td width=70%>
      <select name="zhuYuan.patientObj.patiendId">
      <%
        for(Patient patient:patientList) {
      %>
          <option value='<%=patient.getPatiendId() %>'><%=patient.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>年龄:</td>
    <td width=70%><input id="zhuYuan.age" name="zhuYuan.age" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>住院日期:</td>
    <td width=70%><input type="text" readonly id="zhuYuan.inDate"  name="zhuYuan.inDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>入住天数:</td>
    <td width=70%><input id="zhuYuan.inDays" name="zhuYuan.inDays" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>床位号:</td>
    <td width=70%><input id="zhuYuan.bedNum" name="zhuYuan.bedNum" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>负责医生:</td>
    <td width=70%>
      <select name="zhuYuan.doctorObj.doctorNo">
      <%
        for(Doctor doctor:doctorList) {
      %>
          <option value='<%=doctor.getDoctorNo() %>'><%=doctor.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>备注信息:</td>
    <td width=70%><textarea id="zhuYuan.memo" name="zhuYuan.memo" rows="5" cols="50"></textarea></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
