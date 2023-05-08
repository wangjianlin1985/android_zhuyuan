package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.ZhuYuan;
import com.mobileclient.service.ZhuYuanService;
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class ZhuYuanAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明病人下拉框
	private Spinner spinner_patientObj;
	private ArrayAdapter<String> patientObj_adapter;
	private static  String[] patientObj_ShowText  = null;
	private List<Patient> patientList = null;
	/*病人管理业务逻辑层*/
	private PatientService patientService = new PatientService();
	// 声明年龄输入框
	private EditText ET_age;
	// 出版住院日期控件
	private DatePicker dp_inDate;
	// 声明入住天数输入框
	private EditText ET_inDays;
	// 声明床位号输入框
	private EditText ET_bedNum;
	// 声明负责医生下拉框
	private Spinner spinner_doctorObj;
	private ArrayAdapter<String> doctorObj_adapter;
	private static  String[] doctorObj_ShowText  = null;
	private List<Doctor> doctorList = null;
	/*负责医生管理业务逻辑层*/
	private DoctorService doctorService = new DoctorService();
	// 声明备注信息输入框
	private EditText ET_memo;
	protected String carmera_path;
	/*要保存的住院信息*/
	ZhuYuan zhuYuan = new ZhuYuan();
	/*住院管理业务逻辑层*/
	private ZhuYuanService zhuYuanService = new ZhuYuanService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.zhuyuan_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加住院");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
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
		// 设置下拉列表的风格
		patientObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_patientObj.setAdapter(patientObj_adapter);
		// 添加事件Spinner事件监听
		spinner_patientObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				zhuYuan.setPatientObj(patientList.get(arg2).getPatiendId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_patientObj.setVisibility(View.VISIBLE);
		ET_age = (EditText) findViewById(R.id.ET_age);
		dp_inDate = (DatePicker)this.findViewById(R.id.dp_inDate);
		ET_inDays = (EditText) findViewById(R.id.ET_inDays);
		ET_bedNum = (EditText) findViewById(R.id.ET_bedNum);
		spinner_doctorObj = (Spinner) findViewById(R.id.Spinner_doctorObj);
		// 获取所有的负责医生
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
		// 设置下拉列表的风格
		doctorObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_doctorObj.setAdapter(doctorObj_adapter);
		// 添加事件Spinner事件监听
		spinner_doctorObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				zhuYuan.setDoctorObj(doctorList.get(arg2).getDoctorNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_doctorObj.setVisibility(View.VISIBLE);
		ET_memo = (EditText) findViewById(R.id.ET_memo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加住院按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取年龄*/ 
					if(ET_age.getText().toString().equals("")) {
						Toast.makeText(ZhuYuanAddActivity.this, "年龄输入不能为空!", Toast.LENGTH_LONG).show();
						ET_age.setFocusable(true);
						ET_age.requestFocus();
						return;	
					}
					zhuYuan.setAge(Integer.parseInt(ET_age.getText().toString()));
					/*获取住院日期*/
					Date inDate = new Date(dp_inDate.getYear()-1900,dp_inDate.getMonth(),dp_inDate.getDayOfMonth());
					zhuYuan.setInDate(new Timestamp(inDate.getTime()));
					/*验证获取入住天数*/ 
					if(ET_inDays.getText().toString().equals("")) {
						Toast.makeText(ZhuYuanAddActivity.this, "入住天数输入不能为空!", Toast.LENGTH_LONG).show();
						ET_inDays.setFocusable(true);
						ET_inDays.requestFocus();
						return;	
					}
					zhuYuan.setInDays(Integer.parseInt(ET_inDays.getText().toString()));
					/*验证获取床位号*/ 
					if(ET_bedNum.getText().toString().equals("")) {
						Toast.makeText(ZhuYuanAddActivity.this, "床位号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_bedNum.setFocusable(true);
						ET_bedNum.requestFocus();
						return;	
					}
					zhuYuan.setBedNum(ET_bedNum.getText().toString());
					/*验证获取备注信息*/ 
					if(ET_memo.getText().toString().equals("")) {
						Toast.makeText(ZhuYuanAddActivity.this, "备注信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_memo.setFocusable(true);
						ET_memo.requestFocus();
						return;	
					}
					zhuYuan.setMemo(ET_memo.getText().toString());
					/*调用业务逻辑层上传住院信息*/
					ZhuYuanAddActivity.this.setTitle("正在上传住院信息，稍等...");
					String result = zhuYuanService.AddZhuYuan(zhuYuan);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
