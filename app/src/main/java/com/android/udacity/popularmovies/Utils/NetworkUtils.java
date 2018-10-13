package com.android.udacity.popularmovies.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.android.udacity.popularmovies.BuildConfig;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getName();

    private static final String MOVIE_DATABASE_API_URL = "https://api.themoviedb.org/3";
    private static final String MOVIE_DATABASE_API_GET_POPULAR = "movie/popular";
    private static final String MOVIE_DATABASE_API_GET_TOP_RATED = "movie/top_rated";
    private static final String API_KEY = "api_key";

    /*
    @param userPreference based on user preference, try to get movies: popular or top rated
    @return The URL to use to query the movie database server.
     */
    public static URL buildURL(int userPreference){
        // MOVIE_DATABASE_API_GET_POPULAR or MOVIE_DATABASE_API_GET_TOP_RATED
        String requestType = getUserPreference(userPreference);

        URL url = null;
        Uri uri = Uri.parse(MOVIE_DATABASE_API_URL)
                .buildUpon()
                .appendEncodedPath(requestType)
                .appendQueryParameter(API_KEY, BuildConfig.API_KEY)
                .build();
        try {
            Log.d(TAG, "buildURL: " + uri.toString());
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String getUserPreference(int userPreference){
        switch (userPreference){
            case 0: // POPULAR
                return MOVIE_DATABASE_API_GET_POPULAR;
            case 1: // TOP RATED
                return MOVIE_DATABASE_API_GET_TOP_RATED;
            default:
                return "";
        }
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Log.d(TAG, "getResponseFromHttpUrl: "+ in.toString());

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static boolean checkInternetConnection(Context context){
        boolean isConnected;
        NetworkInfo activeNetwork = null;
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(cm != null){
            activeNetwork = cm.getActiveNetworkInfo();
        }
        isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
        Log.d(TAG, "checkInternetConnection: " + isConnected);
        return isConnected;
    }
}
