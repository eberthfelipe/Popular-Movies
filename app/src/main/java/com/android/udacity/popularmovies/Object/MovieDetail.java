package com.android.udacity.popularmovies.Object;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieDetail implements Parcelable {
    private MovieVideo[] movieVideos;
    private MovieReview[] movieReviews;

    public MovieDetail(MovieVideo[] movieVideos, MovieReview[] movieReviews) {
        this.movieVideos = movieVideos;
        this.movieReviews = movieReviews;
    }

    public MovieVideo[] getMovieVideos() {
        return movieVideos;
    }

    public void setMovieVideos(MovieVideo[] movieVideos) {
        this.movieVideos = movieVideos;
    }

    public MovieReview[] getMovieReviews() {
        return movieReviews;
    }

    public void setMovieReviews(MovieReview[] movieReviews) {
        this.movieReviews = movieReviews;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedArray(movieVideos, i);
        parcel.writeTypedArray(movieReviews, i);
    }

    protected MovieDetail(Parcel in) {
        movieVideos = in.createTypedArray(MovieVideo.CREATOR);
        movieReviews = in.createTypedArray(MovieReview.CREATOR);
    }

    public static final Creator<MovieDetail> CREATOR = new Creator<MovieDetail>() {
        @Override
        public MovieDetail createFromParcel(Parcel in) {
            return new MovieDetail(in);
        }

        @Override
        public MovieDetail[] newArray(int size) {
            return new MovieDetail[size];
        }
    };

}
