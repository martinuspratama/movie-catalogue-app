package com.rmalan.app.moviecataloguealpha.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rmalan.app.moviecataloguealpha.MovieDetailActivity;
import com.rmalan.app.moviecataloguealpha.R;
import com.rmalan.app.moviecataloguealpha.adapter.MoviesAdapter;
import com.rmalan.app.moviecataloguealpha.model.MovieItems;
import com.rmalan.app.moviecataloguealpha.viewmodel.MovieViewModel;

import java.util.ArrayList;

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
}