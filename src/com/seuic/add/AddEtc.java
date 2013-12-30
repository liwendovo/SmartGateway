package com.seuic.add;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.seuic.smartgateway.ControlBox;
import com.seuic.smartgateway.DevSetup;
import com.seuic.smartgateway.R;

public class AddEtc extends Activity {
	Button okBtn;	
	Spinner spinnerEtc;
	String mUid,mClass;
	int mDevID;
	private ArrayAdapter<String> adapter;
	public SQLiteDatabase writeDB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addetc);		
		Intent intent=getIntent();
		mUid=intent.getStringExtra("uid");	
		mClass=intent.getStringExtra("type");		
		Log.e("leewoo", "AddEtc -------  onCreate"+mUid+"   "+mClass);			
		okBtn=(Button)findViewById(R.id.okBtn);		
		spinnerEtc=(Spinner)findViewById(R.id.spinnerEtc);	
	
		if(mClass.equals("ir")){
			adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ControlBox.itemsIR);
		}else{
			adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ControlBox.itemsRF);
		}
        //���������б�ķ��  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);            
        //��adapter ��ӵ�spinner��  
        spinnerEtc.setAdapter(adapter);            
        //����Ĭ��ֵ  
        spinnerEtc.setVisibility(View.VISIBLE);  
    	okBtn.setOnClickListener(new OnClickListener()
		{		
			public void onClick(View source){
				String name = ((EditText)findViewById(R.id.devName)).getText().toString();
				//�������ݿ��				
				writeDB=ControlBox.mSQLHelper.getWritableDatabase();
				ControlBox.mSQLHelper.insertList(writeDB, mUid, mClass,spinnerEtc.getSelectedItem().toString(), name, "0","0");
				//����ҳ��Ĵ���
				//Log.e("leewoo", mUid+" "+name+" "+mClass+" "+listBtn.getText().toString());
				finish();
			}			
		});			
	}

	
	
	
	
	
	
	
	
}
				