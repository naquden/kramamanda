/*
 * Copyright (c) Backenhof.
 */
package com.atte.kramamanda.ui;

import android.app.Activity;
import android.view.View;

import com.atte.kramamanda.R;
import com.plattysoft.leonids.ParticleSystem;

import java.util.ArrayList;

import util.KramTools;

/**
 * Wraps ParticleSystem for our Kram uses.
 */
public class ParticleSystemWrapper {

    private static ArrayList<ParticleSystem> mParticleSystems = new ArrayList<>();

    /**
     * Cancel all particle systems.
     */
    public static void cancelAllParticleSystems() {
        for (int i = mParticleSystems.size() - 1; i >= 0; i--) {
            mParticleSystems.get(i).cancel();
            mParticleSystems.remove(i);
        }
    }

    /**
     * Animates a Kram explosion.
     */
    public static void explodeFromView(Activity activity, final View parentView) {
        int drawableId = KramTools.getRandomParticleDrawable();

        ParticleSystem system = new ParticleSystem(activity, 50, drawableId, 6000)
                .setSpeedRange(0.03f, 0.3f)
                .setRotationSpeed(100)
                .setInitialRotationRange(0, 360);
        system.oneShot(parentView, 10);
    }

    /**
     * Makes it rain from the given views.
     */
    public static void rain(Activity activity, final View viewLeft, final View viewRight) {
        ParticleSystem systemLeft = new ParticleSystem(activity, 10, R.drawable.particle_heart, 10000)
                .setSpeedModuleAndAngleRange(0f, 0.1f, 0, 0)
                .setRotationSpeed(144)
                .setAcceleration(0.0001f, 90);
        systemLeft.emit(viewLeft, 2);
        mParticleSystems.add(systemLeft);

        ParticleSystem systemRight = new ParticleSystem(activity, 10, R.drawable.particle_heart, 10000)
                .setSpeedModuleAndAngleRange(0f, 0.1f, 180, 180)
                .setRotationSpeed(144)
                .setAcceleration(0.0001f, 90);
        systemRight.emit(viewRight, 2);
        mParticleSystems.add(systemRight);
    }
}
