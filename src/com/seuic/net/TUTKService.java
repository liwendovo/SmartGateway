package com.seuic.net;




import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class TUTKService extends Service{
	public static String TUTKService_Name = "com.seuic.net.TUTKService";
	String logcat="TUTKService";
	private final IBinder binder = new MyBinder();
	
	
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public class MyBinder extends Binder
	{
		public MyBinder()
		{
		}

		TUTKService getService()
		{
		  return TUTKService.this;
		}
	}	
	
  
}
