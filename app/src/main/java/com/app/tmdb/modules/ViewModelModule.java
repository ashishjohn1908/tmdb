package com.app.tmdb.modules;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.app.tmdb.base.BaseViewModelFactory;
import com.app.tmdb.detail.MovieDetailViewModel;
import com.app.tmdb.home.HomeViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


/*
 *
 * Module class that will provide view model class object to inject via Dagger.
 */

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(BaseViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel homeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel.class)
    abstract ViewModel movieDetailViewModel(MovieDetailViewModel homeViewModel);

}
