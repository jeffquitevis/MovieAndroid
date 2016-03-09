package com.example.jeff.moviedatabase;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Created by jeff on 3/6/2016.
 */
public class DetailMovieFragment extends Fragment {


    private TextView originalTitle,releaseDate,overView;
    private ImageView posterPath;
    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detailmovie_fragment_layout,container,false);
        initialize();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void setDetails(String originalTitle, String releaseDate, String overview, String posterPath, String backdropPath){
        this.originalTitle.setText(originalTitle);
        this.releaseDate.setText(releaseDate);
        this.overView.setText(overview);
        new DownloadImage().execute(posterPath);


    }
    public void initialize(){
        originalTitle = (TextView) view.findViewById(R.id.tvOriginalTitle);
        releaseDate = (TextView) view.findViewById(R.id.tvReleaseDate);
        overView = (TextView) view.findViewById(R.id.tvOverview);
        posterPath = (ImageView) view.findViewById(R.id.ivPoster);
    }


    public class DownloadImage extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {
            String posterPathUrl = params[0];
            Bitmap bitmapPoster = null;

            try{
                InputStream inputStream = new java.net.URL(posterPathUrl).openStream();
                bitmapPoster = BitmapFactory.decodeStream(inputStream);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return bitmapPoster;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            posterPath.setImageBitmap(bitmap);
        }
    }
}
