package com.seuic.devetc;

import com.seuic.smartgateway.R;
import com.seuic.smartgateway.R.layout;
import com.seuic.smartgateway.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;

public class RF_Power extends Activity {
	
	Button  backBtn,leanrnBtn;
	ImageView   devpic;
	ImageView  button1,button2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rf_power);
        backBtn=(Button)findViewById(R.id.back);
		leanrnBtn=(Button)findViewById(R.id.titleBtn);
		button1=(ImageView)findViewById(R.id.button1);
		button2=(ImageView)findViewById(R.id.button2);
		devpic=(ImageView)findViewById(R.id.pic);
		devpic.setImageDrawable(getResources().getDrawable(R.drawable.rf_power));
        
    }

   
}
