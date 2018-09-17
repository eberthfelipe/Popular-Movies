package com.android.udacity.popularmovies.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.udacity.popularmovies.Model.Movie;
import com.android.udacity.popularmovies.R;

public class MoviesDetailActivity extends AppCompatActivity {

    private TextView mTextViewMovieTitle, mTextViewMovieReleaseDate, mTextViewMovieAverage, mTextViewMovieDescription;
    private ImageView mImageViewMoviewPoster;

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
        mImageViewMoviewPoster = findViewById(R.id.iv_movie_poster_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setData(Intent intent) {
        Movie movie = intent.getParcelableExtra(MoviesActivity.MOVIE_OBJECT);
//        byte[] byteArrayPosterImage = intent.getByteArrayExtra(MoviesActivity.MOVIE_IMAGE);

        mTextViewMovieTitle.setText(movie.getTitle());
        mTextViewMovieReleaseDate.setText(movie.getRelease_date());
        mTextViewMovieAverage.setText(String.valueOf(movie.getVote_average()));
        mTextViewMovieDescription.setText(movie.getOverview());
//TODO: Fix load poster image bug
//        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayPosterImage, 0, byteArrayPosterImage.length);
//        mImageViewMoviewPoster.setImageBitmap(bitmap);
    }

}
