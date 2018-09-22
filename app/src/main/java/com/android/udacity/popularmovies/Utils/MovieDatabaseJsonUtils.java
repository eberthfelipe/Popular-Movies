package com.android.udacity.popularmovies.Utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.udacity.popularmovies.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDatabaseJsonUtils {
    private static final String TAG = MovieDatabaseJsonUtils.class.getName();

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
    public static ArrayList<Movie> getMovieDatabasePopularList(@NonNull String retrieveJsonString){
        final String MOVIE_DATABASE_API_PAGE = "page";
        final String MOVIE_DATABASE_API_TOTAL_RESULTS = "total_results";
        final String MOVIE_DATABASE_API_TOTAL_PAGES = "total_pages";
        final String MOVIE_DATABASE_API_RESULTS = "results";
        Movie movie;
        ArrayList<Movie> movieArrayList = new ArrayList<>();

        try {
            // Done: return a Movie object after parse all JSON object
            JSONObject objJson = new JSONObject(retrieveJsonString);
            if(objJson.has(MOVIE_DATABASE_API_PAGE)
                && objJson.has(MOVIE_DATABASE_API_TOTAL_PAGES)
                && objJson.has(MOVIE_DATABASE_API_TOTAL_RESULTS)){

                JSONArray resultsJsonArray = objJson.getJSONArray(MOVIE_DATABASE_API_RESULTS);
                JSONObject objJsonAux;
                for (int i = 0; i < resultsJsonArray.length(); i++) {
                    objJsonAux = resultsJsonArray.getJSONObject(i);
                    Log.d(TAG, "getMovieDatabasePopularList: " + objJsonAux.toString());
                    movie = new Movie(objJsonAux);
                    movieArrayList.add(movie);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieArrayList;
    }
}
