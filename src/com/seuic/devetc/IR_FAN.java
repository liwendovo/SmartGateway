package com.seuic.devetc;

import com.seuic.smartgateway.R;
import com.seuic.smartgateway.R.layout;
import com.seuic.smartgateway.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;

public class IR_FAN extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ir_fan);
	}

	
}
