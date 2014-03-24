package com.seuic.smartgateway;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

import com.seuic.adapter.CustomToast;
import com.seuic.adapter.ViewSelected;
import com.seuic.net.TUTKClient;
import com.seuic.sqlite.SQLiteHelper;

@SuppressWarnings("deprecation")
public class TabControl extends ActivityGroup {
	public final static String[] itemsIR = {"TV", "AC","MEDIA","STU","WH", "DVD","FAN","CUSTOM1","CUSTOM2"}; 
	public final static String[] itemsRF = {"Switch", "Lamp", "Curtain","Power","CUSTOM1","CUSTOM2"}; 	

//	public static boolean tempmode = true;
	public static TabHost host = null;
	private LayoutInflater mInflater = null;
	public static int time = 500;
	public static SQLiteHelper mSQLHelper;
	public static SQLiteDatabase writeDB;
	public static ViewSelected mViewSelected;
	BroadcastReceiver connectionReceiver;	
	public static TUTKClient mClient=null;	
	public static  String mUid="NULL";	//当有设备online时为相应的uid
	public static SharedPreferences myPre;
	public static SharedPreferences myPreferences;
	public static SharedPreferences.Editor editor;	
	public static TabControl instance;
	
	
	public Handler tutkHandler = new Handler(){ 
        @Override  
        public void handleMessage(Message msg) {  
          if(0xff01==msg.what){
        	  Log.e("TabControl","receive handler message");
	          AlertDialog.Builder builder = new Builder(TabControl.this); 
	          builder.setMessage("Are you sure you want to exit?") 
	                 .setCancelable(false) 
	                 .setPositiveButton("Yes", new DialogInterface.OnClickListener() { 
	                     public void onClick(DialogInterface dialog, int id) { 
	                  	  /* 
	                  	   Intent intent = new Intent();
	                  	   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
	                  	   intent.setClass(TabControl.this, TabSET.class);
	                  	   startActivity(intent);
	                  	   */
	                    	 host.setCurrentTab(4);
	                  	   
	                     }
	         
	                 }); 
	          AlertDialog alert = builder.create(); 
	          alert.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
	          alert.show();
        	
          }
        }
	};
	
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tabcontrol);	
		
		instance = this;
		
		mSQLHelper = new SQLiteHelper(this,"smartgateway.db",1); //数据库
		writeDB=mSQLHelper.getWritableDatabase();

		myPreferences= getSharedPreferences("devset", Activity.MODE_PRIVATE);		
		editor= myPreferences.edit();
		mViewSelected=new ViewSelected();
		
		myPre= getSharedPreferences("devset", Activity.MODE_PRIVATE);
		TabControl.mUid = myPre.getString("uid", "NULL");	

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
		
		intent = new Intent(this, TabCam.class);
		View tab4Spec = mInflater.inflate(R.layout.tab4_spec, null);
		host.addTab(host
				.newTabSpec("CAM")
				.setIndicator(tab4Spec)				
				.setContent(intent));
		intent = new Intent(this, TabSET.class);
		View tab5Spec = mInflater.inflate(R.layout.tab5_spec, null);
		host.addTab(host
				.newTabSpec("SET")
				.setIndicator(tab5Spec)
				.setContent(intent));
		
		
		if (!TabControl.mUid.equals("NULL")) {	
			Log.e("TabControl","mUid is not NULL");
		    host.setCurrentTab(0);
		}else{
			Log.e("TabControl","mUid is NULL");
			host.setCurrentTab(4);
		}
		
		
		host.setOnTabChangedListener(new OnTabChangeListener() {
			   @Override
			   public void onTabChanged(String tabId) {
			    // TODO Auto-generated method stub
			
//				   myPre= getSharedPreferences("devset", Activity.MODE_PRIVATE);
//					String mUid = myPre.getString("uid", "NULL");	
					if (mUid.equals("NULL")) {	
			    	host.setCurrentTab(4);
			    	CustomToast.showToast(getApplicationContext(),getResources().getString(R.string.activateinfo), Toast.LENGTH_SHORT);
			    }
			  }
		});   		
		connectionReceiver = new BroadcastReceiver(){ 
			@Override 
			public void onReceive(Context context, Intent intent) { 
				ConnectivityManager connectMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE); 
				NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); 
				NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
				if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) { 
				Log.e("connectionReceiver", "unconnect"); 
				CustomToast.showToast(getApplicationContext(),getResources().getString(R.string.networkunconnect), Toast.LENGTH_SHORT);	
				mUid="NULL";	//   所有device重置为OFFLINE状态
				host.setCurrentTab(4);
				// unconnect network 
				}else if (mobNetInfo.isConnected() || wifiNetInfo.isConnected())  { 
				// connect network 
					Log.e("connectionReceiver", "connected"); 
				} 
			} 
		};
		IntentFilter intentFilter = new IntentFilter(); 
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION); 
		registerReceiver(connectionReceiver, intentFilter);
	}
	
	
	 

	
	@Override                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();	
//		mUid=myPreferences.getString("uid", "NULL");
		Log.e("leewoo", "TabControl---onStart："+mUid);
	
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e("leewoo", "TabControl---onDestroy");	
//		editor.putString("uid","NULL");
//		editor.commit();
		mUid="NULL";
		TUTKClient.stop();
		writeDB.close();
		if (connectionReceiver != null) { 
			unregisterReceiver(connectionReceiver); 
		}
        
		System.exit(0);
	}
	
	
}


