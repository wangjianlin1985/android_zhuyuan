package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.DrugUseDAO;
import com.mobileserver.domain.DrugUse;

import org.json.JSONStringer;

public class DrugUseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造用药业务层对象*/
	private DrugUseDAO drugUseDAO = new DrugUseDAO();

	/*默认构造函数*/
	public DrugUseServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询用药的参数信息*/
			int treatObj = 0;
			if (request.getParameter("treatObj") != null)
				treatObj = Integer.parseInt(request.getParameter("treatObj"));
			int drugObj = 0;
			if (request.getParameter("drugObj") != null)
				drugObj = Integer.parseInt(request.getParameter("drugObj"));
			String useTime = request.getParameter("useTime");
			useTime = useTime == null ? "" : new String(request.getParameter(
					"useTime").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行用药查询*/
			List<DrugUse> drugUseList = drugUseDAO.QueryDrugUse(treatObj,drugObj,useTime);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<DrugUses>").append("\r\n");
			for (int i = 0; i < drugUseList.size(); i++) {
				sb.append("	<DrugUse>").append("\r\n")
				.append("		<drugUseId>")
				.append(drugUseList.get(i).getDrugUseId())
				.append("</drugUseId>").append("\r\n")
				.append("		<treatObj>")
				.append(drugUseList.get(i).getTreatObj())
				.append("</treatObj>").append("\r\n")
				.append("		<drugObj>")
				.append(drugUseList.get(i).getDrugObj())
				.append("</drugObj>").append("\r\n")
				.append("		<drugCount>")
				.append(drugUseList.get(i).getDrugCount())
				.append("</drugCount>").append("\r\n")
				.append("		<drugMoney>")
				.append(drugUseList.get(i).getDrugMoney())
				.append("</drugMoney>").append("\r\n")
				.append("		<useTime>")
				.append(drugUseList.get(i).getUseTime())
				.append("</useTime>").append("\r\n")
				.append("	</DrugUse>").append("\r\n");
			}
			sb.append("</DrugUses>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(DrugUse drugUse: drugUseList) {
				  stringer.object();
			  stringer.key("drugUseId").value(drugUse.getDrugUseId());
			  stringer.key("treatObj").value(drugUse.getTreatObj());
			  stringer.key("drugObj").value(drugUse.getDrugObj());
			  stringer.key("drugCount").value(drugUse.getDrugCount());
			  stringer.key("drugMoney").value(drugUse.getDrugMoney());
			  stringer.key("useTime").value(drugUse.getUseTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加用药：获取用药参数，参数保存到新建的用药对象 */ 
			DrugUse drugUse = new DrugUse();
			int drugUseId = Integer.parseInt(request.getParameter("drugUseId"));
			drugUse.setDrugUseId(drugUseId);
			int treatObj = Integer.parseInt(request.getParameter("treatObj"));
			drugUse.setTreatObj(treatObj);
			int drugObj = Integer.parseInt(request.getParameter("drugObj"));
			drugUse.setDrugObj(drugObj);
			float drugCount = Float.parseFloat(request.getParameter("drugCount"));
			drugUse.setDrugCount(drugCount);
			float drugMoney = Float.parseFloat(request.getParameter("drugMoney"));
			drugUse.setDrugMoney(drugMoney);
			String useTime = new String(request.getParameter("useTime").getBytes("iso-8859-1"), "UTF-8");
			drugUse.setUseTime(useTime);

			/* 调用业务层执行添加操作 */
			String result = drugUseDAO.AddDrugUse(drugUse);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除用药：获取用药的用药id*/
			int drugUseId = Integer.parseInt(request.getParameter("drugUseId"));
			/*调用业务逻辑层执行删除操作*/
			String result = drugUseDAO.DeleteDrugUse(drugUseId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新用药之前先根据drugUseId查询某个用药*/
			int drugUseId = Integer.parseInt(request.getParameter("drugUseId"));
			DrugUse drugUse = drugUseDAO.GetDrugUse(drugUseId);

			// 客户端查询的用药对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("drugUseId").value(drugUse.getDrugUseId());
			  stringer.key("treatObj").value(drugUse.getTreatObj());
			  stringer.key("drugObj").value(drugUse.getDrugObj());
			  stringer.key("drugCount").value(drugUse.getDrugCount());
			  stringer.key("drugMoney").value(drugUse.getDrugMoney());
			  stringer.key("useTime").value(drugUse.getUseTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新用药：获取用药参数，参数保存到新建的用药对象 */ 
			DrugUse drugUse = new DrugUse();
			int drugUseId = Integer.parseInt(request.getParameter("drugUseId"));
			drugUse.setDrugUseId(drugUseId);
			int treatObj = Integer.parseInt(request.getParameter("treatObj"));
			drugUse.setTreatObj(treatObj);
			int drugObj = Integer.parseInt(request.getParameter("drugObj"));
			drugUse.setDrugObj(drugObj);
			float drugCount = Float.parseFloat(request.getParameter("drugCount"));
			drugUse.setDrugCount(drugCount);
			float drugMoney = Float.parseFloat(request.getParameter("drugMoney"));
			drugUse.setDrugMoney(drugMoney);
			String useTime = new String(request.getParameter("useTime").getBytes("iso-8859-1"), "UTF-8");
			drugUse.setUseTime(useTime);

			/* 调用业务层执行更新操作 */
			String result = drugUseDAO.UpdateDrugUse(drugUse);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
