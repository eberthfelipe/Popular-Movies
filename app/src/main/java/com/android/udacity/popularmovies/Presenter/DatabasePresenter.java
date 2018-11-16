package com.android.udacity.popularmovies.Presenter;

import android.content.Context;
import android.content.ContextWrapper;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Object.Movie;
import com.android.udacity.popularmovies.Model.PicassoModelSingleton;
import com.android.udacity.popularmovies.Model.PicassoTarget;

import java.util.ArrayList;

public class DatabasePresenter implements MovieContract.DatabasePresenter {

    private static final String IMG_FOLDER = "moviePoster";
    private MovieContract.View mView;
    private MovieContract.DatabaseModel mDatabaseModel;

    public DatabasePresenter(MovieContract.View view) {
        this.mView = view;
        mDatabaseModel = new com.android.udacity.popularmovies.Model.DatabaseModel();
    }

    @Override
    public void insertNewFavorite(Movie movie) {
        String fileImgPath;
        mDatabaseModel.insertNewFavorite(mView.getContext(), movie);
        ContextWrapper contextWrapper = new ContextWrapper(mView.getContext());
        fileImgPath = contextWrapper.getDir(IMG_FOLDER, Context.MODE_PRIVATE) + movie.getPosterPath();
        savePicassoImage(movie.getPosterPath(),fileImgPath);
        updateMovieImagePath(movie.getId(), fileImgPath);
    }


    @Override
    public boolean isMovieAlreadyInserted(int id) {
        return mDatabaseModel.isMovieAlreadyInserted(mView.getContext(), id);
    }

    @Override
    public void deleteFavoriteMovie(int id) {
        mDatabaseModel.deleteFavoriteMovie(mView.getContext(), id);
    }

    @Override
    public void updateMovieImagePath(int id, String value) {
        mDatabaseModel.updateMovieImagePath(mView.getContext(), id, value);
    }

    @Override
    public ArrayList<Movie> loadFavoriteMovies() {
        return mDatabaseModel.loadFavoriteMovies(getContext());
    }

    @Override
    public Context getContext() {
        if(mView != null)
            return mView.getContext();
        return null;
    }

    private synchronized void savePicassoImage(String imgURL, String imgName){
        PicassoModelSingleton picassoModel = PicassoModelSingleton.getInstance();
        PicassoTarget picassoTarget = new PicassoTarget(imgName);
        picassoModel.retrieveImageSrc(getContext(), imgURL, picassoTarget, PicassoModelSingleton.TYPE_TARGET);
    }
}
