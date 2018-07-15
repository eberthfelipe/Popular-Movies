package com.android.udacity.popularmovies;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.udacity.popularmovies.Model.ConnectNetwork;
import com.android.udacity.popularmovies.Utils.NetworkUtils;

public class MoviesActivity extends AppCompatActivity {
    private ConnectNetwork connectNetwork;
    private final int MY_PERMISSIONS_INTERNET = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        fetchDataFromMovieDatabase();
    }

    public void fetchDataFromMovieDatabase(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){
            connectNetwork = new ConnectNetwork();
            // get User preference for movies: popular or top rated
            int preference = 0;
            connectNetwork.execute(NetworkUtils.buildURL(preference));

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
}
