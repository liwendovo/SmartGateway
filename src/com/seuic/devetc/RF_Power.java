package com.seuic.devetc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.seuic.net.TUTKClient;
import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class RF_Power extends Activity implements android.view.View.OnClickListener{
	
	Button  backBtn,leanrnBtn;
	ImageView   devpic;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rf_power);
        backBtn=(Button)findViewById(R.id.back);
		leanrnBtn=(Button)findViewById(R.id.titleBtn);
		button[0]=(ImageView)findViewById(R.id.button1);
		button[1]=(ImageView)findViewById(R.id.button2);
	
		backBtn.setOnClickListener(this); 
		leanrnBtn.setOnClickListener(this); 
		button[0].setOnClickListener(this);  
		button[1].setOnClickListener(this); 
		
		TabControl.mViewSelected.setButtonClickChanged(backBtn);
		TabControl.mViewSelected.setButtonClickChanged(leanrnBtn);
		TabControl.mViewSelected.setImageViewClickChanged(button[0]);
		TabControl.mViewSelected.setImageViewClickChanged(button[1]);		
		devpic=(ImageView)findViewById(R.id.pic);
		devpic.setImageDrawable(getResources().getDrawable(R.drawable.rf_power));
		
		
		
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
                           
	        default:  
	        	Log.e("leewoo", "Button id =default " ); 
	            break;  
        }	  
	}
		 private void showProgressDialog(){  
		 progressDialog = ProgressDialog.show(RF_Power.this,"", getResources().getString(R.string.studying), true, false); 
		 new Thread(){        
		     @Override  
		     public void run() {  
		    	 Message learnMsg=new Message();
		    	 if(TUTKClient.learn(2,ioCtrlBuf))
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
			 progressDialog = ProgressDialog.show(RF_Power.this, "sending...", "Please wait...", true, false); 
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
