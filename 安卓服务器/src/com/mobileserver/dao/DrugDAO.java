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
	/* 传入药品对象，进行药品的添加业务 */
	public String AddDrug(Drug drug) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新药品 */
			String sqlString = "insert into Drug(drugName,unit,price) values (";
			sqlString += "'" + drug.getDrugName() + "',";
			sqlString += "'" + drug.getUnit() + "',";
			sqlString += drug.getPrice();
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "药品添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "药品添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除药品 */
	public String DeleteDrug(int drugId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Drug where drugId=" + drugId;
			db.executeUpdate(sqlString);
			result = "药品删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "药品删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据药品id获取到药品 */
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
	/* 更新药品 */
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
			result = "药品更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "药品更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
