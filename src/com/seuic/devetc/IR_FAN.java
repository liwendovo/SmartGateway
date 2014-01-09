package com.seuic.devetc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class IR_FAN extends Activity implements android.view.View.OnClickListener{
	Button  backBtn,leanrnBtn;
	ImageView  button1,button2,button3,
			button4;
	Button  button5,button6,button7;
	ImageView   devpic;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ir_fan);
		backBtn=(Button)findViewById(R.id.back);
		leanrnBtn=(Button)findViewById(R.id.titleBtn);
		button1=(ImageView)findViewById(R.id.button1);
		button2=(ImageView)findViewById(R.id.button2);
		button3=(ImageView)findViewById(R.id.button3);		
		button4=(ImageView)findViewById(R.id.button4);		
		button5=(Button)findViewById(R.id.button5);
		button6=(Button)findViewById(R.id.button6);	
		button7=(Button)findViewById(R.id.button7);
		devpic=(ImageView)findViewById(R.id.pic);
		devpic.setImageDrawable(getResources().getDrawable(R.drawable.ir_fan));
		
		
		backBtn.setOnClickListener(this); 
		leanrnBtn.setOnClickListener(this); 
		button1.setOnClickListener(this);  
		button2.setOnClickListener(this);  
		button3.setOnClickListener(this);  
		button4.setOnClickListener(this);  
		button5.setOnClickListener(this);  
		button6.setOnClickListener(this);  
		button7.setOnClickListener(this);
		 
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
        case R.id.button4:  
        	TabControl.mImageViewSelected.setButtonFocusChanged(button4);
            break;  
//        case R.id.button5:  
//        	TabControl.mImageViewSelected.setButtonFocusChanged(button5);
//            break;  
//        case R.id.button6:  
//        	TabControl.mImageViewSelected.setButtonFocusChanged(button6);
//            break;  
//        case R.id.button7:  
//        	TabControl.mImageViewSelected.setButtonFocusChanged(button7);
//            break;  
//   
        default:  
            break;  
       
        }
	}

	
}
