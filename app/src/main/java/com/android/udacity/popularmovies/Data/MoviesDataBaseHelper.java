package com.android.udacity.popularmovies.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Based on udacity/android-content-provider
 * https://github.com/udacity/android-content-provider
 */
public class MoviesDataBaseHelper extends SQLiteOpenHelper{

    private final String TAG = MoviesDataBaseHelper.class.getName();
    private static final String DB_NAME = "movies.db";
    private static final int DB_VERSION = 1;

    public MoviesDataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder stringBuilder = new StringBuilder();
        final String SQL_CREATE_MOVIE_TABLE = stringBuilder.append("CREATE TABLE ")
                .append(MoviesContractDB.MovieEntry.TABLE).append("(")
                .append(MoviesContractDB.MovieEntry._ID).append(" INTEGER PRIMARY KEY, ")
                .append(MoviesContractDB.MovieEntry.COLUMN_TITLE).append(" TEXT NOT NULL, ")
                .append(MoviesContractDB.MovieEntry.COLUMN_OVERVIEW).append(" TEXT NOT NULL, ")
                .append(MoviesContractDB.MovieEntry.COLUMN_VOTE_AVERAGE).append(" REAL NOT NULL, ")
                .append(MoviesContractDB.MovieEntry.COLUMN_RELEASE_DATE).append(" TEXT NOT NULL, ")
                .append(MoviesContractDB.MovieEntry.COLUMN_IMAGE_PATH).append(" TEXT);")
                .toString();
        Log.d(TAG, "onCreate database: " + SQL_CREATE_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading database from version "
                + oldVersion
                + " to "
                + newVersion);
        // Drop the table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContractDB.MovieEntry.TABLE);
        sqLiteDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
                +MoviesContractDB.MovieEntry.TABLE + "'");

        // re-create database
        onCreate(sqLiteDatabase);
    }
}
