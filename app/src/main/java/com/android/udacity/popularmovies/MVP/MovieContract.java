package com.android.udacity.popularmovies.MVP;

import android.widget.ImageView;

import com.android.udacity.popularmovies.Model.Movie;

import java.util.ArrayList;

public interface MovieContract {

    //region Models
    interface Model { }
    interface NetworkModel{
        void fetchDataFromMovieDatabase(int preference);
        //TODO: retrieve image from PICASSO
        ImageView retrieveImageSrc(String imgPath);
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
        //TODO: implement presenter data
        void showMovies();
        void setMovieList(ArrayList<Movie> movieArrayList);
    }
    //endregion

}
