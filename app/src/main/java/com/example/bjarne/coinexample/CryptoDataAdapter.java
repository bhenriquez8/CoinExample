package com.example.bjarne.coinexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjarne on 11/19/17.
 */

public class CryptoDataAdapter extends RecyclerView.Adapter<CryptoUpdateViewHolder> {
    private final List<CryptoUpdate> data = new ArrayList<>();

    @Override
    public CryptoUpdateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.crypto_update_item,
                parent, false);
        CryptoUpdateViewHolder ch = new CryptoUpdateViewHolder(v);
        return ch;
    }

    @Override
    public void onBindViewHolder(CryptoUpdateViewHolder holder, int position) {
        CryptoUpdate cryptoUpdate = data.get(position);
        holder.setCryptoSymbol(cryptoUpdate.getSymbol());
        holder.setPrice(cryptoUpdate.getPriceUsd());
        holder.setDate(cryptoUpdate.getLastUpdated());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(CryptoUpdate newCryptoUpdate) {
        for (CryptoUpdate cryptoUpdate : data) {
            if (cryptoUpdate.getSymbol().equals(newCryptoUpdate.getSymbol())) {
                if (cryptoUpdate.getPriceUsd().equals(newCryptoUpdate.getPriceUsd())) {
                    return;
                }
                break;
            }
        }

        this.data.add(0, newCryptoUpdate);
        notifyItemInserted(0);
    }
}
