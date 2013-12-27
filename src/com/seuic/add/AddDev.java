package com.seuic.add;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seuic.smartgateway.ControlBox;
import com.seuic.smartgateway.DevSetup;
import com.seuic.smartgateway.R;

public class AddDev extends Activity {
	Button addDevBtn;
	EditText edt;
	//int changeCase;
    String srString;
    Toast toast = null;
	public SQLiteDatabase writeDB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.adddev);		
		addDevBtn=(Button)findViewById(R.id.addDevBtn);
		edt = (EditText) findViewById(R.id.uidEditText);
		
		edt.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//edt.removeTextChangedListener(this);
				
				//判断如果是小写的字母的换，就转换
//				if((UID.charAt(0))-0 >= 97 && (UID.charAt(0))-0 <=122){
//					new Handler().postDelayed(new Runnable() {
//						@Override
//						public void run() {
//							//小写转大写
//							edt.setText(UID.toUpperCase());
//						}
//					}, 100);
//				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				

				try {

				 String temp = s.toString();

				        String tem = temp.substring(temp.length()-1,temp.length());

				char[] temC = tem.toCharArray();

				int mid = temC[0];

					if(mid>=48&&mid<=57)
					{//数字
	
					return;
	
					}

					if(mid>=65&&mid<=90){//大写字母
	
					return;
	
					}

					if(mid>97&&mid<=122){//小写字母
	
					return;
	
					}

				s.delete(temp.length()-1, temp.length());
				
				

				} catch (Exception e) {

				// TODO: handle exception

				}
				

			}

	   });
		
		
		addDevBtn.setOnClickListener(new OnClickListener()
		{		
			public void onClick(View source){
				
				
				//edt.setTransformationMethod(new AllCapTransformationMethod ());			
				final String UIDlen  = edt.getText().toString();
				if(UIDlen.length()!=5)
				{   
					toast = Toast.makeText(getApplicationContext(),
			       		     "UID位数不对，请输入5位UID", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					
				}
				else
				{
					
					
				String UID = StringChange(UIDlen);
					
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
			}
//
//			private EditText findViewById(int uidedittext) {
//				// TODO 自动生成的方法存根
//				return null;
//			}			
		});	
	}
      //	小写字母转换为大写字母
	public String StringChange(String s){
		  char[] c=s.toCharArray();
		  for(int i=0;i<s.length();i++){
		    if(c[i]>='a'&&c[i]<='z')
			   c[i]=Character.toUpperCase(c[i]);
//		    else if(c[i]>='A'&&c[i]<='Z') 
//			   c[i]=Character.toLowerCase(c[i]);
		   }
		  return String.valueOf(c);
		}
	
}


