package com.seuic.smartgateway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class LogoActivity extends Activity {
	protected static final int MESSAGE_MAIN_PROCEDURE = 0;
	private Handler handler = null;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);

		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MESSAGE_MAIN_PROCEDURE:					
					Intent intent = new Intent(LogoActivity.this, ControlBox.class);
					startActivityForResult(intent, 1);
					//进入主界面后可以直接退出，不会退回logo
				
					break;
				
				default:
					break;
				}
				super.handleMessage(msg);
			}
		};	
	}
	@Override
	protected void onStart() {
		super.onStart();
		Runnable init = new Runnable() {

			//@Override
			public void run() {				
				try {
					Thread.sleep(600);
					Message messageLoadingSuccess = new Message();
					messageLoadingSuccess.what = MESSAGE_MAIN_PROCEDURE;
					handler.sendMessage(messageLoadingSuccess);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread initThread = new Thread(init);
		initThread.start();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		finish();
	
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0);
	}
	
	
}
