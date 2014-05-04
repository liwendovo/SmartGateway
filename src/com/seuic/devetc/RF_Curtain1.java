package com.seuic.devetc;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.seuic.function.CustomToast;
import com.seuic.net.TUTKClient;
import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class RF_Curtain1 extends Activity implements android.view.View.OnClickListener{
	Button  backBtn,leanrnBtn;
	LinearLayout back_ll,titleBtn_ll;
	ImageView   devpic;
	final int buttonMaxNum=3;
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
        setContentView(R.layout.rf_curtain1);
        backBtn=(Button)findViewById(R.id.back);
		leanrnBtn=(Button)findViewById(R.id.titleBtn);
		back_ll=(LinearLayout)findViewById(R.id.back_ll);
		titleBtn_ll=(LinearLayout)findViewById(R.id.titleBtn_ll);
		button[0]=(ImageView)findViewById(R.id.button1);
		button[1]=(ImageView)findViewById(R.id.button2);
		button[2]=(ImageView)findViewById(R.id.button3);
	
		backBtn.setOnClickListener(this); 
		leanrnBtn.setOnClickListener(this); 
		back_ll.setOnClickListener(this); 
		titleBtn_ll.setOnClickListener(this);
		button[0].setOnClickListener(this);  
		button[1].setOnClickListener(this);
		button[2].setOnClickListener(this);
		
		TabControl.mViewSelected.setButtonClickChanged(backBtn);
		TabControl.mViewSelected.setButtonClickChanged(leanrnBtn);
		TabControl.mViewSelected.setImageViewClickChanged(button[0]);
		TabControl.mViewSelected.setImageViewClickChanged(button[1]);	
		TabControl.mViewSelected.setImageViewClickChanged(button[2]);
		devpic=(ImageView)findViewById(R.id.pic);
		devpic.setImageDrawable(getResources().getDrawable(R.drawable.rf_curtain));
		
		
		
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
                           
	        default:  
	        	Log.e("leewoo", "Button id =default " ); 
	            break;  
        }	  
	}
		 private void showProgressDialog(int num){ 
			 TabControl.mViewSelected.imageviewClickLearnDefault(button[num-1]);
			 progressDialog = new ProgressDialog(RF_Curtain1.this);
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
