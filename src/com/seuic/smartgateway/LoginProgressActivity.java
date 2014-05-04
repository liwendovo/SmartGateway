package com.seuic.smartgateway;

import com.seuic.adapter.DevChoiceAdapter;
import com.seuic.function.CustomToast;
import com.seuic.net.TUTKClient;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class LoginProgressActivity extends Activity {
	
	
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_progress);
		showProgressDialog(TabControl.mUid);
	}
	

	private void showProgressDialog(final String uid){  
		 progressDialog = ProgressDialog.show(this,"" , "Connecting...", true, false); 
		 new Thread(){        
		     @Override  
		     public void run() {  
		    Message startMsg=new Message();
//		       TUTKClient.stop();
			   if(TUTKClient.start(uid))				 
			   {
				   startMsg.what=0;
			   }else{
				   startMsg.what=1;
			   }			   
		       handler.sendMessage(startMsg);  
		     }
		   }.start();      
	 }
	 
	 private Handler handler = new Handler(){ 
	        @Override  
	        public void handleMessage(Message msg) {  
	        	if(0==msg.what)
	        	{
	        		CustomToast.showToast(getApplicationContext(), "Connect success", Toast.LENGTH_LONG); 
	        		Cursor cursor=TabControl.mSQLHelper.seleteSetup(TabControl.writeDB,TabControl.mUid);
//	        		Log.e("leewoo", "Tabset---onStart->cur:"+cursor.getCount()+" mUid:"+TabControl.mUid);
	        		if(cursor.getCount()>0){
	        			int fah=cursor.getInt(3);
	        			int hour=cursor.getInt(4);   
	        			int timezone=cursor.getInt(5);  
	        			Log.e("TabIR ", "fah="+ fah);
	        			TUTKClient.setTempMode(fah);
	        			TUTKClient.setHourMode(hour);
	        			String a[]=getApplicationContext().getResources().getStringArray(R.array.timezone_entries);   
	        			String[] ss=new String[2];
	                	ss=a[timezone].split("UTC"); 
	                	ss[1]=ss[1].replace("+",""); 
	                	float i=4*Float.parseFloat(ss[1]);  
	                	Log.e("Device ", "timezone length="+ timezone);
	                	TUTKClient.setTimeZone((int)i);
	        		}	
	        		
	        	}else{
	        		CustomToast.showToast(getApplicationContext(), "Can not connect to device, please check your device or if has connect to a wireless network", Toast.LENGTH_LONG); 
	        		DevChoiceAdapter.currentID=-1;
	        		TabControl.editor.putString("uid","NULL");
	        		TabControl.editor.commit();
					TabControl.mUid="NULL";//Œ¥¡¨Ω”÷√ø’
//	        		notifyDataSetChanged();
	        	}
//	            //πÿ±’ProgressDialog  
	            progressDialog.dismiss(); 
	            finish();

	        }};  

}
