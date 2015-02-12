/*
 * Copyright (c) 2015 Backenhof.
 */
package com.atte.kramamanda.backend;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.atte.kramamanda.R;
import com.atte.kramamanda.ui.MainActivity;
import com.atte.kramamanda.ui.hugs.Hug;

import java.util.Calendar;
import java.util.Random;

import util.ImageDownloader;
import util.KramConstant;
import util.KramLog;
import util.KramTools;
import util.QuoteDownloader;

/**
 * Class handling the background hug requests and shows the notifications to the user.
 */
public class HugRequestService extends BroadcastReceiver{
    private static final float HUG_REQUEST_SUCCESS_CHANCE = 1.0f / 3.0f;

    private String mNewHugMessage = null;
    private String mNewHugImagePath = null;

    /**
     * Called when this has received a hug request broadcast.
     */
    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        KramLog.d("received action:" + action);
        if (action == null || !action.equals(KramConstant.ACTION_REQUEST_HUG)) {
            return;
        }
        if (!KramTools.isNetworkAvailable(context)) {
            KramLog.d("No internet connection available, exiting...");
            return;
        }
        if (new Random().nextFloat() > HUG_REQUEST_SUCCESS_CHANCE) {
            // No luck today, have to wait for tomorrow
            KramLog.i("No hug given this time...");
            return;
        }

        // TODO: PIANO ONLY ALLOW WHEN USING WIFI?

        new ImageDownloader(context) {
            @Override
            protected void onPostExecute(String filePath) {
                super.onPostExecute(filePath);
                mNewHugImagePath = filePath;
                tryToSaveHugAndNotifyUser(context);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, KramConstant.GOOGLE_SEARCH_URL);

        new QuoteDownloader(context.getResources()) {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mNewHugMessage = s;
                tryToSaveHugAndNotifyUser(context);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * Schedules this receiver to awaken every day at 8.00 am.
     */
    public static void scheduleHugRequests(Context context) {
        if (isRecurringHugRequestActive(context)) {
            return;
        }

        // Schedule first occurrence tomorrow 8.00am
        Calendar updateTime = Calendar.getInstance();
        updateTime.add(Calendar.DATE, 1);
        updateTime.set(Calendar.HOUR_OF_DAY, 8);
        updateTime.set(Calendar.MINUTE, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(
                AlarmManager.RTC,
                updateTime.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                getNotificationPendingIntent(context));
    }

    /**
     * Builds and return the hug notification.
     */
    private static Notification getHugWaitingNotification(Context context) {
        return new Notification.Builder(context)
                .setContentIntent(PendingIntent.getActivity(
                        context,
                        0,
                        new Intent(context, MainActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT))
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentText(context.getString(R.string.notification_content_text))
                .setContentTitle(context.getString(R.string.app_name))
                .setAutoCancel(true)
                .build();
    }

    /**
     * Builds and returns a pending intent to show a notification when fired.
     */
    private static PendingIntent getNotificationPendingIntent(Context context) {
        return PendingIntent.getBroadcast(
                context,
                1,
                new Intent(KramConstant.ACTION_REQUEST_HUG),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * Returns true if the recurring alarm for requesting hugs is already active.
     */
    private static boolean isRecurringHugRequestActive(Context context) {
        return PendingIntent.getBroadcast(
                    context,
                    1,
                    new Intent(KramConstant.ACTION_REQUEST_HUG),
                    PendingIntent.FLAG_NO_CREATE)
                != null;
    }

    /**
     * Tries to save a new hug to the database. If successful it will notify the user that there is
     * a new hug.
     */
    private void tryToSaveHugAndNotifyUser(Context context) {
        if (TextUtils.isEmpty(mNewHugMessage) || TextUtils.isEmpty(mNewHugImagePath)) {
            return;
        }

        Hug hug = new Hug(mNewHugMessage, mNewHugImagePath, System.currentTimeMillis());
        HugDatabaseHelper.insertHug(context, hug);
        showHugWaitingNotification(context);
    }

    /**
     * Show a hug is awaiting notification.
     */
    private static void showHugWaitingNotification(Context context) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, getHugWaitingNotification(context));
    }
}
