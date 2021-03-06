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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.seuic.function.CustomToast;
import com.seuic.net.FTPUtil;
import com.seuic.net.NetConfig;
import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;


@SuppressLint("HandlerLeak")
public class AddDev extends Activity implements android.view.View.OnClickListener{
	public final static String PREFERENCE_NAME = "netconfig";
	Button addDevBtn;
	
	String tag="AddDev";
	Button titleBtn,homeBtn;
	LinearLayout back_ll;
	ImageView titlePic;	
	EditText edtName,edtUid;
	EditText edtSSID, edtPassword;
    Spinner spinnerSSID;
	List<String> listSSID;
	List<String> listCapabilities;
	private ProgressDialog progressDialog; 
//	RadioGroup radioGroup;
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
		
		back_ll=(LinearLayout)findViewById(R.id.back_ll);
		
    	homeBtn.setBackgroundResource(R.drawable.title_back);
    	titlePic.setImageResource(R.drawable.tab_set);
    	titleBtn.setVisibility(View.INVISIBLE);
    	
    	edtName    = (EditText)findViewById(R.id.devnameEdt);
		addDevBtn=(Button)findViewById(R.id.addDevBtn);
//		edtUid = (EditText) findViewById(R.id.uidEdt);
		spinnerSSID=(Spinner)findViewById(R.id.spinnerSSID);		
		edtPassword = (EditText)findViewById(R.id.passwordEdt);	
//		radioGroup= (RadioGroup)findViewById(R.id.radioGroup);		
		TabControl.mViewSelected.setButtonClickChanged(addDevBtn);
		
		
		homeBtn.setOnClickListener(this); 
		addDevBtn.setOnClickListener(this);
		back_ll.setOnClickListener(this); 
		
		
		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		String wifissid = wifiInfo.getSSID();  
		listSSID =  new ArrayList<String>();
		listCapabilities =  new ArrayList<String>();
		if(wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED){
		Log.d("leewoo","wifiinfo:"+wifiInfo.toString());
		Log.d("leewoo","SSID:"+wifissid);			
		List<ScanResult> results = wifiManager.getScanResults();  			
		  if(!results.isEmpty()){		
			for (ScanResult result : results) {    	          		   
			    if(!result.SSID.equals("")){
				listSSID.add(result.SSID);
			    listCapabilities.add(result.capabilities);
			    }
			  }  		
			}
		}
		else {  
			CustomToast.showToast(getApplicationContext(), "Please connect wireless network", Toast.LENGTH_SHORT);
			addDevBtn.setEnabled(false);
		}
		
		 
		
//	    Thread uidThread = new Thread(getuid);
//		uidThread.start();

//		Log.e(tag, "uid= "+mUid);
		
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listSSID);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  	 
		//添加事件Spinner事件监听    
		spinnerSSID.setAdapter(adapter);
		spinnerSSID.setOnItemSelectedListener(new OnItemSelectedListener() {		
			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
	                int postion, long id) {
				// TODO Auto-generated method stub
				if(listCapabilities.get(postion).contains("WPA")){
					Key_mgmt="WPA-PSK";
					edtPassword.setEnabled(true); 
				}else if(listCapabilities.get(postion).contains("WEP")){
					Key_mgmt="WEP";
					edtPassword.setEnabled(true); 
				}else{
					Key_mgmt="";
					edtPassword.setText("");
					edtPassword.setEnabled(false); 
				}
			
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});  
		
//		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			
//			@Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				// TODO Auto-generated method stub
//				RadioButton radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
//				
//				if(radioButton.getText().toString().equals("WPA")){
//					Key_mgmt="WPA-PSK";
//					netConfig.setIsWep(false);
//				}else if(radioButton.getText().toString().equals("WEP")){
//					Key_mgmt="WEP";
//					netConfig.setIsWep(true);
//				}else{
//					Key_mgmt="";
//					netConfig.setIsWep(false);
//				}
//			}
//		});
		
		
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
	                            CustomToast.showToast(AddDev.this,"Can't get UID, please check network", Toast.LENGTH_SHORT);
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
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
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
	

	 Handler uidHandler = new Handler(){
		@Override
		public void handleMessage(Message msg)
		{
			if(TabControl.mSQLHelper.insertSetup(TabControl.writeDB, mUid, "Devices", edtName.getText().toString()))
			{
				CustomToast.showToast(AddDev.this, "Message sent,Please exit APP and connect to wireless network!", Toast.LENGTH_LONG);
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
				CustomToast.showToast(AddDev.this, " send success but UID already exits!\n Please switch work mode", Toast.LENGTH_LONG);
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
//				addDevBtn.setText("Send to Device");
			    Thread uidThread = new Thread(getuid);
				uidThread.start();				
				break;
			case MESSAGE_PUSHAP_FAILED:
				addDevBtn.setEnabled(true);
//				addDevBtn.setText("send failed");
				CustomToast.showToast(AddDev.this, "Failure, please check the network!", Toast.LENGTH_LONG);
//			"SENT FAILED, Please check the network name and password of your router"
				break;
			case MESSAGE_PUSHFILE_NOEXIT:
				addDevBtn.setEnabled(true);
//				addDevBtn.setText("send failed");
				CustomToast.showToast(AddDev.this, "Failure, please to create a configuration file!", Toast.LENGTH_LONG);
				break;
			default:
				break;
			}
			super.handleMessage(msg);			
		}		
	};
//	public void SaveShared(String ssid, String psk){
//		SharedPreferences myPreferences = getSharedPreferences("netconfig", Activity.MODE_PRIVATE);
//		SharedPreferences.Editor editor = myPreferences.edit();
//		editor.putString(ssid, psk);
//		editor.commit();
//	}
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
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		
		switch(v.getId())  
        {  
        case R.id.back_ll:
        case R.id.back:
        	finish();
        	break;
        	
        case R.id.addDevBtn:
        	
        	progressDialog = ProgressDialog.show(AddDev.this, "Sending...", "Please wait...", true, false); 
			Log.e(tag, spinnerSSID.getSelectedItem().toString()+" "+Key_mgmt+" "+edtPassword.getText());
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
			
							
//			if (psk_leng != 0)
//			{
//				SaveShared(netConfig.getSsid(),netConfig.getPsk());
//			}
			
			SaveFile(netConfig.toString());
			Thread pushThread = new Thread(pushRunnable);
			pushThread.start();
			break;
       
            
        default:  
            break;  
       
        }
	}

}


