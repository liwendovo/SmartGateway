package com.seuic.add;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.seuic.net.FTPUtil;
import com.seuic.net.NetConfig;
import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class AddDev extends Activity {
	public final static String PREFERENCE_NAME = "netconfig";
	Button addDevBtn;
//	Button pushap;
	
	Button titleBtn,homeBtn;
	ImageView titlePic;
	
	EditText edtUid;
	EditText edtSSID, edtPassword;
    Spinner spinnerSSID;
	List<String> listSSID;
	
	
	Handler handler;
	NetConfig netConfig;
	String target = null;
	public static final int MESSAGE_PUSHAP_SUBENABLE = 9990;
	public static final int MESSAGE_PUSHAP_FAILED = 9991;
	public static final int MESSAGE_PUSHFILE_NOEXIT = 9992;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.adddev);	
		
		
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
		//String otherwifi ="The existing network SSID is: \n\n"; 
		
		
		
		if(!results.isEmpty()){
		
		for (ScanResult result : results) {    	          		   
		     listSSID.add(result.SSID);
		  				}  		
				}
		}
		
		else {  Toast.makeText(getApplicationContext(), "wifi未连接，请连接wifi", Toast.LENGTH_SHORT).show();
		
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
		edtUid.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//edt.removeTextChangedListener(this);
				
				//判断如果是小写的字母的换，就转换
//				if((UID.charAt(0))-0 >= 97 && (UID.charAt(0))-0 <=122){
//					new Handler().postDelayed(new Runnable() {
//						@Override
//						public void run() {
//							//小写转大写
//							edt.setText(UID.toUpperCase());
//						}
//					}, 100);
//				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				

				try {

				 String temp = s.toString();

				        String tem = temp.substring(temp.length()-1,temp.length());

				char[] temC = tem.toCharArray();

				int mid = temC[0];

					if(mid>=48&&mid<=57)
					{//数字	
					return;	
					}
					if(mid>=65&&mid<=90){//大写字母	
					return;	
					}

					if(mid>97&&mid<=122){//小写字母	
					return;	
					}

				s.delete(temp.length()-1, temp.length());
				
				} catch (Exception e) {

				// TODO: handle exception

				}
			}

	   });
		
		
		addDevBtn.setOnClickListener(new OnClickListener()
		{		
			public void onClick(View source){
				
				
				//edt.setTransformationMethod(new AllCapTransformationMethod ());			
				final String UIDlen  = edtUid.getText().toString();
				if(UIDlen.length()!=5)
				{   
				
					Toast toast=Toast.makeText(getApplicationContext(),
			       		     "UID位数不对，请输入5位UID", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
				else
				{
					
					
				String UID = StringChange(UIDlen);
//				itemsIR = {"TV", "AC","Media","STU","WH", "DVD","FAN","自定义1","自定义2"};
//				itemsRF = {"Switch", "WH", "Lamp","Curtain","自定义1","自定义2"};	
				//插入数据库库
//				writeDB=ControlBox.mSQLHelper.getWritableDatabase();
				if(TabControl.mSQLHelper.insertSetup(TabControl.writeDB, UID, "Devices", "1"))
				{
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
					
					Toast toast=Toast.makeText(getApplicationContext(),
			       		     "UID已存在", Toast.LENGTH_SHORT);					
					toast.show();
				}
				//出入三级表
				
				finish();
				}
			}
		
		});	
		
		
		  
//			pushap.setEnabled(true);

			
//			pushap.setOnClickListener(new OnClickListener()
//			{
//				public void onClick(View v)
//				{
//					int psk_leng = 5;//spinnerSSID.getContext().length();
//					if (psk_leng == 0)
//					{
//						netConfig.setPsk(null);
//					}else {
//						netConfig.setPsk(edtPassword.getText().toString().trim());
//					}
//					pushap.setEnabled(false);
//					pushap.setText("sending");
//					target = netConfig.toString();
//					if (psk_leng != 0)
//					{
//						SaveShared(netConfig.getSsid(), netConfig.getPsk());
//					}
//					SaveFile();
//					Thread pushThread = new Thread(pushRunnable);
//					pushThread.start();
//				
//				}		
//			});      
		
		
		
		
	}
      //	小写字母转换为大写字母
	public String StringChange(String s){
		  char[] c=s.toCharArray();
		  for(int i=0;i<s.length();i++){
		    if(c[i]>='a'&&c[i]<='z')
			   c[i]=Character.toUpperCase(c[i]);
//		    else if(c[i]>='A'&&c[i]<='Z') 
//			   c[i]=Character.toLowerCase(c[i]);
		   }
		  return String.valueOf(c);
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


