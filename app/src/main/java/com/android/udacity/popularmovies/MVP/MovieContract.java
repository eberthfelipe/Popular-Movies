package com.android.udacity.popularmovies.MVP;

import android.content.Context;
import android.widget.ImageView;

import com.android.udacity.popularmovies.Model.Movie;

import java.util.ArrayList;

public interface MovieContract {

    //region Models
    interface Model { }
    interface NetworkModel{
        void fetchDataFromMovieDatabase(int preference);
        //DONE: retrieve image from PICASSO
        void retrieveImageSrc(Context context, String imgPath, ImageView imageView);
    }
    interface UserPreferenceModel {
        int getPreferences(Context context);
        void setPreferences(Context context, int value);
    }
    //endregion

    //region Views
    interface View { }
    interface ActivityView{
        void showProgress();
        void hideProgress();
        void showMovies();
        void setMovieList(ArrayList<Movie> movieArrayList);
        Context getContext();
    }
    interface ListItemClickListener {
        void onListItemClick(int listItemIndex);
    }
    //endregion

    //region Presenters
    interface Presenter { }
    interface MoviesPresenter {
        void fetchDataFromMovieDatabase(int preference);
        void showProgress();
        void hideProgress();
        Context getContext();
        void showMovies();
        void setMovieList(ArrayList<Movie> movieArrayList);
        void retrieveImageSrc(String imgPath, ImageView imageView);
        int getPreferences(Context context);
        void setPreferences(Context context, int value);
    }
    //endregion

}
