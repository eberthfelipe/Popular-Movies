package com.android.udacity.popularmovies.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.android.udacity.popularmovies.MVP.MovieMVP;
import com.android.udacity.popularmovies.Presenter.NetworkPresenter;
import com.android.udacity.popularmovies.R;

public class MoviesActivity extends AppCompatActivity implements MovieMVP.ActivityView{
    private final int MY_PERMISSIONS_INTERNET = 0;
    private NetworkPresenter networkPresenter;
    private ProgressBar mLoadingMoviesProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkPresenter = new NetworkPresenter();
        networkPresenter.setActivityView(this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchDataFromMovieDatabase();
    }

    private void fetchDataFromMovieDatabase(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){
            networkPresenter.fetchDataFromMovieDatabase();
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
    }

    @Override
    public void showProgress() {
        mLoadingMoviesProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingMoviesProgressBar.setVisibility(View.INVISIBLE);
    }
}
