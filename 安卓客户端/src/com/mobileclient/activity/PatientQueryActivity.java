package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Patient;

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
public class PatientQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明姓名输入框
	private EditText ET_name;
	// 出生日期控件
	private DatePicker dp_birthday;
	private CheckBox cb_birthday;
	// 声明身份证号输入框
	private EditText ET_cardNo;
	// 声明籍贯输入框
	private EditText ET_originPlace;
	// 声明联系电话输入框
	private EditText ET_telephone;
	/*查询过滤条件保存到这个对象中*/
	private Patient queryConditionPatient = new Patient();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.patient_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置病人查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_name = (EditText) findViewById(R.id.ET_name);
		dp_birthday = (DatePicker) findViewById(R.id.dp_birthday);
		cb_birthday = (CheckBox) findViewById(R.id.cb_birthday);
		ET_cardNo = (EditText) findViewById(R.id.ET_cardNo);
		ET_originPlace = (EditText) findViewById(R.id.ET_originPlace);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionPatient.setName(ET_name.getText().toString());
					if(cb_birthday.isChecked()) {
						/*获取出生日期*/
						Date birthday = new Date(dp_birthday.getYear()-1900,dp_birthday.getMonth(),dp_birthday.getDayOfMonth());
						queryConditionPatient.setBirthday(new Timestamp(birthday.getTime()));
					} else {
						queryConditionPatient.setBirthday(null);
					} 
					queryConditionPatient.setCardNo(ET_cardNo.getText().toString());
					queryConditionPatient.setOriginPlace(ET_originPlace.getText().toString());
					queryConditionPatient.setTelephone(ET_telephone.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionPatient", queryConditionPatient);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
