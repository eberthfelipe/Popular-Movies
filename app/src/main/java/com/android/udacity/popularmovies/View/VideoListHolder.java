package com.android.udacity.popularmovies.View;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.R;

public class VideoListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView mTextViewVideoName;
    private TextView mTextViewVideoType;
    private ImageView mImageViewVideoThumbnail;
    private MovieContract.ListItemClickListener mListItemOnClickListener;

    public VideoListHolder(@NonNull View itemView, MovieContract.ListItemClickListener listItemClickListener) {
        super(itemView);
        mTextViewVideoName = itemView.findViewById(R.id.tv_movie_video_name);
        mTextViewVideoType = itemView.findViewById(R.id.tv_movie_video_type);
        mImageViewVideoThumbnail = itemView.findViewById(R.id.iv_movie_video_thumbnail);
        mListItemOnClickListener = listItemClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int clickPosition = getAdapterPosition();
        Log.d("VideoListHolder", "teste onClick: " + clickPosition);
        mListItemOnClickListener.onListItemClick(clickPosition);
    }

    public void setVideoName(String videoName){
        mTextViewVideoName.setText(videoName);
    }

    public void setVideoType(String videoType){
        mTextViewVideoType.setText(videoType);
    }

    public ImageView getImageVideoThumbnail(){
        return mImageViewVideoThumbnail;
    }
}
