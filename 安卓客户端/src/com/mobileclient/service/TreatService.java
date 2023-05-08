package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Treat;
import com.mobileclient.util.HttpUtil;

/*治疗管理业务逻辑层*/
public class TreatService {
	/* 添加治疗 */
	public String AddTreat(Treat treat) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("treatId", treat.getTreatId() + "");
		params.put("treatName", treat.getTreatName());
		params.put("patientObj", treat.getPatientObj() + "");
		params.put("diagnosis", treat.getDiagnosis());
		params.put("treatContent", treat.getTreatContent());
		params.put("treatResult", treat.getTreatResult());
		params.put("doctorObj", treat.getDoctorObj());
		params.put("startTime", treat.getStartTime());
		params.put("timeLong", treat.getTimeLong());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TreatServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询治疗 */
	public List<Treat> QueryTreat(Treat queryConditionTreat) throws Exception {
		String urlString = HttpUtil.BASE_URL + "TreatServlet?action=query";
		if(queryConditionTreat != null) {
			urlString += "&treatName=" + URLEncoder.encode(queryConditionTreat.getTreatName(), "UTF-8") + "";
			urlString += "&patientObj=" + queryConditionTreat.getPatientObj();
			urlString += "&doctorObj=" + URLEncoder.encode(queryConditionTreat.getDoctorObj(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		TreatListHandler treatListHander = new TreatListHandler();
		xr.setContentHandler(treatListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Treat> treatList = treatListHander.getTreatList();
		return treatList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Treat> treatList = new ArrayList<Treat>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Treat treat = new Treat();
				treat.setTreatId(object.getInt("treatId"));
				treat.setTreatName(object.getString("treatName"));
				treat.setPatientObj(object.getInt("patientObj"));
				treat.setDiagnosis(object.getString("diagnosis"));
				treat.setTreatContent(object.getString("treatContent"));
				treat.setTreatResult(object.getString("treatResult"));
				treat.setDoctorObj(object.getString("doctorObj"));
				treat.setStartTime(object.getString("startTime"));
				treat.setTimeLong(object.getString("timeLong"));
				treatList.add(treat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return treatList;
	}

	/* 更新治疗 */
	public String UpdateTreat(Treat treat) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("treatId", treat.getTreatId() + "");
		params.put("treatName", treat.getTreatName());
		params.put("patientObj", treat.getPatientObj() + "");
		params.put("diagnosis", treat.getDiagnosis());
		params.put("treatContent", treat.getTreatContent());
		params.put("treatResult", treat.getTreatResult());
		params.put("doctorObj", treat.getDoctorObj());
		params.put("startTime", treat.getStartTime());
		params.put("timeLong", treat.getTimeLong());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TreatServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除治疗 */
	public String DeleteTreat(int treatId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("treatId", treatId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TreatServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "治疗信息删除失败!";
		}
	}

	/* 根据治疗id获取治疗对象 */
	public Treat GetTreat(int treatId)  {
		List<Treat> treatList = new ArrayList<Treat>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("treatId", treatId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TreatServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Treat treat = new Treat();
				treat.setTreatId(object.getInt("treatId"));
				treat.setTreatName(object.getString("treatName"));
				treat.setPatientObj(object.getInt("patientObj"));
				treat.setDiagnosis(object.getString("diagnosis"));
				treat.setTreatContent(object.getString("treatContent"));
				treat.setTreatResult(object.getString("treatResult"));
				treat.setDoctorObj(object.getString("doctorObj"));
				treat.setStartTime(object.getString("startTime"));
				treat.setTimeLong(object.getString("timeLong"));
				treatList.add(treat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = treatList.size();
		if(size>0) return treatList.get(0); 
		else return null; 
	}
}
