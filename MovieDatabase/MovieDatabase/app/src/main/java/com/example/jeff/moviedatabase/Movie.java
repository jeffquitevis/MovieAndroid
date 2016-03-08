package com.example.jeff.moviedatabase;

/**
 * Created by jeff on 3/6/2016.
 */
public class Movie {

    private String originalTitle;
    private String overview;
    private String releaseDate;
    private String backdropPath;
    private String posterPath;


    public Movie(String originalTitle, String overview, String releaseDate, String backdropPath, String posterPath){
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
    }


    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
