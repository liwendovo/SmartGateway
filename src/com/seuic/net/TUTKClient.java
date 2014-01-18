package com.seuic.net;

import java.io.UnsupportedEncodingException;

import android.util.Log;

import com.tutk.IOTC.AVAPIs;
import com.tutk.IOTC.IOTCAPIs;


public class TUTKClient {
	 static int sid=-1;
	 static String uid=null;
	 static int avIndex=-1;
	 static boolean isConnect = false;
	//custom request code
	 final static int LEARNTIMEOUT       =1000*10;
	 final static int MAX_SIZE_IOCTRL_BUF=1024;	
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

	 final static int IOTYPE_BL_BOX_LEARN_RF_REQ                   =0xFF000060;
	 final static int IOTYPE_BL_BOX_LEARN_RF_RESP                  =0xFF000061;
	 final static int IOTYPE_BL_BOX_SEND_RF_REQ                    =0xFF000062;
	 final static int IOTYPE_BL_BOX_SEND_RF_RESP                   =0xFF000063;

	 final static int IOTYPE_BL_BOX_GET_LEDS_POWER_REQ             =0xFF000070;
	 final static int IOTYPE_BL_BOX_GET_LEDS_POWER_RESP            =0xFF000071;
	 final static int IOTYPE_BL_BOX_SET_LEDS_POWER_REQ             =0xFF000072;
	 final static int IOTYPE_BL_BOX_SET_LEDS_POWER_RESP            =0xFF000073;
//	 static Thread videoThread;
//	 static Thread audioThread;
	 public static boolean start(String uid) {  
		 if (!isConnect) {
		 	Log.e("TUTKClient", "uid");
	        System.out.println("StreamClient start...");

	        // use which Master base on location, port 0 means to get a random port
	        int ret = IOTCAPIs.IOTC_Initialize(0, "m1.iotcplatform.com",
	                	   "m2.iotcplatform.com", "m4.iotcplatform.com",
	                       "m5.iotcplatform.com");
	        System.out.printf("IOTC_Initialize() ret = %d\n", ret);
	        if (ret != IOTCAPIs.IOTC_ER_NoERROR) {
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

	        if (avIndex < 0) {
	            System.out.printf("avClientStart failed[%d]\n", avIndex);
	            return false;
	        }

	        if (startIpcamStream(avIndex)) {
	        	isConnect=true;
//	             videoThread = new Thread(new VideoThread(avIndex),
//	                    "Video Thread");
//	             audioThread = new Thread(new AudioThread(avIndex),
//	                    "Audio Thread");
	          
//	            videoThread.start();
//	            audioThread.start();
	            return true;
	        }
	       
		 }
		 return false;
	    }
    public static void stop() {
    	Log.e("TUTKClient", "in stop");
    	if(isConnect) {
    		 Log.e("TUTKClient", "stopping");
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
			AVAPIs.avDeInitialize();
			IOTCAPIs.IOTC_DeInitialize();
	        System.out.printf("StreamClient exit...\n");	
    	}

    }
    public static boolean learn(int type) { 
    	 //数据发送
        AVAPIs av = new AVAPIs();
        boolean irflag= (type < 2);
        byte[] devType=new byte[1];
        devType[0]=0;
        if(type==1)devType[0]=1;        
        int ret = av.avSendIOCtrl(avIndex,irflag?IOTYPE_BL_BOX_LEARN_IR_REQ:IOTYPE_BL_BOX_LEARN_RF_REQ,devType , 1);
        if (ret < 0) {
        	Log.e("TUTKClient", "learn failed "+ret);
           return false;
        }
        //数据接收 放到线程里 先这样            
        int ioType[]=new int[1];
        byte ioCtrlBuf[]=new byte[MAX_SIZE_IOCTRL_BUF];      
        ret = av.avRecvIOCtrl(avIndex, ioType, ioCtrlBuf, MAX_SIZE_IOCTRL_BUF, LEARNTIMEOUT);
        if (ret > 0&&(ioType[0]==IOTYPE_BL_BOX_LEARN_IR_RESP||ioType[0]== IOTYPE_BL_BOX_LEARN_RF_RESP)) {
            Log.e("TUTKClient", "learn ok");
            String str = null;
            try {
            	str=new String(ioCtrlBuf,"ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				
			}
            Log.e("TUTKClient", "num:"+ret+" data:"+str);
           return true;
        }
        return false;
    }
    public static boolean send() { 
   	 //数据发送
       AVAPIs av = new AVAPIs();
       int ret = av.avSendIOCtrl(avIndex, AVAPIs.IOTYPE_INNER_SND_DATA_DELAY,new byte[2], 2);
       if (ret < 0) {
           System.out.printf("start_ipcam_stream failed[%d]\n", ret); 
           return false;
       }
       return true;
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
//        int IOTYPE_USER_IPCAM_START = 0x1FF;
//        ret = av.avSendIOCtrl(avIndex, IOTYPE_USER_IPCAM_START,
//                new byte[8], 8);
//        if (ret < 0) {
//            System.out.printf("start_ipcam_stream failed[%d]\n", ret);
//            return false;
//        }
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
}
