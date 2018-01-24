package com.example.android.popularmovies;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.data.Review;

/**
 * Created by lsitec335.takayama on 29/01/18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder>{

    GridItemClickListener mOnClickListener;

    /**
     * Defines a method signature for treat click events
     */
    public interface GridItemClickListener {
        void onGridReviewItemClick(int clickedItemIndex);
    }

    /**
     * Constructor
     * Receives method within GridItemClickListener to treat on click events
     */
    public ReviewAdapter(GridItemClickListener listener) {
        mOnClickListener = listener;
    }

    private Review[] mReviews;

    //method used outside from this class
    public Review getReview(int index){
        return mReviews[index];
    }

    /**
     * Set new content of movies for MovieAdapter;
     * Used in AssyncTask Function in order to updates RecyclerView
     */
    public void setReviewsData(Review[] reviews) {
        mReviews = reviews; //updates reviews list
        notifyDataSetChanged();
    }

    /**
     * Inflates a ViewHolder
     */
    @Override
    public ReviewAdapter.ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        int layoutIdForGridItem = R.layout.review_grid_item;

        View view = inflater.inflate(layoutIdForGridItem, parent, shouldAttachToParentImmediately);
        ReviewAdapterViewHolder viewHolder = new ReviewAdapterViewHolder(view);

        return viewHolder;
    }

    /**
     * Binds desired content to viewHolder, according to its position
     */
    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewAdapterViewHolder holder, int position) {
        //access method bind from viewHolder
        holder.bind(mReviews[position]);
    }

    @Override
    public int getItemCount() {
        if( mReviews != null)
            return mReviews.length;
        else
            return 0;
    }

    /**
     * ReviewAdapterViewHolder is the inner class ViewHolder for this Adapter
     * It's the class for the grid activity
     */
    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mAuthorTextView;
        private TextView mContentTextView;
        private TextView mUrlTextView;
        Context context;

        public ReviewAdapterViewHolder(View itemView) {
            super(itemView);

            mAuthorTextView = (TextView) itemView.findViewById(R.id.review_author);
            mContentTextView = (TextView) itemView.findViewById(R.id.review_content);
            mUrlTextView = (TextView) itemView.findViewById(R.id.review_url);
            context = itemView.getContext();
        }

        /**
         * Invokes mOnClickListener from adapter class.
         */
        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onGridReviewItemClick(clickedPosition);
        }

        /**
         * This method is called in adapter to bind specific values into viewHolder
         */
        void bind(Review review) {
            String author = String.format(
                    context.getString(R.string.author_field), review.getAuthor());
            mAuthorTextView.setText(author);
            mContentTextView.setText(review.getContent());
            mUrlTextView.setText(review.getUrlString());
        }
    }
}
