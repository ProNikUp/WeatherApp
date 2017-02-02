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
        WeatherDay weatherDay = getIntent().getExtras().getParcelable("weatherDay");

        int[] temp = weatherDay.temp; //создали массив с температурами на 8 дней
        double[] pres = weatherDay.pressure;
        int[] humidity = weatherDay.humidity;
        double[] windspeed = weatherDay.windSpeed;

        LineChart charttemp = (LineChart) findViewById(R.id.charttemp);
        LineChart chartpres = (LineChart) findViewById(R.id.chartpres);
        LineChart charthumi = (LineChart) findViewById(R.id.charthumidity);
        LineChart chartwind = (LineChart) findViewById(R.id.chartwind);

        createChart(temp,"Temperature", charttemp);
        createChart(pres,"Pressure", chartpres);
        createChart(humidity,"Humidity",charthumi);
        createChart(windspeed,"Wind speed", chartwind);
    }

    public void createChart(int[] dataMas, String descr, LineChart lineChart){
        List<Entry> entries = new ArrayList<Entry>(); //входные данные для графика
        int count = 0;
        for(int data:dataMas) {
            entries.add(new Entry(count, data)); //добавляем точку  на график (по x - временной отрезок,
            // по y - температура для этого временного отрезка)
            count+=3;
        }

        LineDataSet dataSet = new LineDataSet(entries,descr); // создаем набор данных для создание линии на графике
        // (так же входит форматирование текста, линий и тд)
        // , заполняем её данными из entries и создаем метку
        dataSet.setDrawFilled(true); // заполнение цветом
        dataSet.setFillColor(Color.BLUE); //синим

        LineData lineData = new LineData(dataSet); //создаем линию
        lineChart.setData(lineData); //добавляем линию к графику
        YAxis left = lineChart.getAxisLeft(); //создаем анимацию по оси Y
        lineChart.animateY(1500);  //1.5 сек
        lineChart.setTouchEnabled(false); //касани не распознаются графиком
        lineChart.setDrawGridBackground(true);

        lineChart.invalidate(); //обновили график
    }

    public void createChart(double[] dataMas, String descr, LineChart lineChart){
        List<Entry> entries = new ArrayList<Entry>(); //входные данные для графика
        int count = 0;
        for(double data:dataMas) {
            entries.add(new Entry(count, ((float) data))); //добавляем точку  на график (по x - временной отрезок,
            // по y - температура для этого временного отрезка)
            count+=3;
        }

        LineDataSet dataSet = new LineDataSet(entries,descr); // создаем набор данных для создание линии на графике
        // (так же входит форматирование текста, линий и тд)
        // , заполняем её данными из entries и создаем метку
        dataSet.setDrawFilled(true); // заполнение цветом
        dataSet.setFillColor(Color.BLUE); //синим

        LineData lineData = new LineData(dataSet); //создаем линию
        lineChart.setData(lineData); //добавляем линию к графику
        YAxis left = lineChart.getAxisLeft(); //создаем анимацию по оси Y
        lineChart.animateY(1500);  //1.5 сек
        lineChart.setTouchEnabled(false); //касани не распознаются графиком
        lineChart.setDrawGridBackground(true);

        lineChart.invalidate(); //обновили график
    }
}

