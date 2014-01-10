package com.seuic.devetc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class RF_Custom2 extends Activity implements android.view.View.OnClickListener{
	Button  backBtn,leanrnBtn;
	ImageView  button1,button2,button3,
			button4,button5,button6,
			button7,button8,button9,button10;
	ImageView   devpic;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.rf_custom2);
		
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
		devpic=(ImageView)findViewById(R.id.pic);
		devpic.setImageDrawable(getResources().getDrawable(R.drawable.ir_custom));
		
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
		TabControl.mViewSelected.setImageViewFocusChanged(button1);
		TabControl.mViewSelected.setImageViewFocusChanged(button2);
		TabControl.mViewSelected.setImageViewFocusChanged(button3);
		TabControl.mViewSelected.setImageViewFocusChanged(button4);
		TabControl.mViewSelected.setImageViewFocusChanged(button5);
		TabControl.mViewSelected.setImageViewFocusChanged(button6);
		TabControl.mViewSelected.setImageViewFocusChanged(button7);
		TabControl.mViewSelected.setImageViewFocusChanged(button8);
		TabControl.mViewSelected.setImageViewFocusChanged(button9);
		TabControl.mViewSelected.setImageViewFocusChanged(button10);
	
	}

	 protected void dialog() {
		 AlertDialog.Builder builder = new Builder(RF_Custom2.this);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 switch(v.getId())  
	        {  
//	        case R.id.button1: 
//	        	TabControl.mViewSelected.setImageViewFocusChanged(button1);
//	            break;  
//	        case R.id.button2:  
//	        	TabControl.mViewSelected.setImageViewFocusChanged(button2);
//	            break;  
//	        case R.id.button3:  
//	        	TabControl.mViewSelected.setImageViewFocusChanged(button3);
//	            break;  
//	        case R.id.button4:  
//	        	TabControl.mViewSelected.setImageViewFocusChanged(button4);
//	            break;  
//	        case R.id.button5:  
//	        	TabControl.mViewSelected.setImageViewFocusChanged(button5);
//	            break;  
//	        case R.id.button6:  
//	        	TabControl.mViewSelected.setImageViewFocusChanged(button6);
//	            break;  
//	        case R.id.button7:  
//	        	TabControl.mViewSelected.setImageViewFocusChanged(button7);
//	            break;  
//	        case R.id.button8:  
//	        	TabControl.mViewSelected.setImageViewFocusChanged(button8);
//	            break;  
//	        case R.id.button9:  
//	        	TabControl.mViewSelected.setImageViewFocusChanged(button9);
//	            break;  
//	        case R.id.button10:  
//	        	TabControl.mViewSelected.setImageViewFocusChanged(button10);
//	            break;  
	        default:  
	            break;  
	        }  
	}

}
