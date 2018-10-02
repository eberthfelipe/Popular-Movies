package com.android.udacity.popularmovies.Presenter;

import android.content.Context;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Model.DatabaseModel;
import com.android.udacity.popularmovies.Model.Movie;

public class DatabasePresenter implements MovieContract.DatabasePresenter {

    private MovieContract.View mDetailView;
    private MovieContract.DataBaseModel mDataBaseModel;

    public DatabasePresenter(MovieContract.View detailView) {
        this.mDetailView = detailView;
        mDataBaseModel = new DatabaseModel();
    }

    @Override
    public void insertNewFavorite(Movie movie) {
        mDataBaseModel.insertNewFavorite(mDetailView.getContext(), movie);
    }
}
