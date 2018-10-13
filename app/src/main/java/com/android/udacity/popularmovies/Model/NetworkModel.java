package com.android.udacity.popularmovies.Model;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Utils.NetworkUtils;

//class to implements NetworkModel
public class NetworkModel implements MovieContract.NetworkModel{
    private MovieContract.MoviesPresenter mMoviesPresenter;

    public NetworkModel(MovieContract.MoviesPresenter moviesPresenter){
        this.mMoviesPresenter = moviesPresenter;
    }

    @Override
    public void fetchDataFromMovieDatabase(int preference) {
        if(NetworkUtils.checkInternetConnection(mMoviesPresenter.getContext())){
            mMoviesPresenter.showNoInternetConnection(false);
            ConnectNetwork connectNetwork = new ConnectNetwork(mMoviesPresenter);
            connectNetwork.execute(NetworkUtils.buildURL(preference));
        } else {
            //Done: implement network problem view
            mMoviesPresenter.showNoInternetConnection(true);
        }
    }

}
