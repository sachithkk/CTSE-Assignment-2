package com.example.sachith.alarm_app;

import android.content.Context;
import android.media.MediaPlayer;

public class Play {

    public static MediaPlayer mediaPlayer;
    public static boolean isplayingAudio=false;

    public void startAlarm(Context context){
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
