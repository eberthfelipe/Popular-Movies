package com.android.udacity.popularmovies.Object;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

import java.util.Objects;

import static com.android.udacity.popularmovies.Object.MovieDetail.*;
import static com.android.udacity.popularmovies.Object.MovieDetail.CREATOR;

public class Movie implements Parcelable{
    private int id;
    private int voteCount;
    private String title;
    private String posterPath;
    private String originalLanguage;
    private String originalTitle;
    private String backdropPath;
    private String overview;
    private String releaseDate;
    private boolean video;
    private boolean adult;
    private double voteAverage;
    private double popularity;
    //Done: Add imageView object to represent the poster of movie
    private ImageView moviePoster;
    //class contains movie's videos and reviews
    private MovieDetail movieDetail;

    public Movie() {
        this.id = 0;
        this.voteCount = 0;
        this.title = "";
        this.posterPath = "";
        this.originalLanguage = "";
        this.originalTitle = "";
        this.backdropPath = "";
        this.overview = "";
        this.releaseDate = "";
        this.video = false;
        this.adult = false;
        this.voteAverage = 0.0;
        this.popularity = 0.0;
        this.moviePoster = null;
    }

    public Movie(Movie movie) {
        this.id = movie.id;
        this.voteCount = movie.voteCount;
        this.title = movie.title;
        this.posterPath = movie.posterPath;
        this.originalLanguage = movie.originalLanguage;
        this.originalTitle = movie.originalTitle;
        this.backdropPath = movie.backdropPath;
        this.overview = movie.overview;
        this.releaseDate = movie.releaseDate;
        this.video = movie.video;
        this.adult = movie.adult;
        this.voteAverage = movie.voteAverage;
        this.popularity = movie.popularity;
        this.moviePoster = movie.moviePoster;
        this.movieDetail = movie.movieDetail;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", voteCount=" + voteCount +
                ", title='" + title + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", video=" + video +
                ", adult=" + adult +
                ", voteAverage=" + voteAverage +
                ", popularity=" + popularity +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Movie movie = (Movie) object;
        return id == movie.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
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

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public ImageView getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(ImageView moviePoster) {
        this.moviePoster = moviePoster;
    }

    public MovieDetail getMovieDetail() {
        return movieDetail;
    }

    public void setMovieDetail(MovieDetail movieDetail) {
        this.movieDetail = movieDetail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeString(this.overview);
        dest.writeDouble(this.voteAverage);
        dest.writeString(this.releaseDate);
        dest.writeTypedObject(this.movieDetail, flags);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.overview = in.readString();
        this.voteAverage = in.readDouble();
        this.releaseDate = in.readString();
        this.movieDetail = in.readTypedObject(MovieDetail.CREATOR);
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}

