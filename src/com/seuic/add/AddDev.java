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

import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

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
				
				//�ж������Сд����ĸ�Ļ�����ת��
//				if((UID.charAt(0))-0 >= 97 && (UID.charAt(0))-0 <=122){
//					new Handler().postDelayed(new Runnable() {
//						@Override
//						public void run() {
//							//Сдת��д
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
					{//����	
					return;	
					}
					if(mid>=65&&mid<=90){//��д��ĸ	
					return;	
					}

					if(mid>97&&mid<=122){//Сд��ĸ	
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
				
					toast=Toast.makeText(getApplicationContext(),
			       		     "UIDλ�����ԣ�������5λUID", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
				else
				{
					
					
				String UID = StringChange(UIDlen);
					
				//�������ݿ��
//				writeDB=ControlBox.mSQLHelper.getWritableDatabase();
				TabControl.mSQLHelper.insertSetup(TabControl.writeDB, UID, "Devices", "1");
				//devices�ж�
				//Ĭ�ϼ����豸
				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", TabControl.itemsIR[0], "Devices",  "0","0");
				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", TabControl.itemsIR[1], "Devices",  "0","0");
				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", TabControl.itemsIR[2], "Devices",  "0","0");
				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", TabControl.itemsIR[3], "Devices",  "0","0");
				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", TabControl.itemsIR[4], "Devices",  "0","0");
				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", TabControl.itemsIR[5], "Devices",  "0","0");
				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", TabControl.itemsIR[6], "Devices",  "0","0");
				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", TabControl.itemsIR[7], "Devices",  "0","0");
				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "ir", TabControl.itemsIR[8], "Devices",  "0","0");
				
				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "rf", TabControl.itemsRF[0], "Devices",  "0","0");
				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "rf", TabControl.itemsRF[1], "Devices",  "0","0");
				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "rf", TabControl.itemsRF[2], "Devices",  "0","0");
				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "rf", TabControl.itemsRF[3], "Devices",  "0","0");
				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "rf", TabControl.itemsRF[4], "Devices",  "0","0");
				TabControl.mSQLHelper.insertList(TabControl.writeDB, UID, "rf", TabControl.itemsRF[5], "Devices",  "0","0");
				
				//����������
				
				finish();
				}
			}
		
		});	
	}
      //	Сд��ĸת��Ϊ��д��ĸ
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


