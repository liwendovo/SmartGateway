package com.seuic.devetc;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
	ImageView  button1,button2,button3,
			button4,button5,button6,
			button7,button8,button9,
			button10,button11,button12,
			button13,button14;
	int devid;
	String mUid;
	String learnFalse="false";
	Boolean lenclr=false;
	Boolean btnclr1=false;
	Boolean btnclr2=false;
	Boolean btnclr3=false;
	Boolean btnclr4=false;
	Boolean btnclr5=false;
	Boolean btnclr6=false;
	Boolean btnclr7=false;
	Boolean btnclr8=false;
	Boolean btnclr9=false;
	Boolean btnclr10=false;
	Boolean btnclr11=false;
	Boolean btnclr12=false;
	Boolean btnclr13=false;
	Boolean btnclr14=false;
	 

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
		button1=(ImageView)findViewById(R.id.button1);
		button2=(ImageView)findViewById(R.id.button2);
		button3=(ImageView)findViewById(R.id.button3);		
		button4=(ImageView)findViewById(R.id.button4);		
		button5=(ImageView)findViewById(R.id.button5);
		button6=(ImageView)findViewById(R.id.button6);		
		button7=(ImageView)findViewById(R.id.button7);		
		button8=(ImageView)findViewById(R.id.button8);
		button9=(ImageView)findViewById(R.id.button9);
		button10=(ImageView)findViewById(R.id.button10);
		button11=(ImageView)findViewById(R.id.button11);
		button12=(ImageView)findViewById(R.id.button12);
		button13=(ImageView)findViewById(R.id.button13);
		button14=(ImageView)findViewById(R.id.button14);
		backBtn.setOnClickListener(this); 
		leanrnBtn.setOnClickListener(this); 
		button1.setOnClickListener(this);  
		button2.setOnClickListener(this);  
		button3.setOnClickListener(this);  
		button4.setOnClickListener(this);  
		button5.setOnClickListener(this);  
		button6.setOnClickListener(this);  
		button7.setOnClickListener(this);  
		button8.setOnClickListener(this);  
		button9.setOnClickListener(this);  
		button10.setOnClickListener(this);  
		button11.setOnClickListener(this);  
		button12.setOnClickListener(this);  
		button13.setOnClickListener(this);  
		button14.setOnClickListener(this);  
		

		TabControl.mViewSelected.setButtonFocusChanged(backBtn);
		TabControl.mViewSelected.setButtonFocusChanged(leanrnBtn);
		TabControl.mViewSelected.setImageViewFocusChanged(button1);
		TabControl.mViewSelected.setImageViewFocusChanged(button2);
		TabControl.mViewSelected.setImageViewFocusChanged(button3);
		TabControl.mViewSelected.setImageViewFocusChanged(button4);
		TabControl.mViewSelected.setImageViewFocusChanged(button5);
		TabControl.mViewSelected.setImageViewFocusChanged(button6);
		TabControl.mViewSelected.setImageViewFocusChanged(button7);
		TabControl.mViewSelected.setImageViewFocusChanged(button8);
		TabControl.mViewSelected.setImageViewFocusChanged(button9);
		TabControl.mViewSelected.setImageViewFocusChanged(button10);
		TabControl.mViewSelected.setImageViewFocusChanged(button11);
		TabControl.mViewSelected.setImageViewFocusChanged(button12);
		TabControl.mViewSelected.setImageViewFocusChanged(button13);
		TabControl.mViewSelected.setImageViewFocusChanged(button14);
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
			btnclr1=cursor.getString(1+2).equals("true")?true:false;
			btnclr2=cursor.getString(2+2).equals("true")?true:false;
			btnclr3=cursor.getString(3+2).equals("true")?true:false;
			btnclr4=cursor.getString(4+2).equals("true")?true:false;
			btnclr5=cursor.getString(5+2).equals("true")?true:false;
			btnclr6=cursor.getString(6+2).equals("true")?true:false;
			btnclr7=cursor.getString(7+2).equals("true")?true:false;
			btnclr8=cursor.getString(8+2).equals("true")?true:false;
			btnclr9=cursor.getString(9+2).equals("true")?true:false;	
			btnclr10=cursor.getString(1+2).equals("true")?true:false;
			btnclr11=cursor.getString(1+2).equals("true")?true:false;
			btnclr12=cursor.getString(1+2).equals("true")?true:false;
			btnclr13=cursor.getString(1+2).equals("true")?true:false;
			btnclr14=cursor.getString(1+2).equals("true")?true:false;
		}else{
			Log.e("leewoo", "cur learn 初始化"+cursor.getCount());
			//未初始化
			TabControl.mSQLHelper.insertBtn(TabControl.writeDB,mUid,devid,"learn" ,learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse);
		}
		cursor.close();
		setbuttonstate();
	}

	
	private void setbuttonstate()
	{
    	if(btnclr1)	TabControl.mViewSelected.imageviewClickRecover(button1);
    	else TabControl.mViewSelected.imageviewClickGreyChanged(button1);
    	
    	if(btnclr2)	TabControl.mViewSelected.imageviewClickRecover(button2);
    	else TabControl.mViewSelected.imageviewClickGreyChanged(button2);
    	
    	if(btnclr3)	TabControl.mViewSelected.imageviewClickRecover(button3);
    	else TabControl.mViewSelected.imageviewClickGreyChanged(button3);
    	
    	if(btnclr4)	TabControl.mViewSelected.imageviewClickRecover(button4);
    	else TabControl.mViewSelected.imageviewClickGreyChanged(button4);
    	
    	if(btnclr5)	TabControl.mViewSelected.imageviewClickRecover(button5);
    	else TabControl.mViewSelected.imageviewClickGreyChanged(button5);
    	
    	if(btnclr6)	TabControl.mViewSelected.imageviewClickRecover(button6);
    	else TabControl.mViewSelected.imageviewClickGreyChanged(button6);
    	
    	if(btnclr7)	TabControl.mViewSelected.imageviewClickRecover(button7);
    	else TabControl.mViewSelected.imageviewClickGreyChanged(button7);
    	
    	if(btnclr8)	TabControl.mViewSelected.imageviewClickRecover(button8);
    	else TabControl.mViewSelected.imageviewClickGreyChanged(button8);
    	
    	if(btnclr9)	TabControl.mViewSelected.imageviewClickRecover(button9);
    	else TabControl.mViewSelected.imageviewClickGreyChanged(button9);
    	
    	if(btnclr10) TabControl.mViewSelected.imageviewClickRecover(button10);
    	else TabControl.mViewSelected.imageviewClickGreyChanged(button10);
    	
    	if(btnclr11)	TabControl.mViewSelected.imageviewClickRecover(button11);
    	else TabControl.mViewSelected.imageviewClickGreyChanged(button11);
    	
    	if(btnclr12)	TabControl.mViewSelected.imageviewClickRecover(button12);
    	else TabControl.mViewSelected.imageviewClickGreyChanged(button12);
    	
    	if(btnclr13)	TabControl.mViewSelected.imageviewClickRecover(button13);
    	else TabControl.mViewSelected.imageviewClickGreyChanged(button13);
    	
    	if(btnclr14)	TabControl.mViewSelected.imageviewClickRecover(button14);
    	else TabControl.mViewSelected.imageviewClickGreyChanged(button14);
    	
    
    	
    	    	
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
	            	TabControl.mViewSelected.imageviewClickLearn(button1);
	            	TabControl.mViewSelected.imageviewClickLearn(button2);
	            	TabControl.mViewSelected.imageviewClickLearn(button3);
	            	TabControl.mViewSelected.imageviewClickLearn(button4);
	            	TabControl.mViewSelected.imageviewClickLearn(button5);
	            	TabControl.mViewSelected.imageviewClickLearn(button6);
	            	TabControl.mViewSelected.imageviewClickLearn(button7);
	            	TabControl.mViewSelected.imageviewClickLearn(button8);
	            	TabControl.mViewSelected.imageviewClickLearn(button9);
	            	TabControl.mViewSelected.imageviewClickLearn(button10);
	            	TabControl.mViewSelected.imageviewClickLearn(button11);
	            	TabControl.mViewSelected.imageviewClickLearn(button12);
	            	TabControl.mViewSelected.imageviewClickLearn(button13);
	            	TabControl.mViewSelected.imageviewClickLearn(button14);
	        	}else{
	        		TabControl.mViewSelected.buttonClickRecover(leanrnBtn);
	        		
	        		setbuttonstate();    
	        	}
	        	break;
        	 case R.id.button1:
             	
             	if(lenclr==true){
     	        	
             		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
             		btnclr1=true;

             		TabControl.mViewSelected.imageviewClickRecover(button1);

             		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, 1, true);        		

             		Log.e("btnclr1=",""+btnclr1);
     	        	
     	        }
             	
             	break;
             case R.id.button2:
             	
             	if(lenclr==true){
     	        	
             		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
             		btnclr2=true;

             		TabControl.mViewSelected.imageviewClickRecover(button2);

             		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid,2, true);

             		Log.e("btnclr2=",""+btnclr2);
     	        	
             	}
             	
             	break;
             case R.id.button3:

             	if(lenclr==true){
     	        
             		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
             		btnclr3=true;

             		TabControl.mViewSelected.imageviewClickRecover(button3);

//             		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, 3, true);

             		Log.e("btnclr3=",""+btnclr3);
             		
             	}
             	
             	break;
             
             case R.id.button4:
             	if(lenclr==true){
     	        	
     	        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
     	        		btnclr4=true;

     	        		TabControl.mViewSelected.imageviewClickRecover(button4);

     	        		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, 4, true);
     	        		

     	        		Log.e("btnclr4=",""+btnclr4);
     	        	
             	}
             	break;
             case R.id.button5:
             	if(lenclr==true){
     	         
     	        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
     	        		btnclr5=true;

     	        		TabControl.mViewSelected.imageviewClickRecover(button5);

     	        		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid,5, true);

     	        		Log.e("btnclr5=",""+btnclr5);
     	        	
             	}
             	break;
             case R.id.button6:
             	if(lenclr==true){
     	        	 
     	        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
     	        		btnclr6=true;

     	        		TabControl.mViewSelected.imageviewClickRecover(button6);

     	        		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, 6, true);

     	        		Log.e("btnclr6=",""+btnclr6);
     	        	
             	}
             	break;
             case R.id.button7:
             	if(lenclr==true){
     	        	  
     	        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
     	        		btnclr7=true;
     	        		TabControl.mViewSelected.imageviewClickRecover(button7);
     	        		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, 7, true);
     	        		Log.e("btnclr7=",""+btnclr7);
     	        	
             	}
             	break;
             case R.id.button8:
             	if(lenclr==true){
     	        	 
     	        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
     	        		btnclr8=true;
     	        		TabControl.mViewSelected.imageviewClickRecover(button8);
     	        		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, 8, true);
     	        		Log.e("btnclr8=",""+btnclr8);     	        	
             	}
             	break;
             case R.id.button9:
             	if(lenclr==true){     	        	 
     	        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
     	        		btnclr9=true;
     	        		TabControl.mViewSelected.imageviewClickRecover(button9);
     	        		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, 9, true);
     	        		Log.e("btnclr9=",""+btnclr9);
     	        	   }
             	break;    
             	
             case R.id.button10:
             	if(lenclr==true){     	        	  
     	        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
     	        		btnclr10=true;
     	        		TabControl.mViewSelected.imageviewClickRecover(button10);
     	        		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, 7, true);
     	        		Log.e("btnclr10=",""+btnclr10);     	        	
             	}
             	break;
             	
             	 case R.id.button11:                 	
                 	if(lenclr==true){         	        	
                 		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
                 		btnclr11=true;
                 		TabControl.mViewSelected.imageviewClickRecover(button11);
                 		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, 1, true);  
                 		Log.e("btnclr11=",""+btnclr11);
         	        	
         	        }
                 	
                 	break;
                 case R.id.button12:                 	
                 	if(lenclr==true){         	        	
                 		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
                 		btnclr12=true;
                 		TabControl.mViewSelected.imageviewClickRecover(button12);
                 		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid,2, true);
                 		Log.e("btnclr12=",""+btnclr12);         	        	
                 	}
                 	
                 	break;
                 case R.id.button13:
                 	if(lenclr==true){         	        
                 		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
                 		btnclr13=true;
                 		TabControl.mViewSelected.imageviewClickRecover(button13);
                 		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, 3, true);
                 		Log.e("btnclr13=",""+btnclr13);
                 		
                 	}                 	
                 	break;                 
                 case R.id.button14:
                 	if(lenclr==true){         	        	
         	        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
         	        		btnclr14=true;
         	        		TabControl.mViewSelected.imageviewClickRecover(button14);
         	        		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, 4, true);         	        		
         	        		Log.e("btnclr14=",""+btnclr14);         	        	
                 	}
                 	break;                
        default:  
        	Log.e("leewoo", "Button id =default " ); 
            break;  
        }
	  
	}

}
