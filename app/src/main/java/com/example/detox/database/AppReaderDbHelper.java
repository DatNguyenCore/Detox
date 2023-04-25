package com.example.detox.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.detox.database.AppReaderContract.AppEntry;

public class AppReaderDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AppReader.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AppEntry.TABLE_NAME + " (" +
                    AppEntry._ID + " INTEGER PRIMARY KEY," +
                    AppEntry.COLUMN_NAME_NAME + " TEXT," +
                    AppEntry.COLUMN_NAME_ICON + " INTEGER," +
                    AppEntry.COLUMN_NAME_PACKAGE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AppEntry.TABLE_NAME;



    public AppReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }


    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
