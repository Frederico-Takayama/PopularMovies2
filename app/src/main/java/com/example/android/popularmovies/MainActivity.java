package com.example.android.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.utilities.MoviesJsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

/**
 * Main Class. implements MovieAdapter.GridItemClickListener in order to treat click events
 * */
public class MainActivity extends AppCompatActivity implements MovieAdapter.GridItemClickListener {

    private final String TAG = MainActivity.class.toString();
    RecyclerView mMoviesRecyclerView;
    MovieAdapter mMovieAdapter;
    TextView mErrorView;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorView = (TextView) findViewById(R.id.tv_error_display);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mMoviesRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);

        setRecyclerView();
    }

    /**
     * Defines recycler view configuration
     */
    private void setRecyclerView() {

        final boolean reverseLayout = false;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                this, setNumberOfColumns(), LinearLayoutManager.VERTICAL, reverseLayout);
        mMoviesRecyclerView.setLayoutManager(gridLayoutManager);
        mMoviesRecyclerView.setHasFixedSize(true);

        //this is MainActivity. Passed with method onGridItemclick() below:
        mMovieAdapter = new MovieAdapter(this);
        mMoviesRecyclerView.setAdapter(mMovieAdapter);

        loadMovies(NetworkUtils.SORT_BY_POPULARITY);
    }

    public void onGridItemClick(int clickedItemIndex){
        Log.d(TAG, "click item index" + clickedItemIndex);

        Movie movie = mMovieAdapter.getMovie(clickedItemIndex);
        Intent intent = new Intent(this, MovieDetail.class);
        intent.putExtra(Movie.class.toString(), movie);
        startActivity(intent);
    }

    /**
     * Inflates Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;
    }

    /**
     * Handles selected items from menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //I could use this line, but notifyDataSetChanged(); already do the job
        //mMovieAdapter.setMoviesData(null);

        switch (id) {
            case R.id.sort_by_popularity:
                Log.d(TAG, "Selecionar pela popularidade");
                loadMovies(NetworkUtils.SORT_BY_POPULARITY);
                break;
            case R.id.sort_by_average_rate:
                Log.d(TAG, "Selecionar pelo rating");
                loadMovies(NetworkUtils.SORT_BY_RATING);
                break;
            default:
                Log.d(TAG, "Invalid option");
                break;
        }

        return true;
    }

    /**
     * Shows error view and hides recycler view;
     */
    private void showErrorView() {
        mMoviesRecyclerView.setVisibility(View.INVISIBLE);
        mErrorView.setVisibility(View.VISIBLE);
    }

    /**
     * Shows recycler view and hides error view;
     */
    private void showRecyclerView() {
        mErrorView.setVisibility(View.INVISIBLE);
        mMoviesRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * Calls network to fetch movies by popularity or by average rating
     *
     * @param sortedBy: can be NetworkUtils.SORT_BY_POPULARITY or NetworkUtils.SORT_BY_RATING
     *                  Otherwise, this method is going to be sorted by popularity.
     */
    private void loadMovies(int sortedBy) {

        URL urlPostersQuery;

        if (sortedBy == NetworkUtils.SORT_BY_POPULARITY || sortedBy == NetworkUtils.SORT_BY_RATING) {
            urlPostersQuery = NetworkUtils.buildUrlWithFilter(sortedBy);
        } else {
            urlPostersQuery = NetworkUtils.buildUrlWithFilter(NetworkUtils.SORT_BY_POPULARITY);
        }
        new MoviesQueryTask().execute(urlPostersQuery);

    }

    /**
     * returns the number of Columns based on width screen size
     */
    private int setNumberOfColumns() {

        //from https://stackoverflow.com/questions/4743116/get-screen-width-and-height
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int dpi = displayMetrics.densityDpi;
        float posterSizeDp = 180;

        Log.d(TAG, "width: " + width);
        Log.d(TAG, "dpi: " + dpi);
        Log.d(TAG, "dp: " + (width / (dpi / 160)) / posterSizeDp);

        //px = dp*(dpi/160)
        return (int) ((width / (dpi / 160)) / posterSizeDp);
    }

    private class MoviesQueryTask extends AsyncTask<URL, Void, Movie[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
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
            mProgressBar.setVisibility(View.INVISIBLE);
            if (movies != null) {
                Log.d("Test","cheio!");
                Log.d("movies:", movies.toString());
                mMovieAdapter.setMoviesData(movies);
                showRecyclerView();
            } else {
                Log.d("Test","vazio");
                showErrorView();
            }
        }
    }

}
