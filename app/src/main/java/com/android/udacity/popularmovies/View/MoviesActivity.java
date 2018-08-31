package com.android.udacity.popularmovies.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Model.Movie;
import com.android.udacity.popularmovies.Presenter.NetworkPresenter;
import com.android.udacity.popularmovies.R;

import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity implements MovieContract.ActivityView, MovieContract.ListItemClickListener{

    private final int MY_PERMISSIONS_INTERNET = 0;
    private NetworkPresenter mNetworkPresenter;
    private ProgressBar mLoadingMoviesProgressBar;
    private RecyclerView mRecyclerView;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNetworkPresenter = new NetworkPresenter();
        mNetworkPresenter.setActivityView(this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void fetchDataFromMovieDatabase(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){
            mNetworkPresenter.fetchDataFromMovieDatabase();
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

    // Method to initialize view and visual components
    private void init(){
        setContentView(R.layout.activity_movies);
        mLoadingMoviesProgressBar = findViewById(R.id.pb_loading_movies);

        mRecyclerView = findViewById(R.id.rv_movies_list);
        int GRID_COLUMNS = 2;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, GRID_COLUMNS);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        fetchDataFromMovieDatabase();
    }

    @Override
    public void showProgress() {
        mLoadingMoviesProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingMoviesProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMovies() {

    }

    @Override
    public void setMovieList(ArrayList<Movie> movieArrayList) {
        GridAdapter mGridAdapter = new GridAdapter(movieArrayList, this);
        mRecyclerView.setAdapter(mGridAdapter);
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
}
