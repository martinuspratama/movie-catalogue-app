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

import com.rmalan.app.moviecataloguealpha.R;
import com.rmalan.app.moviecataloguealpha.TvShowDetailActivity;
import com.rmalan.app.moviecataloguealpha.adapter.TvShowsAdapter;
import com.rmalan.app.moviecataloguealpha.model.TvShowItems;
import com.rmalan.app.moviecataloguealpha.viewmodel.TvShowViewModel;

import java.util.ArrayList;

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

}