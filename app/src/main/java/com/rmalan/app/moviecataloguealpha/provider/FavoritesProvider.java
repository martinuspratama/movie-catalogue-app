package com.rmalan.app.moviecataloguealpha.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rmalan.app.moviecataloguealpha.db.FavoritesHelper;

import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.AUTHORITY;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.CONTENT_URI_MOVIES;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.TABLE_MOVIES;

public class FavoritesProvider extends ContentProvider {

    private static final int MOVIES = 1;
    private static final int MOVIE_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIES, MOVIES);
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIES + "/#", MOVIE_ID);
    }

    private FavoritesHelper favoritesHelper;

    @Override
    public boolean onCreate() {
        favoritesHelper = FavoritesHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        favoritesHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                cursor = favoritesHelper.queryMoviesProvider();
                break;
            case MOVIE_ID:
                cursor = favoritesHelper.queryMovieByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        favoritesHelper.open();
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                added = favoritesHelper.insertMovieProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI_MOVIES, null);
        return Uri.parse(CONTENT_URI_MOVIES + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        favoritesHelper.open();
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted = favoritesHelper.deleteMovieProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI_MOVIES, null);
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
