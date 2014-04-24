package com.seuic.smartgateway;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;


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
		setContentView(R.layout.splash);

		handler = new Handler();
		handler.postDelayed(startAct, 2000);
	}
			
	
}
