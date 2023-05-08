package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.DrugUse;
import com.mobileserver.util.DB;

public class DrugUseDAO {

	public List<DrugUse> QueryDrugUse(int treatObj,int drugObj,String useTime) {
		List<DrugUse> drugUseList = new ArrayList<DrugUse>();
		DB db = new DB();
		String sql = "select * from DrugUse where 1=1";
		if (treatObj != 0)
			sql += " and treatObj=" + treatObj;
		if (drugObj != 0)
			sql += " and drugObj=" + drugObj;
		if (!useTime.equals(""))
			sql += " and useTime like '%" + useTime + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				DrugUse drugUse = new DrugUse();
				drugUse.setDrugUseId(rs.getInt("drugUseId"));
				drugUse.setTreatObj(rs.getInt("treatObj"));
				drugUse.setDrugObj(rs.getInt("drugObj"));
				drugUse.setDrugCount(rs.getFloat("drugCount"));
				drugUse.setDrugMoney(rs.getFloat("drugMoney"));
				drugUse.setUseTime(rs.getString("useTime"));
				drugUseList.add(drugUse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return drugUseList;
	}
	/* 传入用药对象，进行用药的添加业务 */
	public String AddDrugUse(DrugUse drugUse) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新用药 */
			String sqlString = "insert into DrugUse(treatObj,drugObj,drugCount,drugMoney,useTime) values (";
			sqlString += drugUse.getTreatObj() + ",";
			sqlString += drugUse.getDrugObj() + ",";
			sqlString += drugUse.getDrugCount() + ",";
			sqlString += drugUse.getDrugMoney() + ",";
			sqlString += "'" + drugUse.getUseTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "用药添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "用药添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除用药 */
	public String DeleteDrugUse(int drugUseId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from DrugUse where drugUseId=" + drugUseId;
			db.executeUpdate(sqlString);
			result = "用药删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "用药删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据用药id获取到用药 */
	public DrugUse GetDrugUse(int drugUseId) {
		DrugUse drugUse = null;
		DB db = new DB();
		String sql = "select * from DrugUse where drugUseId=" + drugUseId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				drugUse = new DrugUse();
				drugUse.setDrugUseId(rs.getInt("drugUseId"));
				drugUse.setTreatObj(rs.getInt("treatObj"));
				drugUse.setDrugObj(rs.getInt("drugObj"));
				drugUse.setDrugCount(rs.getFloat("drugCount"));
				drugUse.setDrugMoney(rs.getFloat("drugMoney"));
				drugUse.setUseTime(rs.getString("useTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return drugUse;
	}
	/* 更新用药 */
	public String UpdateDrugUse(DrugUse drugUse) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update DrugUse set ";
			sql += "treatObj=" + drugUse.getTreatObj() + ",";
			sql += "drugObj=" + drugUse.getDrugObj() + ",";
			sql += "drugCount=" + drugUse.getDrugCount() + ",";
			sql += "drugMoney=" + drugUse.getDrugMoney() + ",";
			sql += "useTime='" + drugUse.getUseTime() + "'";
			sql += " where drugUseId=" + drugUse.getDrugUseId();
			db.executeUpdate(sql);
			result = "用药更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "用药更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
