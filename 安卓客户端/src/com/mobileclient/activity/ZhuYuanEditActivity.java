package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class ZhuYuanEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明住院idTextView
	private TextView TV_zhuyuanId;
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

	private int zhuyuanId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.zhuyuan_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑住院信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_zhuyuanId = (TextView) findViewById(R.id.TV_zhuyuanId);
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
		// 设置图书类别下拉列表的风格
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
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		zhuyuanId = extras.getInt("zhuyuanId");
		/*单击修改住院按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取年龄*/ 
					if(ET_age.getText().toString().equals("")) {
						Toast.makeText(ZhuYuanEditActivity.this, "年龄输入不能为空!", Toast.LENGTH_LONG).show();
						ET_age.setFocusable(true);
						ET_age.requestFocus();
						return;	
					}
					zhuYuan.setAge(Integer.parseInt(ET_age.getText().toString()));
					/*获取出版日期*/
					Date inDate = new Date(dp_inDate.getYear()-1900,dp_inDate.getMonth(),dp_inDate.getDayOfMonth());
					zhuYuan.setInDate(new Timestamp(inDate.getTime()));
					/*验证获取入住天数*/ 
					if(ET_inDays.getText().toString().equals("")) {
						Toast.makeText(ZhuYuanEditActivity.this, "入住天数输入不能为空!", Toast.LENGTH_LONG).show();
						ET_inDays.setFocusable(true);
						ET_inDays.requestFocus();
						return;	
					}
					zhuYuan.setInDays(Integer.parseInt(ET_inDays.getText().toString()));
					/*验证获取床位号*/ 
					if(ET_bedNum.getText().toString().equals("")) {
						Toast.makeText(ZhuYuanEditActivity.this, "床位号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_bedNum.setFocusable(true);
						ET_bedNum.requestFocus();
						return;	
					}
					zhuYuan.setBedNum(ET_bedNum.getText().toString());
					/*验证获取备注信息*/ 
					if(ET_memo.getText().toString().equals("")) {
						Toast.makeText(ZhuYuanEditActivity.this, "备注信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_memo.setFocusable(true);
						ET_memo.requestFocus();
						return;	
					}
					zhuYuan.setMemo(ET_memo.getText().toString());
					/*调用业务逻辑层上传住院信息*/
					ZhuYuanEditActivity.this.setTitle("正在更新住院信息，稍等...");
					String result = zhuYuanService.UpdateZhuYuan(zhuYuan);
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
	    zhuYuan = zhuYuanService.GetZhuYuan(zhuyuanId);
		this.TV_zhuyuanId.setText(zhuyuanId+"");
		for (int i = 0; i < patientList.size(); i++) {
			if (zhuYuan.getPatientObj() == patientList.get(i).getPatiendId()) {
				this.spinner_patientObj.setSelection(i);
				break;
			}
		}
		this.ET_age.setText(zhuYuan.getAge() + "");
		Date inDate = new Date(zhuYuan.getInDate().getTime());
		this.dp_inDate.init(inDate.getYear() + 1900,inDate.getMonth(), inDate.getDate(), null);
		this.ET_inDays.setText(zhuYuan.getInDays() + "");
		this.ET_bedNum.setText(zhuYuan.getBedNum());
		for (int i = 0; i < doctorList.size(); i++) {
			if (zhuYuan.getDoctorObj().equals(doctorList.get(i).getDoctorNo())) {
				this.spinner_doctorObj.setSelection(i);
				break;
			}
		}
		this.ET_memo.setText(zhuYuan.getMemo());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
