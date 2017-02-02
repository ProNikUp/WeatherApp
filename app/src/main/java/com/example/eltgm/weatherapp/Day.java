package com.example.eltgm.weatherapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eltgm on 24.01.17.
 */

public class Day extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_layout);
        WeatherDay weatherDay = (WeatherDay) getIntent().getExtras().getParcelable("weatherDay");

        int[] temp = weatherDay.temp; //создали массив с температурами на 8 дней

        LineChart chart = (LineChart) findViewById(R.id.chart);

        List<Entry> entries = new ArrayList<Entry>(); //входные данные для графика
        int count = 0;
        for(int data:temp) {
            entries.add(new Entry(count, data)); //добавляем точку  на график (по x - временной отрезок,
                                                // по y - температура для этого временного отрезка)
            count+=3;
        }

        LineDataSet dataSet = new LineDataSet(entries,"Temperature"); // создаем набор данных для создание линии на графике
        // (так же входит форматирование текста, линий и тд)
        // , заполняем её данными из entries и создаем метку
        dataSet.setDrawFilled(true); // заполнение цветом
        dataSet.setFillColor(Color.BLUE); //синим

        LineData lineData = new LineData(dataSet); //создаем линию
        chart.setData(lineData); //добавляем линию к графику
        YAxis left = chart.getAxisLeft(); //создаем анимацию по оси Y
        chart.animateY(1500);  //1.5 сек
        chart.setTouchEnabled(false); //касани не распознаются графиком

        chart.invalidate(); //обновили график
    }
}

