package com.seuic.smartgateway;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.seuic.add.AddEtc;

public class ControlBox extends Activity {
	TextView tv1; 
	Button addIRBtn,addRFBtn;
	String mUid;
	List<Map<String,String>> listItemsIR,listItemsRF;
	ListView listViewIR;
	ListView listViewRF;
	public SQLiteDatabase readDB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		listItemsIR=new ArrayList<Map<String,String>>();		
		listItemsRF=new ArrayList<Map<String,String>>();	
		for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
			Map<String,String> listItem =new HashMap<String,String>();
			
		
			listItem.put("title", cur.getString(1));
			listItem.put("content", cur.getString(3));
			String mtype=cur.getString(2);
			if(mtype.equals("ir")){
			listItemsIR.add(listItem);	
			}else if(mtype.equals("rf")){
				listItemsRF.add(listItem);	
			}
			
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
	
}
