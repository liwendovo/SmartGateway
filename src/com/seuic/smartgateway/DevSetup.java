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
import android.widget.Button;
import android.widget.ListView;

import com.seuic.adapter.SingleChoiceAdapter;
import com.seuic.add.AddDev;
import com.seuic.net.SetupAp;
import com.seuic.swipelistview.BaseSwipeListViewListener;
import com.seuic.swipelistview.SwipeListView;




public class DevSetup extends Activity {
	ListView listView;
	Button addDevBtn;
	Button setupBtn;
	int currentID=-1;
	SharedPreferences myPreferences;
	SharedPreferences.Editor editor;
	
	private SwipeListView swipeListView;
	private SingleChoiceAdapter mAdapter ;
	
	
	List<Map<String, Object>> listItems;
	public SQLiteDatabase writeDB;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.devsetup);

		
		 myPreferences= getSharedPreferences("devset", Activity.MODE_PRIVATE);
		 writeDB=ControlBox.mSQLHelper.getWritableDatabase();
		 editor= myPreferences.edit();
		 

		
		swipeListView = (SwipeListView)findViewById(R.id.devListView);		
		
		
		
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
		
//		swipeListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View view, int position,
//					long id) {
//				// TODO Auto-generated method stub
//				
//					mAdapter.setItemChecked(position);
//					mAdapter.notifyDataSetChanged();
//				Log.e("leewoo", "click on: "+position);
//				
//			}
//		});
		swipeListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
		
			
			@Override
			public void onClickFrontView(int position) {
				// L.d("swipe",
				// String.format("onClickFrontView %d", position));
				// T.showShort(mApplication, "item onClickFrontView ="
				// + mAdapter.getItem(position));
				mAdapter.setItemChecked(position);
				mAdapter.notifyDataSetChanged();
				editor.putString("uid",mAdapter.getItem(position).get("title").toString());
			    editor.commit();
			}

			@Override
			public void onClickBackView(int position) {
				swipeListView.closeOpenedItems();// 关闭打开的项
			}
		});
		
		
}
//		
		
		
		
	

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
		Log.e("leewoo", "uid="+uidstr);
		int index=0,mCurSet=-1;
        listItems=new ArrayList<Map<String,Object>>();		
        
		for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
			Map<String, Object> listItem =new HashMap<String,Object>();
			listItem.put("title", cur.getString(0));
			listItem.put("content", cur.getString(1));
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
		mAdapter = new SingleChoiceAdapter(this,listItems,swipeListView);	
		mAdapter.setItemChecked(mCurSet);
		swipeListView.setAdapter(mAdapter);
		
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		writeDB.close();
	}
	
 

}



