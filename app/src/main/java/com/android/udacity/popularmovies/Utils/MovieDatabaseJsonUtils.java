package com.android.udacity.popularmovies.Utils;

import android.support.annotation.NonNull;

import com.android.udacity.popularmovies.Model.Movie;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieDatabaseJsonUtils {
    public static final String JSON_VOTE_COUNT = "vote_count";
    public static final String JSON_ID = "id";
    public static final String JSON_VIDEO = "video";
    public static final String JSON_VOTE_AVERAGE = "vote_average";
    public static final String JSON_TITLE = "title";
    public static final String JSON_POPULARITY = "popularity";
    public static final String JSON_POSTER_PATH = "poster_path";
    public static final String JSON_ORIGINAL_LANGUAGE = "original_language";
    public static final String JSON_ORIGINAL_TITLE = "original_title";
    public static final String JSON_BACKDROP_PATH = "backdrop_path";
    public static final String JSON_ADULT = "adult";
    public static final String JSON_OVERVIEW = "overview";
    public static final String JSON_RELEASE_DATE = "release_date";


    /*
    @param retrieveJsonString JSON response from server
    @return Movies and its data
     */
    public static Movie getMovieDatabasePopularList(@NonNull String retrieveJsonString){
        try {
            JSONObject objJson = new JSONObject(retrieveJsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
