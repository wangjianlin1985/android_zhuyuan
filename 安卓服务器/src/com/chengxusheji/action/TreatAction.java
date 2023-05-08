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
import com.chengxusheji.dao.TreatDAO;
import com.chengxusheji.domain.Treat;
import com.chengxusheji.dao.PatientDAO;
import com.chengxusheji.domain.Patient;
import com.chengxusheji.dao.DoctorDAO;
import com.chengxusheji.domain.Doctor;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class TreatAction extends BaseAction {

    /*�������Ҫ��ѯ������: ��������*/
    private String treatName;
    public void setTreatName(String treatName) {
        this.treatName = treatName;
    }
    public String getTreatName() {
        return this.treatName;
    }

    /*�������Ҫ��ѯ������: ����*/
    private Patient patientObj;
    public void setPatientObj(Patient patientObj) {
        this.patientObj = patientObj;
    }
    public Patient getPatientObj() {
        return this.patientObj;
    }

    /*�������Ҫ��ѯ������: ����ҽ��*/
    private Doctor doctorObj;
    public void setDoctorObj(Doctor doctorObj) {
        this.doctorObj = doctorObj;
    }
    public Doctor getDoctorObj() {
        return this.doctorObj;
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

    private int treatId;
    public void setTreatId(int treatId) {
        this.treatId = treatId;
    }
    public int getTreatId() {
        return treatId;
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
    @Resource DoctorDAO doctorDAO;
    @Resource TreatDAO treatDAO;

    /*��������Treat����*/
    private Treat treat;
    public void setTreat(Treat treat) {
        this.treat = treat;
    }
    public Treat getTreat() {
        return this.treat;
    }

    /*��ת�����Treat��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Patient��Ϣ*/
        List<Patient> patientList = patientDAO.QueryAllPatientInfo();
        ctx.put("patientList", patientList);
        /*��ѯ���е�Doctor��Ϣ*/
        List<Doctor> doctorList = doctorDAO.QueryAllDoctorInfo();
        ctx.put("doctorList", doctorList);
        return "add_view";
    }

    /*���Treat��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddTreat() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Patient patientObj = patientDAO.GetPatientByPatiendId(treat.getPatientObj().getPatiendId());
            treat.setPatientObj(patientObj);
            Doctor doctorObj = doctorDAO.GetDoctorByDoctorNo(treat.getDoctorObj().getDoctorNo());
            treat.setDoctorObj(doctorObj);
            treatDAO.AddTreat(treat);
            ctx.put("message",  java.net.URLEncoder.encode("Treat��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Treat���ʧ��!"));
            return "error";
        }
    }

    /*��ѯTreat��Ϣ*/
    public String QueryTreat() {
        if(currentPage == 0) currentPage = 1;
        if(treatName == null) treatName = "";
        List<Treat> treatList = treatDAO.QueryTreatInfo(treatName, patientObj, doctorObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        treatDAO.CalculateTotalPageAndRecordNumber(treatName, patientObj, doctorObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = treatDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = treatDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("treatList",  treatList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("treatName", treatName);
        ctx.put("patientObj", patientObj);
        List<Patient> patientList = patientDAO.QueryAllPatientInfo();
        ctx.put("patientList", patientList);
        ctx.put("doctorObj", doctorObj);
        List<Doctor> doctorList = doctorDAO.QueryAllDoctorInfo();
        ctx.put("doctorList", doctorList);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryTreatOutputToExcel() { 
        if(treatName == null) treatName = "";
        List<Treat> treatList = treatDAO.QueryTreatInfo(treatName,patientObj,doctorObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Treat��Ϣ��¼"; 
        String[] headers = { "����id","��������","����","������","���Ƽ�¼","���ƽ��","����ҽ��","���ƿ�ʼʱ��","�Ƴ�ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<treatList.size();i++) {
        	Treat treat = treatList.get(i); 
        	dataset.add(new String[]{treat.getTreatId() + "",treat.getTreatName(),treat.getPatientObj().getName(),
treat.getDiagnosis(),treat.getTreatContent(),treat.getTreatResult(),treat.getDoctorObj().getName(),
treat.getStartTime(),treat.getTimeLong()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Treat.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯTreat��Ϣ*/
    public String FrontQueryTreat() {
        if(currentPage == 0) currentPage = 1;
        if(treatName == null) treatName = "";
        List<Treat> treatList = treatDAO.QueryTreatInfo(treatName, patientObj, doctorObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        treatDAO.CalculateTotalPageAndRecordNumber(treatName, patientObj, doctorObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = treatDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = treatDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("treatList",  treatList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("treatName", treatName);
        ctx.put("patientObj", patientObj);
        List<Patient> patientList = patientDAO.QueryAllPatientInfo();
        ctx.put("patientList", patientList);
        ctx.put("doctorObj", doctorObj);
        List<Doctor> doctorList = doctorDAO.QueryAllDoctorInfo();
        ctx.put("doctorList", doctorList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Treat��Ϣ*/
    public String ModifyTreatQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������treatId��ȡTreat����*/
        Treat treat = treatDAO.GetTreatByTreatId(treatId);

        List<Patient> patientList = patientDAO.QueryAllPatientInfo();
        ctx.put("patientList", patientList);
        List<Doctor> doctorList = doctorDAO.QueryAllDoctorInfo();
        ctx.put("doctorList", doctorList);
        ctx.put("treat",  treat);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Treat��Ϣ*/
    public String FrontShowTreatQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������treatId��ȡTreat����*/
        Treat treat = treatDAO.GetTreatByTreatId(treatId);

        List<Patient> patientList = patientDAO.QueryAllPatientInfo();
        ctx.put("patientList", patientList);
        List<Doctor> doctorList = doctorDAO.QueryAllDoctorInfo();
        ctx.put("doctorList", doctorList);
        ctx.put("treat",  treat);
        return "front_show_view";
    }

    /*�����޸�Treat��Ϣ*/
    public String ModifyTreat() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Patient patientObj = patientDAO.GetPatientByPatiendId(treat.getPatientObj().getPatiendId());
            treat.setPatientObj(patientObj);
            Doctor doctorObj = doctorDAO.GetDoctorByDoctorNo(treat.getDoctorObj().getDoctorNo());
            treat.setDoctorObj(doctorObj);
            treatDAO.UpdateTreat(treat);
            ctx.put("message",  java.net.URLEncoder.encode("Treat��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Treat��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Treat��Ϣ*/
    public String DeleteTreat() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            treatDAO.DeleteTreat(treatId);
            ctx.put("message",  java.net.URLEncoder.encode("Treatɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Treatɾ��ʧ��!"));
            return "error";
        }
    }

}
