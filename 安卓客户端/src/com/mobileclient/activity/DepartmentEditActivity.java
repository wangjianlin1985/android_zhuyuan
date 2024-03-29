package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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

public class DepartmentEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明科室编号TextView
	private TextView TV_departmentNo;
	// 声明科室名称输入框
	private EditText ET_departmentName;
	// 出版成立日期控件
	private DatePicker dp_bornDate;
	// 声明负责人输入框
	private EditText ET_chargeMan;
	protected String carmera_path;
	/*要保存的科室信息*/
	Department department = new Department();
	/*科室管理业务逻辑层*/
	private DepartmentService departmentService = new DepartmentService();

	private String departmentNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.department_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑科室信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_departmentNo = (TextView) findViewById(R.id.TV_departmentNo);
		ET_departmentName = (EditText) findViewById(R.id.ET_departmentName);
		dp_bornDate = (DatePicker)this.findViewById(R.id.dp_bornDate);
		ET_chargeMan = (EditText) findViewById(R.id.ET_chargeMan);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		departmentNo = extras.getString("departmentNo");
		/*单击修改科室按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取科室名称*/ 
					if(ET_departmentName.getText().toString().equals("")) {
						Toast.makeText(DepartmentEditActivity.this, "科室名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_departmentName.setFocusable(true);
						ET_departmentName.requestFocus();
						return;	
					}
					department.setDepartmentName(ET_departmentName.getText().toString());
					/*获取出版日期*/
					Date bornDate = new Date(dp_bornDate.getYear()-1900,dp_bornDate.getMonth(),dp_bornDate.getDayOfMonth());
					department.setBornDate(new Timestamp(bornDate.getTime()));
					/*验证获取负责人*/ 
					if(ET_chargeMan.getText().toString().equals("")) {
						Toast.makeText(DepartmentEditActivity.this, "负责人输入不能为空!", Toast.LENGTH_LONG).show();
						ET_chargeMan.setFocusable(true);
						ET_chargeMan.requestFocus();
						return;	
					}
					department.setChargeMan(ET_chargeMan.getText().toString());
					/*调用业务逻辑层上传科室信息*/
					DepartmentEditActivity.this.setTitle("正在更新科室信息，稍等...");
					String result = departmentService.UpdateDepartment(department);
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
	    department = departmentService.GetDepartment(departmentNo);
		this.TV_departmentNo.setText(departmentNo);
		this.ET_departmentName.setText(department.getDepartmentName());
		Date bornDate = new Date(department.getBornDate().getTime());
		this.dp_bornDate.init(bornDate.getYear() + 1900,bornDate.getMonth(), bornDate.getDate(), null);
		this.ET_chargeMan.setText(department.getChargeMan());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
