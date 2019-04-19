package com.example.sachith.alarm_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;

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

    public boolean addAlarm(String alarmTime ,String name , int toneId){

        String am_pm = alarmTime.substring(6,alarmTime.length());
        String newTime = alarmTime.substring(0,5)+":00";
        String id = String.valueOf(toneId);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1 , newTime);
        contentValues.put(col2 , name);
        contentValues.put(col3,id);
        contentValues.put(col4 , am_pm);

        long result = db.insert(tableName , null , contentValues);
        if(result != -1){
            return true;
        }
        return false;
    }

    public String findAmPm(){

        long date = System.currentTimeMillis();
        SimpleDateFormat time1 = new SimpleDateFormat("kk");
        String timeString1 = time1.format(date);
        int timeFormat = Integer.parseInt(timeString1);
        System.out.println(timeString1);

        if(timeFormat < 12){
            return "AM";
        }
        return "PM";
    }


    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM alarmtable ORDER BY time(time) DESC LIMIT 100" , null);
//        SELECT *  FROM alarmtable ORDER  BY time(time) DESC LIMIT 1
        return cursor;
    }

    public Cursor getNearTime() {
        String am_pm = findAmPm();
        if (am_pm.equalsIgnoreCase("AM")) {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] selectionArgs = {"AM" + "%"};
            Cursor cursor = db.rawQuery("SELECT * FROM alarmtable WHERE am_pm LIKE ? ORDER BY time(time) DESC LIMIT 1", selectionArgs);
//        SELECT *  FROM alarmtable ORDER  BY time(time) DESC LIMIT 1
            return cursor;
        } else {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] selectionArgs = {"PM" + "%"};
            Cursor cursor = db.rawQuery("SELECT * FROM alarmtable WHERE am_pm LIKE ? ORDER BY time(time) DESC LIMIT 1", selectionArgs);
//        SELECT *  FROM alarmtable ORDER  BY time(time) DESC LIMIT 1
            return cursor;

        }
    }

    public Integer deleteAlarmrow(String Id){

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName , "id = ?",new String[] {Id});
    }
}
