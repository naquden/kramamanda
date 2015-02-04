/*
 * Copyright (c) 2015 Backenhof
 */
package util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/**
 * Loads an image from a local storage asynchronously.
 */
public class ImageLoader extends AsyncTask<String, Void, Bitmap> {
    private int mRequestedWidth = -1;

    /**
     * Constructor that also starts this task.
     */
    public ImageLoader(String imagePath) {
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, imagePath);
    }

    /**
     * Constructor that also starts this task.
     */
    public ImageLoader(String imagePath, int requestedWidth) {
        mRequestedWidth = requestedWidth;
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, imagePath);
    }

    /**
     * Loads the image from the file path.
     */
    @Override
    protected Bitmap doInBackground(String... filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath[0]);
        if (mRequestedWidth == -1 || bitmap.getWidth() == mRequestedWidth) {
            return bitmap;
        }

        // Scale bitmap to fit requested width. Keep aspect ratio
        float scaleFactor = mRequestedWidth / bitmap.getWidth();
        return Bitmap.createScaledBitmap(
                bitmap, mRequestedWidth, (int) (bitmap.getHeight() * scaleFactor), true);
    }
}
