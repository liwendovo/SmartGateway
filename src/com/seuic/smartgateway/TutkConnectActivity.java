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
	private Timer timer;

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
		        	if(0xf0f1ff00==msg.what){
		        		Intent intent = new Intent(instance, LoginProgressActivity.class);
		    	    	startActivity(intent);
		    	    	finish(); 		        
		        	}
		        }}; 
			
			
			timer=new Timer();// µ¿˝ªØTimer¿‡
			timer.schedule(new TimerTask(){
			public void run(){
				Message learnMsg=new Message();
		    	learnMsg.what=0xf0f1ff00;	
		    	sendHandler.sendMessage(learnMsg);  
		    	Log.e("TutkConnect","Timer start");
			    this.cancel();}},60000);//60√Î
		
	 } 
	

    public void exitbutton0(View v) { 
    	Log.e("TutkConnectActivity", "TUTKCONNECT-exitbutton0-finish");	
    	TabControl.mUid="NULL";
    	timer.cancel();
    	finish();
    } 
    
    public void exitbutton1(View v) { 
    	Log.e("TutkConnectActivity", "TUTKCONNECT-exitbutton1-start");
    	Intent intent = new Intent(this,LoginProgressActivity.class);
    	startActivity(intent);
    	timer.cancel();
    	finish();

	} 
	    
	   		
} 
