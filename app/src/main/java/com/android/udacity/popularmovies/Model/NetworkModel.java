package com.android.udacity.popularmovies.Model;

import com.android.udacity.popularmovies.MVP.MovieMVP;
import com.android.udacity.popularmovies.Utils.NetworkUtils;

//class to implements NetworkModel
public class NetworkModel implements MovieMVP.NetworkModel{
    private MovieMVP.NetworkPresenter mNetworkPresenter;

    public NetworkModel(MovieMVP.NetworkPresenter networkPresenter){
        this.mNetworkPresenter = networkPresenter;
    }
    @Override
    public void fetchDataFromMovieDatabase(int preference) {
        ConnectNetwork connectNetwork = new ConnectNetwork(mNetworkPresenter);
        connectNetwork.execute(NetworkUtils.buildURL(preference));
    }
}
