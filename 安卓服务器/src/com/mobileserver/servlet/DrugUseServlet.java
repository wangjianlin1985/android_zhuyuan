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

	/*������ҩҵ������*/
	private DrugUseDAO drugUseDAO = new DrugUseDAO();

	/*Ĭ�Ϲ��캯��*/
	public DrugUseServlet() {
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
			/*��ȡ��ѯ��ҩ�Ĳ�����Ϣ*/
			int treatObj = 0;
			if (request.getParameter("treatObj") != null)
				treatObj = Integer.parseInt(request.getParameter("treatObj"));
			int drugObj = 0;
			if (request.getParameter("drugObj") != null)
				drugObj = Integer.parseInt(request.getParameter("drugObj"));
			String useTime = request.getParameter("useTime");
			useTime = useTime == null ? "" : new String(request.getParameter(
					"useTime").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ����ҩ��ѯ*/
			List<DrugUse> drugUseList = drugUseDAO.QueryDrugUse(treatObj,drugObj,useTime);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �����ҩ����ȡ��ҩ�������������浽�½�����ҩ���� */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = drugUseDAO.AddDrugUse(drugUse);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����ҩ����ȡ��ҩ����ҩid*/
			int drugUseId = Integer.parseInt(request.getParameter("drugUseId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = drugUseDAO.DeleteDrugUse(drugUseId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*������ҩ֮ǰ�ȸ���drugUseId��ѯĳ����ҩ*/
			int drugUseId = Integer.parseInt(request.getParameter("drugUseId"));
			DrugUse drugUse = drugUseDAO.GetDrugUse(drugUseId);

			// �ͻ��˲�ѯ����ҩ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������ҩ����ȡ��ҩ�������������浽�½�����ҩ���� */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = drugUseDAO.UpdateDrugUse(drugUse);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
