package com.example.android.popularmovies.utilities;

import android.content.Context;

import org.json.JSONException;

/**
 * Created by lsitec335.takayama on 04/08/17.
 */

public final class MoviesJsonUtils {

    /**
     * This method parses JSON from a web response and returns an array of Strings
     * containing paths for movies posters.
     *
     * @param movieJsonStr JSON response from server
     *
     * @return Array of Strings describing movies details
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static String[] getMoviePostersPathsFromJson(Context context, String movieJsonStr)
            throws JSONException {

    }

}
