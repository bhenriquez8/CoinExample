package com.example.bjarne.coinexample;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bjarne on 11/19/17.
 */

public class CryptoUpdateViewHolder extends RecyclerView.ViewHolder {
    private static final NumberFormat PRICE_FORMAT = new DecimalFormat("#0.00");

    @BindView(R.id.crypto_item_symbol)
    TextView cryptoSymbol;

    @BindView(R.id.crypto_item_date)
    TextView date;

    @BindView(R.id.crypto_item_price)
    TextView price;

    public CryptoUpdateViewHolder(View v) {
        super(v);
        ButterKnife.bind(this,v);
    }

    public void setCryptoSymbol(String cryptoSymbol) {
        this.cryptoSymbol.setText(cryptoSymbol);
    }

    public void setPrice(BigDecimal price) {
        this.price.setText(PRICE_FORMAT.format(price.floatValue()));
    }

    public void setDate(Date date) {
        this.date.setText(DateFormat.format("yyyy-MM-dd hh:mm", date));
    }
}
