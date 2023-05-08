package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Patient;
import com.mobileclient.util.HttpUtil;

/*病人管理业务逻辑层*/
public class PatientService {
	/* 添加病人 */
	public String AddPatient(Patient patient) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("patiendId", patient.getPatiendId() + "");
		params.put("name", patient.getName());
		params.put("sex", patient.getSex());
		params.put("birthday", patient.getBirthday().toString());
		params.put("cardNo", patient.getCardNo());
		params.put("originPlace", patient.getOriginPlace());
		params.put("telephone", patient.getTelephone());
		params.put("address", patient.getAddress());
		params.put("caseHistory", patient.getCaseHistory());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PatientServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询病人 */
	public List<Patient> QueryPatient(Patient queryConditionPatient) throws Exception {
		String urlString = HttpUtil.BASE_URL + "PatientServlet?action=query";
		if(queryConditionPatient != null) {
			urlString += "&name=" + URLEncoder.encode(queryConditionPatient.getName(), "UTF-8") + "";
			if(queryConditionPatient.getBirthday() != null) {
				urlString += "&birthday=" + URLEncoder.encode(queryConditionPatient.getBirthday().toString(), "UTF-8");
			}
			urlString += "&cardNo=" + URLEncoder.encode(queryConditionPatient.getCardNo(), "UTF-8") + "";
			urlString += "&originPlace=" + URLEncoder.encode(queryConditionPatient.getOriginPlace(), "UTF-8") + "";
			urlString += "&telephone=" + URLEncoder.encode(queryConditionPatient.getTelephone(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		PatientListHandler patientListHander = new PatientListHandler();
		xr.setContentHandler(patientListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Patient> patientList = patientListHander.getPatientList();
		return patientList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Patient> patientList = new ArrayList<Patient>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Patient patient = new Patient();
				patient.setPatiendId(object.getInt("patiendId"));
				patient.setName(object.getString("name"));
				patient.setSex(object.getString("sex"));
				patient.setBirthday(Timestamp.valueOf(object.getString("birthday")));
				patient.setCardNo(object.getString("cardNo"));
				patient.setOriginPlace(object.getString("originPlace"));
				patient.setTelephone(object.getString("telephone"));
				patient.setAddress(object.getString("address"));
				patient.setCaseHistory(object.getString("caseHistory"));
				patientList.add(patient);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return patientList;
	}

	/* 更新病人 */
	public String UpdatePatient(Patient patient) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("patiendId", patient.getPatiendId() + "");
		params.put("name", patient.getName());
		params.put("sex", patient.getSex());
		params.put("birthday", patient.getBirthday().toString());
		params.put("cardNo", patient.getCardNo());
		params.put("originPlace", patient.getOriginPlace());
		params.put("telephone", patient.getTelephone());
		params.put("address", patient.getAddress());
		params.put("caseHistory", patient.getCaseHistory());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PatientServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除病人 */
	public String DeletePatient(int patiendId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("patiendId", patiendId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PatientServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "病人信息删除失败!";
		}
	}

	/* 根据病人id获取病人对象 */
	public Patient GetPatient(int patiendId)  {
		List<Patient> patientList = new ArrayList<Patient>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("patiendId", patiendId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PatientServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Patient patient = new Patient();
				patient.setPatiendId(object.getInt("patiendId"));
				patient.setName(object.getString("name"));
				patient.setSex(object.getString("sex"));
				patient.setBirthday(Timestamp.valueOf(object.getString("birthday")));
				patient.setCardNo(object.getString("cardNo"));
				patient.setOriginPlace(object.getString("originPlace"));
				patient.setTelephone(object.getString("telephone"));
				patient.setAddress(object.getString("address"));
				patient.setCaseHistory(object.getString("caseHistory"));
				patientList.add(patient);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = patientList.size();
		if(size>0) return patientList.get(0); 
		else return null; 
	}
}
