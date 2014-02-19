package com.seuic.devetc;


import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.seuic.net.TUTKClient;
import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class IR_TV extends Activity implements android.view.View.OnClickListener{
	Button backBtn,leanrnBtn;
	ImageView   devpic;
	final int buttonMaxNum=14;
	ImageView button[]=new ImageView[buttonMaxNum];
	boolean btnLearn[]=new boolean[buttonMaxNum];
	int curButton=-1;
	byte ioCtrlBuf[]=new byte[TUTKClient.MAX_SIZE_IOCTRL_BUF]; 
	private ProgressDialog progressDialog;  
	int devid;
	String mUid;	
	Boolean lenclr=false;
	Cursor learnCursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ir_tv);

		backBtn=(Button)findViewById(R.id.back);	
		leanrnBtn=(Button)findViewById(R.id.titleBtn);	
		devpic=(ImageView)findViewById(R.id.pic);
		devpic.setImageDrawable(getResources().getDrawable(R.drawable.ir_tv));
		button[0]=(ImageView)findViewById(R.id.button1);
		button[1]=(ImageView)findViewById(R.id.button2);
		button[2]=(ImageView)findViewById(R.id.button3);		
		button[3]=(ImageView)findViewById(R.id.button4);		
		button[4]=(ImageView)findViewById(R.id.button5);
		button[5]=(ImageView)findViewById(R.id.button6);		
		button[6]=(ImageView)findViewById(R.id.button7);		
		button[7]=(ImageView)findViewById(R.id.button8);
		button[8]=(ImageView)findViewById(R.id.button9);
		button[9]=(ImageView)findViewById(R.id.button10);
		button[10]=(ImageView)findViewById(R.id.button11);
		button[11]=(ImageView)findViewById(R.id.button12);
		button[12]=(ImageView)findViewById(R.id.button13);
		button[13]=(ImageView)findViewById(R.id.button14);
		
		backBtn.setOnClickListener(this); 
		leanrnBtn.setOnClickListener(this); 		 
	
		TabControl.mViewSelected.setButtonClickChanged(backBtn);
		TabControl.mViewSelected.setButtonClickChanged(leanrnBtn);
		for(int i=0;i< buttonMaxNum;i++){
			button[i].setOnClickListener(this);  
			TabControl.mViewSelected.setImageViewClickChanged(button[i]);
		}
		TabControl.mViewSelected.buttonClickRecover(leanrnBtn);
		
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
	        case R.id.back:
	        	finish();
	        	break;
         
	        case R.id.titleBtn:	
	        	lenclr=!lenclr;  
	        	if(lenclr==true){	
	            	Log.e("leewoo", "clr"+v.getId() ); 
	            	TabControl.mViewSelected.buttonClickLearn(leanrnBtn);	 
	            	for(int i=0;i< buttonMaxNum;i++){
	        			TabControl.mViewSelected.imageviewClickLearn(button[i]);
	        		}
	        	}else{
	        		TabControl.mViewSelected.buttonClickRecover(leanrnBtn);
	        		setbuttonstate();    
	        	}
	        	break;
        	 case R.id.button1:             	
             	if(lenclr==true){
             		showProgressDialog();
             		curButton=1;
     	        }else{//发送
     	        	send(1);
             	}
             	break;
             case R.id.button2:             	
             	if(lenclr==true){
             		showProgressDialog(); 
             		curButton=2;
             	}else{
     	        	send(2);
             	}             	
             	break;
             case R.id.button3:
             	if(lenclr==true){  
             		showProgressDialog();
             		curButton=3;
             	}else{
     	        	send(3);
             	}             	
             	break;             
             case R.id.button4:
             	if(lenclr==true){
         			showProgressDialog();
         			curButton=4;
             	}else{
     	        	send(4);
             	} 
             	break;
             case R.id.button5:
             	if(lenclr==true){             		 
             		showProgressDialog();
         			curButton=5;     	        	
             	}else{
     	        	send(5);
             	} 
             	break;
             case R.id.button6:
             	if(lenclr==true){     	        	 
             		showProgressDialog();
         			curButton=6;    	        	
             	}else{
     	        	send(6);
             	} 
             	break;
             case R.id.button7:
             	if(lenclr==true){
             		showProgressDialog();
         			curButton=7;     	        
             	}else{
     	        	send(7);
             	} 
             	break;
             case R.id.button8:
             	if(lenclr==true){
             		showProgressDialog();
         			curButton=8;    	        	
             	}else{
     	        	send(8);
             	} 
             	break;
             case R.id.button9:
             	if(lenclr==true){     	        	 
             		showProgressDialog();
         			curButton=9;
     	        }else{
     	        	send(9);
             	} 
             	break;
             case R.id.button10:
             	if(lenclr==true){     	        	  
             		showProgressDialog();
         			curButton=10;  	        	
             	}else{
     	        	send(10);
             	} 
             	break;             	
         	 case R.id.button11:                 	
             	if(lenclr==true){         	        	
             		showProgressDialog();
         			curButton=11;
     	        }else{
     	        	send(11);
             	} 
             	break;
             case R.id.button12:                 	
             	if(lenclr==true){         	        	
             		showProgressDialog();
         			curButton=12;       	        	
             	}else{
     	        	send(12);
             	}              	
             	break;
             case R.id.button13:
             	if(lenclr==true){         	        
             		showProgressDialog();
         			curButton=13;             		
             	}else{
     	        	send(13);
             	}                  	
             	break;                 
             case R.id.button14:
             	if(lenclr==true){         	        	
             		showProgressDialog();
         			curButton=14;       	        	
             	}else{
     	        	send(14);
             	} 
             	break;                
	        default:  
	        	Log.e("leewoo", "Button id =default " ); 
	            break;  
        }	  
	}
	 	 
		 private void showProgressDialog(){  
		 progressDialog = ProgressDialog.show(IR_TV.this,"", getResources().getString(R.string.studying), true, false); 
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
		    	 learnHandler.sendMessage(learnMsg);  
		     }}.start();      
		 }
		 private Handler learnHandler = new Handler(){ 
		        @Override  
		        public void handleMessage(Message msg) {  
		        	if(0==msg.what){
			        	Toast.makeText(getApplicationContext(),  getResources().getString(R.string.studysuccessful), Toast.LENGTH_SHORT).show();  
			        	TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, curButton,ioCtrlBuf);
			        	btnLearn[curButton-1]=true;	        	
			        	curButton=-1;
			        	//更新learnCursor
			        	learnCursor.close();
			        	learnCursor=TabControl.mSQLHelper.seleteBtnLearn(TabControl.writeDB,devid);	 
		        	}else{
		        		Toast.makeText(getApplicationContext(),  getResources().getString(R.string.studyfailed), Toast.LENGTH_SHORT).show();	
		        	}
		            progressDialog.dismiss(); 
		        }}; 
		 private void send(final int btnid){  
			 progressDialog = ProgressDialog.show(IR_TV.this, "sending...", "Please wait...", true, false); 
			 new Thread(){        
			     @Override  
			     public void run() {  
			    	 Message learnMsg=new Message();
			    	
			    	 if(TUTKClient.send(learnCursor.getBlob(btnid+2),true))
			    	 {
			    		 learnMsg.what=0;
			    	 }else{
			    		 learnMsg.what=1;	
			    	 }	 
			    	 sendHandler.sendMessage(learnMsg);  
			     }}.start();      
			 }
		private Handler sendHandler = new Handler(){ 
	        @Override  
	        public void handleMessage(Message msg) {  
	        	if(0==msg.what){
	        	Toast.makeText(getApplicationContext(), "send success", Toast.LENGTH_SHORT).show(); 		        
	        	}else{
	        		Toast.makeText(getApplicationContext(), "send failed", Toast.LENGTH_SHORT).show();	
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
