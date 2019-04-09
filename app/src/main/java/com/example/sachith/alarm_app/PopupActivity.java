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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopupActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivHoursUp, ivHoursDown, ivMinutesUp, ivMinutesDown;
    private Button btnAmPm, btnSet, btnCancel;
    private EditText etTitle;
    private TextView tvTime, tvHours, tvMinutes;
    private Spinner spnRingtone;

    MediaPlayer mPlayer = new MediaPlayer();
    List<String> toneList;
    List<String> hourList;
    List<String> minuteList;

    String newHour = "01";
    String newMinute = "00";
    boolean isAm = true;
    int hourIndex = 0;
    int minuteIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        initComponents();

        ivHoursUp.setOnClickListener(this);
        ivHoursDown.setOnClickListener(this);
        ivMinutesUp.setOnClickListener(this);
        ivMinutesDown.setOnClickListener(this);
        btnSet.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        addValuesToHourMinutesLists();

        updateTimeField();

        // sounds doesn't play if following map & list value adding are separated to methods
        // keep them in onCreate method

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

    private void addValuesToHourMinutesLists() {
        hourList = new ArrayList<>();
        minuteList = new ArrayList<>();

        for (int i = 0; i < 60; i++) {
            if (i >= 1 && i <= 12) {
                hourList.add((i < 10) ? "0" + i : "" + i); // add hours to hours list
            }
            minuteList.add((i < 10) ? "0" + i : "" + i); // add minutes to hours list
        }
    }

    @Override
    protected void onPause() {
        mPlayer.stop();
        super.onPause();
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
        tvHours = findViewById(R.id.etHours);
        tvMinutes = findViewById(R.id.etMinutes);
        etTitle = findViewById(R.id.etTitle);
        tvTime = findViewById(R.id.tvTime);
        spnRingtone = findViewById(R.id.spnRingtone);
    }

    public void swapAmPm(View view) {
        if (btnAmPm.getText().toString().equalsIgnoreCase("AM")) {
            btnAmPm.setText("PM");
            isAm = false;
        } else {
            btnAmPm.setText("AM");
            isAm = true;
        }
        updateTimeField();
    }

    private void updateTimeField() {
        tvTime.setText(newHour + ":" + newMinute + " " + (isAm ? "AM" : "PM"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivHoursUp:
                if(hourIndex < hourList.size()-1){
                    tvHours.setText(hourList.get(hourIndex+1));
                    hourIndex += 1;
                    newHour = tvHours.getText().toString();
                    updateTimeField();
                }
                break;

            case R.id.ivHoursDown:
                if(hourIndex > 0){
                    tvHours.setText(hourList.get(hourIndex-1));
                    hourIndex -= 1;
                    newHour = tvHours.getText().toString();
                    updateTimeField();
                }
                break;

            case R.id.ivMinutesUp:
                if(minuteIndex < minuteList.size()-1){
                    tvMinutes.setText(minuteList.get(minuteIndex+1));
                    minuteIndex += 1;
                    newMinute = tvMinutes.getText().toString();
                    updateTimeField();
                }
                break;

            case R.id.ivMinutesDown:
                if(minuteIndex > 0){
                    tvMinutes.setText(minuteList.get(minuteIndex-1));
                    minuteIndex -= 1;
                    newMinute = tvMinutes.getText().toString();
                    updateTimeField();
                }
                break;

            case R.id.btnSet:
                Toast.makeText(this, "Alarm Added", Toast.LENGTH_SHORT).show();
                this.finish();
                break;

            case R.id.btnCancel:
                this.finish();
                break;
        }
    }
}
