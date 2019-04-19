package com.example.sachith.alarm_app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Alarm extends AppCompatActivity{

    private Button setAlarm;
    private TextView status;
    DatabaseHelper databaseHelper;
    ListView listView;
    TimePicker view;
    boolean isData = false;
    MediaPlayer mediaPlayer;
    Play play = new Play();

    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    public Alarm(){
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        databaseHelper = new DatabaseHelper(this);
        mediaPlayer = MediaPlayer.create(this, R.raw.ring_bright_morning);


        setAlarm = findViewById(R.id.createAlarm);
        listView = findViewById(R.id.list1);
//        alarmOffButton = findViewById(R.id.offBtn);

        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Alarm.this, PopupActivity.class);
                startActivity(intent);
            }
        });

        viewData();

        Intent intent = getIntent();
        boolean isAlarmRing = intent.getBooleanExtra("isAlarmWork" , false);

        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss a");

        Cursor result = databaseHelper.getAllData();
        if (result.getCount() != 0) {
            Intent alarmServiceIntent = new Intent(this, AlarmService.class);
            startService(alarmServiceIntent);
        }

//        if(isAlarmRing){
//            alarmOffButton.setVisibility(View.VISIBLE);
//            alarmOffButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(getApplicationContext() , AlarmOff.class);
//                    startActivity(intent);
//                }
//            });
//        }
//        else{
//            alarmOffButton.setVisibility(View.INVISIBLE);
//        }
    }

    public void viewData() {
        Cursor result = databaseHelper.getAllData();
        Calendar calendar = Calendar.getInstance();

        if(result.getCount() == 0){
            return;
        }
        else{
            String am_pm = findAmPm();
            ArrayList<String> amlist = new ArrayList<>();
            ArrayList<String> pmlist = new ArrayList<>();

            isData = true;
            while (result.moveToNext()){

                String AM_PM = result.getString(4);

                if(AM_PM.equalsIgnoreCase("PM")){
                    pmlist.add(result.getString(1)+" "+result.getString(4)+" "+result.getString(2));
                }
                else if(AM_PM.equalsIgnoreCase("AM")){
                    amlist.add(result.getString(1)+" "+result.getString(4)+" "+result.getString(2));
                }
            }
            if(am_pm.equalsIgnoreCase("AM")){
                list.addAll(amlist);
                list.addAll(pmlist);
            }
            else{
                list.addAll(pmlist);
                list.addAll(amlist);
            }

            arrayAdapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 , list);
            listView.setAdapter(arrayAdapter);
        }
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
}
