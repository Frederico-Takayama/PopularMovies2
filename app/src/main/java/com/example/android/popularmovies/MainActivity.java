package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.iv_main_poster);
        Context context = this;
        mImageView.setVisibility(View.VISIBLE);

        Picasso.with(context).setLoggingEnabled(true);
        //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
        Log.d(this.toString(), "try load a image using Picasso");
        //Picasso.with(context).load("http://i.imgur.com/yWyBaYk.jpg").into(mImageView);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w342//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg")
                .into(mImageView);

    }
}
