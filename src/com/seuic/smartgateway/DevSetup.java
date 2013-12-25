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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.seuic.add.AddDev;
import com.seuic.net.SetupAp;
import com.seuic.sqlite.SQLiteHelper;


public class DevSetup extends Activity {
	ListView listView;
	Button addDevBtn;
	Button setupBtn;
	
	List<Map<String, Object>> listItems;
	public static SQLiteHelper mSQLHelper;
	public static SQLiteDatabase writeDB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dev);
		mSQLHelper = new SQLiteHelper(this,"smartgateway.db",1); //数据库
		writeDB=mSQLHelper.getWritableDatabase();
		listView = (ListView)findViewById(R.id.devListView);			
		addDevBtn=(Button)findViewById(R.id.addDevBtn);
		setupBtn=(Button)findViewById(R.id.SetupBtn);
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
		setupBtn.setOnClickListener(new OnClickListener()
		{		
			public void onClick(View source)
			{
				Intent intent = new Intent(DevSetup.this
						, SetupAp.class);					
					//启动Activity
					startActivity(intent);				
			}			
		});	
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				 String uid=(String) listItems.get(arg2).get("title");			
				 Intent intent = new Intent(DevSetup.this, ControlBox.class);				 
				 intent.putExtra("uid", uid);
				 startActivity(intent);		
				 
			}
		});
		
		onSwipeTouchListener touchListener =
                new onSwipeTouchListener(
                        listView,
                        new onSwipeTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                	Map<String, Object> listItem=listItems.get(position);
                                	mSQLHelper.deleteSetup(writeDB, listItem.get("title").toString());
                                	listItems.remove(listItem);
                                	//数据库删除
                                	Log.e("leewoo", "swipe->Right");
                                	
                                }
                              //  listItems.notifyDataSetChanged();
                            }
                        });
        listView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        listView.setOnScrollListener(touchListener.makeScrollListener());
    }

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Cursor cur=mSQLHelper.seleteSetupALL(writeDB);
		if(0==cur.getCount()){
			return;	
		}	
		if(listItems!=null){
			listItems.clear();
		}
        listItems=new ArrayList<Map<String,Object>>();		
		for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
			Map<String, Object> listItem =new HashMap<String,Object>();
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



