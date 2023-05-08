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
    //获取所有的Patient信息
    List<Patient> patientList = (List<Patient>)request.getAttribute("patientList");
    //获取所有的Doctor信息
    List<Doctor> doctorList = (List<Doctor>)request.getAttribute("doctorList");
    Treat treat = (Treat)request.getAttribute("treat");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改治疗</TITLE>
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
    var treatName = document.getElementById("treat.treatName").value;
    if(treatName=="") {
        alert('请输入治疗名称!');
        return false;
    }
    var diagnosis = document.getElementById("treat.diagnosis").value;
    if(diagnosis=="") {
        alert('请输入诊断情况!');
        return false;
    }
    var treatContent = document.getElementById("treat.treatContent").value;
    if(treatContent=="") {
        alert('请输入治疗记录!');
        return false;
    }
    var startTime = document.getElementById("treat.startTime").value;
    if(startTime=="") {
        alert('请输入治疗开始时间!');
        return false;
    }
    var timeLong = document.getElementById("treat.timeLong").value;
    if(timeLong=="") {
        alert('请输入疗程时间!');
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
    <td width=30%>治疗id:</td>
    <td width=70%><input id="treat.treatId" name="treat.treatId" type="text" value="<%=treat.getTreatId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>治疗名称:</td>
    <td width=70%><input id="treat.treatName" name="treat.treatName" type="text" size="30" value='<%=treat.getTreatName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>病人:</td>
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
    <td width=30%>诊断情况:</td>
    <td width=70%><textarea id="treat.diagnosis" name="treat.diagnosis" rows=5 cols=50><%=treat.getDiagnosis() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>治疗记录:</td>
    <td width=70%><textarea id="treat.treatContent" name="treat.treatContent" rows=5 cols=50><%=treat.getTreatContent() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>治疗结果:</td>
    <td width=70%><textarea id="treat.treatResult" name="treat.treatResult" rows=5 cols=50><%=treat.getTreatResult() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>主治医生:</td>
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
    <td width=30%>治疗开始时间:</td>
    <td width=70%><input id="treat.startTime" name="treat.startTime" type="text" size="20" value='<%=treat.getStartTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>疗程时间:</td>
    <td width=70%><input id="treat.timeLong" name="treat.timeLong" type="text" size="20" value='<%=treat.getTimeLong() %>'/></td>
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
