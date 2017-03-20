package com.example.eltgm.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import static android.support.v4.content.ContextCompat.startActivity;

public class ItemClickListener implements View.OnClickListener {
    private final int dayNum;
    Context mContext;

    public ItemClickListener(Context mContext, int dayNum) {
        this.mContext = mContext;
        this.dayNum = dayNum;
    } //принимает номер дня, для перехода и контекст

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, Day.class);
        intent.putExtra("dayNum",dayNum);

        Bundle bundle = null;
        startActivity(mContext,intent,bundle);
    } // запускает новое актвити и передает номер дня, информацию о котором нужно вывести
} //слушатель нажатий, применяется для перехода в полную информацию о дне
