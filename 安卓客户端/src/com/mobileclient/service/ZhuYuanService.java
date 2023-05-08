package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.ZhuYuan;
import com.mobileclient.util.HttpUtil;

/*住院管理业务逻辑层*/
public class ZhuYuanService {
	/* 添加住院 */
	public String AddZhuYuan(ZhuYuan zhuYuan) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("zhuyuanId", zhuYuan.getZhuyuanId() + "");
		params.put("patientObj", zhuYuan.getPatientObj() + "");
		params.put("age", zhuYuan.getAge() + "");
		params.put("inDate", zhuYuan.getInDate().toString());
		params.put("inDays", zhuYuan.getInDays() + "");
		params.put("bedNum", zhuYuan.getBedNum());
		params.put("doctorObj", zhuYuan.getDoctorObj());
		params.put("memo", zhuYuan.getMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ZhuYuanServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询住院 */
	public List<ZhuYuan> QueryZhuYuan(ZhuYuan queryConditionZhuYuan) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ZhuYuanServlet?action=query";
		if(queryConditionZhuYuan != null) {
			urlString += "&patientObj=" + queryConditionZhuYuan.getPatientObj();
			if(queryConditionZhuYuan.getInDate() != null) {
				urlString += "&inDate=" + URLEncoder.encode(queryConditionZhuYuan.getInDate().toString(), "UTF-8");
			}
			urlString += "&bedNum=" + URLEncoder.encode(queryConditionZhuYuan.getBedNum(), "UTF-8") + "";
			urlString += "&doctorObj=" + URLEncoder.encode(queryConditionZhuYuan.getDoctorObj(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ZhuYuanListHandler zhuYuanListHander = new ZhuYuanListHandler();
		xr.setContentHandler(zhuYuanListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<ZhuYuan> zhuYuanList = zhuYuanListHander.getZhuYuanList();
		return zhuYuanList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<ZhuYuan> zhuYuanList = new ArrayList<ZhuYuan>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ZhuYuan zhuYuan = new ZhuYuan();
				zhuYuan.setZhuyuanId(object.getInt("zhuyuanId"));
				zhuYuan.setPatientObj(object.getInt("patientObj"));
				zhuYuan.setAge(object.getInt("age"));
				zhuYuan.setInDate(Timestamp.valueOf(object.getString("inDate")));
				zhuYuan.setInDays(object.getInt("inDays"));
				zhuYuan.setBedNum(object.getString("bedNum"));
				zhuYuan.setDoctorObj(object.getString("doctorObj"));
				zhuYuan.setMemo(object.getString("memo"));
				zhuYuanList.add(zhuYuan);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return zhuYuanList;
	}

	/* 更新住院 */
	public String UpdateZhuYuan(ZhuYuan zhuYuan) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("zhuyuanId", zhuYuan.getZhuyuanId() + "");
		params.put("patientObj", zhuYuan.getPatientObj() + "");
		params.put("age", zhuYuan.getAge() + "");
		params.put("inDate", zhuYuan.getInDate().toString());
		params.put("inDays", zhuYuan.getInDays() + "");
		params.put("bedNum", zhuYuan.getBedNum());
		params.put("doctorObj", zhuYuan.getDoctorObj());
		params.put("memo", zhuYuan.getMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ZhuYuanServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除住院 */
	public String DeleteZhuYuan(int zhuyuanId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("zhuyuanId", zhuyuanId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ZhuYuanServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "住院信息删除失败!";
		}
	}

	/* 根据住院id获取住院对象 */
	public ZhuYuan GetZhuYuan(int zhuyuanId)  {
		List<ZhuYuan> zhuYuanList = new ArrayList<ZhuYuan>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("zhuyuanId", zhuyuanId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ZhuYuanServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ZhuYuan zhuYuan = new ZhuYuan();
				zhuYuan.setZhuyuanId(object.getInt("zhuyuanId"));
				zhuYuan.setPatientObj(object.getInt("patientObj"));
				zhuYuan.setAge(object.getInt("age"));
				zhuYuan.setInDate(Timestamp.valueOf(object.getString("inDate")));
				zhuYuan.setInDays(object.getInt("inDays"));
				zhuYuan.setBedNum(object.getString("bedNum"));
				zhuYuan.setDoctorObj(object.getString("doctorObj"));
				zhuYuan.setMemo(object.getString("memo"));
				zhuYuanList.add(zhuYuan);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = zhuYuanList.size();
		if(size>0) return zhuYuanList.get(0); 
		else return null; 
	}
}
