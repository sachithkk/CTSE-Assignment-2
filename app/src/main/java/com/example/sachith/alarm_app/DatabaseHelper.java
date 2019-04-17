package com.example.sachith.alarm_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String databaseName = "mydb";
    private static final String tableName = "alarmtable";

    private static final String col1 = "time";
    private static final String col2 = "alarmName";
    private static final String col3 = "alarmTone";
    private static final String col4 = "am_pm";

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
//        onUpgrade(db , 1 , 1);
        //addAlarm("wake up" , "tring" , "01.40 AM");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+tableName +"(id integer PRIMARY KEY AUTOINCREMENT,"+"time text,alarmName text,alarmTone text,am_pm text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+tableName);
        onCreate(db);
    }

    public boolean addAlarm(String alarmTime ,String name){

        String am_pm = alarmTime.substring(6,alarmTime.length());
        String newTime = alarmTime.substring(0,5)+":00";

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1 , newTime);
        contentValues.put(col2 , name);
        contentValues.put(col4 , am_pm);

        long result = db.insert(tableName , null , contentValues);
        if(result != -1){
            return true;
        }
        return false;
    }


    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM alarmtable ORDER BY time(time) DESC LIMIT 100" , null);
//        SELECT *  FROM alarmtable ORDER  BY time(time) DESC LIMIT 1
        return cursor;
    }

    public Cursor getNearTime(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM alarmtable ORDER BY time(time) DESC LIMIT 1" , null);
//        SELECT *  FROM alarmtable ORDER  BY time(time) DESC LIMIT 1
        return cursor;
    }
}
