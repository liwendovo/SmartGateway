package com.seuic.smartgateway ;  

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.seuic.adapter.CustomToast;
import com.seuic.adapter.DevChoiceAdapter;
import com.seuic.adapter.EtcAdapter;
import com.seuic.add.AddEtc;
import com.seuic.devetc.IR_AC;
import com.seuic.devetc.IR_Custom1;
import com.seuic.devetc.IR_Custom2;
import com.seuic.devetc.IR_DVD;
import com.seuic.devetc.IR_FAN;
import com.seuic.devetc.IR_Media;
import com.seuic.devetc.IR_STU;
import com.seuic.devetc.IR_TV;
import com.seuic.devetc.IR_WH;
import com.seuic.net.TUTKClient;

public class TabIR extends Activity {
	Button titleBtn,homeBtn;
	ImageView titlePic;	
//	String mUid=null;	
	private Context context; 
	private ProgressDialog progressDialog; 
	ListView listViewIR;
	EtcAdapter irAdapter;
	SharedPreferences myPre;	
//	SharedPreferences myPreferences;
//	SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabir);		
		homeBtn=(Button)findViewById(R.id.back);
		titlePic=(ImageView)findViewById(R.id.pic);
		titleBtn=(Button)findViewById(R.id.titleBtn);
		
    	homeBtn.setBackgroundResource(R.drawable.ep_logo);
    	titlePic.setImageResource(R.drawable.tab_ir);
    	titleBtn.setBackgroundResource(R.drawable.title_add);
    	

		
		
		if (!TabControl.mUid.equals("NULL")) {	 
			Log.e("TabIR","connecting mUid is not NULL");
			showProgressDialog(TabControl.mUid);
		}else{
			Log.e("TabIR","connecting mUid is NULL");
		}
    	
    	
    	
//		myPreferences= getSharedPreferences("devset", Activity.MODE_PRIVATE);
		titleBtn.setOnClickListener(new OnClickListener()
		{		
			public void onClick(View source){	
				if(TabControl.mUid.equals("NULL")){
//					Toast.makeText(getApplicationContext(),"无法添加遥控器，请先到Set界面进行设置", Toast.LENGTH_SHORT).show();		
					CustomToast.showToast(getApplicationContext(),"can't add remote controller，please go to setup", Toast.LENGTH_SHORT);
				}else{
				 Intent intent = new Intent(TabIR.this, AddEtc.class);	
				 intent.putExtra("uid", TabControl.mUid);
				 intent.putExtra("type", "ir");
				 startActivity(intent);	  
				}
			}			
		});	
		

		
		listViewIR = (ListView)findViewById(R.id.listViewIR);
		
