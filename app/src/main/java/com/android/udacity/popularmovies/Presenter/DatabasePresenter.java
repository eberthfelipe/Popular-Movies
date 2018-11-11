package com.android.udacity.popularmovies.Presenter;

import android.content.Context;
import android.content.ContextWrapper;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Model.DatabaseModel;
import com.android.udacity.popularmovies.Object.Movie;
import com.android.udacity.popularmovies.Model.PicassoModelSingleton;
import com.android.udacity.popularmovies.Model.PicassoTarget;

public class DatabasePresenter implements MovieContract.DatabasePresenter {

    private static final String IMG_FOLDER = "moviePoster";
    private MovieContract.View mDetailView;
    private MovieContract.DataBaseModel mDataBaseModel;

    public DatabasePresenter(MovieContract.View detailView) {
        this.mDetailView = detailView;
        mDataBaseModel = new DatabaseModel();
    }

    @Override
    public void insertNewFavorite(Movie movie) {
        String fileImgPath;
        mDataBaseModel.insertNewFavorite(mDetailView.getContext(), movie);
        ContextWrapper contextWrapper = new ContextWrapper(mDetailView.getContext());
        fileImgPath = contextWrapper.getDir(IMG_FOLDER, Context.MODE_PRIVATE) + movie.getPosterPath();
        savePicassoImage(movie.getPosterPath(),fileImgPath);
//        Local Test
//        savePicassoImage(movie.getPosterPath(),"/data/data/popularmovies.udacity.android.com.popularmovies/app_moviePoster/" + movie.getPosterPath());
        updateMovieImagePath(movie.getId(), fileImgPath);
    }


    @Override
    public boolean isMovieAlreadyInserted(int id) {
        return mDataBaseModel.isMovieAlreadyInserted(mDetailView.getContext(), id);
    }

    @Override
    public void deleteFavoriteMovie(int id) {
        mDataBaseModel.deleteFavoriteMovie(mDetailView.getContext(), id);
    }

    @Override
    public void updateMovieImagePath(int id, String value) {
        mDataBaseModel.updateMovieImagePath(mDetailView.getContext(), id, value);
    }

    @Override
    public Context getContext() {
        if(mDetailView != null)
            return mDetailView.getContext();
        return null;
    }

    private synchronized void savePicassoImage(String imgURL, String imgName){
        PicassoModelSingleton picassoModel = PicassoModelSingleton.getInstance();
        PicassoTarget picassoTarget = new PicassoTarget(imgName);
        picassoModel.retrieveImageSrc(getContext(), imgURL, picassoTarget, PicassoModelSingleton.TYPE_FILE);
    }
}
