/*
 * Copyright (c) Backenhof.
 */
package com.atte.kramamanda.ui;

import android.app.Activity;

import com.atte.kramamanda.R;
import com.plattysoft.leonids.ParticleSystem;

/**
 * Wraps ParticleSystem for our Kram uses.
 */
public class ParticleSystemWrapper {

    /**
     * Animates a Kram explosion.
     */
    public static void explodeFromCenter(Activity activity, int viewId) {
        new ParticleSystem(activity, 50, R.drawable.heart, 3000)
                .setSpeedRange(0.1f, 0.5f)
                .setRotationSpeed(100)
                .setInitialRotationRange(0, 360)
                .oneShot(activity.findViewById(viewId), 50);
    }

    /**
     * Makes it rain from the given views.
     */
    public static void rain(Activity activity, int viewLeftId, int viewRightId) {
        new ParticleSystem(activity, 10, R.drawable.heart, 10000)
                .setSpeedModuleAndAngleRange(0f, 0.1f, 0, 0)
                .setRotationSpeed(144)
                .setAcceleration(0.0001f, 90)
                .emit(activity.findViewById(viewLeftId), 2);

        new ParticleSystem(activity, 10, R.drawable.heart, 10000)
                .setSpeedModuleAndAngleRange(0f, 0.1f, 180, 180)
                .setRotationSpeed(144)
                .setAcceleration(0.0001f, 90)
                .emit(activity.findViewById(viewRightId), 2);
    }
}
