package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Patient;
import com.mobileserver.util.DB;

public class PatientDAO {

	public List<Patient> QueryPatient(String name,Timestamp birthday,String cardNo,String originPlace,String telephone) {
		List<Patient> patientList = new ArrayList<Patient>();
		DB db = new DB();
		String sql = "select * from Patient where 1=1";
		if (!name.equals(""))
			sql += " and name like '%" + name + "%'";
		if(birthday!=null)
			sql += " and birthday='" + birthday + "'";
		if (!cardNo.equals(""))
			sql += " and cardNo like '%" + cardNo + "%'";
		if (!originPlace.equals(""))
			sql += " and originPlace like '%" + originPlace + "%'";
		if (!telephone.equals(""))
			sql += " and telephone like '%" + telephone + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Patient patient = new Patient();
				patient.setPatiendId(rs.getInt("patiendId"));
				patient.setName(rs.getString("name"));
				patient.setSex(rs.getString("sex"));
				patient.setBirthday(rs.getTimestamp("birthday"));
				patient.setCardNo(rs.getString("cardNo"));
				patient.setOriginPlace(rs.getString("originPlace"));
				patient.setTelephone(rs.getString("telephone"));
				patient.setAddress(rs.getString("address"));
				patient.setCaseHistory(rs.getString("caseHistory"));
				patientList.add(patient);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return patientList;
	}
	/* ���벡�˶��󣬽��в��˵����ҵ�� */
	public String AddPatient(Patient patient) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����²��� */
			String sqlString = "insert into Patient(name,sex,birthday,cardNo,originPlace,telephone,address,caseHistory) values (";
			sqlString += "'" + patient.getName() + "',";
			sqlString += "'" + patient.getSex() + "',";
			sqlString += "'" + patient.getBirthday() + "',";
			sqlString += "'" + patient.getCardNo() + "',";
			sqlString += "'" + patient.getOriginPlace() + "',";
			sqlString += "'" + patient.getTelephone() + "',";
			sqlString += "'" + patient.getAddress() + "',";
			sqlString += "'" + patient.getCaseHistory() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ������ */
	public String DeletePatient(int patiendId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Patient where patiendId=" + patiendId;
			db.executeUpdate(sqlString);
			result = "����ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݲ���id��ȡ������ */
	public Patient GetPatient(int patiendId) {
		Patient patient = null;
		DB db = new DB();
		String sql = "select * from Patient where patiendId=" + patiendId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				patient = new Patient();
				patient.setPatiendId(rs.getInt("patiendId"));
				patient.setName(rs.getString("name"));
				patient.setSex(rs.getString("sex"));
				patient.setBirthday(rs.getTimestamp("birthday"));
				patient.setCardNo(rs.getString("cardNo"));
				patient.setOriginPlace(rs.getString("originPlace"));
				patient.setTelephone(rs.getString("telephone"));
				patient.setAddress(rs.getString("address"));
				patient.setCaseHistory(rs.getString("caseHistory"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return patient;
	}
	/* ���²��� */
	public String UpdatePatient(Patient patient) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Patient set ";
			sql += "name='" + patient.getName() + "',";
			sql += "sex='" + patient.getSex() + "',";
			sql += "birthday='" + patient.getBirthday() + "',";
			sql += "cardNo='" + patient.getCardNo() + "',";
			sql += "originPlace='" + patient.getOriginPlace() + "',";
			sql += "telephone='" + patient.getTelephone() + "',";
			sql += "address='" + patient.getAddress() + "',";
			sql += "caseHistory='" + patient.getCaseHistory() + "'";
			sql += " where patiendId=" + patient.getPatiendId();
			db.executeUpdate(sql);
			result = "���˸��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���˸���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
