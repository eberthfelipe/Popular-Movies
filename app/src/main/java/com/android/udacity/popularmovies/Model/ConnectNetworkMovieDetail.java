package com.android.udacity.popularmovies.Model;

import android.os.AsyncTask;
import android.util.Log;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Object.MovieDetail;
import com.android.udacity.popularmovies.Object.MovieReview;
import com.android.udacity.popularmovies.Object.MovieVideo;
import com.android.udacity.popularmovies.Utils.MovieDatabaseJsonUtils;
import com.android.udacity.popularmovies.Utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class ConnectNetworkMovieDetail extends AsyncTask<URL, Void, MovieDetail> {

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
    protected MovieDetail doInBackground(URL... urls) {
        String result;
        MovieVideo[] movieVideos = new MovieVideo[0];
        MovieReview[] movieReviews = new MovieReview[0];
        int aux = 0;
        for (URL url: urls) {
            try {
                result = NetworkUtils.getResponseFromHttpUrl(url);
                Log.d(TAG, "doInBackground: " + result);
                // Parse JSON Object
                if(result != null){
                    switch (aux){
                        case 0:
                            movieVideos = MovieDatabaseJsonUtils.getMovieDatabaseVideo(result);
                            break;
                        case 1:
                            movieReviews = MovieDatabaseJsonUtils.getMovieDatabaseReview(result);
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                aux++;
            }
        }
        return new MovieDetail(movieVideos,movieReviews);
    }

    @Override
    protected void onPostExecute(MovieDetail movieDetail) {
        mMovieDetailPresenter.hideProgress();
        mMovieDetailPresenter.setMovieDetail(movieDetail);
    }
}
