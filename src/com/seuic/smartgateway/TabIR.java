package com.seuic.smartgateway ;  

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.seuic.adapter.EtcAdapter;
import com.seuic.add.AddEtc;
import com.seuic.devetc.IR_AC;
import com.seuic.devetc.IR_DVD;
import com.seuic.devetc.IR_FAN;
import com.seuic.devetc.IR_Media;
import com.seuic.devetc.IR_STU;
import com.seuic.devetc.IR_Selfdefine1;
import com.seuic.devetc.IR_Selfdefine2;
import com.seuic.devetc.IR_TV;
import com.seuic.devetc.IR_WH;
import com.seuic.swipelistview.BaseSwipeListViewListener;
import com.seuic.swipelistview.SwipeListView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TabIR extends Activity {
	Button titleBtn,homeBtn;
	TextView titleTxt;
	String mUid=null;
	List<Map<String, Object>> listItemsIR;
	SwipeListView listViewIR;
	EtcAdapter irAdapter;
	SharedPreferences myPreferences;
	SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabir);		
		homeBtn=(Button)findViewById(R.id.leftBtn);
		titleTxt = (TextView)findViewById(R.id.titleTxt);
		titleBtn=(Button)findViewById(R.id.rightBtn);	
		homeBtn.setText("Home");
		titleTxt.setText("IR");
		titleBtn.setText("ADD");
		myPreferences= getSharedPreferences("devset", Activity.MODE_PRIVATE);
		titleBtn.setOnClickListener(new OnClickListener()
		{		
			public void onClick(View source){				 
				 Intent intent = new Intent(TabIR.this, AddEtc.class);	
				 intent.putExtra("uid", mUid);
				 intent.putExtra("type", "ir");
				 startActivity(intent);	            	
			}			
		});	
		
		homeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		listViewIR = (SwipeListView)findViewById(R.id.listViewIR);
		listViewIR.setSwipeListViewListener(new BaseSwipeListViewListener() {
			@Override
			public void onClickFrontView(int position) {
				 String type=(String)listItemsIR.get(position).get("content");
				 int devid=Integer.parseInt(String.valueOf(listItemsIR.get(position).get("devid")));
				 Intent intent ;
				 if(type.equals(ControlBox.itemsIR[0])){
					 intent = new Intent(TabIR.this,IR_TV.class);	
				 }else if(type.equals(ControlBox.itemsIR[1])){
					 intent = new Intent(TabIR.this,IR_AC.class);
				 }else if(type.equals(ControlBox.itemsIR[2])){
					 intent = new Intent(TabIR.this,IR_Media.class);
				 }else if(type.equals(ControlBox.itemsIR[3])){
					 intent = new Intent(TabIR.this,IR_STU.class);
				 }else if(type.equals(ControlBox.itemsIR[4])){
					 intent = new Intent(TabIR.this,IR_WH.class);
				 }else if(type.equals(ControlBox.itemsIR[5])){
					 intent = new Intent(TabIR.this,IR_DVD.class);
				 }else if(type.equals(ControlBox.itemsIR[6])){
					 intent = new Intent(TabIR.this,IR_FAN.class);
				 }else if(type.equals(ControlBox.itemsIR[7])){
					 intent = new Intent(TabIR.this,IR_Selfdefine1.class);
				 }else {
					 intent = new Intent(TabIR.this,IR_Selfdefine2.class);
				 }						 
				 intent.putExtra("uid",  mUid);
				 intent.putExtra("devid", devid);
				 startActivity(intent);	
			}

			@Override
			public void onClickBackView(int position) {
				listViewIR.closeOpenedItems();// 关闭打开的项
			}
		});
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mUid=myPreferences.getString("uid", "NULL");	
		if (mUid.equals("NULL")) {
			Toast.makeText(getApplicationContext(),"设备为设置，请到Set界面添加设备", Toast.LENGTH_SHORT).show();		
		}
		Log.e("leewoo","mUid="+mUid);
		Cursor cur=ControlBox.mSQLHelper.seleteListClass(ControlBox.writeDB, mUid,"ir");
		Log.e("leewoo","count="+cur.getCount());
		if(0==cur.getCount()){
			Log.e("leewoo","count="+0);
			return;
		}			
		if(listItemsIR!=null){
			listItemsIR.clear();
		}
		listItemsIR=new ArrayList<Map<String,Object>>();	
		for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
			Map<String,Object> listItem =new HashMap<String,Object>();			
			listItem.put("title", cur.getString(4));
			listItem.put("content", cur.getString(3));
			listItem.put("devid", cur.getInt(1));
			listItemsIR.add(listItem);
		}	
		cur.close();
		//list排序  需要优化
		if (!listItemsIR.isEmpty()) {    
	    	 Collections.sort(listItemsIR, new Comparator<Map<String, Object>>() {
	     	@Override
	     	public int compare(Map<String, Object> object1,
	     	Map<String, Object> object2) {
			//根据文本排序
	        return ((String) object1.get("title")).compareTo((String) object2.get("title"));
	     	}    
	       });    
	     }	
		irAdapter=new EtcAdapter(this, listItemsIR, listViewIR);
		listViewIR.setAdapter(irAdapter);		
	}	
}
  
