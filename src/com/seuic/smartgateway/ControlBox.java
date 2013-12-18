package com.seuic.smartgateway;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class ControlBox extends Activity {
	TextView tv1,tv2; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.controlbox);	
		Intent intent=getIntent();
		String uid1=intent.getStringExtra("uid");	
		Toast toast = Toast.makeText(ControlBox.this, uid1, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 50);
		toast.show();		
				
		
		TabHost host = (TabHost)findViewById(R.id.tabhost);
		host.setup();

		TabHost.TabSpec irSpec = host.newTabSpec("IR");        //This param will be used as tabId.
		irSpec.setIndicator(null,         					//This param will diplay as title. 
		getResources().getDrawable(R.drawable.ir));
		irSpec.setContent(R.id.tabir);
		host.addTab(irSpec);

		TabHost.TabSpec rfSpec = host.newTabSpec("RF");
		rfSpec.setIndicator(null, getResources().getDrawable(R.drawable.rf));
		rfSpec.setContent(R.id.tabrf);
		host.addTab(rfSpec);

		TabHost.TabSpec thSpec = host.newTabSpec("TH");
		thSpec.setIndicator(null, getResources().getDrawable(R.drawable.th));
		thSpec.setContent(R.id.tabth);
		host.addTab(thSpec);
		
		TabHost.TabSpec setSpec = host.newTabSpec("SET");
		setSpec.setIndicator(null, getResources().getDrawable(R.drawable.setp));
		setSpec.setContent(R.id.tabset);
		host.addTab(setSpec);
	}
	
	


}
