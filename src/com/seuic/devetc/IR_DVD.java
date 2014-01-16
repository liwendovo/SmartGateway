package com.seuic.devetc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class IR_DVD extends Activity implements android.view.View.OnClickListener{
	
	Button backBtn,leanrnBtn;
	ImageView   devpic;
	ImageView   button1,button2,
				button3,button4,button5,
				button6,button7,button8,
				button9,button10,button11,
				button12,button13,button14,
				button15,button16;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ir_dvd);
		
		backBtn=(Button)findViewById(R.id.back);
		leanrnBtn=(Button)findViewById(R.id.titleBtn);		
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
		button11=(ImageView)findViewById(R.id.button11);
		button12=(ImageView)findViewById(R.id.button12);
		button13=(ImageView)findViewById(R.id.button13);
		button14=(ImageView)findViewById(R.id.button14);
		button15=(ImageView)findViewById(R.id.button15);
		button16=(ImageView)findViewById(R.id.button16);		
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
		button11.setOnClickListener(this);  
		button12.setOnClickListener(this);  
		button13.setOnClickListener(this); 
		button14.setOnClickListener(this); 
		button15.setOnClickListener(this); 
		button16.setOnClickListener(this); 
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
		TabControl.mViewSelected.setImageViewClickChanged(button11);
		TabControl.mViewSelected.setImageViewClickChanged(button12);
		TabControl.mViewSelected.setImageViewClickChanged(button13);
		TabControl.mViewSelected.setImageViewClickChanged(button14);
		TabControl.mViewSelected.setImageViewClickChanged(button15);
		TabControl.mViewSelected.setImageViewClickChanged(button16);
		
		
		devpic=(ImageView)findViewById(R.id.pic);
		devpic.setImageDrawable(getResources().getDrawable(R.drawable.ir_dvd));
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
