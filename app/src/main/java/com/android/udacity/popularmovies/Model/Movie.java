package com.android.udacity.popularmovies.Model;

import android.util.Log;

import com.android.udacity.popularmovies.Utils.MovieDatabaseJsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {
    private static final String TAG = Movie.class.getName();
    private int id, vote_count;
    private String title, poster_path, original_language, original_title, backdrop_path, overview, release_date;
    private boolean video, adult;
    private double vote_average, popularity;

    public Movie(){}

    public Movie(int id, int vote_count, String title, String poster_path, String original_language, String original_title, String backdrop_path, String overview, String release_date, boolean video, boolean adult, double vote_average, double popularity) {
        this.id = id;
        this.vote_count = vote_count;
        this.title = title;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
        this.video = video;
        this.adult = adult;
        this.vote_average = vote_average;
        this.popularity = popularity;

        Log.d(TAG, "Movie parse JSON: /n" + this.toString());
    }

    public Movie(JSONObject objJsonAux) {
        try {
            this.id = objJsonAux.getInt(MovieDatabaseJsonUtils.JSON_ID);
            this.vote_count = objJsonAux.getInt(MovieDatabaseJsonUtils.JSON_VOTE_COUNT);
            this.video = objJsonAux.getBoolean(MovieDatabaseJsonUtils.JSON_VIDEO);
            this.adult = objJsonAux.getBoolean(MovieDatabaseJsonUtils.JSON_ADULT);
            this.vote_average = objJsonAux.getDouble(MovieDatabaseJsonUtils.JSON_VOTE_AVERAGE);
            this.popularity = objJsonAux.getDouble(MovieDatabaseJsonUtils.JSON_POPULARITY);
            this.poster_path = objJsonAux.getString(MovieDatabaseJsonUtils.JSON_POSTER_PATH);
            this.title = objJsonAux.getString(MovieDatabaseJsonUtils.JSON_TITLE);
            this.original_title = objJsonAux.getString(MovieDatabaseJsonUtils.JSON_ORIGINAL_TITLE);
            this.original_language = objJsonAux.getString(MovieDatabaseJsonUtils.JSON_ORIGINAL_LANGUAGE);
            this.backdrop_path = objJsonAux.getString(MovieDatabaseJsonUtils.JSON_BACKDROP_PATH);
            this.overview = objJsonAux.getString(MovieDatabaseJsonUtils.JSON_OVERVIEW);
            this.release_date = objJsonAux.getString(MovieDatabaseJsonUtils.JSON_RELEASE_DATE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", vote_count=" + vote_count +
                ", title='" + title + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", original_language='" + original_language + '\'' +
                ", original_title='" + original_title + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                ", video=" + video +
                ", adult=" + adult +
                ", vote_average=" + vote_average +
                ", popularity=" + popularity +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }
}

