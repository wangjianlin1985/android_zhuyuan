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

    /*界面层需要查询的属性: 病人*/
    private Patient patientObj;
    public void setPatientObj(Patient patientObj) {
        this.patientObj = patientObj;
    }
    public Patient getPatientObj() {
        return this.patientObj;
    }

    /*界面层需要查询的属性: 住院日期*/
    private String inDate;
    public void setInDate(String inDate) {
        this.inDate = inDate;
    }
    public String getInDate() {
        return this.inDate;
    }

    /*界面层需要查询的属性: 床位号*/
    private String bedNum;
    public void setBedNum(String bedNum) {
        this.bedNum = bedNum;
    }
    public String getBedNum() {
        return this.bedNum;
    }

    /*界面层需要查询的属性: 负责医生*/
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

    private int zhuyuanId;
    public void setZhuyuanId(int zhuyuanId) {
        this.zhuyuanId = zhuyuanId;
    }
    public int getZhuyuanId() {
        return zhuyuanId;
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
    @Resource ZhuYuanDAO zhuYuanDAO;

    /*待操作的ZhuYuan对象*/
    private ZhuYuan zhuYuan;
    public void setZhuYuan(ZhuYuan zhuYuan) {
        this.zhuYuan = zhuYuan;
    }
    public ZhuYuan getZhuYuan() {
        return this.zhuYuan;
    }

    /*跳转到添加ZhuYuan视图*/
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

    /*添加ZhuYuan信息*/
    @SuppressWarnings("deprecation")
    public String AddZhuYuan() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Patient patientObj = patientDAO.GetPatientByPatiendId(zhuYuan.getPatientObj().getPatiendId());
            zhuYuan.setPatientObj(patientObj);
            Doctor doctorObj = doctorDAO.GetDoctorByDoctorNo(zhuYuan.getDoctorObj().getDoctorNo());
            zhuYuan.setDoctorObj(doctorObj);
            zhuYuanDAO.AddZhuYuan(zhuYuan);
            ctx.put("message",  java.net.URLEncoder.encode("ZhuYuan添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ZhuYuan添加失败!"));
            return "error";
        }
    }

    /*查询ZhuYuan信息*/
    public String QueryZhuYuan() {
        if(currentPage == 0) currentPage = 1;
        if(inDate == null) inDate = "";
        if(bedNum == null) bedNum = "";
        List<ZhuYuan> zhuYuanList = zhuYuanDAO.QueryZhuYuanInfo(patientObj, inDate, bedNum, doctorObj, currentPage);
        /*计算总的页数和总的记录数*/
        zhuYuanDAO.CalculateTotalPageAndRecordNumber(patientObj, inDate, bedNum, doctorObj);
        /*获取到总的页码数目*/
        totalPage = zhuYuanDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryZhuYuanOutputToExcel() { 
        if(inDate == null) inDate = "";
        if(bedNum == null) bedNum = "";
        List<ZhuYuan> zhuYuanList = zhuYuanDAO.QueryZhuYuanInfo(patientObj,inDate,bedNum,doctorObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ZhuYuan信息记录"; 
        String[] headers = { "住院id","病人","年龄","住院日期","入住天数","床位号","负责医生"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"ZhuYuan.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询ZhuYuan信息*/
    public String FrontQueryZhuYuan() {
        if(currentPage == 0) currentPage = 1;
        if(inDate == null) inDate = "";
        if(bedNum == null) bedNum = "";
        List<ZhuYuan> zhuYuanList = zhuYuanDAO.QueryZhuYuanInfo(patientObj, inDate, bedNum, doctorObj, currentPage);
        /*计算总的页数和总的记录数*/
        zhuYuanDAO.CalculateTotalPageAndRecordNumber(patientObj, inDate, bedNum, doctorObj);
        /*获取到总的页码数目*/
        totalPage = zhuYuanDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的ZhuYuan信息*/
    public String ModifyZhuYuanQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键zhuyuanId获取ZhuYuan对象*/
        ZhuYuan zhuYuan = zhuYuanDAO.GetZhuYuanByZhuyuanId(zhuyuanId);

        List<Patient> patientList = patientDAO.QueryAllPatientInfo();
        ctx.put("patientList", patientList);
        List<Doctor> doctorList = doctorDAO.QueryAllDoctorInfo();
        ctx.put("doctorList", doctorList);
        ctx.put("zhuYuan",  zhuYuan);
        return "modify_view";
    }

    /*查询要修改的ZhuYuan信息*/
    public String FrontShowZhuYuanQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键zhuyuanId获取ZhuYuan对象*/
        ZhuYuan zhuYuan = zhuYuanDAO.GetZhuYuanByZhuyuanId(zhuyuanId);

        List<Patient> patientList = patientDAO.QueryAllPatientInfo();
        ctx.put("patientList", patientList);
        List<Doctor> doctorList = doctorDAO.QueryAllDoctorInfo();
        ctx.put("doctorList", doctorList);
        ctx.put("zhuYuan",  zhuYuan);
        return "front_show_view";
    }

    /*更新修改ZhuYuan信息*/
    public String ModifyZhuYuan() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Patient patientObj = patientDAO.GetPatientByPatiendId(zhuYuan.getPatientObj().getPatiendId());
            zhuYuan.setPatientObj(patientObj);
            Doctor doctorObj = doctorDAO.GetDoctorByDoctorNo(zhuYuan.getDoctorObj().getDoctorNo());
            zhuYuan.setDoctorObj(doctorObj);
            zhuYuanDAO.UpdateZhuYuan(zhuYuan);
            ctx.put("message",  java.net.URLEncoder.encode("ZhuYuan信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ZhuYuan信息更新失败!"));
            return "error";
       }
   }

    /*删除ZhuYuan信息*/
    public String DeleteZhuYuan() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            zhuYuanDAO.DeleteZhuYuan(zhuyuanId);
            ctx.put("message",  java.net.URLEncoder.encode("ZhuYuan删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ZhuYuan删除失败!"));
            return "error";
        }
    }

}
