package com.android.udacity.popularmovies.Model;

import android.os.AsyncTask;
import android.util.Log;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Utils.MovieDatabaseJsonUtils;
import com.android.udacity.popularmovies.Utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

// Class to perform http requests
public class ConnectNetwork extends AsyncTask<URL, Void, String>{
    private static final String TAG = ConnectNetwork.class.getName();
    private MovieContract.NetworkPresenter mNetworkPresenter;
    private ArrayList<Movie> mMovieArrayList;


    ConnectNetwork(MovieContract.NetworkPresenter networkPresenter){
        this.mNetworkPresenter = networkPresenter;
    }

    @Override
    protected void onPreExecute() {
        mNetworkPresenter.showProgress();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(URL... urls) {
        String result = null;
        for (URL url: urls) {
            try {
                result = NetworkUtils.getResponseFromHttpUrl(url);
                Log.d(TAG, "doInBackground: " + result);
                // Parse JSON Object
                if(result != null){
                    mMovieArrayList = new ArrayList<>(MovieDatabaseJsonUtils.getMovieDatabasePopularList(result, mNetworkPresenter.getContext()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mNetworkPresenter.hideProgress();
        mNetworkPresenter.setMovieList(mMovieArrayList);
    }

}
