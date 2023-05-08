package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.DrugUse;
import com.mobileclient.domain.Treat;
import com.mobileclient.service.TreatService;
import com.mobileclient.domain.Drug;
import com.mobileclient.service.DrugService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class DrugUseQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明治疗名称下拉框
	private Spinner spinner_treatObj;
	private ArrayAdapter<String> treatObj_adapter;
	private static  String[] treatObj_ShowText  = null;
	private List<Treat> treatList = null; 
	/*治疗管理业务逻辑层*/
	private TreatService treatService = new TreatService();
	// 声明所用药品下拉框
	private Spinner spinner_drugObj;
	private ArrayAdapter<String> drugObj_adapter;
	private static  String[] drugObj_ShowText  = null;
	private List<Drug> drugList = null; 
	/*药品管理业务逻辑层*/
	private DrugService drugService = new DrugService();
	// 声明用药时间输入框
	private EditText ET_useTime;
	/*查询过滤条件保存到这个对象中*/
	private DrugUse queryConditionDrugUse = new DrugUse();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.druguse_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置用药查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_treatObj = (Spinner) findViewById(R.id.Spinner_treatObj);
		// 获取所有的治疗
		try {
			treatList = treatService.QueryTreat(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int treatCount = treatList.size();
		treatObj_ShowText = new String[treatCount+1];
		treatObj_ShowText[0] = "不限制";
		for(int i=1;i<=treatCount;i++) { 
			treatObj_ShowText[i] = treatList.get(i-1).getTreatName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		treatObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, treatObj_ShowText);
		// 设置治疗名称下拉列表的风格
		treatObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_treatObj.setAdapter(treatObj_adapter);
		// 添加事件Spinner事件监听
		spinner_treatObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionDrugUse.setTreatObj(treatList.get(arg2-1).getTreatId()); 
				else
					queryConditionDrugUse.setTreatObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_treatObj.setVisibility(View.VISIBLE);
		spinner_drugObj = (Spinner) findViewById(R.id.Spinner_drugObj);
		// 获取所有的药品
		try {
			drugList = drugService.QueryDrug(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int drugCount = drugList.size();
		drugObj_ShowText = new String[drugCount+1];
		drugObj_ShowText[0] = "不限制";
		for(int i=1;i<=drugCount;i++) { 
			drugObj_ShowText[i] = drugList.get(i-1).getDrugName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		drugObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, drugObj_ShowText);
		// 设置所用药品下拉列表的风格
		drugObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_drugObj.setAdapter(drugObj_adapter);
		// 添加事件Spinner事件监听
		spinner_drugObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionDrugUse.setDrugObj(drugList.get(arg2-1).getDrugId()); 
				else
					queryConditionDrugUse.setDrugObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_drugObj.setVisibility(View.VISIBLE);
		ET_useTime = (EditText) findViewById(R.id.ET_useTime);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionDrugUse.setUseTime(ET_useTime.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionDrugUse", queryConditionDrugUse);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
