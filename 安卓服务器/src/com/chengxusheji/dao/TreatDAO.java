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
import com.chengxusheji.domain.Patient;
import com.chengxusheji.domain.Doctor;
import com.chengxusheji.domain.Treat;

@Service @Transactional
public class TreatDAO {

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
    public void AddTreat(Treat treat) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(treat);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Treat> QueryTreatInfo(String treatName,Patient patientObj,Doctor doctorObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Treat treat where 1=1";
    	if(!treatName.equals("")) hql = hql + " and treat.treatName like '%" + treatName + "%'";
    	if(null != patientObj && patientObj.getPatiendId()!=0) hql += " and treat.patientObj.patiendId=" + patientObj.getPatiendId();
    	if(null != doctorObj && !doctorObj.getDoctorNo().equals("")) hql += " and treat.doctorObj.doctorNo='" + doctorObj.getDoctorNo() + "'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List treatList = q.list();
    	return (ArrayList<Treat>) treatList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Treat> QueryTreatInfo(String treatName,Patient patientObj,Doctor doctorObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Treat treat where 1=1";
    	if(!treatName.equals("")) hql = hql + " and treat.treatName like '%" + treatName + "%'";
    	if(null != patientObj && patientObj.getPatiendId()!=0) hql += " and treat.patientObj.patiendId=" + patientObj.getPatiendId();
    	if(null != doctorObj && !doctorObj.getDoctorNo().equals("")) hql += " and treat.doctorObj.doctorNo='" + doctorObj.getDoctorNo() + "'";
    	Query q = s.createQuery(hql);
    	List treatList = q.list();
    	return (ArrayList<Treat>) treatList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Treat> QueryAllTreatInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Treat";
        Query q = s.createQuery(hql);
        List treatList = q.list();
        return (ArrayList<Treat>) treatList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String treatName,Patient patientObj,Doctor doctorObj) {
        Session s = factory.getCurrentSession();
        String hql = "From Treat treat where 1=1";
        if(!treatName.equals("")) hql = hql + " and treat.treatName like '%" + treatName + "%'";
        if(null != patientObj && patientObj.getPatiendId()!=0) hql += " and treat.patientObj.patiendId=" + patientObj.getPatiendId();
        if(null != doctorObj && !doctorObj.getDoctorNo().equals("")) hql += " and treat.doctorObj.doctorNo='" + doctorObj.getDoctorNo() + "'";
        Query q = s.createQuery(hql);
        List treatList = q.list();
        recordNumber = treatList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Treat GetTreatByTreatId(int treatId) {
        Session s = factory.getCurrentSession();
        Treat treat = (Treat)s.get(Treat.class, treatId);
        return treat;
    }

    /*����Treat��Ϣ*/
    public void UpdateTreat(Treat treat) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(treat);
    }

    /*ɾ��Treat��Ϣ*/
    public void DeleteTreat (int treatId) throws Exception {
        Session s = factory.getCurrentSession();
        Object treat = s.load(Treat.class, treatId);
        s.delete(treat);
    }

}
