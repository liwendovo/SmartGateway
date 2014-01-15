package com.seuic.net;

import android.util.Log;

import com.tutk.IOTC.IOTCAPIs;
import com.tutk.IOTC.AVAPIs;

public class Client {
	 static int sid;
	 static int uid;
	 static int avIndex;
	//custom request code
	 final static int LEARNTIMEOUT       =1000*10;
	 final static int MAX_SIZE_IOCTRL_BUF=1024;	
	
	 
    public static void start(String uid) {      
        
        System.out.println("StreamClient start...");

        // use which Master base on location, port 0 means to get a random port
        int ret = IOTCAPIs.IOTC_Initialize(0, "m1.iotcplatform.com",
                "m2.iotcplatform.com", "m4.iotcplatform.com",
                "m5.iotcplatform.com");
        System.out.printf("IOTC_Initialize() ret = %d\n", ret);
        if (ret != IOTCAPIs.IOTC_ER_NoERROR) {
            System.out.printf("IOTCAPIs_Device exit...!!\n");
            return;
        }

        // alloc 3 sessions for video and two-way audio
        AVAPIs.avInitialize(3);

        sid = IOTCAPIs.IOTC_Connect_ByUID(uid);
        System.out.printf("Step 2: call IOTC_Connect_ByUID(%s).......\n", uid);

        long[] srvType = new long[1];
        avIndex = AVAPIs.avClientStart(sid, "admin", "888888", 20000, srvType, 0);
        System.out.printf("Step 2: call avClientStart(%d).......\n", avIndex);

        if (avIndex < 0) {
            System.out.printf("avClientStart failed[%d]\n", avIndex);
            return;
        }

        

      
    }
    public static void stop() {
        AVAPIs.avClientStop(avIndex);
        System.out.printf("avClientStop OK\n");
        IOTCAPIs.IOTC_Session_Close(sid);
        System.out.printf("IOTC_Session_Close OK\n");
		AVAPIs.avDeInitialize();
		IOTCAPIs.IOTC_DeInitialize();
        System.out.printf("StreamClient exit...\n");    
    }
    public static boolean learn(String uid) { 
    	 //数据发送
        AVAPIs av = new AVAPIs();
        int ret = av.avSendIOCtrl(avIndex, AVAPIs.IOTYPE_BL_BOX_LEARN_IR_REQ, new byte[2], 2);
        if (ret < 0) {
            System.out.printf("start_ipcam_stream failed[%d]\n", ret);
           return false;
        }
        //数据接收 放到线程里 先这样            
        int ioType[]=new int[1];
        byte ioCtrlBuf[]=new byte[MAX_SIZE_IOCTRL_BUF];
      
        ret = av.avRecvIOCtrl(avIndex, ioType, ioCtrlBuf, MAX_SIZE_IOCTRL_BUF, LEARNTIMEOUT);
        if (ret > 0&&(ioType[0]==AVAPIs.IOTYPE_BL_BOX_LEARN_IR_RESP||ioType[0]== AVAPIs.IOTYPE_BL_BOX_LEARN_RF_RESP)) {
            Log.e("leewoo", "learn ok");
           return true;
        }
        return false;
    }
    public static void send(String uid) { 
   	 //数据发送
       AVAPIs av = new AVAPIs();
       int ret = av.avSendIOCtrl(avIndex, AVAPIs.IOTYPE_INNER_SND_DATA_DELAY,new byte[2], 2);
       if (ret < 0) {
           System.out.printf("start_ipcam_stream failed[%d]\n", ret);          
       }
    }
    public static void restart(String uid) {   
       

		AVAPIs.avClientStop(avIndex);
	    System.out.printf("avClientStop OK\n");
	    IOTCAPIs.IOTC_Session_Close(sid);
        
//////////////////////////////////////////////////        
        
        sid = IOTCAPIs.IOTC_Connect_ByUID(uid);
        System.out.printf("Step 2: call IOTC_Connect_ByUID(%s).......\n", uid);

        long[] srvType = new long[1];
         avIndex = AVAPIs.avClientStart(sid, "admin", "888888", 20000, srvType, 0);
        System.out.printf("Step 2: call avClientStart(%d).......\n", avIndex);
        if (avIndex < 0) {
            System.out.printf("avClientStart failed[%d]\n", avIndex);
            return;
        }
        
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
        
        int IOTYPE_USER_IPCAM_AUDIOSTART = 0x300;
        ret = av.avSendIOCtrl(avIndex, IOTYPE_USER_IPCAM_AUDIOSTART,
                new byte[8], 8);
        if (ret < 0) {
            System.out.printf("start_ipcam_stream failed[%d]\n", ret);
            return false;
        }

        return true;
    }

}
