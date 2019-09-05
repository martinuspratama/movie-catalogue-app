package com.rmalan.app.moviecataloguealpha.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.rmalan.app.moviecataloguealpha.R;
import com.rmalan.app.moviecataloguealpha.db.FavoritesHelper;
import com.rmalan.app.moviecataloguealpha.model.FavoriteItems;

public class TvShowFavoriteDetailActivity extends AppCompatActivity {

    public static final String EXTRA_FAVORITE = "extra_favorite";
    public static final String EXTRA_POSITION = "extra_position";

    public static final int REQUEST_DELETE = 300;
    public static final int RESULT_DELETE = 301;

    private final int ALERT_DIALOG_DELETE = 20;

    TextView tvTitle, tvRelease, tvOverview;
    ImageView imgPoster;

    private FavoriteItems favoriteItems;
    private int position;

    private FavoritesHelper favoritesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgPoster = findViewById(R.id.img_poster);
        tvTitle = findViewById(R.id.txt_title);
        tvRelease = findViewById(R.id.txt_release);
        tvOverview = findViewById(R.id.txt_overview);

        favoritesHelper = FavoritesHelper.getInstance(getApplicationContext());
        favoritesHelper.open();

        favoriteItems = getIntent().getParcelableExtra(EXTRA_FAVORITE);
        position = getIntent().getIntExtra(EXTRA_POSITION, 0);

        Glide.with(this).load("https://image.tmdb.org/t/p/w500/" + favoriteItems.getPoster()).into(imgPoster);
        tvTitle.setText(favoriteItems.getTitle());
        tvRelease.setText(favoriteItems.getReleaseDate());
        tvOverview.setText(favoriteItems.getOverview());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.detail_tv_show));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            showAlertDialog(ALERT_DIALOG_DELETE);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog(int type) {
        String dialogTitle, dialogMessage;

        dialogTitle = getResources().getString(R.string.title_delete);
        dialogMessage = getResources().getString(R.string.delete);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder.setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        long result = favoritesHelper.deleteTvShow(favoriteItems.getId());
                        if (result > 0) {
                            Intent intent = new Intent();
                            intent.putExtra(EXTRA_POSITION, position);
                            setResult(RESULT_DELETE, intent);
                            Toast.makeText(TvShowFavoriteDetailActivity.this, R.string.delete_item, Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(TvShowFavoriteDetailActivity.this, R.string.delete_failed, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
