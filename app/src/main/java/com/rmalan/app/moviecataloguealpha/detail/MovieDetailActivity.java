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
import com.rmalan.app.moviecataloguealpha.model.MovieItems;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    TextView tvTitle, tvRelease, tvOverview;
    ImageView imgPoster;

    private MovieItems movieItems;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_favorite) {
            favoriteItems = new FavoriteItems();

            favoriteItems.setId(movieItems.getId());
            favoriteItems.setPoster(movieItems.getPoster());
            favoriteItems.setTitle(movieItems.getTitle());
            favoriteItems.setReleaseDate(movieItems.getReleaseDate());
            favoriteItems.setOverview(movieItems.getOverview());

            long result = favoritesHelper.insertMovie(favoriteItems);

            if (result > 0) {
                Toast.makeText(this, R.string.add_favorite, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.already_favorite, Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
