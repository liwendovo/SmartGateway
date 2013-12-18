/*
 * liwendong 2013-12-17
 * copyright@ seuic 
 * */
package com.seuic.sqlite;

import android.content.Context;  
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
public class SQLiteService {
	private SQLiteHelper mSQLHelper;

	 public SQLiteService(Context context) {  
		 final String name = "smartgateway.db";//Êý¾Ý¿âÃû³Æ  
		 mSQLHelper = new SQLiteHelper(context,name,1);  
	    } 
	 
	 public void saveDevSetupInfo() {  
		 mSQLHelper.getWritableDatabase().execSQL(null);  
	    } 	 
	 public void saveDevListInfo() {  
		 mSQLHelper.getWritableDatabase().execSQL(null);  
	    } 	 
	 public void saveDevEtcInfo() {  
		 mSQLHelper.getWritableDatabase().execSQL(null);  
	    } 
	 
	

	 public void findDevSetupInfo(Integer id) {}
	 public void findDevListInfo(Integer id) {}
	 public void findDevEtcInfo(Integer id) {}
}
