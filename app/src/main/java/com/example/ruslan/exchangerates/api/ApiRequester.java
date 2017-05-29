package com.example.ruslan.exchangerates.api;

import com.example.ruslan.exchangerates.api.pojo.BaseRate;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Ruslan on 29.05.2017.
 */

public class ApiRequester {
    private static FixerService fixerService;
    private static ApiRequester apiRequester;

    private static final String BASE_URL = "http://api.fixer.io";

    private ApiRequester(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        fixerService = retrofit.create(FixerService.class);
    }

    public static ApiRequester getInstance(){
        if (apiRequester == null){
            apiRequester = new ApiRequester();
        }
        return apiRequester;
    }

    public Observable<BaseRate> getRates(String date){
        return fixerService.getRates(date);
    }

}
