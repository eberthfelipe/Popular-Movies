package com.android.udacity.popularmovies.View;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Model.PicassoModelSingleton;
import com.android.udacity.popularmovies.Object.MovieVideo;
import com.android.udacity.popularmovies.R;

public class GridAdapterVideo extends RecyclerView.Adapter<VideoListHolder> {

    private MovieVideo[] movieVideos;
    private MovieContract.MovieDetailPresenter mMovieDetailPresenter;
    final private MovieContract.ListItemClickListener mListItemOnClickListener;

    public GridAdapterVideo(MovieVideo[] movieVideos, MovieContract.MovieDetailPresenter movieDetailPresenter, MovieContract.ListItemClickListener mListItemOnClickListener) {
        this.movieVideos = movieVideos;
        this.mMovieDetailPresenter = movieDetailPresenter;
        this.mListItemOnClickListener = mListItemOnClickListener;
    }

    @NonNull
    @Override
    public VideoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.video_view, parent, false);
        return new VideoListHolder(view, mListItemOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoListHolder videoListHolder, int i) {
        if(movieVideos != null){
            videoListHolder.setVideoName(movieVideos[i].getName());
            videoListHolder.setVideoType(movieVideos[i].getType());
            mMovieDetailPresenter.retrieveImageSrc(movieVideos[i].getKey(), videoListHolder.getImageVideoThumbnail(), PicassoModelSingleton.TYPE_VIDEO);
        }
    }

    @Override
    public int getItemCount() {
        if(movieVideos == null){
            return 0;
        }
        return movieVideos.length;
    }
}
