package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Treat;
import com.mobileclient.domain.Patient;
import com.mobileclient.service.PatientService;
import com.mobileclient.domain.Doctor;
import com.mobileclient.service.DoctorService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class TreatQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明治疗名称输入框
	private EditText ET_treatName;
	// 声明病人下拉框
	private Spinner spinner_patientObj;
	private ArrayAdapter<String> patientObj_adapter;
	private static  String[] patientObj_ShowText  = null;
	private List<Patient> patientList = null; 
	/*病人管理业务逻辑层*/
	private PatientService patientService = new PatientService();
	// 声明主治医生下拉框
	private Spinner spinner_doctorObj;
	private ArrayAdapter<String> doctorObj_adapter;
	private static  String[] doctorObj_ShowText  = null;
	private List<Doctor> doctorList = null; 
	/*医生管理业务逻辑层*/
	private DoctorService doctorService = new DoctorService();
	/*查询过滤条件保存到这个对象中*/
	private Treat queryConditionTreat = new Treat();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.treat_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置治疗查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_treatName = (EditText) findViewById(R.id.ET_treatName);
		spinner_patientObj = (Spinner) findViewById(R.id.Spinner_patientObj);
		// 获取所有的病人
		try {
			patientList = patientService.QueryPatient(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int patientCount = patientList.size();
		patientObj_ShowText = new String[patientCount+1];
		patientObj_ShowText[0] = "不限制";
		for(int i=1;i<=patientCount;i++) { 
			patientObj_ShowText[i] = patientList.get(i-1).getName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		patientObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, patientObj_ShowText);
		// 设置病人下拉列表的风格
		patientObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_patientObj.setAdapter(patientObj_adapter);
		// 添加事件Spinner事件监听
		spinner_patientObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionTreat.setPatientObj(patientList.get(arg2-1).getPatiendId()); 
				else
					queryConditionTreat.setPatientObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_patientObj.setVisibility(View.VISIBLE);
		spinner_doctorObj = (Spinner) findViewById(R.id.Spinner_doctorObj);
		// 获取所有的医生
		try {
			doctorList = doctorService.QueryDoctor(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int doctorCount = doctorList.size();
		doctorObj_ShowText = new String[doctorCount+1];
		doctorObj_ShowText[0] = "不限制";
		for(int i=1;i<=doctorCount;i++) { 
			doctorObj_ShowText[i] = doctorList.get(i-1).getName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		doctorObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, doctorObj_ShowText);
		// 设置主治医生下拉列表的风格
		doctorObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_doctorObj.setAdapter(doctorObj_adapter);
		// 添加事件Spinner事件监听
		spinner_doctorObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionTreat.setDoctorObj(doctorList.get(arg2-1).getDoctorNo()); 
				else
					queryConditionTreat.setDoctorObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_doctorObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionTreat.setTreatName(ET_treatName.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionTreat", queryConditionTreat);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
