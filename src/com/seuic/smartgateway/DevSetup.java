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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import com.seuic.add.AddDev;
import com.seuic.sqlite.SQLiteHelper;

public class DevSetup extends Activity {
	ListView listView;
	Button addDevBtn;
	public static SQLiteHelper mSQLHelper;
	public SQLiteDatabase readDB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dev);
		mSQLHelper = new SQLiteHelper(this,"smartgateway.db",1); //数据库
		readDB=mSQLHelper.getReadableDatabase();
		listView = (ListView)findViewById(R.id.devListView);	
		addDevBtn=(Button)findViewById(R.id.addDevBtn);
		addDevBtn.setOnClickListener(new OnClickListener()
		{		
			public void onClick(View source)
			{
				Intent intent = new Intent(DevSetup.this
						, AddDev.class);					
					//启动Activity
					startActivity(intent);				
			}			
		});	
	 
    }

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Cursor cur=mSQLHelper.seleteByUidALL(readDB);
		if(0==cur.getCount()){
			return;	
		}	
		
		List<Map<String,String>> listItems=new ArrayList<Map<String,String>>();
		for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
			Map<String,String> listItem =new HashMap<String,String>();
			listItem.put("title", cur.getString(0));
			listItem.put("content", cur.getString(1));
			listItems.add(listItem);	
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this
				, listItems 
				, R.layout.line
				, new String[]{ "title", "content" }
				, new int[]{R.id.title , R.id.content});

		//Adapter<String> adapter=new ArrayAdapter<String>(this,android.R.,arr);
		//SimpleCursorAdapter adapter=new SimpleCursorAdapter(this, R.layout.line, cur,new String[]{ mSQLHelper.Uid}, new int []{R.id.title});
		listView.setAdapter(simpleAdapter);
		
	}
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		//退出程序时关闭MyDatabaseHelper里的SQLiteDatabase
		if (mSQLHelper != null)
		{
			mSQLHelper.close();
		}
	}
}



