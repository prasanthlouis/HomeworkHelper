package com.psiuol21.assignmentchecklist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




	public class DBAdapter {

		public static final String KEY_ROWID = "id";
		public static final String KEY_TITLE = "title";
		public static final String KEY_DATE = "date";
		public static final String KEY_SUBJECT = "subject";
		public static final String KEY_DES = "des";
		
		public static final String DATABASENAME = "helperdb";
		public static final String TABLENAME = "workable";
		public static final int DATABASEVERSION = 2;
		
		public static final String DATABASE_CREATE="create table if not exists workable(id integer primary key autoincrement,title VARCHAR, "
				+ "date date, subject VARCHAR,des VARCHAR);";
		public final Context context;
		public DatabaseHelper DBHelper;
		public SQLiteDatabase db;
		
		public DBAdapter(Context con)
		{
			this.context=con;
			DBHelper=new DatabaseHelper(context);
		}
		
		


	public static class DatabaseHelper extends SQLiteOpenHelper
	{
		
		DatabaseHelper(Context context)
		{
			super(context,DATABASENAME,null,DATABASEVERSION);
		}
		public void onCreate(SQLiteDatabase db)
		{
			try
			{
				db.execSQL(DATABASE_CREATE);
			}catch(Exception e){}
			
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
	}
	public DBAdapter open() throws Exception
	{
		db=DBHelper.getWritableDatabase();
		return this;
	}
	public void close()
	{
		DBHelper.close();
	}

	public long insertRecord(String title, String duedate, String course, String notes)
	{
		ContentValues initial=new ContentValues();
		initial.put(KEY_TITLE, title);
		initial.put(KEY_DATE, duedate);
		initial.put(KEY_SUBJECT, course);
		initial.put(KEY_DES, notes);
		return db.insert(TABLENAME, null, initial);
		
	}


	public boolean deleteRecord(long rowId)
	{
		return db.delete(TABLENAME, KEY_ROWID+"="+rowId, null)>0;
	}
	public Cursor getAllRecords()
	{
		return db.query(TABLENAME, new String[] {KEY_ROWID,KEY_TITLE,KEY_DATE,KEY_SUBJECT,KEY_DES}, null, null, null, null,null);
	}
	public Cursor getRecord(long rowId)
	{
		Cursor cursor=db.query(true,TABLENAME, new String[] {KEY_ROWID,KEY_TITLE,KEY_DATE,KEY_SUBJECT,KEY_DES}, KEY_ROWID+"="+rowId, null, null, null, null, null);
		if(cursor!=null)
		{
			cursor.moveToFirst();
		}
		return cursor;
	}
	public boolean updateRecords(long rowId,String title, String duedate, String course, String notes)
	{
		ContentValues cv=new ContentValues();
		cv.put(KEY_TITLE, title);
		cv.put(KEY_DATE, duedate);
		cv.put(KEY_SUBJECT, course);
		cv.put(KEY_DES, notes);
		return db.update(TABLENAME, cv, KEY_ROWID+"="+rowId, null)>0;
	}
	}
