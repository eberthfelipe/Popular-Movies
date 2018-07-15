package com.android.udacity.popularmovies.Model;

import android.os.AsyncTask;
import android.util.Log;

import com.android.udacity.popularmovies.Utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

// Class to perform http requests
public class ConnectNetwork extends AsyncTask<URL, Void, String>{
    private static final String TAG = ConnectNetwork.class.getName();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(URL... urls) {
        String result = null;
        for (URL url: urls) {
            try {
                result = NetworkUtils.getResponseFromHttpUrl(url);
                Log.d(TAG, "doInBackground: " + result);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return result;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
