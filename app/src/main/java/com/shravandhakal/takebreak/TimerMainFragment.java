package com.shravandhakal.takebreak;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import static com.shravandhakal.takebreak.MainActivity.isOn;


public class TimerMainFragment extends Fragment {

    private static Chronometer timer;
    private static long timeWhenStopped;
    private Toast mToast;
    private Button start_timer;
    private Button stop_timer;
    private Button resume_timer;
    private Button pause_timer;

    public TimerMainFragment()
    {
        timeWhenStopped=0;
        timer=null;
        mToast=null;
        start_timer=null;
        stop_timer=null;
        resume_timer=null;
        pause_timer=null;
    }

    public static TimerMainFragment newInstance(){
        TimerMainFragment fragment = new TimerMainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timer_page, container, false);
        timer = (Chronometer)view.findViewById(R.id.chronometer1);
         start_timer = (Button)view.findViewById(R.id.button1);
         stop_timer = (Button) view.findViewById(R.id.button2);
         resume_timer = (Button) view.findViewById(R.id.button3);
         pause_timer = (Button) view.findViewById(R.id.button4);

        start_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isOn=true;
                ReminderUtil.scheduleReminder(getContext());
                if(mToast != null) mToast.cancel();
                mToast = Toast.makeText(getContext(), R.string.start_toast, Toast.LENGTH_SHORT);
                mToast.show();
                TimerMainFragment.timer.setBase(SystemClock.elapsedRealtime());
                TimerMainFragment.timer.start();
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
                        ReminderUtil.scheduleReminder(getContext());
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
                if(isOn)
                    ReminderUtil.cancelReminder();
                NotificationUtil.clearAllNotifications(getContext());
                start_timer.setVisibility(view.VISIBLE);
                resume_timer.setVisibility(view.GONE);
                pause_timer.setVisibility(view.GONE);
                TimerMainFragment.timer.setBase(SystemClock.elapsedRealtime());
                TimerMainFragment.timer.stop();

            }
        });

        return view;
    }




}
