package com.seuic.net;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.seuic.smartgateway.R;

public class SetupAp extends Activity
{
	public final static String PREFERENCE_NAME = "netconfig";
	public static SetupAp instance = null;
	
	EditText pushap_psk;
	TextView ssid, password;
	Button pushap;
	Spinner spinnerSSID;
	List<String> listSSID;
	
	public static final int MESSAGE_PUSHAP_SUBENABLE = 9990;
	public static final int MESSAGE_PUSHAP_FAILED = 9991;
	public static final int MESSAGE_PUSHFILE_NOEXIT = 9992;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setupap);
		password = (EditText)findViewById(R.id.password);
		//ssid = (TextView)findViewById(R.id.ssid);
		pushap= (Button)findViewById(R.id.pushapBtn);
		spinnerSSID=(Spinner)findViewById(R.id.spinnerSSID);
		listSSID =  new ArrayList<String>();
		
		   WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
		   WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		   String wifissid = wifiInfo.getSSID();   
		   Log.d("leewoo","wifiinfo:"+wifiInfo.toString());
		   Log.d("leewoo","SSID:"+wifissid);
			
		   List<ScanResult> results = wifiManager.getScanResults();  
		   //String otherwifi ="The existing network SSID is: \n\n"; 
		   if(!results.isEmpty()){
		   for (ScanResult result : results) {    	          		   
	            listSSID.add(result.SSID);	            
	        }  		
		   }
			ArrayAdapter<String> _Adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listSSID);
			//°ó¶¨ Adapterµ½¿Ø¼þ
			spinnerSSID.setAdapter(_Adapter);
	
	

		pushap.setEnabled(true);

		
		pushap.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{

			}		
		});
	}	
}
