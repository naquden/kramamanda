/*
 * Copyright (c) Backenhof.
 */
package com.atte.kramamanda.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import com.atte.kramamanda.R;
import com.atte.kramamanda.backend.HugRequestService;
import com.atte.kramamanda.ui.hugs.Hug;
import com.atte.kramamanda.ui.hugs.HugListFragment;
import com.atte.kramamanda.backend.HugDatabaseHelper;

import util.ImageDownloader;
import util.KramConstant;
import util.KramPreferences;

/**
 * Main activity. Contains the fragments.
 */
public class MainActivity extends Activity {
    private static final String TAG_MONTH_FRAGMENT = "monthFragment";

    /**
     * Called to create this activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!KramPreferences.hasStartedAppBefore(this)) {
            // First time app is started
            requestFirstHug();
            HugRequestService.scheduleHugRequests(this);
            KramPreferences.putHasStartedAppBefore(this);
        }

        requestFirstHug();

        setContentView(R.layout.activity_main);
        initMonthView(savedInstanceState);
    }

    /**
     * Initializes the month view.
     */
    private void initMonthView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            HugListFragment hugFragment = new HugListFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_container, hugFragment, TAG_MONTH_FRAGMENT)
                    .commit();
        }
    }

    /**
     * Requests the very first hug.
     */
    private void requestFirstHug() {
        new ImageDownloader(this) {
            @Override
            protected void onPostExecute(String filePath) {
                super.onPostExecute(filePath);
                Hug hug = new Hug(
                        getString(R.string.first_hug_message),
                        filePath,
                        System.currentTimeMillis());
                HugDatabaseHelper.insertHug(MainActivity.this, hug);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, KramConstant.GOOGLE_SEARCH_URL);
    }
}
