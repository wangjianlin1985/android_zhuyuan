package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Patient;
import com.mobileclient.service.PatientService;
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
public class PatientAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明姓名输入框
	private EditText ET_name;
	// 声明性别输入框
	private EditText ET_sex;
	// 出版出生日期控件
	private DatePicker dp_birthday;
	// 声明身份证号输入框
	private EditText ET_cardNo;
	// 声明籍贯输入框
	private EditText ET_originPlace;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明联系地址输入框
	private EditText ET_address;
	// 声明病历历史输入框
	private EditText ET_caseHistory;
	protected String carmera_path;
	/*要保存的病人信息*/
	Patient patient = new Patient();
	/*病人管理业务逻辑层*/
	private PatientService patientService = new PatientService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.patient_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加病人");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		dp_birthday = (DatePicker)this.findViewById(R.id.dp_birthday);
		ET_cardNo = (EditText) findViewById(R.id.ET_cardNo);
		ET_originPlace = (EditText) findViewById(R.id.ET_originPlace);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_address = (EditText) findViewById(R.id.ET_address);
		ET_caseHistory = (EditText) findViewById(R.id.ET_caseHistory);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加病人按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取姓名*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(PatientAddActivity.this, "姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					patient.setName(ET_name.getText().toString());
					/*验证获取性别*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(PatientAddActivity.this, "性别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					patient.setSex(ET_sex.getText().toString());
					/*获取出生日期*/
					Date birthday = new Date(dp_birthday.getYear()-1900,dp_birthday.getMonth(),dp_birthday.getDayOfMonth());
					patient.setBirthday(new Timestamp(birthday.getTime()));
					/*验证获取身份证号*/ 
					if(ET_cardNo.getText().toString().equals("")) {
						Toast.makeText(PatientAddActivity.this, "身份证号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_cardNo.setFocusable(true);
						ET_cardNo.requestFocus();
						return;	
					}
					patient.setCardNo(ET_cardNo.getText().toString());
					/*验证获取籍贯*/ 
					if(ET_originPlace.getText().toString().equals("")) {
						Toast.makeText(PatientAddActivity.this, "籍贯输入不能为空!", Toast.LENGTH_LONG).show();
						ET_originPlace.setFocusable(true);
						ET_originPlace.requestFocus();
						return;	
					}
					patient.setOriginPlace(ET_originPlace.getText().toString());
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(PatientAddActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					patient.setTelephone(ET_telephone.getText().toString());
					/*验证获取联系地址*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(PatientAddActivity.this, "联系地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					patient.setAddress(ET_address.getText().toString());
					/*验证获取病历历史*/ 
					if(ET_caseHistory.getText().toString().equals("")) {
						Toast.makeText(PatientAddActivity.this, "病历历史输入不能为空!", Toast.LENGTH_LONG).show();
						ET_caseHistory.setFocusable(true);
						ET_caseHistory.requestFocus();
						return;	
					}
					patient.setCaseHistory(ET_caseHistory.getText().toString());
					/*调用业务逻辑层上传病人信息*/
					PatientAddActivity.this.setTitle("正在上传病人信息，稍等...");
					String result = patientService.AddPatient(patient);
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
