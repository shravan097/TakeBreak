package com.shravandhakal.takebreak;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

public class NotificationUtil {



        private static final int BREAK_REMINDER_NOTIFICATION_ID = 1138;
        private static final int BREAK_REMINDER_PENDING_INTENT_ID = 3417;
        private static final String BREAK_REMINDER_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel";


        public static void remindUser(Context context) {
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);

            //This only works on  build version is greater than Android Oreo or API 26
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = null;
                if (android.os.Build.VERSION.SDK_INT >= 26) {
                    mChannel = new NotificationChannel(
                            BREAK_REMINDER_NOTIFICATION_CHANNEL_ID,
                            context.getString(R.string.break_reminder_notification),
                            NotificationManager.IMPORTANCE_HIGH);
                    notificationManager.createNotificationChannel(mChannel);
                }

            }


            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setLargeIcon(largeIcon(context))
                    .setContentTitle(context.getString(R.string.break_reminder_notification_title))
                    .setContentText(context.getString(R.string.break_reminder_notification))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(
                            context.getString(R.string.break_reminder_notification)))
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setContentIntent(contentIntent(context))
                    .setAutoCancel(true);

            // Fallback notification call for all th other versions.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                    && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
            }

            notificationManager.notify(BREAK_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
        }


        private static PendingIntent contentIntent(Context context) {
            Intent startActivityIntent = new Intent(context, MainActivity.class);
            return PendingIntent.getActivity(
                    context,
                    BREAK_REMINDER_PENDING_INTENT_ID,
                    startActivityIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }



        private static Bitmap largeIcon(Context context) {
            Resources res = context.getResources();
            Bitmap largeIcon = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher_round);
            return largeIcon;
        }


}
