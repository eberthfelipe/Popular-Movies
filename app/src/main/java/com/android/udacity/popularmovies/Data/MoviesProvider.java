package com.android.udacity.popularmovies.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Objects;

/**
 * Based on udacity/android-content-provider
 * https://github.com/udacity/android-content-provider
 */
public class MoviesProvider extends ContentProvider {

    private static final String TAG = MoviesProvider.class.getName();
    private static final UriMatcher mUriMatcher = buildUriMatcher();
    private MoviesDataBaseHelper mMoviesDataBaseHelper;
    private static final int CONTENT_ALL_DATA = 100;
    private static final int CONTENT_ITEM_DATA = 200;

    @Override
    public boolean onCreate() {
        mMoviesDataBaseHelper = new MoviesDataBaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (mUriMatcher.match(uri)){
            case CONTENT_ALL_DATA:
                cursor = mMoviesDataBaseHelper.getReadableDatabase().query(
                        MoviesContractDB.MovieEntry.TABLE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                return cursor;
            case CONTENT_ITEM_DATA:
                cursor = mMoviesDataBaseHelper.getReadableDatabase().query(
                        MoviesContractDB.MovieEntry.TABLE,
                        projection,
                        MoviesContractDB.MovieEntry._ID + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder
                        );
                return cursor;
            default:
                throw new UnsupportedOperationException("Uri problem: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int id = mUriMatcher.match(uri);

        switch (id){
            case CONTENT_ALL_DATA:{
                return MoviesContractDB.MovieEntry.CONTENT_ALL_DATA;
            }
            case CONTENT_ITEM_DATA:{
                return MoviesContractDB.MovieEntry.CONTENT_ITEM_DATA;
            }
            default:{
                throw new UnsupportedOperationException("Uri problem: " + uri);
            }
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase movieDB = mMoviesDataBaseHelper.getWritableDatabase();
        Uri uriAux;
        switch (mUriMatcher.match(uri)) {
            case CONTENT_ALL_DATA: {
                long _id = movieDB.insert(MoviesContractDB.MovieEntry.TABLE, null, contentValues);
                if (_id > 0) {
                    Log.d(TAG, "insert _ID: " + _id);
                    uriAux = MoviesContractDB.MovieEntry.buildMoviesUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

            default: {
                throw new UnsupportedOperationException("Uri problem: " + uri);

            }
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return uriAux;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase movieDB = mMoviesDataBaseHelper.getWritableDatabase();
        final int uriMatch = mUriMatcher.match(uri);
        int idDeleted;
        switch(uriMatch){
            case CONTENT_ALL_DATA:
                idDeleted = movieDB.delete(MoviesContractDB.MovieEntry.TABLE, selection, selectionArgs);
                movieDB.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
                        + MoviesContractDB.MovieEntry.CONTENT_ALL_DATA + "'");
                Log.d(TAG, "CONTENT_ALL_DATA\ndelete _ID: " + idDeleted);
                break;
            case CONTENT_ITEM_DATA:
                idDeleted = movieDB.delete(MoviesContractDB.MovieEntry.TABLE,
                        MoviesContractDB.MovieEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                movieDB.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        MoviesContractDB.MovieEntry.TABLE + "'");
                Log.d(TAG, "CONTENT_ITEM_DATA\ndelete _ID: " + idDeleted);
                break;
            default:
                throw new UnsupportedOperationException("Uri problem: " + uri);
        }
        return idDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase movieDB = mMoviesDataBaseHelper.getWritableDatabase();
        int idUpdated;

        if (contentValues == null){
            throw new IllegalArgumentException("Cannot have null content values");
        }

        switch(mUriMatcher.match(uri)){
            case CONTENT_ALL_DATA:{
                idUpdated = movieDB.update(MoviesContractDB.MovieEntry.TABLE,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            }
            case CONTENT_ITEM_DATA: {
                idUpdated = movieDB.update(MoviesContractDB.MovieEntry.TABLE,
                        contentValues,
                        MoviesContractDB.MovieEntry._ID + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            default:{
                throw new UnsupportedOperationException("Problem uri: " + uri);
            }
        }
        if (idUpdated > 0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        Log.d(TAG, "update _ID: " + idUpdated);
        return idUpdated;
    }

    private static UriMatcher buildUriMatcher(){
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MoviesContractDB.CONTENT_AUTHORITY, MoviesContractDB.MovieEntry.TABLE,CONTENT_ALL_DATA);
        uriMatcher.addURI(MoviesContractDB.CONTENT_AUTHORITY, MoviesContractDB.MovieEntry.TABLE + "/#",CONTENT_ITEM_DATA);
        return uriMatcher;
    }
}
