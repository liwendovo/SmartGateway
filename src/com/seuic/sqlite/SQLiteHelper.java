/*
 * liwendong 2013-12-17
 * copyright@ seuic 
 * */
package com.seuic.sqlite;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class SQLiteHelper extends SQLiteOpenHelper
{

	private final static String Table_Name_Setup = "devsetup";
	private final static String Table_Name_List = "devlist";
//	private final static String Table_Name_Etc = "devetc";
	private final static String Table_Name_Btn = "devbtn";
	
	public  final static String Uid   = "uid";    //设备ID
	private final static String DevID = "devid";//遥控器ID
//	private final static String EtcID = "etcid";//按键的ID	
	private final static String Name  = "name";
	private final static String Type  = "type";
	private final static String Class = "class";	
	private final static String Status= "status";	
	private final static String Other = "other";
	
	
    final String CREATE_SETUP_TABLE_SQL =
    		 "CREATE TABLE IF NOT EXISTS " + Table_Name_Setup + 
    		 " ( " + Uid + " VHARCHAR PRIMARY KEY, " + Type  + " VHARCHAR, " + Name + " VHARCHAR );";
  //_id INTEGER PRIMARY KEY AUTOINCREMENT
	final String CREATE_LIST_TABLE_SQL =
			 "CREATE TABLE IF NOT EXISTS " + Table_Name_List + 
    		 " ( "+  Uid + " VHARCHAR(20), "+ DevID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + Class + " VHARCHAR, " + Type + " VHARCHAR, "+Name + " VHARCHAR, " + Status + " VHARCHAR, "+ Other+ " VHARCHAR );";
//	final String CREATE_ETC_TABLE_SQL =
//			 "CREATE TABLE IF NOT EXISTS " + Table_Name_Etc + 
//    		 " ( "+  Uid +" VHARCHAR(20), " + DevID + " INTEGER, " + EtcID + " VHARCHAR, "+ Name + " VHARCHAR, "+ Status + " VHARCHAR, "+  Other+ " VHARCHAR );";
	final String CREATE_BTN_TABLE_SQL =
			 "CREATE TABLE IF NOT EXISTS " + Table_Name_Btn + 
   		     " ( "+  Uid +" VHARCHAR(20), " + DevID + " INTEGER, " + Type + " VHARCHAR,  button1  VHARCHAR, button2 VHARCHAR, button3  VHARCHAR, button4  VHARCHAR, button5  VHARCHAR, button6  VHARCHAR, button7  VHARCHAR,button8  VHARCHAR,button9  VHARCHAR, button10  VHARCHAR, button11  VHARCHAR,button12  VHARCHAR, button13  VHARCHAR,button14  VHARCHAR);";
		
		
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// 第一个使用数据库时自动建表
		db.execSQL(CREATE_SETUP_TABLE_SQL);
		db.execSQL(CREATE_LIST_TABLE_SQL);
	//	db.execSQL(CREATE_ETC_TABLE_SQL);
		db.execSQL(CREATE_BTN_TABLE_SQL);		
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
	public boolean insertSetup(SQLiteDatabase db, String Tag_Uid, String Tag_Type, String Tag_Name) {
		
		String str = "SELECT * FROM " + Table_Name_Setup + " WHERE " +Uid + "=\""+ Tag_Uid + "\"";
		Cursor ToReturn = db.rawQuery(str, null);
		if(0==ToReturn.getCount())
		{
		String sql = "INSERT INTO " + Table_Name_Setup + " Values(\'" + Tag_Uid + "\',\'" + Tag_Type + "\',\'" + Tag_Name + "\');";
		db.execSQL(sql);
		return true;
		}else{
		return false;
		}
	}
	// 返回值  成功  重复
	public void insertList(SQLiteDatabase db, String Tag_Uid, String Tag_Class, String Tag_Type, String Tag_Name, String Tag_Status, String Tag_Other) {
		//插入前查询，查重
	
		String sql = "INSERT INTO " + Table_Name_List + " Values( \'" + Tag_Uid + "\', NULL ,\'" + Tag_Class + "\',\'" + Tag_Type + "\',\'" + Tag_Name + "\',\'"  + Tag_Status + "\',\'"+ Tag_Other + "\');";
		db.execSQL(sql);
		
	}
//	public void insertEtc(SQLiteDatabase db, String Tag_Uid, int Tag_DevID, String Tag_EtcID, String Tag_Name, String Tag_Status, String Tag_Other) {
//		
//		String sql = "INSERT INTO " + Table_Name_Etc  + " Values( \'"+ Tag_Uid + "\',\'" +Tag_DevID+ "\',\'"+ Tag_EtcID+"\',\'" +Tag_Name + "\',\'" + Tag_Status + "\',\'"+ Tag_Other + "\');";
//		db.execSQL(sql);
//	}
	public void insertBtn(SQLiteDatabase db, String Tag_Uid, int Tag_DevID, String Tag_Type, String Btn1, String Btn2, String Btn3,String Btn4, String Btn5, String Btn6,String Btn7, String Btn8, String Btn9,String Btn10, String Btn11, String Btn12,String Btn13, String Btn14) {
		
		String sql = "INSERT INTO " + Table_Name_Btn  + " Values( \'"+ Tag_Uid + "\',\'" +Tag_DevID+ "\',\'"+ Tag_Type+"\',\'"+ Btn1+"\',\'" +Btn2 + "\',\'" + Btn3 + "\',\'"+ Btn4 +"\',\'" +Btn5 +"\',\'" +Btn6 +"\',\'" +Btn7 +"\',\'" +Btn8 +"\',\'" +Btn9 +"\',\'" +Btn10 +"\',\'" +Btn11 +"\',\'" +Btn12 +"\',\'" +Btn13 +"\',\'" +Btn14 + "\');";
		db.execSQL(sql);
		
	}
	public void deleteSetup(SQLiteDatabase db, String Tag_Uid) {
		
		String 
		sql = "DELETE FROM " + Table_Name_Setup + " WHERE " + Uid + "=\"" + Tag_Uid + "\"";
		db.execSQL(sql);
		sql = "DELETE FROM " + Table_Name_List  + " WHERE " + Uid + "=\"" + Tag_Uid + "\"";
		db.execSQL(sql);
		sql = "DELETE FROM " + Table_Name_Btn   + " WHERE " + Uid + "=\"" + Tag_Uid + "\"";
		db.execSQL(sql);
		
	}
	public void deleteList(SQLiteDatabase db, int Tag_DevID) {
		
		String 	
		sql = "DELETE FROM " + Table_Name_List  + " WHERE " + DevID + "=\"" + Tag_DevID + "\"";
		db.execSQL(sql);
		sql = "DELETE FROM " + Table_Name_Btn   + " WHERE " + DevID + "=\"" + Tag_DevID + "\"";
		db.execSQL(sql);
	}
	public Cursor seleteSetup(SQLiteDatabase db, String Tag_Uid) {
		String str = "SELECT * FROM " + Table_Name_Setup + " WHERE " +Uid + "=\""+ Tag_Uid + "\"";
		Cursor ToReturn = db.rawQuery(str, null);
		ToReturn.moveToFirst();
		return ToReturn;
	}
	public Cursor seleteSetupALL(SQLiteDatabase db) {
		String str = "SELECT * FROM " + Table_Name_Setup ;
		Cursor ToReturn = db.rawQuery(str, null);
		ToReturn.moveToFirst();
		return ToReturn;
	}
	public Cursor seleteList(SQLiteDatabase db, String Tag_UID) {
	
		String str = "SELECT * FROM " + Table_Name_List + " WHERE " +Uid + "=\""	+ Tag_UID + "\"";
		Cursor ToReturn = db.rawQuery(str, null);
		Log.e("leewoo","Cursor="+ToReturn);
		ToReturn.moveToFirst();
		return ToReturn;
	}
	//未测试
	public Cursor seleteListClass(SQLiteDatabase db, String Tag_UID, String Tag_Class) {
		String str = "SELECT * FROM " + Table_Name_List + " WHERE " +Uid + "=\"" + Tag_UID + "\" AND "+Class+ "=\""+Tag_Class+"\"";
		Cursor ToReturn = db.rawQuery(str, null);
		ToReturn.moveToFirst();
		return ToReturn;
	}
//	public Cursor seleteEtc(SQLiteDatabase db, int Tag_ID) {
//		String str = "SELECT * FROM " + Table_Name_Etc + " WHERE " +Uid + "=\""	+ Tag_ID + "\"";
//		Cursor ToReturn = db.rawQuery(str, null);
//		ToReturn.moveToFirst();
//		return ToReturn;
//	}
	public Cursor seleteBtnLearn(SQLiteDatabase db, int Tag_DevID) {
		String str = "SELECT * FROM " + Table_Name_Btn + " WHERE " +DevID + "=\""+ Tag_DevID + "\" AND "+Type+ "=\"learn\"";
		Cursor ToReturn = db.rawQuery(str, null);
		ToReturn.moveToFirst();
		return ToReturn;
	}
	public Cursor seleteBtnName(SQLiteDatabase db, int Tag_DevID) {
		String str = "SELECT * FROM " + Table_Name_Btn + " WHERE " +DevID + "=\""+ Tag_DevID + "\" AND "+Type+ "=\"name\"";
		Cursor ToReturn = db.rawQuery(str, null);
		ToReturn.moveToFirst();
		return ToReturn;
	}
	public Cursor seleteBtn(SQLiteDatabase db, int Tag_DevID) {
		String str = "SELECT * FROM " + Table_Name_Btn + " WHERE " +DevID + "=\""+ Tag_DevID + "\"";
		Cursor ToReturn = db.rawQuery(str, null);
		ToReturn.moveToFirst();
		return ToReturn;
	}
	public void updateListStatus(SQLiteDatabase db, String Tag_ID, String Tag_Status) {
		String str = "update " + Table_Name_List + " set "+ Status+"='"+Tag_Status+"' where " + Uid + "=\"" + Tag_ID + "\"";
		db.execSQL(str);
	}
		
	public void updateBtnName(SQLiteDatabase db,int Tag_DevID, int Tag_BtnID, String Tag_Name) {
		String str = "update " + Table_Name_Btn + " set  button"+Tag_BtnID+" ='"+Tag_Name+"' where " + DevID + "=\"" + Tag_DevID +"\" AND "+Type +"=\"name\"";
		
		db.execSQL(str);
	}
	public void updateBtnlearn(SQLiteDatabase db,int Tag_DevID, int Tag_BtnID, Boolean Tag_Learn) {
		String flag;
		if(Tag_Learn){flag="true";}else{flag="false";}
		String str = "update " + Table_Name_Btn + " set  button"+Tag_BtnID+" ='"+flag+"' where " + DevID + "=\"" + Tag_DevID +"\" AND "+Type +"=\"learn\"";
		
		db.execSQL(str);
	}
//	public void updateEtcStatus(SQLiteDatabase db, String Tag_ID) {
//		String str = "update " + Table_Name_Etc + " set "+ Status+"='yes' where " + Uid + "=\"" + Tag_ID + "\"";
//		db.execSQL(str);
//	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		System.out.println("--------onUpdate Called--------" 
			+ oldVersion + "--->" + newVersion);
	}
}
