package com.android.udacity.popularmovies.Model;

import android.content.Context;
import android.widget.ImageView;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Utils.NetworkUtils;

//class to implements NetworkModel
public class NetworkModel implements MovieContract.NetworkModel{
    private MovieContract.NetworkPresenter mNetworkPresenter;

    public NetworkModel(MovieContract.NetworkPresenter networkPresenter){
        this.mNetworkPresenter = networkPresenter;
    }
    @Override
    public void fetchDataFromMovieDatabase(int preference) {
        ConnectNetwork connectNetwork = new ConnectNetwork(mNetworkPresenter);
        connectNetwork.execute(NetworkUtils.buildURL(preference));
    }

    @Override
    public void retrieveImageSrc(Context context, String imgPath, ImageView imageView) {
        NetworkUtils.buildPicassoRequest(context, imgPath, imageView);
    }
}
