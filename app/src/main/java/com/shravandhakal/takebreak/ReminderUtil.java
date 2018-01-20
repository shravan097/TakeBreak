package com.shravandhakal.takebreak;

import android.content.Context;
import android.support.annotation.NonNull;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.firebase.jobdispatcher.*;
import java.util.concurrent.TimeUnit;

public class ReminderUtil {


    private static final int REMINDER_INTERVAL_MINUTES = 1;
   // private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
   private static final int REMINDER_INTERVAL_SECONDS = 1;
    private static final int SYNC_FLEXTIME_SECONDS = 2;

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
                .setTrigger(Trigger.executionWindow(1,2))
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


}
