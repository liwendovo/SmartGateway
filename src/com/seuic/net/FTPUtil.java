package com.seuic.net;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import android.util.Log;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;




public class FTPUtil
{
	private static FTPUtil ftp;
	private static final String TAG = "FTPUtil";
	/*
	 * FTP地址
	 */
	private static String ADDRESS = "192.168.1.100";
	
	/*
	 * FTP用户�?
	 */
	private static String USERNAME = "anonymous";
	/*
	 * FTP密码
	 */
	private static String PASSWORD = "anonymous";
	/*
	 * 实例化对�?
	 * @return
	 */
	public static FTPUtil getInstance(){
		if (ftp == null)
		{
			ftp = new FTPUtil();
		}
		return ftp;
	}
	
	/*
	 * 获取FTP客户端的对象
	 * @return 
	 * @throws Exception
	 */
	private FTPClient getClient(){
		FTPClient client = new FTPClient();
		Log.e(TAG, "FTP server 1");
		try
		{
			Log.e(TAG, "FTP server 2");
			client.setCharset("utf-8");
			client.setType(FTPClient.TYPE_BINARY);
//			URL url = new URL(ADDRESS);
			int port = 21;
		//	int port = url.getPort() < 1 ? 21:url.getPort();
			Log.e(TAG, "FTP server listening on address "+ADDRESS);
			client.connect(ADDRESS, port);
			client.login(USERNAME, PASSWORD);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return client;
	}
	
	/*
	 * 注销客户端连�?
	 * @param client
	 * 	FTP客户端对�?
	 * @throws Exception
	 */
	private void logout(FTPClient client){
		if (client != null)
		{
			try
			{
				//有些FTP服务器未实现此功能，若未实现则会出现错误
				client.logout();
			} catch (Exception e)
			{
				// TODO: handle exception
			}finally{
				
				if (client.isConnected())
				{
					try
					{
						client.disconnect(true);
					} catch (IllegalStateException e)
					{
						e.printStackTrace();
					} catch (IOException e)
					{
						e.printStackTrace();
					} catch (FTPIllegalReplyException e)
					{
						e.printStackTrace();
					} catch (FTPException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}
	/*
	 * 上传文件
	 * 上传完毕后关闭�?�?
	 */
	public boolean upload(File localfile){
		FTPClient client = getClient();
		try
		{
			client.upload(localfile);
			return true;
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}finally{
			logout(client);
		}
	}
	
}
