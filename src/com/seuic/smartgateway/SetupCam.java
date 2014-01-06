package com.seuic.smartgateway;

import com.seuic.add.AddCam;
import com.seuic.add.AddDev;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class SetupCam extends Activity{
	Button titleBtn,homeBtn;
	ImageView titlePic;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setup_cam);
		
		homeBtn=(Button)findViewById(R.id.back);
		titlePic=(ImageView)findViewById(R.id.pic);
		titleBtn=(Button)findViewById(R.id.titleBtn);
		
    	homeBtn.setBackgroundResource(R.drawable.title_back);
    	titlePic.setImageResource(R.drawable.tab_cam);
    	titleBtn.setBackgroundResource(R.drawable.title_add);
    	
    	
    	
//    	
//    	titleBtn.setOnClickListener(new OnClickListener()
//		{		
//			public void onClick(View source)
//			{
//				Intent intent = new Intent(SetupCam.this
//						, AddCam.class);					
//					//Æô¶¯Activity
//					startActivity(intent);				
//			}			
//		});	
	}
}
