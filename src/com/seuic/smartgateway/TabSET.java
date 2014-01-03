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
import android.widget.TextView;

public class TabSET extends Activity {

	Button titleBtn,homeBtn;
	ImageView titlePic;
	Button aboutBtn,resetBtn,devSetBtn,camSetBtn,timeSetBtn;
	SharedPreferences myPreferences;
	SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabset);
		myPreferences= getSharedPreferences("devset", Activity.MODE_PRIVATE);
		
		
		
		aboutBtn=(Button)findViewById(R.id.aboutBtn);
		resetBtn=(Button)findViewById(R.id.resetBtn);
		
		homeBtn=(Button)findViewById(R.id.back);
		titlePic=(ImageView)findViewById(R.id.pic);
		titleBtn=(Button)findViewById(R.id.titleBtn);
		
    	homeBtn.setBackgroundResource(R.drawable.ep_logo);
    	titlePic.setImageResource(R.drawable.tab_rf);
    	titleBtn.setBackgroundResource(R.drawable.title_add);
    	
//		titleBtn.setVisibility(Button.INVISIBLE);
		
		devSetBtn=(Button)findViewById(R.id.devSetBtn);
		camSetBtn=(Button)findViewById(R.id.camSetBtn);
		timeSetBtn=(Button)findViewById(R.id.timeSetBtn);
		
		
	   aboutBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				aboutus();
			}
		});
	   resetBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("leewoo", "reset");
			}
		});
	   devSetBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(TabSET.this,SetupDev.class);	
				 startActivity(intent);	
			}
		}); 
	   camSetBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(TabSET.this,SetupCam.class);	
				 startActivity(intent);	
			}
		});
	   
	   timeSetBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(TabSET.this,SetupTime.class);	
				 startActivity(intent);	
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
}
  
