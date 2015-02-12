/*
 * Copyright (c) 2015 Backenhof
 */
package com.atte.kramamanda.ui.hugs;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atte.kramamanda.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import util.ImageLoader;

/**
 * ViewHolder for showing Hug items.s
 */
public class HugViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
    private static final String DAY_FORMAT = "d MMM";
    private TextView mMessageView;
    private ImageView mImageView;
    private TextView mDateDayView;
    private TextView mDateYearView;
    private ProgressBar mImageProgressView;
    private Hug mHug;
    private OnHugItemClickedListener mHugClickListener;

    /**
     * Constructor.
     */
    public HugViewHolder(View itemView, OnHugItemClickedListener listener) {
        super(itemView);
        mMessageView = (TextView) itemView.findViewById(R.id.hug_item_message);
        mImageView = (ImageView) itemView.findViewById(R.id.hug_item_image);
        mImageProgressView = (ProgressBar) itemView.findViewById(R.id.hug_item_image_progress);
        mDateDayView = (TextView) itemView.findViewById(R.id.hug_item_date_day);
        mDateYearView = (TextView) itemView.findViewById(R.id.hug_item_date_year);
        mHugClickListener = listener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    /**
     * Binds the data of the given Hug to the views of this holder.
     */
    public void bindHug(Context context, Hug hug) {
        mHug = hug;
        mMessageView.setText(hug.message);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(hug.date);
        mDateDayView.setText(new SimpleDateFormat(DAY_FORMAT).format(calendar.getTime()));
        mDateYearView.setText(String.valueOf(calendar.get(Calendar.YEAR)));

        mImageView.setVisibility(View.GONE);
        mImageProgressView.setVisibility(View.VISIBLE);

        new ImageLoader(
                hug.imagePath,
                (int) context.getResources().getDimension(R.dimen.card_item_image_width)) {
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (bitmap != null) {
                    mImageView.setImageBitmap(bitmap);
                    mImageView.setVisibility(View.VISIBLE);
                }

                mImageProgressView.setVisibility(View.GONE);
            }
        };
    }

    /**
     * Called when this view holder is clicked.
     */
    @Override
    public void onClick(View v) {
        if (mHugClickListener != null) {
            mHugClickListener.onHugClicked(mHug);
        }
    }

    /**
     * Called when this view holder is long clicked.
     */
    @Override
    public boolean onLongClick(View v) {
        if (mHugClickListener != null) {
            mHugClickListener.onHugLongClicked(mHug);
        }
        return false;
    }
}