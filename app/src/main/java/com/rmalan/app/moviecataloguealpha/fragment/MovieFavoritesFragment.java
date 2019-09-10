package com.rmalan.app.moviecataloguealpha.fragment;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmalan.app.moviecataloguealpha.LoadFavoritesCallback;
import com.rmalan.app.moviecataloguealpha.R;
import com.rmalan.app.moviecataloguealpha.adapter.MovieFavoritesAdapter;
import com.rmalan.app.moviecataloguealpha.model.FavoriteItems;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.CONTENT_URI_MOVIES;
import static com.rmalan.app.moviecataloguealpha.helper.MappingHelper.mapCursorToArrayList;


public class MovieFavoritesFragment extends Fragment implements LoadFavoritesCallback {

    private static final String EXTRA_STATE = "extra_state";
    private static HandlerThread handlerThread;
    private RecyclerView rvMovieFavorites;
    private MovieFavoritesAdapter movieFavoritesAdapter;

    public MovieFavoritesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovieFavorites = view.findViewById(R.id.rv_movie_favorites);
        rvMovieFavorites.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvMovieFavorites.setHasFixedSize(true);

        handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        movieFavoritesAdapter = new MovieFavoritesAdapter(getActivity());
        rvMovieFavorites.setAdapter(movieFavoritesAdapter);

        if (savedInstanceState == null) {
            new LoadMovieFavoritesAsync(getActivity(), this).execute();
        } else {
            ArrayList<FavoriteItems> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                movieFavoritesAdapter.setListMovieFavorites(list);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, movieFavoritesAdapter.getListMovieFavorites());
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void postExecute(Cursor favoriteItems) {
        ArrayList<FavoriteItems> listMovieFavorites = mapCursorToArrayList(favoriteItems);

        if (listMovieFavorites.size() > 0) {
            movieFavoritesAdapter.setListMovieFavorites(listMovieFavorites);
        } else {
            movieFavoritesAdapter.setListMovieFavorites(new ArrayList<FavoriteItems>());
            Toast.makeText(getActivity(), getResources().getString(R.string.no_data), Toast.LENGTH_SHORT).show();
        }
    }

    private static class LoadMovieFavoritesAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadFavoritesCallback> weakCallback;

        private LoadMovieFavoritesAsync(Context context, LoadFavoritesCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }


        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI_MOVIES, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor favoriteItems) {
            super.onPostExecute(favoriteItems);
            weakCallback.get().postExecute(favoriteItems);
        }
    }

}
