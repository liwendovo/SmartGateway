package com.seuic.smartgateway;

import com.seuic.adapter.CustomToast;
import com.seuic.adapter.DevChoiceAdapter;
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
//			Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！", 
//			Toast.LENGTH_SHORT).show(); 
			} 
		}); 
	} 
		
		    public void exitbutton0(View v) { 
//			   Intent intent = new Intent();
//        	   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
//        	   intent.setClass(TutkConnectActivity.this, TabSET.class);
//        	   startActivity(intent); 
		    	Log.e("TutkConnectActivity", "TUTKCONNECT-exitbutton0-finish");	
		    	finish();
		   } 
		    
		    public void exitbutton1(View v) { 
		    	Log.e("TutkConnectActivity", "TUTKCONNECT-exitbutton1-start");
		    	finish();
		    	Log.e("TutkConnectActivity", "TUTKCONNECT-exitbutton1-finish");
		    	if (!TabControl.mUid.equals("NULL")) {	 
					Log.e("TabIR","connecting mUid is not NULL");
					showProgressDialog(TabControl.mUid);
				}else{
					Log.e("TabIR","connecting mUid is NULL");
				}
			    		
//			    	finish();
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
//				System.exit(0);
			}
		    
		    public void showProgressDialog(final String uid){  
				 progressDialog = ProgressDialog.show(this,"" , "Connecting...", true, false); 
				 new Thread(){        
				     @Override  
				     public void run() {  
				    Message startMsg=new Message();
//				       TUTKClient.stop();
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
//			        		Log.e("leewoo", "Tabset---onStart->cur:"+cursor.getCount()+" mUid:"+TabControl.mUid);
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
							TabControl.mUid="NULL";//未连接置空
//			        		notifyDataSetChanged();
			        	}
//			            //关闭ProgressDialog  
			            progressDialog.dismiss(); 

			        }};  
		
} 
