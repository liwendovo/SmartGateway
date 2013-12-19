package com.seuic.add;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seuic.smartgateway.DevSetup;
import com.seuic.smartgateway.R;

public class AddEtc extends Activity {
	Button okBtn;
	Button listBtn;
	String mUid,mType;
	public SQLiteDatabase writeDB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addetc);
		
		Intent intent=getIntent();
		mUid=intent.getStringExtra("uid");	
		mType=intent.getStringExtra("type");	
		Log.e("leewoo", "AddEtc -------  onCreate"+mUid+"   "+mType);
	
		
		
		okBtn=(Button)findViewById(R.id.okBtn);
		listBtn=(Button)findViewById(R.id.listBtn);
		if(mType.equals("ir")){
			listBtn.setText("TV");
		}else{
			listBtn.setText("Switch");
		}
		final AlertDialog.Builder builder = new AlertDialog.Builder(this); 
		
	
		listBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub		
			
			//	Toast.makeText(getApplicationContext(), "Onlick", Toast.LENGTH_SHORT).show(); 
			//	Log.e("leewoo", "listBtn -------  onClick");	
				final CharSequence[] itemsIR = {"TV", "AC","Media","STU","WH", "DVD","FAN","自定义1","自定义2"}; 
				final CharSequence[] itemsRF = {"SWitch", "WH", "Lamp","Curtain","自定义1","自定义2"}; 	
				
				final CharSequence[] items;
				if(mType.equals("ir")){
					builder.setTitle("IR Name"); 
					items=itemsIR;
				}else{
					builder.setTitle("RF Name"); 
					items=itemsRF;
				}
				builder.setItems(items, new DialogInterface.OnClickListener() { 
				    public void onClick(DialogInterface dialog, int item) { 
				      Log.e("leewoo", "listBtn -------  onClick:"+item);
				      Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show(); 				        
				      listBtn.setText(items[item]);
				    } 
				}); 
				builder.create().show();				
			}
		});
		
		okBtn.setOnClickListener(new OnClickListener()
		{		
			public void onClick(View source){
				String name = ((EditText)findViewById(R.id.devName)).getText().toString();
				//插入数据库库				
				writeDB=DevSetup.mSQLHelper.getWritableDatabase();
				DevSetup.mSQLHelper.insertList(writeDB, mUid, name, mType,listBtn.getText().toString());
				Log.e("leewoo", mUid+" "+name+" "+mType+" "+listBtn.getText().toString());
				finish();
			}			
		});	
	}

}
				