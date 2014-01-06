package com.seuic.smartgateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.seuic.adapter.CamChoiceAdapter;
import com.seuic.adapter.DevChoiceAdapter;
import com.seuic.add.AddCam;
import com.seuic.add.AddDev;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class SetupCam extends Activity{
	Button titleBtn,homeBtn;
	ImageView titlePic;	
	ListView mListView;
	private CamChoiceAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setup_cam);
		
		homeBtn=(Button)findViewById(R.id.back);
		titlePic=(ImageView)findViewById(R.id.pic);
		titleBtn=(Button)findViewById(R.id.titleBtn);
		mListView = (ListView)findViewById(R.id.camListView);	
    	homeBtn.setBackgroundResource(R.drawable.title_back);
    	titlePic.setImageResource(R.drawable.tab_cam);
    	titleBtn.setBackgroundResource(R.drawable.title_add);
    	
    	
    	
//    	
//    	titleBtn.setOnClickListener(new OnClickListener()
//		{		
//			public void onClick(View source)
//			{
//				Intent intent = new Intent(SetupCam.this
//						, AddCam.class);					
//					//Æô¶¯Activity
//					startActivity(intent);				
//			}			
//		});	
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();		
		List<Map<String, Object>> listItems=new ArrayList<Map<String,Object>>();		
        
		for(int i=0;i<4;i++){
			Map<String, Object> listItem =new HashMap<String,Object>();
			listItem.put("uid", "Cam"+i);
			listItem.put("type", "Cam"+i);
			listItems.add(listItem);	

		}

//		simpleAdapter = new ArrayAdapter<String> (this,android.R.layout.simple_list_item_single_choice,listDev);		
		mAdapter = new CamChoiceAdapter(this,listItems);	
		mAdapter.setItemChecked(1);
		mListView.setAdapter(mAdapter);
	}

}
