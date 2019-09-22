package com.android.udacity.popularmovies.Model;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

public class PicassoModelSingleton implements MovieContract.PicassoModel {

    private static PicassoModelSingleton sPicassoModelInstance;
    private static  Picasso sPicasso;
    // Movies database information
    private static final String MOVIE_DATABASE_API_POSTER_URL = "http://image.tmdb.org/t/p/";
    //Poster sizes:  "w92", "w154", "w185", "w342", "w500", "w780" ou "original".
    private static final String MOVIE_DATABASE_API_POSTER_SIZE = "w500";
    // Youtube information
    private static final String YOUTUBE_POSTER_URL = "http://img.youtube.com/vi/?/0.jpg";
    // type of images which Picasso can handle
    public static final int TYPE_POSTER = 0;
    public static final int TYPE_TARGET = 1;
    public static final int TYPE_VIDEO = 2;
    public static final int TYPE_FILE = 3;

    private PicassoModelSingleton(){
        if(sPicassoModelInstance != null){
            throw new RuntimeException("Singleton Pattern: Use getInstance() method!");
        }
    }

    public static synchronized PicassoModelSingleton getInstance(Context context) {
        if(sPicassoModelInstance == null){
            sPicassoModelInstance = new PicassoModelSingleton();
            sPicasso = new Picasso.Builder(context).loggingEnabled(false).listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    Log.d("Picasso", "onImageLoadFailed: " + uri.toString() + "exception: " +  exception);
                }
            }).build();
            Picasso.setSingletonInstance(sPicasso);
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
                Picasso.with(context)
                        .load(MOVIE_DATABASE_API_POSTER_URL
                        + MOVIE_DATABASE_API_POSTER_SIZE
                        + imgPath)
                        .placeholder(R.drawable.ic_place_holder)
                        .into(imageView);
                break;
            case TYPE_TARGET:
                target = (Target) object;
                Picasso.with(context)
                        .load(MOVIE_DATABASE_API_POSTER_URL
                        + MOVIE_DATABASE_API_POSTER_SIZE
                        + imgPath)
                        .placeholder(R.drawable.ic_place_holder)
                        .into(target);
                break;
            case TYPE_VIDEO:
                imageView = (ImageView) object;
                Picasso.with(context)
                        .load(YOUTUBE_POSTER_URL
                        .replace("?", imgPath))
                        .placeholder(R.drawable.ic_place_holder)
                        .into(imageView);
                break;
            case TYPE_FILE:
                imageView = (ImageView) object;
                File file =  (imgPath != null) ? new File(imgPath) : null;
                if (file != null){
                    Picasso.with(context)
                            .load(file)
                            .placeholder(R.drawable.ic_place_holder)
                            .into(imageView);
                } else {
                    Picasso.with(context)
                            .load(R.drawable.ic_place_holder)
                            .into(imageView);
                }
                break;
        }
    }

}
