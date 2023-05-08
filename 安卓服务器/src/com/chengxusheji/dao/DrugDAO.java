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
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddDrug(Drug drug) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(drug);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Drug> QueryDrugInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Drug drug where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Drug GetDrugByDrugId(int drugId) {
        Session s = factory.getCurrentSession();
        Drug drug = (Drug)s.get(Drug.class, drugId);
        return drug;
    }

    /*����Drug��Ϣ*/
    public void UpdateDrug(Drug drug) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(drug);
    }

    /*ɾ��Drug��Ϣ*/
    public void DeleteDrug (int drugId) throws Exception {
        Session s = factory.getCurrentSession();
        Object drug = s.load(Drug.class, drugId);
        s.delete(drug);
    }

}
