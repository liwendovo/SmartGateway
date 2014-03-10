package com.seuic.net;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import android.R.string;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.seuic.adapter.CustomToast;
import com.seuic.smartgateway.TabControl;
import com.seuic.smartgateway.TabSET;
import com.tutk.IOTC.AVAPIs;
import com.tutk.IOTC.IOTCAPIs;


public class TUTKClient {
	 static int sid=-1;
	 static int avIndex=-1;
	 static String uid=null;
	
	 static boolean isConnect = false;
	 final static int LEARNTIMEOUT    =1000*60;
	 final static int SENDTIMEOUT    =1000*5;
	 final static int WAITTIMEOUT    =1000*3;
	 public final static int MAX_SIZE_IOCTRL_BUF=1024;	
	 
	//custom request code
	 final static int IOTYPE_BL_BOX_GET_GMT_TIME_REQ               =0xFF000010;
	 final static int IOTYPE_BL_BOX_GET_GMT_TIME_RESP              =0xFF000011;
	 final static int IOTYPE_BL_BOX_SET_GMT_TIME_REQ               =0xFF000012;
	 final static int IOTYPE_BL_BOX_SET_GMT_TIME_RESP              =0xFF000013;

	 final static int IOTYPE_BL_BOX_GET_LOCAL_TIME_MODE_REQ        =0xFF000020;
	 final static int IOTYPE_BL_BOX_GET_LOCAL_TIME_MODE_RESP       =0xFF000021;
	 final static int IOTYPE_BL_BOX_SET_LOCAL_TIME_MODE_REQ        =0xFF000022;
	 final static int IOTYPE_BL_BOX_SET_LOCAL_TIME_MODE_RESP       =0xFF000023;

	 final static int IOTYPE_BL_BOX_GET_TEMPERATURE_HUMIDITY_REQ   =0xFF000030;
	 final static int IOTYPE_BL_BOX_GET_TEMPERATURE_HUMIDITY_RESP  =0xFF000031;

	 final static int OTYPE_BL_BOX_GET_TEMPERATURE_MODE_REQ        =0xFF000040;
	 final static int OTYPE_BL_BOX_GET_TEMPERATURE_MODE_RESP       =0xFF000041;
	 final static int OTYPE_BL_BOX_SET_TEMPERATURE_MODE_REQ        =0xFF000042;
	 final static int OTYPE_BL_BOX_SET_TEMPERATURE_MODE_RESP       =0xFF000043;
	 final static int IOTYPE_BL_BOX_LEARN_IR_REQ                   =0xFF000050;
	 final static int IOTYPE_BL_BOX_LEARN_IR_RESP                  =0xFF000051;
	 final static int IOTYPE_BL_BOX_SEND_IR_REQ                    =0xFF000052;
	 final static int IOTYPE_BL_BOX_SEND_IR_RESP                   =0xFF000053;
	 final static int IOTYPE_BL_BOX_CANCEL_IR_REQ                  =0xFF000054;
	 final static int IOTYPE_BL_BOX_CANCEL_IR_RESP                 =0xFF000055;
	 final static int IOTYPE_BL_BOX_LEARN_RF_REQ                   =0xFF000060;
	 final static int IOTYPE_BL_BOX_LEARN_RF_RESP                  =0xFF000061;
	 final static int IOTYPE_BL_BOX_SEND_RF_REQ                    =0xFF000062;
	 final static int IOTYPE_BL_BOX_SEND_RF_RESP                   =0xFF000063;
	 final static int IOTYPE_BL_BOX_CANCEL_RF_REQ          	       =0xFF000064;
	 final static int IOTYPE_BL_BOX_CANCEL_RF_RESP           	   =0xFF000065;

	 final static int IOTYPE_BL_BOX_GET_LEDS_POWER_REQ             =0xFF000070;
	 final static int IOTYPE_BL_BOX_GET_LEDS_POWER_RESP            =0xFF000071;
	 final static int IOTYPE_BL_BOX_SET_LEDS_POWER_REQ             =0xFF000072;
	 final static int IOTYPE_BL_BOX_SET_LEDS_POWER_RESP            =0xFF000073;
	 
