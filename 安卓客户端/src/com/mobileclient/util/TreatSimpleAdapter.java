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

public class TreatSimpleAdapter extends SimpleAdapter { 
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

    public TreatSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.treat_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_treatName = (TextView)convertView.findViewById(R.id.tv_treatName);
	  holder.tv_patientObj = (TextView)convertView.findViewById(R.id.tv_patientObj);
	  holder.tv_diagnosis = (TextView)convertView.findViewById(R.id.tv_diagnosis);
	  holder.tv_treatContent = (TextView)convertView.findViewById(R.id.tv_treatContent);
	  holder.tv_treatResult = (TextView)convertView.findViewById(R.id.tv_treatResult);
	  holder.tv_doctorObj = (TextView)convertView.findViewById(R.id.tv_doctorObj);
	  /*设置各个控件的展示内容*/
	  holder.tv_treatName.setText("治疗名称：" + mData.get(position).get("treatName").toString());
	  holder.tv_patientObj.setText("病人：" + (new PatientService()).GetPatient(Integer.parseInt(mData.get(position).get("patientObj").toString())).getName());
	  holder.tv_diagnosis.setText("诊断情况：" + mData.get(position).get("diagnosis").toString());
	  holder.tv_treatContent.setText("治疗记录：" + mData.get(position).get("treatContent").toString());
	  holder.tv_treatResult.setText("治疗结果：" + mData.get(position).get("treatResult").toString());
	  holder.tv_doctorObj.setText("主治医生：" + (new DoctorService()).GetDoctor(mData.get(position).get("doctorObj").toString()).getName());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_treatName;
    	TextView tv_patientObj;
    	TextView tv_diagnosis;
    	TextView tv_treatContent;
    	TextView tv_treatResult;
    	TextView tv_doctorObj;
    }
} 
