package com.android.udacity.popularmovies.Utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.udacity.popularmovies.Object.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDatabaseJsonUtils {
    private static final String TAG = MovieDatabaseJsonUtils.class.getName();

    private static final String JSON_VOTE_COUNT = "vote_count";
    private static final String JSON_ID = "id";
    private static final String JSON_VIDEO = "video";
    private static final String JSON_VOTE_AVERAGE = "vote_average";
    private static final String JSON_TITLE = "title";
    private static final String JSON_POPULARITY = "popularity";
    private static final String JSON_POSTER_PATH = "poster_path";
    private static final String JSON_ORIGINAL_LANGUAGE = "original_language";
    private static final String JSON_ORIGINAL_TITLE = "original_title";
    private static final String JSON_BACKDROP_PATH = "backdrop_path";
    private static final String JSON_ADULT = "adult";
    private static final String JSON_OVERVIEW = "overview";
    private static final String JSON_RELEASE_DATE = "release_date";


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
                    movieArrayList.add(buildMovie(objJsonAux));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieArrayList;
    }

    private static Movie buildMovie(JSONObject objJsonAux){
        Movie movie = new Movie();
        try {
            movie.setId(objJsonAux.getInt(MovieDatabaseJsonUtils.JSON_ID));
            movie.setVoteCount(objJsonAux.getInt(MovieDatabaseJsonUtils.JSON_VOTE_COUNT));
            movie.setVideo(objJsonAux.getBoolean(MovieDatabaseJsonUtils.JSON_VIDEO));
            movie.setAdult(objJsonAux.getBoolean(MovieDatabaseJsonUtils.JSON_ADULT));
            movie.setVoteAverage(objJsonAux.getDouble(MovieDatabaseJsonUtils.JSON_VOTE_AVERAGE));
            movie.setPopularity(objJsonAux.getDouble(MovieDatabaseJsonUtils.JSON_POPULARITY));
            movie.setPosterPath(objJsonAux.getString(MovieDatabaseJsonUtils.JSON_POSTER_PATH));
            movie.setTitle(objJsonAux.getString(MovieDatabaseJsonUtils.JSON_TITLE));
            movie.setOriginalTitle(objJsonAux.getString(MovieDatabaseJsonUtils.JSON_ORIGINAL_TITLE));
            movie.setOriginalLanguage(objJsonAux.getString(MovieDatabaseJsonUtils.JSON_ORIGINAL_LANGUAGE));
            movie.setBackdropPath(objJsonAux.getString(MovieDatabaseJsonUtils.JSON_BACKDROP_PATH));
            movie.setOverview(objJsonAux.getString(MovieDatabaseJsonUtils.JSON_OVERVIEW));
            movie.setReleaseDate(objJsonAux.getString(MovieDatabaseJsonUtils.JSON_RELEASE_DATE));
            movie.setMoviePoster(null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;
    }
}
