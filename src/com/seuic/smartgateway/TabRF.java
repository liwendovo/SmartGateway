package com.seuic.smartgateway ;  

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.seuic.adapter.EtcAdapter;
import com.seuic.add.AddEtc;
import com.seuic.devetc.RF_Curtain;
import com.seuic.devetc.RF_Lamp;
import com.seuic.devetc.RF_Selfdefine1;
import com.seuic.devetc.RF_Selfdefine2;
import com.seuic.devetc.RF_Switch;
import com.seuic.devetc.RF_WH;
import com.seuic.swipelistview.BaseSwipeListViewListener;
import com.seuic.swipelistview.SwipeListView;

public class TabRF extends Activity {
	Button titleBtn,homeBtn;
	TextView titleTxt;
	
	String mUid=null;
	List<Map<String, Object>> listItemsRF;	
	SwipeListView listViewRF;
	EtcAdapter rfAdapter;
	SharedPreferences myPreferences;
	SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabrf);
		myPreferences= getSharedPreferences("devset", Activity.MODE_PRIVATE);
		homeBtn=(Button)findViewById(R.id.leftBtn);
		titleTxt = (TextView)findViewById(R.id.titleTxt);
		titleBtn=(Button)findViewById(R.id.rightBtn);	
		homeBtn.setText("Home");
		titleTxt.setText("RF");
		titleBtn.setText("ADD");
		titleBtn.setOnClickListener(new OnClickListener()
		{	
			@Override	
			public void onClick(View source){				 
				 Intent intent = new Intent(TabRF.this, AddEtc.class);	
        		 intent.putExtra("uid", mUid);
				 intent.putExtra("type", "rf");
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
		listViewRF = (SwipeListView)findViewById(R.id.listViewRF);
		
		listViewRF.setSwipeListViewListener(new BaseSwipeListViewListener() {
			@Override
			public void onClickFrontView(int position) {		
				 String type=(String)listItemsRF.get(position).get("content");
				 int devid=Integer.parseInt(String.valueOf(listItemsRF.get(position).get("devid")));
				 Intent intent ;
				 if(type.equals(ControlBox.itemsRF[0])){
					 intent = new Intent(TabRF.this,RF_Switch.class);
				 }else if(type.equals(ControlBox.itemsRF[1])){
					 intent = new Intent(TabRF.this,RF_WH.class);
				 }else if(type.equals(ControlBox.itemsRF[2])){
					 intent = new Intent(TabRF.this,RF_Lamp.class);
				 }else if(type.equals(ControlBox.itemsRF[3])){
					 intent = new Intent(TabRF.this,RF_Curtain.class);				 
				 }else if(type.equals(ControlBox.itemsRF[4])){
					 intent = new Intent(TabRF.this,RF_Selfdefine1.class);
				 }else {
					 intent = new Intent(TabRF.this,RF_Selfdefine2.class);
				 }						 
				 intent.putExtra("uid",  mUid);
				 intent.putExtra("devid", devid);
				 startActivity(intent);		
			}

			@Override
			public void onClickBackView(int position) {
				listViewRF.closeOpenedItems();// 关闭打开的项
			}

			@Override
			public void onDismiss(int[] reverseSortedPositions) {
				// TODO Auto-generated method stub
				for (int position : reverseSortedPositions) {
					listItemsRF.remove(position);
				}
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
		Cursor cur=ControlBox.mSQLHelper.seleteListClass(ControlBox.writeDB, mUid,"rf");
		if(0==cur.getCount()){
			Log.e("leewoo","count="+0);
			return;
		}	
		if(listItemsRF!=null){
			listItemsRF.clear();
		}
		listItemsRF=new ArrayList<Map<String,Object>>();	
		for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
			Map<String,Object> listItem =new HashMap<String,Object>();	
		
			listItem.put("title", cur.getString(4));
			listItem.put("content", cur.getString(3));
			listItem.put("devid", cur.getInt(1));
			listItemsRF.add(listItem);
		}	
		cur.close();
		//list排序  需要优化
		if (!listItemsRF.isEmpty()) {    
	    	 Collections.sort(listItemsRF, new Comparator<Map<String, Object>>() {
	     	@Override
	     	public int compare(Map<String, Object> object1,
	     	Map<String, Object> object2) {
			//根据文本排序
	          	return ((String) object1.get("title")).compareTo((String) object2.get("title"));
	     	}    
	    	 });    
	     }	
		rfAdapter=new EtcAdapter(this, listItemsRF, listViewRF);
		listViewRF.setAdapter(rfAdapter);
	}
}
  
