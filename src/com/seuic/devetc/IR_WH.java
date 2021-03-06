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
import android.widget.ToggleButton;

import com.seuic.function.CustomToast;
import com.seuic.net.TUTKClient;
import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class IR_WH extends Activity implements android.view.View.OnClickListener{
	int devid;
	int uidOn;
	int uidOff;
	final int buttonMaxNum=4;
	final int allButtonNum=5;
	ImageView button[]=new ImageView[buttonMaxNum];
	boolean btnLearn[]=new boolean[allButtonNum];
	int curButton=-1;
	byte ioCtrlBuf[]=new byte[TUTKClient.MAX_SIZE_IOCTRL_BUF]; 
	private ProgressDialog progressDialog;  
	private int mHour;
	private int mMinute;
	byte onHour;
	byte onMinute;
	byte offHour;
	byte offMinute;
	StringBuilder sb;
	String WhTimerOn;
	String WhTimerOff;
	Calendar calendar=Calendar.getInstance(TimeZone.getDefault());
	String mUid;	
	Boolean lenclr=false;
	Cursor learnCursor;
	Cursor timerCursor;
	Button  backBtn,leanrnBtn;
	LinearLayout back_ll,titleBtn_ll;
	ImageView   devpic;
	ToggleButton button5;
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
		button5=(ToggleButton)findViewById(R.id.button5);
		
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
		button5.setOnClickListener(this);
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
			btnLearn[buttonMaxNum]=learnCursor.getInt(buttonMaxNum+3) == 1?true:false;
		}else{
			Log.e("leewoo", "cur learn 初始化"+learnCursor.getCount());
			//未初始化
			TabControl.mSQLHelper.insertBtnLearn(TabControl.writeDB,mUid,devid);
		}		
		
		Log.e("IR_WH","start insert Timer Table");
//		TabControl.mSQLHelper.insertTimer(TabControl.writeDB,mUid,devid);
//		Log.e("IR_WH","finish insert Timer Table");
		timerCursor=TabControl.mSQLHelper.seleteTimer(TabControl.writeDB,devid);
		Log.e("leewoo", "cur: "+timerCursor.getCount());
		if(timerCursor.getCount()>0){				 
			
			WhTimerOn = timerCursor.getString(2);
			WhTimerOff = timerCursor.getString(3);
			uidOn = timerCursor.getInt(timerCursor.getColumnIndex("WhUidOn"));
			uidOff = timerCursor.getInt(timerCursor.getColumnIndex("WhUidOff"));
			Log.e("IR_WH","timerCursor uidOn="+uidOn);
			Log.e("IR_WH","timerCursor uidOff="+uidOff);
			
			
			onHour = getwhtime(WhTimerOn,0);
			onMinute = getwhtime(WhTimerOn,3);
			offHour = getwhtime(WhTimerOff,0);
			offMinute = getwhtime(WhTimerOff,3);
		    Log.e("IR_WH","timerCursor onHour="+onHour+"  onMinute="+onMinute+"  offHour="+offHour+"  offMinute="+offMinute);
			
			textOn.setText(WhTimerOn);
			textOff.setText(WhTimerOff);
			
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
            	 curButton=5;
            	 if(learnCursor.getBlob(5)==null&&learnCursor.getBlob(6)==null)
            	 {
            		 CustomToast.showToast(getApplicationContext(),"please learn first", Toast.LENGTH_SHORT);	
            		 break;
            	 }
            	 Log.e("IR_WH","button5.isChecked()="+button5.isChecked());
            	 if (button5.isChecked()) {
            		 button5.setBackgroundResource(R.drawable.rf_switch_yellow);
            		 uidOn=(int)(Math.random()*100000);
                	 uidOff=(int)(Math.random()*100000);
                	 Log.e("IR_WH","random uidOn="+uidOn);
         			 Log.e("IR_WH","random uidOff="+uidOff);
                	 TabControl.mSQLHelper.updateBtn(TabControl.writeDB, devid, curButton,1);
                	 TabControl.mSQLHelper.updateWhUid(TabControl.writeDB, devid,uidOn,uidOff);
         			 whtimeradd(uidOff,uidOn,(short)0x7f,onHour,onMinute,offHour,offMinute,learnCursor.getBlob(5),learnCursor.getBlob(6),true);
     			 } else {
     				button5.setBackgroundResource(R.drawable.rf_switch_blue);
     				TabControl.mSQLHelper.updateBtn(TabControl.writeDB, devid, curButton,0);
     				whtimerdel(uidOn,uidOff);
     			 }
            	
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
			        	  TabControl.mSQLHelper.updateBtn(TabControl.writeDB, devid, 5,0);
			        	  button5.setChecked(false);
			        	  button5.setBackgroundResource(R.drawable.rf_switch_blue);
			        	 
		             }         

		       },mHour,mMinute,true).show();
		
		 
		 whtimerdel(uidOn,uidOff);
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
	        
	        
//	        whtimeradd(uidOn,(short)0x7f,onHour,onMinute,learnCursor.getBlob(5),true);
//			 whtimeradd(uidOff,(short)0x7f,offHour,offMinute,learnCursor.getBlob(6),true);
	        private void whtimeradd(final int uidon,final int uidoff,final short week, final byte onHour, final byte onMinute,final byte offHour, final byte offMinute,final byte[] data1,final byte[] data2, final boolean irflag){  
				 new Thread(){        
				     @Override  
				     public void run() {  
				    	 TUTKClient.timeradd(uidon, week, onHour, onMinute, data1, irflag);
				    	 TUTKClient.timeradd(uidoff, week, offHour, offMinute, data2, irflag);
				     }}.start();      
				 }    
		    
			        
	        private void whtimerdel(final int uidon, final int uidoff){  
				 new Thread(){        
				     @Override  
				     public void run() {  
				    	 if(uidon != -1)
				    	 {
				    		 TUTKClient.timerdel(uidon); 
				    		 Log.e("RF_Switch","delete tomeron");
				    	 }
				    	 
				    	 if(uidoff != -1)
				    	 {
				    		 TUTKClient.timerdel(uidoff); 
				    		 Log.e("RF_Switch","delete tomeroff");
				    	 }

				     }}.start();      
				 }
	        
	        
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
			if(btnLearn[allButtonNum-1])	
			{    button5.setChecked(true);
			     button5.setBackgroundResource(R.drawable.rf_switch_yellow);
   			}
		}
	 
	 private byte getwhtime(String str,int i)
	 {
		    char[] prefix = new char[2];
			str.getChars(i, i+2, prefix, 0);
			String tmp= new String(prefix);
			byte time = (byte)Integer.parseInt(tmp);
			return time;
	 }

}
