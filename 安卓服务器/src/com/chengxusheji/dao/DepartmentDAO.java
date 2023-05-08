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
import com.chengxusheji.domain.Department;

@Service @Transactional
public class DepartmentDAO {

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
    public void AddDepartment(Department department) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(department);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Department> QueryDepartmentInfo(String bornDate,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Department department where 1=1";
    	if(!bornDate.equals("")) hql = hql + " and department.bornDate like '%" + bornDate + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List departmentList = q.list();
    	return (ArrayList<Department>) departmentList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Department> QueryDepartmentInfo(String bornDate) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Department department where 1=1";
    	if(!bornDate.equals("")) hql = hql + " and department.bornDate like '%" + bornDate + "%'";
    	Query q = s.createQuery(hql);
    	List departmentList = q.list();
    	return (ArrayList<Department>) departmentList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Department> QueryAllDepartmentInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Department";
        Query q = s.createQuery(hql);
        List departmentList = q.list();
        return (ArrayList<Department>) departmentList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String bornDate) {
        Session s = factory.getCurrentSession();
        String hql = "From Department department where 1=1";
        if(!bornDate.equals("")) hql = hql + " and department.bornDate like '%" + bornDate + "%'";
        Query q = s.createQuery(hql);
        List departmentList = q.list();
        recordNumber = departmentList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Department GetDepartmentByDepartmentNo(String departmentNo) {
        Session s = factory.getCurrentSession();
        Department department = (Department)s.get(Department.class, departmentNo);
        return department;
    }

    /*����Department��Ϣ*/
    public void UpdateDepartment(Department department) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(department);
    }

    /*ɾ��Department��Ϣ*/
    public void DeleteDepartment (String departmentNo) throws Exception {
        Session s = factory.getCurrentSession();
        Object department = s.load(Department.class, departmentNo);
        s.delete(department);
    }

}
