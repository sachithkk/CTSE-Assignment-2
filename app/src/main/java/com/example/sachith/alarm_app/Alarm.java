package com.example.sachith.alarm_app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Alarm extends AppCompatActivity {

    private TimePickerDialog timePickerDialog;
    private Button setAlarm;
    private TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        setAlarm = findViewById(R.id.createAlarm);
        time = findViewById(R.id.txtTime);

        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                final Calendar c = Calendar.getInstance();
                final int hour = c.get(Calendar.HOUR_OF_DAY);
                final int minute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Alarm.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.HOUR_OF_DAY , hourOfDay);
                                calendar.set(Calendar.MINUTE , minute);
                                calendar.set(Calendar.SECOND , 0);
                                setAlarm(calendar);
                                time.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();*/

                Intent intent = new Intent(Alarm.this, PopupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setAlarm(Calendar calendar){

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this , AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this , 1 , intent , 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP , calendar.getTimeInMillis() , pendingIntent);

    }
}
