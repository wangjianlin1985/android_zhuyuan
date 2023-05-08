package com.mobileclient.activity;

import java.util.Date;
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
public class DrugDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明药品id控件
	private TextView TV_drugId;
	// 声明药品名称控件
	private TextView TV_drugName;
	// 声明药品单位控件
	private TextView TV_unit;
	// 声明药品单价控件
	private TextView TV_price;
	/* 要保存的药品信息 */
	Drug drug = new Drug(); 
	/* 药品管理业务逻辑层 */
	private DrugService drugService = new DrugService();
	private int drugId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.drug_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看药品详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_drugId = (TextView) findViewById(R.id.TV_drugId);
		TV_drugName = (TextView) findViewById(R.id.TV_drugName);
		TV_unit = (TextView) findViewById(R.id.TV_unit);
		TV_price = (TextView) findViewById(R.id.TV_price);
		Bundle extras = this.getIntent().getExtras();
		drugId = extras.getInt("drugId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DrugDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    drug = drugService.GetDrug(drugId); 
		this.TV_drugId.setText(drug.getDrugId() + "");
		this.TV_drugName.setText(drug.getDrugName());
		this.TV_unit.setText(drug.getUnit());
		this.TV_price.setText(drug.getPrice() + "");
	} 
}
