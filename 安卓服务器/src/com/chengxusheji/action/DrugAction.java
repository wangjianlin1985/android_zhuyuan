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

    private int drugId;
    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }
    public int getDrugId() {
        return drugId;
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
    @Resource DrugDAO drugDAO;

    /*��������Drug����*/
    private Drug drug;
    public void setDrug(Drug drug) {
        this.drug = drug;
    }
    public Drug getDrug() {
        return this.drug;
    }

    /*��ת�����Drug��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���Drug��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddDrug() {
        ActionContext ctx = ActionContext.getContext();
        try {
            drugDAO.AddDrug(drug);
            ctx.put("message",  java.net.URLEncoder.encode("Drug��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Drug���ʧ��!"));
            return "error";
        }
    }

    /*��ѯDrug��Ϣ*/
    public String QueryDrug() {
        if(currentPage == 0) currentPage = 1;
        List<Drug> drugList = drugDAO.QueryDrugInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        drugDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = drugDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = drugDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("drugList",  drugList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryDrugOutputToExcel() { 
        List<Drug> drugList = drugDAO.QueryDrugInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Drug��Ϣ��¼"; 
        String[] headers = { "ҩƷid","ҩƷ����","ҩƷ��λ","ҩƷ����"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Drug.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯDrug��Ϣ*/
    public String FrontQueryDrug() {
        if(currentPage == 0) currentPage = 1;
        List<Drug> drugList = drugDAO.QueryDrugInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        drugDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = drugDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = drugDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("drugList",  drugList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Drug��Ϣ*/
    public String ModifyDrugQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������drugId��ȡDrug����*/
        Drug drug = drugDAO.GetDrugByDrugId(drugId);

        ctx.put("drug",  drug);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Drug��Ϣ*/
    public String FrontShowDrugQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������drugId��ȡDrug����*/
        Drug drug = drugDAO.GetDrugByDrugId(drugId);

        ctx.put("drug",  drug);
        return "front_show_view";
    }

    /*�����޸�Drug��Ϣ*/
    public String ModifyDrug() {
        ActionContext ctx = ActionContext.getContext();
        try {
            drugDAO.UpdateDrug(drug);
            ctx.put("message",  java.net.URLEncoder.encode("Drug��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Drug��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Drug��Ϣ*/
    public String DeleteDrug() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            drugDAO.DeleteDrug(drugId);
            ctx.put("message",  java.net.URLEncoder.encode("Drugɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Drugɾ��ʧ��!"));
            return "error";
        }
    }

}
