package com.example.eltgm.weatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

/**
 * Created by eltgm on 24.01.17.
 */

public class Day extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_layout);
        WeatherDay weatherDay = (WeatherDay) getIntent().getExtras().getParcelable("weatherDay");
        ListView listView = (ListView) findViewById(R.id.lvDay);
        listView.setAdapter(new DayAdapter(this, weatherDay));
    }
}
