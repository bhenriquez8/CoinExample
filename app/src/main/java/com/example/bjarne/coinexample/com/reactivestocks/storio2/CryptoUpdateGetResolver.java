package com.example.bjarne.coinexample.com.reactivestocks.storio2;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.example.bjarne.coinexample.CryptoUpdate;
import com.pushtorefresh.storio2.sqlite.StorIOSQLite;
import com.pushtorefresh.storio2.sqlite.operations.get.DefaultGetResolver;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by bjarne on 11/28/17.
 */

public class CryptoUpdateGetResolver extends DefaultGetResolver<CryptoUpdate> {

    @NonNull
    @Override
    public CryptoUpdate mapFromCursor(@NonNull StorIOSQLite sql, @NonNull Cursor cursor) {
        final int id = cursor.getInt(cursor.getColumnIndexOrThrow(CryptoUpdateTable.Columns.ID));
        final long dateLong = cursor.getLong(cursor.getColumnIndexOrThrow(CryptoUpdateTable.Columns.DATE));
        final long priceLong = cursor.getLong(cursor.getColumnIndexOrThrow(CryptoUpdateTable.Columns.PRICE));
        final String cryptoSymbol = cursor.getString(cursor.getColumnIndexOrThrow(CryptoUpdateTable.Columns.CRYTPO_SYMBOL));

        Date date = getDate(dateLong);
        BigDecimal price = getPrice(priceLong);

        final CryptoUpdate cryptoUpdate = new CryptoUpdate(cryptoSymbol, price, date);

        cryptoUpdate.setId(id);

        return cryptoUpdate;
    }

    private Date getDate(long dateLong) {
        return new Date(dateLong);
    }

    private BigDecimal getPrice(long priceLong) {
        return new BigDecimal(priceLong).scaleByPowerOfTen(-4);
    }
}
