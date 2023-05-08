package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.PatientDAO;
import com.mobileserver.domain.Patient;

import org.json.JSONStringer;

public class PatientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造病人业务层对象*/
	private PatientDAO patientDAO = new PatientDAO();

	/*默认构造函数*/
	public PatientServlet() {
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
			/*获取查询病人的参数信息*/
			String name = request.getParameter("name");
			name = name == null ? "" : new String(request.getParameter(
					"name").getBytes("iso-8859-1"), "UTF-8");
			Timestamp birthday = null;
			if (request.getParameter("birthday") != null)
				birthday = Timestamp.valueOf(request.getParameter("birthday"));
			String cardNo = request.getParameter("cardNo");
			cardNo = cardNo == null ? "" : new String(request.getParameter(
					"cardNo").getBytes("iso-8859-1"), "UTF-8");
			String originPlace = request.getParameter("originPlace");
			originPlace = originPlace == null ? "" : new String(request.getParameter(
					"originPlace").getBytes("iso-8859-1"), "UTF-8");
			String telephone = request.getParameter("telephone");
			telephone = telephone == null ? "" : new String(request.getParameter(
					"telephone").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行病人查询*/
			List<Patient> patientList = patientDAO.QueryPatient(name,birthday,cardNo,originPlace,telephone);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Patients>").append("\r\n");
			for (int i = 0; i < patientList.size(); i++) {
				sb.append("	<Patient>").append("\r\n")
				.append("		<patiendId>")
				.append(patientList.get(i).getPatiendId())
				.append("</patiendId>").append("\r\n")
				.append("		<name>")
				.append(patientList.get(i).getName())
				.append("</name>").append("\r\n")
				.append("		<sex>")
				.append(patientList.get(i).getSex())
				.append("</sex>").append("\r\n")
				.append("		<birthday>")
				.append(patientList.get(i).getBirthday())
				.append("</birthday>").append("\r\n")
				.append("		<cardNo>")
				.append(patientList.get(i).getCardNo())
				.append("</cardNo>").append("\r\n")
				.append("		<originPlace>")
				.append(patientList.get(i).getOriginPlace())
				.append("</originPlace>").append("\r\n")
				.append("		<telephone>")
				.append(patientList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("		<address>")
				.append(patientList.get(i).getAddress())
				.append("</address>").append("\r\n")
				.append("		<caseHistory>")
				.append(patientList.get(i).getCaseHistory())
				.append("</caseHistory>").append("\r\n")
				.append("	</Patient>").append("\r\n");
			}
			sb.append("</Patients>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Patient patient: patientList) {
				  stringer.object();
			  stringer.key("patiendId").value(patient.getPatiendId());
			  stringer.key("name").value(patient.getName());
			  stringer.key("sex").value(patient.getSex());
			  stringer.key("birthday").value(patient.getBirthday());
			  stringer.key("cardNo").value(patient.getCardNo());
			  stringer.key("originPlace").value(patient.getOriginPlace());
			  stringer.key("telephone").value(patient.getTelephone());
			  stringer.key("address").value(patient.getAddress());
			  stringer.key("caseHistory").value(patient.getCaseHistory());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加病人：获取病人参数，参数保存到新建的病人对象 */ 
			Patient patient = new Patient();
			int patiendId = Integer.parseInt(request.getParameter("patiendId"));
			patient.setPatiendId(patiendId);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			patient.setName(name);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			patient.setSex(sex);
			Timestamp birthday = Timestamp.valueOf(request.getParameter("birthday"));
			patient.setBirthday(birthday);
			String cardNo = new String(request.getParameter("cardNo").getBytes("iso-8859-1"), "UTF-8");
			patient.setCardNo(cardNo);
			String originPlace = new String(request.getParameter("originPlace").getBytes("iso-8859-1"), "UTF-8");
			patient.setOriginPlace(originPlace);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			patient.setTelephone(telephone);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			patient.setAddress(address);
			String caseHistory = new String(request.getParameter("caseHistory").getBytes("iso-8859-1"), "UTF-8");
			patient.setCaseHistory(caseHistory);

			/* 调用业务层执行添加操作 */
			String result = patientDAO.AddPatient(patient);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除病人：获取病人的病人id*/
			int patiendId = Integer.parseInt(request.getParameter("patiendId"));
			/*调用业务逻辑层执行删除操作*/
			String result = patientDAO.DeletePatient(patiendId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新病人之前先根据patiendId查询某个病人*/
			int patiendId = Integer.parseInt(request.getParameter("patiendId"));
			Patient patient = patientDAO.GetPatient(patiendId);

			// 客户端查询的病人对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("patiendId").value(patient.getPatiendId());
			  stringer.key("name").value(patient.getName());
			  stringer.key("sex").value(patient.getSex());
			  stringer.key("birthday").value(patient.getBirthday());
			  stringer.key("cardNo").value(patient.getCardNo());
			  stringer.key("originPlace").value(patient.getOriginPlace());
			  stringer.key("telephone").value(patient.getTelephone());
			  stringer.key("address").value(patient.getAddress());
			  stringer.key("caseHistory").value(patient.getCaseHistory());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新病人：获取病人参数，参数保存到新建的病人对象 */ 
			Patient patient = new Patient();
			int patiendId = Integer.parseInt(request.getParameter("patiendId"));
			patient.setPatiendId(patiendId);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			patient.setName(name);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			patient.setSex(sex);
			Timestamp birthday = Timestamp.valueOf(request.getParameter("birthday"));
			patient.setBirthday(birthday);
			String cardNo = new String(request.getParameter("cardNo").getBytes("iso-8859-1"), "UTF-8");
			patient.setCardNo(cardNo);
			String originPlace = new String(request.getParameter("originPlace").getBytes("iso-8859-1"), "UTF-8");
			patient.setOriginPlace(originPlace);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			patient.setTelephone(telephone);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			patient.setAddress(address);
			String caseHistory = new String(request.getParameter("caseHistory").getBytes("iso-8859-1"), "UTF-8");
			patient.setCaseHistory(caseHistory);

			/* 调用业务层执行更新操作 */
			String result = patientDAO.UpdatePatient(patient);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
