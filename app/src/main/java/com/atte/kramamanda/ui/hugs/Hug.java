/*
 * Copyright (c) 2015 Backenhof
 */
package com.atte.kramamanda.ui.hugs;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents a Hug.
 */
public class Hug implements Parcelable {
    public String imagePath;
    public String message;
    public long date;

    /**
     * Constructor.
     */
    public Hug(String message, String imagePath, long date) {
        this.imagePath = imagePath;
        this.message = message;
        this.date = date;
    }

    /**
     * Constructor that creates a hug from a parcel.
     */
    public Hug(Parcel parcel) {
        imagePath = parcel.readString();
        message = parcel.readString();
        date = parcel.readLong();
    }

    /**
     * Describes this content.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Writes this hug to a parcel.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imagePath);
        dest.writeString(message);
        dest.writeLong(date);
    }

    /**
     * Creator for this parcelable hug.
     */
    public static final Creator<Hug> CREATOR = new Creator<Hug>() {
        @Override
        public Hug createFromParcel(Parcel source) {
            return new Hug(source);
        }

        @Override
        public Hug[] newArray(int size) {
            return new Hug[size];
        }
    };
}