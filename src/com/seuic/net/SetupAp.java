package com.seuic.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.seuic.smartgateway.R;
import com.seuic.smartgateway.R.id;
import com.seuic.smartgateway.R.layout;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetupAp extends Activity
{
	public final static String PREFERENCE_NAME = "netconfig";
	public static SetupAp instance = null;
	
	EditText pushap_psk;
	TextView ssid, password;
	Button pushap;
//	String targetIP = WifiCar.targetIP; // Target IP
	int targetPort = 9090; // Target port
	NetConfig netConfig;
	String target = null;
	Handler handler;
	
	public static final int MESSAGE_PUSHAP_SUBENABLE = 9990;
	public static final int MESSAGE_PUSHAP_FAILED = 9991;
	public static final int MESSAGE_PUSHFILE_NOEXIT = 9992;
	public Handler getHandler()
	{
		return handler;
	}
	public static SetupAp getInstance()
	{
		return instance;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setupap);
		instance = this;
	//	Android_Exit.getInstance().addActivity(this); //tianjia
		try
		{
			handler = new Handler(){

				@Override
				public void handleMessage(Message msg)
				{
					switch (msg.what)
					{
					case MESSAGE_PUSHAP_SUBENABLE:
						pushap.setEnabled(true);
						pushap.setText("send success");
						Toast.makeText(SetupAp.this, "send success  Please switch P - care work mode", Toast.LENGTH_LONG).show();
						break;
					case MESSAGE_PUSHAP_FAILED:
						pushap.setEnabled(true);
						Toast.makeText(SetupAp.this, "Failure, please check the network!", Toast.LENGTH_LONG).show();
						pushap.setText("send failed");
						break;
					case MESSAGE_PUSHFILE_NOEXIT:
						pushap.setEnabled(true);
						Toast.makeText(SetupAp.this, "Failure, please to create a configuration file!", Toast.LENGTH_LONG).show();
						pushap.setText("send failed");
						break;
					default:
						break;
					}
					super.handleMessage(msg);
					finish();
				}
				
			};
		} catch (Exception e)
		{
			
		}
		
		password = (EditText)findViewById(R.id.password);
		ssid = (TextView)findViewById(R.id.ssid);
	//	key = (TextView)findViewById(R.id.key);
		pushap= (Button)findViewById(R.id.pushapBtn);
//		pushap_cal = (Button)findViewById(R.id.pushap_cal);
	//	priority = (TextView)findViewById(R.id.priority);
	//	Bundle bundle = this.getIntent().getExtras();
		//netConfig = (NetConfig)bundle.getSerializable("netConfig");
	//	ssid.setText(netConfig.getSsid());
	//	key.setText(netConfig.getKey_mgmt());
	//	priority.setText(netConfig.getPriority());
		pushap.setEnabled(true);
//		if (netConfig.getPsk() != null)
//		{
//			pushap_psk.setText(netConfig.getPsk());
//		}else {
//			
//		}
//		if (netConfig.getPsk() != null)
//		{
//			pushap_psk.setEnabled(true);
//			if (netConfig.getIsSaved())
//			{
//				pushap_psk.setText(netConfig.getPsk());
//			}
//			
//		}else {
//			pushap_psk.setEnabled(false);
//			pushap_psk.setText("Not input Password");
//		}
//		pushap_cal.setOnClickListener(new OnClickListener()
//		{
//			
//			public void onClick(View v)
//			{
//				SetupAp.this.finish();
//			}
//		});
		
		pushap.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
//				int psk_leng = pushap_psk.getText().length();
//					if (psk_leng == 0)
//					{
//						netConfig.setPsk(null);
//					}else {
//						netConfig.setPsk(pushap_psk.getText().toString().trim());
//					}
//					pushap_sub.setEnabled(false);
//					pushap_sub.setText("sending");
//					
//					target = netConfig.toString();
//					if (psk_leng != 0)
//					{
//						SaveShared(netConfig.getSsid(),netConfig.getPsk());
//					}
//					SaveFile();
//					Thread pushThread = new Thread(pushRunnable);
//					pushThread.start();
				}
//			}
		});
	}
	
	public void SaveFile(){
		String path = "/mnt/sdcard/Pcare-Route/Config/";
		String pathFile = "/mnt/sdcard/Pcare-Route/Config/wpa_supplicant.conf";
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
	
	public void SaveShared(String ssid, String psk){
		SharedPreferences myPreferences = getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = myPreferences.edit();
		editor.putString(ssid, psk);
		editor.commit();
	}
	
	Runnable pushRunnable = new Runnable()
	{
		File file = null;
		String path = "/mnt/sdcard/Pcare-Route/Config/wpa_supplicant.conf";
//		String remoteFilename = "wpa_supplicant.conf";
		public void run()
		{
//			file = new File(path);
//			if (file.exists())
//			{
//				try
//				{
//					boolean success = FTPUtil.getInstance().upload(file);
//					if (success)
//					{
//						Message shootpre = new Message();
//						shootpre.what = MESSAGE_PUSHAP_SUBENABLE;
//						handler.sendMessage(shootpre);
//						shootpre = null;
//					}else {
//						Message shootpre = new Message();
//						shootpre.what = MESSAGE_PUSHAP_FAILED;
//						handler.sendMessage(shootpre);
//						shootpre = null;
//					}
//				} catch (Exception e)
//				{
//					e.printStackTrace();
//				}
//			}else {
//				Message shootpre = new Message();
//				shootpre.what = MESSAGE_PUSHFILE_NOEXIT;
//				handler.sendMessage(shootpre);
//				shootpre = null;
//			}
		}
		
	};
	
}
