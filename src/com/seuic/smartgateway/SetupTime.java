package com.seuic.smartgateway;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.seuic.net.TUTKClient;

public class SetupTime extends Activity implements android.view.View.OnClickListener{
	Button titleBtn,homeBtn;
	LinearLayout back_ll;
	ImageView titlePic;	
	Spinner spinnerZone;
	ToggleButton timeAutoBtn,timeHourBtn;
	private ArrayAdapter<CharSequence> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setup_time);
		
		homeBtn=(Button)findViewById(R.id.back);
		titlePic=(ImageView)findViewById(R.id.pic);
		titleBtn=(Button)findViewById(R.id.titleBtn);
		
		back_ll=(LinearLayout)findViewById(R.id.back_ll);
		
    	homeBtn.setBackgroundResource(R.drawable.title_back);
    	titlePic.setImageResource(R.drawable.set_time);
    	titleBtn.setBackgroundResource(R.drawable.title_add);
    	titleBtn.setVisibility(Button.INVISIBLE);    	
    	timeAutoBtn=(ToggleButton)findViewById(R.id.timeAutoBtn);
    	timeHourBtn=(ToggleButton)findViewById(R.id.timeHourBtn);   
    	
    	homeBtn.setOnClickListener(this); 
    	back_ll.setOnClickListener(this);
		timeHourBtn.setOnClickListener(this); 
		timeAutoBtn.setOnClickListener(this);
    	
    	spinnerZone=(Spinner)findViewById(R.id.spinnerZone);     
//    	adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, R.array.timezone_entries);
    	adapter = ArrayAdapter.createFromResource(
    	            this, R.array.timezone_entries, android.R.layout.simple_spinner_item);
		//设置下拉列表的风格  
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);            
		//将adapter 添加到spinner中  
		spinnerZone.setAdapter(adapter);  
//		spinnerZone.setSelection(position);
		//设置默认值  
//		spinnerZone.setVisibility(View.VISIBLE);
		spinnerZone.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                int postion, long id) {
            	  String selectedName = parent.getItemAtPosition(postion).toString();

            	  TabControl.mSQLHelper.updateTimezone(TabControl.writeDB,TabControl.mUid,postion);
            	  String[] ss=new String[2];
            	  ss=selectedName.split("UTC"); 
            	  ss[1]=ss[1].replace("+",""); 
            	  int i=Integer.parseInt(ss[1]);            	
            	  Log.e("SetupTime", ss[0]+" "+ss[1]+" "+i);
            	  TUTKClient.setTimeZone(i);
            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        });
		
//		timeAutoBtn.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					if (timeAutoBtn.isChecked()) {
//						timeAutoBtn.setBackgroundResource(R.drawable.rf_switch_yellow);
////						TUTKClient.setTimeMode(0);
//					} else {
//						timeAutoBtn.setBackgroundResource(R.drawable.rf_switch_blue);
////						TUTKClient.setTimeMode(1);
//					}
//					//状态记录 数据库		
//					
////					 new Thread(){        
////					     @Override  
////					     public void run() { 
////					    	 if	(TUTKClient.setTime()){
////					    		 Log.e("Setuptime", "setTime success");
////					    	 }else{
////					    		 Log.e("Setuptime", "setTime failed"); 
////					    	 }			 
////					     }}.start();   
//				}
//			   });
		
//		timeHourBtn.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					if (timeHourBtn.isChecked()) {
//						timeHourBtn.setBackgroundResource(R.drawable.rf_switch_yellow);
//						TUTKClient.setHourMode(1);
//						TabControl.mSQLHelper.updateHour(TabControl.writeDB,TabControl.mUid,1);
//						} else {
//						timeHourBtn.setBackgroundResource(R.drawable.rf_switch_blue);
//						TUTKClient.setHourMode(0);
//						TabControl.mSQLHelper.updateHour(TabControl.writeDB,TabControl.mUid,0);
//					}
//							
//				}
//			   });
		
		     
		
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		Cursor cursor=TabControl.mSQLHelper.seleteSetup(TabControl.writeDB,TabControl.mUid);
		Log.e("leewoo", "Tabset---onStart->cur:"+cursor.getCount()+" mUid:"+TabControl.mUid);
		if(cursor.getCount()>0){
			int timezone=cursor.getInt(5);
			spinnerZone.setSelection(timezone);
			int hour=cursor.getInt(4);
			
			if (hour==1) {
				Log.e("leewoo", "hour==1");
				timeHourBtn.setChecked(true);
				timeHourBtn.setBackgroundResource(R.drawable.rf_switch_yellow);
			}else{
				Log.e("leewoo", "hour==0");
				timeHourBtn.setChecked(false);
				timeHourBtn.setBackgroundResource(R.drawable.rf_switch_blue);
			}
		}else{
		
		}		
		cursor.close();
	}
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch(v.getId())  
        {  
        case R.id.back_ll:
        case R.id.back:
        	finish();
        	break;
        	
        case R.id.timeAutoBtn:
			if (timeAutoBtn.isChecked()) {
				timeAutoBtn.setBackgroundResource(R.drawable.rf_switch_yellow);
//				TUTKClient.setTimeMode(0);
			} else {
				timeAutoBtn.setBackgroundResource(R.drawable.rf_switch_blue);
//				TUTKClient.setTimeMode(1);
			}
			 break; 
			 
        case R.id.timeHourBtn:
        	if (timeHourBtn.isChecked()) {
				timeHourBtn.setBackgroundResource(R.drawable.rf_switch_yellow);
				TUTKClient.setHourMode(1);
				TabControl.mSQLHelper.updateHour(TabControl.writeDB,TabControl.mUid,1);
				} else {
				timeHourBtn.setBackgroundResource(R.drawable.rf_switch_blue);
				TUTKClient.setHourMode(0);
				TabControl.mSQLHelper.updateHour(TabControl.writeDB,TabControl.mUid,0);
			}
        	break;
            
        default:  
            break;  
       
        }
	}

}

