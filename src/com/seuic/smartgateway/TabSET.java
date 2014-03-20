package com.seuic.smartgateway ;  

import com.seuic.adapter.CustomToast;
import com.seuic.adapter.DevChoiceAdapter;
import com.seuic.net.TUTKClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

public class TabSET extends Activity {

	Button titleBtn,homeBtn;
	ImageView titlePic;
	private Context context; 
	private ProgressDialog progressDialog; 
	ToggleButton setTempBtn;
	RelativeLayout    layoutDev,layoutCam,layoutTemp,layoutTime,layoutReset,layoutAbout;
//	SharedPreferences myPreferences;
//	SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabset);
//		myPreferences= getSharedPreferences("devset", Activity.MODE_PRIVATE);
				
		homeBtn=(Button)findViewById(R.id.back);
		titlePic=(ImageView)findViewById(R.id.pic);
		titleBtn=(Button)findViewById(R.id.titleBtn);
		
    	homeBtn.setBackgroundResource(R.drawable.ep_logo);
    	titlePic.setImageResource(R.drawable.tab_set_logo);
    	titleBtn.setVisibility(View.INVISIBLE);
    	

    
		layoutDev=(RelativeLayout)findViewById(R.id.layoutDev);
		layoutCam=(RelativeLayout)findViewById(R.id.layoutCam);
		layoutTemp=(RelativeLayout)findViewById(R.id.layoutTemp);
		layoutTime=(RelativeLayout)findViewById(R.id.layoutTime);
		layoutReset=(RelativeLayout)findViewById(R.id.layoutRest);
		layoutAbout=(RelativeLayout)findViewById(R.id.layoutAbout);
		
		layoutDev.setBackgroundResource(android.R.drawable.list_selector_background);
		layoutCam.setBackgroundResource(android.R.drawable.list_selector_background);
		layoutTemp.setBackgroundResource(android.R.drawable.list_selector_background);
		layoutTime.setBackgroundResource(android.R.drawable.list_selector_background);
		layoutReset.setBackgroundResource(android.R.drawable.list_selector_background);		
		layoutAbout.setBackgroundResource(android.R.drawable.list_selector_background);
//		layoutAbout.setBackgroundResource(android.R.drawable.btn_default);
		
		
		setTempBtn=(ToggleButton)findViewById(R.id.setTempBtn);
		
		if(TabControl.mUid!="NULL"){
			String uid = TabControl.mUid; 
			showProgressDialog(uid);
			
			
		}
		

