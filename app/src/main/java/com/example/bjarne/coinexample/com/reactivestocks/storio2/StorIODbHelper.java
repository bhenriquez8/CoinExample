package com.example.bjarne.coinexample.com.reactivestocks.storio2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.reactivex.annotations.NonNull;

/**
 * Created by bjarne on 11/20/17.
 */

public class StorIODbHelper extends SQLiteOpenHelper {
    StorIODbHelper(@NonNull Context context) {
        super(context, "reactivestocks.db", null, 1);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        db.execSQL(CryptoUpdateTable.createTableQuery());
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
