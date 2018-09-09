package com.android.udacity.popularmovies.View;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Model.Movie;
import com.android.udacity.popularmovies.Presenter.MoviesPresenter;
import com.android.udacity.popularmovies.R;

import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity implements MovieContract.ActivityView, MovieContract.ListItemClickListener{
    private final int MY_PERMISSIONS_INTERNET = 0;
    private MoviesPresenter mMoviesPresenter;
//    private ProgressBar mLoadingMoviesProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener;
    private RecyclerView mRecyclerView;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoviesPresenter = new MoviesPresenter();
        mMoviesPresenter.setActivityView(this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //TODO: Add menu for sort preference
    //region Menu methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
    //endregion

    private void fetchDataFromMovieDatabase(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){
            mMoviesPresenter.fetchDataFromMovieDatabase();
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
                    finish();
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
    public void showMovies() {

    }

    @Override
    public void setMovieList(ArrayList<Movie> movieArrayList) {
        GridAdapter mGridAdapter = new GridAdapter(movieArrayList, this, mMoviesPresenter);
        mRecyclerView.setAdapter(mGridAdapter);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onListItemClick(int listItemIndex) {
        if(mToast != null){
            mToast.cancel();
        }
        String toastMessage = "Item #" + listItemIndex + " clicked.";
        mToast = Toast.makeText( this, toastMessage, Toast.LENGTH_SHORT);

        mToast.show();
    }
    //endregion

    // Method to initialize view and visual components
    private void init(){
        final int GRID_COLUMNS = 2;

        setContentView(R.layout.activity_movies);
        mSwipeRefreshLayout = findViewById(R.id.srl_refresh_movies);
        mRecyclerView = findViewById(R.id.rv_movies_list);

        mOnRefreshListener = getSwipeRefreshListener();
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        setColorScheme();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, GRID_COLUMNS);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        updateData();
    }

    private void updateData(){
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                mOnRefreshListener.onRefresh();
            }
        });
    }

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
