package com.android.udacity.popularmovies.Presenter;

import android.content.Context;
import android.widget.ImageView;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Model.Movie;
import com.android.udacity.popularmovies.Model.NetworkModel;
import com.android.udacity.popularmovies.Model.UserPreferenceModel;

import java.util.ArrayList;

public class MoviesPresenter implements MovieContract.MoviesPresenter {
    private MovieContract.NetworkModel mNetworkModel;
    private MovieContract.UserPreferenceModel mUserPreferenceModel;
    private MovieContract.ActivityView mActivityView;

    public MoviesPresenter(){
        mNetworkModel = new NetworkModel(this);
    }

    //region Model Interactions
    @Override
    public void fetchDataFromMovieDatabase(int preference) {
        // get User preference for movies: popular or top rated
        mNetworkModel.fetchDataFromMovieDatabase(preference);
    }

    @Override
    public int getPreferences(Context context) {
        if(mUserPreferenceModel == null){
            mUserPreferenceModel = new UserPreferenceModel();
        }
        return mUserPreferenceModel.getPreferences(getContext());
    }

    @Override
    public void setPreferences(Context context, int value) {
        if(mUserPreferenceModel == null){
            mUserPreferenceModel = new UserPreferenceModel();
        }
        mUserPreferenceModel.setPreferences(context, value);
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