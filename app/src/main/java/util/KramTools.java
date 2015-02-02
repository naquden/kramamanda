/*
 * Copyright (c) Backenhof.
 */
package util;

import java.util.Random;

/**
 * Utility class containing general useful methods for this project.
 */
public class KramTools {

    private static Random mRandom;

    /**
     * Returns a random int between the given min and max.
     */
    public static int getRandomInt(int min, int max) {
        if (mRandom == null) {
            mRandom = new Random(System.currentTimeMillis());
        }

        return mRandom.nextInt((max - min) + 1) + min;
    }

    /**
     * Returns a random page in the QUOTE_URL.
     */
    public static String getRandomQuoteUrl() {
        int page = getRandomInt(1, 3);  // The number of pages could perhaps be fetched from html
        StringBuilder sb = new StringBuilder();
        sb.append(KramConstant.QUOTES_URL);
        sb.append("_");
        sb.append(page);
        sb.append(".html");

        KramLog.i("getRandomQuoteUrl: " + sb.toString());
        return sb.toString();
    }
}
