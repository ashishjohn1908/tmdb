package com.app.tmdb.base;

import android.content.Context;
import android.util.Log;

import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableObserver;

/*
 * This class is to distinguish network error type and to handle them.
 */

public class BaseNetworkSubscriber<T> extends DisposableObserver<T> {

    private Context mContext;

    public BaseNetworkSubscriber(Context context) {
        mContext = context;
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof RetrofitException) {
            final RetrofitException error = (RetrofitException) e;

            if (error.getKind() == RetrofitException.Kind.NETWORK) {
                if (error.getCause() instanceof SocketTimeoutException) {
                    onSocketTimeoutException(e);
                } else {
                    onNetworkError();
                }
            } else if (error.getKind() == RetrofitException.Kind.HTTP) {
                try {

                    onCustomServerError();

                } catch (Exception re) {
                    onUnknownError(re);
                }
            } else {
                onUnknownError(e);
            }
        } else {
            onUnknownError(e);
        }
    }

    public void onNetworkError() {
    }

    public void onUnknownError(Throwable e) {
        if (e != null) {
            e.printStackTrace();
        }
    }

    public void onSocketTimeoutException(Throwable e) {

    }

    public void onCustomServerError() {
    }

    @Override
    public void onNext(T t) {
        Log.d(getClass().getSimpleName(), "onNext");
    }
}
