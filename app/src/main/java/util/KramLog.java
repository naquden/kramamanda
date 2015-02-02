/*
 * Copyright (c) Backenhof.
 */
package util;

import android.util.Log;

/**
 * Created by Atte on 2015-02-02.
 */
public class KramLog {
    private static final String TAG = "backenhof";

    /**
     * Log a verbose message.
     */
    public static void v(String message) {
        if (KramConstant.DEBUG) {
            Log.v(TAG, message);
        }
    }

    /**
     * Log a debug message.
     */
    public static void d(String message) {
        if (KramConstant.DEBUG) {
            Log.d(TAG, message);
        }
    }

    /**
     * Log an information message.
     */
    public static void i(String message) {
        if (KramConstant.DEBUG) {
            Log.i(TAG, message);
        }
    }

    /**
     * Log an error message.
     */
    public static void e(String message) {
        if (KramConstant.DEBUG) {
            Log.e(TAG, message);
        }
    }
}
