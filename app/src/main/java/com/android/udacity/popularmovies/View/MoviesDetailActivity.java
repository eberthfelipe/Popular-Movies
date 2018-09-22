package com.android.udacity.popularmovies.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.udacity.popularmovies.Model.Movie;
import com.android.udacity.popularmovies.Presenter.MoviesPresenter;
import com.android.udacity.popularmovies.R;

import java.util.Objects;

public class MoviesDetailActivity extends AppCompatActivity {

    private TextView mTextViewMovieTitle, mTextViewMovieReleaseDate, mTextViewMovieAverage, mTextViewMovieDescription;
    private ImageView mImageViewMoviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movies);
        init();
        setData(getIntent());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void init() {
        mTextViewMovieTitle = findViewById(R.id.tv_movie_title_detail);
        mTextViewMovieReleaseDate = findViewById(R.id.tv_release_date);
        mTextViewMovieAverage = findViewById(R.id.tv_average);
        mTextViewMovieDescription = findViewById(R.id.tv_movie_description);
        mImageViewMoviePoster = findViewById(R.id.iv_movie_poster_detail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setData(Intent intent) {
        MoviesPresenter moviesPresenter = new MoviesPresenter();
        Movie movie = intent.getParcelableExtra(MoviesActivity.MOVIE_OBJECT);

        mTextViewMovieTitle.setText(movie.getTitle());
        mTextViewMovieReleaseDate.setText(parseDate(movie.getRelease_date()));
        mTextViewMovieAverage.setText(String.valueOf(movie.getVote_average()));
        mTextViewMovieDescription.setText(movie.getOverview());
        mTextViewMovieDescription.setMovementMethod(new ScrollingMovementMethod()); //make text view scrollable
        //DONE: Fix load poster image bug
        moviesPresenter.retrieveImageSrc(this, movie.getPoster_path(), mImageViewMoviePoster);
    }

    //Method to get only the year of movies
    private String parseDate(String date){
        return date.substring(0,4);
    }

}
