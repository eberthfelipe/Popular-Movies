package com.android.udacity.popularmovies.MVP;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.android.udacity.popularmovies.Model.Movie;

import java.util.ArrayList;

public interface MovieContract {

    //region Models
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
    interface ActivityView{
        void showProgress();
        void hideProgress();
        void setMovieList(ArrayList<Movie> movieArrayList);
        Context getContext();
        void showNoInternetConnection(boolean show);
    }
    interface ListItemClickListener {
        void onListItemClick(int listItemIndex, Drawable drawable);
    }
    //endregion

    //region Presenters
    interface MoviesPresenter {
        void fetchDataFromMovieDatabase(int preference);
        void showProgress();
        void hideProgress();
        Context getContext();
        void setMovieList(ArrayList<Movie> movieArrayList);
        void retrieveImageSrc(String imgPath, ImageView imageView);
        int getPreferences(Context context);
        void setPreferences(Context context, int value);
        void showNoInternetConnection(boolean show);
    }
    //endregion

}
