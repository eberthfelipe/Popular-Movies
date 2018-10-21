package com.android.udacity.popularmovies.Presenter;

import android.content.Context;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Model.NetworkDetailModel;

public class MovieDetailPresenter implements MovieContract.MovieDetailPresenter {

    private MovieContract.View mView;
    private MovieContract.NetworkMovieDetailModel mNetworkMovieDetailModel;

    public MovieDetailPresenter(MovieContract.View mView) {
        this.mView = mView;
        mNetworkMovieDetailModel = new NetworkDetailModel(this);
    }

    @Override
    public Context getContext() {
        return mView != null ? mView.getContext() : null;
    }

    @Override
    public void showNoInternetConnection(boolean show) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void fetchTrailerAndReviewFromMovieDB(int movieId) {
        mNetworkMovieDetailModel.fetchTrailerAndReviewFromMovieDB(movieId);
    }
}
