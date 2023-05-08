<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.ZhuYuan" %>
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
    ZhuYuan zhuYuan = (ZhuYuan)request.getAttribute("zhuYuan");

%>
<HTML><HEAD><TITLE>查看住院</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>住院id:</td>
    <td width=70%><%=zhuYuan.getZhuyuanId() %></td>
  </tr>

  <tr>
    <td width=30%>病人:</td>
    <td width=70%>
      <%=zhuYuan.getPatientObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>年龄:</td>
    <td width=70%><%=zhuYuan.getAge() %></td>
  </tr>

  <tr>
    <td width=30%>住院日期:</td>
        <% java.text.DateFormat inDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=inDateSDF.format(zhuYuan.getInDate()) %></td>
  </tr>

  <tr>
    <td width=30%>入住天数:</td>
    <td width=70%><%=zhuYuan.getInDays() %></td>
  </tr>

  <tr>
    <td width=30%>床位号:</td>
    <td width=70%><%=zhuYuan.getBedNum() %></td>
  </tr>

  <tr>
    <td width=30%>负责医生:</td>
    <td width=70%>
      <%=zhuYuan.getDoctorObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>备注信息:</td>
    <td width=70%><%=zhuYuan.getMemo() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
