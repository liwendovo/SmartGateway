package com.seuic.smartgateway;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class LogoActivity extends Activity {
	protected static final int MESSAGE_MAIN_PROCEDURE = 0;
	private Handler handler = null;
	
	Runnable startAct = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
	
			startActivity(new Intent(LogoActivity.this,	TabControl.class));
			
			finish();
		}
	};
	
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);

		handler = new Handler();
		handler.postDelayed(startAct, 600);
	}
	
	
}
