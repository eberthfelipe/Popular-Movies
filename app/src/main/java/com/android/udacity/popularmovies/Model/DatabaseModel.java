package com.android.udacity.popularmovies.Model;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.udacity.popularmovies.Data.MoviesContractDB;
import com.android.udacity.popularmovies.MVP.MovieContract;

public class DatabaseModel implements MovieContract.DataBaseModel {

    private final String TAG = MovieContract.DataBaseModel.class.getName();

    @Override
    public void insertNewFavorite(Context context, Movie movie) {
        Log.d(TAG, "insertNewFavorite: " + movie.toString());
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContractDB.MovieEntry._ID, movie.getId());
        contentValues.put(MoviesContractDB.MovieEntry.COLUMN_TITLE, movie.getTitle());
        contentValues.put(MoviesContractDB.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        contentValues.put(MoviesContractDB.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        contentValues.put(MoviesContractDB.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());

        Uri uri = context.getContentResolver().insert(
                MoviesContractDB.MovieEntry.CONTENT_URI,
                contentValues
        );
        Log.d(TAG, "URI inserted: " + uri.toString());
    }
}
