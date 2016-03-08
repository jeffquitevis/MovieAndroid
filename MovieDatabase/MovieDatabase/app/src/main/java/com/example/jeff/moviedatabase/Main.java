package com.example.jeff.moviedatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main extends AppCompatActivity implements MovieListFragment.OnTransferMovieData {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void setMovieDetails(String originalTitle, String releaseDate, String overview, String posterPath, String backdropPath) {

    }
}
