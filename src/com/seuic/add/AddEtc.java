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
import android.widget.Toast;

import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

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
		if(mUid.equals("NULL")){
			Toast.makeText(getApplicationContext(),"无法添加遥控器，请先到Set界面进行设置", Toast.LENGTH_SHORT).show();		
			finish();
			}
		mClass=intent.getStringExtra("type");		
		Log.e("leewoo", "AddEtc -------  onCreate"+mUid+"   "+mClass);			
		okBtn=(Button)findViewById(R.id.okBtn);		
		spinnerEtc=(Spinner)findViewById(R.id.spinnerEtc);	
	
		if(mClass.equals("ir")){
			adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TabControl.itemsIR);
		}else{
			adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TabControl.itemsRF);
		}
        //设置下拉列表的风格  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);            
        //将adapter 添加到spinner中  
        spinnerEtc.setAdapter(adapter);            
        //设置默认值  
        spinnerEtc.setVisibility(View.VISIBLE);  
        TabControl.mViewSelected.setButtonClickChanged(okBtn);
    	okBtn.setOnClickListener(new OnClickListener()
		{		
			public void onClick(View source){
				String name = ((EditText)findViewById(R.id.devName)).getText().toString();
				//插入数据库库				
				writeDB=TabControl.mSQLHelper.getWritableDatabase();
				TabControl.mSQLHelper.insertList(writeDB, mUid, mClass,spinnerEtc.getSelectedItem().toString(), name, "0","0");
				//三级页表的创建
				//Log.e("leewoo", mUid+" "+name+" "+mClass+" "+listBtn.getText().toString());
				finish();
			}			
		});			
	}

	
	
	
	
	
	
	
	
}
				