package com.seuic.smartgateway ;  

import com.seuic.adapter.DevChoiceAdapter;
import com.seuic.function.CustomToast;
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
	ToggleButton setTempBtn;
	RelativeLayout    layoutDev,layoutCam,layoutTemp,layoutTime,layoutReset,layoutAbout;

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
				TabControl.editor.putBoolean("tempmode",false);
				TabControl.editor.commit();
//				TabControl.tempmode = false;
				TabControl.mSQLHelper.updateFah(TabControl.writeDB,TabControl.mUid,1);			
			} else {
				setTempBtn.setBackgroundResource(R.drawable.rf_switch_yellow);
				TUTKClient.setTempMode(0);
				TabControl.editor.putBoolean("tempmode",true);
				TabControl.editor.commit();
//				TabControl.tempmode = true;
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
				setTempBtn.setChecked(false);
				setTempBtn.setBackgroundResource(R.drawable.rf_switch_blue);	
			}else{
				Log.e("leewoo", "fah==0");
				setTempBtn.setChecked(true);
				setTempBtn.setBackgroundResource(R.drawable.rf_switch_yellow);
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
//                	 finish();
                	 TUTKClient.stoptutk();
                	 System.exit(0);
                 }
                 return true;
         }
         return super.onKeyDown(keyCode, event);
 }
	
	
	
		
		
	
}
  
