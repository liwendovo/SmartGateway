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

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.seuic.smartgateway.SetupDev;
import com.seuic.smartgateway.R;
import com.seuic.smartgateway.TabControl;


public class CamChoiceAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private Context context; 
    int  currentID = -1;
    
//    StatusListener mStatusListener;
    private float x,ux;
    public CamChoiceAdapter(Context context, List<Map<String, Object>> data) {
        this.context = context;
        this.data = data;       
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
            convertView = li.inflate(R.layout.item_list, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.status = (ImageView) convertView.findViewById(R.id.status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        if(position==currentID)        	
            holder.status.setImageResource(R.drawable.dev_on);
        else        	
        	holder.status.setImageResource(R.drawable.dev_off);
    
        holder.title.setText(item.get("uid").toString());
        holder.icon.setImageResource(R.drawable.dev_icon);
        
     
        
        convertView.setOnTouchListener(new OnTouchListener() {  
        	public boolean onTouch(View v, MotionEvent event) {  
//        	final ViewHolder holder = (ViewHolder) v.getTag();  
        	//������ʱ����  
        	if (event.getAction() == MotionEvent.ACTION_DOWN) {  
        	//���ñ���Ϊѡ��״̬  
//        	v.setBackgroundResource(R.drawable.mm_listitem_pressed);  
	        	//��ȡ����ʱ��x������  
	        	x = event.getX();  
        	} else if (event.getAction() == MotionEvent.ACTION_UP) {// �ɿ�����  
        	//���ñ���Ϊδѡ������״̬  
//        	v.setBackgroundResource(R.drawable.mm_listitem_simple);  
        	//��ȡ�ɿ�ʱ��x����  
        	ux = event.getX();          	
        	//���º��ɿ�����ֵ�����20ʱ��ʾɾ����ť��������ʾ  
	        	if (Math.abs(x - ux) > 25) {  
	        		 Toast.makeText(context,"�һ�"+position, Toast.LENGTH_SHORT).show();	        		
	        		 Log.e("leewoo", "�һ�"+position);

	        		 AlertDialog.Builder builder = new Builder(context);
	        		 builder.setMessage("ȷ��ɾ���豸��");
	        		 builder.setTitle("ȷ����Ϣ");
	        		 builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
		        		@Override
		        		public void onClick(DialogInterface arg0, int arg1) {
		        			// TODO Auto-generated method stub
		        			if(position>=0&&position<data.size()){
		        			if(currentID==position){
		        				currentID=-1;	
		        				SetupDev.editor.putString("uid","NULL");
		        				SetupDev.editor.commit();
		        			} 
//		        			TabControl.mSQLHelper.deleteSetup(TabControl.writeDB, data.get(position).get("uid").toString());
				 			data.remove(position);	
			        		notifyDataSetChanged();
		        			}
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
	        		 
	        		 
	        	 
        	 
        	} else if (event.getAction() == MotionEvent.ACTION_MOVE) {//������ʱ����Ϊѡ��״̬  
//        	v.setBackgroundResource(R.drawable.mm_listitem_pressed);  
//        		 Log.e("leewoo", "onTouch ACTION_MOVE: "+position);
        	} else {//����ģʽ  
        	//���ñ���Ϊδѡ������״̬  
//        	v.setBackgroundResource(R.drawable.mm_listitem_simple);  
        	}  
        	return true;  
         }  
        }); 
        holder.status.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				currentID=position;  
//				SetupDev.editor.putString("uid",data.get(position).get("uid").toString());
//				SetupDev.editor.commit();
				notifyDataSetChanged();
			}
		});
//        convertView.setOnClickListener(new OnClickListener() {
//            
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
////                mActivity.openActivity();            	
//            	currentID=position;            	
//            }
//        });
  
        return convertView;
    }

    
    static class ViewHolder {
        TextView title;
        ImageView icon,status;
    }
    
	public void setItemChecked(int setID) {
		currentID = setID;
	}
	
	
	
//	class OnStatusClickListener implements OnClickListener{
//		int position;
//		
//		public OnStatusClickListener(int position){
//			this.position = position;
//		}
//
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			mStatusListener.onDeleteClicked(position);
//			currentID=position;  
//			notifyDataSetChanged();
//		}
//		
//	}
//		
//	public interface StatusListener {
//
//		void onDeleteClicked(int index);
//	}
//	
//	public void setDeleteListener(StatusListener mListener) {
//		// TODO Auto-generated method stub
//		mStatusListener = mListener;
//	}

}
