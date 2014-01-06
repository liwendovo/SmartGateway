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
	        		
	        	//当按下时处理  
	        	if (event.getAction() == MotionEvent.ACTION_DOWN) {  
	        	//设置背景为选中状态  
//	        	v.setBackgroundResource(R.drawable.mm_listitem_pressed);  
		        	//获取按下时的x轴坐标  
		        	x = event.getX();  
		        	y = event.getY();  
	        	} else if (event.getAction() == MotionEvent.ACTION_UP) {// 松开处理  
	        	//设置背景为未选中正常状态  
//	        	v.setBackgroundResource(R.drawable.mm_listitem_simple);  
	        	//获取松开时的x坐标  
	        	ux = event.getX(); 
	        	uy = event.getY();
	        	int position1 = ((ListView) v).pointToPosition((int) x, (int) y);  
                int position2 = ((ListView) v).pointToPosition((int) ux,(int) uy);
                Log.e("leewoo", "onTouch :"+position1+" "+position2);
	        	//按下和松开绝对值差当大于20时显示删除按钮，否则不显示  
		        	if (position1 == position2 &&Math.abs(x - ux) > 20) {  
		        		 if(position1>=0&&position1<irAdapter.getCount()){	
			        		 Log.e("leewoo", "右滑"+position1);
			        		 final int position=position1;
			        		 AlertDialog.Builder builder = new Builder(TabIR.this);
			        		 builder.setMessage("确认删除设备？"+irAdapter.getItem(position).get("type"));
			        		 builder.setTitle("确认信息");
			        		 builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				        		@Override
				        		public void onClick(DialogInterface arg0, int arg1) {
				        			// TODO Auto-generated method stub	
					        		 TabControl.mSQLHelper.deleteList(TabControl.writeDB, Integer.parseInt(String.valueOf(irAdapter.getItem(position).get("devid"))));
					        		 irAdapter.remove(position);	
					        		 //数据库
				        			}
			        		 	});
			        		 builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
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
//	        	else if (event.getAction() == MotionEvent.ACTION_MOVE) {//当滑动时背景为选中状态  
////	        	v.setBackgroundResource(R.drawable.mm_listitem_pressed);  
////	        		 Log.e("leewoo", "onTouch ACTION_MOVE: "+position);
//	        	} else {//其他模式  
//	        	//设置背景为未选中正常状态  
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
			Toast.makeText(getApplicationContext(),"设备为设置，请到Set界面添加设备", Toast.LENGTH_SHORT).show();		
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
			
//			itemsIR = {"TV", "AC","Media","STU","WH", "DVD","FAN","自定义1","自定义2"};
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
			 }else if(type.equals("自定义1")){
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
		//list排序  需要优化
//		if (!listItemsIR.isEmpty()) {    
//	    	 Collections.sort(listItemsIR, new Comparator<Map<String, Object>>() {
//	     	@Override
//	     	public int compare(Map<String, Object> object1,
//	     	Map<String, Object> object2) {
//			//根据文本排序
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
  
