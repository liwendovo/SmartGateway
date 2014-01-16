package com.seuic.devetc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class IR_Media extends Activity implements android.view.View.OnClickListener {
	Button  backBtn,leanrnBtn;
	ImageView   devpic;
	ImageView  button1,button2,button3,
			button4,button5,button6,
			button7,button8,button9,button10;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ir_media);
		
	
		backBtn=(Button)findViewById(R.id.back);
		leanrnBtn=(Button)findViewById(R.id.titleBtn);
		devpic=(ImageView)findViewById(R.id.pic);
		devpic.setImageDrawable(getResources().getDrawable(R.drawable.ir_media));
		
		
		button1=(ImageView)findViewById(R.id.button1);
		button2=(ImageView)findViewById(R.id.button2);
		button3=(ImageView)findViewById(R.id.button3);		
		button4=(ImageView)findViewById(R.id.button4);		
		button5=(ImageView)findViewById(R.id.button5);
		button6=(ImageView)findViewById(R.id.button6);		
		button7=(ImageView)findViewById(R.id.button7);		
		button8=(ImageView)findViewById(R.id.button8);
		button9=(ImageView)findViewById(R.id.button9);		
		button10=(ImageView)findViewById(R.id.button10);
		
		backBtn.setOnClickListener(this); 
		leanrnBtn.setOnClickListener(this); 
		button1.setOnClickListener(this);  
		button2.setOnClickListener(this);  
		button3.setOnClickListener(this);  
		button4.setOnClickListener(this);  
		button5.setOnClickListener(this);  
		button6.setOnClickListener(this);  
		button7.setOnClickListener(this);  
		button8.setOnClickListener(this); 
		button9.setOnClickListener(this);  
		button10.setOnClickListener(this); 
		TabControl.mViewSelected.setButtonClickChanged(backBtn);
		TabControl.mViewSelected.setButtonClickChanged(leanrnBtn);
		TabControl.mViewSelected.setImageViewClickChanged(button1);
		TabControl.mViewSelected.setImageViewClickChanged(button2);
		TabControl.mViewSelected.setImageViewClickChanged(button3);
		TabControl.mViewSelected.setImageViewClickChanged(button4);
		TabControl.mViewSelected.setImageViewClickChanged(button5);
		TabControl.mViewSelected.setImageViewClickChanged(button6);
		TabControl.mViewSelected.setImageViewClickChanged(button7);
		TabControl.mViewSelected.setImageViewClickChanged(button8);
		TabControl.mViewSelected.setImageViewClickChanged(button9);
		TabControl.mViewSelected.setImageViewClickChanged(button10);
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
        case R.id.button4:  
           break;  
        case R.id.button5:  
           break;  
        case R.id.button6:  
           break;  
        case R.id.button7:  
           break;  
        case R.id.button8:  
           break;  
        case R.id.button9:  

            break;  
        default:  
            break;  
       
        }	
	}

	
}
