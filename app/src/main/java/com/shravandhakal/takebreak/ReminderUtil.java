package com.shravandhakal.takebreak;

import android.content.Context;
import android.support.annotation.NonNull;
import android.content.Context;
import android.os.AsyncTask;
import com.firebase.jobdispatcher.*;
import java.util.concurrent.TimeUnit;

public class ReminderUtil {


    private static final int REMINDER_INTERVAL_MINUTES = 1;
    private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

    private static final String REMINDER_JOB_TAG = "break_reminder_tag";

    private static boolean sInitialized;

    synchronized public static void scheduleReminder(@NonNull final Context context) {


        if (sInitialized) return;


        Driver driver = new GooglePlayDriver(context);

        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job constraintReminderJob = dispatcher.newJobBuilder()

                .setService(ReminderFirebaseJobService.class)

                .setTag(REMINDER_JOB_TAG)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        REMINDER_INTERVAL_SECONDS,
                        REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(constraintReminderJob);


        sInitialized = true;
    }

}
