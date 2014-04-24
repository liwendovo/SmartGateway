package com.seuic.smartgateway;

import java.util.Timer;
import java.util.TimerTask;

import com.seuic.adapter.CustomToast;
import com.seuic.adapter.DevChoiceAdapter;
import com.seuic.devetc.IR_AC;
import com.seuic.net.TUTKClient;
import com.seuic.smartgateway.R;
import android.app.Activity; 
import android.os.Bundle; 
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent; 
import android.view.View; 
import android.view.View.OnClickListener; 
import android.widget.LinearLayout; 
import android.widget.TabHost;
import android.widget.Toast; 
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;

public class TutkConnectActivity extends Activity {
	
	
	private LayoutInflater mInflater = null;
	private static TabHost host = null;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutk_connect); 
		LinearLayout layout = (LinearLayout)findViewById(R.id.exit_layout); 
		layout.setOnClickListener(new OnClickListener() { 
		@Override 
			public void onClick(View v) { 
			// TODO Auto-generated method stub 
			
			} 
		
		}); 
		
		Log.e("TUTUConnect","start delay thread ");
		
		new Thread(){        
		     @Override  
		     public void run() {  
		    	    
		    	 try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} 
		    	 
		    	    					    	 
		     }}.start();   
		
//		try
//		{
//		 Thread.currentThread();
//	  	 Thread.sleep(10000);//毫秒 
//		}
//		catch(Exception e){}   
		
		Log.e("TUTUConnect","end delay thread ");
		

		
		
		Timer timer=new Timer();//实例化Timer类
		timer.schedule(new TimerTask(){
		public void run(){
			Intent intent = new Intent(null, LoginProgressActivity.class);
	    	startActivity(intent);
		this.cancel();

    	finish();}},10000);//五百毫秒
		
		
	} 
		
	
//	 private Handler handler = new Handler();
//	    private Runnable runnable = new Runnable() {
//	        public void run() {
//	        	Intent intent = new Intent(null, LoginProgressActivity.class);
//		    	startActivity(intent);
//		    	finish();
//	            handler.postDelayed(this, 1000 * 5);// 间隔5秒
//	        }
//	       
//	    }; 
	    public void exitbutton0(View v) { 
	    	Log.e("TutkConnectActivity", "TUTKCONNECT-exitbutton0-finish");	
	    	finish();
	   } 
	    
	    public void exitbutton1(View v) { 
	    	Log.e("TutkConnectActivity", "TUTKCONNECT-exitbutton1-start");
	    	Intent intent = new Intent(this,LoginProgressActivity.class);
	    	startActivity(intent);
	    	finish();

		} 
	    
	    @Override  
	    protected void onStop() {  
	        super.onStop();  
	        Log.i("TutkConnectActivity", "onStop called.");     
	    }  
	    
	    protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			Log.e("TutkConnectActivity", "TUTKCONNECT---onDestroy");	
		}
		    
		 
		
} 
