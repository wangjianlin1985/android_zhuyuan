package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Drug;
import com.mobileclient.util.HttpUtil;

/*药品管理业务逻辑层*/
public class DrugService {
	/* 添加药品 */
	public String AddDrug(Drug drug) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("drugId", drug.getDrugId() + "");
		params.put("drugName", drug.getDrugName());
		params.put("unit", drug.getUnit());
		params.put("price", drug.getPrice() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DrugServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询药品 */
	public List<Drug> QueryDrug(Drug queryConditionDrug) throws Exception {
		String urlString = HttpUtil.BASE_URL + "DrugServlet?action=query";
		if(queryConditionDrug != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		DrugListHandler drugListHander = new DrugListHandler();
		xr.setContentHandler(drugListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Drug> drugList = drugListHander.getDrugList();
		return drugList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Drug> drugList = new ArrayList<Drug>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Drug drug = new Drug();
				drug.setDrugId(object.getInt("drugId"));
				drug.setDrugName(object.getString("drugName"));
				drug.setUnit(object.getString("unit"));
				drug.setPrice((float) object.getDouble("price"));
				drugList.add(drug);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return drugList;
	}

	/* 更新药品 */
	public String UpdateDrug(Drug drug) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("drugId", drug.getDrugId() + "");
		params.put("drugName", drug.getDrugName());
		params.put("unit", drug.getUnit());
		params.put("price", drug.getPrice() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DrugServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除药品 */
	public String DeleteDrug(int drugId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("drugId", drugId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DrugServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "药品信息删除失败!";
		}
	}

	/* 根据药品id获取药品对象 */
	public Drug GetDrug(int drugId)  {
		List<Drug> drugList = new ArrayList<Drug>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("drugId", drugId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DrugServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Drug drug = new Drug();
				drug.setDrugId(object.getInt("drugId"));
				drug.setDrugName(object.getString("drugName"));
				drug.setUnit(object.getString("unit"));
				drug.setPrice((float) object.getDouble("price"));
				drugList.add(drug);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = drugList.size();
		if(size>0) return drugList.get(0); 
		else return null; 
	}
}
