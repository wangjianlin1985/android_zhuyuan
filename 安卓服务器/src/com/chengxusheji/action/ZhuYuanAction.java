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
import com.chengxusheji.dao.ZhuYuanDAO;
import com.chengxusheji.domain.ZhuYuan;
import com.chengxusheji.dao.PatientDAO;
import com.chengxusheji.domain.Patient;
import com.chengxusheji.dao.DoctorDAO;
import com.chengxusheji.domain.Doctor;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ZhuYuanAction extends BaseAction {

    /*�������Ҫ��ѯ������: ����*/
    private Patient patientObj;
    public void setPatientObj(Patient patientObj) {
        this.patientObj = patientObj;
    }
    public Patient getPatientObj() {
        return this.patientObj;
    }

    /*�������Ҫ��ѯ������: סԺ����*/
    private String inDate;
    public void setInDate(String inDate) {
        this.inDate = inDate;
    }
    public String getInDate() {
        return this.inDate;
    }

    /*�������Ҫ��ѯ������: ��λ��*/
    private String bedNum;
    public void setBedNum(String bedNum) {
        this.bedNum = bedNum;
    }
    public String getBedNum() {
        return this.bedNum;
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

    private int zhuyuanId;
    public void setZhuyuanId(int zhuyuanId) {
        this.zhuyuanId = zhuyuanId;
    }
    public int getZhuyuanId() {
        return zhuyuanId;
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
    @Resource ZhuYuanDAO zhuYuanDAO;

    /*��������ZhuYuan����*/
    private ZhuYuan zhuYuan;
    public void setZhuYuan(ZhuYuan zhuYuan) {
        this.zhuYuan = zhuYuan;
    }
    public ZhuYuan getZhuYuan() {
        return this.zhuYuan;
    }

    /*��ת�����ZhuYuan��ͼ*/
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

    /*���ZhuYuan��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddZhuYuan() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Patient patientObj = patientDAO.GetPatientByPatiendId(zhuYuan.getPatientObj().getPatiendId());
            zhuYuan.setPatientObj(patientObj);
            Doctor doctorObj = doctorDAO.GetDoctorByDoctorNo(zhuYuan.getDoctorObj().getDoctorNo());
            zhuYuan.setDoctorObj(doctorObj);
            zhuYuanDAO.AddZhuYuan(zhuYuan);
            ctx.put("message",  java.net.URLEncoder.encode("ZhuYuan��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ZhuYuan���ʧ��!"));
            return "error";
        }
    }

    /*��ѯZhuYuan��Ϣ*/
    public String QueryZhuYuan() {
        if(currentPage == 0) currentPage = 1;
        if(inDate == null) inDate = "";
        if(bedNum == null) bedNum = "";
        List<ZhuYuan> zhuYuanList = zhuYuanDAO.QueryZhuYuanInfo(patientObj, inDate, bedNum, doctorObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        zhuYuanDAO.CalculateTotalPageAndRecordNumber(patientObj, inDate, bedNum, doctorObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = zhuYuanDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = zhuYuanDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("zhuYuanList",  zhuYuanList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("patientObj", patientObj);
        List<Patient> patientList = patientDAO.QueryAllPatientInfo();
        ctx.put("patientList", patientList);
        ctx.put("inDate", inDate);
        ctx.put("bedNum", bedNum);
        ctx.put("doctorObj", doctorObj);
        List<Doctor> doctorList = doctorDAO.QueryAllDoctorInfo();
        ctx.put("doctorList", doctorList);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryZhuYuanOutputToExcel() { 
        if(inDate == null) inDate = "";
        if(bedNum == null) bedNum = "";
        List<ZhuYuan> zhuYuanList = zhuYuanDAO.QueryZhuYuanInfo(patientObj,inDate,bedNum,doctorObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ZhuYuan��Ϣ��¼"; 
        String[] headers = { "סԺid","����","����","סԺ����","��ס����","��λ��","����ҽ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<zhuYuanList.size();i++) {
        	ZhuYuan zhuYuan = zhuYuanList.get(i); 
        	dataset.add(new String[]{zhuYuan.getZhuyuanId() + "",zhuYuan.getPatientObj().getName(),
zhuYuan.getAge() + "",new SimpleDateFormat("yyyy-MM-dd").format(zhuYuan.getInDate()),zhuYuan.getInDays() + "",zhuYuan.getBedNum(),zhuYuan.getDoctorObj().getName()
});
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
			response.setHeader("Content-disposition","attachment; filename="+"ZhuYuan.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯZhuYuan��Ϣ*/
    public String FrontQueryZhuYuan() {
        if(currentPage == 0) currentPage = 1;
        if(inDate == null) inDate = "";
        if(bedNum == null) bedNum = "";
        List<ZhuYuan> zhuYuanList = zhuYuanDAO.QueryZhuYuanInfo(patientObj, inDate, bedNum, doctorObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        zhuYuanDAO.CalculateTotalPageAndRecordNumber(patientObj, inDate, bedNum, doctorObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = zhuYuanDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = zhuYuanDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("zhuYuanList",  zhuYuanList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("patientObj", patientObj);
        List<Patient> patientList = patientDAO.QueryAllPatientInfo();
        ctx.put("patientList", patientList);
        ctx.put("inDate", inDate);
        ctx.put("bedNum", bedNum);
        ctx.put("doctorObj", doctorObj);
        List<Doctor> doctorList = doctorDAO.QueryAllDoctorInfo();
        ctx.put("doctorList", doctorList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�ZhuYuan��Ϣ*/
    public String ModifyZhuYuanQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������zhuyuanId��ȡZhuYuan����*/
        ZhuYuan zhuYuan = zhuYuanDAO.GetZhuYuanByZhuyuanId(zhuyuanId);

        List<Patient> patientList = patientDAO.QueryAllPatientInfo();
        ctx.put("patientList", patientList);
        List<Doctor> doctorList = doctorDAO.QueryAllDoctorInfo();
        ctx.put("doctorList", doctorList);
        ctx.put("zhuYuan",  zhuYuan);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�ZhuYuan��Ϣ*/
    public String FrontShowZhuYuanQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������zhuyuanId��ȡZhuYuan����*/
        ZhuYuan zhuYuan = zhuYuanDAO.GetZhuYuanByZhuyuanId(zhuyuanId);

        List<Patient> patientList = patientDAO.QueryAllPatientInfo();
        ctx.put("patientList", patientList);
        List<Doctor> doctorList = doctorDAO.QueryAllDoctorInfo();
        ctx.put("doctorList", doctorList);
        ctx.put("zhuYuan",  zhuYuan);
        return "front_show_view";
    }

    /*�����޸�ZhuYuan��Ϣ*/
    public String ModifyZhuYuan() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Patient patientObj = patientDAO.GetPatientByPatiendId(zhuYuan.getPatientObj().getPatiendId());
            zhuYuan.setPatientObj(patientObj);
            Doctor doctorObj = doctorDAO.GetDoctorByDoctorNo(zhuYuan.getDoctorObj().getDoctorNo());
            zhuYuan.setDoctorObj(doctorObj);
            zhuYuanDAO.UpdateZhuYuan(zhuYuan);
            ctx.put("message",  java.net.URLEncoder.encode("ZhuYuan��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ZhuYuan��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��ZhuYuan��Ϣ*/
    public String DeleteZhuYuan() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            zhuYuanDAO.DeleteZhuYuan(zhuyuanId);
            ctx.put("message",  java.net.URLEncoder.encode("ZhuYuanɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ZhuYuanɾ��ʧ��!"));
            return "error";
        }
    }

}
