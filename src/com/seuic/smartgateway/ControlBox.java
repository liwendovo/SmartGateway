package com.seuic.smartgateway;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;

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
import com.seuic.devetc.RF_Curtain;
import com.seuic.devetc.RF_Lamp;
import com.seuic.devetc.RF_Selfdefine1;
import com.seuic.devetc.RF_Selfdefine2;
import com.seuic.devetc.RF_Switch;
import com.seuic.devetc.RF_WH;

public class ControlBox extends Activity {
	public final static CharSequence[] itemsIR = {"TV", "AC","Media","STU","WH", "DVD","FAN","自定义1","自定义2"}; 
	public final static CharSequence[] itemsRF = {"Switch", "WH", "Lamp","Curtain","自定义1","自定义2"}; 	
	TextView tv1; 
	Button addIRBtn,addRFBtn;
	String mUid;
	List<Map<String, Object>> listItemsIR;
	List<Map<String, Object>> listItemsRF;
	ListView listViewIR;
	ListView listViewRF;
	
	
	public SQLiteDatabase readDB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.controlbox);	
		
		listViewIR = (ListView)findViewById(R.id.listViewIR);
		listViewRF = (ListView)findViewById(R.id.listViewRF);
		
		Intent intent=getIntent();
		mUid=intent.getStringExtra("uid");	
//		Toast toast = Toast.makeText(ControlBox.this, mUid, Toast.LENGTH_SHORT);
//		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 50);
//		toast.show();	
	
		
		TabHost host = (TabHost)findViewById(R.id.tabhost);
		host.setup();

		TabHost.TabSpec irSpec = host.newTabSpec("IR");        //This param will be used as tabId.
		irSpec.setIndicator(null,         					//This param will diplay as title. 
		getResources().getDrawable(R.drawable.ir));
		irSpec.setContent(R.id.tabir);
		host.addTab(irSpec);

		TabHost.TabSpec rfSpec = host.newTabSpec("RF");
		rfSpec.setIndicator(null, getResources().getDrawable(R.drawable.rf));
		rfSpec.setContent(R.id.tabrf);
		host.addTab(rfSpec);

		TabHost.TabSpec thSpec = host.newTabSpec("TH");
		thSpec.setIndicator(null, getResources().getDrawable(R.drawable.th));
		thSpec.setContent(R.id.tabth);
		host.addTab(thSpec);
		
		TabHost.TabSpec setSpec = host.newTabSpec("SET");
		setSpec.setIndicator(null, getResources().getDrawable(R.drawable.setp));
		setSpec.setContent(R.id.tabset);
		host.addTab(setSpec);
		
		
		addIRBtn=(Button)findViewById(R.id.addIRBtn);		
		addRFBtn=(Button)findViewById(R.id.addRFBtn);
		addIRBtn.setOnClickListener(new OnClickListener()
		{		
			public void onClick(View source){
						
				 Intent intent = new Intent(ControlBox.this, AddEtc.class);	
				 intent.putExtra("uid", mUid);
				 intent.putExtra("type", "ir");
				 startActivity(intent);		
			}			
		});	
		addRFBtn.setOnClickListener(new OnClickListener()
		{		
			public void onClick(View source){
							
				 Intent intent = new Intent(ControlBox.this, AddEtc.class);	
				 intent.putExtra("uid", mUid);
				 intent.putExtra("type", "rf");
				 startActivity(intent);		
			}			
		});	
		
		listViewIR.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				 String type=(String)listItemsIR.get(arg2).get("content");
				 Intent intent ;
				 if(type.equals("TV")){
					 intent = new Intent(ControlBox.this,IR_TV.class);	
				 }else if(type.equals("AC")){
					 intent = new Intent(ControlBox.this,IR_AC.class);
				 }else if(type.equals("Media")){
					 intent = new Intent(ControlBox.this,IR_Media.class);
				 }else if(type.equals("STU")){
					 intent = new Intent(ControlBox.this,IR_STU.class);
				 }else if(type.equals("WH")){
					 intent = new Intent(ControlBox.this,IR_WH.class);
				 }else if(type.equals("DVD")){
					 intent = new Intent(ControlBox.this,IR_DVD.class);
				 }else if(type.equals("FAN")){
					 intent = new Intent(ControlBox.this,IR_FAN.class);
				 }else if(type.equals("自定义1")){
					 intent = new Intent(ControlBox.this,IR_Selfdefine1.class);
				 }else {
					 intent = new Intent(ControlBox.this,IR_Selfdefine2.class);
				 }						 
				// intent.putExtra("uid", uid);
				 startActivity(intent);		
				 
			}
		});
		
		listViewRF.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				 String type=(String)listItemsRF.get(arg2).get("content");
				 Intent intent ;
				 if(type.equals(itemsRF[0])){
					 intent = new Intent(ControlBox.this,RF_Switch.class);
				 }else if(type.equals(itemsRF[1])){
					 intent = new Intent(ControlBox.this,RF_WH.class);
				 }else if(type.equals(itemsRF[2])){
					 intent = new Intent(ControlBox.this,RF_Lamp.class);
				 }else if(type.equals(itemsRF[3])){
					 intent = new Intent(ControlBox.this,RF_Curtain.class);				 
				 }else if(type.equals(itemsRF[4])){
					 intent = new Intent(ControlBox.this,RF_Selfdefine1.class);
				 }else {
					 intent = new Intent(ControlBox.this,RF_Selfdefine2.class);
				 }						 
				// intent.putExtra("uid", uid);
				 startActivity(intent);		
				 
			}
		});
		
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Cursor cur=DevSetup.mSQLHelper.seleteByDevID(DevSetup.readDB,mUid);
		if(0==cur.getCount()){
			return;	
		}	
		if(listItemsRF!=null){
			listItemsRF.clear();
		}
		if(listItemsIR!=null){
			listItemsIR.clear();
		}
		listItemsIR=new ArrayList<Map<String,Object>>();		
		listItemsRF=new ArrayList<Map<String,Object>>();	
		for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
			Map<String,Object> listItem =new HashMap<String,Object>();	
		
			listItem.put("title", cur.getString(4));
			listItem.put("content", cur.getString(3));
			String mClass=cur.getString(2);
			if(mClass.equals("ir")){
			listItemsIR.add(listItem);	
			}else if(mClass.equals("rf")){
			listItemsRF.add(listItem);	
			}
			
		}
		
		
		
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
		
		
		
		SimpleAdapter simpleAdapterIR = new SimpleAdapter(this
				, listItemsIR 
				, R.layout.line
				, new String[]{ "title", "content" }
				, new int[]{R.id.title , R.id.content});

		listViewIR.setAdapter(simpleAdapterIR);
		
		SimpleAdapter simpleAdapterRF= new SimpleAdapter(this
				, listItemsRF 
				, R.layout.line
				, new String[]{ "title", "content" }
				, new int[]{R.id.title , R.id.content});

		listViewRF.setAdapter(simpleAdapterRF);
		
	}
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); 
		 
		//add Data ...
		 
		//对list进行排序
		 
		if (!list.isEmpty()) {    
		    	 Collections.sort(list, new Comparator<Map<String, Object>>() {
		     	@Override
		     	public int compare(Map<String, Object> object1,
		     	Map<String, Object> object2) {
				//根据文本排序
		          	return ((String) object1.get("content")).compareTo((String) object2.get("content"));
		     	}    
		    	 });    
		}		 
		return list;
		}
}
