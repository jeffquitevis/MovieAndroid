package com.example.jeff.moviedatabase;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeff on 3/6/2016.
 */
public class MovieListFragment extends Fragment {


    public interface OnTransferMovieData {

        public void setMovieDetails(List<Movie> movieList, int position);
    }

    private OnTransferMovieData transferMovieData;
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

        new DownloadJsonMovie().execute(PAGE);


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            transferMovieData = (OnTransferMovieData) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());

        }
    }


    public void initialize() {
        movieList = (ListView) view.findViewById(R.id.lvMovieList);

    }

//////////////DOWNLOAD JSON MOVIE///////////////////

    public class DownloadJsonMovie extends AsyncTask<Integer, Void, String> {

        private MovieAdapter movieAdapter;

        @Override
        protected String doInBackground(Integer... params) {

            String jsonData = null;
            HttpURLConnection urlConnection = null;
            BufferedReader bufferedReader = null;

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
            final List<Movie> tempMovieList = parseJsonData(result);
            movieAdapter = new MovieAdapter(getContext(),R.layout.listview_layout,R.id.tvOriginalTitle,tempMovieList);
            movieList.setAdapter(movieAdapter);
            movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    transferMovieData.setMovieDetails(tempMovieList, position);
                }
            });

        }

        public List<Movie> parseJsonData(String result){
            List<Movie> movieArrayList = new ArrayList<Movie>();
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

            return  movieArrayList;
        }
    }
}
