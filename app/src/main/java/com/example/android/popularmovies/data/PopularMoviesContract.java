package com.example.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by lsitec335.takayama on 30/01/18.
 * This contract defines how databases looks like for the rest of the application.
 */

public class PopularMoviesContract{

    public static final String AUTHORITY = "com.example.android.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MOVIES = "movies";


    private PopularMoviesContract(){} // it's never declared, so it's private

    /**
     * This is Movie table schema. Implements BaseColumns, in order to get ID column.
     */
    public static final class Movies implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_URL = "poster_url";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
//        public static final String COLUMN_IS_FAVORITE = "is_favorite";
    }

    public static final class Reviews implements BaseColumns{
        public static final String TABLE_NAME = "reviews";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_URL_STRING = "urlString";
    }

    public static final class Traillers implements BaseColumns{
        public static final String TABLE_NAME = "traillers";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_KEY = "key";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_URL_STRING = "urlString";
    }
}
