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

    /*界面层需要查询的属性: 姓名*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*界面层需要查询的属性: 出生日期*/
    private String birthday;
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getBirthday() {
        return this.birthday;
    }

    /*界面层需要查询的属性: 身份证号*/
    private String cardNo;
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    public String getCardNo() {
        return this.cardNo;
    }

    /*界面层需要查询的属性: 籍贯*/
    private String originPlace;
    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }
    public String getOriginPlace() {
        return this.originPlace;
    }

    /*界面层需要查询的属性: 联系电话*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
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

    private int patiendId;
    public void setPatiendId(int patiendId) {
        this.patiendId = patiendId;
    }
    public int getPatiendId() {
        return patiendId;
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

    /*待操作的Patient对象*/
    private Patient patient;
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public Patient getPatient() {
        return this.patient;
    }

    /*跳转到添加Patient视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加Patient信息*/
    @SuppressWarnings("deprecation")
    public String AddPatient() {
        ActionContext ctx = ActionContext.getContext();
        try {
            patientDAO.AddPatient(patient);
            ctx.put("message",  java.net.URLEncoder.encode("Patient添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Patient添加失败!"));
            return "error";
        }
    }

    /*查询Patient信息*/
    public String QueryPatient() {
        if(currentPage == 0) currentPage = 1;
        if(name == null) name = "";
        if(birthday == null) birthday = "";
        if(cardNo == null) cardNo = "";
        if(originPlace == null) originPlace = "";
        if(telephone == null) telephone = "";
        List<Patient> patientList = patientDAO.QueryPatientInfo(name, birthday, cardNo, originPlace, telephone, currentPage);
        /*计算总的页数和总的记录数*/
        patientDAO.CalculateTotalPageAndRecordNumber(name, birthday, cardNo, originPlace, telephone);
        /*获取到总的页码数目*/
        totalPage = patientDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryPatientOutputToExcel() { 
        if(name == null) name = "";
        if(birthday == null) birthday = "";
        if(cardNo == null) cardNo = "";
        if(originPlace == null) originPlace = "";
        if(telephone == null) telephone = "";
        List<Patient> patientList = patientDAO.QueryPatientInfo(name,birthday,cardNo,originPlace,telephone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Patient信息记录"; 
        String[] headers = { "病人id","姓名","性别","出生日期","身份证号","籍贯","联系电话"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Patient.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Patient信息*/
    public String FrontQueryPatient() {
        if(currentPage == 0) currentPage = 1;
        if(name == null) name = "";
        if(birthday == null) birthday = "";
        if(cardNo == null) cardNo = "";
        if(originPlace == null) originPlace = "";
        if(telephone == null) telephone = "";
        List<Patient> patientList = patientDAO.QueryPatientInfo(name, birthday, cardNo, originPlace, telephone, currentPage);
        /*计算总的页数和总的记录数*/
        patientDAO.CalculateTotalPageAndRecordNumber(name, birthday, cardNo, originPlace, telephone);
        /*获取到总的页码数目*/
        totalPage = patientDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Patient信息*/
    public String ModifyPatientQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键patiendId获取Patient对象*/
        Patient patient = patientDAO.GetPatientByPatiendId(patiendId);

        ctx.put("patient",  patient);
        return "modify_view";
    }

    /*查询要修改的Patient信息*/
    public String FrontShowPatientQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键patiendId获取Patient对象*/
        Patient patient = patientDAO.GetPatientByPatiendId(patiendId);

        ctx.put("patient",  patient);
        return "front_show_view";
    }

    /*更新修改Patient信息*/
    public String ModifyPatient() {
        ActionContext ctx = ActionContext.getContext();
        try {
            patientDAO.UpdatePatient(patient);
            ctx.put("message",  java.net.URLEncoder.encode("Patient信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Patient信息更新失败!"));
            return "error";
       }
   }

    /*删除Patient信息*/
    public String DeletePatient() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            patientDAO.DeletePatient(patiendId);
            ctx.put("message",  java.net.URLEncoder.encode("Patient删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Patient删除失败!"));
            return "error";
        }
    }

}
