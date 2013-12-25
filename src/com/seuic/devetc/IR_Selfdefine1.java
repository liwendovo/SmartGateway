
package com.seuic.devetc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seuic.smartgateway.R;

public class IR_Selfdefine1 extends Activity implements android.view.View.OnClickListener,OnLongClickListener {
	Button  backBtn,leanrnBtn;
	Button  button1,button2,button3,
			button4,button5,button6,
			button7,button8,button9;
	static Boolean lenclr=false;
	static Boolean btnclr1=false;
	static Boolean btnclr2=false;
	static Boolean btnclr3=false;
	static Boolean btnclr4=false;
	static Boolean btnclr5=false;
	static Boolean btnclr6=false;
	static Boolean btnclr7=false;
	static Boolean btnclr8=false;
	static Boolean btnclr9=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ir_selfdefine1);
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
//保存名字	
//		SharedPreferences sharedPreferences = getSharedPreferences("itcast", Context.MODE_PRIVATE);
//		Editor editor = sharedPreferences.edit();//获取编辑器
//		editor.putString("name", "传智播客");
//		editor.putInt("age", 4);
//		editor.commit();
//		SharedPreferences sharedPreferences = getSharedPreferences("itcast", Context.MODE_PRIVATE);
//		//getString()第二个参数为缺省值，如果preference中不存在该key，将返回缺省值
//		String name = sharedPreferences.getString("name", "");
//		int age = sharedPreferences.getInt("age", 1);
	}
	
	@Override
	public void onClick(View v) {
		Toast toast = null;

		// TODO Auto-generated method stub
//		Log.e("leewoo", "Button id = " + v.getId());  
		switch(v.getId())  
        {  
        case R.id.back: finish();break;
        case R.id.titleBtn:
        	lenclr=!lenclr;
        	Log.e("lenclr=",""+lenclr);
        	if(lenclr==true)
        		{leanrnBtn.setBackgroundColor(Color.GRAY);
            		Log.e("leewoo", "clr"+v.getId() ); 
        		        	       	
        	if(btnclr1==false)
               	button1.setBackgroundColor(Color.GRAY);
        	else button1.setBackgroundColor(Color.YELLOW);
        	if(btnclr2==false)
               	button2.setBackgroundColor(Color.GRAY);
        	else button2.setBackgroundColor(Color.YELLOW);
        	if(btnclr3==false)
               	button3.setBackgroundColor(Color.GRAY);
        	else button3.setBackgroundColor(Color.YELLOW);
        	if(btnclr4==false)
               	button4.setBackgroundColor(Color.GRAY);
        	else button4.setBackgroundColor(Color.YELLOW);
        	if(btnclr5==false)
               	button5.setBackgroundColor(Color.GRAY);
        	else button5.setBackgroundColor(Color.YELLOW);
        	if(btnclr6==false)
               	button6.setBackgroundColor(Color.GRAY);
        	else button6.setBackgroundColor(Color.YELLOW);
        	if(btnclr7==false)
               	button7.setBackgroundColor(Color.GRAY);
        	else button7.setBackgroundColor(Color.YELLOW);
        	if(btnclr8==false)
               	button8.setBackgroundColor(Color.GRAY);
        	else button8.setBackgroundColor(Color.YELLOW);
        	if(btnclr9==false)
               	button9.setBackgroundColor(Color.GRAY);
        	else button9.setBackgroundColor(Color.YELLOW);}
        	
        	else {
        		leanrnBtn.setBackgroundColor(Color.YELLOW);
        		button1.setBackgroundColor(Color.YELLOW);
        		button2.setBackgroundColor(Color.YELLOW);
        		button3.setBackgroundColor(Color.YELLOW);
        		button4.setBackgroundColor(Color.YELLOW);
        		button5.setBackgroundColor(Color.YELLOW);
        		button6.setBackgroundColor(Color.YELLOW);
        		button7.setBackgroundColor(Color.YELLOW);
        		button8.setBackgroundColor(Color.YELLOW);
        		button9.setBackgroundColor(Color.YELLOW);
        		        	}
        	break;
//        	if(btnclr1=false)
//               	button1.setBackgroundColor(Color.GRAY);
//        	else button1.setBackgroundColor(Color.GRAY);
//        	if(btnclr1=false)
//               	button1.setBackgroundColor(Color.GRAY);
//        	else button1.setBackgroundColor(Color.GRAY);
//        	if(btnclr1=false)
//               	button1.setBackgroundColor(Color.GRAY);
//        	else button1.setBackgroundColor(Color.GRAY);
//        	if(btnclr1=false)
//               	button1.setBackgroundColor(Color.GRAY);
//        	else button1.setBackgroundColor(Color.GRAY);
//        	if(btnclr1=false)
//               	button1.setBackgroundColor(Color.GRAY);
//        	else button1.setBackgroundColor(Color.GRAY);
//        	if(btnclr1=false)
//               	button1.setBackgroundColor(Color.GRAY);
//        	else button1.setBackgroundColor(Color.GRAY);
      	
        	
//        	button2.setBackgroundColor(Color.GRAY);
//        	button3.setBackgroundColor(Color.GRAY);    
//        	button4.setBackgroundColor(Color.GRAY);
//        	button5.setBackgroundColor(Color.GRAY);
//        	button6.setBackgroundColor(Color.GRAY); 
//        	button7.setBackgroundColor(Color.GRAY);
//        	button8.setBackgroundColor(Color.GRAY);
//        	button9.setBackgroundColor(Color.GRAY); 
        	
        	
        case R.id.button1:
        	if(lenclr==true){
        		
        	
        	if(btnclr1==false)
        	{   toast = Toast.makeText(getApplicationContext(),
        		     "学习成功", Toast.LENGTH_SHORT);
        	   toast.setGravity(Gravity.CENTER, 0, 0);
        	   toast.show();

        		btnclr1=!btnclr1;
        		button1.setBackgroundColor(Color.YELLOW);
        		Log.e("btnclr1=",""+btnclr1);
        	}
        }
        	break;
        case R.id.button2:
        	if(lenclr==true){
        	if(btnclr2==false)
        	{toast = Toast.makeText(getApplicationContext(),
       		     "学习成功", Toast.LENGTH_SHORT);
       	   toast.setGravity(Gravity.CENTER, 0, 0);
       	   toast.show();
        		btnclr2=!btnclr2;
        		button2.setBackgroundColor(Color.YELLOW);
        		Log.e("btnclr2=",""+btnclr2);
        	}
        	}
        	break;
        case R.id.button3:
        	if(lenclr==true){
        	if(btnclr3==false)
        	{toast = Toast.makeText(getApplicationContext(),
       		     "学习成功", Toast.LENGTH_SHORT);
       	   toast.setGravity(Gravity.CENTER, 0, 0);
       	   toast.show();
        		btnclr3=!btnclr3;
        		button3.setBackgroundColor(Color.YELLOW);
        		Log.e("btnclr3=",""+btnclr3);
        	}
        	}
        	
        	break;
        
        case R.id.button4:
        	if(lenclr==true){
        	if(btnclr4==false)
        	{toast = Toast.makeText(getApplicationContext(),
       		     "学习成功", Toast.LENGTH_SHORT);
       	   toast.setGravity(Gravity.CENTER, 0, 0);
       	   toast.show();
        		btnclr4=!btnclr4;
        		button4.setBackgroundColor(Color.YELLOW);
        		Log.e("btnclr4=",""+btnclr4);
        	}
        	}
        	break;
        case R.id.button5:
        	if(lenclr==true){
        	if(btnclr5==false)
        	{    toast = Toast.makeText(getApplicationContext(),
       		     "学习成功", Toast.LENGTH_SHORT);
       	   toast.setGravity(Gravity.CENTER, 0, 0);
       	   toast.show();
        		btnclr5=!btnclr5;
        		button5.setBackgroundColor(Color.YELLOW);
        		Log.e("btnclr5=",""+btnclr5);
        	}
        	}
        	break;
        case R.id.button6:
        	if(lenclr==true){
        	if(btnclr6==false)
        	{  toast = Toast.makeText(getApplicationContext(),
       		     "学习成功", Toast.LENGTH_SHORT);
       	   toast.setGravity(Gravity.CENTER, 0, 0);
       	   toast.show();
        		btnclr6=!btnclr6;
        		button6.setBackgroundColor(Color.YELLOW);
        		Log.e("btnclr6=",""+btnclr6);
        	}
        	}
        	break;
        case R.id.button7:
        	if(lenclr==true){
        	if(btnclr7==false)
        	{  toast = Toast.makeText(getApplicationContext(),
       		     "学习成功", Toast.LENGTH_SHORT);
       	   toast.setGravity(Gravity.CENTER, 0, 0);
       	   toast.show();
        		btnclr7=!btnclr7;
        		button7.setBackgroundColor(Color.YELLOW);
        		Log.e("btnclr7=",""+btnclr7);
        	}
        	}
        	break;
        case R.id.button8:
        	if(lenclr==true){
        	if(btnclr8==false)
        	{  toast = Toast.makeText(getApplicationContext(),
       		     "学习成功", Toast.LENGTH_SHORT);
       	   toast.setGravity(Gravity.CENTER, 0, 0);
       	   toast.show();
        		btnclr8=!btnclr8;
        		button8.setBackgroundColor(Color.YELLOW);
        		Log.e("btnclr8=",""+btnclr8);
        	}
        	}
        	break;
        case R.id.button9:
        	if(lenclr==true){
        	if(btnclr9==false)
        	{  toast = Toast.makeText(getApplicationContext(),
       		     "学习成功", Toast.LENGTH_SHORT);
       	   toast.setGravity(Gravity.CENTER, 0, 0);
       	   toast.show();
        		btnclr9=!btnclr9;
        		button9.setBackgroundColor(Color.YELLOW);
        		Log.e("btnclr9=",""+btnclr9);
        	}
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
		 AlertDialog.Builder builder = new Builder(IR_Selfdefine1.this);
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

//	 public void drawAhade(Canvas canvas) {
//
//			Paint shadepaint = new Paint();
//			shadepaint.setARGB(170, 123, 125, 127);
//			canvas.drawRect(0, 50, 200, 300, shadepaint);
//
//		}
}

