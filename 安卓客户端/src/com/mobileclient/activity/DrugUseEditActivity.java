package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.DrugUse;
import com.mobileclient.service.DrugUseService;
import com.mobileclient.domain.Treat;
import com.mobileclient.service.TreatService;
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

public class DrugUseEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明用药idTextView
	private TextView TV_drugUseId;
	// 声明治疗名称下拉框
	private Spinner spinner_treatObj;
	private ArrayAdapter<String> treatObj_adapter;
	private static  String[] treatObj_ShowText  = null;
	private List<Treat> treatList = null;
	/*治疗名称管理业务逻辑层*/
	private TreatService treatService = new TreatService();
	// 声明所用药品下拉框
	private Spinner spinner_drugObj;
	private ArrayAdapter<String> drugObj_adapter;
	private static  String[] drugObj_ShowText  = null;
	private List<Drug> drugList = null;
	/*所用药品管理业务逻辑层*/
	private DrugService drugService = new DrugService();
	// 声明用药数量输入框
	private EditText ET_drugCount;
	// 声明药品费用输入框
	private EditText ET_drugMoney;
	// 声明用药时间输入框
	private EditText ET_useTime;
	protected String carmera_path;
	/*要保存的用药信息*/
	DrugUse drugUse = new DrugUse();
	/*用药管理业务逻辑层*/
	private DrugUseService drugUseService = new DrugUseService();

	private int drugUseId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.druguse_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑用药信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_drugUseId = (TextView) findViewById(R.id.TV_drugUseId);
		spinner_treatObj = (Spinner) findViewById(R.id.Spinner_treatObj);
		// 获取所有的治疗名称
		try {
			treatList = treatService.QueryTreat(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int treatCount = treatList.size();
		treatObj_ShowText = new String[treatCount];
		for(int i=0;i<treatCount;i++) { 
			treatObj_ShowText[i] = treatList.get(i).getTreatName();
		}
		// 将可选内容与ArrayAdapter连接起来
		treatObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, treatObj_ShowText);
		// 设置图书类别下拉列表的风格
		treatObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_treatObj.setAdapter(treatObj_adapter);
		// 添加事件Spinner事件监听
		spinner_treatObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				drugUse.setTreatObj(treatList.get(arg2).getTreatId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_treatObj.setVisibility(View.VISIBLE);
		spinner_drugObj = (Spinner) findViewById(R.id.Spinner_drugObj);
		// 获取所有的所用药品
		try {
			drugList = drugService.QueryDrug(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int drugCount = drugList.size();
		drugObj_ShowText = new String[drugCount];
		for(int i=0;i<drugCount;i++) { 
			drugObj_ShowText[i] = drugList.get(i).getDrugName();
		}
		// 将可选内容与ArrayAdapter连接起来
		drugObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, drugObj_ShowText);
		// 设置图书类别下拉列表的风格
		drugObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_drugObj.setAdapter(drugObj_adapter);
		// 添加事件Spinner事件监听
		spinner_drugObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				drugUse.setDrugObj(drugList.get(arg2).getDrugId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_drugObj.setVisibility(View.VISIBLE);
		ET_drugCount = (EditText) findViewById(R.id.ET_drugCount);
		ET_drugMoney = (EditText) findViewById(R.id.ET_drugMoney);
		ET_useTime = (EditText) findViewById(R.id.ET_useTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		drugUseId = extras.getInt("drugUseId");
		/*单击修改用药按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取用药数量*/ 
					if(ET_drugCount.getText().toString().equals("")) {
						Toast.makeText(DrugUseEditActivity.this, "用药数量输入不能为空!", Toast.LENGTH_LONG).show();
						ET_drugCount.setFocusable(true);
						ET_drugCount.requestFocus();
						return;	
					}
					drugUse.setDrugCount(Float.parseFloat(ET_drugCount.getText().toString()));
					/*验证获取药品费用*/ 
					if(ET_drugMoney.getText().toString().equals("")) {
						Toast.makeText(DrugUseEditActivity.this, "药品费用输入不能为空!", Toast.LENGTH_LONG).show();
						ET_drugMoney.setFocusable(true);
						ET_drugMoney.requestFocus();
						return;	
					}
					drugUse.setDrugMoney(Float.parseFloat(ET_drugMoney.getText().toString()));
					/*验证获取用药时间*/ 
					if(ET_useTime.getText().toString().equals("")) {
						Toast.makeText(DrugUseEditActivity.this, "用药时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_useTime.setFocusable(true);
						ET_useTime.requestFocus();
						return;	
					}
					drugUse.setUseTime(ET_useTime.getText().toString());
					/*调用业务逻辑层上传用药信息*/
					DrugUseEditActivity.this.setTitle("正在更新用药信息，稍等...");
					String result = drugUseService.UpdateDrugUse(drugUse);
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
	    drugUse = drugUseService.GetDrugUse(drugUseId);
		this.TV_drugUseId.setText(drugUseId+"");
		for (int i = 0; i < treatList.size(); i++) {
			if (drugUse.getTreatObj() == treatList.get(i).getTreatId()) {
				this.spinner_treatObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < drugList.size(); i++) {
			if (drugUse.getDrugObj() == drugList.get(i).getDrugId()) {
				this.spinner_drugObj.setSelection(i);
				break;
			}
		}
		this.ET_drugCount.setText(drugUse.getDrugCount() + "");
		this.ET_drugMoney.setText(drugUse.getDrugMoney() + "");
		this.ET_useTime.setText(drugUse.getUseTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
