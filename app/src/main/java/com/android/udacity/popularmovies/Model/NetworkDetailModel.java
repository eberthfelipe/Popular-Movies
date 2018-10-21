package com.android.udacity.popularmovies.Model;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Utils.NetworkUtils;

public class NetworkDetailModel implements MovieContract.NetworkMovieDetailModel {

    private MovieContract.MovieDetailPresenter mMovieDetailPresenter;

    public NetworkDetailModel(MovieContract.MovieDetailPresenter movieDetailPresenter) {
        this.mMovieDetailPresenter = movieDetailPresenter;
    }

    @Override
    public void fetchTrailerAndReviewFromMovieDB(int movieId) {
        if(NetworkUtils.checkInternetConnection(mMovieDetailPresenter.getContext())){
            mMovieDetailPresenter.showNoInternetConnection(false);
            ConnectNetworkMovieDetail connectNetworkMovieDetail = new ConnectNetworkMovieDetail(mMovieDetailPresenter);
            connectNetworkMovieDetail.execute(
                    NetworkUtils.buildURLForTrailers(movieId),
                    NetworkUtils.buildURLForReviews(movieId)
            );
        } else {
            //Done: implement network problem view
            mMovieDetailPresenter.showNoInternetConnection(true);
        }
    }
}
