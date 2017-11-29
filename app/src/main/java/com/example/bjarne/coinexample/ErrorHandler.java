package com.example.bjarne.coinexample;

import android.util.Log;

import io.reactivex.functions.Consumer;

/**
 * Created by bjarne on 11/28/17.
 */

public class ErrorHandler implements Consumer<Throwable> {

    private static final ErrorHandler INSTANCE = new ErrorHandler();

    public static ErrorHandler get() {
        return INSTANCE;
    }

    private ErrorHandler() {}

    @Override
    public void accept(Throwable throwable) throws Exception {
        Log.e("APP", "Error on " + Thread.currentThread().getName() + ":", throwable);
    }
}
