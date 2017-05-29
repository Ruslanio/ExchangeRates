package com.example.ruslan.exchangerates.api;

import com.example.ruslan.exchangerates.api.pojo.BaseRate;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by Ruslan on 29.05.2017.
 */

public interface FixerService {

    @GET("/{date}")
    Observable<BaseRate> getRates(@Path("date") String date);
}
