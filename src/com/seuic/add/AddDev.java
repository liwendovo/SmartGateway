package com.seuic.add;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.seuic.smartgateway.DevSetup;
import com.seuic.smartgateway.R;

public class AddDev extends Activity {
	Button addDevBtn;
	public SQLiteDatabase writeDB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adddev);		
		addDevBtn=(Button)findViewById(R.id.addDevBtn);
		addDevBtn.setOnClickListener(new OnClickListener()
		{		
			public void onClick(View source){
				String UID = ((EditText)findViewById(R.id.uidEditText))
				.getText().toString();
				//≤Â»Î ˝æ›ø‚ø‚
				writeDB=DevSetup.mSQLHelper.getWritableDatabase();
				DevSetup.mSQLHelper.insertSetup(writeDB, UID, "Devices", "1");
			}			
		});	
	}



}