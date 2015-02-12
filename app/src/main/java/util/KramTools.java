/*
 * Copyright (c) Backenhof.
 */
package util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.atte.kramamanda.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
     * Returns true if there's an internet connection available.
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Returns a random particle drawable.
     */
    public static int getRandomParticleDrawable() {
        ArrayList<Integer> drawableIds = getAllParticleDrawableResId();
        int index = getRandomInt(0, drawableIds.size() - 1);
        return drawableIds.get(index);
    }

    /**
     * Returns all drawable that begins which file name begins with "particle".
     */
    private static ArrayList<Integer> getAllParticleDrawableResId() {
        final R.drawable drawableResources = new R.drawable();
        final Class<R.drawable> c = R.drawable.class;
        final Field[] fields = c.getDeclaredFields();
        ArrayList<Integer> resIdList = new ArrayList<>();

        for (int i = 0, max = fields.length; i < max; i++) {
            try {
                if (fields[i].getName().substring(0, 8).equals("particle")) {
                    resIdList.add(fields[i].getInt(drawableResources));
                }
            } catch (Exception e) {
                // In case of exception add one default icon
                resIdList.add(R.drawable.particle_heart);
                continue;
            }
        }
        return resIdList;
    }
}
