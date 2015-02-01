/*
 * Copyright (c) Backenhof.
 */

package util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * Task that takes an url and returns the bitmap it points to.
 * Will catch an exception if url is not pointing to an bitmap.
 */
public class ImageDownloader extends AsyncTask<Void, Void, Bitmap> {

    /**
     * Main method run in background. Returns a random bitmap from google when done.
     */
    @Override
    protected Bitmap doInBackground(Void... params) {
        return getImageFromUrlTask(GetRandomUrlTask());
    }

    /**
     * Returns a random Url to an image from the google search url constant.
     */
    private URL GetRandomUrlTask() {
        try {
            // Connect to the website and get the html
            Document doc = Jsoup.connect(KramConstant.GOOGLE_SEARCH_URL).get();

            // Get all elements with the img tag
            Elements img = doc.getElementsByTag("img");

            // Randomize an index (except index 0 since that's the google keyboard icon)
            int randomIndex =
                    new Random(System.currentTimeMillis()).nextInt((img.size() - 1) + 1) + 1;

            return new URL(img.get(randomIndex).absUrl("src"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the image for the given url.
     */
    private Bitmap getImageFromUrlTask(URL url) {
        if (url == null) {
            return null;
        }

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            inputStream.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
