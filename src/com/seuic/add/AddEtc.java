package com.seuic.add;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seuic.smartgateway.ControlBox;
import com.seuic.smartgateway.DevSetup;
import com.seuic.smartgateway.R;

public class AddEtc extends Activity {
	Button okBtn;
	Button listBtn;
	String mUid,mClass;
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
		listBtn=(Button)findViewById(R.id.listBtn);
		
		if(mClass.equals("ir")){
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
			
				
				final CharSequence[] items;
				if(mClass.equals("ir")){
					builder.setTitle("IR Name"); 
					items=ControlBox.itemsIR;
				}else{
					builder.setTitle("RF Name"); 
					items=ControlBox.itemsRF;
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
				DevSetup.mSQLHelper.insertList(writeDB, mUid, mClass, listBtn.getText().toString(), name, "0","0");
				//三级页表的创建
				
				
				
				
				
				//Log.e("leewoo", mUid+" "+name+" "+mClass+" "+listBtn.getText().toString());
				finish();
			}			
		});	
	}

}
				