/*
 * Copyright (c) 2015 Backenhof
 */
package com.atte.kramamanda.ui.hugs;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

import util.ImageDownloader;
import util.KramConstant;

/**
 * RecyclerView for showing hug items in a list.
 */
public class HugListView extends RecyclerView {
    private HugAdapter mAdapter;

    /**
     * Constructor.
     */
    public HugListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAdapter = new HugAdapter();
        setLayoutManager(new LinearLayoutManager(context));
        setAdapter(mAdapter);

        // Add a temporary image to test this out
        new ImageDownloader(context) {
            @Override
            protected void onPostExecute(String filePath) {
                super.onPostExecute(filePath);
                mAdapter.addHug(new Hug(filePath, "First hug!", System.currentTimeMillis()));
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, KramConstant.GOOGLE_SEARCH_URL);
    }
}