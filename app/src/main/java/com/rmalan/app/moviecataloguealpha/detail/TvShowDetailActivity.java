package com.rmalan.app.moviecataloguealpha.detail;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.rmalan.app.moviecataloguealpha.R;
import com.rmalan.app.moviecataloguealpha.db.FavoritesHelper;
import com.rmalan.app.moviecataloguealpha.model.FavoriteItems;
import com.rmalan.app.moviecataloguealpha.model.TvShowItems;

public class TvShowDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TV_SHOW = "extra_tv_show";

    TextView tvTitle, tvRelease, tvOverview;
    ImageView imgPoster;

    private TvShowItems tvShowItems;
    private FavoriteItems favoriteItems;

    private FavoritesHelper favoritesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        favoritesHelper = FavoritesHelper.getInstance(getApplicationContext());
        favoritesHelper.open();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_favorite) {
            favoriteItems = new FavoriteItems();

            favoriteItems.setId(tvShowItems.getId());
            favoriteItems.setPoster(tvShowItems.getPoster());
            favoriteItems.setTitle(tvShowItems.getTitle());
            favoriteItems.setReleaseDate(tvShowItems.getReleaseDate());
            favoriteItems.setOverview(tvShowItems.getOverview());

            long result = favoritesHelper.insertTvShow(favoriteItems);

            if (result > 0) {
                Toast.makeText(this, R.string.add_favorite, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.already_favorite, Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
