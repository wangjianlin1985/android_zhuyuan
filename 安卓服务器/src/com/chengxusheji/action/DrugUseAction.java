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
import com.chengxusheji.dao.DrugUseDAO;
import com.chengxusheji.domain.DrugUse;
import com.chengxusheji.dao.TreatDAO;
import com.chengxusheji.domain.Treat;
import com.chengxusheji.dao.DrugDAO;
import com.chengxusheji.domain.Drug;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class DrugUseAction extends BaseAction {

    /*界面层需要查询的属性: 治疗名称*/
    private Treat treatObj;
    public void setTreatObj(Treat treatObj) {
        this.treatObj = treatObj;
    }
    public Treat getTreatObj() {
        return this.treatObj;
    }

    /*界面层需要查询的属性: 所用药品*/
    private Drug drugObj;
    public void setDrugObj(Drug drugObj) {
        this.drugObj = drugObj;
    }
    public Drug getDrugObj() {
        return this.drugObj;
    }

    /*界面层需要查询的属性: 用药时间*/
    private String useTime;
    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }
    public String getUseTime() {
        return this.useTime;
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

    private int drugUseId;
    public void setDrugUseId(int drugUseId) {
        this.drugUseId = drugUseId;
    }
    public int getDrugUseId() {
        return drugUseId;
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
    @Resource TreatDAO treatDAO;
    @Resource DrugDAO drugDAO;
    @Resource DrugUseDAO drugUseDAO;

    /*待操作的DrugUse对象*/
    private DrugUse drugUse;
    public void setDrugUse(DrugUse drugUse) {
        this.drugUse = drugUse;
    }
    public DrugUse getDrugUse() {
        return this.drugUse;
    }

    /*跳转到添加DrugUse视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Treat信息*/
        List<Treat> treatList = treatDAO.QueryAllTreatInfo();
        ctx.put("treatList", treatList);
        /*查询所有的Drug信息*/
        List<Drug> drugList = drugDAO.QueryAllDrugInfo();
        ctx.put("drugList", drugList);
        return "add_view";
    }

    /*添加DrugUse信息*/
    @SuppressWarnings("deprecation")
    public String AddDrugUse() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Treat treatObj = treatDAO.GetTreatByTreatId(drugUse.getTreatObj().getTreatId());
            drugUse.setTreatObj(treatObj);
            Drug drugObj = drugDAO.GetDrugByDrugId(drugUse.getDrugObj().getDrugId());
            drugUse.setDrugObj(drugObj);
            drugUseDAO.AddDrugUse(drugUse);
            ctx.put("message",  java.net.URLEncoder.encode("DrugUse添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("DrugUse添加失败!"));
            return "error";
        }
    }

    /*查询DrugUse信息*/
    public String QueryDrugUse() {
        if(currentPage == 0) currentPage = 1;
        if(useTime == null) useTime = "";
        List<DrugUse> drugUseList = drugUseDAO.QueryDrugUseInfo(treatObj, drugObj, useTime, currentPage);
        /*计算总的页数和总的记录数*/
        drugUseDAO.CalculateTotalPageAndRecordNumber(treatObj, drugObj, useTime);
        /*获取到总的页码数目*/
        totalPage = drugUseDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = drugUseDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("drugUseList",  drugUseList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("treatObj", treatObj);
        List<Treat> treatList = treatDAO.QueryAllTreatInfo();
        ctx.put("treatList", treatList);
        ctx.put("drugObj", drugObj);
        List<Drug> drugList = drugDAO.QueryAllDrugInfo();
        ctx.put("drugList", drugList);
        ctx.put("useTime", useTime);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryDrugUseOutputToExcel() { 
        if(useTime == null) useTime = "";
        List<DrugUse> drugUseList = drugUseDAO.QueryDrugUseInfo(treatObj,drugObj,useTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "DrugUse信息记录"; 
        String[] headers = { "用药id","治疗名称","所用药品","用药数量","药品费用","用药时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<drugUseList.size();i++) {
        	DrugUse drugUse = drugUseList.get(i); 
        	dataset.add(new String[]{drugUse.getDrugUseId() + "",drugUse.getTreatObj().getTreatName(),
drugUse.getDrugObj().getDrugName(),
drugUse.getDrugCount() + "",drugUse.getDrugMoney() + "",drugUse.getUseTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"DrugUse.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询DrugUse信息*/
    public String FrontQueryDrugUse() {
        if(currentPage == 0) currentPage = 1;
        if(useTime == null) useTime = "";
        List<DrugUse> drugUseList = drugUseDAO.QueryDrugUseInfo(treatObj, drugObj, useTime, currentPage);
        /*计算总的页数和总的记录数*/
        drugUseDAO.CalculateTotalPageAndRecordNumber(treatObj, drugObj, useTime);
        /*获取到总的页码数目*/
        totalPage = drugUseDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = drugUseDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("drugUseList",  drugUseList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("treatObj", treatObj);
        List<Treat> treatList = treatDAO.QueryAllTreatInfo();
        ctx.put("treatList", treatList);
        ctx.put("drugObj", drugObj);
        List<Drug> drugList = drugDAO.QueryAllDrugInfo();
        ctx.put("drugList", drugList);
        ctx.put("useTime", useTime);
        return "front_query_view";
    }

    /*查询要修改的DrugUse信息*/
    public String ModifyDrugUseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键drugUseId获取DrugUse对象*/
        DrugUse drugUse = drugUseDAO.GetDrugUseByDrugUseId(drugUseId);

        List<Treat> treatList = treatDAO.QueryAllTreatInfo();
        ctx.put("treatList", treatList);
        List<Drug> drugList = drugDAO.QueryAllDrugInfo();
        ctx.put("drugList", drugList);
        ctx.put("drugUse",  drugUse);
        return "modify_view";
    }

    /*查询要修改的DrugUse信息*/
    public String FrontShowDrugUseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键drugUseId获取DrugUse对象*/
        DrugUse drugUse = drugUseDAO.GetDrugUseByDrugUseId(drugUseId);

        List<Treat> treatList = treatDAO.QueryAllTreatInfo();
        ctx.put("treatList", treatList);
        List<Drug> drugList = drugDAO.QueryAllDrugInfo();
        ctx.put("drugList", drugList);
        ctx.put("drugUse",  drugUse);
        return "front_show_view";
    }

    /*更新修改DrugUse信息*/
    public String ModifyDrugUse() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Treat treatObj = treatDAO.GetTreatByTreatId(drugUse.getTreatObj().getTreatId());
            drugUse.setTreatObj(treatObj);
            Drug drugObj = drugDAO.GetDrugByDrugId(drugUse.getDrugObj().getDrugId());
            drugUse.setDrugObj(drugObj);
            drugUseDAO.UpdateDrugUse(drugUse);
            ctx.put("message",  java.net.URLEncoder.encode("DrugUse信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("DrugUse信息更新失败!"));
            return "error";
       }
   }

    /*删除DrugUse信息*/
    public String DeleteDrugUse() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            drugUseDAO.DeleteDrugUse(drugUseId);
            ctx.put("message",  java.net.URLEncoder.encode("DrugUse删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("DrugUse删除失败!"));
            return "error";
        }
    }

}
