package com.seuic.smartgateway ;  

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

public class TabSET extends Activity {

	Button titleBtn,homeBtn;
	ImageView titlePic;

	ToggleButton setTempBtn;
	RelativeLayout    layoutDev,layoutCam,layoutTemp,layoutTime,layoutRest,layoutAbout;
	SharedPreferences myPreferences;
	SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabset);
		myPreferences= getSharedPreferences("devset", Activity.MODE_PRIVATE);
				
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
		layoutRest=(RelativeLayout)findViewById(R.id.layoutRest);
		layoutAbout=(RelativeLayout)findViewById(R.id.layoutAbout);
		
		layoutDev.setBackgroundResource(android.R.drawable.list_selector_background);
		layoutCam.setBackgroundResource(android.R.drawable.list_selector_background);
		layoutTemp.setBackgroundResource(android.R.drawable.list_selector_background);
		layoutTime.setBackgroundResource(android.R.drawable.list_selector_background);
		layoutRest.setBackgroundResource(android.R.drawable.list_selector_background);		
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
				setTempBtn.setBackgroundResource(R.drawable.rf_switch_yellow);
			} else {
				setTempBtn.setBackgroundResource(R.drawable.rf_switch_blue);
			}
			//×´Ì¬¼ÇÂ¼ Êý¾Ý¿â			
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
	   layoutRest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("leewoo", "reset");
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
		Log.e("leewoo", "TabSET---onStart");
	}
}
  
