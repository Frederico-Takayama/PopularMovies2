package com.example.android.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by lsitec335.takayama on 03/08/17.
 */

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    //TODO : Add a API Key here in order to get things working
    private static final String API_KEY = "";

    //Allow your user to change sort order via a setting:
    //The sort order can be by most popular, or by top rated
    ////https://api.themoviedb.org/3/movie/popular?api_key=<api_key>
    //https://api.themoviedb.org/3/movie/top_rated?api_key=<api_key>

    private static final String MOVIE_BASE_URL = "https://api.themoviedb.org";
    private static final String MOVIE_PATH_URL = "/3/movie";
    private static final String MOVIE_PATH_POPULAR = "/popular";
    private static final String MOVIE_PATH_TOP_RATED = "/top_rated";
    private static final String PARAM_API_KEY = "api_key";

    private static final int SORT_BY_POPULARITY = 0;
    private static final int SORT_BY_RATING = 1;

    /**
     * This method defines a java URL object to fetch movies from a choosed sort type
     *
     * @param sortBy the type of sort (SORT_BY_POPULARITY, SORT_BY_RATING)
     * @return
     */
    public static URL buildUrl (int sortBy){

        String urlChoosed = "";

        switch(sortBy)
        {
            case SORT_BY_RATING:
                urlChoosed = MOVIE_BASE_URL + MOVIE_PATH_URL + MOVIE_PATH_TOP_RATED;

                break;

            case SORT_BY_POPULARITY:
            default:
                urlChoosed = MOVIE_BASE_URL + MOVIE_PATH_URL + MOVIE_PATH_POPULAR;
                break;
        }

        Uri builtUri = Uri.parse(urlChoosed).buildUpon()
                .appendQueryParameter(PARAM_API_KEY,API_KEY)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "built URL: " + url);

        return url;
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

}
