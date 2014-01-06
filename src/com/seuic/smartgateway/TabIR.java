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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.seuic.adapter.EtcAdapter;
import com.seuic.add.AddEtc;
import com.seuic.devetc.IR_AC;
import com.seuic.devetc.IR_DVD;
import com.seuic.devetc.IR_FAN;
import com.seuic.devetc.IR_Media;
import com.seuic.devetc.IR_STU;
import com.seuic.devetc.IR_Selfdefine1;
import com.seuic.devetc.IR_Selfdefine2;
import com.seuic.devetc.IR_TV;
import com.seuic.devetc.IR_WH;

public class TabIR extends Activity {
	Button titleBtn,homeBtn;
	ImageView titlePic;	
	String mUid=null;	
	ListView listViewIR;
	EtcAdapter irAdapter;
	SharedPreferences myPreferences;
	SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabir);		
		homeBtn=(Button)findViewById(R.id.back);
		titlePic=(ImageView)findViewById(R.id.pic);
		titleBtn=(Button)findViewById(R.id.titleBtn);
		
    	homeBtn.setBackgroundResource(R.drawable.ep_logo);
    	titlePic.setImageResource(R.drawable.tab_ir);
    	titleBtn.setBackgroundResource(R.drawable.title_add);
    	
    	
    	
		myPreferences= getSharedPreferences("devset", Activity.MODE_PRIVATE);
		titleBtn.setOnClickListener(new OnClickListener()
		{		
			public void onClick(View source){				 
				 Intent intent = new Intent(TabIR.this, AddEtc.class);	
				 intent.putExtra("uid", mUid);
				 intent.putExtra("type", "ir");
				 startActivity(intent);	            	
			}			
		});	
		

		
		listViewIR = (ListView)findViewById(R.id.listViewIR);
		
		listViewIR.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				 String type=(String)irAdapter.getItem(position).get("type");
				 Log.e("leewoo", "type="+type);
				 int devid=Integer.parseInt(String.valueOf(irAdapter.getItem(position).get("devid")));
				 Intent intent ;
				 if(type.equals("TV")){
					 intent = new Intent(TabIR.this,IR_TV.class);	
				 }else if(type.equals("AC")){
					 intent = new Intent(TabIR.this,IR_AC.class);
				 }
				 
//				 else if(type.equals(TabControl.itemsIR[2])){
//					 intent = new Intent(TabIR.this,IR_Media.class);
//				 }
				 
				 else if(type.equals("STU")){
					 intent = new Intent(TabIR.this,IR_STU.class);
				 }
			else if(type.equals("WH")){
					 intent = new Intent(TabIR.this,IR_WH.class);
				 }
			else{
					 intent = new Intent(TabIR.this,IR_DVD.class);
				 }
				 
				 
//			else (type.equals("DVD")){
//					 intent = new Intent(TabIR.this,IR_FAN.class);
//				 }
				 
//				 else if(type.equals(TabControl.itemsIR[7])){
//					 intent = new Intent(TabIR.this,IR_Selfdefine1.class);
//				 }else {
//					 intent = new Intent(TabIR.this,IR_Selfdefine2.class);
//				 }	
				 
				 
				 intent.putExtra("uid",  mUid);
				 intent.putExtra("devid", devid);
				 startActivity(intent);	
			}
			});
		listViewIR.setOnTouchListener(new OnTouchListener() {  
			
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
		        		 if(position1>=0&&position1<irAdapter.getCount()){	
			        		 Log.e("leewoo", "�һ�"+position1);
			        		 final int position=position1;
			        		 AlertDialog.Builder builder = new Builder(TabIR.this);
			        		 builder.setMessage("ȷ��ɾ���豸��"+irAdapter.getItem(position).get("type"));
			        		 builder.setTitle("ȷ����Ϣ");
			        		 builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				        		@Override
				        		public void onClick(DialogInterface arg0, int arg1) {
				        			// TODO Auto-generated method stub	
					        		 TabControl.mSQLHelper.deleteList(TabControl.writeDB, Integer.parseInt(String.valueOf(irAdapter.getItem(position).get("devid"))));
					        		 irAdapter.remove(position);	
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
		        		 }
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
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.e("leewoo","TabIR-->onStart");
		Log.e("leewoo","TabIR-->onResume");
		mUid=myPreferences.getString("uid", "NULL");	
		if (mUid.equals("NULL")) {
			Toast.makeText(getApplicationContext(),"�豸Ϊ���ã��뵽Set��������豸", Toast.LENGTH_SHORT).show();		
		}
		Log.e("leewoo","mUid="+mUid);
		Cursor cur=TabControl.mSQLHelper.seleteListClass(TabControl.writeDB, mUid,"ir");
		Log.e("leewoo","count="+cur.getCount());
//		if(0==cur.getCount()){
//			Log.e("leewoo","count="+0);
//			return;
//		}			
		List<Map<String, Object>> listItemsIR=new ArrayList<Map<String,Object>>();	
		for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
			Map<String,Object> listItem =new HashMap<String,Object>();			
			listItem.put("name", cur.getString(4));
			String type=cur.getString(3);
			
//			itemsIR = {"TV", "AC","Media","STU","WH", "DVD","FAN","�Զ���1","�Զ���2"};
			 listItem.put("type",type);
			 if(type.equals("TV")){
				 listItem.put("icon", R.drawable.ir_logo_tv);
				 listItem.put("status", R.drawable.ir_logo_close);
			 }else if(type.equals("AC")){
				 listItem.put("icon", R.drawable.ir_logo_ac);
				 listItem.put("status", R.drawable.ir_logo_close);
			 }else if(type.equals("Media")){//media
				 listItem.put("icon", R.drawable.ir_logo_stu);
				 listItem.put("status", R.drawable.ir_logo_close);
			 }
			 else if(type.equals("STU")){
				 listItem.put("icon", R.drawable.ir_logo_stu);
				 listItem.put("status", R.drawable.ir_logo_close);			
			 }else if(type.equals("WH")){
				 listItem.put("icon",R.drawable.ir_logo_wh );
				 listItem.put("status", R.drawable.ir_logo_up);
				 listItem.put("status2", R.drawable.ir_logo_down);
			 }else if(type.equals("DVD")){
				 listItem.put("icon", R.drawable.ir_logo_dvd);
				 listItem.put("status", R.drawable.ir_logo_close);
			 }else if(type.equals("FAN")){
				 listItem.put("icon",  R.drawable.ir_logo_fan1);
				 listItem.put("status", R.drawable.ir_logo_close);
			 }else if(type.equals("�Զ���1")){
				 listItem.put("icon",  R.drawable.ir_logo_fan2);
				 listItem.put("status", R.drawable.ir_logo_close);
			 }else {
				 listItem.put("icon",R.drawable.ir_logo_fan2 );
				 listItem.put("status", R.drawable.ir_logo_close);
			 }	

			listItem.put("devid", cur.getInt(1));
			listItemsIR.add(listItem);
		}	
		cur.close();
		//list����  ��Ҫ�Ż�
//		if (!listItemsIR.isEmpty()) {    
//	    	 Collections.sort(listItemsIR, new Comparator<Map<String, Object>>() {
//	     	@Override
//	     	public int compare(Map<String, Object> object1,
//	     	Map<String, Object> object2) {
//			//�����ı�����
//	        return ((String) object1.get("name")).compareTo((String) object2.get("name"));
//	     	}    
//	       });    
//	     }	
		irAdapter=new EtcAdapter(this, listItemsIR);
		listViewIR.setAdapter(irAdapter);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
				
	}	

}
  
