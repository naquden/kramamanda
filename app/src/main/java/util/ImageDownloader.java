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
 * TODO: extends search to include more images, it currently fetches about 21 images.
 */
public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    /**
     * Main method run in background. Returns a random bitmap from google when done.
     */
    @Override
    protected Bitmap doInBackground(String... url) {
        if (url[0] == null) {
            return null;
        }
        return getImageFromUrlTask(GetRandomUrlTask(url[0]));
    }

    /**
     * Returns a random Url to an image from the google search url constant.
     */
    private URL GetRandomUrlTask(String url) {
        try {
            // Connect to the website and get the html
            Document doc = Jsoup.connect(url).get();

            // Get all elements with the img tag
            Elements imagesUrl = doc.getElementsByTag("img");

            return new URL(imagesUrl.get(getRandomInt(1, (imagesUrl.size() - 1))).absUrl("src"));
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

    /**
     * Returns a random int between the given min and max.
     */
    private int getRandomInt(int min, int max) {
        return new Random(System.currentTimeMillis()).nextInt((max - min) + 1) + min;
    }
}
