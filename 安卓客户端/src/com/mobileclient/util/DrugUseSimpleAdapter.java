package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.TreatService;
import com.mobileclient.service.DrugService;
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

public class DrugUseSimpleAdapter extends SimpleAdapter { 
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

    public DrugUseSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.druguse_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_treatObj = (TextView)convertView.findViewById(R.id.tv_treatObj);
	  holder.tv_drugObj = (TextView)convertView.findViewById(R.id.tv_drugObj);
	  holder.tv_drugCount = (TextView)convertView.findViewById(R.id.tv_drugCount);
	  holder.tv_drugMoney = (TextView)convertView.findViewById(R.id.tv_drugMoney);
	  holder.tv_useTime = (TextView)convertView.findViewById(R.id.tv_useTime);
	  /*设置各个控件的展示内容*/
	  holder.tv_treatObj.setText("治疗名称：" + (new TreatService()).GetTreat(Integer.parseInt(mData.get(position).get("treatObj").toString())).getTreatName());
	  holder.tv_drugObj.setText("所用药品：" + (new DrugService()).GetDrug(Integer.parseInt(mData.get(position).get("drugObj").toString())).getDrugName());
	  holder.tv_drugCount.setText("用药数量：" + mData.get(position).get("drugCount").toString());
	  holder.tv_drugMoney.setText("药品费用：" + mData.get(position).get("drugMoney").toString());
	  holder.tv_useTime.setText("用药时间：" + mData.get(position).get("useTime").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_treatObj;
    	TextView tv_drugObj;
    	TextView tv_drugCount;
    	TextView tv_drugMoney;
    	TextView tv_useTime;
    }
} 
