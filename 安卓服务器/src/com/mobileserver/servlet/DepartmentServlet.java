package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.DepartmentDAO;
import com.mobileserver.domain.Department;

import org.json.JSONStringer;

public class DepartmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造科室业务层对象*/
	private DepartmentDAO departmentDAO = new DepartmentDAO();

	/*默认构造函数*/
	public DepartmentServlet() {
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
			/*获取查询科室的参数信息*/
			Timestamp bornDate = null;
			if (request.getParameter("bornDate") != null)
				bornDate = Timestamp.valueOf(request.getParameter("bornDate"));

			/*调用业务逻辑层执行科室查询*/
			List<Department> departmentList = departmentDAO.QueryDepartment(bornDate);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Departments>").append("\r\n");
			for (int i = 0; i < departmentList.size(); i++) {
				sb.append("	<Department>").append("\r\n")
				.append("		<departmentNo>")
				.append(departmentList.get(i).getDepartmentNo())
				.append("</departmentNo>").append("\r\n")
				.append("		<departmentName>")
				.append(departmentList.get(i).getDepartmentName())
				.append("</departmentName>").append("\r\n")
				.append("		<bornDate>")
				.append(departmentList.get(i).getBornDate())
				.append("</bornDate>").append("\r\n")
				.append("		<chargeMan>")
				.append(departmentList.get(i).getChargeMan())
				.append("</chargeMan>").append("\r\n")
				.append("	</Department>").append("\r\n");
			}
			sb.append("</Departments>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Department department: departmentList) {
				  stringer.object();
			  stringer.key("departmentNo").value(department.getDepartmentNo());
			  stringer.key("departmentName").value(department.getDepartmentName());
			  stringer.key("bornDate").value(department.getBornDate());
			  stringer.key("chargeMan").value(department.getChargeMan());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加科室：获取科室参数，参数保存到新建的科室对象 */ 
			Department department = new Department();
			String departmentNo = new String(request.getParameter("departmentNo").getBytes("iso-8859-1"), "UTF-8");
			department.setDepartmentNo(departmentNo);
			String departmentName = new String(request.getParameter("departmentName").getBytes("iso-8859-1"), "UTF-8");
			department.setDepartmentName(departmentName);
			Timestamp bornDate = Timestamp.valueOf(request.getParameter("bornDate"));
			department.setBornDate(bornDate);
			String chargeMan = new String(request.getParameter("chargeMan").getBytes("iso-8859-1"), "UTF-8");
			department.setChargeMan(chargeMan);

			/* 调用业务层执行添加操作 */
			String result = departmentDAO.AddDepartment(department);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除科室：获取科室的科室编号*/
			String departmentNo = new String(request.getParameter("departmentNo").getBytes("iso-8859-1"), "UTF-8");
			/*调用业务逻辑层执行删除操作*/
			String result = departmentDAO.DeleteDepartment(departmentNo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新科室之前先根据departmentNo查询某个科室*/
			String departmentNo = new String(request.getParameter("departmentNo").getBytes("iso-8859-1"), "UTF-8");
			Department department = departmentDAO.GetDepartment(departmentNo);

			// 客户端查询的科室对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("departmentNo").value(department.getDepartmentNo());
			  stringer.key("departmentName").value(department.getDepartmentName());
			  stringer.key("bornDate").value(department.getBornDate());
			  stringer.key("chargeMan").value(department.getChargeMan());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新科室：获取科室参数，参数保存到新建的科室对象 */ 
			Department department = new Department();
			String departmentNo = new String(request.getParameter("departmentNo").getBytes("iso-8859-1"), "UTF-8");
			department.setDepartmentNo(departmentNo);
			String departmentName = new String(request.getParameter("departmentName").getBytes("iso-8859-1"), "UTF-8");
			department.setDepartmentName(departmentName);
			Timestamp bornDate = Timestamp.valueOf(request.getParameter("bornDate"));
			department.setBornDate(bornDate);
			String chargeMan = new String(request.getParameter("chargeMan").getBytes("iso-8859-1"), "UTF-8");
			department.setChargeMan(chargeMan);

			/* 调用业务层执行更新操作 */
			String result = departmentDAO.UpdateDepartment(department);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
