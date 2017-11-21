package com.example.bjarne.coinexample;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by bjarne on 11/19/17.
 */

public interface CryptoService {
    @GET("?")
    Single<List<CryptoUpdate>> cryptoQuery(@Query("limit") int limit);
}
