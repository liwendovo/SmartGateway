package com.seuic.smartgateway;


import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.seuic.adapter.EtcAdapter;
import com.seuic.sqlite.SQLiteHelper;
import com.seuic.swipelistview.SwipeListView;
//import com.seuic.add.AddEtc;
//import com.seuic.devetc.IR_AC;
//import com.seuic.devetc.IR_DVD;
//import com.seuic.devetc.IR_FAN;
//import com.seuic.devetc.IR_Media;
//import com.seuic.devetc.IR_STU;
//import com.seuic.devetc.IR_Selfdefine1;
//import com.seuic.devetc.IR_Selfdefine2;
//import com.seuic.devetc.IR_TV;
//import com.seuic.devetc.IR_WH;
//import com.seuic.devetc.RF_Curtain;
//import com.seuic.devetc.RF_Lamp;
//import com.seuic.devetc.RF_Selfdefine1;
//import com.seuic.devetc.RF_Selfdefine2;
//import com.seuic.devetc.RF_Switch;
//import com.seuic.devetc.RF_WH;
//import com.seuic.swipelistview.BaseSwipeListViewListener;




@SuppressWarnings("deprecation")
public class ControlBox extends ActivityGroup {
	public final static String[] itemsIR = {"TV", "AC","Media","STU","WH", "DVD","FAN","自定义1","自定义2"}; 
	public final static String[] itemsRF = {"Switch", "WH", "Lamp","Curtain","自定义1","自定义2"}; 	

//	TextView tv1; 
//	Button aboutBtn,resetBtn,devSetBtn,camSetBtn;
//	
//	Button titleBtn,homeBtn;
//	String mUid=null;
//	List<Map<String, Object>> listItemsIR;
//	SwipeListView listViewIR;
//	List<Map<String, Object>> listItemsRF;	
//	SwipeListView listViewRF;
//	ListView listViewIR;
//	ListView listViewRF;
//	EtcAdapter irAdapter,rfAdapter;
//	TextView devClass;
//	String TabID="IR";
	
	private TabHost host = null;
	private LayoutInflater mInflater = null;	
	public static SQLiteHelper mSQLHelper;
	public static SQLiteDatabase writeDB;
	public static SharedPreferences myPreferences;
	public static SharedPreferences.Editor editor;
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.controltab);	
		
		mSQLHelper = new SQLiteHelper(this,"smartgateway.db",1); //数据库
		writeDB=mSQLHelper.getWritableDatabase();
		myPreferences= getSharedPreferences("devset", Activity.MODE_PRIVATE);
		
//		listViewIR = (SwipeListView)findViewById(R.id.listViewIR);
//		listViewRF = (SwipeListView)findViewById(R.id.listViewRF);
		
//		homeBtn=(Button)findViewById(R.id.home);
//		devClass   = (TextView)findViewById(R.id.devClass);		
//		titleBtn=(Button)findViewById(R.id.titleBtn);
//		
//		aboutBtn=(Button)findViewById(R.id.aboutBtn);
//		resetBtn=(Button)findViewById(R.id.resetBtn);
//		
//		devSetBtn=(Button)findViewById(R.id.devSetBtn);
		

		
//		host = (TabHost)findViewById(R.id.tabhost);
////		final TabHost host = getTabHost();
//		host.setup();
//
//		TabHost.TabSpec irSpec = host.newTabSpec("IR");        //This param will be used as tabId.
//		irSpec.setIndicator(null,         					//This param will diplay as title. 
//		getResources().getDrawable(R.drawable.ir));
//		irSpec.setContent(new Intent(ControlBox.this, TabIR.class));
//		host.addTab(irSpec);
//
//		TabHost.TabSpec rfSpec = host.newTabSpec("RF");
//		rfSpec.setIndicator(null, getResources().getDrawable(R.drawable.rf));
//		rfSpec.setContent(new Intent(ControlBox.this, TabRF.class));
//		host.addTab(rfSpec);
//
//		TabHost.TabSpec thSpec = host.newTabSpec("TH");
//		thSpec.setIndicator(null, getResources().getDrawable(R.drawable.th));
//		thSpec.setContent(new Intent(ControlBox.this, TabTH.class));
//		host.addTab(thSpec);
//		
//		TabHost.TabSpec setSpec = host.newTabSpec("SET");
//		setSpec.setIndicator(null, getResources().getDrawable(R.drawable.setp));
//		setSpec.setContent(new Intent(ControlBox.this, TabSET.class));
//		host.addTab(setSpec);
//		host.getId();
		mInflater = LayoutInflater.from(this);
		host = (TabHost) findViewById(R.id.tabhost);
		host.setup(this.getLocalActivityManager());
		
		Intent intent = new Intent(this, TabIR.class);
		View tab1Spec = mInflater.inflate(R.layout.tab1_spec, null);
		host.addTab(host
				.newTabSpec("IR")
				.setIndicator(tab1Spec)
				.setContent(intent));
		
		intent = new Intent(this, TabRF.class);
		View tab2Spec = mInflater.inflate(R.layout.tab2_spec, null);
		host.addTab(host
				.newTabSpec("RF")
				.setIndicator(tab2Spec)
				.setContent(intent));
		
		intent = new Intent(this, TabTH.class);
		View tab3Spec = mInflater.inflate(R.layout.tab3_spec, null);
		host.addTab(host
				.newTabSpec("TH")
				.setIndicator(tab3Spec)
				.setContent(intent));
		
		intent = new Intent(this, TabSET.class);
		View tab4Spec = mInflater.inflate(R.layout.tab4_spec, null);
		host.addTab(host
				.newTabSpec("SET")
				.setIndicator(tab4Spec)
				.setContent(intent));
		
		
		
		
		
		
		
		
		
