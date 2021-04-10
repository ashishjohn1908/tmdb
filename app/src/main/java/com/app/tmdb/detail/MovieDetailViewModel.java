package com.app.tmdb.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.app.tmdb.model.MovieDetailModel;
import com.app.tmdb.model.MovieReviewResponseModel;
import com.hadilq.liveevent.LiveEvent;

import javax.inject.Inject;

public class MovieDetailViewModel extends AndroidViewModel {

    private Application mAppContext;
    private MovieDetailRepository mMovieDetailRepository;
    private LiveEvent<MovieDetailModel> mMovieDetailLiveData = new LiveEvent<>();
    private MutableLiveData<MovieReviewResponseModel> mMovieReviewLiveData = new MutableLiveData<>();

    @Inject
    public MovieDetailViewModel(@NonNull Application application, MovieDetailRepository movieDetailRepository) {
        super(application);
        mMovieDetailRepository = movieDetailRepository;
        mAppContext = application;

        observeData();
    }

    private void observeData() {
        mMovieDetailLiveData.addSource(mMovieDetailRepository.getMovieDetailLiveData(), movieModel -> mMovieDetailLiveData.setValue(movieModel));

        mMovieDetailLiveData.addSource(mMovieDetailRepository.getMovieReviewLiveData(), movieModel -> mMovieReviewLiveData.setValue(movieModel));

    }

    public void getMovieDetail(int movieId) {
        mMovieDetailRepository.fetchMovieDetail(movieId);
        mMovieDetailRepository.fetchMovieReviews(movieId);
    }

    MutableLiveData<MovieDetailModel> getMovieDetailLiveData() {
        return mMovieDetailLiveData;
    }

    MutableLiveData<MovieReviewResponseModel> getMovieReviewLiveData() {
        return mMovieReviewLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        mMovieDetailRepository.clearSubscription();
    }
}
