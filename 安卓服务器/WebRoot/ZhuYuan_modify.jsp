<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.ZhuYuan" %>
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
    ZhuYuan zhuYuan = (ZhuYuan)request.getAttribute("zhuYuan");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸�סԺ</TITLE>
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
    var bedNum = document.getElementById("zhuYuan.bedNum").value;
    if(bedNum=="") {
        alert('�����봲λ��!');
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
    <TD align="left" vAlign=top ><s:form action="ZhuYuan/ZhuYuan_ModifyZhuYuan.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>סԺid:</td>
    <td width=70%><input id="zhuYuan.zhuyuanId" name="zhuYuan.zhuyuanId" type="text" value="<%=zhuYuan.getZhuyuanId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%>
      <select name="zhuYuan.patientObj.patiendId">
      <%
        for(Patient patient:patientList) {
          String selected = "";
          if(patient.getPatiendId() == zhuYuan.getPatientObj().getPatiendId())
            selected = "selected";
      %>
          <option value='<%=patient.getPatiendId() %>' <%=selected %>><%=patient.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="zhuYuan.age" name="zhuYuan.age" type="text" size="8" value='<%=zhuYuan.getAge() %>'/></td>
  </tr>

  <tr>
    <td width=30%>סԺ����:</td>
    <% DateFormat inDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="zhuYuan.inDate"  name="zhuYuan.inDate" onclick="setDay(this);" value='<%=inDateSDF.format(zhuYuan.getInDate()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ס����:</td>
    <td width=70%><input id="zhuYuan.inDays" name="zhuYuan.inDays" type="text" size="8" value='<%=zhuYuan.getInDays() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��λ��:</td>
    <td width=70%><input id="zhuYuan.bedNum" name="zhuYuan.bedNum" type="text" size="20" value='<%=zhuYuan.getBedNum() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����ҽ��:</td>
    <td width=70%>
      <select name="zhuYuan.doctorObj.doctorNo">
      <%
        for(Doctor doctor:doctorList) {
          String selected = "";
          if(doctor.getDoctorNo().equals(zhuYuan.getDoctorObj().getDoctorNo()))
            selected = "selected";
      %>
          <option value='<%=doctor.getDoctorNo() %>' <%=selected %>><%=doctor.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��ע��Ϣ:</td>
    <td width=70%><textarea id="zhuYuan.memo" name="zhuYuan.memo" rows=5 cols=50><%=zhuYuan.getMemo() %></textarea></td>
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
