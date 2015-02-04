/*
 * Copyright (c) 2015 Backenhof
 */
package com.atte.kramamanda.ui.hugs;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

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
    }

    /**
     * Initializes this list.
     */
    public void init(OnHugItemClickedListener hugClickListener) {
        mAdapter = new HugAdapter(hugClickListener);
        setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter(mAdapter);

        // Add a temporary image to test this out
        new ImageDownloader(getContext()) {
            @Override
            protected void onPostExecute(String filePath) {
                super.onPostExecute(filePath);
                mAdapter.addHug(new Hug(filePath, "First hug!", System.currentTimeMillis()));
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, KramConstant.GOOGLE_SEARCH_URL);
    }
}