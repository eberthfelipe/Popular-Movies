package com.android.udacity.popularmovies.Model;

import android.content.Context;
import android.widget.ImageView;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Utils.NetworkUtils;

public class PicassoModelSingleton implements MovieContract.PicassoModel {

    private static PicassoModelSingleton sPicassoModelInstance;

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
    public void retrieveImageSrc(Context context, String imgPath, ImageView imageView) {
        NetworkUtils.buildPicassoRequest(context, imgPath, imageView);
    }
}
