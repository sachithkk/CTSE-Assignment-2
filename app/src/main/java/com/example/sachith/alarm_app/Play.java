package com.example.sachith.alarm_app;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Play {

    public static MediaPlayer mediaPlayer;
    public static boolean isplayingAudio=false;
    List<String> toneList;
    final Map<String, Integer> toneMap = new HashMap<>();

    public Play(){
        //set values to ring List
        toneList = new ArrayList<>();
        toneList.add("-None-");
        toneList.add("Bright Morning");
        toneList.add("Early Twilight");
        toneList.add("Gentle Breeze");
        toneList.add("Icicles");
        toneList.add("Jump Start");
        toneList.add("Rolling Fog");
        toneList.add("Sun Shower");


        toneMap.put("Bright Morning", R.raw.ring_bright_morning);
        toneMap.put("Early Twilight", R.raw.ring_early_twilight);
        toneMap.put("Gentle Breeze", R.raw.ring_gentle_breeze);
        toneMap.put("Icicles", R.raw.ring_icicles);
        toneMap.put("Jump Start", R.raw.ring_jump_start);
        toneMap.put("Rolling Fog", R.raw.ring_rolling_fog);
        toneMap.put("Sun Shower", R.raw.ring_sun_shower);
    }

    public void startAlarm(Context context , String toneId){

        int id = Integer.parseInt(toneId);
        System.out.println("------------------->>>> "+id);

        mediaPlayer = MediaPlayer.create(context , R.raw.ring_bright_morning);

        if(!mediaPlayer.isPlaying())
        {
            isplayingAudio=true;
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
    }

    public void stopAlarm(){
        mediaPlayer.stop();
    }
}
