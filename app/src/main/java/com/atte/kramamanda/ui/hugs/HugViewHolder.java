/*
 * Copyright (c) 2015 Backenhof
 */
package com.atte.kramamanda.ui.hugs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atte.kramamanda.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * ViewHolder for showing Hug items.s
 */
public class HugViewHolder extends RecyclerView.ViewHolder {
    private static final String DAY_FORMAT = "d MMM";
    private TextView mMessageView;
    private ImageView mImageView;
    private TextView mDateDayView;
    private TextView mDateYearView;
    private ProgressBar mImageProgressView;

    /**
     * Constructor.
     */
    public HugViewHolder(View itemView) {
        super(itemView);
        mMessageView = (TextView) itemView.findViewById(R.id.hug_item_message);
        mImageView = (ImageView) itemView.findViewById(R.id.hug_item_image);
        mImageProgressView = (ProgressBar) itemView.findViewById(R.id.hug_item_image_progress);
        mDateDayView = (TextView) itemView.findViewById(R.id.hug_item_date_day);
        mDateYearView = (TextView) itemView.findViewById(R.id.hug_item_date_year);
    }

    /**
     * Binds the data of the given Hug to the views of this holder.
     */
    public void bindHug(Hug hug) {
        mMessageView.setText(hug.mMessage);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(hug.mDate);
        mDateDayView.setText(new SimpleDateFormat(DAY_FORMAT).format(calendar.getTime()));
        mDateYearView.setText(String.valueOf(calendar.get(Calendar.YEAR)));

        mImageView.setVisibility(View.GONE);
        mImageProgressView.setVisibility(View.VISIBLE);

        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... filePath) {
                return BitmapFactory.decodeFile(filePath[0]);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (bitmap != null) {
                    mImageView.setImageBitmap(bitmap);
                    mImageView.setVisibility(View.VISIBLE);
                }

                mImageProgressView.setVisibility(View.GONE);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, hug.mImagePath);
    }
}