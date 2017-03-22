package com.example.eltgm.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import static android.support.v4.content.ContextCompat.startActivity;

class HistoryItemListener implements View.OnClickListener {
    Context mContext;
    String cityName;

    public HistoryItemListener(Context mContext, String cityName) {
        this.mContext = mContext;
        this.cityName = cityName;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext,MainActivity.class);
        intent.putExtra("cityName",cityName);

        Bundle bundle = null;
        startActivity(mContext,intent,bundle);
    }
}
