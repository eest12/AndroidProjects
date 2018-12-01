package com.example.erikaestrada.hw3;

import android.content.Context;

import java.net.URL;

public class RefreshTasks {

    public static final String ACTION_REFRESH = "refresh";
    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";

    public static void executeTask(Context context, String action) {
        if (action.equals(ACTION_REFRESH)) {
            refreshArticles(context);
        } else if (action.equals(ACTION_DISMISS_NOTIFICATION)) {
            NotificationUtils.clearAllNotifications(context);
        }
    }

    private static void refreshArticles(Context context) {
        URL url = NetworkUtils.buildUrl();
        Repository.syncWithApi(url);
        NotificationUtils.sendNotification(context);
    }
}
