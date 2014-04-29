package com.seuic.smartgateway ;  

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seuic.net.TUTKClient;

public class TabTH extends Activity {
	String mUid=null;
	Button titleBtn,homeBtn;
	ImageView titlePic;
	TextView  temp,humi;
	
	 private Handler handler = new Handler();
	    private Runnable runnable = new Runnable() {
	        public void run() {
	            this.update();
	            handler.postDelayed(this, 1000 * 5);// 间隔5秒
	        }
	        void update() {
	            //刷新msg的内容
		         int[] th=new int[4];
		 		 TUTKClient.getTH(th);
		 		 if(th[2]>0){
			 		humi.setText(th[2]+"%");
			 		boolean tempmode = TabControl.myPre.getBoolean("tempmode", true);
			 		if(!tempmode) temp.setText(th[0]+"℃");
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
    	
 
		
    	titleBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
		}
	});

	}
	
	
	 @Override
	 protected void onResume() {
	 
		  super.onResume();	 
		  handler.postDelayed(runnable, 10);// 打开定时器，执行操作  
          
	 }
	 
	 @Override
	    protected void onPause() {
	        handler.removeCallbacks(runnable); //停止刷新
	        super.onPause();
	    }
	 
	 
	 @Override
	protected void onDestroy() {
		 Log.i("TabTH", "onDestroy");
		// TODO 自动生成的方法存根
		super.onDestroy();
	}




	private long mExitTime;
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			 Log.e("TabControl","get the keyback");
	       if (keyCode == KeyEvent.KEYCODE_BACK) {
	      	     Log.e("TabControl","get keyback");
					if ((System.currentTimeMillis() - mExitTime) > 2000) {
	                       Object mHelperUtils;
	                       Toast.makeText(this, "press again to exit the app", TabControl.time).show();
	                       mExitTime = System.currentTimeMillis();

	               } else {
//	              	 finish();
//	              	onDestroy();
	            	TUTKClient.stoptutk();
	              	System.exit(0);
	               }
	               return true;
	       }
	       return super.onKeyDown(keyCode, event);
	}

}
  
