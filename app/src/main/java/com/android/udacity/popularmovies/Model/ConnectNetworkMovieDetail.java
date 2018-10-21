package com.android.udacity.popularmovies.Model;

import android.os.AsyncTask;
import android.util.Log;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class ConnectNetworkMovieDetail extends AsyncTask<URL, Void, String> {

    private static final String TAG = ConnectNetworkMovieDetail.class.getName();
    private MovieContract.MovieDetailPresenter mMovieDetailPresenter;

    ConnectNetworkMovieDetail(MovieContract.MovieDetailPresenter movieDetailPresenter){
        this.mMovieDetailPresenter = movieDetailPresenter;
    }

    @Override
    protected void onPreExecute() {
        mMovieDetailPresenter.showProgress();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(URL... urls) {
        String result;
        for (URL url: urls) {
            try {
                result = NetworkUtils.getResponseFromHttpUrl(url);
                Log.d(TAG, "doInBackground: " + result);
                // Parse JSON Object
                if(result != null){
                    //TODO: feature retrieve trailers and reviews
//                    return new ArrayList<>(MovieDatabaseJsonUtils.getMovieDatabasePopularList(result));
                    return result;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String mMovieArrayList) {
        mMovieDetailPresenter.hideProgress();
    }
}
