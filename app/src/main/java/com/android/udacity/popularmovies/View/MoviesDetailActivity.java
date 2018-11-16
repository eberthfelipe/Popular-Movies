package com.android.udacity.popularmovies.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.LinearLayout;
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
    private LinearLayout mLinearLayoutNoInternet;
    private MenuItem mShareMenu;
    private boolean isFavorite = false;
    private DatabasePresenter mDatabasePresenter;
    private MovieDetailPresenter mMovieDetailPresenter;
    private Movie mMovie;
    private int mUserPreference;
    private final String MOVIE_FAVORITE = "movie_favorite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movies);
        mDatabasePresenter = new DatabasePresenter(this);
        mMovieDetailPresenter = new MovieDetailPresenter(this);
        init();
        //DONE: feature: implement onSavedInstance and onRestore for MoviesDetail view
        if(savedInstanceState == null || savedInstanceState.isEmpty()){
            setData(getIntent());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(!savedInstanceState.isEmpty()){
            mMovie = (Movie) savedInstanceState.get(MoviesActivity.MOVIE_OBJECT);
            setMovieInfo();
            setMovieDetail(mMovie.getMovieDetail());
            mUserPreference = savedInstanceState.getInt(MoviesActivity.USER_PREFERENCE);
            loadMovieImage();
            isFavorite = savedInstanceState.getBoolean(MOVIE_FAVORITE);
            hideProgress();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        MovieDetail movieDetail;
        if(mMovie != null && mRecyclerViewVideo != null && mRecyclerViewReview.getAdapter() != null){
            movieDetail = new MovieDetail(getGridAdapterVideo().getMovieVideos(), getGridAdapterReview().getMovieReviews());
            mMovie.setMovieDetail(movieDetail);
            outState.putParcelable(MoviesActivity.MOVIE_OBJECT, mMovie);
            outState.putInt(MoviesActivity.USER_PREFERENCE, mUserPreference);
            outState.putBoolean(MOVIE_FAVORITE, isFavorite);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("isFavorite", isFavorite);
        intent.putExtra("movieID",mMovie.getId());
        setResult(Activity.CONTEXT_INCLUDE_CODE, intent);
        finish();
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
        mShareMenu = menu.getItem(1);
        if(validateGridAdapterVideo()){
            mShareMenu.setVisible(true);
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
                if(validateGridAdapterVideo()){
                    String movieKey = getGridAdapterVideo().getMovieKey(0);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, mMovie.getTitle() + "\nhttp://m.youtube.com/watch?v="+movieKey);
                    startActivity(intent);
                }
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

        // layout which shows: unavailable internet
        mLinearLayoutNoInternet = findViewById(R.id.ll_unavailable_internet_detail);

        // layout manager of recycler view
        LinearLayoutManager layoutManagerVideo = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManagerReview = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewVideo.setLayoutManager(layoutManagerVideo);
        mRecyclerViewReview.setLayoutManager(layoutManagerReview);
    }

    private void setData(Intent intent) {
        mMovie = intent.getParcelableExtra(MoviesActivity.MOVIE_OBJECT);
        setMovieInfo();

        //get user preference
        mUserPreference = intent.getIntExtra(MoviesActivity.USER_PREFERENCE, 0);
        loadMovieImage();

        //Is this movie favorite?
        isFavorite = isMovieFavorite(mMovie.getId());

        //load trailers and reviews
        mMovieDetailPresenter.fetchTrailerAndReviewFromMovieDB(mMovie.getId());
    }

    private void setMovieInfo(){
        mTextViewMovieTitle.setText(mMovie.getTitle());
        mTextViewMovieReleaseDate.setText(parseDate(mMovie.getReleaseDate()));
        mTextViewMovieAverage.setText(String.valueOf(mMovie.getVoteAverage()));
        mTextViewMovieDescription.setText(mMovie.getOverview());
        mTextViewMovieDescription.setMovementMethod(new ScrollingMovementMethod()); //make text view scrollable
    }

    /**
     *  mUserpreference changes how picasso will load images
     *  values 0 and 1 load images from web
     *  value 2 loads images from local database
     */
    private void loadMovieImage(){
        if(mUserPreference == 2){
            mMovieDetailPresenter.retrieveImageSrc(mMovie.getPosterPath(), mImageViewMoviePoster, PicassoModelSingleton.TYPE_FILE);
        } else {
            mMovieDetailPresenter.retrieveImageSrc(mMovie.getPosterPath(), mImageViewMoviePoster, PicassoModelSingleton.TYPE_POSTER);
        }
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
        if(show){
            mLinearLayoutNoInternet.setVisibility(View.VISIBLE);
            hideProgress();
        } else {
            mLinearLayoutNoInternet.setVisibility(View.GONE);
            showProgress();
        }
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
    public void enableShareMenuOption() {
        if(mShareMenu != null)
            mShareMenu.setVisible(true);
    }

    @Override
    public void onListItemClick(int listItemIndex) {
        if(validateGridAdapterVideo()){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.youtube.com/watch?v="+getGridAdapterVideo().getMovieKey(listItemIndex)));
            startActivity(intent);
        }
    }
    //endregion

    public boolean validateGridAdapterVideo(){
        GridAdapterVideo gridAdapterVideo = getGridAdapterVideo();
        return gridAdapterVideo != null && gridAdapterVideo.getItemCount() > 0;
    }

    public GridAdapterVideo getGridAdapterVideo(){
        return (GridAdapterVideo) mRecyclerViewVideo.getAdapter();
    }

    public GridAdapterReview getGridAdapterReview(){
        return (GridAdapterReview) mRecyclerViewReview.getAdapter();
    }
}
