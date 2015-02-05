/*
 * Copyright (c) 2015 Backenhof
 */
package com.atte.kramamanda.backend;

/**
 * Listener for when the data in the database changes.
 */
public interface DataChangedListener {
    /**
     * Called when the data in the database has changed somehow.
     */
    public void onDataChanged();
}
