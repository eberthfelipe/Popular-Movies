package com.android.udacity.popularmovies.Object;

public class MovieDetail {
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
}