//		// 就是从一个标签切换到另外一个标签会触发的事件
//		host.setOnTabChangedListener(new OnTabChangeListener() {
//                public void onTabChanged(String tabId) {
//                	devClass.setText(tabId);
//                	TabID=tabId;
//                	titleBtn.setVisibility(Button.VISIBLE);
//                	if(tabId.equals("IR")){                	
//                		titleBtn.setText("ADD");
//                	}else if(tabId.equals("RF")){
//                	//	titleBtn.setVisibility(Button.VISIBLE);
//                		titleBtn.setText("ADD");
//                	}else if(tabId.equals("TH")){
//                	//	titleBtn.setVisibility(Button.VISIBLE);
//                		titleBtn.setText("Chart");
//                	}else {
//                		titleBtn.setVisibility(Button.INVISIBLE);
//                    }
//                }
//        });
		
	
//		titleBtn.setOnClickListener(new OnClickListener()
//		{		
//			public void onClick(View source){
//				 
//				if(TabID.equals("IR")){                	
//					 Intent intent = new Intent(ControlBox.this, AddEtc.class);	
//					 intent.putExtra("uid", mUid);
//					 intent.putExtra("type", "ir");
//					 startActivity(intent);	
//            	}else if(TabID.equals("RF")){
//            	//	titleBtn.setVisibility(Button.VISIBLE);
//            		 Intent intent = new Intent(ControlBox.this, AddEtc.class);	
//            		 intent.putExtra("uid", mUid);
//    				 intent.putExtra("type", "rf");
//    				 startActivity(intent);	
//            	}else if(TabID.equals("TH")){
//            		//charge
//            	}
//			}			
//		});	
//		aboutBtn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				aboutus();
//			}
//		});
//		resetBtn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Log.i("leewoo", "reset");
//			}
//		});
//		homeBtn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				finish();
//			}
//		});
//		
//		
//		devSetBtn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				 Intent intent = new Intent(ControlBox.this,DevSetup.class);	
//				 startActivity(intent);	
//			}
//		});
//		
//		
//		listViewIR.setSwipeListViewListener(new BaseSwipeListViewListener() {
//			@Override
//			public void onClickFrontView(int position) {
//				// L.d("swipe",
//				// String.format("onClickFrontView %d", position));
//				// T.showShort(mApplication, "item onClickFrontView ="
//				// + mAdapter.getItem(position));
//				
////				editor.putString("uid",irAdapter.getItem(position).get("title").toString());
////			    editor.commit();
//				 String type=(String)listItemsIR.get(position).get("content");
//				 int devid=Integer.parseInt(String.valueOf(listItemsIR.get(position).get("devid")));
//				 Intent intent ;
//				 if(type.equals("TV")){
//					 intent = new Intent(ControlBox.this,IR_TV.class);	
//				 }else if(type.equals("AC")){
//					 intent = new Intent(ControlBox.this,IR_AC.class);
//				 }else if(type.equals("Media")){
//					 intent = new Intent(ControlBox.this,IR_Media.class);
//				 }else if(type.equals("STU")){
//					 intent = new Intent(ControlBox.this,IR_STU.class);
//				 }else if(type.equals("WH")){
//					 intent = new Intent(ControlBox.this,IR_WH.class);
//				 }else if(type.equals("DVD")){
//					 intent = new Intent(ControlBox.this,IR_DVD.class);
//				 }else if(type.equals("FAN")){
//					 intent = new Intent(ControlBox.this,IR_FAN.class);
//				 }else if(type.equals("自定义1")){
//					 intent = new Intent(ControlBox.this,IR_Selfdefine1.class);
//				 }else {
//					 intent = new Intent(ControlBox.this,IR_Selfdefine2.class);
//				 }						 
//				 intent.putExtra("uid",  mUid);
//				 intent.putExtra("devid", devid);
//				 startActivity(intent);		
//				
//			}
//
//			@Override
//			public void onClickBackView(int position) {
//				listViewIR.closeOpenedItems();// 关闭打开的项
//			}
//		});
//		
//		
//		listViewRF.setSwipeListViewListener(new BaseSwipeListViewListener() {
//		
//			
//			@Override
//			public void onClickFrontView(int position) {
//				// L.d("swipe",
//				// String.format("onClickFrontView %d", position));
//				// T.showShort(mApplication, "item onClickFrontView ="
//				// + mAdapter.getItem(position));
//				
//				 String type=(String)listItemsRF.get(position).get("content");
//				 int devid=Integer.parseInt(String.valueOf(listItemsRF.get(position).get("devid")));
//				// String id=listItemsRF.get(arg2).get("id");
//				     // Integer.parseInt(String.valueOf(value));
//				 Intent intent ;
//				 if(type.equals(itemsRF[0])){
//					 intent = new Intent(ControlBox.this,RF_Switch.class);
//				 }else if(type.equals(itemsRF[1])){
//					 intent = new Intent(ControlBox.this,RF_WH.class);
//				 }else if(type.equals(itemsRF[2])){
//					 intent = new Intent(ControlBox.this,RF_Lamp.class);
//				 }else if(type.equals(itemsRF[3])){
//					 intent = new Intent(ControlBox.this,RF_Curtain.class);				 
//				 }else if(type.equals(itemsRF[4])){
//					 intent = new Intent(ControlBox.this,RF_Selfdefine1.class);
//				 }else {
//					 intent = new Intent(ControlBox.this,RF_Selfdefine2.class);
//				 }						 
//				 intent.putExtra("uid",  mUid);
//				 intent.putExtra("devid", devid);
//				 startActivity(intent);		
//			}
//
//			@Override
//			public void onClickBackView(int position) {
//				listViewRF.closeOpenedItems();// 关闭打开的项
//			}
//
//			@Override
//			public void onDismiss(int[] reverseSortedPositions) {
//				// TODO Auto-generated method stub
//				for (int position : reverseSortedPositions) {
//					listItemsIR.remove(position);
//				}
//			}
//
//		});     		
	}
	
	
	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		mUid=myPreferences.getString("uid", "NULL");
