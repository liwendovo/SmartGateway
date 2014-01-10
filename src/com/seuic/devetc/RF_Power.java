package com.seuic.devetc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class RF_Power extends Activity implements android.view.View.OnClickListener{
	
	Button  backBtn,leanrnBtn;
	ImageView   devpic;
	ImageView  button1,button2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rf_power);
        backBtn=(Button)findViewById(R.id.back);
		leanrnBtn=(Button)findViewById(R.id.titleBtn);
		button1=(ImageView)findViewById(R.id.button1);
		button2=(ImageView)findViewById(R.id.button2);
		devpic=(ImageView)findViewById(R.id.pic);
		devpic.setImageDrawable(getResources().getDrawable(R.drawable.rf_power));
        
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
	       
	        default:  
	            break;  
	        }  
	}

   
}
