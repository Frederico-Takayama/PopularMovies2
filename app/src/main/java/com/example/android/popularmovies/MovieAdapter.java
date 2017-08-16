package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.data.Movie;
import com.squareup.picasso.Picasso;

/**
 * This class generates an Adapter to the RecyclerView
 *
 * Created by lsitec335.takayama on 16/08/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    final private String POSTER_URL_BASE = "http://image.tmdb.org/t/p/";
    final private String POSTER_SIZE_PATH_URL = "w185"; //w500

    private Movie[] mMovies;

    public MovieAdapter(){

    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        int layoutIdForGridItem = R.layout.movie_grid_item;

        View view = inflater.inflate(layoutIdForGridItem, parent, shouldAttachToParentImmediately);
        MovieAdapterViewHolder viewHolder = new MovieAdapterViewHolder(view);

        return viewHolder;
    }

    /**
        Binds desired content to viewHolder, according to its position
     */
    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {

        //debug
        //String posterUrl = "http://image.tmdb.org/t/p/w342//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";
        String posterUrl = POSTER_URL_BASE + POSTER_SIZE_PATH_URL +
                mMovies[position].getPosterUrl();
        Log.d("viewHolder","posterUrl: " + posterUrl);

        holder.bind(posterUrl);
    }

    @Override
    public int getItemCount() {
        if(mMovies != null)
            return mMovies.length;
        else
            return 0;
    }

    /**
        Set new content of movies for MovieAdapter;
     */
    public void setMoviesData(Movie[] movies){
        mMovies = movies;
        notifyDataSetChanged();
    }

    /**
     * MovieAdapterViewHolder is the inner class ViewHolder for this Adapter
     */
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView mMoviePoster;
        Context context;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);

            mMoviePoster = (ImageView) itemView.findViewById(R.id.iv_poster);

            context = itemView.getContext();

        }

        @Override
        public void onClick(View view) {
            Log.d(MovieAdapterViewHolder.this.toString(), "youclicked!");
        }

        void bind(String posterUrl){

            Picasso.with(context).setLoggingEnabled(true);
            Log.d(this.toString(), "try load a image using Picasso");
            //example:
            // Picasso.with(context).load("http://i.imgur.com/yWyBaYk.jpg").into(mImageView);
            Picasso.with(context).load(posterUrl).into(mMoviePoster);

        }
    }

}
