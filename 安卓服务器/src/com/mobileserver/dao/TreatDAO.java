package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Treat;
import com.mobileserver.util.DB;

public class TreatDAO {

	public List<Treat> QueryTreat(String treatName,int patientObj,String doctorObj) {
		List<Treat> treatList = new ArrayList<Treat>();
		DB db = new DB();
		String sql = "select * from Treat where 1=1";
		if (!treatName.equals(""))
			sql += " and treatName like '%" + treatName + "%'";
		if (patientObj != 0)
			sql += " and patientObj=" + patientObj;
		if (!doctorObj.equals(""))
			sql += " and doctorObj = '" + doctorObj + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Treat treat = new Treat();
				treat.setTreatId(rs.getInt("treatId"));
				treat.setTreatName(rs.getString("treatName"));
				treat.setPatientObj(rs.getInt("patientObj"));
				treat.setDiagnosis(rs.getString("diagnosis"));
				treat.setTreatContent(rs.getString("treatContent"));
				treat.setTreatResult(rs.getString("treatResult"));
				treat.setDoctorObj(rs.getString("doctorObj"));
				treat.setStartTime(rs.getString("startTime"));
				treat.setTimeLong(rs.getString("timeLong"));
				treatList.add(treat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return treatList;
	}
	/* �������ƶ��󣬽������Ƶ����ҵ�� */
	public String AddTreat(Treat treat) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в��������� */
			String sqlString = "insert into Treat(treatName,patientObj,diagnosis,treatContent,treatResult,doctorObj,startTime,timeLong) values (";
			sqlString += "'" + treat.getTreatName() + "',";
			sqlString += treat.getPatientObj() + ",";
			sqlString += "'" + treat.getDiagnosis() + "',";
			sqlString += "'" + treat.getTreatContent() + "',";
			sqlString += "'" + treat.getTreatResult() + "',";
			sqlString += "'" + treat.getDoctorObj() + "',";
			sqlString += "'" + treat.getStartTime() + "',";
			sqlString += "'" + treat.getTimeLong() + "'";
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
	public String DeleteTreat(int treatId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Treat where treatId=" + treatId;
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

	/* ��������id��ȡ������ */
	public Treat GetTreat(int treatId) {
		Treat treat = null;
		DB db = new DB();
		String sql = "select * from Treat where treatId=" + treatId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				treat = new Treat();
				treat.setTreatId(rs.getInt("treatId"));
				treat.setTreatName(rs.getString("treatName"));
				treat.setPatientObj(rs.getInt("patientObj"));
				treat.setDiagnosis(rs.getString("diagnosis"));
				treat.setTreatContent(rs.getString("treatContent"));
				treat.setTreatResult(rs.getString("treatResult"));
				treat.setDoctorObj(rs.getString("doctorObj"));
				treat.setStartTime(rs.getString("startTime"));
				treat.setTimeLong(rs.getString("timeLong"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return treat;
	}
	/* �������� */
	public String UpdateTreat(Treat treat) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Treat set ";
			sql += "treatName='" + treat.getTreatName() + "',";
			sql += "patientObj=" + treat.getPatientObj() + ",";
			sql += "diagnosis='" + treat.getDiagnosis() + "',";
			sql += "treatContent='" + treat.getTreatContent() + "',";
			sql += "treatResult='" + treat.getTreatResult() + "',";
			sql += "doctorObj='" + treat.getDoctorObj() + "',";
			sql += "startTime='" + treat.getStartTime() + "',";
			sql += "timeLong='" + treat.getTimeLong() + "'";
			sql += " where treatId=" + treat.getTreatId();
			db.executeUpdate(sql);
			result = "���Ƹ��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���Ƹ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
