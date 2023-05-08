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

@Service @Transactional
public class PatientDAO {

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
    public void AddPatient(Patient patient) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(patient);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Patient> QueryPatientInfo(String name,String birthday,String cardNo,String originPlace,String telephone,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Patient patient where 1=1";
    	if(!name.equals("")) hql = hql + " and patient.name like '%" + name + "%'";
    	if(!birthday.equals("")) hql = hql + " and patient.birthday like '%" + birthday + "%'";
    	if(!cardNo.equals("")) hql = hql + " and patient.cardNo like '%" + cardNo + "%'";
    	if(!originPlace.equals("")) hql = hql + " and patient.originPlace like '%" + originPlace + "%'";
    	if(!telephone.equals("")) hql = hql + " and patient.telephone like '%" + telephone + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List patientList = q.list();
    	return (ArrayList<Patient>) patientList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Patient> QueryPatientInfo(String name,String birthday,String cardNo,String originPlace,String telephone) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Patient patient where 1=1";
    	if(!name.equals("")) hql = hql + " and patient.name like '%" + name + "%'";
    	if(!birthday.equals("")) hql = hql + " and patient.birthday like '%" + birthday + "%'";
    	if(!cardNo.equals("")) hql = hql + " and patient.cardNo like '%" + cardNo + "%'";
    	if(!originPlace.equals("")) hql = hql + " and patient.originPlace like '%" + originPlace + "%'";
    	if(!telephone.equals("")) hql = hql + " and patient.telephone like '%" + telephone + "%'";
    	Query q = s.createQuery(hql);
    	List patientList = q.list();
    	return (ArrayList<Patient>) patientList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Patient> QueryAllPatientInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Patient";
        Query q = s.createQuery(hql);
        List patientList = q.list();
        return (ArrayList<Patient>) patientList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String name,String birthday,String cardNo,String originPlace,String telephone) {
        Session s = factory.getCurrentSession();
        String hql = "From Patient patient where 1=1";
        if(!name.equals("")) hql = hql + " and patient.name like '%" + name + "%'";
        if(!birthday.equals("")) hql = hql + " and patient.birthday like '%" + birthday + "%'";
        if(!cardNo.equals("")) hql = hql + " and patient.cardNo like '%" + cardNo + "%'";
        if(!originPlace.equals("")) hql = hql + " and patient.originPlace like '%" + originPlace + "%'";
        if(!telephone.equals("")) hql = hql + " and patient.telephone like '%" + telephone + "%'";
        Query q = s.createQuery(hql);
        List patientList = q.list();
        recordNumber = patientList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Patient GetPatientByPatiendId(int patiendId) {
        Session s = factory.getCurrentSession();
        Patient patient = (Patient)s.get(Patient.class, patiendId);
        return patient;
    }

    /*����Patient��Ϣ*/
    public void UpdatePatient(Patient patient) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(patient);
    }

    /*ɾ��Patient��Ϣ*/
    public void DeletePatient (int patiendId) throws Exception {
        Session s = factory.getCurrentSession();
        Object patient = s.load(Patient.class, patiendId);
        s.delete(patient);
    }

}
