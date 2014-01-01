package com.seuic.smartgateway ;  

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TabTH extends Activity {

	Button titleBtn,homeBtn;
	TextView titleTxt;
	SharedPreferences myPreferences;
	SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabth);
		homeBtn=(Button)findViewById(R.id.leftBtn);
		titleTxt = (TextView)findViewById(R.id.titleTxt);
		titleBtn=(Button)findViewById(R.id.rightBtn);	
		homeBtn.setText("Home");
		titleTxt.setText("TH");
		titleBtn.setText("Chart");
		
		homeBtn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
}
  
