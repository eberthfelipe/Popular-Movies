package com.android.udacity.popularmovies.Model;

import android.os.AsyncTask;
import android.util.Log;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Object.Movie;
import com.android.udacity.popularmovies.Utils.MovieDatabaseJsonUtils;
import com.android.udacity.popularmovies.Utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

// Class to perform http requests
class ConnectNetworkMovie extends AsyncTask<URL, Void, ArrayList<Movie>>{
    private static final String TAG = ConnectNetworkMovie.class.getName();
    private MovieContract.MoviesPresenter mMoviesPresenter;


    ConnectNetworkMovie(MovieContract.MoviesPresenter moviesPresenter){
        this.mMoviesPresenter = moviesPresenter;
    }

    @Override
    protected void onPreExecute() {
        mMoviesPresenter.showProgress();
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Movie> doInBackground(URL... urls) {
        String result;
        for (URL url: urls) {
            try {
                result = NetworkUtils.getResponseFromHttpUrl(url);
                Log.d(TAG, "doInBackground: " + result);
                // Parse JSON Object
                if(result != null){
                    return new ArrayList<>(MovieDatabaseJsonUtils.getMovieDatabasePopularList(result));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> mMovieArrayList) {
        super.onPostExecute(mMovieArrayList);
        mMoviesPresenter.hideProgress();
        //DONE: treat when mMovieArrayList is null and has nothing to show. Still have to show some feedback to user
        mMoviesPresenter.setMovieList(mMovieArrayList);
    }

}
