<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加科室</TITLE> 
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
    var departmentNo = document.getElementById("department.departmentNo").value;
    if(departmentNo=="") {
        alert('请输入科室编号!');
        return false;
    }
    var departmentName = document.getElementById("department.departmentName").value;
    if(departmentName=="") {
        alert('请输入科室名称!');
        return false;
    }
    var chargeMan = document.getElementById("department.chargeMan").value;
    if(chargeMan=="") {
        alert('请输入负责人!');
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
    <s:form action="Department/Department_AddDepartment.action" method="post" id="departmentAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>科室编号:</td>
    <td width=70%><input id="department.departmentNo" name="department.departmentNo" type="text" /></td>
  </tr>

  <tr>
    <td width=30%>科室名称:</td>
    <td width=70%><input id="department.departmentName" name="department.departmentName" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>成立日期:</td>
    <td width=70%><input type="text" readonly id="department.bornDate"  name="department.bornDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>负责人:</td>
    <td width=70%><input id="department.chargeMan" name="department.chargeMan" type="text" size="20" /></td>
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
