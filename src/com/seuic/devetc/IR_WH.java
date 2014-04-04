package com.seuic.devetc;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.seuic.adapter.CustomToast;
import com.seuic.net.TUTKClient;
import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class IR_WH extends Activity implements android.view.View.OnClickListener{
	int devid;
	final int buttonMaxNum=4;
	ImageView button[]=new ImageView[buttonMaxNum];
	boolean btnLearn[]=new boolean[buttonMaxNum];
	int curButton=-1;
	byte ioCtrlBuf[]=new byte[TUTKClient.MAX_SIZE_IOCTRL_BUF]; 
	private ProgressDialog progressDialog;  
	private int mHour;
	private int mMinute;
	int onHour;
	int onMinute;
	int offHour;
	int offMinute;
	Calendar calendar=Calendar.getInstance(TimeZone.getDefault());
	String mUid;	
	Boolean lenclr=false;
	Cursor learnCursor;
	Button  backBtn,leanrnBtn;
	LinearLayout back_ll,titleBtn_ll;
	ImageView   devpic;
	ImageView  button1,button2,
			button3,button4,button5;
	TextView textOn,textOff;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ir_wh);
		Log.e("leewoo", "in IR_WH ");
		
		backBtn=(Button)findViewById(R.id.back);
		leanrnBtn=(Button)findViewById(R.id.titleBtn);
		button[0]=(ImageView)findViewById(R.id.button1);
		button[1]=(ImageView)findViewById(R.id.button2);
		button[2]=(ImageView)findViewById(R.id.button3);		
		button[3]=(ImageView)findViewById(R.id.button4);		
		button5=(ImageView)findViewById(R.id.button5);
		
		back_ll=(LinearLayout)findViewById(R.id.back_ll);
		titleBtn_ll=(LinearLayout)findViewById(R.id.titleBtn_ll);
		textOn=(TextView)findViewById(R.id.textOn);
		textOff=(TextView)findViewById(R.id.textOff);
		textOn.setOnClickListener(this); 
		textOff.setOnClickListener(this); 
		backBtn.setOnClickListener(this); 
		leanrnBtn.setOnClickListener(this); 
		back_ll.setOnClickListener(this); 
		titleBtn_ll.setOnClickListener(this);
		for(int i=0;i< buttonMaxNum;i++){
			button[i].setOnClickListener(this);  
			TabControl.mViewSelected.setImageViewClickChanged(button[i]);
		}
		TabControl.mViewSelected.buttonClickRecover(leanrnBtn);
		
		
		devpic=(ImageView)findViewById(R.id.pic);
		devpic.setImageDrawable(getResources().getDrawable(R.drawable.ir_wh));
		
		
		Intent intent=getIntent();
		mUid=intent.getStringExtra("uid");
		devid=intent.getIntExtra("devid", 0);
		if(devid==0){
			Log.e("leewoo", "deid error = 0");
			}
		
		learnCursor=TabControl.mSQLHelper.seleteBtnLearn(TabControl.writeDB,devid);
		Log.e("leewoo", "cur: "+learnCursor.getCount());
		if(learnCursor.getCount()>0){				 
			//学习	
			for(int i=0;i<buttonMaxNum;i++){
				btnLearn[i]=learnCursor.getBlob(i+3)!=null?true:false;
			}
		}else{
			Log.e("leewoo", "cur learn 初始化"+learnCursor.getCount());
			//未初始化
			TabControl.mSQLHelper.insertBtnLearn(TabControl.writeDB,mUid,devid);
		}		
		setbuttonstate();
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
    		switch(v.getId())  
            {  
            case R.id.back_ll:
            case R.id.back:
            	finish();
            	break;
            	
            case R.id.titleBtn_ll:
            case R.id.titleBtn:
            	lenclr=!lenclr;  
	        	if(lenclr==true){	
	            	Log.e("leewoo", "clr"+v.getId() ); 
	            	TabControl.mViewSelected.buttonClickLearn(leanrnBtn);	 
	            	for(int i=0;i< buttonMaxNum;i++){
	        			TabControl.mViewSelected.imageviewClickLearnDefault(button[i]);
	        		}
	        	}else{
	        		TabControl.mViewSelected.buttonClickRecover(leanrnBtn);
	        		setbuttonstate();    
	        	}
       		break;
            case R.id.button1:             	
             	if(lenclr==true){
             		curButton=1;
             		showProgressDialog(curButton);
     	        }else{//发送
     	        	send(1);
             	}
             	break;
             case R.id.button2:             	
             	if(lenclr==true){
             		curButton=2;
             		showProgressDialog(curButton);
             	}else{
     	        	send(2);
             	}             	
             	break;
             case R.id.button3:
             	if(lenclr==true){  
             		curButton=3;
             		showProgressDialog(curButton);
             	}else{
     	        	send(3);
             	}             	
             	break;             
             case R.id.button4:
             	if(lenclr==true){
         			curButton=4;
         			showProgressDialog(curButton);
             	}else{
     	        	send(4);
             	} 
             	break;
             case R.id.button5:
            	 TUTKClient.timeradd(devid,0,onHour,onMinute,learnCursor.getBlob(5),true);
            	 TUTKClient.timeradd(devid,0,offHour,offMinute,learnCursor.getBlob(6),true);
//            	 timeradd(int uid,int bit_week, int hour, int min,byte[] data, boolean irflag);
             	break;
             case R.id.textOn:
            	 timePicker(v);
              	break;
              case R.id.textOff:
            	  timePicker(v);
              	break;
                    
            default:  
                break; 
            }		
	}

	private void timePicker(final View v){
		
		Log.e("ir_wh", "go to timePicker");
		calendar.setTimeInMillis(System.currentTimeMillis());
		mHour=calendar.get(Calendar.HOUR_OF_DAY);  
		mMinute=calendar.get(Calendar.MINUTE);  
		new TimePickerDialog(this,

		        new TimePickerDialog.OnTimeSetListener()

		        {   

			          public void onTimeSet(TimePicker view,int hourOfDay,int minute)
			          {
	//		        	  updateDisplay();
			            /* 这里放更新时间的方法 */
			        	  calendar.setTimeInMillis(System.currentTimeMillis());  
			        	  calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);   
			        	  calendar.set(Calendar.MINUTE, minute);        
			        	  calendar.set(Calendar.SECOND, 0);        
			        	  calendar.set(Calendar.MILLISECOND, 0);   
			        	  if(v == textOn){
				        	  onHour = hourOfDay;
				        	  onMinute = minute;
			        	  }else if(v == textOff){
			        		  offHour = hourOfDay;
				        	  offMinute = minute;
			        	  }
			        	  Log.e("ir_wh", "go to updateDisplay   hourOfDay="+hourOfDay+"minute="+minute);
			        	  ((TextView) v).setText(
			        		      new StringBuilder().append(format(hourOfDay)).append(":")
	
			        		                         .append(format(minute))
			              );
		             }         

		       },mHour,mMinute,true).show();
	}
	

	  /*日期时间显示两位数的方法*/

	  private String format(int x)

	  {

	    String s=""+x;

	    if(s.length()==1) s="0"+s;

	    return s;

	  }
	  
	  
	 private void showProgressDialog(int num){  
		 TabControl.mViewSelected.imageviewClickLearnDefault(button[num-1]);
		 progressDialog = new ProgressDialog(IR_WH.this);
		 progressDialog.setMessage(getResources().getString(R.string.studying));
		 progressDialog.setCancelable(false);
		 progressDialog.setButton("Cancel", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int i)
             {
            	 new Thread(){        
    			     @Override  
    			     public void run() {  
    			     TUTKClient.cancellearn(true);
    			     }}.start(); 
    			     dialog.cancel();
             }
         });
		 progressDialog.show();

