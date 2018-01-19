package com.shravandhakal.takebreak;
import android.content.Context;
import android.os.AsyncTask;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.shravandhakal.takebreak.NotificationUtil;

public class ReminderFirebaseJobService extends JobService {


    private AsyncTask mBackgroundTask;


    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
    static final String ACTION_BREAK_REMINDER = "break-reminder";


    public static void executeTask(Context context, String action) {

         if (ACTION_DISMISS_NOTIFICATION.equals(action)) {
            NotificationUtil.clearAllNotifications(context);
        }
        if ( ACTION_BREAK_REMINDER.equals(action))
        {
            NotificationUtil.remindUser(context);
        }
    }


    @Override
    public boolean onStartJob(final JobParameters job) {

        mBackgroundTask = new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {

                Context context = ReminderFirebaseJobService.this;
                ReminderFirebaseJobService.executeTask(context, ReminderFirebaseJobService.ACTION_BREAK_REMINDER);
                return null;
            }
            @Override
            protected void onPostExecute(Object o) {


                jobFinished(job, false);
            }
        };


        mBackgroundTask.execute();
        return true;

    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if (mBackgroundTask != null) mBackgroundTask.cancel(true);
        return true;
    }
}


