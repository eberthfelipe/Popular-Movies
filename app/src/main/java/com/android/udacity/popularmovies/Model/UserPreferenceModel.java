package com.android.udacity.popularmovies.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.udacity.popularmovies.MVP.MovieContract;

public class UserPreferenceModel implements MovieContract.UserPreferenceModel {
    private final String mPopOrTop = "PopularOrTopRated";
    private final String mPrefsName = "com.android.udacity.popularmovies.PREFERENCE_FILE";

    @Override
    public int getPreferences(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                mPrefsName, Context.MODE_PRIVATE);
        return sharedPref.getInt(mPopOrTop, 0);
    }

    @Override
    public void setPreferences(Context context, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                mPrefsName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(mPopOrTop, value);
        editor.apply();
    }
}
