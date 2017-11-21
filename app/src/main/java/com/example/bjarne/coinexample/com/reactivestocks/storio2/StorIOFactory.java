package com.example.bjarne.coinexample.com.reactivestocks.storio2;

import android.content.Context;
import android.database.Cursor;

import com.example.bjarne.coinexample.CryptoUpdate;
import com.pushtorefresh.storio2.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio2.sqlite.StorIOSQLite;
import com.pushtorefresh.storio2.sqlite.impl.DefaultStorIOSQLite;
import com.pushtorefresh.storio2.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio2.sqlite.operations.delete.DeleteResolver;
import com.pushtorefresh.storio2.sqlite.operations.get.DefaultGetResolver;
import com.pushtorefresh.storio2.sqlite.operations.get.GetResolver;
import com.pushtorefresh.storio2.sqlite.queries.DeleteQuery;

import io.reactivex.annotations.NonNull;

/**
 * Created by bjarne on 11/20/17.
 */

public class StorIOFactory {
    private static StorIOSQLite INSTANCE;

    public synchronized static StorIOSQLite get(Context context) {
        if (INSTANCE != null) {
            return INSTANCE;
        }

        INSTANCE = DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(new StorIODbHelper(context))
                .addTypeMapping(CryptoUpdate.class, SQLiteTypeMapping.<CryptoUpdate>builder()
                        .putResolver(new CrytpoUpdatePutResolver())
                        .getResolver(createGetResolver())
                        .deleteResolver(createDeleteResolver())
                        .build())
                .build();

        return INSTANCE;
    }

    private static DeleteResolver<CryptoUpdate> createDeleteResolver() {
        return new DefaultDeleteResolver<CryptoUpdate>() {
            @NonNull
            @Override
            protected DeleteQuery mapToDeleteQuery(@NonNull CryptoUpdate object) {
                return null;
            }
        };
    }

    private static GetResolver<CryptoUpdate> createGetResolver() {
        return new DefaultGetResolver<CryptoUpdate>() {
            @NonNull
            @Override
            public CryptoUpdate mapFromCursor(@NonNull StorIOSQLite storIOSQLite, @NonNull Cursor cursor) {
                return null;
            }
        };
    }
}
