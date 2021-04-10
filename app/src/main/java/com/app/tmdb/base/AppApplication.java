package com.app.tmdb.base;

import android.app.Application;

import com.app.tmdb.modules.ApplicationComponent;
import com.app.tmdb.modules.ApplicationModule;
import com.app.tmdb.modules.DaggerApplicationComponent;

public class AppApplication extends Application {

    private static AppApplication mainApplication;
    private ApplicationComponent mAppComponent;

    public static AppApplication getApp() {

        return mainApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mainApplication = this;
        System.setProperty("http.keepAlive", "false");

        mAppComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();

    }

    public ApplicationComponent getDaggerAppComponent() {
        return mAppComponent;
    }
}
