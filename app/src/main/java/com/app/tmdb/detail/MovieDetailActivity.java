package com.app.tmdb.detail;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tmdb.BuildConfig;
import com.app.tmdb.R;
import com.app.tmdb.Utils.Utils;
import com.app.tmdb.base.AppApplication;
import com.app.tmdb.base.AppConstant;
import com.app.tmdb.base.BaseViewModelFactory;
import com.app.tmdb.model.MovieDetailModel;
import com.app.tmdb.model.MovieReviewResponseModel;
import com.app.tmdb.model.Reviews;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

import javax.inject.Inject;

public class MovieDetailActivity extends AppCompatActivity {

    @Inject
    public BaseViewModelFactory mViewModelFactory;

    private AppCompatImageView mImageViewPoster, mImageViewBackdrop;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private TextView mTvMovieTitle, mTvReleaseDate, mTvOverview, mTvNoReview;
    private RecyclerView mRecyclerViewReviews;
    private RatingBar mRatingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);
        setupToolBar();
        initViews();
        AppApplication.getApp().getDaggerAppComponent().provideIn(this);

        MovieDetailViewModel movieDetailViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MovieDetailViewModel.class);

        int movieId = getIntent().getExtras() != null ? getIntent().getExtras().getInt(AppConstant.KEY_MOVIE_ID, -1) : -1;

        if (movieDetailViewModel.getMovieDetailLiveData() != null)
            movieDetailViewModel.getMovieDetailLiveData().observe(this, this::observeData);

        if (movieDetailViewModel.getMovieReviewLiveData() != null)
            movieDetailViewModel.getMovieReviewLiveData().observe(this, this::observeReviewData);

        movieDetailViewModel.getMovieDetail(movieId);
    }

    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void initViews() {
        mImageViewPoster = findViewById(R.id.iv_poster_image_details);
        mImageViewBackdrop = findViewById(R.id.backdrop_image_view);
        mCollapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        mTvMovieTitle = findViewById(R.id.text_view_movie_title);
        mRatingBar = findViewById(R.id.rating_bar_movie_avg);
        mTvReleaseDate = findViewById(R.id.text_view_release_date);
        mTvOverview = findViewById(R.id.text_view_overview);
        mRecyclerViewReviews = findViewById(R.id.recycler_view_reviews);
        mTvNoReview = findViewById(R.id.tv_no_review);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void observeData(MovieDetailModel movieDetailModel) {

        Utils.setImage(BuildConfig.BackdropBaseUrl + movieDetailModel.getBackdrop(), mImageViewBackdrop);
        Utils.setImage(BuildConfig.PosterBaseUrl + movieDetailModel.getMoviePoster(), mImageViewPoster);

        mCollapsingToolbarLayout.setTitleEnabled(false);
        mTvMovieTitle.setText(movieDetailModel.getMovieTitle());
        mRatingBar.setRating(movieDetailModel.getVoterAverage() / 2);
        mTvReleaseDate.setText(movieDetailModel.getMovieReleaseDate());
        mTvOverview.setText(movieDetailModel.getMovieOverview());
    }

    private void observeReviewData(MovieReviewResponseModel movieReviewResponseModel) {
        setupReviews(movieReviewResponseModel.getReviews());
    }

    private void setupReviews(ArrayList<Reviews> reviews) {
        if (reviews != null && !reviews.isEmpty()) {
            ReviewsAdapter vReviewsAdapter = new ReviewsAdapter(reviews);
            mRecyclerViewReviews.setHasFixedSize(true);
            mRecyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerViewReviews.setAdapter(vReviewsAdapter);
        } else {
            mRecyclerViewReviews.setVisibility(View.GONE);
            mTvNoReview.setVisibility(View.VISIBLE);
        }
    }
}
