package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.Treat;
import com.chengxusheji.domain.Drug;
import com.chengxusheji.domain.DrugUse;

@Service @Transactional
public class DrugUseDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddDrugUse(DrugUse drugUse) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(drugUse);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<DrugUse> QueryDrugUseInfo(Treat treatObj,Drug drugObj,String useTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From DrugUse drugUse where 1=1";
    	if(null != treatObj && treatObj.getTreatId()!=0) hql += " and drugUse.treatObj.treatId=" + treatObj.getTreatId();
    	if(null != drugObj && drugObj.getDrugId()!=0) hql += " and drugUse.drugObj.drugId=" + drugObj.getDrugId();
    	if(!useTime.equals("")) hql = hql + " and drugUse.useTime like '%" + useTime + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List drugUseList = q.list();
    	return (ArrayList<DrugUse>) drugUseList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<DrugUse> QueryDrugUseInfo(Treat treatObj,Drug drugObj,String useTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From DrugUse drugUse where 1=1";
    	if(null != treatObj && treatObj.getTreatId()!=0) hql += " and drugUse.treatObj.treatId=" + treatObj.getTreatId();
    	if(null != drugObj && drugObj.getDrugId()!=0) hql += " and drugUse.drugObj.drugId=" + drugObj.getDrugId();
    	if(!useTime.equals("")) hql = hql + " and drugUse.useTime like '%" + useTime + "%'";
    	Query q = s.createQuery(hql);
    	List drugUseList = q.list();
    	return (ArrayList<DrugUse>) drugUseList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<DrugUse> QueryAllDrugUseInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From DrugUse";
        Query q = s.createQuery(hql);
        List drugUseList = q.list();
        return (ArrayList<DrugUse>) drugUseList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Treat treatObj,Drug drugObj,String useTime) {
        Session s = factory.getCurrentSession();
        String hql = "From DrugUse drugUse where 1=1";
        if(null != treatObj && treatObj.getTreatId()!=0) hql += " and drugUse.treatObj.treatId=" + treatObj.getTreatId();
        if(null != drugObj && drugObj.getDrugId()!=0) hql += " and drugUse.drugObj.drugId=" + drugObj.getDrugId();
        if(!useTime.equals("")) hql = hql + " and drugUse.useTime like '%" + useTime + "%'";
        Query q = s.createQuery(hql);
        List drugUseList = q.list();
        recordNumber = drugUseList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public DrugUse GetDrugUseByDrugUseId(int drugUseId) {
        Session s = factory.getCurrentSession();
        DrugUse drugUse = (DrugUse)s.get(DrugUse.class, drugUseId);
        return drugUse;
    }

    /*更新DrugUse信息*/
    public void UpdateDrugUse(DrugUse drugUse) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(drugUse);
    }

    /*删除DrugUse信息*/
    public void DeleteDrugUse (int drugUseId) throws Exception {
        Session s = factory.getCurrentSession();
        Object drugUse = s.load(DrugUse.class, drugUseId);
        s.delete(drugUse);
    }

}
