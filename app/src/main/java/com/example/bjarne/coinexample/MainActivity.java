package com.example.bjarne.coinexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AndroidException;
import android.util.Log;
import android.widget.TextView;

import com.example.bjarne.coinexample.com.reactivestocks.storio2.StorIOFactory;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.hello_world_salute)
    TextView helloText;

    @BindView(R.id.crypto_updates_recycler_view)
    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CryptoDataAdapter cryptoDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        cryptoDataAdapter = new CryptoDataAdapter();
        recyclerView.setAdapter(cryptoDataAdapter);

        Observable.just("Please use this app responsibly!")
                .subscribe(s -> helloText.setText(s));

        CryptoService cryptoService = new RetrofitCryptoServiceFactory().create();

        Observable.interval(0, 5, TimeUnit.SECONDS)
                .flatMap(a -> cryptoService.cryptoQuery(5).toObservable())
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::fromIterable)
                .doOnNext(this::saveCryptoUpdate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cryptoUpdate -> {
                    Log.d("APP", "New update " + cryptoUpdate.getSymbol());
                    cryptoDataAdapter.add(cryptoUpdate);
                });
    }

    private void Log(Throwable throwable) {
        Log.e("APP", "Error", throwable);
    }

    private void saveCryptoUpdate(CryptoUpdate cryptoUpdate) {
        log("saveStockUpdate", cryptoUpdate.getSymbol());
        StorIOFactory.get(this)
                .put()
                .object(cryptoUpdate)
                .prepare()
                .asRxSingle()
                .subscribe();
    }

    private void log(Throwable throwable) {
        Log.e("APP", "Error", throwable);
    }

    private void log(String stage, String item) {
        Log.d("APP", stage + ":" + Thread.currentThread().getName() + ":" + item);
    }

    private void log(String stage) {
        Log.d("APP", stage + ":" + Thread.currentThread().getName());
    }
}
