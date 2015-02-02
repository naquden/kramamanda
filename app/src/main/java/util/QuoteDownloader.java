/*
 * Copyright (c) Backenhof.
 */
package util;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Class handling the download for quotes from the web.
 * NOTE:
 * This implementation only works with "http://www.brainyquote.com/quotes/keywords/good_day.html"
 */
public class QuoteDownloader extends AsyncTask<String, Void, String> {

    /**
     * Main method run in background. Returns a random quote from the QUOTE_URL webpage.
     */
    @Override
    protected String doInBackground(String... url) {
        KramLog.d("QuoteDownloader doInBackground...");
        // Connect to the website and get the html
        try {
            Document doc = Jsoup.connect(url[0]).get();

            Elements quotes = doc.getElementsByClass("bqQuoteLink");

            int randomIndex = KramTools.getRandomInt(0, quotes.size() - 1);

            Element element = quotes.get(randomIndex);
            for (Node firstNode : element.childNodes()) {
                for (Node secondNode : firstNode.childNodes()) {
                    if (secondNode instanceof TextNode) {
                        KramLog.i("Found text:" + ((TextNode) secondNode).text());
                        return ((TextNode) secondNode).text();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
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
