package com.example.android.popularmovies;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.PopularMoviesContract;
import com.example.android.popularmovies.data.PopularMoviesDBHelper;
import com.example.android.popularmovies.utilities.MoviesJsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

/**
 * Main Class. implements MovieAdapter.GridItemClickListener in order to treat click events
 * */
public class MainActivity extends AppCompatActivity implements MovieAdapter.GridItemClickListener {

    private SQLiteDatabase mDb; // to use Database

    private final String TAG = MainActivity.class.toString();
//    private static final String MOVIES_FETCHED= "movies_fetched";

    private RecyclerView mMoviesRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorView;
    private ProgressBar mProgressBar;
    private static Movie[] mMovies; // saves movies list temporally
    private static boolean isErrorViewVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorView = (TextView) findViewById(R.id.tv_error_display);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mMoviesRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);

        setRecyclerView();

//        if (savedInstanceState != null && savedInstanceState.containsKey(MOVIES_FETCHED)) {
//            mMovies = (Movie[]) savedInstanceState.getParcelableArray(MOVIES_FETCHED);
//        }

        //TODO: talvez tenha que mudar isto!!
        if(mMovies != null){
            // enter here during a device rotation
            if(isErrorViewVisible)
                showErrorView();
            else
                mMovieAdapter.setMoviesData(mMovies);
        }
        else{
            loadMovies(NetworkUtils.SORT_BY_POPULARITY);
        }

        //using database
        PopularMoviesDBHelper dbHelper = new PopularMoviesDBHelper(this);
        mDb = dbHelper.getWritableDatabase();
    }

    private Cursor getAllMovies(){
        return mDb.query(
                PopularMoviesContract.Movies.TABLE_NAME,
                null, // projection array (array of columns interested
                null,
                null,
                null,
                null,
                PopularMoviesContract.Movies.COLUMN_TITLE); // last param is sorted by
    }

    /**
     * This method do a parser in a cursor in current position to a Movie
     */
    Movie parseMovie(Cursor cursor)
    {
        Movie movie = null;

        try
        {
            int idIndex = cursor.getColumnIndexOrThrow(PopularMoviesContract.Movies.COLUMN_MOVIE_ID);
            int titleIndex = cursor.getColumnIndexOrThrow(PopularMoviesContract.Movies.COLUMN_TITLE);
            int posterUrlIndex = cursor.getColumnIndexOrThrow(PopularMoviesContract.Movies.COLUMN_POSTER_URL);
            int sysnopsisIndex = cursor.getColumnIndexOrThrow(PopularMoviesContract.Movies.COLUMN_SYNOPSIS);
            int ratingIndex = cursor.getColumnIndexOrThrow(PopularMoviesContract.Movies.COLUMN_RATING);
            int releaseDateIndex = cursor.getColumnIndexOrThrow(PopularMoviesContract.Movies.COLUMN_RELEASE_DATE);
//            int isFavoriteIndex = cursor.getColumnIndexOrThrow(PopularMoviesContract.Movies.COLUMN_IS_FAVORITE);
            long id = cursor.getLong(idIndex);
            String title = cursor.getString(titleIndex);
            String posterUrl = cursor.getString(posterUrlIndex);
            String sysnopsis = cursor.getString(sysnopsisIndex);
            Double rating = cursor.getDouble(ratingIndex);
            String releaseDate = cursor.getString(releaseDateIndex);
//            Integer isFavorite = cursor.getInt(isFavoriteIndex);
            movie = new Movie(id, title, posterUrl, sysnopsis, rating, releaseDate);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return movie;
    }

    /**
     * This method uses parseMovie() to do a parser from Cursor to Movie[]
     * */
    Movie[] populateMovies(Cursor cursor){
        if(cursor ==null || cursor.getCount() == 0)
            return null;

        Movie[] movies = new Movie[cursor.getCount()];

        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            Movie movie = parseMovie(cursor);
            movies[i] = movie;
            cursor.moveToNext();
        }

        return movies;
    }


    //Didn't used at end.. using private static Movie[] mMovies instead
    //so I could maintaing actual movies list even if I change from detail activity
    //back to main activity.
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        //saves movies list
//        if(mMovies!=null){
//            outState.putParcelableArray(MOVIES_FETCHED, mMovies);
//        }
//    }

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
        //I could use this line below, but notifyDataSetChanged(); already do the job
        //mMovieAdapter.setMoviesData(null);
        mMoviesRecyclerView.smoothScrollToPosition(0); // resets position

        switch (id) {
            case R.id.sort_by_popularity:
                Log.d(TAG, "Select by popularity");
                loadMovies(NetworkUtils.SORT_BY_POPULARITY);
                break;
            case R.id.sort_by_highest_rate:
                Log.d(TAG, "Select by rating");
                loadMovies(NetworkUtils.SORT_BY_RATING);
                break;
            case R.id.sort_by_favorites:
                Log.d(TAG, "Select by favorites");
                loadMoviesFromDB();
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
        isErrorViewVisible = true;
    }

    /**
     * Shows recycler view and hides error view;
     */
    private void showRecyclerView() {
        mErrorView.setVisibility(View.INVISIBLE);
        mMoviesRecyclerView.setVisibility(View.VISIBLE);
        isErrorViewVisible = false;
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
     * retrieves movies from DB
     * */
    private void loadMoviesFromDB(){
        Cursor cursor = getAllMovies();
        Movie[] movies = populateMovies(cursor);
        cursor.close(); // close this cursor in order to avoid memory leak;

        if(movies != null){
            mMovies = movies;
            mMovieAdapter.setMoviesData(movies);
            showRecyclerView();
            // debug
//            for(Movie movie : movies){
//                Log.d("movies_from_db", movie.toString());
//            }
        }else{
//            Log.d("movies_from_db", "empty");
            Toast.makeText(this, R.string.favorites_empty, Toast.LENGTH_SHORT).show();
        }
        mMovies = movies;
        mMovieAdapter.setMoviesData(movies);
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
                Log.d("Test","full!");
                mMovies = movies;//updates moviesList var
                mMovieAdapter.setMoviesData(movies);
                showRecyclerView();
            } else {
                Log.d("Test","empty");
                showErrorView();
            }
        }
    }

}
