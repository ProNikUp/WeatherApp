package com.example.eltgm.weatherapp;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by eltgm on 20.12.2016.
 */

public class Weather{
    private int tmp;
    String date;
    private double pressure;
    private int humidity;
    private double windSpeed;
    private String description;
    private long day;
    private String cityName;
    private String id;


    public void setTmp(int tmp) {
        this.tmp = tmp;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    long getDay() {
        return day;
    }

    double getPressure() {
        return pressure;
    }

    double getWindSpeed() {
        return windSpeed;
    }

    int getHumidity() {
        return humidity;
    }

    String getDescription() {
        return description;
    }

    Weather(double temp, String date, String description,
                   double windSpeed, int humidity, int day, double pressure,String id,String cityName){
        this.tmp = ((int) Math.round(temp));
        this.date = date;
        this.description = description;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.day = day;
        this.pressure = pressure;
        this.id = id;
        this.cityName = cityName;
    }

    public Weather(String json){

    }

    int getTemp() {
        return tmp;
    }

    String getDate() {
        Date date1 = new Date((getDay()-86400)*1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("E");
        return dateFormat.format(date1);
    }

    public String getCityName() {
        return cityName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
