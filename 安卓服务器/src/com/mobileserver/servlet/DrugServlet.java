package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.DrugDAO;
import com.mobileserver.domain.Drug;

import org.json.JSONStringer;

public class DrugServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造药品业务层对象*/
	private DrugDAO drugDAO = new DrugDAO();

	/*默认构造函数*/
	public DrugServlet() {
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
			/*获取查询药品的参数信息*/

			/*调用业务逻辑层执行药品查询*/
			List<Drug> drugList = drugDAO.QueryDrug();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Drugs>").append("\r\n");
			for (int i = 0; i < drugList.size(); i++) {
				sb.append("	<Drug>").append("\r\n")
				.append("		<drugId>")
				.append(drugList.get(i).getDrugId())
				.append("</drugId>").append("\r\n")
				.append("		<drugName>")
				.append(drugList.get(i).getDrugName())
				.append("</drugName>").append("\r\n")
				.append("		<unit>")
				.append(drugList.get(i).getUnit())
				.append("</unit>").append("\r\n")
				.append("		<price>")
				.append(drugList.get(i).getPrice())
				.append("</price>").append("\r\n")
				.append("	</Drug>").append("\r\n");
			}
			sb.append("</Drugs>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Drug drug: drugList) {
				  stringer.object();
			  stringer.key("drugId").value(drug.getDrugId());
			  stringer.key("drugName").value(drug.getDrugName());
			  stringer.key("unit").value(drug.getUnit());
			  stringer.key("price").value(drug.getPrice());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加药品：获取药品参数，参数保存到新建的药品对象 */ 
			Drug drug = new Drug();
			int drugId = Integer.parseInt(request.getParameter("drugId"));
			drug.setDrugId(drugId);
			String drugName = new String(request.getParameter("drugName").getBytes("iso-8859-1"), "UTF-8");
			drug.setDrugName(drugName);
			String unit = new String(request.getParameter("unit").getBytes("iso-8859-1"), "UTF-8");
			drug.setUnit(unit);
			float price = Float.parseFloat(request.getParameter("price"));
			drug.setPrice(price);

			/* 调用业务层执行添加操作 */
			String result = drugDAO.AddDrug(drug);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除药品：获取药品的药品id*/
			int drugId = Integer.parseInt(request.getParameter("drugId"));
			/*调用业务逻辑层执行删除操作*/
			String result = drugDAO.DeleteDrug(drugId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新药品之前先根据drugId查询某个药品*/
			int drugId = Integer.parseInt(request.getParameter("drugId"));
			Drug drug = drugDAO.GetDrug(drugId);

			// 客户端查询的药品对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("drugId").value(drug.getDrugId());
			  stringer.key("drugName").value(drug.getDrugName());
			  stringer.key("unit").value(drug.getUnit());
			  stringer.key("price").value(drug.getPrice());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新药品：获取药品参数，参数保存到新建的药品对象 */ 
			Drug drug = new Drug();
			int drugId = Integer.parseInt(request.getParameter("drugId"));
			drug.setDrugId(drugId);
			String drugName = new String(request.getParameter("drugName").getBytes("iso-8859-1"), "UTF-8");
			drug.setDrugName(drugName);
			String unit = new String(request.getParameter("unit").getBytes("iso-8859-1"), "UTF-8");
			drug.setUnit(unit);
			float price = Float.parseFloat(request.getParameter("price"));
			drug.setPrice(price);

			/* 调用业务层执行更新操作 */
			String result = drugDAO.UpdateDrug(drug);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
