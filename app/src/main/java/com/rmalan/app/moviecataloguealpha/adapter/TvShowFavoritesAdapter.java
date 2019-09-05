package com.rmalan.app.moviecataloguealpha.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rmalan.app.moviecataloguealpha.CustomOnItemClickListener;
import com.rmalan.app.moviecataloguealpha.R;
import com.rmalan.app.moviecataloguealpha.detail.TvShowFavoriteDetailActivity;
import com.rmalan.app.moviecataloguealpha.model.FavoriteItems;

import java.util.ArrayList;

public class TvShowFavoritesAdapter extends RecyclerView.Adapter<TvShowFavoritesAdapter.TvShowFavoritesViewHolder> {

    private ArrayList<FavoriteItems> listTvShowFavorites = new ArrayList<>();
    private Activity activity;

    public TvShowFavoritesAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<FavoriteItems> getListTvShowFavorites() {
        return listTvShowFavorites;
    }

    public void setListTvShowFavorites(ArrayList<FavoriteItems> listTvShowFavorites) {
        if (listTvShowFavorites.size() > 0) {
            this.listTvShowFavorites.clear();
        }
        this.listTvShowFavorites.addAll(listTvShowFavorites);

        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        this.listTvShowFavorites.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listTvShowFavorites.size());
    }

    @NonNull
    @Override
    public TvShowFavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_items, parent, false);
        return new TvShowFavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowFavoritesViewHolder holder, int position) {
        Glide.with(holder.imgPoster.getContext()).load("https://image.tmdb.org/t/p/w500/" + listTvShowFavorites.get(position).getPoster()).into(holder.imgPoster);
        holder.itemView.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, TvShowFavoriteDetailActivity.class);
                intent.putExtra(TvShowFavoriteDetailActivity.EXTRA_POSITION, position);
                intent.putExtra(TvShowFavoriteDetailActivity.EXTRA_FAVORITE, listTvShowFavorites.get(position));
                activity.startActivityForResult(intent, TvShowFavoriteDetailActivity.REQUEST_DELETE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listTvShowFavorites.size();
    }

    public class TvShowFavoritesViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;

        public TvShowFavoritesViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.img_poster);
        }
    }
}
