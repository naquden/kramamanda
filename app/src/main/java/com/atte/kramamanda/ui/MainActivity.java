/*
 * Copyright (c) Backenhof.
 */
package com.atte.kramamanda.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import com.atte.kramamanda.R;

/**
 * Main activity. Contains the fragments.
 */
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTestViews();
    }

    /**
     * Method to initialize elements for test purpose.
     * TODO: remove this method and following test elements
     */
    private void initTestViews() {
        // Test Notification
        findViewById(R.id.btn_test_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationService.scheduleNotification(
                        MainActivity.this,
                        NotificationService.NOTIFICATION_ID_HUG_WAITING_ID,
                        (System.currentTimeMillis() + 5000));
            }
        });
    }
}
