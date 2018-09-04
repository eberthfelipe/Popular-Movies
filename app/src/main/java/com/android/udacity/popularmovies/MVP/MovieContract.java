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
    //endregion

    //region Views
    interface View { }
    interface ActivityView{
        void showProgress();
        void hideProgress();
        //TODO: implement view data
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
    interface NetworkPresenter {
        void fetchDataFromMovieDatabase();
        void showProgress();
        void hideProgress();
        Context getContext();
        //TODO: implement presenter data
        void showMovies();
        void setMovieList(ArrayList<Movie> movieArrayList);
        void retrieveImageSrc(String imgPath, ImageView imageView);
    }
    //endregion

}
