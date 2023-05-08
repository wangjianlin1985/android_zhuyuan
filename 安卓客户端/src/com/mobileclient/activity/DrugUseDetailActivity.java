package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.DrugUse;
import com.mobileclient.service.DrugUseService;
import com.mobileclient.domain.Treat;
import com.mobileclient.service.TreatService;
import com.mobileclient.domain.Drug;
import com.mobileclient.service.DrugService;
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
public class DrugUseDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明用药id控件
	private TextView TV_drugUseId;
	// 声明治疗名称控件
	private TextView TV_treatObj;
	// 声明所用药品控件
	private TextView TV_drugObj;
	// 声明用药数量控件
	private TextView TV_drugCount;
	// 声明药品费用控件
	private TextView TV_drugMoney;
	// 声明用药时间控件
	private TextView TV_useTime;
	/* 要保存的用药信息 */
	DrugUse drugUse = new DrugUse(); 
	/* 用药管理业务逻辑层 */
	private DrugUseService drugUseService = new DrugUseService();
	private TreatService treatService = new TreatService();
	private DrugService drugService = new DrugService();
	private int drugUseId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.druguse_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看用药详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_drugUseId = (TextView) findViewById(R.id.TV_drugUseId);
		TV_treatObj = (TextView) findViewById(R.id.TV_treatObj);
		TV_drugObj = (TextView) findViewById(R.id.TV_drugObj);
		TV_drugCount = (TextView) findViewById(R.id.TV_drugCount);
		TV_drugMoney = (TextView) findViewById(R.id.TV_drugMoney);
		TV_useTime = (TextView) findViewById(R.id.TV_useTime);
		Bundle extras = this.getIntent().getExtras();
		drugUseId = extras.getInt("drugUseId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DrugUseDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    drugUse = drugUseService.GetDrugUse(drugUseId); 
		this.TV_drugUseId.setText(drugUse.getDrugUseId() + "");
		Treat treatObj = treatService.GetTreat(drugUse.getTreatObj());
		this.TV_treatObj.setText(treatObj.getTreatName());
		Drug drugObj = drugService.GetDrug(drugUse.getDrugObj());
		this.TV_drugObj.setText(drugObj.getDrugName());
		this.TV_drugCount.setText(drugUse.getDrugCount() + "");
		this.TV_drugMoney.setText(drugUse.getDrugMoney() + "");
		this.TV_useTime.setText(drugUse.getUseTime());
	} 
}
