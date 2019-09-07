package com.rmalan.app.moviecataloguealpha.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmalan.app.moviecataloguealpha.R;
import com.rmalan.app.moviecataloguealpha.adapter.TvShowsAdapter;
import com.rmalan.app.moviecataloguealpha.detail.TvShowDetailActivity;
import com.rmalan.app.moviecataloguealpha.model.TvShowItems;
import com.rmalan.app.moviecataloguealpha.viewmodel.TvShowViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class TvShowsFragment extends Fragment {

    private TvShowsAdapter tvShowsAdapter;
    private ProgressBar progressBar;

    private TvShowViewModel tvShowViewModel;
    private Observer<ArrayList<TvShowItems>> getTvShow = new Observer<ArrayList<TvShowItems>>() {
        @Override
        public void onChanged(ArrayList<TvShowItems> tvShowItems) {
            if (tvShowItems != null) {
                tvShowsAdapter.setData(tvShowItems);
                showLoading(false);
            }
        }
    };
    private Observer<ArrayList<TvShowItems>> getSeachTvShow = new Observer<ArrayList<TvShowItems>>() {
        @Override
        public void onChanged(ArrayList<TvShowItems> tvShowItems) {
            if (tvShowItems != null) {
                tvShowsAdapter.setData(tvShowItems);
                showLoading(false);
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_shows, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getTvShows().observe(this, getTvShow);

        tvShowsAdapter = new TvShowsAdapter();
        tvShowsAdapter.notifyDataSetChanged();

        RecyclerView rvTvShows = view.findViewById(R.id.rv_tv_shows);
        rvTvShows.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvTvShows.setAdapter(tvShowsAdapter);

        progressBar = view.findViewById(R.id.progress_bar);

        showLoading(true);
        tvShowViewModel.setTvShow(getResources().getString(R.string.language));

        tvShowsAdapter.setOnItemClickCallback(new TvShowsAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TvShowItems tvShowItems) {
                // Toast.makeText(getActivity(), "Id: " +tvShowItems.getId(), Toast.LENGTH_SHORT).show();

                Intent tvShowDetailIntent = new Intent(getActivity(), TvShowDetailActivity.class);
                tvShowDetailIntent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW, tvShowItems);
                startActivity(tvShowDetailIntent);
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint_movie));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    showLoading(true);
                    tvShowViewModel.getSearchTvShows().observe(getActivity(), getSeachTvShow);
                    tvShowViewModel.setSearchTvShow(getResources().getString(R.string.language), query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }

        MenuItem menuItem = menu.findItem(R.id.search);
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                showLoading(true);
                tvShowViewModel.setTvShow(getResources().getString(R.string.language));
                return true;
            }
        });

    }

}