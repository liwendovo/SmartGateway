
package com.seuic.devetc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class IR_Custom1 extends Activity implements android.view.View.OnClickListener,OnLongClickListener {
	Button  backBtn,leanrnBtn;
	ImageView   devpic;	
	
	final int buttonMaxNum=10;
	View button[]=new View[buttonMaxNum];
	boolean btnLearn[]=new boolean[buttonMaxNum];
	int curButton=-1;
	Boolean lenclr=false;
	private ProgressDialog progressDialog;  
//	Button  button1,button2,button3,
//			button4,button5,button6,
//			button7,button8,button9;
	ImageView   button10;
	
	int devid;
	String mUid;
	String learnFalse="false";
	String learnTrue="true";
	String btnDefaults="自定义";
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ir_custom1);
		for(int i=0;i<buttonMaxNum;i++){btnLearn[i]=false;}
		Log.e("leewoo", "in IR_Custom1 ");
		backBtn=(Button)findViewById(R.id.back);
		leanrnBtn=(Button)findViewById(R.id.titleBtn);
		button[0]=(Button)findViewById(R.id.button1);
		button[1]=(Button)findViewById(R.id.button2);
		button[2]=(Button)findViewById(R.id.button3);		
		button[3]=(Button)findViewById(R.id.button4);		
		button[4]=(Button)findViewById(R.id.button5);
		button[5]=(Button)findViewById(R.id.button6);		
		button[6]=(Button)findViewById(R.id.button7);		
		button[7]=(Button)findViewById(R.id.button8);
		button[8]=(Button)findViewById(R.id.button9);
		button[9]=(ImageView)findViewById(R.id.button10);	

		backBtn.setOnClickListener(this); 
		leanrnBtn.setOnClickListener(this);
		for(int i=0;i<buttonMaxNum-1;i++){
			button[i].setOnClickListener(this);
			button[i].setOnLongClickListener(this);
		}		
		button[9].setOnClickListener(this);
		
		
		
		TabControl.mViewSelected.setButtonFocusChanged(backBtn);
		TabControl.mViewSelected.setButtonFocusChanged(leanrnBtn);		
		TabControl.mViewSelected.setButtonFocusChanged(button[0]);
		TabControl.mViewSelected.setButtonFocusChanged(button[1]);
		TabControl.mViewSelected.setButtonFocusChanged(button[2]);
		TabControl.mViewSelected.setButtonFocusChanged(button[3]);
		TabControl.mViewSelected.setButtonFocusChanged(button[4]);
		TabControl.mViewSelected.setButtonFocusChanged(button[5]);
		TabControl.mViewSelected.setButtonFocusChanged(button[6]);
		TabControl.mViewSelected.setButtonFocusChanged(button[7]);
		TabControl.mViewSelected.setButtonFocusChanged(button[8]);		
		TabControl.mViewSelected.setImageViewFocusChanged(button[9]);
		
		TabControl.mViewSelected.buttonClickRecover(leanrnBtn);
		
		devpic=(ImageView)findViewById(R.id.pic);
		devpic.setImageDrawable(getResources().getDrawable(R.drawable.ir_custom));

		

	

		
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
		
		cursor=TabControl.mSQLHelper.seleteBtnName(TabControl.writeDB,devid);
		Log.e("leewoo", "cur: "+cursor.getCount());
		if(cursor.getCount()>0){
			//已初始化		//学习	
			for(int i=0;i<buttonMaxNum-1;i++){
				
				((Button)button[i]).setText(cursor.getString(i+3));
			}			
		}else{
			Log.e("leewoo", "cur name 初始化"+cursor.getCount());
			//未初始化
			TabControl.mSQLHelper.insertBtn(TabControl.writeDB,mUid,devid, "name" ,btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults+14);
		}		
		cursor.close();
		
		
	    setbuttonstate();
				
		
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
        	if(lenclr==true){
            	Log.e("leewoo", "clr"+v.getId() ); 

            	TabControl.mViewSelected.buttonClickLearn(leanrnBtn); 
            	
            	for(int i=0;i<buttonMaxNum-1;i++){    				
            		TabControl.mViewSelected.buttonClickLearn(button[i]);
    			}	            
            	TabControl.mViewSelected.imageviewClickLearn(button[9]);			        	

        	}else{
        		TabControl.mViewSelected.buttonClickRecover(leanrnBtn); 
        		setbuttonstate();    
        	}
        	break;
  	    case R.id.button1:        	
        	if(lenclr==true){	        	
        		showProgressDialog();
         		curButton=1;
	        	
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
            //关闭ProgressDialog  
            progressDialog.dismiss(); 
//        	TabControl.mViewSelected.imageviewClickRecover(button[curButton-1]);
//          TabControl.mViewSelected.imageviewClickLearn(button[curButton-1]);
        }};  
		 private void showProgressDialog(){  
		 progressDialog = ProgressDialog.show(IR_Custom1.this, "Learnning...", "Please wait...", true, false); 
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
				for(int i=0;i<(buttonMaxNum-1);i++){
					if(btnLearn[i])	TabControl.mViewSelected.buttonClickRecover(button[i]);
			    	else TabControl.mViewSelected.buttonClickGreyChanged(button[i]);
				}
				if(btnLearn[9])TabControl.mViewSelected.imageviewClickRecover(button[9]);
		    	else TabControl.mViewSelected.imageviewClickGreyChanged(button[9]);
			}
			
	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
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
		 AlertDialog.Builder builder = new Builder(IR_Custom1.this);
		// final String mStr = null;
		 builder.setMessage("Please input name");
		 builder.setTitle("Button name");
		 final EditText et=new EditText(this);
		 builder.setView(et);
		 builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub	
			
			((Button)button[btnid-1]).setText(et.getText().toString());
			TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, btnid-1, et.getText().toString());
			
			
//			switch (btnid) {
//			case 1:
//				button1.setText(et.getText().toString());
//				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 1, et.getText().toString());
//				break;
//			case 2:
//				button2.setText(et.getText().toString());
//				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 2, et.getText().toString());
//				break;
//			case 3:
//				button3.setText(et.getText().toString());
//				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 3, et.getText().toString());
//				break;
//			case 4:
//				button4.setText(et.getText().toString());
//				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 4, et.getText().toString());
//				break;
//			case 5:
//				button5.setText(et.getText().toString());
//				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 5, et.getText().toString());
//				break;
//			case 6:
//				button6.setText(et.getText().toString());
//				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 6, et.getText().toString());
//				break;
//			case 7:
//				button7.setText(et.getText().toString());
//				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 7, et.getText().toString());
//				break;				
//			case 8:
//				button8.setText(et.getText().toString());
//				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 8, et.getText().toString());
//				break;
//			case 9:
//				button9.setText(et.getText().toString());
//				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 9, et.getText().toString());
//				break;
//			default:
//				break;
//			}
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

