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
import com.rmalan.app.moviecataloguealpha.adapter.MoviesAdapter;
import com.rmalan.app.moviecataloguealpha.detail.MovieDetailActivity;
import com.rmalan.app.moviecataloguealpha.model.MovieItems;
import com.rmalan.app.moviecataloguealpha.settings.SettingsActivity;
import com.rmalan.app.moviecataloguealpha.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class MoviesFragment extends Fragment {

    private MoviesAdapter moviesAdapter;
    private ProgressBar progressBar;

    private MovieViewModel movieViewModel;
    private Observer<ArrayList<MovieItems>> getMovie = new Observer<ArrayList<MovieItems>>() {
        @Override
        public void onChanged(ArrayList<MovieItems> movieItems) {
            if (movieItems != null) {
                moviesAdapter.setData(movieItems);
                showLoading(false);
            }
        }
    };
    private Observer<ArrayList<MovieItems>> getSearchMovie = new Observer<ArrayList<MovieItems>>() {
        @Override
        public void onChanged(ArrayList<MovieItems> movieItems) {
            if (movieItems != null) {
                moviesAdapter.setData(movieItems);
                showLoading(false);
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, getMovie);

        moviesAdapter = new MoviesAdapter();
        moviesAdapter.notifyDataSetChanged();

        RecyclerView rvMovies = view.findViewById(R.id.rv_movies);
        rvMovies.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvMovies.setAdapter(moviesAdapter);

        progressBar = view.findViewById(R.id.progress_bar);

        showLoading(true);
        movieViewModel.setMovie(getResources().getString(R.string.language));

        moviesAdapter.setOnItemClickCallback(new MoviesAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MovieItems movieItems) {
                // Toast.makeText(getActivity(), "Id: " +movieItems.getId(), Toast.LENGTH_SHORT).show();

                Intent movieDetailIntent = new Intent(getActivity(), MovieDetailActivity.class);
                movieDetailIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movieItems);
                startActivity(movieDetailIntent);
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
                    movieViewModel.getSearchMovies().observe(getActivity(), getSearchMovie);
                    movieViewModel.setSearchMovie(getResources().getString(R.string.language), query);
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
                movieViewModel.setMovie(getResources().getString(R.string.language));
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}