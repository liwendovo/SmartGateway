package com.seuic.smartgateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.seuic.adapter.CamChoiceAdapter;

public class SetupCam extends Activity implements android.view.View.OnClickListener{
	Button titleBtn,homeBtn;
	LinearLayout back_ll;
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
    	
    	back_ll=(LinearLayout)findViewById(R.id.back_ll);
    	
    	homeBtn.setOnClickListener(this); 
    	back_ll.setOnClickListener(this); 
    	
//    	
//    	titleBtn.setOnClickListener(new OnClickListener()
//		{		
//			public void onClick(View source)
//			{
//				Intent intent = new Intent(SetupCam.this
//						, AddCam.class);					
//					//启动Activity
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

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch(v.getId())  
        {  
        case R.id.back_ll:
        case R.id.back:
        	finish();
        	break;
        	
                   
        default:  
            break;  
       
        }
	}

}
