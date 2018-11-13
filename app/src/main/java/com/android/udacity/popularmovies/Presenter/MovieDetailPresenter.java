package com.android.udacity.popularmovies.Presenter;

import android.content.Context;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Model.NetworkDetailModel;
import com.android.udacity.popularmovies.Model.PicassoModelSingleton;
import com.android.udacity.popularmovies.Object.MovieDetail;

public class MovieDetailPresenter implements MovieContract.MovieDetailPresenter {

    private MovieContract.DetailView mDetailView;
    private MovieContract.NetworkMovieDetailModel mNetworkMovieDetailModel;

    public MovieDetailPresenter(MovieContract.DetailView mView) {
        this.mDetailView = mView;
        mNetworkMovieDetailModel = new NetworkDetailModel(this);
    }

    @Override
    public Context getContext() {
        return mDetailView != null ? mDetailView.getContext() : null;
    }

    @Override
    public void showNoInternetConnection(boolean show) {

    }

    @Override
    public void showProgress() {
        mDetailView.showProgress();
    }

    @Override
    public void hideProgress() {
        mDetailView.hideProgress();
    }

    @Override
    public void retrieveImageSrc(String imgPath, Object object, int type) {
        PicassoModelSingleton picassoModel = PicassoModelSingleton.getInstance();
        picassoModel.retrieveImageSrc(getContext(), imgPath, object, type);
    }

    @Override
    public void fetchTrailerAndReviewFromMovieDB(int movieId) {
        mNetworkMovieDetailModel.fetchTrailerAndReviewFromMovieDB(movieId);
    }

    @Override
    public void setMovieDetail(MovieDetail movieDetail) {
        mDetailView.setMovieDetail(movieDetail);
    }

    @Override
    public void enableShareMenuOption() {
        mDetailView.enableShareMenuOption();
    }
}
