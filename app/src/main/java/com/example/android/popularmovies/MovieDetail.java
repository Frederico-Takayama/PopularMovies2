package com.example.android.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.Trailler;
import com.example.android.popularmovies.utilities.NetworkUtils;

import java.net.URL;

public class MovieDetail extends AppCompatActivity {

    private TextView mTitleTextView;
    private TextView mYearTextView;
    private TextView mRatingTextView;
    private TextView mSynopsisTextView;
    private ImageView mPosterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mTitleTextView = (TextView) findViewById(R.id.tv_movie_title);
        mYearTextView = (TextView) findViewById(R.id.tv_year);
        mRatingTextView = (TextView) findViewById(R.id.tv_rating);
        mSynopsisTextView = (TextView) findViewById(R.id.tv_synopsis);
        mPosterImageView = (ImageView) findViewById(R.id.iv_poster);

        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra(Movie.class.toString());

        loadTraillers(movie.getId());
        loadReviews(movie.getId());

        mTitleTextView.setText(movie.getTitle());
        String year = movie.getReleaseDate().substring(0,
                movie.getReleaseDate().lastIndexOf("-"));
        mYearTextView.setText(year);
        String rating = movie.getRating() + "/10";
        mRatingTextView.setText(rating);
        mSynopsisTextView.setText(movie.getSynopsis());

        String posterUrl = NetworkUtils.POSTER_URL_BASE + NetworkUtils.POSTER_SIZE_PATH_URL +
                movie.getPosterUrl();
        Log.d("posterUrl", posterUrl.toString());

        NetworkUtils.setImage(this, posterUrl, mPosterImageView);

    }

    //retrieve trailler url information
    public void loadTraillers(long movie_id){
        URL urlTraillerQuery;
        urlTraillerQuery = NetworkUtils.buildUrlWithParam(
                NetworkUtils.MOVIE_TRAILLER, movie_id);
        Log.d("trailler", urlTraillerQuery.toString());

        //execute out from main thread
        new TraillerQueryTask().execute(urlTraillerQuery);
    }

    //retrieve review url information
    public void loadReviews(long movie_id){
        URL urlReviewQuery;
        urlReviewQuery = NetworkUtils.buildUrlWithParam(
                NetworkUtils.MOVIE_REVIEW, movie_id);
        Log.d("review", urlReviewQuery.toString());

        //execute out from main thread
        //new ReviewQueryTask().execute(urlReviewQuery);
    }

    private class TraillerQueryTask extends AsyncTask<URL, Void, Trailler[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Trailler[] doInBackground(URL... urls) {
            return new Trailler[0];
        }

        @Override
        protected void onPostExecute(Trailler[] traillers) {
            super.onPostExecute(traillers);
        }
    }
}
