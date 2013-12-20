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
	private final static String Class = "class";
	private final static String Status = "status";
	private final static String Other = "other";
	private final static String ID = "_id";
	
    final String CREATE_SETUP_TABLE_SQL =
    		 "CREATE TABLE IF NOT EXISTS " + Table_Name_Setup + 
    		 " ( " + Uid + " VHARCHAR, " + Type  + " VHARCHAR, " + Name + " VHARCHAR );";
  //_id INTEGER PRIMARY KEY AUTOINCREMENT
	final String CREATE_LIST_TABLE_SQL =
			 "CREATE TABLE IF NOT EXISTS " + Table_Name_List + 
    		 " ( "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " + DevID + " VHARCHAR, " + Class + " VHARCHAR, " + Type + " VHARCHAR, "+Name + " VHARCHAR, " + Status + " VHARCHAR, "+ Other+ " VHARCHAR );";
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
	// 返回值  成功  重复
	public void insertSetup(SQLiteDatabase db, String Tag_Uid, String Tag_Type, String Tag_Name) {
		String sql = "INSERT INTO " + Table_Name_Setup + " Values(\'" + Tag_Uid + "\',\'" + Tag_Type + "\',\'" + Tag_Name + "\');";
		db.execSQL(sql);
	}
	// 返回值  成功  重复
	public void insertList(SQLiteDatabase db, String Tag_Uid, String Tag_Class, String Tag_Type, String Tag_Name, String Tag_Status, String Tag_Other) {
		//插入前查询，查重
		
		String sql = "INSERT INTO " + Table_Name_List + " Values(NULL,\'" + Tag_Uid + "\',\'" + Tag_Class + "\',\'" + Tag_Type + "\',\'" + Tag_Name + "\',\'"  + Tag_Status + "\',\'"+ Tag_Other + "\');";
		db.execSQL(sql);
	}
	public void insertEtc (SQLiteDatabase db, String Tag_Uid, String Tag_Name, String Tag_Type, String Tag_Other) {
		
		String sql = "INSERT INTO " + Table_Name_Etc  + " Values( \'"+ Tag_Uid + "\',\'" + Tag_Name + "\',\'" + Tag_Type + "\',\'"+ Tag_Other + "\');";
		db.execSQL(sql);
	}
	public void deleteSetup(SQLiteDatabase db, String Tag_Uid) {
		
		String sql = "DELETE FROM " + Table_Name_Setup + " WHERE " + Uid + "=\"" + Tag_Uid + "\"";
		db.execSQL(sql);
		sql = "DELETE FROM " + Table_Name_List + " WHERE " + Uid + "=\"" + Tag_Uid + "\"";
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
	public Cursor updateStatusByID(SQLiteDatabase db, String Tag_ID) {
		String str = "update " + Table_Name_Setup + " set Complain='yes' where " + Uid + "=\"" + Tag_ID + "\"";
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
