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

	/*���첡��ҵ������*/
	private PatientDAO patientDAO = new PatientDAO();

	/*Ĭ�Ϲ��캯��*/
	public PatientServlet() {
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
			/*��ȡ��ѯ���˵Ĳ�����Ϣ*/
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

			/*����ҵ���߼���ִ�в��˲�ѯ*/
			List<Patient> patientList = patientDAO.QueryPatient(name,birthday,cardNo,originPlace,telephone);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��Ӳ��ˣ���ȡ���˲������������浽�½��Ĳ��˶��� */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = patientDAO.AddPatient(patient);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�����ˣ���ȡ���˵Ĳ���id*/
			int patiendId = Integer.parseInt(request.getParameter("patiendId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = patientDAO.DeletePatient(patiendId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���²���֮ǰ�ȸ���patiendId��ѯĳ������*/
			int patiendId = Integer.parseInt(request.getParameter("patiendId"));
			Patient patient = patientDAO.GetPatient(patiendId);

			// �ͻ��˲�ѯ�Ĳ��˶��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���²��ˣ���ȡ���˲������������浽�½��Ĳ��˶��� */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = patientDAO.UpdatePatient(patient);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
