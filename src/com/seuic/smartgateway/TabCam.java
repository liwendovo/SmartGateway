package com.seuic.smartgateway ;  

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;

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
	
}
  
