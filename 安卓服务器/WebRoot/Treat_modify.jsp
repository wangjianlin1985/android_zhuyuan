<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Treat" %>
<%@ page import="com.chengxusheji.domain.Patient" %>
<%@ page import="com.chengxusheji.domain.Doctor" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Patient��Ϣ
    List<Patient> patientList = (List<Patient>)request.getAttribute("patientList");
    //��ȡ���е�Doctor��Ϣ
    List<Doctor> doctorList = (List<Doctor>)request.getAttribute("doctorList");
    Treat treat = (Treat)request.getAttribute("treat");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸�����</TITLE>
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
    var treatName = document.getElementById("treat.treatName").value;
    if(treatName=="") {
        alert('��������������!');
        return false;
    }
    var diagnosis = document.getElementById("treat.diagnosis").value;
    if(diagnosis=="") {
        alert('������������!');
        return false;
    }
    var treatContent = document.getElementById("treat.treatContent").value;
    if(treatContent=="") {
        alert('���������Ƽ�¼!');
        return false;
    }
    var startTime = document.getElementById("treat.startTime").value;
    if(startTime=="") {
        alert('���������ƿ�ʼʱ��!');
        return false;
    }
    var timeLong = document.getElementById("treat.timeLong").value;
    if(timeLong=="") {
        alert('�������Ƴ�ʱ��!');
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
    <TD align="left" vAlign=top ><s:form action="Treat/Treat_ModifyTreat.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>����id:</td>
    <td width=70%><input id="treat.treatId" name="treat.treatId" type="text" value="<%=treat.getTreatId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input id="treat.treatName" name="treat.treatName" type="text" size="30" value='<%=treat.getTreatName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%>
      <select name="treat.patientObj.patiendId">
      <%
        for(Patient patient:patientList) {
          String selected = "";
          if(patient.getPatiendId() == treat.getPatientObj().getPatiendId())
            selected = "selected";
      %>
          <option value='<%=patient.getPatiendId() %>' <%=selected %>><%=patient.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%><textarea id="treat.diagnosis" name="treat.diagnosis" rows=5 cols=50><%=treat.getDiagnosis() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>���Ƽ�¼:</td>
    <td width=70%><textarea id="treat.treatContent" name="treat.treatContent" rows=5 cols=50><%=treat.getTreatContent() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>���ƽ��:</td>
    <td width=70%><textarea id="treat.treatResult" name="treat.treatResult" rows=5 cols=50><%=treat.getTreatResult() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>����ҽ��:</td>
    <td width=70%>
      <select name="treat.doctorObj.doctorNo">
      <%
        for(Doctor doctor:doctorList) {
          String selected = "";
          if(doctor.getDoctorNo().equals(treat.getDoctorObj().getDoctorNo()))
            selected = "selected";
      %>
          <option value='<%=doctor.getDoctorNo() %>' <%=selected %>><%=doctor.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>���ƿ�ʼʱ��:</td>
    <td width=70%><input id="treat.startTime" name="treat.startTime" type="text" size="20" value='<%=treat.getStartTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�Ƴ�ʱ��:</td>
    <td width=70%><input id="treat.timeLong" name="treat.timeLong" type="text" size="20" value='<%=treat.getTimeLong() %>'/></td>
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
