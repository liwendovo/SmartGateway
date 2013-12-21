package com.seuic.devetc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.seuic.smartgateway.R;

public class IR_WH extends Activity implements android.view.View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ir_wh);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
