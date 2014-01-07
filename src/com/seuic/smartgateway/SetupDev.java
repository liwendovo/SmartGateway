/*
 * liwendong 2013-12-17
 * copyright@ seuic 
 * */
package com.seuic.smartgateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.seuic.adapter.DevChoiceAdapter;
import com.seuic.add.AddDev;

public class SetupDev extends Activity {
	Button titleBtn,homeBtn;
	ImageView titlePic;	
	
	ListView mListView;
	private DevChoiceAdapter mAdapter;
	Button addDevBtn;
//	Button setupBtn;
	int currentID=-1;
	public static SharedPreferences myPreferences;
	public static SharedPreferences.Editor editor;	


	public SQLiteDatabase writeDB;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setup_dev);
		
		homeBtn=(Button)findViewById(R.id.back);
		titlePic=(ImageView)findViewById(R.id.pic);
		titleBtn=(Button)findViewById(R.id.titleBtn);
		
    	homeBtn.setBackgroundResource(R.drawable.title_back);
    	titlePic.setImageResource(R.drawable.dev_icon);
    	titleBtn.setBackgroundResource(R.drawable.title_add);
		
		
		myPreferences= getSharedPreferences("devset", Activity.MODE_PRIVATE);
		writeDB=TabControl.mSQLHelper.getWritableDatabase();
		editor= myPreferences.edit();
		mListView = (ListView)findViewById(R.id.devListView);	
		
		
		titleBtn.setOnClickListener(new OnClickListener()
		{		
			public void onClick(View source)
			{
				Intent intent = new Intent(SetupDev.this
						, AddDev.class);					
					//Æô¶¯Activity
					startActivity(intent);				
			}			
		});	
//		setupBtn=(Button)findViewById(R.id.SetupBtn);
//		setupBtn.setOnClickListener(new OnClickListener()
//		{		
//			public void onClick(View source)
//			{
//				Intent intent = new Intent(SetupDev.this
//						, SetupAp.class);					
//					//Æô¶¯Activity
//					startActivity(intent);				
//			}			
//		});	
		homeBtn.setOnClickListener(new OnClickListener()
		{		
			public void onClick(View source)
			{
				finish();			
			}			
		});
}
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		Cursor cur=TabControl.mSQLHelper.seleteSetupALL(writeDB);
		if(0==cur.getCount()){
			return;	
		}	
	

		String uidstr = myPreferences.getString("uid", "NULL");
		Log.e("leewoo", "uid="+uidstr);
		int index=0,mCurSet=-1;
		List<Map<String, Object>> listItems=new ArrayList<Map<String,Object>>();		
        
		for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
			Map<String, Object> listItem =new HashMap<String,Object>();
			listItem.put("uid", cur.getString(0));
			listItem.put("type", cur.getString(1));
			Log.e("leewoo", cur.getString(0)+" "+cur.getString(1));
			listItems.add(listItem);	
//			listDev.add(cur.getString(0));
			if(uidstr.equals(cur.getString(0))){
				mCurSet=index;
			}
			index++;
		}
		Log.e("leewoo", "DevSetup index:"+index+"  mCurSet:"+mCurSet);
//		simpleAdapter = new ArrayAdapter<String> (this,android.R.layout.simple_list_item_single_choice,listDev);		
		mAdapter = new DevChoiceAdapter(this,listItems);	
		mAdapter.setItemChecked(mCurSet);
		mListView.setAdapter(mAdapter);
	}



	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		writeDB.close();
	}
	
 

}



