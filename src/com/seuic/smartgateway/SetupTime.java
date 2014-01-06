package com.seuic.smartgateway;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class SetupTime extends Activity{
	Button titleBtn,homeBtn;
	ImageView titlePic;	
	Spinner spinnerZone;
	ToggleButton timeAutoBtn,timeHourBtn;
	private ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setup_time);
		
		homeBtn=(Button)findViewById(R.id.back);
		titlePic=(ImageView)findViewById(R.id.pic);
		titleBtn=(Button)findViewById(R.id.titleBtn);
		
    	homeBtn.setBackgroundResource(R.drawable.title_back);
    	titlePic.setImageResource(R.drawable.set_time);
    	titleBtn.setBackgroundResource(R.drawable.title_add);
    	titleBtn.setVisibility(Button.INVISIBLE);
    	
    	timeAutoBtn=(ToggleButton)findViewById(R.id.timeAutoBtn);
    	timeHourBtn=(ToggleButton)findViewById(R.id.timeHourBtn);
    	
    	
    	spinnerZone=(Spinner)findViewById(R.id.spinnerZone);     
    	adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TabControl.itemsIR);
		//设置下拉列表的风格  
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);            
		//将adapter 添加到spinner中  
		spinnerZone.setAdapter(adapter);            
		//设置默认值  
		spinnerZone.setVisibility(View.VISIBLE);  
		timeAutoBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (timeAutoBtn.isChecked()) {
						timeAutoBtn.setBackgroundResource(R.drawable.rf_switch_yellow);
					} else {
						timeAutoBtn.setBackgroundResource(R.drawable.rf_switch_blue);
					}
					//状态记录 数据库			
				}
			   });
		
		timeHourBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (timeHourBtn.isChecked()) {
						timeHourBtn.setBackgroundResource(R.drawable.rf_switch_yellow);
					} else {
						timeHourBtn.setBackgroundResource(R.drawable.rf_switch_blue);
					}
					//状态记录 数据库			
				}
			   });
		
	}
}
