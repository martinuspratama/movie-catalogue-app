package com.rmalan.app.moviecataloguealpha.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.rmalan.app.moviecataloguealpha";
    private static final String SCHEME = "content";

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static final class FavoritesColumns implements BaseColumns {

        public static String TABLE_MOVIES = "movies";
        public static final Uri CONTENT_URI_MOVIES = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIES)
                .build();
        public static String TABLE_TV_SHOWS = "tv_shows";
        public static final Uri CONTENT_URI_TV_SHOWS = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_TV_SHOWS)
                .build();

        public static String ID = "id";
        public static String POSTER = "poster";
        public static String TITLE = "title";
        public static String RELEASE_DATE = "release_date";
        public static String OVERVIEW = "overview";

    }

}
