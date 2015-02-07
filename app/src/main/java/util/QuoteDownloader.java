/*
 * Copyright (c) Backenhof.
 */
package util;

import android.content.res.Resources;
import android.os.AsyncTask;

import com.atte.kramamanda.R;

/**
 * Class handling the download for quotes from the web.
 * NOTE:
 * This implementation only works with "http://www.brainyquote.com/quotes/keywords/good_day.html"
 */
public class QuoteDownloader extends AsyncTask<Void, Void, String> {

    private Resources mResources;

    /**
     * Constructor
     */
    public QuoteDownloader(Resources resources) {
        mResources = resources;
    }

    /**
     * Main method run in background. Returns a random quote from the QUOTE_URL webpage.
     */
    @Override
    protected String doInBackground(Void... params) {
        KramLog.d("QuoteDownloader doInBackground...");

        String[] texts = mResources.getStringArray(R.array.hug_texts);

        return texts[KramTools.getRandomInt(0,texts.length-1)];
    }

    /**
     * Called when doInBackground returns.
     */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        KramLog.d("QuoteDownloader done!");
    }
}
