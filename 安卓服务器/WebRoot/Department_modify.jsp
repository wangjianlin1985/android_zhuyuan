<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Department" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Department department = (Department)request.getAttribute("department");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸Ŀ���</TITLE>
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
/*��֤��*/
function checkForm() {
    var departmentNo = document.getElementById("department.departmentNo").value;
    if(departmentNo=="") {
        alert('��������ұ��!');
        return false;
    }
    var departmentName = document.getElementById("department.departmentName").value;
    if(departmentName=="") {
        alert('�������������!');
        return false;
    }
    var chargeMan = document.getElementById("department.chargeMan").value;
    if(chargeMan=="") {
        alert('�����븺����!');
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
    <TD align="left" vAlign=top ><s:form action="Department/Department_ModifyDepartment.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>���ұ��:</td>
    <td width=70%><input id="department.departmentNo" name="department.departmentNo" type="text" value="<%=department.getDepartmentNo() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input id="department.departmentName" name="department.departmentName" type="text" size="20" value='<%=department.getDepartmentName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <% DateFormat bornDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="department.bornDate"  name="department.bornDate" onclick="setDay(this);" value='<%=bornDateSDF.format(department.getBornDate()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%><input id="department.chargeMan" name="department.chargeMan" type="text" size="20" value='<%=department.getChargeMan() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
