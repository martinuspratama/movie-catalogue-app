package com.rmalan.app.moviecataloguealpha.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rmalan.app.moviecataloguealpha.model.FavoriteItems;

import java.util.ArrayList;

import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.ID;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.OVERVIEW;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.POSTER;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.RELEASE_DATE;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.TABLE_MOVIES;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.TABLE_TV_SHOWS;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.TITLE;

public class FavoritesHelper {

    private static final String DATABASE_TABLE_TV_SHOWS = TABLE_TV_SHOWS;
    private static final String DATABASE_TABLE_MOVIES = TABLE_MOVIES;
    private static DatabaseHelper databaseHelper;
    private static FavoritesHelper INSTANCE;

    private static SQLiteDatabase database;

    private FavoritesHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoritesHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoritesHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<FavoriteItems> getAllMovies() {
        ArrayList<FavoriteItems> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE_MOVIES, null,
                null,
                null,
                null,
                null,
                ID + " ASC",
                null);
        cursor.moveToFirst();
        FavoriteItems favoriteItems;
        if (cursor.getCount() > 0) {
            do {
                favoriteItems = new FavoriteItems();
                favoriteItems.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                favoriteItems.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                favoriteItems.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                favoriteItems.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                favoriteItems.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));

                arrayList.add(favoriteItems);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return arrayList;
    }

    // Movie Provider
    public Cursor queryMovieByIdProvider(String id) {
        return database.query(DATABASE_TABLE_MOVIES, null
                , ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryMoviesProvider() {
        return database.query(DATABASE_TABLE_MOVIES
                , null
                , null
                , null
                , null
                , null
                , ID + " ASC");
    }

    public long insertMovieProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE_MOVIES, null, values);
    }

    public int deleteMovieProvider(String id) {
        return database.delete(DATABASE_TABLE_MOVIES, ID + " = ?", new String[]{id});
    }

    // TV Show Provider
    public Cursor queryTvShowByIdProvider(String id) {
        return database.query(DATABASE_TABLE_TV_SHOWS, null
                , ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryTvShowsProvider() {
        return database.query(DATABASE_TABLE_TV_SHOWS
                , null
                , null
                , null
                , null
                , null
                , ID + " ASC");
    }

    public long insertTvShowProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE_TV_SHOWS, null, values);
    }

    public int deleteTvShowProvider(String id) {
        return database.delete(DATABASE_TABLE_TV_SHOWS, ID + " = ?", new String[]{id});
    }

}
