package com.example.sachith.alarm_app;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AlarmService extends Service{

    public int counter=0;
    private int nxtAlarm = 10;
    boolean isStatus = true;

    public AlarmService(){}

    DatabaseHelper databaseHelper;
    public static MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onCreate() {

        databaseHelper = new DatabaseHelper(this);

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        startTimer();
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    public void startAlarm(){
        mediaPlayer = MediaPlayer.create(getApplicationContext() , R.raw.ring_bright_morning);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

    }

    public void stopAlarm(){
        mediaPlayer = MediaPlayer.create(getApplicationContext() , R.raw.ring_bright_morning);
        mediaPlayer.stop();
    }

    private Timer timer;
    private TimerTask timerTask;
    public void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                SimpleDateFormat formatter= new SimpleDateFormat("hh:mm:ss a");
                Date date = new Date(System.currentTimeMillis());
                String currentTime = formatter.format(date);
                boolean isTrue = true;

                Log.i("Count", "=========  "+ (counter++));
                Log.i("Time" , "==========="+currentTime);
                    Cursor result = databaseHelper.getNearTime();
                    if(result.getCount() != 0){
                        while (result.moveToNext()){
                            String dbTime = result.getString(1)+" "+result.getString(4);
                            if(currentTime.equalsIgnoreCase(dbTime)){
                                Play play = new Play();
                                play.startAlarm(getApplicationContext());
                                Intent intent = new Intent(getApplicationContext() , AlarmOff.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    }
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }

    public void deletePastAlarm(int id){

    }

}
