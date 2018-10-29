package com.android.udacity.popularmovies.View;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Object.MovieVideo;
import com.android.udacity.popularmovies.R;

public class GridAdapterVideo extends RecyclerView.Adapter<VideoGridHolder> {

    private MovieVideo[] movieVideos;
    final private MovieContract.ListItemClickListener mListItemOnClickListener;

    public GridAdapterVideo(MovieVideo[] movieVideos, MovieContract.ListItemClickListener mListItemOnClickListener) {
        this.movieVideos = movieVideos;
        this.mListItemOnClickListener = mListItemOnClickListener;
    }

    @NonNull
    @Override
    public VideoGridHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.video_view, parent, false);
        return new VideoGridHolder(view, mListItemOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoGridHolder videoGridHolder, int i) {
        if(movieVideos != null){
            videoGridHolder.setVideoName(movieVideos[i].getName());
            videoGridHolder.setVideoType(movieVideos[i].getType());
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
