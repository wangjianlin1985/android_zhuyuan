package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.ZhuYuan;
import com.mobileserver.util.DB;

public class ZhuYuanDAO {

	public List<ZhuYuan> QueryZhuYuan(int patientObj,Timestamp inDate,String bedNum,String doctorObj) {
		List<ZhuYuan> zhuYuanList = new ArrayList<ZhuYuan>();
		DB db = new DB();
		String sql = "select * from ZhuYuan where 1=1";
		if (patientObj != 0)
			sql += " and patientObj=" + patientObj;
		if(inDate!=null)
			sql += " and inDate='" + inDate + "'";
		if (!bedNum.equals(""))
			sql += " and bedNum like '%" + bedNum + "%'";
		if (!doctorObj.equals(""))
			sql += " and doctorObj = '" + doctorObj + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				ZhuYuan zhuYuan = new ZhuYuan();
				zhuYuan.setZhuyuanId(rs.getInt("zhuyuanId"));
				zhuYuan.setPatientObj(rs.getInt("patientObj"));
				zhuYuan.setAge(rs.getInt("age"));
				zhuYuan.setInDate(rs.getTimestamp("inDate"));
				zhuYuan.setInDays(rs.getInt("inDays"));
				zhuYuan.setBedNum(rs.getString("bedNum"));
				zhuYuan.setDoctorObj(rs.getString("doctorObj"));
				zhuYuan.setMemo(rs.getString("memo"));
				zhuYuanList.add(zhuYuan);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return zhuYuanList;
	}
	/* ����סԺ���󣬽���סԺ�����ҵ�� */
	public String AddZhuYuan(ZhuYuan zhuYuan) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����סԺ */
			String sqlString = "insert into ZhuYuan(patientObj,age,inDate,inDays,bedNum,doctorObj,memo) values (";
			sqlString += zhuYuan.getPatientObj() + ",";
			sqlString += zhuYuan.getAge() + ",";
			sqlString += "'" + zhuYuan.getInDate() + "',";
			sqlString += zhuYuan.getInDays() + ",";
			sqlString += "'" + zhuYuan.getBedNum() + "',";
			sqlString += "'" + zhuYuan.getDoctorObj() + "',";
			sqlString += "'" + zhuYuan.getMemo() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "סԺ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "סԺ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��סԺ */
	public String DeleteZhuYuan(int zhuyuanId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from ZhuYuan where zhuyuanId=" + zhuyuanId;
			db.executeUpdate(sqlString);
			result = "סԺɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "סԺɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����סԺid��ȡ��סԺ */
	public ZhuYuan GetZhuYuan(int zhuyuanId) {
		ZhuYuan zhuYuan = null;
		DB db = new DB();
		String sql = "select * from ZhuYuan where zhuyuanId=" + zhuyuanId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				zhuYuan = new ZhuYuan();
				zhuYuan.setZhuyuanId(rs.getInt("zhuyuanId"));
				zhuYuan.setPatientObj(rs.getInt("patientObj"));
				zhuYuan.setAge(rs.getInt("age"));
				zhuYuan.setInDate(rs.getTimestamp("inDate"));
				zhuYuan.setInDays(rs.getInt("inDays"));
				zhuYuan.setBedNum(rs.getString("bedNum"));
				zhuYuan.setDoctorObj(rs.getString("doctorObj"));
				zhuYuan.setMemo(rs.getString("memo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return zhuYuan;
	}
	/* ����סԺ */
	public String UpdateZhuYuan(ZhuYuan zhuYuan) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update ZhuYuan set ";
			sql += "patientObj=" + zhuYuan.getPatientObj() + ",";
			sql += "age=" + zhuYuan.getAge() + ",";
			sql += "inDate='" + zhuYuan.getInDate() + "',";
			sql += "inDays=" + zhuYuan.getInDays() + ",";
			sql += "bedNum='" + zhuYuan.getBedNum() + "',";
			sql += "doctorObj='" + zhuYuan.getDoctorObj() + "',";
			sql += "memo='" + zhuYuan.getMemo() + "'";
			sql += " where zhuyuanId=" + zhuYuan.getZhuyuanId();
			db.executeUpdate(sql);
			result = "סԺ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "סԺ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
