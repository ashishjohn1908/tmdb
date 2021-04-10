package com.app.tmdb.modules;


import javax.inject.Singleton;

import dagger.Component;

/*
 * Component class that define all the modules used by Dagger .
 */

@Singleton
@AppScope
@Component(modules = {
        ApplicationModule.class,
        ViewModelModule.class,
        ApiModule.class,
        NetworkModule.class
})
public interface ApplicationComponent extends Injector {
}
