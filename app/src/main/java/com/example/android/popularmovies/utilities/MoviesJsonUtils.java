package com.example.android.popularmovies.utilities;

import android.content.Context;

import android.util.Log;

import com.example.android.popularmovies.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lsitec335.takayama on 04/08/17.
 */

public final class MoviesJsonUtils {

    private static final String TAG = MoviesJsonUtils.class.getName();

    /**
     * This method parses JSON from a web response and returns an array of Strings
     * containing paths for movies posters.
     *
     * @param moviesJsonStr JSON response from server
     * @return Array of Movies describing movies details
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static Movie[] getMoviesFromJson(Context context, String moviesJsonStr)
            throws JSONException {

        /* example:
        moviesJsonStr:{
            "page":1,
            "total_results":19601,
            "total_pages":981,
            "results":[
            {
                "vote_count":4064,
                "id":211672,
                "video":false,
                "vote_average":6.4,
                "title":"Minions",
                "popularity":197.218355,
                "poster_path":"\/q0R4crx2SehcEEQEkYObktdeFy.jpg",
                "original_language":"en",
                "original_title":"Minions",
                "genre_ids":[
                    10751,
                    16,
                    12,
                    35
                ],
                "backdrop_path":"\/uX7LXnsC7bZJZjn048UCOwkPXWJ.jpg",
                "adult":false,
                "overview":"Minions Stuart, Kevin and Bob are recruited by Scarlet Overkill, a super-villain who, alongside her inventor husband Herb, hatches a plot to take over the world.",
                "release_date":"2015-06-17"
            },
            ...
        */

        JSONObject moviesJson = new JSONObject(moviesJsonStr);//create a JSON Object
        JSONArray resultsArray = moviesJson.getJSONArray("results");
        Movie[] movies = new Movie[resultsArray.length()];
        for (int i = 0; i < resultsArray.length(); i++) {

            JSONObject movieJson = resultsArray.getJSONObject(i);
            movies[i] = new Movie(
                    movieJson.getString("title"),
                    movieJson.getString("poster_path"),
                    movieJson.getString("overview"),
                    movieJson.getDouble("vote_average"),
                    movieJson.getString("release_date"));

            Log.d(TAG, movies[i].toString());
        }

        return movies;
    }

}
