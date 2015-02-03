/*
 * Copyright (c) Backenhof.
 */
package com.atte.kramamanda.ui;

import android.app.Activity;
import android.os.Bundle;

import com.atte.kramamanda.R;
import com.atte.kramamanda.ui.hugs.HugListFragment;

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
        setContentView(R.layout.activity_main);
        initMonthView(savedInstanceState);
    }

    /**
     * Initializes the month view.
     */
    private void initMonthView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            HugListFragment monthFragment = new HugListFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_container, monthFragment, TAG_MONTH_FRAGMENT)
                    .commit();
        }
    }
}
