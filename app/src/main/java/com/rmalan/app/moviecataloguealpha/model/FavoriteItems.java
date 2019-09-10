package com.rmalan.app.moviecataloguealpha.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.ID;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.OVERVIEW;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.POSTER;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.RELEASE_DATE;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.FavoritesColumns.TITLE;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.getColumnInt;
import static com.rmalan.app.moviecataloguealpha.db.DatabaseContract.getColumnString;

public class FavoriteItems implements Parcelable {
    public static final Creator<FavoriteItems> CREATOR = new Creator<FavoriteItems>() {
        @Override
        public FavoriteItems createFromParcel(Parcel source) {
            return new FavoriteItems(source);
        }

        @Override
        public FavoriteItems[] newArray(int size) {
            return new FavoriteItems[size];
        }
    };
    private int id;
    private String poster, title, releaseDate, overview;

    public FavoriteItems() {
    }

    public FavoriteItems(int id, String poster, String title, String releaseDate, String overview) {
        this.id = id;
        this.poster = poster;
        this.title = title;
        this.releaseDate = releaseDate;
        this.overview = overview;
    }

    public FavoriteItems(Cursor cursor) {
        this.id = getColumnInt(cursor, ID);
        this.poster = getColumnString(cursor, POSTER);
        this.title = getColumnString(cursor, TITLE);
        this.releaseDate = getColumnString(cursor, RELEASE_DATE);
        this.overview = getColumnString(cursor, OVERVIEW);
    }

    protected FavoriteItems(Parcel in) {
        this.id = in.readInt();
        this.poster = in.readString();
        this.title = in.readString();
        this.releaseDate = in.readString();
        this.overview = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.poster);
        dest.writeString(this.title);
        dest.writeString(this.releaseDate);
        dest.writeString(this.overview);
    }
}
