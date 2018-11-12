package com.android.udacity.popularmovies.MVP;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.android.udacity.popularmovies.Object.Movie;
import com.android.udacity.popularmovies.Object.MovieDetail;
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
        void retrieveImageSrc(Context context, String imgPath, Object object, int type);
    }

    interface UserPreferenceModel {
        int getPreferences(Context context);
        void setPreferences(Context context, int value);
    }

    interface DatabaseModel {
        void insertNewFavorite(Context context, Movie movie);
        boolean isMovieAlreadyInserted(Context context, int id);
        void deleteFavoriteMovie(Context context, int id);
        void updateMovieImagePath(Context context, int id, String value);
        ArrayList<Movie> loadFavoriteMovies(Context context);
    }
    //endregion

    //region Views
    interface ActivityView extends View{
        void setMovieList(ArrayList<Movie> movieArrayList);
    }

    interface DetailView extends View{
        void setMovieDetail(MovieDetail movieDetail);
    }

    interface View{
        Context getContext();
        void showProgress();
        void hideProgress();
        void showNoInternetConnection(boolean show);
    }

    interface ListItemClickListener {
        void onListItemClick(int listItemIndex);
    }
    //endregion

    //region Presenters
    interface Presenter {
        Context getContext();
        void showNoInternetConnection(boolean show);
        void showProgress();
        void hideProgress();
        void retrieveImageSrc(String imgPath, Object object, int type);
    }

    interface MoviesPresenter extends Presenter{
        void fetchDataFromMovieDatabase(int preference);
        void setMovieList(ArrayList<Movie> movieArrayList);
        int getPreferences(Context context);
        void setPreferences(Context context, int value);
    }

    interface MovieDetailPresenter extends Presenter{
        void fetchTrailerAndReviewFromMovieDB(int movieId);
        void setMovieDetail(MovieDetail movieDetail);
    }

    interface DatabasePresenter {
        void insertNewFavorite(Movie movie);
        boolean isMovieAlreadyInserted(int id);
        void deleteFavoriteMovie(int id);
        void updateMovieImagePath(int id, String value);
        ArrayList<Movie> loadFavoriteMovies();
        Context getContext();
    }
    //endregion

}
