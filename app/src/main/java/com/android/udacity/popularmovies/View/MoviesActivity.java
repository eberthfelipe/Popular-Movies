package com.android.udacity.popularmovies.View;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Object.Movie;
import com.android.udacity.popularmovies.Presenter.MoviesPresenter;
import com.android.udacity.popularmovies.R;

import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity implements MovieContract.ActivityView, MovieContract.View, MovieContract.ListItemClickListener{

    public static final String MOVIE_OBJECT = "movie_object";
    public static final String USER_PREFERENCE = "user_preference";
    public static final String RECYCLER_POSITION = "recycler_position";
    // 0 = POPULAR | 1 = TOP_RATED | 2 = FAVORITES
    public static final int PREFERENCE_POPULAR = 0;
    public static final int PREFERENCE_TOP_RATED = 1;
    public static final int PREFERENCE_FAVORITES = 2;
    private final int MY_PERMISSIONS_INTERNET = 0;
    private MoviesPresenter mMoviesPresenter;
    //    private ProgressBar mLoadingMoviesProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private Parcelable mListState;
    private LinearLayout mLinearLayoutNoInternet;
    private int mUserPreference = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoviesPresenter = new MoviesPresenter(this);
        init();
        if(savedInstanceState == null || savedInstanceState.isEmpty()){
            getUserPreferences();
            updateData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewCheckGridForOrientation();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(!savedInstanceState.isEmpty()){
            mUserPreference = savedInstanceState.getInt(USER_PREFERENCE);
            setMovieList(savedInstanceState.<Movie>getParcelableArrayList(MOVIE_OBJECT));
            mListState = savedInstanceState.getParcelable(RECYCLER_POSITION);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mRecyclerView != null && mRecyclerView.getAdapter() != null && mGridLayoutManager != null){
            GridAdapter mGridAdapter = (GridAdapter) mRecyclerView.getAdapter();
            if(!mGridAdapter.getMovieArrayList().isEmpty()){
                outState.putParcelableArrayList(MOVIE_OBJECT, mGridAdapter.getMovieArrayList());
                outState.putInt(USER_PREFERENCE, mUserPreference);
                outState.putParcelable(RECYCLER_POSITION, mGridLayoutManager.onSaveInstanceState());
                super.onSaveInstanceState(outState);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == resultCode && mUserPreference == 2 && data != null){
            boolean isFavorite = data.getBooleanExtra("isFavorite", false);
            if(!isFavorite){
                int idMovie = data.getIntExtra("movieID", -1);
                if(!mMoviesPresenter.isMovieSavedInDB(idMovie)){
                    notifyChanges(idMovie);
                }
            }
        }
    }

    //DONE: Add menu for sort preference
    //region Menu methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem;
        switch (mUserPreference){
            case PREFERENCE_POPULAR:
            menuItem = menu.findItem(R.id.menu_popular);
            menuItem.setChecked(true);
            break;
            case PREFERENCE_TOP_RATED:
            menuItem = menu.findItem(R.id.menu_top_rated);
            menuItem.setChecked(true);
            break;
            case PREFERENCE_FAVORITES:
            menuItem = menu.findItem(R.id.menu_favorite_view);
            menuItem.setChecked(true);
            break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()){
            case R.id.menu_popular:
                if(mUserPreference != PREFERENCE_POPULAR){
                    setItemMenuClicked(PREFERENCE_POPULAR);
                }
                break;
            case R.id.menu_top_rated:
                if(mUserPreference != PREFERENCE_TOP_RATED){
                    setItemMenuClicked(PREFERENCE_TOP_RATED);
                }
                break;
            case R.id.menu_favorite_view:
                if(mUserPreference != PREFERENCE_FAVORITES){
                    setItemMenuClicked(PREFERENCE_FAVORITES);
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void setItemMenuClicked(int value){
        mUserPreference = value;
        mMoviesPresenter.setPreferences(this, mUserPreference);
        updateData();
    }
    //endregion

    private void fetchDataFromMovieDatabase(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){
            mMoviesPresenter.fetchDataFromMovieDatabase(mUserPreference);
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET}, MY_PERMISSIONS_INTERNET);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_INTERNET:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchDataFromMovieDatabase();
                } else {
                    //DONE: show a message to user instead of close the app
                    Toast.makeText(this, R.string.permissions, Toast.LENGTH_LONG).show();
                }

        }
    }

    //region Presenter methods
    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showNoInternetConnection(boolean show) {
        if (show) {
            mLinearLayoutNoInternet.setVisibility(View.VISIBLE);
            if(mSwipeRefreshLayout.isRefreshing()){
                hideProgress();
            }
            mRecyclerView.setVisibility(View.GONE);
        }
        else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mLinearLayoutNoInternet.setVisibility(View.GONE);
        }
    }

    @Override
    public void setMovieList(ArrayList<Movie> movieArrayList) {
        GridAdapter mGridAdapter;
        if(movieArrayList != null) {
            mGridAdapter = new GridAdapter(movieArrayList, this, mMoviesPresenter, mUserPreference);
        } else {
            //DONE: implement try again for null array
            mGridAdapter = new GridAdapter(this, mMoviesPresenter, mUserPreference);
//            Toast.makeText(this, R.string.try_again, Toast.LENGTH_LONG).show();
        }
        mRecyclerView.setAdapter(mGridAdapter);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onListItemClick(int listItemIndex) {
        GridAdapter mGridAdapter = (GridAdapter) mRecyclerView.getAdapter();
        if (mGridAdapter != null) {
            Movie movie = new Movie(mGridAdapter.getMovieArrayList().get(listItemIndex));
            Intent intent = new Intent(this, MoviesDetailActivity.class);
            intent.putExtra(MOVIE_OBJECT, movie);
            intent.putExtra(USER_PREFERENCE, mUserPreference);
            startActivityForResult(intent, Activity.CONTEXT_INCLUDE_CODE);
        }
    }
    //endregion

    //region View methods

    // Method to initialize view and visual components
    private void init(){
        setContentView(R.layout.activity_movies);
        mSwipeRefreshLayout = findViewById(R.id.srl_refresh_movies);
        mRecyclerView = findViewById(R.id.rv_movies_list);
        mRecyclerView.setAdapter(new GridAdapter(this, mMoviesPresenter, mUserPreference));
        mLinearLayoutNoInternet = findViewById(R.id.ll_unavailable_internet);

        mOnRefreshListener = getSwipeRefreshListener();
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        setColorScheme();

    }

    //organize Grid for different configs
    private void viewCheckGridForOrientation(){
        int grid_columns = 2;  // In portrait
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            grid_columns = 3;
        }

        mGridLayoutManager = new GridLayoutManager(this, grid_columns);
        if(mListState != null)
            mGridLayoutManager.onRestoreInstanceState(mListState);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
    }

    private void updateData(){
        setMovieList(null);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                mOnRefreshListener.onRefresh();
            }
        });
    }

    private void getUserPreferences(){
        mUserPreference = mMoviesPresenter.getPreferences(this);
    }

    private void notifyChanges(int idMovie) {
        GridAdapter mGridAdapter = (GridAdapter) mRecyclerView.getAdapter();
        Movie auxMovie = new Movie();
        auxMovie.setId(idMovie);
        mGridAdapter.getMovieArrayList().remove(auxMovie);
        mGridAdapter.notifyDataSetChanged();
    }
    //endregion

    //region SwipeRefresh Methods
    private SwipeRefreshLayout.OnRefreshListener getSwipeRefreshListener(){
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchDataFromMovieDatabase();
            }
        };
    }

    /**
     * Method to set colors of loading icon
     */
    private void setColorScheme(){
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.colorGreen1,
                R.color.colorGreen2,
                R.color.colorGreen3);
    }
    //endregion
}
