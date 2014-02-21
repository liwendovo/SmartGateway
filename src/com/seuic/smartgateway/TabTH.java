package com.seuic.smartgateway ;  

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.seuic.net.TUTKClient;

public class TabTH extends Activity {
	String mUid=null;
	Button titleBtn,homeBtn;
	ImageView titlePic;
	TextView  temp,humi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabth);

		homeBtn=(Button)findViewById(R.id.back);
		titlePic=(ImageView)findViewById(R.id.pic);
		titleBtn=(Button)findViewById(R.id.titleBtn);
		temp=(TextView)findViewById(R.id.textView1);
		humi=(TextView)findViewById(R.id.textView2);
    	homeBtn.setBackgroundResource(R.drawable.ep_logo);
    	titlePic.setImageResource(R.drawable.tab_th);
    	titleBtn.setBackgroundResource(R.drawable.title_chart);
		
    	titleBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			int[] th=new int[4];
//			TUTKClient.getTH(th);
//			temp.setText(th[0]+"."+th[1]+"%");
//			humi.setText(th[2]+"."+th[3]+"C");
			
		}
	});

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		new Thread(){        
		     @Override  
		     public void run() {  
		 		int[] th=new int[4];
		 		TUTKClient.getTH(th);
//		 		temp.setText(th[0]+"."+th[1]+"%");
//		 		humi.setText(th[2]+"."+th[3]+"C");
		     }}.start();   
	}
	
}
  
