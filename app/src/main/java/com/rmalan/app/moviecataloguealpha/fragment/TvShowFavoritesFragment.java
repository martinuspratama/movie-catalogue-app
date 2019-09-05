package com.rmalan.app.moviecataloguealpha.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.rmalan.app.moviecataloguealpha.adapter.TvShowFavoritesAdapter;
import com.rmalan.app.moviecataloguealpha.db.FavoritesHelper;
import com.rmalan.app.moviecataloguealpha.detail.MovieFavoriteDetailActivity;
import com.rmalan.app.moviecataloguealpha.model.FavoriteItems;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class TvShowFavoritesFragment extends Fragment implements LoadFavoritesCallback {

    private static final String EXTRA_STATE = "extra_state";
    private RecyclerView rvTvShowFavorites;
    private TvShowFavoritesAdapter tvShowFavoritesAdapter;
    private FavoritesHelper favoritesHelper;

    public TvShowFavoritesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTvShowFavorites = view.findViewById(R.id.rv_tv_show_favorites);
        rvTvShowFavorites.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvTvShowFavorites.setHasFixedSize(true);

        favoritesHelper = FavoritesHelper.getInstance(getActivity());
        favoritesHelper.open();

        tvShowFavoritesAdapter = new TvShowFavoritesAdapter(getActivity());
        rvTvShowFavorites.setAdapter(tvShowFavoritesAdapter);

        if (savedInstanceState == null) {
            new LoadTvShowFavoritesAsync(favoritesHelper, this).execute();
        } else {
            ArrayList<FavoriteItems> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                tvShowFavoritesAdapter.setListTvShowFavorites(list);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, tvShowFavoritesAdapter.getListTvShowFavorites());
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
    public void postExecute(ArrayList<FavoriteItems> favoriteItems) {
        tvShowFavoritesAdapter.setListTvShowFavorites(favoriteItems);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == MovieFavoriteDetailActivity.REQUEST_DELETE) {
                if (resultCode == MovieFavoriteDetailActivity.RESULT_DELETE) {
                    int position = data.getIntExtra(MovieFavoriteDetailActivity.EXTRA_POSITION, 0);
                    tvShowFavoritesAdapter.removeItem(position);
                    Toast.makeText(getActivity(), getString(R.string.delete_item), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favoritesHelper.close();
    }

    private static class LoadTvShowFavoritesAsync extends AsyncTask<Void, Void, ArrayList<FavoriteItems>> {

        private final WeakReference<FavoritesHelper> weakFavoritesHelper;
        private final WeakReference<LoadFavoritesCallback> weakCallback;

        private LoadTvShowFavoritesAsync(FavoritesHelper favoritesHelper, LoadFavoritesCallback callback) {
            weakFavoritesHelper = new WeakReference<>(favoritesHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }


        @Override
        protected ArrayList<FavoriteItems> doInBackground(Void... voids) {
            return weakFavoritesHelper.get().getAllTvShows();
        }

        @Override
        protected void onPostExecute(ArrayList<FavoriteItems> favoriteItems) {
            super.onPostExecute(favoriteItems);
            weakCallback.get().postExecute(favoriteItems);
        }
    }
}
