package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Treat;
import com.mobileclient.service.TreatService;
import com.mobileclient.domain.Patient;
import com.mobileclient.service.PatientService;
import com.mobileclient.domain.Doctor;
import com.mobileclient.service.DoctorService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class TreatEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明治疗idTextView
	private TextView TV_treatId;
	// 声明治疗名称输入框
	private EditText ET_treatName;
	// 声明病人下拉框
	private Spinner spinner_patientObj;
	private ArrayAdapter<String> patientObj_adapter;
	private static  String[] patientObj_ShowText  = null;
	private List<Patient> patientList = null;
	/*病人管理业务逻辑层*/
	private PatientService patientService = new PatientService();
	// 声明诊断情况输入框
	private EditText ET_diagnosis;
	// 声明治疗记录输入框
	private EditText ET_treatContent;
	// 声明治疗结果输入框
	private EditText ET_treatResult;
	// 声明主治医生下拉框
	private Spinner spinner_doctorObj;
	private ArrayAdapter<String> doctorObj_adapter;
	private static  String[] doctorObj_ShowText  = null;
	private List<Doctor> doctorList = null;
	/*主治医生管理业务逻辑层*/
	private DoctorService doctorService = new DoctorService();
	// 声明治疗开始时间输入框
	private EditText ET_startTime;
	// 声明疗程时间输入框
	private EditText ET_timeLong;
	protected String carmera_path;
	/*要保存的治疗信息*/
	Treat treat = new Treat();
	/*治疗管理业务逻辑层*/
	private TreatService treatService = new TreatService();

	private int treatId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.treat_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑治疗信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_treatId = (TextView) findViewById(R.id.TV_treatId);
		ET_treatName = (EditText) findViewById(R.id.ET_treatName);
		spinner_patientObj = (Spinner) findViewById(R.id.Spinner_patientObj);
		// 获取所有的病人
		try {
			patientList = patientService.QueryPatient(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int patientCount = patientList.size();
		patientObj_ShowText = new String[patientCount];
		for(int i=0;i<patientCount;i++) { 
			patientObj_ShowText[i] = patientList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		patientObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, patientObj_ShowText);
		// 设置图书类别下拉列表的风格
		patientObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_patientObj.setAdapter(patientObj_adapter);
		// 添加事件Spinner事件监听
		spinner_patientObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				treat.setPatientObj(patientList.get(arg2).getPatiendId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_patientObj.setVisibility(View.VISIBLE);
		ET_diagnosis = (EditText) findViewById(R.id.ET_diagnosis);
		ET_treatContent = (EditText) findViewById(R.id.ET_treatContent);
		ET_treatResult = (EditText) findViewById(R.id.ET_treatResult);
		spinner_doctorObj = (Spinner) findViewById(R.id.Spinner_doctorObj);
		// 获取所有的主治医生
		try {
			doctorList = doctorService.QueryDoctor(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int doctorCount = doctorList.size();
		doctorObj_ShowText = new String[doctorCount];
		for(int i=0;i<doctorCount;i++) { 
			doctorObj_ShowText[i] = doctorList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		doctorObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, doctorObj_ShowText);
		// 设置图书类别下拉列表的风格
		doctorObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_doctorObj.setAdapter(doctorObj_adapter);
		// 添加事件Spinner事件监听
		spinner_doctorObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				treat.setDoctorObj(doctorList.get(arg2).getDoctorNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_doctorObj.setVisibility(View.VISIBLE);
		ET_startTime = (EditText) findViewById(R.id.ET_startTime);
		ET_timeLong = (EditText) findViewById(R.id.ET_timeLong);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		treatId = extras.getInt("treatId");
		/*单击修改治疗按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取治疗名称*/ 
					if(ET_treatName.getText().toString().equals("")) {
						Toast.makeText(TreatEditActivity.this, "治疗名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_treatName.setFocusable(true);
						ET_treatName.requestFocus();
						return;	
					}
					treat.setTreatName(ET_treatName.getText().toString());
					/*验证获取诊断情况*/ 
					if(ET_diagnosis.getText().toString().equals("")) {
						Toast.makeText(TreatEditActivity.this, "诊断情况输入不能为空!", Toast.LENGTH_LONG).show();
						ET_diagnosis.setFocusable(true);
						ET_diagnosis.requestFocus();
						return;	
					}
					treat.setDiagnosis(ET_diagnosis.getText().toString());
					/*验证获取治疗记录*/ 
					if(ET_treatContent.getText().toString().equals("")) {
						Toast.makeText(TreatEditActivity.this, "治疗记录输入不能为空!", Toast.LENGTH_LONG).show();
						ET_treatContent.setFocusable(true);
						ET_treatContent.requestFocus();
						return;	
					}
					treat.setTreatContent(ET_treatContent.getText().toString());
					/*验证获取治疗结果*/ 
					if(ET_treatResult.getText().toString().equals("")) {
						Toast.makeText(TreatEditActivity.this, "治疗结果输入不能为空!", Toast.LENGTH_LONG).show();
						ET_treatResult.setFocusable(true);
						ET_treatResult.requestFocus();
						return;	
					}
					treat.setTreatResult(ET_treatResult.getText().toString());
					/*验证获取治疗开始时间*/ 
					if(ET_startTime.getText().toString().equals("")) {
						Toast.makeText(TreatEditActivity.this, "治疗开始时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_startTime.setFocusable(true);
						ET_startTime.requestFocus();
						return;	
					}
					treat.setStartTime(ET_startTime.getText().toString());
					/*验证获取疗程时间*/ 
					if(ET_timeLong.getText().toString().equals("")) {
						Toast.makeText(TreatEditActivity.this, "疗程时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_timeLong.setFocusable(true);
						ET_timeLong.requestFocus();
						return;	
					}
					treat.setTimeLong(ET_timeLong.getText().toString());
					/*调用业务逻辑层上传治疗信息*/
					TreatEditActivity.this.setTitle("正在更新治疗信息，稍等...");
					String result = treatService.UpdateTreat(treat);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    treat = treatService.GetTreat(treatId);
		this.TV_treatId.setText(treatId+"");
		this.ET_treatName.setText(treat.getTreatName());
		for (int i = 0; i < patientList.size(); i++) {
			if (treat.getPatientObj() == patientList.get(i).getPatiendId()) {
				this.spinner_patientObj.setSelection(i);
				break;
			}
		}
		this.ET_diagnosis.setText(treat.getDiagnosis());
		this.ET_treatContent.setText(treat.getTreatContent());
		this.ET_treatResult.setText(treat.getTreatResult());
		for (int i = 0; i < doctorList.size(); i++) {
			if (treat.getDoctorObj().equals(doctorList.get(i).getDoctorNo())) {
				this.spinner_doctorObj.setSelection(i);
				break;
			}
		}
		this.ET_startTime.setText(treat.getStartTime());
		this.ET_timeLong.setText(treat.getTimeLong());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
