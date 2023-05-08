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
import com.chengxusheji.domain.Drug;

@Service @Transactional
public class DrugDAO {

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
    public void AddDrug(Drug drug) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(drug);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Drug> QueryDrugInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Drug drug where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List drugList = q.list();
    	return (ArrayList<Drug>) drugList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Drug> QueryDrugInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Drug drug where 1=1";
    	Query q = s.createQuery(hql);
    	List drugList = q.list();
    	return (ArrayList<Drug>) drugList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Drug> QueryAllDrugInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Drug";
        Query q = s.createQuery(hql);
        List drugList = q.list();
        return (ArrayList<Drug>) drugList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From Drug drug where 1=1";
        Query q = s.createQuery(hql);
        List drugList = q.list();
        recordNumber = drugList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Drug GetDrugByDrugId(int drugId) {
        Session s = factory.getCurrentSession();
        Drug drug = (Drug)s.get(Drug.class, drugId);
        return drug;
    }

    /*更新Drug信息*/
    public void UpdateDrug(Drug drug) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(drug);
    }

    /*删除Drug信息*/
    public void DeleteDrug (int drugId) throws Exception {
        Session s = factory.getCurrentSession();
        Object drug = s.load(Drug.class, drugId);
        s.delete(drug);
    }

}
