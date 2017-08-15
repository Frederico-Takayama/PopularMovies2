package com.example.android.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.utilities.MoviesJsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView mImageView;

    TextView testDisplay = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.iv_main_poster);
        Context context = this;
        mImageView.setVisibility(View.VISIBLE);

        testDisplay = (TextView) findViewById(R.id.test_display);

        /*Picasso.with(context).setLoggingEnabled(true);
        //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
        Log.d(this.toString(), "try load a image using Picasso");
        //Picasso.with(context).load("http://i.imgur.com/yWyBaYk.jpg").into(mImageView);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w342//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg")
                .into(mImageView);*/

        URL urlPostersQuery = NetworkUtils.buildUrl(NetworkUtils.SORT_BY_POPULARITY);

        new MoviesQueryTask().execute(urlPostersQuery);
    }


    public class MoviesQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            testDisplay.setText("");
        }

        @Override
        protected String doInBackground(URL... urls) {

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
                        return movies[0].toString();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {
                testDisplay.setText(s);//just for test
            }

        }
    }

}
