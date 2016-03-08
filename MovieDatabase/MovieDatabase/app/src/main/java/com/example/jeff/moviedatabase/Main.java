package com.example.jeff.moviedatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class Main extends AppCompatActivity implements MovieListFragment.OnTransferMovieData {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void setMovieDetails(List<Movie> movieList, int position) {

    }
}
