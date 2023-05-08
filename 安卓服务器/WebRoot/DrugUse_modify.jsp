<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.DrugUse" %>
<%@ page import="com.chengxusheji.domain.Treat" %>
<%@ page import="com.chengxusheji.domain.Drug" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Treat信息
    List<Treat> treatList = (List<Treat>)request.getAttribute("treatList");
    //获取所有的Drug信息
    List<Drug> drugList = (List<Drug>)request.getAttribute("drugList");
    DrugUse drugUse = (DrugUse)request.getAttribute("drugUse");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改用药</TITLE>
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
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="DrugUse/DrugUse_ModifyDrugUse.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>用药id:</td>
    <td width=70%><input id="drugUse.drugUseId" name="drugUse.drugUseId" type="text" value="<%=drugUse.getDrugUseId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>治疗名称:</td>
    <td width=70%>
      <select name="drugUse.treatObj.treatId">
      <%
        for(Treat treat:treatList) {
          String selected = "";
          if(treat.getTreatId() == drugUse.getTreatObj().getTreatId())
            selected = "selected";
      %>
          <option value='<%=treat.getTreatId() %>' <%=selected %>><%=treat.getTreatName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>所用药品:</td>
    <td width=70%>
      <select name="drugUse.drugObj.drugId">
      <%
        for(Drug drug:drugList) {
          String selected = "";
          if(drug.getDrugId() == drugUse.getDrugObj().getDrugId())
            selected = "selected";
      %>
          <option value='<%=drug.getDrugId() %>' <%=selected %>><%=drug.getDrugName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>用药数量:</td>
    <td width=70%><input id="drugUse.drugCount" name="drugUse.drugCount" type="text" size="8" value='<%=drugUse.getDrugCount() %>'/></td>
  </tr>

  <tr>
    <td width=30%>药品费用:</td>
    <td width=70%><input id="drugUse.drugMoney" name="drugUse.drugMoney" type="text" size="8" value='<%=drugUse.getDrugMoney() %>'/></td>
  </tr>

  <tr>
    <td width=30%>用药时间:</td>
    <td width=70%><input id="drugUse.useTime" name="drugUse.useTime" type="text" size="20" value='<%=drugUse.getUseTime() %>'/></td>
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
