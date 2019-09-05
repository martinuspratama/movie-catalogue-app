package com.rmalan.app.moviecataloguealpha.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_MOVIES = "movies";

    static String TABLE_TV_SHOWS = "tv_shows";

    static final class FavoritesColumns implements BaseColumns {
        static String ID = "id";
        static String POSTER = "poster";
        static String TITLE = "title";
        static String RELEASE_DATE = "release_date";
        static String OVERVIEW = "overview";
    }

}
