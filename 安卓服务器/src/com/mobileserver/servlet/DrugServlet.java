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

	/*����ҩƷҵ������*/
	private DrugDAO drugDAO = new DrugDAO();

	/*Ĭ�Ϲ��캯��*/
	public DrugServlet() {
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
			/*��ȡ��ѯҩƷ�Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ��ҩƷ��ѯ*/
			List<Drug> drugList = drugDAO.QueryDrug();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���ҩƷ����ȡҩƷ�������������浽�½���ҩƷ���� */ 
			Drug drug = new Drug();
			int drugId = Integer.parseInt(request.getParameter("drugId"));
			drug.setDrugId(drugId);
			String drugName = new String(request.getParameter("drugName").getBytes("iso-8859-1"), "UTF-8");
			drug.setDrugName(drugName);
			String unit = new String(request.getParameter("unit").getBytes("iso-8859-1"), "UTF-8");
			drug.setUnit(unit);
			float price = Float.parseFloat(request.getParameter("price"));
			drug.setPrice(price);

			/* ����ҵ���ִ����Ӳ��� */
			String result = drugDAO.AddDrug(drug);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��ҩƷ����ȡҩƷ��ҩƷid*/
			int drugId = Integer.parseInt(request.getParameter("drugId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = drugDAO.DeleteDrug(drugId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����ҩƷ֮ǰ�ȸ���drugId��ѯĳ��ҩƷ*/
			int drugId = Integer.parseInt(request.getParameter("drugId"));
			Drug drug = drugDAO.GetDrug(drugId);

			// �ͻ��˲�ѯ��ҩƷ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����ҩƷ����ȡҩƷ�������������浽�½���ҩƷ���� */ 
			Drug drug = new Drug();
			int drugId = Integer.parseInt(request.getParameter("drugId"));
			drug.setDrugId(drugId);
			String drugName = new String(request.getParameter("drugName").getBytes("iso-8859-1"), "UTF-8");
			drug.setDrugName(drugName);
			String unit = new String(request.getParameter("unit").getBytes("iso-8859-1"), "UTF-8");
			drug.setUnit(unit);
			float price = Float.parseFloat(request.getParameter("price"));
			drug.setPrice(price);

			/* ����ҵ���ִ�и��²��� */
			String result = drugDAO.UpdateDrug(drug);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
