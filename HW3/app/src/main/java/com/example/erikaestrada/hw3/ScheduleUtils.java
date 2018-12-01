package com.example.erikaestrada.hw3;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public class ScheduleUtils {

    private static final int SCHEDULE_INTERVAL_SECONDS = 10;
    private static final int SYNC_FLEXTIME_SECONDS = 10;

    private static final String NEWS_JOB_TAG = "news_job_tag";

    private static boolean sInitialized; // keeps track of whether the job has started

    // synchronized so job doesn't execute more than once at a time
    synchronized public static void scheduleRefresh(@NonNull final Context context) {
        if (sInitialized) {
            return;
        }

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        // schedule job with all of the constraints
        Job constraintReminderJob = dispatcher.newJobBuilder()
                .setService(NewsJob.class)
                .setTag(NEWS_JOB_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(SCHEDULE_INTERVAL_SECONDS,
                        SCHEDULE_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(constraintReminderJob);
        sInitialized = true;
    }
}
