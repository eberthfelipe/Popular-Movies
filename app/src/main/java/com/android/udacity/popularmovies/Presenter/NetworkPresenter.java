package com.android.udacity.popularmovies.Presenter;

import android.content.Context;
import android.widget.ImageView;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Model.Movie;
import com.android.udacity.popularmovies.Model.NetworkModel;

import java.util.ArrayList;

public class NetworkPresenter implements MovieContract.NetworkPresenter {
    private MovieContract.NetworkModel mNetworkModel;
    private MovieContract.ActivityView mActivityView;

    public NetworkPresenter(){
        mNetworkModel = new NetworkModel(this);
    }

    //region Model Interactions
    @Override
    public void fetchDataFromMovieDatabase() {
        // get User preference for movies: popular or top rated
        int preference = 0;
        mNetworkModel.fetchDataFromMovieDatabase(preference);
    }
    //endregion

    //region View Interactions
    public void setActivityView(MovieContract.ActivityView mActivityView){
        this.mActivityView = mActivityView;
    }

    @Override
    public void showProgress() {
        mActivityView.showProgress();
    }

    @Override
    public void hideProgress() {
        mActivityView.hideProgress();
    }

    @Override
    public Context getContext() {
        return mActivityView.getContext();
    }

    @Override
    public void showMovies() {
        mActivityView.showMovies();
    }

    @Override
    public void setMovieList(ArrayList<Movie> movieArrayList) {
        mActivityView.setMovieList(movieArrayList);

    }

    @Override
    public void retrieveImageSrc(String imgPath, ImageView imageView) {
        mNetworkModel.retrieveImageSrc(getContext(), imgPath, imageView);
    }
    //endregion
}
