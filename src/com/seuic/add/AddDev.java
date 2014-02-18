package com.seuic.add;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
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


@SuppressLint("HandlerLeak")
public class AddDev extends Activity {
	public final static String PREFERENCE_NAME = "netconfig";
	Button addDevBtn;
	
	String tag="AddDev";
	Button titleBtn,homeBtn;
	ImageView titlePic;	
	EditText edtName,edtUid;
	EditText edtSSID, edtPassword;
    Spinner spinnerSSID;
	List<String> listSSID;
	private ProgressDialog progressDialog; 
	RadioGroup radioGroup;
	String Key_mgmt="WPA-PSK";
	NetConfig netConfig;
	String mUid=null;
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
		netConfig.setPriority("123");
		
		homeBtn=(Button)findViewById(R.id.back);
		titlePic=(ImageView)findViewById(R.id.pic);
		titleBtn=(Button)findViewById(R.id.titleBtn);
		
    	homeBtn.setBackgroundResource(R.drawable.title_back);
    	titlePic.setImageResource(R.drawable.tab_set);
    	titleBtn.setVisibility(View.INVISIBLE);
    	
    	edtName    = (EditText)findViewById(R.id.devnameEdt);
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
		
		 
		
//	    Thread uidThread = new Thread(getuid);
//		uidThread.start();

//		Log.e(tag, "uid= "+mUid);
		
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listSSID);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  	 
		//添加事件Spinner事件监听    
		spinnerSSID.setAdapter(adapter);
//		spinnerSSID.setOnItemSelectedListener(new OnItemSelectedListener() {
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				// TODO Auto-generated method stub
//			}
//		
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//		});  
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				RadioButton radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
				
				if(radioButton.getText().toString().equals("WPA")){
					Key_mgmt="WPA-PSK";
					netConfig.setIsWep(false);
				}else if(radioButton.getText().toString().equals("WEP")){
					Key_mgmt="WEP";
					netConfig.setIsWep(true);
				}else{
					Key_mgmt="";
					netConfig.setIsWep(false);
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
				if (psk_leng != 0)
				{
					SaveShared(netConfig.getSsid(),netConfig.getPsk());
				}
				SaveFile(netConfig.toString());
				Thread pushThread = new Thread(pushRunnable);
				pushThread.start();
					
			}
		});	
		
	} 
	
	Runnable getuid=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
                	 byte sendvalue[]={0x0a,0x1b,0x2c,0x3d};
                	
                     try {
                    		Socket socket = new Socket();
                    		SocketAddress socAddress = new InetSocketAddress("192.168.1.100", 0xabc); 
                    		socket.connect(socAddress, 5000);
                    		InputStream in  = socket.getInputStream();
                            OutputStream out = socket.getOutputStream();
                     	    out.write(sendvalue);
                     	    out.flush();
							
							int numbytes;							
							byte[] data = new byte[20];
//							for(int i=0;i<20;i++)
//							{data[i]='\0';}
							if ((numbytes=in.read(data)) == -1) {	
	                            Log.e(tag, "null"+numbytes);
	                            Toast.makeText(AddDev.this,"未收到UID，请检查网络", Toast.LENGTH_SHORT).show();
	                        }else{
                            	Log.e(tag,""+ numbytes);
//                            	data[numbytes]='\0';
                            	String str =new String(data, "ISO-8859-1");
//	                            	 Toast.makeText(SimpleClient.this,str, Toast.LENGTH_SHORT).show();		
		                             Log.e(tag, ""+str);
		                             mUid=str;
//		                             edtUid.setText(str);
	                        }							
							uidHandler.sendEmptyMessage(0);
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							Log.e(tag, "Socket Error");
//							e.printStackTrace();
						}
           
		}
	};
	
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
	

	 Handler uidHandler = new Handler(){
		@Override
		public void handleMessage(Message msg)
		{
			if(TabControl.mSQLHelper.insertSetup(TabControl.writeDB, mUid, "Devices", edtPassword.getText().toString()))
			{
				Toast.makeText(AddDev.this, "send success  Please switch  work mode", Toast.LENGTH_LONG).show();
				finish();
			//devices判断
			//调试用默认加入设备
//			TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", "TV", "Devices",  "0","0");
//			TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", "AC", "Devices",  "0","0");
//			TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", "Media", "Devices",  "0","0");
//			TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", "STU", "Devices",  "0","0");
//			TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", "WH", "Devices",  "0","0");
//			TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", "DVD", "Devices",  "0","0");
//			TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", "FAN", "Devices",  "0","0");
//			TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", "CUSTOM1", "Devices",  "0","0");
//			TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", "CUSTOM2", "Devices",  "0","0");
//			
//			TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "rf", "Switch", "Devices",  "0","0");
//			TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "rf", "Lamp", "Devices",  "0","0");				
//			TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "rf", "Curtain", "Devices",  "0","0");
//			TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "rf", "Power", "Devices",  "0","0");
//			TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "rf", "CUSTOM1", "Devices",  "0","0");
//			TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "rf", "CUSTOM2", "Devices",  "0","0");
			}else{					
				Toast.makeText(AddDev.this, " send success but UID already exits!\n Please switch work mode", Toast.LENGTH_LONG).show();
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
			    Thread uidThread = new Thread(getuid);
				uidThread.start();
				
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
	public void SaveFile(String target){
		Log.e(tag, path);
		Log.e(tag, pathFile);		
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


