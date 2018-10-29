package com.android.udacity.popularmovies.View;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Object.MovieReview;
import com.android.udacity.popularmovies.R;

public class GridAdapterReview extends RecyclerView.Adapter<ReviewGridHolder> {

    private MovieReview[] movieReviews;
    final private MovieContract.ListItemClickListener mListItemOnClickListener;

    public GridAdapterReview(MovieReview[] movieReviews, MovieContract.ListItemClickListener mListItemOnClickListener) {
        this.movieReviews = movieReviews;
        this.mListItemOnClickListener = mListItemOnClickListener;
    }

    @NonNull
    @Override
    public ReviewGridHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.review_view, parent, false);
        return new ReviewGridHolder(view, mListItemOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewGridHolder reviewGridHolder, int i) {
        if(movieReviews != null){
            reviewGridHolder.setReviewAuthor(movieReviews[i].getAuthor());
            reviewGridHolder.setReviewContent(movieReviews[i].getContent());
        }

    }

    @Override
    public int getItemCount() {
        if(movieReviews == null){
            return 0;
        }
        return movieReviews.length;
    }
}
