package com.seuic.smartgateway ;  

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.seuic.adapter.CustomToast;
import com.seuic.adapter.EtcAdapter;
import com.seuic.add.AddEtc;
import com.seuic.devetc.IR_Custom1;
import com.seuic.devetc.IR_Custom2;
import com.seuic.devetc.RF_Curtain1;
import com.seuic.devetc.RF_Lamp;
import com.seuic.devetc.RF_Power;
import com.seuic.devetc.RF_Switch;
import com.seuic.devetc.RF_WH;


public class TabRF extends Activity {
	Button titleBtn,homeBtn;
	ImageView titlePic;
	
//	String mUid=null;

	ListView listViewRF;
	EtcAdapter rfAdapter;
//	SharedPreferences myPreferences;
//	SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabrf);
//		myPreferences= getSharedPreferences("devset", Activity.MODE_PRIVATE);


		homeBtn=(Button)findViewById(R.id.back);
		titlePic=(ImageView)findViewById(R.id.pic);
		titleBtn=(Button)findViewById(R.id.titleBtn);
		
    	homeBtn.setBackgroundResource(R.drawable.ep_logo);
    	titlePic.setImageResource(R.drawable.tab_rf);
    	titleBtn.setBackgroundResource(R.drawable.title_add);
		titleBtn.setOnClickListener(new OnClickListener()
		{	
			@Override	
			public void onClick(View source){	
				if(TabControl.mUid.equals("NULL")){
					CustomToast.showToast(getApplicationContext(),"无法添加遥控器，请先到Set界面进行设置", Toast.LENGTH_SHORT);		
					
				}else{
				 Intent intent = new Intent(TabRF.this, AddEtc.class);	
        		 intent.putExtra("uid", TabControl.mUid);
				 intent.putExtra("type", "rf");
				 startActivity(intent);	
				}
            }			
		});	
	
		listViewRF = (ListView)findViewById(R.id.listViewRF);
		listViewRF.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
//				itemsIR = {"TV", "AC","Media","STU","WH", "DVD","FAN","自定义1","自定义2"};
//				 itemsRF = {"Switch", "WH", "Lamp","Power","Curtain","自定义1","自定义2"};
				 String type=(String)rfAdapter.getItem(position).get("type");
				 int devid=Integer.parseInt(String.valueOf(rfAdapter.getItem(position).get("devid")));
				 Intent intent ;
				 if(type.equals("Switch")){
					 intent = new Intent(TabRF.this,RF_Switch.class);
				 }else if(type.equals("WH")){
					 intent = new Intent(TabRF.this,RF_WH.class);
				 }else if(type.equals("Lamp")){
					 intent = new Intent(TabRF.this,RF_Lamp.class);
				 }else if(type.equals("Curtain")){
					 intent = new Intent(TabRF.this,RF_Curtain1.class);
				 }else if(type.equals("Power")){
					 intent = new Intent(TabRF.this,RF_Power.class);
				 }else if(type.equals("CUSTOM1")){
					 Command.IrSelected = false;
					 intent = new Intent(TabRF.this,IR_Custom1.class);
				 }else {
					 Command.IrSelected = false;
					 intent = new Intent(TabRF.this,IR_Custom2.class);
				 }			
				 
				 intent.putExtra("uid",  TabControl.mUid);
				 intent.putExtra("devid", devid);
				 startActivity(intent);	
			}
			
		});
		
		listViewRF.setOnTouchListener(new OnTouchListener() {  
			
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
		        		 if(position1>=0&&position1<rfAdapter.getCount()){	       		
			        		 Log.e("leewoo", "右滑"+position1);
			        		 final int position=position1;
			        		 AlertDialog.Builder builder = new Builder(TabRF.this);
			        		 builder.setMessage(getResources().getString(R.string.deletedevinfo) +rfAdapter.getItem(position).get("type"));
			        		 builder.setTitle(R.string.deletetitle);
			        		 builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				        		@Override
				        		public void onClick(DialogInterface arg0, int arg1) {
				        			// TODO Auto-generated method stub		        		
					        		
					        		 TabControl.mSQLHelper.deleteList(TabControl.writeDB, Integer.parseInt(String.valueOf(rfAdapter.getItem(position).get("devid"))));
					        		 rfAdapter.remove(position);	
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
		
//		mUid=myPreferences.getString("uid", "NULL");	
		if (TabControl.mUid.equals("NULL")) {
//			Toast.makeText(getApplicationContext(),"设备为设置，请到Set界面添加设备", Toast.LENGTH_SHORT).show();	
//			finish();
		}
		Log.e("leewoo","mUid="+TabControl.mUid);
		Cursor cur=TabControl.mSQLHelper.seleteListClass(TabControl.writeDB, TabControl.mUid,"rf");

		List<Map<String, Object>> listItemsRF=new ArrayList<Map<String,Object>>();	
		for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
			Map<String,Object> listItem =new HashMap<String,Object>();	
		
			listItem.put("name", cur.getString(4));			
			listItem.put("devid", cur.getInt(1));
			
			String type=cur.getString(3);
			 listItem.put("type",type);	

			 //itemsRF = {"Switch", "WH", "Lamp","Curtain","自定义1","自定义2"}; 	
			 if(type.equals("Switch")){
				 listItem.put("icon", R.drawable.rf_logo_switch);
				 listItem.put("status2", R.drawable.rf_logo_on);
				 listItem.put("status", R.drawable.rf_logo_off);
			 }else if(type.equals("WH")){//wh
				 listItem.put("icon", R.drawable.ir_logo_wh);
				 listItem.put("status2", R.drawable.rf_logo_on);
				 listItem.put("status", R.drawable.rf_logo_off);
			 }else if(type.equals("Lamp")){//lamp
				 listItem.put("icon", R.drawable.rf_logo_lamp);
				 listItem.put("status2", R.drawable.rf_logo_on);
				 listItem.put("status", R.drawable.rf_logo_off);
			 }else if(type.equals("Curtain")){
				 listItem.put("icon", R.drawable.rf_logo_curtain);
				 listItem.put("status2", R.drawable.rf_logo_open);
				 listItem.put("status", R.drawable.rf_logo_close);
			 }else if(type.equals("Power")){
				 listItem.put("icon", R.drawable.rf_logo_power);
				 listItem.put("status2", R.drawable.rf_logo_on);
				 listItem.put("status", R.drawable.rf_logo_off);
			 }else if(type.equals("CUSTOM1")){
				 listItem.put("icon",R.drawable.ir_logo_custom );
				 listItem.put("status2", R.drawable.rf_logo_on);
				 listItem.put("status", R.drawable.rf_logo_off);
			 }else {
				 listItem.put("icon",R.drawable.ir_logo_custom );
				 listItem.put("status2", R.drawable.rf_logo_on);
				 listItem.put("status", R.drawable.rf_logo_off);
			 }	

			listItemsRF.add(listItem);
		}	
		cur.close();
		rfAdapter=new EtcAdapter(this, listItemsRF);
		listViewRF.setAdapter(rfAdapter);
	}
	
	private long mExitTime;
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 Log.e("TabControl","get the keyback");
       if (keyCode == KeyEvent.KEYCODE_BACK) {
      	     Log.e("TabControl","get keyback");
				if ((System.currentTimeMillis() - mExitTime) > 2000) {
                       Object mHelperUtils;
                       Toast.makeText(this, "press again to exit the app", Toast.LENGTH_SHORT).show();
                       mExitTime = System.currentTimeMillis();

               } else {
              	 finish();
//              	 System.exit(0);
               }
               return true;
       }
       return super.onKeyDown(keyCode, event);
}


}
  
