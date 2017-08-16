package com.example.android.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.utilities.MoviesJsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    final private int NUMBER_OF_COLLUMNS = 2;

    RecyclerView mMoviesRecyclerView;
    MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);

        boolean reverseLayout = false;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                this, NUMBER_OF_COLLUMNS, LinearLayoutManager.VERTICAL, reverseLayout);
        mMoviesRecyclerView.setLayoutManager(gridLayoutManager);

        mMoviesRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter();

        mMoviesRecyclerView.setAdapter(mMovieAdapter);

        loadMovies(NetworkUtils.SORT_BY_POPULARITY);
    }

    private class MoviesQueryTask extends AsyncTask<URL, Void, Movie[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Movie[] doInBackground(URL... urls) {

            URL url;
            Movie[] movies = null;

            if (urls.length > 0) {
                url = urls[0];

                String moviesJsonString = null;
                try {
                    moviesJsonString = NetworkUtils.getResponseFromHttpUrl(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.d(MainActivity.this.getClass().toString(), "moviesJsonString: " + moviesJsonString);

                if (moviesJsonString != null) {
                    try {
                        movies = MoviesJsonUtils.
                                getMoviesFromJson(MainActivity.this, moviesJsonString);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (movies != null)
                        return movies;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            if (movies != null && !movies.equals("")) {
                mMovieAdapter.setMoviesData(movies);
            }

        }
    }

    /**
        Calls network to fetch movies by popularity or by average rating

        @param sortedBy: can be NetworkUtils.SORT_BY_POPULARITY or NetworkUtils.SORT_BY_RATING
                Otherwise, this method is going to be sorted by popularity.
    */
    private void loadMovies(int sortedBy){

        URL urlPostersQuery;

        if(sortedBy == NetworkUtils.SORT_BY_POPULARITY || sortedBy == NetworkUtils.SORT_BY_RATING){
            urlPostersQuery = NetworkUtils.buildUrl(sortedBy);
        }
        else{
            urlPostersQuery = NetworkUtils.buildUrl(NetworkUtils.SORT_BY_POPULARITY);
        }
        new MoviesQueryTask().execute(urlPostersQuery);

    }

}
