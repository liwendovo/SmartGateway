package com.seuic.devetc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.seuic.smartgateway.R;

public class IR_Selfdefine2 extends Activity implements android.view.View.OnClickListener,OnLongClickListener{
	int devid;
	Button  backBtn,leanrnBtn;
	Button  button1,button2,
			button3,button4,button5,
			button6,button7,button8,
			button9,button10,button11,
			button12,button13,button14;
	String btnName1,btnName2,
		   btnName3,btnName4,btnName5,
		   btnName6,btnName7,btnName8,
		   btnName9,btnName10,btnName11,
		   btnName12,btnName13,btnName14;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ir_selfdefine2);
		
		Intent intent=getIntent();
		devid=intent.getIntExtra("devid", 0);
		if(devid==0){
			Log.e("leewoo", "deid error = 0");
			}
		backBtn=(Button)findViewById(R.id.back);
		leanrnBtn=(Button)findViewById(R.id.titleBtn);
		button1=(Button)findViewById(R.id.button1);
		button2=(Button)findViewById(R.id.button2);
		button3=(Button)findViewById(R.id.button3);		
		button4=(Button)findViewById(R.id.button4);		
		button5=(Button)findViewById(R.id.button5);
		button6=(Button)findViewById(R.id.button6);		
		button7=(Button)findViewById(R.id.button7);		
		button8=(Button)findViewById(R.id.button8);
		button9=(Button)findViewById(R.id.button9);
		button10=(Button)findViewById(R.id.button10);
		button11=(Button)findViewById(R.id.button11);
		button12=(Button)findViewById(R.id.button12);
		button13=(Button)findViewById(R.id.button13);
		button14=(Button)findViewById(R.id.button14);
		
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
		button1.setOnLongClickListener(this);
		button2.setOnLongClickListener(this);
		button3.setOnLongClickListener(this);
		button4.setOnLongClickListener(this);
		button5.setOnLongClickListener(this);
		button6.setOnLongClickListener(this);
		button7.setOnLongClickListener(this);
		button8.setOnLongClickListener(this);
		button9.setOnLongClickListener(this);
		button10.setOnLongClickListener(this);
		button11.setOnLongClickListener(this);
		button12.setOnLongClickListener(this);
		button13.setOnLongClickListener(this);
		button14.setOnLongClickListener(this);
		//°´¼üÃû³¼Ë¢ÐÂ btnName
	
	}
	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		  switch(v.getId())  
	        {  
	        case R.id.button1:  
	        	
	        	dialog(1);
	            break;  
	        case R.id.button2:  
	        	Log.e("leewoo", "button2 onLongClick");
	        	dialog(2);
	            break;  
	        case R.id.button3:  
	        	Log.e("leewoo", "button3 onLongClick");
	        	dialog(3);
	            break;  
	        case R.id.button4:  
	        	Log.e("leewoo", "button4 onLongClick");
	        	dialog(4);
	            break;  
	        case R.id.button5:  
	        	Log.e("leewoo", "button5 onLongClick");
	        	dialog(5);
	            break;  
	        case R.id.button6:  
	        	Log.e("leewoo", "button6 onLongClick");
	        	dialog(6);
	            break;  
	        case R.id.button7:  
	        	Log.e("leewoo", "button7 onLongClick");
	        	dialog(7);
	            break;  
	        case R.id.button8:  
	        	Log.e("leewoo", "button7 onLongClick");
	        	dialog(8);
	            break;  
	        case R.id.button9:  
	        	Log.e("leewoo", "button7 onLongClick");
	        	dialog(9);
	            break;  
	        case R.id.button10:  
	        	Log.e("leewoo", "button7 onLongClick");
	        	dialog(10);
	            break;  
	        case R.id.button11:  
	        	Log.e("leewoo", "button7 onLongClick");
	        	dialog(11);
	            break;  
	        case R.id.button12:  
	        	Log.e("leewoo", "button7 onLongClick");
	        	dialog(12);
	            break;  
	        case R.id.button13:  
	        	Log.e("leewoo", "button7 onLongClick");
	        	dialog(13);
	            break;  
	        case R.id.button14:  
	        	Log.e("leewoo", "button7 onLongClick");
	        	dialog(14);
	            break;  
	            
	        default:  
	            break;  
	        }  
	        
		return false;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())  
        {  
        case R.id.back: finish();break;
        case R.id.titleBtn: break;
        default:  
            break;  
        }  		
	}

	
	 protected void dialog(int btnid) {
		 AlertDialog.Builder builder = new Builder(IR_Selfdefine2.this);
		 builder.setMessage("Please input name");
		 builder.setTitle("Button name");
		 builder.setView(new EditText(this));
		 builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			//devid;
		}
		});
		  builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
		@Override
		 public void onClick(DialogInterface dialog, int which) {
		 dialog.dismiss();
		  }
		 });
		 builder.create().show();
	   }

}
