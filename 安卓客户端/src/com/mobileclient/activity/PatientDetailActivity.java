package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Patient;
import com.mobileclient.service.PatientService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class PatientDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明病人id控件
	private TextView TV_patiendId;
	// 声明姓名控件
	private TextView TV_name;
	// 声明性别控件
	private TextView TV_sex;
	// 声明出生日期控件
	private TextView TV_birthday;
	// 声明身份证号控件
	private TextView TV_cardNo;
	// 声明籍贯控件
	private TextView TV_originPlace;
	// 声明联系电话控件
	private TextView TV_telephone;
	// 声明联系地址控件
	private TextView TV_address;
	// 声明病历历史控件
	private TextView TV_caseHistory;
	/* 要保存的病人信息 */
	Patient patient = new Patient(); 
	/* 病人管理业务逻辑层 */
	private PatientService patientService = new PatientService();
	private int patiendId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.patient_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看病人详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_patiendId = (TextView) findViewById(R.id.TV_patiendId);
		TV_name = (TextView) findViewById(R.id.TV_name);
		TV_sex = (TextView) findViewById(R.id.TV_sex);
		TV_birthday = (TextView) findViewById(R.id.TV_birthday);
		TV_cardNo = (TextView) findViewById(R.id.TV_cardNo);
		TV_originPlace = (TextView) findViewById(R.id.TV_originPlace);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_address = (TextView) findViewById(R.id.TV_address);
		TV_caseHistory = (TextView) findViewById(R.id.TV_caseHistory);
		Bundle extras = this.getIntent().getExtras();
		patiendId = extras.getInt("patiendId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				PatientDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    patient = patientService.GetPatient(patiendId); 
		this.TV_patiendId.setText(patient.getPatiendId() + "");
		this.TV_name.setText(patient.getName());
		this.TV_sex.setText(patient.getSex());
		Date birthday = new Date(patient.getBirthday().getTime());
		String birthdayStr = (birthday.getYear() + 1900) + "-" + (birthday.getMonth()+1) + "-" + birthday.getDate();
		this.TV_birthday.setText(birthdayStr);
		this.TV_cardNo.setText(patient.getCardNo());
		this.TV_originPlace.setText(patient.getOriginPlace());
		this.TV_telephone.setText(patient.getTelephone());
		this.TV_address.setText(patient.getAddress());
		this.TV_caseHistory.setText(patient.getCaseHistory());
	} 
}
