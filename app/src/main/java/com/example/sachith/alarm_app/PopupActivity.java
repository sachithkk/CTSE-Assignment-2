package com.example.sachith.alarm_app;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopupActivity extends AppCompatActivity {

    private ImageView ivHoursUp, ivHoursDown, ivMinutesUp, ivMinutesDown;
    private Button btnAmPm, btnSet, btnCancel;
    private EditText etHours, etMinutes, etTitle;
    private TextView tvTime;
    private Spinner spnRingtone;

    MediaPlayer mPlayer = new MediaPlayer();
    Map<String, Integer> toneMap;
    List<String> toneList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        initComponents();

        // add files to ringtone map
        final Map<String, Integer> toneMap = new HashMap<>();
        toneMap.put("Bright Morning", R.raw.ring_bright_morning);
        toneMap.put("Early Twilight", R.raw.ring_early_twilight);
        toneMap.put("Gentle Breeze", R.raw.ring_gentle_breeze);
        toneMap.put("Icicles", R.raw.ring_icicles);
        toneMap.put("Jump Start", R.raw.ring_jump_start);
        toneMap.put("Rolling Fog", R.raw.ring_rolling_fog);
        toneMap.put("Sun Shower", R.raw.ring_sun_shower);

        //set values to ring List
        toneList = new ArrayList<>();
        toneList.add("-Select Alarm Tone-");
        toneList.add("Bright Morning");
        toneList.add("Early Twilight");
        toneList.add("Gentle Breeze");
        toneList.add("Icicles");
        toneList.add("Jump Start");
        toneList.add("Rolling Fog");
        toneList.add("Sun Shower");

        // set array adapter to spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, toneList);
        spnRingtone.setAdapter(adapter);

        // spinner on click event
        spnRingtone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    try {
                        if (mPlayer.isPlaying()) {
                            mPlayer.stop();
                        }
                        int tone = toneMap.get(toneList.get(position));
                        mPlayer = MediaPlayer.create(PopupActivity.this, tone);
                        mPlayer.start();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        mPlayer.stop();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mPlayer.stop();
        super.onDestroy();
    }

    private void initComponents() {
        ivHoursUp = findViewById(R.id.ivHoursUp);
        ivHoursDown = findViewById(R.id.ivHoursDown);
        ivMinutesUp = findViewById(R.id.ivMinutesUp);
        ivMinutesDown = findViewById(R.id.ivMinutesDown);
        btnAmPm = findViewById(R.id.btnAmPm);
        btnCancel = findViewById(R.id.btnCancel);
        btnSet = findViewById(R.id.btnSet);
        etHours = findViewById(R.id.etHours);
        etMinutes = findViewById(R.id.etMinutes);
        etTitle = findViewById(R.id.etTitle);
        tvTime = findViewById(R.id.tvTime);
        spnRingtone = findViewById(R.id.spnRingtone);
    }
}
