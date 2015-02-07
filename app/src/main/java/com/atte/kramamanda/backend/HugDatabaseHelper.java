/*
 * Copyright (c) 2015 Backenhof
 */
package com.atte.kramamanda.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.atte.kramamanda.ui.hugs.Hug;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides us with method for interacting with the database.
 */
public class HugDatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "MandaHugs.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String LONG_TYPE = " LONG";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + HugEntry.TABLE_HUG + " (" +
            HugEntry._ID + " INTEGER PRIMARY KEY," +
            HugEntry.COLUMN_MESSAGE + TEXT_TYPE + COMMA_SEP +
            HugEntry.COLUMN_IMAGEPATH + TEXT_TYPE + COMMA_SEP +
            HugEntry.COLUMN_DATE + LONG_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HugEntry.TABLE_HUG;

    private static List<DataChangedListener> mDataChangedListeners;

    /**
     * Constructor.
     */
    public HugDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * Called to create the database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * Called to upgrade the database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Currently just delete the current database and replace with new empty
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * Inserts the given hug into the database.
     */
    public static void insertHug(Context context, Hug hug) {
        ContentValues values = new ContentValues();
        values.put(HugEntry.COLUMN_MESSAGE, hug.message);
        values.put(HugEntry.COLUMN_IMAGEPATH, hug.imagePath);
        values.put(HugEntry.COLUMN_DATE, hug.date);

        SQLiteDatabase db = new HugDatabaseHelper(context).getWritableDatabase();
        db.insert(HugEntry.TABLE_HUG, null, values);
        notifyListeners();
    }

    /**
     * Returns all hugs from the database.
     */
    public static List<Hug> getAllHugs(Context context) {
        String[] projection = {
                HugEntry.COLUMN_MESSAGE,
                HugEntry.COLUMN_IMAGEPATH,
                HugEntry.COLUMN_DATE};
        String sortOrder = HugEntry.COLUMN_DATE + " DESC";

        SQLiteDatabase db = new HugDatabaseHelper(context).getReadableDatabase();
        Cursor cursor = db.query(
                HugEntry.TABLE_HUG,
                projection,
                null,
                null,
                null,
                null,
                sortOrder);

        List<Hug> result = new ArrayList<Hug>();
        while (cursor.moveToNext()) {
            result.add(new Hug(cursor.getString(0), cursor.getString(1), cursor.getLong(2)));
        }

        return result;
    }

    /**
     * Adds a listener that will be notified as data is changed.
     */
    public static void addDataChangedListener(DataChangedListener listener) {
        if (mDataChangedListeners == null) {
            mDataChangedListeners = new ArrayList<DataChangedListener>();
        }

        if (!mDataChangedListeners.contains(listener)) {
            mDataChangedListeners.add(listener);
        }
    }

    /**
     * Removes the given listener.
     */
    public static void removeDataChangedListener(DataChangedListener listener) {
        if (mDataChangedListeners == null) {
            return;
        }

        mDataChangedListeners.remove(listener);
    }

    /**
     * Notifies all listeners that the data has changed.
     */
    private static void notifyListeners() {
        if (mDataChangedListeners == null) {
            return;
        }

        for (DataChangedListener listener : mDataChangedListeners) {
            listener.onDataChanged();
        }
    }

    /**
     * Hug table.
     */
    private static abstract class HugEntry implements BaseColumns {
        public static final String TABLE_HUG = "hug";
        public static final String COLUMN_MESSAGE = "message";
        public static final String COLUMN_IMAGEPATH = "imagepath";
        public static final String COLUMN_DATE = "date";
    }
}
