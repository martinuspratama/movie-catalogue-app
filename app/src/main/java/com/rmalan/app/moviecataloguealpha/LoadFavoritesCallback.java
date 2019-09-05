package com.rmalan.app.moviecataloguealpha;

import com.rmalan.app.moviecataloguealpha.model.FavoriteItems;

import java.util.ArrayList;

public interface LoadFavoritesCallback {
    void preExecute();

    void postExecute(ArrayList<FavoriteItems> favoriteItems);
}
