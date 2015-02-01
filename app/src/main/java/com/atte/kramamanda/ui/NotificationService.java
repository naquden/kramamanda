/*
 * Copyright (c) Backenhof.
 */

package com.atte.kramamanda.ui;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.atte.kramamanda.R;

import util.KramConstant;

/**
 * Class handling all notification shiet.
 */
public class NotificationService extends BroadcastReceiver{

    public static final int NOTIFICATION_ID_HUG_WAITING_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            showNotification(context, intent.getIntExtra(KramConstant.EXTRA_NOTIFICATION_ID, -1));
        }
    }

    public static void showNotification(Context context, int notificationId) {
        switch (notificationId) {
            case NOTIFICATION_ID_HUG_WAITING_ID:
            showHugWaitingNotification(context);
        }
    }

    public static void scheduleNotification(Context context, int notificationId, long timeInMilli) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(
                AlarmManager.RTC,
                timeInMilli,
                getNotificationPendingIntent(context, notificationId));
    }

    /**
     * Show a hug is awaiting notification.
     */
    private static void showHugWaitingNotification(Context context) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, getHugWaitingNotification(context));
    }

    /**
     * Builds and return the hug notification.
     */
    private static Notification getHugWaitingNotification(Context context) {
        return new Notification.Builder(context)
                .setContentIntent(PendingIntent.getActivity(
                        context,
                        0,
                        new Intent(context, MainActivity.class), // TODO: change this to calendar activity (don't forget to add in manifest)
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
    private static PendingIntent getNotificationPendingIntent(Context context, int notificationId) {
        return PendingIntent.getBroadcast(
                context,
                1,
                new Intent(KramConstant.ACTION_SHOW_NOTIFICATION).
                        putExtra(KramConstant.EXTRA_NOTIFICATION_ID, notificationId),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