	 final static int IOTYPE_USER_IPCAM_GET_TIMEZONE_REQ           = 0x3A0;
	 final static int IOTYPE_USER_IPCAM_GET_TIMEZONE_RESP          = 0x3A1;
	 final static int IOTYPE_USER_IPCAM_SET_TIMEZONE_REQ           = 0x3B0;
	 final static int IOTYPE_USER_IPCAM_SET_TIMEZONE_RESP          = 0x3B1;
	 public static boolean checkoutnetwork(){
		 
		 return false;
	 }
	 //type 0=ir 1=ac 2=rf
    public static boolean learn(int type,byte[] ioCtrlBuf) {     	
        AVAPIs av = new AVAPIs();
        boolean irflag= (type < 2);
        byte[] devType=new byte[1];
        devType[0]=0;
        if(type==1)devType[0]=1;        
        int ret = av.avSendIOCtrl(avIndex,irflag?IOTYPE_BL_BOX_LEARN_IR_REQ:IOTYPE_BL_BOX_LEARN_RF_REQ,devType, devType.length);
        Log.e("TUTKClient", "learn  ret="+ret);
        if ((ret < 0)&&(ret !=-105)) {
        	Log.e("TUTKClient", "learn send failed "+ret);
           return false;
        }               
        int ioType[]=new int[1];
//        byte ioCtrlBuf[]=new byte[MAX_SIZE_IOCTRL_BUF];      
        int returnvalue= av.avRecvIOCtrl(avIndex, ioType, ioCtrlBuf, MAX_SIZE_IOCTRL_BUF, LEARNTIMEOUT);
        Log.e("TUTKClient", "returnvalue"+returnvalue);  
        if (returnvalue >= 0&&(ioType[0]==IOTYPE_BL_BOX_LEARN_IR_RESP||ioType[0]== IOTYPE_BL_BOX_LEARN_RF_RESP)) {
            Log.e("TUTKClient", "learn ok");
		    Log.e("TUTKClient", "num:"+ret+" data:"+bytes2HexString(ioCtrlBuf));    		
           return true;
        }
       
//        cancellearn( irflag);
        return false;
    }
    public static boolean cancellearn(boolean irflag)
    {
        if (!isConnect) {
        	Log.e("TUTKClient", "cancellearn disconnect");
            return false;
        }
        AVAPIs av = new AVAPIs();
        int ret = av.avSendIOCtrl(avIndex, irflag?IOTYPE_BL_BOX_CANCEL_IR_REQ:IOTYPE_BL_BOX_CANCEL_RF_REQ,null , 0); 
       	if(ret< 0)
    	{
       		Log.e("TUTKClient", "cancellearn SendIOCtrl false"+ret);
    		return false;
    		
    	}   
       	Log.e("TUTKClient", "cancellearn ret="+ret);
        return true;
    }
    public static boolean send(byte[] data,boolean irflag ) { 
   	 //数据发送
       AVAPIs av = new AVAPIs();
//       String str=new String(data);	

       int ret = av.avSendIOCtrl(avIndex, irflag?IOTYPE_BL_BOX_SEND_IR_REQ:IOTYPE_BL_BOX_SEND_RF_REQ , data , data.length);
       if (ret < 0) {
           System.out.printf("send failed[%d]\n", ret); 
           return false;
       }
       int ioType[]=new int[1];
       byte ioCtrlBuf[]=new byte[MAX_SIZE_IOCTRL_BUF]; 
       int returnvalue = av.avRecvIOCtrl(avIndex, ioType, ioCtrlBuf, MAX_SIZE_IOCTRL_BUF, SENDTIMEOUT);
       if (returnvalue>0&&(ioType[0]==IOTYPE_BL_BOX_SEND_IR_RESP||ioType[0]==IOTYPE_BL_BOX_SEND_RF_RESP)) {
    	   Log.e("TUTKClient", "send ok->"+returnvalue);
           Log.e("TUTKClient", "num:"+data.length+" data:"+bytes2HexString(data));
    	   return true;
       }
       Log.e("TUTKClient", "send failed->"+returnvalue);
       return false;
    }    
    public static boolean getTH(int TH[])
    {
        if (!isConnect) {
            return false;
        }
        AVAPIs av = new AVAPIs();
        av.avSendIOCtrl(avIndex, IOTYPE_BL_BOX_GET_TEMPERATURE_HUMIDITY_REQ,new byte[0], 0);
        int ioType[]=new int[1];
        byte[]  th=new byte[16];
        
        int ret = av.avRecvIOCtrl(avIndex, ioType, th, th.length, LEARNTIMEOUT);
        if (ret>0&&(ioType[0]==IOTYPE_BL_BOX_GET_TEMPERATURE_HUMIDITY_RESP)) {
//        	String str=null;    
//        	str=new String(th);
        	
        	Log.e("getTempHum","num="+ret +"data:"+bytes2HexString(th));
        
//          TH[0]= sm.humidityPointLeft   +sm.humidityPointRight/10.0;
//          TH[1]= sm.temperaturePointLeft+sm.temperaturePointRight/10.0;        	
        	//小端         
            int temp=0;
            int n=0;
            for(int i=0;i<4;i++){
	            for(int j=3;j>=0;j--){
	               n<<=8;
	               temp=th[i*4+j]&0XFF;
	               n|=temp;
	            }
	            TH[i]=n;  
	            
	            n=0;temp=0;
            }
            
            Log.e("getTempHum","data in int:"+TH[3]+" "+TH[2]+" "+TH[1]+" "+TH[0]);
            
            return true;
        }
        return false;

    }
    public static boolean setTempMode(int mode){ 
    	    if (!isConnect) {
    	        return false;
    	    }
    	    int ret;
    	    AVAPIs av = new AVAPIs();
    	    int[]  tempMode=new int[]{mode};
    	    byte[] tempModeByte=intToByte(tempMode);
    	    Log.e("setTempMode", " "+tempMode[0]+" "+bytes2HexString(tempModeByte));
    	    ret = av.avSendIOCtrl(avIndex, OTYPE_BL_BOX_SET_TEMPERATURE_MODE_REQ, tempModeByte,tempModeByte.length);
    	  	if(ret < 0)
    	    {
//    	        printf("setdevicetime failed[%d]\n", ret);
    	        return false;
    	    }
    	  	 int ioType[]=new int[1];
          	 byte[] ioCtrlBuf=new byte[MAX_SIZE_IOCTRL_BUF];
    	     int returnvalue = av.avRecvIOCtrl(avIndex, ioType, ioCtrlBuf, MAX_SIZE_IOCTRL_BUF, WAITTIMEOUT);
    	     if (returnvalue>0&&(ioType[0]==OTYPE_BL_BOX_SET_TEMPERATURE_MODE_RESP)) {
    	    	  Log.e("setTempMode", " OK");
    	        return true;
    	    }
    	     
    	return false;
    }
    public static boolean setHourMode(int mode){ 
	    if (!isConnect) {
	        return false;
	    }
	    int ret;
	    AVAPIs av = new AVAPIs();
	    int[]  timeMode=new int[]{mode};
	    byte[] timeModeByte=intToByte(timeMode);
	    Log.e("setHourMode", " "+mode+" "+bytes2HexString(timeModeByte));
	    ret = av.avSendIOCtrl(avIndex, IOTYPE_BL_BOX_SET_LOCAL_TIME_MODE_REQ, timeModeByte,timeModeByte.length);
	  	if(ret < 0)
	    {
	  		 Log.e("setHourMode", "setdevicetime failed  ret="+ ret);
	        return false;
	    }
	  	 Log.e("setHourMode", "setdevicetime success "+ ret);
	  	 int ioType[]=new int[1];
      	 byte[] ioCtrlBuf=new byte[MAX_SIZE_IOCTRL_BUF];
	     int returnvalue = av.avRecvIOCtrl(avIndex, ioType, ioCtrlBuf, MAX_SIZE_IOCTRL_BUF, WAITTIMEOUT);
//	     Log.e("setTimeMode", ""+ioType[0]);
	     if (returnvalue>0&&(ioType[0]==IOTYPE_BL_BOX_SET_LOCAL_TIME_MODE_RESP)) {
	   	  Log.e("setHourMode", " OK");
	    	 return true;
	    }	 
	     Log.e("setHourMode", "setdevicetime receive failed   returnvalue="+ returnvalue);
	return false;
}
    public static boolean setTimeZone(int timeZone){     	
    	if (!isConnect) {
            return false;
        }
    	
    	AVAPIs av = new AVAPIs();        
        int[] tmz=new int[]{timeZone};  
//        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
       
//        byte[]  tmzByte=intToByte(tmz);
        byte[]  tmzByte=new byte[268];
        tmzByte[8]=(byte) timeZone;
        string ss=bytetostring(tmzByte);
//        ByteArrayOutputStream tmzByte;
//		tmzByte.write(bOut,9, 12);
        
//        Log.e("setTimeZone", " "+tmz[0]+" "+bytes2HexString(tmzByte)+"tmzByte[11]="+tmzByte[11]);
        Log.e("setTimeZone", " "+tmz[0]+" "+ss+"tmzByte[11]="+tmzByte[11]);
        int ret;
        ret = av.avSendIOCtrl(avIndex, IOTYPE_USER_IPCAM_SET_TIMEZONE_REQ, tmzByte, 268);
        Log.e("setTimeZone", "tmzByte.length="+ tmzByte.length);
      	if(ret<0)
        {
      		 Log.e("setTimeZone", "setTimeZone send failed ret="+ ret);
            return false;
        }
      	 Log.e("setTimeZone", "setTimeZone send success ret="+ ret);
      	 int ioType[]=new int[1];
      	 byte[] ioCtrlBuf=new byte[MAX_SIZE_IOCTRL_BUF];
        int returnvalue = av.avRecvIOCtrl(avIndex, ioType, ioCtrlBuf, MAX_SIZE_IOCTRL_BUF, WAITTIMEOUT);
        if (returnvalue>0&&(ioType[0]==IOTYPE_USER_IPCAM_SET_TIMEZONE_RESP)) {
        	  Log.e("setTimeZone", " OK");
            return true;
        }
        Log.e("setTimeZone", "setTimeZone receive failed  returnvalue="+ returnvalue);
        return false;
    }
    private static string bytetostring(byte[] tmzByte) {
		// TODO 自动生成的方法存根
		return null;
	}
	public static boolean setTime() { 
      	 //数据发送
    	  Log.e("TUTKClient", "setTime");
    	  byte[] time =new byte[8];
    	  long curTime=System.currentTimeMillis();
    	  final Calendar mCalendar=Calendar.getInstance();
    	  mCalendar.setTimeInMillis(curTime);
    	  time[0]=(byte) (mCalendar.get(Calendar.YEAR)& 0xFF);//
    	  time[1]=(byte)((mCalendar.get(Calendar.YEAR) >> 8) & 0xFF);    	  
    	  time[2]=(byte) (mCalendar.get(Calendar.MONTH)+1);    	
    	  time[3]=(byte) mCalendar.get(Calendar.DAY_OF_MONTH);   
    	  time[4]=(byte) mCalendar.get(Calendar.DAY_OF_WEEK);    	
    	  time[5]=(byte) mCalendar.get(Calendar.HOUR_OF_DAY);
    	  time[6]=(byte) mCalendar.get(Calendar.MINUTE);
    	  time[7]=(byte) mCalendar.get(Calendar.SECOND);

          AVAPIs av = new AVAPIs();     
          int ret = av.avSendIOCtrl(avIndex, IOTYPE_BL_BOX_SET_GMT_TIME_REQ,time, time.length);
          if (ret < 0) {              
              Log.e("TUTKClient", "start_settime failed  "+ret);
              return false;
          }
          int ioType[]=new int[1];
          byte[] ioCtrlBuf=new byte[MAX_SIZE_IOCTRL_BUF];
          int returnvalue = av.avRecvIOCtrl(avIndex, ioType,ioCtrlBuf, MAX_SIZE_IOCTRL_BUF, WAITTIMEOUT);
          Log.e("TUTKClient", "start_settime stop");
          if (returnvalue>0&&(ioType[0]==IOTYPE_BL_BOX_SET_GMT_TIME_RESP)) {
              return true;
          }
          return false;
    }
    public static boolean getTime()
    {
    	AVAPIs av = new AVAPIs();
        int ret;    	
    	byte[] val =new byte[2];
    	ret = av.avSendIOCtrl(avIndex, IOTYPE_BL_BOX_GET_GMT_TIME_REQ, val,val.length );
    	if(ret < 0)
        {
    		Log.e("getTime", "getdevicetime failed "+ret);           
            return true;
        }
    	int ioType[]=new int[1];
        byte[] ioCtrlBuf=new byte[MAX_SIZE_IOCTRL_BUF];
        ret = av.avRecvIOCtrl(avIndex, ioType, ioCtrlBuf, MAX_SIZE_IOCTRL_BUF, WAITTIMEOUT);        
        if (ret==0&&(ioType[0]==IOTYPE_BL_BOX_GET_GMT_TIME_RESP)) {
        	Log.e("getTime", "getdevicetime success");     
            return true;
        }
        return false;
    }   
	 public static boolean start(String uid) { 
		    Log.e("TUTKClient", "uid:"+uid+" isConnect:"+isConnect+" sid:"+sid);
			 if (isConnect) {
				 stop();
			 }
		 
	        System.out.println("StreamClient start...");
	        // use which Master base on location, port 0 means to get a random port
	        int ret = IOTCAPIs.IOTC_Initialize(0, "m1.iotcplatform.com",
	                	   "m2.iotcplatform.com", "m4.iotcplatform.com",
	                       "m5.iotcplatform.com");
	        System.out.printf("IOTC_Initialize() ret = %d\n", ret);
	        
//	        if(ret == IOTCAPIs.IOTC_ER_ALREADY_INITIALIZED)
//	        {
//	        	IOTCAPIs.IOTC_DeInitialize();
//	        	ret = IOTCAPIs.IOTC_Initialize(0, "m1.iotcplatform.com",
//	                	   "m2.iotcplatform.com", "m4.iotcplatform.com",
//	                       "m5.iotcplatform.com");
//	        }
	        
	        if ((ret != IOTCAPIs.IOTC_ER_NoERROR)&&(ret != IOTCAPIs. IOTC_ER_ALREADY_INITIALIZED)) {
	            System.out.printf("IOTCAPIs_Device exit...!!\n");
	            return false;
	        }
	        
	       
	        // alloc 3 sessions for video and two-way audio
	        AVAPIs.avInitialize(3);
	        
	        sid = IOTCAPIs.IOTC_Connect_ByUID(uid);
	        System.out.printf("Step 2: call IOTC_Connect_ByUID(%s).......\n", uid);
	        long[] srvType = new long[1];
	        avIndex = AVAPIs.avClientStart(sid, "admin", "888888", 20000, srvType, 0);
	        System.out.printf("Step 2: call avClientStart(%d).......\n", avIndex);
	        if (avIndex == IOTCAPIs.IOTC_ER_SERVER_NOT_RESPONSE) {
	        	stop();
	        }
	        
	        
	        if (avIndex < 0) {
	            System.out.printf("avClientStart failed[%d]\n", avIndex);
	            return false;
	        }

	        if (startIpcamStream(avIndex)) {
	        	isConnect=true;
	        	Thread videoThread = new Thread(new VideoThread(avIndex),
	                    "Video Thread");
//	             audioThread = new Thread(new AudioThread(avIndex),
//	                    "Audio Thread");
	          
	            videoThread.start();
//	            audioThread.start();
	            return true;
	        }
		 
		return false;
		
	}
    public static void stop() {
    	Log.e("TUTKClient", "stop()->begin to stop...........");
    	if(isConnect) {
    		Log.e("TUTKClient", "stop()->stopping.............");
	    	isConnect=false;
//	    	 try {
//	             videoThread.join();
//	         }
//	         catch (InterruptedException e) {
//	             System.out.println(e.getMessage());             
//	         }
//	         try {
//	             audioThread.join();
//	         }
//	         catch (InterruptedException e) {
//	             System.out.println(e.getMessage());             
//	         }    	
	    	 AVAPIs.avClientStop(avIndex);
	         System.out.printf("avClientStop OK\n");
	         IOTCAPIs.IOTC_Session_Close(sid);
	         System.out.printf("IOTC_Session_Close OK\n");
//	 		 AVAPIs.avDeInitialize();
	 		 IOTCAPIs.IOTC_DeInitialize();
	         System.out.printf("StreamClient exit...\n");
	        sid=-1;
	   	    avIndex=-1;
    	}
    }
    public static boolean restart(String uid) {   
		AVAPIs.avClientStop(avIndex);
	    System.out.printf("avClientStop OK\n");
	    IOTCAPIs.IOTC_Session_Close(sid);
//////////////////////////////////////////////////   
        sid = IOTCAPIs.IOTC_Connect_ByUID(uid);
        System.out.printf("Step 2: call IOTC_Connect_ByUID(%s).......\n", uid);

        long[] srvType = new long[1];
         avIndex = AVAPIs.avClientStart(sid, "admin", "888888", 20000, srvType, 0);
        System.out.printf("Step 2: call avClientRestart(%d).......\n", avIndex);
        if (avIndex < 0) {
            System.out.printf("restart failed[%d]\n", avIndex);
            return false;
        }
        return true;
     }    
      
