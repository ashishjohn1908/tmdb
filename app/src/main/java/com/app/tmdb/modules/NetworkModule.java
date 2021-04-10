package com.app.tmdb.modules;

import android.content.Context;

import com.app.tmdb.BuildConfig;
import com.app.tmdb.base.AppConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 *
 * Module class that will provide instance of variable that are related to network.
 */

@Module
public class NetworkModule {

    Context mContext;

    @Provides
    @AppScope
    OkHttpClient provideOkHttpClientInstance(Context context) {

        mContext = context;

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);
        builder.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request originalRequest = chain.request();

                Request.Builder builder = originalRequest.newBuilder();

                // Add authorization header with updated authorization value to intercepted request
                builder.header("Content-Type", "application/json");

                builder.header(AppConstant.KEY_AUTHORIZATION, "Bearer " + BuildConfig.TMDB_TOKEN);

                Request modifiedRequest = builder.build();

                return chain.proceed(modifiedRequest);
            }
        });
        return builder.build();
    }


    @Provides
    @AppScope
    Retrofit provideRetrofitInstance(OkHttpClient okHttpClient) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.BaseUrl);

        return builder.build();
    }

}