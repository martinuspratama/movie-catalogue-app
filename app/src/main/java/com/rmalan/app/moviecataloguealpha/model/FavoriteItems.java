package com.rmalan.app.moviecataloguealpha.model;

import android.os.Parcel;
import android.os.Parcelable;

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
