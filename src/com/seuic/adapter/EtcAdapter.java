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

import com.seuic.function.ContinuousClick;
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
    String type;
  
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
        type=(String) item.get("type");
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

        }else{
        	
       	 holder.quickBtn2.setVisibility(View.INVISIBLE);
       	
       }
        
        
       holder.quickBtn2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (ContinuousClick.isFastDoubleClick()) {  
				        return;  
				    }
					learnCursor=TabControl.mSQLHelper.seleteBtnLearn(TabControl.writeDB,(Integer) item.get("devid"));
					type=(String) item.get("type");
			        irflag=(String) item.get("irflag");
			        SetBtn();
			        
			        Log.e("EtcAdapter", "devid:"+(Integer) item.get("devid"));
					Log.e("EtcAdapter", "irflag: "+item.get("irflag"));
					Log.e("EtcAdapter", "type: "+item.get("type"));
					Log.e("EtcAdapter", "btnOn="+btnOn+"btnOff="+btnOff);
					Log.e("EtcAdapter", "learnCursor.getBlob(btnOn+3):"+(learnCursor.getBlob(btnOn+3)));
					send(btnOn);
					Log.e("EtcAdapter", "quickBtn1 onclick");
					
				
				}
	   });
       
       
     
        holder.quickBtn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(context,	"Btn1 onclick", Toast.LENGTH_SHORT).show();if(irflag.equals("rf"))if(irflag.equals("rf"))
				 if (ContinuousClick.isFastDoubleClick()) {  
				        return;  
				    }  
				learnCursor=TabControl.mSQLHelper.seleteBtnLearn(TabControl.writeDB,(Integer) item.get("devid"));

		        type=(String) item.get("type");
		        irflag=(String) item.get("irflag");
		        SetBtn();
				Log.e("EtcAdapter", "devid:"+(Integer) item.get("devid"));
				Log.e("EtcAdapter", "irflag: "+item.get("irflag"));
				Log.e("EtcAdapter", "type: "+item.get("type"));
				Log.e("TUTKClient", "learnCursor.getBlob(btnOff+3):"+(learnCursor.getBlob(btnOff+3)));
				Log.e("EtcAdapter", "btnOn"+btnOn+"btnOff"+btnOff);
				send(btnOff);
				Log.e("EtcAdapter", "quickBtn2 onclick");
				
			}
		});
        
        
       
    	
		if(learnCursor.getCount()>0){				 
			//学习	
			
			 
			  SetBtn();
			  Log.e("EtcAdapter", "btnOn"+btnOn+"btnOff"+btnOff);
				
			  if(learnCursor.getBlob(btnOn+3)!=null)
				  TabControl.mViewSelected.imageviewClickRecover(holder.quickBtn2);
				
			  if(learnCursor.getBlob(btnOff+3)!=null)
				  TabControl.mViewSelected.imageviewClickRecover(holder.quickBtn1);
		}else{
			Log.e("leewoo", "cur learn 初始化"+learnCursor.getCount());
			//未初始化
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
    
    private void send(final int btnid){  
		 new Thread(){        
		     @Override  
		     public void run() {  
				if(irflag.equals("rf"))	TUTKClient.send(learnCursor.getBlob(btnid+3),false);
				else TUTKClient.send(learnCursor.getBlob(btnid+3),true);
		     }}.start();      
		 }
    
    
    private void SetBtn()
    {
    	
    	
    	 if(irflag.equals("rf")){
			 if(type.equals("CUSTOM1")){
				 btnOff=9;
			 }else if(type.equals("CUSTOM2")){
				 btnOn=2;
				 btnOff=4;
			 }else{
				 btnOn=0;
				 btnOff=1;
			 }
			 
	  }else{
		  
		  if(type.equals("TV")){
			     btnOff=12;
			 }else if(type.equals("AC")){
				 btnOn=9;
				 btnOff=10;
			 }else if(type.equals("MEDIA")){//media
				 btnOff=8;
			 }else if(type.equals("STU")){
				 btnOff=12;	
			 }else if(type.equals("WH")){
				 btnOn=2;
				 btnOff=3;
			 }else if(type.equals("DVD")){
				 btnOff=12;
			 }else if(type.equals("FAN")){
				 btnOff=3;
			 }else if(type.equals("CUSTOM1")){
				 btnOff=9;
			 }else {
				 btnOn=2;
				 btnOff=4;
			 }	
		  Log.e("EtcAdapter", "btnOn= "+btnOn+" btnOff= "+btnOff);
	  }
    }
    
}
