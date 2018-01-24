package com.example.android.popularmovies.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * This class has some methods to help during network calls
 *
 * Created by lsitec335.takayama on 03/08/17.
 */

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    //TODO : Add a API Key here in order to get this class working
    private static final String API_KEY = "";

    //Allow your user to change sort order via a setting:
    //The sort order can be by most popular, or by top rated
    ////https://api.themoviedb.org/3/movie/popular?api_key=<api_key>
    //https://api.themoviedb.org/3/movie/top_rated?api_key=<api_key>

    private static final String MOVIE_BASE_URL = "https://api.themoviedb.org";
    private static final String MOVIE_PATH_URL = "/3/movie";
    private static final String MOVIE_PATH_POPULAR = "/popular";
    private static final String MOVIE_PATH_TOP_RATED = "/top_rated";
    private static final String MOVIE_PATH_VIDEOS = "/{id}/videos";
    private static final String MOVIE_PATH_REVIEWS = "/{id}/reviews";

    private static final String PARAM_API_KEY = "api_key";

    public static final int SORT_BY_POPULARITY = 0;
    public static final int SORT_BY_RATING = 1;
    public static final int MOVIE_TRAILLER = 2;
    public static final int MOVIE_REVIEW = 3;
    public static final String POSTER_URL_BASE = "http://image.tmdb.org/t/p/";
    public static final String POSTER_SIZE_PATH_URL = "w185";

    /**
     * This method defines a java URL object from String url
     *
     * @param urlString contains url in String format
     * @return
     */
    public static URL buildUrl(String urlString){
        Uri builtUri = Uri.parse(urlString).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built URL: " + url);

        return url;
    }

    /**
     * This method defines a java URL object which uses some param
     *
     * @param param contains the value to be replaced in url path
     * @return
     */
    public static URL buildUrlWithParam(int type, long param){
        String urlChoosed = MOVIE_BASE_URL + MOVIE_PATH_URL;

        switch (type) {
            case MOVIE_TRAILLER:
                urlChoosed += MOVIE_PATH_VIDEOS;
                break;

            case MOVIE_REVIEW:
            default:
                urlChoosed += MOVIE_PATH_REVIEWS;
                break;
        }

        urlChoosed = urlChoosed.replace("{id}",Long.toString(param));

        return buildUrl(urlChoosed);
    }

    /**
     * This method defines a java URL object to fetch movies from a choosed sort type
     *
     * @param sortBy the type of choosen (SORT_BY_POPULARITY, SORT_BY_RATING)
     * @return
     */
    public static URL buildUrlWithFilter(int sortBy) {
        String urlChoosed = MOVIE_BASE_URL + MOVIE_PATH_URL;

        switch (sortBy) {
            case SORT_BY_RATING:
                urlChoosed += MOVIE_PATH_TOP_RATED;
                break;

            case SORT_BY_POPULARITY:
            default:
                urlChoosed += MOVIE_PATH_POPULAR;
                break;
        }

        return buildUrl(urlChoosed);
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

    public static void setImage(Context context, String posterUrl, ImageView imageView){
        Picasso.with(context).setLoggingEnabled(true);
        Log.d(context.toString(), "try load a image using Picasso");
        //example:
        // Picasso.with(context).load("http://i.imgur.com/yWyBaYk.jpg").into(mImageView);
        Picasso.with(context).load(posterUrl).into(imageView);
    }

}
