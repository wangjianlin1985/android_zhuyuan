<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Drug" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Drug drug = (Drug)request.getAttribute("drug");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改药品</TITLE>
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
    var drugName = document.getElementById("drug.drugName").value;
    if(drugName=="") {
        alert('请输入药品名称!');
        return false;
    }
    var unit = document.getElementById("drug.unit").value;
    if(unit=="") {
        alert('请输入药品单位!');
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
    <TD align="left" vAlign=top ><s:form action="Drug/Drug_ModifyDrug.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>药品id:</td>
    <td width=70%><input id="drug.drugId" name="drug.drugId" type="text" value="<%=drug.getDrugId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>药品名称:</td>
    <td width=70%><input id="drug.drugName" name="drug.drugName" type="text" size="20" value='<%=drug.getDrugName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>药品单位:</td>
    <td width=70%><input id="drug.unit" name="drug.unit" type="text" size="20" value='<%=drug.getUnit() %>'/></td>
  </tr>

  <tr>
    <td width=30%>药品单价:</td>
    <td width=70%><input id="drug.price" name="drug.price" type="text" size="8" value='<%=drug.getPrice() %>'/></td>
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
