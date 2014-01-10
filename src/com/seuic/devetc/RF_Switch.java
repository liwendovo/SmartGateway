package com.seuic.devetc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class RF_Switch extends Activity implements android.view.View.OnClickListener{
	Button  backBtn,leanrnBtn;
	ImageView   devpic;
	ImageView  button1,button2,button3,
			button4,button5,button6,
			button7,button8,button9;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.rf_switch);
		backBtn=(Button)findViewById(R.id.back);
		leanrnBtn=(Button)findViewById(R.id.titleBtn);
		button1=(ImageView)findViewById(R.id.button1);
		button2=(ImageView)findViewById(R.id.button2);
		devpic=(ImageView)findViewById(R.id.pic);
		devpic.setImageDrawable(getResources().getDrawable(R.drawable.rf_switch));	
		
		backBtn.setOnClickListener(this); 
		leanrnBtn.setOnClickListener(this); 
		button1.setOnClickListener(this);  
		button2.setOnClickListener(this);  
		TabControl.mViewSelected.setButtonFocusChanged(backBtn);
		TabControl.mViewSelected.setButtonFocusChanged(leanrnBtn);
		TabControl.mViewSelected.setImageViewFocusChanged(button1);
		TabControl.mViewSelected.setImageViewFocusChanged(button2);
		
		
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
