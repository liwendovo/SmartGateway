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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seuic.smartgateway.R;
     

public class EtcAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private Context context; 
  
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
        ViewHolder holder;
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
        
             	
         
        holder.title.setText(item.get("type").toString());
        holder.icon.setImageResource((Integer) item.get("icon"));
        holder.quickBtn1.setImageResource((Integer) item.get("status")); 
        if(item.get("status2") != null){
        holder.quickBtn2.setVisibility(View.VISIBLE);
        holder.quickBtn2.setImageResource((Integer) item.get("status2"));  
       
        	holder.quickBtn2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					Toast.makeText(context,	"Btn2 onclick", Toast.LENGTH_SHORT).show();
					Log.e("leewoo", "quickBtn1 onclick");
					
				}
			});
        }else{
        	
        	 holder.quickBtn2.setVisibility(View.INVISIBLE);
        	
        }
       
     
        holder.quickBtn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(context,	"Btn1 onclick", Toast.LENGTH_SHORT).show();
				Log.e("leewoo", "quickBtn2 onclick");
			}
		});

        return convertView;
    }

    static class ViewHolder {
        TextView title;
        ImageView icon,quickBtn1,quickBtn2;
    }
    
}
