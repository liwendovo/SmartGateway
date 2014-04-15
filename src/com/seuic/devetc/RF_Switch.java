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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.seuic.adapter.CustomToast;
import com.seuic.net.TUTKClient;
import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class RF_Switch extends Activity implements android.view.View.OnClickListener{
	Button  backBtn,leanrnBtn;
	LinearLayout back_ll,titleBtn_ll;
	ImageView   devpic;
	TextView textOn1,textOff1,textOn2,textOff2,textOn3,textOff3;
	Calendar calendar=Calendar.getInstance(TimeZone.getDefault());
	final int buttonMaxNum=2;
	ImageView button[]=new ImageView[buttonMaxNum];
	boolean btnLearn[]=new boolean[buttonMaxNum];
	int curButton=-1;
	byte ioCtrlBuf[]=new byte[TUTKClient.MAX_SIZE_IOCTRL_BUF]; 
	private ProgressDialog progressDialog;  
	int devid;
	String mUid;	
	Boolean lenclr=false;
	Cursor learnCursor;
	Cursor timerCursor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rf_switch);
        backBtn=(Button)findViewById(R.id.back);
		leanrnBtn=(Button)findViewById(R.id.titleBtn);
		back_ll=(LinearLayout)findViewById(R.id.back_ll);
		titleBtn_ll=(LinearLayout)findViewById(R.id.titleBtn_ll);
		button[0]=(ImageView)findViewById(R.id.button1);
		button[1]=(ImageView)findViewById(R.id.button2);
		textOn1=(TextView)findViewById(R.id.textOn1);
		textOff1=(TextView)findViewById(R.id.textOff1);
		textOn2=(TextView)findViewById(R.id.textOn2);
		textOff2=(TextView)findViewById(R.id.textOff2);
		textOn3=(TextView)findViewById(R.id.textOn3);
		textOff3=(TextView)findViewById(R.id.textOff3);
		textOn1.setOnClickListener(this); 
		textOff1.setOnClickListener(this);
		textOn2.setOnClickListener(this); 
		textOff2.setOnClickListener(this);
		textOn3.setOnClickListener(this); 
		textOff3.setOnClickListener(this);
	
		backBtn.setOnClickListener(this); 
		leanrnBtn.setOnClickListener(this); 
		back_ll.setOnClickListener(this); 
		titleBtn_ll.setOnClickListener(this);
		button[0].setOnClickListener(this);  
		button[1].setOnClickListener(this); 
		
		TabControl.mViewSelected.setButtonClickChanged(backBtn);
		TabControl.mViewSelected.setButtonClickChanged(leanrnBtn);
		TabControl.mViewSelected.setImageViewClickChanged(button[0]);
		TabControl.mViewSelected.setImageViewClickChanged(button[1]);		
		devpic=(ImageView)findViewById(R.id.pic);
		devpic.setImageDrawable(getResources().getDrawable(R.drawable.rf_switch));
		
		
		
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
		
		
		timerCursor=TabControl.mSQLHelper.seleteTimer(TabControl.writeDB,devid);
		Log.e("leewoo", "cur: "+timerCursor.getCount());
		if(timerCursor.getCount()>0){				 
			
//			WhTimerOn = timerCursor.getString(2);
//			WhTimerOff = timerCursor.getString(3);
////			timerCursor.getInt(uidOn)  getColumnIndex("WhUidOn");
//			uidOn = timerCursor.getInt(timerCursor.getColumnIndex("WhUidOn"));
//			uidOff = timerCursor.getInt(timerCursor.getColumnIndex("WhUidOff"));
////			uidOff = timerCursor.getColumnIndex("WhUidOff");
//			Log.e("IR_WH","timerCursor uidOn="+uidOn);
//			Log.e("IR_WH","timerCursor uidOff="+uidOff);
////			byte[] data = null;
//			byte[] data = new byte[2] ;
//			char[] prefix = null;
//			prefix = new char[2];
//			WhTimerOn.getChars(0, 2, prefix, 0);
////			WhTimerOn.getChars(3, 4, prefix, 0);
//			String tmp = new String(prefix);
//			data[0] = (byte)Integer.parseInt(tmp);
//			onHour = data[0];
////			offHour = 
//			textOn.setText(WhTimerOn);
//			textOff.setText(WhTimerOff);
			
		}else{
			Log.e("IR_WH", "timerCursor 初始化"+timerCursor.getCount());
			//未初始化
			TabControl.mSQLHelper.insertTimer(TabControl.writeDB,mUid,devid);
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
             case R.id.textOn1:
            	 timePicker(v);
              	break;
              case R.id.textOff1:
            	  timePicker(v);
              	break;
              case R.id.textOn2:
             	 timePicker(v);
               	break;
               case R.id.textOff2:
             	  timePicker(v);
               	break;
               case R.id.textOn3:
              	 timePicker(v);
                	break;
                case R.id.textOff3:
              	  timePicker(v);
                	break;
                           
	        default:  
	        	Log.e("leewoo", "Button id =default " ); 
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
			        	 
			        	  Log.e("ir_wh", "go to updateDisplay   hourOfDay="+hourOfDay+"minute="+minute);
			        	  sb = new StringBuilder().append(format(hourOfDay)).append(":")
			        				
 		                         .append(format(minute));
			        	  ((TextView) v).setText(sb);
			        	  if(v == textOn){
				        	  onHour = (byte)hourOfDay;
				        	  onMinute = (byte)minute;
				        	  WhTimerOn = sb.toString( );
				        	  Log.e("IR_WH","WhTimerOn:"+WhTimerOn);
			        	  }else if(v == textOff){
			        		  offHour = (byte)hourOfDay;
				        	  offMinute = (byte)minute;
				        	  WhTimerOff = sb.toString( );
				        	  Log.e("IR_WH","WhTimerOff:"+WhTimerOff);
			        	  }
			        	  TabControl.mSQLHelper.updateWh(TabControl.writeDB, devid,WhTimerOn,WhTimerOff);
			        	  button5.setChecked(false);
			        	  button5.setBackgroundResource(R.drawable.rf_switch_blue);
			        	 
		             }         

		       },mHour,mMinute,true).show();
		
		 TUTKClient.timerdel(uidOn);
		 TUTKClient.timerdel(uidOff);
	}
		 private void showProgressDialog(int num){ 
			 TabControl.mViewSelected.imageviewClickLearnDefault(button[num-1]);
			 progressDialog = new ProgressDialog(RF_Switch.this);
			 progressDialog.setMessage(getResources().getString(R.string.studying));
			 progressDialog.setCancelable(false);
			 progressDialog.setButton("Cancel", new DialogInterface.OnClickListener() {
	             public void onClick(DialogInterface dialog, int i)
	             {
	            	 Log.e("RF_Power","start cancle button");
	            	 new Thread(){        
	    			     @Override  
	    			     public void run() {  
	    			     TUTKClient.cancellearn(false);
	    			     }}.start(); 
	    			     Log.e("RF_Power","cancle button success");
	    			     dialog.cancel();
	             }
	         });
			 progressDialog.show();
//		 progressDialog = ProgressDialog.show(RF_Power.this,"", getResources().getString(R.string.studying), true, false); 
		 new Thread(){        
		     @Override  
		     public void run() {  
		    	 Message learnMsg=new Message();
		    	 Log.e("RF_Power","start to learn");
		    	 if(TUTKClient.learn(2,ioCtrlBuf))
		    	 {
		    		 learnMsg.what=0;
		    	 }else{
		    		 learnMsg.what=1;	
		    	 }
		    	 Log.e("RF_Power","return learn result");
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
//			 progressDialog = ProgressDialog.show(RF_Power.this, "sending...", "Please wait...", true, false); 
			 new Thread(){        
			     @Override  
			     public void run() {  
//			    	 Message learnMsg=new Message();
			    	 TUTKClient.send(learnCursor.getBlob(btnid+2),false);
//			    	 if(TUTKClient.send(learnCursor.getBlob(btnid+2),false))
//			    	 {
//			    		 learnMsg.what=0;
//			    	 }else{
//			    		 learnMsg.what=1;	
//			    	 }	 
//			    	 sendHandler.sendMessage(learnMsg);  
			     }}.start();      
			 }
		private Handler sendHandler = new Handler(){ 
	        @Override  
	        public void handleMessage(Message msg) {  
	        	if(0==msg.what){
	        		CustomToast.showToast(getApplicationContext(), "send success", Toast.LENGTH_SHORT); 		        
	        	}else{
	        		CustomToast.showToast(getApplicationContext(), "send failed", Toast.LENGTH_SHORT);	
	        	}
	            progressDialog.dismiss(); 
	        }}; 
		private void setbuttonstate()
		{
			for(int i=0;i<buttonMaxNum;i++){
				if(btnLearn[i])	TabControl.mViewSelected.imageviewClickRecover(button[i]);
		    	else TabControl.mViewSelected.imageviewClickGreyChanged(button[i]);
			}
		}
   
}
