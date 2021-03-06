
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
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.seuic.function.CustomToast;
import com.seuic.net.TUTKClient;
import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class IR_Custom1 extends Activity implements android.view.View.OnClickListener,OnLongClickListener {
	Button  backBtn,leanrnBtn;
	ImageView   devpic;	
	LinearLayout back_ll,titleBtn_ll;
	final int buttonMaxNum=10;
	View button[]=new View[buttonMaxNum];
	boolean btnLearn[]=new boolean[buttonMaxNum];
	int curButton=-1;
	Boolean lenclr=false;
	private ProgressDialog progressDialog;  
	ImageView   button10;
	byte ioCtrlBuf[]=new byte[TUTKClient.MAX_SIZE_IOCTRL_BUF]; 
	int devid;
	String mUid;
	String devType;
	Cursor learnCursor;
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
		
		back_ll=(LinearLayout)findViewById(R.id.back_ll);
		titleBtn_ll=(LinearLayout)findViewById(R.id.titleBtn_ll);

		backBtn.setOnClickListener(this); 
		leanrnBtn.setOnClickListener(this);
		back_ll.setOnClickListener(this); 
		titleBtn_ll.setOnClickListener(this);
		for(int i=0;i<buttonMaxNum-1;i++){
			button[i].setOnClickListener(this);
			button[i].setOnLongClickListener(this);
		}		
		button[9].setOnClickListener(this);
		
		
		
		TabControl.mViewSelected.setButtonClickChanged(backBtn);
		TabControl.mViewSelected.setButtonClickChanged(leanrnBtn);		
		TabControl.mViewSelected.setButtonClickChanged(button[0]);
		TabControl.mViewSelected.setButtonClickChanged(button[1]);
		TabControl.mViewSelected.setButtonClickChanged(button[2]);
		TabControl.mViewSelected.setButtonClickChanged(button[3]);
		TabControl.mViewSelected.setButtonClickChanged(button[4]);
		TabControl.mViewSelected.setButtonClickChanged(button[5]);
		TabControl.mViewSelected.setButtonClickChanged(button[6]);
		TabControl.mViewSelected.setButtonClickChanged(button[7]);
		TabControl.mViewSelected.setButtonClickChanged(button[8]);		
		TabControl.mViewSelected.setImageViewClickChanged(button[9]);		
		TabControl.mViewSelected.buttonClickRecover(leanrnBtn);
		devpic=(ImageView)findViewById(R.id.pic);
		devpic.setImageDrawable(getResources().getDrawable(R.drawable.ir_custom));

		

	

		
		Intent intent=getIntent();
		mUid=intent.getStringExtra("uid");
		devid=intent.getIntExtra("devid", 0);
		devType=intent.getStringExtra("devType");
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
		
		Cursor cursor=TabControl.mSQLHelper.seleteBtnName(TabControl.writeDB,devid);
		Log.e("leewoo", "cur: "+cursor.getCount());
		if(cursor.getCount()>0){
			//已初始化		//学习	
//			for(int i=0;i<buttonMaxNum-1;i++){	
//				String str=cursor.getBlob(i+3)!=null?new String(cursor.getBlob(i+3)):"define";
//				((Button)button[i]).setText(str);
//			}	
			for(int i=1;i<buttonMaxNum;i++){	
				String str;
				if(cursor.getBlob(i+2)!=null) str=new String(cursor.getBlob(i+2));
				else str="Mode"+i;
				((Button)button[i-1]).setText(str);
			}			
		}else{
			Log.e("leewoo", "cur name 初始化"+cursor.getCount());
			//未初始化
			TabControl.mSQLHelper.insertBtnName(TabControl.writeDB,mUid,devid);
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
        case R.id.back_ll:
        case R.id.back: finish();break;
        
        case R.id.titleBtn_ll:
        case R.id.titleBtn:
        	lenclr=!lenclr; 
        	if(lenclr==true){
            	Log.e("leewoo", "clr"+v.getId() ); 

            	TabControl.mViewSelected.buttonClickLearn(leanrnBtn); 
            	
            	for(int i=0;i<buttonMaxNum-1;i++){    				
            		TabControl.mViewSelected.buttonClickLearnDefault(button[i]);
    			}	            
            	TabControl.mViewSelected.imageviewClickLearnDefault(button[9]);			        	

        	}else{
        		TabControl.mViewSelected.buttonClickRecover(leanrnBtn); 
        		setbuttonstate();    
        	}
        	break;
  	    case R.id.button1:        	
        	if(lenclr==true){	        	
        		
         		curButton=1;
         		showProgressDialog(curButton);
	        	
	        }else{
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
		 InputFilter[] filters = {new InputFilter.LengthFilter(8)};   
		 et.setFilters(filters); 
		 et.setSingleLine(true);
		 builder.setView(et);
		 builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub	
			 if(!btnLearn[btnid-1]) TabControl.mViewSelected.buttonClickLearnDefault(button[btnid-1]);
			((Button)button[btnid-1]).setText(et.getText().toString());
			TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, btnid, et.getText().toString());
		   }
		});
		  builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			 public void onClick(DialogInterface dialog, int which) {
			if(!btnLearn[btnid-1]) TabControl.mViewSelected.buttonClickLearnDefault(button[btnid-1]);
			 dialog.dismiss();
			  }
			 });
		 builder.create().show();		
	   }
	
		 private void showProgressDialog( int num){  
//			 Log.e("IR_Custom1", "num"+num);
			 if(num==10)   TabControl.mViewSelected.imageviewClickLearnDefault(button[9]);	
			 else TabControl.mViewSelected.buttonClickLearnDefault(button[num-1]);
			 progressDialog = new ProgressDialog(IR_Custom1.this);
			 progressDialog.setMessage(getResources().getString(R.string.studying));
			 progressDialog.setCancelable(false);
			 progressDialog.setButton("Cancel", new DialogInterface.OnClickListener() {
	             public void onClick(DialogInterface dialog, int i)
	             {
	            	 new Thread(){        
	    			     @Override  
	    			     public void run() {  
		    			    if(devType.equals("ir"))
		    			    {
		    			    	TUTKClient.cancellearn(true);
		    			    }else{
		    			    	TUTKClient.cancellearn(false);
		    			    }
	    			     
	    			     }}.start(); 
	    			     dialog.cancel();
	    			     Log.e("IR_Custom1", "in the progressDialog.setButton");
	             }
	         });
			 progressDialog.show();
//			 Log.e("IR_Custom1", "in the progressDialog");
			 new Thread(){        
		     @Override  
		     public void run() {  
		    	 Message learnMsg=new Message();
		    	 Log.e("IR_Custom1 devType", "devType"+devType);
		    	 if(devType.equals("ir")){
			    	 if(TUTKClient.learn(0,ioCtrlBuf))
			    	 {
			    		 learnMsg.what=0;
			    	 }else{
			    		 learnMsg.what=1;	
			    	 }	
			    	 Log.e("IR_Custom1", "learnMsg.what"+learnMsg.what);
			    }else{
			    	if(TUTKClient.learn(2,ioCtrlBuf))
			    	 {
			    		 learnMsg.what=0;
			    	 }else{
			    		 learnMsg.what=1;	
			    	 }	
			    	 Log.e("RF_Custom1", "learnMsg.what"+learnMsg.what);
			    	
			    }
		    	 
		    	 learnHandler.sendMessage(learnMsg); 
		     }}.start();  
		 }
	 	private Handler learnHandler = new Handler(){ 
	        @Override  
	        public void handleMessage(Message msg) {  
	        	if(0==msg.what){

//		    		 if(num==10)   TabControl.mViewSelected.imageviewClickRecover(button[9]);	
//					 else TabControl.mViewSelected.buttonClickRecover(button[num-1]);
		    		 
//		        	Toast.makeText(getApplicationContext(), getResources().getString(R.string.studysuccessful), Toast.LENGTH_SHORT).show(); 
	        		CustomToast.showToast(getApplicationContext(), getResources().getString(R.string.studysuccessful), Toast.LENGTH_SHORT); 
		        	TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, curButton,ioCtrlBuf);
		        	btnLearn[curButton-1]=true;	  
		        	if(curButton==10)   TabControl.mViewSelected.imageviewClickRecover(button[9]);	
					 else TabControl.mViewSelected.buttonClickRecover(button[curButton-1]);
		        	curButton=-1;
		        	//更新learnCursor
		        	Log.e("IR_Custom1", "updateBtnlearn");
		        	learnCursor.close();
		        	learnCursor=TabControl.mSQLHelper.seleteBtnLearn(TabControl.writeDB,devid);	
		        	}else{
//		        		Toast.makeText(getApplicationContext(), getResources().getString(R.string.studyfailed), Toast.LENGTH_SHORT).show();	
		        		CustomToast.showToast(getApplicationContext(), getResources().getString(R.string.studyfailed), Toast.LENGTH_SHORT);
		        	}	        
	            progressDialog.dismiss(); 
	        }};  
			 private void send(final int btnid){  
//				 progressDialog = ProgressDialog.show(IR_Custom1.this, "sending...", "Please wait...", true, false); 
				 new Thread(){        
				     @Override  
				     public void run() {  
//				    	 Message learnMsg=new Message();
				    	 String str=new String(learnCursor.getBlob(btnid+2));	
				    	 Log.e("IR_Custom1", ""+str);
				    	 
				    	 if(devType.equals("ir")){
				    	     TUTKClient.send(learnCursor.getBlob(btnid+2),true);
				    	 }else{
				    		 TUTKClient.send(learnCursor.getBlob(btnid+2),false);
				    	 }
//				    	 if(TUTKClient.send(learnCursor.getBlob(btnid+2),true))
//				    	 {
//				    		 learnMsg.what=0;
//				    	 }else{
//				    		 learnMsg.what=1;	
//				    	 }	 
//				    	 sendHandler.sendMessage(learnMsg);  
				     }}.start();      
				 }
				private Handler sendHandler = new Handler(){ 
			        @Override  
			        public void handleMessage(Message msg) {  
			        	if(0==msg.what){
//			        	Toast.makeText(getApplicationContext(), "send success", Toast.LENGTH_SHORT).show(); 
			        		CustomToast.showToast(getApplicationContext(), "send success", Toast.LENGTH_SHORT);
			        	}else{
//			        		Toast.makeText(getApplicationContext(), "send failed", Toast.LENGTH_SHORT).show();
			        		CustomToast.showToast(getApplicationContext(), "send failed", Toast.LENGTH_SHORT);
			        	}
			            progressDialog.dismiss(); 

			        }}; 
			 private void setbuttonstate()
				{				
					for(int i=0;i<(buttonMaxNum-1);i++){
						if(btnLearn[i])	TabControl.mViewSelected.buttonClickRecover(button[i]);
				    	else TabControl.mViewSelected.buttonClickGreyChanged(button[i]);
					}
					if(btnLearn[9])TabControl.mViewSelected.imageviewClickRecover(button[9]);
			    	else TabControl.mViewSelected.imageviewClickGreyChanged(button[9]);
				}
				
}

