package com.example.erikaestrada.hw3;

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

public class NotificationUtils {

    private static final int NEWS_PENDING_INTENT_ID = 3417;
    private static final String NEWS_NOTIFICATION_CHANNEL_ID = "news_notification_channel";
    private static final int NEWS_NOTIFICATION_ID = 1138;
    private static final int ACTION_IGNORE_PENDING_INTENT_ID = 14;

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void sendNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        // notification channel for Oreo
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    NEWS_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, NEWS_NOTIFICATION_CHANNEL_ID)
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setSmallIcon(R.drawable.ic_public_black_24dp)
                    .setLargeIcon(largeIcon(context))
                    .setContentTitle(context.getString(R.string.news_notification_title))
                    .setContentText(context.getString(R.string.news_notification_body))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(
                            context.getString(R.string.news_notification_body)))
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setContentIntent(contentIntent(context)) // launch application when clicked
                    .addAction(ignoreNotificationAction(context))
                    .setAutoCancel(true); // disappear when clicked

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(NEWS_NOTIFICATION_ID, notificationBuilder.build());
    }

    private static NotificationCompat.Action ignoreNotificationAction(Context context) {
        Intent ignoreNotifIntent = new Intent(context, NewsIntentService.class);
        ignoreNotifIntent.setAction(RefreshTasks.ACTION_DISMISS_NOTIFICATION);
        PendingIntent ignorePendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreNotifIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        NotificationCompat.Action ignoreAction = new NotificationCompat.Action(
                R.drawable.ic_public_black_24dp,
                "Cancel",
                ignorePendingIntent
        );
        return ignoreAction;
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);

        return PendingIntent.getActivity(
                context,
                NEWS_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_public_black_24dp);
        return largeIcon;
    }
}
