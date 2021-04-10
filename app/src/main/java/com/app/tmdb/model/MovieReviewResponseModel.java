package com.app.tmdb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieReviewResponseModel {

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    @SerializedName("results")
    @Expose
    private ArrayList<Reviews> mReviews;


    public int getPage() {
        return page;
    }

    public ArrayList<Reviews> getReviews() {
        return mReviews;
    }

    public int getTotalPages() {
        return totalPages;
    }
}