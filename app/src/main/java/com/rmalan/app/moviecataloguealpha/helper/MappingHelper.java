package com.rmalan.app.moviecataloguealpha.helper;

import android.database.Cursor;

import com.rmalan.app.moviecataloguealpha.model.FavoriteItems;

import java.util.ArrayList;

import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.ID;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.OVERVIEW;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.POSTER;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.RELEASE_DATE;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.TITLE;

public class MappingHelper {

    public static ArrayList<FavoriteItems> mapCursorToArrayList(Cursor favoritesCursor) {
        ArrayList<FavoriteItems> favoritesList = new ArrayList<>();

        while (favoritesCursor.moveToNext()) {
            int id = favoritesCursor.getInt(favoritesCursor.getColumnIndexOrThrow(ID));
            String poster = favoritesCursor.getString(favoritesCursor.getColumnIndexOrThrow(POSTER));
            String title = favoritesCursor.getString(favoritesCursor.getColumnIndexOrThrow(TITLE));
            String releaseDate = favoritesCursor.getString(favoritesCursor.getColumnIndexOrThrow(RELEASE_DATE));
            String overview = favoritesCursor.getString(favoritesCursor.getColumnIndexOrThrow(OVERVIEW));

            favoritesList.add(new FavoriteItems(id, poster, title, releaseDate, overview));
        }

        return favoritesList;
    }

}
