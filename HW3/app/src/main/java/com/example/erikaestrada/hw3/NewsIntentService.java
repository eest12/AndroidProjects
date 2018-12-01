package com.example.erikaestrada.hw3;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

public class NewsIntentService extends IntentService {

    public NewsIntentService() {
        super("DatabaseIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        Context context = NewsIntentService.this;
        RefreshTasks.executeTask(context, action);
    }
}