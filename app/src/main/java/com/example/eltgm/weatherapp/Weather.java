package com.example.eltgm.weatherapp;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by eltgm on 20.12.2016.
 */

public class Weather{
    private long tmp;
    String date;
    private double pressure;
    private long humidity;
    private double windSpeed;
    private String description;
    private long day;
    private String cityName;
    private String id;

    public Weather(long tmp, long humidity, double windSpeed, double pressure, String description, String cityName , long day){
        this.tmp = tmp;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.description = description;
        this.cityName = cityName;
        this.day = day;
    }
    public void setTmp(long tmp) {
        this.tmp = tmp;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(long humidity) {
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

    long getUnix() {
        return day;
    }

    double getPressure() {
        return pressure;
    }

    double getWindSpeed() {
        return windSpeed;
    }

    long getHumidity() {
        return humidity;
    }

    String getDescription() {
        return description;
    }

    Weather(double temp, String date, String description,
                   double windSpeed, long humidity, long day, double pressure,String id,String cityName){
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

    long getTemp() {
        return tmp;
    }

    String getDate() {
        Date date1 = new Date((getUnix())*1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("E");
        String sq = dateFormat.format(date1);
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
