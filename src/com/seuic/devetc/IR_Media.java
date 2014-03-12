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

import com.seuic.adapter.CustomToast;
import com.seuic.net.TUTKClient;
import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class IR_Media extends Activity implements android.view.View.OnClickListener {
	Button backBtn,leanrnBtn;
	LinearLayout back_ll,titleBtn_ll;
	ImageView   devpic;
	final int buttonMaxNum=10;
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
		setContentView(R.layout.ir_media);

		backBtn=(Button)findViewById(R.id.back);	
		leanrnBtn=(Button)findViewById(R.id.titleBtn);	
		back_ll=(LinearLayout)findViewById(R.id.back_ll);	
		titleBtn_ll=(LinearLayout)findViewById(R.id.titleBtn_ll);	
		devpic=(ImageView)findViewById(R.id.pic);
		devpic.setImageDrawable(getResources().getDrawable(R.drawable.ir_media));
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
		
		backBtn.setOnClickListener(this); 
		leanrnBtn.setOnClickListener(this); 		 
	    
		TabControl.mViewSelected.setButtonClickChanged(backBtn);
		TabControl.mViewSelected.setButtonClickChanged(leanrnBtn);
		back_ll.setOnClickListener(this); 
		titleBtn_ll.setOnClickListener(this);
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
             	if(lenclr==true){   
         			curButton=5;    
         			showProgressDialog(curButton);
             	}else{
     	        	send(5);
             	} 
             	break;
             case R.id.button6:
             	if(lenclr==true){  
         			curButton=6;   
         			showProgressDialog(curButton);
             	}else{
     	        	send(6);
             	} 
             	break;
             case R.id.button7:
             	if(lenclr==true){
         			curButton=7;  
         			showProgressDialog(curButton);
             	}else{
     	        	send(7);
             	} 
             	break;
             case R.id.button8:
             	if(lenclr==true){
         			curButton=8; 
         			showProgressDialog(curButton);
             	}else{
     	        	send(8);
             	} 
             	break;
             case R.id.button9:
             	if(lenclr==true){   
         			curButton=9;
         			showProgressDialog(curButton);
     	        }else{
     	        	send(9);
             	} 
             	break;
             case R.id.button10:
              	if(lenclr==true){   
          			curButton=10;
          			showProgressDialog(curButton);
      	        }else{
      	        	send(10);
              	} 
              	break;
            
	        default:  
	        	Log.e("leewoo", "Button id =default " ); 
	            break;  
        }	  
	}
	 	 
		 private void showProgressDialog(int num){  
			 TabControl.mViewSelected.imageviewClickLearnDefault(button[num-1]);
			 progressDialog = new ProgressDialog(IR_Media.this);
			 progressDialog.setMessage(getResources().getString(R.string.studying));
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

//			 progressDialog = ProgressDialog.show(IR_TV.this,"", getResources().getString(R.string.studying), true, false); 
		
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
//			 progressDialog = ProgressDialog.show(IR_TV.this, "", "", true, false); 
			 new Thread(){        
			     @Override  
			     public void run() {  
//			    	 Message learnMsg=new Message();
			    	 TUTKClient.send(learnCursor.getBlob(btnid+2),true);
//			    	 if(TUTKClient.send(learnCursor.getBlob(btnid+2),true))
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
