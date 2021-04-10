package com.app.tmdb.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class BaseRepository {

    private CompositeDisposable mCompositeSubscription = new CompositeDisposable();

    protected void addSubscription(DisposableObserver subscription) {
        mCompositeSubscription.add(subscription);
    }

    public void clearSubscription() {
        mCompositeSubscription.clear();
    }
}
