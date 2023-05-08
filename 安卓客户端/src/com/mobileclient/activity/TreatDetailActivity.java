package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Treat;
import com.mobileclient.service.TreatService;
import com.mobileclient.domain.Patient;
import com.mobileclient.service.PatientService;
import com.mobileclient.domain.Doctor;
import com.mobileclient.service.DoctorService;
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
public class TreatDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明治疗id控件
	private TextView TV_treatId;
	// 声明治疗名称控件
	private TextView TV_treatName;
	// 声明病人控件
	private TextView TV_patientObj;
	// 声明诊断情况控件
	private TextView TV_diagnosis;
	// 声明治疗记录控件
	private TextView TV_treatContent;
	// 声明治疗结果控件
	private TextView TV_treatResult;
	// 声明主治医生控件
	private TextView TV_doctorObj;
	// 声明治疗开始时间控件
	private TextView TV_startTime;
	// 声明疗程时间控件
	private TextView TV_timeLong;
	/* 要保存的治疗信息 */
	Treat treat = new Treat(); 
	/* 治疗管理业务逻辑层 */
	private TreatService treatService = new TreatService();
	private PatientService patientService = new PatientService();
	private DoctorService doctorService = new DoctorService();
	private int treatId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.treat_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看治疗详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_treatId = (TextView) findViewById(R.id.TV_treatId);
		TV_treatName = (TextView) findViewById(R.id.TV_treatName);
		TV_patientObj = (TextView) findViewById(R.id.TV_patientObj);
		TV_diagnosis = (TextView) findViewById(R.id.TV_diagnosis);
		TV_treatContent = (TextView) findViewById(R.id.TV_treatContent);
		TV_treatResult = (TextView) findViewById(R.id.TV_treatResult);
		TV_doctorObj = (TextView) findViewById(R.id.TV_doctorObj);
		TV_startTime = (TextView) findViewById(R.id.TV_startTime);
		TV_timeLong = (TextView) findViewById(R.id.TV_timeLong);
		Bundle extras = this.getIntent().getExtras();
		treatId = extras.getInt("treatId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TreatDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    treat = treatService.GetTreat(treatId); 
		this.TV_treatId.setText(treat.getTreatId() + "");
		this.TV_treatName.setText(treat.getTreatName());
		Patient patientObj = patientService.GetPatient(treat.getPatientObj());
		this.TV_patientObj.setText(patientObj.getName());
		this.TV_diagnosis.setText(treat.getDiagnosis());
		this.TV_treatContent.setText(treat.getTreatContent());
		this.TV_treatResult.setText(treat.getTreatResult());
		Doctor doctorObj = doctorService.GetDoctor(treat.getDoctorObj());
		this.TV_doctorObj.setText(doctorObj.getName());
		this.TV_startTime.setText(treat.getStartTime());
		this.TV_timeLong.setText(treat.getTimeLong());
	} 
}
