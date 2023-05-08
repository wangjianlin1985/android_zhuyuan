package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Drug;
import com.mobileclient.service.DrugService;
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

public class DrugEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明药品idTextView
	private TextView TV_drugId;
	// 声明药品名称输入框
	private EditText ET_drugName;
	// 声明药品单位输入框
	private EditText ET_unit;
	// 声明药品单价输入框
	private EditText ET_price;
	protected String carmera_path;
	/*要保存的药品信息*/
	Drug drug = new Drug();
	/*药品管理业务逻辑层*/
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
		setContentView(R.layout.drug_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑药品信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_drugId = (TextView) findViewById(R.id.TV_drugId);
		ET_drugName = (EditText) findViewById(R.id.ET_drugName);
		ET_unit = (EditText) findViewById(R.id.ET_unit);
		ET_price = (EditText) findViewById(R.id.ET_price);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		drugId = extras.getInt("drugId");
		/*单击修改药品按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取药品名称*/ 
					if(ET_drugName.getText().toString().equals("")) {
						Toast.makeText(DrugEditActivity.this, "药品名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_drugName.setFocusable(true);
						ET_drugName.requestFocus();
						return;	
					}
					drug.setDrugName(ET_drugName.getText().toString());
					/*验证获取药品单位*/ 
					if(ET_unit.getText().toString().equals("")) {
						Toast.makeText(DrugEditActivity.this, "药品单位输入不能为空!", Toast.LENGTH_LONG).show();
						ET_unit.setFocusable(true);
						ET_unit.requestFocus();
						return;	
					}
					drug.setUnit(ET_unit.getText().toString());
					/*验证获取药品单价*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(DrugEditActivity.this, "药品单价输入不能为空!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					drug.setPrice(Float.parseFloat(ET_price.getText().toString()));
					/*调用业务逻辑层上传药品信息*/
					DrugEditActivity.this.setTitle("正在更新药品信息，稍等...");
					String result = drugService.UpdateDrug(drug);
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
	    drug = drugService.GetDrug(drugId);
		this.TV_drugId.setText(drugId+"");
		this.ET_drugName.setText(drug.getDrugName());
		this.ET_unit.setText(drug.getUnit());
		this.ET_price.setText(drug.getPrice() + "");
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
