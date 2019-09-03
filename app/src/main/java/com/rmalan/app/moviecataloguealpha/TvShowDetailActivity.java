package com.rmalan.app.moviecataloguealpha;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.rmalan.app.moviecataloguealpha.model.TvShowItems;

public class TvShowDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TV_SHOW = "extra_tv_show";

    TextView tvTitle, tvRelease, tvOverview;
    ImageView imgPoster;

    private TvShowItems tvShowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgPoster = findViewById(R.id.img_poster);
        tvTitle = findViewById(R.id.txt_title);
        tvRelease = findViewById(R.id.txt_release);
        tvOverview = findViewById(R.id.txt_overview);

        tvShowItems = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

        Glide.with(this).load("https://image.tmdb.org/t/p/w500/" + tvShowItems.getPoster()).into(imgPoster);
        tvTitle.setText(tvShowItems.getTitle());
        tvRelease.setText(tvShowItems.getReleaseDate());
        tvOverview.setText(tvShowItems.getOverview());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.detail_tv_show));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
