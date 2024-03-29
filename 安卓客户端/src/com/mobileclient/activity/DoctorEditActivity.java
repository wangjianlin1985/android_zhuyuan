package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Doctor;
import com.mobileclient.service.DoctorService;
import com.mobileclient.domain.Department;
import com.mobileclient.service.DepartmentService;
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

public class DoctorEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明医生工号TextView
	private TextView TV_doctorNo;
	// 声明登录密码输入框
	private EditText ET_password;
	// 声明所在科室下拉框
	private Spinner spinner_departmentObj;
	private ArrayAdapter<String> departmentObj_adapter;
	private static  String[] departmentObj_ShowText  = null;
	private List<Department> departmentList = null;
	/*所在科室管理业务逻辑层*/
	private DepartmentService departmentService = new DepartmentService();
	// 声明姓名输入框
	private EditText ET_name;
	// 声明性别输入框
	private EditText ET_sex;
	// 声明身份证号输入框
	private EditText ET_cardNo;
	// 声明医生照片图片框控件
	private ImageView iv_doctorPhoto;
	private Button btn_doctorPhoto;
	protected int REQ_CODE_SELECT_IMAGE_doctorPhoto = 1;
	private int REQ_CODE_CAMERA_doctorPhoto = 2;
	// 出版出生日期控件
	private DatePicker dp_birthday;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明毕业学校输入框
	private EditText ET_school;
	// 声明工作经验输入框
	private EditText ET_workYears;
	// 声明备注输入框
	private EditText ET_memo;
	protected String carmera_path;
	/*要保存的医生信息*/
	Doctor doctor = new Doctor();
	/*医生管理业务逻辑层*/
	private DoctorService doctorService = new DoctorService();

	private String doctorNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.doctor_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑医生信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_doctorNo = (TextView) findViewById(R.id.TV_doctorNo);
		ET_password = (EditText) findViewById(R.id.ET_password);
		spinner_departmentObj = (Spinner) findViewById(R.id.Spinner_departmentObj);
		// 获取所有的所在科室
		try {
			departmentList = departmentService.QueryDepartment(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int departmentCount = departmentList.size();
		departmentObj_ShowText = new String[departmentCount];
		for(int i=0;i<departmentCount;i++) { 
			departmentObj_ShowText[i] = departmentList.get(i).getDepartmentName();
		}
		// 将可选内容与ArrayAdapter连接起来
		departmentObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, departmentObj_ShowText);
		// 设置图书类别下拉列表的风格
		departmentObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_departmentObj.setAdapter(departmentObj_adapter);
		// 添加事件Spinner事件监听
		spinner_departmentObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				doctor.setDepartmentObj(departmentList.get(arg2).getDepartmentNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_departmentObj.setVisibility(View.VISIBLE);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		ET_cardNo = (EditText) findViewById(R.id.ET_cardNo);
		iv_doctorPhoto = (ImageView) findViewById(R.id.iv_doctorPhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_doctorPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(DoctorEditActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_doctorPhoto);
			}
		});
		btn_doctorPhoto = (Button) findViewById(R.id.btn_doctorPhoto);
		btn_doctorPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_doctorPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_doctorPhoto);  
			}
		});
		dp_birthday = (DatePicker)this.findViewById(R.id.dp_birthday);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_school = (EditText) findViewById(R.id.ET_school);
		ET_workYears = (EditText) findViewById(R.id.ET_workYears);
		ET_memo = (EditText) findViewById(R.id.ET_memo);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		doctorNo = extras.getString("doctorNo");
		/*单击修改医生按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取登录密码*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(DoctorEditActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					doctor.setPassword(ET_password.getText().toString());
					/*验证获取姓名*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(DoctorEditActivity.this, "姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					doctor.setName(ET_name.getText().toString());
					/*验证获取性别*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(DoctorEditActivity.this, "性别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					doctor.setSex(ET_sex.getText().toString());
					/*验证获取身份证号*/ 
					if(ET_cardNo.getText().toString().equals("")) {
						Toast.makeText(DoctorEditActivity.this, "身份证号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_cardNo.setFocusable(true);
						ET_cardNo.requestFocus();
						return;	
					}
					doctor.setCardNo(ET_cardNo.getText().toString());
					if (!doctor.getDoctorPhoto().startsWith("upload/")) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						DoctorEditActivity.this.setTitle("正在上传图片，稍等...");
						String doctorPhoto = HttpUtil.uploadFile(doctor.getDoctorPhoto());
						DoctorEditActivity.this.setTitle("图片上传完毕！");
						doctor.setDoctorPhoto(doctorPhoto);
					} 
					/*获取出版日期*/
					Date birthday = new Date(dp_birthday.getYear()-1900,dp_birthday.getMonth(),dp_birthday.getDayOfMonth());
					doctor.setBirthday(new Timestamp(birthday.getTime()));
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(DoctorEditActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					doctor.setTelephone(ET_telephone.getText().toString());
					/*验证获取毕业学校*/ 
					if(ET_school.getText().toString().equals("")) {
						Toast.makeText(DoctorEditActivity.this, "毕业学校输入不能为空!", Toast.LENGTH_LONG).show();
						ET_school.setFocusable(true);
						ET_school.requestFocus();
						return;	
					}
					doctor.setSchool(ET_school.getText().toString());
					/*验证获取工作经验*/ 
					if(ET_workYears.getText().toString().equals("")) {
						Toast.makeText(DoctorEditActivity.this, "工作经验输入不能为空!", Toast.LENGTH_LONG).show();
						ET_workYears.setFocusable(true);
						ET_workYears.requestFocus();
						return;	
					}
					doctor.setWorkYears(ET_workYears.getText().toString());
					/*验证获取备注*/ 
					if(ET_memo.getText().toString().equals("")) {
						Toast.makeText(DoctorEditActivity.this, "备注输入不能为空!", Toast.LENGTH_LONG).show();
						ET_memo.setFocusable(true);
						ET_memo.requestFocus();
						return;	
					}
					doctor.setMemo(ET_memo.getText().toString());
					/*调用业务逻辑层上传医生信息*/
					DoctorEditActivity.this.setTitle("正在更新医生信息，稍等...");
					String result = doctorService.UpdateDoctor(doctor);
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
	    doctor = doctorService.GetDoctor(doctorNo);
		this.TV_doctorNo.setText(doctorNo);
		this.ET_password.setText(doctor.getPassword());
		for (int i = 0; i < departmentList.size(); i++) {
			if (doctor.getDepartmentObj().equals(departmentList.get(i).getDepartmentNo())) {
				this.spinner_departmentObj.setSelection(i);
				break;
			}
		}
		this.ET_name.setText(doctor.getName());
		this.ET_sex.setText(doctor.getSex());
		this.ET_cardNo.setText(doctor.getCardNo());
		byte[] doctorPhoto_data = null;
		try {
			// 获取图片数据
			doctorPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + doctor.getDoctorPhoto());
			Bitmap doctorPhoto = BitmapFactory.decodeByteArray(doctorPhoto_data, 0, doctorPhoto_data.length);
			this.iv_doctorPhoto.setImageBitmap(doctorPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date birthday = new Date(doctor.getBirthday().getTime());
		this.dp_birthday.init(birthday.getYear() + 1900,birthday.getMonth(), birthday.getDate(), null);
		this.ET_telephone.setText(doctor.getTelephone());
		this.ET_school.setText(doctor.getSchool());
		this.ET_workYears.setText(doctor.getWorkYears());
		this.ET_memo.setText(doctor.getMemo());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_doctorPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_doctorPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_doctorPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_doctorPhoto.setImageBitmap(booImageBm);
				this.iv_doctorPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.doctor.setDoctorPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_doctorPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_doctorPhoto.setImageBitmap(bm); 
				this.iv_doctorPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			doctor.setDoctorPhoto(filename); 
		}
	}
}
