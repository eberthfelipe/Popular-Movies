package com.android.udacity.popularmovies.View;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.udacity.popularmovies.MVP.MovieContract;
import com.android.udacity.popularmovies.Model.Movie;
import com.android.udacity.popularmovies.R;

import java.util.ArrayList;

public class GridAdapter extends RecyclerView.Adapter<MovieGridHolder> {

    private ArrayList<Movie> movieArrayList;
    final private MovieContract.ListItemClickListener mListItemOnClickListener;

    GridAdapter(ArrayList<Movie> movieArrayList, MovieContract.ListItemClickListener mListItemOnClickListener){
        this.movieArrayList = new ArrayList<>(movieArrayList);
        this.mListItemOnClickListener = mListItemOnClickListener;
    }

    @Override
    public MovieGridHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_view, parent, false);
        return new MovieGridHolder(view, mListItemOnClickListener);
    }

    @Override
    public void onBindViewHolder(MovieGridHolder holder, int position) {
        holder.setMovieTitle(movieArrayList.get(position).getTitle());
        //TODO 2: implement picasso fetch with MVP
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

}
