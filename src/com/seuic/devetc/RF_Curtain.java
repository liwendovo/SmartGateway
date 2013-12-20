package com.seuic.devetc;

import com.seuic.smartgateway.R;
import com.seuic.smartgateway.R.layout;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;

public class RF_Curtain extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.rf_curtain);
	}

	
}
