package com.shravandhakal.takebreak;

import android.content.Context;
import android.support.annotation.NonNull;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.firebase.jobdispatcher.*;
import java.util.concurrent.TimeUnit;

public class ReminderUtil {


    private static int REMINDER_INTERVAL_MINUTES = 60;
    private static  int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
    private static  int SYNC_FLEXTIME_SECONDS = Math.round(REMINDER_INTERVAL_SECONDS*80/100);

    private static final String REMINDER_JOB_TAG = "break_reminder_tag";
    private static FirebaseJobDispatcher dispatcher;

    private static boolean sInitialized;

    synchronized public static void scheduleReminder(@NonNull final Context context) {

        Log.d("1","Reminder good");
        if (sInitialized) return;


        Driver driver = new GooglePlayDriver(context);

         dispatcher = new FirebaseJobDispatcher(driver);

        Job constraintReminderJob = dispatcher.newJobBuilder()

                .setService(ReminderFirebaseJobService.class)
                .setTag(REMINDER_JOB_TAG)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(REMINDER_INTERVAL_SECONDS,SYNC_FLEXTIME_SECONDS+REMINDER_INTERVAL_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(constraintReminderJob);


        sInitialized = true;
    }

    synchronized  public static void cancelReminder()
    {
        dispatcher.cancel(REMINDER_JOB_TAG);
        sInitialized=false;
    }

    synchronized public static int changeTime(int minutes,Context context)
    {
        if(MainActivity.isOn)
        {
            update_time(minutes);
            cancelReminder();
            scheduleReminder(context);
            return 1;
        }
        return 0;

    }

    synchronized private static void update_time(int minutes)
    {
        REMINDER_INTERVAL_MINUTES = minutes;
        REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
        SYNC_FLEXTIME_SECONDS = Math.round(REMINDER_INTERVAL_SECONDS*80/100);

    }


}
