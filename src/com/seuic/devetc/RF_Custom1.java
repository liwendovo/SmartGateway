package com.seuic.devetc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class RF_Custom1 extends Activity implements android.view.View.OnClickListener,OnLongClickListener{
	Button  backBtn,leanrnBtn;
	LinearLayout back_ll,titleBtn_ll;
	Button  button1,button2,button3,
			button4,button5,button6,
			button7,button8,button9;
	ImageView   devpic;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.rf_custom1);
		Log.e("leewoo", "in RF_Custom1 ");
		backBtn=(Button)findViewById(R.id.back);		
		leanrnBtn=(Button)findViewById(R.id.titleBtn);	
		back_ll=(LinearLayout)findViewById(R.id.back_ll);		
		titleBtn_ll=(LinearLayout)findViewById(R.id.titleBtn_ll);	
		button1=(Button)findViewById(R.id.button1);
		button2=(Button)findViewById(R.id.button2);
		button3=(Button)findViewById(R.id.button3);		
		button4=(Button)findViewById(R.id.button4);		
		button5=(Button)findViewById(R.id.button5);
		button6=(Button)findViewById(R.id.button6);		
		button7=(Button)findViewById(R.id.button7);		
		button8=(Button)findViewById(R.id.button8);
		button9=(Button)findViewById(R.id.button9);
		devpic=(ImageView)findViewById(R.id.pic);
		devpic.setImageDrawable(getResources().getDrawable(R.drawable.ir_custom));
		
		backBtn.setOnClickListener(this); 
		leanrnBtn.setOnClickListener(this); 
		back_ll.setOnClickListener(this); 
		titleBtn_ll.setOnClickListener(this);
		button1.setOnClickListener(this);  
		button2.setOnClickListener(this);  
		button3.setOnClickListener(this);  
		button4.setOnClickListener(this);  
		button5.setOnClickListener(this);  
		button6.setOnClickListener(this);  
		button7.setOnClickListener(this);  
		button8.setOnClickListener(this);  
		button9.setOnClickListener(this);  
		button1.setOnLongClickListener(this);
		button2.setOnLongClickListener(this);
		button3.setOnLongClickListener(this);
		button4.setOnLongClickListener(this);
		button5.setOnLongClickListener(this);
		button6.setOnLongClickListener(this);
		button7.setOnLongClickListener(this);
		button8.setOnLongClickListener(this);
		button9.setOnLongClickListener(this);		
		TabControl.mViewSelected.setButtonClickChanged(backBtn);
		TabControl.mViewSelected.setButtonClickChanged(leanrnBtn);
		TabControl.mViewSelected.setButtonClickChanged(button1);
		TabControl.mViewSelected.setButtonClickChanged(button2);
		TabControl.mViewSelected.setButtonClickChanged(button3);
		TabControl.mViewSelected.setButtonClickChanged(button4);
		TabControl.mViewSelected.setButtonClickChanged(button5);
		TabControl.mViewSelected.setButtonClickChanged(button6);
		TabControl.mViewSelected.setButtonClickChanged(button7);
		TabControl.mViewSelected.setButtonClickChanged(button8);
		TabControl.mViewSelected.setButtonClickChanged(button9);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		Log.e("leewoo", "Button id = " + v.getId());  
		switch(v.getId())  
        {  
        case R.id.back_ll:
        case R.id.back:
        	finish();
        	break;
        	
        case R.id.titleBtn_ll:
        case R.id.titleBtn:
   		break;
        case R.id.button1: 
        	Log.e("RF_Custom1", "button1 onClick");
            break;  
        case R.id.button2: 
        	Log.e("RF_Custom1", "button2 onClick");
           break;  
        case R.id.button3:  
        	Log.e("RF_Custom1", "button2 onClick");
            break;  
        default:  
            break;  
       
        } 
        
	}

	
	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
//		 dialog();
//	 	 Log.e("leewoo", "onLongClick");
//		 Log.e("leewoo", "Button id = " + v.getId());  
	        switch(v.getId())  
	        {  
	        case R.id.button1:  
	        	Log.e("leewoo", "Button1 onLongClick");
	        	 dialog();
	            break;  
	        case R.id.button2:  
	        	Log.e("leewoo", "button2 onLongClick");
	        	dialog();
	            break;  
	        case R.id.button3:  
	        	Log.e("leewoo", "button3 onLongClick");
	        	dialog();
	            break;  
	        case R.id.button4:  
	        	Log.e("leewoo", "button4 onLongClick");
	        	dialog();
	            break;  
	        case R.id.button5:  
	        	Log.e("leewoo", "button5 onLongClick");
	        	dialog();
	            break;  
	        case R.id.button6:  
	        	Log.e("leewoo", "button6 onLongClick");
	        	dialog();
	            break;  
	        case R.id.button7:  
	        	Log.e("leewoo", "button7 onLongClick");
	        	dialog();
	            break;  
	        case R.id.button8:  
	        	Log.e("leewoo", "button7 onLongClick");
	        	dialog();
	            break;  
	        case R.id.button9:  
	        	Log.e("leewoo", "button7 onLongClick");
	        	dialog();
	            break;  
	        default:  
	            break;  
	        }  
	        
		return false;
	}

	 protected void dialog() {
		 AlertDialog.Builder builder = new Builder(RF_Custom1.this);
		 builder.setMessage("Please input name");
		 builder.setTitle("Button name");
		 builder.setView(new EditText(this));
		 builder.setPositiveButton("Confirm", new OnClickListener() {
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
		});
		  builder.setNegativeButton("Cancel", new OnClickListener() {
		@Override
		 public void onClick(DialogInterface dialog, int which) {
		 dialog.dismiss();
		  }
		 });
		 builder.create().show();
	   }

}
