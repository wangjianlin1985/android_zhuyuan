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

    /*�������Ҫ��ѯ������: ��������*/
    private Treat treatObj;
    public void setTreatObj(Treat treatObj) {
        this.treatObj = treatObj;
    }
    public Treat getTreatObj() {
        return this.treatObj;
    }

    /*�������Ҫ��ѯ������: ����ҩƷ*/
    private Drug drugObj;
    public void setDrugObj(Drug drugObj) {
        this.drugObj = drugObj;
    }
    public Drug getDrugObj() {
        return this.drugObj;
    }

    /*�������Ҫ��ѯ������: ��ҩʱ��*/
    private String useTime;
    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }
    public String getUseTime() {
        return this.useTime;
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

    private int drugUseId;
    public void setDrugUseId(int drugUseId) {
        this.drugUseId = drugUseId;
    }
    public int getDrugUseId() {
        return drugUseId;
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
    @Resource TreatDAO treatDAO;
    @Resource DrugDAO drugDAO;
    @Resource DrugUseDAO drugUseDAO;

    /*��������DrugUse����*/
    private DrugUse drugUse;
    public void setDrugUse(DrugUse drugUse) {
        this.drugUse = drugUse;
    }
    public DrugUse getDrugUse() {
        return this.drugUse;
    }

    /*��ת�����DrugUse��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Treat��Ϣ*/
        List<Treat> treatList = treatDAO.QueryAllTreatInfo();
        ctx.put("treatList", treatList);
        /*��ѯ���е�Drug��Ϣ*/
        List<Drug> drugList = drugDAO.QueryAllDrugInfo();
        ctx.put("drugList", drugList);
        return "add_view";
    }

    /*���DrugUse��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddDrugUse() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Treat treatObj = treatDAO.GetTreatByTreatId(drugUse.getTreatObj().getTreatId());
            drugUse.setTreatObj(treatObj);
            Drug drugObj = drugDAO.GetDrugByDrugId(drugUse.getDrugObj().getDrugId());
            drugUse.setDrugObj(drugObj);
            drugUseDAO.AddDrugUse(drugUse);
            ctx.put("message",  java.net.URLEncoder.encode("DrugUse��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("DrugUse���ʧ��!"));
            return "error";
        }
    }

    /*��ѯDrugUse��Ϣ*/
    public String QueryDrugUse() {
        if(currentPage == 0) currentPage = 1;
        if(useTime == null) useTime = "";
        List<DrugUse> drugUseList = drugUseDAO.QueryDrugUseInfo(treatObj, drugObj, useTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        drugUseDAO.CalculateTotalPageAndRecordNumber(treatObj, drugObj, useTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = drugUseDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryDrugUseOutputToExcel() { 
        if(useTime == null) useTime = "";
        List<DrugUse> drugUseList = drugUseDAO.QueryDrugUseInfo(treatObj,drugObj,useTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "DrugUse��Ϣ��¼"; 
        String[] headers = { "��ҩid","��������","����ҩƷ","��ҩ����","ҩƷ����","��ҩʱ��"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"DrugUse.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯDrugUse��Ϣ*/
    public String FrontQueryDrugUse() {
        if(currentPage == 0) currentPage = 1;
        if(useTime == null) useTime = "";
        List<DrugUse> drugUseList = drugUseDAO.QueryDrugUseInfo(treatObj, drugObj, useTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        drugUseDAO.CalculateTotalPageAndRecordNumber(treatObj, drugObj, useTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = drugUseDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�DrugUse��Ϣ*/
    public String ModifyDrugUseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������drugUseId��ȡDrugUse����*/
        DrugUse drugUse = drugUseDAO.GetDrugUseByDrugUseId(drugUseId);

        List<Treat> treatList = treatDAO.QueryAllTreatInfo();
        ctx.put("treatList", treatList);
        List<Drug> drugList = drugDAO.QueryAllDrugInfo();
        ctx.put("drugList", drugList);
        ctx.put("drugUse",  drugUse);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�DrugUse��Ϣ*/
    public String FrontShowDrugUseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������drugUseId��ȡDrugUse����*/
        DrugUse drugUse = drugUseDAO.GetDrugUseByDrugUseId(drugUseId);

        List<Treat> treatList = treatDAO.QueryAllTreatInfo();
        ctx.put("treatList", treatList);
        List<Drug> drugList = drugDAO.QueryAllDrugInfo();
        ctx.put("drugList", drugList);
        ctx.put("drugUse",  drugUse);
        return "front_show_view";
    }

    /*�����޸�DrugUse��Ϣ*/
    public String ModifyDrugUse() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Treat treatObj = treatDAO.GetTreatByTreatId(drugUse.getTreatObj().getTreatId());
            drugUse.setTreatObj(treatObj);
            Drug drugObj = drugDAO.GetDrugByDrugId(drugUse.getDrugObj().getDrugId());
            drugUse.setDrugObj(drugObj);
            drugUseDAO.UpdateDrugUse(drugUse);
            ctx.put("message",  java.net.URLEncoder.encode("DrugUse��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("DrugUse��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��DrugUse��Ϣ*/
    public String DeleteDrugUse() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            drugUseDAO.DeleteDrugUse(drugUseId);
            ctx.put("message",  java.net.URLEncoder.encode("DrugUseɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("DrugUseɾ��ʧ��!"));
            return "error";
        }
    }

}
