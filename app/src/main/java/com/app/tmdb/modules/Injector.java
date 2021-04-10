package com.app.tmdb.modules;

import com.app.tmdb.detail.MovieDetailActivity;
import com.app.tmdb.home.HomeActivity;

public interface Injector {

    void provideIn(HomeActivity homeActivity);

    void provideIn(MovieDetailActivity movieDetailActivity);
}
