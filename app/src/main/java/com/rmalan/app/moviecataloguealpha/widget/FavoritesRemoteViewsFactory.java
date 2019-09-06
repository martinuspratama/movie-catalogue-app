package com.rmalan.app.moviecataloguealpha.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.rmalan.app.moviecataloguealpha.R;
import com.rmalan.app.moviecataloguealpha.db.FavoritesHelper;
import com.rmalan.app.moviecataloguealpha.model.FavoriteItems;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FavoritesRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private final FavoritesHelper favoritesHelper;
    private ArrayList<FavoriteItems> listMovieFavorites = new ArrayList<>();

    FavoritesRemoteViewsFactory(Context context) {
        mContext = context;
        favoritesHelper = FavoritesHelper.getInstance(mContext);
        favoritesHelper.open();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        listMovieFavorites = favoritesHelper.getAllMovies();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listMovieFavorites.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bitmap;
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w500/" + listMovieFavorites.get(position).getPoster())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            remoteViews.setImageViewBitmap(R.id.imageView, bitmap);
            Log.d("Widget", "Success");
        } catch (InterruptedException | ExecutionException e) {
            Log.d("Widget Load Error", "Error");
        }

        Bundle extras = new Bundle();
        extras.putInt(FavoritesWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
