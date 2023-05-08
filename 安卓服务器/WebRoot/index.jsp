<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>119基于Android的病人住院信息管理系统的设计与实现-首页</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">首页</a></li>
			<li><a href="<%=basePath %>Department/Department_FrontQueryDepartment.action" target="OfficeMain">科室</a></li> 
			<li><a href="<%=basePath %>Doctor/Doctor_FrontQueryDoctor.action" target="OfficeMain">医生</a></li> 
			<li><a href="<%=basePath %>Patient/Patient_FrontQueryPatient.action" target="OfficeMain">病人</a></li> 
			<li><a href="<%=basePath %>ZhuYuan/ZhuYuan_FrontQueryZhuYuan.action" target="OfficeMain">住院</a></li> 
			<li><a href="<%=basePath %>Drug/Drug_FrontQueryDrug.action" target="OfficeMain">药品</a></li> 
			<li><a href="<%=basePath %>Treat/Treat_FrontQueryTreat.action" target="OfficeMain">治疗</a></li> 
			<li><a href="<%=basePath %>DrugUse/DrugUse_FrontQueryDrugUse.action" target="OfficeMain">用药</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>双鱼林设计 QQ:287307421或254540457 &copy;版权所有 <a href="http://www.shuangyulin.com" target="_blank">双鱼林设计网</a>&nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>后台登陆</font></a></p>
	</div>
</div>
</body>
</html>
