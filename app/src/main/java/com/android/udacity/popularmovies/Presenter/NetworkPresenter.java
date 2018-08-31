package com.android.udacity.popularmovies.Presenter;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Model.Movie;
import com.android.udacity.popularmovies.Model.NetworkModel;

import java.util.ArrayList;

public class NetworkPresenter implements MovieContract.NetworkPresenter {
    private MovieContract.NetworkModel networkModel;
    private MovieContract.ActivityView activityView;

    public NetworkPresenter(){
        networkModel = new NetworkModel(this);
    }

    //region Model Interactions
    @Override
    public void fetchDataFromMovieDatabase() {
        // get User preference for movies: popular or top rated
        int preference = 0;
        networkModel.fetchDataFromMovieDatabase(preference);
    }
    //endregion

    //region View Interactions
    public void setActivityView(MovieContract.ActivityView activityView){
        this.activityView = activityView;
    }

    @Override
    public void showProgress() {
        activityView.showProgress();
    }

    @Override
    public void hideProgress() {
        activityView.hideProgress();
    }

    @Override
    public void showMovies() {
        activityView.showMovies();
    }

    @Override
    public void setMovieList(ArrayList<Movie> movieArrayList) {
        activityView.setMovieList(movieArrayList);
    }
    //endregion
}
