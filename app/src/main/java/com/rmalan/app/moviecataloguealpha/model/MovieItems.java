package com.rmalan.app.moviecataloguealpha.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieItems implements Parcelable {
    public static final Parcelable.Creator<MovieItems> CREATOR = new Parcelable.Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel source) {
            return new MovieItems(source);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };
    private int id;
    private String poster, title, releaseDate, overview;

    public MovieItems(JSONObject object) {
        try {
            int id = object.getInt("id");
            String poster = object.getString("poster_path");
            String title = object.getString("title");

            DateFormat outputFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");

            String inputText = object.getString("release_date");
            Date date = inputFormat.parse(inputText);
            String releaseDate = outputFormat.format(date);

            String overview = object.getString("overview");

            this.id = id;
            this.poster = poster;
            this.title = title;
            this.releaseDate = releaseDate;
            this.overview = overview;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected MovieItems(Parcel in) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
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
