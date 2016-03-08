package com.example.jeff.moviedatabase;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeff on 3/6/2016.
 */
public class MovieListFragment extends Fragment {

    private ListView movieList;
    private static final String URL_API = "http://api.themoviedb.org/3/discover/movie";
    private static final String API_KEY = "c8e968bcd558585cd3f858dda44ae89d";
    private int PAGE = 1;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.movielist_fragment_layout, container, false);
        initialize();
        DownloadJsonMovie downloadJsonMovie = new DownloadJsonMovie();
        downloadJsonMovie.execute(PAGE);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void initialize() {
        movieList = (ListView) view.findViewById(R.id.lvMovieList);
    }

//////////////DOWNLOAD JSON MOVIE///////////////////

    public class DownloadJsonMovie extends AsyncTask<Integer, Void, String> {


        private HttpURLConnection urlConnection = null;
        private BufferedReader bufferedReader = null;
        private String jsonData = null;
        private List<Movie> movieArrayList = new ArrayList<Movie>();
        private MovieAdapter movieAdapter;

        @Override
        protected String doInBackground(Integer... params) {


            try {

                URL url = new URL("http://api.themoviedb.org/3/discover/movie?api_key=c8e968bcd558585cd3f858dda44ae89d&page=" + params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "/n");
                    jsonData = stringBuffer.toString();

                    Log.v("JSON", jsonData);
                }

                return jsonData;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            parseJsonData(result);
            movieAdapter = new MovieAdapter(getContext(),R.layout.listview_layout,R.id.tvMovie,movieArrayList);
            movieList.setAdapter(movieAdapter);


        }


        public void parseJsonData(String result){

            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int x = 0; x < jsonArray.length(); x++){
                    JSONObject realJsonObject = jsonArray.getJSONObject(x);
                    movieArrayList.add(new Movie(realJsonObject.getString("original_title"), realJsonObject.getString("overview"), realJsonObject.getString("release_date"), realJsonObject.getString("backdrop_path"), realJsonObject.getString("poster_path")));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
