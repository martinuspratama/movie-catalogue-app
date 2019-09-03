package com.rmalan.app.moviecataloguealpha;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.rmalan.app.moviecataloguealpha.model.MovieItems;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_DETAIL_MOVIE = "extra_detail_movie";

    TextView tvTitle, tvRelease, tvOverview;
    ImageView imgPoster;

    private MovieItems movieItems;

    private String actionBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgPoster = findViewById(R.id.img_poster);
        tvTitle = findViewById(R.id.txt_title);
        tvRelease = findViewById(R.id.txt_release);
        tvOverview = findViewById(R.id.txt_overview);

        movieItems = getIntent().getParcelableExtra(EXTRA_MOVIE);

        Glide.with(this).load("https://image.tmdb.org/t/p/w500/" + movieItems.getPoster()).into(imgPoster);
        tvTitle.setText(movieItems.getTitle());
        tvRelease.setText(movieItems.getReleaseDate());
        tvOverview.setText(movieItems.getOverview());

        actionBarTitle = getIntent().getStringExtra(EXTRA_DETAIL_MOVIE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
