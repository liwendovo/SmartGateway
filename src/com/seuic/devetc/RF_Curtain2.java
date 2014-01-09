package com.seuic.devetc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class RF_Curtain2 extends Activity implements android.view.View.OnClickListener {
	
	Button  backBtn,leanrnBtn;
	ImageView   devpic;
	ImageView  button1,button2,button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rf_curtain2);
        backBtn=(Button)findViewById(R.id.back);
		leanrnBtn=(Button)findViewById(R.id.titleBtn);
		button1=(ImageView)findViewById(R.id.button1);
		button2=(ImageView)findViewById(R.id.button2);
		button3=(ImageView)findViewById(R.id.button3);		
		devpic=(ImageView)findViewById(R.id.pic);
		devpic.setImageDrawable(getResources().getDrawable(R.drawable.rf_curtain));		
		
		backBtn.setOnClickListener(this); 
		leanrnBtn.setOnClickListener(this); 
		button1.setOnClickListener(this);  
		button2.setOnClickListener(this);  
		button3.setOnClickListener(this);
}
    
    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
    	switch(v.getId())  
        {  
//        case R.id.back:
//        	TabControl.mImageViewSelected.setButtonFocusChanged(backBtn);
//        	finish();
//        	break;
//        case R.id.titleBtn:
//        	TabControl.mImageViewSelected.setButtonFocusChanged(leanrnBtn);
//    		break;
        case R.id.button1: 
        	TabControl.mImageViewSelected.setButtonFocusChanged(button1);
            break;  
        case R.id.button2:  
        	TabControl.mImageViewSelected.setButtonFocusChanged(button2);
            break;  
        case R.id.button3:  
        	TabControl.mImageViewSelected.setButtonFocusChanged(button3);
            break;  
        
        default:  
            break;  
       
        }
	}
}
