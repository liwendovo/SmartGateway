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

import com.seuic.smartgateway.TabControl;
import com.seuic.smartgateway.SetupDev;
import com.seuic.smartgateway.R;

public class IR_Custom2 extends Activity implements android.view.View.OnClickListener,OnLongClickListener{
	int devid;
	String learnFalse="false";
	String learnTrue="true";
	String btnDefaults="自定义";
	String mUid;
	Button  backBtn,leanrnBtn;
	ImageView   devpic;
	ImageView  button1,button2,
			button3,button4,button5,
			button6,button7,button8,
			button9,button10;
	String btnName1,btnName2,
		   btnName3,btnName4,btnName5,
		   btnName6,btnName7,btnName8,
		   btnName9,btnName10;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ir_custom2);
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
		
		
		Intent intent=getIntent();
		mUid=intent.getStringExtra("uid");
		devid=intent.getIntExtra("devid", 0);
		if(devid==0){
			Log.e("leewoo", "deid error = 0");
			}
		
		Cursor cursor=TabControl.mSQLHelper.seleteBtn(TabControl.writeDB,devid);
		Log.e("leewoo", "cur: "+cursor.getCount());
		if(cursor.getCount()>0){
			//已初始化
			if(cursor.getCount()!=2){Log.e("leewoo", "cur不足2");}
			    
			    Log.e("leewoo", "cur "+cursor.getString(2));
				if(cursor.getString(2).equals("learn"))
				{
				//学习
				//	Log.e("leewoo", "cur learn");
					
					
					
					
					
					
					
					
				}
				cursor.moveToNext();
				Log.e("leewoo", "cur "+cursor.getString(2));
				if(cursor.getString(2).equals("name"))
				{
//				button1.setText(cursor.getString(1+2));
//				button2.setText(cursor.getString(2+2));
//				button3.setText(cursor.getString(3+2));
//				button4.setText(cursor.getString(4+2));
//				button5.setText(cursor.getString(5+2));
//				button6.setText(cursor.getString(6+2));			
//				button7.setText(cursor.getString(7+2));
//				button8.setText(cursor.getString(8+2));
//				button9.setText(cursor.getString(9+2));
//				button10.setText(cursor.getString(10+2));
				
				}
			
			
		}else{
			Log.e("leewoo", "cur 初始化"+cursor.getCount());
			//未初始化
			TabControl.mSQLHelper.insertBtn(TabControl.writeDB,mUid,devid,"learn" ,learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse);
			TabControl.mSQLHelper.insertBtn(TabControl.writeDB,mUid,devid, "name" ,btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults+14);
			//DevSetup.mSQLHelper.insertEtc(DevSetup.writeDB, mUid, 8, "name", "name", "name", "name");			
		}

		
		
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
		
		//按键名臣刷新 btnName
	
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

	
	 protected void dialog(final int btnid) {
		 AlertDialog.Builder builder = new Builder(IR_Custom2.this);
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
//			case 1:
//				button1.setText(et.getText().toString());
//				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 1, et.getText().toString());
//				break;
//			case 2:
//				button2.setText(et.getText().toString());
//				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 2, et.getText().toString());
//				break;
//			case 3:
//				button3.setText(et.getText().toString());
//				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 3, et.getText().toString());
//				break;
//			case 4:
//				button4.setText(et.getText().toString());
//				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 4, et.getText().toString());
//				break;
//			case 5:
//				button5.setText(et.getText().toString());
//				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 5, et.getText().toString());
//				break;
//			case 6:
//				button1.setText(et.getText().toString());
//				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 6, et.getText().toString());
//				break;
//			case 7:
//				button1.setText(et.getText().toString());
//				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 7, et.getText().toString());
//				break;				
//			case 8:
//				button8.setText(et.getText().toString());
//				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 8, et.getText().toString());
//				break;
//			case 9:
//				button9.setText(et.getText().toString());
//				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 9, et.getText().toString());
//				break;
//			case 10:
//				button10.setText(et.getText().toString());
//				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 10, et.getText().toString());
//				break;
			
		
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
