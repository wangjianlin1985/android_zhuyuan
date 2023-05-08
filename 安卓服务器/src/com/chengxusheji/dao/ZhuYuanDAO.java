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
import com.chengxusheji.domain.ZhuYuan;

@Service @Transactional
public class ZhuYuanDAO {

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
    public void AddZhuYuan(ZhuYuan zhuYuan) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(zhuYuan);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ZhuYuan> QueryZhuYuanInfo(Patient patientObj,String inDate,String bedNum,Doctor doctorObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ZhuYuan zhuYuan where 1=1";
    	if(null != patientObj && patientObj.getPatiendId()!=0) hql += " and zhuYuan.patientObj.patiendId=" + patientObj.getPatiendId();
    	if(!inDate.equals("")) hql = hql + " and zhuYuan.inDate like '%" + inDate + "%'";
    	if(!bedNum.equals("")) hql = hql + " and zhuYuan.bedNum like '%" + bedNum + "%'";
    	if(null != doctorObj && !doctorObj.getDoctorNo().equals("")) hql += " and zhuYuan.doctorObj.doctorNo='" + doctorObj.getDoctorNo() + "'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List zhuYuanList = q.list();
    	return (ArrayList<ZhuYuan>) zhuYuanList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ZhuYuan> QueryZhuYuanInfo(Patient patientObj,String inDate,String bedNum,Doctor doctorObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ZhuYuan zhuYuan where 1=1";
    	if(null != patientObj && patientObj.getPatiendId()!=0) hql += " and zhuYuan.patientObj.patiendId=" + patientObj.getPatiendId();
    	if(!inDate.equals("")) hql = hql + " and zhuYuan.inDate like '%" + inDate + "%'";
    	if(!bedNum.equals("")) hql = hql + " and zhuYuan.bedNum like '%" + bedNum + "%'";
    	if(null != doctorObj && !doctorObj.getDoctorNo().equals("")) hql += " and zhuYuan.doctorObj.doctorNo='" + doctorObj.getDoctorNo() + "'";
    	Query q = s.createQuery(hql);
    	List zhuYuanList = q.list();
    	return (ArrayList<ZhuYuan>) zhuYuanList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ZhuYuan> QueryAllZhuYuanInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From ZhuYuan";
        Query q = s.createQuery(hql);
        List zhuYuanList = q.list();
        return (ArrayList<ZhuYuan>) zhuYuanList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Patient patientObj,String inDate,String bedNum,Doctor doctorObj) {
        Session s = factory.getCurrentSession();
        String hql = "From ZhuYuan zhuYuan where 1=1";
        if(null != patientObj && patientObj.getPatiendId()!=0) hql += " and zhuYuan.patientObj.patiendId=" + patientObj.getPatiendId();
        if(!inDate.equals("")) hql = hql + " and zhuYuan.inDate like '%" + inDate + "%'";
        if(!bedNum.equals("")) hql = hql + " and zhuYuan.bedNum like '%" + bedNum + "%'";
        if(null != doctorObj && !doctorObj.getDoctorNo().equals("")) hql += " and zhuYuan.doctorObj.doctorNo='" + doctorObj.getDoctorNo() + "'";
        Query q = s.createQuery(hql);
        List zhuYuanList = q.list();
        recordNumber = zhuYuanList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ZhuYuan GetZhuYuanByZhuyuanId(int zhuyuanId) {
        Session s = factory.getCurrentSession();
        ZhuYuan zhuYuan = (ZhuYuan)s.get(ZhuYuan.class, zhuyuanId);
        return zhuYuan;
    }

    /*更新ZhuYuan信息*/
    public void UpdateZhuYuan(ZhuYuan zhuYuan) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(zhuYuan);
    }

    /*删除ZhuYuan信息*/
    public void DeleteZhuYuan (int zhuyuanId) throws Exception {
        Session s = factory.getCurrentSession();
        Object zhuYuan = s.load(ZhuYuan.class, zhuyuanId);
        s.delete(zhuYuan);
    }

}
