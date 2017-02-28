package com.example.eltgm.weatherapp;

/**
 * Created by eltgm on 20.12.2016.
 */

public class Weather {
    private int tmp;
    String date;
    private double pressure;
    private int humidity;
    private double windSpeed;
    private String description;
    private int day;
    private String cityName;
    private String id;

    int getDay() {
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

    int getTemp() {
        return tmp;
    }

    String getDate() {
        return date;
    }

    void setDate(String date) {
        this.date = date;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