//		if (mUid.equals("NULL")) {
//			Toast.makeText(getApplicationContext(),"设备为设置，请到Set界面添加设备", Toast.LENGTH_SHORT).show();		
//		}
//		Log.e("leewoo","mUid="+mUid);
//		Cursor cur=mSQLHelper.seleteList(writeDB, mUid);
//		if(0==cur.getCount()){
//			Log.e("leewoo","count="+0);
//			return;
//		}	
//		if(listItemsRF!=null){
//			listItemsRF.clear();
//		}
//		if(listItemsIR!=null){
//			listItemsIR.clear();
//		}
//		listItemsIR=new ArrayList<Map<String,Object>>();		
//		listItemsRF=new ArrayList<Map<String,Object>>();	
//		for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
//			Map<String,Object> listItem =new HashMap<String,Object>();	
//		
//			listItem.put("title", cur.getString(4));
//			listItem.put("content", cur.getString(3));
//			listItem.put("devid", cur.getInt(1));
//			String mClass=cur.getString(2);
//			if(mClass.equals("ir")){
//			listItemsIR.add(listItem);	
//			}else if(mClass.equals("rf")){
//			listItemsRF.add(listItem);	
//			}
//			
//		}		
//		
//		cur.close();
//		
//		//list排序  需要优化
//		if (!listItemsIR.isEmpty()) {    
//	    	 Collections.sort(listItemsIR, new Comparator<Map<String, Object>>() {
//	     	@Override
//	     	public int compare(Map<String, Object> object1,
//	     	Map<String, Object> object2) {
//			//根据文本排序
//	          	return ((String) object1.get("title")).compareTo((String) object2.get("title"));
//	     	}    
//	    	 });    
//	     }	
//		if (!listItemsRF.isEmpty()) {    
//	    	 Collections.sort(listItemsRF, new Comparator<Map<String, Object>>() {
//	     	@Override
//	     	public int compare(Map<String, Object> object1,
//	     	Map<String, Object> object2) {
//			//根据文本排序
//	          	return ((String) object1.get("title")).compareTo((String) object2.get("title"));
//	     	}    
//	    	 });    
//	     }	
//		
//		
//		
//
//		irAdapter=new EtcAdapter(this, listItemsIR, listViewIR);
//		listViewIR.setAdapter(irAdapter);
//		
//
//		rfAdapter=new EtcAdapter(this, listItemsRF, listViewRF);
//		listViewRF.setAdapter(rfAdapter);
//		
//	}
//	 protected void aboutus() {
//		 AlertDialog.Builder builder = new Builder(ControlBox.this);
//		 builder.setMessage("Please input name");
//		 builder.setTitle("About us");
//		 builder.setMessage(this.getString(R.string.aboutus));		
//		 builder.create().show();
//	   }
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		writeDB.close();
	}


}
