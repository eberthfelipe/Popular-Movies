package com.android.udacity.popularmovies.View;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.R;

public class VideoGridHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView mTextViewVideoName;
    private TextView mTextViewVideoType;
    private MovieContract.ListItemClickListener mListItemOnClickListener;

    public VideoGridHolder(@NonNull View itemView, MovieContract.ListItemClickListener listItemClickListener) {
        super(itemView);
        mTextViewVideoName = itemView.findViewById(R.id.tv_movie_video_name);
        mTextViewVideoType = itemView.findViewById(R.id.tv_movie_video_type);
        mListItemOnClickListener = listItemClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int clickPosition = getAdapterPosition();
        Log.d("VideoGridHolder", "teste onClick: " + clickPosition);
    }

    public void setVideoName(String videoName){
        mTextViewVideoName.setText(videoName);
    }

    public void setVideoType(String videoType){
        mTextViewVideoType.setText(videoType);
    }
}
