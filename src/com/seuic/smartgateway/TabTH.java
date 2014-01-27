package com.seuic.smartgateway ;  

import com.seuic.net.TUTKClient;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TabTH extends Activity {

	Button titleBtn,homeBtn;
	ImageView titlePic;
	TextView  temp,humi;
	SharedPreferences myPreferences;
	SharedPreferences.Editor editor;
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
			int[] th=new int[4];
			TUTKClient.getTH(th);
			temp.setText(th[0]+"."+th[1]+"%");
			humi.setText(th[2]+"."+th[3]+"C");
			
		}
	});
	}
	
}
  
