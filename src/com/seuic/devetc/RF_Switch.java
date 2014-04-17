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
import android.widget.ToggleButton;

import com.seuic.adapter.CustomToast;
import com.seuic.net.TUTKClient;
import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class RF_Switch extends Activity implements android.view.View.OnClickListener{
	Button  backBtn,leanrnBtn;
	LinearLayout back_ll,titleBtn_ll;
	ImageView   devpic;
	TextView textOn1,textOff1,textOn2,textOff2,textOn3,textOff3;
	ToggleButton togBtn1,togBtn2,togBtn3,togBtn4;
	Calendar calendar=Calendar.getInstance(TimeZone.getDefault());
	final int buttonMaxNum=9;
	final int buttonNum=2;
	ImageView button[]=new ImageView[buttonMaxNum];
	boolean btnLearn[]=new boolean[buttonMaxNum];
	int curButton=-1;
	byte ioCtrlBuf[]=new byte[TUTKClient.MAX_SIZE_IOCTRL_BUF]; 
	private ProgressDialog progressDialog;  
	private int mHour;
	private int mMinute;
	byte hour;
	byte min;
	byte onHour1, onMinute1, offHour1, offMinute1, onHour2,onMinute2,offHour2,offMinute2,onHour3,onMinute3,offHour3,offMinute3;
	int devid;
	int uidOn1,uidOff1,uidOn2,uidOff2,uidOn3,uidOff3;
	String mUid;
	String str;
	String WhTimerOn1, WhTimerOff1,WhTimerOn2,WhTimerOff2,WhTimerOn3,WhTimerOff3;
	StringBuilder sb;
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
		button[2]=(ImageView)findViewById(R.id.button3);		
		button[3]=(ImageView)findViewById(R.id.button4);		
		button[4]=(ImageView)findViewById(R.id.button5);
		button[5]=(ImageView)findViewById(R.id.button6);		
		button[6]=(ImageView)findViewById(R.id.button7);		
		button[7]=(ImageView)findViewById(R.id.button8);
		button[8]=(ImageView)findViewById(R.id.button9);
		togBtn1=(ToggleButton)findViewById(R.id.togBtn1);
		togBtn2=(ToggleButton)findViewById(R.id.togBtn2);
		togBtn3=(ToggleButton)findViewById(R.id.togBtn3);
		togBtn4=(ToggleButton)findViewById(R.id.togBtn4);
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
		togBtn1.setOnClickListener(this); 
		togBtn2.setOnClickListener(this);
		togBtn3.setOnClickListener(this); 
		togBtn4.setOnClickListener(this);
		backBtn.setOnClickListener(this); 
		leanrnBtn.setOnClickListener(this); 
		back_ll.setOnClickListener(this); 
		titleBtn_ll.setOnClickListener(this);
		
		for(int i=0; i<buttonMaxNum; i++)
		{
			button[i].setOnClickListener(this);  
			TabControl.mViewSelected.setImageViewClickChanged(button[i]);
		}
		
		
		TabControl.mViewSelected.setButtonClickChanged(backBtn);
		TabControl.mViewSelected.setButtonClickChanged(leanrnBtn);	
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
			for(int i=0;i<buttonNum;i++){
				btnLearn[i]=learnCursor.getBlob(i+3)!=null?true:false;
			}
			for(int i=buttonNum;i<buttonMaxNum;i++){
				btnLearn[i]=learnCursor.getInt(i+3) == 1?true:false;
			}
		}else{
			Log.e("leewoo", "cur learn 初始化"+learnCursor.getCount());
			//未初始化
			TabControl.mSQLHelper.insertBtnLearn(TabControl.writeDB,mUid,devid);
		}		
		
		TabControl.mSQLHelper.insertTimer(TabControl.writeDB,mUid,devid);
		timerCursor=TabControl.mSQLHelper.seleteTimer(TabControl.writeDB,devid);
		Log.e("leewoo", "cur: "+timerCursor.getCount());
		if(timerCursor.getCount()>0){				 
			
			WhTimerOn1 = timerCursor.getString(timerCursor.getColumnIndex("switchtimeron1"));
			WhTimerOff1 = timerCursor.getString(timerCursor.getColumnIndex("switchtimeroff1"));
			WhTimerOn2 = timerCursor.getString(timerCursor.getColumnIndex("switchtimeron2"));
			WhTimerOff2 = timerCursor.getString(timerCursor.getColumnIndex("switchtimeroff2"));
			WhTimerOn3 = timerCursor.getString(timerCursor.getColumnIndex("switchtimeron3"));
			WhTimerOff3 = timerCursor.getString(timerCursor.getColumnIndex("switchtimeroff3"));
			
			uidOn1 = timerCursor.getInt(timerCursor.getColumnIndex("SwitchUidOn1"));
			uidOff1 = timerCursor.getInt(timerCursor.getColumnIndex("SwitchUidOff1"));
			uidOn2 = timerCursor.getInt(timerCursor.getColumnIndex("SwitchUidOn2"));
			uidOff2 = timerCursor.getInt(timerCursor.getColumnIndex("SwitchUidOff2"));
			uidOn3 = timerCursor.getInt(timerCursor.getColumnIndex("SwitchUidOn3"));
			uidOff3 = timerCursor.getInt(timerCursor.getColumnIndex("SwitchUidOff3"));
//			uidOff = timerCursor.getColumnIndex("WhUidOff");
			Log.e("RF_Switch","timerCursor uidOn1="+uidOn1);
			Log.e("RF_Switch","timerCursor uidOff1="+uidOff1);
			Log.e("RF_Switch","timerCursor uidOn2="+uidOn2);
			Log.e("RF_Switch","timerCursor uidOff2="+uidOff2);
			Log.e("RF_Switch","timerCursor uidOn3="+uidOn3);
			Log.e("RF_Switch","timerCursor uidOff3="+uidOff3);
////			timerCursor.getInt(uidOn)  getColumnIndex("WhUidOn");
//			uidOn = timerCursor.getInt(timerCursor.getColumnIndex("WhUidOn"));
//			uidOff = timerCursor.getInt(timerCursor.getColumnIndex("WhUidOff"));
////			uidOff = timerCursor.getColumnIndex("WhUidOff");
//			Log.e("RF_Switch","timerCursor uidOn="+uidOn);
//			Log.e("RF_Switch","timerCursor uidOff="+uidOff);
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
			textOn1.setText(WhTimerOn1);
			textOff1.setText(WhTimerOff1);
			textOn2.setText(WhTimerOn2);
			textOff2.setText(WhTimerOff2);
			textOn3.setText(WhTimerOn3);
			textOff3.setText(WhTimerOff3);
			
		}else{
			Log.e("RF_Switch", "timerCursor 初始化"+timerCursor.getCount());
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
	            	for(int i=0;i< buttonNum;i++){
	            		TabControl.mViewSelected.imageviewClickLearnDefault(button[i]);
	        		}
	        	}else{
	        		TabControl.mViewSelected.buttonClickRecover(leanrnBtn);
	        		for(int i=0;i<buttonNum;i++){
	    				if(btnLearn[i])	TabControl.mViewSelected.imageviewClickRecover(button[i]);
	    		    	else TabControl.mViewSelected.imageviewClickGreyChanged(button[i]);
	    			}    
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
            	 curButton = 3;
            	 weekbuttonstate(curButton);
            	 
//		        	btnLearn[curButton-1]=true;	  
//		        	curButton=-1;
              	break;
             case R.id.button4:
            	 curButton = 4;
            	 weekbuttonstate(curButton);
              	break;
             case R.id.button5:
            	 curButton = 5;
            	 weekbuttonstate(curButton);
              	break;
             case R.id.button6:
            	 curButton = 6;
            	 weekbuttonstate(curButton);
            	break;
             case R.id.button7:
            	 curButton = 7;
            	 weekbuttonstate(curButton);
              	break;
             case R.id.button8:
            	 curButton = 8;
            	 weekbuttonstate(curButton);
              	break;
             case R.id.button9:
            	 curButton = 9;
            	 weekbuttonstate(curButton);
              	break;
             case R.id.togBtn1:
            	 if(learnCursor.getBlob(3)==null&&learnCursor.getBlob(4)==null)
            	 {
            		 CustomToast.showToast(getApplicationContext(),"please learn first", Toast.LENGTH_SHORT);	
            		 break;
            	 }
            	 Log.e("RF_Switch","togBtn1.isChecked()="+togBtn1.isChecked());
            	 if (togBtn1.isChecked()) {
            		 togBtn1.setBackgroundResource(R.drawable.rf_switch_yellow);
            		 uidOn1=(int)(Math.random()*100000);
                	 uidOff1=(int)(Math.random()*100000);
                	 Log.e("RF_Switch","random uidOn1="+uidOn1);
         			 Log.e("RF_Switch","random uidOff1="+uidOff1);
                	 TUTKClient.timeradd(uidOn1,(short)0x7f,onHour1,onMinute1,learnCursor.getBlob(3),false);
                	 TUTKClient.timeradd(uidOff1,(short)0x7f,offHour1,offMinute1,learnCursor.getBlob(4),false);
                	 TabControl.mSQLHelper.updateSwitchUid(TabControl.writeDB, devid,uidOn1,uidOff1,uidOn2,uidOff2,uidOn3,uidOff3);
     			 } else {
     				togBtn1.setBackgroundResource(R.drawable.rf_switch_blue);
     	            TUTKClient.timerdel(uidOn1);
     				TUTKClient.timerdel(uidOff1);
     			 }
            	 
             	break;
             case R.id.togBtn2:
            	 if(learnCursor.getBlob(3)==null&&learnCursor.getBlob(4)==null)
            	 {
            		 CustomToast.showToast(getApplicationContext(),"please learn first", Toast.LENGTH_SHORT);	
            		 break;
            	 }
            	 Log.e("RF_Switch","togBtn2.isChecked()="+togBtn2.isChecked());
            	 if (togBtn2.isChecked()) {
            		 togBtn2.setBackgroundResource(R.drawable.rf_switch_yellow);
            		 uidOn2=(int)(Math.random()*100000);
                	 uidOff2=(int)(Math.random()*100000);
                	 Log.e("RF_Switch","random uidOn2="+uidOn2);
         			 Log.e("RF_Switch","random uidOff2="+uidOff2);
                	 TUTKClient.timeradd(uidOn2,(short)0x7f,onHour2,onMinute2,learnCursor.getBlob(3),false);
                	 TUTKClient.timeradd(uidOff2,(short)0x7f,offHour2,offMinute2,learnCursor.getBlob(4),false);
                	 TabControl.mSQLHelper.updateSwitchUid(TabControl.writeDB, devid,uidOn1,uidOff1,uidOn2,uidOff2,uidOn3,uidOff3);
     			 } else {
     				togBtn2.setBackgroundResource(R.drawable.rf_switch_blue);
     				TUTKClient.timerdel(uidOn2);
      				TUTKClient.timerdel(uidOff2);
     			 }
            	
             	break;
             case R.id.togBtn3:
            	 if(learnCursor.getBlob(3)==null&&learnCursor.getBlob(4)==null)
            	 {
            		 CustomToast.showToast(getApplicationContext(),"please learn first", Toast.LENGTH_SHORT);	
            		 break;
            	 }
            	 Log.e("RF_Switch","togBtn3.isChecked()="+togBtn3.isChecked());
            	 if (togBtn3.isChecked()) {
            		 togBtn3.setBackgroundResource(R.drawable.rf_switch_yellow);
            		 uidOn3=(int)(Math.random()*100000);
                	 Log.e("RF_Switch","random uidOn3="+uidOn3);
         			 Log.e("RF_Switch","random uidOff3="+uidOff3);
                	 TUTKClient.timeradd(uidOn3,(short)0x7f,onHour3,onMinute3,learnCursor.getBlob(3),false);
                	 TabControl.mSQLHelper.updateSwitchUid(TabControl.writeDB, devid,uidOn1,uidOff1,uidOn2,uidOff2,uidOn3,uidOff3);
     			 } else {
     				togBtn3.setBackgroundResource(R.drawable.rf_switch_blue);
     				 TUTKClient.timerdel(uidOn3);
     			 }
            	
             	break;
             case R.id.togBtn4:
            	 if(learnCursor.getBlob(3)==null&&learnCursor.getBlob(4)==null)
            	 {
            		 CustomToast.showToast(getApplicationContext(),"please learn first", Toast.LENGTH_SHORT);	
            		 break;
            	 }
            	 Log.e("RF_Switch","togBtn3.isChecked()="+togBtn3.isChecked());
            	 if (togBtn3.isChecked()) {
            		 togBtn3.setBackgroundResource(R.drawable.rf_switch_yellow);
            		 uidOff3=(int)(Math.random()*100000);
                	 Log.e("RF_Switch","random uidOn3="+uidOn3);
         			 Log.e("RF_Switch","random uidOff3="+uidOff3);
                	 TUTKClient.timeradd(uidOff3,(short)0x7f,offHour3,offMinute3,learnCursor.getBlob(4),false);
                	 TabControl.mSQLHelper.updateSwitchUid(TabControl.writeDB, devid,uidOn1,uidOff1,uidOn2,uidOff2,uidOn3,uidOff3);
     			 } else {
     				togBtn3.setBackgroundResource(R.drawable.rf_switch_blue);
      				 TUTKClient.timerdel(uidOff3);
     			 }
            	
             	break;
             case R.id.textOn1:
            	 timePicker(v);
//            	 onHour1 = hour;
//	   	       	 onMinute1 = min;
//	   	       	 WhTimerOn1 = str;
                 TUTKClient.timerdel(uidOn1);
	   			 TUTKClient.timerdel(uidOff1);
	   			 togBtn1.setChecked(false);
	   			 togBtn1.setBackgroundResource(R.drawable.rf_switch_blue);
//	   			 TabControl.mSQLHelper.updateSwitch(TabControl.writeDB, devid,WhTimerOn1,WhTimerOff1,WhTimerOn2,WhTimerOff2,WhTimerOn3,WhTimerOff3);
//	   	   		 Log.e("RF_Switch","WhTimerOn1:"+WhTimerOn1);
              	break;
              case R.id.textOff1:
            	  timePicker(v);
//            	  offHour1 = hour;
//    	       	  offMinute1 = min;
//    	   		  WhTimerOff1 = str;
    	   		  TUTKClient.timerdel(uidOn1);
    			  TUTKClient.timerdel(uidOff1);
    			  togBtn1.setChecked(false);
    			  togBtn1.setBackgroundResource(R.drawable.rf_switch_blue);
//    			  TabControl.mSQLHelper.updateSwitch(TabControl.writeDB, devid,WhTimerOn1,WhTimerOff1,WhTimerOn2,WhTimerOff2,WhTimerOn3,WhTimerOff3);
//    	   		  Log.e("RF_Switch","WhTimerOff1:"+WhTimerOff1);
              	break;
              case R.id.textOn2:
             	 timePicker(v);
//             	 onHour2 = hour;
//  	       	     onMinute2 = min;
//  	   		     WhTimerOn2 = str;
  	   		     TUTKClient.timerdel(uidOn2);
  			     TUTKClient.timerdel(uidOff2);
  			     togBtn2.setChecked(false);
  			     togBtn2.setBackgroundResource(R.drawable.rf_switch_blue);
//  			   TabControl.mSQLHelper.updateSwitch(TabControl.writeDB, devid,WhTimerOn1,WhTimerOff1,WhTimerOn2,WhTimerOff2,WhTimerOn3,WhTimerOff3);
//  	   		     Log.e("RF_Switch","WhTimerOn2:"+WhTimerOn2);
               	break;
               case R.id.textOff2:
             	  timePicker(v);
//             	  offHour2 = hour;
//	   	       	  offMinute2 = min;
//	   	   		  WhTimerOff2 = str;
	   	   		  TUTKClient.timerdel(uidOn2);
	   			  TUTKClient.timerdel(uidOff2);
	   			  togBtn2.setChecked(false);
	   			  togBtn2.setBackgroundResource(R.drawable.rf_switch_blue);
//	   			 TabControl.mSQLHelper.updateSwitch(TabControl.writeDB, devid,WhTimerOn1,WhTimerOff1,WhTimerOn2,WhTimerOff2,WhTimerOn3,WhTimerOff3);
//	   	   		  Log.e("RF_Switch","WhTimerOff2:"+WhTimerOff2);
               	break;
               case R.id.textOn3:
              	  timePicker(v);
//                  onHour3 = hour;
//	  	       	  onMinute3 = min;
//	  	   		  WhTimerOn3 = str;
	  	   		  TUTKClient.timerdel(uidOn3);
	  			  togBtn3.setChecked(false);
	  			  togBtn3.setBackgroundResource(R.drawable.rf_switch_blue);
//	  			 TabControl.mSQLHelper.updateSwitch(TabControl.writeDB, devid,WhTimerOn1,WhTimerOff1,WhTimerOn2,WhTimerOff2,WhTimerOn3,WhTimerOff3);
//	  	   		  Log.e("RF_Switch","WhTimerOn3:"+WhTimerOn3);
//                break;
               case R.id.textOff3:
              	  timePicker(v);
//              	  offHour3 = hour;
//	   	       	  offMinute3 = min;
//	   	   		  WhTimerOff3 = str;
	   	   		  TUTKClient.timerdel(uidOff3);
	   			  togBtn4.setChecked(false);
	   			  togBtn4.setBackgroundResource(R.drawable.rf_switch_blue);
//	   			 TabControl.mSQLHelper.updateSwitch(TabControl.writeDB, devid,WhTimerOn1,WhTimerOff1,WhTimerOn2,WhTimerOff2,WhTimerOn3,WhTimerOff3);
//	   	   		  Log.e("RF_Switch","WhTimerOff3:"+WhTimerOff3);
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
			        	  hour = (byte)hourOfDay;
				       	  min = (byte)minute;
				       	  str = sb.toString();
				 		 switch(v.getId())
				   	  {
					   	  case R.id.textOn1:
					   		  onHour1 = hour;
					       	  onMinute1 = min;
					       	  WhTimerOn1 = str;
//				              TUTKClient.timerdel(uidOn1);
//							  TUTKClient.timerdel(uidOff1);
//							  togBtn1.setChecked(false);
//							  togBtn1.setBackgroundResource(R.drawable.rf_switch_blue);
					   		  Log.e("RF_Switch","WhTimerOn1:"+WhTimerOn1);
					   		  break;
					   	  case R.id.textOff1:
					   		  offHour1 = hour;
					       	  offMinute1 = min;
					   		  WhTimerOff1 = str;
//					   		  TUTKClient.timerdel(uidOn1);
//							  TUTKClient.timerdel(uidOff1);
//							  togBtn1.setChecked(false);
//							  togBtn1.setBackgroundResource(R.drawable.rf_switch_blue);
					   		  Log.e("RF_Switch","WhTimerOff1:"+WhTimerOff1);
					   		  break;
					   	  case R.id.textOn2:
					   		  onHour2 = hour;
					       	  onMinute2 = min;
					   		  WhTimerOn2 = str;
//					   		  TUTKClient.timerdel(uidOn2);
//							  TUTKClient.timerdel(uidOff2);
//							  togBtn2.setChecked(false);
//							  togBtn2.setBackgroundResource(R.drawable.rf_switch_blue);
					   		  Log.e("RF_Switch","WhTimerOn2:"+WhTimerOn2);
					   		  break;
					   	  case R.id.textOff2:
					   		  offHour2 = hour;
					       	  offMinute2 = min;
					   		  WhTimerOff2 = str;
//					   		  TUTKClient.timerdel(uidOn2);
//							  TUTKClient.timerdel(uidOff2);
//							  togBtn2.setChecked(false);
//							  togBtn2.setBackgroundResource(R.drawable.rf_switch_blue);
					   		  Log.e("RF_Switch","WhTimerOff2:"+WhTimerOff2);
					   		  break;
					   	  case R.id.textOn3:
					   		  onHour3 = hour;
					       	  onMinute3 = min;
					   		  WhTimerOn3 = str;
//					   		  TUTKClient.timerdel(uidOn3);
//							  togBtn3.setChecked(false);
//							  togBtn3.setBackgroundResource(R.drawable.rf_switch_blue);
					   		  Log.e("RF_Switch","WhTimerOn3:"+WhTimerOn3);
					   		  break;
					   	  case R.id.textOff3:
					   		  offHour3 = hour;
					       	  offMinute3 = min;
					   		  WhTimerOff3 = str;
//					   		  TUTKClient.timerdel(uidOff3);
//							  togBtn4.setChecked(false);
//							  togBtn4.setBackgroundResource(R.drawable.rf_switch_blue);
					   		  Log.e("RF_Switch","WhTimerOff3:"+WhTimerOff3);
					   		  break;
					   	  default:
					   		  break;
					   			  
					   	  }
						 TabControl.mSQLHelper.updateSwitch(TabControl.writeDB, devid,WhTimerOn1,WhTimerOff1,WhTimerOn2,WhTimerOff2,WhTimerOn3,WhTimerOff3);
					
			        	  
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
		
		private void weekbuttonstate(int curButton )
		{
			 if(learnCursor.getInt(curButton+2) == 1)
        	 {
        		 TabControl.mSQLHelper.updateBtn(TabControl.writeDB, devid, curButton,0);
        		 TabControl.mViewSelected.imageviewClickGreyChanged(button[curButton-1]);
        		 
        	 }else{
        		 TabControl.mSQLHelper.updateBtn(TabControl.writeDB, devid, curButton,1);
        		 TabControl.mViewSelected.imageviewClickRecover(button[curButton-1]);
        	 }
		}
   
}
