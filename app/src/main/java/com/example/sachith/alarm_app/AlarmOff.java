package com.example.sachith.alarm_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class AlarmOff extends Activity {

    RadioGroup radioGroup;
    RadioButton r_1 , r_2 , r_3 , r_4;
    Button button;
    RadioButton radioButton;
    TextView textView;

    Random random = new Random();
    Play play = new Play();

    private static boolean isAnswerCorrect = false;
    Alarm alarm;
    private static int num1, num2, num3, num4, num5;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_off);
        alarm  = new Alarm();

        initComponents();

        generateRandomValues();

        int answer = quizGenerate();
        textView.setText(String.valueOf(num1)+" - "+String.valueOf(num2)+" + "+String.valueOf(num3)+" - "+String.valueOf(num4)+" + "+String.valueOf(num5)+"= ?");

        r_1.setText(String.valueOf(answer*10));
        r_2.setText(String.valueOf(answer-1));
        r_3.setText(String.valueOf(answer+5));
        r_4.setText(String.valueOf(answer));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    public void initComponents(){
        radioGroup = findViewById(R.id.radioGroup);
        button = findViewById(R.id.checkBtn);
        textView = findViewById(R.id.question);
        r_1 = findViewById(R.id.ans_1);
        r_2 = findViewById(R.id.ans_2);
        r_3 = findViewById(R.id.ans_3);
        r_4 = findViewById(R.id.ans_4);
    }

    public void generateRandomValues(){
        num1 = random.nextInt(10);
        num2 = random.nextInt(10);
        num3 = random.nextInt(10);
        num4 = random.nextInt(10);
        num5 = random.nextInt(10);
    }

    public int quizGenerate(){
        return (num1-num2+num3-num4+num5);
    }

    public void setAns(View view){
        int radioId = radioGroup.getCheckedRadioButtonId();
        String quizAnswer = String.valueOf(quizGenerate());
        radioButton = findViewById(radioId);

        if(quizAnswer.equalsIgnoreCase(String.valueOf(radioButton.getText()))){
            isAnswerCorrect = true;
            //Toast.makeText(this , "Correct Answer", Toast.LENGTH_LONG).show();
        }
        else{
            isAnswerCorrect = false;
            //Toast.makeText(this , "Wrong Answer", Toast.LENGTH_LONG).show();
        }
    }

    public void checkAnswer(){
        if(isAnswerCorrect){

            Play play = new Play();
            play.stopAlarm();

            Intent alarmService = new Intent(this, AlarmService.class);
            stopService(alarmService);

            Intent intent = new Intent(getApplicationContext() , Alarm.class);
            startActivity(intent);
        }
    }
}
