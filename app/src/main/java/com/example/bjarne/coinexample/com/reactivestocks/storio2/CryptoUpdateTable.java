package com.example.bjarne.coinexample.com.reactivestocks.storio2;

import java.math.BigDecimal;

/**
 * Created by bjarne on 11/20/17.
 */

public class CryptoUpdateTable {
    public static final String TABLE = "crypto_updates";

    static class Columns {
        static final String ID = "_id";
        static final String CRYTPO_SYMBOL = "crypto_symbol";
        static final String PRICE = "price";
        static final String DATE = "date";
    }

    private CryptoUpdateTable() {
        // stuff goes here
    }

    static String createTableQuery() {
        return "CREATE TABLE " + TABLE + " ("
                + Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Columns.CRYTPO_SYMBOL + " TEXT NOT NULL, "
                + Columns.DATE + " LONG NOT NULL, "
                + Columns.PRICE + " LONG NOT NULL"
                + ");";
    }
}
