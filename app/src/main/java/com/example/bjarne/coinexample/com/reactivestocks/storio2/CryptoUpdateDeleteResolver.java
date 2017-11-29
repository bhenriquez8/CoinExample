package com.example.bjarne.coinexample.com.reactivestocks.storio2;

import android.support.annotation.NonNull;

import com.example.bjarne.coinexample.CryptoUpdate;
import com.pushtorefresh.storio2.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio2.sqlite.queries.DeleteQuery;

/**
 * Created by bjarne on 11/29/17.
 */

public class CryptoUpdateDeleteResolver extends DefaultDeleteResolver<CryptoUpdate> {

    @NonNull
    @Override
    protected DeleteQuery mapToDeleteQuery(@NonNull CryptoUpdate object) {
        return DeleteQuery.builder()
                .table(CryptoUpdateTable.TABLE)
                .where(CryptoUpdateTable.Columns.ID + " = ?")
                .whereArgs(object.getId())
                .build();
    }
}
