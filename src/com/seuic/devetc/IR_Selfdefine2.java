package com.seuic.devetc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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
	Button  backBtn;
	Button  button1,button2,
			button3,button4,button5,
			button6,button7,button8,
			button9,button10,button11,
			button12,button13,button14;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ir_selfdefine2);
		backBtn=(Button)findViewById(R.id.back);		
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
		
	
	}
	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
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
	        case R.id.button10:  
	        	Log.e("leewoo", "button7 onLongClick");
	        	dialog();
	            break;  
	        case R.id.button11:  
	        	Log.e("leewoo", "button7 onLongClick");
	        	dialog();
	            break;  
	        case R.id.button12:  
	        	Log.e("leewoo", "button7 onLongClick");
	        	dialog();
	            break;  
	        case R.id.button13:  
	        	Log.e("leewoo", "button7 onLongClick");
	        	dialog();
	            break;  
	        case R.id.button14:  
	        	Log.e("leewoo", "button7 onLongClick");
	        	dialog();
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
        default:  
            break;  
        }  		
	}

	
	 protected void dialog() {
		 AlertDialog.Builder builder = new Builder(IR_Selfdefine2.this);
		 builder.setMessage("Please input name");
		 builder.setTitle("Button name");
		 builder.setView(new EditText(this));
		 builder.setPositiveButton("Confirm", new OnClickListener() {
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
		});
		  builder.setNegativeButton("Cancle", new OnClickListener() {
		@Override
		 public void onClick(DialogInterface dialog, int which) {
		 dialog.dismiss();
		  }
		 });
		 builder.create().show();
	   }

}
