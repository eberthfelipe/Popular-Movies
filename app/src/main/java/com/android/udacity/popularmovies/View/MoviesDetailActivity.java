package com.android.udacity.popularmovies.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Object.Movie;
import com.android.udacity.popularmovies.Model.PicassoModelSingleton;
import com.android.udacity.popularmovies.Object.MovieDetail;
import com.android.udacity.popularmovies.Object.MovieReview;
import com.android.udacity.popularmovies.Object.MovieVideo;
import com.android.udacity.popularmovies.Presenter.DatabasePresenter;
import com.android.udacity.popularmovies.Presenter.MovieDetailPresenter;
import com.android.udacity.popularmovies.R;

import java.util.Objects;

public class MoviesDetailActivity extends AppCompatActivity implements MovieContract.DetailView, MovieContract.ListItemClickListener {

    private TextView mTextViewMovieTitle;
    private TextView mTextViewMovieReleaseDate;
    private TextView mTextViewMovieAverage;
    private TextView mTextViewMovieDescription;
    private ImageView mImageViewMoviePoster;
    private RecyclerView mRecyclerViewVideo;
    private RecyclerView mRecyclerViewReview;
    private ProgressBar mProgressBarVideos;
    private ProgressBar mProgressBarReviews;
    private boolean isFavorite = false;
    private DatabasePresenter mDatabasePresenter;
    private MovieDetailPresenter mMovieDetailPresenter;
    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movies);
        mDatabasePresenter = new DatabasePresenter(this);
        mMovieDetailPresenter = new MovieDetailPresenter(this);
        init();
        //TODO: feature: implement onSavedInstance and onRestore
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

        // loaders for Video and Review
        mRecyclerViewVideo = findViewById(R.id.rv_movie_video_list);
        mRecyclerViewReview = findViewById(R.id.rv_movie_review_list);
        mProgressBarVideos = findViewById(R.id.pb_loading_videos);
        mProgressBarReviews = findViewById(R.id.pb_loading_reviews);

        // layout manager of recycler view
        LinearLayoutManager layoutManagerVideo = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManagerReview = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewVideo.setLayoutManager(layoutManagerVideo);
        mRecyclerViewReview.setLayoutManager(layoutManagerReview);
    }

    private void setData(Intent intent) {
        mMovie = intent.getParcelableExtra(MoviesActivity.MOVIE_OBJECT);
        mTextViewMovieTitle.setText(mMovie.getTitle());
        mTextViewMovieReleaseDate.setText(parseDate(mMovie.getReleaseDate()));
        mTextViewMovieAverage.setText(String.valueOf(mMovie.getVoteAverage()));
        mTextViewMovieDescription.setText(mMovie.getOverview());
        mTextViewMovieDescription.setMovementMethod(new ScrollingMovementMethod()); //make text view scrollable

        //DONE: Fix load poster image bug
        mMovieDetailPresenter.retrieveImageSrc(mMovie.getPosterPath(), mImageViewMoviePoster, PicassoModelSingleton.TYPE_POSTER);

        //Is this movie favorite?
        isFavorite = isMovieFavorite(mMovie.getId());

        //load trailers and reviews
        mMovieDetailPresenter.fetchTrailerAndReviewFromMovieDB(mMovie.getId());
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
    public void showProgress() {
        mProgressBarVideos.setVisibility(View.VISIBLE);
        mProgressBarReviews.setVisibility(View.VISIBLE);
        mRecyclerViewVideo.setVisibility(View.GONE);
        mRecyclerViewReview.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mProgressBarVideos.setVisibility(View.GONE);
        mProgressBarReviews.setVisibility(View.GONE);
        mRecyclerViewVideo.setVisibility(View.VISIBLE);
        mRecyclerViewReview.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoInternetConnection(boolean show) {
        //TODO: implement NoInternetConnection for detail view
    }



    @Override
    public void setMovieDetail(MovieDetail movieDetail) {
        GridAdapterVideo gridAdapterVideo = null;
        GridAdapterReview gridAdapterReview = null;
        if(movieDetail != null){
            mMovie.setMovieDetail(movieDetail);
            if(movieDetail.getMovieVideos() != null && movieDetail.getMovieReviews() != null){
                gridAdapterVideo = new GridAdapterVideo(movieDetail.getMovieVideos(), mMovieDetailPresenter, this);
                gridAdapterReview = new GridAdapterReview(movieDetail.getMovieReviews(), this);
            } else {
                gridAdapterVideo = new GridAdapterVideo(new MovieVideo[]{}, mMovieDetailPresenter,this);
                gridAdapterReview = new GridAdapterReview(new MovieReview[]{}, this);
                Toast.makeText(this, R.string.try_again, Toast.LENGTH_LONG).show();
            }
        }
        mRecyclerViewVideo.setAdapter(gridAdapterVideo);
        mRecyclerViewReview.setAdapter(gridAdapterReview);
    }

    @Override
    public void onListItemClick(int listItemIndex) {
        Toast.makeText(this, "DO NOTHING!!!!!!", Toast.LENGTH_LONG).show();
    }
    //endregion
}
