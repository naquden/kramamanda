/*
 * Copyright (c) 2015 Backenhof
 */
package com.atte.kramamanda.ui.hugs;

/**
 * Listener that reacts on clicks on hug items.
 */
public interface OnHugItemClickedListener {
    /**
     * Called when the item with the given hug is clicked.
     */
    public void onHugClicked(Hug hug);
}