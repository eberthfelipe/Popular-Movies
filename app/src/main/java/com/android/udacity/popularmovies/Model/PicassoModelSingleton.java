package com.android.udacity.popularmovies.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class PicassoModelSingleton implements MovieContract.PicassoModel {

    private static PicassoModelSingleton sPicassoModelInstance;
    // Movies database information
    private static final String MOVIE_DATABASE_API_POSTER_URL = "http://image.tmdb.org/t/p/";
    //Poster sizes:  "w92", "w154", "w185", "w342", "w500", "w780" ou "original".
    private static final String MOVIE_DATABASE_API_POSTER_SIZE = "/w500";
    // Youtube information
    private static final String YOUTUBE_POSTER_URL = "http://img.youtube.com/vi/?/0.jpg";
    // type of images which Picasso can handle
    public static final int TYPE_POSTER = 0;
    public static final int TYPE_FILE = 1;
    public static final int TYPE_VIDEO = 2;

    private PicassoModelSingleton(){
        if(sPicassoModelInstance != null){
            throw new RuntimeException("Singleton Pattern: Use getInstance() method!");
        }
    }

    public static synchronized PicassoModelSingleton getInstance() {
        if(sPicassoModelInstance == null){
            sPicassoModelInstance = new PicassoModelSingleton();
        }
        return sPicassoModelInstance;
    }

    @Override
    public void retrieveImageSrc(Context context, String imgPath, Object object, int type) {
        buildPicassoRequest(context, imgPath, object, type);
    }

    private void buildPicassoRequest(@NonNull Context context, String imgPath, Object object, int type){
        //DONE: create class to save image from picasso to use on favorites view
        ImageView imageView;
        Target target;
        switch (type){
            case TYPE_POSTER:
                imageView = (ImageView) object;
                Picasso.with(context).load(MOVIE_DATABASE_API_POSTER_URL
                        + MOVIE_DATABASE_API_POSTER_SIZE
                        + imgPath)
                        .placeholder(R.drawable.ic_place_holder)
                        .into(imageView);
                break;
            case TYPE_FILE:
                target = (Target) object;
                Picasso.with(context).load(MOVIE_DATABASE_API_POSTER_URL
                        + MOVIE_DATABASE_API_POSTER_SIZE
                        + imgPath)
                        .placeholder(R.drawable.ic_place_holder)
                        .into(target);
                break;
            case TYPE_VIDEO:
                imageView = (ImageView) object;
                Picasso.with(context).load(YOUTUBE_POSTER_URL
                        .replace("?", imgPath))
                        .placeholder(R.drawable.ic_place_holder)
                        .into(imageView);
                break;
        }
    }

}
