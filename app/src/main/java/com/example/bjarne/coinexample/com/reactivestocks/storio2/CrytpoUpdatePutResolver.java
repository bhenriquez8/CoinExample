package com.example.bjarne.coinexample.com.reactivestocks.storio2;

import android.content.ContentValues;

import com.example.bjarne.coinexample.CryptoUpdate;
import com.pushtorefresh.storio2.sqlite.operations.put.DefaultPutResolver;
import com.pushtorefresh.storio2.sqlite.queries.InsertQuery;
import com.pushtorefresh.storio2.sqlite.queries.UpdateQuery;

import io.reactivex.annotations.NonNull;

/**
 * Created by bjarne on 11/20/17.
 */

public class CrytpoUpdatePutResolver extends DefaultPutResolver<CryptoUpdate>{

    @NonNull
    @Override
    protected InsertQuery mapToInsertQuery(@NonNull CryptoUpdate object) {
        return InsertQuery.builder()
                .table(CryptoUpdateTable.TABLE)
                .build();
    }

    @NonNull
    @Override
    protected UpdateQuery mapToUpdateQuery(@NonNull CryptoUpdate object) {
        return UpdateQuery.builder()
                .table(CryptoUpdateTable.TABLE)
                .where(CryptoUpdateTable.Columns.ID + " = ?")
                .whereArgs(object.getId())
                .build();
    }

    @NonNull
    @Override
    protected ContentValues mapToContentValues(@NonNull CryptoUpdate entity) {
        final ContentValues contentValues = new ContentValues();

        contentValues.put(CryptoUpdateTable.Columns.ID, entity.getId());
        contentValues.put(CryptoUpdateTable.Columns.CRYTPO_SYMBOL, entity.getSymbol());
        contentValues.put(CryptoUpdateTable.Columns.PRICE, getPrice(entity));
        contentValues.put(CryptoUpdateTable.Columns.DATE, getDate(entity));

        return contentValues;
    }

    private long getDate(@NonNull CryptoUpdate entity) {
        return entity.getLastUpdated().getTime();
    }

    private long getPrice(@NonNull CryptoUpdate entity) {
        return entity.getPriceUsd().scaleByPowerOfTen(4).longValue();
    }
}
