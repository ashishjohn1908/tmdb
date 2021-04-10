package com.app.tmdb.home;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.app.tmdb.base.BaseNetworkSubscriber;
import com.app.tmdb.base.BaseRepository;
import com.app.tmdb.model.MovieListModel;
import com.app.tmdb.modules.ApiService;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class HomeRepository extends BaseRepository {

    private Application mApplication;
    private ApiService mApiService;
    private MutableLiveData<MovieListModel> mMovieLiveData = new MutableLiveData<>();
    private MutableLiveData<MovieListModel> mPopularLiveData = new MutableLiveData<>();
    private MutableLiveData<MovieListModel> mTopRatedLiveData = new MutableLiveData<>();

    @Inject
    HomeRepository(ApiService apiService, Application application) {
        mApiService = apiService;
        mApplication = application;
    }

    public void fetchUpcomingMovies(int pageNo) {

        addSubscription(mApiService.getUpcomingMovieList(pageNo).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseNetworkSubscriber<MovieListModel>(mApplication) {
                    @Override
                    public void onNext(MovieListModel movieModel) {
                        super.onNext(movieModel);

                        mMovieLiveData.setValue(movieModel);
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

    public MutableLiveData<MovieListModel> getMovieLiveData() {
        return mMovieLiveData;
    }

    public void fetchPopularMovies(int pageNo) {

        addSubscription(mApiService.getPopularMovieList(pageNo).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseNetworkSubscriber<MovieListModel>(mApplication) {
                    @Override
                    public void onNext(MovieListModel movieModel) {
                        super.onNext(movieModel);

                        mPopularLiveData.setValue(movieModel);
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

    public MutableLiveData<MovieListModel> getPopularLiveData() {
        return mPopularLiveData;
    }

    public void fetchTopRatedMovies(int pageNo) {

        addSubscription(mApiService.getTopRatedMovieList(pageNo).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseNetworkSubscriber<MovieListModel>(mApplication) {
                    @Override
                    public void onNext(MovieListModel movieModel) {
                        super.onNext(movieModel);

                        mTopRatedLiveData.setValue(movieModel);
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

    public MutableLiveData<MovieListModel> getTopRatedLiveData() {
        return mTopRatedLiveData;
    }

}
