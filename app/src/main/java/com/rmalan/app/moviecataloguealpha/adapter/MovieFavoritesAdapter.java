package com.rmalan.app.moviecataloguealpha.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rmalan.app.moviecataloguealpha.CustomOnItemClickListener;
import com.rmalan.app.moviecataloguealpha.R;
import com.rmalan.app.moviecataloguealpha.detail.MovieFavoriteDetailActivity;
import com.rmalan.app.moviecataloguealpha.model.FavoriteItems;

import java.util.ArrayList;

import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.CONTENT_URI_MOVIES;

public class MovieFavoritesAdapter extends RecyclerView.Adapter<MovieFavoritesAdapter.MovieFavoritesViewHolder> {

    private ArrayList<FavoriteItems> listMovieFavorites = new ArrayList<>();
    private Activity activity;

    public MovieFavoritesAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<FavoriteItems> getListMovieFavorites() {
        return listMovieFavorites;
    }

    public void setListMovieFavorites(ArrayList<FavoriteItems> listMovieFavorites) {
        this.listMovieFavorites.clear();
        this.listMovieFavorites.addAll(listMovieFavorites);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieFavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_items, parent, false);
        return new MovieFavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieFavoritesViewHolder holder, int position) {
        Glide.with(holder.imgPoster.getContext()).load("https://image.tmdb.org/t/p/w500/" + listMovieFavorites.get(position).getPoster()).into(holder.imgPoster);
        holder.itemView.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, MovieFavoriteDetailActivity.class);

                Uri uri = Uri.parse(CONTENT_URI_MOVIES + "/" + listMovieFavorites.get(position).getId());
                intent.setData(uri);
                intent.putExtra(MovieFavoriteDetailActivity.EXTRA_POSITION, position);
                intent.putExtra(MovieFavoriteDetailActivity.EXTRA_FAVORITE, listMovieFavorites.get(position));
                activity.startActivityForResult(intent, MovieFavoriteDetailActivity.REQUEST_DELETE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listMovieFavorites.size();
    }

    public class MovieFavoritesViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;

        public MovieFavoritesViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.img_poster);
        }
    }

}
