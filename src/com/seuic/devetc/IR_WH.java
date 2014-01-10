package com.seuic.devetc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class IR_WH extends Activity implements android.view.View.OnClickListener{
	Button  backBtn,leanrnBtn;
	ImageView   devpic;
	ImageView  button1,button2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ir_wh);

		backBtn=(Button)findViewById(R.id.back);
		leanrnBtn=(Button)findViewById(R.id.titleBtn);
		button1=(ImageView)findViewById(R.id.imageView2);
		button2=(ImageView)findViewById(R.id.imageView3);
		devpic=(ImageView)findViewById(R.id.pic);
		devpic.setImageDrawable(getResources().getDrawable(R.drawable.ir_wh));
		
		backBtn.setOnClickListener(this); 
		leanrnBtn.setOnClickListener(this); 
		button1.setOnClickListener(this);  
		button2.setOnClickListener(this);  
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())  
        {  
//        case R.id.back:
//        	TabControl.mViewSelected.setImageViewFocusChanged(backBtn);
//        	finish();
//        	break;
//        case R.id.titleBtn:
//        	TabControl.mViewSelected.setImageViewFocusChanged(leanrnBtn);
//    		break;
        case R.id.button1: 
        	TabControl.mViewSelected.setImageViewFocusChanged(button1);
            break;  
        case R.id.button2:  
        	TabControl.mViewSelected.setImageViewFocusChanged(button2);
            break;  
      
        default:  
            break;  
       
        }
	}

}
