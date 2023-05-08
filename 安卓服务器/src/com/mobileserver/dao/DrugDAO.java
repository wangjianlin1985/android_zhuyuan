package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Drug;
import com.mobileserver.util.DB;

public class DrugDAO {

	public List<Drug> QueryDrug() {
		List<Drug> drugList = new ArrayList<Drug>();
		DB db = new DB();
		String sql = "select * from Drug where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Drug drug = new Drug();
				drug.setDrugId(rs.getInt("drugId"));
				drug.setDrugName(rs.getString("drugName"));
				drug.setUnit(rs.getString("unit"));
				drug.setPrice(rs.getFloat("price"));
				drugList.add(drug);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return drugList;
	}
	/* ����ҩƷ���󣬽���ҩƷ�����ҵ�� */
	public String AddDrug(Drug drug) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����ҩƷ */
			String sqlString = "insert into Drug(drugName,unit,price) values (";
			sqlString += "'" + drug.getDrugName() + "',";
			sqlString += "'" + drug.getUnit() + "',";
			sqlString += drug.getPrice();
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "ҩƷ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ҩƷ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��ҩƷ */
	public String DeleteDrug(int drugId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Drug where drugId=" + drugId;
			db.executeUpdate(sqlString);
			result = "ҩƷɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ҩƷɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����ҩƷid��ȡ��ҩƷ */
	public Drug GetDrug(int drugId) {
		Drug drug = null;
		DB db = new DB();
		String sql = "select * from Drug where drugId=" + drugId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				drug = new Drug();
				drug.setDrugId(rs.getInt("drugId"));
				drug.setDrugName(rs.getString("drugName"));
				drug.setUnit(rs.getString("unit"));
				drug.setPrice(rs.getFloat("price"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return drug;
	}
	/* ����ҩƷ */
	public String UpdateDrug(Drug drug) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Drug set ";
			sql += "drugName='" + drug.getDrugName() + "',";
			sql += "unit='" + drug.getUnit() + "',";
			sql += "price=" + drug.getPrice();
			sql += " where drugId=" + drug.getDrugId();
			db.executeUpdate(sql);
			result = "ҩƷ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ҩƷ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
