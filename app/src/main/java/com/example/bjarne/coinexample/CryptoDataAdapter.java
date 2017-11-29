package com.example.bjarne.coinexample;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        for (int i = 0; i < data.size(); i++) {
            CryptoUpdate cryptoUpdate = data.get(i);
            if (cryptoUpdate.getSymbol().equals(newCryptoUpdate.getSymbol())) {
                Log.d("TAG", "Found match");
                if (cryptoUpdate.getPriceUsd().equals(newCryptoUpdate.getPriceUsd())) {
                    Log.d("TAG", "Price Same");
                    return;
                } else {
                    Log.d("TAG", "Price Changed");
                    this.data.set(i, newCryptoUpdate);
                    notifyItemChanged(i);
                    return;
                }
            }
            Log.d("TAG", "No match");
        }

        this.data.add(0, newCryptoUpdate);
        notifyItemInserted(0);
    }
}
