package com.example.eltgm.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by eltgm on 29.01.17.
 */
public class ItemClickListener implements View.OnClickListener {
    WeatherDay weatherDay;
    Context mContext;

    public ItemClickListener(Context mContext, WeatherDay w) {
        this.mContext = mContext;
        this.weatherDay = w;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, Day.class);
        intent.putExtra("weatherDay", weatherDay);
        Bundle bundle = null;
        startActivity(mContext,intent,bundle);
    }
}
