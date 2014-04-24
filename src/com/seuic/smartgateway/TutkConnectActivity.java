package com.seuic.smartgateway;

import java.util.Timer;
import java.util.TimerTask;

import com.seuic.smartgateway.R;
import android.app.Activity; 
import android.os.Bundle; 
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View; 
import android.view.View.OnClickListener; 
import android.widget.LinearLayout; 
import android.content.Intent;

public class TutkConnectActivity extends Activity {
	
	
	public TutkConnectActivity instance = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutk_connect); 
		LinearLayout layout = (LinearLayout)findViewById(R.id.exit_layout); 
		instance = this;
		layout.setOnClickListener(new OnClickListener() { 
		@Override 
			public void onClick(View v) { 
			// TODO Auto-generated method stub 
			
			} 
		
		}); 
		
		
		final Handler sendHandler = new Handler(){ 
	        @Override  
	        public void handleMessage(Message msg) {  
	        	if(0xfff1==msg.what){
	        		Intent intent = new Intent(instance, LoginProgressActivity.class);
	    	    	startActivity(intent);
	    	    	finish(); 		        
	        	}
	        }}; 
		
		
		Timer timer=new Timer();//实例化Timer类
		timer.schedule(new TimerTask(){
		public void run(){
			Message learnMsg=new Message();
	    	learnMsg.what=0xfff1;	
	    	sendHandler.sendMessage(learnMsg);  
		    this.cancel();}},60000);//五百毫秒
		
	    } 
	

	    public void exitbutton0(View v) { 
	    	Log.e("TutkConnectActivity", "TUTKCONNECT-exitbutton0-finish");	
	    	TabControl.mUid="NULL";
	    	finish();
	   } 
	    
	    public void exitbutton1(View v) { 
	    	Log.e("TutkConnectActivity", "TUTKCONNECT-exitbutton1-start");
	    	Intent intent = new Intent(this,LoginProgressActivity.class);
	    	startActivity(intent);
	    	finish();

		} 
	    
	   		
} 
