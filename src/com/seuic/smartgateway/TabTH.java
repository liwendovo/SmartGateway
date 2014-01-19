package com.seuic.smartgateway ;  

import com.seuic.net.TUTKClient;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class TabTH extends Activity {

	Button titleBtn,homeBtn;
	ImageView titlePic;
	SharedPreferences myPreferences;
	SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabth);

		homeBtn=(Button)findViewById(R.id.back);
		titlePic=(ImageView)findViewById(R.id.pic);
		titleBtn=(Button)findViewById(R.id.titleBtn);
		
    	homeBtn.setBackgroundResource(R.drawable.ep_logo);
    	titlePic.setImageResource(R.drawable.tab_th);
    	titleBtn.setBackgroundResource(R.drawable.title_chart);
		
    	titleBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int[] th=new int[2];
			TUTKClient.getTH(th);
		}
	});
	}
	
}
  
