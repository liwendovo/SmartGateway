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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.seuic.adapter.CustomToast;
import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;

public class AddEtc extends Activity implements android.view.View.OnClickListener{
	Button okBtn;	
	Button titleBtn,homeBtn;
	LinearLayout back_ll;
	ImageView titlePic;	
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
		homeBtn=(Button)findViewById(R.id.back);
		titlePic=(ImageView)findViewById(R.id.pic);
		titleBtn=(Button)findViewById(R.id.titleBtn);
		
		back_ll=(LinearLayout)findViewById(R.id.back_ll);
		
    	homeBtn.setBackgroundResource(R.drawable.title_back);
    	titlePic.setVisibility(View.INVISIBLE);
    	titleBtn.setVisibility(View.INVISIBLE);
    	
    	
		
		Intent intent=getIntent();
		mUid=intent.getStringExtra("uid");	
		if(mUid.equals("NULL")){
			CustomToast.showToast(getApplicationContext(),"can't add remote controller，please go to set interface to setup", Toast.LENGTH_SHORT);		
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
//        adapter.setDropDownViewResource(R.layout.timezone_dropdown_item); 
        //将adapter 添加到spinner中  
        spinnerEtc.setAdapter(adapter);            
        //设置默认值  
        spinnerEtc.setVisibility(View.VISIBLE);  
        TabControl.mViewSelected.setButtonClickChanged(okBtn);
        
        homeBtn.setOnClickListener(this); 
		back_ll.setOnClickListener(this); 
		okBtn.setOnClickListener(this); 
		
    	
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
				switch(v.getId())  
		        {  
		        case R.id.back_ll:
		        case R.id.back:
		        	finish();
		        	break;
		        	
		        case R.id.okBtn:
		        	String name = ((EditText)findViewById(R.id.devName)).getText().toString();
					//插入数据库库				
					writeDB=TabControl.mSQLHelper.getWritableDatabase();
					TabControl.mSQLHelper.insertList(writeDB, mUid, mClass,spinnerEtc.getSelectedItem().toString(), name, "0","0");
					//三级页表的创建
					Log.e("leewoo", mUid+" name= "+name);
					finish();
		        	break;
		            
		        default:  
		            break;  
		       
		        }
			}

		}
