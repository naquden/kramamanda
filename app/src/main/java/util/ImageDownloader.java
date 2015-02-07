/*
 * Copyright (c) Backenhof.
 */

package util;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

/**
 * Task that takes an url and returns the bitmap it points to.
 * Will catch an exception if url is not pointing to an bitmap.
 * TODO: extends search to include more images, it currently fetches about 21 images.
 */
public class ImageDownloader extends AsyncTask<String, Void, String> {
    private Context mContext;

    /**
     * Constructor.
     */
    public ImageDownloader(Context context) {
        mContext = context;
    }

    /**
     * Main method run in background. Returns a random bitmap from google when done.
     */
    @Override
    protected String doInBackground(String... url) {
        KramLog.d("ImageDownloader doInBackground...");
        if (url[0] == null) {
            return null;
        }
        return downloadAndWrite(GetRandomUrlTask(url[0]));
    }

    /**
     * Called when doInBackground returns.
     */
    @Override
    protected void onPostExecute(String path) {
        super.onPostExecute(path);
        KramLog.d("Downloaded image to " + path);
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

            return getSourceImageUrl(
                    imagesUrl.get(KramTools.getRandomInt(1, (imagesUrl.size() - 1))).absUrl("src"));
        } catch (IOException e) {
            KramLog.e("Could not fetch image url", e);
        }
        return null;
    }

    /**
     * Returns the url to the source image of the given URL.
     */
    private URL getSourceImageUrl(String path) throws MalformedURLException {
        String sourcePath = path.substring(0, path.indexOf('&'));
        return new URL(sourcePath);
    }

    /**
     * Downloads and writes an image to file. Returns the local path to the image file.
     */
    private String downloadAndWrite(URL url) {
        if (url == null) {
            return null;
        }

        FileOutputStream fos = null;
        InputStream inputStream = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            inputStream = connection.getInputStream();

            int name = new Random().nextInt(999999);
            String filePath =
                    mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + name;
            fos = new FileOutputStream(new File(filePath));

            byte[] buffer = new byte[8192];
            int n;
            while ((n = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, n);
            }

            return filePath;
        } catch (IOException e) {
            KramLog.e("Failed to create input/output stream.", e);
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                } catch (IOException e) {
                    KramLog.e("Failed to flush FileOutputStream", e);
                } finally {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        KramLog.e("Failed to close FileOutputStream", e);
                    }
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    KramLog.e("Failed to close InputStream", e);
                }
            }
        }

        return null;
    }
}
