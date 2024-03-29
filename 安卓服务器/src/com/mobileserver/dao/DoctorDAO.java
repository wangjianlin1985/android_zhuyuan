package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Doctor;
import com.mobileserver.util.DB;

public class DoctorDAO {

	public List<Doctor> QueryDoctor(String doctorNo,String departmentObj,String name,String cardNo,Timestamp birthday,String telephone) {
		List<Doctor> doctorList = new ArrayList<Doctor>();
		DB db = new DB();
		String sql = "select * from Doctor where 1=1";
		if (!doctorNo.equals(""))
			sql += " and doctorNo like '%" + doctorNo + "%'";
		if (!departmentObj.equals(""))
			sql += " and departmentObj = '" + departmentObj + "'";
		if (!name.equals(""))
			sql += " and name like '%" + name + "%'";
		if (!cardNo.equals(""))
			sql += " and cardNo like '%" + cardNo + "%'";
		if(birthday!=null)
			sql += " and birthday='" + birthday + "'";
		if (!telephone.equals(""))
			sql += " and telephone like '%" + telephone + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Doctor doctor = new Doctor();
				doctor.setDoctorNo(rs.getString("doctorNo"));
				doctor.setPassword(rs.getString("password"));
				doctor.setDepartmentObj(rs.getString("departmentObj"));
				doctor.setName(rs.getString("name"));
				doctor.setSex(rs.getString("sex"));
				doctor.setCardNo(rs.getString("cardNo"));
				doctor.setDoctorPhoto(rs.getString("doctorPhoto"));
				doctor.setBirthday(rs.getTimestamp("birthday"));
				doctor.setTelephone(rs.getString("telephone"));
				doctor.setSchool(rs.getString("school"));
				doctor.setWorkYears(rs.getString("workYears"));
				doctor.setMemo(rs.getString("memo"));
				doctorList.add(doctor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return doctorList;
	}
	/* 传入医生对象，进行医生的添加业务 */
	public String AddDoctor(Doctor doctor) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新医生 */
			String sqlString = "insert into Doctor(doctorNo,password,departmentObj,name,sex,cardNo,doctorPhoto,birthday,telephone,school,workYears,memo) values (";
			sqlString += "'" + doctor.getDoctorNo() + "',";
			sqlString += "'" + doctor.getPassword() + "',";
			sqlString += "'" + doctor.getDepartmentObj() + "',";
			sqlString += "'" + doctor.getName() + "',";
			sqlString += "'" + doctor.getSex() + "',";
			sqlString += "'" + doctor.getCardNo() + "',";
			sqlString += "'" + doctor.getDoctorPhoto() + "',";
			sqlString += "'" + doctor.getBirthday() + "',";
			sqlString += "'" + doctor.getTelephone() + "',";
			sqlString += "'" + doctor.getSchool() + "',";
			sqlString += "'" + doctor.getWorkYears() + "',";
			sqlString += "'" + doctor.getMemo() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "医生添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "医生添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除医生 */
	public String DeleteDoctor(String doctorNo) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Doctor where doctorNo='" + doctorNo + "'";
			db.executeUpdate(sqlString);
			result = "医生删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "医生删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据医生工号获取到医生 */
	public Doctor GetDoctor(String doctorNo) {
		Doctor doctor = null;
		DB db = new DB();
		String sql = "select * from Doctor where doctorNo='" + doctorNo + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				doctor = new Doctor();
				doctor.setDoctorNo(rs.getString("doctorNo"));
				doctor.setPassword(rs.getString("password"));
				doctor.setDepartmentObj(rs.getString("departmentObj"));
				doctor.setName(rs.getString("name"));
				doctor.setSex(rs.getString("sex"));
				doctor.setCardNo(rs.getString("cardNo"));
				doctor.setDoctorPhoto(rs.getString("doctorPhoto"));
				doctor.setBirthday(rs.getTimestamp("birthday"));
				doctor.setTelephone(rs.getString("telephone"));
				doctor.setSchool(rs.getString("school"));
				doctor.setWorkYears(rs.getString("workYears"));
				doctor.setMemo(rs.getString("memo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return doctor;
	}
	/* 更新医生 */
	public String UpdateDoctor(Doctor doctor) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Doctor set ";
			sql += "password='" + doctor.getPassword() + "',";
			sql += "departmentObj='" + doctor.getDepartmentObj() + "',";
			sql += "name='" + doctor.getName() + "',";
			sql += "sex='" + doctor.getSex() + "',";
			sql += "cardNo='" + doctor.getCardNo() + "',";
			sql += "doctorPhoto='" + doctor.getDoctorPhoto() + "',";
			sql += "birthday='" + doctor.getBirthday() + "',";
			sql += "telephone='" + doctor.getTelephone() + "',";
			sql += "school='" + doctor.getSchool() + "',";
			sql += "workYears='" + doctor.getWorkYears() + "',";
			sql += "memo='" + doctor.getMemo() + "'";
			sql += " where doctorNo='" + doctor.getDoctorNo() + "'";
			db.executeUpdate(sql);
			result = "医生更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "医生更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
