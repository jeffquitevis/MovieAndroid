package com.example.jeff.moviedatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeff on 3/6/2016.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    private Context context;
    private List<Movie> movies;
    private int resource;
    private TextView originalTitle;

    public MovieAdapter(Context context, int resource, int textViewResourceId, List<Movie> movies ) {
        super(context, resource, textViewResourceId, movies);
        this.context = context;
        this.resource = resource;
        this.movies = movies;

    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.listview_layout,parent,false);
        Movie temp = movies.get(position);
        originalTitle = (TextView) view.findViewById(R.id.tvOriginalTitle);
        originalTitle.setText(temp.getOriginalTitle());
        return view;
    }
}
