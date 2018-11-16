package com.android.udacity.popularmovies.Utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.udacity.popularmovies.Object.Movie;
import com.android.udacity.popularmovies.Object.MovieReview;
import com.android.udacity.popularmovies.Object.MovieVideo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDatabaseJsonUtils {
    private static final String TAG = MovieDatabaseJsonUtils.class.getName();

    //Movie fields
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

    //MovieVideo and MovieReview fields
    private static final String JSON_ISO_639_1 = "iso_639_1";
    private static final String JSON_ISO_3166_1 = "iso_3166_1";
    private static final String JSON_KEY = "key";
    private static final String JSON_NAME = "name";
    private static final String JSON_SITE = "site";
    private static final String JSON_SIZE = "size";
    private static final String JSON_TYPE = "type";
    private static final String JSON_AUTHOR = "author";
    private static final String JSON_CONTENT = "content";
    private static final String JSON_URL = "url";


    /*
    @param retrieveJsonString JSON response from server
    @return Movies and its data
     */
    public static ArrayList<Movie> getMovieDatabasePopularList(@NonNull String retrieveJsonString){
        final String MOVIE_DATABASE_API_PAGE = "page";
        final String MOVIE_DATABASE_API_TOTAL_RESULTS = "total_results";
        final String MOVIE_DATABASE_API_TOTAL_PAGES = "total_pages";
        final String MOVIE_DATABASE_API_RESULTS = "results";
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

    /*
    @param retrieveJsonString JSON response from server
    @return MovieVideos and its data
     */
    public static MovieVideo[] getMovieDatabaseVideo(@NonNull String retrieveJsonString){
        final String MOVIE_DATABASE_API_RESULTS = "results";
        MovieVideo[] arrayMovieVideos = new MovieVideo[0];
        try {
            JSONObject objJson = new JSONObject(retrieveJsonString);
            if(objJson.has(MOVIE_DATABASE_API_RESULTS)){
                JSONArray resultsJsonArray = objJson.getJSONArray(MOVIE_DATABASE_API_RESULTS);
                JSONObject objJsonAux;
                arrayMovieVideos = new MovieVideo[resultsJsonArray.length()];
                for(int i = 0; i < resultsJsonArray.length(); i++){
                    objJsonAux = resultsJsonArray.getJSONObject(i);
                    Log.d(TAG, "getMovieDatabaseVideo: " + objJsonAux.toString());
                    arrayMovieVideos[i] = buildMovieVideo(objJsonAux);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayMovieVideos;
    }

    /*
    @param retrieveJsonString JSON response from server
    @return MovieReviews and its data
     */
    public static MovieReview[] getMovieDatabaseReview(@NonNull String retrieveJsonString){
        final String MOVIE_DATABASE_API_RESULTS = "results";
        MovieReview[] arrayMovieReview = new MovieReview[0];
        try {
            JSONObject objJson = new JSONObject(retrieveJsonString);
            if(objJson.has(MOVIE_DATABASE_API_RESULTS)){
                JSONArray resultsJsonArray = objJson.getJSONArray(MOVIE_DATABASE_API_RESULTS);
                JSONObject objJsonAux;
                arrayMovieReview = new MovieReview[resultsJsonArray.length()];
                for(int i = 0; i < resultsJsonArray.length(); i++){
                    objJsonAux = resultsJsonArray.getJSONObject(i);
                    Log.d(TAG, "getMovieDatabaseReview: " + objJsonAux.toString());
                    arrayMovieReview[i] = buildMovieReview(objJsonAux);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayMovieReview;
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

    private static MovieVideo buildMovieVideo(JSONObject objJsonAux) {
        MovieVideo movieVideo = new MovieVideo();
        try {
            movieVideo.setId(objJsonAux.getString(MovieDatabaseJsonUtils.JSON_ID));
            movieVideo.setIso_639_1(objJsonAux.getString(MovieDatabaseJsonUtils.JSON_ISO_639_1));
            movieVideo.setIso_3166_1(objJsonAux.getString(MovieDatabaseJsonUtils.JSON_ISO_3166_1));
            movieVideo.setKey(objJsonAux.getString(MovieDatabaseJsonUtils.JSON_KEY));
            movieVideo.setName(objJsonAux.getString(MovieDatabaseJsonUtils.JSON_NAME));
            movieVideo.setSite(objJsonAux.getString(MovieDatabaseJsonUtils.JSON_SITE));
            movieVideo.setSize(objJsonAux.getInt(MovieDatabaseJsonUtils.JSON_SIZE));
            movieVideo.setType(objJsonAux.getString(MovieDatabaseJsonUtils.JSON_TYPE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieVideo;
    }

    private static MovieReview buildMovieReview(JSONObject objJsonAux) {
        MovieReview movieReview = new MovieReview();
        try {
            movieReview.setId(objJsonAux.getString(MovieDatabaseJsonUtils.JSON_ID));
            movieReview.setAuthor(objJsonAux.getString(MovieDatabaseJsonUtils.JSON_AUTHOR));
            movieReview.setContent(objJsonAux.getString(MovieDatabaseJsonUtils.JSON_CONTENT));
            movieReview.setUrl(objJsonAux.getString(MovieDatabaseJsonUtils.JSON_URL));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieReview;
    }
}
