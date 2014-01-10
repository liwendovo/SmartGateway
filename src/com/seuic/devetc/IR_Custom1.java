
package com.seuic.devetc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
	Button  button1,button2,button3,
			button4,button5,button6,
			button7,button8,button9;
	int devid;
	String learnFalse="false";
	String learnTrue="true";
	String btnDefaults="自定义";
	String mUid;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ir_custom1);
		
		Log.e("leewoo", "in IR_Custom1 ");
		
		
		backBtn=(Button)findViewById(R.id.back);
		leanrnBtn=(Button)findViewById(R.id.titleBtn);
		button1=(Button)findViewById(R.id.button1);
		button2=(Button)findViewById(R.id.button2);
		button3=(Button)findViewById(R.id.button3);		
		button4=(Button)findViewById(R.id.button4);		
		button5=(Button)findViewById(R.id.button5);
		button6=(Button)findViewById(R.id.button6);		
		button7=(Button)findViewById(R.id.button7);		
		button8=(Button)findViewById(R.id.button8);
		button9=(Button)findViewById(R.id.button9);
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
		button1.setOnLongClickListener(this);
		button2.setOnLongClickListener(this);
		button3.setOnLongClickListener(this);
		button4.setOnLongClickListener(this);
		button5.setOnLongClickListener(this);
		button6.setOnLongClickListener(this);
		button7.setOnLongClickListener(this);
		button8.setOnLongClickListener(this);
		button9.setOnLongClickListener(this);
		TabControl.mViewSelected.setButtonFocusChanged(backBtn);
		TabControl.mViewSelected.setButtonFocusChanged(leanrnBtn);
		TabControl.mViewSelected.setButtonFocusChanged(button1);
		TabControl.mViewSelected.setButtonFocusChanged(button2);
		TabControl.mViewSelected.setButtonFocusChanged(button3);
		TabControl.mViewSelected.setButtonFocusChanged(button4);
		TabControl.mViewSelected.setButtonFocusChanged(button5);
		TabControl.mViewSelected.setButtonFocusChanged(button6);
		TabControl.mViewSelected.setButtonFocusChanged(button7);
		TabControl.mViewSelected.setButtonFocusChanged(button8);
		TabControl.mViewSelected.setButtonFocusChanged(button9);
		
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
			btnclr1=cursor.getString(1+2).equals("true")?true:false;
			btnclr2=cursor.getString(2+2).equals("true")?true:false;
			btnclr3=cursor.getString(3+2).equals("true")?true:false;
			btnclr4=cursor.getString(4+2).equals("true")?true:false;
			btnclr5=cursor.getString(5+2).equals("true")?true:false;
			btnclr6=cursor.getString(6+2).equals("true")?true:false;
			btnclr7=cursor.getString(7+2).equals("true")?true:false;
			btnclr8=cursor.getString(8+2).equals("true")?true:false;
			btnclr9=cursor.getString(9+2).equals("true")?true:false;	
			
		}else{
			Log.e("leewoo", "cur learn 初始化"+cursor.getCount());
			//未初始化
			TabControl.mSQLHelper.insertBtn(TabControl.writeDB,mUid,devid,"learn" ,learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse, learnFalse);
		}
		cursor.close();
		
		cursor=TabControl.mSQLHelper.seleteBtnName(TabControl.writeDB,devid);
		Log.e("leewoo", "cur: "+cursor.getCount());
		if(cursor.getCount()>0){
			//已初始化		
				button1.setText(cursor.getString(1+2));
				button2.setText(cursor.getString(2+2));
				button3.setText(cursor.getString(3+2));
				button4.setText(cursor.getString(4+2));
				button5.setText(cursor.getString(5+2));
				button6.setText(cursor.getString(6+2));			
				button7.setText(cursor.getString(7+2));
				button8.setText(cursor.getString(8+2));
				button9.setText(cursor.getString(9+2));
		}else{
			Log.e("leewoo", "cur name 初始化"+cursor.getCount());
			//未初始化
			TabControl.mSQLHelper.insertBtn(TabControl.writeDB,mUid,devid, "name" ,btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults+14);
		}		
		cursor.close();
	    setbuttonstate();
				
		
	}
	
	public void setbuttonstate()
	{
    	if(btnclr1)	TabControl.mViewSelected.buttonclickrecover(button1);
    	else TabControl.mViewSelected.buttonclickgreychanged(button1);
    	
    	if(btnclr2)	TabControl.mViewSelected.buttonclickrecover(button2);
    	else TabControl.mViewSelected.buttonclickgreychanged(button2);
    	
    	if(btnclr3)	TabControl.mViewSelected.buttonclickrecover(button3);
    	else TabControl.mViewSelected.buttonclickgreychanged(button3);
    	
    	if(btnclr4)	TabControl.mViewSelected.buttonclickrecover(button4);
    	else TabControl.mViewSelected.buttonclickgreychanged(button4);
    	
    	if(btnclr5)	TabControl.mViewSelected.buttonclickrecover(button5);
    	else TabControl.mViewSelected.buttonclickgreychanged(button5);
    	
    	if(btnclr6)	TabControl.mViewSelected.buttonclickrecover(button6);
    	else TabControl.mViewSelected.buttonclickgreychanged(button6);
    	
    	if(btnclr7)	TabControl.mViewSelected.buttonclickrecover(button7);
    	else TabControl.mViewSelected.buttonclickgreychanged(button7);
    	
    	if(btnclr8)	TabControl.mViewSelected.buttonclickrecover(button8);
    	else TabControl.mViewSelected.buttonclickgreychanged(button8);
    	
    	if(btnclr9)	TabControl.mViewSelected.buttonclickrecover(button9);
    	else TabControl.mViewSelected.buttonclickgreychanged(button9);
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
            	TabControl.mViewSelected.buttonclicklearn(leanrnBtn);                     	
            	TabControl.mViewSelected.buttonclicklearn(button1);
            	TabControl.mViewSelected.buttonclicklearn(button2);
            	TabControl.mViewSelected.buttonclicklearn(button3);
            	TabControl.mViewSelected.buttonclicklearn(button4);
            	TabControl.mViewSelected.buttonclicklearn(button5);
            	TabControl.mViewSelected.buttonclicklearn(button6);
            	TabControl.mViewSelected.buttonclicklearn(button7);
            	TabControl.mViewSelected.buttonclicklearn(button8);
            	TabControl.mViewSelected.buttonclicklearn(button9);
        	}else{
        		TabControl.mViewSelected.buttonclickrecover(leanrnBtn);        		
        		setbuttonstate();    
        	}
        	break;
  	    case R.id.button1:        	
        	if(lenclr==true){	        	
        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
        		btnclr1=true;
        		TabControl.mViewSelected.buttonclickrecover(button1);
        		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, 1, true);  
        		Log.e("btnclr1=",""+btnclr1);	        	
	        }        	
        	break;
        case R.id.button2:        	
        	if(lenclr==true){	        	
        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
        		btnclr2=true;
        		TabControl.mViewSelected.buttonclickrecover(button2);
        		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid,2, true);
        		Log.e("btnclr2=",""+btnclr2);	        	
        	}        	
        	break;
        case R.id.button3:
        	if(lenclr==true){	        
        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
        		btnclr3=true;
        		TabControl.mViewSelected.buttonclickrecover(button3);
        		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, 3, true);
        		Log.e("btnclr3=",""+btnclr3);        		
        	}        	
        	break;        
        case R.id.button4:
        	if(lenclr==true){	        	
	        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
	        		btnclr4=true;
	        		TabControl.mViewSelected.buttonclickrecover(button4);
	        		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, 4, true);  
	        		Log.e("btnclr4=",""+btnclr4);	        	
        	}
        	break;
        case R.id.button5:
        	if(lenclr==true){	         
	        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
	        		btnclr5=true;
	        		TabControl.mViewSelected.buttonclickrecover(button5);
	        		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid,5, true);
	        		Log.e("btnclr5=",""+btnclr5);	        	
        	}
        	break;
        case R.id.button6:
        	if(lenclr==true){	        	 
	        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
	        		btnclr6=true;
	        		TabControl.mViewSelected.buttonclickrecover(button6);
	        		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, 6, true);
	        		Log.e("btnclr6=",""+btnclr6);
        	}
        	break;
        case R.id.button7:
        	if(lenclr==true){	        	  
	        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
	        		btnclr7=true;
	        		TabControl.mViewSelected.buttonclickrecover(button7);
	        		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, 7, true);
	        		Log.e("btnclr7=",""+btnclr7);	        	
        	}
        	break;
        case R.id.button8:
        	if(lenclr==true){	        	 
	        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
	        		btnclr8=true;
	        		TabControl.mViewSelected.buttonclickrecover(button8);
	        		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, 8, true);
	        		Log.e("btnclr8=",""+btnclr8);	        	
        	}
        	break;
        case R.id.button9:
        	if(lenclr==true){	        	 
	        		Toast.makeText(getApplicationContext(), "学习成功", Toast.LENGTH_SHORT).show(); 
	        		btnclr9=true;
	        		TabControl.mViewSelected.buttonclickrecover(button9);
	        		TabControl.mSQLHelper.updateBtnlearn(TabControl.writeDB, devid, 9, true);
	        		Log.e("btnclr9=",""+btnclr9);
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
		 builder.setView(et);
		 builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub			
			switch (btnid) {
			case 1:
				button1.setText(et.getText().toString());
				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 1, et.getText().toString());
				break;
			case 2:
				button2.setText(et.getText().toString());
				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 2, et.getText().toString());
				break;
			case 3:
				button3.setText(et.getText().toString());
				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 3, et.getText().toString());
				break;
			case 4:
				button4.setText(et.getText().toString());
				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 4, et.getText().toString());
				break;
			case 5:
				button5.setText(et.getText().toString());
				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 5, et.getText().toString());
				break;
			case 6:
				button6.setText(et.getText().toString());
				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 6, et.getText().toString());
				break;
			case 7:
				button1.setText(et.getText().toString());
				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 7, et.getText().toString());
				break;				
			case 8:
				button8.setText(et.getText().toString());
				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 8, et.getText().toString());
				break;
			case 9:
				button9.setText(et.getText().toString());
				TabControl.mSQLHelper.updateBtnName(TabControl.writeDB, devid, 9, et.getText().toString());
				break;
			default:
				break;
			}
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

