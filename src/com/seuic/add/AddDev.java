package com.seuic.add;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.seuic.smartgateway.ControlBox;
import com.seuic.smartgateway.DevSetup;
import com.seuic.smartgateway.R;

public class AddDev extends Activity {
	Button addDevBtn;
	String btnDefaults="自定义";
	public SQLiteDatabase writeDB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.adddev);		
		addDevBtn=(Button)findViewById(R.id.addDevBtn);
		addDevBtn.setOnClickListener(new OnClickListener()
		{		
			public void onClick(View source){
				String UID = ((EditText)findViewById(R.id.uidEditText))
				.getText().toString();
				//插入数据库库
				writeDB=DevSetup.mSQLHelper.getWritableDatabase();
				DevSetup.mSQLHelper.insertSetup(writeDB, UID, "Devices", "1");
				//devices判断
				//默认加入设备
				DevSetup.mSQLHelper.insertList(writeDB, UID, "ir", ControlBox.itemsIR[0].toString(), "Devices",  "0","0");
				DevSetup.mSQLHelper.insertList(writeDB, UID, "ir", ControlBox.itemsIR[1].toString(), "Devices",  "0","0");
				DevSetup.mSQLHelper.insertList(writeDB, UID, "ir", ControlBox.itemsIR[2].toString(), "Devices",  "0","0");
				DevSetup.mSQLHelper.insertList(writeDB, UID, "ir", ControlBox.itemsIR[3].toString(), "Devices",  "0","0");
				DevSetup.mSQLHelper.insertList(writeDB, UID, "ir", ControlBox.itemsIR[4].toString(), "Devices",  "0","0");
				DevSetup.mSQLHelper.insertList(writeDB, UID, "ir", ControlBox.itemsIR[5].toString(), "Devices",  "0","0");
				DevSetup.mSQLHelper.insertList(writeDB, UID, "ir", ControlBox.itemsIR[6].toString(), "Devices",  "0","0");
				DevSetup.mSQLHelper.insertList(writeDB, UID, "ir", ControlBox.itemsIR[7].toString(), "Devices",  "0","0");
					
						//DevSetup.mSQLHelper.insertBtn(writeDB,UID,8, "name" ,btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults, btnDefaults+14);
						//DevSetup.mSQLHelper.insertEtc(writeDB, UID, 8, UID, UID, UID, UID);
				DevSetup.mSQLHelper.insertList(writeDB, UID, "ir", ControlBox.itemsIR[8].toString(), "Devices",  "0","0");
				
				DevSetup.mSQLHelper.insertList(writeDB, UID, "rf", ControlBox.itemsRF[0].toString(), "Devices",  "0","0");
				DevSetup.mSQLHelper.insertList(writeDB, UID, "rf", ControlBox.itemsRF[1].toString(), "Devices",  "0","0");
				DevSetup.mSQLHelper.insertList(writeDB, UID, "rf", ControlBox.itemsRF[2].toString(), "Devices",  "0","0");
				DevSetup.mSQLHelper.insertList(writeDB, UID, "rf", ControlBox.itemsRF[3].toString(), "Devices",  "0","0");
				DevSetup.mSQLHelper.insertList(writeDB, UID, "rf", ControlBox.itemsRF[4].toString(), "Devices",  "0","0");
				DevSetup.mSQLHelper.insertList(writeDB, UID, "rf", ControlBox.itemsRF[5].toString(), "Devices",  "0","0");
				
				//出入三级表
				
				finish();
			}			
		});	
	}



}