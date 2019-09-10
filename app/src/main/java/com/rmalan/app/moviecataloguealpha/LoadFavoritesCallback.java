package com.rmalan.app.moviecataloguealpha;

import android.database.Cursor;

public interface LoadFavoritesCallback {
    void preExecute();

    void postExecute(Cursor favoriteItems);
}