    public static boolean startIpcamStream(int avIndex) {
        AVAPIs av = new AVAPIs();
        int ret = av.avSendIOCtrl(avIndex, AVAPIs.IOTYPE_INNER_SND_DATA_DELAY,
                new byte[2], 2);
        if (ret < 0) {
            System.out.printf("start_ipcam_stream failed[%d]\n", ret);
            return false;
        }
        
        // This IOTYPE constant and its corrsponsing data structure is defined in
        // Sample/Linux/Sample_AVAPIs/AVIOCTRLDEFs.h
        //
        
        int IOTYPE_USER_IPCAM_START = 0x1FF;
        ret = av.avSendIOCtrl(avIndex, IOTYPE_USER_IPCAM_START,
                new byte[8], 8);
        if (ret < 0) {
            System.out.printf("start_ipcam_stream failed[%d]\n", ret);
            return false;
        }
//        
//        int IOTYPE_USER_IPCAM_AUDIOSTART = 0x300;
//        ret = av.avSendIOCtrl(avIndex, IOTYPE_USER_IPCAM_AUDIOSTART,
//                new byte[8], 8);
//        if (ret < 0) {
//            System.out.printf("start_ipcam_stream failed[%d]\n", ret);
//            return false;
//        }

        return true;
    }
    public static class VideoThread implements Runnable {
        static final int VIDEO_BUF_SIZE = 100000;
        static final int FRAME_INFO_SIZE = 16;
		private static final Context Context = null;

