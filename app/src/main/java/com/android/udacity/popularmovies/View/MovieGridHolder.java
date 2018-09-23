package com.android.udacity.popularmovies.View;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.R;

public class MovieGridHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private ImageView mImageMovie;
    private TextView mTextViewTitle;
    private MovieContract.ListItemClickListener mListItemOnClickListener;

    MovieGridHolder(View itemView, MovieContract.ListItemClickListener listItemClickListener) {
        super(itemView);
        mImageMovie = itemView.findViewById(R.id.iv_movie_poster);
        mTextViewTitle = itemView.findViewById(R.id.tv_movie_title);
        mListItemOnClickListener = listItemClickListener;
        itemView.setOnClickListener(this);
    }

    public ImageView getMovieImageView(){
        return mImageMovie;
    }

    public void setMovieTitle(String text){
        mTextViewTitle.setText(text);
    }

    @Override
    public void onClick(View v) {
        int clickPosition = getAdapterPosition();
        mListItemOnClickListener.onListItemClick(clickPosition, mImageMovie.getDrawable());
    }
}
