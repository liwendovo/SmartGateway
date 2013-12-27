package com.seuic.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.seuic.smartgateway.R;

public class SetupAp extends Activity
{
	public final static String PREFERENCE_NAME = "netconfig";
	public static SetupAp instance = null;
	
	EditText pushap_psk;
	EditText ssid, password;
	Button pushap;
	Spinner spinnerSSID;
	List<String> listSSID;
	Handler handler;
	NetConfig netConfig;
	String target = null;
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
			ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listSSID);
			//绑定 Adapter到控件
			//设置下拉列表的风格  
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  	 
			//添加事件Spinner事件监听    
			spinnerSSID.setAdapter(adapter);
			spinnerSSID.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});  
	          
	
	

		
		
		pushap.setEnabled(true);

		
		pushap.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				int psk_leng = 5;//spinnerSSID.getContext().length();
				if (psk_leng == 0)
				{
					netConfig.setPsk(null);
				}else {
					netConfig.setPsk(password.getText().toString().trim());
				}
				pushap.setEnabled(false);
				pushap.setText("sending");
				
				target = netConfig.toString();
				if (psk_leng != 0)
				{
					SaveShared(netConfig.getSsid(),netConfig.getPsk());
				}
				SaveFile();
				Thread pushThread = new Thread(pushRunnable);
				pushThread.start();
			
			}		
		});
		
	
		
	}	
	Runnable pushRunnable = new Runnable()
	{
		File file = null;
		String path = "/mnt/sdcard/SmartGateway/Config/wpa_supplicant.conf";
//		String remoteFilename = "wpa_supplicant.conf";
		public void run()
		{
			file = new File(path);
			if (file.exists())
			{
				try
				{
					boolean success = FTPUtil.getInstance().upload(file);
//					boolean success=true;
					if (success)
					{
						Message shootpre = new Message();
						shootpre.what = MESSAGE_PUSHAP_SUBENABLE;
						handler.sendMessage(shootpre);
						shootpre = null;
					}else {
						Message shootpre = new Message();
						shootpre.what = MESSAGE_PUSHAP_FAILED;
						handler.sendMessage(shootpre);
						shootpre = null;
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}else {
				Message shootpre = new Message();
				shootpre.what = MESSAGE_PUSHFILE_NOEXIT;
				handler.sendMessage(shootpre);
				shootpre = null;
			}
		}
		
	};
	public void SaveShared(String ssid, String psk){
		SharedPreferences myPreferences = getSharedPreferences("netconfig", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = myPreferences.edit();
		editor.putString(ssid, psk);
		editor.commit();
	}
	public void SaveFile(){
		String path = "/mnt/sdcard/SmartGateway/Config/";
		String pathFile = "/mnt/sdcard/SmartGateway/Config/wpa_supplicant.conf";
		try
		{
			File config = new File(path);
			if (!config.exists())
			{
				config.mkdirs();
			}
			config = new File(pathFile);
			if (config.exists())
			{
				config.delete();
				config.createNewFile();
			}else {
				config.createNewFile();
			} 
		FileOutputStream fout = new FileOutputStream(config);
		byte[] bytes = target.getBytes();
		fout.write(bytes);
		fout.close();
		}catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
