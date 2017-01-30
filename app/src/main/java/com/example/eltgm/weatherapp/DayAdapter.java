package com.example.eltgm.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by eltgm on 29.01.17.
 */

public class DayAdapter extends BaseAdapter {

    private final WeatherDay weatherDay;
    private final Context mContext;
    private final LayoutInflater lInflater;

    DayAdapter(Context context, WeatherDay weatherDay){
        this.mContext = context;
        this.weatherDay = weatherDay;
        lInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return weatherDay.pressure.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       double pressure = weatherDay.getPressure()[position];
        View view = convertView;
        if(view == null){
            view = lInflater.inflate(R.layout.itemday,parent,false);
        }
        ((TextView)view.findViewById(R.id.tvPress)).setText("Pressure: " + String.valueOf(pressure));

        return view;
    }
}
