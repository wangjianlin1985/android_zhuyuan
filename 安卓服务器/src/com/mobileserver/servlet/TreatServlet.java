package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.TreatDAO;
import com.mobileserver.domain.Treat;

import org.json.JSONStringer;

public class TreatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造治疗业务层对象*/
	private TreatDAO treatDAO = new TreatDAO();

	/*默认构造函数*/
	public TreatServlet() {
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
			/*获取查询治疗的参数信息*/
			String treatName = request.getParameter("treatName");
			treatName = treatName == null ? "" : new String(request.getParameter(
					"treatName").getBytes("iso-8859-1"), "UTF-8");
			int patientObj = 0;
			if (request.getParameter("patientObj") != null)
				patientObj = Integer.parseInt(request.getParameter("patientObj"));
			String doctorObj = "";
			if (request.getParameter("doctorObj") != null)
				doctorObj = request.getParameter("doctorObj");

			/*调用业务逻辑层执行治疗查询*/
			List<Treat> treatList = treatDAO.QueryTreat(treatName,patientObj,doctorObj);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Treats>").append("\r\n");
			for (int i = 0; i < treatList.size(); i++) {
				sb.append("	<Treat>").append("\r\n")
				.append("		<treatId>")
				.append(treatList.get(i).getTreatId())
				.append("</treatId>").append("\r\n")
				.append("		<treatName>")
				.append(treatList.get(i).getTreatName())
				.append("</treatName>").append("\r\n")
				.append("		<patientObj>")
				.append(treatList.get(i).getPatientObj())
				.append("</patientObj>").append("\r\n")
				.append("		<diagnosis>")
				.append(treatList.get(i).getDiagnosis())
				.append("</diagnosis>").append("\r\n")
				.append("		<treatContent>")
				.append(treatList.get(i).getTreatContent())
				.append("</treatContent>").append("\r\n")
				.append("		<treatResult>")
				.append(treatList.get(i).getTreatResult())
				.append("</treatResult>").append("\r\n")
				.append("		<doctorObj>")
				.append(treatList.get(i).getDoctorObj())
				.append("</doctorObj>").append("\r\n")
				.append("		<startTime>")
				.append(treatList.get(i).getStartTime())
				.append("</startTime>").append("\r\n")
				.append("		<timeLong>")
				.append(treatList.get(i).getTimeLong())
				.append("</timeLong>").append("\r\n")
				.append("	</Treat>").append("\r\n");
			}
			sb.append("</Treats>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Treat treat: treatList) {
				  stringer.object();
			  stringer.key("treatId").value(treat.getTreatId());
			  stringer.key("treatName").value(treat.getTreatName());
			  stringer.key("patientObj").value(treat.getPatientObj());
			  stringer.key("diagnosis").value(treat.getDiagnosis());
			  stringer.key("treatContent").value(treat.getTreatContent());
			  stringer.key("treatResult").value(treat.getTreatResult());
			  stringer.key("doctorObj").value(treat.getDoctorObj());
			  stringer.key("startTime").value(treat.getStartTime());
			  stringer.key("timeLong").value(treat.getTimeLong());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加治疗：获取治疗参数，参数保存到新建的治疗对象 */ 
			Treat treat = new Treat();
			int treatId = Integer.parseInt(request.getParameter("treatId"));
			treat.setTreatId(treatId);
			String treatName = new String(request.getParameter("treatName").getBytes("iso-8859-1"), "UTF-8");
			treat.setTreatName(treatName);
			int patientObj = Integer.parseInt(request.getParameter("patientObj"));
			treat.setPatientObj(patientObj);
			String diagnosis = new String(request.getParameter("diagnosis").getBytes("iso-8859-1"), "UTF-8");
			treat.setDiagnosis(diagnosis);
			String treatContent = new String(request.getParameter("treatContent").getBytes("iso-8859-1"), "UTF-8");
			treat.setTreatContent(treatContent);
			String treatResult = new String(request.getParameter("treatResult").getBytes("iso-8859-1"), "UTF-8");
			treat.setTreatResult(treatResult);
			String doctorObj = new String(request.getParameter("doctorObj").getBytes("iso-8859-1"), "UTF-8");
			treat.setDoctorObj(doctorObj);
			String startTime = new String(request.getParameter("startTime").getBytes("iso-8859-1"), "UTF-8");
			treat.setStartTime(startTime);
			String timeLong = new String(request.getParameter("timeLong").getBytes("iso-8859-1"), "UTF-8");
			treat.setTimeLong(timeLong);

			/* 调用业务层执行添加操作 */
			String result = treatDAO.AddTreat(treat);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除治疗：获取治疗的治疗id*/
			int treatId = Integer.parseInt(request.getParameter("treatId"));
			/*调用业务逻辑层执行删除操作*/
			String result = treatDAO.DeleteTreat(treatId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新治疗之前先根据treatId查询某个治疗*/
			int treatId = Integer.parseInt(request.getParameter("treatId"));
			Treat treat = treatDAO.GetTreat(treatId);

			// 客户端查询的治疗对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("treatId").value(treat.getTreatId());
			  stringer.key("treatName").value(treat.getTreatName());
			  stringer.key("patientObj").value(treat.getPatientObj());
			  stringer.key("diagnosis").value(treat.getDiagnosis());
			  stringer.key("treatContent").value(treat.getTreatContent());
			  stringer.key("treatResult").value(treat.getTreatResult());
			  stringer.key("doctorObj").value(treat.getDoctorObj());
			  stringer.key("startTime").value(treat.getStartTime());
			  stringer.key("timeLong").value(treat.getTimeLong());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新治疗：获取治疗参数，参数保存到新建的治疗对象 */ 
			Treat treat = new Treat();
			int treatId = Integer.parseInt(request.getParameter("treatId"));
			treat.setTreatId(treatId);
			String treatName = new String(request.getParameter("treatName").getBytes("iso-8859-1"), "UTF-8");
			treat.setTreatName(treatName);
			int patientObj = Integer.parseInt(request.getParameter("patientObj"));
			treat.setPatientObj(patientObj);
			String diagnosis = new String(request.getParameter("diagnosis").getBytes("iso-8859-1"), "UTF-8");
			treat.setDiagnosis(diagnosis);
			String treatContent = new String(request.getParameter("treatContent").getBytes("iso-8859-1"), "UTF-8");
			treat.setTreatContent(treatContent);
			String treatResult = new String(request.getParameter("treatResult").getBytes("iso-8859-1"), "UTF-8");
			treat.setTreatResult(treatResult);
			String doctorObj = new String(request.getParameter("doctorObj").getBytes("iso-8859-1"), "UTF-8");
			treat.setDoctorObj(doctorObj);
			String startTime = new String(request.getParameter("startTime").getBytes("iso-8859-1"), "UTF-8");
			treat.setStartTime(startTime);
			String timeLong = new String(request.getParameter("timeLong").getBytes("iso-8859-1"), "UTF-8");
			treat.setTimeLong(timeLong);

			/* 调用业务层执行更新操作 */
			String result = treatDAO.UpdateTreat(treat);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
