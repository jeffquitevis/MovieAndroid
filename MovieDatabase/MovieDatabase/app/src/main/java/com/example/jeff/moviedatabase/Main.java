package com.example.jeff.moviedatabase;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class Main extends AppCompatActivity implements MovieListFragment.OnTransferMovieData {

    private DetailMovieFragment detailMovieFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        detailMovieFragment = (DetailMovieFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
    }

    @Override
    public void setMovieDetails(List<Movie> movieList, int position) {
        detailMovieFragment.setDetails(movieList.get(position).getOriginalTitle(),movieList.get(position).getReleaseDate(),movieList.get(position).getOverview(),
                movieList.get(position).getPosterPath(),movieList.get(position).getBackdropPath());
    }
}
