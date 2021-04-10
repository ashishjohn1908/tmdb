package com.app.tmdb.detail;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.app.tmdb.base.BaseNetworkSubscriber;
import com.app.tmdb.base.BaseRepository;
import com.app.tmdb.model.MovieDetailModel;
import com.app.tmdb.model.MovieReviewResponseModel;
import com.app.tmdb.modules.ApiService;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailRepository extends BaseRepository {

    private Application mApplication;
    private ApiService mApiService;
    private MutableLiveData<MovieDetailModel> mMovieDetailLiveData = new MutableLiveData<>();
    private MutableLiveData<MovieReviewResponseModel> mMovieReviewLiveData = new MutableLiveData<>();

    @Inject
    MovieDetailRepository(ApiService apiService, Application application) {
        mApiService = apiService;
        mApplication = application;
    }

    public void fetchMovieDetail(int movieId) {

        addSubscription(mApiService.getMovieDetail(movieId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseNetworkSubscriber<MovieDetailModel>(mApplication) {
                    @Override
                    public void onNext(@NotNull MovieDetailModel movieDetailModel) {
                        super.onNext(movieDetailModel);

                        mMovieDetailLiveData.setValue(movieDetailModel);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                }));

    }

    public MutableLiveData<MovieDetailModel> getMovieDetailLiveData() {
        return mMovieDetailLiveData;
    }

    public void fetchMovieReviews(int movieId) {

        addSubscription(mApiService.getMovieReviews(movieId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseNetworkSubscriber<MovieReviewResponseModel>(mApplication) {
                    @Override
                    public void onNext(@NotNull MovieReviewResponseModel movieReviewModel) {
                        super.onNext(movieReviewModel);

                        mMovieReviewLiveData.setValue(movieReviewModel);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                }));

    }

    public MutableLiveData<MovieReviewResponseModel> getMovieReviewLiveData() {
        return mMovieReviewLiveData;
    }
}
