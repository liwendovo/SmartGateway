package com.seuic.devetc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class RF_Lamp extends Activity implements android.view.View.OnClickListener{
	Button  backBtn,leanrnBtn;
	ImageView   devpic;
	ImageView  button1,button2,button3,
			button4,button5,button6,
			button7;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.rf_lamp);

		backBtn=(Button)findViewById(R.id.back);
		leanrnBtn=(Button)findViewById(R.id.titleBtn);
		button1=(ImageView)findViewById(R.id.button1);
		button2=(ImageView)findViewById(R.id.button2);
		button3=(ImageView)findViewById(R.id.button3);		
		button4=(ImageView)findViewById(R.id.button4);		
		button5=(ImageView)findViewById(R.id.button5);
		button6=(ImageView)findViewById(R.id.button6);		
		button7=(ImageView)findViewById(R.id.button7);		
		devpic=(ImageView)findViewById(R.id.pic);
		devpic.setImageDrawable(getResources().getDrawable(R.drawable.rf_lamp));
		
		backBtn.setOnClickListener(this); 
		leanrnBtn.setOnClickListener(this); 
		button1.setOnClickListener(this);  
		button2.setOnClickListener(this);  
		button3.setOnClickListener(this);  
		button4.setOnClickListener(this);  
		button5.setOnClickListener(this);  
		button6.setOnClickListener(this);  
		button7.setOnClickListener(this);  
		TabControl.mViewSelected.setButtonClickChanged(backBtn);
		TabControl.mViewSelected.setButtonClickChanged(leanrnBtn);
		TabControl.mViewSelected.setImageViewClickChanged(button1);
		TabControl.mViewSelected.setImageViewClickChanged(button2);
		TabControl.mViewSelected.setImageViewClickChanged(button3);
		TabControl.mViewSelected.setImageViewClickChanged(button4);
		TabControl.mViewSelected.setImageViewClickChanged(button5);
		TabControl.mViewSelected.setImageViewClickChanged(button6);
		TabControl.mViewSelected.setImageViewClickChanged(button7);
	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())  
        {  
        case R.id.back:
        	finish();
        	break;
        case R.id.titleBtn:
   		break;
        case R.id.button1: 
            break;  
        case R.id.button2:  
           break;  
        case R.id.button3:  
            break;  
        
        default:  
            break;  
       
        }
	}

	
}
