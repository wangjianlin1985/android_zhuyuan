package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.ZhuYuanDAO;
import com.mobileserver.domain.ZhuYuan;

import org.json.JSONStringer;

public class ZhuYuanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����סԺҵ������*/
	private ZhuYuanDAO zhuYuanDAO = new ZhuYuanDAO();

	/*Ĭ�Ϲ��캯��*/
	public ZhuYuanServlet() {
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
			/*��ȡ��ѯסԺ�Ĳ�����Ϣ*/
			int patientObj = 0;
			if (request.getParameter("patientObj") != null)
				patientObj = Integer.parseInt(request.getParameter("patientObj"));
			Timestamp inDate = null;
			if (request.getParameter("inDate") != null)
				inDate = Timestamp.valueOf(request.getParameter("inDate"));
			String bedNum = request.getParameter("bedNum");
			bedNum = bedNum == null ? "" : new String(request.getParameter(
					"bedNum").getBytes("iso-8859-1"), "UTF-8");
			String doctorObj = "";
			if (request.getParameter("doctorObj") != null)
				doctorObj = request.getParameter("doctorObj");

			/*����ҵ���߼���ִ��סԺ��ѯ*/
			List<ZhuYuan> zhuYuanList = zhuYuanDAO.QueryZhuYuan(patientObj,inDate,bedNum,doctorObj);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<ZhuYuans>").append("\r\n");
			for (int i = 0; i < zhuYuanList.size(); i++) {
				sb.append("	<ZhuYuan>").append("\r\n")
				.append("		<zhuyuanId>")
				.append(zhuYuanList.get(i).getZhuyuanId())
				.append("</zhuyuanId>").append("\r\n")
				.append("		<patientObj>")
				.append(zhuYuanList.get(i).getPatientObj())
				.append("</patientObj>").append("\r\n")
				.append("		<age>")
				.append(zhuYuanList.get(i).getAge())
				.append("</age>").append("\r\n")
				.append("		<inDate>")
				.append(zhuYuanList.get(i).getInDate())
				.append("</inDate>").append("\r\n")
				.append("		<inDays>")
				.append(zhuYuanList.get(i).getInDays())
				.append("</inDays>").append("\r\n")
				.append("		<bedNum>")
				.append(zhuYuanList.get(i).getBedNum())
				.append("</bedNum>").append("\r\n")
				.append("		<doctorObj>")
				.append(zhuYuanList.get(i).getDoctorObj())
				.append("</doctorObj>").append("\r\n")
				.append("		<memo>")
				.append(zhuYuanList.get(i).getMemo())
				.append("</memo>").append("\r\n")
				.append("	</ZhuYuan>").append("\r\n");
			}
			sb.append("</ZhuYuans>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(ZhuYuan zhuYuan: zhuYuanList) {
				  stringer.object();
			  stringer.key("zhuyuanId").value(zhuYuan.getZhuyuanId());
			  stringer.key("patientObj").value(zhuYuan.getPatientObj());
			  stringer.key("age").value(zhuYuan.getAge());
			  stringer.key("inDate").value(zhuYuan.getInDate());
			  stringer.key("inDays").value(zhuYuan.getInDays());
			  stringer.key("bedNum").value(zhuYuan.getBedNum());
			  stringer.key("doctorObj").value(zhuYuan.getDoctorObj());
			  stringer.key("memo").value(zhuYuan.getMemo());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���סԺ����ȡסԺ�������������浽�½���סԺ���� */ 
			ZhuYuan zhuYuan = new ZhuYuan();
			int zhuyuanId = Integer.parseInt(request.getParameter("zhuyuanId"));
			zhuYuan.setZhuyuanId(zhuyuanId);
			int patientObj = Integer.parseInt(request.getParameter("patientObj"));
			zhuYuan.setPatientObj(patientObj);
			int age = Integer.parseInt(request.getParameter("age"));
			zhuYuan.setAge(age);
			Timestamp inDate = Timestamp.valueOf(request.getParameter("inDate"));
			zhuYuan.setInDate(inDate);
			int inDays = Integer.parseInt(request.getParameter("inDays"));
			zhuYuan.setInDays(inDays);
			String bedNum = new String(request.getParameter("bedNum").getBytes("iso-8859-1"), "UTF-8");
			zhuYuan.setBedNum(bedNum);
			String doctorObj = new String(request.getParameter("doctorObj").getBytes("iso-8859-1"), "UTF-8");
			zhuYuan.setDoctorObj(doctorObj);
			String memo = new String(request.getParameter("memo").getBytes("iso-8859-1"), "UTF-8");
			zhuYuan.setMemo(memo);

			/* ����ҵ���ִ����Ӳ��� */
			String result = zhuYuanDAO.AddZhuYuan(zhuYuan);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��סԺ����ȡסԺ��סԺid*/
			int zhuyuanId = Integer.parseInt(request.getParameter("zhuyuanId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = zhuYuanDAO.DeleteZhuYuan(zhuyuanId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����סԺ֮ǰ�ȸ���zhuyuanId��ѯĳ��סԺ*/
			int zhuyuanId = Integer.parseInt(request.getParameter("zhuyuanId"));
			ZhuYuan zhuYuan = zhuYuanDAO.GetZhuYuan(zhuyuanId);

			// �ͻ��˲�ѯ��סԺ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("zhuyuanId").value(zhuYuan.getZhuyuanId());
			  stringer.key("patientObj").value(zhuYuan.getPatientObj());
			  stringer.key("age").value(zhuYuan.getAge());
			  stringer.key("inDate").value(zhuYuan.getInDate());
			  stringer.key("inDays").value(zhuYuan.getInDays());
			  stringer.key("bedNum").value(zhuYuan.getBedNum());
			  stringer.key("doctorObj").value(zhuYuan.getDoctorObj());
			  stringer.key("memo").value(zhuYuan.getMemo());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����סԺ����ȡסԺ�������������浽�½���סԺ���� */ 
			ZhuYuan zhuYuan = new ZhuYuan();
			int zhuyuanId = Integer.parseInt(request.getParameter("zhuyuanId"));
			zhuYuan.setZhuyuanId(zhuyuanId);
			int patientObj = Integer.parseInt(request.getParameter("patientObj"));
			zhuYuan.setPatientObj(patientObj);
			int age = Integer.parseInt(request.getParameter("age"));
			zhuYuan.setAge(age);
			Timestamp inDate = Timestamp.valueOf(request.getParameter("inDate"));
			zhuYuan.setInDate(inDate);
			int inDays = Integer.parseInt(request.getParameter("inDays"));
			zhuYuan.setInDays(inDays);
			String bedNum = new String(request.getParameter("bedNum").getBytes("iso-8859-1"), "UTF-8");
			zhuYuan.setBedNum(bedNum);
			String doctorObj = new String(request.getParameter("doctorObj").getBytes("iso-8859-1"), "UTF-8");
			zhuYuan.setDoctorObj(doctorObj);
			String memo = new String(request.getParameter("memo").getBytes("iso-8859-1"), "UTF-8");
			zhuYuan.setMemo(memo);

			/* ����ҵ���ִ�и��²��� */
			String result = zhuYuanDAO.UpdateZhuYuan(zhuYuan);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
