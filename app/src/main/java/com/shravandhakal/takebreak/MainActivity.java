package com.shravandhakal.takebreak;

import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static Chronometer timer;
    private static long timeWhenStopped;
    private Toast mToast;


    public MainActivity(){timer=null; timeWhenStopped=0;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        timer = (Chronometer)findViewById(R.id.chronometer1);
        final Button start_timer = (Button)findViewById(R.id.button1);
        final Button stop_timer = (Button) findViewById(R.id.button2);
        final Button resume_timer = (Button) findViewById(R.id.button3);
        final Button pause_timer = (Button) findViewById(R.id.button4);


        start_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReminderUtil.scheduleReminder(getApplicationContext());
                if(mToast != null) mToast.cancel();
                mToast = Toast.makeText(getApplicationContext(), R.string.start_toast, Toast.LENGTH_SHORT);
                mToast.show();
                MainActivity.timer.setBase(SystemClock.elapsedRealtime());
                MainActivity.timer.start();
                start_timer.setVisibility(view.GONE);
                resume_timer.setVisibility(view.GONE);
                pause_timer.setVisibility(view.VISIBLE);


            }
        });

        pause_timer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ReminderUtil.cancelReminder();
                        start_timer.setVisibility(view.GONE);
                        resume_timer.setVisibility(view.VISIBLE);
                        pause_timer.setVisibility(view.GONE);
                        timeWhenStopped = timer.getBase() - SystemClock.elapsedRealtime();
                        timer.stop();
                        timer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                    }
                }
        );

        resume_timer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ReminderUtil.scheduleReminder(getApplicationContext());
                        timer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                        start_timer.setVisibility(view.GONE);
                        resume_timer.setVisibility(view.GONE);
                        pause_timer.setVisibility(view.VISIBLE);
                        timer.start();
                    }
                }
        );

        stop_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReminderUtil.cancelReminder();
                NotificationUtil.clearAllNotifications(getApplicationContext());
                start_timer.setVisibility(view.VISIBLE);
                resume_timer.setVisibility(view.GONE);
                pause_timer.setVisibility(view.GONE);
                MainActivity.timer.setBase(SystemClock.elapsedRealtime());
                MainActivity.timer.stop();
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getApplicationContext()).setContentTitle("My notification").setContentText("Hello World!");

            }
        });

    }


}
