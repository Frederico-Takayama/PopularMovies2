package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.data.Movie;
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

        //retrieve trailler url information
        URL urlTraillerQuery = retriveTraillerInformation(movie.getId());

        //retrieve trailler url information
        URL urlReviewQuery = retriveReviewInformation(movie.getId());

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
    public URL retriveTraillerInformation(long movie_id){
        URL urlTraillerQuery;
        urlTraillerQuery = NetworkUtils.buildUrlWithParam(
                NetworkUtils.MOVIE_TRAILLER, movie_id);
        Log.d("trailler", urlTraillerQuery.toString());
        //new MainActivity.MoviesQueryTask().execute(urlPostersQuery);
        return urlTraillerQuery;
    }

    //retrieve review url information
    public URL retriveReviewInformation(long movie_id){
        URL urlReviewQuery;
        urlReviewQuery = NetworkUtils.buildUrlWithParam(
                NetworkUtils.MOVIE_REVIEW, movie_id);
        Log.d("review", urlReviewQuery.toString());
        //new MainActivity.MoviesQueryTask().execute(urlPostersQuery);
        return urlReviewQuery;
    }
}