        private int avIndex;
        public VideoThread(int avIndex) {
            this.avIndex = avIndex;
        }

        @Override
        public void run() {
            System.out.printf("[%s] Start\n",
                    Thread.currentThread().getName());

            AVAPIs av = new AVAPIs();
            byte[] frameInfo = new byte[FRAME_INFO_SIZE];
            byte[] videoBuffer = new byte[VIDEO_BUF_SIZE];
            while (isConnect) {
                int[] frameNumber = new int[1];
                int ret = av.avRecvFrameData(avIndex, videoBuffer,
                        VIDEO_BUF_SIZE, frameInfo, FRAME_INFO_SIZE,
                        frameNumber);
//                Log.e("TUTKClient","VideoThread ret"+ret);
                if (ret == AVAPIs.AV_ER_DATA_NOREADY) {
                    try {
                        Thread.sleep(30);
                        continue;
                    }
                    catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                }
                else if (ret == AVAPIs.AV_ER_LOSED_THIS_FRAME) {
                    System.out.printf("[%s] Lost video frame number[%d]\n",
                            Thread.currentThread().getName(), frameNumber[0]);
                    continue;
                }
                else if (ret == AVAPIs.AV_ER_INCOMPLETE_FRAME) {
                    System.out.printf("[%s] Incomplete video frame number[%d]\n",
                            Thread.currentThread().getName(), frameNumber[0]);
                    continue;
                }
                else if (ret == AVAPIs.AV_ER_SESSION_CLOSE_BY_REMOTE) {
                    System.out.printf("[%s] AV_ER_SESSION_CLOSE_BY_REMOTE\n",
                            Thread.currentThread().getName());
                    break;
                }
                else if (ret == AVAPIs.AV_ER_REMOTE_TIMEOUT_DISCONNECT) {
                    System.out.printf("[%s] AV_ER_REMOTE_TIMEOUT_DISCONNECT\n",
                            Thread.currentThread().getName());
                    break;
                }
                else if (ret == AVAPIs.AV_ER_INVALID_SID) {
                    System.out.printf("[%s] Session cant be used anymore\n",
                            Thread.currentThread().getName());
                    break;
                }
                else if (ret == IOTCAPIs.IOTC_ER_REMOTE_TIMEOUT_DISCONNECT) {
                    System.out.printf("[%s] IOTC_ER_REMOTE_TIMEOUT_DISCONNECT\n",
                            Thread.currentThread().getName());
                    TabControl.mUid="NULL";
                    
//                    AlertDialog.Builder builder = new Builder(Context); 
//                    builder.setMessage("Are you sure you want to exit?") 
//                           .setCancelable(false) 
//                           .setPositiveButton("Yes", new DialogInterface.OnClickListener() { 
//                               public void onClick(DialogInterface dialog, int id) { 
//                            	   
//                            	   Intent intent = new Intent();
//                            	   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
//                            	   intent.setClass(Context, TabSET.class);
//                            	   startActivity(intent);
//                               }
//
//							private void startActivity(Intent intent) {
//								// TODO Auto-generated method stub
//								
//							} 
//                           }); 
//                    AlertDialog alert = builder.create(); 
//                    alert.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
//                    alert.show();
                    
                    CustomToast.showToast(Context, "it has disconnected with the device ", Toast.LENGTH_LONG); 
                    break;
                }

                // Now the data is ready in videoBuffer[0 ... ret - 1]
                // Do something here
            }
            
            System.out.printf("[%s] Exit\n",
                    Thread.currentThread().getName());
        }
    }

