package com.seuic.smartgateway;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class SetupTime extends Activity{
	Button titleBtn,homeBtn;
	ImageView titlePic;	
	Spinner spinnerZone;
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
    	
    	spinnerZone=(Spinner)findViewById(R.id.spinnerZone);    	
    	adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TabControl.itemsIR);
		//设置下拉列表的风格  
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);            
		//将adapter 添加到spinner中  
		spinnerZone.setAdapter(adapter);            
		//设置默认值  
		spinnerZone.setVisibility(View.VISIBLE);  
	}
}
