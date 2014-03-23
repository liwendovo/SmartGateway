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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.seuic.adapter.DevChoiceAdapter;
import com.seuic.add.AddDev;

public class SetupDev extends Activity implements android.view.View.OnClickListener{
	Button titleBtn,homeBtn;
	LinearLayout back_ll,titleBtn_ll;
	ImageView titlePic;		
	ListView mListView;
	private DevChoiceAdapter mAdapter;
	Button addDevBtn;
	
	
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
		
    	back_ll=(LinearLayout)findViewById(R.id.back_ll);
		titleBtn_ll=(LinearLayout)findViewById(R.id.titleBtn_ll);
		mListView = (ListView)findViewById(R.id.devListView);	
		
		homeBtn.setOnClickListener(this); 
		titleBtn.setOnClickListener(this);
		back_ll.setOnClickListener(this); 
		titleBtn_ll.setOnClickListener(this);
		
				
	}
	


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		Cursor cur=TabControl.mSQLHelper.seleteSetupALL(TabControl.writeDB);
		if(0==cur.getCount()){
			return;	
		}	
	

//		String uidstr = myPreferences.getString("uid", "NULL");
//		String uidstr
		Log.e("leewoo", "uid="+TabControl.mUid);
		int index=0;
		int mCurSet=-1;
		List<Map<String, Object>> listItems=new ArrayList<Map<String,Object>>();		
        
		for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
			Map<String, Object> listItem =new HashMap<String,Object>();
			listItem.put("uid",  cur.getString(0));//uid
			listItem.put("name", cur.getString(2));//名称
			Log.e("leewoo", cur.getString(0)+" "+cur.getString(1)+" "+cur.getString(2));
			listItems.add(listItem);	
//			listDev.add(cur.getString(0));
			if(TabControl.mUid.equals(cur.getString(0))){
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



	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch(v.getId())  
        {  
        case R.id.back_ll:
        case R.id.back:
        	finish();
        	break;
        	
        case R.id.titleBtn_ll:
        case R.id.titleBtn:
        	Intent intent = new Intent(SetupDev.this
					, AddDev.class);					
				//启动Activity
				startActivity(intent);	
        	break;
            
        default:  
            break;  
       
        }
	}

}




