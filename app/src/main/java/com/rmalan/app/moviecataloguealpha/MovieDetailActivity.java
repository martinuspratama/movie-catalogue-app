package com.rmalan.app.moviecataloguealpha;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.rmalan.app.moviecataloguealpha.model.MovieItems;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    TextView tvTitle, tvRelease, tvOverview;
    ImageView imgPoster;

    private MovieItems movieItems;

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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.detail_movie));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
