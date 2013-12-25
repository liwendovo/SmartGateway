package com.seuic.devetc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.seuic.smartgateway.R;

public class RF_Selfdefine1 extends Activity implements android.view.View.OnClickListener,OnLongClickListener{
	Button  backBtn,leanrnBtn;
	Button  button1,button2,button3,
			button4,button5,button6,
			button7,button8,button9;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.rf_selfdefine1);
//		SharedPreferences sharedPreferences = getSharedPreferences("itcast", Context.MODE_PRIVATE);
//		Editor editor = sharedPreferences.edit();//获取编辑器
//		editor.putString("name", "传智播客");
//		editor.putInt("age", 4);
//		editor.commit();
//		SharedPreferences sharedPreferences = getSharedPreferences("itcast", Context.MODE_PRIVATE);
//		//getString()第二个参数为缺省值，如果preference中不存在该key，将返回缺省值
//		String name = sharedPreferences.getString("name", "");
//		int age = sharedPreferences.getInt("age", 1);
		
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
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		Log.e("leewoo", "Button id = " + v.getId());  
		switch(v.getId())  
        {  
        case R.id.back: finish();break;
        default:  
            break;  
        }  
        
	}

	
	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
//		 dialog();
//	 	 Log.e("leewoo", "onLongClick");
//		 Log.e("leewoo", "Button id = " + v.getId());  
	        switch(v.getId())  
	        {  
	        case R.id.button1:  
	        	Log.e("leewoo", "Button1 onLongClick");
	        	 dialog();
	            break;  
	        case R.id.button2:  
	        	Log.e("leewoo", "button2 onLongClick");
	        	dialog();
	            break;  
	        case R.id.button3:  
	        	Log.e("leewoo", "button3 onLongClick");
	        	dialog();
	            break;  
	        case R.id.button4:  
	        	Log.e("leewoo", "button4 onLongClick");
	        	dialog();
	            break;  
	        case R.id.button5:  
	        	Log.e("leewoo", "button5 onLongClick");
	        	dialog();
	            break;  
	        case R.id.button6:  
	        	Log.e("leewoo", "button6 onLongClick");
	        	dialog();
	            break;  
	        case R.id.button7:  
	        	Log.e("leewoo", "button7 onLongClick");
	        	dialog();
	            break;  
	        case R.id.button8:  
	        	Log.e("leewoo", "button7 onLongClick");
	        	dialog();
	            break;  
	        case R.id.button9:  
	        	Log.e("leewoo", "button7 onLongClick");
	        	dialog();
	            break;  
	        default:  
	            break;  
	        }  
	        
		return false;
	}

	 protected void dialog() {
		 AlertDialog.Builder builder = new Builder(RF_Selfdefine1.this);
		 builder.setMessage("Please input name");
		 builder.setTitle("Button name");
		 builder.setView(new EditText(this));
		 builder.setPositiveButton("Confirm", new OnClickListener() {
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
		});
		  builder.setNegativeButton("Cancle", new OnClickListener() {
		@Override
		 public void onClick(DialogInterface dialog, int which) {
		 dialog.dismiss();
		  }
		 });
		 builder.create().show();
	   }

}
