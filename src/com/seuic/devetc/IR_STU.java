package com.seuic.devetc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.seuic.smartgateway.R;

public class IR_STU extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ir_stu);
	}

}