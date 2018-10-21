package com.android.udacity.popularmovies.MVP;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.android.udacity.popularmovies.Model.Movie;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public interface MovieContract {

    //region Models
    interface NetworkModel{
        void fetchDataFromMovieDatabase(int preference);
    }

    interface NetworkMovieDetailModel{
        void fetchTrailerAndReviewFromMovieDB(int movieId);
    }

    interface PicassoModel{
        void retrieveImageSrc(Context context, String imgPath, ImageView imageView);
        void retrieveImageSrc(Context context, String imgPath, Target target);
    }

    interface UserPreferenceModel {
        int getPreferences(Context context);
        void setPreferences(Context context, int value);
    }

    interface DataBaseModel {
        void insertNewFavorite(Context context, Movie movie);
        boolean isMovieAlreadyInserted(Context context, int id);
        void deleteFavoriteMovie(Context context, int id);
        void updateMovieImagePath(Context context, int id, String value);
    }
    //endregion

    //region Views
    interface ActivityView extends View{
        void showProgress();
        void hideProgress();
        void setMovieList(ArrayList<Movie> movieArrayList);
    }
    interface View{
        Context getContext();
        void showNoInternetConnection(boolean show);
    }
    interface ListItemClickListener {
        void onListItemClick(int listItemIndex, Drawable drawable);
    }
    //endregion

    //region Presenters
    interface Presenter {
        Context getContext();
        void showNoInternetConnection(boolean show);
        void showProgress();
        void hideProgress();
    }

    interface MoviesPresenter extends Presenter{
        void fetchDataFromMovieDatabase(int preference);
        void setMovieList(ArrayList<Movie> movieArrayList);
        void retrieveImageSrc(String imgPath, ImageView imageView);
        int getPreferences(Context context);
        void setPreferences(Context context, int value);
    }

    interface MovieDetailPresenter extends Presenter{
        void fetchTrailerAndReviewFromMovieDB(int movieId);
    }

    interface DatabasePresenter {
        void insertNewFavorite(Movie movie);
        boolean isMovieAlreadyInserted(int id);
        void deleteFavoriteMovie(int id);
        void updateMovieImagePath(int id, String value);
        Context getContext();
    }
    //endregion

}