//		 progressDialog = ProgressDialog.show(IR_TV.this,"", getResources().getString(R.string.studying), true, false); 
	
	 new Thread(){        
	     @Override  
	     public void run() {  
	    	 Message learnMsg=new Message();
		    	 if(TUTKClient.learn(0,ioCtrlBuf))
		    	 {
		    		 learnMsg.what=0;
		    	 }else{
		    		 learnMsg.what=1;	
		    	 }	
		    	 Log.e("IR_Custom1", "learnMsg.what"+learnMsg.what);
	    	 learnHandler.sendMessage(learnMsg);  
	     }}.start();      
	 }
	 private Handler learnHandler = new Handler(){ 
	        @Override  
	        public void handleMessage(Message msg) {  
	        	if(0==msg.what){
	        		CustomToast.showToast(getApplicationContext(),  getResources().getString(R.string.studysuccessful), Toast.LENGTH_SHORT);  
		        	TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, curButton,ioCtrlBuf);
		        	btnLearn[curButton-1]=true;	 
		        	TabControl.mViewSelected.imageviewClickRecover(button[curButton-1]);
		        	curButton=-1;
		        	//更新learnCursor
		        	learnCursor.close();
		        	learnCursor=TabControl.mSQLHelper.seleteBtnLearn(TabControl.writeDB,devid);	 
	        	}else{
	        		CustomToast.showToast(getApplicationContext(),  getResources().getString(R.string.studyfailed), Toast.LENGTH_SHORT);	
	        	}
	            progressDialog.dismiss(); 
	        }}; 
	 private void send(final int btnid){  
//		 progressDialog = ProgressDialog.show(IR_TV.this, "", "", true, false); 
		 new Thread(){        
		     @Override  
		     public void run() {  
//		    	 Message learnMsg=new Message();
		    	 TUTKClient.send(learnCursor.getBlob(btnid+2),true);
//		    	 if(TUTKClient.send(learnCursor.getBlob(btnid+2),true))
//		    	 {
//		    		 learnMsg.what=0;
//		    	 }else{
//		    		 learnMsg.what=1;	
//		    	 }	 
//		    	 sendHandler.sendMessage(learnMsg);  
		     }}.start();      
		 }
	 
	 private void setbuttonstate()
		{
			for(int i=0;i<buttonMaxNum;i++){
				if(btnLearn[i])	TabControl.mViewSelected.imageviewClickRecover(button[i]);
		    	else TabControl.mViewSelected.imageviewClickGreyChanged(button[i]);
			}
		}
	 

}
