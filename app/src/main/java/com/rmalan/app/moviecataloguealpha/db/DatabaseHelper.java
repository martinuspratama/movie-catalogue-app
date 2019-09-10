package com.rmalan.app.moviecataloguealpha.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_TV_SHOWS = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.FavoritesColumns.TABLE_TV_SHOWS,
            DatabaseContract.FavoritesColumns.ID,
            DatabaseContract.FavoritesColumns.POSTER,
            DatabaseContract.FavoritesColumns.TITLE,
            DatabaseContract.FavoritesColumns.RELEASE_DATE,
            DatabaseContract.FavoritesColumns.OVERVIEW
    );
    private static final String SQL_CREATE_TABLE_MOVIES = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.FavoritesColumns.TABLE_MOVIES,
            DatabaseContract.FavoritesColumns.ID,
            DatabaseContract.FavoritesColumns.POSTER,
            DatabaseContract.FavoritesColumns.TITLE,
            DatabaseContract.FavoritesColumns.RELEASE_DATE,
            DatabaseContract.FavoritesColumns.OVERVIEW
    );
    public static String DATABASE_NAME = "moviecatalogue";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIES);
        db.execSQL(SQL_CREATE_TABLE_TV_SHOWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FavoritesColumns.TABLE_MOVIES);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FavoritesColumns.TABLE_TV_SHOWS);
        onCreate(db);
    }

}
