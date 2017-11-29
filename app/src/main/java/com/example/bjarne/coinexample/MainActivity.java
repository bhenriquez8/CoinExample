package com.example.bjarne.coinexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.bjarne.coinexample.com.reactivestocks.storio2.CryptoUpdateTable;
import com.example.bjarne.coinexample.com.reactivestocks.storio2.StorIOFactory;
import com.pushtorefresh.storio2.sqlite.queries.Query;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.plugins.RxJavaPlugins;

import static hu.akarnokd.rxjava.interop.RxJavaInterop.toV2Observable;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.hello_world_salute)
    TextView helloText;

    @BindView(R.id.no_data_available)
    TextView noDataAvailableView;

    @BindView(R.id.crypto_updates_recycler_view)
    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CryptoDataAdapter cryptoDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RxJavaPlugins.setErrorHandler(ErrorHandler.get());

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
                .onExceptionResumeNext(
                        v2(StorIOFactory.get(this)
                        .get()
                        .listOfObjects(CryptoUpdate.class)
                        .withQuery(Query.builder()
                            .table(CryptoUpdateTable.TABLE)
                            .orderBy("date DESC")
                            .limit(5)
                            .build())
                        .prepare()
                        .asRxObservable())
                        .take(1)
                        .flatMap(Observable::fromIterable)
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cryptoUpdate -> {
                    Log.d("APP", "New update " + cryptoUpdate.getSymbol());
                    noDataAvailableView.setVisibility(View.GONE);
                    cryptoDataAdapter.add(cryptoUpdate);
                }, error -> {
                    if (cryptoDataAdapter.getItemCount() == 0) {
                        noDataAvailableView.setVisibility(View.VISIBLE);
                    }
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

    public static <T> Observable<T> v2(rx.Observable<T> source) {
        return toV2Observable(source);
    }
}
