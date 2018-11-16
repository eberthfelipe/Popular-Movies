package com.android.udacity.popularmovies.Data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Based on udacity/android-content-provider
 * https://github.com/udacity/android-content-provider
 */
public class MoviesContractDB {

    public static final String CONTENT_AUTHORITY = "com.android.udacity.popularmovies.provider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class MovieEntry implements BaseColumns{
        //table name
        public static final String TABLE = "movies";
        //columns
        public static final String _ID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_IMAGE_PATH = "imgPath";

        //content uri
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE)
                .build();

        //cursor
        public static final String CONTENT_ALL_DATA = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/" + CONTENT_AUTHORITY
                + "/" + TABLE;

        public static final String CONTENT_ITEM_DATA = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + CONTENT_AUTHORITY
                + "/" + TABLE;

        public static Uri buildMoviesUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
