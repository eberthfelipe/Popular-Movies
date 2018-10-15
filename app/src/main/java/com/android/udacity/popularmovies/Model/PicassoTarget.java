package com.android.udacity.popularmovies.Model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PicassoTarget implements Target {

    private static final String TAG = PicassoTarget.class.getName();
    private String imgName;

    public PicassoTarget(String imgName) {
        this.imgName = imgName;
    }

    @Override
    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final File imgFile = new File(imgName);
                FileOutputStream fileOutputStream;
                try {
                    fileOutputStream = new FileOutputStream(imgFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    Log.d(TAG, "onBitmapLoaded SAVED: " + imgName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}

//TODO:delete movie from DB only on destroy method in MoviesDetailActivity