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
import com.chengxusheji.dao.DrugDAO;
import com.chengxusheji.domain.Drug;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class DrugAction extends BaseAction {

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

    private int drugId;
    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }
    public int getDrugId() {
        return drugId;
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
    @Resource DrugDAO drugDAO;

    /*待操作的Drug对象*/
    private Drug drug;
    public void setDrug(Drug drug) {
        this.drug = drug;
    }
    public Drug getDrug() {
        return this.drug;
    }

    /*跳转到添加Drug视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加Drug信息*/
    @SuppressWarnings("deprecation")
    public String AddDrug() {
        ActionContext ctx = ActionContext.getContext();
        try {
            drugDAO.AddDrug(drug);
            ctx.put("message",  java.net.URLEncoder.encode("Drug添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Drug添加失败!"));
            return "error";
        }
    }

    /*查询Drug信息*/
    public String QueryDrug() {
        if(currentPage == 0) currentPage = 1;
        List<Drug> drugList = drugDAO.QueryDrugInfo(currentPage);
        /*计算总的页数和总的记录数*/
        drugDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = drugDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = drugDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("drugList",  drugList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryDrugOutputToExcel() { 
        List<Drug> drugList = drugDAO.QueryDrugInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Drug信息记录"; 
        String[] headers = { "药品id","药品名称","药品单位","药品单价"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<drugList.size();i++) {
        	Drug drug = drugList.get(i); 
        	dataset.add(new String[]{drug.getDrugId() + "",drug.getDrugName(),drug.getUnit(),drug.getPrice() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"Drug.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Drug信息*/
    public String FrontQueryDrug() {
        if(currentPage == 0) currentPage = 1;
        List<Drug> drugList = drugDAO.QueryDrugInfo(currentPage);
        /*计算总的页数和总的记录数*/
        drugDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = drugDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = drugDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("drugList",  drugList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的Drug信息*/
    public String ModifyDrugQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键drugId获取Drug对象*/
        Drug drug = drugDAO.GetDrugByDrugId(drugId);

        ctx.put("drug",  drug);
        return "modify_view";
    }

    /*查询要修改的Drug信息*/
    public String FrontShowDrugQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键drugId获取Drug对象*/
        Drug drug = drugDAO.GetDrugByDrugId(drugId);

        ctx.put("drug",  drug);
        return "front_show_view";
    }

    /*更新修改Drug信息*/
    public String ModifyDrug() {
        ActionContext ctx = ActionContext.getContext();
        try {
            drugDAO.UpdateDrug(drug);
            ctx.put("message",  java.net.URLEncoder.encode("Drug信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Drug信息更新失败!"));
            return "error";
       }
   }

    /*删除Drug信息*/
    public String DeleteDrug() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            drugDAO.DeleteDrug(drugId);
            ctx.put("message",  java.net.URLEncoder.encode("Drug删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Drug删除失败!"));
            return "error";
        }
    }

}
