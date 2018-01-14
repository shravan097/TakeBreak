package com.shravandhakal.takebreak;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {
    private static Chronometer timer;

    public MainActivity(){timer=null;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start_timer = (Button)findViewById(R.id.button1);
        Button stop_timer = (Button) findViewById(R.id.button2);
        timer = (Chronometer)findViewById(R.id.chronometer1);
        start_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.timer.setBase(SystemClock.elapsedRealtime());
                MainActivity.timer.start();
            }
        });

        stop_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.timer.setBase(SystemClock.elapsedRealtime());
                MainActivity.timer.stop();

            }
        });
    }
}
