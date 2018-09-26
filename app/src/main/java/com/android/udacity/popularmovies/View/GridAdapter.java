package com.android.udacity.popularmovies.View;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Model.Movie;
import com.android.udacity.popularmovies.R;

import java.util.ArrayList;

class GridAdapter extends RecyclerView.Adapter<MovieGridHolder> {

    private ArrayList<Movie> mMovieArrayList;
    final private MovieContract.ListItemClickListener mListItemOnClickListener;
    private MovieContract.MoviesPresenter mMoviesPresenter;
    //DONE: Check if it is better to add a presenter object here

    GridAdapter(ArrayList<Movie> movieArrayList, MovieContract.ListItemClickListener mListItemOnClickListener, MovieContract.MoviesPresenter moviesPresenter){
        this.mMovieArrayList = new ArrayList<>(movieArrayList);
        this.mListItemOnClickListener = mListItemOnClickListener;
        this.mMoviesPresenter = moviesPresenter;
    }

    GridAdapter(MovieContract.ListItemClickListener mListItemOnClickListener, MovieContract.MoviesPresenter moviesPresenter){
        this.mMovieArrayList = new ArrayList<>();
        this.mListItemOnClickListener = mListItemOnClickListener;
        this.mMoviesPresenter = moviesPresenter;
    }

    @NonNull
    @Override
    public MovieGridHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_view, parent, false);
        return new MovieGridHolder(view, mListItemOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieGridHolder holder, int position) {
        if(mMovieArrayList != null) {
            holder.setMovieTitle(mMovieArrayList.get(position).getTitle());
            mMoviesPresenter.retrieveImageSrc(mMovieArrayList.get(position).getPosterPath(), holder.getMovieImageView());
            //DONE 2: implement picasso fetch with MVP
        }
    }

    @Override
    public int getItemCount() {
        if(mMovieArrayList == null){
            return 0;
        }
        return mMovieArrayList.size();
    }

    public ArrayList<Movie> getMovieArrayList(){
        if(mMovieArrayList == null){
            return new ArrayList<>();
        }
        return mMovieArrayList;
    }
}
