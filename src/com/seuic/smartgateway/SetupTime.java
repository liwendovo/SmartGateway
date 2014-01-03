package com.seuic.smartgateway;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class SetupTime extends Activity{
	Button titleBtn,homeBtn;
	ImageView titlePic;	
	
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
	}
}
