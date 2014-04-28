package com.seuic.smartgateway ;  

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

public class TabCam extends Activity {

	  List<Map<String, Object>> list;
	  ListView myList;
	 
		int currentID=-1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabcam);		
		 getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,      
                 WindowManager.LayoutParams. FLAG_FULLSCREEN); 


		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub		
		super.onDestroy();
	}
	
	
	private long mExitTime;
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 Log.e("TabControl","get the keyback");
       if (keyCode == KeyEvent.KEYCODE_BACK) {
      	     Log.e("TabControl","get keyback");
				if ((System.currentTimeMillis() - mExitTime) > 2000) {
                       Object mHelperUtils;
                       Toast.makeText(this, "press again to exit the app", TabControl.time).show();
                       mExitTime = System.currentTimeMillis();

               } else {
//              	 finish();
              	 System.exit(0);
               }
               return true;
       }
       return super.onKeyDown(keyCode, event);
}
}
  
