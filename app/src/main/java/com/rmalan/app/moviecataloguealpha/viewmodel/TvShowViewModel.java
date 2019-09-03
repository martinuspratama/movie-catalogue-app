package com.rmalan.app.moviecataloguealpha.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.rmalan.app.moviecataloguealpha.model.TvShowItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.rmalan.app.moviecataloguealpha.BuildConfig.API_KEY;
import static com.rmalan.app.moviecataloguealpha.BuildConfig.BASE_URL;

public class TvShowViewModel extends ViewModel {
    private MutableLiveData<ArrayList<TvShowItems>> listTvShows = new MutableLiveData<>();

    public void setTvShow(final String language) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShowItems> listItems = new ArrayList<>();

        String url = BASE_URL + "discover/tv?api_key=" + API_KEY + "&language=" + language;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tvShow = list.getJSONObject(i);
                        TvShowItems tvShowItems = new TvShowItems(tvShow);
                        listItems.add(tvShowItems);
                    }
                    listTvShows.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<TvShowItems>> getTvShows() {
        return listTvShows;
    }
}
