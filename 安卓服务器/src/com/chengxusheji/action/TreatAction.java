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

    /*界面层需要查询的属性: 治疗名称*/
    private String treatName;
    public void setTreatName(String treatName) {
        this.treatName = treatName;
    }
    public String getTreatName() {
        return this.treatName;
    }

    /*界面层需要查询的属性: 病人*/
    private Patient patientObj;
    public void setPatientObj(Patient patientObj) {
        this.patientObj = patientObj;
    }
    public Patient getPatientObj() {
        return this.patientObj;
    }

    /*界面层需要查询的属性: 主治医生*/
    private Doctor doctorObj;
    public void setDoctorObj(Doctor doctorObj) {
        this.doctorObj = doctorObj;
    }
    public Doctor getDoctorObj() {
        return this.doctorObj;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
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

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource PatientDAO patientDAO;
    @Resource DoctorDAO doctorDAO;
    @Resource TreatDAO treatDAO;

    /*待操作的Treat对象*/
    private Treat treat;
    public void setTreat(Treat treat) {
        this.treat = treat;
    }
    public Treat getTreat() {
        return this.treat;
    }

    /*跳转到添加Treat视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Patient信息*/
        List<Patient> patientList = patientDAO.QueryAllPatientInfo();
        ctx.put("patientList", patientList);
        /*查询所有的Doctor信息*/
        List<Doctor> doctorList = doctorDAO.QueryAllDoctorInfo();
        ctx.put("doctorList", doctorList);
        return "add_view";
    }

    /*添加Treat信息*/
    @SuppressWarnings("deprecation")
    public String AddTreat() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Patient patientObj = patientDAO.GetPatientByPatiendId(treat.getPatientObj().getPatiendId());
            treat.setPatientObj(patientObj);
            Doctor doctorObj = doctorDAO.GetDoctorByDoctorNo(treat.getDoctorObj().getDoctorNo());
            treat.setDoctorObj(doctorObj);
            treatDAO.AddTreat(treat);
            ctx.put("message",  java.net.URLEncoder.encode("Treat添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Treat添加失败!"));
            return "error";
        }
    }

    /*查询Treat信息*/
    public String QueryTreat() {
        if(currentPage == 0) currentPage = 1;
        if(treatName == null) treatName = "";
        List<Treat> treatList = treatDAO.QueryTreatInfo(treatName, patientObj, doctorObj, currentPage);
        /*计算总的页数和总的记录数*/
        treatDAO.CalculateTotalPageAndRecordNumber(treatName, patientObj, doctorObj);
        /*获取到总的页码数目*/
        totalPage = treatDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryTreatOutputToExcel() { 
        if(treatName == null) treatName = "";
        List<Treat> treatList = treatDAO.QueryTreatInfo(treatName,patientObj,doctorObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Treat信息记录"; 
        String[] headers = { "治疗id","治疗名称","病人","诊断情况","治疗记录","治疗结果","主治医生","治疗开始时间","疗程时间"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Treat.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
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
    /*前台查询Treat信息*/
    public String FrontQueryTreat() {
        if(currentPage == 0) currentPage = 1;
        if(treatName == null) treatName = "";
        List<Treat> treatList = treatDAO.QueryTreatInfo(treatName, patientObj, doctorObj, currentPage);
        /*计算总的页数和总的记录数*/
        treatDAO.CalculateTotalPageAndRecordNumber(treatName, patientObj, doctorObj);
        /*获取到总的页码数目*/
        totalPage = treatDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Treat信息*/
    public String ModifyTreatQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键treatId获取Treat对象*/
        Treat treat = treatDAO.GetTreatByTreatId(treatId);

        List<Patient> patientList = patientDAO.QueryAllPatientInfo();
        ctx.put("patientList", patientList);
        List<Doctor> doctorList = doctorDAO.QueryAllDoctorInfo();
        ctx.put("doctorList", doctorList);
        ctx.put("treat",  treat);
        return "modify_view";
    }

    /*查询要修改的Treat信息*/
    public String FrontShowTreatQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键treatId获取Treat对象*/
        Treat treat = treatDAO.GetTreatByTreatId(treatId);

        List<Patient> patientList = patientDAO.QueryAllPatientInfo();
        ctx.put("patientList", patientList);
        List<Doctor> doctorList = doctorDAO.QueryAllDoctorInfo();
        ctx.put("doctorList", doctorList);
        ctx.put("treat",  treat);
        return "front_show_view";
    }

    /*更新修改Treat信息*/
    public String ModifyTreat() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Patient patientObj = patientDAO.GetPatientByPatiendId(treat.getPatientObj().getPatiendId());
            treat.setPatientObj(patientObj);
            Doctor doctorObj = doctorDAO.GetDoctorByDoctorNo(treat.getDoctorObj().getDoctorNo());
            treat.setDoctorObj(doctorObj);
            treatDAO.UpdateTreat(treat);
            ctx.put("message",  java.net.URLEncoder.encode("Treat信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Treat信息更新失败!"));
            return "error";
       }
   }

    /*删除Treat信息*/
    public String DeleteTreat() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            treatDAO.DeleteTreat(treatId);
            ctx.put("message",  java.net.URLEncoder.encode("Treat删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Treat删除失败!"));
            return "error";
        }
    }

}
