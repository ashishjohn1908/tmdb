package com.app.tmdb.modules;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/*
 * Module class that will provide application class object to inject via Dagger.
 */

@Module
public class ApplicationModule {


    public Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    public Application getApplication() {
        return mApplication;
    }

    @Provides
    Context getApplicationContext() {
        return mApplication;
    }

}
