
package com.seuic.devetc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seuic.smartgateway.DevSetup;
import com.seuic.smartgateway.R;

public class IR_Selfdefine1 extends Activity implements android.view.View.OnClickListener,OnLongClickListener {
	Button  backBtn,leanrnBtn;
	Button  button1,button2,button3,
			button4,button5,button6,
			button7,button8,button9;
	int devid;
	String learnFalse="false";
	String learnTrue="true";
	String btnDefaults="自定义";
	String mUid;
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
	 Drawable drawable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ir_selfdefine1);
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
		
		Intent intent=getIntent();
		mUid=intent.getStringExtra("uid");
		devid=intent.getIntExtra("devid", 0);
		if(devid==0){
			Log.e("leewoo", "deid error = 0");
			}
		
		Cursor cursor=DevSetup.mSQLHelper.seleteBtn(DevSetup.writeDB,devid);
		Log.e("leewoo", "cur: "+cursor.getCount());
		if(cursor.getCount()>0){
			//已初始化
			if(cursor.getCount()!=2){Log.e("leewoo", "cur不足2");}
			    
			    Log.e("leewoo", "cur "+cursor.getString(2));
				if(cursor.getString(2).equals("learn"))
				{
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
				}
				cursor.moveToNext();
				Log.e("leewoo", "cur "+cursor.getString(2));
				if(cursor.getString(2).equals("name"))
				{
					button1.setText(cursor.getString(1+2));
					button2.setText(cursor.getString(2+2));
					button3.setText(cursor.getString(3+2));
					button4.setText(cursor.getString(4+2));
					button5.setText(cursor.getString(5+2));
					button6.setText(cursor.getString(6+2));			
					button7.setText(cursor.getString(7+2));
					button8.setText(cursor.getString(8+2));
					button9.setText(cursor.getString(9+2));
			
				}
			
			
		}else{
			Log.e("leewoo", "cur 初始化"+cursor.getCount());
			//未初始化
			DevSetup.mSQLHelper.insertBtn(DevSetup.writeDB,mUid,devid,"learn" ,learnFalse, "true", learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse);
			DevSetup.mSQLHelper.insertBtn(DevSetup.writeDB,mUid,devid, "name" ,btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults+14);
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
		
		
		drawable = leanrnBtn.getBackground();
	}
	
	@Override
	public void onClick(View v) {	

		// TODO Auto-generated method stub
//		Log.e("leewoo", "Button id = " + v.getId());  
		switch(v.getId())  
        {  
        case R.id.back: finish();break;
        case R.id.titleBtn:
        	lenclr=!lenclr;        	
        	Log.e("lenclr=",""+drawable);
        	if(lenclr==true){

            	Log.e("leewoo", "clr"+v.getId() ); 
            	leanrnBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_normal_disable));
            	
			        	if(btnclr1)	button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_selected));
			        	else button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_normal_disable));
			        	
			        	if(btnclr2)	button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_selected));
			        	else button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_normal_disable));
			        	
			        	if(btnclr3)	button3.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_selected));
			        	else button3.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_normal_disable));
			        	
			        	if(btnclr4)	button4.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_selected));
			        	else button4.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_normal_disable));
			        	
			        	if(btnclr5)	button5.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_selected));
			        	else button5.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_normal_disable));
			        	
			        	if(btnclr6)	button6.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_selected));
			        	else button6.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_normal_disable));
			        	
			        	if(btnclr7)	button7.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_selected));
			        	else button7.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_normal_disable));
			        	
			        	if(btnclr8)	button8.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_selected));
			        	else button8.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_normal_disable));
			        	
			        	if(btnclr9)	button9.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_selected));
			        	else button9.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_normal_disable));
			        	
        	}else{
        		leanrnBtn.setBackgroundDrawable(drawable);
        		button1.setBackgroundDrawable(drawable);
        		button2.setBackgroundDrawable(drawable);
        		button3.setBackgroundDrawable(drawable);
        		button4.setBackgroundDrawable(drawable);
        		button5.setBackgroundDrawable(drawable);
        		button6.setBackgroundDrawable(drawable);
        		button7.setBackgroundDrawable(drawable);
        		button8.setBackgroundDrawable(drawable);
        		button9.setBackgroundDrawable(drawable);      
        	}
        	break;
  	
        case R.id.button1:
        	if(lenclr==true){
	        	if(btnclr1==false)
	        	{  
        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
        		btnclr1=true;
        		button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_selected));
        		Log.e("btnclr1=",""+btnclr1);
	        	}
	        }
        	
        	break;
        case R.id.button2:
        	if(lenclr==true){
	        	if(btnclr2==false)
	        	{
        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
        		btnclr2=true;
        		button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_selected));
        		Log.e("btnclr2=",""+btnclr2);
	        	}
        	}
        	
        	break;
        case R.id.button3:
        	if(lenclr==true){
	        	if(btnclr3==false)
	        	{
        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
        		btnclr3=true;
        		button3.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_selected));
        		Log.e("btnclr3=",""+btnclr3);
        		}
        	}
        	
        	break;
        
        case R.id.button4:
        	if(lenclr==true){
	        	if(btnclr4==false)
	        	{	
	        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
	        		btnclr4=true;
	        		button4.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_selected));
	        		Log.e("btnclr4=",""+btnclr4);
	        	}
        	}
        	break;
        case R.id.button5:
        	if(lenclr==true){
	        	if(btnclr5==false)
	        	{   
	        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
	        		btnclr5=true;
	        		button5.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_selected));
	        		Log.e("btnclr5=",""+btnclr5);
	        	}
        	}
        	break;
        case R.id.button6:
        	if(lenclr==true){
	        	if(btnclr6==false)
	        	{ 
	        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
	        		btnclr6=true;
	        		button6.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_selected));
	        		Log.e("btnclr6=",""+btnclr6);
	        	}
        	}
        	break;
        case R.id.button7:
        	if(lenclr==true){
	        	if(btnclr7==false)
	        	{   
	        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
	        		btnclr7=true;
	        		button7.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_selected));
	        		Log.e("btnclr7=",""+btnclr7);
	        	}
        	}
        	break;
        case R.id.button8:
        	if(lenclr==true){
	        	if(btnclr8==false)
	        	{  
	        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
	        		btnclr8=true;
	        		button8.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_selected));
	        		Log.e("btnclr8=",""+btnclr8);
	        	}
        	}
        	break;
        case R.id.button9:
        	if(lenclr==true){
	        	if(btnclr9==false)
	        	{  
	        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
	        		btnclr9=true;
	        		button9.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_selected));
	        		Log.e("btnclr9=",""+btnclr9);
	        	}
        	}
        	break;       
        default:  
        	Log.e("leewoo", "Button id =default " ); 
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

	 protected void dialog(final int btnid) {
		 AlertDialog.Builder builder = new Builder(IR_Selfdefine1.this);
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
				DevSetup.mSQLHelper.updateBtnName(DevSetup.writeDB, devid, 1, et.getText().toString());
				break;
			case 2:
				button2.setText(et.getText().toString());
				DevSetup.mSQLHelper.updateBtnName(DevSetup.writeDB, devid, 2, et.getText().toString());
				break;
			case 3:
				button3.setText(et.getText().toString());
				DevSetup.mSQLHelper.updateBtnName(DevSetup.writeDB, devid, 3, et.getText().toString());
				break;
			case 4:
				button4.setText(et.getText().toString());
				DevSetup.mSQLHelper.updateBtnName(DevSetup.writeDB, devid, 4, et.getText().toString());
				break;
			case 5:
				button5.setText(et.getText().toString());
				DevSetup.mSQLHelper.updateBtnName(DevSetup.writeDB, devid, 5, et.getText().toString());
				break;
			case 6:
				button6.setText(et.getText().toString());
				DevSetup.mSQLHelper.updateBtnName(DevSetup.writeDB, devid, 6, et.getText().toString());
				break;
			case 7:
				button7.setText(et.getText().toString());
				DevSetup.mSQLHelper.updateBtnName(DevSetup.writeDB, devid, 7, et.getText().toString());
				break;				
			case 8:
				button8.setText(et.getText().toString());
				DevSetup.mSQLHelper.updateBtnName(DevSetup.writeDB, devid, 8, et.getText().toString());
				break;
			case 9:
				button9.setText(et.getText().toString());
				DevSetup.mSQLHelper.updateBtnName(DevSetup.writeDB, devid, 9, et.getText().toString());
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

