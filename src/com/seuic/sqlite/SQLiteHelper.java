/*
 * liwendong 2013-12-17
 * copyright@ seuic 
 * */
package com.seuic.sqlite;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteHelper extends SQLiteOpenHelper
{

	private final static String Table_Name_Setup = "devsetup";
	private final static String Table_Name_List = "devlist";
	private final static String Table_Name_Etc = "devetc";
	public final static String Uid = "_id";
	private final static String DevID = "devid";
	private final static String EtcID = "etcid";	
	private final static String Name = "name";
	private final static String Type = "type";
	private final static String Other = "other";
	
	
    final String CREATE_SETUP_TABLE_SQL =
    		 "CREATE TABLE IF NOT EXISTS " + Table_Name_Setup + 
    		 " ( " + Uid + " VHARCHAR, " + Name + " VHARCHAR, " + Type + " VHARCHAR );";
	final String CREATE_LIST_TABLE_SQL =
			 "CREATE TABLE IF NOT EXISTS " + Table_Name_List + 
    		 " ( " + DevID + " VHARCHAR, " + Name + " VHARCHAR, " + Type + " VHARCHAR, "+  Other+ " VHARCHAR );";
	final String CREATE_ETC_TABLE_SQL =
			 "CREATE TABLE IF NOT EXISTS " + Table_Name_Etc + 
    		 " ( " + EtcID + " VHARCHAR, " + Name + " VHARCHAR, " + Type + " VHARCHAR, "+ Other+ " VHARCHAR );";
		
		
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// 第一个使用数据库时自动建表
		db.execSQL(CREATE_SETUP_TABLE_SQL);
		db.execSQL(CREATE_LIST_TABLE_SQL);
		db.execSQL(CREATE_ETC_TABLE_SQL);
	}
	
	/**
	 * @param context
	 * @param name
	 * @param version
	 */
	public SQLiteHelper(Context context, String name, int version)
	{
		super(context, name, null, version);
	}
		
	public void insertSetup(SQLiteDatabase db, String Tag_Uid, String Tag_Name, String Tag_Type) {
		String sql = "INSERT INTO " + Table_Name_Setup + " Values(\'" + Tag_Uid + "\',\'" + Tag_Name + "\',\'" + Tag_Type + "\');";
		db.execSQL(sql);
	}
	public void insertList(SQLiteDatabase db, String Tag_Uid, String Tag_Name, String Tag_Type, String Tag_Other) {
		String sql = "INSERT INTO " + Table_Name_List + " Values(\'" + Tag_Uid + "\',\'" + Tag_Name + "\',\'" + Tag_Type + "\',\'"+ Tag_Other + "\');";
		db.execSQL(sql);
	}
	public void insertEtc (SQLiteDatabase db, String Tag_Uid, String Tag_Name, String Tag_Type, String Tag_Other) {
		String sql = "INSERT INTO " + Table_Name_Etc  + " Values(\'" + Tag_Uid + "\',\'" + Tag_Name + "\',\'" + Tag_Type + "\',\'"+ Tag_Other + "\');";
		db.execSQL(sql);
	}
	public void deleteSetup(SQLiteDatabase db, String Tag_Uid) {
		String sql = "DELETE FROM " + Table_Name_Setup + " WHERE " + Uid + "=\"" + Tag_Uid + "\"";
		db.execSQL(sql);
	}
	public Cursor seleteByUid(SQLiteDatabase db, String Tag_Uid) {
		String str = "SELECT * FROM " + Table_Name_Setup + " WHERE " +Uid + "=\""+ Tag_Uid + "\"";
		Cursor ToReturn = db.rawQuery(str, null);
		ToReturn.moveToFirst();
		return ToReturn;
	}
	public Cursor seleteByUidALL(SQLiteDatabase db) {
		String str = "SELECT * FROM " + Table_Name_Setup ;
		Cursor ToReturn = db.rawQuery(str, null);
		ToReturn.moveToFirst();
		return ToReturn;
	}
	public Cursor seleteByDevID(SQLiteDatabase db, String Tag_DevID) {
		String str = "SELECT * FROM " + Table_Name_List + " WHERE " +DevID + "=\""	+ Tag_DevID + "\"";
		Cursor ToReturn = db.rawQuery(str, null);
		ToReturn.moveToFirst();
		return ToReturn;
	}
	public Cursor updateByUid(SQLiteDatabase db, String Tag_Uid) {
		String str = "update " + Table_Name_Setup + " set Complain='yes' where " + Uid + "=\"" + Tag_Uid + "\"";
		Cursor ToReturn = db.rawQuery(str, null);
		ToReturn.moveToFirst();
		return ToReturn;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		System.out.println("--------onUpdate Called--------" 
			+ oldVersion + "--->" + newVersion);
	}
}
