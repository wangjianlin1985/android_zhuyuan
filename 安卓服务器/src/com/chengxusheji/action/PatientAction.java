package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.PatientDAO;
import com.chengxusheji.domain.Patient;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class PatientAction extends BaseAction {

    /*�������Ҫ��ѯ������: ����*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String birthday;
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getBirthday() {
        return this.birthday;
    }

    /*�������Ҫ��ѯ������: ���֤��*/
    private String cardNo;
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    public String getCardNo() {
        return this.cardNo;
    }

    /*�������Ҫ��ѯ������: ����*/
    private String originPlace;
    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }
    public String getOriginPlace() {
        return this.originPlace;
    }

    /*�������Ҫ��ѯ������: ��ϵ�绰*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int patiendId;
    public void setPatiendId(int patiendId) {
        this.patiendId = patiendId;
    }
    public int getPatiendId() {
        return patiendId;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource PatientDAO patientDAO;

    /*��������Patient����*/
    private Patient patient;
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public Patient getPatient() {
        return this.patient;
    }

    /*��ת�����Patient��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���Patient��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddPatient() {
        ActionContext ctx = ActionContext.getContext();
        try {
            patientDAO.AddPatient(patient);
            ctx.put("message",  java.net.URLEncoder.encode("Patient��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Patient���ʧ��!"));
            return "error";
        }
    }

    /*��ѯPatient��Ϣ*/
    public String QueryPatient() {
        if(currentPage == 0) currentPage = 1;
        if(name == null) name = "";
        if(birthday == null) birthday = "";
        if(cardNo == null) cardNo = "";
        if(originPlace == null) originPlace = "";
        if(telephone == null) telephone = "";
        List<Patient> patientList = patientDAO.QueryPatientInfo(name, birthday, cardNo, originPlace, telephone, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        patientDAO.CalculateTotalPageAndRecordNumber(name, birthday, cardNo, originPlace, telephone);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = patientDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = patientDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("patientList",  patientList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("name", name);
        ctx.put("birthday", birthday);
        ctx.put("cardNo", cardNo);
        ctx.put("originPlace", originPlace);
        ctx.put("telephone", telephone);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryPatientOutputToExcel() { 
        if(name == null) name = "";
        if(birthday == null) birthday = "";
        if(cardNo == null) cardNo = "";
        if(originPlace == null) originPlace = "";
        if(telephone == null) telephone = "";
        List<Patient> patientList = patientDAO.QueryPatientInfo(name,birthday,cardNo,originPlace,telephone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Patient��Ϣ��¼"; 
        String[] headers = { "����id","����","�Ա�","��������","���֤��","����","��ϵ�绰"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<patientList.size();i++) {
        	Patient patient = patientList.get(i); 
        	dataset.add(new String[]{patient.getPatiendId() + "",patient.getName(),patient.getSex(),new SimpleDateFormat("yyyy-MM-dd").format(patient.getBirthday()),patient.getCardNo(),patient.getOriginPlace(),patient.getTelephone()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Patient.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*ǰ̨��ѯPatient��Ϣ*/
    public String FrontQueryPatient() {
        if(currentPage == 0) currentPage = 1;
        if(name == null) name = "";
        if(birthday == null) birthday = "";
        if(cardNo == null) cardNo = "";
        if(originPlace == null) originPlace = "";
        if(telephone == null) telephone = "";
        List<Patient> patientList = patientDAO.QueryPatientInfo(name, birthday, cardNo, originPlace, telephone, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        patientDAO.CalculateTotalPageAndRecordNumber(name, birthday, cardNo, originPlace, telephone);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = patientDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = patientDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("patientList",  patientList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("name", name);
        ctx.put("birthday", birthday);
        ctx.put("cardNo", cardNo);
        ctx.put("originPlace", originPlace);
        ctx.put("telephone", telephone);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Patient��Ϣ*/
    public String ModifyPatientQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������patiendId��ȡPatient����*/
        Patient patient = patientDAO.GetPatientByPatiendId(patiendId);

        ctx.put("patient",  patient);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Patient��Ϣ*/
    public String FrontShowPatientQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������patiendId��ȡPatient����*/
        Patient patient = patientDAO.GetPatientByPatiendId(patiendId);

        ctx.put("patient",  patient);
        return "front_show_view";
    }

    /*�����޸�Patient��Ϣ*/
    public String ModifyPatient() {
        ActionContext ctx = ActionContext.getContext();
        try {
            patientDAO.UpdatePatient(patient);
            ctx.put("message",  java.net.URLEncoder.encode("Patient��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Patient��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Patient��Ϣ*/
    public String DeletePatient() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            patientDAO.DeletePatient(patiendId);
            ctx.put("message",  java.net.URLEncoder.encode("Patientɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Patientɾ��ʧ��!"));
            return "error";
        }
    }

}
