package com.seuic.smartgateway;

import com.seuic.net.TUTKClient;
import com.seuic.smartgateway.R;
import android.app.Activity; 
import android.os.Bundle; 
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
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class TutkConnectActivity extends Activity {
	
	
	private LayoutInflater mInflater = null;
	private static TabHost host = null;

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
		    	Log.e("leewoo", "TUTKCONNECT---finish");	
		    	finish();
		   } 
		    
		    protected void onDestroy() {
				// TODO Auto-generated method stub
				super.onDestroy();
				Log.e("leewoo", "TUTKCONNECT---onDestroy");	
				System.exit(0);
			}
		
} 
