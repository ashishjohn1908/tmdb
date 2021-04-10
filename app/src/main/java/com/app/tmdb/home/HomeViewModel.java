package com.app.tmdb.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.app.tmdb.Utils.Utils;
import com.app.tmdb.db.MovieListDbHelper;
import com.app.tmdb.model.MovieListModel;
import com.app.tmdb.model.Result;
import com.hadilq.liveevent.LiveEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeViewModel extends AndroidViewModel {

    private Application mAppContext;
    private HomeRepository mHomeMeRepository;
    private LiveEvent<MovieListModel> mMovieLiveData = new LiveEvent<>();
    private MutableLiveData<MovieListModel> mPopularMovieListLiveData = new MutableLiveData<>();
    private MutableLiveData<MovieListModel> mTopRatedMovieListLiveData = new MutableLiveData<>();

    @Inject
    public HomeViewModel(@NonNull Application application, HomeRepository homeRepository) {
        super(application);

        mHomeMeRepository = homeRepository;
        mAppContext = application;
        observeData();
    }

    private void observeData() {
        mMovieLiveData.addSource(mHomeMeRepository.getMovieLiveData(), movieModel -> {
            mMovieLiveData.setValue(movieModel);

            if (movieModel != null && movieModel.getResults() != null)
                new MovieListDbHelper().saveMovieList(movieModel.getResults(), mAppContext);
        });

        mMovieLiveData.addSource(mHomeMeRepository.getPopularLiveData(), movieModel -> mPopularMovieListLiveData.setValue(movieModel));

        mMovieLiveData.addSource(mHomeMeRepository.getTopRatedLiveData(), movieModel -> mTopRatedMovieListLiveData.setValue(movieModel));
    }

    public void getUpcomingList(int pageNo) {
        if (Utils.checkNetwork(mAppContext))
            mHomeMeRepository.fetchUpcomingMovies(pageNo);
        else {
            MovieListModel movieModel = new MovieListModel();

            List<Result> results = new MovieListDbHelper().getMovieList(mAppContext);
            movieModel.setResults((ArrayList<Result>) results);

            mMovieLiveData.setValue(movieModel);
        }
    }

    MutableLiveData<MovieListModel> getMovieListLiveData() {
        return mMovieLiveData;
    }

    public void getMostPopularList(int pageNo) {
        mHomeMeRepository.fetchPopularMovies(pageNo);
    }

    MutableLiveData<MovieListModel> getMostPopularMovieListLiveData() {
        return mPopularMovieListLiveData;
    }

    public void getTopRatedList(int pageNo) {
        mHomeMeRepository.fetchTopRatedMovies(pageNo);
    }

    MutableLiveData<MovieListModel> getTopRatedMovieListLiveData() {
        return mTopRatedMovieListLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mHomeMeRepository.clearSubscription();
    }
}
