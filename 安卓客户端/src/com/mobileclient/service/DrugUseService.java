package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.DrugUse;
import com.mobileclient.util.HttpUtil;

/*用药管理业务逻辑层*/
public class DrugUseService {
	/* 添加用药 */
	public String AddDrugUse(DrugUse drugUse) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("drugUseId", drugUse.getDrugUseId() + "");
		params.put("treatObj", drugUse.getTreatObj() + "");
		params.put("drugObj", drugUse.getDrugObj() + "");
		params.put("drugCount", drugUse.getDrugCount() + "");
		params.put("drugMoney", drugUse.getDrugMoney() + "");
		params.put("useTime", drugUse.getUseTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DrugUseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询用药 */
	public List<DrugUse> QueryDrugUse(DrugUse queryConditionDrugUse) throws Exception {
		String urlString = HttpUtil.BASE_URL + "DrugUseServlet?action=query";
		if(queryConditionDrugUse != null) {
			urlString += "&treatObj=" + queryConditionDrugUse.getTreatObj();
			urlString += "&drugObj=" + queryConditionDrugUse.getDrugObj();
			urlString += "&useTime=" + URLEncoder.encode(queryConditionDrugUse.getUseTime(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		DrugUseListHandler drugUseListHander = new DrugUseListHandler();
		xr.setContentHandler(drugUseListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<DrugUse> drugUseList = drugUseListHander.getDrugUseList();
		return drugUseList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<DrugUse> drugUseList = new ArrayList<DrugUse>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				DrugUse drugUse = new DrugUse();
				drugUse.setDrugUseId(object.getInt("drugUseId"));
				drugUse.setTreatObj(object.getInt("treatObj"));
				drugUse.setDrugObj(object.getInt("drugObj"));
				drugUse.setDrugCount((float) object.getDouble("drugCount"));
				drugUse.setDrugMoney((float) object.getDouble("drugMoney"));
				drugUse.setUseTime(object.getString("useTime"));
				drugUseList.add(drugUse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return drugUseList;
	}

	/* 更新用药 */
	public String UpdateDrugUse(DrugUse drugUse) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("drugUseId", drugUse.getDrugUseId() + "");
		params.put("treatObj", drugUse.getTreatObj() + "");
		params.put("drugObj", drugUse.getDrugObj() + "");
		params.put("drugCount", drugUse.getDrugCount() + "");
		params.put("drugMoney", drugUse.getDrugMoney() + "");
		params.put("useTime", drugUse.getUseTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DrugUseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除用药 */
	public String DeleteDrugUse(int drugUseId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("drugUseId", drugUseId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DrugUseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "用药信息删除失败!";
		}
	}

	/* 根据用药id获取用药对象 */
	public DrugUse GetDrugUse(int drugUseId)  {
		List<DrugUse> drugUseList = new ArrayList<DrugUse>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("drugUseId", drugUseId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DrugUseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				DrugUse drugUse = new DrugUse();
				drugUse.setDrugUseId(object.getInt("drugUseId"));
				drugUse.setTreatObj(object.getInt("treatObj"));
				drugUse.setDrugObj(object.getInt("drugObj"));
				drugUse.setDrugCount((float) object.getDouble("drugCount"));
				drugUse.setDrugMoney((float) object.getDouble("drugMoney"));
				drugUse.setUseTime(object.getString("useTime"));
				drugUseList.add(drugUse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = drugUseList.size();
		if(size>0) return drugUseList.get(0); 
		else return null; 
	}
}
