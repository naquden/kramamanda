/*
 * Copyright (c) 2015 Backenhof
 */
package com.atte.kramamanda.ui.hugs;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

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
        setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /**
     * Initializes this list.
     */
    public void init(OnHugItemClickedListener hugClickListener) {
        mAdapter = new HugAdapter(getContext(), hugClickListener);
        setAdapter(mAdapter);
    }

    /**
     * Reloads the list items and redraws the list.
     */
    public void refresh() {
        mAdapter.refresh(getContext());
    }
 }