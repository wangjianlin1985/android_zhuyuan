package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Doctor;
import com.mobileclient.util.HttpUtil;

/*医生管理业务逻辑层*/
public class DoctorService {
	/* 添加医生 */
	public String AddDoctor(Doctor doctor) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("doctorNo", doctor.getDoctorNo());
		params.put("password", doctor.getPassword());
		params.put("departmentObj", doctor.getDepartmentObj());
		params.put("name", doctor.getName());
		params.put("sex", doctor.getSex());
		params.put("cardNo", doctor.getCardNo());
		params.put("doctorPhoto", doctor.getDoctorPhoto());
		params.put("birthday", doctor.getBirthday().toString());
		params.put("telephone", doctor.getTelephone());
		params.put("school", doctor.getSchool());
		params.put("workYears", doctor.getWorkYears());
		params.put("memo", doctor.getMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DoctorServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询医生 */
	public List<Doctor> QueryDoctor(Doctor queryConditionDoctor) throws Exception {
		String urlString = HttpUtil.BASE_URL + "DoctorServlet?action=query";
		if(queryConditionDoctor != null) {
			urlString += "&doctorNo=" + URLEncoder.encode(queryConditionDoctor.getDoctorNo(), "UTF-8") + "";
			urlString += "&departmentObj=" + URLEncoder.encode(queryConditionDoctor.getDepartmentObj(), "UTF-8") + "";
			urlString += "&name=" + URLEncoder.encode(queryConditionDoctor.getName(), "UTF-8") + "";
			urlString += "&cardNo=" + URLEncoder.encode(queryConditionDoctor.getCardNo(), "UTF-8") + "";
			if(queryConditionDoctor.getBirthday() != null) {
				urlString += "&birthday=" + URLEncoder.encode(queryConditionDoctor.getBirthday().toString(), "UTF-8");
			}
			urlString += "&telephone=" + URLEncoder.encode(queryConditionDoctor.getTelephone(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		DoctorListHandler doctorListHander = new DoctorListHandler();
		xr.setContentHandler(doctorListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Doctor> doctorList = doctorListHander.getDoctorList();
		return doctorList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Doctor> doctorList = new ArrayList<Doctor>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Doctor doctor = new Doctor();
				doctor.setDoctorNo(object.getString("doctorNo"));
				doctor.setPassword(object.getString("password"));
				doctor.setDepartmentObj(object.getString("departmentObj"));
				doctor.setName(object.getString("name"));
				doctor.setSex(object.getString("sex"));
				doctor.setCardNo(object.getString("cardNo"));
				doctor.setDoctorPhoto(object.getString("doctorPhoto"));
				doctor.setBirthday(Timestamp.valueOf(object.getString("birthday")));
				doctor.setTelephone(object.getString("telephone"));
				doctor.setSchool(object.getString("school"));
				doctor.setWorkYears(object.getString("workYears"));
				doctor.setMemo(object.getString("memo"));
				doctorList.add(doctor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doctorList;
	}

	/* 更新医生 */
	public String UpdateDoctor(Doctor doctor) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("doctorNo", doctor.getDoctorNo());
		params.put("password", doctor.getPassword());
		params.put("departmentObj", doctor.getDepartmentObj());
		params.put("name", doctor.getName());
		params.put("sex", doctor.getSex());
		params.put("cardNo", doctor.getCardNo());
		params.put("doctorPhoto", doctor.getDoctorPhoto());
		params.put("birthday", doctor.getBirthday().toString());
		params.put("telephone", doctor.getTelephone());
		params.put("school", doctor.getSchool());
		params.put("workYears", doctor.getWorkYears());
		params.put("memo", doctor.getMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DoctorServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除医生 */
	public String DeleteDoctor(String doctorNo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("doctorNo", doctorNo);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DoctorServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "医生信息删除失败!";
		}
	}

	/* 根据医生工号获取医生对象 */
	public Doctor GetDoctor(String doctorNo)  {
		List<Doctor> doctorList = new ArrayList<Doctor>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("doctorNo", doctorNo);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DoctorServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Doctor doctor = new Doctor();
				doctor.setDoctorNo(object.getString("doctorNo"));
				doctor.setPassword(object.getString("password"));
				doctor.setDepartmentObj(object.getString("departmentObj"));
				doctor.setName(object.getString("name"));
				doctor.setSex(object.getString("sex"));
				doctor.setCardNo(object.getString("cardNo"));
				doctor.setDoctorPhoto(object.getString("doctorPhoto"));
				doctor.setBirthday(Timestamp.valueOf(object.getString("birthday")));
				doctor.setTelephone(object.getString("telephone"));
				doctor.setSchool(object.getString("school"));
				doctor.setWorkYears(object.getString("workYears"));
				doctor.setMemo(object.getString("memo"));
				doctorList.add(doctor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = doctorList.size();
		if(size>0) return doctorList.get(0); 
		else return null; 
	}
}
