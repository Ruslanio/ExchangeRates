package com.example.ruslan.exchangerates.api;

import android.util.Log;

import rx.Observable;

/**
 * Created by Ruslan on 29.05.2017.
 */

public class LogTransformer<T> implements Observable.Transformer<T,T> {
    private static final String TRANSFORMER_TAG = "transformer";

    @Override
    public Observable<T> call(Observable<T> tObservable) {
        return tObservable
                .doOnSubscribe(() -> Log.i(TRANSFORMER_TAG, "subscribed"))
                .doAfterTerminate(() -> Log.i(TRANSFORMER_TAG,"completed"));
    }
}
