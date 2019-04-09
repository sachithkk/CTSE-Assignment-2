package com.example.sachith.alarm_app;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CTP extends Activity {

    ListView lvHour, lvMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctp);

        lvHour = findViewById(R.id.lvHour);
        lvMinute = findViewById(R.id.lvMinute);

        ArrayAdapter<String> hourAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1
                , getResources().getStringArray(R.array.hours));
        lvHour.setAdapter(hourAdapter);

        ArrayAdapter<String> minuteAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, getResources().getStringArray(R.array.minutes));
        lvMinute.setAdapter(minuteAdapter);
    }
}