		layoutDev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(TabSET.this,SetupDev.class);	
				 startActivity(intent);	
			}
		});
		
	   layoutCam.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(TabSET.this,SetupCam.class);	
				 startActivity(intent);	
			}
		});
	   
	   setTempBtn.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (setTempBtn.isChecked()) {
				setTempBtn.setBackgroundResource(R.drawable.rf_switch_blue);
				TUTKClient.setTempMode(1);
				TabControl.tempmode = false;
				TabControl.mSQLHelper.updateFah(TabControl.writeDB,TabControl.mUid,1);			
			} else {
				setTempBtn.setBackgroundResource(R.drawable.rf_switch_yellow);
				TUTKClient.setTempMode(0);
				TabControl.tempmode = true;
				TabControl.mSQLHelper.updateFah(TabControl.writeDB,TabControl.mUid,0);
				
			}
				
		}
	   });
	   
	   
	   layoutTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(TabSET.this,SetupTime.class);	
				 startActivity(intent);	
			}
		});
	   layoutReset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("leewoo", "reset");
				AlertDialog.Builder builder = new Builder(TabSET.this);
				 builder.setMessage(getResources().getString(R.string.resetinfo));
				 builder.setTitle(R.string.resettitle);
				 builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
		   		@Override
		   		public void onClick(DialogInterface arg0, int arg1) {
		   			// TODO Auto-generated method stub
		   			TabControl.mSQLHelper.deleteAll(TabControl.writeDB);
		   			}
				 });
				 builder.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
		   		@Override
		   		 public void onClick(DialogInterface dialog, int which) {
		   		
		   		  
		   		  }
				 });
				 builder.create().show();	
			}
		});
	   layoutAbout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				aboutus();
			}
		});

	}
	 protected void aboutus() {
		 AlertDialog.Builder builder = new Builder(TabSET.this);
		 builder.setMessage("Please input name");
		 builder.setTitle("About us");
		 builder.setMessage(this.getString(R.string.aboutus));		
		 builder.create().show();
	   }
	 

	

	 
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();		
		Cursor cursor=TabControl.mSQLHelper.seleteSetup(TabControl.writeDB,TabControl.mUid);
		Log.e("leewoo", "Tabset---onStart->cur:"+cursor.getCount()+" mUid:"+TabControl.mUid);
		if(cursor.getCount()>0){		
			int fah=cursor.getInt(3);
			if (fah==1) {
				Log.e("leewoo", "fah==1");
				setTempBtn.setChecked(true);
				setTempBtn.setBackgroundResource(R.drawable.rf_switch_yellow);
			}else{
				Log.e("leewoo", "fah==0");
				setTempBtn.setChecked(false);
				setTempBtn.setBackgroundResource(R.drawable.rf_switch_blue);				
			}
		
		}else{
		
		}		
		cursor.close();
	}
	
	
	
	 private long mExitTime;
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
		 Log.e("TabControl","get the keyback");
         if (keyCode == KeyEvent.KEYCODE_BACK) {
        	     Log.e("TabControl","get keyback");
				if ((System.currentTimeMillis() - mExitTime) > 2000) {
                         Object mHelperUtils;
                         Toast.makeText(this, "press again to exit the app", TabControl.time).show();
                         mExitTime = System.currentTimeMillis();

                 } else {
                	 finish();
//                	 System.exit(0);
                 }
                 return true;
         }
         return super.onKeyDown(keyCode, event);
 }
	
	 
	 public void showProgressDialog(final String uid){  
		 progressDialog = ProgressDialog.show(context,"" , "Connecting...", true, false); 
		 new Thread(){        
		     @Override  
		     public void run() {  
		    Message startMsg=new Message();
//		       TUTKClient.stop();
			   if(TUTKClient.start(uid))				 
			   {
				   startMsg.what=0;
			   }else{
				   startMsg.what=1;
			   }			   
		       handler.sendMessage(startMsg);  
		     }
		   }.start();      
	 }
	 
	 private Handler handler = new Handler(){ 
	        @Override  
	        public void handleMessage(Message msg) {  
	        	if(0==msg.what)
	        	{
	        		CustomToast.showToast(context, "Connect success", Toast.LENGTH_SHORT); 
	        		Cursor cursor=TabControl.mSQLHelper.seleteSetup(TabControl.writeDB,TabControl.mUid);
//	        		Log.e("leewoo", "Tabset---onStart->cur:"+cursor.getCount()+" mUid:"+TabControl.mUid);
	        		if(cursor.getCount()>0){
	        			int fah=cursor.getInt(3);
	        			int hour=cursor.getInt(4);   
	        			int timezone=cursor.getInt(5);  
	        		
	        			TUTKClient.setTempMode(fah);
	        			TUTKClient.setHourMode(hour);
	        			String a[]=context.getResources().getStringArray(R.array.timezone_entries);   
	        			String[] ss=new String[2];
	                	ss=a[timezone].split("UTC"); 
	                	ss[1]=ss[1].replace("+",""); 
	                	float i=4*Float.parseFloat(ss[1]);  
	                	Log.e("Device ", "timezone length="+ timezone);
	                	TUTKClient.setTimeZone((int)i);
	        		}	
	        		
	        	}else{
	        		CustomToast.showToast(context, "Can not connect to device, please check your device or if has connect to a wireless network", Toast.LENGTH_LONG); 
	        		DevChoiceAdapter.currentID=-1;
//	        		SetupDev.editor.putString("uid","NULL");
//					SetupDev.editor.commit();
					TabControl.mUid="NULL";//Œ¥¡¨Ω”÷√ø’
//	        		notifyDataSetChanged();
	        	}
//	            //πÿ±’ProgressDialog  
	            progressDialog.dismiss(); 

	        }};  
		
		
	
}
  
