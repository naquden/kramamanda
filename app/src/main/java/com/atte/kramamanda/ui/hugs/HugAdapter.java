/*
 * Copyright (c) 2015 Backenhof
 */
package com.atte.kramamanda.ui.hugs;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atte.kramamanda.R;
import com.atte.kramamanda.ui.hugs.database.HugDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter used by HugListView.
 */
public class HugAdapter extends RecyclerView.Adapter<HugViewHolder> {
    private List<Hug> mHugs = new ArrayList<Hug>();
    private OnHugItemClickedListener mHugClickListener;

    /**
     * Constructor.
     */
    public HugAdapter(Context context, OnHugItemClickedListener hugClickListener) {
        mHugClickListener = hugClickListener;
        fetchAllHugs(context);
    }

    /**
     * Adds a new hug to the list.
     */
    public void addHug(Hug hug) {
        mHugs.add(hug);
        notifyDataSetChanged();
    }

    /**
     * Adds all hugs in the given list.
     */
    public void addHugs(List<Hug> hugs) {
        mHugs.addAll(hugs);
        notifyDataSetChanged();
    }

    /**
     * Inflates a hug view and returns a HugHolder.
     */
    @Override
    public HugViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.view_hug_item, parent, false);
        return new HugViewHolder(view, mHugClickListener);
    }

    /**
     * Updates the given view holder to show the data from the item in the given position.
     */
    @Override
    public void onBindViewHolder(HugViewHolder holder, int position) {
        holder.bindHug(mHugs.get(position));
    }

    /**
     * Returns the amount of hug items there are.
     */
    @Override
    public int getItemCount() {
        return mHugs.size();
    }

    /**
     * Fetches all hugs from the database.
     */
    private void fetchAllHugs(Context context) {
        new AsyncTask<Context, Void, List<Hug>>() {
            @Override
            protected List<Hug> doInBackground(Context... context) {
                return HugDatabaseHelper.getAllHugs(context[0]);
            }

            @Override
            protected void onPostExecute(List<Hug> hugs) {
                super.onPostExecute(hugs);
                addHugs(hugs);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, context);
    }
}
