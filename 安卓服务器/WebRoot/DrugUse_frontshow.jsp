<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.DrugUse" %>
<%@ page import="com.chengxusheji.domain.Treat" %>
<%@ page import="com.chengxusheji.domain.Drug" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Treat��Ϣ
    List<Treat> treatList = (List<Treat>)request.getAttribute("treatList");
    //��ȡ���е�Drug��Ϣ
    List<Drug> drugList = (List<Drug>)request.getAttribute("drugList");
    DrugUse drugUse = (DrugUse)request.getAttribute("drugUse");

%>
<HTML><HEAD><TITLE>�鿴��ҩ</TITLE>
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
    <td width=30%>��ҩid:</td>
    <td width=70%><%=drugUse.getDrugUseId() %></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%>
      <%=drugUse.getTreatObj().getTreatName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>����ҩƷ:</td>
    <td width=70%>
      <%=drugUse.getDrugObj().getDrugName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>��ҩ����:</td>
    <td width=70%><%=drugUse.getDrugCount() %></td>
  </tr>

  <tr>
    <td width=30%>ҩƷ����:</td>
    <td width=70%><%=drugUse.getDrugMoney() %></td>
  </tr>

  <tr>
    <td width=30%>��ҩʱ��:</td>
    <td width=70%><%=drugUse.getUseTime() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
