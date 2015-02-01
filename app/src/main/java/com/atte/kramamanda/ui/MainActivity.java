/*
 * Copyright (c) Backenhof.
 */
package com.atte.kramamanda.ui;

import android.app.Activity;
import android.os.Bundle;

import com.atte.kramamanda.R;

/**
 * Main activity. Contains the fragments.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
