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

import android.R.integer;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.seuic.smartgateway.ControlBox;
import com.seuic.smartgateway.R;
import com.seuic.swipelistview.SwipeListView;

public class EtcAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private Context context;
    private SwipeListView mSwipeListView ;
  

    public EtcAdapter(Context context, List<Map<String, Object>> data, SwipeListView mSwipeListView) {
        this.context = context;
        this.data = data;
        this.mSwipeListView = mSwipeListView ;
		
    }

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
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
            convertView = li.inflate(R.layout.row_etc, parent, false);
            holder = new ViewHolder();
            holder.icon=(ImageView)convertView.findViewById(R.id.icon);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.lastpress=(ImageView)convertView.findViewById(R.id.lastpress);
            holder.delete = (Button) convertView.findViewById(R.id.deleteBtn);          
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        ((SwipeListView)parent).recycle(convertView, position);

        holder.icon.setImageDrawable((Drawable)item.get("icon"));
        holder.title.setText(item.get("content").toString());
        holder.lastpress.setImageDrawable((Drawable)item.get("lastpress"));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            	mSwipeListView.closeAnimate(position);
//				mSwipeListView.dismiss(position);
            	int devid=Integer.parseInt(String.valueOf(getItem(position).get("devid")));
            	ControlBox.mSQLHelper.deleteList(ControlBox.writeDB,devid);
				data.remove(position);
				
				notifyDataSetChanged();
				if (mSwipeListView != null)
					mSwipeListView.closeOpenedItems();				
				
				
				//share中的怎么办 。。。不用管了 
			        
            }
        });

  


        return convertView;
    }

    static class ViewHolder {
        ImageView icon;
        TextView title;
        ImageView lastpress;
        Button delete;        
    }
    



}
