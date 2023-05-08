package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.DoctorDAO;
import com.mobileserver.domain.Doctor;

import org.json.JSONStringer;

public class DoctorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����ҽ��ҵ������*/
	private DoctorDAO doctorDAO = new DoctorDAO();

	/*Ĭ�Ϲ��캯��*/
	public DoctorServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯҽ���Ĳ�����Ϣ*/
			String doctorNo = request.getParameter("doctorNo");
			doctorNo = doctorNo == null ? "" : new String(request.getParameter(
					"doctorNo").getBytes("iso-8859-1"), "UTF-8");
			String departmentObj = "";
			if (request.getParameter("departmentObj") != null)
				departmentObj = request.getParameter("departmentObj");
			String name = request.getParameter("name");
			name = name == null ? "" : new String(request.getParameter(
					"name").getBytes("iso-8859-1"), "UTF-8");
			String cardNo = request.getParameter("cardNo");
			cardNo = cardNo == null ? "" : new String(request.getParameter(
					"cardNo").getBytes("iso-8859-1"), "UTF-8");
			Timestamp birthday = null;
			if (request.getParameter("birthday") != null)
				birthday = Timestamp.valueOf(request.getParameter("birthday"));
			String telephone = request.getParameter("telephone");
			telephone = telephone == null ? "" : new String(request.getParameter(
					"telephone").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ��ҽ����ѯ*/
			List<Doctor> doctorList = doctorDAO.QueryDoctor(doctorNo,departmentObj,name,cardNo,birthday,telephone);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Doctors>").append("\r\n");
			for (int i = 0; i < doctorList.size(); i++) {
				sb.append("	<Doctor>").append("\r\n")
				.append("		<doctorNo>")
				.append(doctorList.get(i).getDoctorNo())
				.append("</doctorNo>").append("\r\n")
				.append("		<password>")
				.append(doctorList.get(i).getPassword())
				.append("</password>").append("\r\n")
				.append("		<departmentObj>")
				.append(doctorList.get(i).getDepartmentObj())
				.append("</departmentObj>").append("\r\n")
				.append("		<name>")
				.append(doctorList.get(i).getName())
				.append("</name>").append("\r\n")
				.append("		<sex>")
				.append(doctorList.get(i).getSex())
				.append("</sex>").append("\r\n")
				.append("		<cardNo>")
				.append(doctorList.get(i).getCardNo())
				.append("</cardNo>").append("\r\n")
				.append("		<doctorPhoto>")
				.append(doctorList.get(i).getDoctorPhoto())
				.append("</doctorPhoto>").append("\r\n")
				.append("		<birthday>")
				.append(doctorList.get(i).getBirthday())
				.append("</birthday>").append("\r\n")
				.append("		<telephone>")
				.append(doctorList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("		<school>")
				.append(doctorList.get(i).getSchool())
				.append("</school>").append("\r\n")
				.append("		<workYears>")
				.append(doctorList.get(i).getWorkYears())
				.append("</workYears>").append("\r\n")
				.append("		<memo>")
				.append(doctorList.get(i).getMemo())
				.append("</memo>").append("\r\n")
				.append("	</Doctor>").append("\r\n");
			}
			sb.append("</Doctors>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Doctor doctor: doctorList) {
				  stringer.object();
			  stringer.key("doctorNo").value(doctor.getDoctorNo());
			  stringer.key("password").value(doctor.getPassword());
			  stringer.key("departmentObj").value(doctor.getDepartmentObj());
			  stringer.key("name").value(doctor.getName());
			  stringer.key("sex").value(doctor.getSex());
			  stringer.key("cardNo").value(doctor.getCardNo());
			  stringer.key("doctorPhoto").value(doctor.getDoctorPhoto());
			  stringer.key("birthday").value(doctor.getBirthday());
			  stringer.key("telephone").value(doctor.getTelephone());
			  stringer.key("school").value(doctor.getSchool());
			  stringer.key("workYears").value(doctor.getWorkYears());
			  stringer.key("memo").value(doctor.getMemo());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���ҽ������ȡҽ���������������浽�½���ҽ������ */ 
			Doctor doctor = new Doctor();
			String doctorNo = new String(request.getParameter("doctorNo").getBytes("iso-8859-1"), "UTF-8");
			doctor.setDoctorNo(doctorNo);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			doctor.setPassword(password);
			String departmentObj = new String(request.getParameter("departmentObj").getBytes("iso-8859-1"), "UTF-8");
			doctor.setDepartmentObj(departmentObj);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			doctor.setName(name);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			doctor.setSex(sex);
			String cardNo = new String(request.getParameter("cardNo").getBytes("iso-8859-1"), "UTF-8");
			doctor.setCardNo(cardNo);
			String doctorPhoto = new String(request.getParameter("doctorPhoto").getBytes("iso-8859-1"), "UTF-8");
			doctor.setDoctorPhoto(doctorPhoto);
			Timestamp birthday = Timestamp.valueOf(request.getParameter("birthday"));
			doctor.setBirthday(birthday);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			doctor.setTelephone(telephone);
			String school = new String(request.getParameter("school").getBytes("iso-8859-1"), "UTF-8");
			doctor.setSchool(school);
			String workYears = new String(request.getParameter("workYears").getBytes("iso-8859-1"), "UTF-8");
			doctor.setWorkYears(workYears);
			String memo = new String(request.getParameter("memo").getBytes("iso-8859-1"), "UTF-8");
			doctor.setMemo(memo);

			/* ����ҵ���ִ����Ӳ��� */
			String result = doctorDAO.AddDoctor(doctor);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��ҽ������ȡҽ����ҽ������*/
			String doctorNo = new String(request.getParameter("doctorNo").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = doctorDAO.DeleteDoctor(doctorNo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����ҽ��֮ǰ�ȸ���doctorNo��ѯĳ��ҽ��*/
			String doctorNo = new String(request.getParameter("doctorNo").getBytes("iso-8859-1"), "UTF-8");
			Doctor doctor = doctorDAO.GetDoctor(doctorNo);

			// �ͻ��˲�ѯ��ҽ�����󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("doctorNo").value(doctor.getDoctorNo());
			  stringer.key("password").value(doctor.getPassword());
			  stringer.key("departmentObj").value(doctor.getDepartmentObj());
			  stringer.key("name").value(doctor.getName());
			  stringer.key("sex").value(doctor.getSex());
			  stringer.key("cardNo").value(doctor.getCardNo());
			  stringer.key("doctorPhoto").value(doctor.getDoctorPhoto());
			  stringer.key("birthday").value(doctor.getBirthday());
			  stringer.key("telephone").value(doctor.getTelephone());
			  stringer.key("school").value(doctor.getSchool());
			  stringer.key("workYears").value(doctor.getWorkYears());
			  stringer.key("memo").value(doctor.getMemo());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����ҽ������ȡҽ���������������浽�½���ҽ������ */ 
			Doctor doctor = new Doctor();
			String doctorNo = new String(request.getParameter("doctorNo").getBytes("iso-8859-1"), "UTF-8");
			doctor.setDoctorNo(doctorNo);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			doctor.setPassword(password);
			String departmentObj = new String(request.getParameter("departmentObj").getBytes("iso-8859-1"), "UTF-8");
			doctor.setDepartmentObj(departmentObj);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			doctor.setName(name);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			doctor.setSex(sex);
			String cardNo = new String(request.getParameter("cardNo").getBytes("iso-8859-1"), "UTF-8");
			doctor.setCardNo(cardNo);
			String doctorPhoto = new String(request.getParameter("doctorPhoto").getBytes("iso-8859-1"), "UTF-8");
			doctor.setDoctorPhoto(doctorPhoto);
			Timestamp birthday = Timestamp.valueOf(request.getParameter("birthday"));
			doctor.setBirthday(birthday);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			doctor.setTelephone(telephone);
			String school = new String(request.getParameter("school").getBytes("iso-8859-1"), "UTF-8");
			doctor.setSchool(school);
			String workYears = new String(request.getParameter("workYears").getBytes("iso-8859-1"), "UTF-8");
			doctor.setWorkYears(workYears);
			String memo = new String(request.getParameter("memo").getBytes("iso-8859-1"), "UTF-8");
			doctor.setMemo(memo);

			/* ����ҵ���ִ�и��²��� */
			String result = doctorDAO.UpdateDoctor(doctor);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
