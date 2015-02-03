/*
 * Copyright (c) 2015 Backenhof
 */
package com.atte.kramamanda.ui.hugs;

/**
 * Represents a Hug.
 */
public class Hug {
    public String mImagePath;
    public String mMessage;
    public long mDate;

    /**
     * Constructor.
     */
    public Hug(String imagePath, String message, long date) {
        mImagePath = imagePath;
        mMessage = message;
        mDate = date;
    }
}