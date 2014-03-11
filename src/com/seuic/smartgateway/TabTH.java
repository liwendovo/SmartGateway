package com.seuic.smartgateway ;  

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.seuic.net.TUTKClient;

public class TabTH extends Activity {
	String mUid=null;
	Button titleBtn,homeBtn;
	ImageView titlePic;
	TextView  temp,humi;
	
	
	Handler mhandler=new Handler(){
	     public void handleMessage(Message msg){
	         System.out.println("mhandler");
	         int[] th=new int[4];
	         TUTKClient.cancellearn(false);
	 		 TUTKClient.getTH(th);
	 		 if(th[2]>=0){
		 		humi.setText(th[2]+"%");
		 		if(!TabControl.tempmode) temp.setText(th[0]+"℃");
		 		else temp.setText(th[0]+"℉");
	 		 }
	         Log.e("TabTH", "end onResume");
   	    }
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabth);

		homeBtn=(Button)findViewById(R.id.back);
		titlePic=(ImageView)findViewById(R.id.pic);
		titleBtn=(Button)findViewById(R.id.titleBtn);
		humi=(TextView)findViewById(R.id.textView1);
		temp=(TextView)findViewById(R.id.textView2);
    	homeBtn.setBackgroundResource(R.drawable.ep_logo);
    	titlePic.setImageResource(R.drawable.tab_th);
    	titleBtn.setBackgroundResource(R.drawable.title_chart);
    	Log.e("TabTH", "TabTH init " ); 
    	
    	

    	
    	
//    	 Log.e("TabTH", "start onCreate");
//         int[] th=new int[4];
// 		 TUTKClient.getTH(th);
// 		 if(th[2]>=0){
//	 		humi.setText(th[2]+"%");
//	 		if(!TabControl.tempmode) temp.setText(th[0]+"℃");
//	 		else temp.setText(th[0]+"℉");
// 		 }
//         Log.e("TabTH", "end onCreate");
//    	
    	
    	
    	
//    	final Handler handler = new Handler();  
//        Runnable runnable = new Runnable(){  
//            @Override  
//            public void run() {  
//                // TODO Auto-generated method stub  
//                // 在此处添加执行的代码  
//            	
//            	int[] th=new int[4];
//        		TUTKClient.getTH(th);
//        		humi.setText(th[2]+"."+th[2]+"%");
//        		temp.setText(th[0]+"."+th[1]+"C");
//        		
//        		Log.e("TabTH", "TabTH end" ); 
//                handler.postDelayed(this, 1000);// 50是延时时长  
//            }   
//        };   
//        handler.postDelayed(runnable, 1000);// 打开定时器，执行操作  
//        handler.removeCallbacks((Runnable) this);// 关闭定时器处理 
    	

		
    	titleBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			int[] th=new int[4];
//			TUTKClient.getTH(th);
//			temp.setText(th[0]+"."+th[1]+"%");
//			humi.setText(th[2]+"."+th[3]+"C");
			
		}
	});

	}
	
	
	 @Override
	 protected void onResume() {
	 
		         super.onResume();	 
		         Log.e("TabTH", "start onResume");
//		         int[] th=new int[4];
//		         TUTKClient.cancellearn(false);
//		 		 TUTKClient.getTH(th);
//		 		 if(th[2]>=0){
//			 		humi.setText(th[2]+"%");
//			 		if(!TabControl.tempmode) temp.setText(th[0]+"℃");
//			 		else temp.setText(th[0]+"℉");
//		 		 }
//		         Log.e("TabTH", "end onResume");
		         
  
		         new Thread(){        
				     @Override  
				     public void run() {  
				    	 Message learnMsg=new Message();
				    	 Log.e("TabTH", "start thread");
				    	 mhandler.sendMessage(learnMsg);  
				     }}.start();      
				
		         
	 }

}
  
