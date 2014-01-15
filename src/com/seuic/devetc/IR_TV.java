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
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class IR_TV extends Activity implements android.view.View.OnClickListener{
	Button backBtn,leanrnBtn;
	ImageView   devpic;
	final int buttonMaxNum=14;
	ImageView button[]=new ImageView[buttonMaxNum];
	boolean btnLearn[]=new boolean[buttonMaxNum];
	int curButton=-1;
	
	private ProgressDialog progressDialog;  
	int devid;
	String mUid;
	String learnFalse="false"; 
	Boolean lenclr=false;
	

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
		button[0].setOnClickListener(this);  
		button[1].setOnClickListener(this);  
		button[2].setOnClickListener(this);  
		button[3].setOnClickListener(this);  
		button[4].setOnClickListener(this);  
		button[5].setOnClickListener(this);  
		button[6].setOnClickListener(this);  
		button[7].setOnClickListener(this);  
		button[8].setOnClickListener(this);  
		button[9].setOnClickListener(this);  
		button[10].setOnClickListener(this);  
		button[11].setOnClickListener(this);  
		button[12].setOnClickListener(this);  
		button[13].setOnClickListener(this);  
		
		TabControl.mViewSelected.setButtonFocusChanged(backBtn);
		TabControl.mViewSelected.setButtonFocusChanged(leanrnBtn);
		for(int i=0;i< buttonMaxNum;i++){
			TabControl.mViewSelected.setImageViewFocusChanged(button[i]);
		}
		TabControl.mViewSelected.buttonClickRecover(leanrnBtn);
		
		Intent intent=getIntent();
		mUid=intent.getStringExtra("uid");
		devid=intent.getIntExtra("devid", 0);
		if(devid==0){
			Log.e("leewoo", "deid error = 0");
			}
		
		Cursor cursor=TabControl.mSQLHelper.seleteBtnLearn(TabControl.writeDB,devid);
		Log.e("leewoo", "cur: "+cursor.getCount());
		if(cursor.getCount()>0){				 
			//学习	
			for(int i=0;i<buttonMaxNum;i++){
				btnLearn[i]=cursor.getString(i+3).equals("true")?true:false;
			}
		}else{
			Log.e("leewoo", "cur learn 初始化"+cursor.getCount());
			//未初始化
			TabControl.mSQLHelper.insertBtn(TabControl.writeDB,mUid,devid,"learn" ,learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse);
		}
		cursor.close();
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
//             		btnclr1=true;
//             		TabControl.mViewSelected.imageviewClickRecover(button1);
//             		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, 1, true);  
     	        }
             	
             	break;
             case R.id.button2:
             	
             	if(lenclr==true){
             		showProgressDialog(); 
             		curButton=2;
             	}
             	
             	break;
             case R.id.button3:
             	if(lenclr==true){  
             		showProgressDialog();
             		curButton=3;
             	}             	
             	break;
             
             case R.id.button4:
             	if(lenclr==true){
         			showProgressDialog();
         			curButton=4;
             	}
             	break;
             case R.id.button5:
             	if(lenclr==true){             		 
             		showProgressDialog();
         			curButton=5;     	        	
             	}
             	break;
             case R.id.button6:
             	if(lenclr==true){     	        	 
             		showProgressDialog();
         			curButton=6;    	        	
             	}
             	break;
             case R.id.button7:
             	if(lenclr==true){
             		showProgressDialog();
         			curButton=7;
     	        	
             	}
             	break;
             case R.id.button8:
             	if(lenclr==true){
             		showProgressDialog();
         			curButton=8;    	        	
             	}
             	break;
             case R.id.button9:
             	if(lenclr==true){     	        	 
             		showProgressDialog();
         			curButton=9;
     	        }
             	break;    
             	
             case R.id.button10:
             	if(lenclr==true){     	        	  
             		showProgressDialog();
         			curButton=10;  	        	
             	}
             	break;             	
         	 case R.id.button11:                 	
             	if(lenclr==true){         	        	
             		showProgressDialog();
         			curButton=11;
     	        }
             	break;
             case R.id.button12:                 	
             	if(lenclr==true){         	        	
             		showProgressDialog();
         			curButton=12;       	        	
             	}             	
             	break;
             case R.id.button13:
             	if(lenclr==true){         	        
             		showProgressDialog();
         			curButton=13;             		
             	}                 	
             	break;                 
             case R.id.button14:
             	if(lenclr==true){         	        	
             		showProgressDialog();
         			curButton=14;       	        	
             	}
             	break;                
        default:  
        	Log.e("leewoo", "Button id =default " ); 
            break;  
        }
	  
	}
	 	private Handler handler = new Handler(){ 
	        @Override  
	        public void handleMessage(Message msg) {  
	        	
	        	Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show();  

	        	TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, curButton, true);
	        	btnLearn[curButton-1]=true;
	        	Log.e("leewoo", "");
	        	curButton=-1;
	            //关闭ProgressDialog  
	            progressDialog.dismiss(); 
//	        	TabControl.mViewSelected.imageviewClickRecover(button[curButton-1]);
//	            TabControl.mViewSelected.imageviewClickLearn(button[curButton-1]);
	        }};  
			 private void showProgressDialog(){  
			 progressDialog = ProgressDialog.show(IR_TV.this, "Learnning...", "Please wait...", true, false); 
			 new Thread(){        
			     @Override  
			     public void run() {  
			       try {
							Thread.sleep(3000) ;
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			         handler.sendEmptyMessage(0);  
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
