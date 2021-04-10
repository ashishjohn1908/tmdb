package com.app.tmdb.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailModel {

    @SerializedName("original_title")
    private String movieTitle;

    @SerializedName("overview")
    private String movieOverview;

    @SerializedName("release_date")
    private String movieReleaseDate;

    @SerializedName("poster_path")
    private String moviePoster;

    @SerializedName("vote_average")
    private float voterAverage;

    @SerializedName("backdrop_path")
    private String backdrop;

    @SerializedName("id")
    private int movieId;

    private boolean isFavourite;

    private ArrayList<Reviews> mReviews;

    private List<Trailers> mTrailers;

    public MovieDetailModel() {
    }

    public MovieDetailModel(int movieId, String movieTitle, String movieOverview, String movieReleaseDate, String moviePoster, String backdrop, float voterAverage, boolean isFavourite) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieOverview = movieOverview;
        this.movieReleaseDate = movieReleaseDate;
        this.moviePoster = moviePoster;
        this.backdrop = backdrop;
        this.voterAverage = voterAverage;
        this.isFavourite = isFavourite;
    }

    public MovieDetailModel(String movieTitle, String movieOverview, String movieReleaseDate, String moviePoster, String backdrop, float voterAverage, boolean isFavourite) {
        this.movieTitle = movieTitle;
        this.movieOverview = movieOverview;
        this.movieReleaseDate = movieReleaseDate;
        this.moviePoster = moviePoster;
        this.backdrop = backdrop;
        this.voterAverage = voterAverage;
        this.isFavourite = isFavourite;
    }

    public ArrayList<Reviews> getReviews() {
        return mReviews;
    }

    public void setReviews(ArrayList<Reviews> reviews) {
        mReviews = reviews;
    }

    public List<Trailers> getTrailers() {
        return mTrailers;
    }

    public void setTrailers(List<Trailers> trailers) {
        mTrailers = trailers;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public float getVoterAverage() {
        return voterAverage;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public String getBackdrop() {
        return backdrop;
    }
}
