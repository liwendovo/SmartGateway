package com.seuic.devetc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class IR_TV extends Activity implements android.view.View.OnClickListener{
	Button backBtn,leanrnBtn;
	ImageView   devpic;
	ImageView  button1,button2,button3,
			button4,button5,button6,
			button7,button8,button9,
			button10,button11,button12,button13,button14;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ir_tv);

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
		
		devpic=(ImageView)findViewById(R.id.pic);
		devpic.setImageDrawable(getResources().getDrawable(R.drawable.ir_tv));
		
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
//		button1.setClickable(false);
//		button2.setClickable(false);
//		button3.setClickable(false);
//		button4.setClickable(false);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 switch(v.getId())  
	        {  
	        case R.id.button1: 
	        	TabControl.mViewSelected.setImageViewFocusChanged(button1);
	            break;  
	        case R.id.button2:  
	        	TabControl.mViewSelected.setImageViewFocusChanged(button2);
	            break;  
	        case R.id.button3:  
	        	TabControl.mViewSelected.setImageViewFocusChanged(button3);
	            break;  
	        case R.id.button4:  
	        	TabControl.mViewSelected.setImageViewFocusChanged(button4);
	            break;  
	        case R.id.button5:  
	        	TabControl.mViewSelected.setImageViewFocusChanged(button5);
	            break;  
	        case R.id.button6:  
	        	TabControl.mViewSelected.setImageViewFocusChanged(button6);
	            break;  
	        case R.id.button7:  
	        	TabControl.mViewSelected.setImageViewFocusChanged(button7);
	            break;  
	        case R.id.button8:  
	        	TabControl.mViewSelected.setImageViewFocusChanged(button8);
	            break;  
	        case R.id.button9:  
	        	TabControl.mViewSelected.setImageViewFocusChanged(button9);
	            break;  
	        case R.id.button10:  
	        	TabControl.mViewSelected.setImageViewFocusChanged(button10);
	            break;  
	        case R.id.button11:  
	        	TabControl.mViewSelected.setImageViewFocusChanged(button11);
	            break;  
	        case R.id.button12:  
	        	TabControl.mViewSelected.setImageViewFocusChanged(button12);
	            break;  
	        case R.id.button13:  
	        	TabControl.mViewSelected.setImageViewFocusChanged(button13);
	            break;  
	        case R.id.button14:  
	        	TabControl.mViewSelected.setImageViewFocusChanged(button14);
	            break;  
	       
	        default:  
	            break;  
	        }  
	  
	}

}
