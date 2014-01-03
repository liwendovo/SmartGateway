package com.seuic.smartgateway ;  

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.seuic.adapter.EtcAdapter;
import com.seuic.add.AddEtc;
import com.seuic.devetc.RF_Curtain1;
import com.seuic.devetc.RF_Lamp;
import com.seuic.devetc.RF_Selfdefine1;
import com.seuic.devetc.RF_Selfdefine2;
import com.seuic.devetc.RF_Switch;
import com.seuic.devetc.RF_WH;


public class TabRF extends Activity {
	Button titleBtn,homeBtn;
	TextView titleTxt;
	
	String mUid=null;

	ListView listViewRF;
	EtcAdapter rfAdapter;
	SharedPreferences myPreferences;
	SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabrf);
		myPreferences= getSharedPreferences("devset", Activity.MODE_PRIVATE);
		homeBtn=(Button)findViewById(R.id.back);
//		titleTxt = (TextView)findViewById(R.id.titleTxt);
		titleBtn=(Button)findViewById(R.id.titleBtn);	
//		homeBtn.setText("Home");
//		titleTxt.setText("RF");
//		titleBtn.setText("ADD");
		titleBtn.setOnClickListener(new OnClickListener()
		{	
			@Override	
			public void onClick(View source){				 
				 Intent intent = new Intent(TabRF.this, AddEtc.class);	
        		 intent.putExtra("uid", mUid);
				 intent.putExtra("type", "rf");
				 startActivity(intent);	
            }			
		});	
	
		listViewRF = (ListView)findViewById(R.id.listViewRF);
		listViewRF.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				 String type=(String)rfAdapter.getItem(position).get("type");
				 int devid=Integer.parseInt(String.valueOf(rfAdapter.getItem(position).get("devid")));
				 Intent intent ;
				 if(type.equals(TabControl.itemsRF[0])){
					 intent = new Intent(TabRF.this,RF_Switch.class);
				 }else if(type.equals(TabControl.itemsRF[1])){
					 intent = new Intent(TabRF.this,RF_WH.class);
				 }else if(type.equals(TabControl.itemsRF[2])){
					 intent = new Intent(TabRF.this,RF_Lamp.class);
				 }else if(type.equals(TabControl.itemsRF[3])){
					 intent = new Intent(TabRF.this,RF_Curtain1.class);				 
				 }else if(type.equals(TabControl.itemsRF[4])){
					 intent = new Intent(TabRF.this,RF_Selfdefine1.class);
				 }else {
					 intent = new Intent(TabRF.this,RF_Selfdefine2.class);
				 }						 
				 intent.putExtra("uid",  mUid);
				 intent.putExtra("devid", devid);
				 startActivity(intent);	
			}
			
		});
		
		listViewRF.setOnTouchListener(new OnTouchListener() {  
			
			 private float x,ux,y,uy;
	        	public boolean onTouch(View v, MotionEvent event) {  
	        		
	        	//������ʱ����  
	        	if (event.getAction() == MotionEvent.ACTION_DOWN) {  
	        	//���ñ���Ϊѡ��״̬  
//	        	v.setBackgroundResource(R.drawable.mm_listitem_pressed);  
		        	//��ȡ����ʱ��x������  
		        	x = event.getX();  
		        	y = event.getY();  
	        	} else if (event.getAction() == MotionEvent.ACTION_UP) {// �ɿ�����  
	        	//���ñ���Ϊδѡ������״̬  
//	        	v.setBackgroundResource(R.drawable.mm_listitem_simple);  
	        	//��ȡ�ɿ�ʱ��x����  
	        	ux = event.getX(); 
	        	uy = event.getY();
	        	int position1 = ((ListView) v).pointToPosition((int) x, (int) y);  
               int position2 = ((ListView) v).pointToPosition((int) ux,(int) uy);
               Log.e("leewoo", "onTouch :"+position1+" "+position2);
	        	//���º��ɿ�����ֵ�����20ʱ��ʾɾ����ť��������ʾ  
		        	if (position1 == position2 &&Math.abs(x - ux) > 20) {  
		        		        		
		        		 Log.e("leewoo", "�һ�"+position1);
		        		 final int position=position1;
		        		 AlertDialog.Builder builder = new Builder(TabRF.this);
		        		 builder.setMessage("ȷ��ɾ���豸��"+rfAdapter.getItem(position).get("type"));
		        		 builder.setTitle("ȷ����Ϣ");
		        		 builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			        		@Override
			        		public void onClick(DialogInterface arg0, int arg1) {
			        			// TODO Auto-generated method stub		        		
				        		
				        		 TabControl.mSQLHelper.deleteList(TabControl.writeDB, Integer.parseInt(String.valueOf(rfAdapter.getItem(position).get("devid"))));
				        		 rfAdapter.remove(position);	
				        		 //���ݿ�
				        		
			        		}
			        	});
		        		 builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			        		@Override
			        		 public void onClick(DialogInterface dialog, int which) {
			        		 dialog.dismiss();
			        		  
			        		}
			        	});
		        		 builder.create().show();	
		        		 return true;  
		        	 } 
	        	} 
//	        	else if (event.getAction() == MotionEvent.ACTION_MOVE) {//������ʱ����Ϊѡ��״̬  
////	        	v.setBackgroundResource(R.drawable.mm_listitem_pressed);  
////	        		 Log.e("leewoo", "onTouch ACTION_MOVE: "+position);
//	        	} else {//����ģʽ  
//	        	//���ñ���Ϊδѡ������״̬  
////	        	v.setBackgroundResource(R.drawable.mm_listitem_simple);  
//	        	}  
	        	return false;  
	         }  
	        }); 
	}
  
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mUid=myPreferences.getString("uid", "NULL");	
		if (mUid.equals("NULL")) {
			Toast.makeText(getApplicationContext(),"�豸Ϊ���ã��뵽Set��������豸", Toast.LENGTH_SHORT).show();		
		}
		Log.e("leewoo","mUid="+mUid);
		Cursor cur=TabControl.mSQLHelper.seleteListClass(TabControl.writeDB, mUid,"rf");
		if(0==cur.getCount()){
			Log.e("leewoo","count="+0);
			return;
		}	
		List<Map<String, Object>> listItemsRF=new ArrayList<Map<String,Object>>();	
		for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
			Map<String,Object> listItem =new HashMap<String,Object>();	
		
			listItem.put("name", cur.getString(4));
			listItem.put("type", cur.getString(3));
			listItem.put("devid", cur.getInt(1));
			listItemsRF.add(listItem);
		}	
		cur.close();
		//list����  ��Ҫ�Ż�
//		if (!listItemsRF.isEmpty()) {    
//	    	 Collections.sort(listItemsRF, new Comparator<Map<String, Object>>() {
//	     	@Override
//	     	public int compare(Map<String, Object> object1,
//	     	Map<String, Object> object2) {
//			//�����ı�����
//	          	return ((String) object1.get("name")).compareTo((String) object2.get("name"));
//	     	}    
//	    	 });    
//	     }	
		rfAdapter=new EtcAdapter(this, listItemsRF);
		listViewRF.setAdapter(rfAdapter);
	}
}
  
