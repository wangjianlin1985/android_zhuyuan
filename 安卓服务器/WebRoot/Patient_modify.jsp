<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Patient" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Patient patient = (Patient)request.getAttribute("patient");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸Ĳ���</TITLE>
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
    var name = document.getElementById("patient.name").value;
    if(name=="") {
        alert('����������!');
        return false;
    }
    var sex = document.getElementById("patient.sex").value;
    if(sex=="") {
        alert('�������Ա�!');
        return false;
    }
    var cardNo = document.getElementById("patient.cardNo").value;
    if(cardNo=="") {
        alert('���������֤��!');
        return false;
    }
    var originPlace = document.getElementById("patient.originPlace").value;
    if(originPlace=="") {
        alert('�����뼮��!');
        return false;
    }
    var telephone = document.getElementById("patient.telephone").value;
    if(telephone=="") {
        alert('��������ϵ�绰!');
        return false;
    }
    var address = document.getElementById("patient.address").value;
    if(address=="") {
        alert('��������ϵ��ַ!');
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
    <TD align="left" vAlign=top ><s:form action="Patient/Patient_ModifyPatient.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>����id:</td>
    <td width=70%><input id="patient.patiendId" name="patient.patiendId" type="text" value="<%=patient.getPatiendId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="patient.name" name="patient.name" type="text" size="20" value='<%=patient.getName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�Ա�:</td>
    <td width=70%><input id="patient.sex" name="patient.sex" type="text" size="4" value='<%=patient.getSex() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <% DateFormat birthdaySDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="patient.birthday"  name="patient.birthday" onclick="setDay(this);" value='<%=birthdaySDF.format(patient.getBirthday()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>���֤��:</td>
    <td width=70%><input id="patient.cardNo" name="patient.cardNo" type="text" size="20" value='<%=patient.getCardNo() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="patient.originPlace" name="patient.originPlace" type="text" size="20" value='<%=patient.getOriginPlace() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="patient.telephone" name="patient.telephone" type="text" size="20" value='<%=patient.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ϵ��ַ:</td>
    <td width=70%><input id="patient.address" name="patient.address" type="text" size="50" value='<%=patient.getAddress() %>'/></td>
  </tr>

  <tr>
    <td width=30%>������ʷ:</td>
    <td width=70%><textarea id="patient.caseHistory" name="patient.caseHistory" rows=5 cols=50><%=patient.getCaseHistory() %></textarea></td>
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
