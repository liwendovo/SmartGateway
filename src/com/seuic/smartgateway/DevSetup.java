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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.seuic.add.AddDev;
import com.seuic.net.SetupAp;


public class DevSetup extends Activity {
	ListView listView;
	Button addDevBtn;
	Button setupBtn;
	SharedPreferences myPreferences;
	SharedPreferences.Editor editor;
	List<Map<String, Object>> listItems;
	List<String> listDev;
	public SQLiteDatabase writeDB;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dev);

		listDev =  new ArrayList<String>();
		
		 myPreferences= getSharedPreferences("devset", Activity.MODE_PRIVATE);
		 writeDB=ControlBox.mSQLHelper.getWritableDatabase();
		 editor= myPreferences.edit();
		 
//		SharedPreferences sharedPreferences = otherAppsContext.getSharedPreferences("itcast", Context.MODE_WORLD_READABLE);
//		String name = sharedPreferences.getString("name", "");
//		int age = sharedPreferences.getInt("age", 0);
		
		
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
				
				
				
					editor.putString("uid", (String) listItems.get(arg2).get("title"));
					editor.commit();
				
//				 String uid=(String) listItems.get(arg2).get("title");			
//				 Intent intent = new Intent(DevSetup.this, ControlBox.class);				 
//				 intent.putExtra("uid", uid);
//				 startActivity(intent);		
				 
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
                                	ControlBox.mSQLHelper.deleteSetup(ControlBox.writeDB, listItem.get("title").toString());
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
		Cursor cur=ControlBox.mSQLHelper.seleteSetupALL(writeDB);
		if(0==cur.getCount()){
			return;	
		}	
		if(listItems!=null){
			listItems.clear();
		}
		String uidstr = myPreferences.getString("uid", "NULL");
		int index=1,mCurSet=0;
        listItems=new ArrayList<Map<String,Object>>();		
        
		for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
			Map<String, Object> listItem =new HashMap<String,Object>();
			listItem.put("title", cur.getString(0));
			listItem.put("content", cur.getString(1));
			listItems.add(listItem);	
			listDev.add(cur.getString(0));
			if(uidstr.equals(cur.getString(1))){
				mCurSet=index;
			}
			index++;
		}
//		SimpleAdapter simpleAdapter = new SimpleAdapter(this
//				, listItems 
//				, R.layout.line//android.R.layout.simple_list_item_single_choice
//				, new String[]{ "title", "content" }
//				, new int[]{R.id.title , R.id.content});
		ArrayAdapter<String> simpleAdapter = new ArrayAdapter<String> (this,android.R.layout.simple_list_item_checked,listDev);
			
	
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);// 如果不使用这个设置，选项中的radiobutton无法响应选中事件
		
		//初始选中状态判断
		
		if(0!=mCurSet){
			listView.setItemChecked(mCurSet, true);
		}
		//Adapter<String> adapter=new ArrayAdapter<String>(this,android.R.,arr);
		//SimpleCursorAdapter adapter=new SimpleCursorAdapter(this, R.layout.line, cur,new String[]{ mSQLHelper.Uid}, new int []{R.id.title});
		listView.setAdapter(simpleAdapter);
		
		
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		writeDB.close();
	}
	

}



