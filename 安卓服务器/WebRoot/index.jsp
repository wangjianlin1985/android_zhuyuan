<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>119����Android�Ĳ���סԺ��Ϣ����ϵͳ�������ʵ��-��ҳ</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">��ҳ</a></li>
			<li><a href="<%=basePath %>Department/Department_FrontQueryDepartment.action" target="OfficeMain">����</a></li> 
			<li><a href="<%=basePath %>Doctor/Doctor_FrontQueryDoctor.action" target="OfficeMain">ҽ��</a></li> 
			<li><a href="<%=basePath %>Patient/Patient_FrontQueryPatient.action" target="OfficeMain">����</a></li> 
			<li><a href="<%=basePath %>ZhuYuan/ZhuYuan_FrontQueryZhuYuan.action" target="OfficeMain">סԺ</a></li> 
			<li><a href="<%=basePath %>Drug/Drug_FrontQueryDrug.action" target="OfficeMain">ҩƷ</a></li> 
			<li><a href="<%=basePath %>Treat/Treat_FrontQueryTreat.action" target="OfficeMain">����</a></li> 
			<li><a href="<%=basePath %>DrugUse/DrugUse_FrontQueryDrugUse.action" target="OfficeMain">��ҩ</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>˫������� QQ:287307421��254540457 &copy;��Ȩ���� <a href="http://www.shuangyulin.com" target="_blank">˫���������</a>&nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>��̨��½</font></a></p>
	</div>
</div>
</body>
</html>
