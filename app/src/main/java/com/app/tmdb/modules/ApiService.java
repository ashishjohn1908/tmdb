package com.app.tmdb.modules;


/*
 *
 * This is the service interface in which all the method define which will used for data.
 */

import com.app.tmdb.model.MovieDetailModel;
import com.app.tmdb.model.MovieListModel;
import com.app.tmdb.model.MovieReviewResponseModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie/upcoming")
    Observable<MovieListModel> getUpcomingMovieList(
            @Query("page") int page
    );

    @GET("movie/top_rated")
    Observable<MovieListModel> getTopRatedMovieList(
            @Query("page") int page
    );

    @GET("movie/popular")
    Observable<MovieListModel> getPopularMovieList(
            @Query("page") int page
    );

    @GET("movie/{movie_id}")
    Observable<MovieDetailModel> getMovieDetail(@Path("movie_id") int id);

    @GET("movie/{movie_id}/reviews")
    Observable<MovieReviewResponseModel> getMovieReviews(@Path("movie_id") int id);

}
