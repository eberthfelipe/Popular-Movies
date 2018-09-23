package com.android.udacity.popularmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.android.udacity.popularmovies.Utils.MovieDatabaseJsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie implements Parcelable{
    private int id, vote_count;
    private String title, poster_path, original_language, original_title, backdrop_path, overview, release_date;
    private boolean video, adult;
    private double vote_average, popularity;
    //Done: Add imageView object to represent the poster of movie
    private ImageView movie_poster;

    public Movie(@NonNull JSONObject objJsonAux) {
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
            this.movie_poster = null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Movie(Movie movie) {
        this.id = movie.id;
        this.vote_count = movie.vote_count;
        this.title = movie.title;
        this.poster_path = movie.poster_path;
        this.original_language = movie.original_language;
        this.original_title = movie.original_title;
        this.backdrop_path = movie.backdrop_path;
        this.overview = movie.overview;
        this.release_date = movie.release_date;
        this.video = movie.video;
        this.adult = movie.adult;
        this.vote_average = movie.vote_average;
        this.popularity = movie.popularity;
        this.movie_poster = movie.movie_poster;
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

    public ImageView getMovie_poster() {
        return movie_poster;
    }

    public void setMovie_poster(ImageView movie_poster) {
        this.movie_poster = movie_poster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.poster_path);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.overview);
        dest.writeDouble(this.vote_average);
        dest.writeString(this.release_date);
    }

    private Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.poster_path = in.readString();
        this.backdrop_path = in.readString();
        this.overview = in.readString();
        this.vote_average = in.readDouble();
        this.release_date = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}

