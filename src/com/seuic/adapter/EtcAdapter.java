/*
 * Copyright (C) 2013 47 Degrees, LLC
 *  http://47deg.com
 *  hello@47deg.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.seuic.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seuic.net.TUTKClient;
import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;
     

public class EtcAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private Context context; 
    Cursor learnCursor;
    int btnOn;
    int btnOff;
    String irflag;
  
    public EtcAdapter(Context context, List<Map<String, Object>> data) {
        this.context = context;
        this.data = data;       
    }

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}
	
	public void remove(int index) {
		if (index < 0)
			return;
		data.remove(index);		
		notifyDataSetChanged();
	}
	@Override
    public int getCount() {
        return data.size();
    }

    @Override
	public Map<String, Object> getItem(int position) {
		// TODO Auto-generated method stub
    	return data.get(position);
	}

	@Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    
		final Map<String, Object> item =getItem(position);
		Log.e("EtcAdapter","position="+position);
        final ViewHolder holder;
        learnCursor=TabControl.mSQLHelper.seleteBtnLearn(TabControl.writeDB,(Integer) item.get("devid"));
        String type=(String) item.get("type");
        irflag=(String) item.get("irflag");
		
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.item_list, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.quickBtn1 = (ImageView) convertView.findViewById(R.id.status);
            holder.quickBtn2 = (ImageView) convertView.findViewById(R.id.status2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
         
        holder.title.setText(item.get("name").toString());
        holder.icon.setImageResource((Integer) item.get("icon"));
        holder.quickBtn1.setImageResource((Integer) item.get("status")); 
        TabControl.mViewSelected.imageviewClickGreyChanged(holder.quickBtn1);
    	TabControl.mViewSelected.setImageViewClickChanged(holder.quickBtn2);
    	TabControl.mViewSelected.setImageViewClickChanged(holder.quickBtn1);
    	
        if(item.get("status2") != null){
	        holder.quickBtn2.setVisibility(View.VISIBLE);
	        holder.quickBtn2.setImageResource((Integer) item.get("status2"));   
	        TabControl.mViewSelected.imageviewClickGreyChanged(holder.quickBtn2);
	        
//	        if(learnCursor.getCount()>0){				 
//				//ѧϰ		 
//				 if(learnCursor.getBlob(btnOff+3)!=null){
//					holder.quickBtn2.setImageResource((Integer) item.get("status2")); 
//				 }
//				
//			}else{
//				Log.e("leewoo", "cur learn ��ʼ��"+learnCursor.getCount());
//				//δ��ʼ��
//				TabControl.mSQLHelper.insertBtnLearn(TabControl.writeDB,TabControl.mUid,(Integer) item.get("devid"));
//			}		
//	        
        }else{
        	
       	 holder.quickBtn2.setVisibility(View.INVISIBLE);
       	
       }
        
        
     holder.quickBtn2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					Toast.makeText(context,	"Btn2 onclick", Toast.LENGTH_SHORT).show();
					learnCursor=TabControl.mSQLHelper.seleteBtnLearn(TabControl.writeDB,(Integer) item.get("devid"));
//					Log.e("EtcAdapter", "devid:"+(Integer) item.get("devid"));
//					Log.e("EtcAdapter", "learnCursor.getBlob(btnOn+3):"+(learnCursor.getBlob(btnOn+3)));
					if(irflag.equals("rf"))	TUTKClient.send(learnCursor.getBlob(btnOn+3),false);
					else TUTKClient.send(learnCursor.getBlob(btnOn+3),true);
					Log.e("EtcAdapter", "quickBtn1 onclick");
					
				
				}
			});
       
       
     
        holder.quickBtn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(context,	"Btn1 onclick", Toast.LENGTH_SHORT).show();if(irflag.equals("rf"))if(irflag.equals("rf"))
				learnCursor=TabControl.mSQLHelper.seleteBtnLearn(TabControl.writeDB,(Integer) item.get("devid"));
//				Log.e("EtcAdapter", "devid:"+(Integer) item.get("devid"));
//				 Log.e("TUTKClient", "learnCursor.getBlob(btnOff+3):"+(learnCursor.getBlob(btnOff+3)));
					if(irflag.equals("rf"))	TUTKClient.send(learnCursor.getBlob(btnOff+3),false);
					else TUTKClient.send(learnCursor.getBlob(btnOff+3),true);
					Log.e("EtcAdapter", "quickBtn2 onclick");
				
			}
		});
        
        
       
    	Log.e("EtcAdapter","devid="+(Integer) item.get("devid"));
		Log.e("EtcAdapter", "cur: "+learnCursor.getCount());
		Log.e("EtcAdapter", "type: "+item.get("type"));
		if(learnCursor.getCount()>0){				 
			//ѧϰ	
			
			  if(irflag.equals("rf")){
					 if(type.equals("CUSTOM1")){
						 btnOn=9;
						 btnOff=1;
					 }else if(type.equals("CUSTOM2")){
						 btnOn=2;
						 btnOff=4;
					 }else{
						 btnOn=0;
						 btnOff=1;
					 }
					 
				
				
			  }else{
				  
				  if(type.equals("TV")){
					     btnOn=12;
					 }else if(type.equals("AC")){
						 btnOn=9;
						 btnOff=10;
					 }else if(type.equals("MEDIA")){//media
						 btnOn=8;
					 }else if(type.equals("STU")){
						 btnOn=12;	
					 }else if(type.equals("WH")){
						 btnOn=2;
						 btnOff=3;
					 }else if(type.equals("DVD")){
						 btnOn=12;
					 }else if(type.equals("FAN")){
						 btnOn=3;
					 }else if(type.equals("CUSTOM1")){
						 btnOn=9;
						 btnOff=1;
					 }else {
						 btnOn=2;
						 btnOff=4;
					 }	
				  
				  
			  }
				
			  if(learnCursor.getBlob(btnOn+3)!=null)
				  TabControl.mViewSelected.imageviewClickRecover(holder.quickBtn2);
				
			  if(learnCursor.getBlob(btnOff+3)!=null)
				  TabControl.mViewSelected.imageviewClickRecover(holder.quickBtn1);
		}else{
			Log.e("leewoo", "cur learn ��ʼ��"+learnCursor.getCount());
			//δ��ʼ��
			TabControl.mSQLHelper.insertBtnLearn(TabControl.writeDB,TabControl.mUid,(Integer) item.get("devid"));
		}		

        return convertView;
    }

    static class ViewHolder {
        TextView title;
        ImageView icon;
        ImageView quickBtn1;
        ImageView quickBtn2;
    }
    
}
