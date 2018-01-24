package com.example.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.Review;
import com.example.android.popularmovies.data.Trailler;
import com.example.android.popularmovies.utilities.MoviesJsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MovieDetail extends AppCompatActivity
        implements TraillerAdapter.GridItemClickListener, ReviewAdapter.GridItemClickListener{

    private final String TAG = MovieDetail.class.toString();

    private TextView mTitleTextView;
    private TextView mYearTextView;
    private TextView mRatingTextView;
    private TextView mSynopsisTextView;
    private ImageView mPosterImageView;
    private Movie mMovie;

    private ProgressBar mTraillerProgressBar;
    private TextView mTraillerErrorView;
    private RecyclerView mTraillerRecyclerView;
    private TraillerAdapter mTraillerAdapter;
    private ProgressBar mReviewProgressBar;
    private TextView mReviewErrorView;
    private RecyclerView mReviewRecyclerView;
    private ReviewAdapter mReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mTitleTextView = (TextView) findViewById(R.id.tv_movie_title);
        mYearTextView = (TextView) findViewById(R.id.tv_year);
        mRatingTextView = (TextView) findViewById(R.id.tv_rating);
        mSynopsisTextView = (TextView) findViewById(R.id.tv_synopsis);
        mPosterImageView = (ImageView) findViewById(R.id.iv_poster);

        mTraillerProgressBar = (ProgressBar) findViewById(R.id.pb_trailler_loading_indicator);
        mTraillerErrorView = (TextView) findViewById(R.id.tv_trailler_error_display);
        mTraillerRecyclerView = (RecyclerView) findViewById(R.id.rc_traillers);

        mReviewProgressBar = (ProgressBar) findViewById(R.id.pb_review_loading_indicator);
        mReviewErrorView = (TextView) findViewById(R.id.tv_review_error_display);
        mReviewRecyclerView = (RecyclerView) findViewById(R.id.rc_reviews);

        Intent intent = getIntent();
        mMovie = (Movie) intent.getSerializableExtra(Movie.class.toString());

        mTitleTextView.setText(mMovie.getTitle());
        String year = mMovie.getReleaseDate().substring(0,
                mMovie.getReleaseDate().lastIndexOf("-"));
        mYearTextView.setText(year);
        String rating = mMovie.getRating() + "/10";
        mRatingTextView.setText(rating);
        mSynopsisTextView.setText(mMovie.getSynopsis());

        String posterUrl = NetworkUtils.POSTER_URL_BASE + NetworkUtils.POSTER_SIZE_PATH_URL +
                mMovie.getPosterUrl();
//        Log.d("posterUrl", posterUrl.toString());

        NetworkUtils.setImage(this, posterUrl, mPosterImageView);

        setRecyclerViews();
    }

    /**
     * Defines recycler view configuration
     */
    private void setRecyclerViews() {

        final boolean reverseLayout = false;

        //Trailler recyclerView
        LinearLayoutManager linearTraillerLayoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, reverseLayout);
        mTraillerRecyclerView.setLayoutManager(linearTraillerLayoutManager);
        mTraillerRecyclerView.setHasFixedSize(true);
        //add Adapter
        mTraillerAdapter = new TraillerAdapter(this);
        mTraillerRecyclerView.setAdapter(mTraillerAdapter);

        //Review recyclerView
        LinearLayoutManager linearReviewLayoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, reverseLayout);
        mReviewRecyclerView.setLayoutManager(linearReviewLayoutManager);
        mReviewRecyclerView.setHasFixedSize(true);
        //add Adapter
        mReviewAdapter = new ReviewAdapter(this);
        mReviewRecyclerView.setAdapter(mReviewAdapter);

        loadTraillers(mMovie.getId());
        loadReviews(mMovie.getId());
    }

    /**
     * Handler for TraillerAdapter
     */
    public void onGridTraillerItemClick(int clickedItemIndex){
        Log.d(TAG, "click item index" + clickedItemIndex);
        Trailler trailler = mTraillerAdapter.getTrailler(clickedItemIndex);

        //start an intent which call youtube url
        Uri webpage = Uri.parse(trailler.getUrlString());
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Handler for ReviewAdapter
     */
    public void onGridReviewItemClick(int clickedItemIndex){
        Log.d(TAG, "click item index" + clickedItemIndex);
        Review review = mReviewAdapter.getReview(clickedItemIndex);

        //start an intent which call youtube url
        Uri webpage = Uri.parse(review.getUrlString());
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * retrieve trailler url information
     */
    public void loadTraillers(long movie_id){
        URL urlTraillerQuery;
        urlTraillerQuery = NetworkUtils.buildUrlWithParam(
                NetworkUtils.MOVIE_TRAILLER, movie_id);
        Log.d("trailler", urlTraillerQuery.toString());

        //execute out from main thread
        new TraillerQueryTask().execute(urlTraillerQuery);
    }

    /**
     * retrieve review url information
     */
    public void loadReviews(long movie_id){
        URL urlReviewQuery;
        urlReviewQuery = NetworkUtils.buildUrlWithParam(
                NetworkUtils.MOVIE_REVIEW, movie_id);
        Log.d("review", urlReviewQuery.toString());

        //execute out from main thread
        new ReviewQueryTask().execute(urlReviewQuery);
    }

    public void showRecyclerView(int id){
        switch (id){
            case R.id.rc_traillers:
                mTraillerRecyclerView.setVisibility(View.VISIBLE);
                break;
            case R.id.rc_reviews:
                mReviewRecyclerView.setVisibility(View.VISIBLE);
                break;
            default:
                Log.d(MovieDetail.class.toString(),"invalid recyclerView");
                break;
        }
    }

    public void showErrorView(int id){
        switch (id){
            case R.id.rc_traillers:
                mTraillerErrorView.setVisibility(View.VISIBLE);
                break;
            case R.id.rc_reviews:
                mReviewErrorView.setVisibility(View.VISIBLE);
                break;
            default:
                Log.d(MovieDetail.class.toString(),"invalid errorView");
                break;
        }
    }

    private class TraillerQueryTask extends AsyncTask<URL, Void, Trailler[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mTraillerProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Trailler[] doInBackground(URL... urls) {
            URL url;
            Trailler[] traillers = null;

            if (urls.length > 0) {
                url = urls[0];

                String traillersJsonString = null;
                try {
                    traillersJsonString = NetworkUtils.getResponseFromHttpUrl(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.d(MovieDetail.this.getClass().toString(), "traillersJsonString: " + traillersJsonString);

                if (traillersJsonString != null) {
                    try {
                        traillers = MoviesJsonUtils.
                                getTraillersFromJson(traillersJsonString);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (traillers != null)
                        return traillers;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Trailler[] traillers) {
            mTraillerProgressBar.setVisibility(View.INVISIBLE);
            if (traillers != null) {

                mMovie.setTraillers(traillers);
                mTraillerAdapter.setTraillersData(traillers); //updates recycler view
                showRecyclerView(R.id.rc_traillers);
            } else {
                Log.d("traillers","vazio");
                showErrorView(R.id.rc_traillers);
            }
        }
    }

    private class ReviewQueryTask extends AsyncTask<URL, Void, Review[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mReviewProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Review[] doInBackground(URL... urls) {
            URL url;
            Review[] reviews = null;

            if (urls.length > 0) {
                url = urls[0];

                String reviewsJsonString = null;
                try {
                    reviewsJsonString = NetworkUtils.getResponseFromHttpUrl(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.d(MovieDetail.this.getClass().toString(), "reviewsJsonString: " + reviewsJsonString);

                if (reviewsJsonString != null) {
                    try {
                        reviews = MoviesJsonUtils.
                                getReviewsFromJson(reviewsJsonString);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (reviews != null)
                        return reviews;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Review[] reviews) {
            mReviewProgressBar.setVisibility(View.INVISIBLE);
            if (reviews != null) {
                mMovie.setReviews(reviews);
                mReviewAdapter.setReviewsData(reviews); //updates recycler view
                showRecyclerView(R.id.rc_reviews);
            } else {
                Log.d("reviews","vazio");
                showErrorView(R.id.rc_reviews);
            }
        }
    }
}
