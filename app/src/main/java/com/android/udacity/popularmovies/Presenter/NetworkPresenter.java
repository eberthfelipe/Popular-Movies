package com.android.udacity.popularmovies.Presenter;

import com.android.udacity.popularmovies.MVP.MovieMVP;
import com.android.udacity.popularmovies.Model.ConnectNetwork;
import com.android.udacity.popularmovies.Utils.NetworkUtils;

public class NetworkPresenter implements MovieMVP.NetworkPresenter {
    private MovieMVP.NetworkModel networkModel;
    private MovieMVP.ActivityView activityView;

    public NetworkPresenter(){
        networkModel = new ConnectNetwork(this);
    }

    //region Model Interactions
    @Override
    public void fetchDataFromMovieDatabase() {
        // get User preference for movies: popular or top rated
        int preference = 0;
        networkModel.fetchDataFromMovieDatabase(NetworkUtils.buildURL(preference));
    }
    //endregion

    //region View Interactions
    public void setActivityView(MovieMVP.ActivityView activityView){
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
    //endregion
}
