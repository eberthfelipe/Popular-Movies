package com.android.udacity.popularmovies.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Model.Movie;
import com.android.udacity.popularmovies.Model.PicassoModelSingleton;
import com.android.udacity.popularmovies.Presenter.DatabasePresenter;
import com.android.udacity.popularmovies.R;

import java.util.Objects;

public class MoviesDetailActivity extends AppCompatActivity implements MovieContract.View{

    private TextView mTextViewMovieTitle;
    private TextView mTextViewMovieReleaseDate;
    private TextView mTextViewMovieAverage;
    private TextView mTextViewMovieDescription;
    private ImageView mImageViewMoviePoster;
    private boolean isFavorite = false;
    private DatabasePresenter mDatabasePresenter;
    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movies);
        mDatabasePresenter = new DatabasePresenter(this);
        init();
        setData(getIntent());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //region Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.getItem(0);
        if(isFavorite){
            item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO: insert or delete from database with images. Handle it in another thread
        switch (item.getItemId()){
            case R.id.menu_favorite:
                if(isFavorite){
                    item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_not_favorite));
                    isFavorite = false;
                    mDatabasePresenter.deleteFavoriteMovie(mMovie.getId());
                } else {
                    item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
                    isFavorite = true;
                    mDatabasePresenter.insertNewFavorite(mMovie);
                }
                break;
            case R.id.menu_share:
                Toast.makeText(this,"Sharing...", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    //endregion

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
        mMovie = intent.getParcelableExtra(MoviesActivity.MOVIE_OBJECT);
        mTextViewMovieTitle.setText(mMovie.getTitle());
        mTextViewMovieReleaseDate.setText(parseDate(mMovie.getReleaseDate()));
        mTextViewMovieAverage.setText(String.valueOf(mMovie.getVoteAverage()));
        mTextViewMovieDescription.setText(mMovie.getOverview());
        mTextViewMovieDescription.setMovementMethod(new ScrollingMovementMethod()); //make text view scrollable

        //DONE: Fix load poster image bug
        PicassoModelSingleton picassoModel = PicassoModelSingleton.getInstance();
        picassoModel.retrieveImageSrc(this, mMovie.getPosterPath(), mImageViewMoviePoster);

        //Is this movie favorite?
        isFavorite = isMovieFavorite(mMovie.getId());
    }

    //Method to get only the year of movies
    private String parseDate(String date){
        return date.substring(0,4);
    }

    private boolean isMovieFavorite(int movieId){
        return mDatabasePresenter.isMovieAlreadyInserted(movieId);
    }

    //region View Interface
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showNoInternetConnection(boolean show) {
        //TODO: implement NoInternetConnection for detail view
    }
    //endregion
}
