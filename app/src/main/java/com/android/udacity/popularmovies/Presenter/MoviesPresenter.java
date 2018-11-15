package com.android.udacity.popularmovies.Presenter;

import android.content.Context;
import android.widget.ImageView;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Model.DatabaseModel;
import com.android.udacity.popularmovies.Object.Movie;
import com.android.udacity.popularmovies.Model.NetworkModel;
import com.android.udacity.popularmovies.Model.PicassoModelSingleton;
import com.android.udacity.popularmovies.Model.UserPreferenceModel;

import java.util.ArrayList;

public class MoviesPresenter implements MovieContract.MoviesPresenter {
    private MovieContract.NetworkModel mNetworkModel;
    private MovieContract.UserPreferenceModel mUserPreferenceModel;
    private MovieContract.ActivityView mActivityView;
    private MovieContract.DatabasePresenter mDatabasePresenter;

    public MoviesPresenter(MovieContract.ActivityView activityView){
        mNetworkModel = new NetworkModel(this);
        this.mActivityView = activityView;
        mDatabasePresenter = new DatabasePresenter(activityView);
    }

    //region Model Interactions
    @Override
    public void fetchDataFromMovieDatabase(int preference) {
        // get User preference for movies: popular, top rated or local favorites
        switch (preference){
            case 0:
            case 1:
                mNetworkModel.fetchDataFromMovieDatabase(preference);
                break;
            case 2:
                //local database has favorite movies
                mActivityView.setMovieList(mDatabasePresenter.loadFavoriteMovies());
                mActivityView.hideProgress();
                mActivityView.showNoInternetConnection(false);
                break;
        }
    }

    @Override
    public int getPreferences(Context context) {
        if(mUserPreferenceModel == null){
            mUserPreferenceModel = new UserPreferenceModel();
        }
        return mUserPreferenceModel.getPreferences(context);
    }

    @Override
    public void setPreferences(Context context, int value) {
        if(mUserPreferenceModel == null){
            mUserPreferenceModel = new UserPreferenceModel();
        }
        mUserPreferenceModel.setPreferences(context, value);
    }

    @Override
    public void retrieveImageSrc(String imgPath, Object object, int type) {
        PicassoModelSingleton picassoModel = PicassoModelSingleton.getInstance();
        picassoModel.retrieveImageSrc(getContext(), imgPath, object, type);
    }

    //endregion

    //region View Interactions

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
        if(mActivityView != null)
            return mActivityView.getContext();
        return null;
    }

    @Override
    public void setMovieList(ArrayList<Movie> movieArrayList) {
        mActivityView.setMovieList(movieArrayList);

    }

    @Override
    public void showNoInternetConnection(boolean show) {
        mActivityView.showNoInternetConnection(show);
    }
    //endregion

    //region Database Interactions
    public boolean isMovieSavedInDB(int movieID){
        return mDatabasePresenter.isMovieAlreadyInserted(movieID);
    }
    //endregion
}
