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
import android.widget.TextView;

public class TabSET extends Activity {

	Button titleBtn,homeBtn;
	TextView titleTxt;
	Button aboutBtn,resetBtn,devSetBtn,camSetBtn;
	SharedPreferences myPreferences;
	SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabset);
		myPreferences= getSharedPreferences("devset", Activity.MODE_PRIVATE);
		aboutBtn=(Button)findViewById(R.id.aboutBtn);
		resetBtn=(Button)findViewById(R.id.resetBtn);
		
		homeBtn=(Button)findViewById(R.id.leftBtn);
		titleTxt = (TextView)findViewById(R.id.titleTxt);
		titleBtn=(Button)findViewById(R.id.rightBtn);	
		
		homeBtn.setText("Home");
		titleTxt.setText("SET");
		titleBtn.setVisibility(Button.INVISIBLE);
		
		devSetBtn=(Button)findViewById(R.id.devSetBtn);
		camSetBtn=(Button)findViewById(R.id.camSetBtn);
		
		
		
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
				 Intent intent = new Intent(TabSET.this,DevSetup.class);	
				 startActivity(intent);	
			}
		});
	homeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
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
  
