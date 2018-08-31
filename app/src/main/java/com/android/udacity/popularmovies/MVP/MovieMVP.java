package com.android.udacity.popularmovies.MVP;

import java.net.URL;

public interface MovieMVP {

    //region Models
    interface Model { }
    interface NetworkModel{
        void fetchDataFromMovieDatabase(int preference);
    }
    //endregion

    //region Views
    interface View { }
    interface ActivityView{
        void showProgress();
        void hideProgress();
    }
    //endregion

    //region Presenters
    interface Presenter { }
    interface NetworkPresenter {
        void fetchDataFromMovieDatabase();
        void showProgress();
        void hideProgress();
    }
    //endregion

}
