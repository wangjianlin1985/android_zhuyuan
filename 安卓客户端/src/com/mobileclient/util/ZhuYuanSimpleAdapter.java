package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.PatientService;
import com.mobileclient.service.DoctorService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class ZhuYuanSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public ZhuYuanSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.zhuyuan_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_patientObj = (TextView)convertView.findViewById(R.id.tv_patientObj);
	  holder.tv_age = (TextView)convertView.findViewById(R.id.tv_age);
	  holder.tv_inDate = (TextView)convertView.findViewById(R.id.tv_inDate);
	  holder.tv_inDays = (TextView)convertView.findViewById(R.id.tv_inDays);
	  holder.tv_bedNum = (TextView)convertView.findViewById(R.id.tv_bedNum);
	  holder.tv_doctorObj = (TextView)convertView.findViewById(R.id.tv_doctorObj);
	  /*设置各个控件的展示内容*/
	  holder.tv_patientObj.setText("病人：" + (new PatientService()).GetPatient(Integer.parseInt(mData.get(position).get("patientObj").toString())).getName());
	  holder.tv_age.setText("年龄：" + mData.get(position).get("age").toString());
	  try {holder.tv_inDate.setText("住院日期：" + mData.get(position).get("inDate").toString().substring(0, 10));} catch(Exception ex){}
	  holder.tv_inDays.setText("入住天数：" + mData.get(position).get("inDays").toString());
	  holder.tv_bedNum.setText("床位号：" + mData.get(position).get("bedNum").toString());
	  holder.tv_doctorObj.setText("负责医生：" + (new DoctorService()).GetDoctor(mData.get(position).get("doctorObj").toString()).getName());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_patientObj;
    	TextView tv_age;
    	TextView tv_inDate;
    	TextView tv_inDays;
    	TextView tv_bedNum;
    	TextView tv_doctorObj;
    }
} 
