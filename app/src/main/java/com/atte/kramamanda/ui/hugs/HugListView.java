/*
 * Copyright (c) 2015 Backenhof
 */
package com.atte.kramamanda.ui.hugs;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.atte.kramamanda.R;

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
        int horizontalPadding =
                (int) getResources().getDimension(R.dimen.card_list_padding_horizontal);
        setPadding(horizontalPadding, 0, horizontalPadding, 0);
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