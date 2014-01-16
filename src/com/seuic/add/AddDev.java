package com.seuic.add;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.seuic.net.FTPUtil;
import com.seuic.net.NetConfig;
import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;


public class AddDev extends Activity {
	public final static String PREFERENCE_NAME = "netconfig";
	Button addDevBtn;

	String tag="AddDev";
	Button titleBtn,homeBtn;
	ImageView titlePic;	
	EditText edtUid;
	EditText edtSSID, edtPassword;
    Spinner spinnerSSID;
	List<String> listSSID;
	private ProgressDialog progressDialog; 
	RadioGroup radioGroup;
	String Key_mgmt="WPA-PSK";
	NetConfig netConfig;
	String target = null;
	public static final int MESSAGE_PUSHAP_SUBENABLE = 9990;
	public static final int MESSAGE_PUSHAP_FAILED = 9991;
	public static final int MESSAGE_PUSHFILE_NOEXIT = 9992;
	String path = null;
	String pathFile = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.adddev);			
		path=getSDPath()+"/SmartGateway/Config/";
		pathFile=path+"wpa_supplicant.conf";
		Log.e("leewoo", path);
		Log.e("leewoo", pathFile);
		netConfig=new NetConfig();
		homeBtn=(Button)findViewById(R.id.back);
		titlePic=(ImageView)findViewById(R.id.pic);
		titleBtn=(Button)findViewById(R.id.titleBtn);
		
    	homeBtn.setBackgroundResource(R.drawable.title_back);
    	titlePic.setImageResource(R.drawable.tab_set);
    	titleBtn.setVisibility(View.INVISIBLE);
		
		addDevBtn=(Button)findViewById(R.id.addDevBtn);
		edtUid = (EditText) findViewById(R.id.uidEdt);
		spinnerSSID=(Spinner)findViewById(R.id.spinnerSSID);		
		edtPassword = (EditText)findViewById(R.id.passwordEdt);
		radioGroup= (RadioGroup)findViewById(R.id.radioGroup);
		
		TabControl.mViewSelected.setButtonClickChanged(addDevBtn);
		
		homeBtn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		String wifissid = wifiInfo.getSSID();  
		listSSID =  new ArrayList<String>();
		if(wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED){
		Log.d("leewoo","wifiinfo:"+wifiInfo.toString());
		Log.d("leewoo","SSID:"+wifissid);			
		List<ScanResult> results = wifiManager.getScanResults();  			
		  if(!results.isEmpty()){		
			for (ScanResult result : results) {    	          		   
			     listSSID.add(result.SSID);			     
			  	}  		
			}
		}
		else {  
			Toast.makeText(getApplicationContext(), "wifi未连接，请连接wifi", Toast.LENGTH_SHORT).show();
			addDevBtn.setEnabled(false);
		}
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listSSID);
		
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
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				RadioButton radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
				
				if(radioButton.getText().toString().equals("WPA")){
					Key_mgmt="WPA-PSK";
				}else if(radioButton.getText().toString().equals("WEP")){
					Key_mgmt="WEP";
				}else{
					Key_mgmt="";
				}
			}
		});
		addDevBtn.setOnClickListener(new OnClickListener()
		{		
			public void onClick(View source){			
				progressDialog = ProgressDialog.show(AddDev.this, "Sending...", "Please wait...", true, false); 
				Log.e(tag, spinnerSSID.getSelectedItem().toString());
				String ssid=spinnerSSID.getSelectedItem().toString();
				netConfig.setSsid(ssid);
				//加密方式
				netConfig.setKey_mgmt(Key_mgmt);
				int psk_leng = edtPassword.getText().length();
				if (psk_leng == 0)
				{
					netConfig.setPsk(null);
				}else {
					netConfig.setPsk(edtPassword.getText().toString().trim());
				}
				
				addDevBtn.setText("sending");
				
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
		public void run()
		{ 
			file = new File(pathFile);
			if (file.exists())
			{
				try
				{
					boolean success = FTPUtil.getInstance().upload(file);
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
	
	
	 Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg)
		{
			progressDialog.dismiss();
			switch (msg.what)
			{
			case MESSAGE_PUSHAP_SUBENABLE:	
				addDevBtn.setEnabled(true);
				addDevBtn.setText("send success");
				
				if(TabControl.mSQLHelper.insertSetup(TabControl.writeDB, edtUid.getText().toString(), "Devices", "1"))
				{
					Toast.makeText(AddDev.this, "send success  Please switch  work mode", Toast.LENGTH_LONG).show();
					finish();
				//devices判断
				//调试用默认加入设备
//				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", "TV", "Devices",  "0","0");
//				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", "AC", "Devices",  "0","0");
//				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", "Media", "Devices",  "0","0");
//				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", "STU", "Devices",  "0","0");
//				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", "WH", "Devices",  "0","0");
//				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", "DVD", "Devices",  "0","0");
//				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", "FAN", "Devices",  "0","0");
//				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", "CUSTOM1", "Devices",  "0","0");
//				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", "CUSTOM2", "Devices",  "0","0");
//				
//				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "rf", "Switch", "Devices",  "0","0");
//				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "rf", "Lamp", "Devices",  "0","0");				
//				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "rf", "Curtain", "Devices",  "0","0");
//				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "rf", "Power", "Devices",  "0","0");
//				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "rf", "CUSTOM1", "Devices",  "0","0");
//				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "rf", "CUSTOM2", "Devices",  "0","0");
				}else{					
					Toast.makeText(AddDev.this, "UID already exits! \n send success  Please switch work mode", Toast.LENGTH_LONG).show();
				}
				break;
			case MESSAGE_PUSHAP_FAILED:
				addDevBtn.setEnabled(true);
				addDevBtn.setText("send failed");
				Toast.makeText(AddDev.this, "Failure, please check the network!", Toast.LENGTH_LONG).show();
			
				break;
			case MESSAGE_PUSHFILE_NOEXIT:	
				addDevBtn.setEnabled(true);
				addDevBtn.setText("send failed");
				Toast.makeText(AddDev.this, "Failure, please to create a configuration file!", Toast.LENGTH_LONG).show();
				
				break;
			default:
				break;
			}
			super.handleMessage(msg);
			
		}
		
	};
	public void SaveShared(String ssid, String psk){
		SharedPreferences myPreferences = getSharedPreferences("netconfig", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = myPreferences.edit();
		editor.putString(ssid, psk);
		editor.commit();
	}
	public void SaveFile(){
		Log.e(tag, path);Log.e(tag, pathFile);
		
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
	public String getSDPath(){ 
	       File sdDir = null; 
	       boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);   //判断sd卡是否存在 
	       if (sdCardExist)   
	       {                               
	         sdDir = Environment.getExternalStorageDirectory();//获取跟目录 
	      }   
	       return sdDir.toString(); 
	}
	
	
}


