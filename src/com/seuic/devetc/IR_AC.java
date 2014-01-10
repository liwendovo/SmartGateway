package com.seuic.devetc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class IR_AC extends Activity implements android.view.View.OnClickListener,OnLongClickListener {
	Button backBtn,leanrnBtn;
	ImageView  button10;
	Button  button1,button2,button3,
			button4,button5,button6,
			button7,button8,button9;
	 Boolean lenclr=false;
	 Boolean btnclr1=false;
	 Boolean btnclr2=false;
	 Boolean btnclr3=false;
	 Boolean btnclr4=false;
	 Boolean btnclr5=false;
	 Boolean btnclr6=false;
	 Boolean btnclr7=false;
	 Boolean btnclr8=false;
	 Boolean btnclr9=false;
	 Boolean btnclr10=false;
	 
	String learnFalse="false";
	String learnTrue="true";
	String btnDefaults="自定义";
	int devid;
	String mUid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ir_ac);
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
		
		TabControl.mViewSelected.setButtonFocusChanged(button1);
		TabControl.mViewSelected.setButtonFocusChanged(button2);
		TabControl.mViewSelected.setButtonFocusChanged(button3);
		TabControl.mViewSelected.setButtonFocusChanged(button4);
		TabControl.mViewSelected.setButtonFocusChanged(button5);
		TabControl.mViewSelected.setButtonFocusChanged(button6);
		TabControl.mViewSelected.setButtonFocusChanged(button7);
		TabControl.mViewSelected.setButtonFocusChanged(button8);
		TabControl.mViewSelected.setButtonFocusChanged(button9);
		TabControl.mViewSelected.setImageViewFocusChanged(button10);
		
		
		Intent intent=getIntent();
		mUid=intent.getStringExtra("uid");
		devid=intent.getIntExtra("devid", 0);
		if(devid==0){
			Log.e("leewoo", "deid error = 0");
			}

		Cursor cursor=TabControl.mSQLHelper.seleteBtnLearn(TabControl.writeDB,devid);
		Log.e("leewoo", "cur: "+cursor.getCount());
		if(cursor.getCount()>0){
			//已初始化			 
			Log.e("leewoo", "cur "+cursor.getString(2));			
				//学习
		//	Log.e("leewoo", "cur learn");					
			btnclr1=cursor.getString(1+2).equals("true")?true:false;
			btnclr2=cursor.getString(2+2).equals("true")?true:false;
			btnclr3=cursor.getString(3+2).equals("true")?true:false;
			btnclr4=cursor.getString(4+2).equals("true")?true:false;
			btnclr5=cursor.getString(5+2).equals("true")?true:false;
			btnclr6=cursor.getString(6+2).equals("true")?true:false;
			btnclr7=cursor.getString(7+2).equals("true")?true:false;
			btnclr8=cursor.getString(8+2).equals("true")?true:false;
			btnclr9=cursor.getString(9+2).equals("true")?true:false;	
			btnclr10=cursor.getString(10+2).equals("true")?true:false;	
		}else{
			Log.e("leewoo", "cur 初始化"+cursor.getCount());
			//未初始化
			TabControl.mSQLHelper.insertBtn(TabControl.writeDB,mUid,devid,"learn" ,learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse);
		}
		cursor.close();
		
		cursor=TabControl.mSQLHelper.seleteBtnName(TabControl.writeDB,devid);
		Log.e("leewoo", "cur: "+cursor.getCount());
		if(cursor.getCount()>0){
			//已初始化			 
			    Log.e("leewoo", "cur "+cursor.getString(2));		
		
				button1.setText(cursor.getString(1+2));
				button2.setText(cursor.getString(2+2));
				button3.setText(cursor.getString(3+2));
				button4.setText(cursor.getString(4+2));
				button5.setText(cursor.getString(5+2));
				button6.setText(cursor.getString(6+2));			
				button7.setText(cursor.getString(7+2));
				button8.setText(cursor.getString(8+2));
				button9.setText(cursor.getString(9+2));
				
				
			
		}else{
			Log.e("leewoo", "cur 初始化"+cursor.getCount());
			//未初始化
			TabControl.mSQLHelper.insertBtn(TabControl.writeDB,mUid,devid, "name" ,btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults+14);
		}		
		cursor.close();
		
		
		
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
	 protected void dialog(final int btnid) {
		 AlertDialog.Builder builder = new Builder(IR_AC.this);
		// final String mStr = null;
		 builder.setMessage("Please input name");
		 builder.setTitle("Button name");
		 final EditText et=new EditText(this);
		 builder.setView(et);
		 builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			
			switch (btnid) {
			case 1:
				button1.setText(et.getText().toString());
				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 1, et.getText().toString());
				break;
			case 2:
				button2.setText(et.getText().toString());
				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 2, et.getText().toString());
				break;
			case 3:
				button3.setText(et.getText().toString());
				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 3, et.getText().toString());
				break;
			case 4:
				button4.setText(et.getText().toString());
				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 4, et.getText().toString());
				break;
			case 5:
				button5.setText(et.getText().toString());
				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 5, et.getText().toString());
				break;
			case 6:
				button6.setText(et.getText().toString());
				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 6, et.getText().toString());
				break;
			case 7:
				button1.setText(et.getText().toString());
				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 7, et.getText().toString());

				break;				
			case 8:
				button8.setText(et.getText().toString());
				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 8, et.getText().toString());
				break;
			case 9:
				button9.setText(et.getText().toString());
				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 9, et.getText().toString());
				break;
			default:
				break;
			}
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
