<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Department" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Department信息
    List<Department> departmentList = (List<Department>)request.getAttribute("departmentList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加医生</TITLE> 
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
    var doctorNo = document.getElementById("doctor.doctorNo").value;
    if(doctorNo=="") {
        alert('请输入医生工号!');
        return false;
    }
    var password = document.getElementById("doctor.password").value;
    if(password=="") {
        alert('请输入登录密码!');
        return false;
    }
    var name = document.getElementById("doctor.name").value;
    if(name=="") {
        alert('请输入姓名!');
        return false;
    }
    var sex = document.getElementById("doctor.sex").value;
    if(sex=="") {
        alert('请输入性别!');
        return false;
    }
    var cardNo = document.getElementById("doctor.cardNo").value;
    if(cardNo=="") {
        alert('请输入身份证号!');
        return false;
    }
    var telephone = document.getElementById("doctor.telephone").value;
    if(telephone=="") {
        alert('请输入联系电话!');
        return false;
    }
    var school = document.getElementById("doctor.school").value;
    if(school=="") {
        alert('请输入毕业学校!');
        return false;
    }
    var workYears = document.getElementById("doctor.workYears").value;
    if(workYears=="") {
        alert('请输入工作经验!');
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
    <s:form action="Doctor/Doctor_AddDoctor.action" method="post" id="doctorAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>医生工号:</td>
    <td width=70%><input id="doctor.doctorNo" name="doctor.doctorNo" type="text" /></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><input id="doctor.password" name="doctor.password" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>所在科室:</td>
    <td width=70%>
      <select name="doctor.departmentObj.departmentNo">
      <%
        for(Department department:departmentList) {
      %>
          <option value='<%=department.getDepartmentNo() %>'><%=department.getDepartmentName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>姓名:</td>
    <td width=70%><input id="doctor.name" name="doctor.name" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>性别:</td>
    <td width=70%><input id="doctor.sex" name="doctor.sex" type="text" size="4" /></td>
  </tr>

  <tr>
    <td width=30%>身份证号:</td>
    <td width=70%><input id="doctor.cardNo" name="doctor.cardNo" type="text" size="30" /></td>
  </tr>

  <tr>
    <td width=30%>医生照片:</td>
    <td width=70%><input id="doctorPhotoFile" name="doctorPhotoFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>出生日期:</td>
    <td width=70%><input type="text" readonly id="doctor.birthday"  name="doctor.birthday" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><input id="doctor.telephone" name="doctor.telephone" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>毕业学校:</td>
    <td width=70%><input id="doctor.school" name="doctor.school" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>工作经验:</td>
    <td width=70%><input id="doctor.workYears" name="doctor.workYears" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>备注:</td>
    <td width=70%><textarea id="doctor.memo" name="doctor.memo" rows="5" cols="50"></textarea></td>
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