		listViewIR.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
//				itemsIR = {"TV", "AC","Media","STU","WH", "DVD","FAN","自定义1","自定义2"};
//				 itemsRF = {"Switch", "WH", "Lamp","Curtain","自定义1","自定义2"};
				 String type=(String)irAdapter.getItem(position).get("type");
				 Log.e("leewoo", "type="+type);
				 int devid=Integer.parseInt(String.valueOf(irAdapter.getItem(position).get("devid")));
				 Intent intent ;
				 if(type.equals("TV")){
					 intent = new Intent(TabIR.this,IR_TV.class);	
				 }else if(type.equals("AC")){
					 intent = new Intent(TabIR.this,IR_AC.class);
				 }else if(type.equals("MEDIA")){
					 intent = new Intent(TabIR.this,IR_Media.class);
				 }else if(type.equals("STU")){
					 intent = new Intent(TabIR.this,IR_STU.class);
				 }else if(type.equals("WH")){
					 intent = new Intent(TabIR.this,IR_WH.class);
				 }else if(type.equals("DVD")){
					 intent = new Intent(TabIR.this,IR_DVD.class);
				 }else if(type.equals("FAN")){
					 intent = new Intent(TabIR.this,IR_FAN.class);
				 }else if(type.equals("CUSTOM1")){
					 intent = new Intent(TabIR.this, IR_Custom1.class);
				 }else {
					 intent = new Intent(TabIR.this,IR_Custom2.class);
				 }	
				 
				 
				 intent.putExtra("uid",  TabControl.mUid);
				 intent.putExtra("devid", devid);
				 intent.putExtra("devType", "ir");
				 startActivity(intent);	
			}
			});
		listViewIR.setOnTouchListener(new OnTouchListener() {  
			
			 private float x,ux,y,uy;
	        	public boolean onTouch(View v, MotionEvent event) {  
	        		
	        	//当按下时处理  
	        	if (event.getAction() == MotionEvent.ACTION_DOWN) {  
	        	//设置背景为选中状态  
//	        	v.setBackgroundResource(R.drawable.mm_listitem_pressed);  
		        	//获取按下时的x轴坐标  
		        	x = event.getX();  
		        	y = event.getY();  
		        	return true;
	        	} else if (event.getAction() == MotionEvent.ACTION_UP) {// 松开处理  
	        	//设置背景为未选中正常状态  
//	        	v.setBackgroundResource(R.drawable.mm_listitem_simple);  
	        	//获取松开时的x坐标  
	        	ux = event.getX(); 
	        	uy = event.getY();
	        	int position1 = ((ListView) v).pointToPosition((int) x, (int) y);  
                int position2 = ((ListView) v).pointToPosition((int) ux,(int) uy);
                Log.e("leewoo", "onTouch :"+position1+" "+position2);
	        	//按下和松开绝对值差当大于20时显示删除按钮，否则不显示  
		        	if (position1 == position2 &&Math.abs(x - ux) > 20) {  
		        		 if(position1>=0&&position1<irAdapter.getCount()){	
			        		 Log.e("leewoo", "右滑"+position1);
			        		 final int position=position1;
			        		 AlertDialog.Builder builder = new Builder(TabIR.this);
			        		 builder.setMessage(getResources().getString(R.string.deletedevinfo) +irAdapter.getItem(position).get("type"));
			        		 builder.setTitle(R.string.deletetitle);
			        		 builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				        		@Override
				        		public void onClick(DialogInterface arg0, int arg1) {
				        			// TODO Auto-generated method stub	
					        		 TabControl.mSQLHelper.deleteList(TabControl.writeDB, Integer.parseInt(String.valueOf(irAdapter.getItem(position).get("devid"))));
					        		 irAdapter.remove(position);	
					        		 //数据库
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
		        		 return false;  
		        	 } 
	        	} 
//	        	else if (event.getAction() == MotionEvent.ACTION_MOVE) {//当滑动时背景为选中状态  
////	        	v.setBackgroundResource(R.drawable.mm_listitem_pressed);  
////	        		 Log.e("leewoo", "onTouch ACTION_MOVE: "+position);
//	        	} else {//其他模式  
//	        	//设置背景为未选中正常状态  
////	        	v.setBackgroundResource(R.drawable.mm_listitem_simple);  
//	        	}  
	        	return false;  
	         }  
	        }); 
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.e("TabIR","TabIR-->onStart");
//		mUid=myPreferences.getString("uid", "NULL");	
//		if (mUid.equals("NULL")) {
//			Toast.makeText(getApplicationContext(),"设备为设置，请到Set界面添加设备", Toast.LENGTH_SHORT).show();	
//			finish();
//		}
		
//		myPre= getSharedPreferences("devset", Activity.MODE_PRIVATE);
//		String mUid = myPre.getString("uid", "NULL");	
//		if (!mUid.equals("NULL")) {	 
//			Log.e("TabIR","connecting mUid is not NULL");
//			showProgressDialog(mUid);
//		}else{
//			Log.e("TabIR","connecting mUid is not NULL");
//		}
		
		
		
		Log.e("TabIR","mUid="+TabControl.mUid);
		Cursor cur=TabControl.mSQLHelper.seleteListClass(TabControl.writeDB, TabControl.mUid,"ir");
		Log.e("leewoo","count="+cur.getCount());
//		if(0==cur.getCount()){
//			Log.e("leewoo","count="+0);
//			return;
//		}			
		List<Map<String, Object>> listItemsIR=new ArrayList<Map<String,Object>>();	
		for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
			Map<String,Object> listItem =new HashMap<String,Object>();			
			listItem.put("name", cur.getString(4));
			String type=cur.getString(3);
			listItem.put("irflag", "ir");
//			itemsIR = {"TV", "AC","Media","STU","WH", "DVD","FAN","自定义1","自定义2"};
			 listItem.put("type",type);
			 if(type.equals("TV")){
				 listItem.put("icon", R.drawable.ir_logo_tv);
				 listItem.put("status", R.drawable.ir_logo_close);
			 }else if(type.equals("AC")){
				 listItem.put("icon", R.drawable.ir_logo_ac); 
				 listItem.put("status2", R.drawable.rf_logo_on);
				 listItem.put("status", R.drawable.rf_logo_off);
			 }else if(type.equals("MEDIA")){//media
				 listItem.put("icon", R.drawable.ir_logo_media);
				 listItem.put("status", R.drawable.ir_logo_close);
			 }else if(type.equals("STU")){
				 listItem.put("icon", R.drawable.ir_logo_stu);
				 listItem.put("status", R.drawable.ir_logo_close);			
			 }else if(type.equals("WH")){
				 listItem.put("icon",R.drawable.ir_logo_wh );
				 listItem.put("status2", R.drawable.ir_logo_up);
				 listItem.put("status", R.drawable.ir_logo_down);
			 }else if(type.equals("DVD")){
				 listItem.put("icon", R.drawable.ir_logo_dvd);
				 listItem.put("status", R.drawable.ir_logo_close);
			 }else if(type.equals("FAN")){
				 listItem.put("icon",  R.drawable.ir_logo_fan1);
				 listItem.put("status", R.drawable.ir_logo_close);
			 }else if(type.equals("CUSTOM1")){
				 listItem.put("icon",  R.drawable.ir_logo_custom);
				 listItem.put("status", R.drawable.ir_logo_close);
			 }else {
				 listItem.put("icon",R.drawable.ir_logo_custom);
				 listItem.put("status", R.drawable.ir_logo_close);
			 }	
			listItem.put("devid", cur.getInt(1));
			listItemsIR.add(listItem);
		}	
		cur.close();
		//list排序  需要优化
//		if (!listItemsIR.isEmpty()) {    
//	    	 Collections.sort(listItemsIR, new Comparator<Map<String, Object>>() {
//	     	@Override
//	     	public int compare(Map<String, Object> object1,
//	     	Map<String, Object> object2) {
//			//根据文本排序
//	        return ((String) object1.get("name")).compareTo((String) object2.get("name"));
//	     	}    
//	       });    
//	     }	
		irAdapter=new EtcAdapter(this, listItemsIR);
		listViewIR.setAdapter(irAdapter);
	}
	

	private long mExitTime;
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 Log.e("TabControl","get the keyback");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
       	     Log.e("TabControl","get keyback");
				if ((System.currentTimeMillis() - mExitTime) > 2000) {
                        Object mHelperUtils;
                        Toast.makeText(this, "press again to exit the app", TabControl.time).show();
                        mExitTime = System.currentTimeMillis();

                } else {
//               	 finish();
               	System.exit(0);
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
}
	
	
	 public void showProgressDialog(final String uid){  
		 progressDialog = ProgressDialog.show(this,"" , "Connecting...", true, false); 
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
	 
	 private Handler handler = new Handler(){ 
	        @Override  
	        public void handleMessage(Message msg) {  
	        	if(0==msg.what)
	        	{
	        		CustomToast.showToast(getApplicationContext(), "Connect success", Toast.LENGTH_LONG); 
	        		Cursor cursor=TabControl.mSQLHelper.seleteSetup(TabControl.writeDB,TabControl.mUid);
//	        		Log.e("leewoo", "Tabset---onStart->cur:"+cursor.getCount()+" mUid:"+TabControl.mUid);
	        		if(cursor.getCount()>0){
	        			int fah=cursor.getInt(3);
	        			int hour=cursor.getInt(4);   
	        			int timezone=cursor.getInt(5);  
	        			Log.e("TabIR ", "fah="+ fah);
	        			TUTKClient.setTempMode(fah);
	        			TUTKClient.setHourMode(hour);
	        			String a[]=getApplicationContext().getResources().getStringArray(R.array.timezone_entries);   
	        			String[] ss=new String[2];
	                	ss=a[timezone].split("UTC"); 
	                	ss[1]=ss[1].replace("+",""); 
	                	float i=4*Float.parseFloat(ss[1]);  
	                	Log.e("Device ", "timezone length="+ timezone);
	                	TUTKClient.setTimeZone((int)i);
	        		}	
	        		
	        	}else{
	        		CustomToast.showToast(getApplicationContext(), "Can not connect to device, please check your device or if has connect to a wireless network", Toast.LENGTH_LONG); 
	        		DevChoiceAdapter.currentID=-1;
	        		TabControl.editor.putString("uid","NULL");
	        		TabControl.editor.commit();
					TabControl.mUid="NULL";//未连接置空
//	        		notifyDataSetChanged();
	        	}
//	            //关闭ProgressDialog  
	            progressDialog.dismiss(); 

	        }};  
	 

}
  
