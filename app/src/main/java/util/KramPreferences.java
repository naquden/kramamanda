/*
 * Copyright (c) 2015 Backenhof
 */
package util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Class to make it easier to save and get values from preferences.
 */
public class KramPreferences {
    private static String HAS_STARTED_APP = "hasStartedApp";

    /**
     * Saves a value indicating that this app has been started before.
     */
    public static void putHasStartedAppBefore(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(HAS_STARTED_APP, true).apply();
    }

    /**
     * Returns a boolean indicating if this app has started before.
     */
    public static boolean hasStartedAppBefore(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(HAS_STARTED_APP, false);
    }
}
