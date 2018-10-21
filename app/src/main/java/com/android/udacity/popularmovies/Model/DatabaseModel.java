package com.android.udacity.popularmovies.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.udacity.popularmovies.Data.MoviesContractDB;
import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Object.Movie;

import java.io.File;

public class DatabaseModel implements MovieContract.DataBaseModel {

    private final String TAG = MovieContract.DataBaseModel.class.getName();

    @Override
    public void insertNewFavorite(Context context, Movie movie) {
        Log.d(TAG, "insertNewFavorite: " + movie.toString());

        if(isMovieAlreadyInserted(context, movie.getId()))
            return;

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
        Log.d(TAG, "URI inserted: " + (uri != null ? uri.toString() : null));
    }

    @Override
    public boolean isMovieAlreadyInserted(Context context, int id) {
        boolean isFavorite = false;
        Uri uri = MoviesContractDB.MovieEntry.buildMoviesUri(id);
        Cursor cursor = context.getContentResolver().query(
                uri,
                new String[] {MoviesContractDB.MovieEntry._ID},
                MoviesContractDB.MovieEntry._ID,
                new String[]{String.valueOf(id)},
                null
        );
        if(cursor != null && cursor.getCount()>0){
            Log.w(TAG, "Already added in Movies Local DB!");
            isFavorite = true;
            cursor.close();
        }
        return isFavorite;
    }

    @Override
    public void deleteFavoriteMovie(Context context, int id) {
        String imgPath = getImagePathFromDB(context, id);
        Uri uri = MoviesContractDB.MovieEntry.buildMoviesUri(id);
        int deleted = context.getContentResolver().delete(uri, MoviesContractDB.MovieEntry._ID, new String[]{String.valueOf(id)});
        if(deleted > 0){
            if(imgPath != null){
                deleteImage(imgPath);
            }
            Log.d(TAG, "deleteFavoriteMovie: " + id);
        }
    }

    @Override
    public void updateMovieImagePath(Context context, int id, String value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContractDB.MovieEntry.COLUMN_IMAGE_PATH, value);

        Uri uri = MoviesContractDB.MovieEntry.buildMoviesUri(id);
        int update = context.getContentResolver().update(
                uri,
                contentValues,
                MoviesContractDB.MovieEntry._ID,
                new String[]{String.valueOf(id)}
        );
        if(update > 0){
            Log.d(TAG, "updateMovie: " + id);
        }
    }

    private String getImagePathFromDB(Context context, int id){
        String imagePath = "";
        Uri uri = MoviesContractDB.MovieEntry.buildMoviesUri(id);
        Cursor cursor = context.getContentResolver().query(
                uri,
                new String[] {MoviesContractDB.MovieEntry.COLUMN_IMAGE_PATH},
                MoviesContractDB.MovieEntry._ID,
                new String[]{String.valueOf(id)},
                null
        );
        if(cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            imagePath = cursor.getString(0);
            cursor.close();
        }
        return imagePath;
    }

    private void deleteImage(@NonNull String fileName){
        File file = new File(fileName);
        if(file.exists() && file.delete()){
            Log.d(TAG, "deleteImage: " + fileName);
        }
    }
}
