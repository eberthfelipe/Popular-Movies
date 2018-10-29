package com.android.udacity.popularmovies.View;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.R;

public class ReviewGridHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mTextViewReviewAuthor;
    private TextView mTextViewReviewContent;
    private MovieContract.ListItemClickListener mListItemOnClickListener;

    public ReviewGridHolder(@NonNull View itemView, MovieContract.ListItemClickListener listItemClickListener) {
        super(itemView);
        mTextViewReviewAuthor = itemView.findViewById(R.id.tv_review_author);
        mTextViewReviewContent = itemView.findViewById(R.id.tv_review_content);
        mListItemOnClickListener = listItemClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int clickPosition = getAdapterPosition();
        Log.d("ReviewGridHolder", "teste onClick: " + clickPosition);
    }

    public void setReviewAuthor(String reviewAuthor){
        mTextViewReviewAuthor.setText(reviewAuthor);
    }

    public void setReviewContent(String reviewContent){
        mTextViewReviewContent.setText(reviewContent);
    }
}
