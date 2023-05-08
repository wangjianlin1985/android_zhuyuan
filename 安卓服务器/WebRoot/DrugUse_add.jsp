<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
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
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�����ҩ</TITLE> 
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
    <s:form action="DrugUse/DrugUse_AddDrugUse.action" method="post" id="drugUseAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>��������:</td>
    <td width=70%>
      <select name="drugUse.treatObj.treatId">
      <%
        for(Treat treat:treatList) {
      %>
          <option value='<%=treat.getTreatId() %>'><%=treat.getTreatName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>����ҩƷ:</td>
    <td width=70%>
      <select name="drugUse.drugObj.drugId">
      <%
        for(Drug drug:drugList) {
      %>
          <option value='<%=drug.getDrugId() %>'><%=drug.getDrugName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��ҩ����:</td>
    <td width=70%><input id="drugUse.drugCount" name="drugUse.drugCount" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>ҩƷ����:</td>
    <td width=70%><input id="drugUse.drugMoney" name="drugUse.drugMoney" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>��ҩʱ��:</td>
    <td width=70%><input id="drugUse.useTime" name="drugUse.useTime" type="text" size="20" /></td>
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
