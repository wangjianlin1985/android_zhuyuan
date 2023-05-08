package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.ZhuYuan;
import com.mobileclient.service.ZhuYuanService;
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
public class ZhuYuanDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明住院id控件
	private TextView TV_zhuyuanId;
	// 声明病人控件
	private TextView TV_patientObj;
	// 声明年龄控件
	private TextView TV_age;
	// 声明住院日期控件
	private TextView TV_inDate;
	// 声明入住天数控件
	private TextView TV_inDays;
	// 声明床位号控件
	private TextView TV_bedNum;
	// 声明负责医生控件
	private TextView TV_doctorObj;
	// 声明备注信息控件
	private TextView TV_memo;
	/* 要保存的住院信息 */
	ZhuYuan zhuYuan = new ZhuYuan(); 
	/* 住院管理业务逻辑层 */
	private ZhuYuanService zhuYuanService = new ZhuYuanService();
	private PatientService patientService = new PatientService();
	private DoctorService doctorService = new DoctorService();
	private int zhuyuanId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.zhuyuan_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看住院详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_zhuyuanId = (TextView) findViewById(R.id.TV_zhuyuanId);
		TV_patientObj = (TextView) findViewById(R.id.TV_patientObj);
		TV_age = (TextView) findViewById(R.id.TV_age);
		TV_inDate = (TextView) findViewById(R.id.TV_inDate);
		TV_inDays = (TextView) findViewById(R.id.TV_inDays);
		TV_bedNum = (TextView) findViewById(R.id.TV_bedNum);
		TV_doctorObj = (TextView) findViewById(R.id.TV_doctorObj);
		TV_memo = (TextView) findViewById(R.id.TV_memo);
		Bundle extras = this.getIntent().getExtras();
		zhuyuanId = extras.getInt("zhuyuanId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ZhuYuanDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    zhuYuan = zhuYuanService.GetZhuYuan(zhuyuanId); 
		this.TV_zhuyuanId.setText(zhuYuan.getZhuyuanId() + "");
		Patient patientObj = patientService.GetPatient(zhuYuan.getPatientObj());
		this.TV_patientObj.setText(patientObj.getName());
		this.TV_age.setText(zhuYuan.getAge() + "");
		Date inDate = new Date(zhuYuan.getInDate().getTime());
		String inDateStr = (inDate.getYear() + 1900) + "-" + (inDate.getMonth()+1) + "-" + inDate.getDate();
		this.TV_inDate.setText(inDateStr);
		this.TV_inDays.setText(zhuYuan.getInDays() + "");
		this.TV_bedNum.setText(zhuYuan.getBedNum());
		Doctor doctorObj = doctorService.GetDoctor(zhuYuan.getDoctorObj());
		this.TV_doctorObj.setText(doctorObj.getName());
		this.TV_memo.setText(zhuYuan.getMemo());
	} 
}
