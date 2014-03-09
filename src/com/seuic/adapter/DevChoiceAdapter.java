package com.seuic.adapter;

import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seuic.net.TUTKClient;
import com.seuic.smartgateway.R;
import com.seuic.smartgateway.SetupDev;
import com.seuic.smartgateway.TabControl;


public class DevChoiceAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private Context context; 
    int  currentID = -1;
    private ProgressDialog progressDialog; 
//    StatusListener mStatusListener;
    private float x,ux;
    public DevChoiceAdapter(Context context, List<Map<String, Object>> data) {
        this.context = context;
        this.data = data;       
    }

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
    public int getCount() {
        return data.size();
    }

    @Override
	public Map<String, Object> getItem(int position) {
		// TODO Auto-generated method stub
    	return data.get(position);
	}

	@Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    
		final Map<String, Object> item =getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.item_list_dev, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.summary=(TextView) convertView.findViewById(R.id.summary);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.status = (ImageView) convertView.findViewById(R.id.status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(position==currentID)        	
            holder.status.setImageResource(R.drawable.dev_on);
        else        	
        	holder.status.setImageResource(R.drawable.dev_off);
    
        holder.title.setText(item.get("name").toString());
        holder.summary.setText(item.get("uid").toString());
        holder.icon.setImageResource(R.drawable.dev_icon);        
        convertView.setOnTouchListener(new OnTouchListener() {  
        	public boolean onTouch(View v, MotionEvent event) {  
//        	final ViewHolder holder = (ViewHolder) v.getTag();  
        	//当按下时处理  
        	if (event.getAction() == MotionEvent.ACTION_DOWN) {  
        	//设置背景为选中状态  
//        	v.setBackgroundResource(R.drawable.mm_listitem_pressed);  
	        	//获取按下时的x轴坐标  
	        	x = event.getX();  
        	} else if (event.getAction() == MotionEvent.ACTION_UP) {// 松开处理  
        	//设置背景为未选中正常状态  
//        	v.setBackgroundResource(R.drawable.mm_listitem_simple);  
        	//获取松开时的x坐标  
        	ux = event.getX();          	
        	//按下和松开绝对值差当大于20时显示删除按钮，否则不显示  
	        	if (Math.abs(x - ux) > 25) {  
//	        		 Toast.makeText(context,"右滑"+position, Toast.LENGTH_SHORT).show();	        		
	        		 Log.e("leewoo", "右滑"+position);

	        		 AlertDialog.Builder builder = new Builder(context);
	        		 builder.setMessage(context.getResources().getString(R.string.deletedevinfo) +data.get(position).get("uid"));
	        		 builder.setTitle(R.string.deletetitle);
	        		 builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
		        		@Override
		        		public void onClick(DialogInterface arg0, int arg1) {
		        			// TODO Auto-generated method stub
		        			if(position>=0&&position<data.size()){
		        			if(currentID==position){
		        				currentID=-1;	
		        				TabControl.mUid="NULL";//删除置空
//		        				SetupDev.editor.putString("uid","NULL");
//		        				SetupDev.editor.commit();
		        			} 
		        			TabControl.mSQLHelper.deleteSetup(TabControl.writeDB, data.get(position).get("uid").toString());
				 			data.remove(position);	
			        		notifyDataSetChanged();
		        			}
		        		}
		        	});
	        		 builder.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
		        		@Override
		        		 public void onClick(DialogInterface dialog, int which) {
		        		 dialog.dismiss();
		        		  
		        		}
		        	});
	        		 builder.create().show();
	        	   }
	        		 
        	} else if (event.getAction() == MotionEvent.ACTION_MOVE) {//当滑动时背景为选中状态  
//        	v.setBackgroundResource(R.drawable.mm_listitem_pressed);  
//        		 Log.e("leewoo", "onTouch ACTION_MOVE: "+position);
        	} else {//其他模式  
        	//设置背景为未选中正常状态  
//        	v.setBackgroundResource(R.drawable.mm_listitem_simple);  
        	}  
        	return true;  
         }  
        }); 
        holder.status.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				currentID=position;  
				String uid=data.get(position).get("uid").toString();
//				SetupDev.editor.putString("uid",uid);
//				SetupDev.editor.commit();
				TabControl.mUid=uid;
				showProgressDialog(uid);
				notifyDataSetChanged();
			}
		});
        return convertView;
    }

    
    static class ViewHolder {
        TextView title;
        TextView summary;
        ImageView icon;
        ImageView status;
    }
    
	public void setItemChecked(int setID) {
		currentID = setID;
	}
	
	private Handler handler = new Handler(){ 
        @Override  
        public void handleMessage(Message msg) {  
        	if(0==msg.what)
        	{
        		CustomToast.showToast(context, "Connect success", Toast.LENGTH_SHORT); 
        		Cursor cursor=TabControl.mSQLHelper.seleteSetup(TabControl.writeDB,TabControl.mUid);
//        		Log.e("leewoo", "Tabset---onStart->cur:"+cursor.getCount()+" mUid:"+TabControl.mUid);
        		if(cursor.getCount()>0){
        			int fah=cursor.getInt(3);
        			int hour=cursor.getInt(4);   
        			int timezone=cursor.getInt(5);  
        		
        			TUTKClient.setTempMode(fah);
        			TUTKClient.setHourMode(hour);
        			String a[]=context.getResources().getStringArray(R.array.timezone_entries);   
        			String[] ss=new String[2];
                	ss=a[timezone].split("UTC"); 
                	ss[1]=ss[1].replace("+",""); 
                	int i=Integer.parseInt(ss[1]);  
                	Log.e("Device ", "timezone length="+ timezone);
                	TUTKClient.setTimeZone(i);
        		}	
        		
        	}else{
        		CustomToast.showToast(context, "Can not connect to device, please check your device or if has connect to a wireless network", Toast.LENGTH_LONG); 
        		currentID=-1;
//        		SetupDev.editor.putString("uid","NULL");
//				SetupDev.editor.commit();
				TabControl.mUid="NULL";//未连接置空
        		notifyDataSetChanged();
        	}
//            //关闭ProgressDialog  
            progressDialog.dismiss(); 

        }};  
	 public void showProgressDialog(final String uid){  
		 progressDialog = ProgressDialog.show(context,"" , "Connecting...", true, false); 
		 new Thread(){        
		     @Override  
		     public void run() {  
		    Message startMsg=new Message();
//		       TUTKClient.stop();
			   if(TUTKClient.start(uid))				 
			   {
				   startMsg.what=0;
			   }else{
				   startMsg.what=1;
			   }			   
		       handler.sendMessage(startMsg);  
		     }
		   }.start();      
	 }
	  

}