    public static class AudioThread implements Runnable {
        static final int AUDIO_BUF_SIZE = 1024;
        static final int FRAME_INFO_SIZE = 16;

        private int avIndex;

        public AudioThread(int avIndex) {
            this.avIndex = avIndex;
        }

        @Override
        public void run() {
            System.out.printf("[%s] Start\n",
                    Thread.currentThread().getName());

            AVAPIs av = new AVAPIs();
            byte[] frameInfo = new byte[FRAME_INFO_SIZE];
            byte[] audioBuffer = new byte[AUDIO_BUF_SIZE];
            while (isConnect) {
                int ret = av.avCheckAudioBuf(avIndex);

                if (ret < 0) {
                    // Same error codes as below
                    System.out.printf("[%s] avCheckAudioBuf() failed: %d\n",
                            Thread.currentThread().getName(), ret);
                    break;
                }
                else if (ret < 3) {
                    try {
                        Thread.sleep(120);
                        continue;
                    }
                    catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                }

                int[] frameNumber = new int[1];
                ret = av.avRecvAudioData(avIndex, audioBuffer,
                        AUDIO_BUF_SIZE, frameInfo, FRAME_INFO_SIZE,
                        frameNumber);

                if (ret == AVAPIs.AV_ER_SESSION_CLOSE_BY_REMOTE) {
                    System.out.printf("[%s] AV_ER_SESSION_CLOSE_BY_REMOTE\n",
                            Thread.currentThread().getName());
                    break;
                }
                else if (ret == AVAPIs.AV_ER_REMOTE_TIMEOUT_DISCONNECT) {
                    System.out.printf("[%s] AV_ER_REMOTE_TIMEOUT_DISCONNECT\n",
                            Thread.currentThread().getName());
                    break;
                }
                else if (ret == AVAPIs.AV_ER_INVALID_SID) {
                    System.out.printf("[%s] Session cant be used anymore\n",
                            Thread.currentThread().getName());
                    break;
                }
                else if (ret == AVAPIs.AV_ER_LOSED_THIS_FRAME) {
                    //System.out.printf("[%s] Audio frame losed\n",
                    //        Thread.currentThread().getName());
                    continue;
                }

                // Now the data is ready in audioBuffer[0 ... ret - 1]
                // Do something here
            }

            System.out.printf("[%s] Exit\n",
                    Thread.currentThread().getName());
        }
    }
    public static byte[] intToByte(int[] key) { 
    	  
    	  byte[] result = new byte[4*key.length]; 
    	  for(int i=0;i<key.length;i++){
    	  result[i*4+3] = (byte)((key[i] >> 24) & 0xFF);
    	  result[i*4+2] = (byte)((key[i] >> 16) & 0xFF);
    	  result[i*4+1] = (byte)((key[i] >> 8) & 0xFF); 
    	  result[i*4+0] = (byte)(key[i] & 0xFF);
    	  }
    	  return result;
    	 }
   
    public static String bytes2HexString(byte[] b) {
    	  String ret = "";
    	  for (int i = 0; i < b.length; i++) {
    	   String hex = Integer.toHexString(b[ i ] & 0xFF);
    	   if (hex.length() == 1) {
    	    hex = '0' + hex;
    	   }
    	   ret += hex.toUpperCase();
    	  }
    	  return ret;
    	}
}
