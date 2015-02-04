/*
 * Copyright (c) 2015 Backenhof
 */
package com.atte.kramamanda.ui.hugs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.atte.kramamanda.R;
import com.atte.kramamanda.ui.banner.HugBanner;

import util.ImageLoader;

/**
 * Dialog for showing hugs.
 */
public class HugDialog extends DialogFragment {
    private static final String ARGS_HUG = "argsHug";

    /**
     * Returns a new instance of this dialog.
     */
    public static HugDialog newInstance(Hug hug) {
        HugDialog dialog = new HugDialog();

        Bundle args = new Bundle();
        args.putParcelable(ARGS_HUG, hug);
        dialog.setArguments(args);
        dialog.setCancelable(true);
        return dialog;
    }

    /**
     * Called to create the view that is shown in this dialog.
     */
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_hug, container, false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.dialog_hug_image);
        Hug hug = getArguments().getParcelable(ARGS_HUG);
        new ImageLoader(
                hug.imagePath,
                (int) getResources().getDimension(R.dimen.dialog_hug_image_width)) {
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        };

        HugBanner banner = (HugBanner) view.findViewById(R.id.dialog_hug_banner);
        banner.setMessage(hug.message);
        banner.setDate(hug.date);
        return view;
    }

    /**
     * Creates the dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return dialog;
    }
}