package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Doctor;
import com.mobileclient.domain.Department;
import com.mobileclient.service.DepartmentService;

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
public class DoctorQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明医生工号输入框
	private EditText ET_doctorNo;
	// 声明所在科室下拉框
	private Spinner spinner_departmentObj;
	private ArrayAdapter<String> departmentObj_adapter;
	private static  String[] departmentObj_ShowText  = null;
	private List<Department> departmentList = null; 
	/*科室管理业务逻辑层*/
	private DepartmentService departmentService = new DepartmentService();
	// 声明姓名输入框
	private EditText ET_name;
	// 声明身份证号输入框
	private EditText ET_cardNo;
	// 出生日期控件
	private DatePicker dp_birthday;
	private CheckBox cb_birthday;
	// 声明联系电话输入框
	private EditText ET_telephone;
	/*查询过滤条件保存到这个对象中*/
	private Doctor queryConditionDoctor = new Doctor();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.doctor_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置医生查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_doctorNo = (EditText) findViewById(R.id.ET_doctorNo);
		spinner_departmentObj = (Spinner) findViewById(R.id.Spinner_departmentObj);
		// 获取所有的科室
		try {
			departmentList = departmentService.QueryDepartment(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int departmentCount = departmentList.size();
		departmentObj_ShowText = new String[departmentCount+1];
		departmentObj_ShowText[0] = "不限制";
		for(int i=1;i<=departmentCount;i++) { 
			departmentObj_ShowText[i] = departmentList.get(i-1).getDepartmentName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		departmentObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, departmentObj_ShowText);
		// 设置所在科室下拉列表的风格
		departmentObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_departmentObj.setAdapter(departmentObj_adapter);
		// 添加事件Spinner事件监听
		spinner_departmentObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionDoctor.setDepartmentObj(departmentList.get(arg2-1).getDepartmentNo()); 
				else
					queryConditionDoctor.setDepartmentObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_departmentObj.setVisibility(View.VISIBLE);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_cardNo = (EditText) findViewById(R.id.ET_cardNo);
		dp_birthday = (DatePicker) findViewById(R.id.dp_birthday);
		cb_birthday = (CheckBox) findViewById(R.id.cb_birthday);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionDoctor.setDoctorNo(ET_doctorNo.getText().toString());
					queryConditionDoctor.setName(ET_name.getText().toString());
					queryConditionDoctor.setCardNo(ET_cardNo.getText().toString());
					if(cb_birthday.isChecked()) {
						/*获取出生日期*/
						Date birthday = new Date(dp_birthday.getYear()-1900,dp_birthday.getMonth(),dp_birthday.getDayOfMonth());
						queryConditionDoctor.setBirthday(new Timestamp(birthday.getTime()));
					} else {
						queryConditionDoctor.setBirthday(null);
					} 
					queryConditionDoctor.setTelephone(ET_telephone.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionDoctor", queryConditionDoctor);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
